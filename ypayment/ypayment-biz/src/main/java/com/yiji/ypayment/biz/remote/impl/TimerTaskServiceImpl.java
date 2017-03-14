/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:37:02 创建
 */
package com.yiji.ypayment.biz.remote.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yiji.ypayment.biz.enums.TransferTradeMode;
import com.yiji.ypayment.biz.remote.FTPService;
import com.yiji.ypayment.biz.remote.OpenApiArchService;
import com.yiji.ypayment.biz.remote.PaymentQueryRemoteService;
import com.yiji.ypayment.biz.remote.RepaymentService;
import com.yiji.ypayment.biz.remote.TimerTaskService;
import com.yiji.ypayment.biz.remote.TradeRemoteService;
import com.yiji.ypayment.biz.remote.base.RemoteServiceBase;
import com.yiji.ypayment.biz.remote.impl.FTPServiceImpl.OrderItem;
import com.yiji.ypayment.biz.service.ypayment.BillItemInfoService;
import com.yiji.ypayment.biz.service.ypayment.PaymentItemInfoService;
import com.yiji.ypayment.dal.entity.business.BillItemInfo;
import com.yiji.ypayment.dal.entity.business.PaymentItemInfo;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.entity.business.PaymentTrade;
import com.yiji.ypayment.dal.entity.business.RepayOrderInfo;
import com.yiji.ypayment.dal.entity.business.UndoPayment;
import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;
import com.yiji.ypayment.facade.enums.UndoPaymentStatusEnum;
import com.yjf.common.lang.result.Status;
import com.yjf.quickpayment.route.info.PaymentInfo;
import com.yjf.quickpayment.route.result.RouteOrderStatusResult;
import com.yjf.quickpayment.route.result.RouteUndoOrderStatusResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("timerTaskService")
public class TimerTaskServiceImpl extends RemoteServiceBase implements TimerTaskService {
	
	@Autowired
	private PaymentQueryRemoteService paymentQueryRemoteService;
	
	@Autowired
	private TradeRemoteService tradeRemoteService;
	
	@Autowired
	private PaymentItemInfoService paymentItemInfoService;
	
	@Autowired
	private OpenApiArchService openApiArchService;
	
	@Autowired
	private BillItemInfoService billItemInfoService;
	
	@Autowired
	private FTPService ftpService;
	
	@Autowired
	private RepaymentService repaymentService;
	
	/**
	 * 
	 * @see com.yiji.ypayment.biz.remote.TimerTaskService#updatePaymentOrderStstus()
	 */
	@Override
	public void updatePaymentOrderStatus() {
		//更新订单状态
		List<PaymentOrder> paymentOrders = paymentOrderService.findByPaymentStatus(PaymentItemStatusEnum.HANGUP);
		for (PaymentOrder paymentOrder : paymentOrders) {
			RouteOrderStatusResult result = paymentQueryRemoteService.findOrderStatus(paymentOrder.getPaymentOrderNo());
			if (result != null && result.isSuccess()) {
				paymentOrder.setEndTime(new Date());
				if (PaymentItemStatusEnum.SUCCESS.getCode().equalsIgnoreCase(result.getRouteStatus())) {
					paymentOrder.setPaymentStatus(PaymentItemStatusEnum.SUCCESS);
					paymentOrder.setPaymentExtStatus(PaymentItemStatusEnum.SUCCESS);
					paymentOrderService.save(paymentOrder);
					savePaymentItem(paymentOrder, result.getPaymentInfo(), result.getRouteStatus());
					//转账
					tradeRemoteService.transferTrade(paymentOrder);
				} else if (PaymentItemStatusEnum.FAIL.getCode().equalsIgnoreCase(result.getRouteStatus())) {
					paymentOrder.setPaymentStatus(PaymentItemStatusEnum.FAIL);
					paymentOrder.setPaymentExtStatus(PaymentItemStatusEnum.FAIL);
					if (result.getPaymentInfo() != null) {
						paymentOrder.setMemo(result.getPaymentInfo().getError());
					}
					paymentOrderService.save(paymentOrder);
					if (result.getPaymentInfo() != null) {
						savePaymentItem(paymentOrder, result.getPaymentInfo(), result.getRouteStatus());
					}
					//解冻用户冻结金额
					tradeRemoteService.unfreezeAccount(paymentOrder);
				}
				//补缴订单增加
				repaymentService.HandRepaymentResult(paymentOrder);
			}
		}
	}
	
	/**
	 * 
	 * @see com.yiji.ypayment.biz.remote.TimerTaskService#transferProcessoringTrade()
	 */
	@Override
	public void transferProcessoringTrade() {
		//查询出转账中的订单
		List<PaymentOrder> paymentOrders = paymentOrderService.findByPaymentStatusAndTradeStatus(
			PaymentItemStatusEnum.SUCCESS, TransferTradeStatusEnum.PROCESSING);
		for (PaymentOrder paymentOrder : paymentOrders) {
			List<PaymentTrade> paymentTrades = paymentTradeService.findByPaymentOrderNo(paymentOrder.getPaymentOrderNo());
			if(FavourableEnum.TRUE == paymentOrder.getFavourable()){
				Status status = tradeRemoteService.batchTransfer(paymentTrades, TransferTradeMode.TRANSFER_UNFREEZE);
				//更新订单转账状态
				paymentOrder.setTradeStatus(convertTradeStatus(status));
				paymentOrderService.update(paymentOrder);
			}else{
				Status status = tradeRemoteService.transferTrade(paymentTrades.get(0), TransferTradeMode.TRANSFER_UNFREEZE);
				//更新订单转账状态
				paymentOrder.setTradeStatus(convertTradeStatus(status));
				paymentOrderService.update(paymentOrder);
			}
		}
	}
	
	private void savePaymentItem(PaymentOrder paymentOrder, PaymentInfo paymentInfo, String status) {
		PaymentItemInfo paymentItemInfo = paymentItemInfoService.findByPaymentOrderNo(paymentOrder.getPaymentOrderNo());
		if (paymentItemInfo == null) {
			paymentItemInfo = buildPaymentItemInfo(paymentInfo, paymentOrder);
		}
		paymentItemInfo.setPaymentInfoStatus(status);
		paymentItemInfoService.save(paymentItemInfo);
		List<BillItemInfo> billItemInfos = billItemInfoService.findByPaymentOrderNo(paymentOrder.getPaymentOrderNo());
		if (CollectionUtils.isEmpty(billItemInfos)) {
			billItemInfos = buildBillItemInfo(paymentInfo, paymentOrder.getPaymentOrderNo());
			billItemInfoService.saves(billItemInfos);
		}
	}
	
	private TransferTradeStatusEnum convertTradeStatus(Status status) {
		if (Status.SUCCESS == status) {
			return TransferTradeStatusEnum.SUCCESS;
		} else if (Status.FAIL == status) {
			return TransferTradeStatusEnum.FAIL;
		} else {
			return TransferTradeStatusEnum.PROCESSING;
		}
	}
	
	/**
	 * 
	 * @see com.yiji.ypayment.biz.remote.TimerTaskService#updateUndoPaymentStatus()
	 */
	@Override
	public void updateUndoPaymentStatus() {
		List<UndoPayment> undoPayments = undoPaymentService.findByUndoStatus(UndoPaymentStatusEnum.HANGUP);
		for (UndoPayment undoPayment : undoPayments) {
			RouteUndoOrderStatusResult result = paymentQueryRemoteService.findUndoOrderStatus(undoPayment.getUndoPaymentNo());
			if (result != null && result.isSuccess()) {
				if (PaymentItemStatusEnum.SUCCESS.getCode().equalsIgnoreCase(result.getRouteStatus())) {
					undoPayment.setUndoStatus(UndoPaymentStatusEnum.SUCCESS);
					logger.info("定时任务更新返销表状态为成功, undoPayment：{}", undoPayment);
					undoPaymentService.save(undoPayment);
					//更新订单状态
					PaymentOrder paymentOrder = paymentOrderService.findByPaymentOrderNo(undoPayment.getPaymentOrderNo());
					paymentOrder.setPaymentStatus(PaymentItemStatusEnum.PAY_BACK);
					paymentOrder.setPaymentExtStatus(PaymentItemStatusEnum.PAY_BACK);
					logger.info("定时任务更新返销表状态为失败，订单表状态为返销成功, paymentOrder：{}", paymentOrder);
					paymentOrderService.save(paymentOrder);
					//返销成功，转账
					tradeRemoteService.transferTrade(undoPayment);
				} else if (PaymentItemStatusEnum.FAIL.getCode().equalsIgnoreCase(result.getRouteStatus())) {
					undoPayment.setUndoStatus(UndoPaymentStatusEnum.FAIL);
					if (result.getDescription() != null) {
						undoPayment.setMemo(result.getDescription());
					}
					logger.info("定时任务更新返销表状态为失败, undoPayment：{}", undoPayment);
					undoPaymentService.save(undoPayment);
					//更新订单状态
					PaymentOrder paymentOrder = paymentOrderService.findByPaymentOrderNo(undoPayment.getPaymentOrderNo());
					paymentOrder.setPaymentStatus(PaymentItemStatusEnum.SUCCESS);
					paymentOrder.setPaymentExtStatus(PaymentItemStatusEnum.SUCCESS);
					logger.info("定时任务更新返销表状态为失败，订单表状态为成功, paymentOrder：{}", paymentOrder);
					paymentOrderService.save(paymentOrder);
				}
			}
		}
	}

	/**
	 * 
	 * @see com.yiji.ypayment.biz.remote.TimerTaskService#updateApiAsyncNotify()
	 */
	@Override
	public void updateApiAsyncNotify() {
		List<PaymentOrder> paymentOrders = paymentOrderService.findByNotifyOpenApi(true);
		for (PaymentOrder paymentOrder : paymentOrders) {
			openApiArchService.apiAsyncNotify(paymentOrder);
		}
		
		List<UndoPayment> undoPayments = undoPaymentService.findByNotifyOpenApi(true);
		for(UndoPayment undoPayment : undoPayments){
			openApiArchService.asyncNotifyUndoOrder(undoPayment);
		}
	}

	/**
	 * 
	 * @see com.yiji.ypayment.biz.remote.TimerTaskService#checkZhongXinStatus()
	 */
	@Override
	public void checkZhongXinStatus(String dateStr) {
		String fileName = dateStr + "_SUCCESS";
		List<OrderItem> orderItems = ftpService.getPaymentOrder(fileName);
		for(OrderItem orderItem : orderItems){
			PaymentOrder paymentOrder = paymentOrderService.findByPaymentOrderNo(orderItem.getPaymentOrderNo());
			if(paymentOrder.getPaymentStatus() != PaymentItemStatusEnum.SUCCESS){
				RepayOrderInfo repayOrderInfo = repayOrderInfoService.findByPaymentOrderNo(orderItem.getPaymentOrderNo());
				if(repayOrderInfo == null){
					saveRepayOrder(paymentOrder);
				}
			}
		}
	}
	
	private void saveRepayOrder(PaymentOrder paymentOrder){
		RepayOrderInfo repayOrderInfo = new RepayOrderInfo();
		repayOrderInfo.setPaymentOrderNo(paymentOrder.getPaymentOrderNo());
		repayOrderInfo.setUserId(paymentOrder.getUserId());
		repayOrderInfo.setPartnerId(paymentOrder.getPartnerId());
		repayOrderInfo.setPayFrom(paymentOrder.getPayFrom());
		repayOrderInfo.setPaymentType(paymentOrder.getPaymentType());
		repayOrderInfo.setPaymentStatus(paymentOrder.getPaymentStatus());
		repayOrderInfo.setZhongxinStatus(PaymentItemStatusEnum.SUCCESS);
		repayOrderInfo.setPaymentAmount(paymentOrder.getPaymentAmount());
		repayOrderInfo.setStartTime(paymentOrder.getStartTime());
		repayOrderInfo.setEndTime(paymentOrder.getEndTime());
		logger.info("保存中信对账订单，paymentOrder:{}", paymentOrder);
		repayOrderInfoService.save(repayOrderInfo);
	}

}



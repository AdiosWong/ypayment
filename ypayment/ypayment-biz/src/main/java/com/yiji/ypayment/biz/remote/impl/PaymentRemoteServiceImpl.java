/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author faZheng 下午3:17:12 创建
 */
package com.yiji.ypayment.biz.remote.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.yiji.authenticate.enums.PactPurposeEnum;
import com.yiji.authenticate.facade.query.order.QueryByPactNoOrder;
import com.yiji.ypayment.biz.common.utils.PayTypeToBusiTypeUtils;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.remote.OpenApiArchService;
import com.yiji.ypayment.biz.remote.PayengineService;
import com.yiji.ypayment.biz.remote.PaymentBindingRemoteService;
import com.yiji.ypayment.biz.remote.PaymentQueryRemoteService;
import com.yiji.ypayment.biz.remote.PaymentRemoteService;
import com.yiji.ypayment.biz.remote.QueryBankCardService;
import com.yiji.ypayment.biz.remote.TradeRemoteService;
import com.yiji.ypayment.biz.remote.base.RemoteServiceBase;
import com.yiji.ypayment.biz.remote.info.OrderInfo;
import com.yiji.ypayment.biz.remote.info.PactBankCardInfo;
import com.yiji.ypayment.biz.remote.info.PaymentChannelInfo;
import com.yiji.ypayment.biz.remote.info.PaymentQueryInfo;
import com.yiji.ypayment.biz.remote.info.PaymentResultInfo;
import com.yiji.ypayment.biz.remote.info.ResourceInstInfo;
import com.yiji.ypayment.biz.remote.order.ApplyPaymentForm;
import com.yiji.ypayment.biz.remote.order.DeductDepositOrder;
import com.yiji.ypayment.biz.remote.order.DepositPaymentOrder;
import com.yiji.ypayment.biz.remote.order.PaymentBizOrder;
import com.yiji.ypayment.biz.remote.order.PaymentRouteQueryOrder;
import com.yiji.ypayment.common.utils.Dates;
import com.yiji.ypayment.dal.entity.business.BillItemInfo;
import com.yiji.ypayment.dal.entity.business.DeductDepositInfo;
import com.yiji.ypayment.dal.entity.business.PaymentBindingInfo;
import com.yiji.ypayment.dal.entity.business.PaymentItemInfo;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.entity.business.PlatformType;
import com.yiji.ypayment.dal.entity.business.UndoPayment;
import com.yiji.ypayment.dal.enums.DepositStatusEnum;
import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.facade.enums.PayWayEnum;
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.enums.UndoPaymentStatusEnum;
import com.yiji.ypayment.facade.info.payment.PaymentItem;
import com.yiji.ypayment.facade.info.query.ItemInfo;
import com.yiji.ypayment.facade.info.query.PayItemInfo;
import com.yiji.ypayment.facade.info.query.PayOrderInfo;
import com.yiji.ypayment.facade.order.payment.ApplyPaymentOrder;
import com.yiji.ypayment.facade.order.payment.PaymentBindingOrder;
import com.yiji.ypayment.facade.order.payment.UndoPaymentOrder;
import com.yiji.ypayment.facade.order.query.PaymentQueryOrder;
import com.yiji.ypayment.facade.result.payment.PaymentBindingResult;
import com.yiji.ypayment.facade.result.payment.PaymentUndoOrderResult;
import com.yjf.common.collection.CollectionUtils;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.OrderCheckException;
import com.yjf.common.util.StringUtils;
import com.yjf.customer.service.order.UniformStringQueryOrder;
import com.yjf.quickpayment.route.info.PaymentInfo;
import com.yjf.quickpayment.route.order.RoutePaymentOrder;
import com.yjf.quickpayment.route.order.RouteUndoPaymentOrder;
import com.yjf.quickpayment.route.result.RoutePaymentResult;
import com.yjf.quickpayment.route.result.RouteUndoPaymentResult;

/**
 * <1>充值缴费
 * <2>返销
 * 
 * @author xinqing@yiji.com
 *
 */
@Service("paymentRemoteService")
public class PaymentRemoteServiceImpl extends RemoteServiceBase implements PaymentRemoteService {
	
	@Autowired
	private TradeRemoteService tradeRemoteService;
	@Autowired
	private PaymentBindingRemoteService paymentBindingRemoteService;
	@Autowired
	private PaymentQueryRemoteService queryRemoteService;
	@Autowired
	private QueryBankCardService queryBankCardService;
	@Autowired
	private PayengineService payengineService;
	@Autowired
	private OpenApiArchService openApiArchService;
	
	/**
	 * @param applyPaymentForm
	 * @return
	 * @see com.yiji.ypayment.biz.remote.PaymentRemoteService#payment(com.yiji.ypayment.biz.remote.order.ApplyPaymentForm)
	 */
	@Override
	public PaymentResultInfo payment(ApplyPaymentForm applyPaymentForm) {

		PaymentResultInfo resultInfo = new PaymentResultInfo();
		PaymentBizOrder paymentBizOrder = new PaymentBizOrder();
		PaymentTypeEnum paymentType = applyPaymentForm.getPaymentType();
		paymentBizOrder.setApplyPaymentOrder(applyPaymentForm);
		
		applyPaymentForm.check();

		// 业务平台check
		checkPlatformAndPaymentType(applyPaymentForm.getPlatformType(), applyPaymentForm.getPaymentType(),
			applyPaymentForm.getPartnerId(), applyPaymentForm.getPayWay());
		
		// 1.防重复提交check
		if (!checkOutOrderNo(applyPaymentForm, resultInfo)) {
			return resultInfo;
		}
		
		// 2.已经绑定再缴费,校验用户是否签约绑卡
		if (StringUtils.isNotBlank(applyPaymentForm.getContractNo())
			&& PaymentTypeEnum.MOBILE != applyPaymentForm.getPaymentType()) {
			PaymentBindingInfo bindingInfo = paymentBindingInfoService.findByContractNo(applyPaymentForm.getContractNo());
			//校验缴费绑定信息
			checkBindingInfo(bindingInfo, applyPaymentForm);
			applyPaymentForm.setUserCode(bindingInfo.getUserCode());
			applyPaymentForm.setResourceCode(bindingInfo.getResourceCode());
		}
		
		// 3.手机充值, 不需要查询欠费
		logger.info("用户缴费类型, paymentType:{}", paymentType.getMessage());
		if (PaymentTypeEnum.MOBILE == paymentType) {
			//绑定
			reChargeBinding(applyPaymentForm);
			//创建订单
			buildPaymentOrderByMoblie(paymentBizOrder);
		} else {
			PaymentQueryOrder queryOrder = new PaymentQueryOrder();
			queryOrder.setPaymentType(applyPaymentForm.getPaymentType());
			queryOrder.setPlatformType(applyPaymentForm.getPlatformType());
			queryOrder.setResourceCode(applyPaymentForm.getResourceCode());
			queryOrder.setUserCode(applyPaymentForm.getUserCode());
			queryOrder.setUserId(applyPaymentForm.getUserId());
			queryOrder.setStoreId(applyPaymentForm.getStoreId());
			
			queryOrder.setGid(applyPaymentForm.getGid());
			queryOrder.setPartnerId(applyPaymentForm.getPartnerId());
			queryOrder.setMerchOrderNo(applyPaymentForm.getMerchOrderNo());
			PaymentQueryInfo paymentQueryInfo = queryRemoteService.queryPayment(queryOrder);
			
			if(applyPaymentForm.getPlatformType().equals("ZhongXin")) {
				//中信缴费特殊处理，不校验
				buildZhongxinPaymentOrder(paymentBizOrder, paymentQueryInfo);
			}else{
				//校验缴费订单
				checkPaymentOrders(paymentBizOrder, paymentQueryInfo);
				
				//创建缴费订单
				buildPaymentOrder(paymentBizOrder, paymentQueryInfo);
			}
		}
		
		// 4. 校验余额是否充足
		checkAvailableMoney(applyPaymentForm);
		
		// 5. 持久化缴费对象
		paymentOrderDB(paymentBizOrder.getPaymentOrders());
		
		try {
			// 6. 代扣缴费，等待代扣异步通知，再发起资源路由缴费
			if (applyPaymentForm.getPayWay() == PayWayEnum.BY_DEPOSIT) {
				logger.info("支付类型为代扣支付, 缴费外部订单号：{}", applyPaymentForm.getOutBizNo());
				// 发起代扣支付
				DepositStatusEnum depositStatus = sendDeposit(applyPaymentForm, resultInfo);
				if (DepositStatusEnum.SUCCESS == depositStatus) {
					//代扣成功,发起缴费
					DepositPaymentOrder depositOrder = buildDepositOrder(applyPaymentForm);
					depositPayment(depositOrder, resultInfo);
				} else if (DepositStatusEnum.FAILURE == depositStatus) {
					// 代扣失败,更新订单状态
					updatePaymentOrders(applyPaymentForm.getOutBizNo(), resultInfo.getDescription());
				}
			}else{
				// 余额支付，直接发起缴费
				DepositPaymentOrder depositOrder = buildDepositOrder(applyPaymentForm);
				depositPayment(depositOrder, resultInfo);
			}
			return resultInfo;
		} catch (OrderCheckException orderException) {
			logger.info("缴费入参check失败：", orderException.getMessage());
			resultInfo.setFailResultWithMsg(PaymentResultCode.INVAILD_PARAMETER, orderException.getMessage());
		} catch (PaymentException payExeception) {
			logger.info("缴费失败", payExeception.getMessage());
			resultInfo.setFailResultWithMsg(payExeception.getResultCode(), payExeception.getErrorMessage());
		} catch (Exception e) {
			logger.error("缴费发生异常", e);
			resultInfo.setFailResultWithMsg(PaymentResultCode.UNKNOWN_EXCEPTION, "缴费发生系统异常!");
		}
		
		// 发生异常更新订单状态
		updatePaymentOrders(applyPaymentForm.getOutBizNo(), resultInfo.getDescription());
		return resultInfo;
	}
	
	@Override
	public PaymentResultInfo depositPayment(DepositPaymentOrder paymentOrder, PaymentResultInfo resultInfo) {
		logger.info("进入路由缴费,入参paymentOrder:{}", paymentOrder);
		try {
			// 入参check
			paymentOrder.check();
			
			//如果充值且商户有优惠
			if(FavourableEnum.TRUE == paymentOrder.getFavourable()){
				Money paymentAmount = paymentOrder.getPaymentAmount();
				UniformStringQueryOrder parternerOrder = new UniformStringQueryOrder();
				parternerOrder.setGid(paymentOrder.getGid());
				parternerOrder.setMerchOrderNo(paymentOrder.getMerchOrderNo());
				parternerOrder.setPartnerId(paymentOrder.getPartnerId());
				parternerOrder.setParam(paymentOrder.getPartnerId());
				Money partnerAmount = customerClient.getAvailableMoney(parternerOrder);
				if (paymentAmount.greaterThan(partnerAmount)) {
					logger.info("缴费金额大于易极付商户余额, 缴费金额：{}, 易极付商户余额：{}", paymentAmount.toStandardString(),
						partnerAmount.toStandardString());
					throw new PaymentException(PaymentResultCode.NOT_ENOUGH_BALANCE);
				}
				Money payAmount = paymentOrder.getPayAmount();
				UniformStringQueryOrder userOrder = new UniformStringQueryOrder();
				userOrder.setGid(paymentOrder.getGid());
				userOrder.setMerchOrderNo(paymentOrder.getMerchOrderNo());
				userOrder.setPartnerId(paymentOrder.getPartnerId());
				userOrder.setParam(paymentOrder.getUserId());
				Money userAmount = customerClient.getAvailableMoney(userOrder);
				if (payAmount.greaterThan(userAmount)) {
					logger.info("优惠金额大于易极付商户余额, 优惠金额：{}, 易极付用户余额：{}", paymentAmount.toStandardString(),
						userAmount.toStandardString());
					throw new PaymentException(PaymentResultCode.NOT_ENOUGH_BALANCE);
				}
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setGid(paymentOrder.getGid());
				orderInfo.setMerchOrderNo(paymentOrder.getMerchOrderNo());
				orderInfo.setPartnerId(paymentOrder.getPartnerId());
				//冻结商户金额
				boolean freezePartnerResult = tradeRemoteService.freeze(orderInfo, paymentOrder.getPartnerId(), paymentAmount);
				if (!freezePartnerResult) {
					logger.info("冻结缴费金额失败");
					throw new PaymentException(PaymentResultCode.FREEZE_EXCEPTION);
				}
			    //冻结用户金额
				boolean freezeUserResult = tradeRemoteService.freeze(orderInfo, paymentOrder.getUserId(), payAmount);
				if (!freezeUserResult) {
					logger.info("冻结缴费金额失败");
					throw new PaymentException(PaymentResultCode.FREEZE_EXCEPTION);
				}
			}else{
				// 缴费金额、用户余额校验
				Money paymentAmount = paymentOrder.getPaymentAmount();
				UniformStringQueryOrder order = new UniformStringQueryOrder();
				order.setGid(paymentOrder.getGid());
				order.setMerchOrderNo(paymentOrder.getMerchOrderNo());
				order.setPartnerId(paymentOrder.getPartnerId());
				order.setParam(paymentOrder.getUserId());
				Money availableAmount = customerClient.getAvailableMoney(order);
				if (paymentAmount.greaterThan(availableAmount)) {
					logger.info("缴费金额大于易极付用户余额, 缴费金额：{}, 易极付用户余额：{}", paymentAmount.toStandardString(),
						availableAmount.toStandardString());
					throw new PaymentException(PaymentResultCode.NOT_ENOUGH_BALANCE);
				}
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setGid(paymentOrder.getGid());
				orderInfo.setMerchOrderNo(paymentOrder.getMerchOrderNo());
				orderInfo.setPartnerId(paymentOrder.getPartnerId());
				// 5. 冻结缴费金额
				boolean freezeResult = tradeRemoteService.freeze(orderInfo, paymentOrder.getUserId(), paymentAmount);
				if (!freezeResult) {
					logger.info("冻结缴费金额失败");
					throw new PaymentException(PaymentResultCode.FREEZE_EXCEPTION);
				}
			}
			
			// 6. 如果有垃圾费，先缴垃圾费
			sendRoutePayment(paymentOrder, true, resultInfo);
			
			// 7. 其他费种处理
			sendRoutePayment(paymentOrder, false, resultInfo);
			
			return resultInfo;
		} catch (OrderCheckException orderException) {
			logger.info("路由缴费入参check失败：", orderException.getMessage());
			resultInfo.setFailResultWithMsg(PaymentResultCode.INVAILD_PARAMETER, orderException.getMessage());
		} catch (PaymentException payExeception) {
			logger.info("路由缴费失败", payExeception.getMessage());
			resultInfo.setFailResultWithMsg(payExeception.getResultCode(), payExeception.getErrorMessage());
		} catch (Exception e) {
			logger.error("路由缴费发生异常", e);
			resultInfo.setFailResultWithMsg(PaymentResultCode.UNKNOWN_EXCEPTION, "缴费发生系统异常!");
		}
		
		// 发生异常更新订单状态
		updatePaymentOrders(paymentOrder.getOutBizNo(), resultInfo.getDescription());

		return resultInfo;
	}
	
	/**
	 * 返销
	 * @param routeUndoPaymentOrder
	 * @return
	 * @see com.yiji.ypayment.biz.remote.PaymentRemoteService#undoPayment(com.yjf.quickpayment.route.order.RouteUndoPaymentOrder)
	 */
	@Override
	public PaymentUndoOrderResult undoPayment(UndoPaymentOrder undoPaymentOrder) {
		PaymentUndoOrderResult resultInfo = new PaymentUndoOrderResult();
		
		undoPaymentOrder.check();
		
		// 1.校验业务平台
		checkPlatform(undoPaymentOrder.getPlatformType(), undoPaymentOrder.getPartnerId());
		
		// 防重复提交
		UndoPayment undoPayInfo = undoPaymentService.findByUndoPaymentNo(undoPaymentOrder.getUndoPaymentNo());
		if (undoPayInfo != null) {
			logger.info("重复提交返销订单, 订单号：{}", undoPaymentOrder.getUndoPaymentNo());
			resultInfo.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
			resultInfo.setUndoOrderStatus(undoPayInfo.getUndoStatus());
			return resultInfo;
		}
		
		// 订单校验
		PaymentOrder paymentOrder = paymentOrderService.findByPaymentOrderNo(undoPaymentOrder.getPaymentOrderNo());
		if (paymentOrder == null) {
			logger.info("返销时未能查询到订单,订单号：", undoPaymentOrder.getPaymentOrderNo());
			throw new PaymentException(PaymentResultCode.PAYMENT_PAYBACK_FAIL, "返销时未能查询到订单");
		}
		
		if (PaymentItemStatusEnum.SUCCESS != paymentOrder.getPaymentStatus()
			|| TransferTradeStatusEnum.SUCCESS != paymentOrder.getTradeStatus()) {
			logger.info("返销订单不是成功状态,订单号：", undoPaymentOrder.getPaymentOrderNo());
			throw new PaymentException(PaymentResultCode.PAYMENT_PAYBACK_FAIL, "返销订单不是成功状态");
		}
		
		if (!paymentOrder.getPayFrom().equals(undoPaymentOrder.getPlatformType())
			|| !paymentOrder.getUserId().equals(undoPaymentOrder.getUserId())) {
			logger.info("返销平台类型或userId和订单不匹配,订单号：", undoPaymentOrder.getPaymentOrderNo());
			throw new PaymentException(PaymentResultCode.PAYMENT_PAYBACK_FAIL, "返销平台类型或userId和订单不匹配");
		}
		
		if (PaymentTypeEnum.MOBILE == paymentOrder.getPaymentType()) {
			logger.info("话费充值不支持返销,订单号：", undoPaymentOrder.getPaymentOrderNo());
			throw new PaymentException(PaymentResultCode.PAYMENT_PAYBACK_FAIL, "话费充值不支持返销");
		}
		
		UndoPayment undoPayment = new UndoPayment();
		//持久化返销数据
		saveUndoPayment(undoPayment, paymentOrder, undoPaymentOrder);
		//创建返销订单
		RouteUndoPaymentOrder routeUndoPaymentOrder = buildRouteUndoPaymentOrder(undoPaymentOrder);
		
		RouteUndoPaymentResult result = null;
		try {
			result = routePaymentClient.undoPayment(routeUndoPaymentOrder);
        } catch (Exception e) {
			logger.error("返销发生系统异常：{}", e);
			resultInfo.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
			resultInfo.setUndoOrderStatus(UndoPaymentStatusEnum.HANGUP);
			//返销异常，将返销订单更新为挂起
			undoPayment.setUndoStatus(UndoPaymentStatusEnum.HANGUP);
			undoPaymentService.update(undoPayment);
			//设置返销异步通知标志
			setNotifyOpenApi(undoPayment);
			//更新订单状态
			paymentOrder.setPaymentStatus(PaymentItemStatusEnum.CANCEL);
			paymentOrder.setPaymentExtStatus(PaymentItemStatusEnum.CANCEL);
			logger.info("返销挂起，更新订单表，paymentOrder：{}", paymentOrder);
			paymentOrderService.save(paymentOrder);
			return resultInfo;
		}
		
		//更新返销数据库
		updateUndoPayment(result, undoPayment, resultInfo, paymentOrder);
		return resultInfo;
	}
	
	public RouteUndoPaymentOrder buildRouteUndoPaymentOrder(UndoPaymentOrder undoPaymentOrder){
		RouteUndoPaymentOrder routeUndoPaymentOrder = new RouteUndoPaymentOrder();
		//统一订单号修改
		routeUndoPaymentOrder.setGid(undoPaymentOrder.getGid());
		routeUndoPaymentOrder.setPartnerId(undoPaymentOrder.getPartnerId());
		routeUndoPaymentOrder.setMerchOrderNo(undoPaymentOrder.getMerchOrderNo());
		
		routeUndoPaymentOrder.setOrderSerialNumber(undoPaymentOrder.getUndoPaymentNo());
		routeUndoPaymentOrder.setOriginalOrderSerialNumber(undoPaymentOrder.getPaymentOrderNo());
		routeUndoPaymentOrder.setUndoApproach(undoPaymentOrder.getUndoApproach().getCode());
		return routeUndoPaymentOrder;
	}
	
	/**
	 * 代扣支付
	 * @param applyPaymentForm
	 * @param resultInfo
	 */
	public DepositStatusEnum sendDeposit(ApplyPaymentForm applyPaymentForm, PaymentResultInfo resultInfo) {
		try{
			// 查询银行卡
			QueryByPactNoOrder order  = new QueryByPactNoOrder();
			order.setPactNo(applyPaymentForm.getPactNo());
			order.setPurpose(PactPurposeEnum.DEDUCT);
			//统一订单号增加
			order.setGid(applyPaymentForm.getGid());
			order.setPartnerId(applyPaymentForm.getPartnerId());
			order.setMerchOrderNo(applyPaymentForm.getMerchOrderNo());
			PactBankCardInfo pactBankCardInfo = queryBankCardService.queryByPactNo(order);
			
			// 发起代扣
			DeductDepositOrder deductDepositOrder = new DeductDepositOrder();
			//如果充值且商户有优惠
			if(FavourableEnum.TRUE == applyPaymentForm.getFavourable()){
				deductDepositOrder.setAmount(applyPaymentForm.getPayAmount());
			}else{
				deductDepositOrder.setAmount(applyPaymentForm.getPaymentAmount());
			}
			deductDepositOrder.setInlet(applyPaymentForm.getInlet());
			deductDepositOrder.setOutBizNo(applyPaymentForm.getOutBizNo());
			deductDepositOrder.setPartnerId(applyPaymentForm.getPartnerId());
			deductDepositOrder.setUserId(applyPaymentForm.getUserId());
			//统一订单号增加
			deductDepositOrder.setGid(applyPaymentForm.getGid());
			deductDepositOrder.setPartnerId(applyPaymentForm.getPartnerId());
			deductDepositOrder.setMerchOrderNo(applyPaymentForm.getMerchOrderNo());
			DepositStatusEnum status = payengineService.deductDeposit(deductDepositOrder, pactBankCardInfo);
			
			resultInfo.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
			return status;
		} catch (OrderCheckException orderException) {
			logger.error("代扣发生参数异常, 异常信息:{}", orderException.getMessage());
			resultInfo.setFailResultWithMsg(PaymentResultCode.INVAILD_PARAMETER, orderException.getMessage());
		} catch (PaymentException pe) {
			logger.error("代扣发生业务异常, 异常信息:{}", pe.getMessage());
			resultInfo.setFailResultWithMsg(pe.getResultCode(), pe.getMessage());
		} catch (Exception e) {
			logger.error("代扣发生系统异常, 异常信息:{}", e.getMessage(), e);
			resultInfo.setFailResultWithMsg(PaymentResultCode.UNKNOWN_EXCEPTION, "代扣支付时,发生系统异常");
		}
		
		// 发生异常更新代扣表状态
		DeductDepositInfo deductDepositInfo = deductDepositInfoService.findByDepositBizNo(applyPaymentForm.getOutBizNo());
		if(deductDepositInfo != null){
			logger.info("代扣失败,更新代扣表，message：{}", resultInfo.getDescription());
			deductDepositInfo.setDepositStatus(DepositStatusEnum.FAILURE);
			deductDepositInfo.setMemo(resultInfo.getDescription());
			deductDepositInfoService.update(deductDepositInfo);
		}
		
		return DepositStatusEnum.FAILURE;
	}
	
	/**
	 * 发起路由缴费
	 * @param paymentOrder
	 * @param isRubbish
	 * @param resultInfo
	 */
	private void sendRoutePayment(DepositPaymentOrder paymentOrder, boolean isRubbish, PaymentResultInfo resultInfo) {
		// 查询垃圾费
		Map<String, Object> map = Maps.newHashMap();
		map.put("EQ_outBizNo", paymentOrder.getOutBizNo());
		map.put("EQ_userId", paymentOrder.getUserId());
		map.put("EQ_rubbish", isRubbish);
		
		Map<String, Boolean> sortMap = Maps.newHashMap();
		sortMap.put("month", true);
		List<PaymentOrder> paymentOrders = paymentOrderService.query(map, sortMap);
		
		if (CollectionUtils.isEmpty(paymentOrders)) {
			if (isRubbish) {
				logger.info("该缴费无垃圾费");
				return;
			} else {
				logger.info("该缴费无气费");
				return;
			}
		}
		
		// 创建缴费订单
		RoutePaymentOrder routeOrder = buildRoutPaymentOrder(paymentOrders, paymentOrder.getStoreId());
		RoutePaymentResult routePaymentResult = null;
		try {
			// 发起缴费
			routePaymentResult = routePaymentClient.sendPayment(routeOrder);
		} catch (Exception e) {
			logger.error("调用资源路由缴费，发生异常：{}", e.getMessage(), e);
			resultInfo.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
			// 调用缴费服务异常，订单挂起
			handleAllOrder(paymentOrders, PaymentItemStatusEnum.HANGUP, resultInfo.getDescription());
			//根据平台设置需要异步通知的订单
			setNotifyOpenApi(paymentOrders);
			return;
		}
		
		// 7. 处理缴费结果
		handleResult(paymentOrders, routePaymentResult, resultInfo);
	}
	
	/**
	 * 手机缴费绑卡
	 * @param applyPaymentOrder
	 */
	private String reChargeBinding(ApplyPaymentOrder applyPaymentOrder) {
		PaymentBindingOrder bindingOrder = new PaymentBindingOrder();
		bindingOrder.setUserId(applyPaymentOrder.getUserId());
		bindingOrder.setPaymentType(applyPaymentOrder.getPaymentType());
		bindingOrder.setUserCode(applyPaymentOrder.getUserCode());
		bindingOrder.setPlatformType(applyPaymentOrder.getPlatformType());
		bindingOrder.setResourceCode("000000000");
		bindingOrder.setResourceName("移动,联通,电信");
		//统一订单号增加
		bindingOrder.setGid(applyPaymentOrder.getGid());
		bindingOrder.setPartnerId(applyPaymentOrder.getPartnerId());
		bindingOrder.setMerchOrderNo(applyPaymentOrder.getMerchOrderNo());
		logger.info("该充值用户未绑卡, 绑卡入参：{}", bindingOrder);
		PaymentBindingResult result = paymentBindingRemoteService.paymentBinding(bindingOrder);
		logger.info("该充值用户未绑卡, 绑卡出参：{}", result);
		applyPaymentOrder.setResourceCode(bindingOrder.getResourceCode());
		return result.getContractNo();
	}
	
	/**
	 * 缴费结果处理
	 * @param paymentBizOrder
	 * @param routePaymentResult
	 * @param resultInfo
	 */
	private void handleResult(List<PaymentOrder> paymentOrders, RoutePaymentResult routePaymentResult,
								PaymentResultInfo resultInfo) {
		/*			
		 * 在最外层的isSuccess如果是false则交易失败。
		 * 如果是true则判断List<PaymentInfo>为空则本次交易挂起。
		 * 不为空则进入链表判断每个List中的元素如果有status则以status为准。
		 * 若status返回的不在SUSPEND    挂起   FAIL 失败   SUCCESS   成功 这三中这处理为挂起，
		 */
		if (!routePaymentResult.isSuccess()) {
			logger.info("缴费结果为失败，缴费记录记为失败");
			handleAllOrder(paymentOrders, PaymentItemStatusEnum.FAIL, routePaymentResult.getDescription());
			resultInfo.setFailResultWithMsg(PaymentResultCode.EXECUTE_FAIL, routePaymentResult.getDescription());
		} else {
			//根据平台设置需要异步通知的订单
			setNotifyOpenApi(paymentOrders);
			
			//处理订单
			List<PaymentInfo> paymentInfos = routePaymentResult.getPaymentInfos();
			if (CollectionUtils.isEmpty(paymentInfos)) {
				logger.info("缴费结果为成功，但结果列表为空，记为挂起");
				handleAllOrder(paymentOrders, PaymentItemStatusEnum.HANGUP, routePaymentResult.getDescription());
			} else {
				for (PaymentInfo paymentInfo : paymentInfos) {
					PaymentOrder paymentOrder = paymentOrderService.findByPaymentOrderNo(paymentInfo.getOrderSerialNumber());
					if (paymentOrder == null) {
						logger.info("缴费异常,未查询到缴费订单号, paymentOrderNo:{}", paymentInfo.getOrderSerialNumber());
						continue;
					}
					String status = paymentInfo.getStatus();
					PaymentItemStatusEnum paymentStatus = PaymentItemStatusEnum.HANGUP;
                    if (PaymentItemStatusEnum.FAIL.getCode().equalsIgnoreCase(status)) {
						logger.info("缴费明细状态为fail，记为失败");
						paymentStatus = PaymentItemStatusEnum.FAIL;
					} else if (PaymentItemStatusEnum.SUCCESS.getCode().equalsIgnoreCase(status)) {
						logger.info("缴费明细状态为success，记为成功");
						paymentStatus = PaymentItemStatusEnum.SUCCESS;
					} else {
						logger.info("缴费明细状态其他情况：{}，记为挂起", status);
						paymentStatus = PaymentItemStatusEnum.HANGUP;
					}
                    //更新订单
					updatePaymentOrderStatus(paymentInfo, paymentStatus, paymentOrder);
					//缴费成功，转账
					tradeRemoteService.transferTrade(paymentOrder);
					//创建缴费返回结果
					PayOrderInfo payOrderInfo = bulidPayOrderInfo(paymentOrder);
					resultInfo.getPayOrderInfos().add(payOrderInfo);
				}
			}
			resultInfo.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
		}
	}
	
	/** 
	 * 设置异步通知标志
	 * 
	 * @param paymentOrders
	 */
	private void setNotifyOpenApi(List<PaymentOrder> paymentOrders){
		for(PaymentOrder paymentOrder : paymentOrders){
			PlatformType platformType = platformTypeService.findByPlatformType(paymentOrder.getPayFrom());
			//根据平台设置是否需要异步通知openapi
			if (platformType.isNotifyOpenApi()) {
				paymentOrder.setNotifyOpenApi(true);
			}
			paymentOrderService.update(paymentOrder);
		}
	}
	
	/**
	 * 缴费明确失败，或者成功没有返回明细
	 * @param paymentBizOrder
	 * @param paymentStatus
	 */
	private void handleAllOrder(List<PaymentOrder> paymentOrders, PaymentItemStatusEnum paymentStatus, String memo) {
		for (PaymentOrder paymentOrder : paymentOrders) {
			paymentOrder.setPaymentStatus(paymentStatus);
			paymentOrder.setPaymentExtStatus(paymentStatus);
			paymentOrder.setMemo(memo);
			if (paymentStatus == PaymentItemStatusEnum.FAIL) {
				logger.info("缴费失败, 解冻缴费资金");
				//如果是充值且商户有优惠
				tradeRemoteService.unfreezeAccount(paymentOrder);
			}
			paymentOrderService.save(paymentOrder);
		}
	}
	
	/**
	 * 根据结果更新订单状态
	 * @param paymentInfo
	 * @param paymentStatus
	 * @param paymentDes
	 */
	private void updatePaymentOrderStatus(PaymentInfo paymentInfo, PaymentItemStatusEnum paymentStatus, PaymentOrder paymentOrder) {
		//解冻失败的订单
		if (paymentStatus == PaymentItemStatusEnum.FAIL) {
			logger.info("缴费失败, 解冻缴费资金");
			tradeRemoteService.unfreezeAccount(paymentOrder);
		}
		
		//有明确结果的订单不需要异步通知
		if (paymentStatus == PaymentItemStatusEnum.SUCCESS || paymentStatus == PaymentItemStatusEnum.FAIL) {
			if (paymentOrder.isNotifyOpenApi()) {
				paymentOrder.setNotifyOpenApi(false);
			}
		}
		
		PaymentItemInfo itemInfo = buildPaymentItemInfo(paymentInfo, paymentOrder);
		if(paymentOrder.getPaymentType() != PaymentTypeEnum.MOBILE){
			paymentOrder.setPaymentAmount(paymentInfo.getMoney());
		}
		paymentOrder.setUserName(paymentInfo.getUserName());
		paymentOrder.setMonth(paymentInfo.getMonth());
		paymentOrder.setPaymentStatus(paymentStatus);
		paymentOrder.setPaymentExtStatus(paymentStatus);
		paymentOrder.setEndTime(new Date());
		if (PaymentItemStatusEnum.SUCCESS != paymentStatus) {
			paymentOrder.setMemo(paymentInfo.getErrorDescription());
			itemInfo.setErrorCode(paymentInfo.getError());
			itemInfo.setErrorMessage(paymentInfo.getErrorDescription());
		}
		logger.info("更新缴费订单, paymentOrder:{}", paymentOrder);
		paymentOrderService.update(paymentOrder);
		logger.info("新增缴费详情, itemInfo:{}", itemInfo);
		paymentItemInfoService.save(itemInfo);
		
		List<BillItemInfo> billItemInfos = buildBillItemInfo(paymentInfo, paymentOrder.getPaymentOrderNo());
		logger.info("新增缴费账单明细详情, billItemInfos:{}", billItemInfos);
		billItemInfoService.saves(billItemInfos);
	}
	
	/**
	 * 构建路由水电气缴费对象
	 * 
	 * @param paymentBizOrder
	 * @param paymentQueryInfo
	 * @return
	 */
	private void buildPaymentOrder(PaymentBizOrder paymentBizOrder, PaymentQueryInfo paymentQueryInfo) {
		List<PayItemInfo> payItemInfos = paymentQueryInfo.getItems();
		List<PaymentItem> paymentItems = paymentBizOrder.getApplyPaymentOrder().getPaymentItems();
		
		if (payItemInfos.size() != paymentItems.size()) {
			logger.info("缴费列表不等于欠费列表, 缴费列表：{}, 欠费列表：{}", paymentItems, payItemInfos);
			throw new PaymentException(PaymentResultCode.NOT_EQUAL_PAYABLES, "缴费列表不等于欠费列表");
		}
		
		//校验缴费金额、欠费总金额
		Money totalMoney = paymentBizOrder.getApplyPaymentOrder().getPaymentAmount();
		if(!paymentQueryInfo.getTotalPayables().equals(totalMoney)) {
			logger.info("缴费金额不等于欠费总额, 缴费金额：{}, 欠费总额：{}", totalMoney, paymentQueryInfo.getTotalPayables());
			throw new PaymentException(PaymentResultCode.NOT_EQUAL_PAYABLES);
		}
		
		for(PaymentItem paymentItem : paymentItems){
			String channelCode = "";
			if(paymentItem.getPaymentType() == PaymentTypeEnum.RUBBISH){
				channelCode = paymentQueryInfo.getChannelRubbishCode();
			}else{
				channelCode = paymentQueryInfo.getChannelCode();
			}
			// 缴费订单
			buildPaymentOrder(paymentBizOrder, paymentItem, channelCode, paymentQueryInfo.getYjfAccount(), paymentItem.getPaymentType());
		}
	}
	
	/**
	 * 构建路由水电气缴费订单（中信）
	 * 
	 * @param paymentBizOrder
	 * @param paymentQueryInfo
	 */
	private void buildZhongxinPaymentOrder(PaymentBizOrder paymentBizOrder, PaymentQueryInfo paymentQueryInfo) {
		List<PaymentItem> paymentItems = paymentBizOrder.getApplyPaymentOrder().getPaymentItems();
		for(PaymentItem paymentItem : paymentItems){
			String channelCode = "";
			if(paymentItem.getPaymentType() == PaymentTypeEnum.RUBBISH){
				channelCode = paymentQueryInfo.getChannelRubbishCode();
			}else{
				channelCode = paymentQueryInfo.getChannelCode();
			}
			// 缴费订单
			buildPaymentOrder(paymentBizOrder, paymentItem, channelCode, paymentQueryInfo.getYjfAccount(), paymentItem.getPaymentType());
		}
	}
	
	private void buildPaymentOrderByMoblie(PaymentBizOrder paymentBizOrder) {
		PaymentRouteQueryOrder paymentRouteQueryOrder = new PaymentRouteQueryOrder();
		paymentRouteQueryOrder.setPaymentType(PaymentTypeEnum.MOBILE);
		paymentRouteQueryOrder.setResourceCode(paymentBizOrder.getApplyPaymentOrder().getResourceCode());
		paymentRouteQueryOrder.setUserCode(paymentBizOrder.getApplyPaymentOrder().getUserCode());
		paymentRouteQueryOrder.setSystemId(paymentBizOrder.getApplyPaymentOrder().getPlatformType());
		paymentRouteQueryOrder.setStoreId(paymentBizOrder.getApplyPaymentOrder().getStoreId());
		//统一订单号新增
		paymentRouteQueryOrder.setGid(paymentBizOrder.getApplyPaymentOrder().getGid());
		paymentRouteQueryOrder.setPartnerId(paymentBizOrder.getApplyPaymentOrder().getPartnerId());
		paymentRouteQueryOrder.setMerchOrderNo(paymentBizOrder.getApplyPaymentOrder().getMerchOrderNo());
		// 查询欠费记录
		PaymentChannelInfo channelInfo = queryRemoteService.route(paymentRouteQueryOrder);
		String channelCode = channelInfo.getChannelCode();
		String yjfAccount = channelInfo.getUserId();
		
		// 缴费业务号、缴费金额
		PaymentItem paymentItem = paymentBizOrder.getApplyPaymentOrder().getPaymentItems().get(0);
		
		// 缴费订单
		buildPaymentOrder(paymentBizOrder, paymentItem, channelCode, yjfAccount, PaymentTypeEnum.MOBILE);
	}
	
	/**
	 * 根据缴费订单，构建路由缴费订单
	 * @param paymentOrderList
	 * @return
	 */
	private RoutePaymentOrder buildRoutPaymentOrder(List<PaymentOrder> paymentOrders, String storeId) {
		PaymentOrder paymentOrderDemo = paymentOrders.get(0);
		RoutePaymentOrder routePaymentOrder = new RoutePaymentOrder();
		routePaymentOrder.setUserCode(paymentOrderDemo.getUserCode());
		routePaymentOrder.setChannelCode(paymentOrderDemo.getChannelCode());
		routePaymentOrder.setSystemId(paymentOrderDemo.getPayFrom());
		if (StringUtils.isNotBlank(storeId)) {
			routePaymentOrder.setAppId(storeId);
		} else {
			routePaymentOrder.setAppId(paymentOrderDemo.getPayFrom());
		}
		routePaymentOrder.setBusiType(PayTypeToBusiTypeUtils.payTypeToBusiType(paymentOrderDemo.getPaymentType()));
		//统一订单号修改
		routePaymentOrder.setGid(paymentOrderDemo.getGid());
		routePaymentOrder.setPartnerId(paymentOrderDemo.getPartnerId());
		routePaymentOrder.setMerchOrderNo(paymentOrderDemo.getMerchOrderNo());
		List<Money> payables = new ArrayList<>();
		List<String> orderSerialNumbers = new ArrayList<>();
		for (PaymentOrder paymentOrder : paymentOrders) {
			orderSerialNumbers.add(paymentOrder.getPaymentOrderNo());
			payables.add(paymentOrder.getPayable());
		}
		routePaymentOrder.setMoneys(payables);
		routePaymentOrder.setOrderSerialNumbers(orderSerialNumbers);
		return routePaymentOrder;
	}
	
	/**
	 * 构建缴费业务订单
	 * 
	 * @param paymentBizOrder
	 * @param item
	 * @param channelCode
	 * @param yjfAccount
	 * @param bizNo
	 * @param paymentType
	 */
	private void buildPaymentOrder(PaymentBizOrder paymentBizOrder, PaymentItem paymentItem, String channelCode,
									String yjfAccount, PaymentTypeEnum paymentType) {
		PaymentOrder paymentOrder = new PaymentOrder();

		paymentOrder.setUserCode(paymentBizOrder.getApplyPaymentOrder().getUserCode());
		paymentOrder.setUserId(paymentBizOrder.getApplyPaymentOrder().getUserId());
		paymentOrder.setResourceCode(paymentBizOrder.getApplyPaymentOrder().getResourceCode());
		ResourceInstInfo resourceInstInfo = queryRemoteService.queryResource(paymentBizOrder.getApplyPaymentOrder().getResourceCode());
		paymentOrder.setResourceName(resourceInstInfo.getInstName());
		paymentOrder.setPayFrom(paymentBizOrder.getApplyPaymentOrder().getPlatformType());
		
		paymentOrder.setChannelCode(channelCode);
		paymentOrder.setPaymentType(paymentType);
		paymentOrder.setOutBizNo(paymentBizOrder.getApplyPaymentOrder().getOutBizNo());
		paymentOrder.setPaymentOrderNo(paymentItem.getPaymentOrderNo());
		paymentOrder.setPaymentStatus(PaymentItemStatusEnum.INIT);
		paymentOrder.setPaymentExtStatus(PaymentItemStatusEnum.HANGUP);
		paymentOrder.setTradeStatus(TransferTradeStatusEnum.INIT);
		paymentOrder.setInlet(paymentBizOrder.getApplyPaymentOrder().getInlet());
		paymentOrder.setStartTime(new Date());
		paymentOrder.setPayAccount(yjfAccount);
		paymentOrder.setPayWay(paymentBizOrder.getApplyPaymentOrder().getPayWay());
		paymentOrder.setFavourable(paymentBizOrder.getApplyPaymentOrder().getFavourable());
		//统一订单号增加
		paymentOrder.setGid(paymentBizOrder.getApplyPaymentOrder().getGid());
		paymentOrder.setPartnerId(paymentBizOrder.getApplyPaymentOrder().getPartnerId());
		paymentOrder.setMerchOrderNo(paymentBizOrder.getApplyPaymentOrder().getMerchOrderNo());
		if (PaymentTypeEnum.RUBBISH == paymentType) {
			paymentOrder.setRubbish(true);
		}
		if (PaymentTypeEnum.MOBILE == paymentType) {
			paymentOrder.setMonth(Dates.format(new Date(), Dates.DATETIME_YMD));
			if(FavourableEnum.TRUE == paymentBizOrder.getApplyPaymentOrder().getFavourable()){
				paymentOrder.setPayable(paymentBizOrder.getApplyPaymentOrder().getPaymentAmount());
				paymentOrder.setPaymentAmount(paymentBizOrder.getApplyPaymentOrder().getPayAmount());
			}else{
				paymentOrder.setPayable(paymentBizOrder.getApplyPaymentOrder().getPaymentAmount());
				paymentOrder.setPaymentAmount(paymentBizOrder.getApplyPaymentOrder().getPaymentAmount());
			}
		} else {
			paymentOrder.setPayable(paymentItem.getAmount());
			paymentOrder.setPaymentAmount(paymentItem.getAmount());
		}
		paymentBizOrder.getPaymentOrders().add(paymentOrder);
	}
	
	/**
	 * 保存缴费订单信息
	 * @param paymentOrderList
	 */
	private void paymentOrderDB(List<PaymentOrder> paymentOrders) {
		if (CollectionUtils.isEmpty(paymentOrders)) {
			logger.info("无缴费订单生成, paymentOrders：{}", paymentOrders);
			throw new PaymentException(PaymentResultCode.NO_PAYMENT_ORDER);
		}
		for (PaymentOrder paymentOrder : paymentOrders) {
			logger.info("保存缴费订单，订单号：paymentOrder{}", paymentOrder.getPaymentOrderNo());
			paymentOrderService.save(paymentOrder);
		}
	}
	
	/**
	 * 保存返销记录
	 * 
	 * @param undopayment
	 * @param paymentOrder
	 * @param undoApproach
	 * @param orderSerialNumber
	 */
	private void saveUndoPayment(UndoPayment undopayment, PaymentOrder paymentOrder, UndoPaymentOrder undoPaymentOrder) {
		undopayment.setUndoPaymentNo(undoPaymentOrder.getUndoPaymentNo());
		undopayment.setPaymentOrderNo(paymentOrder.getPaymentOrderNo());
		undopayment.setUserId(paymentOrder.getUserId());
		undopayment.setPaymentType(paymentOrder.getPaymentType());
		undopayment.setPayAccount(paymentOrder.getPayAccount());
		undopayment.setTradeStatus(TransferTradeStatusEnum.INIT);
		undopayment.setUndoApproach(undoPaymentOrder.getUndoApproach());
		undopayment.setUndoAmount(paymentOrder.getPaymentAmount());
		undopayment.setUndoStatus(UndoPaymentStatusEnum.INIT);
		undopayment.setPayFrom(undoPaymentOrder.getPlatformType());
		undopayment.setInlet(undoPaymentOrder.getInlet());
		undopayment.setGid(undoPaymentOrder.getGid());
		undopayment.setPartnerId(undoPaymentOrder.getPartnerId());
		undopayment.setMerchOrderNo(undoPaymentOrder.getMerchOrderNo());
		logger.info("保存返销订单，undopayment：{}", undopayment);
		undoPaymentService.save(undopayment);
	}
	
	/**
	 * 更新返销
	 * @param result
	 * @param undopayment
	 * @param resultInfo
	 */
	public void updateUndoPayment(RouteUndoPaymentResult result, UndoPayment undoPayment,
									PaymentUndoOrderResult resultInfo, PaymentOrder paymentOrder) {
		if (!result.isSuccess()) {
			undoPayment.setUndoStatus(UndoPaymentStatusEnum.FAIL);
			undoPayment.setMemo(result.getDescription());
			resultInfo.setUndoOrderStatus(UndoPaymentStatusEnum.FAIL);
			logger.info("返销失败");
			resultInfo.setFailResultWithMsg(PaymentResultCode.PAYMENT_PAYBACK_FAIL, result.getDescription());
		} else {
			if ("success".equalsIgnoreCase(result.getRouteStatus())) {
				logger.info("返销状态为success，记为成功");
				undoPayment.setUndoStatus(UndoPaymentStatusEnum.SUCCESS);
				undoPayment.setRouteSerialNumber(result.getRouteSerialNumber());
				resultInfo.setUndoOrderStatus(UndoPaymentStatusEnum.SUCCESS);
				//更新订单状态
				paymentOrder.setPaymentStatus(PaymentItemStatusEnum.PAY_BACK);
				paymentOrder.setPaymentExtStatus(PaymentItemStatusEnum.PAY_BACK);
				logger.info("返销成功，更新订单表，paymentOrder：{}", paymentOrder);
				paymentOrderService.save(paymentOrder);
				//返销成功，转账
				tradeRemoteService.transferTrade(undoPayment);
			} else if ("fail".equalsIgnoreCase(result.getRouteStatus())) {
				logger.info("返销状态为fail，记为失败");
				undoPayment.setUndoStatus(UndoPaymentStatusEnum.FAIL);
				undoPayment.setRouteSerialNumber(result.getRouteSerialNumber());
				undoPayment.setMemo(result.getDescription());
				resultInfo.setUndoOrderStatus(UndoPaymentStatusEnum.FAIL);
			} else {
				logger.info("返销状态其他情况：{}，记为挂起", result.getRouteStatus());
				undoPayment.setUndoStatus(UndoPaymentStatusEnum.HANGUP);
				undoPayment.setRouteSerialNumber(result.getRouteSerialNumber());
				undoPayment.setMemo(result.getDescription());
				resultInfo.setUndoOrderStatus(UndoPaymentStatusEnum.HANGUP);
				//根据平台设置是否需要异步通知openapi
				setNotifyOpenApi(undoPayment);
				//更新订单状态
				paymentOrder.setPaymentStatus(PaymentItemStatusEnum.CANCEL);
				paymentOrder.setPaymentExtStatus(PaymentItemStatusEnum.CANCEL);
				logger.info("返销挂起，更新订单表，paymentOrder：{}", paymentOrder);
				paymentOrderService.save(paymentOrder);
			}
			resultInfo.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
		}
		undoPaymentService.update(undoPayment);
	}
	
	/**
	 * 设置返销异步通知标志
	 * 
	 * @param undoPayment
	 */
	private void setNotifyOpenApi(UndoPayment undoPayment){
		PlatformType platformType = platformTypeService.findByPlatformType(undoPayment.getPayFrom());
		if (platformType.isNotifyOpenApi()) {
			undoPayment.setNotifyOpenApi(true);
		}
	}
	
	/**
	 * 缴费结果批量更新数据库
	 * @param outBizNo
	 * @param memo
	 */
	public void updatePaymentOrders(String outBizNo, String memo){
		List<PaymentOrder> paymentOrders = paymentOrderService.findByOutBizNo(outBizNo);
		for (PaymentOrder paymentOrder : paymentOrders) {
			paymentOrder.setPaymentStatus(PaymentItemStatusEnum.FAIL);
			paymentOrder.setPaymentExtStatus(PaymentItemStatusEnum.FAIL);
			paymentOrder.setMemo(memo);
			logger.info("更新缴费订单, paymentOrder:{}", paymentOrder);
			paymentOrder.setEndTime(new Date());
			paymentOrderService.update(paymentOrder);
		}
	}
	
	/**
	 * 构造缴费order
	 * @param applyPaymentOrder
	 * @return
	 */
	public DepositPaymentOrder buildDepositOrder(ApplyPaymentForm applyPaymentOrder){
		DepositPaymentOrder depositOrder = new DepositPaymentOrder();
		depositOrder.setOutBizNo(applyPaymentOrder.getOutBizNo());
		depositOrder.setUserId(applyPaymentOrder.getUserId());
		depositOrder.setPaymentAmount(applyPaymentOrder.getPaymentAmount());
		depositOrder.setPartnerId(applyPaymentOrder.getPartnerId());
		depositOrder.setPayAmount(applyPaymentOrder.getPayAmount());
		depositOrder.setFavourable(applyPaymentOrder.getFavourable());
		depositOrder.setPaymentType(applyPaymentOrder.getPaymentType());
		depositOrder.setStoreId(applyPaymentOrder.getStoreId());
		//统一订单号
		depositOrder.setGid(applyPaymentOrder.getGid());
		depositOrder.setPartnerId(applyPaymentOrder.getPartnerId());
		depositOrder.setMerchOrderNo(applyPaymentOrder.getMerchOrderNo());
		return depositOrder;
	}
	
	/**
	 * 校验缴费订单
	 * 
	 * @param paymentBizOrder
	 * @param paymentQueryInfo
	 */
	public void checkPaymentOrders(PaymentBizOrder paymentBizOrder, PaymentQueryInfo paymentQueryInfo){
		if (CollectionUtils.isEmpty(paymentQueryInfo.getItems())) {
			logger.info("查询缴费结果项目为空, 返回信息：{}", paymentQueryInfo);
			throw new PaymentException(PaymentResultCode.PAYMENT_BILL_FAIL, "查询缴费结果项目为空");
		}
		
		if (StringUtils.isBlank(paymentQueryInfo.getYjfAccount())) {
			logger.info("缴费转入账户不存在");
			throw new PaymentException(PaymentResultCode.PAY_ACCOUNT_NOT_EXIST);
		}
		
		List<PayItemInfo> payItemInfos = paymentQueryInfo.getItems();
		List<PaymentItem> paymentItems = paymentBizOrder.getApplyPaymentOrder().getPaymentItems();
		
		if (payItemInfos.size() != paymentItems.size()) {
			logger.info("缴费列表不等于欠费列表, 缴费列表：{}, 欠费列表：{}", paymentItems, payItemInfos);
			throw new PaymentException(PaymentResultCode.NOT_EQUAL_PAYABLES, "缴费列表不等于欠费列表");
		}
		
		//校验缴费金额、欠费总金额
		Money totalMoney = paymentBizOrder.getApplyPaymentOrder().getPaymentAmount();
		if(!paymentQueryInfo.getTotalPayables().equals(totalMoney)) {
			logger.info("缴费金额不等于欠费总额, 缴费金额：{}, 欠费总额：{}", totalMoney, paymentQueryInfo.getTotalPayables());
			throw new PaymentException(PaymentResultCode.NOT_EQUAL_PAYABLES);
		}
		
	}
	
	/**
	 * 外部订单重复提交check
	 * @param outOrderNo
	 */
	protected boolean checkOutOrderNo(ApplyPaymentForm applyPaymentForm, PaymentResultInfo resultInfo) {
		List<PaymentOrder> paymentOrders = paymentOrderService.findByOutBizNo(applyPaymentForm.getOutBizNo());
		if (CollectionUtils.isNotEmpty(paymentOrders)) {
			logger.info("重复提交充值缴费订单, 订单号：{}", applyPaymentForm.getOutBizNo());
			for(PaymentOrder paymentOrder : paymentOrders){
				PayOrderInfo payOrderInfo = bulidPayOrderInfo(paymentOrder);
				resultInfo.getPayOrderInfos().add(payOrderInfo);
			}
			resultInfo.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
			return false;
		}
		for(PaymentItem PaymentItem : applyPaymentForm.getPaymentItems()){
			PaymentOrder paymentOrder = paymentOrderService.findByPaymentOrderNo(PaymentItem.getPaymentOrderNo());
			if(paymentOrder != null){
				logger.info("重复提交充值缴费子订单, 订单号：{}", PaymentItem.getPaymentOrderNo());
				throw new PaymentException(PaymentResultCode.EXECUTE_FAIL, "重复提交缴费子订单");
			}
		}
		return true;
	}

	/**
	 * 创建缴费结果
	 * 
	 * @param paymentOrderNo
	 * @return
	 * @see com.yiji.ypayment.biz.remote.PaymentRemoteService#bulidPayOrderInfo(java.lang.String)
	 */
	@Override
	public PayOrderInfo bulidPayOrderInfo(PaymentOrder paymentOrder) {
		PaymentItemInfo paymentItemInfo = paymentItemInfoService.findByPaymentOrderNo(paymentOrder.getPaymentOrderNo());
		List<BillItemInfo> billItemInfos = billItemInfoService.findByPaymentOrderNo(paymentOrder.getPaymentOrderNo());
		
		PayOrderInfo payOrderInfo = new PayOrderInfo();
		payOrderInfo.setPaymentOrderNo(paymentOrder.getPaymentOrderNo());
		payOrderInfo.setOutBizNo(paymentOrder.getOutBizNo());
		payOrderInfo.setPaymentStatus(paymentOrder.getPaymentStatus());
		payOrderInfo.setUserCode(paymentOrder.getUserCode());
		payOrderInfo.setUserName(paymentOrder.getUserName());
		payOrderInfo.setResourceCode(paymentOrder.getResourceCode());
		payOrderInfo.setResourceName(paymentOrder.getResourceName());
		payOrderInfo.setPaymentType(paymentOrder.getPaymentType());
		payOrderInfo.setMemo(paymentOrder.getMemo());
		
		if(paymentItemInfo != null){
			payOrderInfo.setAddress(paymentItemInfo.getAddress());
			payOrderInfo.setPaymentAmount(paymentItemInfo.getPaymentAmount());
			payOrderInfo.setCount(paymentItemInfo.getQuantity());
			payOrderInfo.setStartCount(paymentItemInfo.getStartCount());
			payOrderInfo.setEndCount(paymentItemInfo.getEndCount());
			if(CollectionUtils.isNotEmpty(billItemInfos)){
				for (BillItemInfo billItemInfo : billItemInfos) {
					ItemInfo itemInfo = new ItemInfo();
					itemInfo.setName(billItemInfo.getName());
					itemInfo.setCount(billItemInfo.getCount());
					itemInfo.setMoney(billItemInfo.getMoney());
					itemInfo.setCharge(billItemInfo.getCharge());
					itemInfo.setPrice(billItemInfo.getPrice());
					payOrderInfo.getItemInfos().add(itemInfo);
				}
			}
			payOrderInfo.setCharge(paymentItemInfo.getChargeAmount());
			payOrderInfo.setMonth(paymentItemInfo.getMonth());
			payOrderInfo.setStartTime(paymentItemInfo.getStartTime());
			payOrderInfo.setEndTime(paymentItemInfo.getEndTime());
			payOrderInfo.setErrorMessage(paymentItemInfo.getErrorMessage());
		}
		return payOrderInfo;
	}
	
}

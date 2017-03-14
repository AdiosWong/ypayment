/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午11:02:48 创建
 */
package com.yiji.ypayment.biz.remote.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiji.ypayment.biz.remote.PaymentRemoteService;
import com.yiji.ypayment.biz.remote.base.RemoteServiceBase;
import com.yiji.ypayment.biz.remote.info.PaymentResultInfo;
import com.yiji.ypayment.biz.remote.order.DepositPaymentOrder;
import com.yiji.ypayment.dal.entity.business.DeductDepositInfo;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.enums.DepositStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yjf.common.lang.result.Status;
import com.yjf.payengine.common.enums.PayengineResultEnum;
import com.yjf.payengine.receipt.service.Receipt;
import com.yjf.payengine.receipt.service.ReceiptReturnMessageResult;
import com.yjf.payengine.receipt.service.ReceivePayAckWithResultService;

/**
 * 
 * 支付引擎代扣异步通知
 *
 * @author xinqing@yiji.com
 *
 */
@Service(version = "1.5", group = "ypayment")
public class DeductDepositNotifyServiceImpl extends RemoteServiceBase implements ReceivePayAckWithResultService {
	
	@Autowired
	PaymentRemoteService paymentRemoteService;
	
	/**
	 * @param receipt
	 * @return
	 * @see com.yjf.payengine.receipt.service.ReceivePayAckWithResultService#signReceipt(com.yjf.payengine.receipt.service.Receipt)
	 */
	@Override
	public ReceiptReturnMessageResult signReceipt(Receipt receipt) {
		logger.info("代扣回推通知：{}", receipt);
		ReceiptReturnMessageResult result = new ReceiptReturnMessageResult();
		try {
			DeductDepositInfo deductDepositInfo = deductDepositInfoService.findByDepositNo(receipt.getPayNo());
			//第一次才异步通知才更新代扣信息和发起缴费，防止重复异步通知
			if (deductDepositInfo != null && DepositStatusEnum.PROCESSING == deductDepositInfo.getDepositStatus()) {
				if (PayengineResultEnum.DEPOSIT_SUCCESS.getCode().equalsIgnoreCase(receipt.getResultCode().getCode())) {
					logger.info("代扣成功，message：{}", receipt.getDescription());
					deductDepositInfo.setDepositStatus(DepositStatusEnum.SUCCESS);
					deductDepositInfo.setActAmount(receipt.getAmount());
					deductDepositInfoService.update(deductDepositInfo);
					//代扣成功,发起缴费
					List<PaymentOrder> paymentOrders = paymentOrderService.findByOutBizNo(deductDepositInfo
						.getDepositBizNo());
					PaymentOrder paymentOrder = paymentOrders.get(0);
					DepositPaymentOrder depositOrder = new DepositPaymentOrder();
					depositOrder.setOutBizNo(deductDepositInfo.getDepositBizNo());
					depositOrder.setUserId(deductDepositInfo.getUserId());
					depositOrder.setPaymentAmount(paymentOrder.getPayable());
					depositOrder.setPartnerId(paymentOrder.getPartnerId());
					depositOrder.setPayAmount(paymentOrder.getPaymentAmount());
					depositOrder.setFavourable(paymentOrder.getFavourable());
					depositOrder.setPaymentType(paymentOrder.getPaymentType());
					//统一订单号
					depositOrder.setGid(paymentOrder.getGid());
					depositOrder.setPartnerId(paymentOrder.getPartnerId());
					depositOrder.setMerchOrderNo(paymentOrder.getMerchOrderNo());
					PaymentResultInfo resultInfo = new PaymentResultInfo();
					paymentRemoteService.depositPayment(depositOrder, resultInfo);
				} else if (PayengineResultEnum.DEPOSIT_FAILURE.getCode().equalsIgnoreCase(
					receipt.getResultCode().getCode())) {
					deductDepositInfo = deductDepositInfoService.findByDepositNo(receipt.getPayNo());
					logger.info("代扣失败，message：{}", receipt.getDescription());
					deductDepositInfo.setDepositStatus(DepositStatusEnum.FAILURE);
					deductDepositInfo.setActAmount(receipt.getAmount());
					deductDepositInfoService.update(deductDepositInfo);
					//代扣失败,更新订单状态
					List<PaymentOrder> paymentOrders = paymentOrderService.findByOutBizNo(deductDepositInfo
						.getDepositBizNo());
					for (PaymentOrder paymentOrder : paymentOrders) {
						paymentOrder.setPaymentStatus(PaymentItemStatusEnum.FAIL);
						paymentOrder.setPaymentExtStatus(PaymentItemStatusEnum.FAIL);
						paymentOrder.setMemo(receipt.getDescription());
						logger.info("更新缴费订单, paymentOrder:{}", paymentOrder);
						paymentOrder.setEndTime(new Date());
						paymentOrderService.update(paymentOrder);
					}
				}
			}
			result.setStatus(Status.SUCCESS);
			result.setCode(PaymentResultCode.EXECUTE_SUCCESS.getCode());
			result.setDescription(PaymentResultCode.EXECUTE_SUCCESS.getMessage());
			result.setSign(true);
		} catch (Exception e) {
			logger.error("支付引擎回推代扣状态异常", e.getMessage(), e);
			result.setStatus(Status.FAIL);
			result.setCode(PaymentResultCode.EXECUTE_FAIL.getCode());
			result.setDescription(e.getMessage());
			if (StringUtils.isBlank(e.getMessage())) {
				result.setDescription("支付引擎回推代扣状态发生异常");
			}
		}
		return result;
	}
	
}

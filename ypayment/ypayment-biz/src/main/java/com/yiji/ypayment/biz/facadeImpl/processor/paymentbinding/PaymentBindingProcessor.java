/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:44:15 创建
 */
package com.yiji.ypayment.biz.facadeImpl.processor.paymentbinding;

import org.springframework.stereotype.Component;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorBase;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.order.payment.PaymentBindingOrder;
import com.yiji.ypayment.facade.order.query.PaymentQueryOrder;
import com.yiji.ypayment.facade.result.payment.PaymentBindingResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Component("paymentBindingProcessor")
public class PaymentBindingProcessor extends PaymentProcessorBase
																	implements
																	PaymentProcessorTemplate<PaymentBindingOrder, PaymentBindingResult> {
	/**
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#getAction()
	 */
	@Override
	public PaymentInstructionAction getAction() {
		return PaymentInstructionAction.CREAT_BINDING;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#initResult(com.yjf.common.service.OrderBase)
	 */
	@Override
	public PaymentBindingResult initResult(PaymentBindingOrder order) {
		PaymentBindingResult result = new PaymentBindingResult();
		return result;
	}
	
	/**
	 * @param order
	 * @param result
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#execute(com.yjf.common.service.OrderBase,
	 * com.yjf.common.lang.result.StandardResultInfo)
	 */
	@Override
	public void execute(PaymentBindingOrder order, PaymentBindingResult result) {
		logger.info("用户绑定缴费卡, 入参：{}", order); 
		PaymentQueryOrder paymentQueryOrder = new PaymentQueryOrder();
		paymentQueryOrder.setUserId(order.getUserId());
		paymentQueryOrder.setPlatformType(order.getPlatformType());
		paymentQueryOrder.setUserCode(order.getUserCode());
		paymentQueryOrder.setPaymentType(order.getPaymentType());
		paymentQueryOrder.setResourceCode(order.getResourceCode());
		//统一订单号修改
		paymentQueryOrder.setGid(order.getGid());
		paymentQueryOrder.setPartnerId(order.getPartnerId());
		paymentQueryOrder.setMerchOrderNo(order.getMerchOrderNo());
		
		boolean isExit = paymentQueryRemoteService.hasUser(paymentQueryOrder);
		if (!isExit) {
			throw new PaymentException(PaymentResultCode.USER_NOT_EXIST, "缴费用户不存在");
		}
		PaymentBindingResult paymentBindingResult = paymentBindingRemoteService.paymentBinding(order);
		result.setUserId(paymentBindingResult.getUserId());
		result.setContractNo(paymentBindingResult.getContractNo());
		result.setUserCode(paymentBindingResult.getUserCode());
		result.setUserName(paymentBindingResult.getUserName());
		result.setBindingStatus(paymentBindingResult.getBindingStatus());
		result.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
		logger.info("用户绑定缴费卡, 出参：{}", result); 
	}
	
}

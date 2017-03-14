/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月27日 上午10:11:01 创建
 */

package com.yiji.ypayment.biz.facadeImpl.processor.applyPayment;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorBase;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate;
import com.yiji.ypayment.biz.remote.info.PaymentResultInfo;
import com.yiji.ypayment.biz.remote.order.ApplyPaymentForm;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.order.payment.ApplyPaymentOrder;
import com.yiji.ypayment.facade.result.payment.ApplyPaymentResult;
import com.yjf.common.lang.result.Status;

/**
 *
 *
 * @author faZheng
 *
 */
@Component("applyPaymentProcessor")
public class ApplyPaymentProcessor extends PaymentProcessorBase
																implements
																PaymentProcessorTemplate<ApplyPaymentOrder, ApplyPaymentResult> {
	
	@Override
	public PaymentInstructionAction getAction() {
		return PaymentInstructionAction.PAYMENT_RECHARGE;
	}
	
	@Override
	public ApplyPaymentResult initResult(ApplyPaymentOrder order) {
		return new ApplyPaymentResult();
	}
	
	@Override
	public void execute(ApplyPaymentOrder order, ApplyPaymentResult result) {
		ApplyPaymentForm paymentForm = new ApplyPaymentForm();
		BeanUtils.copyProperties(order, paymentForm);
		PaymentResultInfo paymentResult = paymentRemoteService.payment(paymentForm);
		if (paymentResult.getStatus() == Status.SUCCESS) {
			result.setResultCode(PaymentResultCode.EXECUTE_SUCCESS);
			result.setStatus(Status.SUCCESS);
			result.setPayOrderInfos(paymentResult.getPayOrderInfos());
		} else {
			result.setResultCode(paymentResult.getResultCode());
			result.setCode(paymentResult.getCode());
			result.setDescription(paymentResult.getDescription());
			result.setStatus(Status.FAIL);
		}
	}
}

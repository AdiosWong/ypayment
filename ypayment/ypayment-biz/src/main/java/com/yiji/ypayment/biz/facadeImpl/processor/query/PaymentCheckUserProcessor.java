/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:46:53 创建
 */
package com.yiji.ypayment.biz.facadeImpl.processor.query;

import org.springframework.stereotype.Component;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorBase;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.order.query.PaymentQueryOrder;
import com.yiji.ypayment.facade.result.query.PaymentCheckUserResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Component("paymentCheckUserProcessor")
public class PaymentCheckUserProcessor extends PaymentProcessorBase
																	implements
																	PaymentProcessorTemplate<PaymentQueryOrder, PaymentCheckUserResult> {
	/**
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#getAction()
	 */
	@Override
	public PaymentInstructionAction getAction() {
		return PaymentInstructionAction.CHECK_PAYMENT_USER;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#initResult(com.yjf.common.service.OrderBase)
	 */
	@Override
	public PaymentCheckUserResult initResult(PaymentQueryOrder order) {
		PaymentCheckUserResult result = new PaymentCheckUserResult();
		return result;
	}
	
	/**
	 * @param order
	 * @param result
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#execute(com.yjf.common.service.OrderBase,
	 * com.yjf.common.lang.result.StandardResultInfo)
	 */
	@Override
	public void execute(PaymentQueryOrder order, PaymentCheckUserResult result) {
		boolean isExist = paymentQueryRemoteService.hasUser(order);
		result.setExist(isExist);
		
		result.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
	}
	
}

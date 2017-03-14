/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:01:09 创建
 */
package com.yiji.ypayment.biz.facadeImpl.processor.applyPayment;

import org.springframework.stereotype.Component;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorBase;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.order.payment.UndoPaymentOrder;
import com.yiji.ypayment.facade.result.payment.PaymentUndoOrderResult;
import com.yjf.common.lang.result.Status;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Component("paymentUndoOrderProcessor")
public class PaymentUndoOrderProcessor extends PaymentProcessorBase
																	implements
																	PaymentProcessorTemplate<UndoPaymentOrder, PaymentUndoOrderResult> {
	
	/**
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#getAction()
	 */
	@Override
	public PaymentInstructionAction getAction() {
		return PaymentInstructionAction.UNDO_PAYMENT_ORDER;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#initResult(com.yjf.common.service.OrderBase)
	 */
	@Override
	public PaymentUndoOrderResult initResult(UndoPaymentOrder order) {
		return new PaymentUndoOrderResult();
	}
	
	/**
	 * @param order
	 * @param result
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#execute(com.yjf.common.service.OrderBase,
	 * com.yjf.common.lang.result.StandardResultInfo)
	 */
	@Override
	public void execute(UndoPaymentOrder order, PaymentUndoOrderResult result) {
		PaymentUndoOrderResult resultInfo = paymentRemoteService.undoPayment(order);
		if (resultInfo.getStatus() == Status.SUCCESS) {
			result.setUndoOrderStatus(resultInfo.getUndoOrderStatus());
			result.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
		} else {
			result.setUndoOrderStatus(resultInfo.getUndoOrderStatus());
			result.setResultCode(resultInfo.getResultCode());
			result.setCode(result.getCode());
			result.setDescription(result.getDescription());
			result.setStatus(Status.FAIL);
		}
	}
	
}

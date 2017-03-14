/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:48:34 创建
 */
package com.yiji.ypayment.biz.facadeImpl.processor.query;

import org.springframework.stereotype.Component;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorBase;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate;
import com.yiji.ypayment.dal.entity.business.UndoPayment;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.order.query.PaymentOrderStatusOrder;
import com.yiji.ypayment.facade.result.query.PaymentUndoOrderStatusResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Component("paymentUndoOrderStatusProcessor")
public class PaymentUndoOrderStatusProcessor extends PaymentProcessorBase
																			implements
																			PaymentProcessorTemplate<PaymentOrderStatusOrder, PaymentUndoOrderStatusResult> {
	
	/**
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#getAction()
	 */
	@Override
	public PaymentInstructionAction getAction() {
		return PaymentInstructionAction.QUERY_UNDO_ORDER_SATUS;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#initResult(com.yjf.common.service.OrderBase)
	 */
	@Override
	public PaymentUndoOrderStatusResult initResult(PaymentOrderStatusOrder order) {
		PaymentUndoOrderStatusResult result = new PaymentUndoOrderStatusResult();
		return result;
	}
	
	/**
	 * @param order
	 * @param result
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#execute(com.yjf.common.service.OrderBase,
	 * com.yjf.common.lang.result.StandardResultInfo)
	 */
	@Override
	public void execute(PaymentOrderStatusOrder order, PaymentUndoOrderStatusResult result) {
		try {
			UndoPayment undoPayment = undoPaymentService.findByUndoPaymentNo(order.getUndoPaymentNo());
			result.setUndoOrderStatus(undoPayment.getUndoStatus());
			result.setUndoAmount(undoPayment.getUndoAmount());
			result.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
		} catch (Exception e) {
			throw new PaymentException(PaymentResultCode.PAYMENT_QUERY_UNDO_ORDER_STATUS_FAIL,
				PaymentResultCode.PAYMENT_QUERY_UNDO_ORDER_STATUS_FAIL.getMessage());
		}
		
		result.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
	}
	
}

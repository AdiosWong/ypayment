/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午9:51:56 创建
 */
package com.yiji.ypayment.facade.api.Payment;

import com.yiji.ypayment.facade.order.payment.ApplyPaymentOrder;
import com.yiji.ypayment.facade.order.payment.UndoPaymentOrder;
import com.yiji.ypayment.facade.result.payment.ApplyPaymentResult;
import com.yiji.ypayment.facade.result.payment.PaymentUndoOrderResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public interface ApplyPaymentFacade {
	
	/**
	 * 缴费
	 * 
	 * @param paymentOrder
	 * @return
	 */
	ApplyPaymentResult applyPayment(ApplyPaymentOrder paymentOrder);
	
	/**
	 * 返销
	 * 
	 * @param undoPaymentOrder
	 * @return
	 */
	PaymentUndoOrderResult undoPayment(UndoPaymentOrder undoPaymentOrder);
	
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:02:29 创建
 */
package com.yiji.ypayment.facade.api.Payment;

import com.yiji.ypayment.facade.order.payment.PaymentBindingOrder;
import com.yiji.ypayment.facade.order.payment.PaymentUnbindingOrder;
import com.yiji.ypayment.facade.result.payment.PaymentBindingResult;
import com.yiji.ypayment.facade.result.payment.PaymentUnbindingResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public interface PaymentBindingFacade {
	
	/**
	 * 用户缴费绑定
	 * 
	 * @param paymentBindingOrder
	 * @return
	 */
	PaymentBindingResult paymentBinding(PaymentBindingOrder paymentBindingOrder);
	
	/**
	 * 用户缴费解绑
	 * 
	 * @param paymentUnbindingOrder
	 * @return
	 */
	PaymentUnbindingResult paymentUnbinding(PaymentUnbindingOrder paymentUnbindingOrder);
	
}

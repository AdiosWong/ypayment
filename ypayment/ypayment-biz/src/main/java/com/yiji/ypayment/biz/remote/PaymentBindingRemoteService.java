/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月26日 下午3:26:59 创建
 */

package com.yiji.ypayment.biz.remote;

import com.yiji.ypayment.facade.order.payment.PaymentBindingOrder;
import com.yiji.ypayment.facade.order.payment.PaymentUnbindingOrder;
import com.yiji.ypayment.facade.result.payment.PaymentBindingResult;
import com.yiji.ypayment.facade.result.payment.PaymentUnbindingResult;

/**
 * 用户缴费绑定服务
 *
 * @author faZheng
 *
 */

public interface PaymentBindingRemoteService {
	
	/**
	 * 用户绑定缴费卡
	 * @param paymentBindingOrder
	 */
	PaymentBindingResult paymentBinding(PaymentBindingOrder paymentBindingOrder);
	
	/**
	 * 用户解绑缴费卡
	 * @param paymentBindingOrder
	 */
	PaymentUnbindingResult paymentUnBinding(PaymentUnbindingOrder PaymentUnbindingOrder);
}

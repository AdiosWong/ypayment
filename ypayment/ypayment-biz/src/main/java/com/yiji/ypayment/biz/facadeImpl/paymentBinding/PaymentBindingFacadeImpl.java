/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:55:16 创建
 */
package com.yiji.ypayment.biz.facadeImpl.paymentBinding;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiji.ypayment.biz.facadeImpl.base.BizServiceBase;
import com.yiji.ypayment.biz.facadeImpl.processor.paymentbinding.PaymentBindingProcessor;
import com.yiji.ypayment.biz.facadeImpl.processor.paymentbinding.PaymentUnbindingProcessor;
import com.yiji.ypayment.facade.api.Payment.PaymentBindingFacade;
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
@Service(version = "1.5")
public class PaymentBindingFacadeImpl extends BizServiceBase implements PaymentBindingFacade {
	
	@Autowired
	PaymentBindingProcessor paymentBindingProcessor;
	@Autowired
	PaymentUnbindingProcessor paymentUnbindingProcessor;
	
	/**
	 * @param paymentBindingOrder
	 * @return
	 * @see com.yiji.ypayment.facade.api.Payment.PaymentBindingFacade#paymentBinding(com.yiji.ypayment.facade.order.payment.PaymentBindingOrder)
	 */
	@Override
	public PaymentBindingResult paymentBinding(PaymentBindingOrder paymentBindingOrder) {
		return doBiz(paymentBindingOrder, paymentBindingProcessor);
	}
	
	/**
	 * @param paymentUnbindingOrder
	 * @return
	 * @see com.yiji.ypayment.facade.api.Payment.PaymentBindingFacade#paymentUnbinding(com.yiji.ypayment.facade.order.payment.PaymentUnbindingOrder)
	 */
	@Override
	public PaymentUnbindingResult paymentUnbinding(PaymentUnbindingOrder paymentUnbindingOrder) {
		return doBiz(paymentUnbindingOrder, paymentUnbindingProcessor);
	}
	
}

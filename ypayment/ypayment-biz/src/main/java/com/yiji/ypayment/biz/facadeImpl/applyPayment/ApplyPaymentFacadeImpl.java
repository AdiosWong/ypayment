/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月27日 上午10:19:14 创建
 */

package com.yiji.ypayment.biz.facadeImpl.applyPayment;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiji.ypayment.biz.facadeImpl.base.BizServiceBase;
import com.yiji.ypayment.biz.facadeImpl.processor.applyPayment.ApplyPaymentProcessor;
import com.yiji.ypayment.biz.facadeImpl.processor.applyPayment.PaymentUndoOrderProcessor;
import com.yiji.ypayment.facade.api.Payment.ApplyPaymentFacade;
import com.yiji.ypayment.facade.order.payment.ApplyPaymentOrder;
import com.yiji.ypayment.facade.order.payment.UndoPaymentOrder;
import com.yiji.ypayment.facade.result.payment.ApplyPaymentResult;
import com.yiji.ypayment.facade.result.payment.PaymentUndoOrderResult;

/**
 *
 *
 * @author faZheng
 *
 */
@Service(version = "1.5")
public class ApplyPaymentFacadeImpl extends BizServiceBase implements ApplyPaymentFacade {
	
	@Autowired
	private ApplyPaymentProcessor applyPaymentProcessor;
	@Autowired
	private PaymentUndoOrderProcessor paymentUndoOrderProcessor;
	
	/**
	 * @param paymentOrder
	 * @return
	 * @see com.yiji.ypayment.facade.api.Payment.ApplyPaymentFacade#applyPayment(com.yiji.ypayment.facade.order.payment.ApplyPaymentOrder)
	 */
	@Override
	public ApplyPaymentResult applyPayment(ApplyPaymentOrder paymentOrder) {
		return doBiz(paymentOrder, applyPaymentProcessor);
	}
	
	/**
	 * @param undoPaymentOrder
	 * @return
	 * @see com.yiji.ypayment.facade.api.Payment.ApplyPaymentFacade#undoPayment(com.yiji.ypayment.facade.order.payment.UndoPaymentOrder)
	 */
	@Override
	public PaymentUndoOrderResult undoPayment(UndoPaymentOrder undoPaymentOrder) {
		return doBiz(undoPaymentOrder, paymentUndoOrderProcessor);
	}
	
}

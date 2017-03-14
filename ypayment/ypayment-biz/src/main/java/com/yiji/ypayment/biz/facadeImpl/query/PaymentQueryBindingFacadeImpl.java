/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:52:54 创建
 */
package com.yiji.ypayment.biz.facadeImpl.query;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiji.ypayment.biz.facadeImpl.base.BizServiceBase;
import com.yiji.ypayment.biz.facadeImpl.processor.query.PaymentQueryBindingProcessor;
import com.yiji.ypayment.facade.api.query.PaymentQueryBindingFacade;
import com.yiji.ypayment.facade.order.query.PaymentQueryBindingOrder;
import com.yiji.ypayment.facade.result.query.PaymentQueryBindingResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service(version = "1.5")
public class PaymentQueryBindingFacadeImpl extends BizServiceBase implements PaymentQueryBindingFacade {
	
	@Autowired
	PaymentQueryBindingProcessor paymentQueryBindingProcessor;
	
	/**
	 * @param paymentQueryBindingOrder
	 * @return
	 * @see com.yiji.ypayment.facade.api.query.PaymentQueryBindingFacade#paymentQueryBindingInfo(com.yiji.ypayment.facade.order.query.PaymentQueryBindingOrder)
	 */
	@Override
	public PaymentQueryBindingResult paymentQueryBindingInfo(PaymentQueryBindingOrder paymentQueryBindingOrder) {
		return doBiz(paymentQueryBindingOrder, paymentQueryBindingProcessor);
	}
	
}

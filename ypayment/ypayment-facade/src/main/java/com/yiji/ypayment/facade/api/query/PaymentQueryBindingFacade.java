/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:20:28 创建
 */
package com.yiji.ypayment.facade.api.query;

import com.yiji.ypayment.facade.order.query.PaymentQueryBindingOrder;
import com.yiji.ypayment.facade.result.query.PaymentQueryBindingResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public interface PaymentQueryBindingFacade {
	
	/**
	 * 查询已绑定缴费卡号信息
	 * 
	 * @param paymentQueryBindingOrder
	 * @return
	 */
	PaymentQueryBindingResult paymentQueryBindingInfo(PaymentQueryBindingOrder paymentQueryBindingOrder);
	
}

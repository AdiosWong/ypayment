/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:12:35 创建
 */
package com.yiji.ypayment.biz.remote;

import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.entity.business.UndoPayment;

/**
 * openapi异步回推服务
 *
 * @author xinqing@yiji.com
 *
 */
public interface OpenApiArchService {
	
	/**
	 * 异步回推openapi(缴费订单)
	 * 
	 * @param paymentOrder
	 */
	void apiAsyncNotify(PaymentOrder paymentOrder);
	
	/**
	 * 异步回推openapi(返销订单)
	 * 
	 * @param undoPayment
	 */
	void asyncNotifyUndoOrder(UndoPayment undoPayment);
	
}

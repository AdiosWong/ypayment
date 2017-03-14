/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午11:13:49 创建
 */
package com.yiji.ypayment.integration.client;

import com.yjf.quickpayment.route.order.RoutePaymentOrder;
import com.yjf.quickpayment.route.order.RouteUndoPaymentOrder;
import com.yjf.quickpayment.route.result.RoutePaymentResult;
import com.yjf.quickpayment.route.result.RouteUndoPaymentResult;

/**
 * 资源路由缴费
 *
 * @author xinqing@yiji.com
 *
 */
public interface RoutePaymentClient {
	
	/**
	 * 发送缴费扣款。
	 * @param paymentOrder 缴费扣款订单。
	 * @return 缴费结果。
	 */
	RoutePaymentResult sendPayment(RoutePaymentOrder paymentOrder);
	
	/**
	 * 返销
	 * @param routeUndoPaymentOrder 返销订单
	 * @return 返销结果
	 */
	RouteUndoPaymentResult undoPayment(RouteUndoPaymentOrder routeUndoPaymentOrder);
	
}

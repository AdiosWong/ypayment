/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:53:32 创建
 */
package com.yiji.ypayment.integration.client;

import com.yjf.quickpayment.route.order.RoutePaymentQueryOrder;
import com.yjf.quickpayment.route.result.RouteBalanceResult;
import com.yjf.quickpayment.route.result.RouteHasUserResult;
import com.yjf.quickpayment.route.result.RouteOrderStatusResult;
import com.yjf.quickpayment.route.result.RoutePaymentQueryResult;
import com.yjf.quickpayment.route.result.RouteUndoOrderStatusResult;

/**
 * 资源路由缴费查询
 *
 * @author xinqing@yiji.com
 *
 */
public interface RoutePaymentQueryClient {
	/**
	 * 查询应支付费用。
	 * @param queryOrder 查询参数订单。
	 * @return 应支付费用的查询结果。
	 */
	RoutePaymentQueryResult findShouldPayCosts(RoutePaymentQueryOrder routeQueryPaymentOrder);
	
	/**
	 * 判定缴费用户是否存在。
	 * @param queryOrder 查询参数订单。
	 * @return 判定结果。
	 */
	RouteHasUserResult hasUser(RoutePaymentQueryOrder routeQueryPaymentOrder);
	
	/**
	 * 查询订单状态。
	 * @param routeSerialNumber 订单号
	 * @param queryFromOut 是否去资源方查询
	 * @return 查询订单结果。
	 */
	RouteOrderStatusResult findOrderStatus(String routeSerialNumber);
	
	/**
	 * 查询备付金。
	 * @param agencyGroupCode 代理机构（机构组）编码
	 * @return 查询备付金的结果。
	 */
	RouteBalanceResult findBalance(String agencyGroupCode);
	
	/**
	 * 查询取消缴费的状态
	 * @param orderSerialNumber 取消订单号
	 * @return
	 */
	RouteUndoOrderStatusResult findUndoOrderStatus(String orderSerialNumber);
}

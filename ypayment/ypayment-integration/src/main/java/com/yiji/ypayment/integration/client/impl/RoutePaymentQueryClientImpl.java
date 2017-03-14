/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:56:13 创建
 */
package com.yiji.ypayment.integration.client.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiji.ypayment.integration.client.RoutePaymentQueryClient;
import com.yiji.ypayment.integration.client.impl.caller.CallerServiceBase;
import com.yjf.quickpayment.route.api.RoutePaymentQueryFacade;
import com.yjf.quickpayment.route.order.RoutePaymentQueryOrder;
import com.yjf.quickpayment.route.result.RouteBalanceResult;
import com.yjf.quickpayment.route.result.RouteHasUserResult;
import com.yjf.quickpayment.route.result.RouteOrderStatusResult;
import com.yjf.quickpayment.route.result.RoutePaymentQueryResult;
import com.yjf.quickpayment.route.result.RouteUndoOrderStatusResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("routePaymentQueryClient")
public class RoutePaymentQueryClientImpl extends CallerServiceBase implements RoutePaymentQueryClient {
	
	@Reference(version = "1.5", group = "online")
	private RoutePaymentQueryFacade routePaymentQueryFacade;
	
	/**
	 * @param routeQueryPaymentOrder
	 * @return
	 * @see com.yiji.ypayment.integration.client.RoutePaymentQueryClient#findShouldPayCosts(com.yjf.quickpayment.route.order.RoutePaymentQueryOrder)
	 */
	@Override
	public RoutePaymentQueryResult findShouldPayCosts(RoutePaymentQueryOrder routeQueryPaymentOrder) {
		logger.info("缴费查询入参:{}", routeQueryPaymentOrder);
		RoutePaymentQueryResult result = routePaymentQueryFacade.findShouldPayCosts(routeQueryPaymentOrder,
			OPERATION_CONTEXT);
		logger.info("缴费查询出参:{}", result);
		return result;
	}
	
	/**
	 * @param routeQueryPaymentOrder
	 * @return
	 * @see com.yiji.ypayment.integration.client.RoutePaymentQueryClient#hasUser(com.yjf.quickpayment.route.order.RoutePaymentQueryOrder)
	 */
	@Override
	public RouteHasUserResult hasUser(RoutePaymentQueryOrder routeQueryPaymentOrder) {
		logger.info("判定缴费用户是否存在入参：{}", routeQueryPaymentOrder);
		RouteHasUserResult result =  routePaymentQueryFacade.hasUser(routeQueryPaymentOrder, OPERATION_CONTEXT);
		logger.info("判定缴费用户是否存在出参：{}", result);
		return result;
	}
	
	/**
	 * @param routeSerialNumber
	 * @param queryFromOut
	 * @return
	 * @see com.yiji.ypayment.integration.client.RoutePaymentQueryClient#findOrderStatus(java.lang.String,
	 * boolean)
	 */
	@Override
	public RouteOrderStatusResult findOrderStatus(String routeSerialNumber) {
		logger.info("查询订单状态入参：routeSerialNumber={}", routeSerialNumber);
		RouteOrderStatusResult result = routePaymentQueryFacade.findOrderStatus(routeSerialNumber, false, OPERATION_CONTEXT);
		logger.info("查询订单状态出参：{}", result);
		return result;
	}
	
	/**
	 * @param agencyGroupCode
	 * @return
	 * @see com.yiji.ypayment.integration.client.RoutePaymentQueryClient#findBalance(java.lang.String)
	 */
	@Override
	public RouteBalanceResult findBalance(String agencyGroupCode) {
		logger.info("查询备付金，入参：agencyGroupCode={}", agencyGroupCode);
		RouteBalanceResult result = routePaymentQueryFacade.findBalance(agencyGroupCode, OPERATION_CONTEXT);
		logger.info("查询备付金，出参：{}", result);
		return result;
	}
	
	/**
	 * @param orderSerialNumber
	 * @return
	 * @see com.yiji.ypayment.integration.client.RoutePaymentQueryClient#findUndoOrderStatus(java.lang.String)
	 */
	@Override
	public RouteUndoOrderStatusResult findUndoOrderStatus(String orderSerialNumber) {
		logger.info("查询返销订单，入参：orderSerialNumber={}", orderSerialNumber);
		RouteUndoOrderStatusResult result = routePaymentQueryFacade.findUndoOrderStatus(orderSerialNumber, OPERATION_CONTEXT);
		logger.info("查询返销订单，出参：result={}", result);
		return result;
	}
	
}

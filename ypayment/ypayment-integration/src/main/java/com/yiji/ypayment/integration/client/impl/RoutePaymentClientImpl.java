/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午11:15:01 创建
 */
package com.yiji.ypayment.integration.client.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiji.ypayment.integration.client.RoutePaymentClient;
import com.yiji.ypayment.integration.client.impl.caller.CallerServiceBase;
import com.yjf.quickpayment.route.api.RoutePaymentFacade;
import com.yjf.quickpayment.route.order.RoutePaymentOrder;
import com.yjf.quickpayment.route.order.RouteUndoPaymentOrder;
import com.yjf.quickpayment.route.result.RoutePaymentResult;
import com.yjf.quickpayment.route.result.RouteUndoPaymentResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("routePaymentClient")
public class RoutePaymentClientImpl extends CallerServiceBase implements RoutePaymentClient {
	
	@Reference(version = "1.5", group = "online")
	private RoutePaymentFacade routePaymentFacadeRemote;
	
	/**
	 * @param paymentOrder
	 * @return
	 * @see com.yiji.ypayment.integration.client.QuickPaymentClient#sendPayment(com.yjf.quickpayment.route.order.RoutePaymentOrder)
	 */
	@Override
	public RoutePaymentResult sendPayment(RoutePaymentOrder paymentOrder) {
		logger.info("缴费扣款入参:{}", paymentOrder);
		RoutePaymentResult result = routePaymentFacadeRemote.sendPayment(paymentOrder, OPERATION_CONTEXT);
		logger.info("缴费扣款出参:{}", result);
		return result;
	}
	
	/**
	 * @param routeUndoPaymentOrder
	 * @return
	 * @see com.yiji.ypayment.integration.client.RoutePaymentClient#undoPayment(com.yjf.quickpayment.route.order.RouteUndoPaymentOrder)
	 */
	@Override
	public RouteUndoPaymentResult undoPayment(RouteUndoPaymentOrder routeUndoPaymentOrder) {
		logger.info("进入返销,入参:{}", routeUndoPaymentOrder);
		RouteUndoPaymentResult routeUndoPaymentResult = routePaymentFacadeRemote.undoPayment(routeUndoPaymentOrder,
			OPERATION_CONTEXT);
		logger.info("退出返销,出参:{}", routeUndoPaymentResult);
		return routeUndoPaymentResult;
	}
	
}

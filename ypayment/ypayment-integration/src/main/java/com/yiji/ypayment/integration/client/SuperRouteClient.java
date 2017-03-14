/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:36:29 创建
 */
package com.yiji.ypayment.integration.client;

import com.yiji.superroute.facade.route.order.ChannelRouteOrder;
import com.yiji.superroute.facade.route.result.ChannelRouteResult;

/**
 * 超级路由
 *
 * @author xinqing@yiji.com
 *
 */
public interface SuperRouteClient {
	
	/**
	 * @param routeOrder　路由Order
	 * @param operationContext 操作上下文,可传null
	 * @return 路由结果（支付上下文）
	 */
	ChannelRouteResult channelRoute(ChannelRouteOrder routeOrder);
	 
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:36:43 创建
 */
package com.yiji.ypayment.integration.client.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiji.superroute.facade.route.api.SuperRouteFacade;
import com.yiji.superroute.facade.route.order.ChannelRouteOrder;
import com.yiji.superroute.facade.route.result.ChannelRouteResult;
import com.yiji.ypayment.integration.client.SuperRouteClient;
import com.yiji.ypayment.integration.client.impl.caller.CallerServiceBase;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("superRouteClient")
public class SuperRouteClientImpl extends CallerServiceBase implements SuperRouteClient {
	
	@Reference(version = "1.5")
	private SuperRouteFacade superRouteFacadeRemote;
	
	@Override
	public ChannelRouteResult channelRoute(ChannelRouteOrder routeOrder) {
		logger.info("查询超级路由渠道路由入参:{}", routeOrder);
		ChannelRouteResult result = superRouteFacadeRemote.channelRoute(routeOrder, OPERATION_CONTEXT);
		logger.info("查询超级路由渠道路由出参:{}", result);
		return result;
	}
	
}

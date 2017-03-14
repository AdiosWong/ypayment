/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:13:51 创建
 */
package com.yiji.ypayment.integration.client.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiji.ypayment.integration.client.RouteQueryClient;
import com.yiji.ypayment.integration.client.impl.caller.CallerServiceBase;
import com.yjf.quickpayment.route.api.RouteQueryFacade;
import com.yjf.quickpayment.route.order.RouteQueryOrder;
import com.yjf.quickpayment.route.result.AgencyChannelResult;
import com.yjf.quickpayment.route.result.AgencyGroupResult;
import com.yjf.quickpayment.route.result.ResourceInstResult;
import com.yjf.quickpayment.route.result.RouteResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("routeQueryClient")
public class RouteQueryClientImpl extends CallerServiceBase implements RouteQueryClient {
	
	@Reference(version = "1.5", group = "online")
	private RouteQueryFacade routeQueryFacade;
	
	/**
	 * @param routeQueryOrder
	 * @return
	 * @see com.yiji.ypayment.integration.client.RouteQueryClient#route(com.yjf.quickpayment.route.order.RouteQueryOrder)
	 */
	@Override
	public RouteResult route(RouteQueryOrder routeQueryOrder) {
		logger.info("资源渠道路由，入参：{}", routeQueryOrder);
		RouteResult result =  routeQueryFacade.route(routeQueryOrder, OPERATION_CONTEXT);
		logger.info("资源渠道路由，出参：{}", result);
		return result;
	}
	
	/**
	 * @param channelCode
	 * @return
	 * @see com.yiji.ypayment.integration.client.RouteQueryClient#queryAgencyChannel(java.lang.String)
	 */
	@Override
	public AgencyChannelResult queryAgencyChannel(String channelCode) {
		logger.info("查询机构渠道，入参：paymentType={}", channelCode);
		AgencyChannelResult result =  routeQueryFacade.queryAgencyChannel(channelCode, OPERATION_CONTEXT);
		logger.info("查询机构渠道，出参：paymentType={}", result);
		return result;
	}
	
	/**
	 * @param groupCode
	 * @return
	 * @see com.yiji.ypayment.integration.client.RouteQueryClient#queryAgencyGroup(java.lang.String)
	 */
	@Override
	public AgencyGroupResult queryAgencyGroup(String groupCode) {
		logger.info("查询机构组信息，入参：paymentType={}", groupCode);
		AgencyGroupResult result = routeQueryFacade.queryAgencyGroup(groupCode, OPERATION_CONTEXT);
		logger.info("查询机构组信息，出参：paymentType={}", result);
		return result;
	}
	
	/**
	 * @param paymentType
	 * @return
	 * @see com.yiji.ypayment.integration.client.RouteQueryClient#queryResourceInst(java.lang.String)
	 */
	@Override
	public ResourceInstResult queryResourceInst(String paymentType) {
		logger.info("查询资源列表，入参：paymentType={}", paymentType);
		ResourceInstResult result =  routeQueryFacade.queryResourceInst(paymentType, OPERATION_CONTEXT);
		logger.info("查询资源列表，出参：{}", result);
		return result;
	}

	/**
	 * @param resourceCode
	 * @return
	 * @see com.yiji.ypayment.integration.client.RouteQueryClient#queryResource(java.lang.String)
	 */
	@Override
	public ResourceInstResult queryResource(String resourceCode) {
		logger.info("查询单个渠道，入参：resourceCode={}", resourceCode);
		ResourceInstResult result =  routeQueryFacade.queryRosourceinst(resourceCode, OPERATION_CONTEXT);
		logger.info("查询单个渠道，出参：{}", result);
		return result;
	}
	
}

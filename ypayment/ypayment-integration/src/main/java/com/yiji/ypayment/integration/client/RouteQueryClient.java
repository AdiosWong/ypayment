/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:11:46 创建
 */
package com.yiji.ypayment.integration.client;

import com.yjf.quickpayment.route.order.RouteQueryOrder;
import com.yjf.quickpayment.route.result.AgencyChannelResult;
import com.yjf.quickpayment.route.result.AgencyGroupResult;
import com.yjf.quickpayment.route.result.ResourceInstResult;
import com.yjf.quickpayment.route.result.RouteResult;

/**
 * 资源路由查询
 *
 * @author xinqing@yiji.com
 *
 */
public interface RouteQueryClient {
	/**
	 * 资源渠道路由
	 * @param routeQueryOrder 路由Order
	 * @return 资源渠道路由结果
	 */
	RouteResult route(RouteQueryOrder routeQueryOrder);
	
	/**
	 * 查询机构渠道
	 * <p>
	 * 如果<code>channelCode</code>非空，则返回该渠道信息<br>
	 * 如果<code>channelCode</code>为空，则返回渠道信息列表<br>
	 * @param channelCode 渠道编码
	 * @return 机构渠道信息。
	 */
	AgencyChannelResult queryAgencyChannel(String channelCode);
	
	/**
	 * 查询机构组信息
	 * <p>
	 * 如果<code>groupCode</code>非空，则返回该代理商（机构组）信息<br>
	 * 如果<code>groupCode</code>为空，则返回代理商（机构组）信息列表<br>
	 * @param groupCode 机构组编码
	 * @return 代理商（机构组）信息。
	 */
	AgencyGroupResult queryAgencyGroup(String groupCode);
	
	/**
	 * 查询资源列表
	 * <p>
	 * 如果<code>paymentType</code>，则返回该代理商（机构组）信息<br>
	 * @param paymentType 缴费类型
	 * @return 资源厂商列表。
	 */
	ResourceInstResult queryResourceInst(String paymentType);
	
	/**
	 * 查询资源-单个
	 * <p>
	 * 如果<code>paymentType</code>，则返回该代理商（机构组）信息<br>
	 * @param resourceCode 渠道编码
	 * @return 资源厂商。
	 */
	ResourceInstResult queryResource(String resourceCode);
	
}

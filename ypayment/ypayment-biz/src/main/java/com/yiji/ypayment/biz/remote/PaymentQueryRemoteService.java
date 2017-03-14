/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午6:48:31 创建
 */
package com.yiji.ypayment.biz.remote;

import java.util.List;

import com.yiji.ypayment.biz.remote.info.PaymentChannelInfo;
import com.yiji.ypayment.biz.remote.info.PaymentQueryInfo;
import com.yiji.ypayment.biz.remote.info.ResourceInstInfo;
import com.yiji.ypayment.biz.remote.order.PaymentRouteQueryOrder;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.order.query.PaymentQueryOrder;
import com.yjf.common.lang.util.money.Money;
import com.yjf.quickpayment.route.result.RouteOrderStatusResult;
import com.yjf.quickpayment.route.result.RouteUndoOrderStatusResult;

/**
 * 缴费查询服务
 *
 * @author xinqing@yiji.com
 *
 */
public interface PaymentQueryRemoteService {
	
	/**
	 * 判定缴费用户是否存在。
	 * @param queryOrder 查询参数订单。
	 * @return 判定结果。
	 */
	boolean hasUser(PaymentQueryOrder order);
	
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
	Money findBalance(String agencyGroupCode);
	
	/**
	 * 查询取消缴费的状态
	 * @param orderSerialNumber 取消订单号
	 * @return 取消缴费的状态
	 */
	RouteUndoOrderStatusResult findUndoOrderStatus(String orderSerialNumber);
	
	/**
	 * 查询用户欠费
	 * 
	 * @param paymentOrder
	 * @return
	 */
	PaymentQueryInfo queryPayment(PaymentQueryOrder paymentQueryOrder);
	
	/**
	 * 查询资源渠道路由
	 * @param routeQueryOrder 路由Order
	 * @return 资源渠道路由结果
	 */
	PaymentChannelInfo route(PaymentRouteQueryOrder paymentRouteQueryOrder);
	
	/**
	 * 查询资源列表
	 * <p>
	 * 如果<code>paymentType</code>，则返回该代理商（机构组）信息<br>
	 * @param paymentType 缴费类型
	 * @return 资源厂商列表。
	 */
	List<ResourceInstInfo> queryResourceInst(PaymentTypeEnum paymentTypeEnum);
	
	/**
	 * 查询资源-单个
	 * 
	 * @param resourceCode
	 * @return
	 */
	ResourceInstInfo queryResource(String resourceCode);
	
}

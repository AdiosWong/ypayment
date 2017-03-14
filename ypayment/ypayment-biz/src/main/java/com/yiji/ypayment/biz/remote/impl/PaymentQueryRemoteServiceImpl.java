/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午6:50:36 创建
 */
package com.yiji.ypayment.biz.remote.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yiji.ypayment.biz.common.utils.PayTypeToBusiTypeUtils;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.remote.PaymentQueryRemoteService;
import com.yiji.ypayment.biz.remote.base.RemoteServiceBase;
import com.yiji.ypayment.biz.remote.info.PaymentChannelInfo;
import com.yiji.ypayment.biz.remote.info.PaymentQueryInfo;
import com.yiji.ypayment.biz.remote.info.ResourceInstInfo;
import com.yiji.ypayment.biz.remote.order.PaymentRouteQueryOrder;
import com.yiji.ypayment.common.constant.Constant;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.info.query.PayItemInfo;
import com.yiji.ypayment.facade.order.query.PaymentQueryOrder;
import com.yjf.common.collection.CollectionUtils;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.StringUtils;
import com.yjf.quickpayment.route.order.RoutePaymentQueryOrder;
import com.yjf.quickpayment.route.order.RouteQueryOrder;
import com.yjf.quickpayment.route.result.ResourceInstResult;
import com.yjf.quickpayment.route.result.RouteBalanceResult;
import com.yjf.quickpayment.route.result.RouteHasUserResult;
import com.yjf.quickpayment.route.result.RouteOrderStatusResult;
import com.yjf.quickpayment.route.result.RoutePaymentQueryResult;
import com.yjf.quickpayment.route.result.RouteResult;
import com.yjf.quickpayment.route.result.RouteUndoOrderStatusResult;
import com.yjf.quickpayment.route.result.ShouldPayCosts;
import com.yjf.quickpayment.route.result.ShouldPayCosts.CostItem;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("paymentQueryRemoteService")
public class PaymentQueryRemoteServiceImpl extends RemoteServiceBase implements PaymentQueryRemoteService {
	
	/**
	 * @param routeQueryPaymentOrder
	 * @return
	 * @see com.yiji.ypayment.biz.remote.PaymentQueryRemoteService#hasUser(com.yjf.quickpayment.route.order.RoutePaymentQueryOrder)
	 */
	@Override
	public boolean hasUser(PaymentQueryOrder order) {
		RoutePaymentQueryOrder routeQueryPaymentOrder = new RoutePaymentQueryOrder();
		routeQueryPaymentOrder.setUserCode(order.getUserCode());
		routeQueryPaymentOrder.setPaymentType(order.getPaymentType().getCode());
		routeQueryPaymentOrder.setInstCode(order.getResourceCode());
		if (StringUtils.isNotBlank(order.getStoreId())) {
			routeQueryPaymentOrder.setAppId(order.getStoreId());
		} else {
			routeQueryPaymentOrder.setAppId(order.getPlatformType());
		}
		routeQueryPaymentOrder.setSystemId(order.getPlatformType());
		routeQueryPaymentOrder.setBusiType(PayTypeToBusiTypeUtils.payTypeToBusiType(order.getPaymentType()));
		//统一订单号新增
		routeQueryPaymentOrder.setGid(order.getGid());
		routeQueryPaymentOrder.setPartnerId(order.getPartnerId());
		routeQueryPaymentOrder.setMerchOrderNo(order.getMerchOrderNo());
		try {
			RouteHasUserResult result = routePaymentQueryClient.hasUser(routeQueryPaymentOrder);
			return result.isExist();
		} catch (Exception e) {
			logger.error("查询用户是否存在失败, 失败原因：{}", e.getMessage(), e);
			throw new PaymentException(PaymentResultCode.PAYMENT_QUERY_USER_FAIL, e.getMessage());
		}
	}
	
	/**
	 * @param routeSerialNumber
	 * @param queryFromOut
	 * @return
	 * @see com.yiji.ypayment.biz.remote.PaymentQueryRemoteService#findOrderStatus(java.lang.String,
	 * boolean)
	 */
	@Override
	public RouteOrderStatusResult findOrderStatus(String routeSerialNumber) {
		try {
			RouteOrderStatusResult result = routePaymentQueryClient.findOrderStatus(routeSerialNumber);
			return result;
		} catch (Exception e) {
			logger.error("查询缴费订单状态失败, 失败原因：{}", e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * @param agencyGroupCode
	 * @return
	 * @see com.yiji.ypayment.biz.remote.PaymentQueryRemoteService#findBalance(java.lang.String)
	 */
	@Override
	public Money findBalance(String agencyGroupCode) {
		try {
			RouteBalanceResult result = routePaymentQueryClient.findBalance(agencyGroupCode);
			return result.isMoney();
		} catch (Exception e) {
			logger.error("查询备付金失败, 失败原因：{}", e.getMessage(), e);
			throw new PaymentException(PaymentResultCode.PAYMENT_QUERY_BALANCE_FAIL, e.getMessage());
		}
	}
	
	/**
	 * @param orderSerialNumber
	 * @return
	 * @see com.yiji.ypayment.biz.remote.PaymentQueryRemoteService#findUndoOrderStatus(java.lang.String)
	 */
	@Override
	public RouteUndoOrderStatusResult findUndoOrderStatus(String orderSerialNumber) {
		try {
			RouteUndoOrderStatusResult result = routePaymentQueryClient.findUndoOrderStatus(orderSerialNumber);
			return result;
		} catch (Exception e) {
			logger.error("查询返销订单状态失败, 失败原因：{}", e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * @param userCode
	 * @param paymentType
	 * @param instCode
	 * @return
	 * @see com.yiji.ypayment.biz.remote.PaymentQueryRemoteService#queryPayment(java.lang.String,
	 * com.yiji.ypayment.facade.enums.PaymentTypeEnum, java.lang.String)
	 */
	@Override
	public PaymentQueryInfo queryPayment(PaymentQueryOrder paymentQueryOrder) {
		PaymentQueryInfo paymentQueryInfo = new PaymentQueryInfo();
		RoutePaymentQueryOrder routePaymentQueryOrder = new RoutePaymentQueryOrder();
		routePaymentQueryOrder.setUserCode(paymentQueryOrder.getUserCode());
		routePaymentQueryOrder.setPaymentType(paymentQueryOrder.getPaymentType().getCode());
		routePaymentQueryOrder.setInstCode(paymentQueryOrder.getResourceCode());
		if(StringUtils.isBlank(paymentQueryOrder.getStoreId())){
			routePaymentQueryOrder.setAppId(paymentQueryOrder.getPlatformType());
		}else{
			routePaymentQueryOrder.setAppId(paymentQueryOrder.getStoreId());
		}
		routePaymentQueryOrder.setSystemId(paymentQueryOrder.getPlatformType());
		routePaymentQueryOrder
			.setBusiType(PayTypeToBusiTypeUtils.payTypeToBusiType(paymentQueryOrder.getPaymentType()));
		//统一订单号新增
		routePaymentQueryOrder.setGid(paymentQueryOrder.getGid());
		routePaymentQueryOrder.setPartnerId(paymentQueryOrder.getPartnerId());
		routePaymentQueryOrder.setMerchOrderNo(paymentQueryOrder.getMerchOrderNo());
		try {
			RoutePaymentQueryResult routePaymentQueryResult = routePaymentQueryClient
				.findShouldPayCosts(routePaymentQueryOrder);
			paymentQueryInfo = checkPaymentCostsResult(paymentQueryInfo, routePaymentQueryResult, paymentQueryOrder.getPaymentType());
			
			if (PaymentTypeEnum.GAS == paymentQueryOrder.getPaymentType()) {
				// 气费查询成功后还需要查询垃圾费
				logger.info("气费查询成功后，还需要查询垃圾费");
				routePaymentQueryOrder.setPaymentType(Constant.PAYMENT_TYPE_RUBBISH);
				RoutePaymentQueryResult rubbishResult = routePaymentQueryClient
					.findShouldPayCosts(routePaymentQueryOrder);
				paymentQueryInfo = checkPaymentCostsResult(paymentQueryInfo, rubbishResult, PaymentTypeEnum.RUBBISH);
			}
			return paymentQueryInfo;
		} catch (Exception e) {
			logger.error("查询用户欠费失败, 失败原因：{}", e.getMessage(), e);
			throw new PaymentException(PaymentResultCode.PAYMENT_BILL_FAIL, e.getMessage());
		}
	}
	
	private PaymentQueryInfo checkPaymentCostsResult(PaymentQueryInfo paymentQueryInfo, RoutePaymentQueryResult routResult, PaymentTypeEnum paymentType) {
		List<ShouldPayCosts> shouldPayCostsList = routResult.getShouldPayCostsList();
		if (CollectionUtils.isEmpty(shouldPayCostsList)) {
			logger.info("查询应缴列表为空, 返回信息：{}", routResult.getDescription());
			return paymentQueryInfo;
		}
		ShouldPayCosts shouldPayCosts = routResult.getShouldPayCostsList().get(0);
		paymentQueryInfo.setAddress(shouldPayCosts.getAddress());
		paymentQueryInfo.setResourceCode(shouldPayCosts.getInstCode());
		paymentQueryInfo.setUserCode(shouldPayCosts.getUserCode());
		paymentQueryInfo.setUsername(shouldPayCosts.getUsername());
		paymentQueryInfo.setYjfAccount(routResult.getYjfId());
		if(PaymentTypeEnum.RUBBISH == paymentType){
			paymentQueryInfo.setChannelRubbishCode(routResult.getChannelCode());
		}else{
			paymentQueryInfo.setChannelCode(routResult.getChannelCode());
		}
		for (CostItem costItem : shouldPayCosts.getItems()) {
			PayItemInfo costItemInfo = new PayItemInfo();
			costItemInfo.setSerialNumber(costItem.getSerialNumber());
			costItemInfo.setContractNumber(costItem.getContractNumber());
			costItemInfo.setName(costItem.getName());
			costItemInfo.setDate(costItem.getMonth());
			costItemInfo.setCount(costItem.getCount());
			costItemInfo.setPayables(costItem.getPayables());
			costItemInfo.setCharge(costItem.getCharge());
			costItemInfo.setPaymentType(PaymentTypeEnum.getByCode(costItem.getType()));
			paymentQueryInfo.getItems().add(costItemInfo);
			paymentQueryInfo.getTotalPayables().addTo(costItem.getPayables());
		}
		return paymentQueryInfo;
	}
	
	/**
	 * @param userCode
	 * @param instCode
	 * @param paymentTypeEnum
	 * @return
	 * @see com.yiji.ypayment.biz.remote.PaymentQueryRemoteService#route(java.lang.String,
	 * java.lang.String, com.yiji.ypayment.facade.enums.PaymentTypeEnum)
	 */
	@Override
	public PaymentChannelInfo route(PaymentRouteQueryOrder paymentRouteQueryOrder) {
		RouteQueryOrder routeQueryOrder = new RouteQueryOrder();
		routeQueryOrder.setBusiType(PayTypeToBusiTypeUtils.payTypeToBusiType(paymentRouteQueryOrder.getPaymentType()));
		routeQueryOrder.setSystemId(paymentRouteQueryOrder.getSystemId());
		if (StringUtils.isBlank(paymentRouteQueryOrder.getStoreId())) {
			routeQueryOrder.setAppId(paymentRouteQueryOrder.getSystemId());
		} else {
			routeQueryOrder.setAppId(paymentRouteQueryOrder.getStoreId());
		}
		routeQueryOrder.setPaymentType(paymentRouteQueryOrder.getPaymentType().getCode());
		routeQueryOrder.setInstCode(paymentRouteQueryOrder.getResourceCode());
		routeQueryOrder.setUserCode(paymentRouteQueryOrder.getUserCode());
		//统一订单号新增
		routeQueryOrder.setGid(paymentRouteQueryOrder.getGid());
		routeQueryOrder.setPartnerId(paymentRouteQueryOrder.getPartnerId());
		routeQueryOrder.setMerchOrderNo(paymentRouteQueryOrder.getMerchOrderNo());
		try {
			RouteResult result = roumeteQueryClient.route(routeQueryOrder);
			PaymentChannelInfo paymentChannelInfo = new PaymentChannelInfo();
			if (result.getAgencyChannelInfo() != null) {
				paymentChannelInfo.setChannelCode(result.getAgencyChannelInfo().getChannelCode());
				paymentChannelInfo.setChannelName(result.getAgencyChannelInfo().getChannelName());
				paymentChannelInfo.setInstCode(result.getAgencyChannelInfo().getInstCode());
				paymentChannelInfo.setInstName(result.getAgencyChannelInfo().getInstName());
				paymentChannelInfo.setPaymentType(result.getAgencyChannelInfo().getPaymentType());
				paymentChannelInfo.setUserId(result.getAgencyGroupInfo().getUserId());
				paymentChannelInfo.setStatus(result.getAgencyChannelInfo().getStatus());
			}
			return paymentChannelInfo;
		} catch (Exception e) {
			logger.error("查询资源渠道路由失败, 失败原因：{}", e.getMessage(), e);
			throw new PaymentException(PaymentResultCode.PAYMENT_QUERY_AGENCY_CHANNEL_FAIL, e.getMessage());
		}
	}
	
	/**
	 * @param paymentTypeEnum
	 * @return
	 * @see com.yiji.ypayment.biz.remote.PaymentQueryRemoteService#queryResourceInst(com.yiji.ypayment.facade.enums.PaymentTypeEnum)
	 */
	@Override
	public List<ResourceInstInfo> queryResourceInst(PaymentTypeEnum paymentTypeEnum) {
		try {
			ResourceInstResult result = roumeteQueryClient.queryResourceInst(paymentTypeEnum.getCode());
			List<ResourceInstInfo> resourceInstInfos = Lists.newArrayList();
			for (com.yjf.quickpayment.route.info.ResourceInstInfo instInfo : result.getInstInfos()) {
				ResourceInstInfo resourceInstInfo = new ResourceInstInfo();
				resourceInstInfo.setCityName(instInfo.getCityName());
				resourceInstInfo.setInstCode(instInfo.getInstCode());
				resourceInstInfo.setInstName(instInfo.getInstName());
				resourceInstInfo.setNational(instInfo.isNational());
				resourceInstInfo.setOpen(instInfo.isOpen());
				resourceInstInfo.setPaymentType(instInfo.getPaymentType());
				resourceInstInfo.setProvinceName(instInfo.getProvinceName());
				resourceInstInfos.add(resourceInstInfo);
			}
			return resourceInstInfos;
		} catch (Exception e) {
			logger.error("查询资源列表失败, 失败原因：{}", e.getMessage(), e);
			throw new PaymentException(PaymentResultCode.PAYMENT_QUERY_RESOURCE_INST_FAIL, e.getMessage());
		}
	}

	/**
	 * @param resourceCode
	 * @return
	 * @see com.yiji.ypayment.biz.remote.PaymentQueryRemoteService#queryResource(java.lang.String)
	 */
	@Override
	public ResourceInstInfo queryResource(String resourceCode) {
		try{
			ResourceInstResult result = roumeteQueryClient.queryResource(resourceCode);
			com.yjf.quickpayment.route.info.ResourceInstInfo instInfo = result.getInstInfo();
			
			ResourceInstInfo resourceInfo = new ResourceInstInfo();
			resourceInfo.setCityName(instInfo.getCityName());
			resourceInfo.setInstCode(instInfo.getInstCode());
			resourceInfo.setInstName(instInfo.getInstName());
			resourceInfo.setNational(instInfo.isNational());
			resourceInfo.setOpen(instInfo.isOpen());
			resourceInfo.setPaymentType(instInfo.getPaymentType());
			resourceInfo.setProvinceName(instInfo.getProvinceName());
			return resourceInfo;
		}catch (Exception e) {
			logger.error("查询渠道资源失败, 失败原因：{}", e.getMessage(), e);
			throw new PaymentException(PaymentResultCode.PAYMENT_QUERY_RESOURCE_INST_FAIL, e.getMessage());
		}
	}
	
}

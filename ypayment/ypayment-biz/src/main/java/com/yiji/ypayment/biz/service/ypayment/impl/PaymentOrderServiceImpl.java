/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author faZheng 下午5:29:20 创建
 */
package com.yiji.ypayment.biz.service.ypayment.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.yiji.ypayment.biz.service.ypayment.PaymentOrderService;
import com.yiji.ypayment.common.dao.support.PageInfo;
import com.yiji.ypayment.common.service.EntityServiceImpl;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.dal.repository.business.PaymentOrderDao;
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.order.query.QueryPayOrderInfoOrder;

@Service("paymentOrderService")
public class PaymentOrderServiceImpl extends EntityServiceImpl<PaymentOrder, PaymentOrderDao> implements
																								PaymentOrderService {
	
	@Override
	public List<PaymentOrder> findByOutBizNo(String outBizNo) {
		return getEntityDao().findByOutBizNo(outBizNo);
	}
	
	@Override
	public PaymentOrder findByPaymentOrderNo(String paymentOrderNo) {
		return getEntityDao().findByPaymentOrderNo(paymentOrderNo);
	}
	
	/**
	 * @param paymentOrderNo
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.PaymentOrderService#lockByPaymentOrderNo(java.lang.String)
	 */
	@Override
	public PaymentOrder lockByPaymentOrderNo(String paymentOrderNo) {
		return getEntityDao().lockByPaymentOrderNo(paymentOrderNo);
	}
	
	/**
	 * @param paymentStatus
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.PaymentOrderService#findByPaymentItemStatusEnum(com.yiji.ypayment.dal.enums.PaymentItemStatusEnum)
	 */
	@Override
	public List<PaymentOrder> findByPaymentStatus(PaymentItemStatusEnum paymentStatus) {
		return getEntityDao().findByPaymentStatus(paymentStatus);
	}
	
	/**
	 * @param paymentStatus
	 * @param tradeStatus
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.PaymentOrderService#finbByPaymentItemStatusEnumAndTransferTradeStatusEnum(com.yiji.ypayment.dal.enums.PaymentItemStatusEnum,
	 * com.yiji.ypayment.dal.enums.TransferTradeStatusEnum)
	 */
	@Override
	public List<PaymentOrder> findByPaymentStatusAndTradeStatus(PaymentItemStatusEnum paymentStatus,
																TransferTradeStatusEnum tradeStatus) {
		return getEntityDao().findByPaymentStatusAndTradeStatus(paymentStatus, tradeStatus);
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.PaymentOrderService#query(com.yiji.ypayment.facade.order.query.QueryPayOrderInfoOrder)
	 */
	@Override
	public PageInfo<PaymentOrder> query(QueryPayOrderInfoOrder order) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("EQ_outBizNo", order.getOutBizNo());
		map.put("EQ_paymentOrderNo", order.getPaymentOrderNo());
		map.put("EQ_userId", order.getUserId());
		map.put("EQ_paymentType", order.getPaymentType());
		map.put("EQ_payFrom", order.getPlatformType());
		map.put("GTE_startTime", order.getStartTime());
		map.put("LTE_startTime", order.getEndTime());
		
		PageInfo<PaymentOrder> pageInfo = new PageInfo<PaymentOrder>();
		pageInfo.setCurrentPage(order.getCurrentPage());
		pageInfo.setCountOfCurrentPage(order.getCountOfCurrentPage());
		
		PageInfo<PaymentOrder> paymentOrders = query(pageInfo, map);
		return paymentOrders;
	}

	/**
	 * @param notifyOpenApi
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.PaymentOrderService#findByNotifyOpenApi(boolean)
	 */
	@Override
	public List<PaymentOrder> findByNotifyOpenApi(boolean notifyOpenApi) {
		return getEntityDao().findByNotifyOpenApi(notifyOpenApi);
	}
	
	/**
	 * @param order
	 * @param paymentTypes
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.PaymentOrderService#query(com.yiji.ypayment.facade.order.query.QueryPayOrderInfoOrder, java.util.List)
	 */
	@Override
	public PageInfo<PaymentOrder> query(QueryPayOrderInfoOrder order, List<PaymentTypeEnum> paymentTypes) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("EQ_outBizNo", order.getOutBizNo());
		map.put("EQ_userId", order.getUserId());
		map.put("IN_paymentType", paymentTypes);
		map.put("EQ_payFrom", order.getPlatformType());
		map.put("GTE_startTime", order.getStartTime());
		map.put("LTE_startTime", order.getEndTime());
		
		PageInfo<PaymentOrder> pageInfo = new PageInfo<PaymentOrder>();
		pageInfo.setCurrentPage(order.getCurrentPage());
		pageInfo.setCountOfCurrentPage(order.getCountOfCurrentPage());
		
		PageInfo<PaymentOrder> paymentOrders = query(pageInfo, map);
		return paymentOrders;
	}

}

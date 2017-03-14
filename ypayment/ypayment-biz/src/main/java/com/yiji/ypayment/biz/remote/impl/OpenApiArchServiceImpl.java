/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:14:50 创建
 */
package com.yiji.ypayment.biz.remote.impl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yiji.openapi.arch.facade.order.ApiNotifyOrder;
import com.yiji.openapi.arch.facade.result.ApiArchCommonResult;
import com.yiji.ypayment.biz.remote.OpenApiArchService;
import com.yiji.ypayment.biz.remote.PaymentRemoteService;
import com.yiji.ypayment.biz.remote.base.RemoteServiceBase;
import com.yiji.ypayment.common.utils.BeanToMapUtil;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.entity.business.UndoPayment;
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;
import com.yiji.ypayment.facade.enums.UndoPaymentStatusEnum;
import com.yiji.ypayment.facade.info.query.PayOrderInfo;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("openApiArchService")
public class OpenApiArchServiceImpl extends RemoteServiceBase implements OpenApiArchService {
	
	@Autowired
	PaymentRemoteService paymentRemoteService;
	
	/**
	 * @param paymentOrder
	 * @see com.yiji.ypayment.biz.remote.OpenApiArchService#apiAsyncNotify(com.yiji.ypayment.dal.entity.business.PaymentOrder)
	 */
	@Override
	public void apiAsyncNotify(PaymentOrder paymentOrder) {
		//明确状态才通知
		if (paymentOrder.getPaymentStatus() == PaymentItemStatusEnum.SUCCESS
			|| paymentOrder.getPaymentStatus() == PaymentItemStatusEnum.FAIL) {
			logger.info("异步通知商户，缴费订单：paymentOrder{}", paymentOrder);
			PayOrderInfo payOrderInfo = paymentRemoteService.bulidPayOrderInfo(paymentOrder);
			
			ApiNotifyOrder order = new ApiNotifyOrder();
			order.setGid(paymentOrder.getGid());
			order.setPartnerId(paymentOrder.getPartnerId());
			order.setMerchOrderNo(paymentOrder.getMerchOrderNo());
			
			Map<String, String> data = new HashMap<>();
			try {
				data = BeanToMapUtil.convertBeanToMap(payOrderInfo);
			} catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
				logger.error("异步通知商户失败，bean转化为map时异常：{}", e);
			}
			//替换itemInfos
			String itemInfosStr = JSON.toJSONString(payOrderInfo.getItemInfos());
			data.replace("itemInfos", itemInfosStr);
			
			order.setData(data);
			try {
				ApiArchCommonResult result = openApiArchClient.apiAsyncNotify(order);
				if (result.isSuccess()) {
					paymentOrder.setNotifyOpenApi(false);
					paymentOrderService.save(paymentOrder);
				}
			} catch (Exception e) {
				logger.warn("调用OpenApi通知商户失败：{}", e);
			}
		}
	}

	/**
	 * @param undoPayment
	 * @see com.yiji.ypayment.biz.remote.OpenApiArchService#asyncNotifyUndoOrder(com.yiji.ypayment.dal.entity.business.UndoPayment)
	 */
	@Override
	public void asyncNotifyUndoOrder(UndoPayment undoPayment) {
		if(undoPayment.getUndoStatus() == UndoPaymentStatusEnum.SUCCESS || undoPayment.getUndoStatus() == UndoPaymentStatusEnum.FAIL){
			logger.info("异步通知商户，返销订单：undoPayment{}", undoPayment);
			ApiNotifyOrder order = new ApiNotifyOrder();
			order.setGid(undoPayment.getGid());
			order.setPartnerId(undoPayment.getPartnerId());
			order.setMerchOrderNo(undoPayment.getMerchOrderNo());
			
			Map<String, String> data = new HashMap<>();
			data.put("undoPaymentNo", undoPayment.getUndoPaymentNo());
			data.put("paymentOrderNo", undoPayment.getPaymentOrderNo());
			data.put("undoStatus", undoPayment.getUndoStatus().getCode());
			order.setData(data);
			try {
				ApiArchCommonResult result = openApiArchClient.apiAsyncNotify(order);
				if (result.isSuccess()) {
					undoPayment.setNotifyOpenApi(false);
					undoPaymentService.save(undoPayment);
				}
			} catch (Exception e) {
				logger.warn("调用OpenApi通知商户失败：{}", e);
			}
		}
	}
	
}

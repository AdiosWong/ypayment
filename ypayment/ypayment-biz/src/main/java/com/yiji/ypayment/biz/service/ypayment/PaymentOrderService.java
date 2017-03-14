/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author faZheng 下午5:29:20 创建
 */
package com.yiji.ypayment.biz.service.ypayment;

import java.util.List;

import com.yiji.ypayment.common.dao.support.PageInfo;
import com.yiji.ypayment.common.service.EntityService;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.order.query.QueryPayOrderInfoOrder;

/**
 * 
 * 交易服务接口
 *
 * @author faZheng
 *
 */
public interface PaymentOrderService extends EntityService<PaymentOrder> {
	
	/**
	 * 根据外部订单号，查询缴费订单记录
	 * @param outBizNo
	 * @return
	 */
	List<PaymentOrder> findByOutBizNo(String outBizNo);
	
	/**
	 * 根据订单号，查询缴费订单
	 * @param paymentOrderNo
	 * @return
	 */
	PaymentOrder findByPaymentOrderNo(String paymentOrderNo);
	
	/**
	 * 根据订单号，查询缴费订单(锁定记录)
	 * @param paymentOrderNo
	 * @return
	 */
	PaymentOrder lockByPaymentOrderNo(String paymentOrderNo);
	
	/**
	 * 根据缴费状态查询缴费订单
	 * @param paymentStatus
	 * @return
	 */
	List<PaymentOrder> findByPaymentStatus(PaymentItemStatusEnum paymentStatus);
	
	/**
	 * 根据订单状态和转账状态查询订单
	 * @param paymentStatus
	 * @param tradeStatus
	 * @return
	 */
	List<PaymentOrder> findByPaymentStatusAndTradeStatus(PaymentItemStatusEnum paymentStatus,
															TransferTradeStatusEnum tradeStatus);
	
	/**
	 * 根据条件分页查询
	 * @param order
	 * @return
	 */
	PageInfo<PaymentOrder> query(QueryPayOrderInfoOrder order);
	
	/**
	 * 根据是否需要再次异步通知查询订单
	 * @param notifyOpenApi
	 * @return
	 */
	List<PaymentOrder> findByNotifyOpenApi(boolean notifyOpenApi);
	
	/**
	 * 根据缴费type列表条件分页查询
	 * @param order
	 * @return
	 */
	PageInfo<PaymentOrder> query(QueryPayOrderInfoOrder order, List<PaymentTypeEnum> paymentTypes);

}

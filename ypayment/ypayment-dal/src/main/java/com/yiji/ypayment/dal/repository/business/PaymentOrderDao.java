/*
* www.yiji.com Inc.
* Copyright (c) 2015 All Rights Reserved.
*/

/*
* 修订记录：
* faZheng 下午2:39:00 创建
*/
package com.yiji.ypayment.dal.repository.business;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yiji.ypayment.common.dao.jpa.EntityJpaDao;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;

/**
 *
 *
 * @author faZheng
 *
 */
public interface PaymentOrderDao extends EntityJpaDao<PaymentOrder, Long> {
	
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
	@Query(value = "select * from payment_order where payment_order_no=?1  for update", nativeQuery = true)
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
	 * 根据是否需要再次异步通知查询订单
	 * @param notifyOpenApi
	 * @return
	 */
	List<PaymentOrder> findByNotifyOpenApi(boolean notifyOpenApi);
	
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:46:27 创建
 */
package com.yiji.ypayment.dal.repository.business;

import org.springframework.data.jpa.repository.Query;

import com.yiji.ypayment.common.dao.jpa.EntityJpaDao;
import com.yiji.ypayment.dal.entity.business.RepayOrderInfo;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public interface RepayOrderInfoDao extends EntityJpaDao<RepayOrderInfo, Long> {
	
	/**
	 * 根据订单号，查询补缴订单
	 * @param paymentOrderNo
	 * @return
	 */
	RepayOrderInfo findByPaymentOrderNo(String paymentOrderNo);
	
	/**
	 * 根据订单号，查询补缴订单(锁定记录)
	 * @param paymentOrderNo
	 * @return
	 */
	@Query(value = "select * from repay_order_info where payment_order_no=?1  for update", nativeQuery = true)
	RepayOrderInfo lockByPaymentOrderNo(String paymentOrderNo);
	
	/**
	 * 根据补缴订单号，查询补缴订单(锁定记录)
	 * @param repayOrderNo
	 * @return
	 */
	@Query(value = "select * from repay_order_info where repay_Order_No=?1  for update", nativeQuery = true)
	RepayOrderInfo lockByRepayOrderNo(String repayOrderNo);
	
}

/*
* www.yiji.com Inc.
* Copyright (c) 2015 All Rights Reserved.
*/

/*
* 修订记录：
* faZheng 下午2:39:00 创建
*/
package com.yiji.ypayment.dal.repository.business;

import com.yiji.ypayment.common.dao.jpa.EntityJpaDao;
import com.yiji.ypayment.dal.entity.business.PaymentItemInfo;

/**
 *
 *
 * @author faZheng
 *
 */
public interface PaymentItemInfoDao extends EntityJpaDao<PaymentItemInfo, Long> {
	
	/**
	 * 根据订单详情号，查询缴费记录
	 * @param paymentInfoNo
	 * @return
	 */
	PaymentItemInfo findByPaymentInfoNo(String paymentInfoNo);
	
	/**
	 * 根据订单号查询缴费详情
	 * @param paymentOrderNo
	 * @return
	 */
	PaymentItemInfo findByPaymentOrderNo(String paymentOrderNo);
	
}

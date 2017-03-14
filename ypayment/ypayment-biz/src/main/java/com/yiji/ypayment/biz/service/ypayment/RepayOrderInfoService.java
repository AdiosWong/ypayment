/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午11:03:59 创建
 */
package com.yiji.ypayment.biz.service.ypayment;

import com.yiji.ypayment.common.service.EntityService;
import com.yiji.ypayment.dal.entity.business.RepayOrderInfo;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public interface RepayOrderInfoService extends EntityService<RepayOrderInfo> {
	
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
	RepayOrderInfo lockByPaymentOrderNo(String paymentOrderNo);
	
	/**
	 * 根据补缴订单号，查询补缴订单(锁定记录)
	 * @param repayOrderNo
	 * @return
	 */
	RepayOrderInfo lockByRepayOrderNo(String repayOrderNo);
	
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午6:20:40 创建
 */
package com.yiji.ypayment.biz.remote;

import com.yiji.ypayment.dal.entity.business.PaymentOrder;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public interface RepaymentService {
	
	/**
	 * 补缴
	 * 
	 * @param paymentOrderNo
	 */
	void repayment(String paymentOrderNo);
	
	/**
	 * 处理缴费异步结果
	 * 
	 * @param paymentOrder
	 */
	void HandRepaymentResult(PaymentOrder paymentOrder);
	
}

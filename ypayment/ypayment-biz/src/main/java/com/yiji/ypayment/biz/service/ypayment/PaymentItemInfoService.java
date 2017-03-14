/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author faZheng 下午5:29:20 创建
 */
package com.yiji.ypayment.biz.service.ypayment;

import com.yiji.ypayment.common.service.EntityService;
import com.yiji.ypayment.dal.entity.business.PaymentItemInfo;

/**
 * 
 * 交易服务接口
 *
 * @author faZheng
 *
 */
public interface PaymentItemInfoService extends EntityService<PaymentItemInfo> {
	
	/**
	 * 根据订单号查询缴费详情
	 * @param paymentOrderNo
	 * @return
	 */
	PaymentItemInfo findByPaymentOrderNo(String paymentOrderNo);
	
}

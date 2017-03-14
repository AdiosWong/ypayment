/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author faZheng 下午5:29:20 创建
 */
package com.yiji.ypayment.biz.service.ypayment.impl;

import org.springframework.stereotype.Service;

import com.yiji.ypayment.biz.service.ypayment.PaymentItemInfoService;
import com.yiji.ypayment.common.service.EntityServiceImpl;
import com.yiji.ypayment.dal.entity.business.PaymentItemInfo;
import com.yiji.ypayment.dal.repository.business.PaymentItemInfoDao;

@Service("paymentItemInfoService")
public class PaymentItemInfoServiceImpl extends EntityServiceImpl<PaymentItemInfo, PaymentItemInfoDao> implements
																										PaymentItemInfoService {
	
	/**
	 * @param paymentOrderNo
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.PaymentItemInfoService#findByPaymentOrderNo(java.lang.String)
	 */
	@Override
	public PaymentItemInfo findByPaymentOrderNo(String paymentOrderNo) {
		return getEntityDao().findByPaymentOrderNo(paymentOrderNo);
	}
	
}

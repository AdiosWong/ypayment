/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午11:04:09 创建
 */
package com.yiji.ypayment.biz.service.ypayment.impl;

import org.springframework.stereotype.Service;

import com.yiji.ypayment.biz.service.ypayment.RepayOrderInfoService;
import com.yiji.ypayment.common.service.EntityServiceImpl;
import com.yiji.ypayment.dal.entity.business.RepayOrderInfo;
import com.yiji.ypayment.dal.repository.business.RepayOrderInfoDao;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("repayOrderInfoService")
public class RepayOrderInfoServiceImpl extends EntityServiceImpl<RepayOrderInfo, RepayOrderInfoDao> implements
																									RepayOrderInfoService {

	/**
	 * @param paymentOrderNo
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.RepayOrderInfoService#findByPaymentOrderNo(java.lang.String)
	 */
	@Override
	public RepayOrderInfo findByPaymentOrderNo(String paymentOrderNo) {
		return getEntityDao().findByPaymentOrderNo(paymentOrderNo);
	}

	/**
	 * @param paymentOrderNo
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.RepayOrderInfoService#lockByPaymentOrderNo(java.lang.String)
	 */
	@Override
	public RepayOrderInfo lockByPaymentOrderNo(String paymentOrderNo) {
		return getEntityDao().lockByPaymentOrderNo(paymentOrderNo);
	}

	/**
	 * @param repayOrderNo
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.RepayOrderInfoService#lockByRepayOrderNo(java.lang.String)
	 */
	@Override
	public RepayOrderInfo lockByRepayOrderNo(String repayOrderNo) {
		return getEntityDao().lockByRepayOrderNo(repayOrderNo);
	}
	
}

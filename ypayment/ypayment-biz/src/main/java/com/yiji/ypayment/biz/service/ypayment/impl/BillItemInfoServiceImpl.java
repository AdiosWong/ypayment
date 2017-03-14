/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:03:30 创建
 */
package com.yiji.ypayment.biz.service.ypayment.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yiji.ypayment.biz.service.ypayment.BillItemInfoService;
import com.yiji.ypayment.common.service.EntityServiceImpl;
import com.yiji.ypayment.dal.entity.business.BillItemInfo;
import com.yiji.ypayment.dal.repository.business.BillItemInfoDao;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */

@Service("billItemInfoService")
public class BillItemInfoServiceImpl extends EntityServiceImpl<BillItemInfo, BillItemInfoDao> implements
																								BillItemInfoService {


	/**
	 * @param paymentOrderNo
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.BillItemInfoService#findByPaymentOrderNo(java.lang.String)
	 */
	@Override
	public List<BillItemInfo> findByPaymentOrderNo(String paymentOrderNo) {
		return getEntityDao().findByPaymentOrderNo(paymentOrderNo);
	}
	
}

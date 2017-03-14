/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:04:30 创建
 */
package com.yiji.ypayment.dal.repository.business;

import java.util.List;

import com.yiji.ypayment.common.dao.jpa.EntityJpaDao;
import com.yiji.ypayment.dal.entity.business.BillItemInfo;

/**
 * 缴费明细
 *
 * @author xinqing@yiji.com
 *
 */
public interface BillItemInfoDao extends EntityJpaDao<BillItemInfo, Long> {
	
	/**
	 * 根据订单号查询账单明细
	 * 
	 * @param paymentOrderNo
	 * @return
	 */
	List<BillItemInfo> findByPaymentOrderNo(String paymentOrderNo);
	
}

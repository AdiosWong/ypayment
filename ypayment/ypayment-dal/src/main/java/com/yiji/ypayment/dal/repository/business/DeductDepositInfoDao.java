/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午9:53:08 创建
 */
package com.yiji.ypayment.dal.repository.business;

import com.yiji.ypayment.common.dao.jpa.EntityJpaDao;
import com.yiji.ypayment.dal.entity.business.DeductDepositInfo;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public interface DeductDepositInfoDao extends EntityJpaDao<DeductDepositInfo, Long> {
	
	/**
	 * 根据userId查询代扣信息
	 * @param userId
	 * @return
	 */
	DeductDepositInfo findByDepositBizNo(String depositBizNo);
	
	/**
	 * 根据depositNo查询代扣信息
	 * @param depositNo
	 * @return
	 */
	DeductDepositInfo findByDepositNo(String depositNo);
	
}

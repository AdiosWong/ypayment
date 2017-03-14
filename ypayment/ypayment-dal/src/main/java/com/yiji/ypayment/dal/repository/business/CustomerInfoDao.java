/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月26日 下午2:49:38 创建
 */

package com.yiji.ypayment.dal.repository.business;

import com.yiji.ypayment.common.dao.jpa.EntityJpaDao;
import com.yiji.ypayment.dal.entity.business.CustomerInfo;

/**
 * 会员信息
 *
 * @author faZheng
 *
 */

public interface CustomerInfoDao extends EntityJpaDao<CustomerInfo, Long> {
	
	/**
	 * 根据用户查询会员信息
	 * @param userId
	 * @return
	 */
	CustomerInfo findByUserId(String userId);
	
}

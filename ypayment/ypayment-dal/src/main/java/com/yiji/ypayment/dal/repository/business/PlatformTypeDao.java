/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年11月17日 下午4:26:07 创建
 */

package com.yiji.ypayment.dal.repository.business;

import com.yiji.ypayment.common.dao.jpa.EntityJpaDao;
import com.yiji.ypayment.dal.entity.business.PlatformType;

/**
 *
 *
 * @author faZheng
 *
 */

public interface PlatformTypeDao extends EntityJpaDao<PlatformType, Long> {
	
	/**
	 * 根据平台类型，查询业务平台
	 * @param platformType
	 * @return
	 */
	PlatformType findByPlatformType(String platformType);
	
}

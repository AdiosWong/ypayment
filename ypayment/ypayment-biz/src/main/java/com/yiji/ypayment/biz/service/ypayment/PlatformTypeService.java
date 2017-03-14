/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年11月17日 下午4:28:44 创建
 */

package com.yiji.ypayment.biz.service.ypayment;

import com.yiji.ypayment.common.service.EntityService;
import com.yiji.ypayment.dal.entity.business.PlatformType;

/**
 * 业务平台
 *
 * @author faZheng
 *
 */

public interface PlatformTypeService extends EntityService<PlatformType> {
	
	/**
	 * 根据平台类型，查询业务平台
	 * @param platformType
	 * @return
	 */
	PlatformType findByPlatformType(String platformType);
	
}

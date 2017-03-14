/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月26日 下午2:51:03 创建
 */

package com.yiji.ypayment.biz.service.ypayment;

import com.yiji.ypayment.common.service.EntityService;
import com.yiji.ypayment.dal.entity.business.CustomerInfo;

/**
 * 会员信息服务
 *
 * @author faZheng
 *
 */

public interface CustomerInfoService extends EntityService<CustomerInfo> {
	
	/**
	 * 根据用户查询会员信息
	 * @param userId
	 * @return
	 */
	CustomerInfo findByUserId(String userId);

}

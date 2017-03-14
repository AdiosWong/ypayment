/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月26日 下午2:52:03 创建
 */

package com.yiji.ypayment.biz.service.ypayment.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiji.boot.securityframework.annotation.Sensitive;
import com.yiji.common.security.annotation.ReverseIndex;
import com.yiji.ypayment.biz.service.ypayment.CustomerInfoService;
import com.yiji.ypayment.common.service.EntityServiceImpl;
import com.yiji.ypayment.dal.entity.business.CustomerInfo;
import com.yiji.ypayment.dal.repository.business.CustomerInfoDao;

/**
 * 会员信息服务
 *
 * @author faZheng
 *
 */
@Service("customerInfoService")
public class CustomerInfoServiceImpl extends EntityServiceImpl<CustomerInfo, CustomerInfoDao> implements
																							CustomerInfoService {
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	@Sensitive
	public @ReverseIndex CustomerInfo findByUserId(String userId) {
		CustomerInfo entity = getEntityDao().findByUserId(userId);
		if (entity != null){
			entityManager.detach(entity);
		}
		return entity;
	}
	
}

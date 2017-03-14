/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午9:58:01 创建
 */
package com.yiji.ypayment.biz.service.ypayment.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiji.boot.securityframework.annotation.Sensitive;
import com.yiji.common.security.annotation.ReverseIndex;
import com.yiji.ypayment.biz.service.ypayment.DeductDepositInfoService;
import com.yiji.ypayment.common.service.EntityServiceImpl;
import com.yiji.ypayment.dal.entity.business.DeductDepositInfo;
import com.yiji.ypayment.dal.repository.business.DeductDepositInfoDao;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("deductDepositInfoService")
public class DeductDepositInfoServiceImpl extends EntityServiceImpl<DeductDepositInfo, DeductDepositInfoDao> implements
																										DeductDepositInfoService {
	
	@Autowired
	private EntityManager entityManager;
	
	/**
	 * @param userId
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.QuickPayInfoService#findByUserId(java.lang.String)
	 */
	@Override
	@Sensitive
	public @ReverseIndex DeductDepositInfo findByDepositBizNo(String depositBizNo) {
		DeductDepositInfo entity = getEntityDao().findByDepositBizNo(depositBizNo);
		if (entity != null){
			entityManager.detach(entity);
		}
		return entity;
	}

	/**
	 * @param depositNo
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.DeductDepositInfoService#findByDepositNo(java.lang.String)
	 */
	@Override
	@Sensitive
	public @ReverseIndex DeductDepositInfo findByDepositNo(String depositNo) {
		DeductDepositInfo entity = getEntityDao().findByDepositNo(depositNo);
		if (entity != null){
			entityManager.detach(entity);
		}
		return entity;
	}

}

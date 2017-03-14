/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月22日 下午8:15:48 创建
 */

package com.yiji.ypayment.biz.service.ypayment.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yiji.ypayment.biz.service.ypayment.PaymentBindingInfoService;
import com.yiji.ypayment.common.service.EntityServiceImpl;
import com.yiji.ypayment.dal.entity.business.PaymentBindingInfo;
import com.yiji.ypayment.dal.repository.business.PaymentBindingInfoDao;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;

/**
 *
 *
 * @author faZheng
 *
 */
@Service("paymentBindingInfoService")
public class PaymentBindingInfoServiceImpl extends EntityServiceImpl<PaymentBindingInfo, PaymentBindingInfoDao>
																												implements
																												PaymentBindingInfoService {
	
	@Override
	public PaymentBindingInfo findByContractNo(String contractNo) {
		return getEntityDao().findByContractNo(contractNo);
	}
	
	@Override
	public List<PaymentBindingInfo> findByUserIdAndPaymentTypeAndPayFrom(String userId, PaymentTypeEnum paymentType,
																			String payFrom) {
		return getEntityDao().findByUserIdAndPaymentTypeAndPayFrom(userId, paymentType, payFrom);
	}
	
	@Override
	public PaymentBindingInfo findByUserIdAndContractNo(String userId, String contractNo) {
		return getEntityDao().findByUserIdAndContractNo(userId, contractNo);
	}
	
	@Override
	public PaymentBindingInfo findByUserIdAndPayFromAndUserCodeAndResourceCode(String userId, String payFrom,
																				String userCode, String resourceCode) {
		return getEntityDao().findByUserIdAndPayFromAndUserCodeAndResourceCode(userId, payFrom, userCode, resourceCode);
	}
	
	@Override
	public List<PaymentBindingInfo> findByUserIdAndPayFromAndResourceCode(String userId, String payFrom,
																			String resourceCode) {
		return getEntityDao().findByUserIdAndPayFromAndResourceCode(userId, payFrom, resourceCode);
	}
	
}

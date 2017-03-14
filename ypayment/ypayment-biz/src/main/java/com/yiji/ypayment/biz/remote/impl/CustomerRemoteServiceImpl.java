/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月26日 下午2:52:03 创建
 */

package com.yiji.ypayment.biz.remote.impl;

import org.springframework.stereotype.Service;

import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.remote.CustomerRemoteService;
import com.yiji.ypayment.biz.remote.base.RemoteServiceBase;
import com.yiji.ypayment.dal.entity.business.CustomerInfo;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yjf.common.service.enums.customer.CertifyStatusEnum;
import com.yjf.customer.service.info.UserInfo;
import com.yjf.customer.service.order.UniformStringQueryOrder;
import com.yjf.customer.service.result.UserQueryResult;

/**
 * 会员信息服务
 * 
 * @author faZheng
 * 
 */
@Service("customerRemoteService")
public class CustomerRemoteServiceImpl extends RemoteServiceBase implements CustomerRemoteService {
	
	@Override
	public boolean checkUserCertifyStatus(UniformStringQueryOrder uniformStringQueryOrder, CertifyStatusEnum certifyStatus) {
		try {
			UserInfo userInfo = getUserInfo(uniformStringQueryOrder);
			if (userInfo == null) {
				throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "用户不存在!");
			}
			if (userInfo.getCertifyStatus() == certifyStatus) {
				return true;
			}
		} catch (Exception e) {
			logger.error("check用户实名状态失败,{}", e);
			throw new PaymentException(PaymentResultCode.UNKNOWN_EXCEPTION, "check用户实名状态失败!");
		}
		return false;
	}
	
	@Override
	public void createPaymentUser(UniformStringQueryOrder uniformStringQueryOrder) {
		CustomerInfo customerInfo = customerInfoService.findByUserId(uniformStringQueryOrder.getParam());
		if (customerInfo == null) {
			logger.info("用户第一次使用, 保存用户信息");
			UserInfo userInfo = getUserInfo(uniformStringQueryOrder);
			customerInfo = new CustomerInfo();
			customerInfo.setAddress(userInfo.getAddress());
			customerInfo.setCertNo(userInfo.getCertNo());
			customerInfo.setMobilePhoneNumber(userInfo.getPhone() != null ? userInfo.getPhone() : userInfo.getMobile());
			customerInfo.setProfession(userInfo.getProfession());
			customerInfo.setRealName(userInfo.getRealName());
			customerInfo.setStatus(userInfo.getUserStatus());
			customerInfo.setUserId(userInfo.getUserId());
			customerInfo.setUserName(userInfo.getUserName());
			customerInfo.setCustomerType(userInfo.getUserType());
			customerInfo.setCertType(userInfo.getCertType());
			logger.info("会员用户信息, customerInfo：{}", customerInfo);
			customerInfoService.save(customerInfo);
		}
	}
	
	public UserInfo getUserInfo(UniformStringQueryOrder uniformStringQueryOrder) {
		UserInfo userInfo = null;
		try {
			UserQueryResult result = customerClient.findUserInfoByUserId(uniformStringQueryOrder);
			if (!result.isSuccess() || result.getUserInfo() == null) {
				throw new PaymentException(PaymentResultCode.USER_NOT_EXIST, "易极付用户不存在!");
			}
			userInfo = result.getUserInfo();
		} catch (PaymentException e) {
			throw new PaymentException(PaymentResultCode.USER_NOT_EXIST, "易极付用户不存在!");
		} catch (Exception e) {
			logger.error("获取UserInfo失败,{}", e);
			throw new PaymentException(PaymentResultCode.UNKNOWN_EXCEPTION, "获取UserInfo失败!");
		}
		return userInfo;
	}
}

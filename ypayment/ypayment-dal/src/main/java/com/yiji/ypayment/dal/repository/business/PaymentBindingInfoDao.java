/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月22日 下午2:17:12 创建
 */

package com.yiji.ypayment.dal.repository.business;

import java.util.List;

import com.yiji.ypayment.common.dao.jpa.EntityJpaDao;
import com.yiji.ypayment.dal.entity.business.PaymentBindingInfo;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;

/**
 * 缴费签约
 *
 * @author faZheng
 *
 */

public interface PaymentBindingInfoDao extends EntityJpaDao<PaymentBindingInfo, Long> {
	
	/**
	 * 根据签约号，查询绑卡信息
	 * @param contractNo
	 * @return
	 */
	PaymentBindingInfo findByContractNo(String contractNo);
	
	/**
	 * 根据易极付会员ID、缴费类型、平台类型, 查询签约绑卡记录
	 * @param userId
	 * @param paymentType
	 * @param payFrom
	 * @return
	 */
	List<PaymentBindingInfo> findByUserIdAndPaymentTypeAndPayFrom(String userId, PaymentTypeEnum paymentType,
																	String payFrom);
	
	/**
	 * 根据易极付会员ID、平台类型、资源编码, 查询签约绑卡记录
	 * @param userId
	 * @param payFrom
	 * @param resourceCode
	 * @return
	 */
	List<PaymentBindingInfo> findByUserIdAndPayFromAndResourceCode(String userId, String payFrom, String resourceCode);
	
	/**
	 * 根据易极付会员ID、缴费签约号查询签约绑卡记录
	 * @param userId
	 * @param contractNo
	 * @return
	 */
	PaymentBindingInfo findByUserIdAndContractNo(String userId, String contractNo);
	
	/**
	 * 根据易极付会员ID、缴费类型、平台类型、缴费卡号， 查询签约绑卡记录
	 * @param userId
	 * @param paymentType
	 * @param payFrom
	 * @param userCode
	 * @return
	 */
	PaymentBindingInfo findByUserIdAndPayFromAndUserCodeAndResourceCode(String userId, String payFrom, String userCode,
																		String resourceCode);
	
}

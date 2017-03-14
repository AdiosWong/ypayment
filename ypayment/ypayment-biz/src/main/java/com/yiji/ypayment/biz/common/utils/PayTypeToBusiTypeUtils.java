/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午6:25:03 创建
 */
package com.yiji.ypayment.biz.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.common.constant.Constant;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PayTypeToBusiTypeUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(PayTypeToBusiTypeUtils.class);
	
	public static String payTypeToBusiType(PaymentTypeEnum paymentTypeEnum) {
		if (PaymentTypeEnum.GAS == paymentTypeEnum || PaymentTypeEnum.WATER == paymentTypeEnum
			|| PaymentTypeEnum.ELECTRICITY == paymentTypeEnum || PaymentTypeEnum.RUBBISH == paymentTypeEnum) {
			return Constant.PAYMENT_BUSI_TYPE_JF;
		} else if (PaymentTypeEnum.MOBILE == paymentTypeEnum) {
			return Constant.PAYMENT_BUSI_TYPE_CZ;
		} else {
			logger.info("缴费类型转换业务类型时，缴费类型未开通：paymentType={}", paymentTypeEnum);
			throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER);
		}
	}
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:42:32 创建
 */
package com.yiji.ypayment.web.front.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.service.OrderBase;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class HasUserForm extends OrderBase{

	private static final long serialVersionUID = 8562821137939084739L;
	
	/**
	 * 用户卡号
	 */
	@NotEmpty
	String userCode;
	
	/**
	 * 缴费类型
	 */
	@NotNull
	PaymentTypeEnum paymentType;
	
	/**
	 * 渠道编码
	 */
	@NotEmpty
	String resourceCode;
	
	/**
	 * 渠道名称
	 */
	@NotEmpty
	String resourceName;

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}

	public String getResourceCode() {
		return this.resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
}

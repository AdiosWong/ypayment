/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:34:07 创建
 */
package com.yiji.ypayment.facade.order.payment;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.ypayment.facade.common.PaymentOrderBase;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentBindingOrder extends PaymentOrderBase {
	
	private static final long serialVersionUID = -109412580761782656L;
	
	/**
	 * 资源代码
	 */
	@NotEmpty
	private String resourceCode;
	
	/**
	 * 资源名称
	 */
	@NotEmpty
	private String resourceName;
	
	/**
	 * 用户卡号
	 */
	@NotEmpty
	private String userCode;
	
	/**
	 * 缴费类型
	 */
	@NotNull
	private PaymentTypeEnum paymentType;
	
	
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
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
}

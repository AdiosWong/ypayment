/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:12:06 创建
 */
package com.yiji.ypayment.facade.order.query;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.ypayment.facade.common.QueryPaymentOrderBase;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentQueryOrder extends QueryPaymentOrderBase {
	
	private static final long serialVersionUID = -4595843342771557397L;
	
	/** 用户缴费代码 */
	@NotEmpty
	private String userCode;
	
	/** 请求类型 */
	@NotNull
	private PaymentTypeEnum paymentType;
	
	/** 资源代码 */
	@NotEmpty
	private String resourceCode;
	
	/**
	 * 门店号(易生活门店查询电费必传)
	 */
	private String storeId;
	
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

	public String getStoreId() {
		return this.storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

}

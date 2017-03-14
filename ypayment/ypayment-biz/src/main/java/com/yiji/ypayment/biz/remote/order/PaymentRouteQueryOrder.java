/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:27:27 创建
 */
package com.yiji.ypayment.biz.remote.order;

import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.service.OrderBase;
import com.yjf.common.util.ToString;

/**
 * 资源渠道路由查询order
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentRouteQueryOrder extends OrderBase {
	
	private static final long serialVersionUID = 2284951483491018258L;

	/**
	 * 用户卡号
	 */
	private String userCode;
	
	/**
	 * 资源代码
	 */
	private String resourceCode;
	
	/**
	 * 缴费类型
	 */
	private PaymentTypeEnum paymentType;
	
	private String systemId;
	
	private String storeId;

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getResourceCode() {
		return this.resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public String getStoreId() {
		return this.storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

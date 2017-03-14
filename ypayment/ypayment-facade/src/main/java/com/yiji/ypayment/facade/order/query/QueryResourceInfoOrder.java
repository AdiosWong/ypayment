/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:16:33 创建
 */
package com.yiji.ypayment.facade.order.query;

import javax.validation.constraints.NotNull;

import com.yiji.ypayment.facade.common.QueryPaymentOrderBase;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class QueryResourceInfoOrder extends QueryPaymentOrderBase {
	
	private static final long serialVersionUID = -6625286897280731549L;
	
	/** 请求缴费类型 */
	@NotNull
	PaymentTypeEnum paymentTypeEnum;
	
	public PaymentTypeEnum getPaymentTypeEnum() {
		return this.paymentTypeEnum;
	}
	
	public void setPaymentTypeEnum(PaymentTypeEnum paymentTypeEnum) {
		this.paymentTypeEnum = paymentTypeEnum;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
}

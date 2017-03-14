/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:24:59 创建
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
public class PaymentQueryBindingOrder extends QueryPaymentOrderBase {
	
	private static final long serialVersionUID = 8150611611466271337L;
	
	/**
	 * 缴费类型
	 */
	@NotNull
	private PaymentTypeEnum paymentType;
	
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

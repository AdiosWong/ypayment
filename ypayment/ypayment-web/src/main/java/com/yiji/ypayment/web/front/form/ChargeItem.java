/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:33:33 创建
 */
package com.yiji.ypayment.web.front.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.yjf.common.lang.util.money.Money;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class ChargeItem implements Serializable{
	
	private static final long serialVersionUID = 2906737408200339932L;

	/**
	 * 充值金额
	 */
	@NotNull
	private Money paymentAmount;
	
	/**
	 * 优惠金额
	 */
	@NotNull
	private Money payAmount;

	public Money getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(Money paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Money getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Money payAmount) {
		this.payAmount = payAmount;
	}
}

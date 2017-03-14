/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年12月10日 下午4:42:23 创建
 */
    
package com.yiji.ypayment.web.front.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.ypayment.facade.enums.PayWayEnum;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.OrderBase;
import com.yjf.common.util.ToString;

/**
 * 充值order
 *
 * @author faZheng
 *
 */

public class RechargePayForm extends OrderBase{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 手机号码
	 */
	@NotEmpty
	private String phoneNumber;
	
	/**
	 * 充值金额
	 */
	@NotNull
	private Money paymentAmount;
	
	/**
	 * 应付金额
	 */
	@NotNull
	private Money payAmount;
	
	/**
	 * 支付方式
	 */
	private PayWayEnum payWay;
	
	/**
	 * 签约
	 */
	private String pactNo;
	
	/**
	 * 支付方式+签约号
	 */
	@NotEmpty
	private String payPact;
	
	/**
	 * 支付密码
	 */
	@NotEmpty
	private String password;
	
	/**
	 * token
	 */
	@NotEmpty
	private String token;

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

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

	public PayWayEnum getPayWay() {
		return this.payWay;
	}

	public void setPayWay(PayWayEnum payWay) {
		this.payWay = payWay;
	}

	@ToString.Maskable
	public String getPactNo() {
		return this.pactNo;
	}

	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}
	
	public String getPayPact() {
		return this.payPact;
	}

	public void setPayPact(String payPact) {
		this.payPact = payPact;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@ToString.Maskable
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

    
/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:24:25 创建
 */
package com.yiji.ypayment.web.front.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.facade.enums.PayWayEnum;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.OrderBase;
import com.yjf.common.service.OrderCheckException;
import com.yjf.common.util.StringUtils;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentForm extends OrderBase{

	private static final long serialVersionUID = 8077575155114486394L;
	
	/**
	 * 缴费户号
	 */
	@NotEmpty
	private String contractNo;
	
	/**
	 * 缴费金额
	 */
	@NotNull
	private Money amount;
	
	/**
	 * 支付方式
	 */
	@NotNull
	private PayWayEnum payWay;
	
	/**
	 * 支付密码
	 */
	@NotEmpty
	private String password;
	
	/**
	 * 签约号
	 */
	private String pactNo;
	
	/**
	 * 缴费类型
	 */
	@NotNull
	private PaymentTypeEnum paymentType;
	
	/**
	 * token
	 */
	@NotEmpty
	private String token;
	
	@Override
	public void check() throws OrderCheckException {
		super.check();
		if (PayWayEnum.BY_DEPOSIT == payWay) {
			if(StringUtils.isEmpty(pactNo)){
				throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "缴费支付方式为代扣时，银行卡签约号不能为空");
			}
		}
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Money getAmount() {
		return this.amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public PayWayEnum getPayWay() {
		return this.payWay;
	}

	public void setPayWay(PayWayEnum payWay) {
		this.payWay = payWay;
	}

	@ToString.Maskable
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ToString.Maskable
	public String getPactNo() {
		return this.pactNo;
	}

	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}

	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

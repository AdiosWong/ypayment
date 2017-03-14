/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:37:44 创建
 */
package com.yiji.ypayment.web.info;

import java.util.Date;

import com.yiji.ypayment.facade.enums.PayWayEnum;
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentRecordBean {
	
	/**
	 * 易极付会员ID
	 */
	private String userId;
	
	/**
	 * 支付方式(默认余额支付)
	 */
	private PayWayEnum payWay = PayWayEnum.BY_BALANCE;
	
	/**
	 * 缴费状态(外部用)
	 */
	private PaymentItemStatusEnum paymentExtStatus;
	
	/**
	 * 用户编码（卡号）
	 */
	private String userCode;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 资源编码
	 */
	private String resourceCode;
	
	/**
	 * 资源名称
	 */
	private String resourceName;
	
	/**
	 * 缴费类型
	 */
	private PaymentTypeEnum paymentType;
	
	/**
	 * 欠费金额
	 */
	private Money payable = new Money(0);
	
	/**
	 * 年月份
	 */
	private String month;
	
	/**
	 * 商户易极付id
	 */
	private String partnerId;
	
	/**
	 * 开始时间
	 */
	private Date startTime;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public PayWayEnum getPayWay() {
		return this.payWay;
	}

	public void setPayWay(PayWayEnum payWay) {
		this.payWay = payWay;
	}

	public PaymentItemStatusEnum getPaymentExtStatus() {
		return this.paymentExtStatus;
	}

	public void setPaymentExtStatus(PaymentItemStatusEnum paymentExtStatus) {
		this.paymentExtStatus = paymentExtStatus;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}

	public Money getPayable() {
		return this.payable;
	}

	public void setPayable(Money payable) {
		this.payable = payable;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public String toString() {
		return ToString.toString(this);
	}
	
}
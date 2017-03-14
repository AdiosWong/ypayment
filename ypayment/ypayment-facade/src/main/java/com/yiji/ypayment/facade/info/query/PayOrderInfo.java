/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:02:27 创建
 */
package com.yiji.ypayment.facade.info.query;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
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
public class PayOrderInfo implements Serializable {
	
	private static final long serialVersionUID = 4861388907526616054L;
	
	/**
	 * 缴费子订单号
	 */
	private String paymentOrderNo;
	
	/**
	 * 外部订单号
	 */
	private String outBizNo;
	
	/**
	 * 缴费状态
	 */
	private PaymentItemStatusEnum paymentStatus;
	
	/**
	 * 用户编码（卡号）
	 */
	private String userCode;
	
	/**
	 * 用户姓名
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
	 * 用户地址
	 */
	private String address;
	
	/**
	 * 缴费类型
	 */
	private PaymentTypeEnum paymentType;
	
	/**
	 * 缴费金额
	 */
	private Money paymentAmount = new Money(0);
	
	/**
	 * 数量
	 */
	private String count;
	
	/**
	 * 开始数量
	 */
	private String startCount;
	
	/**
	 * 结束数量
	 */
	private String endCount;
	
	/**
	 * 缴费明细
	 */
	private List<ItemInfo> itemInfos = Lists.newArrayList();
	
	/**
	 * 滞纳金
	 */
	private Money charge;
	
	/**
	 * 月份
	 */
	private String month;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 错误信息
	 */
	private String errorMessage;
	
	/**
	 * 备注信息
	 */
	private String memo;

	public String getPaymentOrderNo() {
		return this.paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}
	
	public String getOutBizNo() {
		return this.outBizNo;
	}

	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}

	public PaymentItemStatusEnum getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(PaymentItemStatusEnum paymentStatus) {
		this.paymentStatus = paymentStatus;
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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}

	public Money getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(Money paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getCount() {
		return this.count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getStartCount() {
		return this.startCount;
	}

	public void setStartCount(String startCount) {
		this.startCount = startCount;
	}

	public String getEndCount() {
		return this.endCount;
	}

	public void setEndCount(String endCount) {
		this.endCount = endCount;
	}

	public List<ItemInfo> getItemInfos() {
		return this.itemInfos;
	}

	public void setItemInfos(List<ItemInfo> itemInfos) {
		this.itemInfos = itemInfos;
	}

	public Money getCharge() {
		return this.charge;
	}

	public void setCharge(Money charge) {
		this.charge = charge;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

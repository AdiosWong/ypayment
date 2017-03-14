/*
 * Copyright (c) 2015 All Rights Reserved.
 * www.yiji.com Inc.
 */

/*
 * 修订记录：
 * faZheng 2015年10月12日 下午15:40:10 创建
 */
package com.yiji.ypayment.dal.entity.business;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import com.yiji.ypayment.dal.entity.AbstractEntity;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.ToString;

/**
 * 缴费详情表
 * 
 * @author faZheng
 * 
 */
@Entity
@Table(name = "payment_item_info", indexes = { @Index(name = "payment_info_no_index", columnList = "payment_info_no",
		unique = true) })
public class PaymentItemInfo extends AbstractEntity {
	
	private static final long serialVersionUID = 8571893840024993787L;
	
	/**
	 * 缴费详情号
	 */
	@Column(name = "payment_info_no", nullable = false, length = 40, columnDefinition = "varchar(40) comment '缴费详情号'")
	private String paymentInfoNo;
	
	/**
	 * 缴费订单号
	 */
	@Column(name = "payment_order_no", nullable = false, length = 40, columnDefinition = "varchar(40) comment '缴费订单号'")
	private String paymentOrderNo;
	
	/**
	 * 路由流水号(合同号)
	 */
	@Column(name = "resouce_serial_no", length = 32, columnDefinition = "varchar(32) comment '路由流水号(合同号)'")
	private String resouceSerialNo;
	
	/**
	 * 缴费流水号
	 */
	@Column(name = "payment_serial_no", length = 32, columnDefinition = "varchar(32) comment '缴费流水号'")
	private String paymentSerialNo;
	
	/**
	 * 缴费明细状态
	 */
	@Column(name = "payment_info_status", length = 32, columnDefinition = "varchar(32) comment '缴费明细状态'")
	private String paymentInfoStatus;
	
	/**
	 * 缴费错误码
	 */
	@Column(name = "error_code", length = 32, columnDefinition = "varchar(32) comment '缴费错误码'")
	private String errorCode;
	
	/**
	 * 缴费错误信息
	 */
	@Column(name = "error_message", length = 1024, columnDefinition = "varchar(1024) comment '缴费错误码信息'")
	private String errorMessage;
	
	/**
	 * 缴费类型
	 */
	@Column(name = "payment_type", length = 32, columnDefinition = "varchar(32) comment '缴费类型'")
	@Enumerated(EnumType.STRING)
	private PaymentTypeEnum paymentType;
	
	/**
	 * 用户卡号
	 */
	@Column(name = "user_code", length = 32, columnDefinition = "varchar(32) comment '用户卡号'")
	private String userCode;
	
	/**
	 * 用户名称
	 */
	@Column(name = "user_name", length = 32, columnDefinition = "varchar(32) comment '用户名'")
	private String userName;
	
	/**
	 * 渠道编码
	 */
	@Column(name = "channel_code", length = 32, columnDefinition = "varchar(32) comment '渠道编码'")
	private String channelCode;
	
	/**
	 * 资源编码
	 */
	@Column(name = "resource_code", length = 32, columnDefinition = "varchar(32) comment '资源编码'")
	private String resourceCode;
	
	/**
	 * 资源名称
	 */
	@Column(name = "resource_name", length = 32, columnDefinition = "varchar(32) comment '资源名称'")
	private String resourceName;
	
	/**
	 * 地址
	 */
	@Column(name = "address", length = 256, columnDefinition = "varchar(256) comment '地址'")
	private String address;
	
	/**
	 * 缴费金额
	 */
	@Column(name = "payment_amount", columnDefinition = "decimal(17,0) DEFAULT 0 comment '缴费金额'")
	private Money paymentAmount = new Money(0);
	
	/**
	 * 滞纳金金额
	 */
	@Column(name = "charge_amount", columnDefinition = "decimal(17,0) DEFAULT 0 comment '滞纳金金额'")
	private Money chargeAmount = new Money(0);
	
	/**
	 * 返利金额
	 */
	@Column(name = "share_amount", columnDefinition = "decimal(17,0) DEFAULT 0 comment '返利金额'")
	private Money shareAmount = new Money(0);
	
	/**
	 * 月份
	 */
	@Column(name = "month", length = 20, columnDefinition = "varchar(20) comment '月份'")
	private String month;
	
	/**
	 * 使用量
	 */
	@Column(name = "quantity", length = 10, columnDefinition = "varchar(10) comment '使用量'")
	private String quantity;
	
	/**
	 * 起度
	 */
	@Column(name = "start_count", length = 10, columnDefinition = "varchar(10) comment '起度'")
	private String startCount;
	
	/**
	 * 止度
	 */
	@Column(name = "end_count", length = 10, columnDefinition = "varchar(10) comment '止度'")
	private String endCount;
	
	/**
	 * 是否垃圾费
	 */
	@Column(name = "is_rubbish", length = 10, columnDefinition = "char(1) comment '是否垃圾费'")
	private boolean rubbish = false;
	
	/**
	 * 开始时间
	 */
	@Column(name = "start_time", columnDefinition = "TIMESTAMP COMMENT '开始时间'")
	private Date startTime = new Date();
	
	/**
	 * 结束时间
	 */
	@Column(name = "end_time", columnDefinition = "TIMESTAMP COMMENT '开始时间'")
	private Date endTime = new Date();
	
	/**
	 * 备注
	 */
	@Column(name = "memo", length = 1024, columnDefinition = "varchar(1024) comment '备注'")
	private String memo;
	
	public String getPaymentInfoNo() {
		return paymentInfoNo;
	}
	
	public void setPaymentInfoNo(String paymentInfoNo) {
		this.paymentInfoNo = paymentInfoNo;
	}
	
	public String getPaymentOrderNo() {
		return this.paymentOrderNo;
	}
	
	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}
	
	public String getResouceSerialNo() {
		return this.resouceSerialNo;
	}
	
	public void setResouceSerialNo(String resouceSerialNo) {
		this.resouceSerialNo = resouceSerialNo;
	}
	
	public String getPaymentSerialNo() {
		return paymentSerialNo;
	}
	
	public void setPaymentSerialNo(String paymentSerialNo) {
		this.paymentSerialNo = paymentSerialNo;
	}
	
	public String getPaymentInfoStatus() {
		return this.paymentInfoStatus;
	}
	
	public void setPaymentInfoStatus(String paymentInfoStatus) {
		this.paymentInfoStatus = paymentInfoStatus;
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}
	
	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getChannelCode() {
		return channelCode;
	}
	
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getResourceCode() {
		return resourceCode;
	}
	
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	
	public String getResourceName() {
		return resourceName;
	}
	
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Money getPaymentAmount() {
		return paymentAmount;
	}
	
	public void setPaymentAmount(Money paymentAmount) {
		if (paymentAmount == null) {
			this.paymentAmount = new Money(0);
		} else {
			this.paymentAmount = paymentAmount;
		}
	}
	
	public Money getChargeAmount() {
		return chargeAmount;
	}
	
	public void setChargeAmount(Money chargeAmount) {
		if (chargeAmount == null) {
			this.chargeAmount = new Money(0);
		} else {
			this.chargeAmount = chargeAmount;
		}
	}
	
	public Money getShareAmount() {
		return shareAmount;
	}
	
	public void setShareAmount(Money shareAmount) {
		if (shareAmount == null) {
			this.shareAmount = new Money(0);
		} else {
			this.shareAmount = shareAmount;
		}
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getMonth() {
		return month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public String getStartCount() {
		return startCount;
	}
	
	public void setStartCount(String startCount) {
		this.startCount = startCount;
	}
	
	public String getEndCount() {
		return endCount;
	}
	
	public void setEndCount(String endCount) {
		this.endCount = endCount;
	}
	
	public boolean isRubbish() {
		return this.rubbish;
	}
	
	public void setRubbish(boolean rubbish) {
		this.rubbish = rubbish;
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

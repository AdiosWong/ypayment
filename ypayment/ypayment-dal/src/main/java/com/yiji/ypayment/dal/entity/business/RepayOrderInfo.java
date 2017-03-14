/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:07:17 创建
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
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 补缴订单信息
 *
 * @author xinqing@yiji.com
 *
 */
@Entity
@Table(name = "repay_order_info", indexes = { @Index(name = "payment_order_no_index", columnList = "payment_order_no",
		unique = true) })
public class RepayOrderInfo extends AbstractEntity {

	private static final long serialVersionUID = 6407391287484581539L;
	
	/**
	 * 缴费订单号
	 */
	@Column(name = "payment_order_no", nullable = false, length = 40, columnDefinition = "varchar(40) comment '缴费订单号'")
	private String paymentOrderNo;
	
	/**
	 * 补缴订单号
	 */
	@Column(name = "repay_order_no", length = 40, columnDefinition = "varchar(40) comment '补缴订单号'")
	private String repayOrderNo;
	
	/**
	 * 易极付会员ID
	 */
	@Column(name = "user_id", length = 32, columnDefinition = "varchar(32) comment '易极付会员ID'")
	private String userId;
	
	/**
	 * 商户易极付ID
	 */
	@Column(name = "partner_id", length = 32, columnDefinition = "varchar(32) comment '商户易极付ID'")
	private String partnerId;
	
	/**
	 * 缴费来源
	 */
	@Column(name = "pay_from", length = 48, columnDefinition = "varchar(48) comment '缴费来源'")
	private String payFrom;
	
	/**
	 * 缴费类型
	 */
	@Column(name = "payment_type", length = 20, columnDefinition = "varchar(20) comment '缴费类型'")
	@Enumerated(EnumType.STRING)
	private PaymentTypeEnum paymentType;
	
	/**
	 * 缴费状态
	 */
	@Column(name = "payment_status", length = 32, columnDefinition = "varchar(32) comment '缴费状态'")
	@Enumerated(EnumType.STRING)
	private PaymentItemStatusEnum paymentStatus;
	
	/**
	 * 中信缴费状态
	 */
	@Column(name = "zhongxin_status", length = 32, columnDefinition = "varchar(32) comment '中信缴费状态'")
	@Enumerated(EnumType.STRING)
	private PaymentItemStatusEnum zhongxinStatus;
	
	/**
	 * 缴费金额
	 */
	@Column(name = "payment_amount", columnDefinition = "decimal(17,0) DEFAULT 0 comment '缴费金额'")
	private Money paymentAmount;
	
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

	public String getPaymentOrderNo() {
		return this.paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}
	
	public String getRepayOrderNo() {
		return this.repayOrderNo;
	}

	public void setRepayOrderNo(String repayOrderNo) {
		this.repayOrderNo = repayOrderNo;
	}


	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPayFrom() {
		return this.payFrom;
	}

	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
	}

	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}

	public PaymentItemStatusEnum getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(PaymentItemStatusEnum paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public PaymentItemStatusEnum getZhongxinStatus() {
		return this.zhongxinStatus;
	}

	public void setZhongxinStatus(PaymentItemStatusEnum zhongxinStatus) {
		this.zhongxinStatus = zhongxinStatus;
	}

	public Money getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(Money paymentAmount) {
		this.paymentAmount = paymentAmount;
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
	
}

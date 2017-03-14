/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月12日 下午15:53:20 创建
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
import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.facade.enums.PayWayEnum;
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.ToString;

/**
 * 缴费订单表
 * 
 * @author faZheng
 * 
 */
@Entity
@Table(name = "payment_order", indexes = { @Index(name = "payment_order_no_index", columnList = "payment_order_no",
		unique = true) })
public class PaymentOrder extends AbstractEntity {
	
	private static final long serialVersionUID = 8866059159338790725L;
	
	/**
	 * 缴费订单号
	 */
	@Column(name = "payment_order_no", nullable = false, length = 40, columnDefinition = "varchar(40) comment '缴费订单号'")
	private String paymentOrderNo;
	
	/**
	 * 外部订单号
	 */
	@Column(name = "out_biz_no", nullable = false, length = 40, columnDefinition = "varchar(40) comment '外部订单号'")
	private String outBizNo;
	
	/**
	 * 易极付会员ID
	 */
	@Column(name = "user_id", length = 32, columnDefinition = "varchar(32) comment '易极付会员ID'")
	private String userId;
	
	/**
	 * 支付方式(默认余额支付)
	 */
	@Column(name = "pay_way", length = 40, columnDefinition = "varchar(40) comment '支付方式'")
	@Enumerated(EnumType.STRING)
	private PayWayEnum payWay = PayWayEnum.BY_BALANCE;
	
	/**
	 * 缴费状态(外部用)
	 */
	@Column(name = "payment_ext_status", length = 32, columnDefinition = "varchar(32) comment '缴费状态(外部用)'")
	@Enumerated(EnumType.STRING)
	private PaymentItemStatusEnum paymentExtStatus;
	
	/**
	 * 缴费状态
	 */
	@Column(name = "payment_status", length = 32, columnDefinition = "varchar(32) comment '缴费状态'")
	@Enumerated(EnumType.STRING)
	private PaymentItemStatusEnum paymentStatus;
	
	/**
	 * 交易转账状态
	 */
	@Column(name = "trade_status", length = 40, columnDefinition = "varchar(40) comment '交易状态'")
	@Enumerated(EnumType.STRING)
	private TransferTradeStatusEnum tradeStatus;
	
	/**
	 * 用户编码（卡号）
	 */
	@Column(name = "user_code", length = 32, columnDefinition = "varchar(32) comment '用户卡号'")
	private String userCode;
	
	/**
	 * 用户名
	 */
	@Column(name = "user_name", length = 48, columnDefinition = "varchar(48) comment '用户名'")
	private String userName;
	
	/**
	 * 资源编码
	 */
	@Column(name = "resource_code", length = 32, columnDefinition = "varchar(32) comment '资源编码'")
	private String resourceCode;
	
	/**
	 * 资源名称
	 */
	@Column(name = "resource_name", length = 128, columnDefinition = "varchar(128) comment '资源名称'")
	private String resourceName;
	
	/**
	 * 缴费类型
	 */
	@Column(name = "payment_type", length = 20, columnDefinition = "varchar(20) comment '缴费类型'")
	@Enumerated(EnumType.STRING)
	private PaymentTypeEnum paymentType;
	
	/**
	 * 欠费金额
	 */
	@Column(name = "payable", columnDefinition = "decimal(17,0) DEFAULT 0 comment '缴费金额'")
	private Money payable = new Money(0);
	
	/**
	 * 缴费金额
	 */
	@Column(name = "payment_amount", columnDefinition = "decimal(17,0) DEFAULT 0 comment '缴费金额'")
	private Money paymentAmount;
	
	/**
	 * 渠道编码
	 */
	@Column(name = "channel_code", length = 20, columnDefinition = "varchar(20) comment '渠道编码'")
	private String channelCode;
	
	/**
	 * 年月份
	 */
	@Column(name = "month", length = 20, columnDefinition = "varchar(20) comment '年月份'")
	private String month;
	
	/**
	 * 缴费账户
	 */
	@Column(name = "pay_account", length = 32, columnDefinition = "varchar(32) comment '缴费账户'")
	private String payAccount;
	
	/**
	 * 缴费来源
	 */
	@Column(name = "pay_from", length = 48, columnDefinition = "varchar(48) comment '缴费来源'")
	private String payFrom;
	
	/**
	 * 商户易极付id
	 */
	@Column(name = "partner_id", length = 32, columnDefinition = "varchar(32) comment '商户易极付ID'")
	private String partnerId;
	
	/**
	 * 商户交易订单号
	 */
	@Column(name = "merch_order_no", length = 40, columnDefinition = "varchar(40) comment '商户交易订单号'")
	private String merchOrderNo;
	
	/**
	 * 是否有优惠
	 */
	@Column(name = "is_favourable", length = 32, columnDefinition = "varchar(20) comment '是否有优惠'")
	@Enumerated(EnumType.STRING)
	private FavourableEnum favourable;
	
	/**
	 * 是否需要异步通知
	 */
	@Column(name = "is_notify_openapi", length = 10, columnDefinition = "char(1) comment '是否需要异步通知'")
	private boolean notifyOpenApi = false;
	
	/**
	 * 入口编码
	 */
	@Column(name = "inlet", length = 8, columnDefinition = "varchar(8) comment '入口编码'")
	private String inlet;
	
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
	@Column(name = "memo", length = 128, columnDefinition = "varchar(128) comment '备注'")
	private String memo;
	
	/**
	 * gid
	 */
	@Column(name = "gid", length = 40, columnDefinition = "varchar(40) comment 'gid'")
	private String gid;
	
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

	public PaymentItemStatusEnum getPaymentStatus() {
		return this.paymentStatus;
	}
	
	public void setPaymentStatus(PaymentItemStatusEnum paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	public String getUserCode() {
		return userCode;
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
	
	public TransferTradeStatusEnum getTradeStatus() {
		return this.tradeStatus;
	}
	
	public void setTradeStatus(TransferTradeStatusEnum tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}
	
	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getMonth() {
		return this.month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getPayAccount() {
		return this.payAccount;
	}
	
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}
	
	public boolean isRubbish() {
		return this.rubbish;
	}
	
	public void setRubbish(boolean rubbish) {
		this.rubbish = rubbish;
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
	
	public Money getPayable() {
		return this.payable;
	}
	
	public void setPayable(Money payable) {
		if (payable == null) {
			this.payable = new Money(0);
		} else {
			this.payable = payable;
		}
	}
	
	public Money getPaymentAmount() {
		return this.paymentAmount;
	}
	
	public void setPaymentAmount(Money paymentAmount) {
		if (paymentAmount == null) {
			this.paymentAmount = new Money(0);
		} else {
			this.paymentAmount = paymentAmount;
		}
	}
	
	public String getChannelCode() {
		return channelCode;
	}
	
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getPayFrom() {
		return payFrom;
	}
	
	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
	}
	
	public String getInlet() {
		return this.inlet;
	}

	public void setInlet(String inlet) {
		this.inlet = inlet;
	}

	public String getMemo() {
		return this.memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getGid() {
		return this.gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public FavourableEnum getFavourable() {
		return this.favourable;
	}

	public void setFavourable(FavourableEnum favourable) {
		this.favourable = favourable;
	}
	
	public String getMerchOrderNo() {
		return this.merchOrderNo;
	}
	
	public void setMerchOrderNo(String merchOrderNo) {
		this.merchOrderNo = merchOrderNo;
	}
	
	public boolean isNotifyOpenApi() {
		return this.notifyOpenApi;
	}

	public void setNotifyOpenApi(boolean notifyOpenApi) {
		this.notifyOpenApi = notifyOpenApi;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
}

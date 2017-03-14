/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年11月09日 下午17:53:20 创建
 */
package com.yiji.ypayment.dal.entity.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import com.yiji.ypayment.dal.entity.AbstractEntity;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.enums.UndoApproachEnum;
import com.yiji.ypayment.facade.enums.UndoPaymentStatusEnum;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.ToString;

/**
 * 返销订单表
 * 
 * @author faZheng
 * 
 */
@Entity
@Table(name = "undo_payment", indexes = { @Index(name = "undo_payment_no_index", columnList = "undo_payment_no",
		unique = true) })
public class UndoPayment extends AbstractEntity {
	
	private static final long serialVersionUID = -4289417213231203454L;
	
	/**
	 * 返销订单号
	 */
	@Column(name = "undo_payment_no", nullable = false, length = 40, columnDefinition = "varchar(40) comment '返销订单号'")
	private String undoPaymentNo;
	
	/**
	 * 缴费订单号
	 */
	@Column(name = "payment_order_no", nullable = false, length = 40, columnDefinition = "varchar(40) comment '缴费订单号'")
	private String paymentOrderNo;
	
	/**
	 * 资源路由流水号
	 */
	@Column(name = "route_serial_number", length = 32, columnDefinition = "varchar(32) comment '资源路由流水号'")
	private String routeSerialNumber;
	
	/**
	 * 易极付会员ID
	 */
	@Column(name = "user_id", length = 32, columnDefinition = "varchar(32) comment '易极付会员ID'")
	private String userId;
	
	/**
	 * 缴费类型
	 */
	@Column(name = "payment_type", length = 20, columnDefinition = "varchar(20) comment '缴费类型'")
	@Enumerated(EnumType.STRING)
	private PaymentTypeEnum paymentType;
	
	/**
	 * 缴费账户
	 */
	@Column(name = "pay_account", length = 32, columnDefinition = "varchar(32) comment '缴费账户'")
	private String payAccount;
	
	/**
	 * 返销状态
	 */
	@Column(name = "undo_status", length = 32, columnDefinition = "varchar(32) comment '返销状态'")
	@Enumerated(EnumType.STRING)
	private UndoPaymentStatusEnum undoStatus;
	
	/**
	 * 交易转账状态
	 */
	@Column(name = "trade_status", length = 40, columnDefinition = "varchar(40) comment '交易状态'")
	@Enumerated(EnumType.STRING)
	private TransferTradeStatusEnum tradeStatus;
	
	/**
	 * 返销类型
	 */
	@Column(name = "undo_approach", length = 32, columnDefinition = "varchar(32) comment '返销类型'")
	@Enumerated(EnumType.STRING)
	private UndoApproachEnum undoApproach;
	
	/**
	 * 缴费来源
	 */
	@Column(name = "pay_from", length = 48, columnDefinition = "varchar(48) comment '缴费来源'")
	private String payFrom;
	
	/**
	 * 入口编码
	 */
	@Column(name = "inlet", length = 8, columnDefinition = "varchar(8) comment '入口编码'")
	private String inlet;
	
	/**
	 * 是否需要异步通知
	 */
	@Column(name = "is_notify_openapi", length = 10, columnDefinition = "char(1) comment '是否需要异步通知'")
	private boolean notifyOpenApi = false;
	
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
	 * 返销金额
	 */
	@Column(name = "undo_amount", columnDefinition = "decimal(17,0) DEFAULT 0 comment '返销金额'")
	private Money undoAmount;
	
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
	
	public String getUndoPaymentNo() {
		return this.undoPaymentNo;
	}
	
	public void setUndoPaymentNo(String undoPaymentNo) {
		this.undoPaymentNo = undoPaymentNo;
	}
	
	public String getPaymentOrderNo() {
		return this.paymentOrderNo;
	}
	
	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}
	
	public String getRouteSerialNumber() {
		return this.routeSerialNumber;
	}
	
	public void setRouteSerialNumber(String routeSerialNumber) {
		this.routeSerialNumber = routeSerialNumber;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}
	
	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getPayAccount() {
		return this.payAccount;
	}
	
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}
	
	public UndoPaymentStatusEnum getUndoStatus() {
		return this.undoStatus;
	}
	
	public void setUndoStatus(UndoPaymentStatusEnum undoStatus) {
		this.undoStatus = undoStatus;
	}
	
	public TransferTradeStatusEnum getTradeStatus() {
		return this.tradeStatus;
	}
	
	public void setTradeStatus(TransferTradeStatusEnum tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	public UndoApproachEnum getUndoApproach() {
		return this.undoApproach;
	}
	
	public void setUndoApproach(UndoApproachEnum undoApproach) {
		this.undoApproach = undoApproach;
	}
	
	public String getPayFrom() {
		return this.payFrom;
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
	
	public boolean isNotifyOpenApi() {
		return this.notifyOpenApi;
	}

	public void setNotifyOpenApi(boolean notifyOpenApi) {
		this.notifyOpenApi = notifyOpenApi;
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
	
	public Money getUndoAmount() {
		return this.undoAmount;
	}
	
	public void setUndoAmount(Money undoAmount) {
		this.undoAmount = undoAmount;
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

	public String getMerchOrderNo() {
		return this.merchOrderNo;
	}

	public void setMerchOrderNo(String merchOrderNo) {
		this.merchOrderNo = merchOrderNo;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
}

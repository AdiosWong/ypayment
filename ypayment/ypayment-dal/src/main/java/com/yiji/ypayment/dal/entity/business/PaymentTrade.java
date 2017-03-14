/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月12日 下午17:45:10 创建
 */
package com.yiji.ypayment.dal.entity.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import com.yiji.ypayment.dal.entity.AbstractEntity;
import com.yiji.ypayment.dal.enums.TradeTypeEnum;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.ToString;

/**
 * 交易转账表
 * 
 * @author faZheng
 * 
 */
@Entity
@Table(name = "payment_trade", indexes = { @Index(name = "trade_order_no_index", columnList = "biz_order_no",
		unique = true) })
public class PaymentTrade extends AbstractEntity {
	
	private static final long serialVersionUID = 2324576322675498034L;
	
	/**
	 * 转账业务订单号
	 */
	@Column(name = "biz_order_no", length = 40, columnDefinition = "varchar(40) comment '转账业务订单号'")
	private String bizOrderNo;
	
	/**
	 * 缴费订单号
	 */
	@Column(name = "ref_biz_order_no", length = 40, columnDefinition = "varchar(40) comment '缴费订单号'")
	private String refBizOrderNo;
	
	/**
	 * 交易金额
	 */
	@Column(name = "amount", columnDefinition = "decimal(17,0) DEFAULT 0 comment '交易金额'")
	private Money amount;
	
	/**
	 * 交易状态
	 */
	@Column(name = "trade_status", length = 40, columnDefinition = "varchar(40) comment '交易状态'")
	@Enumerated(EnumType.STRING)
	private TransferTradeStatusEnum tradeStatus;
	
	/**
	 * 付款用户id
	 */
	@Column(name = "payer_user_id", length = 40, columnDefinition = "varchar(40) comment '付款用户id'")
	private String payerUserId;
	
	/**
	 * 付款用户卡号
	 */
	@Column(name = "payer_card_no", length = 40, columnDefinition = "varchar(40) comment '付款用户卡号'")
	private String payerCardNo;
	
	/**
	 * 付款用户账户(易极付)
	 */
	@Column(name = "payer_acct_no", length = 40, columnDefinition = "varchar(40) comment '付款用户账户(易极付)'")
	private String payerAcctNo;
	
	/**
	 * 缴费类型
	 */
	@Column(name = "payment_type", length = 16, nullable = false,
			columnDefinition = "varchar(16) not null comment '缴费类型'")
	@Enumerated(EnumType.STRING)
	private PaymentTypeEnum paymentType;
	
	/**
	 * 转账类型(缴费、返销)
	 */
	@Column(name = "trade_type", length = 16, nullable = false,
			columnDefinition = "varchar(16) not null comment '转账类型'")
	@Enumerated(EnumType.STRING)
	private TradeTypeEnum tradeType;
	
	/**
	 * 收款用户id
	 */
	@Column(name = "payee_user_id", length = 40, columnDefinition = "varchar(40) comment '收款用户id'")
	private String payeeUserId;
	
	/**
	 * 收款用户卡号
	 */
	@Column(name = "payee_card_no", length = 40, columnDefinition = "varchar(40) comment '收款用户卡号'")
	private String payeeCardNo;
	
	/**
	 * 收款用户账户(易极付)
	 */
	@Column(name = "payee_acct_no", length = 40, columnDefinition = "varchar(40) comment '收款用户基金账户(易极付)'")
	private String payeeAcctNo;
	
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
	 * 入口编码
	 */
	@Column(name = "inlet", length = 8, columnDefinition = "varchar(8) comment '入口编码'")
	private String inlet;
	
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
	
	public String getGid() {
		return gid;
	}
	
	public void setGid(String gid) {
		this.gid = gid;
	}
	
	public String getPayerUserId() {
		return payerUserId;
	}
	
	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}
	
	public String getPayerCardNo() {
		return payerCardNo;
	}
	
	public void setPayerCardNo(String payerCardNo) {
		this.payerCardNo = payerCardNo;
	}
	
	public String getPayerAcctNo() {
		return payerAcctNo;
	}
	
	public void setPayerAcctNo(String payerAcctNo) {
		this.payerAcctNo = payerAcctNo;
	}
	
	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}
	
	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public TradeTypeEnum getTradeType() {
		return this.tradeType;
	}
	
	public void setTradeType(TradeTypeEnum tradeType) {
		this.tradeType = tradeType;
	}
	
	public String getPayeeUserId() {
		return payeeUserId;
	}
	
	public void setPayeeUserId(String payeeUserId) {
		this.payeeUserId = payeeUserId;
	}
	
	public String getPayeeCardNo() {
		return payeeCardNo;
	}
	
	public void setPayeeCardNo(String payeeCardNo) {
		this.payeeCardNo = payeeCardNo;
	}
	
	public String getPayeeAcctNo() {
		return payeeAcctNo;
	}
	
	public void setPayeeAcctNo(String payeeAcctNo) {
		this.payeeAcctNo = payeeAcctNo;
	}
	
	public TransferTradeStatusEnum getTradeStatus() {
		return this.tradeStatus;
	}
	
	public void setTradeStatus(TransferTradeStatusEnum tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getBizOrderNo() {
		return bizOrderNo;
	}
	
	public void setBizOrderNo(String bizOrderNo) {
		this.bizOrderNo = bizOrderNo;
	}
	
	public String getRefBizOrderNo() {
		return refBizOrderNo;
	}
	
	public void setRefBizOrderNo(String refBizOrderNo) {
		this.refBizOrderNo = refBizOrderNo;
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

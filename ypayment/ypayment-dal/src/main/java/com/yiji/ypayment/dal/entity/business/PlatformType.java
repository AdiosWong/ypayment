/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月17日 下午17:45:10 创建
 */
package com.yiji.ypayment.dal.entity.business;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.yiji.ypayment.dal.entity.AbstractEntity;
import com.yiji.ypayment.dal.enums.AccessTypeEnum;
import com.yiji.ypayment.facade.enums.PaymentValidStatus;
import com.yjf.common.util.ToString;

/**
 * 业务平台
 * 
 * @author faZheng
 * 
 */
@Entity
@Table(name = "platform_type", indexes = { @Index(name = "platform_type_index", columnList = "platform_type",
		unique = true) })
public class PlatformType extends AbstractEntity {
	
	private static final long serialVersionUID = 177389710647826271L;
	
	/**
	 * 用户所属平台类型
	 */
	@Column(name = "platform_type", length = 64, columnDefinition = "varchar(64) comment '平台类型'")
	private String platformType;
	
	/**
	 * 用户所属平台名称
	 */
	@Column(name = "platform_name", length = 128, columnDefinition = "varchar(128) comment '平台名称'")
	private String platformName;
	
	/**
	 * 状态:{有效, 无效}
	 */
	@Column(name = "status", length = 20, columnDefinition = "varchar(20) comment '状态'")
	@Enumerated(EnumType.STRING)
	private PaymentValidStatus status;
	
	/**
	 * 数字签名key
	 */
	@Column(name = "signature_key", length = 128, columnDefinition = "varchar(128) comment '数字签名key'")
	private String signatureKey;
	
	/**
	 * 商户ID
	 */
	@Column(name = "partner_id", length = 64, columnDefinition = "varchar(64) comment '商户ID'")
	private String partnerId;
	
	/**
	 * 交易收费码
	 */
	@Column(name = "trade_product_code", length = 128, columnDefinition = "varchar(128) comment '交易收费码'")
	private String tradeBizProductCode;
	
	/**
	 * 接入方式
	 */
	@Column(name = "access_type", length = 20, columnDefinition = "varchar(20) comment '接入方式'")
	@Enumerated(EnumType.STRING)
	private AccessTypeEnum accessType;
	
	/**
	 * 是否需要异步通知
	 */
	@Column(name = "is_notify_openapi", length = 10, columnDefinition = "char(1) comment '是否需要异步通知'")
	private boolean notifyOpenApi = false;
	
	/**
	 * 是否短信通知
	 */
	@Column(name = "is_sms", length = 10, columnDefinition = "char(1) comment '是否短信通知 '")
	private boolean sms = true;
	
	/**
	 * 是否支持代扣
	 */
	@Column(name = "is_deposit", length = 10, columnDefinition = "char(1) comment '是否支持代扣 '")
	private boolean deposit = false;
	
	/**
	 * 平台拥有的缴费类型，用"|"分隔
	 */
	@Column(name = "payment_types", length = 128, columnDefinition = "varchar(128) comment '平台拥有的缴费类型'")
	private String paymentTypes;
	
	/**
	 * 扩展字段
	 */
	@Column(name = "ext_field", length = 128, columnDefinition = "varchar(128) comment '扩展字段'")
	private String extField;
	
	/**
	 * 备注
	 */
	@Column(name = "memo", length = 128, columnDefinition = "varchar(128) comment '备注'")
	private String memo;
	
	public String getPlatformType() {
		return this.platformType;
	}
	
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	
	public String getPlatformName() {
		return this.platformName;
	}
	
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	
	public PaymentValidStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(PaymentValidStatus status) {
		this.status = status;
	}
	
	public String getSignatureKey() {
		return this.signatureKey;
	}
	
	public void setSignatureKey(String signatureKey) {
		this.signatureKey = signatureKey;
	}
	
	public String getPartnerId() {
		return this.partnerId;
	}
	
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	public String getTradeBizProductCode() {
		return this.tradeBizProductCode;
	}
	
	public void setTradeBizProductCode(String tradeBizProductCode) {
		this.tradeBizProductCode = tradeBizProductCode;
	}
	
	public boolean isSms() {
		return this.sms;
	}
	
	public void setSms(boolean sms) {
		this.sms = sms;
	}
	
	public boolean isDeposit() {
		return this.deposit;
	}

	public void setDeposit(boolean deposit) {
		this.deposit = deposit;
	}

	public List<String> getPaymentTypeList() {
		List<String> paymentTypeList = Lists.newArrayList();
		if (StringUtils.isNotBlank(this.paymentTypes)) {
			String[] paymentTypes = this.paymentTypes.split("\\|");
			paymentTypeList = Arrays.asList(paymentTypes);
		}
		return paymentTypeList;
	}
	
	public String getPaymentTypes() {
		return this.paymentTypes;
	}
	
	public void setPaymentTypes(String paymentTypes) {
		this.paymentTypes = paymentTypes;
	}
	
	public String getExtField() {
		return this.extField;
	}
	
	public void setExtField(String extField) {
		this.extField = extField;
	}
	
	public String getMemo() {
		return this.memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public AccessTypeEnum getAccessType() {
		return this.accessType;
	}

	public void setAccessType(AccessTypeEnum accessType) {
		this.accessType = accessType;
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

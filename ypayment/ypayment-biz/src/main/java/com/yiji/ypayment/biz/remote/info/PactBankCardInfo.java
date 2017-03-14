/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午1:52:17 创建
 */
package com.yiji.ypayment.biz.remote.info;

import com.yiji.ypayment.dal.enums.DebitCreditEnum;
import com.yjf.common.lang.enums.CertTypeEnum;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PactBankCardInfo {
	
	/**
	 * 卡号
	 */
	private String cardNo;
	
	/** 
	 * 签约号
	 */
	private String pactNo;
	
	/**
	 * 持卡人姓名
	 */
	private String cardName;
	
	/**
	 * 卡类型，DEBIT 借记卡 CREDIT 贷记卡
	 */
	private DebitCreditEnum cardType;
	
	/**
	 * 省份
	 */
	private String province;
	
	/**
	 * 市
	 */
	private String city;
	
	/**
	 * 证件号
	 */
	private String certNo;
	
	/**
	 * 件类型证
	 */
	private CertTypeEnum certType;
	
	/**
	 * 银行名称
	 */
	private String bankName;
	
	/**
	 * 银行简称
	 */
	private String bankCode;
	
	/** 
	 * 商户的用户ID 
	 */
	private String partnerId;
	
	public String getCardNo() {
		return this.cardNo;
	}
	
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getPactNo() {
		return this.pactNo;
	}

	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}
	
	public String getCardName() {
		return this.cardName;
	}
	
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public DebitCreditEnum getCardType() {
		return this.cardType;
	}
	
	public void setCardType(DebitCreditEnum cardType) {
		this.cardType = cardType;
	}
	
	public String getProvince() {
		return this.province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCertNo() {
		return this.certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	public CertTypeEnum getCertType() {
		return this.certType;
	}
	
	public void setCertType(CertTypeEnum certType) {
		this.certType = certType;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBankCode() {
		return this.bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
}

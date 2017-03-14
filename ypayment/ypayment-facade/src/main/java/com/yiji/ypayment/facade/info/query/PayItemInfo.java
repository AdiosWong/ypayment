/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午9:56:55 创建
 */
package com.yiji.ypayment.facade.info.query;

import java.io.Serializable;

import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PayItemInfo implements Serializable {
	
	private static final long serialVersionUID = 1645279970925857770L;
	
	/** 返回的流水号 */
	private String serialNumber;
	
	/** 合同号 */
	private String contractNumber;
	
	/** 业务名称 */
	private String name;
	
	/** 数量 */
	private String count;
	
	/** 应付款 */
	private Money payables;
	
	/** 滞纳金 */
	private Money charge;
	
	/** 类型 */
	private PaymentTypeEnum paymentType;
	
	/** 年月 */
	private String date;
	
	public String getSerialNumber() {
		return this.serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getContractNumber() {
		return this.contractNumber;
	}
	
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCount() {
		return this.count;
	}
	
	public void setCount(String count) {
		this.count = count;
	}
	
	public Money getPayables() {
		return this.payables;
	}
	
	public void setPayables(Money payables) {
		this.payables = payables;
	}
	
	public Money getCharge() {
		return this.charge;
	}
	
	public void setCharge(Money charge) {
		this.charge = charge;
	}
	
	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}
	
	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:12:50 创建
 */
package com.yiji.ypayment.facade.info.query;

import java.io.Serializable;

import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PayBindingInfo implements Serializable {
	
	private static final long serialVersionUID = -880116022895564139L;
	
	/**
	 * 易极付用户ID
	 */
	private String userId;
	
	/**
	 * 缴费签约号
	 */
	private String contractNo;
	
	/**
	 * 资源代码
	 */
	private String resourceCode;
	
	/**
	 * 资源名称
	 */
	private String resourceName;
	
	/**
	 * 用户卡号
	 */
	private String userCode;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 缴费类型
	 */
	private PaymentTypeEnum paymentType;
	
	/**
	 * 缴费来源
	 */
	private String payFrom;
	
	/**
	 * 备注
	 */
	private String memo;
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
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
	
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}
	
	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getPayFrom() {
		return this.payFrom;
	}
	
	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
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

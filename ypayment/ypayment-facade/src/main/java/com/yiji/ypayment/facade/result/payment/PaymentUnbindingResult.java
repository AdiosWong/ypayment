/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:05:08 创建
 */
package com.yiji.ypayment.facade.result.payment;

import com.yiji.ypayment.facade.common.PaymentResultBase;
import com.yiji.ypayment.facade.enums.PaymentValidStatus;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentUnbindingResult extends PaymentResultBase {
	
	private static final long serialVersionUID = 7587212030898748201L;
	
	/**
	 * 易极付用户ID
	 */
	private String userId;
	
	/**
	 * 缴费签约号
	 */
	private String contractNo;
	
	/**
	 * 用户卡号
	 */
	private String userCode;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 状态
	 */
	private PaymentValidStatus bindingStatus;
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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
	
	public PaymentValidStatus getBindingStatus() {
		return this.bindingStatus;
	}
	
	public void setBindingStatus(PaymentValidStatus bindingStatus) {
		this.bindingStatus = bindingStatus;
	}
	
}

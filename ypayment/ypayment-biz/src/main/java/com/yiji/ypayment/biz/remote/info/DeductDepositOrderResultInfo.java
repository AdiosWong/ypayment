/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午11:29:57 创建
 */
package com.yiji.ypayment.biz.remote.info;

import com.yjf.common.lang.util.money.Money;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class DeductDepositOrderResultInfo {
	/**
	 * 服务器端交易号
	 */
	private String tradeNo;
	
	/**
	 * 付款用户ID
	 */
	private String userId;
	
	/**
	 * 签约编号
	 */
	private String subscribeCode;
	
	/**
	 * 支付金额
	 */
	private Money amount;
	
	/**
	 * @return
	 */
	public String getTradeNo() {
		return this.tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getSubscribeCode() {
		return this.subscribeCode;
	}
	
	public void setSubscribeCode(String subscribeCode) {
		this.subscribeCode = subscribeCode;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
}

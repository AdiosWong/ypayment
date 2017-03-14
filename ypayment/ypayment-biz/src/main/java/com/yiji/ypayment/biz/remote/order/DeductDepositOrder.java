/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午11:29:41 创建
 */
package com.yiji.ypayment.biz.remote.order;

import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.OrderBase;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class DeductDepositOrder extends OrderBase {
	
	private static final long serialVersionUID = 940670863929907313L;

	/**
	 * 充值账户(会员)
	 */
	private String userId;
	
	/**
	 * 交易发生金额
	 */
	private Money amount;
	
	/**
	 * 外部订单号
	 */
	private String outBizNo;
	
	/**
	 * 系统入口编码
	 */
	protected String inlet;
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getOutBizNo() {
		return this.outBizNo;
	}
	
	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}
	
	public String getInlet() {
		return this.inlet;
	}
	
	public void setInlet(String inlet) {
		this.inlet = inlet;
	}
	
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:08:25 创建
 */
package com.yiji.ypayment.facade.order.query;

import java.util.Date;

import com.yiji.ypayment.facade.common.QueryPageOrderBase;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class QueryPayOrderInfoOrder extends QueryPageOrderBase {
	
	private static final long serialVersionUID = 1665725845600910048L;
	
	/**
	 * 外部订单号(可为空，不为空查出具体的那一笔订单)
	 */
	private String outBizNo;
	
	/**
	 * 子订单号(可为空，不为空查出具体的那一笔子订单)
	 */
	private String paymentOrderNo;
	
	/**
	 * 缴费类型(可为空，不为空查出具体的那一类)
	 */
	private PaymentTypeEnum paymentType;
	
	/**
	 * 开始时间(可为空)
	 */
	private Date startTime;
	
	/**
	 * 结束时间(可为空)
	 */
	private Date endTime;
	
	public String getOutBizNo() {
		return this.outBizNo;
	}
	
	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}
	
	public String getPaymentOrderNo() {
		return this.paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}
	
	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}
	
	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
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

}

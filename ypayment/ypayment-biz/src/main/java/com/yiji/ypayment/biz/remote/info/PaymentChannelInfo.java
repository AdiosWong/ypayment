/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:32:29 创建
 */
package com.yiji.ypayment.biz.remote.info;

import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentChannelInfo {
	/** 渠道编码 */
	private String channelCode;
	
	/** 渠道名称 */
	private String channelName;
	
	/** 缴费类型 */
	private String paymentType;
	
	/** 资源厂商代码（产品用） */
	private String instCode;
	
	/** 资源厂商名称 */
	private String instName;
	
	/** 易极付用户ID **/
	private String userId;
	
	/** 状态 */
	private String status;
	
	public String getChannelCode() {
		return this.channelCode;
	}
	
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getChannelName() {
		return this.channelName;
	}
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public String getPaymentType() {
		return this.paymentType;
	}
	
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getInstCode() {
		return this.instCode;
	}
	
	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}
	
	public String getInstName() {
		return this.instName;
	}
	
	public void setInstName(String instName) {
		this.instName = instName;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
}

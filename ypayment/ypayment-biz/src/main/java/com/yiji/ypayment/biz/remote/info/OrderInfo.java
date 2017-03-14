/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:13:22 创建
 */
package com.yiji.ypayment.biz.remote.info;


/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class OrderInfo {
	
	private String gid;
	
	/**
	 * 易极为商户分配的卡号(cardNo), 必填
	 */
	private String partnerId;
	
	/**
	 * 外部商户请求的交易订单号，必填
	 */
	private String merchOrderNo;

	public String getGid() {
		return this.gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
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
	
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:31:23 创建
 */
package com.yiji.ypayment.biz.remote.info;

import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class ResourceInstInfo {
	/** 缴费类型 */
	private String paymentType;
	
	/** 资源代码（产品用） */
	private String instCode;
	
	/** 资源名称 */
	private String instName;
	
	/** 省名称 */
	private String provinceName;
	
	/** 市名称 */
	private String cityName;
	
	/** 是否全国性 */
	private boolean isNational = false;
	
	/** 是否开放 */
	private boolean isOpen = false;
	
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
	
	public String getProvinceName() {
		return this.provinceName;
	}
	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getCityName() {
		return this.cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public boolean isNational() {
		return this.isNational;
	}
	
	public void setNational(boolean isNational) {
		this.isNational = isNational;
	}
	
	public boolean isOpen() {
		return this.isOpen;
	}
	
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:03:44 创建
 */
package com.yiji.ypayment.facade.info.query;

import java.io.Serializable;

import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class ResourceInfo implements Serializable {
	
	private static final long serialVersionUID = 1983794658166516321L;
	
	/** 资源代码（产品用） */
	private String resourceCode;
	
	/** 资源名称 */
	private String resourceName;
	
	/** 省名称 */
	private String provinceName;
	
	/** 市名称 */
	private String cityName;
	
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
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:38:32 创建
 */
package com.yiji.ypayment.web.info;

import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class ResourceBean {
	
	/** 
	 * 资源代码（产品用）
	 */
	private String instCode;
	
	/** 
	 * 资源名称 
	 */
	private String instName;
	
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
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
}


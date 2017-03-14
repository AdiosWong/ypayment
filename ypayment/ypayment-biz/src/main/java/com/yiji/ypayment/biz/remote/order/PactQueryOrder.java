/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午11:30:12 创建
 */
package com.yiji.ypayment.biz.remote.order;

import com.yjf.common.service.OrderBase;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PactQueryOrder extends OrderBase {
	
	private static final long serialVersionUID = -5175154628136106350L;
	
	/**
	 * 易极付用户ID
	 */
	private String userId;

	public String getUserId() {
		return this.userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午9:53:02 创建
 */
package com.yiji.ypayment.facade.result.query;

import java.util.List;

import com.google.common.collect.Lists;
import com.yiji.ypayment.facade.common.QueryPaymentResultBase;
import com.yiji.ypayment.facade.info.query.PayItemInfo;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentQueryResult extends QueryPaymentResultBase {
	
	private static final long serialVersionUID = 851711474084514109L;
	
	/** 机构编码 */
	private String resourceCode;
	
	/** 用户编码 */
	private String userCode;
	
	/** 用户姓名 */
	private String username;
	
	/** 用户地址 */
	private String address;
	
	/** 欠费总额 */
	private Money totalPayables = new Money(0);
	
	/** 查询结果项目 */
	private List<PayItemInfo> items = Lists.newArrayList();
	
	public String getResourceCode() {
		return this.resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	
	public String getUserCode() {
		return this.userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Money getTotalPayables() {
		return this.totalPayables;
	}
	
	public void setTotalPayables(Money totalPayables) {
		this.totalPayables = totalPayables;
	}
	
	public List<PayItemInfo> getItems() {
		return this.items;
	}
	
	public void setItems(List<PayItemInfo> items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

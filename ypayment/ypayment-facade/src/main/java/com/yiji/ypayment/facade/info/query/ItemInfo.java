/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午1:54:34 创建
 */
package com.yiji.ypayment.facade.info.query;

import java.io.Serializable;

import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class ItemInfo implements Serializable {
	
	private static final long serialVersionUID = 3010383431865370074L;
	
	/** 业务名称 */
	private String name;
	
	/** 数量 */
	private String count;
	
	/** 金额 */
	private Money money;
	
	/** 滞纳金 */
	private Money charge;
	
	/** 单价 */
	private String price;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCount() {
		return this.count;
	}
	
	public void setCount(String count) {
		this.count = count;
	}
	
	public Money getMoney() {
		return this.money;
	}
	
	public void setMoney(Money money) {
		this.money = money;
	}
	
	public Money getCharge() {
		return this.charge;
	}
	
	public void setCharge(Money charge) {
		this.charge = charge;
	}
	
	public String getPrice() {
		return this.price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
}

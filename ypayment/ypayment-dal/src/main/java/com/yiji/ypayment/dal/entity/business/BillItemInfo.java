/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:38:10 创建
 */
package com.yiji.ypayment.dal.entity.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.yiji.ypayment.dal.entity.AbstractEntity;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.ToString;

/**
 * 缴费清单明细表
 *
 * @author xinqing@yiji.com
 *
 */
@Entity
@Table(name = "bill_item_info", indexes = { @Index(name = "bill_item_info_no_index", columnList = "bill_item_info_no",
		unique = true) })
public class BillItemInfo extends AbstractEntity {
	
	private static final long serialVersionUID = -559932741299756167L;
	
	/**
	 * 缴费清单明细号
	 */
	@Column(name = "bill_item_info_no", nullable = false, length = 40, columnDefinition = "varchar(40) comment '缴费清单明细号'")
	private String billItemInfoNo;
	
	/**
	 * 缴费订单号
	 */
	@Column(name = "payment_order_no", nullable = false, length = 40, columnDefinition = "varchar(40) comment '缴费订单号'")
	private String paymentOrderNo;
	
	/**
	 * 业务名称
	 */
	@Column(name = "name", length = 32, columnDefinition = "varchar(32) comment '业务名称'")
	private String name;
	/**
	 * 数量
	 */
	@Column(name = "count", length = 10, columnDefinition = "varchar(10) comment '使用量'")
	private String count;
	
	/**
	 * 金额
	 */
	@Column(name = "money", columnDefinition = "decimal(17,0) DEFAULT 0 comment '金额'")
	private Money money;
	
	/**
	 * 滞纳金
	 */
	@Column(name = "charge", columnDefinition = "decimal(17,0) DEFAULT 0 comment '滞纳金'")
	private Money charge;
	
	/**
	 * 单价
	 */
	@Column(name = "price", length = 10, columnDefinition = "varchar(10) comment '单价'")
	private String price;

	public String getBillItemInfoNo() {
		return this.billItemInfoNo;
	}

	public void setBillItemInfoNo(String billItemInfoNo) {
		this.billItemInfoNo = billItemInfoNo;
	}

	public String getPaymentOrderNo() {
		return this.paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

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

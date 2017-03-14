/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:17:36 创建
 */
package com.yiji.ypayment.facade.order.query;

import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.ypayment.facade.common.QueryPaymentOrderBase;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentOrderStatusOrder extends QueryPaymentOrderBase {
	
	private static final long serialVersionUID = 8124746751735425628L;
	
	/**
	 * 返销订单号
	 */
	@NotEmpty
	private String undoPaymentNo;

	public String getUndoPaymentNo() {
		return this.undoPaymentNo;
	}

	public void setUndoPaymentNo(String undoPaymentNo) {
		this.undoPaymentNo = undoPaymentNo;
	}

}

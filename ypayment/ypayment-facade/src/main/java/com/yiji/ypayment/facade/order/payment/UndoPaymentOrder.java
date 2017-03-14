/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:41:37 创建
 */
package com.yiji.ypayment.facade.order.payment;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.ypayment.facade.common.PaymentOrderBase;
import com.yiji.ypayment.facade.enums.UndoApproachEnum;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class UndoPaymentOrder extends PaymentOrderBase {
	
	private static final long serialVersionUID = -2011369765467997832L;
	
	/**
	 * 缴费订单号
	 */
	@NotEmpty
	@Length(max = 40)
	private String undoPaymentNo;
	
	/**
	 * 缴费订单号
	 */
	@NotEmpty
	@Length(max = 40)
	private String paymentOrderNo;
	
	/** 返销途径 */
	@NotNull
	private UndoApproachEnum undoApproach;
	
	public String getUndoPaymentNo() {
		return this.undoPaymentNo;
	}

	public void setUndoPaymentNo(String undoPaymentNo) {
		this.undoPaymentNo = undoPaymentNo;
	}
	
	public String getPaymentOrderNo() {
		return this.paymentOrderNo;
	}
	
	public void setPaymentOrderNo(String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}
	
	public UndoApproachEnum getUndoApproach() {
		return this.undoApproach;
	}
	
	public void setUndoApproach(UndoApproachEnum undoApproach) {
		this.undoApproach = undoApproach;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

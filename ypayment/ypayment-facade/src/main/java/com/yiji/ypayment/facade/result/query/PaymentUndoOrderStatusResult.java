/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:40:27 创建
 */
package com.yiji.ypayment.facade.result.query;

import com.yiji.ypayment.facade.common.QueryPaymentResultBase;
import com.yiji.ypayment.facade.enums.UndoPaymentStatusEnum;
import com.yjf.common.lang.util.money.Money;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentUndoOrderStatusResult extends QueryPaymentResultBase {
	
	private static final long serialVersionUID = -3617133659327458881L;
	
	/**
	 * 返销状态
	 */
	private UndoPaymentStatusEnum undoOrderStatus;
	
	/**
	 * 返销金额
	 */
	private Money undoAmount;

	public UndoPaymentStatusEnum getUndoOrderStatus() {
		return this.undoOrderStatus;
	}

	public void setUndoOrderStatus(UndoPaymentStatusEnum undoOrderStatus) {
		this.undoOrderStatus = undoOrderStatus;
	}

	public Money getUndoAmount() {
		return this.undoAmount;
	}

	public void setUndoAmount(Money undoAmount) {
		this.undoAmount = undoAmount;
	}
	
}

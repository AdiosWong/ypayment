/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:51:17 创建
 */
package com.yiji.ypayment.facade.result.payment;

import com.yiji.ypayment.facade.common.PaymentResultBase;
import com.yiji.ypayment.facade.enums.UndoPaymentStatusEnum;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentUndoOrderResult extends PaymentResultBase {
	
	private static final long serialVersionUID = 605281877127468253L;
	
	private UndoPaymentStatusEnum undoOrderStatus;
	
	public UndoPaymentStatusEnum getUndoOrderStatus() {
		return this.undoOrderStatus;
	}
	
	public void setUndoOrderStatus(UndoPaymentStatusEnum undoOrderStatus) {
		this.undoOrderStatus = undoOrderStatus;
	}
	
}

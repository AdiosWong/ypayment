/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author faZheng 下午6:15:53 创建
 */
package com.yiji.ypayment.biz.remote;

import com.yiji.ypayment.biz.remote.info.PaymentResultInfo;
import com.yiji.ypayment.biz.remote.order.ApplyPaymentForm;
import com.yiji.ypayment.biz.remote.order.DepositPaymentOrder;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.facade.info.query.PayOrderInfo;
import com.yiji.ypayment.facade.order.payment.UndoPaymentOrder;
import com.yiji.ypayment.facade.result.payment.PaymentUndoOrderResult;

/**
 *
 *
 * @author faZheng
 *
 */
public interface PaymentRemoteService {
	
	/**
	 * 缴费
	 * @param applyPaymentOrder
	 */
	public PaymentResultInfo payment(ApplyPaymentForm applyPaymentForm);
	
	/**
	 * 调用路由缴费
	 * @param paymentOrder
	 * @return
	 */
	public PaymentResultInfo depositPayment(DepositPaymentOrder paymentOrder, PaymentResultInfo resultInfo);
	
	/**
	 * 返销
	 * @param orderSerialNumber 返销流水号
	 * @param originalOrderSerialNumber 原缴费流水号
	 * @param undoApproach 返销途径
	 * @return 返销结果
	 */
	PaymentUndoOrderResult undoPayment(UndoPaymentOrder undoPaymentOrder);
	
	/**
	 * 构造缴费结果
	 * 
	 * @param paymentOrderNo
	 * @return
	 */
	PayOrderInfo bulidPayOrderInfo(PaymentOrder paymentOrder);
	
}

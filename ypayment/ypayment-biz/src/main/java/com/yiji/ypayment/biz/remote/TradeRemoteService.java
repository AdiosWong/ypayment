/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author faZheng 下午3:22:20 创建
 */
package com.yiji.ypayment.biz.remote;

import java.util.List;

import com.yiji.ypayment.biz.enums.TransferTradeMode;
import com.yiji.ypayment.biz.remote.info.OrderInfo;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.entity.business.PaymentTrade;
import com.yiji.ypayment.dal.entity.business.UndoPayment;
import com.yjf.common.lang.result.Status;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 资金解冻、转账服务接口
 *
 * @author faZheng
 *
 */
public interface TradeRemoteService {
	
	/**
	 * 资金转账
	 * 
	 * @param transferTrade
	 * @param tradeMode
	 * @return
	 */
	Status transferTrade(PaymentTrade paymentTrade, TransferTradeMode tradeMode);
	
	/**
	 * 资金批量转账（事物一致性）
	 * 
	 * @param paymentTrades
	 * @param tradeMode
	 * @return
	 */
	Status batchTransfer(List<PaymentTrade> paymentTrades, TransferTradeMode tradeMode);
	
	/**
	 * 资金冻结
	 * 
	 * @param gid
	 * @param userId
	 * @param amount
	 * @return
	 */
	boolean freeze(OrderInfo orderInfo, String userId, Money amount);
	
	/**
	 * 资金解冻
	 * 
	 * @param gid
	 * @param userId
	 * @param amount
	 * @return
	 */
	boolean unfreeze(OrderInfo orderInfo, String userId, Money amount);
	
	/**
	 * 根据订单，解冻用户订单金额
	 * 
	 * @param paymentOrder
	 */
	void unfreezeAccount(PaymentOrder paymentOrder);
	
	/**
	 * 根据订单，资金转账
	 * 
	 * @param paymentOrder
	 */
	void transferTrade(PaymentOrder paymentOrder);
	
	/**
	 * 根据返销订单，资金转账
	 * 
	 * @param undoPayment
	 */
	void transferTrade(UndoPayment undoPayment);
	
	/**
	 * 根据缴费订单号，资金转账
	 * 
	 * @param undoPayment
	 */
	void transferTradePayment(String paymentOrderNo);
	
	/**
	 * 根据返销订单号，资金转账
	 * 
	 * @param undoPaymentNo
	 */
	void transferTradeUndoPayment(String undoPaymentNo);
	
}

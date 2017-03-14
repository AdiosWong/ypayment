/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved
 */

/*
 * 修订记录:
 * faZheng 2015-10-15 17:16 创建
 *
 */
package com.yiji.ypayment.integration.client;

import com.yjf.trade.product.transfer.order.BatchTransferOrder;
import com.yjf.trade.product.transfer.order.SingleTransferOrder;
import com.yjf.trade.service.result.TradeResult;

/**
 * 交易转账
 * 
 * @author faZheng
 * 
 */
public interface TradeClient {
	
	/**
	 * 单笔站内转账
	 * 
	 * @param order
	 * @return
	 */
	public TradeResult transferWarp(SingleTransferOrder order);
	
	/**
	 * 批量转账（保证事务一致性）
	 * 
	 * @param order
	 * @param operationContext
	 * @return
	 */
	public TradeResult batchTransfer(BatchTransferOrder order);
	
}

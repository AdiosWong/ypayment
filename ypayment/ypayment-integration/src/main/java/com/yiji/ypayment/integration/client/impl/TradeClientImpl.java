/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved
 */

/*
 * 修订记录:
 * faZheng 2015-10-15 17:16 创建
 *
 */
package com.yiji.ypayment.integration.client.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiji.ypayment.integration.client.TradeClient;
import com.yiji.ypayment.integration.client.impl.caller.CallerServiceBase;
import com.yjf.trade.product.transfer.api.TradeTransferService;
import com.yjf.trade.product.transfer.order.BatchTransferOrder;
import com.yjf.trade.product.transfer.order.SingleTransferOrder;
import com.yjf.trade.service.result.TradeResult;

/**
 * 
 * @author faZheng
 * 
 */
@Service("tradeClient")
public class TradeClientImpl extends CallerServiceBase implements TradeClient {
	
	@Reference(version = "1.5")
	private TradeTransferService tradeTransferServiceRemote;
	
	@Override
	public TradeResult transferWarp(SingleTransferOrder order) {
		logger.info("单笔站内转账包装入参:{}", order);
		TradeResult result = tradeTransferServiceRemote.transferWarp(order, OPERATION_CONTEXT);
		logger.info("单笔站内转账包装出参:{}", result);
		return result;
	}
	
	@Override
	public TradeResult batchTransfer(BatchTransferOrder order) {
		logger.info("批量转账（保证事务一致性）入参:{}", order);
		TradeResult result = tradeTransferServiceRemote.batchTransfer(order, OPERATION_CONTEXT);
		logger.info("批量转账（保证事务一致性）出参:{}", result);
		return result;
	}
	
}

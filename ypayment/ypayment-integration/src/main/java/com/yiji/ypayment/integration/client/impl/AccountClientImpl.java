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
import com.yiji.ypayment.integration.client.AccountClient;
import com.yiji.ypayment.integration.client.impl.caller.CallerServiceBase;
import com.yjf.accounttrans.service.api.AccountBatchFreezeService;
import com.yjf.accounttrans.service.api.AccountFreezeService;
import com.yjf.accounttrans.service.api.AccountManageService;
import com.yjf.accounttrans.service.api.query.QueryAccountService;
import com.yjf.accounttrans.service.api.query.QueryFreezeLogService;
import com.yjf.accounttrans.service.order.freeze.FreezeBatchOrder;
import com.yjf.accounttrans.service.order.freeze.FreezeOrder;
import com.yjf.accounttrans.service.result.AccountBatchFreezeResult;
import com.yjf.accounttrans.service.result.AccountTransResult;

/**
 * 
 * @author faZheng
 * 
 */
@Service("accountClient")
public class AccountClientImpl extends CallerServiceBase implements AccountClient {
	
	@Reference(version = "1.5")
	private AccountManageService accountManageServiceRemote;
	@Reference(version = "1.5")
	private QueryAccountService queryAccountServiceRemote;
	@Reference(version = "1.5")
	private QueryFreezeLogService queryFreezeLogServiceRemote;
	@Reference(version = "1.5")
	private AccountBatchFreezeService accountBatchFreezeServiceRemote;
	@Reference(version = "1.5")
	private AccountFreezeService accountFreezeServiceRemote;
	
	@Override
	public AccountTransResult freeze(FreezeOrder order) {
		logger.info("资金冻结申请入参:{}", order);
		AccountTransResult result = accountFreezeServiceRemote.freeze(order, OPERATION_CONTEXT);
		logger.info("资金冻结申请出参:{}", result);
		return result;
	}
	
	@Override
	public AccountTransResult unfreeze(FreezeOrder order) {
		logger.info("资金解冻申请入参:{}", order);
		AccountTransResult result = accountFreezeServiceRemote.unfreeze(order, OPERATION_CONTEXT);
		logger.info("资金解冻申请出参:{}", result);
		return result;
	}
	
	@Override
	public AccountBatchFreezeResult batchFreeze(FreezeBatchOrder order) {
		logger.info("批量资金冻结申请入参:{}", order);
		AccountBatchFreezeResult result = accountBatchFreezeServiceRemote.freeze(order, OPERATION_CONTEXT);
		logger.info("批量资金冻结申请出参:{}", result);
		return result;
	}
	
	@Override
	public AccountBatchFreezeResult batchUnfreeze(FreezeBatchOrder order) {
		logger.info("批量资金解冻申请入参:{}", order);
		AccountBatchFreezeResult result = accountBatchFreezeServiceRemote.unfreeze(order, OPERATION_CONTEXT);
		logger.info("批量资金解冻申请出参:{}", result);
		return result;
	}
	
}

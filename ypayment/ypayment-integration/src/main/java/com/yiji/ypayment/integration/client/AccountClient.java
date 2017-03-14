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

import com.yjf.accounttrans.service.order.freeze.FreezeBatchOrder;
import com.yjf.accounttrans.service.order.freeze.FreezeOrder;
import com.yjf.accounttrans.service.result.AccountBatchFreezeResult;
import com.yjf.accounttrans.service.result.AccountTransResult;

/**
 * @author faZheng
 */
public interface AccountClient {
	
	/**
	 * 资金冻结
	 * 
	 * @param order 资金冻结申请
	 * @return
	 */
	AccountTransResult freeze(FreezeOrder order);
	
	/**
	 * 资金解冻
	 * 
	 * @param order 资金解冻申请
	 * @return
	 */
	AccountTransResult unfreeze(FreezeOrder order);
	
	/**
	 * 冻结
	 * 
	 * @param order
	 * @return
	 */
	AccountBatchFreezeResult batchFreeze(FreezeBatchOrder order);
	
	/**
	 * 解冻
	 * 
	 * @param order
	 * @return
	 */
	AccountBatchFreezeResult batchUnfreeze(FreezeBatchOrder order);
	
}

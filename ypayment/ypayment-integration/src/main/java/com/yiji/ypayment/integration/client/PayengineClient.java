/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:55:09 创建
 */
package com.yiji.ypayment.integration.client;

import com.yjf.payengine.deposit.service.order.ApplyDepositDeductOrder;
import com.yjf.payengine.deposit.service.result.DepositResult;

/**
 * 代扣充值
 *
 * @author xinqing@yiji.com
 *
 */
public interface PayengineClient {
	
	/**
	 * 统一代扣接口
	 * 
	 * <li>支持同步和异步
	 * <li>支付走的是同步还是异步由返回结果中result.getErrCodeCtx.getCode给出</li>
	 * <pre><blockquote>
	 * result.getErrCodeCtx.getCode = PayengineResultEnum.EXECUTE_SUCCESS.getCode()  同步 
	 * result.getErrCodeCtx.getCode = PayengineResultEnum.BIZ_PROCESSING.getCode()  异步或者同步转异步
	 * </blockquote></pre>
	 * 
	 * @param order
	 * @param operationContext
	 * @return
	 */
	DepositResult applyUniteDeductDeposit(ApplyDepositDeductOrder order);
	
}

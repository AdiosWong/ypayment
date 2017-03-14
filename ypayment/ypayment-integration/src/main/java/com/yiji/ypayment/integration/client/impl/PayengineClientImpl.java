/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:55:21 创建
 */
package com.yiji.ypayment.integration.client.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiji.ypayment.integration.client.PayengineClient;
import com.yiji.ypayment.integration.client.impl.caller.CallerServiceBase;
import com.yjf.payengine.deposit.service.api.ApplyDeductDepositService;
import com.yjf.payengine.deposit.service.order.ApplyDepositDeductOrder;
import com.yjf.payengine.deposit.service.result.DepositResult;

/**
*
*
* @author xinqing@yiji.com
*
*/
@Service("payengineClient")
public class PayengineClientImpl extends CallerServiceBase implements PayengineClient {
	
	@Reference(version = "1.5")
	private ApplyDeductDepositService applyDeductDepositService;
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.integration.client.ApplyDeductDepositClient#applyUniteDeductDeposit(com.yjf.payengine.deposit.service.order.ApplyDepositDeductOrder)
	 */
	@Override
	public DepositResult applyUniteDeductDeposit(ApplyDepositDeductOrder order) {
		logger.info("统一代扣入参:{}", order);
		DepositResult result = applyDeductDepositService.applyUniteDeductDeposit(order, OPERATION_CONTEXT);
		logger.info("统一代扣出参:{}", result);
		return result;
	}
	
}

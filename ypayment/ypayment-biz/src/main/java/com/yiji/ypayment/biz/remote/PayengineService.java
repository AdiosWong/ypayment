/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:03:39 创建
 */
package com.yiji.ypayment.biz.remote;

import com.yiji.ypayment.biz.remote.info.PactBankCardInfo;
import com.yiji.ypayment.biz.remote.order.DeductDepositOrder;
import com.yiji.ypayment.dal.enums.DepositStatusEnum;

/**
 * 代扣服务
 *
 * @author xinqing@yiji.com
 *
 */
public interface PayengineService {
	
	/**
	 * 统一代扣
	 * @param deductDepositOrder
	 * @param pactBankCardInfo
	 * @return
	 */
	public DepositStatusEnum deductDeposit(DeductDepositOrder deductDepositOrder, PactBankCardInfo pactBankCardInfo);
	
}

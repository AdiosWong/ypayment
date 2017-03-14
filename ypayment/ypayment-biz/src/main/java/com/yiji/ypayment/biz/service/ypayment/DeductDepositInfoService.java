/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午9:57:47 创建
 */
package com.yiji.ypayment.biz.service.ypayment;

import com.yiji.ypayment.common.service.EntityService;
import com.yiji.ypayment.dal.entity.business.DeductDepositInfo;

/**
 * 代扣信息
 *
 * @author xinqing@yiji.com
 *
 */

public interface DeductDepositInfoService extends EntityService<DeductDepositInfo> {
	
	/**
	 * 根据代扣订单号查询代扣信息
	 * @param depositBizNo
	 * @return
	 */
	DeductDepositInfo findByDepositBizNo(String depositBizNo);
	
	/**
	 * 根据代扣支付流水查询代扣信息
	 * @param depositNo
	 * @return
	 */
	DeductDepositInfo findByDepositNo(String depositNo);
	
}

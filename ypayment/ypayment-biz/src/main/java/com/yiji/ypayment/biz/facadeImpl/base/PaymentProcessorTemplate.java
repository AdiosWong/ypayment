/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2015-01-19 14:23 创建
 *
 */
package com.yiji.ypayment.biz.facadeImpl.base;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yjf.common.lang.result.StandardResultInfo;
import com.yjf.common.service.OrderBase;

/**
 * @author tyongfu@yiji.com
 */
public interface PaymentProcessorTemplate<O extends OrderBase, R extends StandardResultInfo> {
	
	/**
	 * 动作
	 * @return
	 */
	PaymentInstructionAction getAction();
	
	/**
	 * 初始化结果
	 * @param order
	 * @return
	 */
	R initResult(O order);
	
	/**
	 * 执行业务
	 * @param order
	 * @param result
	 */
	void execute(O order, R result);
	
}

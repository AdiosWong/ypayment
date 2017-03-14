/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:31:13 创建
 */
package com.yiji.ypayment.biz.remote;

/**
 * 定时任务
 *
 * @author xinqing@yiji.com
 *
 */
public interface TimerTaskService {
	
	/**
	 * 更新订单表状态为挂起的订单，解冻状态为失败的订单
	 */
	void updatePaymentOrderStatus();
	
	/**
	 * 将处理中的转账再一次转账
	 */
	void transferProcessoringTrade();
	
	/**
	 * 更新返销表状态为挂起的订单，
	 */
	void updateUndoPaymentStatus();
	
	/**
	 * 更新异步通知openapi失败的订单
	 */
	void updateApiAsyncNotify();
	
	/**
	 * 中信对账
	 */
	void checkZhongXinStatus(String dateStr);
	
}

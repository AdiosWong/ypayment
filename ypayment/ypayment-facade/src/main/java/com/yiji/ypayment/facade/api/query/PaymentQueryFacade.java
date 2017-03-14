/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午11:14:50 创建
 */
package com.yiji.ypayment.facade.api.query;

import com.yiji.ypayment.facade.order.query.PaymentOrderStatusOrder;
import com.yiji.ypayment.facade.order.query.PaymentQueryOrder;
import com.yiji.ypayment.facade.order.query.QueryPayOrderInfoOrder;
import com.yiji.ypayment.facade.order.query.QueryResourceInfoOrder;
import com.yiji.ypayment.facade.result.query.PaymentCheckUserResult;
import com.yiji.ypayment.facade.result.query.PaymentOrderInfoResult;
import com.yiji.ypayment.facade.result.query.PaymentQueryResult;
import com.yiji.ypayment.facade.result.query.PaymentUndoOrderStatusResult;
import com.yiji.ypayment.facade.result.query.ResourceInfoResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public interface PaymentQueryFacade {
	
	/**
	 * 查询资源列表
	 * 
	 * @param paymentTypeEnum
	 * @return
	 */
	ResourceInfoResult queryResourceInfo(QueryResourceInfoOrder queryResourceInfoOrder);
	
	/**
	 * 查询应支付费用
	 * 
	 * @param queryOrder
	 * @return
	 */
	PaymentQueryResult findShouldPayCosts(PaymentQueryOrder queryOrder);
	
	/**
	 * 判定缴费用户是否存在
	 * 
	 * @param queryOrder
	 * @return
	 */
	PaymentCheckUserResult checkPaymentUser(PaymentQueryOrder queryOrder);
	
	/**
	 * 查询返销订单状态
	 * 
	 * @param userId
	 * @return
	 */
	PaymentUndoOrderStatusResult findUndoOrderStatus(PaymentOrderStatusOrder statusOrder);
	
	/**
	 * 查询订单信息
	 * 
	 * @param queryPayOrderInfoOrder
	 * @return
	 */
	PaymentOrderInfoResult findPayOrderInfo(QueryPayOrderInfoOrder queryPayOrderInfoOrder);
	
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午9:58:44 创建
 */
package com.yiji.ypayment.integration.client;

import com.yiji.authenticate.facade.query.order.QueryByCardNoOrder;
import com.yiji.authenticate.facade.query.order.QueryByPactNoOrder;
import com.yiji.authenticate.facade.query.order.QueryByUserInfoOrder;
import com.yiji.authenticate.facade.query.result.PactCommonListResult;
import com.yiji.authenticate.facade.query.result.PactCommonResult;

/**
 * 签约系统
 *
 * @author xinqing@yiji.com
 *
 */
public interface PactQueryClient {
	
	/**
	 * 签约信息查询
	 * @param quickPaySubscribeOrder
	 * @return
	 */
	PactCommonListResult queryByPartUserPur(QueryByUserInfoOrder order);
	
	/**
	 * 根据银行卡号查询签约信息
	 * @param order
	 * @return
	 */
	PactCommonResult queryByCardNo(QueryByCardNoOrder order);
	
	/**
	 * 根据流水号查询绑卡信息
	 * @param order
	 * @return
	 */
	PactCommonResult queryByPactNo(QueryByPactNoOrder order);
	
}

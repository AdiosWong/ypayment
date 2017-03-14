/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:43:53 创建
 */
package com.yiji.ypayment.biz.remote;

import com.yiji.authenticate.facade.query.order.QueryByPactNoOrder;
import com.yiji.ypayment.biz.remote.info.PactBankCardInfo;
import com.yiji.ypayment.biz.remote.info.PactQueryResultInfo;
import com.yiji.ypayment.biz.remote.order.PactQueryOrder;

/**
 * 签约查询服务
 *
 * @author xinqing@yiji.com
 *
 */
public interface QueryBankCardService {
	
	/**
	 * 签约信息查询
	 * @param order
	 * @return PactQueryResultInfo
	 */
	PactQueryResultInfo queryBankCard(PactQueryOrder order);
	
	/**
	 * 根据签约号查询绑卡信息
	 * @param order
	 * @return
	 */
	PactBankCardInfo queryByPactNo(QueryByPactNoOrder order);
	
}

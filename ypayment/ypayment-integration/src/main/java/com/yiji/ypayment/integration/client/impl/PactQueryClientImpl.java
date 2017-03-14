/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:08:24 创建
 */
package com.yiji.ypayment.integration.client.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiji.authenticate.facade.query.PactQueryFacade;
import com.yiji.authenticate.facade.query.order.QueryByCardNoOrder;
import com.yiji.authenticate.facade.query.order.QueryByPactNoOrder;
import com.yiji.authenticate.facade.query.order.QueryByUserInfoOrder;
import com.yiji.authenticate.facade.query.result.PactCommonListResult;
import com.yiji.authenticate.facade.query.result.PactCommonResult;
import com.yiji.ypayment.integration.client.PactQueryClient;
import com.yiji.ypayment.integration.client.impl.caller.CallerServiceBase;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("pactQueryClient")
public class PactQueryClientImpl extends CallerServiceBase implements PactQueryClient {
	
	@Reference(version = "2.0")
	private PactQueryFacade pactQueryFacade;
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.integration.client.PactQueryClient#queryByPartUserPur(com.yjf.pact.facade.query.order.QueryByUserInfoOrder)
	 */
	@Override
	public PactCommonListResult queryByPartUserPur(QueryByUserInfoOrder order) {
		logger.info("签约信息查询入参:{}", order);
		PactCommonListResult result = pactQueryFacade.queryByPartUserPur(order);
		logger.info("签约信息查询出参:{}", result);
		return result;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.integration.client.PactQueryClient#queryByCardNo(com.yjf.pact.facade.query.order.QueryByCardNoOrder)
	 */
	@Override
	public PactCommonResult queryByCardNo(QueryByCardNoOrder order) {
		logger.info("签约信息查询入参:{}", order);
		PactCommonResult result = pactQueryFacade.queryByCardNo(order);
		logger.info("签约信息查询出参:{}", result);
		return result;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.integration.client.PactQueryClient#queryByPactNo(com.yjf.pact.facade.query.order.QueryByPactNoOrder)
	 */
	@Override
	public PactCommonResult queryByPactNo(QueryByPactNoOrder order) {
		logger.info("签约信息查询入参:{}", order);
		PactCommonResult result = pactQueryFacade.queryByPactNo(order);
		logger.info("签约信息查询出参:{}", result);
		return result;
	}
	
}

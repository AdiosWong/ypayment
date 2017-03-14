/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:13:50 创建
 */
package com.yiji.ypayment.test.queryCard;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yiji.authenticate.enums.PactPurposeEnum;
import com.yiji.authenticate.facade.query.order.QueryByPactNoOrder;
import com.yiji.ypayment.biz.remote.QueryBankCardService;
import com.yiji.ypayment.biz.remote.info.PactBankCardInfo;
import com.yiji.ypayment.biz.remote.info.PactQueryResultInfo;
import com.yiji.ypayment.biz.remote.order.PactQueryOrder;
import com.yiji.ypayment.test.base.TestBase;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class QueryBankCardServiceTest extends TestBase {
	
	@Autowired
	QueryBankCardService queryBankCardService;
	
	@Test
	@Ignore
	public void queryBankCard() {
		PactQueryOrder order = new PactQueryOrder();
		order.setUserId("20151124010000082925");
			
		PactQueryResultInfo result = queryBankCardService.queryBankCard(order);
		logger.info("{}", result);
	}
	
	@Test
	@Ignore
	public void queryByPactNo() {
		QueryByPactNoOrder order  = new QueryByPactNoOrder();
		order.setPactNo("Fa3bsozu49723S");
		order.setPurpose(PactPurposeEnum.DEDUCT);
		
		PactBankCardInfo result = queryBankCardService.queryByPactNo(order);
		logger.info("{}", result);
	}
	
}

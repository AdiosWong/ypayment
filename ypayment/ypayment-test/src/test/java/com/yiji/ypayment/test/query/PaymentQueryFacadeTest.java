/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:01:38 创建
 */
package com.yiji.ypayment.test.query;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yiji.ypayment.biz.remote.PaymentQueryRemoteService;
import com.yiji.ypayment.biz.remote.info.ResourceInstInfo;
import com.yiji.ypayment.common.utils.Dates;
import com.yiji.ypayment.facade.api.query.PaymentQueryFacade;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.order.query.PaymentQueryOrder;
import com.yiji.ypayment.facade.order.query.QueryPayOrderInfoOrder;
import com.yiji.ypayment.facade.order.query.QueryResourceInfoOrder;
import com.yiji.ypayment.facade.result.query.PaymentCheckUserResult;
import com.yiji.ypayment.facade.result.query.PaymentOrderInfoResult;
import com.yiji.ypayment.facade.result.query.PaymentQueryResult;
import com.yiji.ypayment.facade.result.query.ResourceInfoResult;
import com.yiji.ypayment.test.base.TestBase;
import com.yjf.common.id.GID;
import com.yjf.common.id.OID;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentQueryFacadeTest extends TestBase {
	
	@Autowired
	PaymentQueryFacade paymentQueryFacade;
	@Autowired
	protected PaymentQueryRemoteService paymentQueryRemoteService;
	
	@Test
	@Ignore
	public void queryResourceInfo() {
		QueryResourceInfoOrder order = new QueryResourceInfoOrder();
		order.setUserId("20141225010000062065");
		order.setPlatformType("YiPinHui");
		order.setPaymentTypeEnum(PaymentTypeEnum.WATER);
		order.setGid(GID.newGID());
		order.setPartnerId("20141106020000058750");
		order.setMerchOrderNo(OID.newID());
		
		ResourceInfoResult result = paymentQueryFacade.queryResourceInfo(order);
		logger.info("{}", result);
	}
	
	@Test
	@Ignore
	public void findShouldPayCostsWater() {
		PaymentQueryOrder order = new PaymentQueryOrder();
		order.setUserId("20140909010000052154");
		order.setPlatformType("ypayment");
		order.setUserCode("00503011299");
		order.setPaymentType(PaymentTypeEnum.WATER);
		order.setResourceCode("002300001");
		
		PaymentQueryResult result = paymentQueryFacade.findShouldPayCosts(order);
		logger.info("{}", result);
	}
	
	@Test
	@Ignore
	public void findShouldPayCostsGas() {
		PaymentQueryOrder order = new PaymentQueryOrder();
		order.setUserId("20141225010000062065");
		order.setPlatformType("YiPinHui");
		order.setUserCode("00503011295");
		order.setPaymentType(PaymentTypeEnum.WATER);
		order.setResourceCode("002300001");
		order.setGid(GID.newGID());
		order.setPartnerId("20141106020000058750");
		order.setMerchOrderNo(OID.newID());
		
		PaymentQueryResult result = paymentQueryFacade.findShouldPayCosts(order);
		logger.info("{}", result);
	}
	
	@Test
	@Ignore
	public void checkPaymentUser() {
		PaymentQueryOrder order = new PaymentQueryOrder();
		order.setUserId("20140909010000052154");
		order.setPlatformType("ypayment");
		order.setUserCode("00503011299");
		order.setPaymentType(PaymentTypeEnum.WATER);
		order.setResourceCode("002300001");
		
		PaymentCheckUserResult result = paymentQueryFacade.checkPaymentUser(order);
		logger.info("{}", result);
	}
	
	@Test
	@Ignore
	public void findPayOrderInfo() {
		QueryPayOrderInfoOrder order = new QueryPayOrderInfoOrder();
		order.setUserId("20151124010000082925");
		order.setPlatformType("YiPinHui");
		order.setPaymentType(PaymentTypeEnum.MOBILE);
		order.setGid(GID.newGID());
		order.setPartnerId("20141106020000058750");
		order.setMerchOrderNo(OID.newID());
		
		Date startTime = Dates.parse("2016-04-25 10:55:34", Dates.CHINESE_DATETIME_FORMAT_LINE);
		Date endTime = Dates.parse("2016-04-25 11:18:43", Dates.CHINESE_DATETIME_FORMAT_LINE);
		
		order.setStartTime(startTime);
		order.setEndTime(endTime);
		
		PaymentOrderInfoResult result = paymentQueryFacade.findPayOrderInfo(order);
		logger.info("{}", result);
	}
	
	@Test
	public void queryResource() {
		ResourceInstInfo result = paymentQueryRemoteService.queryResource("000000000");
		logger.info("{}", result);
	}
	
}

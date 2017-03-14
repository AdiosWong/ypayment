/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:37:27 创建
 */
package com.yiji.ypayment.test.paymentBinding;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yiji.ypayment.common.constant.Constant;
import com.yiji.ypayment.facade.api.Payment.PaymentBindingFacade;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.order.payment.PaymentBindingOrder;
import com.yiji.ypayment.facade.order.payment.PaymentUnbindingOrder;
import com.yiji.ypayment.facade.result.payment.PaymentBindingResult;
import com.yiji.ypayment.facade.result.payment.PaymentUnbindingResult;
import com.yiji.ypayment.test.base.TestBase;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentBindingFacadeTest extends TestBase {
	
	@Autowired
	PaymentBindingFacade paymentBindingFacade;
	
	@Test
	@Ignore
	public void paymentBindingWater() {
		PaymentBindingOrder order = new PaymentBindingOrder();
		order.setUserId("20141225010000062065");
		order.setPartnerId(Constant.VIRTUAL_MERCHANT);
		order.setPlatformType("ypayment");
		order.setPaymentType(PaymentTypeEnum.WATER);
		order.setResourceCode("002300001");
		order.setResourceName("重庆中法水务");
		//order.setUserCode("00503011299");
		order.setUserCode("00503011294");
		PaymentBindingResult result = paymentBindingFacade.paymentBinding(order);
		
		logger.info("{}", result);
	}
	
	@Test
	@Ignore
	public void paymentBindingGas() {
		PaymentBindingOrder order = new PaymentBindingOrder();
		order.setUserId("20141225010000062065");
		order.setPartnerId(Constant.VIRTUAL_MERCHANT);
		order.setPlatformType("ypayment");
		order.setPaymentType(PaymentTypeEnum.GAS);
		order.setResourceCode("002300007");
		order.setResourceName("重庆燃气");
		order.setUserCode("00000005");
		PaymentBindingResult result = paymentBindingFacade.paymentBinding(order);
		
		logger.info("{}", result);
	}
	
	@Test
	@Ignore
	public void paymentBindingElect() {
		PaymentBindingOrder order = new PaymentBindingOrder();
		order.setUserId("20140909010000052154");
		order.setPartnerId(Constant.VIRTUAL_MERCHANT);
		order.setPlatformType("ypayment");
		order.setPaymentType(PaymentTypeEnum.ELECTRICITY);
		order.setResourceCode("002300009");
		order.setResourceName("重庆电力");
		order.setUserCode("00000001");
		PaymentBindingResult result = paymentBindingFacade.paymentBinding(order);
		
		logger.info("{}", result);
	}
	
	@Test
	@Ignore
	public void paymentUnbinding() {
		PaymentUnbindingOrder order = new PaymentUnbindingOrder();
		order.setUserId("20140909010000052154");
		order.setPartnerId(Constant.VIRTUAL_MERCHANT);
		order.setPlatformType("ypayment");
		order.setContractNo("20151028000000000002");
		PaymentUnbindingResult result = paymentBindingFacade.paymentUnbinding(order);
		
		logger.info("{}", result);
	}
	
}

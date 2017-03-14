/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午6:33:14 创建
 */
package com.yiji.ypayment.test.paymentBinding;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yiji.ypayment.facade.api.query.PaymentQueryBindingFacade;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.order.query.PaymentQueryBindingOrder;
import com.yiji.ypayment.facade.result.query.PaymentQueryBindingResult;
import com.yiji.ypayment.test.base.TestBase;
import com.yjf.common.id.GID;
import com.yjf.common.id.OID;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentQueryBindingFacadeTest extends TestBase {
	
	@Autowired
	PaymentQueryBindingFacade paymentQueryBindingFacade;
	
	@Test
	@Ignore
	public void paymentQueryBindingInfo() {
		PaymentQueryBindingOrder order = new PaymentQueryBindingOrder();
		order.setUserId("20141225010000062065");
		order.setPlatformType("YiPinHui");
		order.setPaymentType(PaymentTypeEnum.WATER);
		order.setGid(GID.newGID());
		order.setPartnerId("20141106020000058750");
		order.setMerchOrderNo(OID.newID());
		
		PaymentQueryBindingResult result = paymentQueryBindingFacade.paymentQueryBindingInfo(order);
		
		logger.info("{}", result);
	}
	
}

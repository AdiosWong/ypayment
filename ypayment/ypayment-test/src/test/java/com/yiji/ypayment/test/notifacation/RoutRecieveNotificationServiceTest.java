/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:02:55 创建
 */
package com.yiji.ypayment.test.notifacation;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yiji.ypayment.test.base.TestBase;
import com.yjf.common.lang.context.OperationContext;
import com.yjf.quickpayment.route.RouteResultBase;
import com.yjf.quickpayment.route.order.RecieveRoutPaymentNotificationOrder;
import com.yjf.quickpayment.route.order.RoutPaymentOperationTypeEnum;
import com.yjf.resroute.api.RoutRecieveNotificationService;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class RoutRecieveNotificationServiceTest extends TestBase {
	
	@Autowired
	RoutRecieveNotificationService routRecieveNotificationService;
	
	@Test
	@Ignore
	public void recieveRoutPaymentNotificationPaymentTest() {
		RecieveRoutPaymentNotificationOrder order = new RecieveRoutPaymentNotificationOrder();
		order.setOperationType(RoutPaymentOperationTypeEnum.PAYMENT);
		order.setStatus("success");
		order.setOriginalOrderSerialnumber("20151111000000000066");
		
		RouteResultBase result = routRecieveNotificationService.recieveRoutPaymentNotification(order,
			new OperationContext());
		logger.info("{}", result);
	}
	
	@Test
	@Ignore
	public void recieveRoutPaymentNotificationCancelPaymentTest() {
		RecieveRoutPaymentNotificationOrder order = new RecieveRoutPaymentNotificationOrder();
		order.setOperationType(RoutPaymentOperationTypeEnum.CANCEL_PAYMENT);
		order.setStatus("success");
		order.setOriginalOrderSerialnumber("20151113000000000078");
		
		RouteResultBase result = routRecieveNotificationService.recieveRoutPaymentNotification(order,
			new OperationContext());
		logger.info("{}", result);
	}
	
}

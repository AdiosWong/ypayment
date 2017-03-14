/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:23:26 创建
 */
package com.yiji.ypayment.test.ftp;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yiji.ypayment.biz.remote.FTPService;
import com.yiji.ypayment.biz.remote.impl.FTPServiceImpl.OrderItem;
import com.yiji.ypayment.test.base.TestBase;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class FTPTest extends TestBase {
	
	@Autowired 
	private FTPService ftpService;
	
	@Test
	public void test() {
		String fileName = "20160718_SUCCESS";
		List<OrderItem> orderItems = ftpService.getPaymentOrder(fileName);
		System.out.print(orderItems);
	}
	
}

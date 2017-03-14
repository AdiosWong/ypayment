/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:21:13 创建
 */
package com.yiji.ypayment.biz.remote;

import java.util.List;

import com.yiji.ypayment.biz.remote.impl.FTPServiceImpl.OrderItem;

/**
 * ftp文件对账
 *
 * @author xinqing@yiji.com
 *
 */
public interface FTPService {
	
	/**
	 * 从FTP获取中信成功的订单
	 * 
	 * @param fileName
	 * @return
	 */
	List<OrderItem> getPaymentOrder(String fileName);
	
}

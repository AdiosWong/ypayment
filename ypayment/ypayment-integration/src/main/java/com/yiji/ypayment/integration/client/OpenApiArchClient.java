/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:40:38 创建
 */
package com.yiji.ypayment.integration.client;

import com.yiji.openapi.arch.facade.order.ApiNotifyOrder;
import com.yiji.openapi.arch.facade.result.ApiArchCommonResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public interface OpenApiArchClient {
	
	/**
	 * OpenApi异步通知商户
	 * @param apiNotifyOrder
	 * @return
	 */
	ApiArchCommonResult apiAsyncNotify(ApiNotifyOrder apiNotifyOrder);
	
}

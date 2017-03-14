/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:48:37 创建
 */
package com.yiji.ypayment.integration.client.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiji.openapi.arch.facade.OpenApiArchServcie;
import com.yiji.openapi.arch.facade.order.ApiNotifyOrder;
import com.yiji.openapi.arch.facade.result.ApiArchCommonResult;
import com.yiji.ypayment.integration.client.OpenApiArchClient;
import com.yiji.ypayment.integration.client.impl.caller.CallerServiceBase;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("openApiArchClient")
public class OpenApiArchClientImpl extends CallerServiceBase implements OpenApiArchClient {
	
	@Reference(version = "1.5", group = "com.yiji.openapi.arch.service.facade.OpenApiArchServcieImpl")
	private OpenApiArchServcie openApiArchServcie;
	
	/**
	 * @param apiNotifyOrder
	 * @return
	 * @see com.yiji.ypayment.integration.client.OpenApiArchClient#apiAsyncNotify(com.yiji.ypayment.integration.client.ApiNotifyOrder)
	 */
	@Override
	public ApiArchCommonResult apiAsyncNotify(ApiNotifyOrder apiNotifyOrder) {
		logger.info("调用OpenApi异步通知商户，入参：{}", apiNotifyOrder);
		ApiArchCommonResult result = openApiArchServcie.asyncNotify(apiNotifyOrder);
		logger.info("调用OpenApi异步通知商户，出参：{}", result);
		return result;
	}
	
}

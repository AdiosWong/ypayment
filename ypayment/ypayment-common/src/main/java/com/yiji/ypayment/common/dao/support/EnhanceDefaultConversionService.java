/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@yiji.com 2015-01-22 05:17 创建
 *
 */
package com.yiji.ypayment.common.dao.support;

import org.springframework.core.convert.support.DefaultConversionService;

/**
 * 增强日期和时间的字段转换
 * 
 * @author zhangpu
 */
public class EnhanceDefaultConversionService extends DefaultConversionService {
	
	public EnhanceDefaultConversionService() {
		super();
		addConverter(new StringToDateConverter());
	}
	
}

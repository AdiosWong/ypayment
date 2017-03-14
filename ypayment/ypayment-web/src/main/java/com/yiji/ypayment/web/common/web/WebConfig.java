package com.yiji.ypayment.web.common.web;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yiji.ypayment.web.filter.SecurityFilter;

/**
 * 配置拦截器
 * 
 * faZheng
 */
@Configuration
public class WebConfig {
	@Bean(name = "securityFilter")
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new SecurityFilter());
		filterRegistrationBean.setEnabled(true);
		
		//使用标准的正则表达式进行url的排除, 多个正则表达式以[,]分隔
		//常用说明  [^login\\..*]  登录页面/ [^/api/.*] 对外接口
		StringBuffer buffer = new StringBuffer();
		buffer
			//.append("^/$").append(",")
			.append("^/ypayment/outEntry\\..*").append(",")
			.append("^/ypayment/innerEntry\\.*").append(",")
			.append("^/error/.").append(",")
			.append("^/boss/.*").append(",")
			.append("^/test/.*").append(",")
			.append("^/timingTask/.*").append(",")
			.append("^/css/.*").append(",")
			.append("^/images/.*").append(",")
			.append("^/favicon.ico$").append(",")
			.append("^/js/.*").append(",")
			.append("^/payment/.*").append(",")
			.append("^/mgt/.*");
		filterRegistrationBean.addInitParameter("excludePatterns", buffer.toString());
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
}

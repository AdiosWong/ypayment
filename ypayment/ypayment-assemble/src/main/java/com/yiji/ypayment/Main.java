/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved
 * 修订记录:
 * qzhanbo@yiji.com 2015-08-20 16:48 创建
 *
 */
package com.yiji.ypayment;

import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.yiji.boot.core.Apps;
import com.yiji.boot.core.YijiBootApplication;
import com.yiji.ypayment.common.dao.jpa.EntityRepositoryFactoryBean;

/**
 * @author qiubo@yiji.com
 */
@YijiBootApplication(sysName = "ypayment", httpPort = 8144, heraEnable = true)
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityRepositoryFactoryBean.class)
public class Main {
	
	public static void main(String[] args) {
		
		//		Apps.setProfileIfNotExists("pdev"); //本地开发环境
		Apps.setProfileIfNotExists("stest");//内部联调环境
		//		Apps.setProfileIfNotExists("stest");//内部测试环境
		//		Apps.setProfileIfNotExists("online");//正式环境
		//		Apps.setProfileIfNotExists("snet");//外部联调环境
		
		SpringApplication app = new SpringApplication(Main.class);
		app.run();
	}
	
}
/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:03:09 创建
 */
package com.yiji.ypayment.web.boss.payment.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yiji.ypayment.biz.service.ypayment.CustomerInfoService;
import com.yiji.ypayment.dal.entity.business.CustomerInfo;
import com.yiji.ypayment.web.common.web.AbstractJQueryEntityController;
import com.yjf.common.service.enums.customer.UserStatusEnum;
import com.yjf.common.service.enums.customer.UserTypeEnum;

/**
 * 用户信息
 *
 * @author xinqing@yiji.com
 *
 */
@Controller
@RequestMapping(value = "boss/ypayment/user/userInfo")
public class UserInfoController extends AbstractJQueryEntityController<CustomerInfo, CustomerInfoService> {
	
	@Autowired
	CustomerInfoService customerInfoService;
	
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("userTypeEnum", UserTypeEnum.values());
		model.put("userStatusEnum", UserStatusEnum.values());
	}
	
}

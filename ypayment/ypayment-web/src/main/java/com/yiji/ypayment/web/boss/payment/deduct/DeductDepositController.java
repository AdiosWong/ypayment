/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:28:44 创建
 */
package com.yiji.ypayment.web.boss.payment.deduct;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yiji.ypayment.biz.service.ypayment.DeductDepositInfoService;
import com.yiji.ypayment.dal.entity.business.DeductDepositInfo;
import com.yiji.ypayment.dal.enums.DepositStatusEnum;
import com.yiji.ypayment.web.common.web.AbstractJQueryEntityController;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Controller
@RequestMapping(value = "boss/ypayment/payment/deduct/deductDeposit")
public class DeductDepositController extends AbstractJQueryEntityController<DeductDepositInfo, DeductDepositInfoService> {
	
	@Autowired
	DeductDepositInfoService deductDepositInfoService;
	
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("depositStatusEnum", DepositStatusEnum.values());
	}
	
}

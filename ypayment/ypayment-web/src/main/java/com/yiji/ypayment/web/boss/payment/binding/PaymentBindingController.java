/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:23:26 创建
 */
package com.yiji.ypayment.web.boss.payment.binding;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yiji.ypayment.biz.service.ypayment.PaymentBindingInfoService;
import com.yiji.ypayment.dal.entity.business.PaymentBindingInfo;
import com.yiji.ypayment.facade.enums.PaymentValidStatus;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.web.common.web.AbstractJQueryEntityController;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Controller
@RequestMapping(value = "boss/ypayment/payment/binding/paymentBinding")
public class PaymentBindingController extends
										AbstractJQueryEntityController<PaymentBindingInfo, PaymentBindingInfoService> {
	
	@Autowired
	PaymentBindingInfoService paymentBindingInfoService;
	
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("paymentTypeEnum", PaymentTypeEnum.values());
		model.put("paymentBindingStatus", PaymentValidStatus.values());
	}
	
}

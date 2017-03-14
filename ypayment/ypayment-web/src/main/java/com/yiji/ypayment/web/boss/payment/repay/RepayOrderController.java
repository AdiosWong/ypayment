/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:38:24 创建
 */
package com.yiji.ypayment.web.boss.payment.repay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yiji.ypayment.biz.remote.RepaymentService;
import com.yiji.ypayment.biz.service.ypayment.RepayOrderInfoService;
import com.yiji.ypayment.dal.entity.business.RepayOrderInfo;
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.web.common.web.AbstractJQueryEntityController;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Controller
@RequestMapping(value = "boss/ypayment/payment/repay/repayOrder")
public class RepayOrderController extends AbstractJQueryEntityController<RepayOrderInfo, RepayOrderInfoService> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	RepayOrderInfoService repayOrderInfoService;
	
	@Autowired
	RepaymentService repaymentService;
	
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("paymentItemStatusEnum", PaymentItemStatusEnum.values());
		model.put("paymentTypeEnum", PaymentTypeEnum.values());
	}
	
	@RequestMapping(value = "repayment")
	protected String transferTrade(HttpServletRequest request, Map<String, Object> model){
		String paymentOrderNo = request.getParameter("paymentOrderNo");
		repaymentService.repayment(paymentOrderNo);
		return "redirect:/boss/ypayment/payment/repay/repayOrder/list.html";	
	}
	
}

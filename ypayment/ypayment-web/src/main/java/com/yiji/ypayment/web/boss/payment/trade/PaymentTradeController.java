/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:25:28 创建
 */
package com.yiji.ypayment.web.boss.payment.trade;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yiji.ypayment.biz.service.ypayment.PaymentTradeService;
import com.yiji.ypayment.dal.entity.business.PaymentTrade;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.web.common.web.AbstractJQueryEntityController;

/**
 * 转账信息
 *
 * @author xinqing@yiji.com
 *
 */
@Controller
@RequestMapping(value = "boss/ypayment/payment/trade/paymentTrade")
public class PaymentTradeController extends AbstractJQueryEntityController<PaymentTrade, PaymentTradeService> {
	
	@Autowired
	PaymentTradeService paymentTradeService;
	
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("transferTradeStatusEnum", TransferTradeStatusEnum.values());
		model.put("paymentTypeEnum", PaymentTypeEnum.values());
	}
	
}

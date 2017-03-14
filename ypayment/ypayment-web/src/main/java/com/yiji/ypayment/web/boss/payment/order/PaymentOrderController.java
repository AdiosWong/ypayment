/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:25:00 创建
 */
package com.yiji.ypayment.web.boss.payment.order;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yiji.ypayment.biz.remote.TradeRemoteService;
import com.yiji.ypayment.biz.service.ypayment.PaymentItemInfoService;
import com.yiji.ypayment.biz.service.ypayment.PaymentOrderService;
import com.yiji.ypayment.biz.service.ypayment.PaymentTradeService;
import com.yiji.ypayment.dal.entity.business.PaymentItemInfo;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.entity.business.PaymentTrade;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
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
@RequestMapping(value = "boss/ypayment/payment/order/paymentOrder")
public class PaymentOrderController extends AbstractJQueryEntityController<PaymentOrder, PaymentOrderService> {
	
	@Autowired
	PaymentOrderService paymentOrderService;
	
	@Autowired
	PaymentItemInfoService paymentItemInfoService;
	
	@Autowired
	PaymentTradeService paymentTradeService;
	
	@Autowired
	TradeRemoteService tradeRemoteService;
	
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("paymentItemStatusEnum", PaymentItemStatusEnum.values());
		model.put("transferTradeStatusEnum", TransferTradeStatusEnum.values());
		model.put("paymentTypeEnum", PaymentTypeEnum.values());
	}
	
	@RequestMapping(value = "redirectItem")
	protected String redirectItem(HttpServletRequest request, Map<String, Object> model) {
		String paymentOrderNo = request.getParameter("paymentOrderNo");
		PaymentItemInfo paymentItemInfo = paymentItemInfoService.findByPaymentOrderNo(paymentOrderNo);
		Long id = paymentItemInfo.getId();
		return "redirect:/boss/ypayment/payment/item/paymentItem/show.html?id=" + id;
	}
	
	@RequestMapping(value = "redirectTrade")
	protected String redirectTrade(HttpServletRequest request, Map<String, Object> model) {
		String paymentOrderNo = request.getParameter("paymentOrderNo");
		String type = request.getParameter("type");
		List<PaymentTrade> paymentTrades = paymentTradeService.findByPaymentOrderNo(paymentOrderNo);
		int index = Integer.parseInt(type);
		Long id = paymentTrades.get(index).getId();
		return "redirect:/boss/ypayment/payment/trade/paymentTrade/show.html?id=" + id;	
	}
	
	@RequestMapping(value = "transferTrade")
	protected String transferTrade(HttpServletRequest request, Map<String, Object> model){
		String paymentOrderNo = request.getParameter("paymentOrderNo");
		tradeRemoteService.transferTradePayment(paymentOrderNo);
		return "redirect:/boss/ypayment/payment/order/paymentOrder/list.html";	
	}
	
}

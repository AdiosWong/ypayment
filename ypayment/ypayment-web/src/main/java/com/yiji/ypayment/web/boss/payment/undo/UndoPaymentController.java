/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:32:30 创建
 */
package com.yiji.ypayment.web.boss.payment.undo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yiji.ypayment.biz.remote.TradeRemoteService;
import com.yiji.ypayment.biz.service.ypayment.UndoPaymentService;
import com.yiji.ypayment.dal.entity.business.UndoPayment;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.enums.UndoApproachEnum;
import com.yiji.ypayment.facade.enums.UndoPaymentStatusEnum;
import com.yiji.ypayment.web.common.web.AbstractJQueryEntityController;

/**
 * 返销信息
 *
 * @author xinqing@yiji.com
 *
 */
@Controller
@RequestMapping(value = "boss/ypayment/payment/undo/undoPayment")
public class UndoPaymentController extends AbstractJQueryEntityController<UndoPayment, UndoPaymentService> {
	
	@Autowired
	UndoPaymentService undoPaymentService;
	
	@Autowired
	TradeRemoteService tradeRemoteService;
	
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("paymentTypeEnum", PaymentTypeEnum.values());
		model.put("undoApproachEnum", UndoApproachEnum.values());
		model.put("undoPaymentStatusEnum", UndoPaymentStatusEnum.values());
		model.put("transferTradeStatusEnum", TransferTradeStatusEnum.values());
	}
	
	@RequestMapping(value = "transferTrade")
	protected String transferTrade(HttpServletRequest request, Map<String, Object> model){
		String undoPaymentNo = request.getParameter("undoPaymentNo");
		tradeRemoteService.transferTradeUndoPayment(undoPaymentNo);
		return "redirect:/boss/ypayment/payment/undo/undoPayment/list.html";	
	}
	
}

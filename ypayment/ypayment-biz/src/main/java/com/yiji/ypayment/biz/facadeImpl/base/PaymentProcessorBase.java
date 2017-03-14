/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2015-01-19 16:34 创建
 *
 */
package com.yiji.ypayment.biz.facadeImpl.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yiji.ypayment.biz.common.servcie.CommonService;
import com.yiji.ypayment.biz.remote.CustomerRemoteService;
import com.yiji.ypayment.biz.remote.PaymentBindingRemoteService;
import com.yiji.ypayment.biz.remote.PaymentQueryRemoteService;
import com.yiji.ypayment.biz.remote.PaymentRemoteService;
import com.yiji.ypayment.biz.service.ypayment.BillItemInfoService;
import com.yiji.ypayment.biz.service.ypayment.PaymentBindingInfoService;
import com.yiji.ypayment.biz.service.ypayment.PaymentItemInfoService;
import com.yiji.ypayment.biz.service.ypayment.PaymentOrderService;
import com.yiji.ypayment.biz.service.ypayment.UndoPaymentService;
import com.yiji.ypayment.integration.client.CustomerClient;
import com.yiji.ypayment.integration.client.TradeClient;

/**
 * 需要引用的服务
 * 
 * @author CuiFuQ
 * 
 */
public class PaymentProcessorBase {
	
	@Autowired
	protected CommonService commonService;
	@Autowired
	protected CustomerClient customerClient;
	@Autowired
	protected CustomerRemoteService customerRemoteService;
	@Autowired
	protected TradeClient tradeClient;
	@Autowired
	protected PaymentQueryRemoteService paymentQueryRemoteService;
	@Autowired
	protected PaymentBindingInfoService paymentBindingInfoService;
	@Autowired
	protected PaymentBindingRemoteService paymentBindingRemoteService;
	@Autowired
	protected PaymentOrderService paymentOrderService;
	@Autowired
	protected PaymentRemoteService paymentRemoteService;
	@Autowired
	protected PaymentItemInfoService paymentItemInfoService;
	@Autowired
	protected BillItemInfoService billItemInfoService;
	@Autowired
	protected UndoPaymentService undoPaymentService;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
}

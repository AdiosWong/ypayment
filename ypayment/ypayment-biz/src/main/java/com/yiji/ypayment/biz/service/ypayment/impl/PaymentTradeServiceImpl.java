/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author faZheng 下午5:29:20 创建
 */
package com.yiji.ypayment.biz.service.ypayment.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yiji.ypayment.biz.service.ypayment.PaymentTradeService;
import com.yiji.ypayment.common.service.EntityServiceImpl;
import com.yiji.ypayment.dal.entity.business.PaymentTrade;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.dal.repository.business.PaymentTradeDao;

@Service("paymentTradeService")
public class PaymentTradeServiceImpl extends EntityServiceImpl<PaymentTrade, PaymentTradeDao> implements
																								PaymentTradeService {
	@Override
	public PaymentTrade findByBizOrderNo(String bizOrderNo) {
		return getEntityDao().findByBizOrderNo(bizOrderNo);
	}
	
	/**
	 * @param tradeStatus
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.PaymentTradeService#findByTransferTradeStatusEnum(com.yiji.ypayment.dal.enums.TransferTradeStatusEnum)
	 */
	@Override
	public List<PaymentTrade> findByTradeStatus(TransferTradeStatusEnum tradeStatus) {
		return getEntityDao().findByTradeStatus(tradeStatus);
	}
	
	/**
	 * @param paymentOrderNo
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.PaymentTradeService#findByPaymentOrderNo(java.lang.String)
	 */
	@Override
	public List<PaymentTrade> findByPaymentOrderNo(String paymentOrderNo) {
		return getEntityDao().findByRefBizOrderNo(paymentOrderNo);
	}
}

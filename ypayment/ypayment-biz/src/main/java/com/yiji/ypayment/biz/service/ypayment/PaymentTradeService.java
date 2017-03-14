/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author faZheng 下午5:29:20 创建
 */
package com.yiji.ypayment.biz.service.ypayment;

import java.util.List;

import com.yiji.ypayment.common.service.EntityService;
import com.yiji.ypayment.dal.entity.business.PaymentTrade;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;

/**
 * 
 * 交易服务接口
 *
 * @author faZheng
 *
 */
public interface PaymentTradeService extends EntityService<PaymentTrade> {
	
	/**
	 * 查询交易订单
	 * @param bizOrderNo
	 * @return
	 */
	PaymentTrade findByBizOrderNo(String bizOrderNo);
	
	/**
	 * 根据缴费订单号，查询交易订单
	 * @param bizOrderNo
	 * @return
	 */
	List<PaymentTrade> findByPaymentOrderNo(String paymentOrderNo);
	
	/**
	 * 根据转账状态查询交易记录
	 * @param tradeStatus
	 * @return
	 */
	List<PaymentTrade> findByTradeStatus(TransferTradeStatusEnum tradeStatus);
	
}

/*
* www.yiji.com Inc.
* Copyright (c) 2015 All Rights Reserved.
*/

/*
* 修订记录：
* faZheng 下午2:39:00 创建
*/
package com.yiji.ypayment.dal.repository.business;

import java.util.List;

import com.yiji.ypayment.common.dao.jpa.EntityJpaDao;
import com.yiji.ypayment.dal.entity.business.PaymentTrade;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;

/**
 *
 *
 * @author faZheng
 *
 */
public interface PaymentTradeDao extends EntityJpaDao<PaymentTrade, Long> {
	
	/**
	 * 根据交易订单号，查询交易记录
	 * @param bizOrderNo
	 * @return
	 */
	PaymentTrade findByBizOrderNo(String bizOrderNo);
	
	/**
	 * 根据缴费订单号，查询交易记录
	 * @param bizOrderNo
	 * @return
	 */
	List<PaymentTrade> findByRefBizOrderNo(String paymentOrderNo);
	
	/**
	 * 根据转账状态查询交易记录
	 * @param tradeStatus
	 * @return
	 */
	List<PaymentTrade> findByTradeStatus(TransferTradeStatusEnum tradeStatus);
	
}

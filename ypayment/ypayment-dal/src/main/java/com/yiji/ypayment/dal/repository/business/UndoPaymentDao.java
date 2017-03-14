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

import org.springframework.data.jpa.repository.Query;

import com.yiji.ypayment.common.dao.jpa.EntityJpaDao;
import com.yiji.ypayment.dal.entity.business.UndoPayment;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.facade.enums.UndoPaymentStatusEnum;

/**
 *
 *
 * @author faZheng
 *
 */
public interface UndoPaymentDao extends EntityJpaDao<UndoPayment, Long> {
	
	/**
	 * 根据返销订单号，查询返销记录
	 * @param undoPaymentNo
	 * @return
	 */
	UndoPayment findByUndoPaymentNo(String undoPaymentNo);
	
	/**
	 * 根据返销订单号，查询返销订单(锁定记录)
	 * @param undoPaymentNo
	 * @return
	 */
	@Query(value = "select * from undo_payment where undo_payment_no=?1  for update", nativeQuery = true)
	UndoPayment lockByUndoPaymentNo(String undoPaymentNo);
	
	/**
	 * 根据缴费订单号，查询返销记录
	 * 
	 * @param paymentOrderNo
	 * @return
	 */
	List<UndoPayment> findByPaymentOrderNo(String paymentOrderNo);
	
	/**
	 * 根据返销状态，查询返销记录
	 * @param undoStatus
	 * @return
	 */
	List<UndoPayment> findByUndoStatus(UndoPaymentStatusEnum undoStatus);
	
	/**
	 * 根据返销状态和转账转账状态，查询返销记录
	 * @param undoStatus
	 * @param tradeStatus
	 * @return
	 */
	List<UndoPayment> findByUndoStatusAndTradeStatus(UndoPaymentStatusEnum undoStatus,
														TransferTradeStatusEnum tradeStatus);
	
	/**
	 * 根据是否需要再次异步通知，查询订单
	 * @param notifyOpenApi
	 * @return
	 */
	List<UndoPayment> findByNotifyOpenApi(boolean notifyOpenApi);
	
}

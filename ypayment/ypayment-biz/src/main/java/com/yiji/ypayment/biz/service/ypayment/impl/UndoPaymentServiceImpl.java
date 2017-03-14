/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年11月9日 下午6:02:15 创建
 */

package com.yiji.ypayment.biz.service.ypayment.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yiji.ypayment.biz.service.ypayment.UndoPaymentService;
import com.yiji.ypayment.common.service.EntityServiceImpl;
import com.yiji.ypayment.dal.entity.business.UndoPayment;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.dal.repository.business.UndoPaymentDao;
import com.yiji.ypayment.facade.enums.UndoPaymentStatusEnum;

/**
 * 返销服务
 *
 * @author faZheng
 *
 */
@Service("undoPaymentService")
public class UndoPaymentServiceImpl extends EntityServiceImpl<UndoPayment, UndoPaymentDao> implements
																							UndoPaymentService {
	
	/**
	 * @param undoPaymentNo
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.UndoPaymentService#findByUndoPaymentNo(java.lang.String)
	 */
	@Override
	public UndoPayment findByUndoPaymentNo(String undoPaymentNo) {
		return getEntityDao().findByUndoPaymentNo(undoPaymentNo);
	}

	/**
	 * @param undoPaymentNo
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.UndoPaymentService#lockByUndoPaymentNo(java.lang.String)
	 */
	@Override
	public UndoPayment lockByUndoPaymentNo(String undoPaymentNo) {
		return getEntityDao().lockByUndoPaymentNo(undoPaymentNo);
	}
	
	/**
	 * @param paymentOrderNo
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.UndoPaymentService#findByPaymentOrderNo(java.lang.String)
	 */
	@Override
	public List<UndoPayment> findByPaymentOrderNo(String paymentOrderNo) {
		return getEntityDao().findByPaymentOrderNo(paymentOrderNo);
	}
	
	/**
	 * @param undoStatus
	 * @param tradeStatus
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.UndoPaymentService#findByUndoStatusAndTradeStatus(com.yiji.ypayment.facade.enums.UndoPaymentStatusEnum,
	 * com.yiji.ypayment.dal.enums.TransferTradeStatusEnum)
	 */
	@Override
	public List<UndoPayment> findByUndoStatusAndTradeStatus(UndoPaymentStatusEnum undoStatus,
															TransferTradeStatusEnum tradeStatus) {
		return getEntityDao().findByUndoStatusAndTradeStatus(undoStatus, tradeStatus);
	}
	
	/**
	 * @param undoStatus
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.UndoPaymentService#findByUndoStatus(com.yiji.ypayment.facade.enums.UndoPaymentStatusEnum)
	 */
	@Override
	public List<UndoPayment> findByUndoStatus(UndoPaymentStatusEnum undoStatus) {
		return getEntityDao().findByUndoStatus(undoStatus);
	}

	/**
	 * @param notifyOpenApi
	 * @return
	 * @see com.yiji.ypayment.biz.service.ypayment.UndoPaymentService#findByNotifyOpenApi(boolean)
	 */
	@Override
	public List<UndoPayment> findByNotifyOpenApi(boolean notifyOpenApi) {
		return getEntityDao().findByNotifyOpenApi(notifyOpenApi);
	}

}

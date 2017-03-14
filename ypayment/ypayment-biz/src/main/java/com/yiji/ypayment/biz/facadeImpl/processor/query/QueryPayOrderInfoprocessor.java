/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午6:35:58 创建
 */
package com.yiji.ypayment.biz.facadeImpl.processor.query;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorBase;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate;
import com.yiji.ypayment.common.dao.support.PageInfo;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.info.query.PayOrderInfo;
import com.yiji.ypayment.facade.order.query.QueryPayOrderInfoOrder;
import com.yiji.ypayment.facade.result.query.PaymentOrderInfoResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Component("queryPayOrderInfoprocessor")
public class QueryPayOrderInfoprocessor extends PaymentProcessorBase
																	implements
																	PaymentProcessorTemplate<QueryPayOrderInfoOrder, PaymentOrderInfoResult> {
	
	/**
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#getAction()
	 */
	@Override
	public PaymentInstructionAction getAction() {
		return PaymentInstructionAction.QUERY_ORDER_INFO;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#initResult(com.yjf.common.service.OrderBase)
	 */
	@Override
	public PaymentOrderInfoResult initResult(QueryPayOrderInfoOrder order) {
		return new PaymentOrderInfoResult();
	}
	
	/**
	 * @param order
	 * @param result
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#execute(com.yjf.common.service.OrderBase,
	 * com.yjf.common.lang.result.StandardResultInfo)
	 */
	@Override
	public void execute(QueryPayOrderInfoOrder order, PaymentOrderInfoResult result) {
		
		try {
			PageInfo<PaymentOrder> pageInfo = paymentOrderService.query(order);
			List<PaymentOrder> paymentOrders = pageInfo.getPageResults();
			for (PaymentOrder paymentOrder : paymentOrders) {
				PayOrderInfo payOrderInfo = paymentRemoteService.bulidPayOrderInfo(paymentOrder);
				result.getPayOrderInfos().add(payOrderInfo);
			}
			result.setCurrentPage(pageInfo.getCurrentPage());
			result.setTotalCount(pageInfo.getTotalCount());
			result.setTotalPage(pageInfo.getTotalPage());
		} catch (Exception e) {
			throw new PaymentException(PaymentResultCode.PAYMENT_QUERY_ORDER_INFO_FAIL,
				PaymentResultCode.PAYMENT_QUERY_ORDER_INFO_FAIL.getMessage());
		}
		
		result.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
	}
	
}

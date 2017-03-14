/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:45:56 创建
 */
package com.yiji.ypayment.biz.facadeImpl.processor.query;

import org.springframework.stereotype.Component;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorBase;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate;
import com.yiji.ypayment.biz.remote.info.PaymentQueryInfo;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.order.query.PaymentQueryOrder;
import com.yiji.ypayment.facade.result.query.PaymentQueryResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Component("paymentQueryProcessor")
public class PaymentQueryProcessor extends PaymentProcessorBase
																implements
																PaymentProcessorTemplate<PaymentQueryOrder, PaymentQueryResult> {
	
	/**
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#getAction()
	 */
	@Override
	public PaymentInstructionAction getAction() {
		return PaymentInstructionAction.QUERY_PAY_COSTS;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#initResult(com.yjf.common.service.OrderBase)
	 */
	@Override
	public PaymentQueryResult initResult(PaymentQueryOrder order) {
		PaymentQueryResult result = new PaymentQueryResult();
		return result;
	}
	
	/**
	 * @param order
	 * @param result
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#execute(com.yjf.common.service.OrderBase,
	 * com.yjf.common.lang.result.StandardResultInfo)
	 */
	@Override
	public void execute(PaymentQueryOrder order, PaymentQueryResult result) {
		PaymentQueryInfo paymentQueryInfo = paymentQueryRemoteService.queryPayment(order);
		result.setResourceCode(paymentQueryInfo.getResourceCode());
		result.setUserCode(paymentQueryInfo.getUserCode());
		result.setUsername(paymentQueryInfo.getUsername());
		result.setAddress(paymentQueryInfo.getAddress());
		result.setItems(paymentQueryInfo.getItems());
		result.setTotalPayables(paymentQueryInfo.getTotalPayables());
		
		result.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
	}
	
}

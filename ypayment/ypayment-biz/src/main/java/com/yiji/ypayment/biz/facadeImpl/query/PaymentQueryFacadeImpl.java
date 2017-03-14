/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:49:27 创建
 */
package com.yiji.ypayment.biz.facadeImpl.query;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiji.ypayment.biz.facadeImpl.base.BizServiceBase;
import com.yiji.ypayment.biz.facadeImpl.processor.query.PaymentCheckUserProcessor;
import com.yiji.ypayment.biz.facadeImpl.processor.query.PaymentQueryProcessor;
import com.yiji.ypayment.biz.facadeImpl.processor.query.PaymentUndoOrderStatusProcessor;
import com.yiji.ypayment.biz.facadeImpl.processor.query.QueryPayOrderInfoprocessor;
import com.yiji.ypayment.biz.facadeImpl.processor.query.QueryResourceInfoProcessor;
import com.yiji.ypayment.facade.api.query.PaymentQueryFacade;
import com.yiji.ypayment.facade.order.query.PaymentOrderStatusOrder;
import com.yiji.ypayment.facade.order.query.PaymentQueryOrder;
import com.yiji.ypayment.facade.order.query.QueryPayOrderInfoOrder;
import com.yiji.ypayment.facade.order.query.QueryResourceInfoOrder;
import com.yiji.ypayment.facade.result.query.PaymentCheckUserResult;
import com.yiji.ypayment.facade.result.query.PaymentOrderInfoResult;
import com.yiji.ypayment.facade.result.query.PaymentQueryResult;
import com.yiji.ypayment.facade.result.query.PaymentUndoOrderStatusResult;
import com.yiji.ypayment.facade.result.query.ResourceInfoResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service(version = "1.5")
public class PaymentQueryFacadeImpl extends BizServiceBase implements PaymentQueryFacade {
	
	@Autowired
	PaymentCheckUserProcessor paymentCheckUserProcessor;
	@Autowired
	PaymentQueryProcessor paymentQueryProcessor;
	@Autowired
	PaymentUndoOrderStatusProcessor paymentUndoOrderStatusProcessor;
	@Autowired
	QueryResourceInfoProcessor queryResourceInfoProcessor;
	@Autowired
	QueryPayOrderInfoprocessor queryPayOrderInfoprocessor;
	
	/**
	 * @param queryResourceInfoOrder
	 * @return
	 * @see com.yiji.ypayment.facade.api.query.PaymentQueryFacade#queryResourceInfo(com.yiji.ypayment.facade.order.query.QueryResourceInfoOrder)
	 */
	@Override
	public ResourceInfoResult queryResourceInfo(QueryResourceInfoOrder queryResourceInfoOrder) {
		return doBiz(queryResourceInfoOrder, queryResourceInfoProcessor);
	}
	
	/**
	 * @param queryOrder
	 * @return
	 * @see com.yiji.ypayment.facade.api.query.PaymentQueryFacade#findShouldPayCosts(com.yiji.ypayment.facade.order.query.PaymentQueryOrder)
	 */
	@Override
	public PaymentQueryResult findShouldPayCosts(PaymentQueryOrder queryOrder) {
		return doBiz(queryOrder, paymentQueryProcessor);
	}
	
	/**
	 * @param queryOrder
	 * @return
	 * @see com.yiji.ypayment.facade.api.query.PaymentQueryFacade#checkPaymentUser(com.yiji.ypayment.facade.order.query.PaymentQueryOrder)
	 */
	@Override
	public PaymentCheckUserResult checkPaymentUser(PaymentQueryOrder queryOrder) {
		return doBiz(queryOrder, paymentCheckUserProcessor);
	}
	
	/**
	 * @param statusOrder
	 * @return
	 * @see com.yiji.ypayment.facade.api.query.PaymentQueryFacade#findUndoOrderStatus(com.yiji.ypayment.facade.order.query.PaymentOrderStatusOrder)
	 */
	@Override
	public PaymentUndoOrderStatusResult findUndoOrderStatus(PaymentOrderStatusOrder statusOrder) {
		return doBiz(statusOrder, paymentUndoOrderStatusProcessor);
	}
	
	/**
	 * @param queryPayOrderInfoOrder
	 * @return
	 * @see com.yiji.ypayment.facade.api.query.PaymentQueryFacade#findPayOrderInfo(com.yiji.ypayment.facade.order.query.QueryPayOrderInfoOrder)
	 */
	@Override
	public PaymentOrderInfoResult findPayOrderInfo(QueryPayOrderInfoOrder queryPayOrderInfoOrder) {
		return doBiz(queryPayOrderInfoOrder, queryPayOrderInfoprocessor);
	}
	
}

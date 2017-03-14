/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:27:40 创建
 */
package com.yiji.ypayment.biz.facadeImpl.processor.query;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorBase;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate;
import com.yiji.ypayment.biz.remote.info.ResourceInstInfo;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.info.query.ResourceInfo;
import com.yiji.ypayment.facade.order.query.QueryResourceInfoOrder;
import com.yiji.ypayment.facade.result.query.ResourceInfoResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Component("queryResourceInfoProcessor")
public class QueryResourceInfoProcessor extends PaymentProcessorBase
																	implements
																	PaymentProcessorTemplate<QueryResourceInfoOrder, ResourceInfoResult> {
	
	/**
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#getAction()
	 */
	@Override
	public PaymentInstructionAction getAction() {
		return PaymentInstructionAction.QUERY_RESOURCE_INFO;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#initResult(com.yjf.common.service.OrderBase)
	 */
	@Override
	public ResourceInfoResult initResult(QueryResourceInfoOrder order) {
		ResourceInfoResult result = new ResourceInfoResult();
		return result;
	}
	
	/**
	 * @param order
	 * @param result
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#execute(com.yjf.common.service.OrderBase,
	 * com.yjf.common.lang.result.StandardResultInfo)
	 */
	@Override
	public void execute(QueryResourceInfoOrder order, ResourceInfoResult result) {
		List<ResourceInstInfo> resourceInstInfos = paymentQueryRemoteService.queryResourceInst(order
			.getPaymentTypeEnum());
		
		if (null != resourceInstInfos && !resourceInstInfos.isEmpty()) {
			for (ResourceInstInfo resourceInstInfo : resourceInstInfos) {
				ResourceInfo instInfo = new ResourceInfo();
				instInfo.setCityName(resourceInstInfo.getCityName());
				instInfo.setResourceCode(resourceInstInfo.getInstCode());
				instInfo.setResourceName(resourceInstInfo.getInstName());
				instInfo.setProvinceName(resourceInstInfo.getProvinceName());
				result.getResourceInfos().add(instInfo);
			}
		}
		result.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
	}
	
}

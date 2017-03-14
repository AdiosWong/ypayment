/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:05:08 创建
 */
package com.yiji.ypayment.biz.facadeImpl.processor.query;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorBase;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate;
import com.yiji.ypayment.dal.entity.business.PaymentBindingInfo;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.enums.PaymentValidStatus;
import com.yiji.ypayment.facade.info.query.PayBindingInfo;
import com.yiji.ypayment.facade.order.query.PaymentQueryBindingOrder;
import com.yiji.ypayment.facade.result.query.PaymentQueryBindingResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Component("paymentQueryBindingProcessor")
public class PaymentQueryBindingProcessor extends PaymentProcessorBase
																		implements
																		PaymentProcessorTemplate<PaymentQueryBindingOrder, PaymentQueryBindingResult> {
	
	/**
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#getAction()
	 */
	@Override
	public PaymentInstructionAction getAction() {
		return PaymentInstructionAction.QUERY_BINDING;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#initResult(com.yjf.common.service.OrderBase)
	 */
	@Override
	public PaymentQueryBindingResult initResult(PaymentQueryBindingOrder order) {
		PaymentQueryBindingResult result = new PaymentQueryBindingResult();
		return result;
	}
	
	/**
	 * @param order
	 * @param result
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#execute(com.yjf.common.service.OrderBase,
	 * com.yjf.common.lang.result.StandardResultInfo)
	 */
	@Override
	public void execute(PaymentQueryBindingOrder order, PaymentQueryBindingResult result) {
		
		try {
			List<PaymentBindingInfo> paymentBindingInfos = paymentBindingInfoService.findByUserIdAndPaymentTypeAndPayFrom(order.getUserId(),
				order.getPaymentType(), order.getPlatformType());
			if (paymentBindingInfos != null && !paymentBindingInfos.isEmpty()) {
				for (PaymentBindingInfo paymentBindingInfo : paymentBindingInfos) {
					if (PaymentValidStatus.VALID == paymentBindingInfo.getStatus()) {
						PayBindingInfo payBindingInfo = new PayBindingInfo();
						payBindingInfo.setUserId(paymentBindingInfo.getUserId());
						payBindingInfo.setContractNo(paymentBindingInfo.getContractNo());
						payBindingInfo.setResourceCode(paymentBindingInfo.getResourceCode());
						payBindingInfo.setResourceName(paymentBindingInfo.getResourceName());
						payBindingInfo.setUserCode(paymentBindingInfo.getUserCode());
						payBindingInfo.setUserName(paymentBindingInfo.getUserName());
						payBindingInfo.setPaymentType(paymentBindingInfo.getPaymentType());
						payBindingInfo.setPayFrom(paymentBindingInfo.getPayFrom());
						payBindingInfo.setMemo(paymentBindingInfo.getMemo());
						result.getPayBindingInfos().add(payBindingInfo);
					}
				}
			}
		} catch (Exception e) {
			throw new PaymentException(PaymentResultCode.PAYMENT_QUERY_BINDING_INFO_FAIL,
				PaymentResultCode.PAYMENT_QUERY_BINDING_INFO_FAIL.getMessage());
		}
		result.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
	}
	
}

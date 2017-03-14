/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:44:27 创建
 */
package com.yiji.ypayment.biz.facadeImpl.processor.paymentbinding;

import org.springframework.stereotype.Component;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorBase;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.order.payment.PaymentUnbindingOrder;
import com.yiji.ypayment.facade.result.payment.PaymentUnbindingResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Component("paymentUnbindingProcessor")
public class PaymentUnbindingProcessor extends PaymentProcessorBase
																	implements
																	PaymentProcessorTemplate<PaymentUnbindingOrder, PaymentUnbindingResult> {
	/**
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#getAction()
	 */
	@Override
	public PaymentInstructionAction getAction() {
		return PaymentInstructionAction.DELETE_BINDING;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#initResult(com.yjf.common.service.OrderBase)
	 */
	@Override
	public PaymentUnbindingResult initResult(PaymentUnbindingOrder order) {
		PaymentUnbindingResult result = new PaymentUnbindingResult();
		return result;
	}
	
	/**
	 * @param order
	 * @param result
	 * @see com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate#execute(com.yjf.common.service.OrderBase,
	 * com.yjf.common.lang.result.StandardResultInfo)
	 */
	@Override
	public void execute(PaymentUnbindingOrder order, PaymentUnbindingResult result) {
		try {
			PaymentUnbindingResult paymentUnbindingResult = paymentBindingRemoteService.paymentUnBinding(order);
			result.setUserId(paymentUnbindingResult.getUserId());
			result.setContractNo(paymentUnbindingResult.getContractNo());
			result.setUserCode(paymentUnbindingResult.getUserCode());
			result.setUserName(paymentUnbindingResult.getUserName());
			result.setBindingStatus(paymentUnbindingResult.getBindingStatus());
			result.setSuccessResult(PaymentResultCode.EXECUTE_SUCCESS);
		} catch (PaymentException paymentException) {
			logger.error("解绑用户缴费绑卡失败：", paymentException.getMessage());
			throw new PaymentException(PaymentResultCode.USER_NOT_BIND_CARD, "该用户还未绑卡，无法解绑");
		}catch (Exception e) {
			logger.error("解绑用户缴费绑卡时发生系统异常！");
			logger.error(e.getMessage(), e);
			throw new PaymentException(PaymentResultCode.DATA_EXCEPTION, "数据库异常");
		}
	}
	
}

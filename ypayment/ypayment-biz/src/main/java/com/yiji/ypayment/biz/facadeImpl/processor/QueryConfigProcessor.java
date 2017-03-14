package com.yiji.ypayment.biz.facadeImpl.processor;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorBase;
import com.yiji.ypayment.biz.facadeImpl.base.PaymentProcessorTemplate;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.order.config.QueryConfigOrder;
import com.yiji.ypayment.facade.result.config.QueryConfigResult;
import com.yjf.common.lang.result.Status;

/**
 * 
 * 
 * @author faZheng
 * 
 */
@Component("queryConfigProcessor")
public class QueryConfigProcessor extends PaymentProcessorBase
																implements
																PaymentProcessorTemplate<QueryConfigOrder, QueryConfigResult> {
	
	@Override
	public PaymentInstructionAction getAction() {
		return PaymentInstructionAction.QUERY_CONFIG;
	}
	
	@Override
	public QueryConfigResult initResult(QueryConfigOrder order) {
		return new QueryConfigResult();
	}
	
	@Override
	@Transactional
	public void execute(QueryConfigOrder order, QueryConfigResult result) {
		result.setStatus(Status.SUCCESS);
		result.setResultCode(PaymentResultCode.EXECUTE_SUCCESS);
		result.setDescription(PaymentResultCode.EXECUTE_SUCCESS.getMessage());
	}
}

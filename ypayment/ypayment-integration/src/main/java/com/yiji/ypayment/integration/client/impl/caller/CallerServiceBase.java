package com.yiji.ypayment.integration.client.impl.caller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yjf.common.lang.context.OperationContext;

/**
 * 
 * @author faZheng
 * 
 */
public class CallerServiceBase {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected static final OperationContext OPERATION_CONTEXT = OperationContext.build("ypayment", "ypayment",
		OperationContext.OperationTypeEnum.member, OperationContext.BusinessTypeEnum.SYSTEM);
	
}

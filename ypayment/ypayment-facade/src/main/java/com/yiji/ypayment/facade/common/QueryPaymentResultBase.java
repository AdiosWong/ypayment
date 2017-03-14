package com.yiji.ypayment.facade.common;

import com.yjf.common.util.ToString;

/**
 * 缴费查询result基类 StandardResultInfo
 * 
 * @author faZheng
 * 
 */

public class QueryPaymentResultBase extends PaymentResultBase {
	
	private static final long serialVersionUID = 7078242107502604481L;
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
}

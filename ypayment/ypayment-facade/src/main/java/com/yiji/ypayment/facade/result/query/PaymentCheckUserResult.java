/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:46:21 创建
 */
package com.yiji.ypayment.facade.result.query;

import com.yiji.ypayment.facade.common.QueryPaymentResultBase;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentCheckUserResult extends QueryPaymentResultBase {
	
	private static final long serialVersionUID = 3951598749543596631L;
	
	private boolean isExist;
	
	public boolean isExist() {
		return this.isExist;
	}
	
	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
}

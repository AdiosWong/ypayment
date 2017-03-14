/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:34:30 创建
 */
package com.yiji.ypayment.facade.order.payment;

import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.ypayment.facade.common.PaymentOrderBase;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentUnbindingOrder extends PaymentOrderBase {
	
	private static final long serialVersionUID = -2216151546158569707L;
	
	/**
	 * 缴费签约号
	 */
	@NotEmpty
	private String contractNo;
	
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
}

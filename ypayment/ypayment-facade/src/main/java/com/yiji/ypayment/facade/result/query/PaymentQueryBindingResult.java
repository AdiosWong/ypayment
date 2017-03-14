/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:07:51 创建
 */
package com.yiji.ypayment.facade.result.query;

import java.util.List;

import com.google.common.collect.Lists;
import com.yiji.ypayment.facade.common.QueryPaymentResultBase;
import com.yiji.ypayment.facade.info.query.PayBindingInfo;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentQueryBindingResult extends QueryPaymentResultBase {
	
	private static final long serialVersionUID = -5714804095394588598L;
	
	List<PayBindingInfo> payBindingInfos = Lists.newArrayList();
	
	public List<PayBindingInfo> getPayBindingInfos() {
		return this.payBindingInfos;
	}


	public void setPayBindingInfos(List<PayBindingInfo> payBindingInfos) {
		this.payBindingInfos = payBindingInfos;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

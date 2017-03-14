/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:57:32 创建
 */
package com.yiji.ypayment.facade.result.query;

import java.util.List;

import com.google.common.collect.Lists;
import com.yiji.ypayment.facade.common.QueryPageResultBase;
import com.yiji.ypayment.facade.info.query.PayOrderInfo;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PaymentOrderInfoResult extends QueryPageResultBase {
	
	private static final long serialVersionUID = 3592093121170588931L;
	
	/**
	 * 缴费结果
	 */
	List<PayOrderInfo> payOrderInfos = Lists.newArrayList();
	
	public List<PayOrderInfo> getPayOrderInfos() {
		return this.payOrderInfos;
	}
	
	public void setPayOrderInfos(List<PayOrderInfo> payOrderInfos) {
		this.payOrderInfos = payOrderInfos;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月27日 上午9:56:39 创建
 */

package com.yiji.ypayment.facade.result.payment;

import java.util.List;

import com.google.common.collect.Lists;
import com.yiji.ypayment.facade.common.PaymentResultBase;
import com.yiji.ypayment.facade.info.query.PayOrderInfo;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author faZheng
 *
 */

public class ApplyPaymentResult extends PaymentResultBase {
	
	private static final long serialVersionUID = 1519420785320358875L;
	
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

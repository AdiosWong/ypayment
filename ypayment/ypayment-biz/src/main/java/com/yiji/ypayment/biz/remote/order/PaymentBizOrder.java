/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月23日 下午3:51:39 创建
 */

package com.yiji.ypayment.biz.remote.order;

import java.util.ArrayList;
import java.util.List;

import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yjf.common.util.ToString;

/**
 * 缴费对象
 *
 * @author xinqing@yiji.com
 *
 */

public class PaymentBizOrder {

	private ApplyPaymentForm applyPaymentOrder;
	
	private String resourceName;
	
	private List<PaymentOrder> paymentOrders = new ArrayList<PaymentOrder>();
	
	public ApplyPaymentForm getApplyPaymentOrder() {
		return this.applyPaymentOrder;
	}
	
	public void setApplyPaymentOrder(ApplyPaymentForm applyPaymentOrder) {
		this.applyPaymentOrder = applyPaymentOrder;
	}
	
	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	public List<PaymentOrder> getPaymentOrders() {
		return this.paymentOrders;
	}

	public void setPaymentOrders(List<PaymentOrder> paymentOrders) {
		this.paymentOrders = paymentOrders;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
}

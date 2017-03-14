package com.yiji.ypayment.biz.remote.info;

import java.util.List;

import com.google.common.collect.Lists;
import com.yiji.ypayment.facade.common.PaymentResultBase;
import com.yiji.ypayment.facade.info.query.PayOrderInfo;
import com.yjf.common.util.ToString;

public class PaymentResultInfo extends PaymentResultBase {
	
	private static final long serialVersionUID = -415018211651190855L;
	
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

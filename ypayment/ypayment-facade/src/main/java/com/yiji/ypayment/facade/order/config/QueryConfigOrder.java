package com.yiji.ypayment.facade.order.config;

import com.yiji.ypayment.facade.common.QueryPaymentOrderBase;
import com.yjf.common.util.ToString;

/**
 * 查询配置信息
 * 
 * @author CuiFuQ
 * 
 */
public class QueryConfigOrder extends QueryPaymentOrderBase {
	
	private static final long serialVersionUID = -5712317539438702739L;
	
	private String key;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
}

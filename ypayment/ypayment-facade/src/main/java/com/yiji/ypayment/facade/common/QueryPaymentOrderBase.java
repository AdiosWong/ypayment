package com.yiji.ypayment.facade.common;

import org.hibernate.validator.constraints.NotEmpty;

import com.yjf.common.service.OrderBase;
import com.yjf.common.util.ToString;

/**
 * 缴费查询order基类
 * 
 * @author faZheng
 * 
 */

public class QueryPaymentOrderBase extends OrderBase {
	
	private static final long serialVersionUID = -5412289238914637186L;
	
	/**
	 * 易极付用户（必填）
	 */
	@NotEmpty
	private String userId;
	
	/**
	 * 平台类型
	 */
	@NotEmpty
	private String platformType;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPlatformType() {
		return this.platformType;
	}
	
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
}

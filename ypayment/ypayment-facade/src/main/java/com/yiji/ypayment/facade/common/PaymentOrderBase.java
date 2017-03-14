package com.yiji.ypayment.facade.common;

import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yjf.common.service.OrderBase;
import com.yjf.common.util.ToString;

/**
 * 缴费order基类
 * 
 * @author faZheng
 * 
 */

public class PaymentOrderBase extends OrderBase {
	
	private static final long serialVersionUID = 3635921019997401408L;
	
	/**
	 * 系统入口编码 
	 */
	@NotEmpty
	@Length(min = 1, max = 8)
	private String inlet = "23";
	
	/**
	 * 易极付用户（必填）
	 */
	@NotEmpty
	@Length(max = 32)
	private String userId;
	
	/**
	 * 平台类型
	 */
	@NotEmpty
	@Length(max = 48)
	private String platformType;
	
	/**
	 * 扩展字段
	 */
	private Map<String, Object> extraParam;
	
	public String getInlet() {
		return this.inlet;
	}

	public void setInlet(String inlet) {
		this.inlet = inlet;
	}

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
	
	public Map<String, Object> getExtraParam() {
		return extraParam;
	}
	
	public void setExtraParam(Map<String, Object> extraParam) {
		this.extraParam = extraParam;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
}

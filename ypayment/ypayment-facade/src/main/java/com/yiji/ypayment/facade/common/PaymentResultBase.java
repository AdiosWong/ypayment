package com.yiji.ypayment.facade.common;

import javax.validation.constraints.NotNull;

import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yjf.common.lang.result.StandardResultInfo;
import com.yjf.common.lang.result.Status;
import com.yjf.common.util.ToString;

/**
 * 缴费result基类 StandardResultInfo
 * 
 * @author faZheng
 * 
 */

public class PaymentResultBase extends StandardResultInfo {
	
	private static final long serialVersionUID = 4800550279661481398L;
	
	@NotNull
	protected PaymentResultCode resultCode;
	
	public PaymentResultCode getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(PaymentResultCode resultCode) {
		this.resultCode = resultCode;
		this.code = resultCode.getSysCode();
		this.description = resultCode.getMessage();
	}
	
	public void setSuccessResult(PaymentResultCode resultCode) {
		this.resultCode = resultCode;
		this.status = Status.SUCCESS;
		this.code = resultCode.getSysCode();
		this.description = resultCode.getMessage();
	}
	
	public void setFailResult(PaymentResultCode resultCode) {
		this.resultCode = resultCode;
		this.status = Status.FAIL;
		this.code = resultCode.getSysCode();
		this.description = resultCode.getMessage();
	}
	
	public void setFailResultWithMsg(PaymentResultCode resultCode, String message) {
		this.resultCode = resultCode;
		this.status = Status.FAIL;
		this.code = resultCode.getSysCode();
		this.description = message;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
}

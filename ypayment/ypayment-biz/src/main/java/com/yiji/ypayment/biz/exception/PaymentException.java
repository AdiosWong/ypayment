package com.yiji.ypayment.biz.exception;

import com.yiji.ypayment.facade.enums.PaymentResultCode;

/**
 * 
 * @author faZheng
 * 
 */
public class PaymentException extends RuntimeException {
	
	private static final long serialVersionUID = 8994257213908844139L;
	
	/**
	 * 错误代码,默认为未知错误
	 */
	private PaymentResultCode resultCode = PaymentResultCode.UNKNOWN_EXCEPTION;
	
	/**
	 * 全局错误码
	 */
	private String globalCode;
	
	/**
	 * 兼容纯错误信息，不含error code,errorArgs的情况
	 */
	private String errorMessage = null;
	
	public PaymentException(PaymentResultCode resultCode, String message) {
		super(message);
		this.resultCode = resultCode;
		this.globalCode = resultCode.getSysCode();
		this.errorMessage = message;
	}
	
	public PaymentException(PaymentResultCode resultCode, String globalCode, String message) {
		super(message);
		this.resultCode = resultCode;
		this.globalCode = globalCode;
		this.errorMessage = message;
	}
	
	public PaymentException(PaymentResultCode resultCode) {
		this.resultCode = resultCode;
		this.errorMessage = resultCode.getMessage();
		this.globalCode = resultCode.getSysCode();
	}
	
	public PaymentException(Throwable throwable) {
		super(throwable);
	}
	
	public String getGlobalCode() {
		return this.globalCode;
	}
	
	public void setGlobalCode(String globalCode) {
		this.globalCode = globalCode;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public PaymentResultCode getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(PaymentResultCode resultCode) {
		this.resultCode = resultCode;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}

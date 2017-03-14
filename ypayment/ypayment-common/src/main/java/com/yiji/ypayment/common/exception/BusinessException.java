package com.yiji.ypayment.common.exception;

/**
 * 服务层通用业务异常 Created by zhangpu on 2015/1/22.
 */
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = -5315988890873900342L;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	protected BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

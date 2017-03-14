package com.yiji.ypayment.facade.enums;

/**
 * 缴费转账状态
 * 
 * @author faZheng
 * 
 */
public enum TransferTradeStatus {
	
	/**
	 * 初始状态
	 */
	INIT("INIT", "初始状态"),
	
	/**
	 * 处理中
	 */
	PROCESSING("PROCESSING", "处理中"),
	
	/**
	 * 成功
	 */
	SUCCESS("SUCCESS", "成功"),
	
	/**
	 * 失败
	 */
	FAIL("FAIL", "失败");
	
	TransferTradeStatus(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static TransferTradeStatus byCode(String code) {
		for (TransferTradeStatus enums : TransferTradeStatus.values()) {
			if (code.equalsIgnoreCase(enums.getCode())) {
				return enums;
			}
		}
		return null;
	}
}

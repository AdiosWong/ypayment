package com.yiji.ypayment.biz.enums;

/**
 * 交易模式
 * 
 * @author faZheng
 * 
 */
public enum TransferTradeMode {
	
	/**
	 * 解冻转账
	 */
	TRANSFER_UNFREEZE("TRANSFER_UNFREEZE", "解冻转账"),
	
	/**
	 * 普通转账
	 */
	TRANSFER("TRANSFER", "普通转账"), ;
	
	TransferTradeMode(String code, String message) {
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
	
	public static TransferTradeMode byCode(String code) {
		for (TransferTradeMode enums : TransferTradeMode.values()) {
			if (code.equalsIgnoreCase(enums.getCode())) {
				return enums;
			}
		}
		return null;
	}
}

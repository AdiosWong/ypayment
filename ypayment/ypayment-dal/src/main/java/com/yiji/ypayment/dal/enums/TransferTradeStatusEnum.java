/*
* www.yiji.com Inc.
* Copyright (c) 2015 All Rights Reserved.
*/

/*
* 修订记录：
* faZheng 上午10:55:20 创建
*/

package com.yiji.ypayment.dal.enums;

/**
 * 交易状态枚举
 *
 * @author faZheng
 *
 */

public enum TransferTradeStatusEnum {
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
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 *
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private TransferTradeStatusEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 *
	 * @param code
	 * @return TransferTradeStatus
	 */
	public static TransferTradeStatusEnum getByCode(String code) {
		for (TransferTradeStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 *
	 * @return List<TransferTradeStatus>
	 */
	public static java.util.List<TransferTradeStatusEnum> getAllEnum() {
		java.util.List<TransferTradeStatusEnum> list = new java.util.ArrayList<TransferTradeStatusEnum>(values().length);
		for (TransferTradeStatusEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 *
	 * @return List<String>
	 */
	public static java.util.List<String> getAllEnumCode() {
		java.util.List<String> list = new java.util.ArrayList<String>(values().length);
		for (TransferTradeStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	/**
	 * 通过code获取msg
	 * @param code 枚举值
	 * @return
	 */
	public static String getMsgByCode(String code) {
		if (code == null) {
			return null;
		}
		TransferTradeStatusEnum _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getMessage();
	}
	
	/**
	 * 获取枚举code
	 * @param _enum
	 * @return
	 */
	public static String getCode(TransferTradeStatusEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}

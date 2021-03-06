/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:05:12 创建
 */
package com.yiji.ypayment.dal.enums;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public enum QuickPayStatusEnum {
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
	private QuickPayStatusEnum(String code, String message) {
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
	 * @return QuickPayStatusEnum
	 */
	public static QuickPayStatusEnum getByCode(String code) {
		for (QuickPayStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<QuickPayStatusEnum>
	 */
	public static java.util.List<QuickPayStatusEnum> getAllEnum() {
		java.util.List<QuickPayStatusEnum> list = new java.util.ArrayList<QuickPayStatusEnum>(values().length);
		for (QuickPayStatusEnum _enum : values()) {
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
		for (QuickPayStatusEnum _enum : values()) {
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
		QuickPayStatusEnum _enum = getByCode(code);
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
	public static String getCode(QuickPayStatusEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}

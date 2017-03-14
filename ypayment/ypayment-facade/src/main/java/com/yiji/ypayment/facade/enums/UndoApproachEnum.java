/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午7:01:16 创建
 */
package com.yiji.ypayment.facade.enums;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public enum UndoApproachEnum {
	
	MANUAL("MANUAL", "人工返销"),
	
	SYSTEM("SYSTEM", "系统返销"),
	
	/**
	 * 推荐方式，先系统返销，如果不支持系统返销，则人工返销
	 */
	RECOMMEND("RECOMMEND", "先系统后人工返销"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 *
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private UndoApproachEnum(String code, String message) {
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
	 * @return UndoApproachEnum
	 */
	public static UndoApproachEnum getByCode(String code) {
		for (UndoApproachEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<UndoApproachEnum>
	 */
	public static java.util.List<UndoApproachEnum> getAllEnum() {
		java.util.List<UndoApproachEnum> list = new java.util.ArrayList<UndoApproachEnum>(values().length);
		for (UndoApproachEnum _enum : values()) {
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
		for (UndoApproachEnum _enum : values()) {
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
		UndoApproachEnum _enum = getByCode(code);
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
	public static String getCode(UndoApproachEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}

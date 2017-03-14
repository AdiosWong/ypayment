/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng@yiji.com 2015年11月09日 上午9:05:10创建
 */
package com.yiji.ypayment.facade.enums;

import com.yjf.common.lang.enums.Messageable;

/**
 * 
 * 
 * @author faZheng@yiji.com
 * 
 */

public enum UndoPaymentStatusEnum implements Messageable {
	
	INIT("INIT", "待返销"),
	
	SUCCESS("SUCCESS", "返销成功"),
	
	HANGUP("HANGUP", "处理中"),
	
	FAIL("FAIL", "返销失败"),
	
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private UndoPaymentStatusEnum(String code, String message) {
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
	 * @return PaymentItemStatusEnum
	 */
	public static UndoPaymentStatusEnum getByCode(String code) {
		for (UndoPaymentStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<PaymentItemStatusEnum>
	 */
	public static java.util.List<UndoPaymentStatusEnum> getAllEnum() {
		java.util.List<UndoPaymentStatusEnum> list = new java.util.ArrayList<UndoPaymentStatusEnum>(values().length);
		for (UndoPaymentStatusEnum _enum : values()) {
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
		for (UndoPaymentStatusEnum _enum : values()) {
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
		UndoPaymentStatusEnum _enum = getByCode(code);
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
	public static String getCode(UndoPaymentStatusEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}

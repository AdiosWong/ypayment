/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年12月9日 下午2:01:06 创建
 */
    
package com.yiji.ypayment.facade.enums;

/**
 * 缴费类型（充值或者缴费）
 *
 * @author faZheng
 *
 */

public enum PaymentModelEnum {
	
	/**
	 * 话费充值
	 */
	RECHARGE("RECHARGE", "话费充值"),
	
	/**
	 * 水电气缴费
	 */
	PAYMENT("PAYMENT", "水电气缴费"),
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
	private PaymentModelEnum(String code, String message) {
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
	 * @return PaymentModelEnum
	 */
	public static PaymentModelEnum getByCode(String code) {
		for (PaymentModelEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 *
	 * @return List<PaymentModelEnum>
	 */
	public static java.util.List<PaymentModelEnum> getAllEnum() {
		java.util.List<PaymentModelEnum> list = new java.util.ArrayList<PaymentModelEnum>(values().length);
		for (PaymentModelEnum _enum : values()) {
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
		for (PaymentModelEnum _enum : values()) {
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
		PaymentModelEnum _enum = getByCode(code);
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
	public static String getCode(PaymentModelEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}

    
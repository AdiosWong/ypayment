/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午1:55:29 创建
 */
package com.yiji.ypayment.facade.enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public enum PaymentTypeEnum {
	
	/** 水电气是缴费类pamentType，其它为充值类RECHARGE */
	
	WATER("000010", "水费"),
	
	GAS("000020", "燃气费"),
	
	ELECTRICITY("000030", "电费"),
	
	MOBILE("000040", "手机话费"),
	
	RUBBISH("000060", "垃圾费");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 *
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private PaymentTypeEnum(String code, String message) {
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
	 * @return PaymentCategoryEnum
	 */
	public static PaymentTypeEnum getByCode(String code) {
		for (PaymentTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<PaymentCategoryEnum>
	 */
	public static java.util.List<PaymentTypeEnum> getAllEnum() {
		java.util.List<PaymentTypeEnum> list = new java.util.ArrayList<PaymentTypeEnum>(values().length);
		for (PaymentTypeEnum _enum : values()) {
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
		for (PaymentTypeEnum _enum : values()) {
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
		PaymentTypeEnum _enum = getByCode(code);
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
	public static String getCode(PaymentTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
	
	public static Map<String, String> paymentTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (PaymentTypeEnum _enum : values()) {
			map.put(_enum.code, _enum.message());
		}
		return map;
	}
	
}

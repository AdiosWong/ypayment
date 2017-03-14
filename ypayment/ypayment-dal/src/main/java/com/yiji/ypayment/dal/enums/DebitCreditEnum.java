/**
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */
package com.yiji.ypayment.dal.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 卡类型枚举值<br>
 * 1.<code>CREDIT</code>:信用卡.<br>
 * 2.<code>DEBIT</code>:借记卡.<br>
 * 3.<code>DEBIT_CREDIT</code>:借贷一体.
 * 
 */
public enum DebitCreditEnum {
	
	/** 信用卡 */
	CREDIT("CREDIT", "贷记卡"),
	
	/** 借记卡 */
	DEBIT("DEBIT", "借记卡"),
	
	/** 准贷记卡 */
	SEMI_CREDIT("SEMI_CREDIT", "准贷记卡"),
	
	/** 预付费卡 */
	PREPAID("PREPAID", "预付费卡"),
	
	/** 借贷一体卡 */
	DEBIT_CREDIT("DEBIT_CREDIT", "借贷一体"),

	/** 所有卡种 */
	ALL("ALL", "所有卡种");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>DebitCreditTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private DebitCreditEnum(String code, String message) {
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
	 * @return DebitCreditTypeEnum
	 */
	public static DebitCreditEnum getByCode(String code) {
		for (DebitCreditEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<DebitCreditTypeEnum>
	 */
	public static List<DebitCreditEnum> getAllEnum() {
		List<DebitCreditEnum> list = new ArrayList<DebitCreditEnum>();
		for (DebitCreditEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (DebitCreditEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}

	public boolean isInList(DebitCreditEnum... enums){
		for (DebitCreditEnum _enum : enums) {
			if (this ==_enum){
				return true;
			}
		}
		return false;
	}
}

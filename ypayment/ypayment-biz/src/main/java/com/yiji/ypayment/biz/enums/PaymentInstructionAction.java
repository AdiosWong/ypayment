/* www.yiji.com Inc.
* Copyright (c) 2015 All Rights Reserved.
*/

/*
 * 修订记录：
 * @author faZheng 上午9:47:24 创建
 */
package com.yiji.ypayment.biz.enums;

import com.yiji.ypayment.facade.enums.PaymentTypeEnum;

/**
 * 
 * @author faZheng
 * 
 */
public enum PaymentInstructionAction {
	
	QUERY_CONFIG("QUERY_CONFIG", "查询配置"),
	
	PAYMENT_RECHARGE("PAYMENT_RECHARGE", "缴费充值"),
	
	PAYMENT_WATER("PAYMENT_WATER", "缴水费"),
	
	PAYMENT_GAS("PAYMENT_GAS", "缴气费"),
	
	PAYMENT_ELECTRICITY("PAYMENT_ELECTRICITY", "缴电费"),
	
	PAYMENT_MOBILE("PAYMENT_MOBILE", "缴话费"),
	
	PAYMENT_TV("PAYMENT_TV", "缴有线电视费"),
	
	PAYMENT_RUBBISH("PAYMENT_RUBBISH", "缴垃圾费"),
	
	CREAT_BINDING("CREAT_BINDING", "缴费绑卡"),
	
	QUERY_BINDING("QUERY_BINDING", "查询缴费绑卡信息"),
	
	DELETE_BINDING("DELETE_BINDING", "解除缴费绑卡"),
	
	QUERY_RESOURCE_INFO("QUERY_RESOURCE_INFO", "查询缴费资源信息"),
	
	QUERY_PAY_COSTS("QUERY_PAY_COSTS", "查询欠费信息"),
	
	CHECK_PAYMENT_USER("CHECK_PAYMENT_USER", "查询缴费用户是否存在"),
	
	QUERY_ORDER_SATUS("QUERY_ORDER_SATUS", "查询缴费订单状态"),
	
	QUERY_UNDO_ORDER_SATUS("QUERY_UNDO_ORDER_SATUS", "查询返销订单状态"),
	
	MONEY_FREEZE("MONEY_FREEZE", "资金冻结"),
	
	MONEY_UNFREEZE("MONEY_UNFREEZE", "资金解冻"),
	
	MONEY_TRANSFER("MONEY_TRANSFER", "资金解冻转账"),
	
	QUERY_ORDER_INFO("QUERY_ORDER_INFO", "查询缴费订单详细信息"),
	
	UNDO_PAYMENT_ORDER("UNDO_PAYMENT_ORDER", "返销"),
	
	;
	
	/**
	 * 枚举值
	 */
	private final String code;
	
	/**
	 * 枚举描述
	 */
	private final String message;
	
	/**
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private PaymentInstructionAction(String code, String message) {
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
	 * @return YzzInstructionAction
	 */
	public static PaymentInstructionAction getByCode(String code) {
		for (PaymentInstructionAction _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<YzzInstructionAction>
	 */
	public static java.util.List<PaymentInstructionAction> getAllEnum() {
		java.util.List<PaymentInstructionAction> list = new java.util.ArrayList<PaymentInstructionAction>(
			values().length);
		for (PaymentInstructionAction _enum : values()) {
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
		for (PaymentInstructionAction _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	/**
	 * 通过code获取msg
	 * 
	 * @param code 枚举值
	 * @return
	 */
	public static String getMsgByCode(String code) {
		if (code == null) {
			return null;
		}
		PaymentInstructionAction _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getMessage();
	}
	
	/**
	 * 获取枚举code
	 * 
	 * @param _enum
	 * @return
	 */
	public static String getCode(PaymentInstructionAction _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
	
	public static PaymentInstructionAction convertByPaymentTypeEnum(PaymentTypeEnum paymentType) {
		PaymentInstructionAction paymentAction = null;
		if (PaymentTypeEnum.GAS == paymentType) {
			paymentAction = PaymentInstructionAction.PAYMENT_GAS;
		} else if (PaymentTypeEnum.ELECTRICITY == paymentType) {
			paymentAction = PaymentInstructionAction.PAYMENT_ELECTRICITY;
		} else if (PaymentTypeEnum.MOBILE == paymentType) {
			paymentAction = PaymentInstructionAction.PAYMENT_MOBILE;
		} else if (PaymentTypeEnum.RUBBISH == paymentType) {
			paymentAction = PaymentInstructionAction.PAYMENT_RUBBISH;
		} else if (PaymentTypeEnum.WATER == paymentType) {
			paymentAction = PaymentInstructionAction.PAYMENT_WATER;
		}
		return paymentAction;
	}
	
}

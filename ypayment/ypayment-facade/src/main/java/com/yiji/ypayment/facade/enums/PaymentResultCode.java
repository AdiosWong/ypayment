package com.yiji.ypayment.facade.enums;

/**
 * 错误码
 * 
 * @author faZheng
 * 
 */
public enum PaymentResultCode {
	
	DATA_EXCEPTION("DATA_EXCEPTION", "comn_00_0000", "数据库异常"),
	
	UNKNOWN_EXCEPTION("UNKNOWN_EXCEPTION", "comn_04_0000", "未知异常"),
	
	EXECUTE_FAIL("EXECUTE_FAIL", "S000_01_0000", "执行失败"),
	
	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "S000_01_0001", "执行成功"),
	
	INVAILD_PARAMETER("INVAILD_PARAMETER", "S000_01_0002", "参数错误"),

	// 缴费状态
	EXECUTE_COMPLETE("EXECUTE_COMPLETED", "S000_02_2003", "缴费处理完成"),
	
	RES_ROUTE_EXCEPTION("EXECUTE_FAIL", "S000_02_2003", "资源路由发生异常"),
	
	UNKNOWN_STATUS("UNKNOWN_STATUS", "S000_02_2004", "未知状态"),
	
	// 金额
	NOT_ENOUGH_BALANCE("NOT_ENOUGH_BALANCE", "S000_03_3001", "账户余额不足"),
	
	NOT_EQUAL_PAYABLES("NOT_EQUAL_PAYABLES", "S000_03_3002", "缴费金额不等于欠费总额"),
	
	FREEZE_EXCEPTION("FREEZE_EXCEPTION", "S000_03_3003", "冻结缴费金额失败"),
	
	UNFREEZE_EXCEPTION("UNFREEZE_EXCEPTION", "S000_03_3004", "解冻缴费金额失败"),
	
	PAY_ACCOUNT_NOT_EXIST("PAY_ACCOUNT_NOT_EXIST", "S000_03_3005", "缴费转入账户不存在"),
	
	// 业务规则
	PAYMENT_USER_NOT_ACTIVE("PAYMENT_USER_NOT_ACTIVE", "S000_04_4001", "未开通水电气缴费"),
	
	PAYMENT_BILL_AMOUNT_ZERO("PAYMENT_BILL_AMOUNT_ZERO", "S000_04_4002", "查询到缴费总金额为零"),
	
	PAYMENT_BILL_FAIL("PAYMENT_BILL_FAIL", "S000_04_4003", "查询欠费失败"),
	
	PAYMENT_BILL_EMPTY("PAYMENT_BILL_EMPTY", "S000_04_4004", "查询欠费结果为空"),
	
	CANNOT_SEND_PAYMENT_WITH_FAIL_PAYMENT("CANNOT_SEND_PAYMENT_WITH_FAIL_PAYMENT", "S000_04_4005", "有缴费失败或挂起时，不允许发起缴费"),
	
	PAYMENT_PAYBACK_FAIL("PAYMENT_PAYBACK_FAIL", "S000_04_4006", "返销失败"),
	
	PAYMENT_QUERY_AGENCY_CHANNEL_FAIL("PAYMENT_QUERY_AGENCYCHANNEL_FAIL", "S000_04_4007", "查询资源渠道路由失败"),
	
	PAYMENT_QUERY_RESOURCE_INST_FAIL("PAYMENT_QUERY_RESOURCE_INST_FAIL", "S000_04_4008", "查询资源列表失败"),
	
	PAYMENT_QUERY_USER_FAIL("PAYMENT_QUERY_USER_FAIL", "S000_04_4009", "查询用户是否存在失败"),
	
	PAYMENT_QUERY_ORDER_STATUS_FAIL("PAYMENT_QUERY_ORDER_STATUS_FAIL", "S000_04_4010", "查询订单状态失败"),
	
	PAYMENT_QUERY_BALANCE_FAIL("PAYMENT_QUERY_BALANCE_FAIL", "S000_04_4011", "查询备付金失败"),
	
	PAYMENT_QUERY_UNDO_ORDER_STATUS_FAIL("PAYMENT_QUERY_UNDO_ORDER_STATUS_FAIL", "S000_04_4012", "查询订单返销状态失败"),
	
	PAYMENT_QUERY_AGENCY_CHANNEL_EMPTY("PAYMENT_QUERY_AGENCY_CHANNEL_EMPTY", "S000_04_4013", "查询资源渠道路由为空"),
	
	PAYMENT_QUERY_RESOURCE_INST_EMPTY("PAYMENT_QUERY_RESOURCE_INST_EMPTY", "S000_04_4014", "查询资源列表为空"),
	
	PAYMENT_QUERY_ORDER_INFO_FAIL("PAYMENT_ORDER_INFO_FAIL", "S000_04_4015", "查询订单详细信息失败"),
	
	PAYMENT_QUERY_BINDING_INFO_FAIL("PAYMENT_QUERY_BINDING_INFO_FAIL", "S000_04_4016", "查询缴费绑卡信息失败"),
	
	USER_NOT_EXIST("USER_NOT_EXIST", "S000_04_4017", "用户不存在"),
	
	USER_ALREADY_BIND_CARD("USER_ALREADY_BIND_CARD", "S000_04_4018", "该用户已经绑卡"),
	
	NO_PAYMENT_ORDER("NO_PAYMENT_ORDER", "S000_04_4019", "缴费订单生成失败"),
	
	PAYMENT_NO_NOT_EXIST("PAYMENT_NO_NOT_EXIST", "S000_04_4020", "业务订单号不存在"),
	
	PACT_QUERY_FAIL("PACY_QUERY_FAIL", "S000_04_4021", "查询签约银行卡信息失败"),
	
	DEDUCT_DEPOSIT_FAIL("DEDUCT_DEPOSIT_FAIL", "S000_04_4022", "代扣失败"),
	
	CHANNEL_ROUTE_EMPUTY("CHANNEL_ROUTE_EMPUTY", "S000_04_4023", "查询代扣路由渠道为空"),
	
	USER_NOT_BIND_CARD("USER_NOT_BIND_CARD", "S000_04_4024", "该用户还未绑卡"),
	;
	
	private String code;
	private String sysCode;
	private String message;
	
	PaymentResultCode(String code, String sysCode, String message) {
		this.code = code;
		this.sysCode = sysCode;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getSysCode() {
		return sysCode;
	}
	
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static PaymentResultCode byCode(String code) {
		for (PaymentResultCode enums : PaymentResultCode.values()) {
			if (code.equalsIgnoreCase(enums.getCode())) {
				return enums;
			}
		}
		return null;
	}
	
}

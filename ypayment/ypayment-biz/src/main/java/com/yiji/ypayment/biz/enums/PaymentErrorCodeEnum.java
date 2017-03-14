package com.yiji.ypayment.biz.enums;

/**
 * 错误的枚举类型 。
 * 
 * 注意在比较ErrorEnum时，不要直接使用equals方法。调用code()让后用String的equals方法。
 * 因为可能会有一个code对应两个枚举类型的情况。
 * 
 * @author liqing
 * 
 */
public enum PaymentErrorCodeEnum {
	
	/** 成功 */
	success("comn_04_0001"),
	
	/** 发送给缴费系统的请求参数没有缴费机构参数 */
	args_lack_inst("S042_01_0100"),
	
	/** 发送给缴费系统的请求参数没有用户号码信息参数 */
	args_lack_userCode("S042_01_0101"),
	
	/** 缴费信息不匹配，如缴费用户号数与金额组的数量 */
	args_wrong_info("S042_01_0102"),
	
	/** 发送给缴费系统的请求参数非法 */
	args_illegal("S042_01_0103"),
	
	/** 缴费金额错误 */
	args_wrong_money("S042_01_0104"),
	
	/** 发送给缴费系统的请求参数没有订单明细Id参数 */
	args_lack_serialNumber("S042_01_0111"),
	
	/** 缴费类型参数不存在 */
	args_lack_paymentType("S042_01_0112"),
	
	/** 外部流水号不存在 */
	args_lack_3thSystemSerialNumber("S042_01_0113"),
	
	/** 重复交易 */
	args_duplicate_request("S042_01_0114"),
	
	/** 找不到记录 */
	args_can_not_find_record("S042_01_0115"),
	
	/** 缴费机构不存在或错误 */
	inst_inst_not_exist("S042_02_0200"),
	
	/** 缴费用户不存在 */
	inst_user_not_exist("S042_02_0201"),
	
	/** 没有欠费信息 */
	inst_debts_not_exist("S042_02_0202"),
	
	/** 没有抄表 */
	inst_debts_not_calculated("S042_02_0203"),
	
	/** 请求时间格式错误 */
	inst_wrongformat_requestTime("S042_02_0204"),
	
	/** 缴费信息不正确，如通讯流水、业务编码、缴费号码或者金额不正确 */
	inst_wrong_info("S042_02_0205"),
	
	/** 流水号错误 */
	inst_wrong_serialNumer("S042_02_0206"),
	
	/** 请求流水号重复 */
	inst_duplicate_serialNumer("S042_02_0207"),
	
	/** 备付金余额不足 */
	inst_unsufficient_reserveMoney("S042_02_0208"),
	
	/** 查询流水号/订单号不存在 */
	inst_serialNumer_not_exist("S042_02_0209"),
	
	/** 远端未知错误 */
	inst_unknown_error("S042_02_0210"),
	
	/** 不支持的操作 */
	inst_unsupported_operation("S042_02_0211"),
	
	/** 对方拒绝，可能是对方处理队列满等 */
	inst_rejected_request("S042_02_0212"),
	
	/** 用户上月未交费 */
	inst_earlierDebts_not_clear("S042_02_0213"),
	
	/** 缴费、收费方式错误/支票错误 */
	inst_wrong_payManner("S042_02_0214"),
	
	/** 缴费卡号重复 */
	inst_duplicate_payCardNo("S042_02_0215"),
	
	/** 操作员错误 */
	inst_wrong_operator("S042_02_0216"),
	
	/** 发票错误 */
	inst_wrong_invoice("S042_02_0217"),
	
	/** 卡或表故障 */
	inst_cardOrWatch_fail("S042_02_0219"),
	
	/** 预付费交易金额不足 */
	inst_unsufficient_predepositMoney("S042_02_0220"),
	
	/** 密码错误 */
	inst_wrong_password("S042_02_0221"),
	
	/** 业务状态异常 */
	inst_abnormal_businessState("S042_02_0222"),
	
	/** 超过限定金额 */
	inst_violate_moneyLimit("S042_02_0223"),
	
	/** 尚未开通此业务 */
	inst_business_not_launch("S042_02_0224"),
	
	/** 结算方式错误 */
	inst_wrong_settle_approach("S042_02_0225"),
	
	/** 不在缴费时间段 */
	inst_not_in_serviceTime("S042_02_0227"),
	
	/** 缴费用户账户状态不正常 */
	inst_account_abnormal("S042_02_0228"),
	
	/** 缺少参数 */
	inst_lack_args("S042_02_0229"),
	
	/** 金额错误 */
	inst_wrong_amount("S042_02_0230"),
	
	/** 缴费未成功导致的失败。 */
	inst_caused_by_pay_bill_fail("S042_02_0231"),
	
	/** 错误的参数 */
	inst_wrong_args("S042_02_0232"),
	
	/** 机构已知，但上层用户不关注的错误 */
	inst_known_but_ignore_error("S042_02_0233"),
	
	/** 超出了合法时间范围：如返销只能在当日 */
	inst_beyond_legal_period("S042_02_0234"),
	
	/** 已打发票不能进行当前操作。 */
	inst_caused_by_print_invoice_already("S042_02_0235"),
	
	/** 不是最后一个月的缴费不能取消。 */
	inst_not_the_last_one("S042_02_0236"),
	
	/** 必须到资源方营业厅缴费。 */
	inst_must_pay_at_inst("S042_02_0237"),
	
	/** 重复返销。 */
	inst_already_back_sell("S042_02_0237"),
	
	/** 产品维护中或缺货 */
	inst_product_maintain("S042_02_0239"),
	
	/** 缴费结果报文解析错误 */
	parse_resp_fail("S042_04_0400"),
	
	/** 请求的报文远端解析错误 */
	parse_req_fail("S042_04_0401"),
	
	/** 远端响应的交易码和接口文档不一致 */
	parse_resp_transCode_not_match("S042_04_0402"),
	
	/** 缴费系统发生内部错误(向机构发送请求前) */
	sys_unknow_beforeSend("S042_05_0500"),
	
	/** 缴费系统到第三方机构发生网络错误或者配置错误(发送中) */
	sys_unknow_whenSend("S042_05_0501"),
	
	/** 缴费系统发生网络或内部错误(发送后) */
	sys_unknow_afterSend("S042_05_0500"),
	
	/** 内部错误 */
	sys_unknown_error("S042_05_0500"),
	
	/** 未知异常 */
	sys_unknow_error("S042_05_0500"),
	
	/** 第三方系统内部错误 */
	sys_3thSystem_error("S042_05_0503"),
	
	/** 生成给资源方的报文是出错 */
	sys_format_req_fail("S042_05_0504"),
	
	/** 处理中 */
	sys_processing("S042_05_0505"),
	
	/** 业务ID未配置或者配置错误 */
	cfg_wrong_busiId("S042_06_0600"),
	
	/** 到第三方请求地址配置错误 */
	cfg_wrong_instUrl("S042_06_0603"),
	
	/** 绑定ip错误 */
	cfg_wrong_bindingIp("S042_06_0604"),
	
	/** 请求时间生成错误 */
	genArgs_wrong_requestTime("S042_07_0700"),
	
	/** 请求的机构在系统中不存在 */
	genArgs_inst_not_exist("S042_07_0704"),
	
	/** 请求的类型在系统中的机构中不存在 */
	genArgs_paymentType_not_existWithInst("S042_07_0705"),
	
	/** 请求的类型在系统中的机构中不存在 */
	genArgs_instOrType_not_exist("S042_07_0706");
	
	private String code;
	
	PaymentErrorCodeEnum(String code) {
		this.code = code;
	}
	
	/**
	 * 根据错误码返回枚举
	 * @param code 错误码
	 * @return
	 */
	public static PaymentErrorCodeEnum fromString(String code) {
		if (code == null) {
			code = "";
		}
		PaymentErrorCodeEnum[] errors = PaymentErrorCodeEnum.values();
		for (PaymentErrorCodeEnum error : errors) {
			if (code.equals(error.code)) {
				return error;
			}
		}
		throw new IllegalArgumentException("no Error has the code:" + code);
	}
	
	/**
	 * @return 枚举对应的错误码
	 */
	public String code() {
		return code;
	}
	
}

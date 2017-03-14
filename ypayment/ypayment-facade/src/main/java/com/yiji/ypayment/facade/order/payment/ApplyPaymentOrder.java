/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月22日 上午12:30:38创建
 */
package com.yiji.ypayment.facade.order.payment;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.yiji.ypayment.facade.common.PaymentOrderBase;
import com.yiji.ypayment.facade.enums.PaymentModelEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.info.payment.PaymentItem;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.lang.validator.MoneyConstraint;
import com.yjf.common.service.OrderCheckException;
import com.yjf.common.util.StringUtils;

/**
 * 
 * 
 * @author xinqing@yiji.com
 * 
 */
public class ApplyPaymentOrder extends PaymentOrderBase {
	
	private static final long serialVersionUID = -7209433672098041815L;
	
	/**
	 * 签约绑卡号(不使用绑定功能，不填)
	 */
	@Length(max=40)
	private String contractNo;
	
	/**
	 * 外部订单号
	 */
	@NotBlank
	@Length(max=40)
	private String outBizNo;
	
	/**
	 * 缴费类型
	 */
	@NotNull
	private PaymentTypeEnum paymentType;
	
	/**
	 * 缴费模型
	 */
	@NotNull
	private PaymentModelEnum paymentModel;
	
	/**
	 * 缴费金额
	 */
	@NotNull
	@MoneyConstraint(min = 1)
	private Money paymentAmount;
	
	/**
	 * 用户卡号(水电气卡号，充值为手机号；不使用绑定功能，必填)
	 */
	@Length(max=32)
	private String userCode;
	
	/**
	 * 资源代码(不使用绑定功能，必填)
	 */
	@Length(max=32)
	private String resourceCode;
	
	/**
	 * 缴费列表(缴费列表为多个，充值默认只有一个)
	 */
	@NotEmpty
	private List<PaymentItem> paymentItems = Lists.newArrayList();
	
	/**
	 * 门店号(易生活门店缴电费必传)
	 */
	@Length(max=35)
	private String storeId;
	
	@Override
	public void check() {
		super.check();
		if (StringUtils.isBlank(contractNo) && paymentModel == PaymentModelEnum.PAYMENT) {
			Assert.hasText(userCode, "用户卡号[userCode]不能为空");
			Assert.hasText(resourceCode, "资源代码[resourceCode]不能为空");
		}
		if (paymentModel == PaymentModelEnum.RECHARGE) {
			Assert.hasText(userCode, "话费充值时，用户卡号[userCode]不能为空");
		}
		if (PaymentModelEnum.RECHARGE == paymentModel) {
			if (PaymentTypeEnum.MOBILE != paymentType) {
				throw new OrderCheckException("paymentModel", "缴费模型与缴费类型不匹配");
			}
		} else {
			if (PaymentTypeEnum.MOBILE == paymentType) {
				throw new OrderCheckException("paymentModel", "缴费模型与缴费类型不匹配");
			}
		}
		
		Money totalMoney = new Money(0);
		for (PaymentItem paymentItem : paymentItems) {
			Assert.hasText(paymentItem.getPaymentOrderNo(), "子订单号[paymentOrderNo]不能为空");
			Assert.isTrue(paymentItem.getPaymentOrderNo().length() <= 40, "子订单号[paymentOrderNo]长度不能超过40位");
			Assert.notNull(paymentItem.getPaymentType(), "子订单缴费类型[paymentType]不能为空");
			Assert.isTrue(paymentItem.getAmount().getCent() > 0, "子订单缴费金额必须大于0");
			totalMoney.addTo(paymentItem.getAmount());
			if (PaymentTypeEnum.GAS != paymentType) {
				if (paymentType != paymentItem.getPaymentType()) {
					throw new OrderCheckException("paymentType", "子缴费类型与主缴费类型不匹配");
				}
			} else {
				if (paymentType != paymentItem.getPaymentType()
					&& paymentItem.getPaymentType() != PaymentTypeEnum.RUBBISH) {
					throw new OrderCheckException("paymentType", "子缴费类型与主缴费类型不匹配");
				}
			}
		}
		if (!paymentAmount.equals(totalMoney)) {
			throw new OrderCheckException("paymentAmount", "缴费金额不等于子订单金额之和");
		}
	}
	
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getOutBizNo() {
		return this.outBizNo;
	}
	
	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}
	
	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}
	
	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}
	
	public Money getPaymentAmount() {
		return this.paymentAmount;
	}
	
	public void setPaymentAmount(Money paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	public String getUserCode() {
		return this.userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getResourceCode() {
		return this.resourceCode;
	}
	
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	
	public List<PaymentItem> getPaymentItems() {
		return this.paymentItems;
	}
	
	public void setPaymentItems(List<PaymentItem> paymentItems) {
		this.paymentItems = paymentItems;
	}
	
	public PaymentModelEnum getPaymentModel() {
		return this.paymentModel;
	}
	
	public void setPaymentModel(PaymentModelEnum paymentModel) {
		this.paymentModel = paymentModel;
	}
	
	public String getStoreId() {
		return this.storeId;
	}
	
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年12月7日 上午9:53:04 创建
 */
    
package com.yiji.ypayment.biz.remote.order;

import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.OrderBase;
import com.yjf.common.util.ToString;

/**
 * 缴费order
 *
 * @author faZheng
 *
 */

public class DepositPaymentOrder extends OrderBase{
	
	private static final long serialVersionUID = -3217943324714337849L;

	/**
	 * 外部订单号
	 */
	private String outBizNo;
	
	/**
	 * 易极付用户ID
	 */
	private String userId;
	
	/**
	 * 缴费金额
	 */
	private Money paymentAmount;
	
	/**
	 * 优惠金额
	 */
	private Money payAmount;
	
	/**
	 * 是否有优惠
	 */
	private FavourableEnum favourable = FavourableEnum.FALSE;
	
	/**
	 * 缴费类型
	 */
	@NotNull
	private PaymentTypeEnum paymentType;
	
	/**
	 * 门店号
	 */
	private String storeId;

	@Override
	public void check() {
		super.check();
		Assert.hasText(outBizNo, "业务编码[outBizNo]不能为空");
		Assert.notNull(this.paymentAmount, "缴费金额不能为空");
		Assert.isTrue(this.paymentAmount.getCent() > 0l, "缴费金额小于或等于0元");
		if(favourable == FavourableEnum.TRUE ){
			Assert.notNull(this.payAmount, "优惠金额不能为空");
			Assert.isTrue(this.payAmount.getCent() > 0l, "优惠金额小于或等于0元");
		}
	}
	
	public String getOutBizNo() {
		return this.outBizNo;
	}

	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Money getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(Money paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Money getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Money payAmount) {
		this.payAmount = payAmount;
	}

	public FavourableEnum getFavourable() {
		return this.favourable;
	}

	public void setFavourable(FavourableEnum favourable) {
		this.favourable = favourable;
	}

	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getStoreId() {
		return this.storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

    
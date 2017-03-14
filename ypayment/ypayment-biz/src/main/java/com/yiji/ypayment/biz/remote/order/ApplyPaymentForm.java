/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年12月17日 上午10:53:31 创建
 */
    
package com.yiji.ypayment.biz.remote.order;

import org.springframework.util.Assert;

import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.facade.enums.PayWayEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.order.payment.ApplyPaymentOrder;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.ToString;

/**
 * 缴费order
 *
 * @author faZheng
 *
 */

public class ApplyPaymentForm extends ApplyPaymentOrder{
	
	private static final long serialVersionUID = 6891489775574613509L;

	/**
	 * 支付方式(默认余额支付)
	 */
	private PayWayEnum payWay = PayWayEnum.BY_BALANCE;
	
	/**
	 * 签约号
	 */
	private String pactNo;
	
	/**
	 * 是否有优惠
	 */
	private FavourableEnum favourable = FavourableEnum.FALSE;
	
	/**
	 * 优惠金额
	 */
	private Money payAmount;
	
	@Override
	public void check() {
		super.check();
		if (FavourableEnum.TRUE == this.favourable) {
			Assert.isTrue(PaymentTypeEnum.MOBILE == getPaymentType(), "有优惠时，业务类型必须为充值");
			Assert.notNull(this.payAmount, "优惠金额不能为空");
			Assert.isTrue(this.payAmount.getCent() > 0l, "优惠金额小于或等于0元");
		} 
		if (PayWayEnum.BY_DEPOSIT == payWay) {
			Assert.hasText(pactNo, "支付方式为代扣时，签约号[pactNo]不能为空");
		}
	}
	
	public String getPactNo() {
		return this.pactNo;
	}

	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}

	public PayWayEnum getPayWay() {
		return this.payWay;
	}

	public void setPayWay(PayWayEnum payWay) {
		this.payWay = payWay;
	}
	
	public FavourableEnum getFavourable() {
		return this.favourable;
	}

	public void setFavourable(FavourableEnum favourable) {
		this.favourable = favourable;
	}

	public Money getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Money payAmount) {
		this.payAmount = payAmount;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

    
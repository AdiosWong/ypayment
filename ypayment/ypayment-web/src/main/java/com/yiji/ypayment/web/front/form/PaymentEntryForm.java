package com.yiji.ypayment.web.front.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.facade.enums.PaymentModelEnum;
import com.yjf.common.service.OrderBase;
import com.yjf.common.service.OrderCheckException;
import com.yjf.common.util.ToString;

public class PaymentEntryForm extends OrderBase {
	
	/**
	 * UID
	 */
	private static final long serialVersionUID = 8250338821814840340L;
	
	/** 入口编码 */
	@NotEmpty
	private String inlet;
	
	/** 平台标识（易品汇、报销等等） */
	@NotEmpty
	private String platformType;
	
	/** 用户id */
	@NotEmpty
	private String userId;
	
	/** 缴费类型（话费充值、 水电气缴费） **/
	@NotNull
	private PaymentModelEnum paymentModel;
	
	/**
	 * 是否有优惠
	 */
	@NotNull
	private FavourableEnum favourable = FavourableEnum.FALSE;
	
	/**
	 * 优惠列表
	 */
	private String chargeItems;
	
	/** 系统时间 (openapi自动生成,格式：yyyy-MM-dd HH:mm:ss) */
	@NotEmpty
	private String systemTime;
	
	@Override
	public void check() throws OrderCheckException {
		if (favourable.equals(FavourableEnum.TRUE)) {
			if (chargeItems.isEmpty()) {
				throw new IllegalArgumentException("充值有优惠时时，优惠列表不能为空");
			}
		}
	}
	
	public String getPlatformType() {
		return this.platformType;
	}
	
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public PaymentModelEnum getPaymentModel() {
		return this.paymentModel;
	}

	public void setPaymentModel(PaymentModelEnum paymentModel) {
		this.paymentModel = paymentModel;
	}

	public String getSystemTime() {
		return systemTime;
	}
	
	public void setSystemTime(String systemTime) {
		this.systemTime = systemTime;
	}
	
	public FavourableEnum getFavourable() {
		return this.favourable;
	}

	public void setFavourable(FavourableEnum favourable) {
		this.favourable = favourable;
	}

	public String getChargeItems() {
		return this.chargeItems;
	}

	public void setChargeItems(String chargeItems) {
		this.chargeItems = chargeItems;
	}
	
	public String getInlet() {
		return this.inlet;
	}
	
	public void setInlet(String inlet) {
		this.inlet = inlet;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

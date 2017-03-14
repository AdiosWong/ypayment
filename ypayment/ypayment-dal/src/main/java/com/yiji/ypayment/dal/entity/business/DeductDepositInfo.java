/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午6:03:15 创建
 */
package com.yiji.ypayment.dal.entity.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import com.yiji.ypayment.dal.entity.AbstractEntity;
import com.yiji.ypayment.dal.enums.DepositStatusEnum;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.ToString;

/**
 * 代扣信息表
 *
 * @author xinqing@yiji.com
 *
 */
@Entity
@Table(name = "deduct_deposit_info", indexes = { @Index(name = "deposit_biz_no_index", columnList = "deposit_biz_no",
		unique = true) })
public class DeductDepositInfo extends AbstractEntity {

	private static final long serialVersionUID = -5478193451212601835L;
	
	/**
	 * 易极付会员ID
	 */
	@Column(name = "user_id", nullable = false, length = 32, columnDefinition = "varchar(32) comment '易极付会员ID'")
	private String userId;
	
	/**
	 * 代扣订单号
	 */
	@Column(name = "deposit_biz_no", nullable = false, length = 40, columnDefinition = "varchar(40) comment '代扣订单号'")
	private String depositBizNo;
	
	/**
	 * 代扣支付流水
	 */
	@Column(name = "deposit_no", length = 40, columnDefinition = "varchar(40) comment '代扣支付流水'")
	private String depositNo;
	
	/**
	 * 代扣金额
	 */
	@Column(name = "deposit_amount", columnDefinition = "decimal(17,0) DEFAULT 0 comment '代扣金额'")
	private Money depositAmount;
	
	/**
	 * 实际入账金额 
	 */
	@Column(name = "act_amount", columnDefinition = "decimal(17,0) DEFAULT 0 comment '实际入账金额 '")
	private Money actAmount;
	
	/**
	 * 银行卡号
	 */
	@com.yiji.common.security.annotation.Index
	@Column(name = "card_no", length = 35, columnDefinition = "varchar(35) comment '银行卡号'")
	private String cardNo;
	
	/**
	 * 银行名称
	 */
	@Column(name = "bank_name", length = 256, columnDefinition = "varchar(256) comment '银行名称'")
	private String bankName;
	
	/** 
	 * 代扣状态 
	 */
	@Column(name = "deposit_status", length = 40, columnDefinition = "varchar(40) comment '代扣状态 '")
	@Enumerated(EnumType.STRING)
	private DepositStatusEnum depositStatus;
	
	/**
	 * 备注
	 */
	@Column(name = "memo", length = 128, columnDefinition = "varchar(128) comment '备注'")
	private String memo;
	
	/**
	 * gid
	 */
	@Column(name = "gid", length = 40, columnDefinition = "varchar(40) comment 'gid'")
	private String gid;
	
	/**
	 * 索引
	 */
	@Column(name = "data_index", length = 40, columnDefinition = "varchar(40) comment '索引'")
	private String deductDepositInfoIndex;
	
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepositBizNo() {
		return this.depositBizNo;
	}

	public void setDepositBizNo(String depositBizNo) {
		this.depositBizNo = depositBizNo;
	}

	public String getDepositNo() {
		return this.depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}

	public Money getDepositAmount() {
		return this.depositAmount;
	}

	public void setDepositAmount(Money depositAmount) {
		if (depositAmount == null) {
			this.depositAmount = new Money(0);
		} else {
			this.depositAmount = depositAmount;
		}
	}

	public Money getActAmount() {
		return this.actAmount;
	}

	public void setActAmount(Money actAmount) {
		if (actAmount == null) {
			this.actAmount = new Money(0);
		} else {
			this.actAmount = actAmount;
		}
	}

	@ToString.Maskable
	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public DepositStatusEnum getDepositStatus() {
		return this.depositStatus;
	}

	public void setDepositStatus(DepositStatusEnum depositStatus) {
		this.depositStatus = depositStatus;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getGid() {
		return this.gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}
	
	public String getDeductDepositInfoIndex() {
		return this.deductDepositInfoIndex;
	}

	public void setDeductDepositInfoIndex(String deductDepositInfoIndex) {
		this.deductDepositInfoIndex = deductDepositInfoIndex;
	}

	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

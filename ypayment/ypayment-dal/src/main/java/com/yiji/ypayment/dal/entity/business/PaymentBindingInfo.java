package com.yiji.ypayment.dal.entity.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import com.yiji.ypayment.dal.entity.AbstractEntity;
import com.yiji.ypayment.facade.enums.PaymentValidStatus;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yjf.common.util.ToString;

/**
 * 缴费绑定信息表
 * 
 * @author faZheng
 * 
 */
@Entity
@Table(name = "payment_binding_info", indexes = { @Index(name = "payment_contract_no_index",
		columnList = "contract_no", unique = true) })
public class PaymentBindingInfo extends AbstractEntity {
	
	private static final long serialVersionUID = 6089309114142513203L;
	
	/**
	 * 易极付用户ID
	 */
	@Column(name = "user_id", length = 32, columnDefinition = "varchar(32) comment '易极付会员ID'")
	private String userId;
	
	/**
	 * 缴费签约号
	 */
	@Column(name = "contract_no", length = 45, columnDefinition = "varchar(45) comment '缴费签约号'")
	private String contractNo;
	
	/**
	 * 资源代码
	 */
	@Column(name = "resource_code", length = 32, columnDefinition = "varchar(32) comment '资源编码'")
	private String resourceCode;
	
	/**
	 * 资源名称
	 */
	@Column(name = "resource_name", length = 128, columnDefinition = "varchar(128) comment '资源名称'")
	private String resourceName;
	
	/**
	 * 用户卡号
	 */
	@Column(name = "user_code", length = 32, columnDefinition = "varchar(32) comment '用户卡号'")
	private String userCode;
	
	/**
	 * 用户名
	 */
	@Column(name = "user_name", length = 32, columnDefinition = "varchar(32) comment '用户名'")
	private String userName;
	
	/**
	 * 缴费类型
	 */
	@Column(name = "payment_type", length = 20, columnDefinition = "varchar(20) comment '缴费类型'")
	@Enumerated(EnumType.STRING)
	private PaymentTypeEnum paymentType;
	
	/**
	 * 状态
	 */
	@Column(name = "status", length = 20, columnDefinition = "varchar(20) comment '状态'")
	@Enumerated(EnumType.STRING)
	private PaymentValidStatus status;
	
	/**
	 * 缴费来源
	 */
	@Column(name = "pay_from", length = 48, columnDefinition = "varchar(48) comment '缴费来源'")
	private String payFrom;
	
	/**
	 * 备注
	 */
	@Column(name = "memo", length = 128, columnDefinition = "varchar(128) comment '备注'")
	private String memo;
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getResourceCode() {
		return this.resourceCode;
	}
	
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	
	public String getResourceName() {
		return this.resourceName;
	}
	
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	public String getUserCode() {
		return this.userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public PaymentTypeEnum getPaymentType() {
		return this.paymentType;
	}
	
	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getPayFrom() {
		return this.payFrom;
	}
	
	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
	}
	
	public String getMemo() {
		return this.memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
	public PaymentValidStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(PaymentValidStatus status) {
		this.status = status;
	}
	
}

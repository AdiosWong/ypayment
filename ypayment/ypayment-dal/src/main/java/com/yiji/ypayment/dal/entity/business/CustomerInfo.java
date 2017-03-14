package com.yiji.ypayment.dal.entity.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yiji.common.security.annotation.Index;
import com.yiji.ypayment.dal.entity.AbstractEntity;
import com.yjf.common.lang.enums.CertTypeEnum;
import com.yjf.common.service.enums.customer.UserStatusEnum;
import com.yjf.common.service.enums.customer.UserTypeEnum;
import com.yjf.common.util.ToString;

/**
 * 用户信息表
 * 
 * @author faZheng
 * 
 */
@Entity
@Table(name = "customer_info")
public class CustomerInfo extends AbstractEntity {
	
	private static final long serialVersionUID = -7719651201944937996L;
	
	/**
	 * 易极付会员ID
	 */
	@Column(name = "user_id", length = 32, columnDefinition = "varchar(32) comment '易极付会员ID'")
	private String userId;
	
	/**
	 * 用户名
	 */
	@Column(name = "user_name", length = 128, columnDefinition = "varchar(128) comment '用户名'")
	private String userName;
	
	/**
	 * 真实姓名
	 */
	@Column(name = "real_name", length = 128, columnDefinition = "varchar(128) comment '真实姓名'")
	private String realName;
	
	/**
	 * 证件类型
	 */
	@Column(name = "cert_type", length = 20, columnDefinition = "varchar(20) comment '证件类型'")
	@Enumerated(EnumType.STRING)
	private CertTypeEnum certType;
	
	/**
	 * 身份证号
	 */
	@Index
	@Column(name = "cert_no", length = 45, columnDefinition = "varchar(45) comment '身份证号码'")
	private String certNo;
	
	/**
	 * 手机号
	 */
	@Index
	@Column(name = "mobile_phone_number", length = 20, columnDefinition = "varchar(20) comment '手机号'")
	private String mobilePhoneNumber;
	
	/**
	 * 地址
	 */
	@Column(name = "address", length = 256, columnDefinition = "varchar(256) comment '地址'")
	private String address;
	
	/**
	 * 用户类型
	 */
	@Column(name = "customer_type", length = 20, columnDefinition = "varchar(20) comment '用户类型  P:个人用户, B:企业用户'")
	@Enumerated(EnumType.STRING)
	private UserTypeEnum customerType;
	
	/**
	 * 职业
	 */
	@Column(name = "profession", length = 128, columnDefinition = "varchar(128) comment '职业'")
	private String profession;
	
	/**
	 * 状态
	 */
	@Column(name = "status", length = 20, columnDefinition = "varchar(20) comment '状态, W:未激活, Q:快速注册用户, T:正常用户'")
	@Enumerated(EnumType.STRING)
	private UserStatusEnum status;
	
	/**
	 * 备注
	 */
	@Column(name = "memo", length = 128, columnDefinition = "varchar(128) comment '备注'")
	private String memo;
	
	/**
	 * 索引
	 */
	@Column(name = "data_index", length = 40, columnDefinition = "varchar(40) comment '索引'")
	private String customerInfoIndex;
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getRealName() {
		return this.realName;
	}
	
	@ToString.Maskable
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@ToString.Maskable
	public String getCertNo() {
		return this.certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	@ToString.Maskable
	public String getMobilePhoneNumber() {
		return this.mobilePhoneNumber;
	}
	
	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public CertTypeEnum getCertType() {
		return this.certType;
	}
	
	public void setCertType(CertTypeEnum certType) {
		this.certType = certType;
	}
	
	public UserTypeEnum getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(UserTypeEnum customerType) {
		this.customerType = customerType;
	}
	
	public String getProfession() {
		return this.profession;
	}
	
	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	public UserStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(UserStatusEnum status) {
		this.status = status;
	}
	
	public String getMemo() {
		return this.memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getCustomerInfoIndex() {
		return this.customerInfoIndex;
	}

	public void setCustomerInfoIndex(String customerInfoIndex) {
		this.customerInfoIndex = customerInfoIndex;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

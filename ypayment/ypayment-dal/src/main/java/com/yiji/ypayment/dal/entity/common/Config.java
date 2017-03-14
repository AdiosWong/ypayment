package com.yiji.ypayment.dal.entity.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yiji.ypayment.dal.entity.AbstractEntity;
import com.yiji.ypayment.dal.enums.ConfigKey;

/**
 * 系统配置信息
 */
@Entity
@Table(name = "sys_config")
public class Config extends AbstractEntity {
	
	private static final long serialVersionUID = 795009995530923946L;
	
	/**
	 * 键
	 */
	@Column(name = "config_key", length = 40, nullable = false, columnDefinition = "varchar(40) comment'键'")
	@Enumerated(EnumType.STRING)
	private ConfigKey configKey;
	
	/**
	 * 值
	 */
	@Column(name = "config_value", length = 1000, columnDefinition = "varchar(1000) comment'值'")
	private String configValue;
	
	/**
	 * 备注
	 */
	@Column(name = "memo", length = 128, columnDefinition = "varchar(128) comment'备注'")
	private String memo;
	
	public ConfigKey getConfigKey() {
		return configKey;
	}
	
	public void setConfigKey(ConfigKey configKey) {
		this.configKey = configKey;
	}
	
	public String getConfigValue() {
		return configValue;
	}
	
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}

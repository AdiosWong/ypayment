/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年11月17日 下午5:07:39 创建
 */

package com.yiji.ypayment.dal.enums;


/**
 *
 *
 * @author faZheng
 *
 */

public enum ConfigKey {
	
	/**
	 * 话费充值资源代码
	 */
	MOBILE_RESOURCE_CODE("MOBILE_RESOURCE_CODE", "话费充值资源代码"),
	
	/**
	 * 话费充值资源名称
	 */
	MOBILE_RESOURCE_NAME("MOBILE_RESOURCE_NAME", "话费充值资源名称"), ;
	
	/** 枚举值 */
	private final String key;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 *
	 * @param key 枚举值
	 * @param message 枚举描述
	 */
	private ConfigKey(String key, String message) {
		this.key = key;
		this.message = message;
	}
	
	/**
	 * @return Returns the key.
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Returns the key.
	 */
	public String key() {
		return key;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}
	
	/**
	 * 通过枚举<code>key<keye>获得枚举
	 *
	 * @param key
	 * @return ConfigKey
	 */
	public static ConfigKey getByKey(String key) {
		for (ConfigKey _enum : values()) {
			if (_enum.getKey().equals(key)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 *
	 * @return List<ConfigKey>
	 */
	public static java.util.List<ConfigKey> getAllEnum() {
		java.util.List<ConfigKey> list = new java.util.ArrayList<ConfigKey>(values().length);
		for (ConfigKey _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 *
	 * @return List<String>
	 */
	public static java.util.List<String> getAllEnumKey() {
		java.util.List<String> list = new java.util.ArrayList<String>(values().length);
		for (ConfigKey _enum : values()) {
			list.add(_enum.key());
		}
		return list;
	}
	
	/**
	 * 通过key获取msg
	 * @param key 枚举值
	 * @return
	 */
	public static String getMsgByKey(String key) {
		if (key == null) {
			return null;
		}
		ConfigKey _enum = getByKey(key);
		if (_enum == null) {
			return null;
		}
		return _enum.getMessage();
	}
	
	/**
	 * 获取枚举key
	 * @param _enum
	 * @return
	 */
	public static String getKey(ConfigKey _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getKey();
	}
}

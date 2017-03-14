package com.yiji.ypayment.web.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yjf.common.util.StringUtils;

public class AppUtil {
	
	/**
	 * 日志对象
	 */
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 重定向
	 * 
	 * @param URL <t>重定向地址</t>
	 * @return
	 */
	public static String redirect(String URL) {
		if (StringUtils.isBlank(URL)) {
			return frontVm("/error.vm");
		} else {
			return "redirect:" + URL;
		}
	}
	
	/**
	 * 内部跳转
	 * 
	 * @param URL <t>内部跳转地址</t>
	 * @return
	 */
	public static String forward(String URL) {
		return "forward:" + URL;
	}
	
	/**
	 * BOSS页面返回方法
	 * 
	 * @return
	 */
	public static String bossVm(String vm) {
		String path = "boss/ypayment/";
		return path + vm;
	}
	
	/**
	 * FRONT页面返回方法
	 * 
	 * @return
	 */
	public static String frontVm(String vm) {
		String path = "front/";
		return path + vm;
	}
}

package com.yiji.ypayment.common.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.yjf.common.util.StringUtils;

/**
 * 简单 Date 相关静态工具
 * 
 * @author zhangpu
 */
public class Dates {
	
	public static final String CHINESE_DATE_FORMAT_LINE = "yyyy-MM-dd";
	public static final String CHINESE_DATETIME_FORMAT_LINE = "yyyy-MM-dd HH:mm:ss";
	public static final String CHINESE_DATE_FORMAT_SLASH = "yyyy/MM/dd";
	public static final String CHINESE_DATETIME_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";
	public static final String DATETIME_NOT_SEPARATOR = "yyyyMMddHHmmssSSS";
	public static final String DATETIME_YMD = "yyyyMMdd";
	
	private static final String[] SUPPORT_ALL_FORMATS = new String[] { CHINESE_DATE_FORMAT_LINE,
																		CHINESE_DATETIME_FORMAT_LINE,
																		CHINESE_DATE_FORMAT_SLASH,
																		CHINESE_DATETIME_FORMAT_SLASH };
	
	private static final String DEFAULT_DATE_FORMAT = CHINESE_DATETIME_FORMAT_LINE;
	
	public static String format(Date date, String pattern) {
		SimpleDateFormat sdf = getSimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static String format(Date date) {
		return format(date, DEFAULT_DATE_FORMAT);
	}
	
	public static String format(String pattern) {
		return format(new Date(), pattern);
	}
	
	public static Date parse(String dateString, String pattern) {
		SimpleDateFormat sdf = getSimpleDateFormat(pattern);
		try {
			return sdf.parse(dateString);
		} catch (Exception e) {
			throw new RuntimeException("parse String[" + dateString + "] to Date faulure with pattern[" + pattern
										+ "].");
		}
	}
	
	public static Date parse(String dateString, String[] patterns) {
		for (String pattern : patterns) {
			if (StringUtils.isBlank(pattern)) {
				continue;
			}
			SimpleDateFormat sdf = getSimpleDateFormat(pattern);
			try {
				return sdf.parse(dateString);
			} catch (Exception e) {
				// ignore exception
			}
		}
		throw new UnsupportedOperationException("Parse String[" + dateString + "] to Date faulure with patterns["
												+ Arrays.toString(patterns) + "]");
		
	}
	
	public static Date parse(String dateString) {
		return parse(dateString, SUPPORT_ALL_FORMATS);
	}
	
	public static Date addYear(Date date, int years) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, years);
		return c.getTime();
	}
	
	public static Date addMonth(Date date, int months) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, months);
		return c.getTime();
	}
	
	public static Date addWeek(Date date, int weeks) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.WEEK_OF_YEAR, weeks);
		return c.getTime();
	}
	
	public static Date addDay(Date date) {
		return addDay(date, 1);
	}
	
	public static Date addDay(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, days);
		return c.getTime();
	}
	
	public static Date addWorkDay(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int totalDays = 0;
		while (true) {
			c.add(Calendar.DAY_OF_YEAR, 1);
			int d = c.get(Calendar.DAY_OF_WEEK) - 1;
			if (d != 0 && d != 6) {// 0代表是周末 6代表是周六
				totalDays++;
			}
			if (totalDays >= days) {
				break;
			}
		}
		return c.getTime();
	}
	
	public static Date minusDay(Date date) {
		long oneDayMillisecond = 24 * 60 * 60 * 1000;
		return addDate(date, -oneDayMillisecond);
	}
	
	public static Date addDate(Date date, long millisecond) {
		return new Date(date.getTime() + millisecond);
	}
	
	private static SimpleDateFormat getSimpleDateFormat(String defaultFormat) {
		if (org.apache.commons.lang.StringUtils.isBlank(defaultFormat)) {
			defaultFormat = DEFAULT_DATE_FORMAT;
		}
		return new SimpleDateFormat(defaultFormat);
	}
}

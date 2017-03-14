/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@yiji.com 2015-01-22 05:17 创建
 *
 */
package com.yiji.ypayment.common.dao.jpa;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

/**
 * 动态查询条件对象
 * 
 * @author zhangpu@yiji.com
 */
public class SearchFilter {
	
	public enum Operator {
		EQ,
		LIKE,
		LLIKE,
		RLIKE,
		GT,
		LT,
		GTE,
		LTE,
		IN,
		NULL,
		NOTNULL
	}
	
	public String fieldName;
	public Object value;
	public Operator operator;
	
	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}
	
	public static List<SearchFilter> parse(Map<String, Object> searchParams) {
		List<SearchFilter> filters = Lists.newArrayList();
		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			Object value = entry.getValue();
			if (value == null || StringUtils.isBlank(String.valueOf(value))) {
				continue;
			}
			String[] names = StringUtils.split(entry.getKey(), "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(entry.getKey() + " is not a valid search filter name");
			}
			SearchFilter filter = new SearchFilter(names[1], Operator.valueOf(names[0]), value);
			filters.add(filter);
		}
		return filters;
	}
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2014-12-03 00:36 创建
 *
 */
package com.yiji.ypayment.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author qzhanbo@yiji.com
 */
public class Utils {
	public static Class<?> getSuperClassGenricType(Class<?> clazz, int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class<?>) params[index];
	}
	
	public static Class<?> getInterfaceGenricType(Class<?> clazz, Class<?> interfaceClazz, int index) {
		Type[] genType = clazz.getGenericInterfaces();
		for (Type type : genType) {
			if (!(type instanceof ParameterizedType)) {
				continue;
			}
			if (((ParameterizedType) type).getRawType() != interfaceClazz) {
				continue;
			}
			Type[] params = ((ParameterizedType) type).getActualTypeArguments();
			if (index >= params.length || index < 0) {
				return Object.class;
			}
			if (!(params[index] instanceof Class)) {
				return Object.class;
			}
			return (Class<?>) params[index];
		}
		return Object.class;
	}
}

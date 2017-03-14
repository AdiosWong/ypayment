package com.yiji.ypayment.common.utils;

import java.util.List;

public class ListUtils {
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(List list) {
		if (null == list || list.isEmpty())
			return true;
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(List list) {
		if (null == list || list.isEmpty())
			return false;
		return true;
	}
}

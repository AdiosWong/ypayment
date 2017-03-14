package com.yiji.ypayment.common.utils;

import com.yjf.common.lang.util.money.Money;

public class ElUtils {
	public static String strCentToStrYuan(String strCent) {
		return Money.cent(Long.valueOf(strCent)).toString();
	}
	
	public static String doubleYuanToStrYuan(double doubleYuan) {
		long longYuan = (long) (doubleYuan * 100);
		return Money.cent(longYuan).toString();
	}
	
}

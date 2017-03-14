package com.yiji.ypayment.biz.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetNumberUtil {
	/**
	 * 创建编码20位 yyMMddHHmmssSSS+5位不重复随机数
	 * 
	 * @return
	 */
	public static String getNumber20() {
		StringBuilder number = new StringBuilder();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		number.append(simpleDateFormat.format(new Date()));
		int a = (int) (Math.random() * 1000);
		if (a < 10 && a > 0) {
			a = a * 100;
		} else if (a >= 10 && a < 100) {
			a = a * 10;
		}
		number.append(a == 0 ? "000" : a);
		return number.toString();
	}
	
}

package com.yiji.ypayment.common.utils;

import java.text.DecimalFormat;

public class DoubleUtils {
	public static String douToStr(double number) {
		DecimalFormat df = new DecimalFormat("########################.###########################");
		return df.format(number);
	}
	
	public static double share2Yuan(double number) {
		return number / 100;
	}
}

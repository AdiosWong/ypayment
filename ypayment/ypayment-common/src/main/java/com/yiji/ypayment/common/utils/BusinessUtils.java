package com.yiji.ypayment.common.utils;

import com.yjf.common.id.ID;

public class BusinessUtils {
	
	public static String getGid() {
		return ID.newID("ypayment");
	}
}

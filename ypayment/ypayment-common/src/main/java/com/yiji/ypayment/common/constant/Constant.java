package com.yiji.ypayment.common.constant;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yiji.ypayment.common.utils.Dates;
import com.yjf.common.id.ID;
import com.yjf.common.lang.util.DateUtil;

/**
 * 系统常量
 * 
 * @author faZheng
 * 
 */
public class Constant {
	/**
	 * 文件分隔符
	 */
	public static final String FILE_SEPARATOR = "|";
	
	/**
	 * 默认文件路径
	 */
	public static final String FILE_SIGN = "/";
	/**
	 * 线程等待时间
	 */
	public static final int SLEEP_TIME = 1000;
	
	/**
	 * 入口编码
	 */
	public static final String INLTE = "23";
	
	/**
	 * 虚拟商户
	 */
	public static final String VIRTUAL_MERCHANT = "20000000000000000017";
	
	/**
	 * 交易业务收费码
	 */
	public static final String TRADE_BIZ_PRODUCT_CODE = "20150521-teyuescbTrade";
	
	/**
	 * 系统id
	 */
	public static final String SYSTEMID = "ypayments";
	
	/**
	 * 编码编码
	 */
	public static final String BIZCODE = "ypayments";
	
	/**
	 * 超级路由
	 */
	public static final String ROUTE_ORDER_API = "ypayment";
	
	/**
	 * 默认表ID
	 */
	public static final long TABLEID = 1l;
	
	/**
	 * 电话分隔符
	 */
	public static final String PHONE_SEPARATOR = "|";
	
	/**
	 * 缴费
	 */
	public static final String PAYMENT_BUSI_TYPE_JF = "jf";
	
	/**
	 * 充值
	 */
	public static final String PAYMENT_BUSI_TYPE_CZ = "cz";
	
	/**
	 * 预存
	 */
	public static final String PAYMENT_BUSI_TYPE_YC = "yc";
	
	/**
	 * 垃圾处理类型
	 */
	public static final String PAYMENT_TYPE_RUBBISH = "000060";
	
	/**
	 * 获取Gid
	 * 
	 * @return
	 */
	public static String getGid() {
		return ID.newID(Constant.BIZCODE);
	}
	
	/**
	 * 系统日终执行
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isExecute(Date date) {
		int dayEndTime = 150300;
		SimpleDateFormat simpleDate = new SimpleDateFormat("HHmmss");
		int sysTime = Integer.parseInt(simpleDate.format(date).toString());
		if (sysTime > dayEndTime) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取当前系统日期
	 * 
	 * @return
	 */
	public static String getStrDate() {
		return DateUtil.shortDate(new Date());
	}
	
	/**
	 * 获取指定日期
	 * 
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static String getShortstring2Date(String dateTime) {
		try {
			return DateUtil.shortDate(Dates.parse(dateTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DateUtil.shortDate(new Date());
	}
	
	/**
	 * 文件目录转换
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String convertFilePath(String filePath) throws IOException {
		File file = new File(filePath);
		filePath = file.getCanonicalPath();
		filePath = filePath.replace("\\", "/");
		return filePath;
	}
	
	/**
	 * 日终开始时间
	 */
	public static String dayEndStartTime = " 15:00:00";
	
	/**
	 * 日终结束时间
	 */
	public static String dayEndEndTime = " 14:59:59";
}

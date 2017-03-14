package com.yiji.ypayment.facade.common;

import com.yjf.common.util.ToString;

/**
 * 缴费查询分页order基类
 * 
 * @author faZheng
 * 
 */

public class QueryPageOrderBase extends QueryPaymentOrderBase {
	
	private static final long serialVersionUID = 7645498209573759199L;
	
	/**
	 * 当前页号
	 */
	private int currentPage = 1;
	
	/**
	 * 当页记录数 默认为10条记录
	 */
	private int countOfCurrentPage = 10;
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getCountOfCurrentPage() {
		return countOfCurrentPage;
	}
	
	public void setCountOfCurrentPage(int countOfCurrentPage) {
		this.countOfCurrentPage = countOfCurrentPage;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
}

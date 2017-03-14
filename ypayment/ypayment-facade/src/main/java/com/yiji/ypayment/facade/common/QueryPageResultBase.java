package com.yiji.ypayment.facade.common;

import com.yjf.common.util.ToString;

/**
 * 缴费查询分页result基类 StandardResultInfo
 * 
 * @author faZheng
 * 
 */

public class QueryPageResultBase extends QueryPaymentResultBase {
	
	private static final long serialVersionUID = 2280670889128163803L;
	
	/**
	 * 当前页号
	 */
	private int currentPage;
	
	/**
	 * 总共记录数
	 */
	private long totalCount;
	
	/**
	 * 总共页数
	 */
	private long totalPage;
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public long getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	public long getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
}

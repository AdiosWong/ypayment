package com.yiji.ypayment.dal.common;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yiji.ypayment.common.dao.support.PageInfo;
import com.yjf.common.util.StringUtils;

public class PagedJdbcTemplate extends JdbcTemplate {
	/** ORACLE 分页查询SQL模版 */
	private static final String ORACLE_PAGESQL_TEMPLATE = "SELECT * FROM (SELECT XX.*,rownum ROW_NUM FROM (${sql}) XX ) ZZ where ZZ.ROW_NUM BETWEEN ${startNum} AND ${endNum}";
	
	/**
	 * Oracle jdbc 分页查询简单封装
	 * 
	 * @param <T>
	 * @param pageInfo
	 * @param sql
	 * @param requiredType
	 * @return
	 */
	public <T> PageInfo<T> queryOracle(PageInfo<T> pageInfo, String sql, Class<T> requiredType) {
		String orderBy = "";
		if (StringUtils.contains(sql, "order by")) {
			orderBy = sql.substring(sql.indexOf("order by"));
			sql = sql.substring(0, sql.indexOf("order by"));
		}
		String sqlFrom = sql.substring(sql.indexOf("from"));
		String sqlCount = "select count(*) " + sqlFrom;
		// 总记录数
		long totalCount = super.queryForObject(sqlCount, Long.class);
		int startNum = pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage() - 1) + 1;
		long endNum = pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage());
		if (endNum > totalCount) {
			endNum = totalCount;
		}
		long totalPage = (totalCount % pageInfo.getCountOfCurrentPage() == 0 ? totalCount
																				/ pageInfo.getCountOfCurrentPage()
			: totalCount / pageInfo.getCountOfCurrentPage() + 1);
		
		String pageSql = ORACLE_PAGESQL_TEMPLATE + " " + orderBy;
		pageSql = StringUtils.replace(pageSql, "${sql}", sql);
		pageSql = StringUtils.replace(pageSql, "${startNum}", String.valueOf(startNum));
		pageSql = StringUtils.replace(pageSql, "${endNum}", String.valueOf(endNum));
		
		List<T> result = null;
		if (requiredType.getName().equals(Map.class.getName())) {
			result = (List<T>) queryForList(pageSql);
		} else {
			result = query(pageSql, BeanPropertyRowMapper.newInstance(requiredType));
		}
		
		pageInfo.setPageResults(result);
		pageInfo.setTotalCount(totalCount);
		pageInfo.setTotalPage(totalPage);
		return pageInfo;
	}
	
	/**
	 * JDBC分页查询
	 * 
	 * @param pageInfo
	 * @param sql
	 * @param dto
	 * @param pageColumn 分页用的列(大数据查询时该列最好有索引)
	 * @return
	 */
	@Deprecated
	public <T> PageInfo<T> queryMSSQL(PageInfo<T> pageInfo, String sql, Class<T> dtoEntity, String pageColumn) {
		try {
			String sqlCount = "select count(*) from (" + sql + ") as t1";
			// 总记录数
			long totalCount = super.queryForObject(sqlCount, Long.class);
			int startNum = pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage() - 1);
			/*int endNum = pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage());
			if (endNum > totalCount) {
				endNum = (int) totalCount;
			}*/
			long totalPage = (totalCount % pageInfo.getCountOfCurrentPage() == 0 ? totalCount
																					/ pageInfo.getCountOfCurrentPage()
				: totalCount / pageInfo.getCountOfCurrentPage() + 1);
			
			String pageSql = sql + " limit " + startNum + "," + pageInfo.getCountOfCurrentPage();
			
			List<T> result = query(pageSql, BeanPropertyRowMapper.newInstance(dtoEntity));
			pageInfo.setPageResults(result);
			pageInfo.setTotalCount(totalCount);
			pageInfo.setTotalPage(totalPage);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return pageInfo;
	}
	
	/**
	 * JDBC分页查询
	 * 
	 * @param pageInfo
	 * @param sql
	 * @param dto
	 * @return
	 */
	public <T> PageInfo<T> queryMySql(PageInfo<T> pageInfo, String sql, Class<T> dtoEntity) {
		try {
			String sqlCount = "select count(*) from (" + sql + ") as xxttbb";
			// 总记录数
			long totalCount = super.queryForObject(sqlCount, Long.class);
			int startNum = pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage() - 1);
			/*int endNum = pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage());
			if (endNum > totalCount) {
				endNum = (int) totalCount;
			}*/
			long totalPage = (totalCount % pageInfo.getCountOfCurrentPage() == 0 ? totalCount
																					/ pageInfo.getCountOfCurrentPage()
				: totalCount / pageInfo.getCountOfCurrentPage() + 1);
			
			String pageSql = sql + " limit " + startNum + "," + pageInfo.getCountOfCurrentPage();
			List<T> result = null;
			if (dtoEntity.getName().equals(Map.class.getName())) {
				result = (List<T>) queryForList(pageSql);
			} else {
				result = query(pageSql, BeanPropertyRowMapper.newInstance(dtoEntity));
			}
			pageInfo.setPageResults(result);
			pageInfo.setTotalCount(totalCount);
			pageInfo.setTotalPage(totalPage);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return pageInfo;
	}
	
	/**
	 * list 查询
	 */
	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
		return query(sql, BeanPropertyRowMapper.newInstance(elementType));
	}
	
}

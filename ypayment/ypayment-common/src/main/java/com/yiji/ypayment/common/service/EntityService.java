package com.yiji.ypayment.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yiji.ypayment.common.dao.support.PageInfo;
import com.yiji.ypayment.common.exception.BusinessException;

/**
 * 针对单个Entity对象的操作定义. 不依赖于具体ORM实现方案.
 * 
 * @author zhangpu
 */
public interface EntityService<T> {
	
	/**
	 * 根据ID获得单个对象
	 * 
	 * @param id
	 * @return
	 */
	T get(Serializable id) throws BusinessException;
	
	/**
	 * 获得对象所有集合
	 * 
	 * @return
	 */
	List<T> getAll() throws BusinessException;
	
	/**
	 * 保存对象
	 */
	void save(T t) throws BusinessException;
	
	/**
	 * 批量保存对象
	 */
	void saves(List<T> ts) throws BusinessException;
	
	/**
	 * 更新对象
	 * 
	 * @param t
	 */
	void update(T t) throws BusinessException;
	
	/**
	 * 删除对象
	 * 
	 * @param t
	 * @throws Exception
	 */
	void remove(T t);
	
	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 * @throws Exception
	 */
	void removeById(Serializable id) throws BusinessException;
	
	void removes(Serializable... ids) throws BusinessException;
	
	/**
	 * 带条件和排序的分页查询
	 * 
	 * @param pageInfo
	 * @param map propertyName([EQ/LIKE...]_propertyName) --> value
	 * @param sortMap propertyName --> true:asc,false:desc
	 * @return
	 */
	PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map, Map<String, Boolean> sortMap)
																									throws BusinessException;
	
	PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map) throws BusinessException;
	
	List<T> query(Map<String, Object> map, Map<String, Boolean> sortMap) throws BusinessException;
	
}

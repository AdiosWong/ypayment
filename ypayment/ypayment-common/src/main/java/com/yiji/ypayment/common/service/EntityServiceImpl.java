package com.yiji.ypayment.common.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.NoRepositoryBean;

import com.yiji.boot.securityframework.annotation.Sensitive;
import com.yiji.common.security.annotation.NeedIndex;
import com.yiji.common.security.annotation.ReverseIndex;
import com.yiji.ypayment.common.dao.EntityDao;
import com.yiji.ypayment.common.dao.support.PageInfo;
import com.yiji.ypayment.common.exception.BusinessException;
import com.yiji.ypayment.common.utils.BeanUtil;
import com.yiji.ypayment.common.utils.GenericsUtils;

/**
 * 基础EntityService的抽象实现。
 * 
 * @param <T> 被管理的实体类
 * @param <M> 实体类的DAO
 * @author zhangpu
 */
@NoRepositoryBean
public abstract class EntityServiceImpl<T, M extends EntityDao<T>> implements ApplicationContextAware, EntityService<T> {
	
	private M entityDao;
	private ApplicationContext context;
	
	@Autowired
	protected EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	protected M getEntityDao() {
		if (entityDao != null) {
			return entityDao;
		}
		// 获取定义的第一个实例变量类型
		Class<M> daoType = GenericsUtils.getSuperClassGenricType(getClass(), 1);
		List<Field> fields = BeanUtil.getFieldsByType(this, daoType);
		try {
			if (fields != null && fields.size() > 0) {
				entityDao = (M) BeanUtil.getDeclaredProperty(this, fields.get(0).getName());
			} else {
				entityDao = (M) context.getBean(daoType);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage());
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e.getMessage());
		}
		return entityDao;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
	@Override
	@Sensitive
	public @ReverseIndex T get(Serializable id) throws BusinessException {
		try {
			T entity = getEntityDao().get(id);
			if (entity!=null){
				entityManager.detach(entity);
			}
			return entity;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		
	}
	
	@Override
	public List<T> getAll() throws BusinessException {
		try {
			return getEntityDao().getAll();
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@Override
	public void remove(T o) throws BusinessException {
		try {
			getEntityDao().remove(o);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@Override
	public void removeById(Serializable id) throws BusinessException {
		try {
			getEntityDao().removeById(id);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
		
	}
	
	@Override
	public void removes(Serializable... ids) throws BusinessException {
		try {
			getEntityDao().removes(ids);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@Override
	@Sensitive
	public void save(@NeedIndex T o) throws BusinessException {
		try {
			getEntityDao().create(o);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@Override
	public void saves(List<T> ts) throws BusinessException {
		try {
			getEntityDao().saves(ts);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@Override
	@Sensitive
	public void update(@NeedIndex T o) throws BusinessException {
		try {
			getEntityDao().update(o);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@Override
	public PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map, Map<String, Boolean> orderMap)
																											throws BusinessException {
		try {
			return getEntityDao().query(pageInfo, map, orderMap);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@Override
	public PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map) throws BusinessException {
		return query(pageInfo, map, null);
	}
	
	@Override
	public List<T> query(Map<String, Object> map, Map<String, Boolean> sortMap) throws BusinessException {
		try {
			return getEntityDao().query(map, sortMap);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
}

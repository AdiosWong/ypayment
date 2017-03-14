/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@yiji.com 2015-01-22 05:17 创建
 *
 */
package com.yiji.ypayment.common.dao.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * JPA-DAO仓库实现
 * <p/>
 * 指定默认的JPA-DAO实现。
 * 
 * @param <R>
 * @param <T>
 * @param <I>
 * @author zhangpu
 */
public class EntityRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
																									extends
																									JpaRepositoryFactoryBean<R, T, I> {
	@SuppressWarnings("rawtypes")
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new EntityRepositoryFactory(entityManager);
	}
	
	private static class EntityRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {
		
		public EntityRepositoryFactory(EntityManager entityManager) {
			super(entityManager);
		}
		
		@SuppressWarnings("unchecked")
		protected SimpleJpaRepository<T, I> getTargetRepository(RepositoryInformation information,
																EntityManager entityManager) {
			return new AbstractEntityJpaDao<T, I>((Class<T>) information.getDomainType(), entityManager);
		}
		
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return AbstractEntityJpaDao.class;
		}
	}
	
}

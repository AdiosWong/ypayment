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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.yiji.ypayment.common.dao.EntityDao;

/**
 * Spring Data with Jpa 基础DAO接口
 * <p/>
 * 扩展该接口是为了兼容原来的框架API 使用定位：用户后台管理型业务系统开发，后端使用Hibernate，对于新能要求是不太高的系统推荐使用。
 * 核心能力：使用JPA方式开发DAO，只写接口，不用写实现。支持一层的多条件组合查询。如果需要特殊功能，可以为特定的DAO扩展JDBC实现。
 * 
 * @param <T>
 * @author zhangpu
 */
@NoRepositoryBean
public interface EntityJpaDao<T, ID extends Serializable> extends JpaRepository<T, ID>, EntityDao<T> {
	
}

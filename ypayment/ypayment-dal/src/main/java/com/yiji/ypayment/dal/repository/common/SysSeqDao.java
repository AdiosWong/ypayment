/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * husheng@yiji.com 2014年8月27日 下午3:51:28创建
 */
package com.yiji.ypayment.dal.repository.common;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yiji.ypayment.dal.entity.common.SysSeq;

/**
 * 
 * @author faZheng
 * 
 */
public interface SysSeqDao extends JpaRepository<SysSeq, Long> {
	
	@Query(value = "SELECT current_timestamp FROM dual", nativeQuery = true)
	public Date getSysDate();
	
}

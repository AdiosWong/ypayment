package com.yiji.ypayment.dal.entity.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yiji.ypayment.dal.entity.AbstractEntity;
import com.yjf.common.util.ToString;

/**
 * 序列号系统表
 * @author faZheng
 * 
 */
@Entity
@Table(name = "sys_seq")
public class SysSeq extends AbstractEntity {
	
	/**
	 * UID
	 */
	private static final long serialVersionUID = -1952705367245833398L;
	
	@Column(name = "seq_name", length = 64, nullable = false, columnDefinition = "varchar(64) not null comment'名称'")
	private String name;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
}

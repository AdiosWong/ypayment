package com.yiji.ypayment.dal.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.type.YesNoType;

import com.yiji.ypayment.dal.type.MoneyType;
import com.yjf.common.fastjson.FastJsonSupport;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.ToString;

/**
 * @author faZheng
 */
@MappedSuperclass
@TypeDefs({	@TypeDef(defaultForType = Money.class, name = "moneyType", typeClass = MoneyType.class),
			@TypeDef(defaultForType = boolean.class, name = "yesNoType", typeClass = YesNoType.class) })
public abstract class AbstractEntity implements Serializable {
	
	/**
	 * UID
	 */
	private static final long serialVersionUID = -4238304951420171102L;
	
	static {
		FastJsonSupport.init();
	}
	
	/**
	 * ID
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	/**
	 * 创建时间
	 */
	@Column(name = "raw_add_time", insertable = true, updatable = false,
			columnDefinition = "TIMESTAMP NOT NULL DEFAULT 0 COMMENT '创建时间'")
	private Date rawAddTime = new Date();
	
	/**
	 * 修改时间
	 */
	@Column(name = "raw_update_time", insertable = false, updatable = false,
			columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'")
	private Date rawUpdateTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月22日 下午5:37:45创建
 */
package com.yiji.ypayment.dal.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 
 * @author faZheng
 * 
 */
public class MoneyType implements UserType {
	
	private static final int[] SQL_TYPES = { Types.INTEGER };
	
	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}
	
	@Override
	public Class<?> returnedClass() {
		return Money.class;
	}
	
	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if ((x == null) || (y == null)) {
			return false;
		}
		return x.equals(y);
	}
	
	@Override
	public int hashCode(Object x) throws HibernateException {
		return x == null ? 0 : x.hashCode();
	}
	
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
																										throws HibernateException,
																										SQLException {
		Long value = rs.getLong(names[0]);
		Money m = new Money();
		m.setCent(value);
		return m;
	}
	
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
																										throws HibernateException,
																										SQLException {
		if (value == null) {
			st.setLong(index, 0L);
			return;
		}
		
		if (!(value instanceof Money)) {
			throw new HibernateException("value is not type of " + Money.class);
		}
		
		st.setLong(index, ((Money) value).getCent());
	}
	
	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value != null && value instanceof Money) {
			return new Money(((Money) value).getAmount());
		}
		return value;
	}
	
	@Override
	public boolean isMutable() {
		return false;
	}
	
	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}
	
	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}
	
	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}
	
}

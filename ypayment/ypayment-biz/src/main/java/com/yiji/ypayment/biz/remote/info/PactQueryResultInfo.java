/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午11:30:33 创建
 */
package com.yiji.ypayment.biz.remote.info;

import java.util.List;

import com.google.common.collect.Lists;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class PactQueryResultInfo {
	
	/** 查询结果 */
	private List<PactBankCardInfo> pactBankCards = Lists.newArrayList();

	public List<PactBankCardInfo> getPactBankCards() {
		return this.pactBankCards;
	}

	public void setPactBankCards(List<PactBankCardInfo> pactBankCards) {
		this.pactBankCards = pactBankCards;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

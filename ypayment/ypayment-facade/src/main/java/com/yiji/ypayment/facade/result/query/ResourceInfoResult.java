/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午2:35:11 创建
 */
package com.yiji.ypayment.facade.result.query;

import java.util.List;

import com.google.common.collect.Lists;
import com.yiji.ypayment.facade.common.QueryPaymentResultBase;
import com.yiji.ypayment.facade.info.query.ResourceInfo;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
public class ResourceInfoResult extends QueryPaymentResultBase {
	
	private static final long serialVersionUID = -8371207298404665006L;
	
	List<ResourceInfo> resourceInfos = Lists.newArrayList();;

	public List<ResourceInfo> getResourceInfos() {
		return this.resourceInfos;
	}

	public void setResourceInfos(List<ResourceInfo> resourceInfos) {
		this.resourceInfos = resourceInfos;
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}

}

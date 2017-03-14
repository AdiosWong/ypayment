/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:00:32 创建
 */
package com.yiji.ypayment.biz.service.ypayment;

import java.util.List;

import com.yiji.ypayment.common.service.EntityService;
import com.yiji.ypayment.dal.entity.business.BillItemInfo;

/**
 * 缴费账单明细信息服务
 *
 * @author xinqing@yiji.com
 *
 */
public interface BillItemInfoService extends EntityService<BillItemInfo> {
	
	/**
	 * 根据订单号查询账单明细
	 * 
	 * @param paymentOrderNo
	 * @return
	 */
	List<BillItemInfo> findByPaymentOrderNo(String paymentOrderNo);
	
}

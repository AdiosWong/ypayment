/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved
 */

/*
 * 修订记录:
 * faZheng 2015-10-12 11:03 创建
 *
 */
package com.yiji.ypayment.biz.common.servcie;

import java.util.Date;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.facade.common.PaymentResultBase;
import com.yjf.common.service.enums.customer.UserTypeEnum;
import com.yjf.customer.service.order.UniformStringQueryOrder;

/**
 * @author faZheng
 */
public interface CommonService {
	
	/**
	 * 生成流水号
	 * @param bizName
	 * @return
	 */
	public String generateBizNo(PaymentInstructionAction paymentAction);
	
	/**
	 * check用户类型(userId的用户类型 等于 枚举userType true)
	 * 
	 * @param userId 用户userId
	 * @param userType 用户类型
	 * @return
	 */
	public boolean checkUserType(UniformStringQueryOrder order, UserTypeEnum userType);
	
	/**
	 * 如果gid为空或者不是35位，则抛出异常
	 * 
	 * @param gid
	 */
	void checkGid(String gid);
	
	/**
	 * 判断时间是否大于15:00
	 * 
	 * @param date
	 * @return
	 */
	boolean isAfter15(Date date);
	
	/**
	 * 构建默认结果
	 * 
	 * @return
	 */
	void setDefaultResult(PaymentResultBase result);
}

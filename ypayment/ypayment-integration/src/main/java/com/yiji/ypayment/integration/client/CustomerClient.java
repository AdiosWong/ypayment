/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved
 */

/*
 * 修订记录:
 * faZheng 2015-10-15 17:16 创建
 *
 */
package com.yiji.ypayment.integration.client;

import com.yjf.common.lang.util.money.Money;
import com.yjf.customer.service.info.OperatorInfo;
import com.yjf.customer.service.info.UserInfo;
import com.yjf.customer.service.order.UniformStringQueryOrder;
import com.yjf.customer.service.order.password.PayPasswordOrder;
import com.yjf.customer.service.result.MobileBindInfoResult;
import com.yjf.customer.service.result.OperatorInfoResult;
import com.yjf.customer.service.result.PasswordResult;
import com.yjf.customer.service.result.UserQueryResult;
import com.yjf.customer.service.result.card.CardInsertResult;

/**
 * 会员系统
 * 
 * @author faZheng
 * 
 */
public interface CustomerClient {
	
	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	UserQueryResult findUserInfoByUserId(UniformStringQueryOrder uniformStringQueryOrder);
	
	/**
	 * 验证支付密码
	 * 
	 * @param operatorId
	 * @param payPasswordo
	 * @return
	 */
	PasswordResult verifyPayPassword(PayPasswordOrder order);
	
	/**
	 * 根据用户名查询操作员
	 * 
	 * @param userId
	 * @return
	 */
	OperatorInfoResult findOperatorByUserName(UniformStringQueryOrder order);
	
	/**
	 * 获取用户可用余额
	 * 
	 * @param userId
	 * @return
	 */
	Money getAvailableMoney(UniformStringQueryOrder order);
	
	/**
	 * 查询用户所属平台商userId
	 * 
	 * @param subUserId
	 * @return
	 */
	String queryMerchantUserId(UniformStringQueryOrder order);
	
	/**
	 * 新增一张卡(来源)
	 * 
	 * 开副卡：只需要填写parentCardNo，cardAlias ，registerFrom ，originRegisterFrom,
	 * accountExtType, isDefault 其他属性随主卡相同
	 * 
	 * CardRegisterFromOrder: isDefault属性： order中 isDefault==DEFAULT
	 * ：修改该币种当前的默认卡的isDefault字段为null；保存新卡 order中 isDefault==null
	 * ：保存新卡（如果是该币种的第一张卡，isDefault会被设置成DEFAULT）
	 * @param userTypeEnum
	 * 
	 * @param cardRegisterFromOrder
	 * @return
	 */
	public CardInsertResult addCard(UserInfo userInfo);
	
	/**
	 * 根据userId查询绑定手机信息
	 * @param userId
	 * @return
	 */
	public MobileBindInfoResult findMobileBindInfoByUserId(UniformStringQueryOrder order);
	
	/**
	 * 根据userId查找administrator operator
     * @param userId
     * @return
	 */
	public OperatorInfo queryAdminOperatorByUserId(UniformStringQueryOrder order);
	
	/**
	 * 验证用户和操作员的关系
	 * @param userId
	 * @param operatorId
	 * @return
	 */
	public OperatorInfo isOperatorOfUserId(UniformStringQueryOrder order, String operatorId);
}

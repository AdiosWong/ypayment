/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved
 */

/*
 * 修订记录:
 * faZheng 2015-10-15 17:16 创建
 *
 */
package com.yiji.ypayment.integration.client.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yiji.ypayment.common.constant.Constant;
import com.yiji.ypayment.integration.client.CustomerClient;
import com.yiji.ypayment.integration.client.impl.caller.CallerServiceBase;
import com.yjf.accounttrans.service.enums.AccountExtType;
import com.yjf.accounttrans.service.info.AccountInfo;
import com.yjf.common.lang.context.OperationContext;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.enums.customer.OperatorTypeEnum;
import com.yjf.common.service.enums.customer.RegisterFromEnum;
import com.yjf.customer.service.api.CardService;
import com.yjf.customer.service.api.PasswordService;
import com.yjf.customer.service.api.query.CardQueryService;
import com.yjf.customer.service.api.query.MerchantUserRelationQueryService;
import com.yjf.customer.service.api.query.MobileBindQueryService;
import com.yjf.customer.service.api.query.OperatorQueryService;
import com.yjf.customer.service.api.query.UserQueryService;
import com.yjf.customer.service.enums.CustomerResultEnum;
import com.yjf.customer.service.info.ExtraOperatorInfo;
import com.yjf.customer.service.info.OperatorInfo;
import com.yjf.customer.service.info.UserInfo;
import com.yjf.customer.service.order.CardRegisterFromOrder;
import com.yjf.customer.service.order.UniformStringQueryOrder;
import com.yjf.customer.service.order.password.PayPasswordOrder;
import com.yjf.customer.service.result.MobileBindInfoResult;
import com.yjf.customer.service.result.OperatorInfoQueryResult;
import com.yjf.customer.service.result.OperatorInfoResult;
import com.yjf.customer.service.result.PasswordResult;
import com.yjf.customer.service.result.UserQueryResult;
import com.yjf.customer.service.result.card.AccountQuerySingleResult;
import com.yjf.customer.service.result.card.CardInsertResult;
import com.yjf.customer.service.result.user.MerchantUserRelationListResult;

/**
 * 
 * @author faZheng
 * 
 */
@Service("customerClient")
public class CustomerClientImpl extends CallerServiceBase implements CustomerClient {
	
	@Reference(version = "1.5")
	private MobileBindQueryService mobileBindQueryServiceRemote;
	@Reference(version = "1.5")
	private CardService cardServiceRemote;
	@Reference(version = "1.5")
	private MerchantUserRelationQueryService merchantUserRelationQueryService;
	@Reference(version = "1.5")
	private CardQueryService cardQueryServiceRemote;
	@Reference(version = "1.5")
	private OperatorQueryService operatorQueryServiceRemote;
	@Reference(version = "1.5")
	private PasswordService passwordServiceRemote;
	@Reference(version = "1.5")
	private UserQueryService userQueryServiceRemote;
	@Reference(version = "1.5")
	private OperatorQueryService operatorQueryService;
	
	@Override
	public UserQueryResult findUserInfoByUserId(UniformStringQueryOrder uniformStringQueryOrder) {
		logger.info("入参:根据用户ID查询用户信息userId:{}", uniformStringQueryOrder);
		UserQueryResult result = userQueryServiceRemote.findUserInfoByUserId(uniformStringQueryOrder);
		logger.info("出参:根据用户ID查询用户信息result:{}", result);
		return result;
	}
	
	@Override
	public PasswordResult verifyPayPassword(PayPasswordOrder order) {
		PasswordResult result = null;
		try {
			logger.info("验证用户支付密码，入参：{}", order);
			result = passwordServiceRemote.verifyPayPassword(order, new OperationContext());
			logger.info("验证用户支付密码，出参：{}", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("验证用户支付密码，发生异常：{}", e);
		}
		return result;
	}
	
	@Override
	public OperatorInfoResult findOperatorByUserName(UniformStringQueryOrder order) {
		OperatorInfoResult operatorInfoResult = null;
		try {
			logger.info("根据用户UserName查询操作员信息，入参：{}", order);
			operatorInfoResult = operatorQueryServiceRemote.findOperatorByUserName(order);
			logger.info("根据用户UserName查询操作员信息，出参：{}", operatorInfoResult);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据用户UserName查询操作员信息，发生异常：{}", e);
		}
		return operatorInfoResult;
	}
	
	@Override
	public Money getAvailableMoney(UniformStringQueryOrder order) {
		Money availableMoney = new Money(0);
		try {
			logger.info("根据用户userId查询操作员信息，入参：{}", order);
			AccountQuerySingleResult accountQuerySingleResult = cardQueryServiceRemote.getAccountInfoByCardNo(order);
			if (accountQuerySingleResult.getResultCode() == CustomerResultEnum.EXECUTE_SUCCESS) {
				Money sumMoney = new Money(0);
				Money freezeMoney = new Money(0);
				AccountInfo accountInfo = accountQuerySingleResult.getAccountInfo();
				sumMoney.addTo(accountInfo.getBalance().add(accountInfo.getCreditBalance()));
				freezeMoney.addTo(accountInfo.getFreezeAmount().addTo(accountInfo.getSystemAmount()));
				availableMoney = sumMoney.subtract(freezeMoney);
			}
			logger.info("根据用户userId查询操作员信息，出参：可用金额{}", availableMoney);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据用户UserId查询账户可用金额，发生异常：{}", e);
		}
		return availableMoney;
	}
	
	@Override
	public String queryMerchantUserId(UniformStringQueryOrder order) {
		String merchantUserId = null;
		try {
			logger.info("查询用户userId所属平台商，入参：{}", order);
			MerchantUserRelationListResult result = merchantUserRelationQueryService
				.findMerchantUserRelationByUserId(order);
			logger.info("查询用户userId所属平台商，出参：{}", result);
			if (result.getResultCode() == CustomerResultEnum.EXECUTE_SUCCESS) {
				merchantUserId = result.getMerchantCardInfoList().get(0).getUserId();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询用户userId所属平台商，异常：{}", e);
		}
		return merchantUserId;
	}
	
	@Override
	public CardInsertResult addCard(UserInfo userInfo) {
		CardRegisterFromOrder order = new CardRegisterFromOrder();
		order.setGid(Constant.getGid());
		order.setUserId(userInfo.getUserId());
		order.setCardAlias("基金账户");
		order.setAccountTypeCA("STANDARD_ACCOUNT");
		order.setRegisterFrom(RegisterFromEnum.FUND);
		order.setOriginRegisterFrom(userInfo.getOriginRegisterFrom());
		order.setAccountExtType(AccountExtType.YJF_FUND_CMBC_ACCOUNT);
		logger.info("查询用户userId开卡，入参：{}", order);
		CardInsertResult result = cardServiceRemote.addCard(order);
		logger.info("查询用户userId开卡，出参：{}", result);
		return result;
	}
	
	@Override
	public MobileBindInfoResult findMobileBindInfoByUserId(UniformStringQueryOrder order) {
		MobileBindInfoResult mobileBindInfoResult = null;
		try {
			logger.info("根据userId查询绑定手机信息，入参：{}", order);
			mobileBindInfoResult = mobileBindQueryServiceRemote.findMobileBindInfoByUserId(order);
			logger.info("根据userId查询绑定手机信息，出参：{}", mobileBindInfoResult);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据userId查询绑定手机信息，发生异常：{}", e);
		}
		return mobileBindInfoResult;
	}
	
	@Override
	public OperatorInfo queryAdminOperatorByUserId(UniformStringQueryOrder order) {
		try {
			OperatorInfoQueryResult result = operatorQueryService.queryOperatorsByUserId(order);
			for (ExtraOperatorInfo operatorInfo : result.getExtraOperatorInfoList()) {
				if (operatorInfo.getIsAdmin() == OperatorTypeEnum.ADMIN) {
					return operatorInfo;
				}
			}
		} catch (Exception e) {
			logger.error("查询操作员Id出现异常", e.getMessage());
		}
		return null;
	}
	
	@Override
	public OperatorInfo isOperatorOfUserId(UniformStringQueryOrder order, String operatorId) {
		OperatorInfoQueryResult result = operatorQueryService.queryOperatorsByUserId(order);
		for (ExtraOperatorInfo operatorInfo : result.getExtraOperatorInfoList()) {
			String extraOperatorId = operatorInfo.getOperatorId();
			if (extraOperatorId.equals(operatorId)) {
				return operatorInfo;
			}
		}
		return null;
	}
	
}

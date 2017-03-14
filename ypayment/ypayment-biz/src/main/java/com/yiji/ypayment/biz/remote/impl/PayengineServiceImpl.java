/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午5:09:29 创建
 */
package com.yiji.ypayment.biz.remote.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yiji.superroute.facade.common.enums.SubTransactionTypeEnum;
import com.yiji.superroute.facade.common.enums.TransactionTypeEnum;
import com.yiji.superroute.facade.route.order.ChannelRouteOrder;
import com.yiji.superroute.facade.route.result.ChannelRouteResult;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.remote.PayengineService;
import com.yiji.ypayment.biz.remote.base.RemoteServiceBase;
import com.yiji.ypayment.biz.remote.info.PactBankCardInfo;
import com.yiji.ypayment.biz.remote.order.DeductDepositOrder;
import com.yiji.ypayment.common.constant.Constant;
import com.yiji.ypayment.dal.entity.business.DeductDepositInfo;
import com.yiji.ypayment.dal.enums.DepositStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yjf.common.id.GID;
import com.yjf.common.service.enums.BusinessNoEnum;
import com.yjf.common.superroute.enums.DebitCreditTypeEnum;
import com.yjf.common.superroute.enums.PersonalCorporateTypeEnum;
import com.yjf.payengine.common.enums.PayengineResultEnum;
import com.yjf.payengine.deposit.service.info.DepositInstructionInfo;
import com.yjf.payengine.deposit.service.order.ApplyDepositDeductOrder;
import com.yjf.payengine.deposit.service.result.DepositResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("payengineService")
public class PayengineServiceImpl extends RemoteServiceBase implements PayengineService {
	
	@Value("${dubbo.version}")
	private String version;
	
	@Value("${dubbo.group}")
	private String group;
	
	/**
	 * 代扣服务
	 * @see com.yiji.ypayment.biz.remote.DeductDepositService#deductDeposit()
	 */
	@Override
	public DepositStatusEnum deductDeposit(DeductDepositOrder deductDepositOrder, PactBankCardInfo pactBankCardInfo) {
		ApplyDepositDeductOrder buildDeductOrder = buildDeductOrder(deductDepositOrder, pactBankCardInfo);
		//数据库持久化
		DeductDepositInfo deductDepositInfo = new DeductDepositInfo();
		deductDepositInfo.setUserId(buildDeductOrder.getUserId());
		deductDepositInfo.setDepositBizNo(buildDeductOrder.getOutBizNo());
		deductDepositInfo.setDepositAmount(buildDeductOrder.getAmount());
		deductDepositInfo.setCardNo(buildDeductOrder.getBankAccountNo());
		deductDepositInfo.setBankName(buildDeductOrder.getBankName());
		deductDepositInfo.setDepositStatus(DepositStatusEnum.INITIAL);
		deductDepositInfo.setGid(buildDeductOrder.getGid());
		deductDepositInfoService.save(deductDepositInfo);
		deductDepositInfo = deductDepositInfoService.get(deductDepositInfo.getId());
		
		DepositResult result = payengineClient.applyUniteDeductDeposit(buildDeductOrder);
		if (result == null) {
			throw new PaymentException(PaymentResultCode.DEDUCT_DEPOSIT_FAIL,
				PaymentResultCode.DEDUCT_DEPOSIT_FAIL.getMessage());
		}
		if (PayengineResultEnum.EXECUTE_SUCCESS.getCode().equalsIgnoreCase(result.getErrCodeCtx().getCode())) {
			deductDepositInfo.setDepositStatus(DepositStatusEnum.SUCCESS);
		} else if (PayengineResultEnum.BIZ_PROCESSING.getCode().equalsIgnoreCase(result.getErrCodeCtx().getCode())) {
			deductDepositInfo.setDepositStatus(DepositStatusEnum.PROCESSING);
		} else {
			deductDepositInfo.setDepositStatus(DepositStatusEnum.FAILURE);
		}
		
		DepositInstructionInfo depositInstructionInfo = result.getInstructionInfo();
		//更新数据库
		deductDepositInfo.setDepositNo(depositInstructionInfo.getDepositId());
		deductDepositInfo.setActAmount(depositInstructionInfo.getPayAmountIn());
		deductDepositInfo.setMemo(result.getDescription());
		deductDepositInfoService.update(deductDepositInfo);
		
		if (result.isFail()) {
			logger.info("代扣失败, 失败原因：{}", result.getDescription());
			throw new PaymentException(PaymentResultCode.DEDUCT_DEPOSIT_FAIL, result.getDescription());
		}
		
		return deductDepositInfo.getDepositStatus();
	}
	
	/**
	 * 查询超级路由渠道
	 * 商户必须去超级路由开通代扣
	 * 
	 * @param deductDepositOrder
	 * @param pactBankCardInfo
	 * @return
	 */
	private ChannelRouteResult supperRouteChannel(DeductDepositOrder deductDepositOrder, PactBankCardInfo pactBankCardInfo){
		ChannelRouteOrder routeOrder = new ChannelRouteOrder();
		routeOrder.setBizCode(Constant.ROUTE_ORDER_API);
		routeOrder.setSubBizCode(deductDepositOrder.getPartnerId());
		routeOrder.setPersonalCorporateType(PersonalCorporateTypeEnum.PERSONAL);
		routeOrder.setDebitCreditType(DebitCreditTypeEnum.getByCode(pactBankCardInfo.getCardType().code()));
		routeOrder.setTransactionType(TransactionTypeEnum.DEPOSIT);
		routeOrder.setSubTransactionType(SubTransactionTypeEnum.DEDUCT);
		routeOrder.setAmount(deductDepositOrder.getAmount());
		routeOrder.setInstBankId(pactBankCardInfo.getBankCode());
		routeOrder.setUserId(deductDepositOrder.getUserId());
		routeOrder.setBankAccountNo(pactBankCardInfo.getCardNo());
		//统一订单号增加
		routeOrder.setGid(GID.newGID());
		routeOrder.setPartnerId(deductDepositOrder.getPartnerId());
		routeOrder.setMerchOrderNo(deductDepositOrder.getMerchOrderNo());
		
		ChannelRouteResult channelRouteResult = superRouteClient.channelRoute(routeOrder);
		return channelRouteResult;
	}
	
	/**
	 * build代扣订单
	 * @param deductDepositOrder
	 * @param pactBankCardInfo
	 * @return
	 */
	private ApplyDepositDeductOrder buildDeductOrder(DeductDepositOrder deductDepositOrder,
														PactBankCardInfo pactBankCardInfo) {
		ApplyDepositDeductOrder order = new ApplyDepositDeductOrder();
		order.setUserId(deductDepositOrder.getUserId());
		order.setAmount(deductDepositOrder.getAmount());
		// 订单号
		order.setOutBizNo(deductDepositOrder.getOutBizNo());
		order.setBankAccountNo(pactBankCardInfo.getCardNo());
		// 银行账户开户名
		order.setBankAccountName(pactBankCardInfo.getCardName());
		// 证件类型
		order.setCertType(pactBankCardInfo.getCertType());
		// 证件号
		order.setCertNo(pactBankCardInfo.getCertNo());
		// 银行市名
		order.setCityName(pactBankCardInfo.getCity());
		// 银行省名
		order.setProvName(pactBankCardInfo.getProvince());
		// 回执地址
		order.setVersion(version);
		order.setGroup(group);
		// 业务标识
		order.setBizIdentity(BusinessNoEnum.DEPOSIT_UNIFORM_PRODUCT.getBizCategory());
		order.setBizNo(BusinessNoEnum.DEPOSIT_UNIFORM_PRODUCT.getCode());
		order.setMemo("缴费充值");
		// 支付渠道
		order.setBankCode(pactBankCardInfo.getBankCode());
		order.setBankName(pactBankCardInfo.getBankName());
		ChannelRouteResult channelRouteResult = supperRouteChannel(deductDepositOrder, pactBankCardInfo);
		if (channelRouteResult.getTransactionContext() == null) {
			logger.info("查询查询代扣路由渠道失败，channelRouteResult={}", channelRouteResult);
			throw new PaymentException(PaymentResultCode.CHANNEL_ROUTE_EMPUTY,
				PaymentResultCode.CHANNEL_ROUTE_EMPUTY.getMessage());
		}
		order.setTransactionContext(channelRouteResult.getTransactionContext());
		//大数据增加
		order.setInlet(deductDepositOrder.getInlet());
		order.setMerchantUserId(deductDepositOrder.getPartnerId());
		order.setMerchantAccNo(deductDepositOrder.getPartnerId());
		order.setMerchantCardNo(deductDepositOrder.getPartnerId());
		order.setMerchantOrderBizNo(deductDepositOrder.getOutBizNo());
		//统一订单号修改
		order.setGid(deductDepositOrder.getGid());
		order.setPartnerId(deductDepositOrder.getPartnerId());
		order.setMerchOrderNo(deductDepositOrder.getMerchOrderNo());
		return order;
	}
	
}

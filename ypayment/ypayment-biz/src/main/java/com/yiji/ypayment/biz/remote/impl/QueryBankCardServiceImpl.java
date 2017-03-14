/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:44:11 创建
 */
package com.yiji.ypayment.biz.remote.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yiji.authenticate.enums.PactPurposeEnum;
import com.yiji.authenticate.enums.PactStatusEnum;
import com.yiji.authenticate.facade.query.info.PactCommonInfo;
import com.yiji.authenticate.facade.query.order.QueryByPactNoOrder;
import com.yiji.authenticate.facade.query.order.QueryByUserInfoOrder;
import com.yiji.authenticate.facade.query.result.PactCommonListResult;
import com.yiji.authenticate.facade.query.result.PactCommonResult;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.remote.QueryBankCardService;
import com.yiji.ypayment.biz.remote.base.RemoteServiceBase;
import com.yiji.ypayment.biz.remote.info.PactBankCardInfo;
import com.yiji.ypayment.biz.remote.info.PactQueryResultInfo;
import com.yiji.ypayment.biz.remote.order.PactQueryOrder;
import com.yiji.ypayment.dal.enums.DebitCreditEnum;
import com.yiji.ypayment.facade.enums.PaymentResultCode;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("queryBankCardService")
public class QueryBankCardServiceImpl extends RemoteServiceBase implements QueryBankCardService {
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.biz.remote.BankCardPayService#queryByPartUserPur(com.yiji.ypayment.biz.remote.order.PactQueryOrder)
	 */
	@Override
	public PactQueryResultInfo queryBankCard(PactQueryOrder order) {
		try {
			QueryByUserInfoOrder QueryByUserInfoOrder = new QueryByUserInfoOrder();
			//统一订单号修改
			QueryByUserInfoOrder.setGid(order.getGid());
			QueryByUserInfoOrder.setPartnerId(order.getPartnerId());
			QueryByUserInfoOrder.setMerchOrderNo(order.getMerchOrderNo());
			QueryByUserInfoOrder.setPurpose(PactPurposeEnum.DEDUCT);
			QueryByUserInfoOrder.setPartnerId(order.getPartnerId());
			QueryByUserInfoOrder.setUserId(order.getUserId());
			QueryByUserInfoOrder.setStatus(PactStatusEnum.PACT_SUCCESS);
			QueryByUserInfoOrder.setSubPartnerId(order.getPartnerId());
			PactCommonListResult result = pactQueryClient.queryByPartUserPur(QueryByUserInfoOrder);
			if (result == null) {
				throw new PaymentException(PaymentResultCode.PACT_QUERY_FAIL,
					PaymentResultCode.PACT_QUERY_FAIL.getMessage());
			}
			if (!result.isSuccess()) {
				throw new PaymentException(PaymentResultCode.PACT_QUERY_FAIL, result.getDescription());
			}
			if (result.getList() == null || result.getList().isEmpty()) {
				PactQueryResultInfo resultInfo = new PactQueryResultInfo();
				return resultInfo;
			}
			List<PactBankCardInfo> pactBankCardInfos = Lists.newArrayList();
			for (PactCommonInfo pactCommonInfo : result.getList()) {
				pactBankCardInfos.add(buildPactBankCardInfo(pactCommonInfo, true));
			}
			PactQueryResultInfo resultInfo = new PactQueryResultInfo();
			resultInfo.setPactBankCards(pactBankCardInfos);
			return resultInfo;
		} catch (Exception e) {
			logger.error("签约信息查询失败, 失败原因：{}", e.getMessage(), e);
			throw new PaymentException(PaymentResultCode.PACT_QUERY_FAIL, e.getMessage());
		}
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yiji.ypayment.biz.remote.QueryBankCardService#queryByPactNo(com.yjf.pact.facade.query.order.QueryByPactNoOrder)
	 */
	@Override
	public PactBankCardInfo queryByPactNo(QueryByPactNoOrder order) {
		try {
			PactCommonResult result = pactQueryClient.queryByPactNo(order);
			if (result == null) {
				throw new PaymentException(PaymentResultCode.PACT_QUERY_FAIL,
					PaymentResultCode.PACT_QUERY_FAIL.getMessage());
			}
			if (!result.isSuccess()) {
				throw new PaymentException(PaymentResultCode.PACT_QUERY_FAIL, result.getDescription());
			}
			return buildPactBankCardInfo(result.getPactCommonInfo(), false);
		} catch (Exception e) {
			logger.error("根据签约号查询绑卡信息失败, 失败原因：{}", e.getMessage(), e);
			throw new PaymentException(PaymentResultCode.PACT_QUERY_FAIL, e.getMessage());
		}
	}
	
	private PactBankCardInfo buildPactBankCardInfo(PactCommonInfo pactCommonInfo, boolean hideCode) {
		PactBankCardInfo pactBankCardInfo = new PactBankCardInfo();
		pactBankCardInfo.setBankCode(pactCommonInfo.getBankCode());
		pactBankCardInfo.setBankName(pactCommonInfo.getBankName());
		pactBankCardInfo.setCardName(pactCommonInfo.getCardName());
		String CardNo = pactCommonInfo.getCardNo();
		if (hideCode) {
			pactBankCardInfo.setCardNo(CardNo.substring(CardNo.length() - 4, CardNo.length()));
		} else {
			pactBankCardInfo.setCardNo(CardNo);
		}
		pactBankCardInfo.setCardType(DebitCreditEnum.getByCode(pactCommonInfo.getCardType().getCode()));
		pactBankCardInfo.setCertNo(pactCommonInfo.getCertNo());
		pactBankCardInfo.setCertType(pactCommonInfo.getCertType());
		pactBankCardInfo.setCity(pactCommonInfo.getCity());
		pactBankCardInfo.setProvince(pactCommonInfo.getProvince());
		pactBankCardInfo.setPartnerId(pactCommonInfo.getPartnerId());
		pactBankCardInfo.setPactNo(pactCommonInfo.getPactNo());
		return pactBankCardInfo;
	}
	
}

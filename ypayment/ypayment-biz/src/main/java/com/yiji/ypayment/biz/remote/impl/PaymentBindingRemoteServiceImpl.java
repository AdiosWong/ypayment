/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月26日 下午3:27:14 创建
 */

package com.yiji.ypayment.biz.remote.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.remote.CustomerRemoteService;
import com.yiji.ypayment.biz.remote.PaymentBindingRemoteService;
import com.yiji.ypayment.biz.remote.PaymentQueryRemoteService;
import com.yiji.ypayment.biz.remote.base.RemoteServiceBase;
import com.yiji.ypayment.biz.remote.info.PaymentQueryInfo;
import com.yiji.ypayment.dal.entity.business.PaymentBindingInfo;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.enums.PaymentValidStatus;
import com.yiji.ypayment.facade.order.payment.PaymentBindingOrder;
import com.yiji.ypayment.facade.order.payment.PaymentUnbindingOrder;
import com.yiji.ypayment.facade.order.query.PaymentQueryOrder;
import com.yiji.ypayment.facade.result.payment.PaymentBindingResult;
import com.yiji.ypayment.facade.result.payment.PaymentUnbindingResult;
import com.yjf.customer.service.order.UniformStringQueryOrder;

/**
 * 用户缴费绑定服务
 *
 * @author faZheng
 *
 */
@Service("paymentBindingRemoteService")
public class PaymentBindingRemoteServiceImpl extends RemoteServiceBase implements PaymentBindingRemoteService {
	
	@Autowired
	private CustomerRemoteService customerRemoteService;
	@Autowired
	private PaymentQueryRemoteService paymentQueryRemoteService;
	
	/**
	 * @param paymentBindingOrder
	 * @return
	 * @see com.yiji.ypayment.biz.remote.PaymentBindingRemoteService#PaymentBindingService(com.yiji.ypayment.facade.order.payment.PaymentBindingOrder)
	 */
	@Override
	public PaymentBindingResult paymentBinding(PaymentBindingOrder order) {
		
		order.check();
		
		// 业务平台check
		checkPlatformAndPaymentType(order.getPlatformType(), order.getPaymentType(), order.getPartnerId(), null);
		
		PaymentBindingInfo paymentBindingInfo = paymentBindingInfoService
			.findByUserIdAndPayFromAndUserCodeAndResourceCode(order.getUserId(), order.getPlatformType(),
				order.getUserCode(), order.getResourceCode());
		if (paymentBindingInfo != null) {
			if (PaymentValidStatus.INVALID == paymentBindingInfo.getStatus()) {
				paymentBindingInfo.setStatus(PaymentValidStatus.VALID);
				paymentBindingInfoService.save(paymentBindingInfo);
			}
		} else {
			String userId = order.getUserId();
			logger.info("会员信息保存，入参userId：{}", userId);
			UniformStringQueryOrder uniformStringQueryOrder = new UniformStringQueryOrder();
			uniformStringQueryOrder.setGid(order.getGid());
			uniformStringQueryOrder.setMerchOrderNo(order.getMerchOrderNo());
			uniformStringQueryOrder.setPartnerId(order.getPartnerId());
			uniformStringQueryOrder.setParam(order.getUserId());
			customerRemoteService.createPaymentUser(uniformStringQueryOrder);
			
			String contractNo = commonService.generateBizNo(PaymentInstructionAction.CREAT_BINDING);
			paymentBindingInfo = new PaymentBindingInfo();
			paymentBindingInfo.setUserId(userId);
			paymentBindingInfo.setContractNo(contractNo);
			paymentBindingInfo.setResourceCode(order.getResourceCode());
			paymentBindingInfo.setResourceName(order.getResourceName());
			paymentBindingInfo.setUserCode(order.getUserCode());
			paymentBindingInfo.setUserName(getUserNameByCode(order));
			paymentBindingInfo.setPaymentType(order.getPaymentType());
			paymentBindingInfo.setStatus(PaymentValidStatus.VALID);
			paymentBindingInfo.setPayFrom(order.getPlatformType());
			logger.info("缴费绑定，入参PaymentBindingInfo：{}", paymentBindingInfo);
			try {
				paymentBindingInfoService.save(paymentBindingInfo);
			} catch (Exception e) {
				logger.error("绑定发生异常,改用户已经绑卡!");
				throw new PaymentException(PaymentResultCode.USER_ALREADY_BIND_CARD, "该用户已经绑卡!");
			}
		}
		PaymentBindingResult result = new PaymentBindingResult();
		result.setUserId(paymentBindingInfo.getUserId());
		result.setContractNo(paymentBindingInfo.getContractNo());
		result.setUserCode(paymentBindingInfo.getUserCode());
		result.setUserName(paymentBindingInfo.getUserName());
		result.setBindingStatus(paymentBindingInfo.getStatus());
		return result;
	}
	
	/**
	 * @param PaymentUnbindingOrder
	 * @see com.yiji.ypayment.biz.remote.PaymentBindingRemoteService#paymentUnBinding(com.yiji.ypayment.facade.order.payment.PaymentUnbindingOrder)
	 */
	@Override
	public PaymentUnbindingResult paymentUnBinding(PaymentUnbindingOrder order) {
		order.check();
		PaymentBindingInfo paymentBindingInfo = paymentBindingInfoService.findByUserIdAndContractNo(order.getUserId(),
			order.getContractNo());
		if (paymentBindingInfo == null) {
			throw new PaymentException(PaymentResultCode.USER_NOT_BIND_CARD, "该用户还未绑卡，无法解绑");
		}
		paymentBindingInfo.setStatus(PaymentValidStatus.INVALID);
		paymentBindingInfoService.save(paymentBindingInfo);
		PaymentUnbindingResult result = new PaymentUnbindingResult();
		result.setUserId(paymentBindingInfo.getUserId());
		result.setContractNo(paymentBindingInfo.getContractNo());
		result.setUserCode(paymentBindingInfo.getUserCode());
		result.setUserName(paymentBindingInfo.getUserName());
		result.setBindingStatus(paymentBindingInfo.getStatus());
		return result;
	}
	
	/**
	 * 通过userCode获取userName
	 * @param order
	 * @return
	 */
	public String getUserNameByCode(PaymentBindingOrder order){
		PaymentQueryOrder queryOrder = new PaymentQueryOrder();
		queryOrder.setPaymentType(order.getPaymentType());
		queryOrder.setPlatformType(order.getPlatformType());
		queryOrder.setResourceCode(order.getResourceCode());
		queryOrder.setUserCode(order.getUserCode());
		queryOrder.setUserId(order.getUserId());
		queryOrder.setGid(order.getGid());
		queryOrder.setPartnerId(order.getPartnerId());
		queryOrder.setMerchOrderNo(order.getMerchOrderNo());
		PaymentQueryInfo paymentQueryInfo = paymentQueryRemoteService.queryPayment(queryOrder);
		return paymentQueryInfo.getUsername();
	}
	
}

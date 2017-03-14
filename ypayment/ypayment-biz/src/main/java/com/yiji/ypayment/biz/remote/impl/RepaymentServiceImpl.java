/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:04:00 创建
 */
package com.yiji.ypayment.biz.remote.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiji.ypayment.biz.remote.PaymentRemoteService;
import com.yiji.ypayment.biz.remote.RepaymentService;
import com.yiji.ypayment.biz.remote.base.RemoteServiceBase;
import com.yiji.ypayment.biz.remote.info.PaymentResultInfo;
import com.yiji.ypayment.biz.remote.order.ApplyPaymentForm;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.entity.business.RepayOrderInfo;
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentModelEnum;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.info.payment.PaymentItem;
import com.yiji.ypayment.facade.info.query.PayOrderInfo;
import com.yjf.common.id.OID;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("repaymentService")
public class RepaymentServiceImpl extends RemoteServiceBase implements RepaymentService {

	@Autowired
	private PaymentRemoteService paymentRemoteService;
	
	/**
	 * @param paymentOrderNo
	 * @see com.yiji.ypayment.biz.remote.RepaymentService#repayment(java.lang.String)
	 */
	@Override
	@Transactional
	public void repayment(String paymentOrderNo) {
		RepayOrderInfo repayOrderInfo = repayOrderInfoService.lockByPaymentOrderNo(paymentOrderNo);
		if(repayOrderInfo.getPaymentStatus() == PaymentItemStatusEnum.FAIL){
			PaymentOrder paymentOrder = paymentOrderService.findByPaymentOrderNo(paymentOrderNo);
			ApplyPaymentForm ApplyPaymentForm = buildRechargePaymentForm(paymentOrder);
			String repayOrderNo = ApplyPaymentForm.getPaymentItems().get(0).getPaymentOrderNo();
			repayOrderInfo.setRepayOrderNo(repayOrderNo);
			logger.info("手动发起补缴费，生成新的补缴订单：{}", repayOrderInfo);
			repayOrderInfoService.save(repayOrderInfo);
			PaymentResultInfo paymentResult = paymentRemoteService.payment(ApplyPaymentForm);
			if(PaymentResultCode.EXECUTE_SUCCESS == paymentResult.getResultCode()){
				repayOrderInfo.setPaymentStatus(PaymentItemStatusEnum.HANGUP);
				for(PayOrderInfo payOrderInfo : paymentResult.getPayOrderInfos()){
					repayOrderInfo.setPaymentStatus(payOrderInfo.getPaymentStatus());
				}
				repayOrderInfoService.save(repayOrderInfo);
			}
		}
	}
	
	/**
	 * 构建缴费入参订单
	 * @param payForm
	 * @return
	 */
	private ApplyPaymentForm buildRechargePaymentForm(PaymentOrder paymentOrder) {
		ApplyPaymentForm applyPaymentForm = new ApplyPaymentForm();
		applyPaymentForm.setUserId(paymentOrder.getUserId());
		applyPaymentForm.setInlet(paymentOrder.getInlet());
		applyPaymentForm.setPlatformType(paymentOrder.getPayFrom());
		applyPaymentForm.setOutBizNo(OID.newID());
		applyPaymentForm.setPaymentAmount(paymentOrder.getPayable());
		applyPaymentForm.setPaymentType(paymentOrder.getPaymentType());
		if(PaymentTypeEnum.MOBILE == paymentOrder.getPaymentType()){
			applyPaymentForm.setPaymentModel(PaymentModelEnum.RECHARGE);
		}else{
			applyPaymentForm.setPaymentModel(PaymentModelEnum.PAYMENT);
		}
		applyPaymentForm.setUserCode(paymentOrder.getUserCode());
		applyPaymentForm.setResourceCode(paymentOrder.getResourceCode());
		//统一订单号增加
		applyPaymentForm.setGid(paymentOrder.getGid());
		applyPaymentForm.setPartnerId(paymentOrder.getPartnerId());
		applyPaymentForm.setMerchOrderNo(paymentOrder.getMerchOrderNo());
		//构建子订单
		PaymentItem paymentItem = new PaymentItem();
		paymentItem.setAmount(paymentOrder.getPayable());
		paymentItem.setPaymentOrderNo(OID.newID());
		paymentItem.setPaymentType(paymentOrder.getPaymentType());
		applyPaymentForm.getPaymentItems().add(paymentItem);
		return applyPaymentForm;
	}

	/**
	 * @param paymentOrder
	 * @see com.yiji.ypayment.biz.remote.RepaymentService#HandRepaymentResult(com.yiji.ypayment.dal.entity.business.PaymentOrder)
	 */
	@Override
	@Transactional
	public void HandRepaymentResult(PaymentOrder paymentOrder) {
		RepayOrderInfo repayOrderInfo = repayOrderInfoService.lockByRepayOrderNo(paymentOrder.getPaymentOrderNo());
		if(repayOrderInfo != null && repayOrderInfo.getPaymentStatus() == PaymentItemStatusEnum.HANGUP){
			if(paymentOrder.getPaymentStatus() == PaymentItemStatusEnum.SUCCESS){
				repayOrderInfo.setPaymentStatus(PaymentItemStatusEnum.SUCCESS);
				repayOrderInfoService.save(repayOrderInfo);
			}else if (paymentOrder.getPaymentStatus() == PaymentItemStatusEnum.FAIL) {
				repayOrderInfo.setPaymentStatus(PaymentItemStatusEnum.FAIL);
				repayOrderInfoService.save(repayOrderInfo);
			}
		}
	}
	
}





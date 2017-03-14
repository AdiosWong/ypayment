/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年10月27日 下午3:27:12 创建
 */

package com.yiji.ypayment.test.applyPayment;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yiji.ypayment.biz.remote.order.ApplyPaymentForm;
import com.yiji.ypayment.common.constant.Constant;
import com.yiji.ypayment.facade.api.Payment.ApplyPaymentFacade;
import com.yiji.ypayment.facade.enums.PayWayEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.enums.UndoApproachEnum;
import com.yiji.ypayment.facade.order.payment.UndoPaymentOrder;
import com.yiji.ypayment.facade.result.payment.ApplyPaymentResult;
import com.yiji.ypayment.facade.result.payment.PaymentUndoOrderResult;
import com.yiji.ypayment.test.base.TestBase;
import com.yjf.common.id.GID;
import com.yjf.common.id.OID;
import com.yjf.common.lang.util.money.Money;

/**
 *
 *
 * @author faZheng
 *
 */

public class ApplyPaymentFacadeTest extends TestBase {
	
	@Autowired
	private ApplyPaymentFacade applyPaymentFacade;
	
	@Test
	@Ignore
	public void applyPaymentWaterTest() {
		ApplyPaymentForm paymentOrder = new ApplyPaymentForm();
		paymentOrder.setContractNo("20151103000000000003");
		paymentOrder.setMerchOrderNo(OID.newID());
		paymentOrder.setPaymentAmount(new Money(31));
		paymentOrder.setPlatformType("ypayment");
		paymentOrder.setPaymentType(PaymentTypeEnum.WATER);
		paymentOrder.setUserId("20141225010000062065");
		paymentOrder.setPartnerId(Constant.VIRTUAL_MERCHANT);
		paymentOrder.setGid(GID.newGID());
		paymentOrder.setPayWay(PayWayEnum.BY_BALANCE);
		ApplyPaymentResult result = applyPaymentFacade.applyPayment(paymentOrder);
		logger.info("缴费结果, code:{}", result.getCode());
		logger.info("缴费结果, description:{}", result.getDescription());
	}
	
	@Test
	@Ignore
	public void applyPaymentGasTest() {
		ApplyPaymentForm paymentOrder = new ApplyPaymentForm();
		paymentOrder.setContractNo("20151103000000000001");
		paymentOrder.setMerchOrderNo(OID.newID());
		paymentOrder.setPaymentAmount(new Money(73.69));
		paymentOrder.setPlatformType("ypayment");
		paymentOrder.setPaymentType(PaymentTypeEnum.GAS);
		paymentOrder.setUserId("20141225010000062065");
		paymentOrder.setPartnerId(Constant.VIRTUAL_MERCHANT);
		paymentOrder.setGid(GID.newGID());
		paymentOrder.setPayWay(PayWayEnum.BY_BALANCE);
		ApplyPaymentResult result = applyPaymentFacade.applyPayment(paymentOrder);
		logger.info("缴费结果, code:{}", result.getCode());
		logger.info("缴费结果, description:{}", result.getDescription());
	}
	
	@Test
	@Ignore
	public void applyPaymentElectTest() {
		ApplyPaymentForm paymentOrder = new ApplyPaymentForm();
		paymentOrder.setContractNo("20151103000000000002");
		paymentOrder.setMerchOrderNo(OID.newID());
		paymentOrder.setPaymentAmount(new Money(60.00));
		paymentOrder.setPlatformType("ypayment");
		paymentOrder.setPaymentType(PaymentTypeEnum.ELECTRICITY);
		paymentOrder.setUserId("20140909010000052154");
		paymentOrder.setPartnerId(Constant.VIRTUAL_MERCHANT);
		paymentOrder.setGid(GID.newGID());
		paymentOrder.setPayWay(PayWayEnum.BY_BALANCE);
		ApplyPaymentResult result = applyPaymentFacade.applyPayment(paymentOrder);
		logger.info("缴费结果, code:{}", result.getCode());
		logger.info("缴费结果, description:{}", result.getDescription());
	}
	
	@Test
	@Ignore
	public void applyPaymentPhoneTest() {
		ApplyPaymentForm paymentOrder = new ApplyPaymentForm();
		paymentOrder.setMerchOrderNo(OID.newID());
		paymentOrder.setPaymentAmount(new Money(20));
		paymentOrder.setPlatformType("ypayment");
		// 测试成功
		paymentOrder.setUserCode("18523125117");
		// paymentOrder.setMobilePhoneNo("13822222222");
		// 测试失败
		// paymentOrder.setMobilePhoneNo("15123334382");
		// 测试挂起
		//paymentOrder.setMobilePhoneNo("13811111111");
		//paymentOrder.setMobilePhoneNo("13811111113");
		paymentOrder.setPaymentType(PaymentTypeEnum.MOBILE);
		paymentOrder.setUserId("20141225010000062065");
		paymentOrder.setPartnerId(Constant.VIRTUAL_MERCHANT);
		paymentOrder.setGid(GID.newGID());
		paymentOrder.setPayWay(PayWayEnum.BY_BALANCE);
		ApplyPaymentResult result = applyPaymentFacade.applyPayment(paymentOrder);
		logger.info("缴费结果, code:{}", result.getCode());
		logger.info("缴费结果, description:{}", result.getDescription());
	}
	
	@Test
	@Ignore
	public void undoPaymentTest() {
		UndoPaymentOrder undoPaymentOrder = new UndoPaymentOrder();
		undoPaymentOrder.setUserId("20150116010000063674"); 
		undoPaymentOrder.setGid(GID.newGID());
		undoPaymentOrder.setMerchOrderNo(OID.newID());
		undoPaymentOrder.setPartnerId("20141106020000058750");
		undoPaymentOrder.setPlatformType("YiPinHui");
		undoPaymentOrder.setPaymentOrderNo("20160622000000003803");
		undoPaymentOrder.setUndoApproach(UndoApproachEnum.RECOMMEND);
		PaymentUndoOrderResult result = applyPaymentFacade.undoPayment(undoPaymentOrder);
		logger.info("返销结果, code:{}", result.getCode());
		logger.info("返销结果, description:{}", result.getDescription());
	}
	
}

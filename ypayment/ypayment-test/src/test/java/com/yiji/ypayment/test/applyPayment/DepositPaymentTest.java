package com.yiji.ypayment.test.applyPayment;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yiji.authenticate.enums.PactPurposeEnum;
import com.yiji.authenticate.facade.query.order.QueryByPactNoOrder;
import com.yiji.ypayment.biz.remote.PayengineService;
import com.yiji.ypayment.biz.remote.PaymentRemoteService;
import com.yiji.ypayment.biz.remote.QueryBankCardService;
import com.yiji.ypayment.biz.remote.info.PactBankCardInfo;
import com.yiji.ypayment.biz.remote.order.DeductDepositOrder;
import com.yiji.ypayment.biz.service.ypayment.DeductDepositInfoService;
import com.yiji.ypayment.common.constant.Constant;
import com.yiji.ypayment.dal.entity.business.DeductDepositInfo;
import com.yiji.ypayment.test.base.TestBase;
import com.yjf.common.id.GID;
import com.yjf.common.id.OID;
import com.yjf.common.lang.util.money.Money;

public class DepositPaymentTest extends TestBase {
	
	@Autowired
	private PayengineService payengineService;
	@Autowired
	private PaymentRemoteService paymentRemoteService;
	@Autowired
	private QueryBankCardService queryBankCardService;
	@Autowired
	private DeductDepositInfoService deductDepositInfoService;
	
	
	@Test
	@Ignore
	public void depositPaymentPhoneTest() {
		// 查询银行卡
		QueryByPactNoOrder order = new QueryByPactNoOrder();
		order.setPactNo("Fm3Iqahm915672");
		order.setPurpose(PactPurposeEnum.DEDUCT);
		order.setGid(GID.newGID());
		order.setPartnerId("20141106020000058750");
		order.setMerchOrderNo(OID.newID());
		
		PactBankCardInfo pactBankCardInfo = queryBankCardService.queryByPactNo(order);
		// 发起代扣
		DeductDepositOrder deductDepositOrder = new DeductDepositOrder();
		deductDepositOrder.setAmount(new Money(10));
		deductDepositOrder.setInlet(Constant.INLTE);
		deductDepositOrder.setGid(GID.newGID());
		deductDepositOrder.setMerchOrderNo(OID.newID());
		deductDepositOrder.setPartnerId("20141106020000058750");
		deductDepositOrder.setUserId("20141225010000062065");
		deductDepositOrder.setOutBizNo(OID.newID());
		payengineService.deductDeposit(deductDepositOrder, pactBankCardInfo);
	}
	
	@Test
	@Ignore
	public void queryDepositTest() {
		DeductDepositInfo deductDepositInfo = deductDepositInfoService.findByDepositBizNo("000400801fuy4ntxoc00");
		logger.info("根据代扣订单号查询代扣信息, deductDepositInfo:{}", deductDepositInfo);
	}
	
}

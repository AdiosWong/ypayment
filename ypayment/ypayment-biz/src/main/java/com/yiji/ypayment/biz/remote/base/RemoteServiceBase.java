/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author faZheng 下午8:17:12 创建
 */
package com.yiji.ypayment.biz.remote.base;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.yiji.ypayment.biz.common.servcie.CommonService;
import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.remote.order.ApplyPaymentForm;
import com.yiji.ypayment.biz.service.manage.ConfigService;
import com.yiji.ypayment.biz.service.ypayment.BillItemInfoService;
import com.yiji.ypayment.biz.service.ypayment.CustomerInfoService;
import com.yiji.ypayment.biz.service.ypayment.DeductDepositInfoService;
import com.yiji.ypayment.biz.service.ypayment.PaymentBindingInfoService;
import com.yiji.ypayment.biz.service.ypayment.PaymentItemInfoService;
import com.yiji.ypayment.biz.service.ypayment.PaymentOrderService;
import com.yiji.ypayment.biz.service.ypayment.PaymentTradeService;
import com.yiji.ypayment.biz.service.ypayment.PlatformTypeService;
import com.yiji.ypayment.biz.service.ypayment.RepayOrderInfoService;
import com.yiji.ypayment.biz.service.ypayment.UndoPaymentService;
import com.yiji.ypayment.dal.entity.business.BillItemInfo;
import com.yiji.ypayment.dal.entity.business.PaymentBindingInfo;
import com.yiji.ypayment.dal.entity.business.PaymentItemInfo;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.entity.business.PlatformType;
import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.facade.enums.PayWayEnum;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.enums.PaymentValidStatus;
import com.yiji.ypayment.integration.client.AccountClient;
import com.yiji.ypayment.integration.client.CustomerClient;
import com.yiji.ypayment.integration.client.OpenApiArchClient;
import com.yiji.ypayment.integration.client.PactQueryClient;
import com.yiji.ypayment.integration.client.PayengineClient;
import com.yiji.ypayment.integration.client.RoutePaymentClient;
import com.yiji.ypayment.integration.client.RoutePaymentQueryClient;
import com.yiji.ypayment.integration.client.RouteQueryClient;
import com.yiji.ypayment.integration.client.SuperRouteClient;
import com.yiji.ypayment.integration.client.TradeClient;
import com.yjf.common.id.OID;
import com.yjf.common.lang.util.money.Money;
import com.yjf.customer.service.order.UniformStringQueryOrder;
import com.yjf.quickpayment.route.info.PaymentInfo;
import com.yjf.quickpayment.route.info.PaymentInfo.InfoItem;

/**
 * 公共服务
 * 
 * @author faZheng
 *
 */
public class RemoteServiceBase {
	
	@Autowired
	protected CommonService commonService;
	@Autowired
	protected CustomerClient customerClient;
	@Autowired
	protected AccountClient accountClient;
	@Autowired
	protected TradeClient tradeClient;
	@Autowired
	protected PayengineClient payengineClient;
	@Autowired
	protected RoutePaymentClient routePaymentClient;
	@Autowired
	protected RoutePaymentQueryClient routePaymentQueryClient;
	@Autowired
	protected RouteQueryClient roumeteQueryClient;
	@Autowired
	protected PactQueryClient pactQueryClient;
	@Autowired
	protected SuperRouteClient superRouteClient;
	@Autowired 
	protected OpenApiArchClient openApiArchClient;
	
	@Autowired
	protected CustomerInfoService customerInfoService;
	@Autowired
	protected PaymentTradeService paymentTradeService;
	@Autowired
	protected PaymentBindingInfoService paymentBindingInfoService;
	@Autowired
	protected PaymentOrderService paymentOrderService;
	@Autowired
	protected PaymentItemInfoService paymentItemInfoService;
	@Autowired
	protected UndoPaymentService undoPaymentService;
	@Autowired
	protected ConfigService configService;
	@Autowired
	protected PlatformTypeService platformTypeService;
	@Autowired
	protected DeductDepositInfoService deductDepositInfoService;
	@Autowired
	protected BillItemInfoService billItemInfoService;
	@Autowired
	protected RepayOrderInfoService repayOrderInfoService;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 校验业务平台信息
	 * @param platformType
	 * @param paymentType
	 * @param partnerId
	 */
	protected void checkPlatformAndPaymentType(String platformType, PaymentTypeEnum paymentType, String partnerId, PayWayEnum payWay) {
		if (StringUtils.isBlank(platformType)) {
			logger.info("入参不完整, 业务平台类型:{}", platformType);
			throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "业务平台类型不能为空");
		} else if (null == paymentType) {
			logger.info("入参不完整, 缴费类型为空！");
			throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "缴费类型不能为空");
		}
		
		PlatformType platformTypeEntity = platformTypeService.findByPlatformType(platformType);
		if (null == platformTypeEntity) {
			logger.info("非法的业务平台, 业务平台类型:{}, 缴费类型:{}", platformType, paymentType);
			throw new PaymentException(PaymentResultCode.EXECUTE_FAIL, "非法的业务平台类型:" + platformType);
		}
		
		if (!partnerId.equals(platformTypeEntity.getPartnerId())) {
			logger.info("非法的平台商户ID, 入参partnerId：{}, 已配置partnerId：{}", partnerId, platformTypeEntity.getPartnerId());
			throw new PaymentException(PaymentResultCode.EXECUTE_FAIL, "非法的平台商户ID");
		}
		
		if (platformTypeEntity.getStatus() == PaymentValidStatus.INVALID) {
			logger.info("业务平台已被禁用, 请联系管理员核实, 平台状态：{}", platformTypeEntity.getStatus().message());
			throw new PaymentException(PaymentResultCode.EXECUTE_FAIL, "业务平台已被禁用, 请联系管理员核实");
		}
		
		if (payWay == PayWayEnum.BY_DEPOSIT && !platformTypeEntity.isDeposit()) {
			logger.info("业务平台不支持代, 支付类型：{}", PayWayEnum.BY_DEPOSIT.getMessage());
			throw new PaymentException(PaymentResultCode.EXECUTE_FAIL, "业务平台不支持代扣");
		}
		
		List<String> paymentTypeList = platformTypeEntity.getPaymentTypeList();
		if (null == paymentTypeList || paymentTypeList.size() == 0) {
			logger.info("业务平台支持的缴费类型为空");
			throw new PaymentException(PaymentResultCode.EXECUTE_FAIL, "业务平台暂不支持缴费, 请联系管理员开通缴费功能");
		} else if (!paymentTypeList.contains(paymentType.code())) {
			logger.info("业务平台不支持该缴费类型, 缴费类型:{}", paymentType);
			throw new PaymentException(PaymentResultCode.EXECUTE_FAIL, "业务平台不支持缴" + paymentType.message()
																		+ ", 请联系管理员开通该缴费功能");
		}
	}
	
	/**
	 * 校验业务平台
	 * 
	 * @param platformType
	 * @param partnerId
	 */
	protected void checkPlatform(String platformType, String partnerId) {
		if (StringUtils.isBlank(platformType)) {
			logger.info("入参不完整, 业务平台类型:{}", platformType);
			throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "业务平台类型不能为空");
		}
		
		PlatformType platformTypeEntity = platformTypeService.findByPlatformType(platformType);
		if (null == platformTypeEntity) {
			logger.info("非法的业务平台, 缴费类型:{}", platformType);
			throw new PaymentException(PaymentResultCode.EXECUTE_FAIL, "非法的业务平台类型:" + platformType);
		}
		
		if (!partnerId.equals(platformTypeEntity.getPartnerId())) {
			logger.info("非法的平台商户ID, 入参partnerId：{}, 已配置partnerId：{}", partnerId, platformTypeEntity.getPartnerId());
			throw new PaymentException(PaymentResultCode.EXECUTE_FAIL, "非法的平台商户ID");
		}
		
		if (platformTypeEntity.getStatus() == PaymentValidStatus.INVALID) {
			logger.info("业务平台已被禁用, 请联系管理员核实, 平台状态：{}", platformTypeEntity.getStatus().message());
			throw new PaymentException(PaymentResultCode.EXECUTE_FAIL, "业务平台已被禁用, 请联系管理员核实");
		}
	}
	
	/**
	 * 校验绑定信息
	 * 
	 * @param bindingInfo
	 * @param applyPaymentForm
	 */
	protected void checkBindingInfo(PaymentBindingInfo bindingInfo, ApplyPaymentForm applyPaymentForm){
		if (bindingInfo == null) {
			logger.info("根据签约号为查询到绑卡信息, 签约号：{}", applyPaymentForm.getContractNo());
			throw new PaymentException(PaymentResultCode.PAYMENT_QUERY_BINDING_INFO_FAIL);
		}
		if (bindingInfo.getStatus() == PaymentValidStatus.INVALID) {
			logger.info("用户未绑定缴费渠道或者绑定无效, 签约号：{}", applyPaymentForm.getContractNo());
			throw new PaymentException(PaymentResultCode.PAYMENT_USER_NOT_ACTIVE);
		}
		if (bindingInfo.getPaymentType() != applyPaymentForm.getPaymentType()) {
			logger.info("绑定类型和缴费类型不同,绑定类型:{},缴费类型：{}", bindingInfo.getPaymentType(), applyPaymentForm.getPaymentType());
			throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "缴费类型和绑定类型不同");
		}
		if (!bindingInfo.getUserId().equals(applyPaymentForm.getUserId())){
			logger.info("绑定userId和缴费userId不同,绑定userId:{},缴费userId：{}", bindingInfo.getUserId(), applyPaymentForm.getUserId());
			throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "缴费userId和绑定userId不同");
		}
	}
	
	/** 
	 * 余额支付
	 * 校验用户余额
	 * @param applyPaymentOrder
	 */
	protected void checkAvailableMoney(ApplyPaymentForm applyPaymentOrder){
		if (PayWayEnum.BY_BALANCE == applyPaymentOrder.getPayWay()) {
			//有优惠、余额，商户用户都要检查
			if (FavourableEnum.TRUE == applyPaymentOrder.getFavourable()) {
				UniformStringQueryOrder parternerOrder = new UniformStringQueryOrder();
				parternerOrder.setGid(applyPaymentOrder.getGid());
				parternerOrder.setMerchOrderNo(applyPaymentOrder.getMerchOrderNo());
				parternerOrder.setPartnerId(applyPaymentOrder.getPartnerId());
				parternerOrder.setParam(applyPaymentOrder.getPartnerId());
				Money partnerAmount = customerClient.getAvailableMoney(parternerOrder);
				Money paymentAmount = applyPaymentOrder.getPaymentAmount();
				if (paymentAmount.greaterThan(partnerAmount)) {
					logger.info("缴费金额大于易极付商户余额, 缴费金额：{}, 易极付商户余额：{}", paymentAmount.toStandardString(),
						partnerAmount.toStandardString());
					throw new PaymentException(PaymentResultCode.NOT_ENOUGH_BALANCE);
				}
				UniformStringQueryOrder userOrder = new UniformStringQueryOrder();
				userOrder.setGid(applyPaymentOrder.getGid());
				userOrder.setMerchOrderNo(applyPaymentOrder.getMerchOrderNo());
				userOrder.setPartnerId(applyPaymentOrder.getPartnerId());
				userOrder.setParam(applyPaymentOrder.getUserId());
				Money userAmount = customerClient.getAvailableMoney(userOrder);
				Money payAmount = applyPaymentOrder.getPayAmount();
				if (payAmount.greaterThan(userAmount)) {
					logger.info("优惠金额大于易极付用户余额, 优惠金额：{}, 易极付用户余额：{}", paymentAmount.toStandardString(),
						userAmount.toStandardString());
					throw new PaymentException(PaymentResultCode.NOT_ENOUGH_BALANCE);
				}
			} else {
				// 余额、无优惠，只检查用户余额
				UniformStringQueryOrder userOrder = new UniformStringQueryOrder();
				userOrder.setGid(applyPaymentOrder.getGid());
				userOrder.setMerchOrderNo(applyPaymentOrder.getMerchOrderNo());
				userOrder.setPartnerId(applyPaymentOrder.getPartnerId());
				userOrder.setParam(applyPaymentOrder.getUserId());
				Money availableAmount = customerClient.getAvailableMoney(userOrder);
				Money paymentAmount = applyPaymentOrder.getPaymentAmount();
				if (paymentAmount.greaterThan(availableAmount)) {
					logger.info("缴费金额大于易极付用户余额, 缴费金额：{}, 易极付用户余额：{}", paymentAmount.toStandardString(),
						availableAmount.toStandardString());
					throw new PaymentException(PaymentResultCode.NOT_ENOUGH_BALANCE);
				}
			}
		}else {
			//代扣、有优惠，只检查商户余额
			if (FavourableEnum.TRUE == applyPaymentOrder.getFavourable()) {
				UniformStringQueryOrder parternerOrder = new UniformStringQueryOrder();
				parternerOrder.setGid(applyPaymentOrder.getGid());
				parternerOrder.setMerchOrderNo(applyPaymentOrder.getMerchOrderNo());
				parternerOrder.setPartnerId(applyPaymentOrder.getPartnerId());
				parternerOrder.setParam(applyPaymentOrder.getPartnerId());
				Money partnerAmount = customerClient.getAvailableMoney(parternerOrder);
				Money paymentAmount = applyPaymentOrder.getPaymentAmount();
				if (paymentAmount.greaterThan(partnerAmount)) {
					logger.info("缴费金额大于易极付商户余额, 缴费金额：{}, 易极付商户余额：{}", paymentAmount.toStandardString(),
						partnerAmount.toStandardString());
					throw new PaymentException(PaymentResultCode.NOT_ENOUGH_BALANCE);
				}
			} 
		}
	}
	
	/**
	 * 构建缴费结果订单
	 * @param paymentInfo
	 * @param paymentOrder
	 * @return
	 */
	protected PaymentItemInfo buildPaymentItemInfo(PaymentInfo paymentInfo, PaymentOrder paymentOrder) {
		PaymentItemInfo itemInfo = new PaymentItemInfo();
		BeanUtils.copyProperties(paymentOrder, itemInfo, new String[] {"id", "rawAddTime", "rawUpdateTime"});
		itemInfo.setPaymentInfoNo(commonService.generateBizNo(PaymentInstructionAction
			.convertByPaymentTypeEnum(paymentOrder.getPaymentType())));
		itemInfo.setPaymentSerialNo(paymentInfo.getPayId());
		itemInfo.setResouceSerialNo(paymentInfo.getRouteSerialNumber());
		itemInfo.setUserName(paymentInfo.getUserName());
		itemInfo.setAddress(paymentInfo.getAddress());
		itemInfo.setMonth(paymentInfo.getMonth());
		itemInfo.setPaymentInfoStatus(paymentInfo.getStatus());
		itemInfo.setRubbish(paymentOrder.isRubbish());
		itemInfo.setShareAmount(paymentInfo.getCommission());
		itemInfo.setStartCount(paymentInfo.getStartCount());
		itemInfo.setEndCount(paymentInfo.getEndCount());
		Date date = new Date();
		itemInfo.setStartTime(date);
		itemInfo.setEndTime(date);
		itemInfo.setPaymentAmount(paymentInfo.getMoney());
		itemInfo.setChargeAmount(paymentInfo.getCharge());
		itemInfo.setQuantity(paymentInfo.getCount());
		return itemInfo;
	}
	
	/**
	 * 构建缴费账单明细
	 * 
	 * @param paymentInfo
	 * @param paymentOrderNo
	 * @return
	 */
	protected List<BillItemInfo> buildBillItemInfo(PaymentInfo paymentInfo, String paymentOrderNo){
		List<BillItemInfo> billItemInfos = Lists.newArrayList();
		for(InfoItem infoItem : paymentInfo.getItems()){
			BillItemInfo billItemInfo = new BillItemInfo();
			billItemInfo.setBillItemInfoNo(OID.newID());
			billItemInfo.setPaymentOrderNo(paymentOrderNo);
			billItemInfo.setName(infoItem.getName());
			billItemInfo.setCount(infoItem.getCount());
			billItemInfo.setMoney(infoItem.getMoney());
			billItemInfo.setCharge(infoItem.getCharge());
			billItemInfo.setPrice(infoItem.getPrice());
			billItemInfos.add(billItemInfo);
		}
		return billItemInfos;
	}

}

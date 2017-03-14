/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * faZheng 2015年12月8日 下午4:58:38 创建
 */

package com.yiji.ypayment.web.front.moblie;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.remote.PaymentRemoteService;
import com.yiji.ypayment.biz.remote.QueryBankCardService;
import com.yiji.ypayment.biz.remote.info.PactQueryResultInfo;
import com.yiji.ypayment.biz.remote.info.PaymentResultInfo;
import com.yiji.ypayment.biz.remote.order.ApplyPaymentForm;
import com.yiji.ypayment.biz.remote.order.PactQueryOrder;
import com.yiji.ypayment.biz.service.ypayment.PaymentOrderService;
import com.yiji.ypayment.biz.service.ypayment.PlatformTypeService;
import com.yiji.ypayment.common.dao.support.PageInfo;
import com.yiji.ypayment.common.exception.BusinessException;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.entity.business.PlatformType;
import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.facade.enums.PayWayEnum;
import com.yiji.ypayment.facade.enums.PaymentModelEnum;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.info.payment.PaymentItem;
import com.yiji.ypayment.facade.order.query.QueryPayOrderInfoOrder;
import com.yiji.ypayment.integration.client.CustomerClient;
import com.yiji.ypayment.web.common.web.AbstractPortalController;
import com.yiji.ypayment.web.front.form.RechargePayForm;
import com.yjf.common.id.OID;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.OrderCheckException;
import com.yjf.customer.service.enums.PasswordResultEnum;
import com.yjf.customer.service.order.UniformStringQueryOrder;
import com.yjf.customer.service.order.password.PayPasswordOrder;
import com.yjf.customer.service.result.PasswordResult;

/**
 * 手机充值
 *
 * @author faZheng
 *
 */

@Controller
@RequestMapping(value = "/recharge")
public class RechargeController extends AbstractPortalController {
	
	@Autowired
	private PaymentOrderService paymentOrderService;
	@Autowired
	private PaymentRemoteService paymentRemoteService;
	@Autowired
	private QueryBankCardService queryBankCardService;
	@Autowired
	private CustomerClient customerClient;
	@Autowired
	private PlatformTypeService plantformService;
	
	@RequestMapping("/index")
	public String paymentIndex(Model model) {
		model.addAttribute("chargeItems", getChargeItems());
		return frontVm("moblie/rechargeIndex");
	}
	
	@RequestMapping("/toPaymentOrder")
	public String toPaymentOrder(HttpSession session, Model model, String phoneNumber, String paymentAmount,
									String payAmount) {
		logger.info("创建充值订单, 入参phoneNumber:{}, paymentAmount:{}, payAmount:{}", phoneNumber, payAmount);
		try {
			//参数校验
			if (StringUtils.isEmpty(phoneNumber)) {
				throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "创建充值订单，电话号不能为空");
			}
			
			String userId = getUserId();
			UniformStringQueryOrder uniformStringQueryOrder = new UniformStringQueryOrder();
			uniformStringQueryOrder.setGid(getGid());
			uniformStringQueryOrder.setMerchOrderNo(getMerchOrderNo());
			uniformStringQueryOrder.setPartnerId(getPartnerId());
			uniformStringQueryOrder.setParam(userId);
			Money availableAmount = customerClient.getAvailableMoney(uniformStringQueryOrder);
			PactQueryOrder order = new PactQueryOrder();
			order.setUserId(userId);
			//统一订单号修改
			order.setGid(getGid());
			order.setPartnerId(getPartnerId());
			order.setMerchOrderNo(getMerchOrderNo());
			//查询商户平台表，检查商户是否开启代扣功能
			PlatformType platform = plantformService.findByPlatformType(getPlatformType());
			if (platform.isDeposit()) {
				PactQueryResultInfo pactQueryResultInfo = queryBankCardService.queryBankCard(order);
				model.addAttribute("pactBankCards", pactQueryResultInfo.getPactBankCards());
			}
			//易极付余额
			model.addAttribute("availableAmount", availableAmount);
			model.addAttribute("phoneNumber", phoneNumber);
			//充值金额
			model.addAttribute("paymentAmount", new Money(paymentAmount));
			//实付金额
			if (FavourableEnum.TRUE == getFavourable()) {
				model.addAttribute("payAmount", new Money(payAmount));
			} else {
				model.addAttribute("payAmount", new Money(paymentAmount));
			}
			model.addAttribute("token", setSessionToken(session));
			return frontVm("moblie/rechargeDetail");
		} catch (PaymentException pe) {
			logger.info("创建充值订单发生业务异常, 异常信息：{}", pe.getMessage());
			model.addAttribute("errorMessage", pe.getMessage());
		} catch (BusinessException be) {
			logger.error("创建充值订单发生业务异常, 异常信息：{}", be.getMessage());
			model.addAttribute("errorMessage", be.getMessage());
		} catch (Exception e) {
			logger.error("创建充值订单发生系统异常, 异常信息：{}", e.getMessage(), e);
			model.addAttribute("errorMessage", "查询用户信息发生异常");
		}
		return "error";
	}
	
	@RequestMapping("/sendRecharge")
	public String sendRecharge(Model model, RechargePayForm payForm) {
		logger.info("[页面进入]发起缴费入参, payForm：{}", payForm);
		try {
			//统一订单号修改
			payForm.setGid(getGid());
			payForm.setPartnerId(getPartnerId());
			payForm.setMerchOrderNo(getMerchOrderNo());
			//校验入参
			payForm.check();
			checkPaymentParameters(payForm);
			//删除Token
			removeToken();
			
			// 1.余额支付充值  2.如果是代扣支付充值，只创建缴费订单
			PaymentResultInfo result = paymentRemoteService.payment(buildRechargePaymentOrder(payForm));
			logger.info("缴费结果, result:{}", result);
			
			if (result.isSuccess()) {
				return frontVm("moblie/paymentSuccess");
			} else if (result.isFail()) {
				model.addAttribute("errorMessage", result.getDescription());
				return frontVm("moblie/paymentFail");
			}
		} catch (OrderCheckException orderException) {
			logger.info("充值入参错误, 异常信息：{}", orderException.getMessage());
			model.addAttribute("errorMessage", orderException.getMessage());
		} catch (PaymentException pe) {
			logger.info("充值发生业务异常, 异常信息：{}", pe.getMessage());
			model.addAttribute("errorMessage", pe.getMessage());
		} catch (BusinessException be) {
			logger.info("充值发生业务异常, 异常信息：{}", be.getMessage());
			model.addAttribute("errorMessage", be.getMessage());
		} catch (Exception e) {
			logger.error("充值发生系统异常, 异常信息：{}", e.getMessage(), e);
			model.addAttribute("errorMessage", "话费充值发生系统异常");
		}
		return frontVm("moblie/paymentFail");
	}
	
	@RequestMapping("/toRechargeRecord")
	public String toRechargeRecord(Model model) {
		return frontVm("moblie/rechargeRecord");
	}
	
	@ResponseBody
	@RequestMapping("rechargeRecord")
	public Map<String, Object> rechargeRecord(Model model, String pageNum) {
		int pageNo = 1;
		if (StringUtils.isEmpty(pageNum)) {
			pageNo = 1;
		} else {
			pageNo = Integer.parseInt(pageNum);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			QueryPayOrderInfoOrder order = new QueryPayOrderInfoOrder();
			order.setUserId(getUserId());
			order.setCurrentPage(pageNo);
			order.setCountOfCurrentPage(10);
			PageInfo<PaymentOrder> paymentOrders = paymentOrderService.query(order);
			map.put("paymentOrderList", paymentOrders);
			map.put("code", 1);
			map.put("message", "充值记录查询成功");
		} catch (BusinessException be) {
			logger.error("查询充值记录，发生异常:{}", be.getMessage());
			map.put("code", 0);
			map.put("message", be.getMessage());
		} catch (Exception e) {
			logger.error("查询充值记录，发生异常", e);
			map.put("code", 0);
			map.put("message", "查询充值记录失败");
		}
		logger.info("查询充值记录成功");
		return map;
	}
	
	@ResponseBody
	@RequestMapping("checkToken")
	public Map<String, Object> checkPaymentToken(@RequestParam("token") String token) {
		logger.info("验证token，入参token：{}", token);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			checkToken(token);
			map.put("code", 1);
			map.put("message", "可以提交");
		} catch (BusinessException be) {
			logger.error("验证token，发生重复提交异常:{}", be.getMessage());
			map.put("code", 0);
			map.put("message", be.getMessage());
		} catch (Exception e) {
			logger.error("验证token，发生异常", e);
			map.put("code", 0);
			map.put("message", "校验token失败");
		}
		logger.info("验证token，出参map：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("checkPayPwd")
	public Map<String, Object> checkPayPwd(@RequestParam("payPwd") String payPwd) {
		logger.info("缴费系统，校验支付密码");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			logger.info("验证用户支付密码，入参operatorId：{}", getOperatorId());
			PayPasswordOrder order = new PayPasswordOrder();
			order.setOperatorId(getOperatorId());
			order.setPayPassword(payPwd);
			order.setGid(getGid());
			order.setPartnerId(getPartnerId());
			order.setMerchOrderNo(getMerchOrderNo());
			PasswordResult result = customerClient.verifyPayPassword(order);
			logger.info("验证用户支付密码，出参：{}", result);
			if (result.isSuccess() && result.getResultCode() == PasswordResultEnum.EXECUTE_SUCCESS) {
				map.put("code", 1);
				map.put("message", "支付密码正确");
			} else {
				String message = StringUtils.isNotEmpty(result.getDescription()) ? result.getDescription() : "支付密码错误";
				map.put("code", 0);
				map.put("message", message);
			}
		} catch (Exception e) {
			logger.error("验证用户支付密码，发生异常", e);
			map.put("code", 0);
			map.put("message", "校验支付密码异常");
		}
		logger.info("缴费系统，校验支付密码结果:{}", map);
		return map;
	}
	
	/**
	 * 校验缴费入参
	 * @param payForm
	 */
	private void checkPaymentParameters(RechargePayForm payForm) {
		Map<String, Object> map = checkPaymentToken(payForm.getToken());
		if (!String.valueOf(map.get("code")).equals("1")
			|| !String.valueOf((map = checkPayPwd(payForm.getPassword())).get("code")).equals("1")) {
			throw new BusinessException(String.valueOf(map.get("message")));
		}
		
		String[] payPact = payForm.getPayPact().split("_");
		if (payPact == null || payPact.length != 2) {
			throw new BusinessException("充值发生系统异常, 请稍后再试");
		} else if (payPact[0].equals("balance")) {
			payForm.setPayWay(PayWayEnum.BY_BALANCE);
		} else if (payPact[0].equals("deposit")) {
			payForm.setPayWay(PayWayEnum.BY_DEPOSIT);
			payForm.setPactNo(payPact[1]);
		} else {
			throw new BusinessException("充值发生系统异常, 请稍后再试");
		}
	}
	
	/**
	 * 构建缴费入参订单
	 * @param payForm
	 * @return
	 */
	private ApplyPaymentForm buildRechargePaymentOrder(RechargePayForm payForm) {
		ApplyPaymentForm paymentForm = new ApplyPaymentForm();
		paymentForm.setUserId(getUserId());
		paymentForm.setInlet(getInlet());
		paymentForm.setPlatformType(getPlatformType());
		paymentForm.setOutBizNo(OID.newID());
		paymentForm.setPaymentAmount(payForm.getPaymentAmount());
		paymentForm.setUserCode(payForm.getPhoneNumber());
		paymentForm.setPaymentType(PaymentTypeEnum.MOBILE);
		paymentForm.setPayWay(payForm.getPayWay());
		paymentForm.setPactNo(payForm.getPactNo());
		paymentForm.setPayAmount(payForm.getPayAmount());
		paymentForm.setFavourable(getFavourable());
		paymentForm.setPaymentModel(PaymentModelEnum.RECHARGE);
		//统一订单号增加
		paymentForm.setGid(payForm.getGid());
		paymentForm.setPartnerId(getPartnerId());
		paymentForm.setMerchOrderNo(payForm.getMerchOrderNo());
		
		PaymentItem paymentItem = new PaymentItem();
		paymentItem.setAmount(payForm.getPaymentAmount());
		paymentItem.setPaymentOrderNo(OID.newID());
		paymentItem.setPaymentType(PaymentTypeEnum.MOBILE);
		paymentForm.getPaymentItems().add(paymentItem);
		
		return paymentForm;
	}
}

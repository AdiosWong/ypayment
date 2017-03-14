/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:26:21 创建
 */
package com.yiji.ypayment.web.front.moblie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.remote.PaymentBindingRemoteService;
import com.yiji.ypayment.biz.remote.PaymentQueryRemoteService;
import com.yiji.ypayment.biz.remote.PaymentRemoteService;
import com.yiji.ypayment.biz.remote.QueryBankCardService;
import com.yiji.ypayment.biz.remote.info.PactQueryResultInfo;
import com.yiji.ypayment.biz.remote.info.PaymentQueryInfo;
import com.yiji.ypayment.biz.remote.info.PaymentResultInfo;
import com.yiji.ypayment.biz.remote.info.ResourceInstInfo;
import com.yiji.ypayment.biz.remote.order.ApplyPaymentForm;
import com.yiji.ypayment.biz.remote.order.PactQueryOrder;
import com.yiji.ypayment.biz.service.ypayment.PaymentBindingInfoService;
import com.yiji.ypayment.biz.service.ypayment.PaymentOrderService;
import com.yiji.ypayment.biz.service.ypayment.PlatformTypeService;
import com.yiji.ypayment.common.dao.support.PageInfo;
import com.yiji.ypayment.common.exception.BusinessException;
import com.yiji.ypayment.dal.entity.business.PaymentBindingInfo;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.entity.business.PlatformType;
import com.yiji.ypayment.facade.enums.PaymentModelEnum;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.enums.PaymentValidStatus;
import com.yiji.ypayment.facade.info.payment.PaymentItem;
import com.yiji.ypayment.facade.info.query.PayItemInfo;
import com.yiji.ypayment.facade.order.payment.PaymentBindingOrder;
import com.yiji.ypayment.facade.order.payment.PaymentUnbindingOrder;
import com.yiji.ypayment.facade.order.query.PaymentQueryOrder;
import com.yiji.ypayment.facade.order.query.QueryPayOrderInfoOrder;
import com.yiji.ypayment.integration.client.CustomerClient;
import com.yiji.ypayment.web.common.web.AbstractPortalController;
import com.yiji.ypayment.web.front.form.HasUserForm;
import com.yiji.ypayment.web.front.form.PaymentForm;
import com.yiji.ypayment.web.info.PaymentBindingBean;
import com.yiji.ypayment.web.info.PaymentRecordBean;
import com.yiji.ypayment.web.info.ResourceBean;
import com.yiji.ypayment.web.info.ResponseBase;
import com.yjf.common.id.OID;
import com.yjf.common.lang.beans.Copier;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.OrderCheckException;
import com.yjf.common.util.StringUtils;
import com.yjf.customer.service.enums.PasswordResultEnum;
import com.yjf.customer.service.order.UniformStringQueryOrder;
import com.yjf.customer.service.order.password.PayPasswordOrder;
import com.yjf.customer.service.result.PasswordResult;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Controller
@RequestMapping(value = "/payment")
public class PaymentController extends AbstractPortalController {
	
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
	@Autowired
	private PaymentBindingInfoService paymentBindingInfoService;
	@Autowired
	private PaymentQueryRemoteService paymentQueryRemoteService;
	@Autowired
	private PaymentBindingRemoteService paymentBindingRemoteService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		return redirect("/payment/home.html");
	}
	
	/**
	 * 查询资源渠道
	 * 
	 * @param model
	 * @param paymentType
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/paymentIndex.json")
	public ResponseBase<Map<String, Object>> paymentIndex(Model model, PaymentTypeEnum paymentType) {
		logger.info("[页面进入]查询绑定信息、资源路由，缴费类型：{}", paymentType);
		ResponseBase<Map<String, Object>> responseBase = new ResponseBase<>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			//参数校验
			if (paymentType == null) {
				throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "查询资源渠道，缴费类型不能为空");
			}
			
			List<ResourceInstInfo> resourceInstInfos = paymentQueryRemoteService.queryResourceInst(paymentType);
			List<ResourceBean> resourceBeans = getResourceBeans(resourceInstInfos);
			data.put("resourceInstInfos", resourceBeans);
			responseBase.setSuccess(true);
			responseBase.setData(data);
		} catch (PaymentException pe) {
			logger.info("查询资源渠道发生业务异常, 异常信息：{}", pe.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(pe.getMessage());
		} catch (BusinessException be) {
			logger.error("查询资源渠道发生业务异常, 异常信息：{}", be.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(be.getMessage());
		} catch (Exception e) {
			logger.error("查询资源渠道，发生异常", e);
			responseBase.setSuccess(false);
			responseBase.setMessage("查询资源渠道，发生异常");
		}
		logger.info("缴费系统，查询资源渠道结果:{}", responseBase);
		return responseBase;
	}
	
	/**
	 * 查询绑定信息
	 * 
	 * @param model
	 * @param resourceCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryBingdingInfo.json")
	public ResponseBase<Map<String, Object>> queryBingdingInfo(Model model, String resourceCode){
		logger.info("[页面]查询绑定信息, 入参resourceCode：{}", resourceCode);
		ResponseBase<Map<String, Object>> responseBase = new ResponseBase<>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			//参数校验
			if (StringUtils.isEmpty(resourceCode)) {
				throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "查询绑定信息，资源编码不能为空");
			}
			
			List<PaymentBindingInfo> paymentBindingInfos = paymentBindingInfoService.findByUserIdAndPayFromAndResourceCode(getUserId(), getPlatformType(), resourceCode);
			List<PaymentBindingBean> paymentBindingBeans = getPaymentBindingBeans(paymentBindingInfos);
			data.put("paymentBindingInfos", paymentBindingBeans);
			responseBase.setSuccess(true);
			responseBase.setData(data);
		} catch (PaymentException pe) {
			logger.info("查询绑定信息发生业务异常, 异常信息：{}", pe.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(pe.getMessage());
		} catch (BusinessException be) {
			logger.error("查询绑定信息发生业务异常, 异常信息：{}", be.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(be.getMessage());
		} catch (Exception e) {
			logger.error("查询绑定信息，发生异常", e);
			responseBase.setSuccess(false);
			responseBase.setMessage("查询绑定信息，发生异常");
		}
		logger.info("缴费系统，查询绑定信息结果:{}", responseBase);
		return responseBase;
	}
	
	private List<PaymentBindingBean> getPaymentBindingBeans(List<PaymentBindingInfo> paymentBindingInfos) {
		List<PaymentBindingBean> paymentBindingBeans = Lists.newArrayList();
		for (PaymentBindingInfo paymentBindingInfo : paymentBindingInfos) {
			if (PaymentValidStatus.VALID == paymentBindingInfo.getStatus()) {
				PaymentBindingBean paymentBindingBean = new PaymentBindingBean();
				Copier.copy(paymentBindingInfo, paymentBindingBean);
				paymentBindingBeans.add(paymentBindingBean);
			}
		}
		return paymentBindingBeans;
	}
	
	private List<ResourceBean> getResourceBeans(List<ResourceInstInfo> resourceInstInfos) {
		List<ResourceBean> resourceBeans = Lists.newArrayList();
		for (ResourceInstInfo resourceInstInfo : resourceInstInfos) {
			ResourceBean resourceBean = new ResourceBean();
			Copier.copy(resourceInstInfo, resourceBean);
			resourceBeans.add(resourceBean);
		}
		return resourceBeans;
	}
	
	/**
	 * 用户解绑缴费卡
	 * 
	 * @param model
	 * @param contractNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/unbandUserCode.json")
	public ResponseBase<String> unbandUserCode(Model model, String contractNo) {
		logger.info("用户解绑缴费卡, 入参contractNo：{}", contractNo);
		ResponseBase<String> responseBase = new ResponseBase<>();
		try {
			//参数校验
			if (StringUtils.isEmpty(contractNo)) {
				throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "用户解绑时，签约号不能为空");
			}
			
			PaymentUnbindingOrder order = new PaymentUnbindingOrder();
			order.setUserId(getUserId());
			order.setPlatformType(getPlatformType());
			order.setContractNo(contractNo);
			order.setGid(getGid());
			order.setPartnerId(getPartnerId());
			order.setMerchOrderNo(getMerchOrderNo());
			paymentBindingRemoteService.paymentUnBinding(order);
			responseBase.setSuccess(true);
		} catch (PaymentException pe) {
			logger.info("用户解绑缴费卡发生业务异常, 异常信息：{}", pe.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(pe.getMessage());
		} catch (BusinessException be) {
			logger.info("用户解绑缴费卡发生业务异常, 异常信息：{}", be.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(be.getMessage());
		} catch (Exception e) {
			logger.info("用户解绑缴费卡发生系统异常, 异常信息：{}", e);
			responseBase.setSuccess(false);
			responseBase.setMessage("用户解绑缴费卡发生异常");
		}
		return responseBase;
	}
	
	/**
	 * 1：判断用户是否存在，2：用户存在未绑卡先绑卡
	 * 
	 * @param model
	 * @param userCode
	 * @param paymentType
	 * @param resourceCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/hasUser.json")
	public ResponseBase<PaymentBindingInfo> hasUser(Model model, HasUserForm hasUserForm) {
		logger.info("验证缴费用户是否存在，入参：hasUserForm：{}", hasUserForm);
		ResponseBase<PaymentBindingInfo> responseBase = new ResponseBase<>();
		try {
			//统一订单号修改
			hasUserForm.setGid(getGid());
			hasUserForm.setPartnerId(getPartnerId());
			hasUserForm.setMerchOrderNo(getMerchOrderNo());
			//校验入参
			hasUserForm.check();
			
			PaymentQueryOrder paymentQueryOrder = new PaymentQueryOrder();
			paymentQueryOrder.setUserId(getUserId());
			paymentQueryOrder.setPlatformType(getPlatformType());
			paymentQueryOrder.setUserCode(hasUserForm.getUserCode());
			paymentQueryOrder.setPaymentType(hasUserForm.getPaymentType());
			paymentQueryOrder.setResourceCode(hasUserForm.getResourceCode());
			//统一订单号修改
			paymentQueryOrder.setGid(hasUserForm.getGid());
			paymentQueryOrder.setPartnerId(hasUserForm.getPartnerId());
			paymentQueryOrder.setMerchOrderNo(hasUserForm.getMerchOrderNo());
			boolean isExit = paymentQueryRemoteService.hasUser(paymentQueryOrder);
			if (!isExit) {
				responseBase.setSuccess(false);
				responseBase.setMessage("该缴费用户不存在");
			} else {
				PaymentBindingOrder bindingOrder = new PaymentBindingOrder();
				bindingOrder.setPaymentType(hasUserForm.getPaymentType());
				bindingOrder.setPlatformType(getPlatformType());
				bindingOrder.setResourceCode(hasUserForm.getResourceCode());
				bindingOrder.setResourceName(hasUserForm.getResourceName());
				bindingOrder.setUserCode(hasUserForm.getUserCode());
				bindingOrder.setUserId(getUserId());
				bindingOrder.setGid(getGid());
				bindingOrder.setPartnerId(getPartnerId());
				bindingOrder.setMerchOrderNo(getMerchOrderNo());
				paymentBindingRemoteService.paymentBinding(bindingOrder);
				
				PaymentBindingInfo paymentBindingInfo = paymentBindingInfoService
					.findByUserIdAndPayFromAndUserCodeAndResourceCode(getUserId(), getPlatformType(),
						hasUserForm.getUserCode(), hasUserForm.getResourceCode());
				responseBase.setSuccess(true);
				responseBase.setData(paymentBindingInfo);
			}
		} catch (OrderCheckException orderException) {
			logger.info("验证缴费用户入参错误, 异常信息：{}", orderException.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(orderException.getMessage());
		} catch (PaymentException payExeception) {
			logger.info("验证缴费用户是否存在，发生异常", payExeception.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(payExeception.getMessage());
		} catch (BusinessException be) {
			logger.info("用户解绑缴费卡发生业务异常, 异常信息：{}", be.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(be.getMessage());
		} catch (Exception e) {
			logger.error("验证缴费用户是否存在，发生异常", e);
			responseBase.setSuccess(false);
			responseBase.setMessage("验证缴费用户是否存在，发生异常");
		}
		logger.info("缴费系统，验证缴费用户是否存在结果:{}", responseBase);
		return responseBase;
	}
	
	/**
	 * 创建缴费订单
	 * 
	 * @param model
	 * @param contractNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/toPaymentOrder.json")
	public ResponseBase<Map<String, Object>> toPaymentOrder(HttpSession session, Model model, String contractNo) {
		logger.info("创建缴费订单, 入参contractNo：{}", contractNo);
		ResponseBase<Map<String, Object>> responseBase = new ResponseBase<>();
		try {
			//参数校验
			if (StringUtils.isEmpty(contractNo)) {
				throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "创建缴费订单，签约号不能为空");
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			UniformStringQueryOrder uniformStringQueryOrder = new UniformStringQueryOrder();
			uniformStringQueryOrder.setGid(getGid());
			uniformStringQueryOrder.setMerchOrderNo(getMerchOrderNo());
			uniformStringQueryOrder.setPartnerId(getPartnerId());
			uniformStringQueryOrder.setParam(getUserId());
			Money availableAmount = customerClient.getAvailableMoney(uniformStringQueryOrder);
			PactQueryOrder order = new PactQueryOrder();
			order.setUserId(getUserId());
			//统一订单号修改
			order.setGid(getGid());
			order.setPartnerId(getPartnerId());
			order.setMerchOrderNo(getMerchOrderNo());
			//查询商户平台表，检查商户是否开启代扣功能
			PlatformType platform = plantformService.findByPlatformType(getPlatformType());
			if (platform.isDeposit()) {
				PactQueryResultInfo pactQueryResultInfo = queryBankCardService.queryBankCard(order);
				data.put("pactBankCards", pactQueryResultInfo.getPactBankCards());
			}
			//查询可缴费金额
			PaymentBindingInfo bindingInfo = paymentBindingInfoService.findByContractNo(contractNo);
			if (bindingInfo != null) {
				PaymentQueryOrder queryOrder = new PaymentQueryOrder();
				queryOrder.setPaymentType(bindingInfo.getPaymentType());
				queryOrder.setPlatformType(getPlatformType());
				queryOrder.setResourceCode(bindingInfo.getResourceCode());
				queryOrder.setUserCode(bindingInfo.getUserCode());
				queryOrder.setUserId(bindingInfo.getUserId());
				queryOrder.setGid(getGid());
				queryOrder.setPartnerId(getPartnerId());
				queryOrder.setMerchOrderNo(getMerchOrderNo());
				PaymentQueryInfo paymentQueryInfo = paymentQueryRemoteService.queryPayment(queryOrder);
				data.put("payItemInfos", paymentQueryInfo.getItems());
				data.put("amount", paymentQueryInfo.getTotalPayables());
				data.put("userCode", paymentQueryInfo.getUserCode());
				data.put("userName", paymentQueryInfo.getUsername());
				data.put("resourceName", bindingInfo.getResourceName());
				data.put("address", paymentQueryInfo.getAddress());
				data.put("token", setSessionToken(session));
			}
			//易极付余额
			data.put("availableAmount", availableAmount);
			responseBase.setSuccess(true);
			responseBase.setData(data);
		} catch (PaymentException payExeception) {
			logger.info("创建缴费订单发生业务异常,异常信息：{}", payExeception.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(payExeception.getMessage());
		} catch (BusinessException be) {
			logger.info("创建缴费订单发生业务异常,异常信息：{}", be.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(be.getMessage());
		} catch (Exception e) {
			logger.info("创建缴费订单发生系统异常,异常信息：{}", e);
			responseBase.setSuccess(false);
			responseBase.setMessage("查询用户信息发生异常");
		}
		return responseBase;
	}
	
	/**
	 * 缴费记录
	 * 
	 * @param model
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/paymentRecord.json")
	public ResponseBase<Map<String, Object>> paymentRecord(Model model, String pageNo, PaymentTypeEnum paymentType) {
		int pageNum = 1;
		if (StringUtils.isEmpty(pageNo)) {
			pageNum = 1;
		} else {
			pageNum = Integer.parseInt(pageNo);
		}
		ResponseBase<Map<String, Object>> responseBase = new ResponseBase<>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			QueryPayOrderInfoOrder order = new QueryPayOrderInfoOrder();
			order.setUserId(getUserId());
			order.setCurrentPage(pageNum);
			order.setCountOfCurrentPage(10);
			List<PaymentTypeEnum> paymentTypes = Lists.newArrayList();
			if (paymentType == null) {
				paymentTypes.add(PaymentTypeEnum.GAS);
				paymentTypes.add(PaymentTypeEnum.RUBBISH);
				paymentTypes.add(PaymentTypeEnum.WATER);
				paymentTypes.add(PaymentTypeEnum.ELECTRICITY);
			} else if (paymentType == PaymentTypeEnum.GAS) {
				paymentTypes.add(PaymentTypeEnum.GAS);
				paymentTypes.add(PaymentTypeEnum.RUBBISH);
			} else {
				paymentTypes.add(paymentType);
			}
			PageInfo<PaymentOrder> paymentOrders = paymentOrderService.query(order, paymentTypes);
			List<PaymentRecordBean> paymentRecordBeans = getPaymentOrder(paymentOrders.getPageResults());
			map.put("totalPageCount", paymentOrders.getTotalPage());
			map.put("currentPageNo", paymentOrders.getCurrentPage());
			map.put("pageSize", paymentOrders.getCountOfCurrentPage());
			map.put("totalCount", paymentOrders.getTotalCount());
			map.put("result", paymentRecordBeans);
			responseBase.setData(map);
			responseBase.setSuccess(true);
		} catch (BusinessException be) {
			logger.error("查询充值记录，发生异常:{}", be.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(be.getMessage());
		} catch (Exception e) {
			logger.error("查询充值记录，发生异常", e);
			responseBase.setSuccess(false);
			responseBase.setMessage("查询充值记录失败");
		}
		logger.info("查询充值记录，出参map：{}", responseBase);
		return responseBase;
	}
	
	private List<PaymentRecordBean> getPaymentOrder(List<PaymentOrder> paymentOrders) {
		List<PaymentRecordBean> paymentRecordBeans = Lists.newArrayList();
		for (PaymentOrder paymentOrder : paymentOrders) {
			PaymentRecordBean paymentRecordBean = new PaymentRecordBean();
			Copier.copy(paymentOrder, paymentRecordBean);
			paymentRecordBeans.add(paymentRecordBean);
		}
		return paymentRecordBeans;
	}
	
	/**
	 * 水电气缴费
	 * 
	 * @param paymentForm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendRecharge.json")
	public ResponseBase<String> sendRecharge(PaymentForm paymentForm) {
		logger.info("[页面进入]发起水电气缴费入参, payForm：{}", paymentForm);
		ResponseBase<String> responseBase = new ResponseBase<>();
		try {
			//统一订单号修改
			paymentForm.setGid(getGid());
			paymentForm.setPartnerId(getPartnerId());
			paymentForm.setMerchOrderNo(getMerchOrderNo());
			//校验入参
			paymentForm.check();
			checkPaymentForm(paymentForm);
			//删除Token
			removeToken();
			// 1.余额支付充值  2.如果是代扣支付充值，只创建缴费订单
			PaymentResultInfo result = paymentRemoteService.payment(buildRechargePaymentOrder(paymentForm));
			logger.info("缴费结果, result:{}", result);
			if (result.isSuccess()) {
				responseBase.setSuccess(true);
			} else if (result.isFail()) {
				responseBase.setSuccess(false);
				responseBase.setMessage(result.getDescription());
			}
		} catch (OrderCheckException orderException) {
			logger.info("水电气缴费入参错误, 异常信息：{}", orderException.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(orderException.getMessage());
		} catch (PaymentException pe) {
			logger.info("水电气缴费发生业务异常, 异常信息：{}", pe.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(pe.getMessage());
		} catch (BusinessException be) {
			logger.info("水电气缴费发生业务异常, 异常信息：{}", be.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(be.getMessage());
		} catch (Exception e) {
			logger.info("水电气缴费发生系统异常, 异常信息：{}", e.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage("话费充值发生系统异常");
		}
		return responseBase;
	}
	
	/**
	 * 校验缴费密码
	 * @param payForm
	 */
	private void checkPaymentForm(PaymentForm paymentForm) {
		ResponseBase<String> responseToken = checkPaymentToken(paymentForm.getToken());
		if (!responseToken.isSuccess()) {
			throw new BusinessException(responseToken.getMessage());
		}
		ResponseBase<String> responsePsd = checkPaymentPwd(paymentForm.getPassword());
		if (!responsePsd.isSuccess()) {
			throw new BusinessException(responsePsd.getMessage());
		}
	}
	
	/**
	 * token验证
	 * 
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkToken.json")
	public ResponseBase<String> checkPaymentToken(@RequestParam("token") String token) {
		logger.info("验证token，入参token：{}", token);
		ResponseBase<String> responseBase = new ResponseBase<>();
		try {
			checkToken(token);
			responseBase.setSuccess(true);
			responseBase.setMessage("可以提交");
		} catch (BusinessException be) {
			logger.error("验证token，发生重复提交异常:{}", be.getMessage());
			responseBase.setSuccess(false);
			responseBase.setMessage(be.getMessage());
		} catch (Exception e) {
			logger.error("验证token，发生异常", e);
			responseBase.setSuccess(false);
			responseBase.setMessage("校验token失败");
		}
		logger.info("验证token，出参map：{}", responseBase);
		return responseBase;
	}
	
	/**
	 * 密码验证
	 * 
	 * @param payPwd
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkPayPwd.json")
	public ResponseBase<String> checkPaymentPwd(@RequestParam("payPwd") String payPwd) {
		logger.info("缴费系统，校验支付密码");
		ResponseBase<String> responseBase = new ResponseBase<>();
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
				responseBase.setSuccess(true);
				responseBase.setMessage("支付密码正确");
			} else {
				String message = StringUtils.isNotEmpty(result.getDescription()) ? result.getDescription() : "支付密码错误";
				responseBase.setSuccess(false);
				responseBase.setMessage(message);
			}
		} catch (Exception e) {
			logger.error("验证用户支付密码，发生异常", e);
			responseBase.setSuccess(false);
			responseBase.setMessage("校验支付密码异常");
		}
		logger.info("缴费系统，校验支付密码结果:{}", responseBase);
		return responseBase;
	}
	
	/**
	 * 构建缴费入参订单
	 * @param payForm
	 * @return
	 */
	private ApplyPaymentForm buildRechargePaymentOrder(PaymentForm payForm) {
		ApplyPaymentForm paymentForm = new ApplyPaymentForm();
		paymentForm.setUserId(getUserId());
		paymentForm.setInlet(getInlet());
		paymentForm.setPlatformType(getPlatformType());
		paymentForm.setOutBizNo(OID.newID());
		paymentForm.setPaymentAmount(payForm.getAmount());
		paymentForm.setPaymentType(payForm.getPaymentType());
		paymentForm.setPayWay(payForm.getPayWay());
		paymentForm.setPactNo(payForm.getPactNo());
		paymentForm.setFavourable(getFavourable());
		paymentForm.setContractNo(payForm.getContractNo());
		paymentForm.setPaymentModel(PaymentModelEnum.PAYMENT);
		//统一订单号增加
		paymentForm.setGid(payForm.getGid());
		paymentForm.setPartnerId(payForm.getPartnerId());
		paymentForm.setMerchOrderNo(payForm.getMerchOrderNo());
		//查询可缴费金额
		PaymentBindingInfo bindingInfo = paymentBindingInfoService.findByContractNo(payForm.getContractNo());
		if (bindingInfo != null) {
			PaymentQueryOrder queryOrder = new PaymentQueryOrder();
			queryOrder.setPaymentType(bindingInfo.getPaymentType());
			queryOrder.setPlatformType(getPlatformType());
			queryOrder.setResourceCode(bindingInfo.getResourceCode());
			queryOrder.setUserCode(bindingInfo.getUserCode());
			queryOrder.setUserId(bindingInfo.getUserId());
			queryOrder.setGid(getGid());
			queryOrder.setPartnerId(getPartnerId());
			queryOrder.setMerchOrderNo(getMerchOrderNo());
			PaymentQueryInfo paymentQueryInfo = paymentQueryRemoteService.queryPayment(queryOrder);
			for(PayItemInfo payItemInfo : paymentQueryInfo.getItems()){
				PaymentItem paymentItem = new PaymentItem();
				paymentItem.setAmount(payItemInfo.getPayables());
				paymentItem.setPaymentOrderNo(OID.newID());
				paymentItem.setPaymentType(payItemInfo.getPaymentType());
				paymentForm.getPaymentItems().add(paymentItem);
			}
		}
		return paymentForm;
	}
	
}

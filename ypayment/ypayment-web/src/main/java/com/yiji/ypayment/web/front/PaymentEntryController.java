package com.yiji.ypayment.web.front;

import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yiji.ypayment.biz.service.ypayment.PlatformTypeService;
import com.yiji.ypayment.common.exception.BusinessException;
import com.yiji.ypayment.dal.entity.business.PlatformType;
import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.facade.enums.PaymentModelEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.enums.PaymentValidStatus;
import com.yiji.ypayment.integration.client.CustomerClient;
import com.yiji.ypayment.web.common.web.AbstractPortalController;
import com.yiji.ypayment.web.filter.SessionAccessor;
import com.yiji.ypayment.web.front.form.ChargeItem;
import com.yiji.ypayment.web.front.form.PaymentEntryForm;
import com.yjf.common.lang.security.DigestUtil;
import com.yjf.common.lang.security.DigestUtil.DigestALGEnum;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.service.OrderCheckException;
import com.yjf.customer.service.api.query.merchant.MerchantQueryService;
import com.yjf.customer.service.enums.CustomerResultEnum;
import com.yjf.customer.service.info.OperatorInfo;
import com.yjf.customer.service.info.UserInfo;
import com.yjf.customer.service.info.merchant.MerchantInfo;
import com.yjf.customer.service.order.UniformStringQueryOrder;
import com.yjf.customer.service.result.UserQueryResult;
import com.yjf.customer.service.result.merchant.MerchantInfoResult;

/**
 * 
 * 缴费系统入口controller
 * 
 * @author faZheng
 * 
 */
@Controller
@RequestMapping("ypayment")
public class PaymentEntryController extends AbstractPortalController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Reference(version = "1.5")
	private MerchantQueryService merchantQueryService;
	@Autowired
	private PlatformTypeService platformTypeService;
	@Autowired
	private CustomerClient customerClient;
	
	@RequestMapping("outEntry")
	public String investEntry(HttpServletRequest request, Model model, PaymentEntryForm entryForm) {
		logger.info("进入[openapi跳转缴费系统],入参:{}", entryForm);
		try {
			// 入参验证
			entryForm.check();
			
			// openapi验签
			checkOpenapiSign(request);
			
			// 业务平台验证
			checkPlatformAndParnerId(entryForm);
			
			// 初始化、进入缴费系统
			String redirectUrl = initEntryParams(entryForm);
			return redirect(redirectUrl);
		} catch (OrderCheckException e) {
			logger.error("参数不完整, 异常：{}", e.getMessage());
			model.addAttribute("errMsg", "参数错误, " + e.getMessage());
		} catch (BusinessException e) {
			logger.error("业务处理异常, 异常：{}", e);
			model.addAttribute("errMsg", "失败, " + e.getMessage());
		} catch (Exception e) {
			logger.error("校验partnerId发生异常", e);
			model.addAttribute("errMsg", "业务平台验签失败！");
		}
		return ERROR_URL;
	}
	
	@RequestMapping("innerEntry")
	public String innerEntry(HttpServletRequest request, Model model, PaymentEntryForm entryForm) {
		logger.info("进入[内部跳转缴费系统],入参:{}", entryForm);
		try {
			// 入参验证
			entryForm.check();
			
			// 业务平台验证
			checkPlatformAndParnerId(entryForm);
			
			// 平台类型验证
			PlatformType platformTypeEntity = platformTypeService.findByPlatformType(entryForm.getPlatformType());
			// 验签
			DigestUtil.check(request, platformTypeEntity.getSignatureKey(), DigestALGEnum.MD5);
			String redirectUrl = initEntryParams(entryForm);
			return redirect(redirectUrl);
		} catch (OrderCheckException e) {
			logger.error("参数不完整, 异常：{}", e.getMessage());
			model.addAttribute("errMsg", "参数错误, " + e.getMessage());
		} catch (BusinessException e) {
			logger.error("业务处理异常, 异常：{}", e);
			model.addAttribute("errMsg", "失败, " + e.getMessage());
		} catch (Exception e) {
			logger.error("校验签约发生异常", e);
			model.addAttribute("errMsg", "业务平台验签失败！");
		}
		return ERROR_URL;
	}
	
	private String initEntryParams(PaymentEntryForm form){
		String redirectUrl = ERROR_URL;
		// 操作员验证
		String userId = form.getUserId();
		String platformType = form.getPlatformType();
		
		// 易极付用户信息
		UniformStringQueryOrder order = new UniformStringQueryOrder();
		order.setGid(form.getGid());
		order.setMerchOrderNo(form.getMerchOrderNo());
		order.setPartnerId(form.getPartnerId());
		order.setParam(form.getUserId());
		UserQueryResult userResult = customerClient.findUserInfoByUserId(order);
		if (userResult.getResultCode() != CustomerResultEnum.EXECUTE_SUCCESS) {
			logger.info("该用户不存在, userId:{}", userId);
			throw new BusinessException("该用户不存在");
		}
		UserInfo userInfo = userResult.getUserInfo();
		
		// 操作员信息
		OperatorInfo operatorInfo = customerClient.queryAdminOperatorByUserId(order);
		if (operatorInfo == null) {
			logger.info("该用户不存在超级操作员, userId:{}", userId);
			throw new BusinessException("该用户不存在超级操作员");
		}
		
		//充值优惠列表
		List<ChargeItem> chargeItems = Lists.newArrayList() ;
		if(FavourableEnum.TRUE == getFavourable()){
			String chargeItemsStr = form.getChargeItems();
			chargeItems = JSON.parseArray(chargeItemsStr, ChargeItem.class);
		}

		// session 初始化
		SessionAccessor.init(userInfo, operatorInfo, platformType, form.getInlet(), form.getPartnerId(), form.getFavourable(), chargeItems, form.getGid(), form.getMerchOrderNo());
		
		// 返回url
		if (form.getPaymentModel() == PaymentModelEnum.RECHARGE) {
			logger.info("业务平台[{}]的用户:{}, 进入话费充值页面", platformType, userId);
			redirectUrl = "/recharge/index.htm";
		} else if (form.getPaymentModel() == PaymentModelEnum.PAYMENT) {
			logger.info("业务平台[{}]的用户：{}, 进入水电气缴费页面", platformType, userId);
			redirectUrl = "/payment/index.htm";
		}
		return redirectUrl;
	}
	
	private void checkPlatformAndParnerId(PaymentEntryForm entryForm){
		PlatformType platformTypeEntity = platformTypeService.findByPlatformType(entryForm.getPlatformType());
		if (null == platformTypeEntity) {
			logger.info("非法的业务平台, 业务平台类型:{}, 缴费类型:{}", entryForm.getPlatformType(), entryForm.getPaymentModel().message());
			throw new BusinessException("非法的业务平台类型:" + entryForm.getPlatformType());
		}
		
		if (!entryForm.getPartnerId().equals(platformTypeEntity.getPartnerId())) {
			logger.info("非法的平台商户ID, 入参partnerId：{}, 已配置partnerId：{}", entryForm.getPartnerId(), platformTypeEntity.getPartnerId());
			throw new BusinessException("非法的平台商户ID");
		}
		
		if (platformTypeEntity.getStatus() == PaymentValidStatus.INVALID) {
			logger.info("业务平台已被禁用, 请联系管理员核实, 平台状态：{}", platformTypeEntity.getStatus().message());
			throw new BusinessException("业务平台已被禁用, 请联系管理员核实");
		}
		
		List<String> paymentTypeList = platformTypeEntity.getPaymentTypeList();
		if (null == paymentTypeList || paymentTypeList.size() == 0) {
			logger.info("业务平台支持的缴费类型为空");
			throw new BusinessException("业务平台暂不支持缴费, 请联系管理员开通缴费功能");
		} else if (entryForm.getPaymentModel() == PaymentModelEnum.RECHARGE) {
			if (!paymentTypeList.contains(PaymentTypeEnum.MOBILE.code())) {
				logger.info("业务平台不支持话费充值, 缴费类型:{}", entryForm.getPaymentModel().message());
				throw new BusinessException("业务平台不支持话费充值, 请联系管理员开通该功能");
			}
		} else if (entryForm.getPaymentModel() == PaymentModelEnum.PAYMENT) {
			if (!paymentTypeList.contains(PaymentTypeEnum.ELECTRICITY.code()) &&
					!paymentTypeList.contains(PaymentTypeEnum.GAS.code()) &&
					!paymentTypeList.contains(PaymentTypeEnum.WATER.code())) {
				logger.info("业务平台不支持水电气缴费, 缴费类型:{}", entryForm.getPaymentModel().message());
				throw new BusinessException("业务平台不支持水电气缴费, 请联系管理员开通该功能");
			} 
		}
	}
	
	/**
	 * OpenApi验签
	 * @param request
	 * @throws ParseException
	 */
	private void checkOpenapiSign(HttpServletRequest request) throws ParseException {
		Date openApiTime = DateUtil.string2DateTime(request.getParameter("systemTime"));
		long minutes = (new Date().getTime() - openApiTime.getTime()) / 1000 / 60;
		if (minutes > 30) {
			throw new BusinessException("请求链接已经过期, 请重新进入缴费系统");
		}
		
		Map<String, String> map = new HashMap<>();
		Enumeration<String> em = request.getParameterNames();
		while (em.hasMoreElements()) {
			String name = em.nextElement();
			map.put(name, request.getParameter(name));
		}
		
		MerchantInfoResult merchantInfoResult = merchantQueryService.findMerchantInfoByUserId(map.get("partnerId"));
		MerchantInfo merchantInfo = null;
		if (merchantInfoResult != null && merchantInfoResult.isSuccess()) {
			merchantInfo = merchantInfoResult.getMerchantInfo();
			if (merchantInfo == null) {
				throw new BusinessException("非法的商户号, 请重新进入缴费系统");
			}
		} else {
			throw new BusinessException("验证商户[" + map.get("partnerId") + "]失败");
		}
		
		String digest = DigestUtil.digest(map, merchantInfo.getSecurityCheckCode(), merchantInfo.getDigestAlg(), "utf-8");
		logger.info("验签sign:{};digest:{}", map.get("sign"), digest);
		if (!StringUtils.equals(digest, map.get("sign"))) {
			throw new BusinessException("验签失败, 请确认后重新进入缴费系统");
		}
	}
}

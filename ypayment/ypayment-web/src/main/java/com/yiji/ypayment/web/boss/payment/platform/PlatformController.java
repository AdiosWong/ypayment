/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午4:44:25 创建
 */
package com.yiji.ypayment.web.boss.payment.platform;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yiji.ypayment.biz.service.ypayment.PlatformTypeService;
import com.yiji.ypayment.common.constant.Constant;
import com.yiji.ypayment.common.utils.AESEncrypt;
import com.yiji.ypayment.dal.entity.business.PlatformType;
import com.yiji.ypayment.dal.enums.AccessTypeEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.facade.enums.PaymentValidStatus;
import com.yiji.ypayment.web.common.web.AbstractJQueryEntityController;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Controller
@RequestMapping(value = "boss/ypayment/payment/platform/platform")
public class PlatformController extends AbstractJQueryEntityController<PlatformType, PlatformTypeService> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PlatformTypeService platformTypeService;
	
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("paymentValidStatus", PaymentValidStatus.values());
		model.put("paymentTypeEnum", PaymentTypeEnum.values());
		model.put("paymentTypeMap", PaymentTypeEnum.paymentTypeMap());
		model.put("accessTypeEnum", AccessTypeEnum.values());
	}
	
	@Override
	public String saveList(HttpServletRequest request, HttpServletResponse response, Model model,
							RedirectAttributes redirectAttributes) {
		String redirectUrl = getSuccessListView();
		try {
			model.addAllAttributes(referenceData(request));
			PlatformType entity = loadEntity(request);
			if (entity == null) {
				entity = getEntityClass().newInstance();
				entity.setSignatureKey(AESEncrypt.getRandomString(15));
			}
			doDataBinding(request, entity);
			// 设置缴费类型
			String[] paymentTypeList = request.getParameterValues("paymentTypeList");
			String paymentTypeCodeStr = "";
			if (paymentTypeList != null && paymentTypeList.length > 0) {
				StringBuilder paymentTypeCode = new StringBuilder();
				for (String paymentType : paymentTypeList) {
					paymentTypeCode.append(paymentType).append(Constant.FILE_SEPARATOR);
				}
				paymentTypeCodeStr = paymentTypeCode.subSequence(0, paymentTypeCode.length() - 1).toString();
			}
			entity.setPaymentTypes(paymentTypeCodeStr);
			
			// 设置短信通知
			String isSms = request.getParameter("sms");
			if (isSms.equals("YES")) {
				entity.setSms(true);
			} else {
				entity.setSms(false);
			}
			
			// 设置代扣支持
			String isDeposit = request.getParameter("deposit");
			if (isDeposit.equals("YES")) {
				entity.setDeposit(true);
			} else {
				entity.setSms(false);
			}
			onSave(request, response, model, entity, true);
			// 这里服务层默认是根据entity的Id是否为空自动判断是SAVE还是UPDATE.
			getEntityService().save(entity);
			saveMessage(request, "保存成功");
		} catch (Exception e) {
			logger.warn(getExceptionMessage("save", e), e);
			handleException("保存", e, request);
			redirectUrl = "redirect:" + getSaveFailureRedirectUrl();
		}
		return redirectUrl;
	}
	
}

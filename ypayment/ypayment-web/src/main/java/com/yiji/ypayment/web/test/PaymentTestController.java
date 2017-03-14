package com.yiji.ypayment.web.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yiji.ypayment.biz.remote.PaymentBindingRemoteService;
import com.yiji.ypayment.biz.remote.PaymentQueryRemoteService;
import com.yiji.ypayment.biz.remote.PaymentRemoteService;
import com.yiji.ypayment.biz.service.ypayment.PaymentBindingInfoService;
import com.yiji.ypayment.biz.service.ypayment.PlatformTypeService;
import com.yiji.ypayment.common.constant.Constant;
import com.yiji.ypayment.common.utils.Dates;
import com.yiji.ypayment.common.utils.EncodeUtils;
import com.yiji.ypayment.dal.entity.business.PlatformType;
import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.facade.enums.PaymentModelEnum;
import com.yiji.ypayment.facade.enums.PaymentTypeEnum;
import com.yiji.ypayment.web.common.utils.AppUtil;
import com.yiji.ypayment.web.front.form.ChargeItem;
import com.yjf.common.id.GID;
import com.yjf.common.id.OID;
import com.yjf.common.lang.security.DigestUtil;
import com.yjf.common.lang.security.DigestUtil.DigestALGEnum;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping(value = "/test/payment")
public class PaymentTestController extends AppUtil {
	@Autowired
	private PaymentBindingInfoService paymentBindingInfoService;
	@Autowired
	private PaymentQueryRemoteService paymentQueryRemoteService;
	@Autowired
	private PaymentBindingRemoteService paymentBindingRemoteService;
	@Autowired
	private PaymentRemoteService paymentRemoteService;
	@Autowired
	private PlatformTypeService platformTypeService;
	@Value("${ypayment.test.userAcount}")
	private String testUserId;
	
	@RequestMapping("/index")
	public String paymentIndex(Model model) {
		model.addAttribute("paymentTypes", PaymentTypeEnum.values());
		return "test/paymentIndex";
	}
	
	/**
	 * 易品会充值无优惠
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List<String> userIds = Lists.newArrayList(testUserId.split("[|]"));
		String userId = userIds.get(0);
		PlatformType platformTypeEntity = platformTypeService.findByPlatformType("YiPinHui");
		HashMap<String, Object> map = new HashMap<>();
		/*大数据*/
		map.put("inlet", Constant.INLTE);
		map.put("partnerId", platformTypeEntity.getPartnerId());
		map.put("userId", userId);
		map.put("platformType", platformTypeEntity.getPlatformType());
		map.put("systemTime", Dates.format(new Date()));
		map.put("paymentModel", PaymentModelEnum.RECHARGE);
		map.put("gid", GID.newGID());
		map.put("favourable", FavourableEnum.FALSE);
		map.put("merchOrderNo", OID.newID());

		String signData = DigestUtil.digest(map, platformTypeEntity.getSignatureKey(), DigestALGEnum.MD5);
		map.put("sign", signData);
		StringBuilder sb = new StringBuilder();
		sb.append("redirect:").append("/ypayment/innerEntry.html?");
		for (Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey()).append("=").append(EncodeUtils.urlEncode(entry.getValue().toString())).append("&");
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}
	
	/**
	 * 易品会充值优惠
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/loginyph")
	public String loginYipinhui(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List<String> userIds = Lists.newArrayList(testUserId.split("[|]"));
		String userId = userIds.get(0);
		PlatformType platformTypeEntity = platformTypeService.findByPlatformType("YiPinHui");
		HashMap<String, Object> map = new HashMap<>();
		/*大数据*/
		map.put("inlet", Constant.INLTE);
		map.put("partnerId", platformTypeEntity.getPartnerId());
		map.put("userId", userId);
		map.put("platformType", platformTypeEntity.getPlatformType());
		map.put("systemTime", Dates.format(new Date()));
		map.put("paymentModel", PaymentModelEnum.RECHARGE);
		map.put("gid", GID.newGID());
		map.put("favourable", FavourableEnum.TRUE);
		map.put("merchOrderNo", OID.newID());
		List<ChargeItem> chargeItems = Lists.newArrayList();
		
		ChargeItem item1 = new ChargeItem();
		item1.setPaymentAmount(new Money(10));
		item1.setPayAmount(new Money(9.90));
		chargeItems.add(item1);
		
		ChargeItem item2 = new ChargeItem();
		item2.setPaymentAmount(new Money(20));
		item2.setPayAmount(new Money(19.80));
		chargeItems.add(item2);
		
		ChargeItem item3 = new ChargeItem();
		item3.setPaymentAmount(new Money(30));
		item3.setPayAmount(new Money(29.80));
		chargeItems.add(item3);
		
		ChargeItem item4 = new ChargeItem();
		item4.setPaymentAmount(new Money(50));
		item4.setPayAmount(new Money(49.50));
		chargeItems.add(item4);
		
		ChargeItem item5 = new ChargeItem();
		item5.setPaymentAmount(new Money(100));
		item5.setPayAmount(new Money(98.80));
		chargeItems.add(item5);
		
		String chargeItemsStr = JSON.toJSONString(chargeItems);
		map.put("chargeItems", chargeItemsStr);
		String signData = DigestUtil.digest(map, platformTypeEntity.getSignatureKey(), DigestALGEnum.MD5);
		map.put("sign", signData);
		StringBuilder sb = new StringBuilder();
		sb.append("redirect:").append("/ypayment/innerEntry.html?");
		for (Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey()).append("=").append(EncodeUtils.urlEncode(entry.getValue().toString())).append("&");
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}
	
	/**
	 * 报销充值优惠
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/loginbx")
	public String loginBaoxiao(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List<String> userIds = Lists.newArrayList(testUserId.split("[|]"));
		String userId = userIds.get(0);
		PlatformType platformTypeEntity = platformTypeService.findByPlatformType("BaoXiao");
		HashMap<String, Object> map = new HashMap<>();
		/*大数据*/
		map.put("inlet", Constant.INLTE);
		map.put("partnerId", platformTypeEntity.getPartnerId());
		map.put("userId", userId);
		map.put("platformType", platformTypeEntity.getPlatformType());
		map.put("systemTime", Dates.format(new Date()));
		map.put("paymentModel", PaymentModelEnum.RECHARGE);
		map.put("gid", GID.newGID());
		map.put("favourable", FavourableEnum.TRUE);
		map.put("merchOrderNo", OID.newID());
		List<ChargeItem> chargeItems = Lists.newArrayList();
		
		ChargeItem item1 = new ChargeItem();
		item1.setPaymentAmount(new Money(10));
		item1.setPayAmount(new Money(9.90));
		chargeItems.add(item1);
		
		ChargeItem item2 = new ChargeItem();
		item2.setPaymentAmount(new Money(20));
		item2.setPayAmount(new Money(19.80));
		chargeItems.add(item2);
		
		ChargeItem item3 = new ChargeItem();
		item3.setPaymentAmount(new Money(30));
		item3.setPayAmount(new Money(29.80));
		chargeItems.add(item3);
		
		ChargeItem item4 = new ChargeItem();
		item4.setPaymentAmount(new Money(50));
		item4.setPayAmount(new Money(49.50));
		chargeItems.add(item4);
		
		ChargeItem item5 = new ChargeItem();
		item5.setPaymentAmount(new Money(100));
		item5.setPayAmount(new Money(98.80));
		chargeItems.add(item5);
		
		String chargeItemsStr = JSON.toJSONString(chargeItems);
		map.put("chargeItems", chargeItemsStr);
		String signData = DigestUtil.digest(map, platformTypeEntity.getSignatureKey(), DigestALGEnum.MD5);
		map.put("sign", signData);
		StringBuilder sb = new StringBuilder();
		sb.append("redirect:").append("/ypayment/innerEntry.html?");
		for (Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey()).append("=").append(EncodeUtils.urlEncode(entry.getValue().toString())).append("&");
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}
	
	/**
	 * 易品会缴费
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/loginjf")
	public String loginjf(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List<String> userIds = Lists.newArrayList(testUserId.split("[|]"));
		String userId = userIds.get(0);
		PlatformType platformTypeEntity = platformTypeService.findByPlatformType("YiPinHui");
		HashMap<String, Object> map = new HashMap<>();
		/*大数据*/
		map.put("inlet", Constant.INLTE);
		map.put("partnerId", platformTypeEntity.getPartnerId());
		map.put("userId", userId);
		map.put("platformType", platformTypeEntity.getPlatformType());
		map.put("systemTime", Dates.format(new Date()));
		map.put("paymentModel", PaymentModelEnum.PAYMENT);
		map.put("gid", GID.newGID());
		map.put("merchOrderNo", OID.newID());

		String signData = DigestUtil.digest(map, platformTypeEntity.getSignatureKey(), DigestALGEnum.MD5);
		map.put("sign", signData);
		StringBuilder sb = new StringBuilder();
		sb.append("redirect:").append("/ypayment/innerEntry.html?");
		for (Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey()).append("=").append(EncodeUtils.urlEncode(entry.getValue().toString())).append("&");
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}
	
}

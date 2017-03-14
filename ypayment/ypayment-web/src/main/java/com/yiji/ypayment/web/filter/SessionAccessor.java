package com.yiji.ypayment.web.filter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yiji.ypayment.common.exception.BusinessException;
import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.web.front.form.ChargeItem;
import com.yjf.customer.service.info.OperatorInfo;
import com.yjf.customer.service.info.UserInfo;

/**
 * session初始化
 *
 *
 * @author faZheng
 *
 */
public class SessionAccessor {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected static final ThreadLocal<HttpSession> threadLocal = new ThreadLocal<>();
	private static final String SESSION_USERINFO = "userInfo";
	private static final String SESSION_OPERATORINFO = "operatorInfo";
	private static final String SESSION_INLET = "inlet";
	private static final String SESSION_TOKEN = "token";
	private static final String SESSION_PLATFORMTYPE = "platformType";
	private static final String SESSION_PARTNERID = "partnerId";
	private static final String SESSION_FAVOURABLE = "favourable";
	private static final String SESSION_CHARGEITEMS = "chargeItems";
	private static final String SESSION_GID = "gid";
	private static final String SESSION_MERCHORDERNO = "merchOrderNo";
	
	public static void init(UserInfo userInfo, OperatorInfo operatorInfo, String platformType, String inlet,
							String partnerId, FavourableEnum favourable, List<ChargeItem> chargeItems, String gid,
							String merchOrderNo) {
		HttpSession session = getSession();
		clearSession();
		session.setAttribute(SESSION_USERINFO, userInfo);
		session.setAttribute(SESSION_OPERATORINFO, operatorInfo);
		session.setAttribute(SESSION_INLET, inlet);
		session.setAttribute(SESSION_PLATFORMTYPE, platformType);
		session.setAttribute(SESSION_PARTNERID, partnerId);
		session.setAttribute(SESSION_FAVOURABLE, favourable);
		session.setAttribute(SESSION_CHARGEITEMS, chargeItems);
		session.setAttribute(SESSION_GID, gid);
		session.setAttribute(SESSION_MERCHORDERNO, merchOrderNo);
	}
	
	public static void clearSession() {
		HttpSession session = getSession();
		List<String> names = new ArrayList<>();
		Enumeration<String> em = session.getAttributeNames();
		while (em.hasMoreElements()) {
			names.add(em.nextElement());
		}
		for (String name : names) {
			session.removeAttribute(name);
		}
		/*session.invalidate();
		threadLocal.remove();*/
	}
	
	/**
	 * 设置session
	 * @param session
	 */
	public static void setSession(HttpSession session) {
		threadLocal.set(session);
	}
	
	/**
	 * 获取session
	 * @return HttpSession
	 */
	public static HttpSession getSession() {
		return threadLocal.get();
	}
	
	public static void invalidateSession() {
		HttpSession session = getSession();
		session.invalidate();
	}
	
	public boolean isLogin() {
		HttpSession session = getSession();
		return session.getAttribute(SESSION_USERINFO) != null;
	}
	
	public static UserInfo getUserInfo() {
		HttpSession session = getSession();
		return (UserInfo) session.getAttribute(SESSION_USERINFO);
	}
	
	public static OperatorInfo getOperatorInfo() {
		HttpSession session = getSession();
		return (OperatorInfo) session.getAttribute(SESSION_OPERATORINFO);
	}
	
	public static String getInlet() {
		HttpSession session = getSession();
		return (String) session.getAttribute(SESSION_INLET);
	}
	
	public static String getPlatformType() {
		HttpSession session = getSession();
		return (String) session.getAttribute(SESSION_PLATFORMTYPE);
	}
	
	public static String getPartnerId() {
		HttpSession session = getSession();
		return (String) session.getAttribute(SESSION_PARTNERID);
	}
	
	public static FavourableEnum getFavourable() {
		HttpSession session = getSession();
		return (FavourableEnum) session.getAttribute(SESSION_FAVOURABLE);
	}
	
	public static List<ChargeItem> getChargeItems() {
		HttpSession session = getSession();
		return (List<ChargeItem>) session.getAttribute(SESSION_CHARGEITEMS);
	}
	
	public static String getGid() {
		HttpSession session = getSession();
		return (String) session.getAttribute(SESSION_GID);
	}
	
	public static String getMerchOrderNo() {
		HttpSession session = getSession();
		return (String) session.getAttribute(SESSION_MERCHORDERNO);
	}
	
	public static void removeToken() {
		HttpSession session = getSession();
		session.removeAttribute(SESSION_TOKEN);
	}
	
	public static void checkToken(String requToken) {
		HttpSession session = getSession();
		String sessionToken = (String) session.getAttribute(SESSION_TOKEN);
		if (StringUtils.isEmpty(sessionToken) || !StringUtils.equals(requToken, sessionToken)) {
			throw new BusinessException("请勿重复提交");
		}
	}
}

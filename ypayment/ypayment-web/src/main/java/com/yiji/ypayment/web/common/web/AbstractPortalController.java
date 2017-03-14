package com.yiji.ypayment.web.common.web;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.web.common.utils.AppUtil;
import com.yiji.ypayment.web.filter.SessionAccessor;
import com.yiji.ypayment.web.front.form.ChargeItem;
import com.yjf.customer.service.info.OperatorInfo;
import com.yjf.customer.service.info.UserInfo;

/**
 * 抽象前端控制器
 *
 * @author zhangpu
 * @date 2014年4月9日
 */

/**
 * @author faZheng
 */
public abstract class AbstractPortalController extends AppUtil {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 错误提示页面
	 */
	protected final String ERROR_URL = "error";
	
	
	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	protected UserInfo getUserInfo() {
		return SessionAccessor.getUserInfo();
	}
	
	/**
	 * 获取用户操作员信息
	 * 
	 * @return
	 */
	protected OperatorInfo getOperatorInfo() {
		return SessionAccessor.getOperatorInfo();
	}
	
	/**
	 * 获取用户ID
	 * 
	 * @return
	 */
	protected String getUserId() {
		return SessionAccessor.getUserInfo().getUserId();
	}
	
	/**
	 * 获取用户操作员ID
	 * 
	 * @return
	 */
	protected String getOperatorId() {
		return SessionAccessor.getOperatorInfo().getOperatorId();
	}
	
	/**
	 * 获取系统入口编码
	 * 
	 * @return
	 */
	protected String getInlet() {
		return SessionAccessor.getInlet();
	}
	
	/**
	 * 获取商户ID
	 * 
	 * @return
	 */
	protected String getPartnerId() {
		return SessionAccessor.getPartnerId();
	}
	
	/**
	 * 获取平台类型
	 * 
	 * @return
	 */
	protected String getPlatformType() {
		return SessionAccessor.getPlatformType();
	}
	
	/**
	 * 是否为优惠缴费
	 * 
	 * @return
	 */
	protected FavourableEnum getFavourable() {
		return SessionAccessor.getFavourable();
	}
	
	/**
	 * 获取优惠缴费子列表
	 * 
	 * @return
	 */
	protected List<ChargeItem> getChargeItems() {
		return SessionAccessor.getChargeItems();
	}
	
	protected String getGid() {
		return SessionAccessor.getGid();
	}
	
	protected String getMerchOrderNo() {
		return SessionAccessor.getMerchOrderNo();
	}
	
	/**
	 * 防止重复提交生成token
	 * 
	 * @param session
	 */
	protected String setSessionToken(HttpSession session) {
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		return token;
	}
	
	/**
	 * 校验Token
	 * @param requToken
	 */
	public void checkToken(String requToken) {
		SessionAccessor.checkToken(requToken);;
	}
	
	/**
	 * 删除Token
	 */
	public void removeToken(){
		SessionAccessor.removeToken();
	}
}

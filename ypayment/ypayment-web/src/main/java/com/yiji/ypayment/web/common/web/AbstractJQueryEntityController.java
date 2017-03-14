package com.yiji.ypayment.web.common.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.yiji.ypayment.common.dao.support.PageInfo;
import com.yiji.ypayment.common.service.EntityService;
import com.yiji.ypayment.common.utils.Dates;
import com.yiji.ypayment.common.utils.EncodeUtils;
import com.yiji.ypayment.dal.entity.AbstractEntity;

/**
 * JQuery和JQuery-easyui作为前端的基础功能模板控制器
 * 
 * <p>
 * 说明： 使用JQuery和EasyUI作为前端的情况是JSTL和JSON开发模式的混合方式，该模式降低开发人员开发类EXTJS方式的前端的门槛。<br>
 * 规则：
 * <li>对于所有前导界面（index,create,edit等通过controller跳转到jsp使用JSTL初始化HTML界面）
 * 的訪問任然使用傳統JSTL页面跳转方式。
 * <li>对于数据访问动作（list分页查询，query条件查询，save,update,remove,get,show等）
 * 全部采用JSON方式实现前端UI功能操作免刷新
 * <li>数据导入导出任然使用传统方式
 * 
 * @author zhangpu
 * 
 * @param <T>
 * @param <M>
 */

public abstract class AbstractJQueryEntityController<T extends AbstractEntity, M extends EntityService<T>>
																											extends
																											AbstractStandardEntityController<T, M> {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractJQueryEntityController.class);
	
	@Override
	protected PageInfo<T> getPageInfo(HttpServletRequest request) {
		PageInfo<T> pageinfo = new PageInfo<T>();
		setPageCurrentPage(pageinfo, request);
		pageinfo.setCountOfCurrentPage(getDefaultPageSize());
		String page = request.getParameter("page");
		if (StringUtils.isNotBlank(page)) {
			pageinfo.setCurrentPage(Integer.parseInt(page));
		}
		String rows = request.getParameter("rows");
		if (StringUtils.isNotBlank(rows)) {
			pageinfo.setCountOfCurrentPage(Integer.parseInt(rows));
		}
		return pageinfo;
	}
	
	@Override
	protected Map<String, Boolean> getSortMap(HttpServletRequest request) {
		Map<String, Boolean> sortMap = Maps.newHashMap();
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		if (StringUtils.isNotBlank(sort)) {
			sortMap.put(sort, "asc".equalsIgnoreCase(order));
		}
		return sortMap;
	}
	
	@Override
	public String getListView() {
		if (StringUtils.isNotBlank(listView)) {
			return listView;
		}
		return getRequestMapperValue();
	}
	
	/**
	 * 分页设置当前页数
	 * 
	 * @param pageInfo
	 * @param request
	 */
	protected void setPageCurrentPage(PageInfo<T> pageInfo, HttpServletRequest request) {
		String currentPage = request.getParameter("currentPage");
		if (StringUtils.isNotBlank(currentPage)) {
			pageInfo.setCurrentPage(Integer.parseInt(currentPage));
		}
	}
	
	protected void sendCSRFToken(HttpServletRequest request) {
		String token = Dates.format(Dates.DATETIME_NOT_SEPARATOR);
		token = EncodeUtils.encodeHex(token.getBytes());
		request.getSession().setAttribute("csrfToken", token);
	}
	
	protected void checkCRFToken(HttpServletRequest request) {
		String requToken = request.getParameter("csrfToken");
		if (StringUtils.isBlank(requToken)) {
			requToken = request.getHeader("csrfToken");
		}
		String sessionToken = (String) request.getSession().getAttribute("csrfToken");
		if (requToken == null || !StringUtils.equals(requToken, sessionToken)) {
			throw new RuntimeException("Token验证失败，请刷新页面重试。");
		} else {
			request.getSession().removeAttribute("csrfToken");
		}
	}
	
}
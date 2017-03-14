package com.yiji.ypayment.web.common.web;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yiji.ypayment.common.dao.support.PageInfo;
import com.yiji.ypayment.common.service.EntityService;
import com.yiji.ypayment.common.utils.Dates;
import com.yiji.ypayment.dal.entity.AbstractEntity;

/**
 * 抽象业务操作类
 * 
 * 负责：框架基本功能(CRUD)的服务层调用
 * 
 * @author zhangpu
 * 
 * @param <T>
 * @param <M>
 */

public abstract class AbstractOperationController<T extends AbstractEntity, M extends EntityService<T>>
																										extends
																										AbstractGenericsController<T, M> {
	/** 实体ID名称 */
	protected String entityIdName = "id";
	/** 默认分页大小 */
	protected int defaultPageSize = 10;
	
	/**
	 * 執行分頁查詢
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	protected PageInfo<T> doList(HttpServletRequest request, HttpServletResponse response, Model model)
																										throws Exception {
		return getEntityService().query(getPageInfo(request), getSearchParams(request, model), getSortMap(request));
	}
	
	protected PageInfo<T> doList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return doList(request, response, new BindingAwareModelMap());
	}
	
	/**
	 * 执行查询
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected List<T> doQuery(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return getEntityService().query(getSearchParams(request, model), getSortMap(request));
	}
	
	protected List<T> doQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return doQuery(request, response, new BindingAwareModelMap());
	}
	
	/**
	 * 执行保存实体(根據請求中是否存在ID選擇保存或更新)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param isCreate
	 * @throws Exception
	 */
	protected T doSave(HttpServletRequest request, HttpServletResponse response, Model model, boolean isCreate)
																												throws Exception {
		T entity = loadEntity(request);
		if (entity == null) {
			entity = getEntityClass().newInstance();
		}
		doDataBinding(request, entity);
		onSave(request, response, model, entity, isCreate);
		// 这里服务层默认是根据entity的Id是否为空自动判断是SAVE还是UPDATE.
		getEntityService().save(entity);
		return entity;
	}
	
	/**
	 * 根据传入的ids删除实体。
	 * 
	 * <p>
	 * <li>ids.length ==1 则调用删除单个实体的服务 getEntityService().removeById(id)
	 * <li>
	 * ids.length > 1 则调用批量删除服务 getEntityService().removes(ids);
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param ids
	 * @throws Exception
	 */
	protected void doRemove(HttpServletRequest request, HttpServletResponse response, Model model, Serializable... ids)
																														throws Exception {
		if (ids == null || ids.length == 0) {
			throw new RuntimeException("请求参数中没有指定需要删除的实体Id");
		}
		if (ids.length == 1) {
			getEntityService().removeById(ids[0]);
		} else {
			getEntityService().removes(ids);
		}
	}
	
	/**
	 * 从request中获取需要删除的实体的ID
	 * 
	 * <p>
	 * 依次支持：
	 * <li>request.getParameterValues方式获取多个同名ID表单的值
	 * <li>request.getParameter方法获取单个ID表单的值，如果该值有逗号分割，则分割为多个ID
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@Deprecated
	protected void doRemove(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String[] ids = request.getParameterValues(getEntityIdName());
		List<Long> idList = Lists.newArrayList();
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				if (StringUtils.isNotBlank(id)) {
					if (StringUtils.contains(id, ",")) {
						String[] subIds = StringUtils.split(id, ",");
						for (String subId : subIds) {
							idList.add(Long.valueOf(subId));
						}
					} else {
						idList.add(Long.valueOf(id));
					}
				}
			}
		}
		if (idList.size() == 0) {
			throw new RuntimeException("请求参数中没有指定需要删除的实体Id");
		}
		Serializable[] lid = idList.toArray(new Long[] {});
		doRemove(request, response, model, lid);
	}
	
	/**
	 * 从request中获取需要删除的实体的ID
	 * 
	 * <p>
	 * 依次支持：
	 * <li>request.getParameterValues方式获取多个同名ID表单的值
	 * <li>request.getParameter方法获取单个ID表单的值，如果该值有逗号分割，则分割为多个ID
	 * 
	 * @param request
	 * @return
	 */
	protected Serializable[] getRequestIds(HttpServletRequest request) {
		String[] ids = request.getParameterValues(getEntityIdName());
		List<Long> idList = Lists.newArrayList();
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				if (StringUtils.isNotBlank(id)) {
					if (StringUtils.contains(id, ",")) {
						String[] subIds = StringUtils.split(id, ",");
						for (String subId : subIds) {
							idList.add(Long.valueOf(subId));
						}
					} else {
						idList.add(Long.valueOf(id));
					}
				}
			}
		}
		if (idList.size() == 0) {
			throw new RuntimeException("请求参数中没有指定需要删除的实体Id");
		}
		return idList.toArray(new Long[] {});
	}
	
	/**
	 * 加载实体
	 * 
	 * @param request
	 * @return
	 */
	protected T loadEntity(HttpServletRequest request) throws Exception {
		String id = request.getParameter(getEntityIdName());
		if (StringUtils.isNotBlank(id)) {
			return getEntityService().get(Long.valueOf(id));
		}
		return null;
	}
	
	/**
	 * 保存实体前，自定义组装对象。用于子类扩展实体组装或保存前检查
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param isCreate
	 * @throws Exception
	 */
	protected T onSave(HttpServletRequest request, HttpServletResponse response, Model model, T entity, boolean isCreate)
																															throws Exception {
		return entity;
	}
	
	/**
	 * 默认分页对象
	 * 
	 * @param request
	 * @return
	 */
	protected PageInfo<T> getPageInfo(HttpServletRequest request) {
		PageInfo<T> pageInfo = new PageInfo<T>();
		pageInfo.setCountOfCurrentPage(getDefaultPageSize());
		return pageInfo;
	}
	
	/**
	 * 默认查询条件
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	protected Map<String, Object> getSearchParams(HttpServletRequest request, Model model) {
		Map<String, Object> searchParams = this.getParametersStartingWith(request, "search_");
		model.addAttribute("param", searchParams);
		return searchParams;
	}
	
	/**
	 * 默认排序
	 * 
	 * @param request
	 * @return
	 */
	protected Map<String, Boolean> getSortMap(HttpServletRequest request) {
		return Maps.newHashMap();
	}
	
	protected void doDataBinding(HttpServletRequest request, Object command) throws Exception {
		bind(request, command);
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) {
		Map<String, Object> map = Maps.newHashMap();
		referenceData(request, map);
		return map;
	}
	
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		
	}
	
	/**
	 * 兼容设置
	 */
	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		initBinder(binder);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Dates.CHINESE_DATETIME_FORMAT_LINE);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
	}
	
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	
	public String getEntityIdName() {
		return entityIdName;
	}
	
	public void setEntityIdName(String entityIdName) {
		this.entityIdName = entityIdName;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Validate.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}
}
package com.yiji.ypayment.web.filter;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 验证用户是否登录
 * 
 * faZheng
 */
public class SecurityFilter implements Filter {
	private String loginUrl = "/error/page.html";
	private String[] excludePatterns = {};
	protected static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
	
	public void destroy() {
		this.loginUrl = null;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
							FilterChain chain) throws IOException, ServletException {
		HttpServletRequest res = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		SessionAccessor.setSession(res.getSession(true));
		String servletPath = ((HttpServletRequest) request).getServletPath();
		
		// 防止ie浏览器iframe嵌套下，session丢失
		resp.setHeader("P3P", "CP=CAO PSA OUR");
		
		//判断当前路径是否被排除
		boolean isExcluded = false;
		if(servletPath.contains(this.loginUrl)){
			//排除loginUrl
			isExcluded = true;
		}else{
			for (String strPattern : excludePatterns) {
				Pattern pattern = Pattern.compile(strPattern);
				Matcher matcher = pattern.matcher(servletPath);
				if (matcher.find()) {
					isExcluded = true;
					break;
				}
			}
		}

		if (isExcluded) {
			//如果属于不需要验证登录的url，直接访问
			logger.debug("--->允许访问");
			chain.doFilter(request, response);
		} else {
			//从session中获取当前操作员信息，如果不存在表示用户未登录
			if (checkParameters()) {
				logger.debug("--->允许访问");
				chain.doFilter(request, response);
			} else {
				res.getSession().setAttribute("timeOutMessage", "长时间未操作，为了您的账户安全请重新缴费系统！");
				logger.info("--->请重新登录");
				resp.sendRedirect(res.getContextPath() + this.loginUrl);
			}
		}
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		String loginUrlInit = filterConfig.getInitParameter("loginUrl");
		String excludePatternsInit = filterConfig.getInitParameter("excludePatterns");
		if (!StringUtils.isBlank(excludePatternsInit)) {
			this.excludePatterns = excludePatternsInit.split(",");
		}
		if (!StringUtils.isBlank(loginUrlInit)) {
			this.loginUrl = loginUrlInit;
		}
	}
	
	public boolean checkParameters(){
		if(SessionAccessor.getUserInfo() != null && SessionAccessor.getOperatorInfo() != null &&
				StringUtils.isNotEmpty(SessionAccessor.getInlet()) && StringUtils.isNotEmpty(SessionAccessor.getPartnerId()) &&
					StringUtils.isNotEmpty(SessionAccessor.getPlatformType())) {
			return true;
		} else {
			return false;
		}
	}
	
}

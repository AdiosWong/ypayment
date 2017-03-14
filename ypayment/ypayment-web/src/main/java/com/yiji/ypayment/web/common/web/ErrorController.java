package com.yiji.ypayment.web.common.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 错误页面
 *
 * @author faZheng
 *
 */
@Controller
@RequestMapping("error")
public class ErrorController extends AbstractPortalController {
	
	@RequestMapping("page")
	public String errorPage(HttpServletRequest request, ModelMap model) {
		return ERROR_URL;
	}
	
}
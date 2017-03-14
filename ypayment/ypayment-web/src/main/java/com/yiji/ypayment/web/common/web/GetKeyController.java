/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.yiji.ypayment.web.common.web;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.phprpc.PHPRPC_Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @Filename BaseController.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author tangzongwei
 * 
 * @Email tzongwei@yiji.com
 * 
 * @History <li>Author: tangzongwei</li> <li>Date: 2011-7-3</li> <li>Version:
 * 1.0</li> <li>Content: create</li>
 * 
 */
@Controller
public class GetKeyController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected String str = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()";
	
	protected Random random = new Random();
	
	@RequestMapping("/anon/toGetKey.htm")
	public void toGetKey(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//		PHPRPC_Server loginServer = new PHPRPC_Server();
		//		loginServer.add("getKey", this);
		//		loginServer.start(request, response);
		logger.info("成功从服务器获取安全控件密钥");
	}
	
	public String getKey(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String aesKey = getRandomString(16);
		session.setAttribute(session.getId(), aesKey);
		return aesKey;
	}
	
	// 产生一个随机数 方法
	public String getRandomString(int length) {
		StringBuilder sf = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sf.append(str.charAt(random.nextInt(72)));
		}
		return sf.toString();
	}
	
}

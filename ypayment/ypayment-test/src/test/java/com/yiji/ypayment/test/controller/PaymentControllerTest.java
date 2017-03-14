/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 下午3:52:59 创建
 */
package com.yiji.ypayment.test.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.yiji.ypayment.facade.enums.PayWayEnum;
import com.yiji.ypayment.test.base.TestBase;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class PaymentControllerTest extends TestBase {
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}
	
	@Test
	@Ignore
	public void paymentIndex() throws Exception {
		MvcResult authResult = mockMvc.perform((post("/payment/paymentIndex.json").param("paymentType", "")))
			.andExpect(status().isOk()).andReturn();
		
		MockHttpServletResponse response = authResult.getResponse();
		response.setCharacterEncoding("utf-8");
		String string = response.getContentAsString();
		System.out.print(string);
	}
	
	@Test
	@Ignore
	public void unbandUserCode() throws Exception {
		MvcResult authResult = mockMvc
			.perform((post("/payment/unbandUserCode.json").param("contractNo", "20151207000000000129")))
			.andExpect(status().isOk()).andReturn();
		
		MockHttpServletResponse response = authResult.getResponse();
		response.setCharacterEncoding("utf-8");
		String string = response.getContentAsString();
		System.out.print(string);
	}
	
	@Test
	@Ignore
	public void hasUser() throws Exception {
		MvcResult authResult = mockMvc
			.perform(
				(post("/payment/hasUser.json").param("userCode", "00000029").param("paymentType", "GAS")
					.param("resourceCode", "002300007").param("resourceName", "重庆燃气"))).andExpect(status().isOk())
			.andReturn();
		
		MockHttpServletResponse response = authResult.getResponse();
		response.setCharacterEncoding("utf-8");
		String string = response.getContentAsString();
		System.out.print(string);
	}
	
	@Test
	@Ignore
	public void toPaymentOrder() throws Exception {
		MvcResult authResult = mockMvc
			.perform((post("/payment/toPaymentOrder.json").param("contractNo", "20160307000000001359")))
			.andExpect(status().isOk()).andReturn();
		
		MockHttpServletResponse response = authResult.getResponse();
		response.setCharacterEncoding("utf-8");
		String string = response.getContentAsString();
		System.out.print(string);
	}
	
	@Test
	@Ignore
	public void paymentRecord() throws Exception {
		MvcResult authResult = mockMvc
			.perform((post("/payment/paymentRecord.json").param("pageNum", "1").param("paymentType", "MOBILE")))
			.andExpect(status().isOk()).andReturn();
		
		MockHttpServletResponse response = authResult.getResponse();
		response.setCharacterEncoding("utf-8");
		String string = response.getContentAsString();
		System.out.print(string);
	}
	
	@Test
	@Ignore
	public void checkPayPwd() throws Exception {
		MvcResult authResult = mockMvc.perform((post("/payment/checkPayPwd.json").param("payPwd", "aaaa11111")))
			.andExpect(status().isOk()).andReturn();
		
		MockHttpServletResponse response = authResult.getResponse();
		response.setCharacterEncoding("utf-8");
		String string = response.getContentAsString();
		System.out.print(string);
	}
	
	@Test
	@Ignore
	public void sendRecharge() throws Exception {
		MvcResult authResult = mockMvc
			.perform(
				(post("/payment/sendRecharge.json").param("userCode", "1234").param("amount", "123")
					.param("payWay", PayWayEnum.BY_BALANCE.getCode()).param("password", "43645")
					.param("pactNo", "43534").param("paymentType", "GAS")
					.param("token", "5656"))).andExpect(status().isOk()).andReturn();
		
		MockHttpServletResponse response = authResult.getResponse();
		response.setCharacterEncoding("utf-8");
		String string = response.getContentAsString();
		System.out.print(string);
	}
}

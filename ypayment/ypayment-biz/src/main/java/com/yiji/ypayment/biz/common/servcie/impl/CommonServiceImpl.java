/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@yiji.com 2015-01-19 11:07 创建
 *
 */
package com.yiji.ypayment.biz.common.servcie.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiji.ypayment.biz.common.servcie.CommonService;
import com.yiji.ypayment.biz.common.servcie.FileHandleService;
import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.dal.entity.common.SysSeq;
import com.yiji.ypayment.dal.repository.common.SysSeqDao;
import com.yiji.ypayment.facade.common.PaymentResultBase;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.integration.client.CustomerClient;
import com.yjf.common.lang.result.Status;
import com.yjf.common.service.enums.customer.UserTypeEnum;
import com.yjf.customer.service.enums.CustomerResultEnum;
import com.yjf.customer.service.order.UniformStringQueryOrder;
import com.yjf.customer.service.result.UserQueryResult;

/**
 * 公共服务
 * 
 * @author CuiFuQ
 * 
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {
	
	@Autowired
	private SysSeqDao sysSeqDao;
	@Autowired
	private CustomerClient customerClient;
	@Autowired
	private FileHandleService fileHandleService;
	
	private static final String SEQ_PLACEHOLDER = "000000000000";
	
	@Override
	public String generateBizNo(PaymentInstructionAction paymentAction) {
		StringBuilder sb = new StringBuilder();
		sb.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		StringBuilder seq = new StringBuilder(SEQ_PLACEHOLDER);
		Long sysSeqId = getSeqId(paymentAction.message());
		seq.append(String.valueOf(sysSeqId));
		String seqNo = seq.substring(seq.length() - 12);
		sb.append(seqNo);
		return sb.toString();
	}
	
	/**
	 * @return
	 */
	private Long getSeqId(String seqname) {
		SysSeq sysSeqDO = new SysSeq();
		sysSeqDO.setName(seqname);
		SysSeq newSysSeqDO = sysSeqDao.save(sysSeqDO);
		Long sysSeqId = newSysSeqDO.getId();
		return sysSeqId;
	}
	
	@Override
	public boolean checkUserType(UniformStringQueryOrder order, UserTypeEnum userType) {
		UserQueryResult result = customerClient.findUserInfoByUserId(order);
		if (result.getResultCode() == CustomerResultEnum.EXECUTE_SUCCESS) {
			if (result.getUserInfo().getUserType() == userType) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void checkGid(String gid) {
		if (gid == null) {
			throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "gid为空");
		} else if (gid.length() != 35) {
			throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "gid长度不是35位");
		}
	}
	
	@Override
	public boolean isAfter15(Date date) {
		int dayEndTime = 150000;
		DateFormat dateFormat = new SimpleDateFormat("HHmmss");
		int sysTime = Integer.parseInt(dateFormat.format(date).toString());
		return sysTime > dayEndTime;
	}
	
	@Override
	public void setDefaultResult(PaymentResultBase result) {
		result.setStatus(Status.SUCCESS);
		result.setCode(PaymentResultCode.EXECUTE_SUCCESS.getCode());
		result.setDescription(PaymentResultCode.EXECUTE_SUCCESS.getMessage());
		result.setResultCode(PaymentResultCode.EXECUTE_SUCCESS);
	}
}

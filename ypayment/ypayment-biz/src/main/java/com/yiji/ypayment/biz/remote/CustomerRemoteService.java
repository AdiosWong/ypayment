package com.yiji.ypayment.biz.remote;

import com.yjf.common.service.enums.customer.CertifyStatusEnum;
import com.yjf.customer.service.order.UniformStringQueryOrder;

/**
 * 会员信息
 * 
 * @author CuiFuQ
 * 
 */
public interface CustomerRemoteService {
	
	/**
	 * check用户实名状态
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	boolean checkUserCertifyStatus(UniformStringQueryOrder uniformStringQueryOrder, CertifyStatusEnum status);
	
	/**
	 * 记录缴费用户信息
	 * @param userId
	 */
	void createPaymentUser(UniformStringQueryOrder uniformStringQueryOrder);
}

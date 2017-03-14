package com.yiji.ypayment.biz.facadeImpl.base;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.facade.common.PaymentResultBase;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yjf.common.lang.enums.CommonErrorCode;
import com.yjf.common.lang.result.Status;
import com.yjf.common.service.Order;
import com.yjf.common.service.OrderBase;

/**
 * 
 * @author CuiFuQ
 *
 */
public class BizServiceBase {
	
	private static final Logger logger = LoggerFactory.getLogger(BizServiceBase.class);
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	public <O extends OrderBase, R extends PaymentResultBase, D> R doBiz(	final O order,
																			final PaymentProcessorTemplate<O, R> processor) {
		// 1.打印请求日志
		logger.info("收到请求 action={},order={}", processor.getAction().message(), order);
		final R result = processor.initResult(order);
		long startTime = System.currentTimeMillis();
		
		try {
			// 2.参数校验
			checkOrder(order);
			// 3.设置默认结果
			setDefaultResult(result);
			// 4.业务处理
			transactionTemplate.execute(new TransactionCallback<Object>() {
				@Override
				public Object doInTransaction(TransactionStatus status) {
					try {
						processor.execute(order, result);
					} catch (PaymentException paymentException) {
						setPaymentExceptionResult(result, paymentException);
						logger.info("{}", paymentException);
					} catch (Exception e) {
						setExceptionResult(result, e);
						logger.info("{}", e);
					} finally {
						status.flush();
					}
					
					return null;
				}
			});
		} catch (PaymentException PaymentException) {
			setPaymentExceptionResult(result, PaymentException);
			logger.info("{}", PaymentException);
		} catch (Exception e) {
			setExceptionResult(result, e);
			logger.info("{}", e);
		}
		// 打印结果日志
		printLog(order, result, startTime);
		return result;
	}
	
	/**
	 * 打印日志
	 * 
	 * @param order
	 * @param result
	 * @param startTime
	 */
	private void printLog(OrderBase order, PaymentResultBase result, long startTime) {
		if (result.isSuccess()) {
			logger.info("处理结果成功 order={},result={},耗时time={} ", order, result, System.currentTimeMillis() - startTime);
		} else {
			logger.info("处理结果失败 order={},result={},耗时time={} ", order, result, System.currentTimeMillis() - startTime);
		}
	}
	
	/**
	 * 校验入参
	 * 
	 * @param order
	 */
	protected void checkOrder(Order order) {
		if (null == order) {
			throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, "order can not be null");
		}
		try {
			order.check();
		} catch (Exception e) {
			logger.error("请求参数不完整：" + e.getMessage() + order);
			throw new PaymentException(PaymentResultCode.INVAILD_PARAMETER, StringUtils.left(e.getMessage(), 200));
		}
	}
	
	/**
	 * 设置默认结果
	 * 
	 * @param result
	 */
	protected void setDefaultResult(PaymentResultBase result) {
		result.setStatus(Status.SUCCESS);
		result.setCode(CommonErrorCode.SUCCESS.getCode());
		result.setDescription(CommonErrorCode.SUCCESS.getMessage());
		result.setResultCode(PaymentResultCode.EXECUTE_SUCCESS);
	}
	
	/**
	 * 设置PaymentException异常返回结果
	 * 
	 * @param result
	 * @param PaymentException
	 */
	protected void setPaymentExceptionResult(PaymentResultBase result, PaymentException paymentException) {
		String message = paymentException.getErrorMessage();
		if(StringUtils.isNotEmpty(paymentException.getErrorMessage())) {
			message = paymentException.getResultCode().getMessage() + ":" + paymentException.getErrorMessage();
		} else {
			message = paymentException.getResultCode().getMessage();
		}
		result.setFailResultWithMsg(paymentException.getResultCode(), message);
	}
	
	/**
	 * 设置Exception异常返回结果
	 * 
	 * @param result
	 * @param e
	 */
	protected void setExceptionResult(PaymentResultBase result, Exception e) {
		logger.error("缴费execute发生异常", e);
		result.setFailResultWithMsg(PaymentResultCode.UNKNOWN_EXCEPTION, "缴费发生系统异常!");
	}
}

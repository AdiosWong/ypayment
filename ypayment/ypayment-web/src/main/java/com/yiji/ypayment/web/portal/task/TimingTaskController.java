package com.yiji.ypayment.web.portal.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.yiji.ypayment.biz.remote.TimerTaskService;
import com.yjf.common.util.StringUtils;

/**
 * 定时任务
 * 
 * @author CuiFuQ
 * 
 */
@Controller
@RequestMapping("/timingTask")
public class TimingTaskController {
	
	private static final Logger logger = LoggerFactory.getLogger(TimingTaskController.class);
	
	@Autowired
	private TimerTaskService timerTaskService;
	
	/**
	 * 查看程序使用线程
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/threads.json")
	@ResponseBody
	public Object threads(Model model) {
		Map<String, Object> map = Maps.newHashMap();
		try {
			map.put("allStackTraces", Thread.getAllStackTraces().size());
			map.put("activeThread", Thread.activeCount());
			logger.info("查看程序使用线程:{}", map);
		} catch (Exception e) {
			logger.error("查看程序使用线程，发生异常", e);
			map.put("code", 0);
			map.put("message", "系统繁忙,请稍后重试!");
		}
		return map;
	}
	
	/**
	 * 更新订单表状态为挂起的订单，解冻状态为失败的订单 更新返销表状态
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryHangupPaymentOrder.json")
	@ResponseBody
	public Object updatePaymentOrderStstus(Model model) {
		logger.info("定时任务：更新订单表状态为挂起的订单，解冻状态为失败的订单，更新返销表状态");
		Map<String, Object> map = Maps.newHashMap();
		try {
			timerTaskService.updatePaymentOrderStatus();
			timerTaskService.updateUndoPaymentStatus();
			map.put("code", 1);
			map.put("message", "更新订单表状态为挂起的订单成功");
			logger.info("定时任务更新订单:{}", map);
		} catch (Exception e) {
			logger.error("更新订单表状态为挂起的订单，发生异常", e);
			map.put("code", 0);
			map.put("message", "更新订单表状态为挂起的订单，发生异常");
		}
		return map;
	}
	
	/**
	 * 查询订单状态为成功的订单，解冻并转账；
	 * @param model
	 * @return
	 */
	@RequestMapping("/paymentOrderTransferTrade.json")
	@ResponseBody
	public Object paymentOrderTransferTrade(Model model) {
		logger.info("定时任务：查询订单状态为成功的订单，解冻并转账");
		Map<String, Object> map = Maps.newHashMap();
		try {
			timerTaskService.transferProcessoringTrade();
			map.put("code", 1);
			map.put("message", "查询订单状态为成功的订单，解冻并转账成功");
			logger.info("定时任务转账:{}", map);
		} catch (Exception e) {
			logger.error("查询订单状态为成功的订单，解冻并转账，发生异常", e);
			map.put("code", 0);
			map.put("message", "查询订单状态为成功的订单，解冻并转账，发生异常");
		}
		return map;
	}
	
	/**
	 * 查询需要再次异步通知的订单，执行异步通知
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/apiAsyncNotify.json")
	@ResponseBody
	public Object apiAsyncNotify(Model model){
		logger.info("定时任务：查询需要异步通知的订单，执行异步通知");
		Map<String, Object> map = Maps.newHashMap();
		try {
			timerTaskService.updateApiAsyncNotify();
			map.put("code", 1);
			map.put("message", "查询需要异步通知的订单，执行异步通知成功");
			logger.info("定时任务转账:{}", map);
		} catch (Exception e) {
			logger.error("查询需要异步通知的订单，执行异步通知，发生异常", e);
			map.put("code", 0);
			map.put("message", "查询需要异步通知的订单，执行异步通知，发生异常");
		}
		return map;
	}
	
	/**
	 * 中信订单对账
	 * 定时任务每天执行一次，可手动触发
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkZhongxinStatus.json")
	@ResponseBody
	public Object checkZhongxinStatus(Model model, String dateStr){
		logger.info("定时任务：中信订单对账");
		Map<String, Object> map = Maps.newHashMap();
		try {
			if(StringUtils.isNotBlank(dateStr)){
				dateStr = dateStr.replace("-", "");
			}else{
				dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
			}
			timerTaskService.checkZhongXinStatus(dateStr);
			map.put("code", 1);
			map.put("message", "中信订单对账成功");
			logger.info("定时任务转账:{}", map);
		} catch (Exception e) {
			logger.error("中信订单对账，发生异常", e);
			map.put("code", 0);
			map.put("message", "中信订单对账，发生异常");
		}
		return map;
	}
	
}

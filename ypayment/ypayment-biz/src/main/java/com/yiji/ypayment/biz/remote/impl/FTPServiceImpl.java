/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author xinqing@yiji.com 上午10:22:45 创建
 */
package com.yiji.ypayment.biz.remote.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yiji.ypayment.biz.remote.FTPService;
import com.yiji.ypayment.biz.remote.base.RemoteServiceBase;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import com.yjf.common.util.ToString;

/**
 *
 *
 * @author xinqing@yiji.com
 *
 */
@Service("ftpService")
public class FTPServiceImpl extends RemoteServiceBase implements FTPService {
	
	private static final Logger logger = LoggerFactory.getLogger(FTPService.class);
	
	@Value("${zhongxin.ftp.host}")
	private String ftpHost;
	
	@Value("${zhongxin.ftp.port}")
	private int port;
	
	@Value("${zhongxin.ftp.username}")
	private String userName;
	
	@Value("${zhongxin.ftp.password}")
	private String password;
	
	@Value("${zhongxin.ftp.filePath}")
	private String filePath;
	
	public List<OrderItem> getPaymentOrder(String fileName) {
		logger.info("获取FTP服务器文件", fileName);
		FTPClient ftpClient = new FTPClient();
		List<OrderItem> orderItems = Lists.newArrayList();
		try {
			ftpClient.connect(ftpHost, port);
			ftpClient.login(userName, password);
			
			int replyCode = ftpClient.getReplyCode();
			logger.info("登录FTP服务器:{},{}", ftpHost, ftpClient.getReplyStrings());
			
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
				return orderItems;
			}
			
			//转移到FTP服务器目录
			if (!ftpClient.changeWorkingDirectory(filePath)) {
				logger.error("切换FTP工作目录至:{}出错,{}", filePath, ftpClient.getReplyStrings());
				return orderItems;
			}
			FTPFile[] fs = ftpClient.listFiles();
			
			for (FTPFile ff : fs) {
				if (ff.getName().substring(0, 16).equalsIgnoreCase(fileName)) {
					InputStream ins = ftpClient.retrieveFileStream(ff.getName());
					BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
					String line;
					int lineNo = 0;
					while ((line = reader.readLine()) != null) {
						lineNo++;
						if (lineNo == 1 || lineNo == 2)
							continue;
						String[] itemStr = line.split("[|]");
						OrderItem orderItem = new OrderItem();
						orderItem.setPaymentOrderNo(itemStr[0]);
						Money money = new Money();
						money.setCent(Long.parseLong(itemStr[1]));
						orderItem.setAmount(money);
						orderItems.add(orderItem);
					}
					reader.close();
					if (ins != null) {
						ins.close();
					}
				}
			}
		} catch (IOException e) {
			logger.error("从ftp服务器下载文件出错,异常信息:", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				logger.error("关闭ftp连接出错,", e);
			}
		}
		logger.info("FTP服务器获取的成功的订单列表：{}", orderItems);
		return orderItems;
	}
	
	public static class OrderItem {
		
		private String paymentOrderNo;
		
		private Money amount;
		
		public String getPaymentOrderNo() {
			return this.paymentOrderNo;
		}
		
		public void setPaymentOrderNo(String paymentOrderNo) {
			this.paymentOrderNo = paymentOrderNo;
		}
		
		public Money getAmount() {
			return this.amount;
		}
		
		public void setAmount(Money amount) {
			this.amount = amount;
		}
		
		@Override
		public String toString() {
			return ToString.toString(this);
		}
		
	}
	
}

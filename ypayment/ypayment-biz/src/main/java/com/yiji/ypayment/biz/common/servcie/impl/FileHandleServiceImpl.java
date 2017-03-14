package com.yiji.ypayment.biz.common.servcie.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yiji.ypayment.biz.common.servcie.FileHandleService;

@Service("fileHandleService")
public class FileHandleServiceImpl implements FileHandleService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileHandleServiceImpl.class);
	
	@Override
	public void writeFile(File fileName, String context) {
		
		try {
			if (!fileName.exists())// 如果文件不存在,则新建.
			{
				File parentDir = new File(fileName.getParent());
				if (!parentDir.exists())
					parentDir.mkdirs();
				fileName.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// FileWriter writer = null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			// context = new String(context.getBytes("UTF-8"), "GBK");
			fos = new FileOutputStream(fileName, true);
			osw = new OutputStreamWriter(fos, "GBK");
			osw.write(context + "\r\n");
			osw.flush();
			osw.close();
			fos.close();
			// fos.write(context.getBytes("UTF-8"));
			// writer = new FileWriter(fileName, true);
			// writer.write(context + "\r\n");
			// writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fos) {
					fos.close();
				}
				if (null != osw) {
					osw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
	}
	
	@Override
	public List<String> readFile(File file) {
		List<String> result = new ArrayList<>();
		BufferedReader reader = null;
		try {
			logger.info("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String strData = null;
			while ((strData = reader.readLine()) != null) {
				// 不读取空行
				if ((strData.length() > 0)) {
					logger.info(strData);
					result.add(strData);
				}
			}
			reader.close();
		} catch (Exception e) {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 读取文件,数据存入数据库中 计算 符号位 的数量
	 */
	
	private int getDataLen(String strDate, String choice) {
		char array_char[];
		int total = 0;
		array_char = strDate.toCharArray();
		for (int i = 0; i < array_char.length; i++) {
			if (array_char[i] == (choice.charAt(0))) {
				total++;
			}
		}
		return total;
	}
	
	@Override
	public List<String> readDetails(String filePath, int startIndex) {
		// 获取整个文件数据
		List<String> data = this.readFile(new File(filePath));
		// 获取startIndex极其之后的数据
		List<String> details = data.subList(startIndex, data.size());
		return details;
	}
	
	/**
	 * 创建文件目录
	 * 
	 * @param fileName
	 */
	public void createFile(File fileName) {
		try {
			if (!fileName.exists())// 如果文件不存在,则新建.
			{
				File parentDir = new File(fileName.getParent());
				if (!parentDir.exists())
					parentDir.mkdirs();
				fileName.createNewFile();
				logger.info("创建文件新的目录" + fileName.getAbsolutePath());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean deleteFile(String filePath) {
		File file = new File(filePath);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				logger.info("删除单个文件" + filePath + "成功！");
				return true;
			} else {
				logger.info("删除单个文件" + filePath + "失败！");
				return false;
			}
		} else {
			logger.info("删除单个文件失败：" + filePath + "不存在！");
			return true;
		}
	}
}

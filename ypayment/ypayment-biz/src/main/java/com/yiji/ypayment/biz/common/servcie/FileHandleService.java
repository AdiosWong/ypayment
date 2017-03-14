package com.yiji.ypayment.biz.common.servcie;

import java.io.File;
import java.util.List;

/**
 * 文件处理
 * 
 * @author CuiFuQ
 * 
 */
public interface FileHandleService {
	
	/**
	 * 写文件
	 * 
	 * @param fileName
	 * @param context
	 */
	void writeFile(File fileName, String context);
	
	/**
	 * 读文件
	 * 
	 * @param file
	 * @return
	 */
	List<String> readFile(File file);
	
	/**
	 * 读取文件详细记录，startIndex为从第几行开始读取（从0开始）
	 * 
	 * @param filePath
	 * @param startIndex
	 * @return
	 */
	List<String> readDetails(String filePath, int startIndex);
	
	/**
	 * 创建文件目录
	 * 
	 * @param fileName
	 */
	public void createFile(File fileName);
	
	/**
	 * 删除文件
	 * 
	 * @param fileName
	 */
	public boolean deleteFile(String filePath);
}

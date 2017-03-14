package com.yiji.ypayment.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yjf.common.util.StringUtils;

public class FileHandle {
	public static String DEFAULT_CHARSET = "GB2312";
	
	private String filePath;
	private String charset;
	private BufferedReader reader;
	
	private static Logger logger = LoggerFactory.getLogger(FileHandle.class);
	
	public FileHandle(String filePath) throws IOException {
		this(filePath, DEFAULT_CHARSET);
	}
	
	public FileHandle(String filePath, String charset) throws IOException {
		this.filePath = filePath;
		this.charset = charset;
		this.reader = getBufferedReader(filePath, charset);
	}
	
	/**
	 * @param filePath
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader getBufferedReader(String filePath, String charset) throws IOException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), charset));
	}
	
	/**
	 * 读取下一行
	 * 
	 * @return
	 * @throws IOException
	 */
	public String readNextLine() throws IOException {
		return reader.readLine();
	}
	
	/**
	 * 读取多行
	 * 
	 * @param lineNumber
	 * @return
	 * @throws IOException
	 */
	public List<String> readNextLines(long lineNumber) throws IOException {
		List<String> lines = new ArrayList<>();
		for (int i = 0; i < lineNumber; i++) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			lines.add(line);
		}
		return lines;
	}
	
	/**
	 * 读取不为空的行
	 * 
	 * @return
	 * @throws IOException
	 */
	public String readNotEmptyLine() throws IOException {
		String line = null;
		do {
			line = reader.readLine();
		} while (line != null && StringUtils.isBlank(line));
		return line;
	}
	
	/**
	 * 跳过lineNumber行数据
	 * 
	 * @param lineNumber
	 * @throws IOException
	 */
	public void skipLines(long lineNumber) throws IOException {
		while (lineNumber > 0) {
			readNextLine();
			lineNumber--;
		}
	}
	
	/**
	 * 跳过不为空的lineNumber行数据
	 * 
	 * @param lineNumber
	 * @throws IOException
	 */
	public void skipNotEmptyLines(long lineNumber) throws IOException {
		while (lineNumber > 0) {
			readNotEmptyLine();
			lineNumber--;
		}
	}
	
	/**
	 * 重新打开该文件
	 * 
	 * @throws IOException
	 */
	public void reOpen() throws IOException {
		reader.close();
		reader = getBufferedReader(filePath, charset);
	}
	
	/**
	 * 关闭
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		reader.close();
	}
	
	public String getCharset() {
		return charset;
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public BufferedReader getReader() {
		return reader;
	}
	
	/**
	 * 执行fhRunnable.run()函数。文件会自动打开和关闭
	 * 
	 * @param filePath
	 * @param fhRunnable
	 * @throws IOException
	 */
	public static void execute(String filePath, FhRunnable fhRunnable) throws Exception {
		FileHandle fileHandle = null;
		try {
			fileHandle = new FileHandle(filePath);
			fhRunnable.run(fileHandle);
		} finally {
			if (fileHandle != null) {
				try {
					fileHandle.close();
				} catch (IOException e) {
					logger.info("{}", e);
				}
			}
		}
	}
	
	/**
	 * 在run()函数中做业务。
	 * 
	 * @author Admin
	 */
	public interface FhRunnable {
		void run(FileHandle fileHandle) throws Exception;
	}
	
}

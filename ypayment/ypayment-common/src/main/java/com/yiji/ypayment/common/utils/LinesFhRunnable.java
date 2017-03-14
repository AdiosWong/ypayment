package com.yiji.ypayment.common.utils;

import com.yiji.ypayment.common.utils.FileHandle.FhRunnable;

/**
 * 读出一行文件内容执行doBiz()函数，直到文件读完为止。
 * 
 * @author Admin
 */
public abstract class LinesFhRunnable implements FhRunnable {
	@Override
	public void run(FileHandle fileHandle) throws Exception {
		String line = null;
		while ((line = fileHandle.readNotEmptyLine()) != null) {
			doBiz(line);
		}
	}
	
	public abstract void doBiz(String line) throws Exception;
}

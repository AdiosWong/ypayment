package com.yiji.ypayment.common.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Logger;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;
import com.yjf.common.lang.exception.Exceptions;
import com.yjf.common.log.LoggerFactory;
import com.yjf.common.log.LoggerImpl;
import com.yjf.common.util.StringUtils;

public class SFTPUtils {
	
	/**
	 * 创建session
	 * 
	 * @param host 目标地址
	 * @param port 端口
	 * @param userName 用户名
	 * @param password 密码
	 * @return Session
	 */
	public static Session createSession(String host, int port, String userName, String password) {
		JSch jsch = new JSch();
		try {
			Session session = jsch.getSession(userName, host, port);
			session.setUserInfo(new YJFUserInfo(password));
			session.connect();
			return session;
		} catch (JSchException e) {
			throw Exceptions.rethrow(e);
		}
	}
	
	/**
	 * 创建ChannelSftp
	 * 
	 * @param session Session
	 * @return ChannelSftp
	 */
	public static ChannelSftp createChannel(Session session) {
		try {
			Channel channel = session.openChannel("sftp");
			channel.connect();
			return (ChannelSftp) channel;
		} catch (JSchException e) {
			throw Exceptions.rethrow(e);
		}
	}
	
	/**
	 * 从sftp服务器指定目录下载文件到本地目录
	 * 
	 * @param channelSftp ChannelSftp对象
	 * @param src sftp上文件路径
	 * @param dst 本地目录
	 */
	public static void download(ChannelSftp channelSftp, String src, String dst) {
		try {
			channelSftp.get(src, dst, null, ChannelSftp.OVERWRITE);
		} catch (SftpException e) {
			throw Exceptions.rethrow(e);
		}
	}
	
	/**
	 * 文件上传
	 * 
	 * @param channelSftp
	 * @param src
	 * @param dst
	 */
	public static void uploadFile(ChannelSftp channelSftp, String src, String dst) {
		try {
			channelSftp.put(src, dst, null, ChannelSftp.OVERWRITE);
		} catch (SftpException e) {
			throw Exceptions.rethrow(e);
		}
	}
	
	/**
	 * 从sftp服务器指定目录下载文件到本地目录
	 * 
	 * @param host 目标地址
	 * @param port 端口
	 * @param userName 用户名
	 * @param password 密码
	 * @param src sftp上文件路径
	 * @param dst 本地目录
	 */
	public static void download(String host, int port, String userName, String password, final String src,
								final String dst) {
		exeSftpCmd(host, port, userName, password, new SFTPCommand() {
			@Override
			public void exe(ChannelSftp channelSftp) {
				try {
					channelSftp.get(src, dst, null, ChannelSftp.OVERWRITE);
				} catch (SftpException e) {
					throw Exceptions.rethrow(e);
				}
			}
		});
	}
	
	public static boolean ensureRemoteDirExist(ChannelSftp channelSftp, String remoteDir) {
		try {
			channelSftp.cd(remoteDir);
			if (channelSftp.pwd().equals(remoteDir)) {
				return true;
			} else {
				channelSftp.cd("/");
			}
		} catch (SftpException ignore) {
		}
		String[] filePathArr = remoteDir.substring(1).split("/");
		for (String interatePath : filePathArr) {
			if (StringUtils.isEmpty(interatePath)) {
				continue;
			}
			try {
				channelSftp.cd(interatePath);
			} catch (Exception e) {
				try {
					channelSftp.mkdir(interatePath);
					channelSftp.cd(interatePath);
				} catch (SftpException e1) {
					e1.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 连接到目标服务器执行command
	 * 
	 * @param host 目标地址
	 * @param port 端口
	 * @param userName 用户名
	 * @param password 密码
	 * @param command 操作命令
	 * 
	 */
	public static void exeSftpCmd(String host, int port, String userName, String password, SFTPCommand command) {
		if (command != null) {
			Session session = null;
			ChannelSftp channelSftp = null;
			try {
				session = createSession(host, port, userName, password);
				channelSftp = createChannel(session);
				if (channelSftp != null) {
					command.exe(channelSftp);
				}
			} finally {
				try {
					closeChannel(channelSftp);
				} finally {
					closeSession(session);
				}
			}
		}
	}
	
	/**
	 * 关闭channel
	 * 
	 * @param channelSftp ChannelSftp
	 */
	public static void closeChannel(ChannelSftp channelSftp) {
		if (channelSftp != null) {
			channelSftp.exit();
		}
	}
	
	/**
	 * 关闭session
	 * 
	 * @param session Session
	 */
	public static void closeSession(Session session) {
		if (session != null) {
			session.disconnect();
		}
	}
	
	/**
	 * 处理密码输入
	 */
	private static class YJFUserInfo implements UserInfo {
		private String password;
		
		public YJFUserInfo(String password) {
			this.password = password;
		}
		
		@Override
		public String getPassphrase() {
			return null;
		}
		
		@Override
		public String getPassword() {
			return this.password;
		}
		
		@Override
		public boolean promptPassword(String s) {
			return true;
		}
		
		@Override
		public boolean promptPassphrase(String s) {
			return true;
		}
		
		@Override
		public boolean promptYesNo(String s) {
			return true;
		}
		
		@Override
		public void showMessage(String s) {
			
		}
	}
	
	/**
	 * JSCH 日志记录器
	 */
	private static class JschLogger implements com.jcraft.jsch.Logger {
		
		private static final com.yjf.common.log.Logger logger = LoggerFactory.getLogger(JschLogger.class);
		static {
			if (logger instanceof LoggerImpl) {
				((LoggerImpl) logger).setFqcn(JschLogger.class.getName());
			}
		}
		
		@Override
		public boolean isEnabled(int level) {
			return true;
		}
		
		@Override
		public void log(int level, String message) {
			if (level == Logger.INFO) {
				logger.info(message);
			} else if (level == Logger.DEBUG) {
				logger.debug(message);
			} else if (level == Logger.WARN) {
				logger.warn(message);
			} else if (level == Logger.ERROR) {
				logger.error(message);
			} else if (level == Logger.FATAL) {
				logger.error(message);
			}
		}
	}
	
	/**
	 * sftp命令
	 */
	public static interface SFTPCommand {
		void exe(ChannelSftp channelSftp);
	}
	
}

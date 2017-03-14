package com.yiji.ypayment.common.utils;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * author Johnson
 */

public class AESEncrypt {
	private String sKey = "";
	private String ivParameter = "";
	private static AESEncrypt instance = null;
	
	private static final Logger logger = LoggerFactory.getLogger(AESEncrypt.class);
	
	public static AESEncrypt getInstance() {
		if (instance == null)
			instance = new AESEncrypt();
		return instance;
	}
	
	public String encrypt(String sSrc) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		return new BASE64Encoder().encode(encrypted);
	}
	
	public String decrypt(String sSrc) throws Exception {
		try {
			byte[] raw = sKey.getBytes("ASCII");
			
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static String getRandomString(int length) {
		String str = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);
			sf.append(str.charAt(number));
			
		}
		return sf.toString();
	}
	
	public void setsKey(String sKey) {
		this.sKey = sKey;
	}
	
	public void setIvParameter(String ivParameter) {
		this.ivParameter = ivParameter;
	}
	
	public static void main(String[] args) throws Exception {
		logger.info(AESEncrypt.getInstance().encrypt("faLTpEzMxWJESD5yuJ/VdA=="));
		long lStart = System.currentTimeMillis();
		String deString = AESEncrypt.getInstance().decrypt("cefmE31Q92HovUvafk8y1g==");
		long lUseTime = System.currentTimeMillis() - lStart;
		logger.info("DeString:{}, lUseTime:{}", deString, lUseTime);
	}
	
}

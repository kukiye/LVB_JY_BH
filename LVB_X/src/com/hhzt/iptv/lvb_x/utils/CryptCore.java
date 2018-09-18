package com.hhzt.iptv.lvb_x.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;

/**
 * 加密解密
 * 
 */
public class CryptCore {
	private static final SecretKey key = new SecretKeySpec(hexStringToBytes("c889b051c4c8983d"), "DES");

	/**
	 * 根据密匙进行DES加密
	 * 
	 * @param info
	 *            要加密的信息
	 * @return String 加密后的信息
	 */
	public static String encryptToDES(String info) {
		// 定义 加密算法,可用 DES,DESede,Blowfish
		String Algorithm = "DES";
		// 加密随机数生成器 (RNG),(可以不写)
		SecureRandom sr = new SecureRandom();
		// 定义要生成的密文
		byte[] cipherByte = null;
		try {
			// 得到加密/解密器
			Cipher c1 = Cipher.getInstance(Algorithm);
			// 用指定的密钥和模式初始化Cipher对象
			// 参数:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
			c1.init(Cipher.ENCRYPT_MODE, key, sr);
			// 对要加密的内容进行编码处理,
			cipherByte = c1.doFinal(info.getBytes());
		} catch (Exception e) {
			LogUtil.e("Exception=" + e);
		}
		// 返回密文的十六进制形式
		return bytesToHexString(cipherByte);
	}

	/**
	 * 根据密匙进行DES解密
	 * 
	 * @param sInfo
	 *            要解密的密文
	 * @return String 返回解密后信息
	 */
	public static String decryptByDES(String sInfo) {
		if (!"true".equalsIgnoreCase(UserMgr.isEncrypted())) {
			return sInfo;
		}
		// 定义 加密算法,
		String Algorithm = "DES";
		// 加密随机数生成器 (RNG)
		SecureRandom sr = new SecureRandom();
		byte[] cipherByte = null;
		try {
			// 得到加密/解密器
			Cipher c1 = Cipher.getInstance(Algorithm);
			// 用指定的密钥和模式初始化Cipher对象
			c1.init(Cipher.DECRYPT_MODE, key, sr);
			// 对要解密的内容进行编码处理
			cipherByte = c1.doFinal(hexStringToBytes(sInfo));
		} catch (Exception e) {
			LogUtil.e("Exception=" + e);
		}
		return null == cipherByte ? null : new String(cipherByte);
	}

	// 工具

	private static String bytesToHexString(byte[] bs) {
		StringBuffer sb = new StringBuffer();
		String hex = "";
		for (int i = 0; i < bs.length; i++) {
			hex = Integer.toHexString(bs[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	private static byte[] hexStringToBytes(String in) {
		byte[] arrB = in.getBytes();
		int iLen = arrB.length;
		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

}
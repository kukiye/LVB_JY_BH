/**
 * 作 者：Kobe
 * 日 期：20152015年5月8日下午6:11:40
 * 包 名：com.hhzt.iptv.lvb_x.mgr
 * 工程名：MediaDetector
 * 文件名：TokenMgr.java
 */
package com.hhzt.iptv.lvb_x.mgr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

public class TokenMgr {

	public static void writeTokenFile(String token) {
		File tokenFile = new File(UrlMgr.getTokenIDStoragePath());
		try {
			if (tokenFile.exists()) {
				tokenFile.delete();
			}
			tokenFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(tokenFile));
			bw.write(token);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readTokenFile() {
		File tokenFile = new File(UrlMgr.getTokenIDStoragePath());
		String token = null;
		if (!tokenFile.exists()) {
			return null;
		} else {
			try {
				BufferedReader br = new BufferedReader(new FileReader(tokenFile));
				String line = null;
				while ((line = br.readLine()) != null) {
					token = line;
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return token;
	}

	public static String getTokenId() {
		String tokenID = null;
		if (StringUtil.isEmpty(readTokenFile())) {
			tokenID = System.currentTimeMillis() + "";
			TokenMgr.writeTokenFile(tokenID);
		} else {
			tokenID = TokenMgr.readTokenFile();
		}
		return tokenID;
	}
}

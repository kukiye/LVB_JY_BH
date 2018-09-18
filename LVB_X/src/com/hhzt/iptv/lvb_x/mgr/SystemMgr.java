/**
 * 作者：   Johnson
 * 日期：   2014年6月11日下午3:27:06
 * 包名：    com.hhzt.iptv.lvb_x.mgr
 * 工程名：LVB_X
 * 文件名：SystemMgr.java
 */
package com.hhzt.iptv.lvb_x.mgr;

import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Vibrator;
import android.util.DisplayMetrics;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.config.CCVersionConfig;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.utils.CommonUtil;

public class SystemMgr {
	public static String getBaseHostUrl() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String baseUrl = sp.getString(Constant.IPTV_SYS_BASE_HOST_URL, null);
		LogUtil.d("wujichang---test----getBaseHostUrl----baseUrl=" + baseUrl);
		return baseUrl;
	}

	public static void setBaseHostUrl(String baseUrl) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		Editor editor = sp.edit();
		editor.putString(Constant.IPTV_SYS_BASE_HOST_URL, baseUrl);
		editor.commit();
	}

	public static String getBaseHostIp() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String baseIp = sp.getString(Constant.IPTV_SYS_BASE_HOST_IP, null);
		return baseIp;
	}

	public static void setBaseHostIp(String baseIp) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		Editor editor = sp.edit();
		editor.putString(Constant.IPTV_SYS_BASE_HOST_IP, baseIp);
		editor.commit();
	}

	public static void setSystemLangType(int langType) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		Editor editor = sp.edit();
		editor.putInt(Constant.IPTV_SYS_HOTEL_LANG_TYPE, langType);
		editor.commit();
	}

	public static int getSystemLangType() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		int langType = sp.getInt(Constant.IPTV_SYS_HOTEL_LANG_TYPE, Constant.IPTV_SYSTEM_LANG_SIMPLE_CHINESE);
		return langType;
	}

	public static void setWantedLangType(int langType) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		Editor editor = sp.edit();
		editor.putInt(Constant.IPTV_SYS_HOTEL_WANTED_LANG_TYPE, langType);
		editor.commit();
	}

	public static int getWantedLangType() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		int langType = sp.getInt(Constant.IPTV_SYS_HOTEL_WANTED_LANG_TYPE, 0);
		return langType;
	}

	public static void setSystemLangName(String langName) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		Editor editor = sp.edit();
		editor.putString(Constant.IPTV_SYS_HOTEL_LANG_NAME, langName);
		editor.commit();
	}

	public static String getSystemLangName() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String langName = sp.getString(Constant.IPTV_SYS_HOTEL_LANG_NAME, LVBXApp.getApp().getString(R.string.simple_chinese));
		return langName;
	}

	public static void changeLanguage(int type) {
		Resources resources = LVBXApp.getApp().getResources();// 获得res资源对象
		Configuration config = resources.getConfiguration();// 获得设置对象
		DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
		switch (type) {
		case Constant.IPTV_SYSTEM_LANG_SIMPLE_CHINESE:
			config.locale = Locale.SIMPLIFIED_CHINESE; // 简体中文
			break;
		case Constant.IPTV_SYSTEM_LANG_US_ENGLISH:
			config.locale = Locale.US; // 英文
			break;
		default:
			break;
		}

		resources.updateConfiguration(config, dm);
	}

	public static String getMsgServerIp() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String serverIp = sp.getString(Constant.IPTV_SYS_MSG_PUSHED_SERVER_IP, null);
		return serverIp;
	}

	public static void setMsgServerIp(String serverIp) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		Editor editor = sp.edit();
		editor.putString(Constant.IPTV_SYS_MSG_PUSHED_SERVER_IP, serverIp);
		editor.commit();
	}

	public static int getMsgServerPort() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		int serverPort = sp.getInt(Constant.IPTV_SYS_MSG_PUSHED_SERVER_PORT, 0);
		return serverPort;
	}

	public static void setMsgServerPort(int serverPort) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		Editor editor = sp.edit();
		editor.putInt(Constant.IPTV_SYS_MSG_PUSHED_SERVER_PORT, serverPort);
		editor.commit();
	}
	
	public static boolean getMediaLockTag() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_PRIVATE);
		boolean lockTag = sp.getBoolean(Constant.IPTV_SYS_MSG_MEDIA_LOCK_TAG, false);
		return lockTag;
	}

	public static void setMediaLockTag(boolean lockTag) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(Constant.IPTV_SYS_MSG_MEDIA_LOCK_TAG, lockTag);
		editor.commit();
	}

	/**
	 * 比较本地版本号和网络版本号
	 * 
	 * @param netVersion
	 * @return
	 */
	public static boolean hasNewVersion(int netVersion) {
		int localVersion = CommonUtil.getVersionCode();
		return netVersion > localVersion;
	}

	public static void setControllerVibrate() {
		Vibrator vibrator = (Vibrator) LVBXApp.getApp().getSystemService("vibrator");
		vibrator.vibrate(50);
	}

	public static String getSoftVersion() {
		return CCVersionConfig.VERSION_HEAD + "_" + "Ver" + CommonUtil.getVersionName() + "." + CommonUtil.getVersionCode() + "."
				+ CCVersionConfig.TO_CLIENT_NUMBER + "_" + CCVersionConfig.IPTV_VERSION_DATE + (CCVersionConfig.DEBUG_MODE ? "   Beta" : "");
	}
	
	/**
	 * 获取消息
	 * @param info
	 */
	public static String getMsgInfo() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String serverPort = sp.getString(Constant.IPTV_SYS_MSG_INFO_CONTENT, null);
		return serverPort;
	}

	/**
	 * 一般消息存储
	 * @param info
	 */
	public static void setMsgInfo(String info) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		Editor editor = sp.edit();
		editor.putString(Constant.IPTV_SYS_MSG_INFO_CONTENT, info);
		editor.commit();
	}
}

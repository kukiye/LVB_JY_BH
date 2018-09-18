/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 *
 * @author Johnson
 * 2013年11月25日 下午4:15:53
 */
package com.hhzt.iptv.lvb_x.mgr;

import java.lang.reflect.Method;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

public class WifiApMgr {
	private String mSSID = "";
	private String mPasswd = "";
	private boolean mSSIDHide = false;
	private Context mContext = null;
	private WifiManager mWifiManager = null;

	public WifiApMgr(Context context) {
		mContext = context;
		mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		closeWifiAp(mWifiManager);
	}

	public void closeWifiAp(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		closeWifiAp(wifiManager);
	}

	public void startWifiAp(String ssid, String passwd, boolean ssidHide) {
		mSSID = ssid;
		mPasswd = passwd;
		mSSIDHide = ssidHide;

		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}

		stratWifiAp();
	}

	private void stratWifiAp() {
		Method method1 = null;
		try {
			method1 = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
			WifiConfiguration netConfig = new WifiConfiguration();
			netConfig.allowedAuthAlgorithms.clear();
			netConfig.allowedGroupCiphers.clear();
			netConfig.allowedKeyManagement.clear();
			netConfig.allowedPairwiseCiphers.clear();
			netConfig.allowedProtocols.clear();

			netConfig.SSID = mSSID;
			netConfig.preSharedKey = mPasswd;

			netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			if (StringUtil.isEmpty(mPasswd)) {
				netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			} else {
				netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			}
			netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
			netConfig.hiddenSSID = mSSIDHide;

			method1.invoke(mWifiManager, netConfig, true);

		} catch (Exception e) {
			LogUtil.e("Exception=" + e);
		}
	}

	private void closeWifiAp(WifiManager wifiManager) {
		if (isWifiApEnabled(wifiManager)) {
			try {
				Method method = wifiManager.getClass().getMethod("getWifiApConfiguration");
				method.setAccessible(true);

				WifiConfiguration config = (WifiConfiguration) method.invoke(wifiManager);

				Method method2 = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
				method2.invoke(wifiManager, config, false);
			} catch (Exception e) {
				LogUtil.e("Exception=" + e);
			}
		}
	}

	private boolean isWifiApEnabled(WifiManager wifiManager) {
		try {
			Method method = wifiManager.getClass().getMethod("isWifiApEnabled");
			method.setAccessible(true);
			boolean tag = (Boolean) method.invoke(wifiManager);
			return tag;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			LogUtil.e("Exception=" + e);
		}
		return false;
	}

	public static boolean checkWifiApIsEnable() {
		WifiManager wifiManager = (WifiManager) LVBXApp.getApp().getSystemService(Context.WIFI_SERVICE);
		try {
			Method method = wifiManager.getClass().getMethod("isWifiApEnabled");
			method.setAccessible(true);
			boolean tag = (Boolean) method.invoke(wifiManager);
			return tag;
		} catch (NoSuchMethodException e) {
			LogUtil.e("Exception=" + e);
		} catch (Exception e) {
			LogUtil.e("Exception=" + e);
		}

		return false;
	}
}

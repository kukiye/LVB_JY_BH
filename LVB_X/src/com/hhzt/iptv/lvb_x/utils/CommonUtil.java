/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 * @author Jhonson
 * 2013-8-16 下午3:22:41
 */
package com.hhzt.iptv.lvb_x.utils;

import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Environment;
import android.os.StatFs;
import android.view.WindowManager;

import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.log.LogUtil;

public class CommonUtil {

	/**
	 * 检测sdcard是否可用
	 * 
	 * @return true为可用，否则为不可用
	 */
	public static boolean sdCardIsAvailable() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return true;
	}

	/**
	 * Checks if there is enough Space on SDCard
	 * 
	 * @param updateSize
	 *            Size to Check
	 * @return True if the Update will fit on SDCard, false if not enough space
	 *         on SDCard Will also return false, if the SDCard is not mounted as
	 *         read/write
	 */
	public static boolean enoughSpaceOnSdCard(long updateSize) {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return (updateSize < getRealSizeOnSdcard());
	}

	/**
	 * get the space is left over on sdcard
	 * 
	 * @return
	 */
	public static long getRealSizeOnSdcard() {
		File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * Checks if there is enough Space on phone self
	 * 
	 * @param updateSize
	 * @return
	 */
	public static boolean enoughSpaceOnPhone(long updateSize) {
		return getRealSizeOnPhone() > updateSize;
	}

	/**
	 * get the space is left over on phone self
	 * 
	 * @return
	 */
	public static long getRealSizeOnPhone() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		long realSize = blockSize * availableBlocks;
		return realSize;
	}

	/**
	 * 根据手机分辨率从dp转成px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f) - 15;
	}

	/**
	 * 保持屏幕长亮
	 * 
	 * @param activity
	 */
	public static void keepScreenOnAlways(Activity activity) {
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	public static String getRootFilePath() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath:
																				// /data/data/
		}
	}

	public static String getLocalMacAddress(Context mc) {
		String defmac = "00:00:00:00:00:00";
		InputStream input = null;
		String wifimac = getWifiMacAddress(mc);
		if (null != wifimac) {
			if (!wifimac.equals(defmac))
				return wifimac;
		}
		try {

			ProcessBuilder builder = new ProcessBuilder("busybox", "ifconfig");
			Process process = builder.start();
			input = process.getInputStream();

			byte[] b = new byte[1024];
			StringBuffer buffer = new StringBuffer();
			while (input.read(b) > 0) {
				buffer.append(new String(b));
			}
			String value = buffer.substring(0);
			String systemFlag = "HWaddr ";
			int index = value.indexOf(systemFlag);
			// List <String> address = new ArrayList <String> ();
			if (0 < index) {
				value = buffer.substring(index + systemFlag.length());
				// address.add(value.substring(0,18));
				defmac = value.substring(0, 17);
			}
		} catch (Exception e) {
			LogUtil.e("Exception=" + e);
		}
		return defmac;
	}

	public static String getWifiMacAddress(Context mc) {
		WifiManager wifi = (WifiManager) mc.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	public static boolean openWifiBrocast(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		MulticastLock multicastLock = wifiManager.createMulticastLock("MediaServer");
		if (multicastLock != null) {
			multicastLock.acquire();
			return true;
		}
		return false;
	}

	public static boolean getWifiState(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (null != networkInfo) {
			State wifistate = networkInfo.getState();
			return State.CONNECTED == wifistate;
		}
		return false;

	}

	public static boolean getMobileState(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (null != networkInfo) {
			State mobileState = networkInfo.getState();
			return State.CONNECTED == mobileState;
		}

		return false;
	}

	public static boolean getEthernetState(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);

		if (null != networkInfo) {
			State mobileState = networkInfo.getState();
			return State.CONNECTED == mobileState;
		}

		return false;
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static int getVersionCode() {
		PackageManager pm = LVBXApp.getApp().getPackageManager();
		PackageInfo pinfo = null;
		int versionCode = 0;
		try {
			pinfo = pm.getPackageInfo(LVBXApp.getApp().getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (pinfo != null) {
			versionCode = pinfo.versionCode;
		}

		return versionCode;
	}

	public static String getVersionName() {
		PackageManager pm = LVBXApp.getApp().getPackageManager();
		PackageInfo pinfo = null;
		String versionName = null;
		try {
			pinfo = pm.getPackageInfo(LVBXApp.getApp().getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (pinfo != null) {
			versionName = pinfo.versionName;
		}

		return versionName;
	}

	/**
	 * 获取本地ip地址
	 * 
	 * @return
	 */
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
					.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
						.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取堆栈顶部Activity名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getTopActivity(Application context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
		if (runningTaskInfos != null) {
			return runningTaskInfos.get(0).topActivity.getClassName();
		} else {
			return null;
		}
	}

	/**
	 * 获取本地子网掩码
	 * 
	 * @return
	 */
	public static String getLocalMaskAddress() {
		String maskAddress = "";
		InputStream input = null;
		try {
			ProcessBuilder builder = new ProcessBuilder("busybox", "ifconfig");
			Process process = builder.start();
			input = process.getInputStream();
			byte[] b = new byte[1024];
			StringBuffer buffer = new StringBuffer();
			while (input.read(b) > 0) {
				buffer.append(new String(b));
			}
			String value = buffer.substring(0);
			String systemFlag = "Mask:";
			int index = value.indexOf(systemFlag);
			if (0 < index) {
				value = buffer.substring(index + systemFlag.length());
				maskAddress = value.substring(0, value.indexOf("\n"));
			}
		} catch (Exception e) {
			LogUtil.e("Exception=" + e);
		}
		return maskAddress;
	}

	public static String getLocalMaskAddress(Context ct) {
		// 判断当前网络是WiFi，还是局域网
		if (getWifiState(ct)) {
			WifiManager wm = (WifiManager) ct.getSystemService(Context.WIFI_SERVICE);
			DhcpInfo di = wm.getDhcpInfo();
			long getewayIpL = di.gateway;
			String getwayIpS = long2ip(getewayIpL);// 网关地址
			long netmaskIpL = di.netmask;
			String netmaskIpS = long2ip(netmaskIpL);// 子网掩码地址
			return netmaskIpS;
		} else {
			return getLocalIpAddress();
		}
	}

	public static String long2ip(long ip) {
		StringBuffer sb = new StringBuffer();
		sb.append(String.valueOf((int) (ip & 0xff)));
		sb.append('.');
		sb.append(String.valueOf((int) ((ip >> 8) & 0xff)));
		sb.append('.');
		sb.append(String.valueOf((int) ((ip >> 16) & 0xff)));
		sb.append('.');
		sb.append(String.valueOf((int) ((ip >> 24) & 0xff)));
		return sb.toString();
	}

	/**
	 * 通过IP和子网掩码获取网段
	 * 
	 * @param selfip
	 *            ---IP地址
	 * @param selfmask
	 *            ---子网掩码
	 * @return
	 */
	public static String getNetworkAddrPrefix(String selfip, String selfmask) {
		try {
			if (selfip != null && selfip.split("\\.").length == 4 || selfmask != null
					&& selfip.split("\\.").length == 4 && maskvaliditycheck(selfmask)) {
				String[] ipArr = selfip.split("\\.");
				String[] maskArr = selfmask.split("\\.");
				Integer ip1 = Integer.valueOf(ipArr[0]) & Integer.valueOf(maskArr[0]);
				Integer ip2 = Integer.valueOf(ipArr[1]) & Integer.valueOf(maskArr[1]);
				Integer ip3 = Integer.valueOf(ipArr[2]) & Integer.valueOf(maskArr[2]);
				return ip1 + "." + ip2 + "." + ip3;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断子网掩码的合法性
	 * 
	 * @param mask
	 *            ---子网掩码
	 * @return
	 */
	public static boolean maskvaliditycheck(String mask) {
		String[] masks = mask.split("\\.");
		String binaryVal = "";
		for (int i = 0; i < masks.length; i++) {
			String binaryStr = Integer.toBinaryString(Integer.parseInt(masks[i]));
			Integer times = 8 - binaryStr.length();
			for (int j = 0; j < times; j++) {
				binaryStr = "0" + binaryStr;
			}
			binaryVal += binaryStr;
		}
		String regx = "^[1]*[0]*$";
		return binaryVal.matches(regx);
	}
}

package com.hhzt.iptv.lvb_x.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.ServerSocket;

import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;

public class DeviceUtil {
	public static String getLocalWifiMacAddress() {
		// 在wifi未开启状态下，仍然可以获取MAC地址，但是IP地址必须在已连接状态下否则为0
		String macAddress = null;
		WifiManager wifiMgr = (WifiManager) LVBXApp.getApp().getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		if (null != info) {
			macAddress = info.getMacAddress();
		}

		return macAddress;
	}

	/**
	 * 使用WIFI
	 * 
	 * @return
	 */
	public static String getLocalWifiIpAdress() {
		int ipAddress = 0;
		WifiManager wifiMgr = (WifiManager) LVBXApp.getApp().getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		if (null != info) {
			ipAddress = info.getIpAddress();
		}
		String ip = intToIp(ipAddress);
		return ip;
	}

	/**
	 * 获取空闲的可用端口
	 * 
	 * @return
	 */
	public static int getUseablePort() {
		ServerSocket serverSocket;
		int port = 0;
		try {
			serverSocket = new ServerSocket(0);
			port = serverSocket.getLocalPort();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return port;
	}

	public static String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
	}

	public static boolean isSDCardExist() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		return sdCardExist;
	}

	public static String getSDPath() {
		String sdDirString = null;
		if (isSDCardExist()) {
			sdDirString = Environment.getExternalStorageDirectory().getPath();
		}

		return sdDirString;
	}

	public static String getMac(int macType) {
		String macSerial = null;
		String str = "";
		try {
			Process pp = null;
			if (macType == Constant.WIFI_MAC) {
				pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
			} else if (macType == Constant.ETHERNET_MAC) {
				pp = Runtime.getRuntime().exec("cat /sys/class/net/eth0/address");
			} else {
				pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
			}

			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();
					break;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return macSerial;
	}

	public static String getIptvMacString() {
		switch (Config.LvbDeviceType) {
		case Constant.DEVICE_TYPE_BOX:
		case Constant.DEVICE_TYPE_BOX_HSJQ:
			return DeviceUtil.getMac(Constant.ETHERNET_MAC);
		case Constant.DEVICE_TYPE_MOBILE:
		case Constant.DEVICE_TYPE_MOBILE_HSJQ:
			return DeviceUtil.getMac(Constant.WIFI_MAC);
		default:
			return null;
		}
	}

	public static String getCustomSystemLang() {
		String lang = SystemMgr.getSystemLangName();
		if (LVBXApp.getApp().getString(R.string.simple_chinese).equalsIgnoreCase(lang)) {
			return "zh_CN";
		} else if (LVBXApp.getApp().getString(R.string.english).equalsIgnoreCase(lang)) {
			return "en_US";
		} else {
			return "zh_CN";
		}
	}
	
	public static void setAudioMusic(Context context, int tempVolume){
		//音量控制,初始化定义    
		AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);    
		//最大音量    
		int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);    
		//当前音量    
		int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);  
		LogUtil.d("setAudioMusic----maxVolume=" + maxVolume + " currentVolume=" + currentVolume);
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, tempVolume, 0); //tempVolume:音量绝对值    
		
	}
}

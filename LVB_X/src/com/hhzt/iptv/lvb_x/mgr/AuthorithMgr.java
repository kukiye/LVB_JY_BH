package com.hhzt.iptv.lvb_x.mgr;

import java.io.DataOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Locale;

import android.os.Build;
import android.util.Base64;

import com.google.gson.Gson;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.config.CCVersionConfig;
import com.hhzt.iptv.lvb_x.floatwindows.FloatWindowManager;
import com.hhzt.iptv.lvb_x.interfaces.IResponseable;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.model.AuthorithBean;
import com.hhzt.iptv.lvb_x.net.LVBHttpUtils;
import com.hhzt.iptv.lvb_x.utils.CommonUtil;
import com.hhzt.iptv.lvb_x.utils.DeviceUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

public class AuthorithMgr {
	private static AuthorithMgr authorithMgr = new AuthorithMgr();
	// 数字签名，密钥算法
	public static final String KEY_ALGORITHM = "RSA";

	// 签名/验证算法
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	private AuthorithMgr() {

	}

	public static AuthorithMgr getInstance() {
		if (null == authorithMgr) {
			synchronized (AuthorithMgr.class) {
				if (null == authorithMgr) {
					authorithMgr = new AuthorithMgr();
				}
			}
		}

		return authorithMgr;
	}

	/**
	 * 通过root权限执行指令
	 * 
	 * @param cmd
	 * @return
	 */
	public boolean RootCmd(String cmd) {
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			LogUtil.e("Exception=" + e);
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
				LogUtil.e("Exception=" + e);
			}
		}
		return true;
	}

	public void changeMode(String filePath) {
		String[] pathSplit = filePath.split("/");
		try {
			Runtime.getRuntime().exec("su");
			String path = "";
			for (int i = 1; i < pathSplit.length; i++) {
				path += ("/" + pathSplit[i]);
				Runtime.getRuntime().exec("chmod 777 " + path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过mac地址检测设备是否合法
	 */
	public void checkMacIsValide() {
		String eth0Mac = DeviceUtil.getMac(Constant.ETHERNET_MAC).toLowerCase(Locale.ENGLISH);
		String tagMac = Constant.IPTV_AUTHORITHED_MAC.toLowerCase(Locale.ENGLISH);
		if (StringUtil.isEmpty(tagMac)) {
			LogUtil.d("All device uthorized successful!");
		} else if (!StringUtil.isEmpty(eth0Mac) && eth0Mac.startsWith(tagMac)) {
			LogUtil.d("This device uthorized successful! eth0Mac=" + eth0Mac);
		} else {
			LogUtil.d("This device uthorized failed! eth0Mac=" + eth0Mac);
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	/**
	 * 通过检测license文件判断设备是否授权
	 */
	public void checkLicenseIsValide() {
		String content = UserMgr.getDeviceLicensecontent();
		// 从未授权
		if (StringUtil.isEmpty(content)) {
			getClientServerAddress();
		} else {
			String deviceInfoId = getDeviceTagId();
			// 授权通过
			if (verify(deviceInfoId, content)) {
				// 授权通过， nothing to do
			} else {
				// 清理license文件
				UserMgr.setDeviceLicensecontent(null);
				getClientServerAddress();
			}
		}
	}

	/**
	 * 获取客户license服务器地址
	 */
	private void getClientServerAddress() {
		// 获取license服务器地址，然后进行访问
		LVBHttpUtils.get(UrlMgr.getLicenseServerAddressUrl(), new IResponseable() {

			@Override
			public void onSuccess(String result) {
				Gson gson = new Gson();
				AuthorithBean bean = gson.fromJson(result, AuthorithBean.class);
				if (null != bean && bean.getResult()) {
					// 使用纯硬件信息不带license去服务器验证
					verifyDeviceInfoNoLicenseToLicenseServer(bean.getDesc());
				} else {
					// 获取服务器地址失败
					FloatWindowManager.getInstance().createInvalideWindow(LVBXApp.getApp());
					if (null != bean) {
						FloatWindowManager.getInstance().setInvalidWindowShowContent(bean.getDesc());
					}
				}
			}

			@Override
			public void onFailed(String result) {
				// 服务器地址访问失败，无法授权激活则无法使用
				FloatWindowManager.getInstance().createInvalideWindow(LVBXApp.getApp());
				FloatWindowManager.getInstance().setInvalidWindowShowContent(LVBXApp.getApp().getString(R.string.get_license_server_failed));
			}
		});
	}

	/**
	 * 使用纯硬件信息不带license去服务器验证
	 * 
	 * @param licenseServer
	 */
	private void verifyDeviceInfoNoLicenseToLicenseServer(final String licenseServerAddress) {
		String hardwareinfo = getDeviceTagId();
		String systeminfo = getSystemInfo();
		String appinfo = getAppInfo();
		String url = UrlMgr.getLicenseNoDeviceInfoUrl(licenseServerAddress, hardwareinfo, systeminfo, appinfo);
		LVBHttpUtils.get(url, new IResponseable() {

			@Override
			public void onSuccess(String result) {
				Gson gson = new Gson();
				AuthorithBean bean = gson.fromJson(result, AuthorithBean.class);
				if (null != bean && bean.getResult()) {
					UserMgr.setDeviceLicensecontent(bean.getDesc());
				} else {
					// License服务器校验失败，从客户服务器获取授权码
					getLicenseCodeFromClientServer(licenseServerAddress);
				}
			}

			@Override
			public void onFailed(String result) {
				UserMgr.setDeviceLicensecontent(null);

				// License服务器校验失败，从客户服务器获取授权码
				getLicenseCodeFromClientServer(licenseServerAddress);
			}
		});
	}

	/**
	 * License服务器校验失败，从客户服务器获取授权码
	 */
	private void getLicenseCodeFromClientServer(final String licenseServerAddress) {
		String url = UrlMgr.getLicenseCodeUrl();
		LVBHttpUtils.get(url, new IResponseable() {

			@Override
			public void onSuccess(String result) {
				// 后台需要优化，需要以数据格式的形式下发客户授权码
				Gson gson = new Gson();
				AuthorithBean bean = gson.fromJson(result, AuthorithBean.class);
				if (null != bean && bean.getResult()) {
					activeWithDeviceInfoAndLicenseCode(licenseServerAddress, getDeviceTagId(), bean.getDesc());
				} else {
					// 从客户服务器获取授权码失败
					FloatWindowManager.getInstance().createInvalideWindow(LVBXApp.getApp());
					if (null != bean) {
						FloatWindowManager.getInstance().setInvalidWindowShowContent(bean.getDesc());
					}
				}
			}

			@Override
			public void onFailed(String result) {
				// 从客户服务器获取授权码失败
				FloatWindowManager.getInstance().createInvalideWindow(LVBXApp.getApp());
				FloatWindowManager.getInstance().setInvalidWindowShowContent(LVBXApp.getApp().getString(R.string.get_client_license_code_failed));
			}
		});
	}

	/**
	 * 使用客户授权码并带上硬件信息地址进行验证
	 * 
	 * @param deviceinfo
	 * @param lisenceCode
	 */
	private void activeWithDeviceInfoAndLicenseCode(String licenseServer, String deviceinfo, String lisenceCode) {
		String systeminfo = getSystemInfo();
		String appinfo = getAppInfo();
		String url = UrlMgr.getActiveWithLicenseAndDeviceInfo(licenseServer, deviceinfo, systeminfo, appinfo, lisenceCode);
		LVBHttpUtils.get(url, new IResponseable() {

			@Override
			public void onSuccess(String result) {
				Gson gson = new Gson();
				AuthorithBean bean = gson.fromJson(result, AuthorithBean.class);
				if (null != bean && bean.getResult()) {
					// 首次授权失败,保存license到本地
					UserMgr.setDeviceLicensecontent(bean.getDesc());
				} else {
					// 首次授权，获取license失败
					FloatWindowManager.getInstance().createInvalideWindow(LVBXApp.getApp());
					if (null != bean) {
						FloatWindowManager.getInstance().setInvalidWindowShowContent(bean.getDesc());
					}
				}
			}

			@Override
			public void onFailed(String result) {
				// 首次授权失败，进行提示，并将界面封掉，使得设备不能使用
				FloatWindowManager.getInstance().createInvalideWindow(LVBXApp.getApp());
				FloatWindowManager.getInstance().setInvalidWindowShowContent(LVBXApp.getApp().getString(R.string.license_failed));
			}
		});
	}

	/**
	 * 获取设备唯一标识码:mac地址+cpu序列號
	 * 
	 * @return
	 */
	private String getDeviceTagId() {
		String mac = "MAC:" + DeviceUtil.getIptvMacString();
		String model = ",MODEL:" + Build.MODEL;
		String cpu = ",CPU_ABI:" + Build.CPU_ABI;
		// 防止标识码中存在特殊字符,导致url防止时出现异常
		return (mac + model + cpu).trim().replaceAll(" ", "").replace("(", "").replace(")", "");
	}

	/**
	 * 獲取系統信息
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String getSystemInfo() {
		String phoneInfo = "Product:" + android.os.Build.PRODUCT;
		phoneInfo += ",CPU_ABI:" + android.os.Build.CPU_ABI;
		phoneInfo += ",TAGS:" + android.os.Build.TAGS;
		phoneInfo += ",VERSION_CODES.BASE:" + android.os.Build.VERSION_CODES.BASE;
		phoneInfo += ",MODEL:" + android.os.Build.MODEL;
		phoneInfo += ",SDK:" + android.os.Build.VERSION.SDK;
		phoneInfo += ",VERSION.RELEASE:" + android.os.Build.VERSION.RELEASE;
		phoneInfo += ",DEVICE:" + android.os.Build.DEVICE;
		phoneInfo += ",DISPLAY:" + android.os.Build.DISPLAY;
		phoneInfo += ",BRAND:" + android.os.Build.BRAND;
		phoneInfo += ",BOARD:" + android.os.Build.BOARD;
		phoneInfo += ",FINGERPRINT:" + android.os.Build.FINGERPRINT;
		phoneInfo += ",ID:" + android.os.Build.ID;
		phoneInfo += ",MANUFACTURER:" + android.os.Build.MANUFACTURER;
		phoneInfo += ",USER:" + android.os.Build.USER;
		return phoneInfo.trim().replaceAll(" ", "");
	}

	/**
	 * 獲取app軟件信息
	 * 
	 * @return
	 */
	private String getAppInfo() {
		return "appName:LVB_X_V_" + CommonUtil.getVersionName() + "." + CommonUtil.getVersionCode() + "_" + CCVersionConfig.IPTV_VERSION_DATE
				+ (CCVersionConfig.DEBUG_MODE ? "_Beta" : "").trim().replaceAll(" ", "");
	}

	/**
	 * 校验license
	 * */
	private boolean verify(String data, String content) {
		boolean result = false;
		// 防止空指针异常
		if (StringUtil.isEmpty(data) || StringUtil.isEmpty(content)) {
			return result;
		}

		String res[] = content.split(",");
		if (null != res && res.length == 2) {
			try {
				// 转换公钥材料
				// 实例化密钥工厂
				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
				// 初始化公钥
				// 密钥材料转换
				X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decode(res[0], Base64.DEFAULT));
				// 产生公钥
				PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
				// 实例化Signature
				Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
				// 初始化Signature
				signature.initVerify(pubKey);
				// 更新
				signature.update(data.getBytes());
				// 验证
				result = signature.verify(Base64.decode(res[1], Base64.DEFAULT));
			} catch (Exception e) {
				LogUtil.e("Exception=" + e);
			}
		}
		return result;
	}
}

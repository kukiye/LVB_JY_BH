/**
 * 作者：   Johnson
 * 日期：   2014年6月24日上午10:21:08
 * 包名：    com.hhzt.iptv.lvb_x.business
 * 工程名：LVB_X
 * 文件名：Loginer.java
 */
package com.hhzt.iptv.lvb_x.business;

import android.app.Activity;
import android.widget.Toast;
import com.google.gson.Gson;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.commonui.LoginExceptionDialog;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.interfaces.INetworkSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IOnPairedSuccessable;
import com.hhzt.iptv.lvb_x.interfaces.IResponseable;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.mgr.ThreadPoolManager;
import com.hhzt.iptv.lvb_x.mgr.TokenMgr;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.AccoutInfoModel;
import com.hhzt.iptv.lvb_x.model.LoginInfoModel;
import com.hhzt.iptv.lvb_x.model.PublicServiceInfoModel;
import com.hhzt.iptv.lvb_x.net.LVBHttpUtils;
import com.hhzt.iptv.lvb_x.utils.CommonUtil;
import com.hhzt.iptv.lvb_x.utils.DeviceUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.hhzt.iptv.ui.SplashActivity;
import com.hhzt.iptv.ui.WelcomeActivity;

public class Loginer {
	private int mTimerRecorderCount = 0;
	private static Loginer mLoginer = new Loginer();
	private int mCurrentIPTag = 0;
	private static String[] ipAddressArray = { ".254", ".253", ".252", ".251", ".250" };

	public static Loginer getLoginer() {
		if (null == mLoginer) {
			mLoginer = new Loginer();
		}

		return mLoginer;
	}

	private Loginer() {

	}

	/**
	 * 用户登录
	 */
	public void loginAction(final int languageFlag, final int screenTag, final String userName, final String passWd, final String serverIp,
			final String token, final Activity currentActivity, final Class<?> destActivityCls) {
		if (!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(passWd) && !StringUtil.isEmpty(serverIp)) {
			String url = UrlMgr.getLoginInfoUrl(userName, passWd, serverIp, CommonUtil.getVersionName(), token);
			LVBHttpUtils.get(url, new IResponseable() {

				@Override
				public void onSuccess(String result) {
					Gson gson = new Gson();
					LoginInfoModel loginModel = gson.fromJson(result, LoginInfoModel.class);

					if (null != loginModel && "200".equalsIgnoreCase(loginModel.getCode())) {
						mTimerRecorderCount = 0;
						UserMgr.setUserName(userName);
						UserMgr.setAcountPasswd(passWd);
						UserMgr.setServerIp(serverIp);
						UserMgr.setChannelGroup(loginModel.getChannelgroup());
						UserMgr.setInteracPassword(loginModel.getInteracpassword());
						UserMgr.setSSIDHideTag(loginModel.getSSIDhide());
						UserMgr.setEncrypted(loginModel.getIsencrypted());
						UserMgr.setHotelRoomNo(StringUtil.isEmpty(loginModel.getRoomno()) ? loginModel.getRoomname() : loginModel.getRoomno());
						UserMgr.setWifiName(loginModel.getWifiname());
						UserMgr.setHotSpotEnable(loginModel.getHotspot());
						UserMgr.setVodFreeEnable(loginModel.getVodfree());
						UserMgr.setVodTypeLeve(loginModel.getLevel());
						UserMgr.setEpgGroupId(loginModel.getEpgGroupid());

						// 开启消息推送接收的服务
						UIDataller.getDataller().openMsgPushService();
						// 初始化配置信息,配置成功后才进入系统
						UIDataller.getDataller().initViewTempConfigInfo(currentActivity, destActivityCls, languageFlag);






					} else {
						if (screenTag == Constant.HOTEL_SPLASH_SCREEN) {
							switch (Config.Auto_ACCOUNT_TAG) {
							case 1:
								gotoSetAutoAccount(loginModel, currentActivity);
								break;
							default:
								gotoReconnection(loginModel, currentActivity, languageFlag, screenTag, userName, passWd, serverIp, token,
										destActivityCls);
								break;
							}
						} else {
							BaseActivity.getInstance().showToast(loginModel.getMessage(), Toast.LENGTH_LONG);
						}
					}
				}

				@Override
				public void onFailed(String result) {
					if (screenTag == Constant.HOTEL_SPLASH_SCREEN) {
						switch (Config.Auto_ACCOUNT_TAG) {
						case 1:
							setServiceInfo(currentActivity, true);
							break;
						default:
							gotoReconnection(null, currentActivity, languageFlag, screenTag, userName, passWd, serverIp, token, destActivityCls);
							break;
						}
					} else {
						BaseActivity.getInstance().showToast(result, Toast.LENGTH_LONG);
					}
				}
			});
		} else {
			switch (Config.Auto_ACCOUNT_TAG) {
			case 1:
				setServiceInfo(currentActivity, true);
				break;
			default:
				gotoAccountActivity(userName, passWd, serverIp, currentActivity, screenTag);
				break;
			}
		}
	}

	private void gotoAccountActivity(String userName, String passWd, String serverIp, Activity currentActivity, int screenTag) {
		if (StringUtil.isEmpty(userName)) {
			if (screenTag == Constant.HOTEL_SPLASH_SCREEN) {
				ActivitySwitchMgr.gotoAccountActivity(currentActivity, currentActivity.getString(R.string.login_screen));
				currentActivity.finish();
			} else {
				BaseActivity.getInstance().showToast(LVBXApp.getApp().getString(R.string.account_input), Toast.LENGTH_LONG);
			}
		} else if (StringUtil.isEmpty(passWd)) {
			if (screenTag == Constant.HOTEL_SPLASH_SCREEN) {
				ActivitySwitchMgr.gotoAccountActivity(currentActivity, currentActivity.getString(R.string.login_screen));
				currentActivity.finish();
			} else {
				BaseActivity.getInstance().showToast(LVBXApp.getApp().getString(R.string.passwd_input), Toast.LENGTH_LONG);
			}
		} else if (StringUtil.isEmpty(serverIp)) {
			if (screenTag == Constant.HOTEL_SPLASH_SCREEN) {
				ActivitySwitchMgr.gotoAccountActivity(currentActivity, currentActivity.getString(R.string.login_screen));
				currentActivity.finish();
			} else {
				BaseActivity.getInstance().showToast(LVBXApp.getApp().getString(R.string.ipAdd_input), Toast.LENGTH_LONG);
			}
		}
	}

	private void gotoSetAutoAccount(LoginInfoModel loginModel, Activity currentActivity) {
		if (!StringUtil.isEmpty(loginModel.getCode())) {
			mTimerRecorderCount = 0;
			if ("606".equalsIgnoreCase(loginModel.getCode())) {
				/* mac地址\token不匹配 ---手动登陆 */
				BaseActivity.getInstance().showToast("702:" + loginModel.getMessage(), Toast.LENGTH_LONG);
			} else if ("607".equalsIgnoreCase(loginModel.getCode())) {
				/* IP地址不匹配 */
				BaseActivity.getInstance().showToast("703:" + loginModel.getMessage(), Toast.LENGTH_SHORT);
				// setIpInfo(currentActivity);
			} else if ("601".equalsIgnoreCase(loginModel.getCode())) {
				setServiceInfo(currentActivity, true);
				return;
			} else {
				BaseActivity.getInstance().showToast("704:" + loginModel.getMessage(), Toast.LENGTH_LONG);
			}
			new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
		} else {
			mTimerRecorderCount = 0;
			BaseActivity.getInstance().showToast("705:" + loginModel.getMessage(), Toast.LENGTH_LONG);
			new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
		}
	}

	private void gotoReconnection(LoginInfoModel loginModel, final Activity currentActivity, final int languageFlag, final int screenTag,
			final String userName, final String passWd, final String serverIp, final String token, final Class<?> destActivityCls) {
		if (null != loginModel && !StringUtil.isEmpty(loginModel.getCode())) {
			// 登陆失败转至设置模块的账号输入界面
			mTimerRecorderCount = 0;
			ActivitySwitchMgr.gotoAccountActivity(currentActivity, currentActivity.getString(R.string.login_screen));
			currentActivity.finish();
			BaseActivity.getInstance().showToast(loginModel.getMessage(), Toast.LENGTH_LONG);
		} else if (mTimerRecorderCount <= 60) {// 登陆失败进行递归调用
			if (null != SplashActivity.getIntance() && SplashActivity.getIntance().mIsInSplashTag) {
				ThreadPoolManager.getInstance().addTask(new Runnable() {

					@Override
					public void run() {
						try {
							if (null != currentActivity && !currentActivity.isFinishing()) {
								Thread.sleep(2000);
								mTimerRecorderCount++;
								loginAction(languageFlag, screenTag, userName, passWd, serverIp, token, currentActivity, destActivityCls);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
			} else {
				mTimerRecorderCount = 0;
			}
		} else {
			// 登陆失败转至设置模块的账号输入界面
			mTimerRecorderCount = 0;
			ActivitySwitchMgr.gotoAccountActivity(currentActivity, currentActivity.getString(R.string.login_screen));
			currentActivity.finish();
			if(loginModel != null){
				BaseActivity.getInstance().showToast(loginModel.getMessage(), Toast.LENGTH_LONG);
			}
		}
	}

	/**
	 * 获取网段
	 * 
	 * @return
	 */
	private String getPublicServerAddrPrefix() {
		String localIpAddress = CommonUtil.getLocalIpAddress();
		String maskAddress = CommonUtil.getLocalMaskAddress(BaseActivity.getInstance());
		LogUtil.d("getPublicServerAddrPrefix-----maskAddress=" + maskAddress);
		return CommonUtil.getNetworkAddrPrefix(localIpAddress, maskAddress);
	}

	/**
	 * 通过本地服务器获取账号、密码、IP、TOKEN等信息
	 * 
	 * @param serverIp
	 * @param currentActivity
	 */
	public void setAccoutInfo(final String serverAddress, final Activity currentActivity) {
		String macAddress = DeviceUtil.getIptvMacString().toUpperCase();
		String url = UrlMgr.getAccoutInfoUrl(serverAddress, macAddress);
		LVBHttpUtils.get(url, new IResponseable() {

			public void onSuccess(String result) {

				Gson gson = new Gson();
				AccoutInfoModel infoModel = gson.fromJson(result, AccoutInfoModel.class);
				if (null != infoModel) {
					if (infoModel.isResult()) {
						if (StringUtil.isEmpty(infoModel.getIptvusername()) || StringUtil.isEmpty(infoModel.getPassword())) {
							BaseActivity.getInstance().showToast("709-1", Toast.LENGTH_SHORT);
							new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
						} else {
							int languageFlag = SystemMgr.getWantedLangType();
							String saveUserName = UserMgr.getUserName();
							String savePassWord = UserMgr.getAcountPasswd();
							if (StringUtil.isEmpty(saveUserName) || StringUtil.isEmpty(savePassWord)) {
								UserMgr.setUserName(infoModel.getIptvusername());
								UserMgr.setAcountPasswd(infoModel.getPassword());
								loginAction(languageFlag, Constant.HOTEL_SPLASH_SCREEN, infoModel.getIptvusername(), infoModel.getPassword(),
										serverAddress, TokenMgr.getTokenId(), currentActivity, WelcomeActivity.class);
							} else {
								if (infoModel.getIptvusername().equalsIgnoreCase(saveUserName)
										&& infoModel.getPassword().equalsIgnoreCase(savePassWord)) {
									BaseActivity.getInstance().showToast("710:" + infoModel.getDesc(), Toast.LENGTH_LONG);
									new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
								} else {
									UserMgr.setUserName(infoModel.getIptvusername());
									UserMgr.setAcountPasswd(infoModel.getPassword());
									UserMgr.setServerIp(serverAddress);
									SystemMgr.setBaseHostIp(serverAddress);
									SystemMgr.setBaseHostUrl("http://" + serverAddress + "/epg/json/");
									loginAction(languageFlag, Constant.HOTEL_SPLASH_SCREEN, infoModel.getIptvusername(), infoModel.getPassword(),
											serverAddress, TokenMgr.getTokenId(), currentActivity, WelcomeActivity.class);
								}
							}
						}
					} else {
						BaseActivity.getInstance().showToast("709-2:" + infoModel.getDesc(), Toast.LENGTH_LONG);
						new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
					}
				} else {
					BaseActivity.getInstance().showToast("709-3", Toast.LENGTH_LONG);
					new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
				}
			}

			@Override
			public void onFailed(String result) {
				BaseActivity.getInstance().showToast("711", Toast.LENGTH_LONG);
				new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
			}
		});
	}

	/**
	 * 通过公网获取本地服务器地址
	 * 
	 * @param currentActivity
	 */
	public void setServiceInfo(final Activity currentActivity, final boolean tag) {
		String serviceAddress = "218.17.13.108:9999"; 
		UserMgr.setServerIp(serviceAddress);
		SystemMgr.setBaseHostIp(serviceAddress);
		SystemMgr.setBaseHostUrl("http://" + serviceAddress + "/epg/json/"); 
		setAccoutInfo(serviceAddress, currentActivity);
		
//		String addrPrefix = getPublicServerAddrPrefix();
//		if (StringUtil.isEmpty(addrPrefix)) {
//			mCurrentIPTag = 0;
//			BaseActivity.getInstance().showToast("706", Toast.LENGTH_LONG);
//			new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
//			return;
//		}
//		String url = null;
//		if (tag) {
//			url = "http://" + addrPrefix + ipAddressArray[mCurrentIPTag] + ":9999/epg/json/stbinit/epgaddress";
//		} else {
//			url = "http://ip.szhhzt.com:9999/epg/json/stbinit/epgaddress";
//		}
//		String url = "http://218.17.13.108" + ipAddressArray[mCurrentIPTag] + ":9999/epg/json/stbinit/epgaddress";
//		LVBHttpUtils.get(url, new IResponseable() {
//
//			@Override
//			public void onSuccess(String result) {
//				Gson gson = new Gson();
//				PublicServiceInfoModel infoModel = gson.fromJson(result, PublicServiceInfoModel.class);
//				mCurrentIPTag = 0;
//				if (null != infoModel) {
//					if (infoModel.isResult()) {
//						String serviceAddress = infoModel.getDesc();
//						if (StringUtil.isEmpty(serviceAddress)) {
//							BaseActivity.getInstance().showToast("707-1", Toast.LENGTH_SHORT);
//							new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
//							return;
//						}
//						if (!StringUtil.isEmpty(UserMgr.getServerIp()) && !UserMgr.getServerIp().equalsIgnoreCase(serviceAddress)) {
//							UserMgr.setUserName("");
//							UserMgr.setAcountPasswd("");
//						}
//						UserMgr.setServerIp(serviceAddress);
//						SystemMgr.setBaseHostIp(serviceAddress);
//						SystemMgr.setBaseHostUrl("http://" + serviceAddress + "/epg/json/");
//						setAccoutInfo(serviceAddress, currentActivity);
//					} else {
//						BaseActivity.getInstance().showToast("707-2:" + infoModel.getDesc(), Toast.LENGTH_LONG);
//						new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
//					}
//				} else {
//					BaseActivity.getInstance().showToast("707-3:" + result, Toast.LENGTH_LONG);
//					new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
//				}
//			}
//
//			@Override
//			public void onFailed(String result) {
//				if (mCurrentIPTag < ipAddressArray.length - 1) {
//					mCurrentIPTag++;
//					setServiceInfo(currentActivity, true);
//				} else {
//					if (tag) {
//						setServiceInfo(currentActivity, false);
//					} else {
//						mCurrentIPTag = 0;
//						BaseActivity.getInstance().showToast("708:" + result, Toast.LENGTH_LONG);
//						new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
//					}
//				}
//			}
//		});
	}

	/**
	 * 测试网络状况,多次测试不通跳转到手动登陆界面
	 * 
	 * @param currentActivity
	 * @param networkCB
	 */
	public void testNetworkState(final Activity currentActivity, final INetworkSuccessCB networkCB) {
		boolean netState = CommonUtil.isNetworkAvailable(LVBXApp.getApp());
		if (netState) {
			mTimerRecorderCount = 0;
			networkCB.networkSuccessCB(true);
			return;
		} else {
			if (mTimerRecorderCount <= 30) {
				mTimerRecorderCount++;
				if (null != SplashActivity.getIntance() && SplashActivity.getIntance().mIsInSplashTag) {
					ThreadPoolManager.getInstance().addTask(new Runnable() {

						@Override
						public void run() {
							if (null != currentActivity && !currentActivity.isFinishing()) {
								try {
									Thread.sleep(3000);
									testNetworkState(currentActivity, networkCB);
								} catch (Exception e) {
									if (mTimerRecorderCount <= 30) {
										testNetworkState(currentActivity, networkCB);
									} else {
										mTimerRecorderCount = 0;
										e.printStackTrace();
										BaseActivity.getInstance().showToast("701-2:" + e.toString(), Toast.LENGTH_LONG);
										switch (Config.Auto_ACCOUNT_TAG) {
										case 1:
											new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
											break;
										default:
											break;
										}
									}
								}
							} else {
								mTimerRecorderCount = 0;
								BaseActivity.getInstance().showToast(LVBXApp.getApp().getString(R.string.activity_exception), Toast.LENGTH_LONG);
							}
						}
					});
				} else {
					mTimerRecorderCount = 0;
					BaseActivity.getInstance().showToast(LVBXApp.getApp().getString(R.string.activity_exception), Toast.LENGTH_LONG);
				}
			} else {
				mTimerRecorderCount = 0;
				BaseActivity.getInstance().showToast("701-1", Toast.LENGTH_LONG);
				switch (Config.Auto_ACCOUNT_TAG) {
				case 1:
					new LoginExceptionDialog(currentActivity, R.style.lvbTwoButtonDialogTheme).show();
					break;
				default:
					break;
				}
			}
		}
	}

	public void getPairedActionInfos(final Activity currentActivity, final String serverIp, String passwd) {
		String url = UrlMgr.getPairedPasswdInfosUrl(serverIp, passwd);

		LVBHttpUtils.get(url, new IResponseable() {

			@Override
			public void onSuccess(String result) {
				Gson gson = new Gson();
				LoginInfoModel loginModel = gson.fromJson(result, LoginInfoModel.class);

				if (null != loginModel && "200".equalsIgnoreCase(loginModel.getCode())) {
					UserMgr.setPairedTag(true);
					UserMgr.setUserName(loginModel.getIptvusername());
					UserMgr.setChannelGroup(loginModel.getChannelgroup());
					UserMgr.setPairedIp(loginModel.getIpaddress());
					UserMgr.setInteracPassword(loginModel.getInteracpassword());
					UserMgr.setSSIDHideTag(loginModel.getSSIDhide());
					UserMgr.setServerIp(serverIp);
					UserMgr.setEncrypted(loginModel.getIsencrypted());
					UserMgr.setHotelRoomNo(StringUtil.isEmpty(loginModel.getRoomno()) ? loginModel.getRoomname() : loginModel.getRoomno());
					UserMgr.setHotSpotEnable(loginModel.getHotspot());
					UserMgr.setVodTypeLeve(loginModel.getLevel());
					UserMgr.setEpgGroupId(loginModel.getEpgGroupid());

					// 移动端发送配对请求
					UIDataller.getDataller().sendPairedAuthorithCmd(currentActivity, loginModel, new IOnPairedSuccessable() {

						@Override
						public void success() {
							enterLVBXSystem(currentActivity);
						}

					});
				} else if (null != loginModel) {
					BaseActivity.getInstance().showToast(loginModel.getMessage(), Toast.LENGTH_LONG);
				} else {
					BaseActivity.getInstance().showToast(result, Toast.LENGTH_LONG);
				}
			}

			@Override
			public void onFailed(String result) {
				BaseActivity.getInstance().showToast(result, Toast.LENGTH_LONG);
			}
		});
	}

	public void enterLVBXSystem(Activity currentActivity) {
		int languageFlag = SystemMgr.getWantedLangType();
		UIDataller.getDataller().initViewTempConfigInfo(currentActivity, WelcomeActivity.class, languageFlag);

	}
}

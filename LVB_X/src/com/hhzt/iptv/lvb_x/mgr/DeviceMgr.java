package com.hhzt.iptv.lvb_x.mgr;

import android.view.KeyEvent;

import com.google.gson.Gson;
import com.hhzt.iptv.lvb_x.floatwindows.FloatWindowManager;
import com.hhzt.iptv.lvb_x.interfaces.IResponseable;
import com.hhzt.iptv.lvb_x.model.DeviceInfoBean;
import com.hhzt.iptv.lvb_x.net.LVBHttpUtils;

public class DeviceMgr {

	private static final int STATUS_NORMAL = 0;// 正常状态
	private static final int STATUS_TICKS = 1;// 倒计时状态
	private static final int STATUS_LOCKED = 2;// 锁定状态

	private static DeviceMgr deviceMgr = new DeviceMgr();

	private DeviceMgr() {

	}

	public static DeviceMgr getInstance() {
		if (null == deviceMgr) {
			synchronized (DeviceMgr.class) {
				if (null == deviceMgr) {
					deviceMgr = new DeviceMgr();
				}
			}
		}

		return deviceMgr;
	}

	public void checkStatus() {
		String account = UserMgr.getUserName();
		String url = UrlMgr.getDeviceStatusUrl(account);
		LVBHttpUtils.get(url, new IResponseable() {

			@Override
			public void onSuccess(String result) {
				Gson gson = new Gson();
				DeviceInfoBean bean = gson.fromJson(result, DeviceInfoBean.class);
				if (null != bean) {
					switch (bean.getStatus()) {
					case STATUS_NORMAL:
						// 正常状态,将倒计时和锁定界面都消失掉
						FloatWindowManager.getInstance().showNormalWindow();
						break;
					case STATUS_TICKS:
						// 倒计时状态，显示倒计时界面
						FloatWindowManager.getInstance().showTickerWindow(bean.getCountdownSecond());
						break;
					case STATUS_LOCKED:
						// 锁定状态，显示锁定界面
						FloatWindowManager.getInstance().showLockedWindow();
						break;
					default:
						break;
					}
				}
			}

			@Override
			public void onFailed(String result) {

			}
		});
	}

	/**
	 * 将按键键值转换为数字
	 * 
	 * @param keyCode
	 * @return
	 */
	public int switchKeyCodeToNmuber(int keyCode) {
		int number = 0;
		switch (keyCode) {
		case KeyEvent.KEYCODE_0:
			number = 0;
			break;
		case KeyEvent.KEYCODE_1:
			number = 1;
			break;
		case KeyEvent.KEYCODE_2:
			number = 2;
			break;
		case KeyEvent.KEYCODE_3:
			number = 3;
			break;
		case KeyEvent.KEYCODE_4:
			number = 4;
			break;
		case KeyEvent.KEYCODE_5:
			number = 5;
			break;
		case KeyEvent.KEYCODE_6:
			number = 6;
			break;
		case KeyEvent.KEYCODE_7:
			number = 7;
			break;
		case KeyEvent.KEYCODE_8:
			number = 8;
			break;
		case KeyEvent.KEYCODE_9:
			number = 9;
			break;
		default:
			break;
		}

		return number;
	}
}

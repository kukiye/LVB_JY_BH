package com.hhzt.iptv.lvb_x.mgr;

import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.floatwindows.FloatWindowManager;

public class WindowController {

	public static void openFloatControllerWindow() {
		switch (Config.FLOAT_WINDOW_SHOW_TAG) {
		case Constant.IPTV_FLOAT_WINDOW_TYPE_BACK_CONTROLLER:
			FloatWindowManager.getInstance().createLVBEntryWindow(LVBXApp.getApp().getApplicationContext());
			break;
		case Constant.IPTV_FLOAT_WINDOW_TYPE_RC_CONTROLLER:
			FloatWindowManager.getInstance().createRcWindow(LVBXApp.getApp().getApplicationContext());
			break;
		default:
			break;
		}
	}

	public static void closeFloatControllerWindow() {
		switch (Config.FLOAT_WINDOW_SHOW_TAG) {
		case Constant.IPTV_FLOAT_WINDOW_TYPE_BACK_CONTROLLER:
			FloatWindowManager.getInstance().removeLVBEntryWindos(LVBXApp.getApp().getApplicationContext());
			break;
		case Constant.IPTV_FLOAT_WINDOW_TYPE_RC_CONTROLLER:
			FloatWindowManager.getInstance().removeLVBRCWindos(LVBXApp.getApp().getApplicationContext());
			break;
		default:
			break;
		}
	}

}

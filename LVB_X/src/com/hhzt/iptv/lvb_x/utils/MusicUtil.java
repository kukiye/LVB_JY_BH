package com.hhzt.iptv.lvb_x.utils;

import com.hhzt.iptv.lvb_x.LVBXApp;

public class MusicUtil {

	public static boolean playTag() {
		boolean tag = true;
		String className = CommonUtil.getTopActivity(LVBXApp.getApp());
		if (className.equalsIgnoreCase("com.hhzt.iptv.ui.MediaPlayerActivity") || className.equalsIgnoreCase("com.hhzt.iptv.ui.VodActivity")
				|| className.equalsIgnoreCase("com.hhzt.iptv.ui.VodItemDetailActivity") || className.equalsIgnoreCase("com.hhzt.iptv.ui.AppActivity")
				|| className.equalsIgnoreCase("com.hhzt.iptv.ui.SubAppActivity") || className.equalsIgnoreCase("com.hhzt.iptv.ui.VodSearchActivity")) {
			tag = false;
		}
		return tag;

	}

}

package com.hhzt.iptv.lvb_x.utils;

import com.hhzt.iptv.lvb_x.ClickType;

public class ClickFilter {
	public static long lastClickTime = 0L;

	public static boolean checkClick(ClickType type) {
		long interval = 0;
		switch (type) {
		case SINGLE_CLICK:
			break;
		case DOUBLE_CLICK:
			interval = 500L;
			break;
		case BUTTON_QUICK_CLICK:
			interval = 300L;
			break;
		case EXIT_CLICK:
			interval = 2000L;
			break;
		case CMD_SPACE:
			interval = 300L;
			break;
		case DRAG_INTERVAL:
			interval = 50L;
			break;
		default:
			break;
		}

		if (interval == 0) {
			return true;
		} else {
			long time = System.currentTimeMillis();
			if ((time - lastClickTime) > interval) {
				lastClickTime = time;
				return false;
			}
			lastClickTime = time;
			return true;
		}
	}

}

package com.hhzt.iptv.lvb_x.utils;

import com.hhzt.iptv.lvb_x.LVBXApp;

/**
 * 自适应多分辨主类
 * 
 * @author johnsonwu
 * 
 */
public class CoordinateUtil {

	public static int getX(int x) {
		if (LVBXApp.getmScreenWidth() == LVBXApp.S_MBASE_SCREENWIDTH && LVBXApp.getmScreenHeight() == LVBXApp.S_MBASE_SCREENHEIGHT) {
			return x;
		}

		int int_x = 0;
		if (x >= 0) {
			float temp = (float) LVBXApp.getmScreenWidth() / LVBXApp.S_MBASE_SCREENWIDTH * x / LVBXApp.getmScreenDensity();
			int_x = Math.round(temp);
		} else {
			float temp = (float) LVBXApp.getmScreenWidth() / LVBXApp.S_MBASE_SCREENWIDTH * (-x) / LVBXApp.getmScreenDensity();
			int_x = -Math.round(temp);
		}
		return int_x;
	}

	public static int getY(int y) {
		if (LVBXApp.getmScreenWidth() == LVBXApp.S_MBASE_SCREENWIDTH && LVBXApp.getmScreenHeight() == LVBXApp.S_MBASE_SCREENHEIGHT) {
			return y;
		}

		int int_y = 0;
		if (y >= 0) {
			float temp = (float) LVBXApp.getmScreenHeight() / LVBXApp.S_MBASE_SCREENHEIGHT * y / LVBXApp.getmScreenDensity();
			int_y = Math.round(temp);
		} else {
			float temp = (float) LVBXApp.getmScreenHeight() / LVBXApp.S_MBASE_SCREENHEIGHT * (-y) / LVBXApp.getmScreenDensity();
			int_y = -Math.round(temp);
		}
		return int_y;
	}

	public static float getX(float x) {
		float float_x = 0f;
		float_x = (float) (LVBXApp.getmScreenWidth() / LVBXApp.S_MBASE_SCREENWIDTH * x) / LVBXApp.getmScreenDensity();
		return float_x;
	}

	public static float getY(float y) {
		float float_y = 0f;
		float_y = (float) (LVBXApp.getmScreenHeight() / LVBXApp.S_MBASE_SCREENHEIGHT * y) / LVBXApp.getmScreenDensity();
		return float_y;
	}

	public static int getWidth(int width) {
		return getX(width);
	}

	public static int getHeight(int height) {
		return getY(height);
	}

	public static float getWidth(float width) {
		return getX(width);
	}

	public static float getHeight(float height) {
		return getY(height);
	}
}

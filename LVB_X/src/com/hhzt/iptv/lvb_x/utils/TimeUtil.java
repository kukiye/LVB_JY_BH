package com.hhzt.iptv.lvb_x.utils;

public class TimeUtil {

	/**
	 * 获取天数
	 * @param time
	 * @return
	 */
	public static int getDayNum(long time) {
		int dayNum = 0;
		int day0 = (int) (time / (3600 * 24));
		int day1 = 0;
		int remainder = (int) (time % (3600 * 24));
		if(remainder>0){
			day1 = 1;
		}
		dayNum = day0 + day1;
		return dayNum;
	}
}

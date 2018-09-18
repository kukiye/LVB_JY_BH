/**
 * 作者：   Johnson
 * 日期：   2014年6月11日下午2:28:50
 * 包名：    com.hhzt.iptv_lvb_x
 * 工程名：LVB_X
 * 文件名：Config.java
 */
package com.hhzt.iptv.lvb_x.config;

import com.hhzt.iptv.lvb_x.Constant;

public class Config {
	public static final String APP_ID = "900001333";

	/**
	 * 数据库版本---当数据库发生改变时该值也要变化从而更新数据库
	 */
	public static final int DB_VERSION = 1;

	/**
	 * 软件运行设备类型：机顶盒、移动端
	 */
	public static final int LvbDeviceType = Constant.DEVICE_TYPE_BOX;

	/**
	 * 打开返回浮动窗口
	 */
	public static final int FLOAT_WINDOW_SHOW_TAG = Constant.IPTV_FLOAT_WINDOW_TYPE_NONE_CONTROLLER;

	/**
	 * 加入设备开放时间和锁定功能 0--关闭、1--打开
	 */
	public static final int DEVICE_BOX_LOCK_TAG = 0;

	/**
	 * 东融国际加入使用第三方播放器功能0--关闭、1--打开
	 */
	public static final int USE_THIRD_PLAYER_TAG = 0;

	/**
	 * 订餐和特产功能实现开关0--浏览、1--功能
	 */
	public static final int USER_ORDER_SPECIAL_ACTION = 0;

	/**
	 * 自动获取账号功能开关0--关闭、1--打开
	 */
	public static final int Auto_ACCOUNT_TAG = 0;
}

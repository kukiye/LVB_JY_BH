/**
 * 作 者：Johnson
 * 日 期：2015年6月18日下午2:20:53
 * 包 名：com.udp.app
 * 工程名：MediaDetector
 * 文件名：SCCMDType.java
 */
package com.hhzt.iptv.lvb_x.scller;

/**
 * 智能控制命令
 * 
 * @author Johnson
 *
 */
public class SCCMDType {
	// 一、灯光控制：
	// 廊灯：
	public static String LIGHT1_CMD = "1e1e484d495300080000ffffffff00005001010201010800000000010000";
	// 卫浴灯：
	public static String LIGHT2_CMD = "1e1e484d495300080000ffffffff00005001010201010800000000020000";
	// 排气扇：
	public static String LIGHT3_CMD = "1e1e484d495300080000ffffffff00005001010201010800000000030000";
	// 夜灯：
	public static String LIGHT4_CMD = "1e1e484d495300080000ffffffff00005001010201010800000000040000";
	// 镜前灯：
	public static String LIGHT5_CMD = "1e1e484d495300080000ffffffff00005001010201010800000000050000";
	// 沐浴灯：
	public static String LIGHT6_CMD = "1e1e484d495300080000ffffffff00005001010201010800000000060000";
	// 左壁灯：
	public static String LIGHT7_CMD = "1e1e484d495300080000ffffffff00005001010201010800000000070000";
	// 右壁灯：
	public static String LIGHT8_CMD = "1e1e484d495300080000ffffffff00005001010201010800000000080000";
	// 房灯：
	public static String LIGHT9_CMD = "1e1e484d495300080000ffffffff00005001010201010800000000090000";
	// 书桌灯：
	public static String LIGHTa_CMD = "1e1e484d495300080000ffffffff000050010102010108000000000a0000";
	// 落地灯：
	public static String LIGHTb_CMD = "1e1e484d495300080000ffffffff000050010102010108000000000b0000";

	// 二、电视控制
	// 暂未确定

	// 三、空调控制
	// 打开、关闭：
	public static String AC_POWER_CMD = "1e1e484d495300080000ffffffff000050010102010108ff050000000000";
	// 模式选择：
	public static String AC_MODE_CMD = "1e1e484d495300080000ffffffff000050010102010108ff010000000000";
	// 温度提高：
	public static String AC_TPADD_CMD = "1e1e484d495300080000ffffffff000050010102010108ff020000000000";
	// 温度降低：
	public static String AC_TPRED_CMD = "1e1e484d495300080000ffffffff000050010102010108ff030000000000";
	// 风速选择：
	public static String AC_SPEED_CMD = "1e1e484d495300080000ffffffff000050010102010108ff040000000000";

	// 四、服务控制
	// 暂未确定
}

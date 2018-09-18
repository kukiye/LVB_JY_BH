/**
 * 作者：   Johnson
 * 日期：   2014年7月15日上午11:55:25
 * 包名：    com.hhzt.iptv.lvb_x.mgr
 * 工程名：LVB_X
 * 文件名：PowerMgr.java
 */
package com.hhzt.iptv.lvb_x.mgr;

import com.hhzt.iptv.lvb_x.LVBXApp;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class PowerMgr {
	/**
	 * onCreate时,申请设备电源锁
	 */
	private WakeLock mWakeLock;// 电源锁
	private static PowerMgr mPowerMgr = new PowerMgr();

	private PowerMgr() {

	}

	public static PowerMgr getPowerMgrInstance() {
		if (null == mPowerMgr) {
			synchronized (PowerMgr.class) {
				if (null == mPowerMgr) {
					mPowerMgr = new PowerMgr();
				}
			}
		}

		return mPowerMgr;
	}

	public void acquireWakeLock() {
		if (null == mWakeLock) {
			PowerManager pm = (PowerManager) LVBXApp.getApp().getSystemService(Context.POWER_SERVICE);
			mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "myService");
			if (null != mWakeLock) {
				mWakeLock.acquire();
			}
		}
	}

	/**
	 * onDestroy时，释放设备电源锁
	 */
	public void releaseWakeLock() {
		if (null != mWakeLock) {
			mWakeLock.release();
			mWakeLock = null;
		}
	}

	/************************************* 操作机顶盒命令 begin ************************************/
	/**
	 * 重启机顶盒
	 */
	public void restartSystem() {
		AuthorithMgr.getInstance().RootCmd("reboot");
	}

	/**
	 * 关闭机顶盒
	 */
	public void shutdownSystem() {

	}

	/**
	 * 休眠机顶盒
	 */
	public void sleepSystem() {

	}

	/**
	 * 返回home界面
	 */
	public void homeSystem() {

	}

	/**
	 * 锁定系统界面
	 */
	public void lockSystem() {

	}

	/**
	 * 触发linux操作命令
	 * 
	 * @param cmd
	 */
	public void actionCmdSystem(String cmd) {
		if ("shutdown".equalsIgnoreCase(cmd)) {
			shutdownSystem();
		} else if ("restart".equalsIgnoreCase(cmd)) {
			restartSystem();
		} else if ("sleep".equalsIgnoreCase(cmd)) {
			sleepSystem();
		} else if ("main".equalsIgnoreCase(cmd)) {
			homeSystem();
		} else if ("lock".equalsIgnoreCase(cmd)) {
			lockSystem();
		}
	}
	/************************************* 操作机顶盒命令 end ************************************/
}

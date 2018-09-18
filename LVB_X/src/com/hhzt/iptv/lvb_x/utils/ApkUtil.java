package com.hhzt.iptv.lvb_x.utils;

import java.io.File;

import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.mgr.ThreadPoolManager;
import com.hhzt.iptv.lvb_x.rootmanager.RootManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class ApkUtil {

	/**
	 * 判断apk是否安装
	 * 
	 * @param uri
	 * @return
	 */
	public static boolean isAppInstalled(Context context, String uri) {
		PackageManager pm = context.getPackageManager();
		boolean installed = false;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			installed = false;
		}
		return installed;
	}

	/**
	 * 安装游戏apk
	 * 
	 * @param file
	 */
	public static void installApk(Context context, final File file) {
		// updateSystemApk();
		try {

			Intent intent = new Intent();
			// 执行动作
			intent.setAction(Intent.ACTION_VIEW);
			// 执行的数据类型
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			context.startActivity(intent);
			Activity activity = (Activity) context;
			if (!Constant.IPTV_LVB_X_ACTIVITY_SUBAPPACTIVITY.equals(activity.getClass().getName())) {
				activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//静默安装
//        ThreadPoolManager.getInstance().addTask(new Runnable() {
//            @Override
//            public void run() {
//                RootManager.getInstance().execRootCmd("pm install -r " + file.getAbsolutePath());
//            }
//        });
	}
}

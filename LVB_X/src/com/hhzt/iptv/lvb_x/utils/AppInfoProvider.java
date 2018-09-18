package com.hhzt.iptv.lvb_x.utils;

import java.util.ArrayList;
import java.util.List;

import com.hhzt.iptv.lvb_x.model.AppInfoModel;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppInfoProvider {
	public static final int ALL = 0;
	public static final int USER = 1;
	public static final int SYSTEM = 2;

	private Context mContext;

	public AppInfoProvider(Context context) {
		this.mContext = context;
	}

	/**
	 * 返回安装的所有的程序信息的集合
	 * 
	 * @return 应用程序的集合
	 */
	public List<AppInfoModel> getAllApps(int type) {
		List<AppInfoModel> appinfos = new ArrayList<AppInfoModel>();
		List<PackageInfo> packinfos = mContext.getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (PackageInfo info : packinfos) {
			if (info.packageName.equalsIgnoreCase(mContext.getPackageName())) {
				// 过滤应用本身
				continue;
			}
			// 过滤系统原生应用
			if (info.packageName.contains("com.android")) {
				// 只显示设置按钮菜单，其他全部过滤掉
				if (!"com.android.settings".equalsIgnoreCase(info.packageName) && !"com.android.browser".equalsIgnoreCase(info.packageName)) {
					continue;
				}
			}

			AppInfoModel app = new AppInfoModel();
			app.setPackname(info.packageName);
			app.setIcon(info.applicationInfo.loadIcon(mContext.getPackageManager()));
			app.setAppname(info.applicationInfo.loadLabel(mContext.getPackageManager()).toString());
			switch (type) {
			case ALL:
				if (isUserApp(info.applicationInfo)) {
					app.setIsSystemApp(false);
				} else {
					app.setIsSystemApp(true);
				}
				appinfos.add(app);
				break;
			case USER:
				if (isUserApp(info.applicationInfo)) {
					app.setIsSystemApp(false);
					appinfos.add(app);
				}
				break;
			case SYSTEM:
				if (!isUserApp(info.applicationInfo)) {
					app.setIsSystemApp(true);
					appinfos.add(app);
				}
				break;
			default:
				break;
			}
		}
		return appinfos;
	}

	/**
	 * 判断某个应用程序是 不是三方的应用程序
	 * 
	 * @param info
	 * @return
	 */
	private boolean isUserApp(ApplicationInfo info) {
		if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
			return true;
		} else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
			return true;
		}
		return false;
	}

}

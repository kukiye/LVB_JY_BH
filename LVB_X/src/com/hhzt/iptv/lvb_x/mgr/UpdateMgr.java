/**
 * 作 者：Johnson
 * 日 期：2015年6月26日下午2:29:14
 * 包 名：com.hhzt.iptv.lvb_x.mgr
 * 工程名：MediaDetector
 * 文件名：UpdateMgr.java
 */
package com.hhzt.iptv.lvb_x.mgr;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.commonui.IPTVConfirmDialog;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.interfaces.IOnDialogClickListner;
import com.hhzt.iptv.lvb_x.interfaces.IResponseable;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.model.VersionInfosModel;
import com.hhzt.iptv.lvb_x.net.LVBDownloadManager;
import com.hhzt.iptv.lvb_x.net.LVBHttpUtils;
import com.hhzt.iptv.lvb_x.utils.ApkUtil;

public class UpdateMgr {

	private Activity mUpdateMgrActivity;
	private LVBDownloadManager mLvbDownloadManager;

	public UpdateMgr(Activity activity) {
		mUpdateMgrActivity = activity;
	}

	@SuppressLint("HandlerLeak")
	public Handler mainHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.IPTV_MSG_UPDATE_DOWNLOAD_FAILED:
				mainHandler.removeMessages(msg.what);
				Toast.makeText(mUpdateMgrActivity, mUpdateMgrActivity.getString(R.string.download_failed), Toast.LENGTH_SHORT).show();
				break;
			case Constant.IPTV_MSG_APP_EXIST_TAG:
				mainHandler.removeMessages(Constant.IPTV_MSG_APP_EXIST_TAG);
				String url = msg.getData().getString(Constant.IPTV_SYS_MSG_APK_DOWNLOAD_URL, null);
				boolean flag = msg.getData().getBoolean(Constant.IPTV_SYS_MSG_APK_EXIST_FLAG, false);
				mLvbDownloadManager.installOrDownloadApk(url, flag);
				break;
			default:
				break;
			}
		}
	};

	public void checkNewVersion() {
		String url = null;
		switch (Config.LvbDeviceType) {
		case Constant.DEVICE_TYPE_BOX:
		case Constant.DEVICE_TYPE_BOX_HSJQ:
			url = UrlMgr.getBoxVersionInfoUrl(UserMgr.getUserName());
			break;
		case Constant.DEVICE_TYPE_MOBILE:
		case Constant.DEVICE_TYPE_MOBILE_HSJQ:
			url = UrlMgr.getMobileVersionInfoUrl(UserMgr.getUserName());
			break;
		default:
			break;
		}
		LogUtil.d("checkNewVersion-------" + url); 
		LVBHttpUtils.get(url, new IResponseable() {

			@Override
			public void onSuccess(String result) {
				Gson gson = new Gson();
				VersionInfosModel models = gson.fromJson(result, VersionInfosModel.class);
				if (models != null) {
					if (models != null && SystemMgr.hasNewVersion(models.getVersioncode())) {
						// if (RootManager.getInstance().grantPermission()) {
						// ActivitySwitchMgr.gotoUpdateTipsActivity(getActivity(),
						// models);
						// } else {
						updateApk(models);
						// }
					} else {
						checkNewMXPlayer();
					}
				}
			}

			@Override
			public void onFailed(String result) {

			}
		});
	}

	private void checkNewMXPlayer() {
		switch (Config.USE_THIRD_PLAYER_TAG) {
		case 1:
			if (!ApkUtil.isAppInstalled(mUpdateMgrActivity, Constant.MXPLAYER_APK_PACKAGE_NAME)) {
				String url = UrlMgr.getMXPlayerUrl(UserMgr.getUserName());
				LVBHttpUtils.get(url, new IResponseable() {

					@Override
					public void onSuccess(String result) {
						Gson gson = new Gson();
						VersionInfosModel models = gson.fromJson(result, VersionInfosModel.class);
						if (models != null) {
							downloadMXPlayer(models.getDownloadurl());
						}
					}

					@Override
					public void onFailed(String result) {

					}
				});
			} else {
				deleteApk();
			}
			break;
		default:
			deleteApk();
			break;
		}
	}

	private void updateApk(final VersionInfosModel infos) {
		if ("1".equalsIgnoreCase(infos.getForceupdate())) {
			updateApps(infos.getDownloadurl());
		} else {
			LogUtil.d("updateApk----" + infos);
			String title = mUpdateMgrActivity.getString(R.string.update_tips);
			String contents = mUpdateMgrActivity.getString(R.string.new_version_update_need);
			IPTVConfirmDialog dialog = new IPTVConfirmDialog(mUpdateMgrActivity, title, contents, mUpdateMgrActivity.getString(R.string.confirm),
					mUpdateMgrActivity.getString(R.string.cancel), new IOnDialogClickListner() {

						@Override
						public void OnOkClick(Activity activity) {
							updateApps(infos.getDownloadurl());
						}

						@Override
						public void OnCancelClick(Activity activity) {
							checkNewMXPlayer(); 
						}
					}, false);
			dialog.show();
		}
	}

	private void updateApps(String url) {
		mLvbDownloadManager = new LVBDownloadManager(mUpdateMgrActivity, mainHandler);
		mLvbDownloadManager.downloadAPPApk(url);
	}

	private void downloadMXPlayer(String url) {
		updateApps(url);
	}

	private void deleteApk() {
		String clientPath = UrlMgr.getClientStoragePath();
		File apkDir = new File(clientPath);
		if (apkDir.exists()) {
			File[] files = apkDir.listFiles();
			for (File file : files) {
				file.delete();
			}
		}
	}
}

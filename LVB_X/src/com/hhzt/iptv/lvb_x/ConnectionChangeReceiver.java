/**
 * 作者：   Johnson
 * 日期：   2014年8月5日下午12:29:44
 * 包名：    com.hhzt.iptv.lvb_x
 * 工程名：LVB_X
 * 文件名：ConnectionChangeReceiver.java
 */
package com.hhzt.iptv.lvb_x;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.MediaControllerMgr;
import com.hhzt.iptv.lvb_x.model.ForcePlayBean;
import com.hhzt.iptv.lvb_x.utils.VirturlKeyPadCtr;
import com.hhzt.iptv.ui.MediaPlayerActivity;

public class ConnectionChangeReceiver extends BroadcastReceiver {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if (BaseActivity.getInstance() == null) {
			LogUtil.d("wujichang----onReceive--->LVB_X app has exist!");
			return;
		}
		LogUtil.d("onReceive--------" + intent.getAction());
		if (intent.getAction().equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)) {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ethNet = null;
			switch (Config.LvbDeviceType) {
			case Constant.DEVICE_TYPE_BOX:
			case Constant.DEVICE_TYPE_BOX_HSJQ:
				ethNet = manager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
				break;
			case Constant.DEVICE_TYPE_MOBILE:
			case Constant.DEVICE_TYPE_MOBILE_HSJQ:
				ethNet = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				break;
			default:
				break;
			}
			if (null != ethNet && !ethNet.isAvailable()) {
				BaseActivity.getInstance().showToast(LVBXApp.getApp().getString(R.string.net_is_unusable_tips), Toast.LENGTH_LONG);
			} else {
				BaseActivity.getInstance().showToast(LVBXApp.getApp().getString(R.string.net_is_usable), Toast.LENGTH_LONG);
			}
		} else if (intent.getAction().equalsIgnoreCase(Constant.IPTV_LVB_X_BROADCAST_MSG_PAIR_AUTHORITH)) {
			ActivitySwitchMgr.gotoPairedAuthorithTips(context, intent);
		} else if (intent.getAction().equalsIgnoreCase(Constant.IPTV_LVB_X_BROADCAST_MSG_HSJQ_SEAT)) {
			MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
			if (null != mediaActivity && !mediaActivity.isFinishing()) {
				if (mediaActivity.getCurrentMediaType() == Constant.IPTV_MEDIA_TYPE_VOD) {
					return;
				}
			}
			ActivitySwitchMgr.gotoSeatSelectionTips(context, intent);
		} else if (intent.getAction().equalsIgnoreCase(Constant.IPTV_LVB_X_BROADCAST_MSG_HSJQ_SEAT_CANCEL)) {
			VirturlKeyPadCtr.RC_ConttrollerAction(KeyEvent.KEYCODE_A);
		} else if(intent.getAction().equalsIgnoreCase(Constant.IPTV_LVB_X_BROADCAST_MSG_PLAY_UNLOCK)){
			UIDataller.getDataller().checkForcePlayMission();
		}else if (intent.getAction().equalsIgnoreCase(Constant.IPTV_LVB_X_BROADCAST_MSG_START_TICKER)) {
            Bundle bundle = intent.getBundleExtra(Constant.IPTV_BOUNDLE_FORCE_PLAY_LOCK);
            ForcePlayBean bean = bundle.getParcelable(Constant.IPTV_DATA_FORCE_PLAY_LOCK);
            MediaControllerMgr.getInstance().startTicker(bean);
        }
	}
}

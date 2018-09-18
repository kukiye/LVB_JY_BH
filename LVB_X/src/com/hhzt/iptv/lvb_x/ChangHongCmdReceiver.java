package com.hhzt.iptv.lvb_x;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;

public class ChangHongCmdReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		/**
		 * 统一接收广播“com.changhong.system.systemkeyfor3rd”，识别4个码值 4----返回键，
		 * 4124-----home，返回桌面 4143-----dtv，dtv快捷键 4153-----帮助 4155-----vod
		 */
		System.out.println("LVB_X received broadcast msg is:" + intent.getAction());
		if (Constant.IPTV_LVB_X_BROADCAST_MSG_CHANGHONG_DTV.equalsIgnoreCase(intent.getAction())) {
			int keyCode = intent.getIntExtra(Intent.EXTRA_KEY_EVENT, 0);
			switch (keyCode) {
			case 4:
			case 4124:
				ActivitySwitchMgr.gotoMainActivity(context);
				break;
			case 4153:
				ActivitySwitchMgr.gotoChangHoneLiveActivity(context);
				break;
			case 4120:
				ActivitySwitchMgr.gotoHelpTips(context, intent);
				break;
			case 4155:
				ActivitySwitchMgr.gotoVodActivity(context, Constant.IPTV_DEFAULT_VOD_MENUCODE, UserMgr.getVodMenuName());
				break;
			default:
				break;
			}
		}
	}
}

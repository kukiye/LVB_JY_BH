package com.hhzt.iptv.lvb_x;

import com.hhzt.iptv.ui.MediaPlayerActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MediaPlayerCmdReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		MediaPlayerActivity activity = MediaPlayerActivity.getInstance();
		if (null == activity) {
			return;
		}
		if (activity.mMediaType == Constant.IPTV_MEDIA_TYPE_VOD || activity.mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
			if (Constant.IPTV_LVB_X_BROADCAST_MSG_MEDIAPLAYER_STATE_CHANGE.equalsIgnoreCase(intent.getAction())) {
				int cmd = intent.getIntExtra(Constant.IPTV_MEDIAPLAYER_CONTROLLER_MSG, 0);
				switch (cmd) {
				case RemoteCMDType.VOD_MEDIA_PLAY:
					activity.playVodMedia();
					activity.releaseSeatDialog();
					break;
				case RemoteCMDType.VOD_MEDIA_PAUSE:
					activity.pauseVodMedia();
					activity.releaseSeatDialog();
					break;
				case RemoteCMDType.VOD_MEDIA_SEEK:
					String stringProgress = intent.getStringExtra(Constant.IPTV_VOD_REMOTE_SEEK_MSG);
					int intProgress = Integer.valueOf(stringProgress);
					activity.seekVodToProgress(intProgress);
					activity.releaseSeatDialog();
					break;
				case RemoteCMDType.VOD_MEDIA_PRE:
					activity.changePreVodItem();
					activity.releaseSeatDialog();
					break;
				case RemoteCMDType.VOD_MEDIA_NEXT:
					activity.changeNextVodItem();
					activity.releaseSeatDialog();
					break;
				default:
					break;
				}
			}
		}
	}
}

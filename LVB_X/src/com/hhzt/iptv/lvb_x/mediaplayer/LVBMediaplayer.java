/**
 * 作者：   Johnson
 * 日期：   2014年6月27日上午10:46:35
 * 包名：    com.hhzt.iptv.lvb_x.mediaplayer
 * 工程名：LVB_X
 * 文件名：LVBMediaplayer.java
 */
package com.hhzt.iptv.lvb_x.mediaplayer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.RemoteCMDType;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.model.VodItemPlayMessageModel;
import com.hhzt.iptv.lvb_x.utils.ApkUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.hhzt.iptv.ui.MediaPlayerActivity;

public class LVBMediaplayer {

	public static void playVod(Activity fromActivity, ArrayList<VodItemPlayMessageModel> models, String mediaUrl, String mediaName, int mediaId,
			int mediaType, int currentPosition, int programeId, String price, String mediaFeeName, String progress) {
		// wujichang 修改保证Mediaplayer在播放直播和点播时的单一性
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !mediaActivity.isFinishing()) {
			mediaActivity.finish();
		}
		switch (mediaType) {
		case Constant.IPTV_MEDIA_TYPE_LIVE:
			gotoMediaActivity(fromActivity, models, mediaUrl, mediaName, mediaId, mediaType, currentPosition, programeId, price, mediaFeeName,
					progress);
			break;
		case Constant.IPTV_MEDIA_TYPE_VOD:
			switch (Config.USE_THIRD_PLAYER_TAG) {
			case 1:
				if (ApkUtil.isAppInstalled(fromActivity, Constant.MXPLAYER_APK_PACKAGE_NAME)) {
					String extension = MimeTypeMap.getFileExtensionFromUrl(mediaUrl);
					Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
					mediaIntent.setDataAndType(Uri.parse(mediaUrl), "video/" + extension);
					fromActivity.startActivity(mediaIntent);
				} else {
					BaseActivity.getInstance().showToast(fromActivity.getString(R.string.player_tips), Toast.LENGTH_LONG);
				}
				break;
			default:
				gotoMediaActivity(fromActivity, models, mediaUrl, mediaName, mediaId, mediaType, currentPosition, programeId, price, mediaFeeName,
						progress);
				break;
			}
			break;
		default:
			break;
		}
	}

	public static void gotoMediaActivity(Activity fromActivity, ArrayList<VodItemPlayMessageModel> models, String mediaUrl, String mediaName,
			int mediaId, int mediaType, int currentPosition, int programeId, String price, String mediaFeeName, String progress) {
		Bundle bundle = new Bundle();
		Intent intent = new Intent(fromActivity, MediaPlayerActivity.class);
		bundle.putInt(Constant.IPTV_MEDIA_TYPE, mediaType);
		bundle.putInt(Constant.IPTV_VOD_MEDIA_PROGRME_ID, programeId);
		bundle.putString(Constant.IPTV_VOD_MEDIA_PRICE, price);
		bundle.putString(Constant.IPTV_MEDIA_URL, mediaUrl);
		bundle.putString(Constant.IPTV_MEDIA_NAME, mediaName);
		bundle.putString(Constant.IPTV_MEDIA_FEE_NAME, mediaFeeName);
		bundle.putInt(Constant.IPTV_MEDIA_MOVIE_ID, mediaId);
		bundle.putParcelableArrayList(Constant.IPTV_VOD_MEDIA_DATA, models);
		bundle.putInt(Constant.IPTV_MEDIA_MOVIE_CURRENT_POSITION, currentPosition);
		bundle.putString(Constant.IPTV_VOD_REMOTE_SEEK_MSG, progress);
		intent.putExtra(Constant.IPTV_MEDIA_DATA_TAG, bundle);
		fromActivity.startActivity(intent);
	}

	public static void playInteractiveLive(Activity fromActivity, String mediaUrl, String mediaName, String position, String channelNum) {
		// wujichang 修改保证Mediaplayer在播放直播和点播时的单一性
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !mediaActivity.isFinishing()) {
			mediaActivity.finish();
		}
		Bundle bundle = new Bundle();
		Intent intent = new Intent(fromActivity, MediaPlayerActivity.class);
		bundle.putString(Constant.IPTV_MEDIA_URL, mediaUrl);
		bundle.putString(Constant.IPTV_MEDIA_NAME, mediaName);
		bundle.putInt(Constant.IPTV_MEDIA_TYPE, Constant.IPTV_MEDIA_TYPE_LIVE);
		bundle.putInt(Constant.IPTV_LIVE_CHANNEL_INDEX, Integer.valueOf(position));
		bundle.putInt(Constant.IPTV_LIVE_CHANNEL_NUMBER, Integer.valueOf(channelNum));
		intent.putExtra(Constant.IPTV_MEDIA_DATA_TAG, bundle);
		fromActivity.startActivity(intent);
	}

	public static void playRemoteIntertiveLive(Activity fromActivity, String mediaUrl, String mediaName, String position, String channelNum) {
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !StringUtil.isEmpty(mediaUrl) && !StringUtil.isEmpty(mediaActivity.mMediaPath)
				&& mediaUrl.equalsIgnoreCase(mediaActivity.mMediaPath)) {
			if (null != mediaActivity && mediaActivity.mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE && null != mediaActivity.mMediaPlayer) {
				// nothing to do
			} else {
				playInteractiveLive(fromActivity, mediaUrl, mediaName, position, channelNum);
			}
		} else {
			playInteractiveLive(fromActivity, mediaUrl, mediaName, position, channelNum);
		}
	}

	public static void playInteractiveVod(Activity fromActivity, ArrayList<VodItemPlayMessageModel> models, String mediaUrl, String mediaName,
			int mediaId, int mediaType, int currentPosition, int programeId, String price, String mediaFeeName) {
		// wujichang 修改保证Mediaplayer在播放直播和点播时的单一性
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !mediaActivity.isFinishing()) {
			mediaActivity.finish();
		}
		playVod(fromActivity, models, mediaUrl, mediaName, mediaId, Constant.IPTV_MEDIA_TYPE_VOD, currentPosition, programeId, price, mediaFeeName,
				null);
	}

	public static void playRemoteInteractiveVod(Activity fromActivity, ArrayList<VodItemPlayMessageModel> models, String mediaUrl, String mediaName,
			int mediaId, int mediaType, int currentPosition, int programeId, String price, String mediaFeeName) {
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !StringUtil.isEmpty(mediaUrl) && !StringUtil.isEmpty(mediaActivity.mMediaPath)
				&& mediaUrl.equalsIgnoreCase(mediaActivity.mMediaPath)) {
			if (null != mediaActivity && mediaActivity.mMediaType == Constant.IPTV_MEDIA_TYPE_VOD && null != mediaActivity.mMediaPlayer
					&& !mediaActivity.mMediaPlayer.isPlaying()) {
				UIDataller.getDataller().sendVodControllerBroadcast(RemoteCMDType.VOD_MEDIA_PLAY);
			} else if (null != mediaActivity && mediaActivity.mMediaType == Constant.IPTV_MEDIA_TYPE_VOD && null != mediaActivity.mMediaPlayer
					&& mediaActivity.mMediaPlayer.isPlaying()) {
				// nothing to do
			} else {
				playInteractiveVod(fromActivity, models, mediaUrl, mediaName, mediaId, mediaType, currentPosition, programeId, price, mediaFeeName);
			}
		} else {
			playInteractiveVod(fromActivity, models, mediaUrl, mediaName, mediaId, mediaType, currentPosition, programeId, price, mediaFeeName);
		}
	}

	public static void playPreNextMediaVod(Activity fromActivity, ArrayList<VodItemPlayMessageModel> models, String mediaUrl, String mediaName,
			int mediaId, int mediaType, int currentPosition, int programeId, String price, String mediaFeeName, int cmd) {
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity) {
			if (null != mediaActivity && mediaActivity.mMediaType == Constant.IPTV_MEDIA_TYPE_VOD) {
				UIDataller.getDataller().sendVodControllerBroadcast(cmd);
			} else {
				playInteractiveVod(fromActivity, models, mediaUrl, mediaName, mediaId, mediaType, currentPosition, programeId, price, mediaFeeName);
			}
		} else {
			playInteractiveVod(fromActivity, models, mediaUrl, mediaName, mediaId, mediaType, currentPosition, programeId, price, mediaFeeName);
		}
	}

	public static void seekRemoteInteractiveVod(Activity fromActivity, ArrayList<VodItemPlayMessageModel> models, String mediaUrl, String mediaName,
			int mediaId, int mediaType, int currentPosition, int programeId, String price, String mediaFeeName, String progress) {
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !StringUtil.isEmpty(mediaUrl) && !StringUtil.isEmpty(mediaActivity.mMediaPath)
				&& mediaUrl.equalsIgnoreCase(mediaActivity.mMediaPath)) {
			if (null != mediaActivity && mediaActivity.mMediaType == Constant.IPTV_MEDIA_TYPE_VOD && null != mediaActivity.mMediaPlayer) {
				UIDataller.getDataller().sendVodSeekBroadcast(RemoteCMDType.VOD_MEDIA_SEEK, progress);
			} else {
				playSeekInteractiveVod(fromActivity, models, mediaUrl, mediaName, mediaId, mediaType, currentPosition, programeId, price,
						mediaFeeName, progress);
			}
		} else {
			playSeekInteractiveVod(fromActivity, models, mediaUrl, mediaName, mediaId, mediaType, currentPosition, programeId, price, mediaFeeName,
					progress);
		}
	}

	public static void playSeekInteractiveVod(Activity fromActivity, ArrayList<VodItemPlayMessageModel> models, String mediaUrl, String mediaName,
			int mediaId, int mediaType, int currentPosition, int programeId, String price, String mediaFeeName, String progress) {
		// wujichang 修改保证Mediaplayer在播放直播和点播时的单一性
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !mediaActivity.isFinishing()) {
			mediaActivity.finish();
		}
		playVod(fromActivity, models, mediaUrl, mediaName, mediaId, Constant.IPTV_MEDIA_TYPE_VOD, currentPosition, programeId, price, mediaFeeName,
				progress);
	}
	
	public static void playInteractiveLiveForce(Activity fromActivity, String mediaUrl, String mediaName,
			String position, String channelNum, long playTime) {
		// wujichang 修改保证Mediaplayer在播放直播和点播时的单一性
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !mediaActivity.isFinishing()) {
			mediaActivity.finish();
		}
		Bundle bundle = new Bundle();
		Intent intent = new Intent(fromActivity, MediaPlayerActivity.class);
		bundle.putString(Constant.IPTV_MEDIA_URL, mediaUrl);
		bundle.putString(Constant.IPTV_MEDIA_NAME, mediaName);
		bundle.putInt(Constant.IPTV_MEDIA_TYPE, Constant.IPTV_MEDIA_TYPE_LIVE_FORCE);
		bundle.putInt(Constant.IPTV_LIVE_CHANNEL_INDEX, Integer.valueOf(position));
		bundle.putInt(Constant.IPTV_LIVE_CHANNEL_NUMBER, Integer.valueOf(channelNum));
		bundle.putLong(Constant.IPTV_LIVE_START_PLAY_TIME, playTime);
		intent.putExtra(Constant.IPTV_MEDIA_DATA_TAG, bundle);
		fromActivity.startActivity(intent);
	}
}

package com.hhzt.iptv.lvb_x.mediaplayer;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.interfaces.IResponseable;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.AuthorithMgr;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.model.MusicItemModel;
import com.hhzt.iptv.lvb_x.net.LVBHttpUtils;
import com.hhzt.iptv.lvb_x.utils.MusicUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.hhzt.iptv.lvb_x.utils.download.DownloadInfo;
import com.hhzt.iptv.lvb_x.utils.download.DownloadManager;
import com.hhzt.iptv.lvb_x.utils.download.DownloadService;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

@SuppressLint("HandlerLeak")
public class LVBMusicBgMediaPlayer implements OnPreparedListener, OnCompletionListener, OnBufferingUpdateListener, OnErrorListener {

	private static LVBMusicBgMediaPlayer mBgPlayer = new LVBMusicBgMediaPlayer();
	private MediaPlayer mMediaPlayer = null;
	private DownloadManager mDownloadManager;
	private DownloadInfo mDownloadInfo = null;
	private String mDesPath = null;
	private int mMediaPlayerErrorCount = 0;

	public void startBgMusic(Application context) {
		if (mMediaPlayer == null) {
			initBgMusic(context);
		}
	}

	public static LVBMusicBgMediaPlayer getInstance() {
		return mBgPlayer;
	}

	public boolean isPlaying() {
		boolean flag = false;
		if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
			flag = true;
		}
		return flag;
	}

	private void playBgMusic(String path) {
		try {
			if (null == mMediaPlayer) {
				mMediaPlayer = new MediaPlayer();
			}

			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(path);// 设置资源
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setVolume(0.3f, 0.3f);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnErrorListener(this);
			mMediaPlayer.setOnBufferingUpdateListener(this);
		} catch (Exception e) {
			LogUtil.e("Exception" + e);
		}
	}

	public void continuePlayBgMusic() {
		if (null != mMediaPlayer && !mMediaPlayer.isPlaying()) {
			mMediaPlayer.setVolume(0.3f, 0.3f);
			mMediaPlayer.start();
		} else if (null == mMediaPlayer) {
			if (null != mDesPath) {
				playBgMusic(mDesPath);
			}
		}
	}

	public void pausePlay() {
		if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
		}
	}

	public void releseMusicPlayer() {
		if (null != mMediaPlayer) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		if (!StringUtil.isEmpty(mDesPath)) {
			if (mMediaPlayerErrorCount >= 30) {
				releseMusicPlayer();
			} else {
				mMediaPlayerErrorCount++;
				playBgMusic(mDesPath);
			}
		}
		return true;
	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int arg1) {

	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		if (mMediaPlayer != null) {
			mMediaPlayer.start();
		}
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {
		if (mMediaPlayer != null) {
			mMediaPlayer.start();
		}
	}

	public void initBgMusic(Context context) {
		mMediaPlayerErrorCount = 0;
		mDownloadManager = DownloadService.getDownloadManager(context);
		String url = UrlMgr.getBgMusicUrl();
		LVBHttpUtils.get(url, new IResponseable() {

			@Override
			public void onSuccess(String result) {
				Gson gson = new Gson();
				ArrayList<MusicItemModel> listModels = gson.fromJson(result, new TypeToken<ArrayList<MusicItemModel>>() {
				}.getType());
				if (listModels != null && listModels.size() > 0) {
					String musicBgUrl = listModels.get(0).getPlayurl();
					String musicName = musicBgUrl.substring(musicBgUrl.lastIndexOf("/")).substring(1);
					deleteMusicFile(musicName);
					if (null != musicName) {
						mDesPath = UrlMgr.getBgMusicStoragePath() + musicName;
						AuthorithMgr.getInstance().changeMode(mDesPath);
						File musicFile = new File(mDesPath);

						if (musicFile.exists()) {
							if (MusicUtil.playTag()) {
								playBgMusic(mDesPath);
							}
						} else {
							try {
								mDownloadManager.addNewDownload(musicBgUrl, musicName, mDesPath, true, false, null);
								getDownLoadMessage(musicBgUrl);
							} catch (DbException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

			@Override
			public void onFailed(String result) {

			}
		});
	}

	private void deleteMusicFile(String name) {
		String bgMusicPath = UrlMgr.getBgMusicStoragePath();
		File musicDir = new File(bgMusicPath);
		if (musicDir.exists()) {
			File[] files = musicDir.listFiles();
			for (File file : files) {
				if (file.getAbsolutePath() != (bgMusicPath + name)) {
					file.delete();
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void getDownLoadMessage(String url) {
		mDownloadInfo = mDownloadManager.getDownloadInfo(url);
		if (mDownloadInfo != null) {
			if (mDownloadInfo.getState() != HttpHandler.State.SUCCESS) {
				refresh();
				HttpHandler<File> handler = mDownloadInfo.getHandler();
				if (handler != null) {
					RequestCallBack callBack = handler.getRequestCallBack();
					if (callBack instanceof DownloadManager.ManagerCallBack) {
						DownloadManager.ManagerCallBack managerCallBack = (DownloadManager.ManagerCallBack) callBack;
						if (managerCallBack.getBaseCallBack() == null) {
							managerCallBack.setBaseCallBack(new DownloadRequestCallBack());
						}
					}
					callBack.setUserTag(new WeakReference<LVBMusicBgMediaPlayer>(this));
				}
			}
		}
	}

	public void refresh() {
		HttpHandler.State state = mDownloadInfo.getState();
		switch (state) {
		case WAITING:
		case STARTED:
		case LOADING:

			break;
		case CANCELLED:

			break;
		case SUCCESS:
			if (MusicUtil.playTag()) {
				playBgMusic(mDesPath);
			}
			try {
				mDownloadManager.removeDownload(mDownloadInfo);
			} catch (DbException e) {
				e.printStackTrace();
			}
			break;
		case FAILURE:

			break;
		default:
			break;
		}
	}

	private class DownloadRequestCallBack extends RequestCallBack<File> {

		@SuppressWarnings("unchecked")
		private void refreshListItem() {
			if (userTag == null)
				return;
			WeakReference<LVBMusicBgMediaPlayer> tag = (WeakReference<LVBMusicBgMediaPlayer>) userTag;
			LVBMusicBgMediaPlayer holder = tag.get();
			if (holder != null) {
				holder.refresh();
			}
		}

		@Override
		public void onStart() {
			refreshListItem();
		}

		@Override
		public void onLoading(long total, long current, boolean isUploading) {
			refreshListItem();
		}

		@Override
		public void onSuccess(ResponseInfo<File> responseInfo) {
			refreshListItem();
		}

		@Override
		public void onFailure(HttpException error, String msg) {
			refreshListItem();
		}

		@Override
		public void onCancelled() {
			refreshListItem();
		}
	}
}

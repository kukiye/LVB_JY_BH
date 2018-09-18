/**
 * 作者：   Johnson
 * 日期：   2014年6月18日下午3:59:43
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：LiveActivity.java
 */
package com.hhzt.iptv.ui;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.jni.SmsMediaRtpToUdp;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.adapter.LiveBackDateSelectAdapter;
import com.hhzt.iptv.lvb_x.adapter.LiveMainListAdapter;
import com.hhzt.iptv.lvb_x.adapter.LiveMainListAdapter.LiveMainHolder;
import com.hhzt.iptv.lvb_x.adapter.LiveSubListAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.commonui.DialogTwoButton;
import com.hhzt.iptv.lvb_x.commonui.MediaActionDialog;
import com.hhzt.iptv.lvb_x.commonui.SeatSelectionTipsDialog;
import com.hhzt.iptv.lvb_x.commonui.SeatSelectionTipsDialog.OnFinishListener;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.db.VodBreakPointInfo;
import com.hhzt.iptv.lvb_x.engine.SoundController;
import com.hhzt.iptv.lvb_x.interfaces.ILiveDateListSelectItemCB;
import com.hhzt.iptv.lvb_x.interfaces.ILiveMainListSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.ILiveSubListSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IMediaPlayerActionable;
import com.hhzt.iptv.lvb_x.interfaces.IOnClickListnerable;
import com.hhzt.iptv.lvb_x.interfaces.IOnVodSubSingleDataSuccess;
import com.hhzt.iptv.lvb_x.interfaces.IPaymentCheckCB;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mediaplayer.LVBMusicBgMediaPlayer;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.LiveDateMessageModel;
import com.hhzt.iptv.lvb_x.model.LiveMainModel;
import com.hhzt.iptv.lvb_x.model.LiveSubModel;
import com.hhzt.iptv.lvb_x.model.VodItemPlayMessageModel;
import com.hhzt.iptv.lvb_x.utils.CryptCore;
import com.hhzt.iptv.lvb_x.utils.DateTimeUtil;
import com.hhzt.iptv.lvb_x.utils.DeviceUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.hhzt.iptv.lvb_x.utils.TimeUtil;
import com.hhzt.iptv.lvb_x.utils.VirturlKeyPadCtr;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemSelected;

@ContentView(R.layout.fragment_mediaplayer)
public class MediaPlayerActivity extends BaseActivity implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
		OnErrorListener, OnVideoSizeChangedListener, SurfaceHolder.Callback, OnSeekBarChangeListener, OnTouchListener {

	@ViewInject(R.id.mediaplayer_title)
	private TextView mMediaNameTextView;
	@ViewInject(R.id.mediacontroller_seekbar)
	private SeekBar mMediaControllerSeekBar;
	@ViewInject(R.id.time_tips)
	private TextView mMediaResourceTime;
	@ViewInject(R.id.mediaplayer_surface)
	private SurfaceView mMediaSurfaceView;
	@ViewInject(R.id.mediaplayer_state_tips)
	private Button mMediaPlayerStateView;
	@ViewInject(R.id.mediaplayer_controller)
	private RelativeLayout mMediaControllerContainer;
	@ViewInject(R.id.live_items)
	private ListView mListMainItemListView;
	@ViewInject(R.id.live_menu_items)
	private ListView mListSubPromListView;
	@ViewInject(R.id.live_pop_window)
	private RelativeLayout mLivePopWindowView;
	@ViewInject(R.id.live_menu_items_main)
	private RelativeLayout mSubLivePopWindowView;
	@ViewInject(R.id.live_popmenu_date)
	private RelativeLayout mLiveDatePopWindowView;
	@ViewInject(R.id.live_menu_date_list)
	private ListView mListDateListView;
	@ViewInject(R.id.no_tv_list_tips)
	private LinearLayout mNoTvMessageTips;
	@ViewInject(R.id.live_channel_num)
	private TextView mLiveChannelNumTextView;
	@ViewInject(R.id.live_back_tag_image)
	private TextView mLiveBackTag;
	@ViewInject(R.id.no_singnal_container_bg)
	private RelativeLayout mLiveNosignalTips;
	@ViewInject(R.id.vod_sound_seekbar)
	private SeekBar mVodSoundControllerSeekBar;
	@ViewInject(R.id.live_sound_seekbar)
	private SeekBar mLiveSoundControllerSeekBar;
	@ViewInject(R.id.media_playpause_controller)
	private Button mMediaPlayPauseControllerButton;
	@ViewInject(R.id.live_sound_controller_container)
	private LinearLayout mLiveMediaPlaySoundController;
	private int mVideoWidth;
	private int mVideoHeight;
	public int mMediaType;
	private int mTimeRecord;
	public String mMediaPath;
	private String mMediaName;
	private long mFocusPlayTime = 0;
	private int mVodCurrentPosition;
	private Bundle mMediaExtrasBundle;
	private SurfaceHolder mHolder;
	public MediaPlayer mMediaPlayer;
	private int mLiveMainChannelIndex;
	private int mLiveCurrentChannelNumber;
	private boolean mIsShowController = false;
	private boolean mIsVideoSizeKnown = false;
	private boolean mIsVideoReadyToBePlayed = false;
	private ArrayList<LiveMainModel> mLiveMainModels;
	private ArrayList<LiveSubModel> mLiveSubModels;
	private ArrayList<LiveDateMessageModel> mLiveDateModels;
	//
	private LiveMainListAdapter mLiveMainListAdapter;
	private ArrayList<VodItemPlayMessageModel> mVodDataModels;
	private MediaUpdateHandler mMediaPlayerHandler;

	private static final int TRAG_NO = 0;
	private static final int TRAG_LEFT = 1;
	private static final int TRAG_RIGHT = 2;
	private static final int TRAG_SPEED = 200;
	private int mTragActionType = TRAG_NO;
	private int mCurrentMediaCurrentTime;
	private int mCurrentMediaTotalTime;

	private int mLiveOsdPopWindowShowTag = Constant.IPTV_LIVE_OSD_HIDE;
	private DbUtils mDb;
	private VodBreakPointInfo mBreakPointInfo;
	private int mMovieId;
	private int mVodMediaPlayerState;
	private int mVodProgrameId;
	private int mMediaMovieId;
	private String mVodMediaPrice;
	private String mVodFeeMediaName;
	private String mVodRemoteSeekProgress;
	// 临时的主列表位置
	private int mTempMainListPosition = 0;
	// 临时的子列表位置
	private int mTempSubListPosition = 0;
	// 临时记录回看天数
	private long mTempLiveBackDayNum = 0;
	// 判断回看天数是否需要更新
	private boolean mNeedUpdateDayNumTag = false;
	private int mMediaPlayerErrorCount;
	private String mLiveChannelTextShow = "";
	private LiveBackDateSelectAdapter mBackDateAdapter;
	private LiveSubListAdapter mSubListAdapter;
	private static MediaPlayerActivity mInstanceActivity;

	private String mCurrentBackAbleTag;
	private String mCurrentShowDate;
	private int mCurrentChannelId;
	// 判断当前时间列表所处位置
	private String mCurrentDate = Constant.IPTV_LIVE_BACK_DEFAULT_DATE;

	private IntentFilter mNetIntentFilter;
	private NetChangeBroadcast mNetChangeBroadcast;
	private MediaActionDialog mMediaActionDialog;
	private SeatSelectionTipsDialog mSeatSelectionTipsDialog;
	//开始播放时间
	private long mVodRecordCurrentTime = 0;
	//当前播放的URL地址
	private String mVodRecordUrl = null; 

	@OnClick({ R.id.mediaplayer_state_tips, R.id.media_playpause_controller })
	private void onClick(View view) {
		switch (mVodMediaPlayerState) {
		case Constant.IPTV_MEDIAPLAYER_STATE_PLAYING:
			pauseVodMedia();
			break;
		case Constant.IPTV_MEDIAPLAYER_STATE_PAUSING:
			playVodMedia();
			break;
		default:
			break;
		}
	}

	public boolean isForcePlayTag() {
		boolean tag = false;
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_FORCE) {
			tag = true;
		}
		return tag;
	}

	public static MediaPlayerActivity getInstance() {
		return mInstanceActivity;
	}

	public int getCurrentMediaType() {
		return mMediaType;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstanceActivity = this;
		mVodMediaPlayerState = Constant.IPTV_MEDIAPLAYER_STATE_PLAYING;
		mNetChangeBroadcast = new NetChangeBroadcast();
		mNetIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		mNetIntentFilter.addAction(Constant.IPTV_LVB_X_BROADCAST_MSG_HSJQ_SEAT);
		mMediaPlayerHandler = new MediaUpdateHandler(this);
		mLiveMainListAdapter = new LiveMainListAdapter(this);
		mHolder = mMediaSurfaceView.getHolder();
		mHolder.addCallback(this);

		registerReceiver(mNetChangeBroadcast, mNetIntentFilter);
		setMediaBaseValueInfo(getIntent());
		setAllListners();
		setBannerVisiable();
		setLiveOsdVisiable();
		setMediaInfos();
		showController();
		checkNeedPayTips();
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveLiveChannel();
		saveVodSqlite();
		closeDatabase();

		removeAllHandlerMsg();
		doCleanUp();
		releaseMediaPlayer();
	}

	@Override
	protected void onResume() {
		super.onResume();
		LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
		playMedia();
	}

	@Override
	protected void onStop() {
		super.onStop();
		releaseSeatDialog();
		unregisterReceiverBroadcast();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		saveLiveChannel();
		saveVodSqlite();
		closeDatabase();
	}

	private void saveLiveChannel() {
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
			UserMgr.setSavedLiveChannelNumber(mLiveCurrentChannelNumber);
		}
	}

	private void saveVodSqlite() {
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD) {
			setSqlite();
		}
	}

	private void closeDatabase() {
		if (null != mDb && mDb.getDatabase().isOpen()) {
			mDb.close();
		}
	}

	public void releaseSeatDialog() {
		if (null != mSeatSelectionTipsDialog) {
			mSeatSelectionTipsDialog.dismiss();
		}
	}

	private void unregisterReceiverBroadcast() {
		if (null != mNetChangeBroadcast) {
			unregisterReceiver(mNetChangeBroadcast);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		LogUtil.d("wujichang----onSaveInstanceState");
		hideLiveOsdMainItemList();
		finish();
	}

	private void playVideo(String url) {
		if (StringUtil.isEmpty(url)) {
			return;
		}
		mLiveNosignalTips.setVisibility(View.GONE);
		mMediaPlayerErrorCount = 0;
		mCurrentMediaTotalTime = 0;
		mCurrentMediaCurrentTime = 0;
		url = CryptCore.decryptByDES(url);
		LogUtil.d("playVideo--->url=" + url);
		try {
			// doCleanUp();
			if (null == mMediaPlayer) {
				mMediaPlayer = new MediaPlayer();
			}
			mMediaPlayer.reset();
			// 海思芯片时最rtp组播进行判断处理 暂时不支持rtp单播 jonson 20140227 begin
			String selfMediaUrl = "";
			if (isRtpMediaType(url)) {
				// 初始化判断
				selfMediaUrl = getSelfMediaUrl();
				if (!mediaTypeAction(url, selfMediaUrl, SmsMediaRtpToUdp.MEDIA_INIT)) {
					return;
				}

				mMediaPlayer.setDataSource(selfMediaUrl);
			} else {
				mMediaPlayer.setDataSource(url);
			}
			// 海思芯片时最rtp组播进行判断处理 暂时不支持rtp单播 jonson 20140227 end

			mMediaPlayer.setDisplay(mHolder);
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnErrorListener(this);
			mMediaPlayer.setOnVideoSizeChangedListener(this);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

			checkMediaPlayerStateBug();
			setDb();
			setLiveChannelNumText();
		} catch (Exception e) {
			LogUtil.e("error: " + e);
		}
	}
	
	/**
	 * 当前播放器URL地址播放的时间
	 */
	private void vodRecordTime(){
//		mVodRecordCurrentTime = 50;
		if(mVodRecordCurrentTime > 0){
			long watchtime = System.currentTimeMillis() - mVodRecordCurrentTime;
			
			if(mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE){
				//json/vodrecord/{username}/{programid}/{watchtime}
				UIDataller.getDataller().getLineRecordTime(this, mLiveCurrentChannelNumber, watchtime);
			}else if(mMediaType == Constant.IPTV_MEDIA_TYPE_VOD){
				//json/vodrecord/tv/{username}/{channelid}/{watchtime}
				UIDataller.getDataller().getVodRecordTime(this, mMediaMovieId, watchtime);
			}
		}
		mVodRecordCurrentTime = 0;
	}

	private void setMediaPlayerState(int stateType) {
		switch (stateType) {
		case Constant.IPTV_MEDIAPLAYER_STATE_PAUSING:
			mVodMediaPlayerState = stateType;
			if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD || mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
				mMediaPlayerStateView.setVisibility(View.VISIBLE);
			}
			mMediaPlayPauseControllerButton.setBackgroundResource(R.drawable.window_play_selector);
			break;
		case Constant.IPTV_MEDIAPLAYER_STATE_DEFAULT:
		case Constant.IPTV_MEDIAPLAYER_STATE_PLAYING:
			mVodMediaPlayerState = stateType;
			mMediaPlayerStateView.setVisibility(View.GONE);
			mMediaPlayPauseControllerButton.setBackgroundResource(R.drawable.window_pause_selector);
			break;
		default:
			break;
		}
	}

	private void setBannerVisiable() {
		switch (mMediaType) {
		case Constant.IPTV_MEDIA_TYPE_LIVE:
			mMediaNameTextView.setVisibility(View.GONE);
			mMediaPlayerStateView.setVisibility(View.GONE);
			mMediaControllerContainer.setVisibility(View.GONE);
			mMediaControllerSeekBar.setProgress(0);
			break;
		case Constant.IPTV_MEDIA_TYPE_VOD:
			mMediaNameTextView.setVisibility(View.VISIBLE);
			mMediaPlayerStateView.setVisibility(View.GONE);
			mMediaControllerContainer.setVisibility(View.VISIBLE);
			break;
		case Constant.IPTV_MEDIA_TYPE_LIVE_BACK:
			mMediaNameTextView.setVisibility(View.GONE);
			mMediaPlayerStateView.setVisibility(View.GONE);
			mMediaControllerContainer.setVisibility(View.GONE);
			mMediaControllerSeekBar.setProgress(0);
			break;
			case Constant.IPTV_MEDIA_TYPE_LIVE_FORCE:
				mMediaNameTextView.setVisibility(View.GONE);
				mMediaPlayerStateView.setVisibility(View.GONE);
				mMediaControllerContainer.setVisibility(View.GONE);
				mMediaControllerSeekBar.setProgress(0);
				break;
		default:
			break;
		}
	}

	private void setLiveOsdVisiable() {
		switch (mMediaType) {
		case Constant.IPTV_MEDIA_TYPE_VOD:
		case Constant.IPTV_MEDIA_TYPE_LIVE_BACK:
			hideLiveOsdMainItemList();
			break;
		case Constant.IPTV_MEDIA_TYPE_LIVE:
			switch (Config.LvbDeviceType) {
			// 目前只有皇室假期的BOX端需要做特殊处理
			case Constant.DEVICE_TYPE_BOX_HSJQ:
				hideLiveOsdMainItemList();
				break;
			default:
				showLiveOsdMainItemList();
				break;
			}
			break;
			case Constant.IPTV_MEDIA_TYPE_LIVE_FORCE:
				if (SystemMgr.getMediaLockTag()) {
					hideLiveOsdMainItemList();
				} else {
					showLiveOsdMainItemList();
				}
				break;
		default:
			break;
		}
	}

	private void setMediaName() {
		if (null != mMediaNameTextView) {
			mMediaNameTextView.setText(mMediaName);
		}
	}

	// 点播媒体音量百分比显示
	private void setVodMediaSoundPercent() {
		SoundController ller = new SoundController(getApplication());
		int currentSoundValue = ller.getStreamVolumeCurrent();
		int maxSoundValue = ller.getStreamVolumeMax();
		mVodSoundControllerSeekBar.setMax(maxSoundValue);
		mVodSoundControllerSeekBar.setProgress(currentSoundValue);
	}

	// 直播媒体音量百分比显示
	private void setLiveMediaSoundPercent() {
		SoundController ller = new SoundController(getApplication());
		int currentSoundValue = ller.getStreamVolumeCurrent();
		int maxSoundValue = ller.getStreamVolumeMax();
		mLiveSoundControllerSeekBar.setMax(maxSoundValue);
		mLiveSoundControllerSeekBar.setProgress(currentSoundValue);
	}

	// 媒体时间和进度条显示
	private void setMediaControllerPercent() {
		if (null != mMediaPlayer && mTragActionType == TRAG_NO && mMediaPlayer.isPlaying()) {
			LogUtil.d("setMediaControllerPercent begin...");
			// 获取媒体时间信息
			int position = mMediaPlayer.getCurrentPosition();
			int duration = mMediaPlayer.getDuration();

			// 保存当前媒体播放时长和总时长
			mCurrentMediaCurrentTime = position;
			mCurrentMediaTotalTime = duration;

			// 防止媒体未播放时，拖动导致异常
			if (mCurrentMediaCurrentTime > mCurrentMediaTotalTime) {
				return;
			}

			// 媒体进度
			LogUtil.d("percent=" + position);
			mMediaControllerSeekBar.setMax(duration);
			mMediaControllerSeekBar.setProgress(position);

			// 媒体时间
			setMediaTime(position, duration);
			LogUtil.d("setMediaControllerPercent end...");
		}
	}

	// 设置托拉进度值
	private void setTragPercent() {
		if (null != mMediaPlayer) {
			// 防止媒体未播放时，拖动导致异常
			if (mCurrentMediaCurrentTime > mCurrentMediaTotalTime) {
				return;
			}
			if (mTragActionType == TRAG_LEFT) {
				mCurrentMediaCurrentTime -= getMediaTragSpeed();
			} else if (mTragActionType == TRAG_RIGHT) {
				mCurrentMediaCurrentTime += getMediaTragSpeed();
			}

			// 设置seekBar、mediaPlayer的进度条时间
			mMediaPlayer.seekTo(mCurrentMediaCurrentTime);
			mMediaControllerSeekBar.setProgress(mCurrentMediaCurrentTime);
			
			if(mCurrentMediaCurrentTime<0){
				mCurrentMediaCurrentTime=0;
			}

			// 媒体时间
			setMediaTime(mCurrentMediaCurrentTime, mCurrentMediaTotalTime);
		}
	}

	// 获取媒体托拉速度
	private int getMediaTragSpeed() {
		int speed = 0;
		if (null != mMediaPlayer) {
			int duration = mMediaPlayer.getDuration();
			speed = duration / TRAG_SPEED;
		}

		return speed > 5000 ? speed : 5000;
	}

	private void setMediaTime(double position, double duration) {
		String currentTimeString = StringUtil.generateTime(position);
		String totalTimeString = StringUtil.generateTime(duration);
		String mediaTimeString = currentTimeString + File.separator + totalTimeString;
		mMediaResourceTime.setText(mediaTimeString);
	}

	private void setMediaInfos() {
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
			setLiveMediaSoundPercent();
		} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD || mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
			setMediaName();
			setVodMediaSoundPercent();
			setMediaControllerPercent();
		}
	}

	/**
	 * 播放某个频道号电视台
	 */
	private void setLiveChannelNumText() {
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_MEDIAPLAYER_LIVE_CHANNEL);
			mLiveChannelNumTextView.setVisibility(View.VISIBLE);
			mLiveChannelNumTextView.setText(mLiveCurrentChannelNumber + "");
			mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_MEDIAPLAYER_LIVE_CHANNEL, Constant.IPTV_TIME_SIX_SECONDES);
		}
	}

	private void setDb() {
		if (mMediaType != Constant.IPTV_MEDIA_TYPE_VOD) {
			return;
		}
		if (null != mVodDataModels && mVodDataModels.size() > 0) {
			resumeVodProgressPlay(mVodDataModels);
		} else {
			UIDataller.getDataller().getVodSubSingleItems(this, mVodProgrameId, new IOnVodSubSingleDataSuccess() {

				@Override
				public void onSuccess(ArrayList<VodItemPlayMessageModel> models) {
					mVodDataModels = models;
					resumeVodProgressPlay(mVodDataModels);
				}
			});
		}
	}

	private void resumeVodProgressPlay(ArrayList<VodItemPlayMessageModel> models) {
		if (StringUtil.isEmpty(mVodRemoteSeekProgress)) {
			mMovieId = mVodDataModels.get(mVodCurrentPosition).getId();
			try {
				mBreakPointInfo = mDb.findFirst(Selector.from(VodBreakPointInfo.class).where("movieId", "=", mMovieId));
			} catch (DbException e) {
				e.printStackTrace();
			}
		} else {
			int progress = Integer.valueOf(mVodRemoteSeekProgress);
			if (null != mMediaControllerSeekBar) {
				mMediaControllerSeekBar.setProgress(progress);
			}
		}

	}

	private void setAllListners() {
		mMediaControllerSeekBar.setOnSeekBarChangeListener(this);
		mVodSoundControllerSeekBar.setOnSeekBarChangeListener(this);
		mLiveSoundControllerSeekBar.setOnSeekBarChangeListener(this);
		mListMainItemListView.setOnTouchListener(this);
		mListSubPromListView.setOnTouchListener(this);
		mListDateListView.setOnTouchListener(this);
	}

	private void pauseMediaPlayer() {
		if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
		}
	}

	private void resumeMediaPlayer() {
		if (null != mMediaPlayer && !mMediaPlayer.isPlaying()) {
			mMediaPlayer.start();
		}
	}

	private void playMedia() {
		if (null != mMediaSurfaceView) {
			mMediaSurfaceView.setVisibility(View.GONE);
			mMediaSurfaceView.setVisibility(View.VISIBLE);
			mMediaSurfaceView.invalidate();
		}
	}

	private void showController() {
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {

		} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD || mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
			setMediaInfos();
			mTimeRecord = 0;
			mIsShowController = true;
			mMediaNameTextView.setVisibility(View.VISIBLE);
			mMediaControllerContainer.setVisibility(View.VISIBLE);
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_UPDATE_TIME_FREQUENCE);
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_UPDATE_CONTROLLER_WAIT);
			mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_UPDATE_TIME_FREQUENCE, Constant.IPTV_TIME_ONE_SECONDE);
			mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_UPDATE_CONTROLLER_WAIT, Constant.IPTV_TIME_ONE_SECONDE);
		}
	}

	private void hideController() {
		mIsShowController = false;
		mMediaPlayerStateView.setVisibility(View.GONE);
		mMediaNameTextView.setVisibility(View.GONE);
		mMediaControllerContainer.setVisibility(View.GONE);
	}

	private void showLiveOsdMainItemList() {
		mLiveOsdPopWindowShowTag = Constant.IPTV_LIVE_OSD_MAIN_SHOW;
		mLivePopWindowView.setVisibility(View.VISIBLE);
		mListMainItemListView.requestLayout();
		mListMainItemListView.requestFocus();
		mListMainItemListView.setSelection(mLiveMainChannelIndex);
		mSubLivePopWindowView.setVisibility(View.GONE);
		mLiveDatePopWindowView.setVisibility(View.GONE);
		mLiveMediaPlaySoundController.setVisibility(View.VISIBLE);
		setLiveMainListDatas();
		setLiveMediaSoundPercent();
		mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD);
		mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD, Constant.IPTV_TIME_SIX_SECONDES);
	}

	private void hideLiveOsdMainItemList() {
		mLiveOsdPopWindowShowTag = Constant.IPTV_LIVE_OSD_HIDE;
		mLivePopWindowView.setVisibility(View.GONE);
		mSubLivePopWindowView.setVisibility(View.GONE);
		mLiveDatePopWindowView.setVisibility(View.GONE);
		mLiveMediaPlaySoundController.setVisibility(View.GONE);
	}

	private void showLiveOsdSubItemList() {
		mLiveOsdPopWindowShowTag = Constant.IPTV_LIVE_OSD_SUB_SHOW;
		int selectionIndex = mListMainItemListView.getSelectedItemPosition();
		mTempMainListPosition = selectionIndex;
		// 防止数组索引越界、selectionIndex出现-1的情况
		if (selectionIndex >= 0 && selectionIndex < mListMainItemListView.getCount()) {
			if (null != mLiveMainModels && mLiveMainModels.size() > 0) {
				int channelId = mLiveMainModels.get(selectionIndex).getId();
				String isBackShowTag = mLiveMainModels.get(selectionIndex).getTimeshift();
				long time = mLiveMainModels.get(selectionIndex).getTimeshiftduration();
				setLiveSubListDatas(channelId, isBackShowTag, time);
			}
		}
		mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD);
		mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD, Constant.IPTV_TIME_SIX_SECONDES);
	}

	private void showLiveOsdSubListForTouch() {
		int selectionIndex = mLiveMainChannelIndex;
		// mTempMainListPosition = selectionIndex;
		// 防止数组索引越界、selectionIndex出现-1的情况
		if (selectionIndex >= 0 && selectionIndex < mListMainItemListView.getCount()) {
			if (null != mLiveMainModels && mLiveMainModels.size() > 0) {
				int channelId = mLiveMainModels.get(selectionIndex).getId();
				String isBackShowTag = mLiveMainModels.get(selectionIndex).getTimeshift();
				long time = mLiveMainModels.get(selectionIndex).getTimeshiftduration();
				setLiveSubListDatas(channelId, isBackShowTag, time);
			}
		}
		mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD);
		mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD, Constant.IPTV_TIME_SIX_SECONDES);
	}

	private void hideLiveOsdSubItemList() {
		mLiveOsdPopWindowShowTag = Constant.IPTV_LIVE_OSD_MAIN_SHOW;
		mSubLivePopWindowView.setVisibility(View.GONE);
		mLiveDatePopWindowView.setVisibility(View.GONE);
		mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD);
		mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD, Constant.IPTV_TIME_SIX_SECONDES);
	}

	private void setLiveMainListDatas() {
		// 判断mListMainItemListView已经设置了适配器数据后直接进行显示
		if (null != mLiveMainModels && mLiveMainModels.size() > 0) {
			setLiveMainListActionData(mLiveMainModels);
		} else {
			UIDataller.getDataller().setLiveMainList(new ILiveMainListSuccessCB() {

				@Override
				public void setListDatas(final ArrayList<LiveMainModel> listModels) {
					mLiveMainModels = listModels;
					setLiveMainListActionData(listModels);
				}
			});
		}
	}

	private void setLiveMainListActionData(final ArrayList<LiveMainModel> listModels) {
		if (listModels != null && listModels.size() > 0) {
			mLiveMainListAdapter.setLiveMainModels(listModels);
			if (null == mListMainItemListView.getAdapter()) {
				mListMainItemListView.setAdapter(mLiveMainListAdapter);
			} else {
				mLiveMainListAdapter.notifyDataSetChanged();
			}
			mListMainItemListView.setSelection(mLiveMainChannelIndex);

			mListMainItemListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					mCurrentDate = Constant.IPTV_LIVE_BACK_DEFAULT_DATE;
					if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
						mLiveMainChannelIndex = arg2;
						mLiveCurrentChannelNumber = listModels.get(arg2).getChannelnumber();
						UserMgr.setSavedLiveChannelNumber(mLiveCurrentChannelNumber);
						returnToLiveActivity();
					} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
						hideLiveOsdMainItemList();
						if (arg2 == mLiveMainChannelIndex) {
							return;
						} else {
							mLiveCurrentChannelNumber = listModels.get(arg2).getChannelnumber();
							UserMgr.setSavedLiveChannelNumber(mLiveCurrentChannelNumber);
							mLiveMainChannelIndex = arg2;
							mMediaPath = listModels.get(arg2).getChannelurl();
							// playVideo(mMediaPath);
							playMedia();
						}
					}
				}
			});

			mListMainItemListView.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD);
					mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD, Constant.IPTV_TIME_SIX_SECONDES);

					hideLiveOsdSubItemList();
					int currentIndex = arg0.getSelectedItemPosition();
					int firstVisiableIndex = arg0.getFirstVisiblePosition();
					int index = currentIndex - firstVisiableIndex;

					for (int i = 0; i < arg0.getChildCount(); i++) {
						View view = arg0.getChildAt(i);
						LiveMainListAdapter.LiveMainHolder holder = (LiveMainHolder) view.getTag();
						if (index == i) {
							holder.channelChoosedTag.setVisibility(View.VISIBLE);
						} else {
							holder.channelChoosedTag.setVisibility(View.INVISIBLE);
						}
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
		} else if (listModels != null && listModels.size() == 0) {

		} else {

		}
	}

	private void setLiveSubListDatas(final int channelId, final String isBackShowTag, final long time) {
		mCurrentChannelId = channelId;
		mTempLiveBackDayNum = time;
		mNeedUpdateDayNumTag = true;
		mSubListAdapter = new LiveSubListAdapter(this);
		UIDataller.getDataller().setLiveSubList(channelId, 1, Constant.LIVE_PRE_LIST_NUMBER, new ILiveSubListSuccessCB() {

			@Override
			public void setListDatas(ArrayList<LiveSubModel> listModels) {
				mLiveSubModels = listModels;
				int currentMainId = mLiveMainModels.get(mLiveMainChannelIndex).getId();
				mSubListAdapter.setDataList(mLiveSubModels);
				mSubListAdapter.initAdapter(mMediaType, mMediaPath, currentMainId, isBackShowTag);
				mListSubPromListView.setAdapter(mSubListAdapter);
				mLivePopWindowView.setVisibility(View.VISIBLE);
				mSubLivePopWindowView.setVisibility(View.VISIBLE);
				if (mLiveSubModels.size() > 0) {
					mNoTvMessageTips.setVisibility(View.GONE);
				} else {
					mNoTvMessageTips.setVisibility(View.VISIBLE);
				}

				mListSubPromListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						if (isBackShowTag.equalsIgnoreCase("1")) {
							clickSubList(mLiveSubModels.get(arg2).getPlaystatus(), mLiveSubModels.get(arg2).getTvodurl(), arg2, channelId);
						} else {

						}
					}
				});

				mListSubPromListView.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD);
						mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD, Constant.IPTV_TIME_SIX_SECONDES);
						if (isBackShowTag.equalsIgnoreCase("1")) {
							subListItemSelected(arg2, Long.valueOf(mLiveSubModels.get(arg2).getStarttime()), mLiveSubModels.get(arg2).getPlaystatus());
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
				setSubListFocus(channelId, currentMainId);
			}
		});
	}

	/**
	 * 节目列表焦点的处理
	 * 
	 * @param channelId
	 * @param currentMainId
	 */
	private void setSubListFocus(int channelId, int currentMainId) {
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
			for (int i = 0; i < mLiveSubModels.size(); i++) {
				if (Constant.LIVE_PLAYING_TAG.equalsIgnoreCase(mLiveSubModels.get(i).getPlaystatus())) {
					int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
					int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
					mSubLivePopWindowView.measure(w, h);
					int height = mSubLivePopWindowView.getMeasuredHeight();
					mListSubPromListView.setSelectionFromTop(i, height / 2 + 100);
					break;
				}
			}
		} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
			if (currentMainId != channelId) {
				for (int i = 0; i < mLiveSubModels.size(); i++) {
					if (Constant.LIVE_PLAYING_TAG.equalsIgnoreCase(mLiveSubModels.get(i).getPlaystatus())) {
						int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
						int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
						mSubLivePopWindowView.measure(w, h);
						int height = mSubLivePopWindowView.getMeasuredHeight();
						mListSubPromListView.setSelectionFromTop(i, height / 2 + 100);
						break;
					}
				}
			} else {
				for (int i = 0; i < mLiveSubModels.size(); i++) {
					if (mMediaPath.equalsIgnoreCase(mLiveSubModels.get(i).getTvodurl())) {
						int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
						int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
						mSubLivePopWindowView.measure(w, h);
						int height = mSubLivePopWindowView.getMeasuredHeight();
						mListSubPromListView.setSelectionFromTop(i, height / 2 + 100);
						break;
					}
				}
			}
		}
		mListSubPromListView.requestLayout();
		mListSubPromListView.requestFocus();
	}

	/**
	 * 选中节目列表时对时间列表的处理
	 * 
	 * @param position
	 * @param milliseconds
	 * @param tag
	 */
	private void subListItemSelected(int position, final long milliseconds, String tag) {
		mTempSubListPosition = position;
		if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_SUB_SHOW) {
			mCurrentShowDate = DateTimeUtil.toTime(milliseconds, DateTimeUtil.DATE_FORMATE_MONTH_DAY);
		}
		mCurrentBackAbleTag = tag;
		if (null == mLiveDateModels) {
			mLiveDateModels = new ArrayList<LiveDateMessageModel>();
		}
		initDateList(milliseconds);
		if (Constant.LIVE_BACK_ABLE_TAG.equalsIgnoreCase(tag) || Constant.LIVE_BACK_UNABLE_TAG.equalsIgnoreCase(tag)) {
			if (null != mBackDateAdapter) {
				String date = mBackDateAdapter.getSelectDate();
				if (!mCurrentDate.equalsIgnoreCase(date)) {
					mBackDateAdapter.setSelectDate(mCurrentDate);
					mBackDateAdapter.notifyDataSetChanged();
				}
			}
			mLiveDatePopWindowView.setVisibility(View.VISIBLE);
		} else {
			mLiveDatePopWindowView.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 设置时间列表
	 * 
	 * @param milliseconds
	 */
	private void initDateList(long milliseconds) {
		if (mNeedUpdateDayNumTag) {
			mNeedUpdateDayNumTag = false;
			mLiveDateModels.clear();
			String currentMonthDay = DateTimeUtil.toTime(milliseconds, DateTimeUtil.DATE_FORMATE_MONTH_DAY);
			String currentDate = DateTimeUtil.toTime(milliseconds, DateTimeUtil.DATE_DEFAULT_FORMATE);
			int dayNum = TimeUtil.getDayNum(mTempLiveBackDayNum);
			for (int i = 0; i < dayNum; i++) {
				LiveDateMessageModel liveDateMessageModel = new LiveDateMessageModel();
				liveDateMessageModel.setWeek(DateTimeUtil.getWeekTime(0 - i, milliseconds));
				liveDateMessageModel.setDate(DateTimeUtil.getSpecifiedDayBefore(currentDate, DateTimeUtil.DATE_DEFAULT_FORMATE, i));
				liveDateMessageModel.setMonthDay(DateTimeUtil.getSpecifiedDayBefore(currentMonthDay, DateTimeUtil.DATE_FORMATE_MONTH_DAY, i));
				mLiveDateModels.add(liveDateMessageModel);
			}
			if (null != mLiveDateModels && mLiveDateModels.size() > 0) {
				if (null == mBackDateAdapter) {
					mBackDateAdapter = new LiveBackDateSelectAdapter(mInstanceActivity, mLiveDateModels);
					mListDateListView.setAdapter(mBackDateAdapter);
				} else {
					mBackDateAdapter.notifyDataSetChanged();
				}

				mListDateListView.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View view, int keyCode, KeyEvent event) {
						switch (keyCode) {
						case KeyEvent.KEYCODE_DPAD_DOWN:
							if (event.getAction() == KeyEvent.ACTION_DOWN) {
								int lastPosition = mListDateListView.getLastVisiblePosition();
								int selectIndex = mListDateListView.getSelectedItemPosition();
								if (selectIndex >= lastPosition) {
									mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD);
									mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD,
											Constant.IPTV_TIME_SIX_SECONDES);
									return true;
								}
							}
							break;
						case KeyEvent.KEYCODE_DPAD_UP:
							if (event.getAction() == KeyEvent.ACTION_DOWN) {
								int currentSecondListIndex = mListDateListView.getSelectedItemPosition();
								if (currentSecondListIndex <= 0) {
									mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD);
									mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD,
											Constant.IPTV_TIME_SIX_SECONDES);
									return true;
								}
							}
							break;
						default:
							break;
						}
						return false;
					}
				});
			}
		}
	}

	/**
	 * 时间列表选中时的处理
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	@OnItemSelected(R.id.live_menu_date_list)
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD);
		mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD, Constant.IPTV_TIME_SIX_SECONDES);
		if (!mLiveDateModels.get(arg2).getMonthDay().equalsIgnoreCase(mCurrentShowDate)) {
			mCurrentShowDate = mLiveDateModels.get(arg2).getMonthDay();
			UIDataller.getDataller().updateSubList(mInstanceActivity, mCurrentChannelId, mLiveDateModels.get(arg2).getDate(), 1,
					Constant.LIVE_BACK_PRE_LIST_NUMBER, new ILiveDateListSelectItemCB() {

						@Override
						public void onDateItemSelectCB(ArrayList<LiveSubModel> models) {
							mLiveSubModels.clear();
							mLiveSubModels = models;
							if (null != mLiveDateModels) {
								mSubListAdapter.setDataList(mLiveSubModels);
								mSubListAdapter.notifyDataSetChanged();
								mListSubPromListView.setSelection(0);
								mTempSubListPosition = 0;
							}
						}
					});
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	private int getLiveIndex(int id) {
		int liveIndex = 0;
		for (int i = 0; i < mLiveMainModels.size(); i++) {
			if (id == mLiveMainModels.get(i).getId()) {
				liveIndex = i;
				break;
			}
		}
		return liveIndex;
	}

	/**
	 * 点击节目子列表
	 * 
	 * @param tag
	 * @param url
	 * @param index
	 * @param channelId
	 */
	private void clickSubList(String tag, String url, int index, int channelId) {
		hideLiveOsdMainItemList();
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
			playVodMedia();
		}
		if (Constant.LIVE_PLAYING_TAG.equalsIgnoreCase(tag)) {
			mCurrentDate = Constant.IPTV_LIVE_BACK_DEFAULT_DATE;
			int liveIndex = getLiveIndex(channelId);
			if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
				if (mLiveMainChannelIndex != liveIndex) {
					mLiveMainChannelIndex = liveIndex;
					String liveUrl = mLiveMainModels.get(mLiveMainChannelIndex).getChannelurl();
					mMediaPath = liveUrl;
					mLiveCurrentChannelNumber = mLiveMainModels.get(mLiveMainChannelIndex).getChannelnumber();
					UserMgr.setSavedLiveChannelNumber(mLiveCurrentChannelNumber);
					playVideo(mMediaPath);
				}
			} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
				mLiveMainChannelIndex = liveIndex;
				String liveUrl = mLiveMainModels.get(mLiveMainChannelIndex).getChannelurl();
				mMediaPath = liveUrl;
				mLiveCurrentChannelNumber = mLiveMainModels.get(mLiveMainChannelIndex).getChannelnumber();
				UserMgr.setSavedLiveChannelNumber(mLiveCurrentChannelNumber);
				returnToLiveActivity();
			}
		} else if (Constant.LIVE_BACK_ABLE_TAG.equalsIgnoreCase(tag)) {
			if (!StringUtil.isEmpty(url)) {
				if (!mMediaPath.equalsIgnoreCase(url)) {
					mCurrentDate = DateTimeUtil.toTime(Long.valueOf(mLiveSubModels.get(index).getStarttime()), DateTimeUtil.DATE_FORMATE_MONTH_DAY);
					Intent intent = new Intent(mInstanceActivity, MediaPlayerActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt(Constant.IPTV_MEDIA_TYPE, Constant.IPTV_MEDIA_TYPE_LIVE_BACK);
					bundle.putString(Constant.IPTV_MEDIA_URL, url);
					bundle.putInt(Constant.IPTV_LIVE_CHANNEL_INDEX, getLiveIndex(channelId));
					intent.putExtra(Constant.IPTV_MEDIA_DATA_TAG, bundle);
					startActivity(intent);
				}
			} else {
				BaseActivity.getInstance().showToast(mInstanceActivity.getString(R.string.live_back_data_null), Toast.LENGTH_LONG);
			}
		}
	}

	private void nextLivePopupWindowShow() {
		if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_MAIN_SHOW) {
			showLiveOsdSubItemList();
		} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_SUB_SHOW) {
			setDateListSelect();
		}
	}

	/**
	 * 设置时间列表的选中项
	 */
	private void setDateListSelect() {
		if (null != mCurrentBackAbleTag) {
			if (mCurrentBackAbleTag.equalsIgnoreCase(Constant.LIVE_BACK_UNABLE_TAG)
					|| mCurrentBackAbleTag.equalsIgnoreCase(Constant.LIVE_BACK_ABLE_TAG)) {
				if (null != mLiveDateModels && mLiveDateModels.size() > 0) {
					for (int i = 0; i < mLiveDateModels.size(); i++) {
						if (mLiveDateModels.get(i).getMonthDay().equalsIgnoreCase(mCurrentShowDate)) {
							mListDateListView.requestLayout();
							mListDateListView.requestFocus();
							mListDateListView.setSelection(i);
							mLiveOsdPopWindowShowTag = Constant.IPTV_LIVE_OSD_DATE_SHOW;
							break;
						}
					}
				}
			}
		}
	}

	private void preLiveHistoryWindowShow() {
		if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_MAIN_SHOW || mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_SUB_SHOW
				|| mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_DATE_SHOW) {
			hideLiveOsdMainItemList();
		} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_HIDE) {
			doCleanUp();
			releaseMediaPlayer();
			finish();
		}
	}

	private void actionToOsdShow(int keycode) {
		switch (keycode) {
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			nextLivePopupWindowShow();
			break;
		case KeyEvent.KEYCODE_BACK:
			vodRecordTime();
			preLiveHistoryWindowShow();
			break;
		default:
			break;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		LogUtil.d("surfaceChanged called");
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		LogUtil.d("surfaceCreated called");
		playVideo(mMediaPath);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		LogUtil.d("surfaceDestroyed called");
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		LogUtil.d("onVideoSizeChanged called");
		if (width == 0 || height == 0) {
			LogUtil.e("invalid video width(" + width + ") or height(" + height + ")");
			return;
		}
		mIsVideoSizeKnown = true;
		mVideoWidth = width;
		mVideoHeight = height;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {
		LogUtil.d("onPrepared called");
		mIsVideoReadyToBePlayed = true;

		// if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
		startVideoPlayback();
		mVodRecordCurrentTime = System.currentTimeMillis();
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD) {
			switch (Config.LvbDeviceType) {
			case Constant.DEVICE_TYPE_BOX_HSJQ:

				break;
			default:
				if (StringUtil.isEmpty(mVodRemoteSeekProgress)) {
					if (mBreakPointInfo != null) {
						String currentTime = mBreakPointInfo.getCurrentTime();
						mMediaControllerSeekBar.setMax(mBreakPointInfo.getDurationTime());
						mMediaControllerSeekBar.setProgress(mBreakPointInfo.getProgress());
						if (!StringUtil.isEmpty(currentTime)) {
							mMediaPlayer.seekTo(StringUtil.getLongTime(currentTime));
						}
						showMediaActionTips();
					}
				} else {
					int progress = Integer.valueOf(mVodRemoteSeekProgress);
					seekVodToProgress(progress);
				}
				break;
			}
		}else if(mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_FORCE){
			// TODO:强制播放
			LogUtil.d("onPrepared----------------mFocusPlayTime=" + mFocusPlayTime);
			mMediaPlayer.seekTo((int)mFocusPlayTime);
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		mMediaPlayerErrorCount++;
		LogUtil.e("MediaPlayer----onError countNumber = " + mMediaPlayerErrorCount);
		LogUtil.e("MediaPlayer----onError---what=" + what + "extra=" + extra);
		mediaSourceErrorLogic();
		return true;
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		LogUtil.d("onCompletion called");
		actionMediaPlayerComplete();
	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int percent) {
		LogUtil.d("onBufferingUpdate percent:" + percent);
		actionMediaPlayerLogic();
	}

	private void mediaSourceErrorLogic() {
		if (mMediaPlayerErrorCount >= 30) {
			if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
				mLiveNosignalTips.setVisibility(View.VISIBLE);
				// showToast(getString(R.string.media_livesource_error),
				// Toast.LENGTH_LONG);
				doCleanUp();
				releaseMediaPlayer();
			} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD) {
				showToast(getString(R.string.media_vodsource_error), Toast.LENGTH_LONG);
				finish();
			} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
				showToast(getString(R.string.media_vodsource_error), Toast.LENGTH_LONG);
				returnToLiveActivity();
			}
		}
	}

	private void actionMediaPlayerLogic() {
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
			// 解决直播长时间播放卡顿问题
			if (null != mMediaPlayer && !mMediaPlayer.isPlaying()) {
				mMediaPlayer.start();
			}
		} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD || mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
			if (mVodMediaPlayerState == Constant.IPTV_MEDIAPLAYER_STATE_PLAYING) {
				// 解决直播长时间播放卡顿问题
				if (null != mMediaPlayer && !mMediaPlayer.isPlaying()) {
					mMediaPlayer.start();
				}
			}
		}
	}

	private void actionMediaPlayerComplete() {
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD) {
			if (null != mMediaPlayer && mMediaPlayer.getCurrentPosition() >= mMediaPlayer.getDuration()) {
				// 媒体播放完成之后,将对应媒体断点续播的数据库中的数据删除
				if (mBreakPointInfo != null) {
					try {
						mDb.delete(mBreakPointInfo);
					} catch (DbException e) {
						e.printStackTrace();
					}
				}

				// 媒体播放完成之后，退出播放器
				mVodCurrentPosition++;
				if (mVodCurrentPosition >= mVodDataModels.size()) {
					finish();
				} else {
					mMediaName = mVodDataModels.get(mVodCurrentPosition).getViewname();
					mMediaPath = mVodDataModels.get(mVodCurrentPosition).getPlayurl();
					playVideo(mMediaPath);

					setBannerVisiable();
					setLiveOsdVisiable();
					setMediaInfos();
					showController();
				}
			}
		} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
			if (null != mMediaPlayer && !mMediaPlayer.isPlaying() && mMediaPlayer.getCurrentPosition() >= mMediaPlayer.getDuration()) {
				returnToLiveActivity();
			}
		}
	}

	private void doCleanUp() {
		mVideoWidth = 0;
		mVideoHeight = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	private void releaseMediaPlayer() {
		if (null != mMediaPlayer) {
			mediaTypeAction(mMediaPath, null, SmsMediaRtpToUdp.MEDIA_DEINIT);
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void startVideoPlayback() {
		LogUtil.d("startVideoPlayback");
		mHolder.setFixedSize(mVideoWidth, mVideoHeight);
		mMediaPlayer.start();
	}

	private void playNumLiveItem(final int channelNumber) {
		if (null != mLiveMainModels) {
			int index = UIDataller.getDataller().getLiveIndexByChannelNumber(channelNumber, mLiveMainModels);
			if (index >= 0) {
				mLiveChannelTextShow = "";
				mLiveMainChannelIndex = index;
				mLiveCurrentChannelNumber = channelNumber;
				mMediaPath = mLiveMainModels.get(mLiveMainChannelIndex).getChannelurl();
				// playVideo(mMediaPath);
				playMedia();
			} else {
				mLiveChannelTextShow = "";
				showToast(getString(R.string.no_live_channel), Toast.LENGTH_LONG);
			}
		} else {
			UIDataller.getDataller().setLiveMainList(new ILiveMainListSuccessCB() {

				@Override
				public void setListDatas(ArrayList<LiveMainModel> listModels) {
					mLiveMainModels = listModels;
					int index = UIDataller.getDataller().getLiveIndexByChannelNumber(channelNumber, mLiveMainModels);
					if (index >= 0) {
						mLiveChannelTextShow = "";
						mLiveMainChannelIndex = index;
						mLiveCurrentChannelNumber = channelNumber;
						mMediaPath = mLiveMainModels.get(mLiveMainChannelIndex).getChannelurl();
						// playVideo(mMediaPath);
						playMedia();
					} else {
						mLiveChannelTextShow = "";
						showToast(getString(R.string.no_live_channel), Toast.LENGTH_LONG);
					}
				}
			});
		}
	}

	private int preCurrentVodIndex() {
		mVodCurrentPosition--;
		if (mVodCurrentPosition < 0) {
			mVodCurrentPosition = mVodDataModels.size() - 1;
		}
		return mVodCurrentPosition;
	}

	private int nextCurrentVodIndex() {
		mVodCurrentPosition++;
		if (mVodCurrentPosition > mVodDataModels.size() - 1) {
			mVodCurrentPosition = 0;
		}

		return mVodCurrentPosition;
	}

	public void changePreVodItem() {
		preCurrentVodIndex();
		mMediaName = mVodDataModels.get(mVodCurrentPosition).getViewname();
		mMediaPath = mVodDataModels.get(mVodCurrentPosition).getPlayurl();
		playVideo(mMediaPath);

		setBannerVisiable();
		setLiveOsdVisiable();
		setMediaInfos();
		showController();
	}

	public void changeNextVodItem() {
		nextCurrentVodIndex();
		mMediaName = mVodDataModels.get(mVodCurrentPosition).getViewname();
		mMediaPath = mVodDataModels.get(mVodCurrentPosition).getPlayurl();
		playVideo(mMediaPath);

		setBannerVisiable();
		setLiveOsdVisiable();
		setMediaInfos();
		showController();
	}

	public void changePreLiveItem() {
		// 只存在一个频道时不做任何切换动作
		if (null != mLiveMainModels && mLiveMainModels.size() == 1) {
			return;
		} else if (null == mLiveMainModels) {
			UIDataller.getDataller().setLiveMainList(new ILiveMainListSuccessCB() {

				@Override
				public void setListDatas(ArrayList<LiveMainModel> listModels) {
					mLiveMainModels = listModels;
					mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_LIVE_DOUBLE_CLICK);
					--mLiveMainChannelIndex;
					if (mLiveMainChannelIndex < 0) {
						mLiveMainChannelIndex = mLiveMainModels.size() - 1;
					}
					mLiveCurrentChannelNumber = listModels.get(mLiveMainChannelIndex).getChannelnumber();
					mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_LIVE_DOUBLE_CLICK, Constant.FIVE_H_MILLSECONDES);
				}
			});
			return;
		}
		mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_LIVE_DOUBLE_CLICK);
		--mLiveMainChannelIndex;
		if (mLiveMainChannelIndex < 0) {
			mLiveMainChannelIndex = mLiveMainModels.size() - 1;
		}
		mLiveCurrentChannelNumber = mLiveMainModels.get(mLiveMainChannelIndex).getChannelnumber();
		mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_LIVE_DOUBLE_CLICK, Constant.FIVE_H_MILLSECONDES);
	}

	public void changeNextLiveItem() {
		// 只存在一个频道时不做任何切换动作
		if (null != mLiveMainModels && mLiveMainModels.size() == 1) {
			return;
		} else if (null == mLiveMainModels) {
			UIDataller.getDataller().setLiveMainList(new ILiveMainListSuccessCB() {

				@Override
				public void setListDatas(ArrayList<LiveMainModel> listModels) {
					mLiveMainModels = listModels;
					mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_LIVE_DOUBLE_CLICK);
					++mLiveMainChannelIndex;
					if (mLiveMainChannelIndex >= mLiveMainModels.size()) {
						mLiveMainChannelIndex = 0;
					}
					mLiveCurrentChannelNumber = mLiveMainModels.get(mLiveMainChannelIndex).getChannelnumber();
					mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_LIVE_DOUBLE_CLICK, Constant.FIVE_H_MILLSECONDES);
				}
			});
			return;
		}
		mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_LIVE_DOUBLE_CLICK);
		++mLiveMainChannelIndex;
		if (mLiveMainChannelIndex >= mLiveMainModels.size()) {
			mLiveMainChannelIndex = 0;
		}
		mLiveCurrentChannelNumber = mLiveMainModels.get(mLiveMainChannelIndex).getChannelnumber();
		mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_LIVE_DOUBLE_CLICK, Constant.FIVE_H_MILLSECONDES);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.mediacontroller_seekbar:
			if (null != mMediaPlayer) {
				if (fromUser) {
					// @johnson delete 20141210 begin for 解决seekbar托拉速度过快问题
					// mTimeRecord = 0;
					// LogUtil.d("isFromUser progress=" +
					// progress);
					// setMediaTime(mMediaPlayer.getCurrentPosition(),
					// mMediaPlayer.getDuration());
					// mMediaPlayer.seekTo(progress);
					// @johnson delete 20141210 end for 解决seekbar托拉速度过快问题
				} else {
					LogUtil.d("isFromSystem progress=" + progress);
				}
				if (progress >= seekBar.getMax()) {
					actionMediaPlayerComplete();
				}
			}
			break;
		case R.id.vod_sound_seekbar:
		case R.id.live_sound_seekbar:
			if (fromUser) {
				SoundController ller = new SoundController(getApplication());
				ller.setStreamVolumeCurrent(progress);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {

	}

	private void setLastMainNum() {
		int index = mListMainItemListView.getSelectedItemPosition();
		if (index > 0) {
			mListMainItemListView.setSelection(index - 1);
			mListMainItemListView.requestLayout();
			mListMainItemListView.requestFocus();
		}
	}

	private void setNextMainNum() {
		int index = mListMainItemListView.getSelectedItemPosition();
		if (index < (mLiveMainModels.size() - 1)) {
			mListMainItemListView.setSelection(index + 1);
		}
	}

	private void setLastSubNum() {
		int index = mListSubPromListView.getSelectedItemPosition();
		if (index > 0) {
			mListSubPromListView.setSelection(index - 1);
			mListSubPromListView.requestLayout();
			mListSubPromListView.requestFocus();
		}
	}

	private void setNextSubNum() {
		int index = mListSubPromListView.getSelectedItemPosition();
		if (index < (mLiveMainModels.size() - 1)) {
			mListSubPromListView.setSelection(index + 1);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_HIDE) {
					VirturlKeyPadCtr.RC_ConttrollerAction(KeyEvent.KEYCODE_VOLUME_DOWN);
					break;
				} else {
					return true;
				}
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_HIDE) {
					VirturlKeyPadCtr.RC_ConttrollerAction(KeyEvent.KEYCODE_VOLUME_UP);
				} else {
					return true;
				}
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_MAIN_SHOW) {
					setLastMainNum();
				} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_SUB_SHOW) {
					setLastSubNum();
				} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_HIDE) {

				}
				return true;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_MAIN_SHOW) {
					setNextMainNum();
				} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_SUB_SHOW) {
					setNextSubNum();
				} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_HIDE) {

				}
				return true;

			case KeyEvent.KEYCODE_VOLUME_UP:
			case KeyEvent.KEYCODE_VOLUME_MUTE:
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				setLiveMediaSoundPercent();
				break;
			default:
				break;
			}
		} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD || mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_HIDE) {
					LogUtil.d("onKeyDown goto trag state...");
					showController();
					if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
						mTragActionType = TRAG_LEFT;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
						mTragActionType = TRAG_RIGHT;
					}
					setTragPercent();
					return true;
				} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_SUB_SHOW
						|| mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_DATE_SHOW) {
					return true;
				}
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_MAIN_SHOW) {
					setLastMainNum();
				} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_SUB_SHOW) {
					setLastSubNum();
				}
				return true;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_MAIN_SHOW) {
					setNextMainNum();
				} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_SUB_SHOW) {
					setNextSubNum();
				}
				return true;
			case KeyEvent.KEYCODE_VOLUME_UP:
			case KeyEvent.KEYCODE_VOLUME_MUTE:
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				setVodMediaSoundPercent();
				break;
			default:
				break;
			}
		}else if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_FORCE) {
			// 强制播放时，锁住所有操作
			if (SystemMgr.getMediaLockTag()) {
				switch (keyCode) {
					case KeyEvent.KEYCODE_VOLUME_UP:
					case KeyEvent.KEYCODE_VOLUME_MUTE:
					case KeyEvent.KEYCODE_VOLUME_DOWN:
						setLiveMediaSoundPercent();
						return super.onKeyDown(keyCode, event);
					default:
						return true;
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				actionToOsdShow(keyCode);
				return true;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_SUB_SHOW) {
					hideLiveOsdSubItemList();
					mListMainItemListView.setSelection(mTempMainListPosition);
					return true;
				} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_DATE_SHOW) {
					setLiveSubListSelect();
					return true;
				}
				break;
			case KeyEvent.KEYCODE_ENTER:
			case KeyEvent.KEYCODE_DPAD_CENTER:
				if (!StringUtil.isEmpty(mLiveChannelTextShow)) {
					mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_MEDIAPLAYER_LIVE_CHANNEL);
					mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_MEDIAPLAYER_LIVE_NUM_INTERVAL);
					mMediaPlayerHandler.sendEmptyMessage(Constant.IPTV_MSG_MEDIAPLAYER_LIVE_NUM_INTERVAL);
				} else {
					showLiveOsdMainItemList();
				}
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_HIDE) {
					changeNextLiveItem();
					return true;
				}
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_HIDE) {
					changePreLiveItem();
					return true;
				}
				break;
			case KeyEvent.KEYCODE_0:
			case KeyEvent.KEYCODE_1:
			case KeyEvent.KEYCODE_2:
			case KeyEvent.KEYCODE_3:
			case KeyEvent.KEYCODE_4:
			case KeyEvent.KEYCODE_5:
			case KeyEvent.KEYCODE_6:
			case KeyEvent.KEYCODE_7:
			case KeyEvent.KEYCODE_8:
			case KeyEvent.KEYCODE_9:
				// 限制无限的数字输入
				if (StringUtil.isEmpty(mLiveChannelTextShow) || (!StringUtil.isEmpty(mLiveChannelTextShow) && mLiveChannelTextShow.length() < 4)) {
					mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_MEDIAPLAYER_LIVE_CHANNEL);
					mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_MEDIAPLAYER_LIVE_NUM_INTERVAL);
					mLiveChannelTextShow += convertKeyToNumberic(keyCode);
					mLiveChannelNumTextView.setVisibility(View.VISIBLE);
					mLiveChannelNumTextView.setText(mLiveChannelTextShow);
					mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_MEDIAPLAYER_LIVE_NUM_INTERVAL, Constant.IPTV_TIME_FOUR_SECONDES);
				}
				break;
			default:
				break;
			}
		} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_ENTER:
			case KeyEvent.KEYCODE_DPAD_CENTER:
				if (mIsShowController) {
					playVodMedia();
				} else {
					pauseVodMedia();
				}
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				LogUtil.d("onKeyUp exit trag state...");
				mTragActionType = TRAG_NO;
				showController();
				break;
			case KeyEvent.KEYCODE_BACK:
				// if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD) {
				// setSqlite();
				// }
				// if (null != mDb && mDb.getDatabase().isOpen()) {
				// mDb.close();
				// }
				// doCleanUp();
				// releaseMediaPlayer();
				vodRecordTime();
				break;
			default:
				break;
			}
		} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_HIDE) {
					mTragActionType = TRAG_NO;
					showController();
				} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_SUB_SHOW) {
					if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
						hideLiveOsdSubItemList();
						mListMainItemListView.setSelection(mTempMainListPosition);
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
						setDateListSelect();
					}
				} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_MAIN_SHOW) {
					if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
						nextLivePopupWindowShow();
					}
				} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_DATE_SHOW) {
					if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
						setLiveSubListSelect();
						return true;
					}
				}
				break;
			case KeyEvent.KEYCODE_BACK:
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_HIDE) {
					doCleanUp();
					if (null != mMediaPlayer) {
						mediaTypeAction(mMediaPath, null, SmsMediaRtpToUdp.MEDIA_DEINIT);
						mMediaPlayer.stop();
						mMediaPlayer = null;
					}
					returnToLiveActivity();
					return true;
				} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_SUB_SHOW
						|| mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_MAIN_SHOW
						|| mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_DATE_SHOW) {
					hideLiveOsdMainItemList();
					mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD);
					if (mVodMediaPlayerState == Constant.IPTV_MEDIAPLAYER_STATE_PLAYING
							|| mVodMediaPlayerState == Constant.IPTV_MEDIAPLAYER_STATE_DEFAULT) {
						mMediaPlayerStateView.setVisibility(View.GONE);
					} else if (mVodMediaPlayerState == Constant.IPTV_MEDIAPLAYER_STATE_PAUSING) {
						mMediaPlayerStateView.setVisibility(View.VISIBLE);
					}
					return true;
				}
				break;
			case KeyEvent.KEYCODE_ENTER:
			case KeyEvent.KEYCODE_DPAD_CENTER:
				if (mVodMediaPlayerState == Constant.IPTV_MEDIAPLAYER_STATE_PAUSING) {
					playVodMedia();
				} else {
					if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_HIDE) {
						showLiveOsdAllItemList();
						pauseVodMedia();
						mTempMainListPosition = mLiveMainChannelIndex;
					} else if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_SUB_SHOW) {
						hideLiveOsdMainItemList();
						playVodMedia();
					}
				}
				break;
			default:
				break;
			}
		}else if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_FORCE) {
			// 强制播放时，锁住所有操作
			if (SystemMgr.getMediaLockTag()) {
				switch (keyCode) {
					case KeyEvent.KEYCODE_VOLUME_UP:
					case KeyEvent.KEYCODE_VOLUME_MUTE:
					case KeyEvent.KEYCODE_VOLUME_DOWN:
						setLiveMediaSoundPercent();
						return super.onKeyDown(keyCode, event);
					default:
						return true;
				}
			}
		}
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * 从回看返回到直播
	 */
	private void returnToLiveActivity() {
		Intent intent = new Intent(mInstanceActivity, MediaPlayerActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(Constant.IPTV_MEDIA_TYPE, Constant.IPTV_MEDIA_TYPE_LIVE);
		bundle.putInt(Constant.IPTV_LIVE_CHANNEL_INDEX, mLiveMainChannelIndex);
		bundle.putInt(Constant.IPTV_LIVE_CHANNEL_NUMBER, mLiveCurrentChannelNumber);
		bundle.putString(Constant.IPTV_MEDIA_URL, mLiveMainModels.get(mLiveMainChannelIndex).getChannelurl());
		bundle.putString(Constant.IPTV_MEDIA_NAME, mLiveMainModels.get(mLiveMainChannelIndex).getChannelname());
		intent.putExtra(Constant.IPTV_MEDIA_DATA_TAG, bundle);
		startActivity(intent);
	}

	/**
	 * 设置子列表里的选中项
	 */
	private void setLiveSubListSelect() {
		if (null != mLiveSubModels && mLiveSubModels.size() > 0) {
			mLiveOsdPopWindowShowTag = Constant.IPTV_LIVE_OSD_SUB_SHOW;
			mListSubPromListView.requestLayout();
			mListSubPromListView.requestFocus();
			mListSubPromListView.setSelection(mTempSubListPosition);
		}
	}

	private void showLiveOsdAllItemList() {
		hideController();
		showLiveOsdSubItemList();
	}

	private void setSqlite() {
		if (null != mMediaPlayer && mMediaPlayer.isPlaying() && mMediaPlayer.getCurrentPosition() < mMediaPlayer.getDuration()
				&& mMediaPlayer.getDuration() > 0) {
			if (mBreakPointInfo == null) {
				mBreakPointInfo = new VodBreakPointInfo();
				mBreakPointInfo.setMovieName(mMediaName);
				mBreakPointInfo.setMovieId(mMovieId);
				mBreakPointInfo.setProgress(mMediaControllerSeekBar.getProgress());
				mBreakPointInfo.setCurrentTime(StringUtil.generateTime(mMediaPlayer.getCurrentPosition()));
				mBreakPointInfo.setDurationTime(mMediaPlayer.getDuration());
				try {
					mDb.saveBindingId(mBreakPointInfo);
				} catch (DbException e) {
					e.printStackTrace();
				}
			} else {
				mBreakPointInfo.setMovieName(mMediaName);
				mBreakPointInfo.setMovieId(mMovieId);
				mBreakPointInfo.setCurrentTime(StringUtil.generateTime(mMediaPlayer.getCurrentPosition()));
				mBreakPointInfo.setProgress(mMediaControllerSeekBar.getProgress());
				mBreakPointInfo.setDurationTime(mMediaPlayer.getDuration());
				try {
					mDb.saveOrUpdate(mBreakPointInfo);
				} catch (DbException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
				if (mLiveOsdPopWindowShowTag == Constant.IPTV_LIVE_OSD_HIDE) {
					showLiveOsdMainItemList();
					showLiveOsdSubListForTouch();
				} else {
					hideLiveOsdMainItemList();
				}
			} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD || mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
				if (mIsShowController) {
					hideController();
				} else {
					showController();
				}
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		setMediaBaseValueInfo(intent);
		setAllListners();
		setBannerVisiable();
		setLiveOsdVisiable();
		setMediaInfos();
		showController();
		checkNeedPayTips();

		// 重走mMediaSurfaceView的生命周期
		mMediaSurfaceView.setVisibility(View.GONE);
		mMediaSurfaceView.setVisibility(View.VISIBLE);
	}

	public void pauseVodMedia() {
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD || mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
			//下面三行解决进入无断点的电影后立即点暂停无法停住这时中间那个大暂停图标会出来
			if(mMediaPlayer.getCurrentPosition()>1000){
				showController();	
				setMediaPlayerState(Constant.IPTV_MEDIAPLAYER_STATE_PAUSING);
			}			
		}
		pauseMediaPlayer();
	}

	public void playVodMedia() {
		hideController();
		resumeMediaPlayer();
		setMediaPlayerState(Constant.IPTV_MEDIAPLAYER_STATE_PLAYING);
	}

	public void seekVodToProgress(int progress) {
		int position = convertToPosition(progress);
		if (null != mMediaControllerSeekBar) {
			mMediaControllerSeekBar.setProgress(position);
		}

		if (null != mMediaPlayer) {
			mMediaPlayer.seekTo(position);
		}
	}

	private int convertToPosition(int percent) {
		int position = 0;
		if (null != mMediaPlayer && mMediaPlayer.getDuration() > 0) {
			position = percent * mMediaPlayer.getDuration() / 100;
		}

		return position;
	}

	public int convertKeyToNumberic(int keyCode) {
		int liveChannelNum = 0;
		switch (keyCode) {
		case KeyEvent.KEYCODE_0:
			liveChannelNum = 0;
			break;
		case KeyEvent.KEYCODE_1:
			liveChannelNum = 1;
			break;
		case KeyEvent.KEYCODE_2:
			liveChannelNum = 2;
			break;
		case KeyEvent.KEYCODE_3:
			liveChannelNum = 3;
			break;
		case KeyEvent.KEYCODE_4:
			liveChannelNum = 4;
			break;
		case KeyEvent.KEYCODE_5:
			liveChannelNum = 5;
			break;
		case KeyEvent.KEYCODE_6:
			liveChannelNum = 6;
			break;
		case KeyEvent.KEYCODE_7:
			liveChannelNum = 7;
			break;
		case KeyEvent.KEYCODE_8:
			liveChannelNum = 8;
			break;
		case KeyEvent.KEYCODE_9:
			liveChannelNum = 9;
			break;
		default:
			break;
		}
		return liveChannelNum;
	}

	private void setMediaBaseValueInfo(Intent intent) {
		mMediaExtrasBundle = intent.getBundleExtra(Constant.IPTV_MEDIA_DATA_TAG);
		mMediaType = mMediaExtrasBundle.getInt(Constant.IPTV_MEDIA_TYPE, 0);
		mMediaPath = mMediaExtrasBundle.getString(Constant.IPTV_MEDIA_URL, null);
		mMediaName = mMediaExtrasBundle.getString(Constant.IPTV_MEDIA_NAME, null);
		mFocusPlayTime = mMediaExtrasBundle.getLong(Constant.IPTV_LIVE_START_PLAY_TIME, 0);
		if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
			mLiveCurrentChannelNumber = mMediaExtrasBundle.getInt(Constant.IPTV_LIVE_CHANNEL_NUMBER, 0);
			mLiveMainChannelIndex = mMediaExtrasBundle.getInt(Constant.IPTV_LIVE_CHANNEL_INDEX, 0);
			mLiveMainModels = intent.getParcelableArrayListExtra(Constant.IPTV_LIVE_CHANNEL_LIST);
		} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD) {
			mDb = DbUtils.create(this, Constant.DB_NAME, Config.DB_VERSION, new DbUpgradeListener() {

				@SuppressWarnings("static-access")
				@Override
				public void onUpgrade(DbUtils arg0, int arg1, int arg2) {
					if (arg1 != arg2) {
						try {
							arg0.dropDb();
							DbUtils.create(MediaPlayerActivity.this, Constant.DB_NAME);
						} catch (DbException e) {
							e.printStackTrace();
						}
					}
				}
			});
			mVodRemoteSeekProgress = mMediaExtrasBundle.getString(Constant.IPTV_VOD_REMOTE_SEEK_MSG, null);
			mVodMediaPrice = mMediaExtrasBundle.getString(Constant.IPTV_VOD_MEDIA_PRICE, null);
			mVodFeeMediaName = mMediaExtrasBundle.getString(Constant.IPTV_MEDIA_FEE_NAME, null);
			mVodProgrameId = mMediaExtrasBundle.getInt(Constant.IPTV_VOD_MEDIA_PROGRME_ID, 0);
			mMediaMovieId = mMediaExtrasBundle.getInt(Constant.IPTV_MEDIA_MOVIE_ID, 0);
			mVodCurrentPosition = mMediaExtrasBundle.getInt(Constant.IPTV_MEDIA_MOVIE_CURRENT_POSITION, 0);
			mVodDataModels = mMediaExtrasBundle.getParcelableArrayList(Constant.IPTV_VOD_MEDIA_DATA);
		} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
			// mLiveMainChannelIndex =
			// mMediaExtrasBundle.getInt(Constant.IPTV_LIVE_CHANNEL_INDEX, 0);
		}

		if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
			mLiveBackTag.setVisibility(View.VISIBLE);
			mLiveChannelNumTextView.setVisibility(View.GONE);
		} else {
			mLiveBackTag.setVisibility(View.GONE);
		}
	}

	private void checkMediaPlayerStateBug() {
		mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_CHECK_MEDIA_PAYER_BUG);
		mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_CHECK_MEDIA_PAYER_BUG, Constant.IPTV_TIME_ONE_SECONDE);
	}

	public static class MediaUpdateHandler extends Handler {
		WeakReference<MediaPlayerActivity> weakActivity;

		MediaUpdateHandler(MediaPlayerActivity activity) {
			weakActivity = new WeakReference<MediaPlayerActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			MediaPlayerActivity activity = weakActivity.get();
			if (null == activity) {
				return;
			}
			switch (msg.what) {
			case Constant.IPTV_MSG_UPDATE_TIME_FREQUENCE:
				if (activity.mIsShowController && activity.mTragActionType == TRAG_NO) {
					activity.setMediaControllerPercent();
					removeMessages(msg.what);
					sendEmptyMessageDelayed(msg.what, Constant.IPTV_TIME_ONE_SECONDE);
				}
				break;
			case Constant.IPTV_MSG_UPDATE_CONTROLLER_WAIT:
				if (activity.mIsShowController && null != activity.mMediaPlayer && activity.mMediaPlayer.isPlaying()
						&& activity.mTragActionType == TRAG_NO) {
					activity.mTimeRecord++;
					LogUtil.d("controller waitting....." + activity.mTimeRecord);
					if (activity.mTimeRecord > 6) {
						activity.mTimeRecord = 0;
						activity.hideController();
					}
				}
				if (activity.mIsShowController) {
					removeMessages(msg.what);
					sendEmptyMessageDelayed(msg.what, Constant.IPTV_TIME_ONE_SECONDE);
				}
				break;
			case Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD:
				removeMessages(msg.what);
				sendEmptyMessageDelayed(msg.what, Constant.IPTV_TIME_SIX_SECONDES);
				activity.hideLiveOsdMainItemList();
				if (activity.mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
					if (activity.mVodMediaPlayerState == Constant.IPTV_MEDIAPLAYER_STATE_PLAYING
							|| activity.mVodMediaPlayerState == Constant.IPTV_MEDIAPLAYER_STATE_DEFAULT) {
						activity.mMediaPlayerStateView.setVisibility(View.GONE);
					} else if (activity.mVodMediaPlayerState == Constant.IPTV_MEDIAPLAYER_STATE_PAUSING) {
						activity.mMediaPlayerStateView.setVisibility(View.VISIBLE);
					}
				}
				break;
			case Constant.IPTV_MSG_SHOW_LIVE_CHANNEL_OSD:
				activity.hideLiveOsdMainItemList();
				break;
			case Constant.IPTV_MSG_HIDE_LIVE_PROGRAME_OSD:
				activity.hideLiveOsdSubItemList();
				break;
			case Constant.IPTV_MSG_CHECK_MEDIA_PAYER_BUG:
				if (null != activity.mMediaPlayer && !activity.mMediaPlayer.isPlaying()) {
					activity.mMediaPlayer.start();
					removeMessages(msg.what);
					sendEmptyMessageDelayed(msg.what, Constant.IPTV_TIME_ONE_SECONDE);
				}

				activity.mMediaSurfaceView.invalidate();
				break;
			case Constant.IPTV_MSG_LIVE_DOUBLE_CLICK:
				activity.mMediaPath = activity.mLiveMainModels.get(activity.mLiveMainChannelIndex).getChannelurl();
				// activity.playVideo(activity.mMediaPath);
				activity.playMedia();
				break;
			case Constant.IPTV_MSG_VOD_PAY_TIPS:
				if (activity.mMediaPlayer != null && activity.mMediaPlayer.getCurrentPosition() >= Constant.IPTV_TIME_FIVE_MINUTES) {
					activity.showNeedPayTips();
				} else {
					sendEmptyMessageDelayed(Constant.IPTV_MSG_VOD_PAY_TIPS, Constant.IPTV_TIME_ONE_SECONDE);
				}
				break;
			case Constant.IPTV_MSG_MEDIAPLAYER_LIVE_CHANNEL:
				activity.mLiveChannelNumTextView.setVisibility(View.GONE);
				break;
			case Constant.IPTV_MSG_MEDIAPLAYER_LIVE_NUM_INTERVAL:
				removeMessages(Constant.IPTV_MSG_MEDIAPLAYER_LIVE_CHANNEL);
				sendEmptyMessageDelayed(Constant.IPTV_MSG_MEDIAPLAYER_LIVE_CHANNEL, Constant.IPTV_TIME_SIX_SECONDES);
				activity.playNumLiveItem(Integer.valueOf(activity.mLiveChannelTextShow));
				break;
			case Constant.IPTV_MSG_MEDIAPLAYER_ACTION_TIPS:
				removeMessages(msg.what);
				activity.hideMediaActionTips();
				break;
			default:
				break;
			}
		}
	}

	private void removeAllHandlerMsg() {
		if (null != mMediaPlayerHandler) {
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_UPDATE_TIME_FREQUENCE);
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_UPDATE_CONTROLLER_WAIT);
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD);
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_SHOW_LIVE_CHANNEL_OSD);
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_PROGRAME_OSD);
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_CHECK_MEDIA_PAYER_BUG);
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_LIVE_DOUBLE_CLICK);
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_VOD_PAY_TIPS);
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_MEDIAPLAYER_LIVE_CHANNEL);
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_MEDIAPLAYER_LIVE_NUM_INTERVAL);
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_MEDIAPLAYER_ACTION_TIPS);
		}
	}

	/****************************** 加入点播计费 begin ***************************************/
	private void checkNeedPayTips() {
		int price = StringUtil.isEmpty(mVodMediaPrice) ? 0 : Integer.valueOf(mVodMediaPrice);
		// vodFree没有打开就需要进行收费判断
		if ("off".equalsIgnoreCase(UserMgr.getVodFreeEnable()) && price > 0) {
			if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD && mVodProgrameId > 0) {
				UIDataller.getDataller().checkIsVodFree(UserMgr.getHotelRoomNo(), mVodProgrameId, new IPaymentCheckCB() {

					@Override
					public void onNeedPay() {
						mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_VOD_PAY_TIPS, Constant.IPTV_TIME_ONE_SECONDE);
					}
				});
			}
		}
	}

	private void showNeedPayTips() {
		pauseVodMedia();

		String title = getString(R.string.exit_tips);
		String contents = String.format(getString(R.string.need_pay_tips), mVodMediaPrice);
		DialogTwoButton dialog = new DialogTwoButton(this, R.style.lvbEdittextDialogTheme, title, contents, new IOnClickListnerable() {

			@Override
			public void ok() {
				thorwVodBillResult();
			}

			@Override
			public void no() {
				finish();
			}
		}, null, true);
		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				finish();
			}
		});
		dialog.show();
	}

	private void thorwVodBillResult() {
		try {
			UIDataller.getDataller().throwVodBillAction(this, UserMgr.getHotelRoomNo(), mVodMediaPrice, 1, mVodProgrameId,
					URLEncoder.encode(mVodFeeMediaName, "UTF-8"), new IPaymentCheckCB() {

						@Override
						public void onNeedPay() {
							hideController();
							resumeMediaPlayer();
							setMediaPlayerState(Constant.IPTV_MEDIAPLAYER_STATE_PLAYING);
						}
					});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/****************************** 加入点播计费 end ***************************************/

	/****************************** 加入RTP播放协议的支持 begin ***************************************/
	// 回环地址
	public static final String IPTV_SELF_IP = "127.0.0.1";

	private boolean isRtpMediaType(String mediaUrl) {
		boolean result = false;
		String[] urlSplits = mediaUrl.split(":");
		if (urlSplits.length > 0) {
			if ("rtp".equalsIgnoreCase(urlSplits[0])) {
				result = true;
			} else {
				result = false;
			}
		}

		return result;
	}

	private String getSelfMediaUrl() {
		String selfIp = IPTV_SELF_IP;
		int usablePort = DeviceUtil.getUseablePort();
		String selfMediaUrl = "udp://" + selfIp + ":" + usablePort;

		return selfMediaUrl;
	}

	private boolean mediaTypeAction(String Mediaurl, String selfUrl, int actionType) {
		boolean result = false;
		if (!StringUtil.isEmpty(Mediaurl)) {
			switch (actionType) {
			case SmsMediaRtpToUdp.MEDIA_INIT:
				if (isRtpMediaType(Mediaurl)) {
					LogUtil.d("mediaTypeAction-->Mediaurl=" + Mediaurl);
					LogUtil.d("mediaTypeAction-->selfUrl=" + selfUrl);
					if (0 == SmsMediaRtpToUdp.Init(Mediaurl, selfUrl)) {
						result = true;
					} else {
						LogUtil.d("mediaTypeAction-->RtpToUdp.Init error!");
					}
				}
				break;
			case SmsMediaRtpToUdp.MEDIA_DEINIT:
				if (isRtpMediaType(Mediaurl)) {
					result = true;
					SmsMediaRtpToUdp.Release();
				}
				break;
			default:
				break;
			}
		}

		return result;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD);
			break;
		case MotionEvent.ACTION_UP:
			mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_HIDE_LIVE_CHANNEL_OSD, Constant.IPTV_TIME_SIX_SECONDES);
			break;
		default:
			break;
		}
		return false;
	}

	private void showSeatDialog() {
		if (null == mSeatSelectionTipsDialog) {
			mVodMediaPlayerState = Constant.IPTV_MEDIAPLAYER_STATE_PLAYING;
			onClick(null);
			mSeatSelectionTipsDialog = new SeatSelectionTipsDialog(mInstanceActivity, R.style.lvbEdittextDialogTheme, new OnFinishListener() {

				@Override
				public void onFinish() {
					mSeatSelectionTipsDialog = null;
					mVodMediaPlayerState = Constant.IPTV_MEDIAPLAYER_STATE_PAUSING;
					onClick(null);
				}
			});
			mSeatSelectionTipsDialog.show();
		}
	}

	/****************************** 加入RTP播放协议的支持 end ***************************************/
	/**************************************** 加入网络状态监听 begin ****************************/
	private class NetChangeBroadcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)) {
				ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ethNet = manager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
				if (null != ethNet && ethNet.isAvailable()) {
					if (mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE) {
						playVideo(mMediaPath);
					} else if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD || mMediaType == Constant.IPTV_MEDIA_TYPE_LIVE_BACK) {
						resumeMediaPlayer();
					}
				}
			} else if (intent.getAction().equalsIgnoreCase(Constant.IPTV_LVB_X_BROADCAST_MSG_HSJQ_SEAT)) {
				if (mMediaType == Constant.IPTV_MEDIA_TYPE_VOD) {
					showSeatDialog();
				}
			}
		}

	}

	/**************************************** 加入网络状态监听 end *****************************/
	/****************************** 加入点播继续播放、从头播放操作按钮 begin *****************************/
	private void showMediaActionTips() {
		mMediaActionDialog = new MediaActionDialog(this, new IMediaPlayerActionable() {

			@Override
			public void resetPlay() {
				playFromMediaBegin();
			}

			@Override
			public void continuePlay() {
				playFromMediaCurrent();
			}

			@Override
			public void onFoucsChange() {
				restartMediaActionTipsMsg();
			}
		});
		mMediaActionDialog.show();
		restartMediaActionTipsMsg();
	}

	private void hideMediaActionTips() {
		if (null != mMediaActionDialog) {
			mMediaActionDialog.dismiss();
			mMediaActionDialog = null;
		}
	}

	private void playFromMediaBegin() {
		if (null != mMediaPlayer) {
			mMediaPlayer.seekTo(0);
			mMediaControllerSeekBar.setProgress(0);
		}
		mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_MEDIAPLAYER_ACTION_TIPS);
	}

	private void playFromMediaCurrent() {
		if (null != mMediaPlayer && !mMediaPlayer.isPlaying()) {
			mMediaPlayer.start();
		}
		mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_MEDIAPLAYER_ACTION_TIPS);
	}

	private void restartMediaActionTipsMsg() {
		mMediaPlayerHandler.removeMessages(Constant.IPTV_MSG_MEDIAPLAYER_ACTION_TIPS);
		mMediaPlayerHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_MEDIAPLAYER_ACTION_TIPS, Constant.IPTV_TIME_TEN_SECONDES);
	}

	/****************************** 加入点播继续播放、从头播放操作按钮 end *****************************/
	
}

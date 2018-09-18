package com.hhzt.iptv.lvb_x.floatwindows;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.mediaplayer.LVBMusicBgMediaPlayer;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.hhzt.iptv.lvb_x.utils.CoordinateUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.hhzt.iptv.ui.MediaPlayerActivity;

public class FloatWindowManager {

	private static UIHandler mUIHandler;
	private static TextView mInvalideWindow;
	private static LayoutParams mInvalideWindowParams;

	private static LayoutParams mLVBEntryWindowParams;
	private static FloatWindowControllerEntryView mLVBEntryWindow;

	private static FloatWindowRCView mLVBRcWindow;
	private static LayoutParams mLVBRcWindowParams;

	private TimerTask mTickTask;
	private Timer mTickTimer;
	private static View mTickerWindow;
	private static TextView mTickershowTextView;
	private static RelativeLayout mTickershowContainer;
	private static LayoutParams mTickerWindowParams;

	private static View mLockedWindow;
	private static ImageView mLockMainContentbg;
	private static TextView mLockUserTipsTextView;
	private static TextView mLockContentTextView;
	private static LayoutParams mLockedWindowParams;

	private static WindowManager mLVBEntryWindowManager;

	private static FloatWindowManager floatWindowManager = new FloatWindowManager();

	private FloatWindowManager() {
		if (null == mUIHandler) {
			mUIHandler = new UIHandler();
		}
	}

	public static FloatWindowManager getInstance() {
		if (null == floatWindowManager) {
			floatWindowManager = new FloatWindowManager();
		}
		return floatWindowManager;
	}

	public void createInvalideWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		if (null == mInvalideWindow) {
			mInvalideWindow = new TextView(LVBXApp.getApp());
		}
		if (null == mInvalideWindowParams) {
			mInvalideWindowParams = new LayoutParams();
		}
		mInvalideWindowParams.type = LayoutParams.TYPE_SYSTEM_ERROR;
		mInvalideWindowParams.format = PixelFormat.RGBA_8888;
		mInvalideWindowParams.flags = LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
		mInvalideWindowParams.gravity = Gravity.CENTER;
		mInvalideWindowParams.width = LayoutParams.WRAP_CONTENT;
		mInvalideWindowParams.height = LayoutParams.WRAP_CONTENT;
		windowManager.addView(mInvalideWindow, mInvalideWindowParams);
	}

	public void createRcWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = new DisplayMetrics().widthPixels;
		int screenHeight = new DisplayMetrics().heightPixels;
		if (null == mLVBRcWindow) {
			mLVBRcWindow = new FloatWindowRCView(context);
		}
		if (null == mLVBRcWindowParams) {
			mLVBRcWindowParams = new LayoutParams();
		}
		mLVBRcWindowParams.type = LayoutParams.TYPE_PHONE;
		mLVBRcWindowParams.format = PixelFormat.RGBA_8888;
		mLVBRcWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
		mLVBRcWindowParams.gravity = Gravity.START | Gravity.TOP;
		mLVBRcWindowParams.width = mLVBRcWindow.viewWidth;
		mLVBRcWindowParams.height = mLVBRcWindow.viewHeight;
		mLVBRcWindowParams.x = screenWidth;
		mLVBRcWindowParams.y = (screenHeight - mLVBRcWindow.viewHeight) / 2;
		mLVBRcWindow.setParams(mLVBRcWindowParams);
		windowManager.addView(mLVBRcWindow, mLVBRcWindowParams);
	}

	public void createLVBEntryWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = new DisplayMetrics().widthPixels;
		int screenHeight = new DisplayMetrics().heightPixels;
		if (null == mLVBEntryWindow) {
			mLVBEntryWindow = new FloatWindowControllerEntryView(context);
		}
		if (null == mLVBEntryWindowParams) {
			mLVBEntryWindowParams = new LayoutParams();
		}
		mLVBEntryWindowParams.type = LayoutParams.TYPE_PHONE;
		mLVBEntryWindowParams.format = PixelFormat.RGBA_8888;
		mLVBEntryWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
		mLVBEntryWindowParams.gravity = Gravity.START | Gravity.TOP;
		mLVBEntryWindowParams.width = mLVBEntryWindow.viewWidth;
		mLVBEntryWindowParams.height = mLVBEntryWindow.viewHeight;
		mLVBEntryWindowParams.x = screenWidth;
		mLVBEntryWindowParams.y = (screenHeight - mLVBEntryWindow.viewHeight) / 2;
		mLVBEntryWindow.setParams(mLVBEntryWindowParams);
		windowManager.addView(mLVBEntryWindow, mLVBEntryWindowParams);
	}

	@SuppressLint("InflateParams")
	public void createTickerWindow(Context context, int ticker) {
		WindowManager windowManager = getWindowManager(context);
		if (null == mTickerWindow) {
			mTickerWindow = LayoutInflater.from(context).inflate(R.layout.fragment_ticker, null);
		}
		mTickershowTextView = (TextView) mTickerWindow.findViewById(R.id.tick_content_time);
		mTickershowContainer = (RelativeLayout) mTickerWindow.findViewById(R.id.tick_container);
		if (null == mTickerWindowParams) {
			mTickerWindowParams = new LayoutParams();
		}
		mTickerWindowParams.type = LayoutParams.TYPE_PHONE;
		mTickerWindowParams.format = PixelFormat.RGBA_8888;
		mTickerWindowParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
		mTickerWindowParams.gravity = Gravity.TOP | Gravity.END;
		mTickerWindowParams.width = LayoutParams.WRAP_CONTENT;
		mTickerWindowParams.height = LayoutParams.WRAP_CONTENT;
		windowManager.addView(mTickerWindow, mTickerWindowParams);
		startTicker(ticker);
	}

	@SuppressLint("InflateParams")
	public void createLockedWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		if (null == mLockedWindow) {
			mLockedWindow = LayoutInflater.from(context).inflate(R.layout.fragment_lock, null);
		}
		mLockMainContentbg = (ImageView) mLockedWindow.findViewById(R.id.block_container_bg);
		mLockUserTipsTextView = (TextView) mLockedWindow.findViewById(R.id.lock_content_text1);
		mLockContentTextView = (TextView) mLockedWindow.findViewById(R.id.lock_content_text2);
		if (null == mLockedWindowParams) {
			mLockedWindowParams = new LayoutParams();
		}
		mLockedWindowParams.type = LayoutParams.TYPE_PHONE;
		mLockedWindowParams.format = PixelFormat.RGBA_8888;
		mLockedWindowParams.flags = LayoutParams.FLAG_FULLSCREEN;
		mLockedWindowParams.gravity = Gravity.CENTER;
		mLockedWindowParams.width = LayoutParams.MATCH_PARENT;
		mLockedWindowParams.height = LayoutParams.MATCH_PARENT;
		windowManager.addView(mLockedWindow, mLockedWindowParams);
		setLockedWindowInfos();
	}

	private void startTicker(final int ticker) {
		cancelTicker();
		if (0 == ticker) {
			// 发送锁定系统消息
			mUIHandler.sendEmptyMessage(Constant.DEVICE_LOCK);
		} else {
			mTickTask = new TimerTask() {
				int totalTimer = ticker;

				@Override
				public void run() {
					if (0 == totalTimer) {
						// 发送解锁系统消息
						mUIHandler.sendEmptyMessage(Constant.DEVICE_LOCK);
					} else {
						Message msg = new Message();
						msg.what = Constant.DEVICE_TICKER;
						msg.arg1 = --totalTimer;
						// 发送倒计时消息
						mUIHandler.sendMessage(msg);
					}
				}
			};

			mTickTimer = new Timer();
			mTickTimer.schedule(mTickTask, 0, 1000);
		}
	}

	private void setLockedWindowInfos() {
		BitmapUtil.setFadeInImage(ConfigMgr.getInstance().getBeanVaule(CCViewConfig.SMART_LOCKED_BG), mLockMainContentbg);
		mLockUserTipsTextView.setText(String.format(LVBXApp.getApp().getString(R.string.dear_client), UserMgr.getUserName()));
		mLockContentTextView.setText(ConfigMgr.getInstance().getBeanVaule(CCViewConfig.SMART_LOCKED_CONTENT));
	}
	
	@SuppressLint("InflateParams")
	public void createCloseLockedWindow(Context context) {
		//关闭视频和音乐
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !mediaActivity.isFinishing()) {
			mediaActivity.finish();
		}
		LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
		WindowManager windowManager = getWindowManager(context);
		if (null == mLockedWindow) {
			mLockedWindow = LayoutInflater.from(context).inflate(R.layout.fragment_close_lock, null);
		}
		if (null == mLockedWindowParams) {
			mLockedWindowParams = new LayoutParams(); 
		}
		mLockedWindowParams.type = LayoutParams.TYPE_PHONE;
		mLockedWindowParams.format = PixelFormat.RGBA_8888;
		mLockedWindowParams.flags = LayoutParams.FLAG_FULLSCREEN;
		mLockedWindowParams.gravity = Gravity.CENTER;
		mLockedWindowParams.width = LayoutParams.MATCH_PARENT;
		mLockedWindowParams.height = LayoutParams.MATCH_PARENT;
		windowManager.addView(mLockedWindow, mLockedWindowParams);
	}

	public void removeInvalideWindow(Context context) {
		if (null != mInvalideWindow) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(mInvalideWindow);
			mInvalideWindow = null;
			mInvalideWindowParams = null;
		}
	}

	public void removeLVBEntryWindos(Context context) {
		if (null != mLVBEntryWindow) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(mLVBEntryWindow);
			mLVBEntryWindow = null;
			mLVBEntryWindowParams = null;
		}
	}

	public void removeLVBRCWindos(Context context) {
		if (null != mLVBRcWindow) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(mLVBRcWindow);
			mLVBRcWindow = null;
			mLVBRcWindowParams = null;
		}
	}

	public void removeTickWindow(Context context) {
		if (null != mTickerWindow) {
			cancelTicker();
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(mTickerWindow);
			mTickerWindow = null;
			mTickerWindowParams = null;
		}
	}

	public void removeLockWindow(Context context) {
		if (null != mLockedWindow) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(mLockedWindow);
			mLockedWindow = null;
			mLockedWindowParams = null;
		}
	}

	private void cancelTicker() {
		if (null != mTickTimer) {
			mTickTimer.cancel();
			mTickTimer.purge();
			mTickTimer = null;
		}
	}

	public boolean isInvalideWindowShowing() {
		return null != mInvalideWindow;
	}

	public boolean isEntryWindowShowing() {
		return null != mLVBEntryWindow;
	}

	public boolean isTickWindowShowing() {
		return null != mTickerWindow;
	}

	public boolean isLockedWindowShowing() {
		return null != mLockedWindow;
	}

	public void setInvalidWindowShowContent(String content) {
		if (null != mInvalideWindow) {
			LayoutParams params = new LayoutParams();
			params.gravity = Gravity.CENTER;
			mInvalideWindow.setLayoutParams(params);
			mInvalideWindow.setPadding(CoordinateUtil.getX(40), CoordinateUtil.getX(20), CoordinateUtil.getX(40), CoordinateUtil.getX(20));
			mInvalideWindow.setGravity(Gravity.CENTER);
			mInvalideWindow.setTextColor(Color.RED);
			mInvalideWindow.setTextSize(CoordinateUtil.getX(35));
			mInvalideWindow.setBackgroundResource(R.drawable.authourith_tips_bg);
			mInvalideWindow.setText(LVBXApp.getApp().getString(R.string.devices_isnot_licensed) + "\n" + content);
		}
	}

	public WindowManager getWindowManager(Context context) {
		if (null == mLVBEntryWindowManager) {
			mLVBEntryWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return mLVBEntryWindowManager;
	}

	public void showNormalWindow() {
		removeTickWindow(LVBXApp.getApp());
		removeLockWindow(LVBXApp.getApp());
	}

	public void showTickerWindow(int ticker) {
		removeLockWindow(LVBXApp.getApp());
		createTickerWindow(LVBXApp.getApp(), ticker);
	}

	public void showLockedWindow() {
		if (null != MediaPlayerActivity.getInstance()) {
			MediaPlayerActivity.getInstance().finish();
		}

		removeTickWindow(LVBXApp.getApp());
		createLockedWindow(LVBXApp.getApp());
	}

	public void updateTickerWindow(int sencond) {
		if (null != mTickerWindow) {
			double millSeconds = sencond * 1000;
			mTickershowContainer.setVisibility(View.VISIBLE);
			mTickershowTextView.setText(String.format(LVBXApp.getApp().getString(R.string.time_still_tips), StringUtil.generateTime(millSeconds)));
		}
	}

	@SuppressLint("HandlerLeak")
	class UIHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 正常状态
			case Constant.DEVICE_NORMAL:
				showNormalWindow();
				break;
			// 倒计时状态
			case Constant.DEVICE_TICKER:
				updateTickerWindow(msg.arg1);
				break;
			// 锁定状态
			case Constant.DEVICE_LOCK:
				showLockedWindow();
				break;
			default:
				break;
			}
		}
	}

}

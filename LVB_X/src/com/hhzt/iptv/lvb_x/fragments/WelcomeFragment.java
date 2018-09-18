/**
 * 作者：   Johnson
 * 日期：   2014年6月24日上午10:48:08
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：WelcomeFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.interfaces.IOnImageRequestSuccessCB;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mediaplayer.LVBMusicBgMediaPlayer;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.mgr.UpdateMgr;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;

@SuppressLint("HandlerLeak")
public class WelcomeFragment extends BaseFragment {

	@ViewInject(R.id.welcome_main)
	private RelativeLayout mWelcomeRelativeLayout;
	@ViewInject(R.id.hotellogimage)
	private ImageView mWelcomeHotelLogoImageView;
	@ViewInject(R.id.welcome_client)
	private TextView mWelcomeHotelWelcomClient;
	@ViewInject(R.id.welcome_contents)
	private TextView mWelcomeHotelWelcomeContents;
	@ViewInject(R.id.chinaSet)
	private Button mLanguageSetChinaButton;
	@ViewInject(R.id.englishSet)
	private Button mLanguageSetEnglishButton;
	@ViewInject(R.id.welcome_bg_img1)
	private ImageView mWelcomeBgImageView1;
	@ViewInject(R.id.welcome_bg_img2)
	private ImageView mWelcomeBgImageView2;
	@ViewInject(R.id.welcome_pop_container)
	private RelativeLayout mWelcomePopContainer;
	@ViewInject(R.id.welcome_pop_bg)
	private ImageView mWelcomePopBgImageView;

	private ArrayList<String> mWelcomeBgUrls;
	private WelcomeHandler mWelcomeHandler;
	private int mWelcomeBgIndex = 0;
	private static boolean mIsFirstBgImage = true;
	private UpdateMgr mUpdateMgr;

	@OnClick({ R.id.chinaSet, R.id.englishSet })
	public void entryMainActivity(View v) {
		switch (v.getId()) {
		case R.id.chinaSet:
			setLanguage();
			break;
		case R.id.englishSet:
			SystemMgr.setSystemLangType(Constant.IPTV_SYSTEM_LANG_US_ENGLISH);
			SystemMgr.setSystemLangName(LVBXApp.getApp().getString(R.string.english));
			SystemMgr.changeLanguage(Constant.IPTV_SYSTEM_LANG_US_ENGLISH);
			ActivitySwitchMgr.gotoMainActivity(getActivity());
			break;
		default:
			break;
		}
	}

	/**
	 * 设置为中文
	 */
	private void setLanguage() {
		LogUtil.d("setLanguage-----");
		SystemMgr.setSystemLangType(Constant.IPTV_SYSTEM_LANG_SIMPLE_CHINESE);
		SystemMgr.setSystemLangName(LVBXApp.getApp().getString(R.string.simple_chinese));
		SystemMgr.changeLanguage(Constant.IPTV_SYSTEM_LANG_SIMPLE_CHINESE);
		ActivitySwitchMgr.gotoMainActivity(getActivity());

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (null == savedInstanceState) {

//			startBackgroundMusic();
		}
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_welcome, container, false);
		ViewUtils.inject(this, view);
		return view;
	}
	

	private void setSystemWantedLang() {
		View view = null;
		int languageFlag = SystemMgr.getWantedLangType();
		switch (languageFlag) {
		case Constant.IPTV_SYSTEM_LANG_US_ENGLISH:
			view = mLanguageSetEnglishButton;
			break;
		case Constant.IPTV_SYSTEM_LANG_SIMPLE_CHINESE:
			view = mLanguageSetChinaButton;
			break;
		default:
			break;
		}

		if (null != view) {
			entryMainActivity(view);
		} else {
			setDefaultFocus();
		}

		SystemMgr.setWantedLangType(0);
	}

	private void setDefaultFocus() {
		mLanguageSetChinaButton.requestLayout();
		mLanguageSetChinaButton.requestFocus();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
//			checkNewVersion();
//			updateWelcomeInfos(true);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		setLanguage();
//		setDefaultCity();
//		setSystemWantedLang();
//		updateWelcomeInfos(false);
//		CCAnimationUtils.setRandomAnimation(mWelcomePopContainer);
//		continueBackgroundMusic();
	}

	private void setDefaultCity() {
		// 没有设置过城市才进行天气城市的设置
		if (!UIDataller.getDataller().hasSettedCity()) {
			UIDataller.getDataller().setDefaultCity();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		stopAllHandler();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LVBMusicBgMediaPlayer bgMusicPlayer = LVBMusicBgMediaPlayer.getInstance();
		bgMusicPlayer.releseMusicPlayer();
	}

//	private void checkNewVersion() {
//		if (null == mUpdateMgr) {
//			mUpdateMgr = new UpdateMgr(getActivity());
//		}
//
//		mUpdateMgr.checkNewVersion(); 
//	}

	// 播放背景音乐
	private void startBackgroundMusic() {
		UIDataller.getDataller().startPlayBackgroundMusic(LVBXApp.getApp());
	}

	// 继续播放背景音乐
	private void continueBackgroundMusic() {
		UIDataller.getDataller().continuePlayBackgroundMusic();
	}

	@SuppressLint("NewApi")
	public void updateWelcomeInfos(boolean enableHotspot) {
		UIDataller.getDataller().setWelcomeDatas(getActivity(), mWelcomeHotelWelcomClient, mWelcomeHotelWelcomeContents, enableHotspot);
		// BitmapUtil.setFadeInImage(ConfigMgr.getInstance().getBeanVaule(CCViewConfig.WELCOME_BG),
		// mWelcomeBgImageView1);

		UIDataller.getDataller().setWelcomeAniBg(getActivity(), new IOnImageRequestSuccessCB() {

			@Override
			public void successCB(ArrayList<String> urls) {
				mWelcomeBgUrls = urls;
				if (null != urls && urls.size() > 0) {
					if (null == mWelcomeHandler) {
						mWelcomeHandler = new WelcomeHandler();
					}
					mWelcomeHandler.sendEmptyMessage(Constant.IPTV_MSG_WELCOME_BG_CHANGE);
				}
			}
		});
		setWelcomeContentBgAlpha();
		BitmapUtil.setFadeInImage(ConfigMgr.getInstance().getBeanVaule(CCViewConfig.HOTEL_LOGO), mWelcomeHotelLogoImageView);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			switch (Config.LvbDeviceType) {
			case Constant.DEVICE_TYPE_MOBILE:
			case Constant.DEVICE_TYPE_MOBILE_HSJQ:
				getActivity().finish();
				android.os.Process.killProcess(android.os.Process.myPid());
				return true;
			default:
				return true;
			}
		case KeyEvent.KEYCODE_MENU:
			ActivitySwitchMgr.gotoSettingActivity(getActivity());
			return true;
		default:
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	private void setWelcomeContentBgAlpha() {
		float alpha = 1.0f;
		try {
			String value = ConfigMgr.getInstance().getBeanName(CCViewConfig.WELCOME_POP_BG_ALPHA);
			if (!StringUtil.isEmpty(value) && StringUtil.isFloatPointNumber(value)) {
				float f = Float.valueOf(value);
				if (f >= 0 && f <= 1) {
					alpha = f;
				}
			} else if (!StringUtil.isEmpty(value) && StringUtil.isIntegerNumber(value)) {
				int i = Integer.valueOf(value);
				if (0 == i) {
					alpha = (float) i;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mWelcomePopBgImageView.setAlpha(alpha);
		}
	}

	private void stopAllHandler() {
		if (null != mWelcomeHandler) {
			mWelcomeHandler.removeMessages(Constant.IPTV_MSG_WELCOME_BG_CHANGE);
			mIsFirstBgImage = true;
			mWelcomeHandler = null;
		}
	}

	public class WelcomeHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.IPTV_MSG_WELCOME_BG_CHANGE:
				if (null != mWelcomeBgUrls && mWelcomeBgUrls.size() > 1) {
					if (mIsFirstBgImage) {
						BitmapUtil.setRandomImage(mWelcomeBgUrls.get(mWelcomeBgIndex), mWelcomeBgImageView1);
					} else {
						BitmapUtil.setRandomImage(mWelcomeBgUrls.get(mWelcomeBgIndex), mWelcomeBgImageView2);
					}
					mIsFirstBgImage = !mIsFirstBgImage;
					nextBgImageIndex();
					removeMessages(Constant.IPTV_MSG_WELCOME_BG_CHANGE);
					sendEmptyMessageDelayed(Constant.IPTV_MSG_WELCOME_BG_CHANGE, Constant.IPTV_TIME_FIVE_SECONDES);
				} else if (null != mWelcomeBgUrls && mWelcomeBgUrls.size() == 1) {
					BitmapUtil.setRandomImage(mWelcomeBgUrls.get(mWelcomeBgIndex), mWelcomeBgImageView1);
				} else {
					// 未取到数据，不设置
				}
				break;
			default:
				break;
			}
		}

		private void nextBgImageIndex() {
			mWelcomeBgIndex++;
			if (mWelcomeBgIndex >= mWelcomeBgUrls.size()) {
				mWelcomeBgIndex = 0;
			}
		}

	}

}

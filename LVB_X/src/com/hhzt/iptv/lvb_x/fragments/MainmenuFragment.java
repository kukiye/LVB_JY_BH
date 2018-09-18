/**
 * 作者：   Johnson
 * 日期：   2014年6月24日上午11:30:28
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：MainmenuFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.interfaces.IOnMainMenuDataSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IResponseable;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.mgr.MenuDataMgr;
import com.hhzt.iptv.lvb_x.mgr.UpdateMgr;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.model.MainmenuBgModel;
import com.hhzt.iptv.lvb_x.model.MainmenuModel;
import com.hhzt.iptv.lvb_x.model.NewsSingleItem;
import com.hhzt.iptv.lvb_x.model.ViewConfigBean;
import com.hhzt.iptv.lvb_x.net.LVBHttpUtils;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

public class MainmenuFragment extends BaseFragment {

	@ViewInject(R.id.mainmenu_left_arrow)
	private ImageView mLeftArrowTipsImageView;
	@ViewInject(R.id.mainmenu_right_arrow)
	private ImageView mRightArrowTipsImageView;

	@ViewInject(R.id.hotellogo)
	private ImageView mHotelImageLogoImageView;
	@ViewInject(R.id.mainmenu_bg1)
	private ImageView mMainmenuBgImageView1;
	@ViewInject(R.id.mainmenu_bg2)
	private ImageView mMainmenuBgImageView2;

	@ViewInject(R.id.mainmenu_date)
	private TextView mMainmenuDateTextView;
	@ViewInject(R.id.mainmenu_time)
	private TextView mMainmenuTimeTextView;
	@ViewInject(R.id.mainmenu_week)
	private TextView mMainmenuWeekTextView;
	@ViewInject(R.id.mainmenu_content_container)
	private LinearLayout mMainMenuLinearLayoutContainer;

	@ViewInject(R.id.tv_mainmenu_name)
	private TextView mMainmenuName;

	// 主界面广告栏目
	@ViewInject(R.id.mainmenu_bg1)
	private ImageView mMainMenuBg1;

	@ViewInject(R.id.mainmenu_bg2)
	private ImageView mMainMenuBg2;
	@ViewInject(R.id.mainmenu_txt2)
	private TextView mMainmenuTxt2;

	@ViewInject(R.id.mainmenu_bg3)
	private ImageView mMainMenuBg3;
	@ViewInject(R.id.mainmenu_txt3)
	private TextView mMainmenuTxt3;

	@ViewInject(R.id.mainmenu_bg4)
	private ImageView mMainMenuBg4;
	@ViewInject(R.id.mainmenu_txt4)
	private TextView mMainmenuTxt4;
	
	@ViewInject(R.id.main_logo_img)
	private ImageView mMainLoginImageView;

	// private int mSubMenuListModelsIndex = 0;
	// private ArrayList<String> mMainMenuBgUrls;
	private MainmenuBgModel mBgModels;

	private MainMenuHandler mMainMenuHandler;
	private static boolean mIsFirstBgImage = true;
	private DateAlarmReceiver mDateAlarmReceiver;
	private IntentFilter mDateAlarmFilter;

	// 消息
	private NewsSingleItem mNewsItem;

	private UpdateMgr mUpdateMgr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		FloatWindowManager.getInstance().createCloseLockedWindow(getActivity().getApplicationContext());
//		DeviceUtil.setAudioMusic(getActivity(), 12);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mainmenu, container,
				false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			UIDataller.getDataller().setMainMenuHoriontalScroviewData(
					new IOnMainMenuDataSuccessCB() {

						@Override
						public void onDataSuccessCB(
								ArrayList<MainmenuModel> items) {
							MenuDataMgr.setMainMenuDataListeners(getActivity(),
									items, mMainMenuLinearLayoutContainer);
						}
					});

		}
		registerReceiver();
		setTimeShow(true);
		checkNewVersion();
	}

	private void checkNewVersion() {
		if (null == mUpdateMgr) {
			mUpdateMgr = new UpdateMgr(getActivity());
		}

		mUpdateMgr.checkNewVersion();
	}

	private void registerReceiver() {
		mDateAlarmReceiver = new DateAlarmReceiver();
		mDateAlarmFilter = new IntentFilter(
				Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE);
		BaseActivity.getInstance().getApplicationContext().registerReceiver(mDateAlarmReceiver, mDateAlarmFilter);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			ActivitySwitchMgr.gotoSettingActivity(getActivity());
			return true;
		case KeyEvent.KEYCODE_BACK:
			return true;
		default:
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onStart() {
		super.onStart();
		startDateUpdateAlarm();
	}

	@Override
	public void onResume() {
		super.onResume();
		setTimeShow(true);
		updateHotelBgInfos();
		playBackgroundMusic();
		UIDataller.getDataller().checkForcePlayMission();

		String mainmenuName = ConfigMgr.getInstance().getBeanVaule(
				CCViewConfig.HOTEL_NAME);
		if (mainmenuName != null) {
			mMainmenuName.setText(mainmenuName);
		}
		
		String mainLogo = ConfigMgr.getInstance().getBeanVaule(
				CCViewConfig.MAINMENU_LOGO);
		if (mainLogo != null) {
			BitmapUtil.setImage(mainLogo, mMainLoginImageView); 
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mMainMenuHandler != null) {
			mMainMenuHandler.removeMessages(Constant.IPTV_MSG_MAINMENU_BG_CHANGE);
			mMainMenuHandler.removeMessages(Constant.IPTV_MSG_HOTERSERVICE_BG_CHANGE);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		stopAllHandler();
		cancelDateUpdateAlarm();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		BaseActivity.getInstance().getApplicationContext().unregisterReceiver(mDateAlarmReceiver);
	}

	// 设置和更新主界面时间信息
	private void setTimeShow(boolean needAnimation) {
		UIDataller.getDataller().setMainMenuDateInfo(mMainmenuDateTextView,
				mMainmenuTimeTextView, mMainmenuWeekTextView, needAnimation);
	}

	// 更新主界面背景图片的显示
	private void updateHotelBgInfos() {
		// UIDataller.getDataller().setMainMenuAniBg(getActivity(), new
		// IOnImageRequestSuccessCB() {
		//
		// @Override
		// public void successCB(ArrayList<String> urls) {
		// mMainMenuBgUrls = urls;
		// if (null != urls && urls.size() > 0) {
		// if (null == mMainMenuHandler) {
		// mMainMenuHandler = new MainMenuHandler();
		// }
		// mMainMenuHandler.sendEmptyMessage(Constant.IPTV_MSG_MAINMENU_BG_CHANGE);
		// }
		// }
		// });

		String url = UrlMgr.getViewConfigUrl();
		 LogUtil.d("initViewTempConfigInfo--------" + url);
		if (mBgModels == null) {
			mBgModels = new MainmenuBgModel();
		}
		LVBHttpUtils.get(url, new IResponseable() {

			@Override
			public void onSuccess(String result) {
				Gson gson = new Gson();
				ArrayList<ViewConfigBean> listModels = gson.fromJson(result,
						new TypeToken<ArrayList<ViewConfigBean>>() {
						}.getType());
				ConfigMgr.getInstance().addConfigs(listModels);

				for (int i = 1; i < 5; i++) {
					String name = CCViewConfig.MAINMENU_BG_HEAD + i;
					String url = ConfigMgr.getInstance().getBeanVaule(name);
					if (!StringUtil.isEmpty(url) && i == 1) {
						mBgModels.setBgUrl1(url);
					}
					if (!StringUtil.isEmpty(url) && i == 2) {
						mBgModels.setBgUrl2(url);
					}
					if (!StringUtil.isEmpty(url) && i == 3) {
						mBgModels.setBgUrl3(url);
					}
					if (!StringUtil.isEmpty(url) && i == 4) {
						mBgModels.setBgUrl4(url);
					}
				}
				if (null == mMainMenuHandler) {
					mMainMenuHandler = new MainMenuHandler();
				}
				mMainMenuHandler
						.sendEmptyMessage(Constant.IPTV_MSG_MAINMENU_BG_CHANGE);
			}

			@Override
			public void onFailed(String result) {

			}
		});
	}

	// 播放背景音乐
	private void playBackgroundMusic() {
		UIDataller.getDataller().continuePlayBackgroundMusic();
	}

	// private void nextBgImageIndex() {
	// ++mSubMenuListModelsIndex;
	// if (mSubMenuListModelsIndex >= mMainMenuBgUrls.size()) {
	// mSubMenuListModelsIndex = 0;
	// }
	// }

	/**
	 * 发送更新时间的刻度表
	 */
	private void startDateUpdateAlarm() {
		AlarmManager am = (AlarmManager) getActivity().getSystemService(
				Context.ALARM_SERVICE);
		Intent intent = new Intent(
				Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE);
		PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// 重复发送时间刻度表
		am.setRepeating(AlarmManager.RTC, System.currentTimeMillis()
				+ Constant.IPTV_TIME_ONE_MINUTE, Constant.IPTV_TIME_ONE_MINUTE,
				pi);
	}

	/**
	 * 取消时间更新刻度表
	 */
	private void cancelDateUpdateAlarm() {
		AlarmManager am = (AlarmManager) getActivity().getSystemService(
				Context.ALARM_SERVICE);
		Intent intent = new Intent(
				Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE);
		PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		am.cancel(pi);
	}

	private void stopAllHandler() {
		if (null != mMainMenuHandler) {
			mMainMenuHandler
					.removeMessages(Constant.IPTV_MSG_MAINMENU_BG_CHANGE);
			mIsFirstBgImage = true;
			mMainMenuHandler = null;
		}
	}

	@SuppressLint("HandlerLeak")
	public class MainMenuHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.IPTV_MSG_MAINMENU_BG_CHANGE:
				if (mBgModels != null) {
					// LogUtil.d("mBgModels-------" + mBgModels);
					if (!TextUtils.isEmpty(mBgModels.getBgUrl1())
							&& mBgModels.getBgUrl1() != null) {
						BitmapUtil
								.setImage(mBgModels.getBgUrl1(), mMainMenuBg1);
					}
					if (!TextUtils.isEmpty(mBgModels.getBgUrl2())
							&& mBgModels.getBgUrl2() != null) {
						BitmapUtil
								.setImage(mBgModels.getBgUrl2(), mMainMenuBg2);
					}
					if (!TextUtils.isEmpty(mBgModels.getBgUrl3())
							&& mBgModels.getBgUrl3() != null) {
						BitmapUtil
								.setImage(mBgModels.getBgUrl3(), mMainMenuBg3);
					}
					if (!TextUtils.isEmpty(mBgModels.getBgUrl4())
							&& mBgModels.getBgUrl4() != null) {
						BitmapUtil
								.setImage(mBgModels.getBgUrl4(), mMainMenuBg4);
					}
				}
				removeMessages(msg.what);
				sendEmptyMessageDelayed(
						Constant.IPTV_MSG_HOTERSERVICE_BG_CHANGE, 20000);
				break;
			case Constant.IPTV_MSG_HOTERSERVICE_BG_CHANGE:
				removeMessages(msg.what);
				updateHotelBgInfos();
				break;
			default:
				break;
			}
		}
	}

	public class DateAlarmReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE.equals(intent
					.getAction())) {
				setTimeShow(false);
			}
		}
	}

}

package com.hhzt.iptv.lvb_x.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class PrisonInfoFragment extends BaseFragment {

	@ViewInject(R.id.mainmenu_img)
	private ImageView mMainmenuImageView; // 图片
	@ViewInject(R.id.mainmenu_txt)
	private TextView mMainmenuTitleTextView; // 文字
	@ViewInject(R.id.weather_time_container)
	private LinearLayout mWeatherTimeContainer;
	@ViewInject(R.id.ll_prison_contrainer)
	private LinearLayout mPrisonContrainer;
	@ViewInject(R.id.tv_mainmenu_name)
	private TextView mMainmenuName;

	// 时钟
	@ViewInject(R.id.mainmenu_date)
	private TextView mMainmenuDateTextView;
	@ViewInject(R.id.mainmenu_time)
	private TextView mMainmenuTimeTextView;
	@ViewInject(R.id.mainmenu_week)
	private TextView mMainmenuWeekTextView;
	private DateAlarmReceiver mDateAlarmReceiver;
	private IntentFilter mDateAlarmFilter;
	
	@ViewInject(R.id.mainmenu_logo_b)
	private ImageView mMainLoginImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_prison_info, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			setValue();
			registerReceiver();
		}
	}

	private void setValue() {
		mMainmenuImageView.setImageResource(R.drawable.prision_info_img);
		mWeatherTimeContainer.setVisibility(View.VISIBLE);
		mPrisonContrainer.setVisibility(View.VISIBLE);
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			String username = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			if (username != null) {
				mMainmenuTitleTextView.setText(username);
			}
		}

		String mainmenuName = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.HOTEL_NAME);
		if (mainmenuName != null) {
			mMainmenuName.setText(mainmenuName);
		}
		
		String mainLogo = ConfigMgr.getInstance().getBeanVaule(
				CCViewConfig.MAINMENU_LOGO);
		if (mainLogo != null) {
			BitmapUtil.setImage(mainLogo, mMainLoginImageView); 
		}
	}

	@OnClick({ R.id.prison_info_news_btn, R.id.prison_info_websit_btn })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.prison_info_news_btn:
//			ActivitySwitchMgr.gotoPrisonInfoCategoryActivity(getActivity(), getActivity()
//					.getString(R.string.prison_info_news_title));
			ActivitySwitchMgr.gotoPrisonNewsActivity(getActivity(),
					mMainmenuTitleTextView.getText().toString());
			break;
		case R.id.prison_info_websit_btn:
			ActivitySwitchMgr.gotoPrisonNewsWebSitActivity(getActivity(), getActivity()
					.getString(R.string.prison_info_website_title));
			break;

		default:
			break;
		}
	}

	// 设置和更新主界面时间信息
	private void setTimeShow(boolean needAnimation) {
		UIDataller.getDataller().setMainMenuDateInfo(mMainmenuDateTextView, mMainmenuTimeTextView,
				mMainmenuWeekTextView,  needAnimation);
	}

	/**
	 * 注册时钟
	 */
	private void registerReceiver() {
		mDateAlarmReceiver = new DateAlarmReceiver();
		mDateAlarmFilter = new IntentFilter(Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE);
		BaseActivity.getInstance().getApplicationContext().registerReceiver(mDateAlarmReceiver, mDateAlarmFilter);
	}

	public class DateAlarmReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE.equals(intent.getAction())) {
				setTimeShow(false);
			}
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setTimeShow(true);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (mDateAlarmReceiver != null) {
			BaseActivity.getInstance().getApplicationContext().unregisterReceiver(mDateAlarmReceiver);
		}
	}
}

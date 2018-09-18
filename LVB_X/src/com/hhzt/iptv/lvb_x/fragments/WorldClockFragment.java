/**
 * 作者：   Johnson
 * 日期：   2014年7月12日下午4:00:36
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：WorldClockFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.TimeZoneType;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.effect.TextShadowEffect;
import com.hhzt.iptv.lvb_x.utils.CCAnimationUtils;
import com.hhzt.iptv.lvb_x.utils.DateTimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class WorldClockFragment extends BaseFragment {

	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.main_type_imgae)
	private ImageView mHomeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mCurrentPathTextView;
	@ViewInject(R.id.tips_back)
	private TextView mActionBackTipsTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mActionOkTipsTextView;

	@ViewInject(R.id.analogclock1_time)
	private TextView mAnalogClockTimeOne;
	@ViewInject(R.id.analogclock1_city_main)
	private TextView mWorlClockTimeMainCityOne;
	@ViewInject(R.id.analogclock1_city_sub)
	private TextView mWorlClockTimeSubCityOne;

	@ViewInject(R.id.analogclock2_time)
	private TextView mAnalogClockTimeTwo;
	@ViewInject(R.id.analogclock2_city_main)
	private TextView mWorlClockTimeMainCityTwo;
	@ViewInject(R.id.analogclock2_city_sub)
	private TextView mWorlClockTimeSubCityTwo;

	@ViewInject(R.id.analogclock3_time)
	private TextView mAnalogClockTimeThree;
	@ViewInject(R.id.analogclock3_city_main)
	private TextView mWorlClockTimeMainCityThree;
	@ViewInject(R.id.analogclock3_city_sub)
	private TextView mWorlClockTimeSubCityThree;

	@ViewInject(R.id.detail_worldclock_bg)
	private ImageView mWorldClockImageViewBg;

	@ViewInject(R.id.analogclock1)
	private View mAnalogClockBeijing;
	@ViewInject(R.id.analogclock2)
	private View mAnalogClockNewYork;
	@ViewInject(R.id.analogclock3)
	private View mAnalogClockLondon;

	private String mMenuPath;
	private String mMenuCode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_world_clock, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			mMenuCode = getActivity().getIntent().getStringExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG);
			setAllCityShow();
			setWorldClockBg();
			getLocalDataMetaInfos();
		}
	}

	private void setAllCityShow() {
		mAnalogClockTimeOne.setText(DateTimeUtil.getTimeInTimeZone(TimeZoneType.timeZoneBeijing, DateTimeUtil.DATE_FORMATE_HOUR_MINUTE));
		mWorlClockTimeMainCityOne.setText(R.string.beijing_cn);
		mWorlClockTimeSubCityOne.setText(R.string.beijing_en);
		TextShadowEffect.getInstance().setBlueShadow(5.0f, 2.0f, 2.0f, Color.argb(255, 0, 140, 255), mAnalogClockTimeOne, mWorlClockTimeMainCityOne,
				mWorlClockTimeSubCityOne);
		CCAnimationUtils.setRotateAnimation(mAnalogClockBeijing);
		CCAnimationUtils.setRandomAnimation(mAnalogClockTimeOne, mWorlClockTimeMainCityOne, mWorlClockTimeSubCityOne);

		mAnalogClockTimeTwo.setText(DateTimeUtil.getTimeInTimeZone(TimeZoneType.timeZoneNewYork, DateTimeUtil.DATE_FORMATE_HOUR_MINUTE));
		mWorlClockTimeMainCityTwo.setText(R.string.newyork_cn);
		mWorlClockTimeSubCityTwo.setText(R.string.newyork_en);
		TextShadowEffect.getInstance().setBlueShadow(5.0f, 2.0f, 2.0f, Color.argb(255, 0, 140, 255), mAnalogClockTimeTwo, mWorlClockTimeMainCityTwo,
				mWorlClockTimeSubCityTwo);
		CCAnimationUtils.setRotateAnimation(mAnalogClockNewYork);
		CCAnimationUtils.setRandomAnimation(mAnalogClockTimeTwo, mWorlClockTimeMainCityTwo, mWorlClockTimeSubCityTwo);

		mAnalogClockTimeThree.setText(DateTimeUtil.getTimeInTimeZone(TimeZoneType.timeZoneLondon, DateTimeUtil.DATE_FORMATE_HOUR_MINUTE));
		mWorlClockTimeMainCityThree.setText(R.string.london_cn);
		mWorlClockTimeSubCityThree.setText(R.string.london_en);
		TextShadowEffect.getInstance().setBlueShadow(5.0f, 2.0f, 2.0f, Color.argb(255, 0, 140, 255), mAnalogClockTimeThree,
				mWorlClockTimeMainCityThree, mWorlClockTimeSubCityThree);
		CCAnimationUtils.setRotateAnimation(mAnalogClockLondon);
		CCAnimationUtils.setRandomAnimation(mAnalogClockTimeThree, mWorlClockTimeMainCityThree, mWorlClockTimeSubCityThree);
	}

	private void setWorldClockBg() {
		Intent intent = getActivity().getIntent();
		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));

		UIDataller ller = UIDataller.getDataller();
		ller.getThirdMenuImageBgInfos(getActivity(), mMenuCode, mWorldClockImageViewBg, null, 0, null, null);
	}

	private void getLocalDataMetaInfos() {
		UIDataller ller = UIDataller.getDataller();
		ller.setHsActionTips(getActivity(), mHomeImageView, R.drawable.home_icon, mCurrentPathTextView, mMenuPath, mActionOkTipsTextView,
				ller.getOkActionTips(getActivity()), mActionBackTipsTextView, ller.getBackActionTips(getActivity()));
	}

}

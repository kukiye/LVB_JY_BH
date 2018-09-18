package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CitySetFragment extends BaseFragment {

	@ViewInject(R.id.main_type_imgae)
	private ImageView mMainTypeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mMainTypeTextView;
	@ViewInject(R.id.tips_back)
	private TextView mBackTipsTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mOkTipsTextView;
	@ViewInject(R.id.current_city_name)
	private TextView mCurrentCityNameTextView;
	@ViewInject(R.id.city_data_item)
	private GridView mSelectCityGridView;

	private String mMenuPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cityset, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			setValue();
		}
	}

	private void setValue() {
		Intent intent = getActivity().getIntent();
		int screenTag1 = intent.getIntExtra(Constant.IPTV_SYS_FRAGMENT_TAG, 0);
		boolean needSaveTag = (screenTag1 == Constant.HOTEL_TRAVEL_WEATHER_SCREEN) ? false : true;
		String passedCityName = intent.getStringExtra(Constant.IPTV_USER_HOTEL_CITY_NAME);

		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		UIDataller ller = UIDataller.getDataller();
		ller.setHsActionTips(getActivity(), mMainTypeImageView, R.drawable.home_icon, mMainTypeTextView, mMenuPath, mOkTipsTextView,
				ller.getOkActionTips(getActivity()), mBackTipsTextView, ller.getBackActionTips(getActivity()));

		ller.getCitySettingMessage(getActivity(), mSelectCityGridView, mCurrentCityNameTextView, needSaveTag, passedCityName);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}

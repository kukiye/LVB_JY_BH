/**
 * 作者：   Johnson
 * 日期：   2014年7月12日上午9:25:27
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：WeatherDetailFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.interfaces.IOnWeatherInfosSuccessCB;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.WeatherInfoModel;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.hhzt.iptv.lvb_x.utils.DateTimeUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.hhzt.iptv.ui.CitySetActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class WeatherDetailFragment extends BaseFragment {
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

	@ViewInject(R.id.detail_weather_bg)
	private ImageView mWeatherMainImageViewBg;
	@ViewInject(R.id.weather_city)
	private TextView mWeatherCityNameTextView;
	@ViewInject(R.id.weather_update_time)
	private TextView mWeatherUpdateTimeTextView;
	@ViewInject(R.id.city_check)
	private Button mWeatherCityCheckButton;

	// 当天天气详细信息
	@ViewInject(R.id.weather_image)
	private ImageView mWeatherImageView;
	@ViewInject(R.id.weather_temprature_desc)
	private TextView mWeatherTempratureDescTextView;
	@ViewInject(R.id.weather_content_desc)
	private TextView mWeatherContentDesTextView;
	@ViewInject(R.id.weather_wind_desc)
	private TextView mWeatherWindDescTextView;
	@ViewInject(R.id.weather_air_desc_tag)
	private TextView mWeatherAirDesTagTextView;
	@ViewInject(R.id.weather_air_desc_num)
	private TextView mWeatherAirDesNumTextView;

	// 明天天气信息
	@ViewInject(R.id.weather_after_one_day_week)
	private TextView mWeatherAfterOneWeekTextView;
	@ViewInject(R.id.weather_after_one_day_image)
	private ImageView mWeatherAfterOneDayImageView;
	@ViewInject(R.id.weather_after_one_day_temprature)
	private TextView mWeatherAfterOneDayTemTextView;
	@ViewInject(R.id.weather_after_one_day_content)
	private TextView mWeatherAfterOneDayContentTextView;
	@ViewInject(R.id.weather_after_one_day_wind)
	private TextView mWeatherAfterOneDayWindTextView;

	// 后天天气信息
	@ViewInject(R.id.weather_after_two_days_week)
	private TextView mWeatherAfterTwoWeeksTextView;
	@ViewInject(R.id.weather_after_two_days_image)
	private ImageView mWeatherAfterTwoDaysImageView;
	@ViewInject(R.id.weather_after_two_days_temprature)
	private TextView mWeatherAfterTwoDaysTemTextView;
	@ViewInject(R.id.weather_after_two_days_content)
	private TextView mWeatherAfterTwoDaysContentTextView;
	@ViewInject(R.id.weather_after_two_days_wind)
	private TextView mWeatherAfterTwoDaysWindTextView;

	// 大后天天气信息
	@ViewInject(R.id.weather_after_three_days_week)
	private TextView mWeatherAfterThreeWeeksTextView;
	@ViewInject(R.id.weather_after_three_days_image)
	private ImageView mWeatherAfterThreeDaysImageView;
	@ViewInject(R.id.weather_after_three_days_temprature)
	private TextView mWeatherAfterThreeDaysTemTextView;
	@ViewInject(R.id.weather_after_three_days_content)
	private TextView mWeatherAfterThreeDaysContentTextView;
	@ViewInject(R.id.weather_after_three_days_wind)
	private TextView mWeatherAfterThreeDaysWindTextView;

	// 大大后天天气信息
	@ViewInject(R.id.weather_after_four_days_week)
	private TextView mWeatherAfterFourWeeksTextView;
	@ViewInject(R.id.weather_after_four_days_image)
	private ImageView mWeatherAfterFourDaysImageView;
	@ViewInject(R.id.weather_after_four_days_temprature)
	private TextView mWeatherAfterFourDaysTemTextView;
	@ViewInject(R.id.weather_after_four_days_content)
	private TextView mWeatherAfterFourDaysContentTextView;
	@ViewInject(R.id.weather_after_four_days_wind)
	private TextView mWeatherAfterFourDaysWindTextView;

	private String mMenuCode;
	private String mMenuPath;
	private static final int TODAY = 0;
	private static final int AFTERONEDAY = 1;
	private static final int AFTERTWODAYS = 2;
	private static final int AFTERTHREEDAYS = 3;
	private static final int AFTERFOURDAYS = 4;

	@OnClick(R.id.city_check)
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.city_check:
			Intent intent = new Intent(getActivity(), CitySetActivity.class);
			intent.putExtra(Constant.IPTV_USER_HOTEL_CITY_NAME, mWeatherCityNameTextView.getText().toString());
			intent.putExtra(Constant.IPTV_SYS_FRAGMENT_TAG, Constant.HOTEL_TRAVEL_WEATHER_SCREEN);
			startActivityForResult(intent, Constant.SETTING_CITYSETTING_SCREEN);
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weather_main, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			mMenuCode = getActivity().getIntent().getStringExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG);
			setWeatherBg();

			String savedCityName = getSavedCityName();
			String savedCityCode = getSavedCityCode();
			updateWeatherInfos(savedCityName, savedCityCode);
			getLocalDataMetaInfos();
		}
	}

	private void setWeatherBg() {
		Intent intent = getActivity().getIntent();
		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));

		UIDataller ller = UIDataller.getDataller();
		ller.getThirdMenuImageBgInfos(getActivity(), mMenuCode, mWeatherMainImageViewBg, null, 0, null, null);
	}

	private String getSavedCityName() {
		String cityName = UserMgr.getWeatherCityName();
		cityName = StringUtil.isEmpty(cityName) ? getString(R.string.default_city) : cityName;

		return cityName;
	}

	private String getSavedCityCode() {
		String cityCode = UserMgr.getWeatherCityNumCode();

		return cityCode;
	}

	private void updateWeatherInfos(String cityName, String cityCode) {
		mWeatherCityNameTextView.setText(cityName);
		String updateTime = getString(R.string.update_at) + DateTimeUtil.getWeekTime(0) + "  "
				+ DateTimeUtil.toTime(System.currentTimeMillis(), DateTimeUtil.DATE_FORMATE_ALL);
		mWeatherUpdateTimeTextView.setText(updateTime);

		int weatherDay = 5;
		UIDataller ller = UIDataller.getDataller();
		ller.setWeatherDetailInfos(cityCode, weatherDay, new IOnWeatherInfosSuccessCB() {

			@Override
			public void success(ArrayList<WeatherInfoModel> models) {
				for (int i = 0; i < models.size(); i++) {
					setWeatherInfos(i, models.get(i));
				}
			}
		});
	}

	private void setWeatherInfos(int dayIndex, WeatherInfoModel weatherInfoModel) {
		switch (dayIndex) {
		case TODAY:
			BitmapUtil.setFadeInImage(weatherInfoModel.getLogoImageUrl(), mWeatherImageView);
			mWeatherTempratureDescTextView.setText(weatherInfoModel.getTemp());
			mWeatherContentDesTextView.setText(weatherInfoModel.getWeather());
			mWeatherWindDescTextView.setText(weatherInfoModel.getWd());
			mWeatherAirDesNumTextView.setText(weatherInfoModel.getWs());
			break;
		case AFTERONEDAY:
			mWeatherAfterOneWeekTextView.setText(DateTimeUtil.getWeekTime(dayIndex));
			BitmapUtil.setFadeInImage(weatherInfoModel.getLogoImageUrl(), mWeatherAfterOneDayImageView);
			mWeatherAfterOneDayTemTextView.setText(weatherInfoModel.getTemp());
			mWeatherAfterOneDayContentTextView.setText(weatherInfoModel.getWeather());
			mWeatherAfterOneDayWindTextView.setText(weatherInfoModel.getWd());
			break;
		case AFTERTWODAYS:
			mWeatherAfterTwoWeeksTextView.setText(DateTimeUtil.getWeekTime(dayIndex));
			BitmapUtil.setFadeInImage(weatherInfoModel.getLogoImageUrl(), mWeatherAfterTwoDaysImageView);
			mWeatherAfterTwoDaysTemTextView.setText(weatherInfoModel.getTemp());
			mWeatherAfterTwoDaysContentTextView.setText(weatherInfoModel.getWeather());
			mWeatherAfterTwoDaysWindTextView.setText(weatherInfoModel.getWd());
			break;
		case AFTERTHREEDAYS:
			mWeatherAfterThreeWeeksTextView.setText(DateTimeUtil.getWeekTime(dayIndex));
			BitmapUtil.setFadeInImage(weatherInfoModel.getLogoImageUrl(), mWeatherAfterThreeDaysImageView);
			mWeatherAfterThreeDaysTemTextView.setText(weatherInfoModel.getTemp());
			mWeatherAfterThreeDaysContentTextView.setText(weatherInfoModel.getWeather());
			mWeatherAfterThreeDaysWindTextView.setText(weatherInfoModel.getWd());
			break;
		case AFTERFOURDAYS:
			mWeatherAfterFourWeeksTextView.setText(DateTimeUtil.getWeekTime(dayIndex));
			BitmapUtil.setFadeInImage(weatherInfoModel.getLogoImageUrl(), mWeatherAfterFourDaysImageView);
			mWeatherAfterFourDaysTemTextView.setText(weatherInfoModel.getTemp());
			mWeatherAfterFourDaysContentTextView.setText(weatherInfoModel.getWeather());
			mWeatherAfterFourDaysWindTextView.setText(weatherInfoModel.getWd());
			break;
		default:
			break;
		}
	}

	private void getLocalDataMetaInfos() {
		UIDataller ller = UIDataller.getDataller();
		ller.setHsActionTips(getActivity(), mHomeImageView, R.drawable.home_icon, mCurrentPathTextView, mMenuPath, mActionOkTipsTextView,
				ller.getOkActionTips(getActivity()), mActionBackTipsTextView, ller.getBackActionTips(getActivity()));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Constant.SETTING_CITYSETTING_SCREEN) {
			String cityName = data.getExtras().getString(Constant.IPTV_USER_HOTEL_CITY_NAME);
			String cityCode = data.getExtras().getString(Constant.IPTV_USER_HOTEL_CITY_NUM_CODE);
			updateWeatherInfos(cityName, cityCode);
		}
	}

}

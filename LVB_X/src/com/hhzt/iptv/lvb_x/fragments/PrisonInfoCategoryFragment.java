package com.hhzt.iptv.lvb_x.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.interfaces.IListOnSuccessCB;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.model.PrisonInfoCategoryModel;
import com.hhzt.iptv.lvb_x.model.PrsionInfoModel;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

public class PrisonInfoCategoryFragment extends BaseFragment {

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

	// 填充数据
	@ViewInject(R.id.prison_container_1)
	private RelativeLayout mPrisonContainer1;
	@ViewInject(R.id.prison_btn_1)
	private Button mPrisonBtn1;
	@ViewInject(R.id.prison_img_1)
	private ImageView mPrisonImg1;
	@ViewInject(R.id.prison_txt_1)
	private TextView mPrisonTxt1;

	@ViewInject(R.id.prison_container_2)
	private RelativeLayout mPrisonContainer2;
	@ViewInject(R.id.prison_btn_2)
	private Button mPrisonBtn2;
	@ViewInject(R.id.prison_img_2)
	private ImageView mPrisonImg2;
	@ViewInject(R.id.prison_txt_2)
	private TextView mPrisonTxt2;

	@ViewInject(R.id.prison_container_3)
	private RelativeLayout mPrisonContainer3;
	@ViewInject(R.id.prison_btn_3)
	private Button mPrisonBtn3;
	@ViewInject(R.id.prison_img_3)
	private ImageView mPrisonImg3;
	@ViewInject(R.id.prison_txt_3)
	private TextView mPrisonTxt3;

	@ViewInject(R.id.prison_container_4)
	private RelativeLayout mPrisonContainer4;
	@ViewInject(R.id.prison_btn_4)
	private Button mPrisonBtn4;
	@ViewInject(R.id.prison_img_4)
	private ImageView mPrisonImg4;
	@ViewInject(R.id.prison_txt_4)
	private TextView mPrisonTxt4;

	@ViewInject(R.id.prison_container_5)
	private RelativeLayout mPrisonContainer5;
	@ViewInject(R.id.prison_btn_5)
	private Button mPrisonBtn5;
	@ViewInject(R.id.prison_img_5)
	private ImageView mPrisonImg5;
	@ViewInject(R.id.prison_txt_5)
	private TextView mPrisonTxt5;

	@ViewInject(R.id.prison_container_6)
	private RelativeLayout mPrisonContainer6;
	@ViewInject(R.id.prison_btn_6)
	private Button mPrisonBtn6;
	@ViewInject(R.id.prison_img_6)
	private ImageView mPrisonImg6;
	@ViewInject(R.id.prison_txt_6)
	private TextView mPrisonTxt6;

	@ViewInject(R.id.prison_container_news_1)
	private RelativeLayout mPrisonContainerNews1;
	@ViewInject(R.id.prison_btn_news_1)
	private Button mPrisonBtnNews1;
	@ViewInject(R.id.prison_img_news_1)
	private ImageView mPrisonImgNews1;
	@ViewInject(R.id.prison_txt_news_1)
	private TextView mPrisonTxtNews1;

	@ViewInject(R.id.prison_container_news_2)
	private RelativeLayout mPrisonContainerNews2;
	@ViewInject(R.id.prison_btn_news_2)
	private Button mPrisonBtnNews2;
	@ViewInject(R.id.prison_img_news_2)
	private ImageView mPrisonImgNews2;
	@ViewInject(R.id.prison_txt_news_2)
	private TextView mPrisonTxtNews2;

	@ViewInject(R.id.prison_container_news)
	private LinearLayout mPrisonContainerNews;
	
	@ViewInject(R.id.mainmenu_logo_b)
	private ImageView mMainLoginImageView;

	private List<PrisonInfoCategoryModel> mInfosCategory;
	private List<PrsionInfoModel> mPrisonInfoModes;

	private String mUsername;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_prison_info_category,
				container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			setValue();
			registerReceiver();
			netWork();
		}
	}

	/**
	 * 请求网络数据
	 */
	private void netWork() {
		UIDataller.getDataller().getPrisonInfoCategory(getActivity(),
				new IListOnSuccessCB<PrisonInfoCategoryModel>() {

					@Override
					public void onSuccess(List<PrisonInfoCategoryModel> datas) {
						mInfosCategory = datas;
						switch (datas.size()) {
						case 1:
							mPrisonTxt1.setText(datas.get(0).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(0).getImg(),
									mPrisonImg1);
							mPrisonContainer2.setVisibility(View.INVISIBLE);
							mPrisonContainer3.setVisibility(View.INVISIBLE);
							mPrisonContainer4.setVisibility(View.INVISIBLE);
							mPrisonContainer5.setVisibility(View.INVISIBLE);
							mPrisonContainer6.setVisibility(View.INVISIBLE);
							break;
						case 2:
							mPrisonTxt1.setText(datas.get(0).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(0).getImg(),
									mPrisonImg1);
							mPrisonTxt2.setText(datas.get(1).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(1).getImg(),
									mPrisonImg2);
							mPrisonContainer3.setVisibility(View.INVISIBLE);
							mPrisonContainer4.setVisibility(View.INVISIBLE);
							mPrisonContainer5.setVisibility(View.INVISIBLE);
							mPrisonContainer6.setVisibility(View.INVISIBLE);
							break;
						case 3:
							mPrisonTxt1.setText(datas.get(0).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(0).getImg(),
									mPrisonImg1);
							mPrisonTxt2.setText(datas.get(1).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(1).getImg(),
									mPrisonImg2);
							mPrisonTxt3.setText(datas.get(2).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(2).getImg(),
									mPrisonImg3);
							mPrisonContainer4.setVisibility(View.INVISIBLE);
							mPrisonContainer5.setVisibility(View.INVISIBLE);
							mPrisonContainer6.setVisibility(View.INVISIBLE);
							break;
						case 4:
							mPrisonTxt1.setText(datas.get(0).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(0).getImg(),
									mPrisonImg1);
							mPrisonTxt2.setText(datas.get(1).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(1).getImg(),
									mPrisonImg2);
							mPrisonTxt3.setText(datas.get(2).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(2).getImg(),
									mPrisonImg3);
							mPrisonTxt4.setText(datas.get(3).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(3).getImg(),
									mPrisonImg4);
							mPrisonContainer5.setVisibility(View.INVISIBLE);
							mPrisonContainer6.setVisibility(View.INVISIBLE);
							break;
						case 5:
							mPrisonTxt1.setText(datas.get(0).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(0).getImg(),
									mPrisonImg1);
							mPrisonTxt2.setText(datas.get(1).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(1).getImg(),
									mPrisonImg2);
							mPrisonTxt3.setText(datas.get(2).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(2).getImg(),
									mPrisonImg3);
							mPrisonTxt4.setText(datas.get(3).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(3).getImg(),
									mPrisonImg4);
							mPrisonTxt5.setText(datas.get(4).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(4).getImg(),
									mPrisonImg5);
							mPrisonContainer6.setVisibility(View.INVISIBLE);
							break;
						case 6:
							mPrisonTxt1.setText(datas.get(0).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(0).getImg(),
									mPrisonImg1);
							mPrisonTxt2.setText(datas.get(1).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(1).getImg(),
									mPrisonImg2);
							mPrisonTxt3.setText(datas.get(2).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(2).getImg(),
									mPrisonImg3);
							mPrisonTxt4.setText(datas.get(3).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(3).getImg(),
									mPrisonImg4);
							mPrisonTxt5.setText(datas.get(4).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(4).getImg(),
									mPrisonImg5);
							mPrisonTxt6.setText(datas.get(5).getCategoryName());
							BitmapUtil.setFadeInImage(datas.get(5).getImg(),
									mPrisonImg6);
							break;

						default:
							break;
						}
					}
				}, 6);

		UIDataller.getDataller().getPrisonNewsFind(getActivity(), -1, 2, 1, 1,
				new IListOnSuccessCB<PrsionInfoModel>() {

					@Override
					public void onSuccess(List<PrsionInfoModel> datas) {
						mPrisonInfoModes = datas;
						mPrisonContainerNews.setVisibility(View.VISIBLE);
						if (null != datas && datas.size() > 0) {
							switch (datas.size()) {
							case 1:
								mPrisonTxtNews1
										.setText(datas.get(0).getTitle());
								BitmapUtil.setFadeInImage(
										datas.get(0).getImg(), mPrisonImgNews1);
								mPrisonContainerNews2
										.setVisibility(View.INVISIBLE);
								break;
							case 2:
								mPrisonTxtNews1
										.setText(datas.get(0).getTitle());
								BitmapUtil.setFadeInImage(
										datas.get(0).getImg(), mPrisonImgNews1);
								mPrisonTxtNews2
										.setText(datas.get(1).getTitle());
								BitmapUtil.setFadeInImage(
										datas.get(1).getImg(), mPrisonImgNews2);
								break;
							}
						} else {
							mPrisonContainerNews.setVisibility(View.INVISIBLE);
						}
					}
				});
	}

	/**
	 * 新闻详情
	 * 
	 * @param v
	 */
	@OnClick({ R.id.prison_btn_news_1, R.id.prison_btn_news_2 })
	public void onNewsOnClick(View v) {
		switch (v.getId()) {
		case R.id.prison_btn_news_1:
			ActivitySwitchMgr.gotoPrisonNewsDetailActivity(getActivity(),
					mUsername, mPrisonInfoModes.get(0).getId());
			break;
		case R.id.prison_btn_news_2:
			ActivitySwitchMgr.gotoPrisonNewsDetailActivity(getActivity(),
					mUsername, mPrisonInfoModes.get(1).getId());
			break;

		default:
			break;
		}
	}

	@OnClick({ R.id.prison_btn_1, R.id.prison_btn_2, R.id.prison_btn_3,
			R.id.prison_btn_4, R.id.prison_btn_5, R.id.prison_btn_6 })
	public void onClick(View v) {
		int currentP = -1;
		switch (v.getId()) {
		case R.id.prison_btn_1:
			currentP = 0;
			break;
		case R.id.prison_btn_2:
			currentP = 1;
			break;
		case R.id.prison_btn_3:
			currentP = 2;
			break;
		case R.id.prison_btn_4:
			currentP = 3;
			break;
		case R.id.prison_btn_5:
			currentP = 4;
			break;
		case R.id.prison_btn_6:
			currentP = 5;
			break;

		default:
			break;
		}
//		if (mInfosCategory != null) {
//			ActivitySwitchMgr.gotoPrisonNewsActivity(getActivity(),
//					mMainmenuTitleTextView.getText().toString(), currentP);
//		}
	}

	private void setValue() {
		mMainmenuImageView.setImageResource(R.drawable.prision_info_img);
		mWeatherTimeContainer.setVisibility(View.VISIBLE);
		mPrisonContrainer.setVisibility(View.GONE);
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			String username = intent
					.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			if (username != null) {
				mMainmenuTitleTextView.setText(username);
			}
			mUsername = username;
		}

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

	// 设置和更新主界面时间信息
	private void setTimeShow(boolean needAnimation) {
		UIDataller.getDataller().setMainMenuDateInfo(mMainmenuDateTextView,
				mMainmenuTimeTextView, mMainmenuWeekTextView,  needAnimation);
	}

	/**
	 * 注册时钟
	 */
	private void registerReceiver() {
		mDateAlarmReceiver = new DateAlarmReceiver();
		mDateAlarmFilter = new IntentFilter(
				Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE);
		BaseActivity.getInstance().getApplicationContext()
				.registerReceiver(mDateAlarmReceiver, mDateAlarmFilter);
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
		BaseActivity.getInstance().getApplicationContext()
				.unregisterReceiver(mDateAlarmReceiver);
	}
}

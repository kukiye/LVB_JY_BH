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
import com.hhzt.iptv.lvb_x.model.BookCategoryModel;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

public class BookFragment extends BaseFragment {

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
	
	@ViewInject(R.id.book_name_1)
	private TextView mBookName1;
	@ViewInject(R.id.book_name_2)
	private TextView mBookName2;
	@ViewInject(R.id.book_container1)
	private RelativeLayout mBookContainer1;
	@ViewInject(R.id.book_container2)
	private RelativeLayout mBookContainer2;
	
	@ViewInject(R.id.mainmenu_logo_b)
	private ImageView mMainLoginImageView;
	
	private List<BookCategoryModel> mBookCategorys;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_book, container, false);
		ViewUtils.inject(this, view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			setValue();
			registerReceiver();
			setNetWork();
		}
	}
	
	/**
	 * 设置网络请求数据
	 */
	private void setNetWork() {
		UIDataller.getDataller().getBookCategory(getActivity(), new IListOnSuccessCB<BookCategoryModel>() {
			
			@Override
			public void onSuccess(List<BookCategoryModel> datas) {
				mBookCategorys = datas;
				mBookContainer1.setVisibility(View.VISIBLE);
				mBookContainer2.setVisibility(View.VISIBLE);
				if(datas.size() > 0){
					switch (datas.size()) {
					case 1:
						mBookName1.setText(datas.get(0).getCategoryName());
						mBookContainer2.setVisibility(View.INVISIBLE);
						break;
					case 2:
						mBookName1.setText(datas.get(0).getCategoryName());
						mBookName2.setText(datas.get(1).getCategoryName());
						break;

					default:
						break;
					}
				}else{
					mBookContainer1.setVisibility(View.INVISIBLE);
					mBookContainer2.setVisibility(View.INVISIBLE);
				}
			}
		}, 2);
	}

	@OnClick({ R.id.prison_info_news_btn, R.id.prison_info_websit_btn })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.prison_info_news_btn:
			ActivitySwitchMgr.gotoBookListActivity(getActivity(), 
					mBookCategorys.get(0).getCategoryName(), mBookCategorys.get(0).getId(), 1);
			break;
		case R.id.prison_info_websit_btn:
			ActivitySwitchMgr.gotoBookListActivity(getActivity(), 
					mBookCategorys.get(1).getCategoryName(),mBookCategorys.get(1).getId(), 2);
			break;

		default:
			break;
		}
	}
	
	private void setValue() {
		mMainmenuImageView.setImageResource(R.drawable.prision_info_img);
		mWeatherTimeContainer.setVisibility(View.VISIBLE);
		mPrisonContrainer.setVisibility(View.INVISIBLE);
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

	// 设置和更新主界面时间信息
		private void setTimeShow(boolean needAnimation) {
			UIDataller.getDataller().setMainMenuDateInfo(mMainmenuDateTextView, mMainmenuTimeTextView,
					mMainmenuWeekTextView, needAnimation);
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

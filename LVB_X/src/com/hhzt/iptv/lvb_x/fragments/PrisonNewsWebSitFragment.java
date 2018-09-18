package com.hhzt.iptv.lvb_x.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.adapter.PrisonWebsitListAdapter;
import com.hhzt.iptv.lvb_x.adapter.WebSitContentListAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.interfaces.IBeanOnSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IListOnSuccessCB;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.model.PrisonWebsitCategoryModel;
import com.hhzt.iptv.lvb_x.model.PrisonWebsitPageModel;
import com.hhzt.iptv.lvb_x.model.PrsionWebsitModel;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PrisonNewsWebSitFragment extends BaseFragment implements
		OnItemSelectedListener, OnItemClickListener {

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

	private int mCurrentCategory = -1;
	@ViewInject(R.id.category_list)
	private ListView mCategoryList;
	private PrisonWebsitListAdapter mAdapter;
	private List<PrisonWebsitCategoryModel> mCategoryData;

	@ViewInject(R.id.prison_detail_framelayout)
	private LinearLayout mPrisonDetailContainer;

	@ViewInject(R.id.txt_page)
	private TextView mTxtPage;

	private int mPageNo = 1;
	private int mPageSize = 1000;
	private int mTotalPages = 1;
	
	private String mUsername = "";
	
	@ViewInject(R.id.mainmenu_logo_b)
	private ImageView mMainLoginImageView;
	
	@ViewInject(R.id.content_list)
	private ListView mContentList;
	private WebSitContentListAdapter mContentAdapter;
	private ArrayList<PrsionWebsitModel> mContentDatas;
	private int mCurrentFocus = 0;
	private int mContentSelect = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_prison_news_websit,
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
			netCategoryWork();
		}
	}

	/**
	 * 请求网络数据，填充数据
	 */
	private void netCategoryWork() {
		mCategoryList.setOnItemSelectedListener(this);
		mCategoryList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (mCurrentCategory != arg2) {
					mCurrentCategory = arg2;
					mPageNo = 1; // 还原PageNo
					// 请求服务器数据
					getNewsPage(mCategoryData.get(mCurrentCategory).getName());
					mCurrentFocus = arg2;
				}
			}
		});
		UIDataller.getDataller().getPrisonNewsWebsitCategory(getActivity(),
				new IListOnSuccessCB<PrisonWebsitCategoryModel>() {

					@Override
					public void onSuccess(List<PrisonWebsitCategoryModel> datas) {
						mCategoryData = datas;
						if (mAdapter == null) {
							mAdapter = new PrisonWebsitListAdapter();
							mAdapter.setmContext(getActivity());
						}
						mAdapter.setmList(datas);
						mCategoryList.setAdapter(mAdapter);
						mCategoryList.setOnKeyListener(new OnKeyListener() {

							@Override
							public boolean onKey(View v, int keyCode, KeyEvent event) {
								switch (keyCode) {
								case KeyEvent.KEYCODE_DPAD_RIGHT:
									if (event.getAction() == KeyEvent.ACTION_DOWN) {
										setSelectContent(0);
										return true;
									}
									break;
								default:
									break;
								}
								return false;
							}
						});
					}
				}, 10);
	}
	
	protected void setSelectCategory(int focus) {
		mContentSelect = 0;
		mCategoryList.setFocusable(true);
		mCategoryList.setFocusableInTouchMode(true);
		mCategoryList.requestLayout();
		mCategoryList.requestFocus();
		mCategoryList.setSelection(focus);
	}
	
	protected void setSelectContent(int focus){
		mContentList.setFocusable(true);
		mContentList.setFocusableInTouchMode(true);
		mContentList.requestLayout();
		mContentList.requestFocus();
		mContentList.setSelection(focus);
	}

	/**
	 * 获取新闻分类资讯信息
	 * 
	 * @param categoryId
	 * @param type
	 */
	private void getNewsPage(String categoryId) {
		UIDataller.getDataller().getPrisonWebsitPages(getActivity(),
				categoryId, mPageNo, mPageSize,
				new IBeanOnSuccessCB<PrisonWebsitPageModel>() {

					@Override
					public void onSuccess(PrisonWebsitPageModel bean) {
						LogUtil.d("getNewsPage----------bean=" + bean);
						mContentDatas = bean.getResult();
						mPageNo = bean.getPageNo();
						mPageSize = bean.getPageSize();
						mTotalPages = bean.getTotalPages();
						if (mContentAdapter == null) {
							mContentAdapter = new WebSitContentListAdapter();
							mContentAdapter.setmContext(getActivity());
						}
						if (mContentDatas != null && mContentDatas.size() > 0) {
							mContentAdapter.setmList(mContentDatas);
						}else{
							mContentAdapter.setmList(new ArrayList<PrsionWebsitModel>());
						}
						mContentList.setAdapter(mContentAdapter);
						mContentList.setOnItemClickListener(PrisonNewsWebSitFragment.this);
						mContentList.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								mContentSelect = arg2;
								//
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub
								
							}
						});
						mContentList.setOnKeyListener(new OnKeyListener() {

							@Override
							public boolean onKey(View v, int keyCode, KeyEvent event) {
								switch (keyCode) {
								case KeyEvent.KEYCODE_DPAD_LEFT:
									if (event.getAction() == KeyEvent.ACTION_DOWN) {
										setSelectCategory(mCurrentFocus);
										return true;
									}
									break;
								case KeyEvent.KEYCODE_DPAD_DOWN:
									if (event.getAction() == KeyEvent.ACTION_DOWN) {
										if(mContentSelect >= mContentDatas.size() - 1){
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
				});
	}

	public void setSelect(int position) {
		mCategoryList.setFocusable(true);
		mCategoryList.setFocusableInTouchMode(true);
		mCategoryList.requestLayout();
		mCategoryList.requestFocus();
		mCategoryList.setSelection(position);
	}

	private void setValue() {
		mMainmenuImageView.setImageResource(R.drawable.prison_new_img);
		mWeatherTimeContainer.setVisibility(View.VISIBLE);
		mPrisonContrainer.setVisibility(View.VISIBLE);
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			String username = intent
					.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			if (username != null) {
				mMainmenuTitleTextView.setText(username);
				mUsername = username;
			}
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		if (mCurrentCategory != arg2) {
			mCurrentCategory = arg2;
			mPageNo = 1; // 还原PageNo
			// 请求服务器数据
			getNewsPage(mCategoryData.get(mCurrentCategory).getName());
			mCurrentFocus = arg2;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		PrsionWebsitModel model = mContentDatas.get(arg2);
		// 进入详情页面
		ActivitySwitchMgr.gotoPrisonWebsitDetailActivity(getActivity(),
				mUsername, model.getUrl());
	}

}

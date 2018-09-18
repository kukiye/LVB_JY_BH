package com.hhzt.iptv.lvb_x.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.interfaces.IBeanOnSuccessCB;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.model.PrsionInfoModel;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.hhzt.iptv.lvb_x.utils.DateTimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PrisonNewsDetailFragment extends BaseFragment {

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

	private int mNewsId;

	private WebView mNewsWeb;
	@ViewInject(R.id.news_source)
	private TextView mNewsSourceTextView;
	@ViewInject(R.id.news_author)
	private TextView mNewsAuthorTextView;
	@ViewInject(R.id.news_createtime)
	private TextView mNewsCreateTimeTextView;
	@ViewInject(R.id.news_title)
	private TextView mNewsTitleTextView;
	
	@ViewInject(R.id.mainmenu_logo_b)
	private ImageView mMainLoginImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_prison_news_detail,
				container, false);
		mNewsWeb = (WebView) view.findViewById(R.id.news_web);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			setValue();
			registerReceiver();
			networkDetail();
			setWebView();
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	private void setWebView() {
		try {
			// 加载地址
			mNewsWeb.setWebViewClient(new WebViewClient() {

				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);

				}

				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
					view.loadUrl(url);
					return true;
				}
				
//				@Override
//				public void onReceivedSslError(WebView view,
//						SslErrorHandler handler, SslError error) {
//					// TODO Auto-generated method stub
//					super.onReceivedSslError(view, handler, error);
//					handler.proceed();  //接受所有证书
//				}
			});
			
			//加了这个就不能显示图片
//			if (Build.VERSION.SDK_INT >= 19) {
//				mNewsWeb.getSettings().setCacheMode(
//						WebSettings.LOAD_CACHE_ELSE_NETWORK);
//			}

			LogUtil.d("setWebView-------"
					+ UrlMgr.getPrsionNewsDetailContent(mNewsId));
			// UrlMgr.getPrsionNewsDetailContent(mNewsId)
			mNewsWeb.loadUrl(UrlMgr.getPrsionNewsDetailContent(mNewsId));
			mNewsWeb.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
			mNewsWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
			mNewsWeb.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
			mNewsWeb.getSettings().setSupportZoom(true);//是否可以缩放，默认true
			mNewsWeb.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
			mNewsWeb.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
			mNewsWeb.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
			mNewsWeb.getSettings().setAppCacheEnabled(true);//是否使用缓存
			mNewsWeb.getSettings().setDomStorageEnabled(true);//DOM Storage
			// displayWebview.getSettings().setUserAgentString("User-Agent:Android");//设置用户代理，一般不用
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

//	@Override
//	public void onReceivedSslError(WebView view, SslErrorHandler handler,
//			SslError error) {
//
//		handler.proceed(); // 接受所有证书
//	}

	private void networkDetail() {
		UIDataller.getDataller().getPrisonNewsDetail(getActivity(), mNewsId,
				new IBeanOnSuccessCB<PrsionInfoModel>() {

					@Override
					public void onSuccess(PrsionInfoModel bean) {
						if (bean != null) {
							mNewsTitleTextView.setText(bean.getTitle());
							if (bean.getAuthor() != null
									&& !TextUtils.isEmpty(bean.getAuthor())) {
								mNewsAuthorTextView.setText(String.format(
										getString(R.string.prison_news_author),
										bean.getAuthor()));
							} else {
								mNewsAuthorTextView.setText(String.format(
										getString(R.string.prison_news_author),
										"未知"));
							}
							if (bean.getSource() != null
									&& !TextUtils.isEmpty(bean.getSource())) {
								mNewsSourceTextView.setText(String.format(
										getString(R.string.prison_news_source),
										bean.getSource()));
							} else {
								mNewsSourceTextView.setText(String.format(
										getString(R.string.prison_news_source),
										"未知"));
							}
							if (bean.getUpdateTime() > 0) {
								mNewsCreateTimeTextView.setText(String
										.format(getString(R.string.prison_news_createtime),
												DateTimeUtil.toTime(
														bean.getUpdateTime(),
														DateTimeUtil.DATE_FORMATE_YEAR_MONTH_DAY)));
							} else {
								mNewsCreateTimeTextView.setText(String
										.format(getString(R.string.prison_news_createtime),
												"未知"));
							}
						}
					}
				});
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
			mNewsId = intent.getIntExtra("new_id", 0);
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
		mNewsWeb.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mNewsWeb.onPause();
	}

	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		BaseActivity.getInstance().getApplicationContext().unregisterReceiver(mDateAlarmReceiver);
	}

}

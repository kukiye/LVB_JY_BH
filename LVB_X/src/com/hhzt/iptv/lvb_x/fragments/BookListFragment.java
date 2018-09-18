package com.hhzt.iptv.lvb_x.fragments;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.interfaces.IBeanOnSuccessCB;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.model.BookModel;
import com.hhzt.iptv.lvb_x.model.BookPageModel;
import com.hhzt.iptv.lvb_x.net.BookDownloadManager;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;

public class BookListFragment extends BaseFragment {

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

	@ViewInject(R.id.book_container1)
	private LinearLayout mBookContainer1;
	@ViewInject(R.id.book_img1)
	private ImageView mBookImg1;
	@ViewInject(R.id.book_btn1)
	private Button mBookBtn1;

	@ViewInject(R.id.book_container2)
	private LinearLayout mBookContainer2;
	@ViewInject(R.id.book_img2)
	private ImageView mBookImg2;
	@ViewInject(R.id.book_btn2)
	private Button mBookBtn2;

	@ViewInject(R.id.book_container3)
	private LinearLayout mBookContainer3;
	@ViewInject(R.id.book_img3)
	private ImageView mBookImg3;
	@ViewInject(R.id.book_btn3)
	private Button mBookBtn3;

	@ViewInject(R.id.book_container4)
	private LinearLayout mBookContainer4;
	@ViewInject(R.id.book_img4)
	private ImageView mBookImg4;
	@ViewInject(R.id.book_btn4)
	private Button mBookBtn4;

	@ViewInject(R.id.book_container5)
	private LinearLayout mBookContainer5;
	@ViewInject(R.id.book_img5)
	private ImageView mBookImg5;
	@ViewInject(R.id.book_btn5)
	private Button mBookBtn5;

	@ViewInject(R.id.book_container6)
	private LinearLayout mBookContainer6;
	@ViewInject(R.id.book_img6)
	private ImageView mBookImg6;
	@ViewInject(R.id.book_btn6)
	private Button mBookBtn6;

	@ViewInject(R.id.book_container7)
	private LinearLayout mBookContainer7;
	@ViewInject(R.id.book_img7)
	private ImageView mBookImg7;
	@ViewInject(R.id.book_btn7)
	private Button mBookBtn7;

	@ViewInject(R.id.book_container8)
	private LinearLayout mBookContainer8;
	@ViewInject(R.id.book_img8)
	private ImageView mBookImg8;
	@ViewInject(R.id.book_btn8)
	private Button mBookBtn8;

	@ViewInject(R.id.book_container9)
	private LinearLayout mBookContainer9;
	@ViewInject(R.id.book_img9)
	private ImageView mBookImg9;
	@ViewInject(R.id.book_btn9)
	private Button mBookBtn9;

	@ViewInject(R.id.book_container10)
	private LinearLayout mBookContainer10;
	@ViewInject(R.id.book_img10)
	private ImageView mBookImg10;
	@ViewInject(R.id.book_btn10)
	private Button mBookBtn10;

	@ViewInject(R.id.book_container11)
	private LinearLayout mBookContainer11;
	@ViewInject(R.id.book_img11)
	private ImageView mBookImg11;
	@ViewInject(R.id.book_btn11)
	private Button mBookBtn11;

	@ViewInject(R.id.book_container12)
	private LinearLayout mBookContainer12;
	@ViewInject(R.id.book_img12)
	private ImageView mBookImg12;
	@ViewInject(R.id.book_btn12)
	private Button mBookBtn12;

	@ViewInject(R.id.book_container13)
	private LinearLayout mBookContainer13;
	@ViewInject(R.id.book_img13)
	private ImageView mBookImg13;
	@ViewInject(R.id.book_btn13)
	private Button mBookBtn13;

	@ViewInject(R.id.book_container14)
	private LinearLayout mBookContainer14;
	@ViewInject(R.id.book_img14)
	private ImageView mBookImg14;
	@ViewInject(R.id.book_btn14)
	private Button mBookBtn14;

	@ViewInject(R.id.book_container15)
	private LinearLayout mBookContainer15;
	@ViewInject(R.id.book_img15)
	private ImageView mBookImg15;
	@ViewInject(R.id.book_btn15)
	private Button mBookBtn15;

	@ViewInject(R.id.book_container16)
	private LinearLayout mBookContainer16;
	@ViewInject(R.id.book_img16)
	private ImageView mBookImg16;
	@ViewInject(R.id.book_btn16)
	private Button mBookBtn16;

	private int mCategoryId;
	private int mPageNo = 1;
	private int mPageSize = 16;
	private int mTotalPages = 0;
	private ArrayList<BookModel> mBookLists;

	@ViewInject(R.id.load_container)
	private RelativeLayout mLoadContainer;
	
	@ViewInject(R.id.mainmenu_logo_b)
	private ImageView mMainLoginImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_book_list, container,
				false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			setValue();
			registerReceiver();
			getBookListPage();
		}
	}

	/**
	 * 设置网络
	 */
	private void getBookListPage() {
		UIDataller.getDataller().getBookPage(getActivity(), mCategoryId,
				mPageNo, mPageSize, new IBeanOnSuccessCB<BookPageModel>() {

					@Override
					public void onSuccess(BookPageModel bean) {
						// TODO Auto-generated method stub
						mBookLists = bean.getResult();
						mPageNo = bean.getPageNo();
						mPageSize = bean.getPageSize();
						mTotalPages = bean.getTotalPages();
						refreshView();
					}
				});
	}

	@OnClick({ R.id.book_btn1, R.id.book_btn2, R.id.book_btn3, R.id.book_btn4,
			R.id.book_btn5, R.id.book_btn6, R.id.book_btn7, R.id.book_btn8,
			R.id.book_btn9, R.id.book_btn10, R.id.book_btn11, R.id.book_btn12,
			R.id.book_btn13, R.id.book_btn14, R.id.book_btn15, R.id.book_btn16 })
	public void onClick(View v) {
		int count = 0;
		switch (v.getId()) {
		case R.id.book_btn1:
			count = 0;
			break;
		case R.id.book_btn2:
			count = 1;
			break;
		case R.id.book_btn3:
			count = 2;
			break;
		case R.id.book_btn4:
			count = 3;
			break;
		case R.id.book_btn5:
			count = 4;
			break;
		case R.id.book_btn6:
			count = 5;
			break;
		case R.id.book_btn7:
			count = 6;
			break;
		case R.id.book_btn8:
			count = 7;
			break;
		case R.id.book_btn9:
			count = 8;
			break;
		case R.id.book_btn10:
			count = 9;
			break;
		case R.id.book_btn11:
			count = 10;
			break;
		case R.id.book_btn12:
			count = 11;
			break;
		case R.id.book_btn13:
			count = 12;
			break;
		case R.id.book_btn14:
			count = 13;
			break;
		case R.id.book_btn15:
			count = 14;
			break;
		case R.id.book_btn16:
			count = 15;
			break;
		default:
			break;
		}
		
		//点击之后请求服务器数据，然后打开资源文件
		getBookFile(mBookLists.get(count).getFilePath());
	}

	/**
	 * 根据资源文件，下载文件，保存在本地
	 * @param filePath
	 */
	private void getBookFile(String filePath) {
		LogUtil.i("filePath::"+filePath);
		BookDownloadManager manager = new BookDownloadManager();
		mLoadContainer.setVisibility(View.VISIBLE);
		manager.checkIsExistAndDownload(filePath, new IBeanOnSuccessCB<String>() {

			@Override
			public void onSuccess(final String path) {
				LogUtil.i("path::"+path);
				BaseActivity.getInstance().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mLoadContainer.setVisibility(View.GONE);
						ActivitySwitchMgr.gotoPdfViewActivity(getActivity(), path);
					}
				});
				
			}
		});
	}

	protected void refreshView() {
		if (mBookLists != null && mBookLists.size() > 0) {
			setContainerVisible(mBookLists.size());
			setOnListener();
			switch (mBookLists.size()) {
			case 1:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				break;
			case 2:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				break;
			case 3:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				break;
			case 4:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				break;
			case 5:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(4).getImg(), mBookImg5);
				mBookBtn5.setText(mBookLists.get(4).getBookname());
				break;
			case 6:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(4).getImg(), mBookImg5);
				mBookBtn5.setText(mBookLists.get(4).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(5).getImg(), mBookImg6);
				mBookBtn6.setText(mBookLists.get(5).getBookname());
				break;
			case 7:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(4).getImg(), mBookImg5);
				mBookBtn5.setText(mBookLists.get(4).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(5).getImg(), mBookImg6);
				mBookBtn6.setText(mBookLists.get(5).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(6).getImg(), mBookImg7);
				mBookBtn7.setText(mBookLists.get(6).getBookname());

				break;
			case 8:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(4).getImg(), mBookImg5);
				mBookBtn5.setText(mBookLists.get(4).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(5).getImg(), mBookImg6);
				mBookBtn6.setText(mBookLists.get(5).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(6).getImg(), mBookImg7);
				mBookBtn7.setText(mBookLists.get(6).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(7).getImg(), mBookImg8);
				mBookBtn8.setText(mBookLists.get(7).getBookname());

				break;
			case 9:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(4).getImg(), mBookImg5);
				mBookBtn5.setText(mBookLists.get(4).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(5).getImg(), mBookImg6);
				mBookBtn6.setText(mBookLists.get(5).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(6).getImg(), mBookImg7);
				mBookBtn7.setText(mBookLists.get(6).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(7).getImg(), mBookImg8);
				mBookBtn8.setText(mBookLists.get(7).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(8).getImg(), mBookImg9);
				mBookBtn9.setText(mBookLists.get(8).getBookname());
				break;
			case 10:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(4).getImg(), mBookImg5);
				mBookBtn5.setText(mBookLists.get(4).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(5).getImg(), mBookImg6);
				mBookBtn6.setText(mBookLists.get(5).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(6).getImg(), mBookImg7);
				mBookBtn7.setText(mBookLists.get(6).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(7).getImg(), mBookImg8);
				mBookBtn8.setText(mBookLists.get(7).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(8).getImg(), mBookImg9);
				mBookBtn9.setText(mBookLists.get(8).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(9).getImg(),
						mBookImg10);
				mBookBtn10.setText(mBookLists.get(9).getBookname());
				break;
			case 11:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(4).getImg(), mBookImg5);
				mBookBtn5.setText(mBookLists.get(4).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(5).getImg(), mBookImg6);
				mBookBtn6.setText(mBookLists.get(5).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(6).getImg(), mBookImg7);
				mBookBtn7.setText(mBookLists.get(6).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(7).getImg(), mBookImg8);
				mBookBtn8.setText(mBookLists.get(7).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(8).getImg(), mBookImg9);
				mBookBtn9.setText(mBookLists.get(8).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(9).getImg(),
						mBookImg10);
				mBookBtn10.setText(mBookLists.get(9).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(10).getImg(),
						mBookImg11);
				mBookBtn11.setText(mBookLists.get(10).getBookname());
				break;
			case 12:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(4).getImg(), mBookImg5);
				mBookBtn5.setText(mBookLists.get(4).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(5).getImg(), mBookImg6);
				mBookBtn6.setText(mBookLists.get(5).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(6).getImg(), mBookImg7);
				mBookBtn7.setText(mBookLists.get(6).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(7).getImg(), mBookImg8);
				mBookBtn8.setText(mBookLists.get(7).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(8).getImg(), mBookImg9);
				mBookBtn9.setText(mBookLists.get(8).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(9).getImg(),
						mBookImg10);
				mBookBtn10.setText(mBookLists.get(9).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(10).getImg(),
						mBookImg11);
				mBookBtn11.setText(mBookLists.get(10).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(11).getImg(),
						mBookImg12);
				mBookBtn12.setText(mBookLists.get(11).getBookname());
				break;
			case 13:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(4).getImg(), mBookImg5);
				mBookBtn5.setText(mBookLists.get(4).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(5).getImg(), mBookImg6);
				mBookBtn6.setText(mBookLists.get(5).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(6).getImg(), mBookImg7);
				mBookBtn7.setText(mBookLists.get(6).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(7).getImg(), mBookImg8);
				mBookBtn8.setText(mBookLists.get(7).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(8).getImg(), mBookImg9);
				mBookBtn9.setText(mBookLists.get(8).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(9).getImg(),
						mBookImg10);
				mBookBtn10.setText(mBookLists.get(9).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(10).getImg(),
						mBookImg11);
				mBookBtn11.setText(mBookLists.get(10).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(11).getImg(),
						mBookImg12);
				mBookBtn12.setText(mBookLists.get(11).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(12).getImg(),
						mBookImg13);
				mBookBtn13.setText(mBookLists.get(12).getBookname());
				break;
			case 14:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(4).getImg(), mBookImg5);
				mBookBtn5.setText(mBookLists.get(4).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(5).getImg(), mBookImg6);
				mBookBtn6.setText(mBookLists.get(5).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(6).getImg(), mBookImg7);
				mBookBtn7.setText(mBookLists.get(6).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(7).getImg(), mBookImg8);
				mBookBtn8.setText(mBookLists.get(7).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(8).getImg(), mBookImg9);
				mBookBtn9.setText(mBookLists.get(8).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(9).getImg(),
						mBookImg10);
				mBookBtn10.setText(mBookLists.get(9).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(10).getImg(),
						mBookImg11);
				mBookBtn11.setText(mBookLists.get(10).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(11).getImg(),
						mBookImg12);
				mBookBtn12.setText(mBookLists.get(11).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(12).getImg(),
						mBookImg13);
				mBookBtn13.setText(mBookLists.get(12).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(13).getImg(),
						mBookImg14);
				mBookBtn14.setText(mBookLists.get(13).getBookname());
				break;
			case 15:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(4).getImg(), mBookImg5);
				mBookBtn5.setText(mBookLists.get(4).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(5).getImg(), mBookImg6);
				mBookBtn6.setText(mBookLists.get(5).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(6).getImg(), mBookImg7);
				mBookBtn7.setText(mBookLists.get(6).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(7).getImg(), mBookImg8);
				mBookBtn8.setText(mBookLists.get(7).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(8).getImg(), mBookImg9);
				mBookBtn9.setText(mBookLists.get(8).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(9).getImg(),
						mBookImg10);
				mBookBtn10.setText(mBookLists.get(9).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(10).getImg(),
						mBookImg11);
				mBookBtn11.setText(mBookLists.get(10).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(11).getImg(),
						mBookImg12);
				mBookBtn12.setText(mBookLists.get(11).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(12).getImg(),
						mBookImg13);
				mBookBtn13.setText(mBookLists.get(12).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(13).getImg(),
						mBookImg14);
				mBookBtn14.setText(mBookLists.get(13).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(14).getImg(),
						mBookImg15);
				mBookBtn15.setText(mBookLists.get(14).getBookname());
				break;
			case 16:
				BitmapUtil
						.setFadeInImage(mBookLists.get(0).getImg(), mBookImg1);
				mBookBtn1.setText(mBookLists.get(0).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(1).getImg(), mBookImg2);
				mBookBtn2.setText(mBookLists.get(1).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(2).getImg(), mBookImg3);
				mBookBtn3.setText(mBookLists.get(2).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(3).getImg(), mBookImg4);
				mBookBtn4.setText(mBookLists.get(3).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(4).getImg(), mBookImg5);
				mBookBtn5.setText(mBookLists.get(4).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(5).getImg(), mBookImg6);
				mBookBtn6.setText(mBookLists.get(5).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(6).getImg(), mBookImg7);
				mBookBtn7.setText(mBookLists.get(6).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(7).getImg(), mBookImg8);
				mBookBtn8.setText(mBookLists.get(7).getBookname());
				BitmapUtil
						.setFadeInImage(mBookLists.get(8).getImg(), mBookImg9);
				mBookBtn9.setText(mBookLists.get(8).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(9).getImg(),
						mBookImg10);
				mBookBtn10.setText(mBookLists.get(9).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(10).getImg(),
						mBookImg11);
				mBookBtn11.setText(mBookLists.get(10).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(11).getImg(),
						mBookImg12);
				mBookBtn12.setText(mBookLists.get(11).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(12).getImg(),
						mBookImg13);
				mBookBtn13.setText(mBookLists.get(12).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(13).getImg(),
						mBookImg14);
				mBookBtn14.setText(mBookLists.get(13).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(14).getImg(),
						mBookImg15);
				mBookBtn15.setText(mBookLists.get(14).getBookname());
				BitmapUtil.setFadeInImage(mBookLists.get(15).getImg(),
						mBookImg16);
				mBookBtn16.setText(mBookLists.get(15).getBookname());
				break;

			default:
				break;
			}
		} else {
			setContainerVisible(0);
		}
	}

	private void setOnListener() {
		mBookBtn1.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo > 1) {
							mPageNo = mPageNo - 1;
							getBookListPage();
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn2.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo > 1) {
							mPageNo = mPageNo - 1;
							getBookListPage();
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn3.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo > 1) {
							mPageNo = mPageNo - 1;
							getBookListPage();
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn4.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo > 1) {
							mPageNo = mPageNo - 1;
							getBookListPage();
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn5.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo > 1) {
							mPageNo = mPageNo - 1;
							getBookListPage();
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn6.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo > 1) {
							mPageNo = mPageNo - 1;
							getBookListPage();
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn7.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo > 1) {
							mPageNo = mPageNo - 1;
							getBookListPage();
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn8.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo > 1) {
							mPageNo = mPageNo - 1;
							getBookListPage();
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});

		mBookBtn9.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_DOWN:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo < mTotalPages) {
							mPageNo = mPageNo + 1;
							getBookListPage();
						} else {
							BaseActivity.getInstance().showToast(
									getString(R.string.prison_news_no_content),
									Toast.LENGTH_SHORT);
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn10.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_DOWN:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo < mTotalPages) {
							mPageNo = mPageNo + 1;
							getBookListPage();
						} else {
							BaseActivity.getInstance().showToast(
									getString(R.string.prison_news_no_content),
									Toast.LENGTH_SHORT);
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn11.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_DOWN:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo < mTotalPages) {
							mPageNo = mPageNo + 1;
							getBookListPage();
						} else {
							BaseActivity.getInstance().showToast(
									getString(R.string.prison_news_no_content),
									Toast.LENGTH_SHORT);
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn12.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_DOWN:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo < mTotalPages) {
							mPageNo = mPageNo + 1;
							getBookListPage();
						} else {
							BaseActivity.getInstance().showToast(
									getString(R.string.prison_news_no_content),
									Toast.LENGTH_SHORT);
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn13.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_DOWN:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo < mTotalPages) {
							mPageNo = mPageNo + 1;
							getBookListPage();
						} else {
							BaseActivity.getInstance().showToast(
									getString(R.string.prison_news_no_content),
									Toast.LENGTH_SHORT);
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn14.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_DOWN:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo < mTotalPages) {
							mPageNo = mPageNo + 1;
							getBookListPage();
						} else {
							BaseActivity.getInstance().showToast(
									getString(R.string.prison_news_no_content),
									Toast.LENGTH_SHORT);
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn15.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_DOWN:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo < mTotalPages) {
							mPageNo = mPageNo + 1;
							getBookListPage();
						} else {
							BaseActivity.getInstance().showToast(
									getString(R.string.prison_news_no_content),
									Toast.LENGTH_SHORT);
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBookBtn16.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_DOWN:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mPageNo < mTotalPages) {
							mPageNo = mPageNo + 1;
							getBookListPage();
						} else {
							BaseActivity.getInstance().showToast(
									getString(R.string.prison_news_no_content),
									Toast.LENGTH_SHORT);
						}
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

	private void setContainerVisible(int size) {
		mBookContainer1.setVisibility(View.VISIBLE);
		mBookContainer2.setVisibility(View.VISIBLE);
		mBookContainer3.setVisibility(View.VISIBLE);
		mBookContainer4.setVisibility(View.VISIBLE);
		mBookContainer5.setVisibility(View.VISIBLE);
		mBookContainer6.setVisibility(View.VISIBLE);
		mBookContainer7.setVisibility(View.VISIBLE);
		mBookContainer8.setVisibility(View.VISIBLE);
		mBookContainer9.setVisibility(View.VISIBLE);
		mBookContainer10.setVisibility(View.VISIBLE);
		mBookContainer11.setVisibility(View.VISIBLE);
		mBookContainer12.setVisibility(View.VISIBLE);
		mBookContainer13.setVisibility(View.VISIBLE);
		mBookContainer14.setVisibility(View.VISIBLE);
		mBookContainer15.setVisibility(View.VISIBLE);
		mBookContainer16.setVisibility(View.VISIBLE);
		switch (size) {
		case 1:
			mBookContainer2.setVisibility(View.INVISIBLE);
			mBookContainer3.setVisibility(View.INVISIBLE);
			mBookContainer4.setVisibility(View.INVISIBLE);
			mBookContainer5.setVisibility(View.INVISIBLE);
			mBookContainer6.setVisibility(View.INVISIBLE);
			mBookContainer7.setVisibility(View.INVISIBLE);
			mBookContainer8.setVisibility(View.INVISIBLE);
			mBookContainer9.setVisibility(View.INVISIBLE);
			mBookContainer10.setVisibility(View.INVISIBLE);
			mBookContainer11.setVisibility(View.INVISIBLE);
			mBookContainer12.setVisibility(View.INVISIBLE);
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 2:
			mBookContainer3.setVisibility(View.INVISIBLE);
			mBookContainer4.setVisibility(View.INVISIBLE);
			mBookContainer5.setVisibility(View.INVISIBLE);
			mBookContainer6.setVisibility(View.INVISIBLE);
			mBookContainer7.setVisibility(View.INVISIBLE);
			mBookContainer8.setVisibility(View.INVISIBLE);
			mBookContainer9.setVisibility(View.INVISIBLE);
			mBookContainer10.setVisibility(View.INVISIBLE);
			mBookContainer11.setVisibility(View.INVISIBLE);
			mBookContainer12.setVisibility(View.INVISIBLE);
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 3:
			mBookContainer4.setVisibility(View.INVISIBLE);
			mBookContainer5.setVisibility(View.INVISIBLE);
			mBookContainer6.setVisibility(View.INVISIBLE);
			mBookContainer7.setVisibility(View.INVISIBLE);
			mBookContainer8.setVisibility(View.INVISIBLE);
			mBookContainer9.setVisibility(View.INVISIBLE);
			mBookContainer10.setVisibility(View.INVISIBLE);
			mBookContainer11.setVisibility(View.INVISIBLE);
			mBookContainer12.setVisibility(View.INVISIBLE);
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 4:
			mBookContainer5.setVisibility(View.INVISIBLE);
			mBookContainer6.setVisibility(View.INVISIBLE);
			mBookContainer7.setVisibility(View.INVISIBLE);
			mBookContainer8.setVisibility(View.INVISIBLE);
			mBookContainer9.setVisibility(View.INVISIBLE);
			mBookContainer10.setVisibility(View.INVISIBLE);
			mBookContainer11.setVisibility(View.INVISIBLE);
			mBookContainer12.setVisibility(View.INVISIBLE);
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 5:
			mBookContainer6.setVisibility(View.INVISIBLE);
			mBookContainer7.setVisibility(View.INVISIBLE);
			mBookContainer8.setVisibility(View.INVISIBLE);
			mBookContainer9.setVisibility(View.INVISIBLE);
			mBookContainer10.setVisibility(View.INVISIBLE);
			mBookContainer11.setVisibility(View.INVISIBLE);
			mBookContainer12.setVisibility(View.INVISIBLE);
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 6:
			mBookContainer7.setVisibility(View.INVISIBLE);
			mBookContainer8.setVisibility(View.INVISIBLE);
			mBookContainer9.setVisibility(View.INVISIBLE);
			mBookContainer10.setVisibility(View.INVISIBLE);
			mBookContainer11.setVisibility(View.INVISIBLE);
			mBookContainer12.setVisibility(View.INVISIBLE);
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 7:
			mBookContainer8.setVisibility(View.INVISIBLE);
			mBookContainer9.setVisibility(View.INVISIBLE);
			mBookContainer10.setVisibility(View.INVISIBLE);
			mBookContainer11.setVisibility(View.INVISIBLE);
			mBookContainer12.setVisibility(View.INVISIBLE);
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 8:
			mBookContainer9.setVisibility(View.INVISIBLE);
			mBookContainer10.setVisibility(View.INVISIBLE);
			mBookContainer11.setVisibility(View.INVISIBLE);
			mBookContainer12.setVisibility(View.INVISIBLE);
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 9:
			mBookContainer10.setVisibility(View.INVISIBLE);
			mBookContainer11.setVisibility(View.INVISIBLE);
			mBookContainer12.setVisibility(View.INVISIBLE);
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 10:
			mBookContainer11.setVisibility(View.INVISIBLE);
			mBookContainer12.setVisibility(View.INVISIBLE);
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 11:
			mBookContainer12.setVisibility(View.INVISIBLE);
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 12:
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 13:
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 14:
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 15:
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		case 16:

			break;

		default:
			mBookContainer1.setVisibility(View.INVISIBLE);
			mBookContainer2.setVisibility(View.INVISIBLE);
			mBookContainer3.setVisibility(View.INVISIBLE);
			mBookContainer4.setVisibility(View.INVISIBLE);
			mBookContainer5.setVisibility(View.INVISIBLE);
			mBookContainer6.setVisibility(View.INVISIBLE);
			mBookContainer7.setVisibility(View.INVISIBLE);
			mBookContainer8.setVisibility(View.INVISIBLE);
			mBookContainer9.setVisibility(View.INVISIBLE);
			mBookContainer10.setVisibility(View.INVISIBLE);
			mBookContainer11.setVisibility(View.INVISIBLE);
			mBookContainer12.setVisibility(View.INVISIBLE);
			mBookContainer13.setVisibility(View.INVISIBLE);
			mBookContainer14.setVisibility(View.INVISIBLE);
			mBookContainer15.setVisibility(View.INVISIBLE);
			mBookContainer16.setVisibility(View.INVISIBLE);
			break;
		}
	}

	private void setValue() {
		mWeatherTimeContainer.setVisibility(View.VISIBLE);
		mPrisonContrainer.setVisibility(View.INVISIBLE);
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			String username = intent
					.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			if (username != null) {
				mMainmenuTitleTextView.setText(username);
			}
			int type = intent.getIntExtra("book_type", 0);
			switch (type) {
			case 1:
				mMainmenuImageView.setImageResource(R.drawable.book_img_type1);
				break;
			case 2:
				mMainmenuImageView.setImageResource(R.drawable.book_img_type2);
				break;

			default:
				break;
			}
			mCategoryId = intent.getIntExtra("category_id", 0);
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
				mMainmenuTimeTextView, mMainmenuWeekTextView, needAnimation);
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
		if (mDateAlarmReceiver != null) {
			BaseActivity.getInstance().getApplicationContext()
					.unregisterReceiver(mDateAlarmReceiver);
		}
	}
}

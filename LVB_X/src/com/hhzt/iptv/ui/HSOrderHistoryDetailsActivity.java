/**
 * 作者：   Johnson
 * 日期：   2014年7月4日下午6:11:15
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：HSOrderHistoryDetailsActivity.java
 */
package com.hhzt.iptv.ui;

import android.content.Intent;
import android.os.Bundle;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.fragments.HSOrderHistoryDetailFragmen;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class HSOrderHistoryDetailsActivity extends BaseActivity {

	HSOrderHistoryDetailFragmen mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		int serviceType = intent.getIntExtra(Constant.IPTV_LVB_X_SCREEN_TAG, Constant.HOTEL_ORDER_HISTORY_DETAILS_SCREEN);
		switch (serviceType) {
		case Constant.HOTEL_ORDER_HISTORY_MAIN_SCREEN:
			serviceType = Constant.HOTEL_ORDER_HISTORY_DETAILS_SCREEN;
			break;
		case Constant.HOTEL_TRAVEL_SPECAIL_HISTORY_MAIN_SCREEN:
			serviceType = Constant.HOTEL_TRAVEL_SPECAIL_HISTORY_DETAILS_SCREEN;
			break;
		default:
			break;
		}
		mFragment = new HSOrderHistoryDetailFragmen();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, serviceType);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

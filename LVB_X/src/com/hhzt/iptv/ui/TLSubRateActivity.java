/**
 * 作者：   Johnson
 * 日期：   2014年6月26日下午4:25:54
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：TLSubRateActivity.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.fragments.RateFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class TLSubRateActivity extends BaseActivity {

	private RateFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragment = new RateFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, Constant.HOTEL_TRAVEL_RATE_SCREEN);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

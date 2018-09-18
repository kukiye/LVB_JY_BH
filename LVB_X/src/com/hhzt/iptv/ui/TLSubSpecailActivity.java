/**
 * 作者：   Johnson
 * 日期：   2014年6月26日下午4:26:20
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：TLSubSpecailActivity.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.fragments.HSOrderFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class TLSubSpecailActivity extends BaseActivity {

	HSOrderFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragment = new HSOrderFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, Constant.HOTEL_TRAVEL_SPECAIL_SCREEN);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return mFragment.onKeyUp(keyCode, event) ? true : super.onKeyUp(keyCode, event);
	}
}

/**
 * 作者：   Johnson
 * 日期：   2014年6月18日下午4:01:29
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：TravelActivity.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.fragments.LVBX1_a4_Fragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class TravelActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LVBX1_a4_Fragment fragment = new LVBX1_a4_Fragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, fragment, Constant.HOTEL_TRAVEL_MAIN_SCREEN);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

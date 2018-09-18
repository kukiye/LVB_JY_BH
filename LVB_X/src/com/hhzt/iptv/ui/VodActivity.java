/**
 * 作者：   Johnson
 * 日期：   2014年6月18日下午4:00:11
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：VodActivity.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.fragments.VodMainFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class VodActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		VodMainFragment fragment = new VodMainFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, fragment, Constant.HOTEL_VOD_SCREEN);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

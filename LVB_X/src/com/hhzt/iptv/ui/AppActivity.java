/**
 * 作者：   Johnson
 * 日期：   2014年6月18日下午4:02:08
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：AppActivity.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.fragments.LVBX1_a1_Fragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class AppActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LVBX1_a1_Fragment appTypeFragment = new LVBX1_a1_Fragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, appTypeFragment, Constant.APP_BUSINESS_MAIN_SCREEN);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

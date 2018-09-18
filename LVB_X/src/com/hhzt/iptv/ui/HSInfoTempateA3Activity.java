/**
 * 作者：   Johnson
 * 日期：   2014年6月25日下午6:17:02
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：HSSubCheckOutInActivity.java
 */
package com.hhzt.iptv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.config.CCTemplateConfig;
import com.hhzt.iptv.lvb_x.fragments.LVBX1_a3_Fragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class HSInfoTempateA3Activity extends BaseActivity {

	LVBX1_a3_Fragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int screenTag = 0;
		Intent intent = getIntent();
		String templateCode = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_TEMPLATE_TAG);
		if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_HOTEL_ROOMSERVICE_CHECKOUTIN.equalsIgnoreCase(templateCode)) {
			screenTag = Constant.HOTEL_CHECK_OUTIN_SCREEN;
		} else if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_HOTEL_ROOMSERVICE_CLEANUP.equalsIgnoreCase(templateCode)) {
			screenTag = Constant.HOTEL_CLEAN_SCREEN;
		} else if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_HOTEL_ROOMSERVICE_WAKEUP.equalsIgnoreCase(templateCode)) {
			screenTag = Constant.HOTEL_WAKEUP_SCREEN;
		}
		mFragment = new LVBX1_a3_Fragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, screenTag);
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

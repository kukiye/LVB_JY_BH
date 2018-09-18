/**
 * 作者：   Johnson
 * 日期：   2014年6月24日下午5:29:08
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：LangSetActivity.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.fragments.LangSetFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class LangSetActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LangSetFragment fragment = new LangSetFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, fragment, Constant.SETTING_LANGUAGE_SCREEN);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

/**
 * 作者：   Johnson
 * 日期：   2014年6月23日下午3:05:47
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：SplashActivity.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.fragments.EnterPrisonTeachFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class BActivity extends BaseActivity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);


		EnterPrisonTeachFragment mFragment= new EnterPrisonTeachFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, 0);

	}




}

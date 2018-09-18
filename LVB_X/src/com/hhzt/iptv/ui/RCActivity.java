/**
 * 作者：   Johnson
 * 日期：   2014年8月13日下午4:12:44
 * 包名：    com.hhzt.iptv.ui
 * 工程名：LVB_X
 * 文件名：RemoteController.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.fragments.RCFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class RCActivity extends BaseActivity {

	private RCFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFragment = new RCFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, 0);
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

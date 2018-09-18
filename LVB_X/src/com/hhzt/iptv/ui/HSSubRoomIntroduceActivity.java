/**
 * 作者：   Johnson
 * 日期：   2014年6月25日下午6:15:39
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：HSSubRoomIntroduceActivity.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.fragments.LVBX1_a2_Fragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class HSSubRoomIntroduceActivity extends BaseActivity {

	private LVBX1_a2_Fragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragment = new LVBX1_a2_Fragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, Constant.HOTEL_ROOM_INTRODUCE_SCREEN);
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

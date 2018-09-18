/**
 * 作者：   Johnson
 * 日期：   2014年6月25日下午6:14:43
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：HSSubIntroduceActivity.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.fragments.LVBX1_a2_Fragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class HSInfoTempateA2Activity extends BaseActivity {

	LVBX1_a2_Fragment mFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragment = new LVBX1_a2_Fragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, Constant.HOTEL_HS_INTRODUCE_SCREEN);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return mFragment.onKeyUp(keyCode, event) ? true : super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mFragment.onTouchEvent(event) ? true : super.onTouchEvent(event);
	}

}

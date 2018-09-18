/**
 * 作者：   Johnson
 * 日期：   2014年6月23日下午3:05:47
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：SplashActivity.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.fragments.SplashFragment;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.hhzt.iptv.lvb_x.utils.VirturlKeyPadCtr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class SplashActivity extends BaseActivity {

	public boolean mIsInSplashTag;
	private SplashFragment mFragment;
	private float mTouchPoint_Begin_X;
	private float mTouchPoint_End_X;
	private float mTouchPoint_Begin_Y;
	private float mTouchPoint_End_Y;
	private static SplashActivity mSplashActivity;

	public static SplashActivity getIntance() {
		return mSplashActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSplashActivity = this;
		mFragment = new SplashFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, Constant.HOTEL_SPLASH_SCREEN);
		
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		mIsInSplashTag = true;
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mTouchPoint_Begin_X = event.getX();
			mTouchPoint_Begin_Y = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			mTouchPoint_End_X = event.getX();
			mTouchPoint_End_Y = event.getY();
			float differ_X = mTouchPoint_End_X - mTouchPoint_Begin_X;
			float differ_Y = mTouchPoint_End_Y - mTouchPoint_Begin_Y;

			if (Math.abs(differ_Y) > LVBXApp.getmScreenHeight() * 3 / 4) {
				ActivitySwitchMgr.gotoSettingActivity(this);
			} else if (Math.abs(differ_X) > LVBXApp.getmScreenWidth() * 3 / 4) {
				ActivitySwitchMgr.gotoProjectTestListActivity(this);
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mIsInSplashTag = false;
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

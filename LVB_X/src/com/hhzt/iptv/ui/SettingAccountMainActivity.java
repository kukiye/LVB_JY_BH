package com.hhzt.iptv.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.fragments.AccountFragment;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class SettingAccountMainActivity extends BaseActivity {

	private float mTouchPoint_Begin_Y;
	private float mTouchPoint_End_Y;
	private AccountFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFragment = new AccountFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, Constant.SETTING_ACCOUNT_SCREEN);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return mFragment.onKeyUp(keyCode, event) ? true : super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mTouchPoint_Begin_Y = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			mTouchPoint_End_Y = event.getY();
			float differ_Y = mTouchPoint_End_Y - mTouchPoint_Begin_Y;
			if (Math.abs(differ_Y) > LVBXApp.getmScreenHeight() / 2) {
				ActivitySwitchMgr.gotoNetSettinActivity(this);
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

}

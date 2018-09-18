package com.hhzt.iptv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.fragments.WelcomeFragment;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.AuthorithMgr;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.hhzt.iptv.lvb_x.mgr.WindowController;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class WelcomeActivity extends BaseActivity {

	private float mTouchPoint_Begin_X;
	private float mTouchPoint_End_X;
	private float mTouchPoint_Begin_Y;
	private float mTouchPoint_End_Y;
	private WelcomeFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragment = new WelcomeFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, Constant.HOTEL_WELCOME_SCREEN);
		//下载开机动画
		UIDataller.getDataller().openBootanimaton();

		// 开启互动接收线程
//		UIDataller.getDataller().openCtrollerService();
		// 开启设备认证检测,检测设备授权合法性
		AuthorithMgr.getInstance().checkLicenseIsValide();
		// 开启浮动控制窗口
		WindowController.openFloatControllerWindow();
//		// 启用电源锁
//		PowerMgr.getPowerMgrInstance().acquireWakeLock();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

//		// 关闭"返回"浮动控制窗口
//		WindowController.closeFloatControllerWindow();
//		// 关闭消息推送接收的服务
//		stopMsgReceiveService();
//		// 关闭Socket UDP接收线程
//		UIDataller.getDataller().closeControllerService();
//		// 释放电源锁
//		PowerMgr.getPowerMgrInstance().releaseWakeLock();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return mFragment.onKeyUp(keyCode, event) ? true : super.onKeyUp(keyCode, event);
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

}

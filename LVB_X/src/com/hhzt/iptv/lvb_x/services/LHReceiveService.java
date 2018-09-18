/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 *
 * @author Johnson
 * 2014年3月19日 上午10:12:56
 */
package com.hhzt.iptv.lvb_x.services;

import com.google.gson.Gson;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.floatwindows.FloatWindowManager;
import com.hhzt.iptv.lvb_x.fragments.MainmenuFragment.MainMenuHandler;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.MediaControllerMgr;
import com.hhzt.iptv.lvb_x.mgr.PowerMgr;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.model.DeviceInfoBean;
import com.hhzt.iptv.lvb_x.model.ForcePlayBean;
import com.hhzt.iptv.lvb_x.model.PushMessage;
import com.hhzt.iptv.lvb_x.net.MsgSocket;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.hhzt.iptv.lvb_x.utils.VirturlKeyPadCtr;
import com.hhzt.iptv.ui.MainmenuActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;

public class LHReceiveService extends Service {
	
//	 private DateAlarmCommunicationReceiver mDateAlarmCommunicationReceiver;
//	 private IntentFilter mDateAlarmCommunicationsFilter;
	 private MainServiceHandler mMainServiceHandler;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
//		 registerReceiver();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mMainServiceHandler.removeMessages(111);
		mMainServiceHandler.removeMessages(222);
//		cancelDateUpdateAlarm();
//		getApplicationContext().unregisterReceiver(mDateAlarmCommunicationReceiver);
	}
	
   @Override
   public void onStart(Intent intent, int startId) {
	// TODO Auto-generated method stub
	   super.onStart(intent, startId);
	  
	  
   }

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		boolean flag=true;
		if(mMainServiceHandler==null){
			mMainServiceHandler=new MainServiceHandler();
		}
		if(flag){
			flag=false;
			Intent IntentSignal = new Intent();
			IntentSignal.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			IntentSignal.setComponent(new ComponentName("com.tsb.tv", "com.tsb.tv.Tv_strategy"));
			IntentSignal.setAction("android.intent.action.VIEW");
			getApplicationContext().startActivity(IntentSignal);
		}
			mMainServiceHandler.sendEmptyMessageDelayed(111, 5000);	
			mMainServiceHandler.sendEmptyMessageDelayed(222, 20000);	
		
		return super.onStartCommand(intent, flags, startId);
	}
	

    @SuppressLint("HandlerLeak")
	public class MainServiceHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 111:
				Intent Intentlvb = new Intent();
            	Intentlvb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	Intentlvb.setComponent(new ComponentName("com.hhzt.iptv", "com.hhzt.iptv.ui.SplashActivity"));
            	Intentlvb.setAction("android.intent.action.VIEW");
        		getApplicationContext().startActivity(Intentlvb);
				break;
			case 222:
				BaseActivity.getInstance().stopMsgLHReceiveService();
				break;
			default:
				break;
			}
		}
	}
	
//	 /**
//     * 时间更新定时器
//     */
//    public class DateAlarmCommunicationReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//        	
//        }
//    }
//    private void registerReceiver() {
//        mDateAlarmCommunicationReceiver = new DateAlarmCommunicationReceiver();
//        mDateAlarmCommunicationsFilter = new IntentFilter("ffffff");
//        getApplicationContext().registerReceiver(mDateAlarmCommunicationReceiver, mDateAlarmCommunicationsFilter);
//    }
//
//    private void startDateUpdateAlarm() {
//        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent("ffffff");
//        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        am.set(AlarmManager.RTC, System.currentTimeMillis() + Constant.IPTV_TIME_FOUR_SECONDES, pi);
//        
//    }
//
//    private void cancelDateUpdateAlarm() {
//        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent("ffffff");
//        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        am.cancel(pi);
//    }

	
}

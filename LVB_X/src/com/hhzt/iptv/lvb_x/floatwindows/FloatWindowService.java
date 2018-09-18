package com.hhzt.iptv.lvb_x.floatwindows;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class FloatWindowService extends Service {

	private Handler handler = new Handler();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		show();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void show() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				FloatWindowManager.getInstance().createLVBEntryWindow(getApplicationContext());
			}
		});
	}

}

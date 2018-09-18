package com.hhzt.iptv.lvb_x.test;

import com.google.gson.Gson;
import com.hhzt.iptv.lvb_x.interfaces.IResponseable;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.net.LVBHttpUtils;

import android.test.AndroidTestCase;

public class AndroidText extends AndroidTestCase {

	public void textUrl(){
		String url = UrlMgr.getUnreadNewsInfosUrl("10003", 1, 1000);
		LogUtil.d("getUnreadNewsMsg----------" + url);  
		LVBHttpUtils.get(url, new IResponseable() {

			@Override
			public void onSuccess(String result) {
				LogUtil.d("getUnreadNewsMsg-----result=" + result);
			}

			@Override
			public void onFailed(String result) {

			}
		});
	}
}

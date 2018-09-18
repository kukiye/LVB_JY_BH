package com.hhzt.iptv.lvb_x.handler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @Package: com.jinggan.dear.common.handler
 * @Description: Handler基类
 * @author: Aaron
 * @date: 2015-12-29
 * @Time: 10:29
 * @version: V1.0
 */
public class BAHandler extends Handler {
    WeakReference<Activity> mActivityReference;

    public BAHandler(Activity activity) {
        mActivityReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mActivityReference.get() == null) {

            throw new NullPointerException();
        }
    }
}

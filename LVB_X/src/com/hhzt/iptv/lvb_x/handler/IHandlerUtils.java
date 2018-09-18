package com.hhzt.iptv.lvb_x.handler;


import android.os.Handler;
import android.os.Message;

/**
 * @Package: com.hhzt.nw.common.handler
 * @Description: Handler工具类
 * @author: Aaron
 * @date: 2015-12-11
 * @Time: 13:20
 * @version: V1.0
 */
public class IHandlerUtils {

    public static void sendHandlerMessage(Handler handler, int what, Object obj) {
        if (handler == null) {
            return;
        }
        handler.sendMessage(handler.obtainMessage(what, obj));
    }

    public static void sendHandlerMessage(Handler handler, int what) {

        if (handler == null) {
            return;
        }
        handler.sendEmptyMessage(what);
    }

    public static void sendHandlerMessage(Handler handler, int what, int arg1, Object obj) {
        if (handler == null)
            return;

        Message msg = handler.obtainMessage();
        msg.what = what;
        msg.arg1 = arg1;
        msg.obj = obj;
        handler.sendMessage(msg);
    }

    public static void sendHandlerMessage(Handler handler, int what, int arg1) {
        if (handler == null) {
            return;
        }
        Message msg = handler.obtainMessage();
        msg.what = what;
        msg.arg1 = arg1;
        handler.sendMessage(msg);
    }


    public static void sendHandlerMessage(Handler handler, int what, int arg1, int arg2) {
        if (handler == null) {
            return;
        }
        handler.sendMessage(handler.obtainMessage(what, arg1, arg2));
    }

    public static void sendHandlerMessage(Handler handler, int what, int arg1, int arg2, Object obj) {
        if (handler == null) {
            return;
        }
        handler.sendMessage(handler.obtainMessage(what, arg1, arg2, obj));
    }

    //延时消息
    public static void sendHandlerMessageDelayTime(Handler handler, int what, int arg1, int arg2, Object obj, long time) {
        if (handler == null) {
            return;
        }
        handler.sendMessageDelayed(handler.obtainMessage(what, arg1, arg2, obj), time);
    }

    //简化
    public static void sendHandlerMessageDelayTime(Handler handler, int what, long time) {
        if (handler == null) {
            return;
        }
        handler.sendMessageDelayed(handler.obtainMessage(what, 0, 0, null), time);

    }

    public static void sendMessageDelayTime(Handler handler, int what, Object obj, long time) {
        handler.sendMessageDelayed(handler.obtainMessage(what, obj), time);
    }

    public static void sendHandlerEmptyMessage(Handler handler, int what) {
        handler.sendEmptyMessage(what);
    }

    public static void sendHandlerEmptyMessageDelayed(Handler handler, int what, int time) {
        handler.sendEmptyMessageDelayed(what, time);
    }

}

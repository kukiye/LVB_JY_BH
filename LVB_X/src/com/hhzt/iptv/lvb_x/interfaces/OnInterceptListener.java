package com.hhzt.iptv.lvb_x.interfaces;

import android.view.KeyEvent;

/**
 * Created by David 16/12/27.
 * 专门用于dispatchKeyEvent事件拦截回调处理
 */

public interface OnInterceptListener {
    boolean onIntercept(KeyEvent event);
}

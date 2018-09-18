/**
 * 作者：   Johnson
 * 日期：   2014年6月18日下午2:37:30
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：MainmenuActivity.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.fragments.MainContentFragment;
import com.hhzt.iptv.lvb_x.fragments.MainTitleFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_main)
public class MainmenuActivity extends BaseActivity {

    public static MainmenuActivity mMainMenuActivity = null;
    private MainTitleFragment mTitleFragment;
    private MainContentFragment mContentFragment;


    public static MainmenuActivity getInstance() {
        return mMainMenuActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainMenuActivity = this;

        // 开启消息推送接收的服务
        UIDataller.getDataller().openMsgPushService();

        mTitleFragment = new MainTitleFragment();
        FragmentMgr.replace(this, false, R.id.fragment_main_title, mTitleFragment, Constant.HOTEL_MAIN_MENU_SCREEN);

        mContentFragment = new MainContentFragment();

        FragmentMgr.replace(this, false, R.id.fragment_main_content, mContentFragment, Constant.HOTEL_MAIN_MENU_SCREEN);
    }


//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                return true;
//        }
//
//        return mContentFragment.onKeyUp(keyCode, event) ? true : super.onKeyUp(keyCode, event);
//    }

    public void setGrideRequestFocus() {
//        GridView programGridView = mContentFragment.getProgramGridView();
//        if (programGridView != null) {
//            programGridView.requestFocus();
//        }

    }
}

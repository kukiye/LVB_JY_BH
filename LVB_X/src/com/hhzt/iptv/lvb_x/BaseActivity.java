/**
 * 作者：   Johnson
 * 日期：   2014年6月11日下午2:45:00
 * 包名：    com.hhzt.iptv_lvb_x
 * 工程名：LVB_X
 * 文件名：BaseActivity.java
 */
package com.hhzt.iptv.lvb_x;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.handler.BAHandler;
import com.hhzt.iptv.lvb_x.handler.IHandlerUtils;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.DeviceMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.lidroid.xutils.ViewUtils;

public abstract class BaseActivity extends FragmentActivity {

    public BAHandler mHandler=new BAHandler(this){
        @Override
        public void handleMessage(Message msg) {
            BaseActivity.this.dispatchMessage(msg);
        }
    };

    private Toast mToast;
    private View mToastRoot;
    private static BaseActivity mInstance;
    private String mFactoryModeCMD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInstance = this;
        ViewUtils.inject(this);
        // 初始化屏幕信息
        LVBXApp.getApp().initScreenInfo();
        // 保持屏幕常亮、加入电源锁
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static BaseActivity getInstance() {
        return mInstance;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * 使用HANDLER 发送消息
     * @param handler
     * @param what
     * @param arg1
     * @param obj
     */
    public void sendHandlerMessage(Handler handler, int what, int arg1, Object obj) {
        IHandlerUtils.sendHandlerMessage(handler, what, arg1, obj);
    }
    //--------------------------------method---------------------------//
    public void dispatchMessage(Message msg) {}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 178:// 乐华电视遥控器信号源切换按键
                BaseActivity.mInstance.showToast("信源切换请进设置", Toast.LENGTH_LONG);
                return true;
            case KeyEvent.KEYCODE_F1:// 酒店服务快捷键
                mFactoryModeCMD = "";
                ActivitySwitchMgr.gotoHSActivity(this, UserMgr.getHSMenuId(), UserMgr.getHSMenuName());
                return true;
            case KeyEvent.KEYCODE_F4:// 电视直播快捷键
            case KeyEvent.KEYCODE_F6:// 电视回看快捷键
                mFactoryModeCMD = "";
                ActivitySwitchMgr.gotoLiveActivity(this);
                return true;
            case KeyEvent.KEYCODE_F5:// 视频点播快捷键
                mFactoryModeCMD = "";
                String a = UserMgr.getVodMenuName();
                ActivitySwitchMgr.gotoVodActivity(this, UserMgr.getVodMenuCode(), UserMgr.getVodMenuName());
                return true;
            case KeyEvent.KEYCODE_F7:// 酒店订餐服务快捷键
                mFactoryModeCMD = "";
                ActivitySwitchMgr.gotoTemplateA5(this, UserMgr.getHSMenuCode(), getString(R.string.hs_order_service));
                return true;
            case KeyEvent.KEYCODE_F8:// 启动第三方app快捷键
                mFactoryModeCMD = "";
                ActivitySwitchMgr.gotoThirdMarketAppActivity(this, UserMgr.getThirdAppMenuCode(), "");
                break;
            case KeyEvent.KEYCODE_F9:// F9保留为之前互动功能快捷键

                break;
            // 勐拉项目预留F10作为音轨切换键,F11为屏比切换键
            case KeyEvent.KEYCODE_F10:
                // F10为音乐快捷键
                break;
            case KeyEvent.KEYCODE_F11:
                // F11为点播搜索快捷键
                break;
            case KeyEvent.KEYCODE_F12:// 语言切换快捷键
                ActivitySwitchMgr.gotoLangSetActivity(this, getString(R.string.system_language));
                break;
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_4:
                mFactoryModeCMD += DeviceMgr.getInstance().switchKeyCodeToNmuber(keyCode);
                gotoFactoryTestMode(keyCode);
                break;
            default:
                mFactoryModeCMD = "";
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void gotoFactoryTestMode(int keyCode) {
        if (Constant.FACTORY_MODE_CMD.equalsIgnoreCase(mFactoryModeCMD)) {
            mFactoryModeCMD = "";
            ActivitySwitchMgr.gotoProjectTestListActivity(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /************************************
     * 单例Toast显示 begin
     **********************************/
    public void showToast(String text, int duration) {
        cancelToast();
        TextView message = null;
        if (null == mToastRoot) {
            mToastRoot = getLayoutInflater().inflate(R.layout.iptv_toast, null);
        }
        message = (TextView) mToastRoot.findViewById(R.id.message);
        message.setText(text);

        if (null == mToast) {
            mToast = new Toast(this);
        }
        mToast.setDuration(duration);
        mToast.setView(mToastRoot);
        mToast.show();
    }

    public void showToast(String text, int gravity, int xOffset, int yOffset, int duration) {
        cancelToast();
        TextView message = null;
        if (null == mToastRoot) {
            mToastRoot = getLayoutInflater().inflate(R.layout.iptv_toast, null);
        }
        message = (TextView) mToastRoot.findViewById(R.id.message);
        message.setText(text);

        if (null == mToast) {
            mToast = new Toast(this);
        }
        mToast.setDuration(duration);
        mToast.setView(mToastRoot);
        mToast.setGravity(gravity, xOffset, yOffset);
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    public void onBackPressed() {
        cancelToast();
        super.onBackPressed();
    }

    /************************************ 单例Toast显示 end **********************************/

    /************************************
     * 启动消息推动接收服务 begin
     **********************************/
    Intent intent = new Intent("com.hhzt.iptv.service.MsgReceiveService");

    public void openMsgReceiveService() {
        startService(intent);
    }

    public void stopMsgReceiveService() {
        stopService(intent);
    }

    /************************************ 启动消息推动接收服务 end **********************************/

    /************************************
     * 乐华电视窗口跳转接收服务 begin
     **********************************/
    Intent intent2 = new Intent("com.hhzt.iptv.service.LHReceiveService");

    public void openMsgLHReceiveService() {
        startService(intent2);
    }

    public void stopMsgLHReceiveService() {
        stopService(intent2);
    }

    /************************************ 乐华窗口跳转接收服务 end **********************************/
}

package com.hhzt.iptv.lvb_x.mgr;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.ConnectionChangeReceiver;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.interfaces.IResponseable;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mediaplayer.LVBMediaplayer;
import com.hhzt.iptv.lvb_x.model.ForcePlayBean;
import com.hhzt.iptv.lvb_x.net.LVBHttpUtils;

/**
 * Created by kobe on 2015/12/5 17:37.
 * 邮箱：quzhongxiang_kobe@163.com
 */
public class MediaControllerMgr {
    private AlarmManager mAlarmTicker = null;
    private static MediaControllerMgr mMediaControllerMgr;

    private MediaControllerMgr() {
    }

    public static MediaControllerMgr getInstance() {
        if (null == mMediaControllerMgr) {
            synchronized (MediaControllerMgr.class) {
                if (null == mMediaControllerMgr) {
                    mMediaControllerMgr = new MediaControllerMgr();
                }
            }
        }

        return mMediaControllerMgr;
    }

    public void startTicker(final ForcePlayBean bean) {
        String url = UrlMgr.getTimeInfoUrl();
        LogUtil.d("startTicker-----url=" + url);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                long differTime = 0;
                long startTime = bean.getStarttime();
                long endTime = bean.getEndtime();
                long currentTime = Long.valueOf(result);

                // 时间已经过期
                if (currentTime > endTime) {
                    // Nothing to do!
                } else {
                    Context context = LVBXApp.getApp().getApplicationContext();
                    Intent intent = new Intent(context, ConnectionChangeReceiver.class);

                    // 在控制时间范围内
                    if (currentTime >= startTime && currentTime <= endTime) {
                        differTime = endTime - currentTime;
                        intent.setAction(Constant.IPTV_LVB_X_BROADCAST_MSG_PLAY_UNLOCK);
                        // 锁定媒体播放器动作
                        SystemMgr.setMediaLockTag(true);
                        // 启动强制播放操作
                        startPlay(bean, (currentTime - startTime));
                    } else {
                        // 锁定媒体播放器动作
                        SystemMgr.setMediaLockTag(false);

                        // 在时间控制范围之前
                        differTime = startTime - currentTime;
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Constant.IPTV_DATA_FORCE_PLAY_LOCK, bean);
                        intent.putExtra(Constant.IPTV_BOUNDLE_FORCE_PLAY_LOCK, bundle);
                        intent.setAction(Constant.IPTV_LVB_X_BROADCAST_MSG_START_TICKER);
                    }

                    PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

                    if (null == mAlarmTicker) {
                        mAlarmTicker = (AlarmManager) LVBXApp.getApp().getSystemService(Context.ALARM_SERVICE);
                    } else {
                        mAlarmTicker.cancel(sender);
                    }
                    mAlarmTicker.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + differTime, sender);
                    
                    //取消播放
//                    UIDataller.getDataller().checkForcePlayMission();
                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 开始播放
     * @param bean 消息
     * @param playTime 开始播放的时间（只针对于点播地址）
     */
    public void startPlay(ForcePlayBean bean, long playTime) {
        String mediaUrl = bean.getUrl();
        String mediaName = "";
        String position = UserMgr.getSavedLiveChannelIndex() + "";
        String channelNum = UserMgr.getSavedLiveChannelNumber() + "";
        LVBMediaplayer.playInteractiveLiveForce(BaseActivity.getInstance(), mediaUrl, mediaName, position, channelNum, playTime);
    }
}


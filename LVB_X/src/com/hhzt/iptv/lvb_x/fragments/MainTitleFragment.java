package com.hhzt.iptv.lvb_x.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/11/20.
 */

public class MainTitleFragment extends BaseFragment {

    @ViewInject(R.id.iv_title_ch)
    private ImageView mTitleChIv;
    @ViewInject(R.id.iv_title_en)
    private ImageView mTitleEnIv;
    @ViewInject(R.id.mainmenu_date)
    private TextView mMainmenuDateTextView;
    @ViewInject(R.id.mainmenu_time)
    private TextView mMainmenuTimeTextView;
    @ViewInject(R.id.mainmenu_week)
    private TextView mMainmenuWeekTextView;
    @ViewInject(R.id.mainmenu_chinese_date)
    private TextView mLunarDateTextView;

    @ViewInject(R.id.base_weath_city)
    private TextView mCityTextView;
    @ViewInject(R.id.base_weath_icon)
    private ImageView mWeatherImageView;
    @ViewInject(R.id.base_weath_tem)
    private TextView mWeatherTextView;

    private DateAlarmReceiver mDateAlarmReceiver;

    private IntentFilter mDateAlarmFilter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_title, container,
                false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (null == savedInstanceState) {
            registerReceiver();

            //            UIDataller.getDataller().setDefaultCity();
            //            UIDataller.getDataller().getWeatherInfos(mActivity, mCityTextView, mWeatherImageView, mWeatherTextView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        startDateUpdateAlarm();
    }


    @Override
    public void onResume() {
        super.onResume();
        setTimeShow(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        cancelDateUpdateAlarm();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BaseActivity.getInstance().getApplicationContext()
                .unregisterReceiver(mDateAlarmReceiver);
    }


    private void startDateUpdateAlarm() {
        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE);
        PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + Constant.IPTV_TIME_ONE_MINUTE, Constant.IPTV_TIME_ONE_MINUTE, pi);
    }

    private void cancelDateUpdateAlarm() {
        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE);
        PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pi);
    }

    private void registerReceiver() {
        mDateAlarmReceiver = new DateAlarmReceiver();
        mDateAlarmFilter = new IntentFilter(
                Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE);
        BaseActivity.getInstance().getApplicationContext()
                .registerReceiver(mDateAlarmReceiver, mDateAlarmFilter);
    }

    public class DateAlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE.equals(intent
                    .getAction())) {
                setTimeShow(false);
            }
        }
    }

    // 设置和更新主界面时间信息
    private void setTimeShow(boolean needAnimation) {
        UIDataller.getDataller().setMainMenuDateInfoWihtLunar(mMainmenuDateTextView,
                mMainmenuTimeTextView, mMainmenuWeekTextView, mLunarDateTextView, needAnimation);
    }


}

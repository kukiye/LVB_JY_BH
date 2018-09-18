/**
 * 作者：   Johnson
 * 日期：   2014年6月11日下午2:47:24
 * 包名：    com.hhzt.iptv_lvb_x
 * 工程名：LVB_X
 * 文件名：BaseFragment.java
 */
package com.hhzt.iptv.lvb_x;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hhzt.iptv.lvb_x.handler.BAHandler;

public class BaseFragment extends Fragment {

    public BAHandler mHandler = new BAHandler(getActivity()) {
        @Override
        public void handleMessage(Message msg) {
            BaseFragment.this.dispatchMessage(msg);
        }
    };

    //--------------------------------method---------------------------//
    public void dispatchMessage(Message msg) {
    }

    public FragmentActivity mActivity;

    // 当fragment被加入到activity时调用（在这个方法中可以获得所在的activity）
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 当activity要得到fragment的layout时，调用此方法，fragment在其中创建自己的layout(界面)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // 当activity的onCreated()方法返回后调用此方法
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mActivity = getActivity();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // 当fragment的layout被销毁时被调用
    @Override
    public void onDetach() {
        super.onDetach();
    }

    // 用于接收宿主Activity的按键事件
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }// 用于接收宿主Activity的按键事件

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    // 用于接收宿主Activity的触摸事件
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }


}

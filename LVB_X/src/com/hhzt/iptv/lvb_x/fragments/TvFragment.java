package com.hhzt.iptv.lvb_x.fragments;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.adapter.TvLiveListAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.customview.LogoLayout;
import com.hhzt.iptv.lvb_x.handler.HandlerValue;
import com.hhzt.iptv.lvb_x.interfaces.ILiveMainListSuccessCB;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.LiveMainModel;
import com.hhzt.iptv.lvb_x.model.MainmenuModel;
import com.hhzt.iptv.lvb_x.utils.BaseLazyFragment;
import com.hhzt.iptv.ui.BaihuHomeActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/20.
 */

public class TvFragment extends BaseLazyFragment {

    @ViewInject(R.id.logo_layout)
    LogoLayout mLogoLayout;
    @ViewInject(R.id.lv_channel)
    ListView mChannelListView;
    private ArrayList<LiveMainModel> mLiveMainModels;
    private TvLiveListAdapter mLiveListAdapter;
    private MainmenuModel model;
    private ArrayList<LiveMainModel> mChannelDataList;
    //当前选中的条目
    private int currentSelectItem = -1;
    private boolean isFirstOnKeyDown = true;


    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv, container,
                false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void initPrepare() {
        isFirstOnKeyDown = true;

        initListener();
        mLiveListAdapter = new TvLiveListAdapter(mActivity);


    }

    @Override
    public void setLeftLayoutRequestLayout() {

        mChannelListView.requestFocus();


    }

    @Override
    protected void onInvisible() {

    }

    @Override
    public void onResume() {
        super.onResume();
        setLiveMainListActionData(mLiveMainModels);
    }

    protected void onVisible() {
        mLogoLayout.setNetInfo(model.getMenulogourl(), model.getName());
        UIDataller.getDataller().setLiveMainList(new ILiveMainListSuccessCB() {
            @Override
            public void setListDatas(ArrayList<LiveMainModel> listModels) {
                sendHandlerMessage(mHandler, HandlerValue.REQ_MAIN_HOME_AD_SUCCESS_RIHT, 0, listModels);


            }
        });

    }


    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {
            case HandlerValue.REQ_MAIN_HOME_AD_SUCCESS_RIHT:
                //左侧广告位  注意 很有可能有变化
                ArrayList<LiveMainModel> listModels = (ArrayList<LiveMainModel>) msg.obj;

                mLiveMainModels = listModels;
                setLiveMainListActionData(listModels);
                break;

        }
    }

    private void initListener() {

        mChannelListView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (currentSelectItem == 0 && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                        ((BaihuHomeActivity) mActivity).setTopButtonRequestFocus();
                    }
                }

                if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        return true;
                    }
                }

                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                        ((BaihuHomeActivity) mActivity).setSpecialFocusRequestFocus();
                        return true;
                    }
                }
                return false;
            }
        });

        mChannelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                int mLiveCurrentChannelNumber = mChannelDataList.get(position).getChannelnumber();
                UserMgr.setSavedLiveChannelNumber(mLiveCurrentChannelNumber);
                ActivitySwitchMgr.gotoLiveActivity(mActivity);

            }
        });

        mChannelListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                currentSelectItem = arg2;


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }


    public void setModel(MainmenuModel model) {
        this.model = model;
    }

    private void setLiveMainListActionData(final ArrayList<LiveMainModel> listModels) {
        if (listModels != null && listModels.size() > 0) {
            this.mChannelDataList = listModels;
            mLiveListAdapter.setLiveMainModels(listModels);
            if (null == mChannelListView.getAdapter()) {
                mChannelListView.setAdapter(mLiveListAdapter);
            } else {
                mLiveListAdapter.notifyDataSetChanged();
            }
            //            mChannelListView.setSelection(mLiveMainChannelIndex);


        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (isFirstOnKeyDown && keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            isFirstOnKeyDown = false;
            mChannelListView.requestFocus();
            mChannelListView.requestFocusFromTouch();
            return true;
        }


        return super.onKeyDown(keyCode, event);
    }

    public void setTvListViewRequestFocus() {

        mChannelListView.requestFocus();
        mChannelListView.requestFocusFromTouch();

    }
}

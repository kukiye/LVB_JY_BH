package com.hhzt.iptv.lvb_x.fragments;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.business.GlobalStore;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.customview.HomeDiamondLayout;
import com.hhzt.iptv.lvb_x.customview.LogoLayout;
import com.hhzt.iptv.lvb_x.handler.HandlerValue;
import com.hhzt.iptv.lvb_x.interfaces.IVodTypeListSuccessed;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.model.MainmenuModel;
import com.hhzt.iptv.lvb_x.model.VodTypeItemModel;
import com.hhzt.iptv.lvb_x.utils.BaseLazyFragment;
import com.hhzt.iptv.ui.BaihuHomeActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/20.
 */

public class ThreeClassTeachFragment extends BaseLazyFragment implements View.OnKeyListener {

    @ViewInject(R.id.logo_layout)
    LogoLayout mLogoLayout;
    @ViewInject(R.id.adapt_layout)
    HomeDiamondLayout mAdaptLayout;
    @ViewInject(R.id.discipline_layout)
    HomeDiamondLayout mDisciplineLayout;
    @ViewInject(R.id.song_layout)
    HomeDiamondLayout mSongLayout;
    private MainmenuModel model;


    private ArrayList<HomeDiamondLayout> mLayoutList;
    private HomeDiamondLayout currentLayout;
    private boolean isShowThreeLevelView = false;
    private VideoGrideFragment videoGrideFragment;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_teach, container,
                false);
        ViewUtils.inject(this, view);
        return view;
    }


    @Override
    protected void initPrepare() {


        mLayoutList = new ArrayList<HomeDiamondLayout>();
        mLayoutList.add(mSongLayout);
        mLayoutList.add(mAdaptLayout);
        mLayoutList.add(mDisciplineLayout);
        initListener();

        if (isShowThreeLevelView) {
            showVodDetailView();
        }

    }

    @Override
    public void setLeftLayoutRequestLayout() {

        mLayoutList.get(mLayoutList.size() - 1).requestFocus();
    }

    @Override
    protected void onInvisible() {

    }


    protected void onVisible() {
        if (model != null) {
            UIDataller.getDataller().setVodTypeData(mActivity, model.getCode(), new IVodTypeListSuccessed() {
                @Override
                public void onTypeListSuccess(ArrayList<VodTypeItemModel> models) {

                    sendHandlerMessage(mHandler, HandlerValue.REQ_HOME_CLASS_TEACH, 0, models);

                }
            });
        }
    }

    private void initListener() {
        mSongLayout.setKeyListner(this);

        mDisciplineLayout.setKeyListner(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        ((BaihuHomeActivity) mActivity).setSpecialFocusRequestFocus();
                        return true;
                    }
                }
                return false;
            }
        });

        mAdaptLayout.setKeyListner(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        return true;
                    }
                }
                return false;
            }
        });

        for (HomeDiamondLayout homeDiamondLayout : mLayoutList) {

            final HomeDiamondLayout finalLayout = homeDiamondLayout;

            homeDiamondLayout.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentLayout = finalLayout;
                    showVodDetailView();

                }
            });

        }
    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {
            case HandlerValue.REQ_HOME_CLASS_TEACH:
                //左侧广告位  注意 很有可能有变化
                ArrayList<VodTypeItemModel> models = (ArrayList<VodTypeItemModel>) msg.obj;
                mLogoLayout.setNetInfo(model.getMenulogourl(), model.getName());

                setVodTypeData(models);
                break;

        }
    }

    /**
     * 显示 视频详情列表
     */
    private void showVodDetailView() {
        VodTypeItemModel vodTypeItemModel = currentLayout.getModel();

        isShowThreeLevelView = true;

        videoGrideFragment = new VideoGrideFragment();
        videoGrideFragment.setModel(vodTypeItemModel, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.class_content_layout, videoGrideFragment);
        transaction.commit();

        mLogoLayout.setNetInfo(vodTypeItemModel.getTypeimage(), vodTypeItemModel.getCategoryname());

        videoGrideFragment.setItemNoListener(new VideoGrideFragment.ItemNoListener() {
            @Override
            public void setItemNo(String number) {
                LogUtil.i("number===" + number);
                ((BaihuHomeActivity) mActivity).getTvCount().setText(number);
            }
        });


    }

    /**
     * 隐藏 视频详情列表
     */
    private void hideVodDetail() {
        isShowThreeLevelView = false;
        GlobalStore.setIsRecycleViewHasFocus(false);

        mLogoLayout.setNetInfo(model.getMenulogourl(), model.getName());
        currentLayout.requestFocus();


        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(videoGrideFragment);
        transaction.commit();
    }

    private void setVodTypeData(ArrayList<VodTypeItemModel> models) {
        if (models != null && models.size() > 0) {
            for (int i = 0; i < models.size(); i++) {
                VodTypeItemModel vodTypeItemModel = models.get(i);
                HomeDiamondLayout diamondLayout = mLayoutList.get(i);
                diamondLayout.setModel(vodTypeItemModel);

            }
        }
    }

    public void setModel(MainmenuModel model) {
        this.model = model;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isShowThreeLevelView) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                hideVodDetail();
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                videoGrideFragment.getFocus();
                return true;
            }

        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                ((BaihuHomeActivity) mActivity).setTopButtonRequestFocus();
            }
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                mAdaptLayout.requestFocus();
                mAdaptLayout.requestFocusFromTouch();
            }
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                return true;
            }
        }
        return false;
    }
}

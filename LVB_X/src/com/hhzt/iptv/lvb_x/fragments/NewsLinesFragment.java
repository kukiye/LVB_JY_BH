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

public class NewsLinesFragment extends BaseLazyFragment implements View.OnKeyListener {

    private MainmenuModel model;

    @ViewInject(R.id.logo_layout)
    LogoLayout mLogoLayout;
    @ViewInject(R.id.baihu_news_layout)
    HomeDiamondLayout mBaihuLayout;
    @ViewInject(R.id.country_news_layout)
    HomeDiamondLayout mCountryLayout;
    @ViewInject(R.id.anhui_news_layout)
    HomeDiamondLayout mAnhuiLayout;
    @ViewInject(R.id.news_topic_layout)
    HomeDiamondLayout mtopicLayout;

    private ArrayList<HomeDiamondLayout> mLayoutList;
    private HomeDiamondLayout currentLayout;
    private boolean isShowThreeLevelView = false;
    private VideoGrideFragment videoGrideFragment;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_lines, container,
                false);
        ViewUtils.inject(this, view);
        return view;
    }


    public void setModel(MainmenuModel model) {
        this.model = model;
    }

    @Override
    protected void initPrepare() {


        if (isShowThreeLevelView) {
            showVodDetailView();
        }

    }


    @Override
    protected void onInvisible() {

    }

    @Override
    protected void onVisible() {

        mLayoutList = new ArrayList<>();
        mLayoutList.add(mBaihuLayout);
        mLayoutList.add(mCountryLayout);
        mLayoutList.add(mAnhuiLayout);
        mLayoutList.add(mtopicLayout);

        initListener();

        if (model != null) {
            UIDataller.getDataller().setVodTypeData(mActivity, model.getCode(), new IVodTypeListSuccessed() {
                @Override
                public void onTypeListSuccess(ArrayList<VodTypeItemModel> models) {

                    sendHandlerMessage(mHandler, HandlerValue.REQ_HOME_NEWS_LINE, 0, models);

                }
            });
        }

    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {
            case HandlerValue.REQ_HOME_NEWS_LINE:
                //左侧广告位  注意 很有可能有变化
                ArrayList<VodTypeItemModel> models = (ArrayList<VodTypeItemModel>) msg.obj;
                mLogoLayout.setNetInfo(model.getMenulogourl(), model.getName());

                setVodTypeData(models);
                break;

        }
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

    private void initListener() {
        mBaihuLayout.setKeyListner(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        ((BaihuHomeActivity) mActivity).setTopButtonRequestFocus();
                    }
                }

                if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        return true;
                    }
                }
                return false;
            }
        });

        mAnhuiLayout.setKeyListner(new View.OnKeyListener() {
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

        mtopicLayout.setKeyListner(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        ((BaihuHomeActivity) mActivity).setSpecialFocusRequestFocus();
                        return true;
                    }
                }
                return false;
            }
        });

        mCountryLayout.setKeyListner(this);

        for (HomeDiamondLayout homeDiamondLayout : mLayoutList) {
            if (isShowThreeLevelView) {
                break;
            }
            final HomeDiamondLayout finalLayout = homeDiamondLayout;

            homeDiamondLayout.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtil.i("finalLayout====" + finalLayout);
                    currentLayout = finalLayout;
                    showVodDetailView();

                }
            });

        }
    }

    /**
     * 显示 视频详情列表
     */
    private void showVodDetailView() {
        VodTypeItemModel vodTypeItemModel = currentLayout.getModel();

        isShowThreeLevelView = true;

        videoGrideFragment = new VideoGrideFragment();
        videoGrideFragment.setModel(vodTypeItemModel, true);

        mLogoLayout.setNetInfo(vodTypeItemModel.getTypeimage(), vodTypeItemModel.getCategoryname());

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.line_content_layout, videoGrideFragment);
        transaction.commit();

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

        mLogoLayout.setNetInfo(model.getMenulogourl(), model.getName());
        currentLayout.requestFocus();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(videoGrideFragment);
        transaction.commit();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isShowThreeLevelView) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                hideVodDetail();
                return true;
            }else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
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
        return false;
    }


}

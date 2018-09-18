package com.hhzt.iptv.lvb_x.fragments;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.adapter.MainFocusListAdapter;
import com.hhzt.iptv.lvb_x.adapter.MainStarAdapter;
import com.hhzt.iptv.lvb_x.adapter.MyStaggeredGridLayoutManager;
import com.hhzt.iptv.lvb_x.business.GlobalStore;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCTemplateConfig;
import com.hhzt.iptv.lvb_x.customview.CustomRecyclerView;
import com.hhzt.iptv.lvb_x.handler.HandlerValue;
import com.hhzt.iptv.lvb_x.interfaces.IVodDetailListSuccessed;
import com.hhzt.iptv.lvb_x.interfaces.IVodTypeListSuccessed;
import com.hhzt.iptv.lvb_x.interfaces.onItemKeyListener;
import com.hhzt.iptv.lvb_x.model.MainmenuModel;
import com.hhzt.iptv.lvb_x.model.VodDetailDataModel;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.model.VodTypeItemModel;
import com.hhzt.iptv.lvb_x.utils.BaseLazyFragment;
import com.hhzt.iptv.ui.MainmenuActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/20.
 */

public class MainRightNewsFragment extends BaseLazyFragment {

    @ViewInject(R.id.lv_focus)
    ListView mLvSpecialFocus;
    @ViewInject(R.id.recycle_view)
    CustomRecyclerView mRecycleView;

    private MainmenuModel specialModel;
    private MainmenuModel starModel;
    private MainFocusListAdapter mFocusAdapter;


    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_right, container,
                false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void initPrepare() {

        mLvSpecialFocus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VodDetailItemModel item = mFocusAdapter.getItem(i);

                //直接播放视频
                UIDataller.getDataller().playVodItem(mActivity, item);
            }
        });

        mLvSpecialFocus.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                        ((MainmenuActivity) mActivity).setGrideRequestFocus();
                    }
                }


                return false;
            }
        });


        ArrayList<MainmenuModel> modelList = GlobalStore.getMainmenuModelList();
        if (modelList != null && modelList.size() > 0) {
            for (MainmenuModel model : modelList) {
                String templatecode = model.getTemplatecode();
                if (templatecode.equalsIgnoreCase(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_SPECIAL_FOCUS)) {
                    specialModel = model;
                } else if (templatecode.equalsIgnoreCase(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_REMOULD_STAR)) {
                    starModel = model;
                }
            }
        }

        if (specialModel != null) {
            UIDataller.getDataller().setVodTypeData(mActivity, specialModel.getCode(), new IVodTypeListSuccessed() {
                @Override
                public void onTypeListSuccess(ArrayList<VodTypeItemModel> models) {

                    sendHandlerMessage(mHandler, HandlerValue.REQ_MAIN_SPECIAL_FOCUS, 0, models);

                }
            });
        }

        if (starModel != null) {
            UIDataller.getDataller().setVodTypeData(mActivity, starModel.getCode(), new IVodTypeListSuccessed() {
                @Override
                public void onTypeListSuccess(ArrayList<VodTypeItemModel> models) {

                    sendHandlerMessage(mHandler, HandlerValue.REQ_MAIN_REMOULD_STAR, 0, models);

                }
            });
        }


    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void onVisible() {


    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {
            case HandlerValue.REQ_MAIN_SPECIAL_FOCUS:
                ArrayList<VodTypeItemModel> focusDataList = (ArrayList<VodTypeItemModel>) msg.obj;

                setFocusModel(focusDataList);
                break;
            case HandlerValue.REQ_MAIN_REMOULD_STAR:
                ArrayList<VodTypeItemModel> models = (ArrayList<VodTypeItemModel>) msg.obj;

                setStarModel(models);
                break;

        }
    }

    private void setFocusModel(ArrayList<VodTypeItemModel> focusDataList) {
        if (focusDataList != null && focusDataList.size() > 0) {

            VodTypeItemModel vodType = focusDataList.get(0);

            UIDataller.getDataller().setVodDetailData(mActivity, vodType.getId(), 1, 500, new IVodDetailListSuccessed() {
                @Override
                public void onDetailListSuccess(VodDetailDataModel voddetailModel) {
                    if (voddetailModel != null) {
                        ArrayList<VodDetailItemModel> vodDetailList = voddetailModel.getResult();
                        setFocusDataList(vodDetailList);
                    }
                }

                @Override
                public void onFail(String result) {

                }
            });
        }


    }

    private void setFocusDataList(ArrayList<VodDetailItemModel> vodDetailList) {
        if (vodDetailList != null && vodDetailList.size() > 0) {

            mFocusAdapter = new MainFocusListAdapter(getActivity(), vodDetailList);
            mLvSpecialFocus.setAdapter(mFocusAdapter);
        }


    }

    private void setStarModel(ArrayList<VodTypeItemModel> models) {
        if (models != null && models.size() > 0) {

            VodTypeItemModel vodType = models.get(0);

            UIDataller.getDataller().setVodDetailData(mActivity, vodType.getId(), 1, 500, new IVodDetailListSuccessed() {
                @Override
                public void onDetailListSuccess(VodDetailDataModel voddetailModel) {
                    if (voddetailModel != null) {
                        ArrayList<VodDetailItemModel> vodDetailList = voddetailModel.getResult();
                        setStarDataList(vodDetailList);
                    }
                }

                @Override
                public void onFail(String result) {

                }
            });
        }


    }

    private void setStarDataList(ArrayList<VodDetailItemModel> vodDetailList) {

        if (vodDetailList != null && vodDetailList.size() > 0) {
            setRecycleViewDataList(vodDetailList);
        }
    }


    private void setRecycleViewDataList(ArrayList<VodDetailItemModel> vodDetailList) {

        MyStaggeredGridLayoutManager linearLayoutManager = new MyStaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);

        mRecycleView.setLayoutManager(linearLayoutManager); //设置他的布局管理器

        MainStarAdapter mRecycleAdapter = new MainStarAdapter(mActivity, mRecycleView, vodDetailList);
        mRecycleView.setAdapter(mRecycleAdapter);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        mRecycleAdapter.setOnItemClickLitener(new MainStarAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(VodDetailItemModel bean, int position) {
                //直接播放视频
                UIDataller.getDataller().playVodItem(mActivity, bean);
            }

            @Override
            public void onKeyListener(int direction, int position) {

            }
        });


        mRecycleView.setonItemKeyUpListener(new onItemKeyListener() {
            @Override
            public void onItemkeyUp() {
                mLvSpecialFocus.requestFocus();
            }

        });

        mRecycleAdapter.setOnLeftEdgeListener(new MainStarAdapter.OnLeftEdgeListener() {
            @Override
            public void onLeftEdge() {
                ((MainmenuActivity) mActivity).setGrideRequestFocus();
            }
        });

    }
}

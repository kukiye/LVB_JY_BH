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
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.adapter.HomeStarAdapter;
import com.hhzt.iptv.lvb_x.adapter.MainFocusListAdapter;
import com.hhzt.iptv.lvb_x.adapter.MyStaggeredGridLayoutManager;
import com.hhzt.iptv.lvb_x.business.GlobalStore;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.customview.CustomRecyclerView;
import com.hhzt.iptv.lvb_x.handler.HandlerValue;
import com.hhzt.iptv.lvb_x.interfaces.IVodDetailListSuccessed;
import com.hhzt.iptv.lvb_x.interfaces.IVodTypeListSuccessed;
import com.hhzt.iptv.lvb_x.interfaces.onItemKeyListener;
import com.hhzt.iptv.lvb_x.model.MainmenuModel;
import com.hhzt.iptv.lvb_x.model.VodDetailDataModel;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.model.VodTypeItemModel;
import com.hhzt.iptv.ui.BaihuHomeActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import static com.hhzt.iptv.lvb_x.handler.IHandlerUtils.sendHandlerMessage;

/**
 * Created by Administrator on 2017/11/20.
 */

public class HomeRightNewsFragment extends BaseFragment {

    @ViewInject(R.id.lv_focus)
    ListView mLvSpecialFocus;
    @ViewInject(R.id.recycle_view)
    CustomRecyclerView mRecyclerView;

    private MainmenuModel specialModel;
    private MainmenuModel starModel;
    private MainFocusListAdapter mFocusAdapter;
    private int currentSelectPosition = 0;
    private boolean isFirstCreate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstCreate = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_right, container,
                false);
        ViewUtils.inject(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();

    }

    protected void initPrepare() {

        initData();


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
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {

                if (currentSelectPosition == 0 && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                        ((BaihuHomeActivity) mActivity).setTopButtonRequestFocus();
                    }
                }

                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                        return true;
                    }
                }
                return false;
            }
        });
        mLvSpecialFocus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                currentSelectPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void initData() {
        specialModel = GlobalStore.getSpecialModel();
        starModel = GlobalStore.getStarModel();

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

            UIDataller.getDataller().setVodDetailData(mActivity, vodType.getId(), 1, 100, new IVodDetailListSuccessed() {
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

            mFocusAdapter = new MainFocusListAdapter(mActivity, vodDetailList);
            mLvSpecialFocus.setAdapter(mFocusAdapter);
        }


    }

    private void setStarModel(ArrayList<VodTypeItemModel> models) {
        if (models != null && models.size() > 0) {

            VodTypeItemModel vodType = models.get(0);

            UIDataller.getDataller().setVodDetailData(mActivity, vodType.getId(), 1, 100, new IVodDetailListSuccessed() {
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

            if (vodDetailList != null && vodDetailList.size() > 0) {
                setRecycleViewDataList(vodDetailList);
            }
        }

    }

    private void setRecycleViewDataList(ArrayList<VodDetailItemModel> vodDetailList) {

        MyStaggeredGridLayoutManager linearLayoutManager = new MyStaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(linearLayoutManager); //设置他的布局管理器

        HomeStarAdapter mRecycleAdapter = new HomeStarAdapter(mActivity, mRecyclerView, vodDetailList);
        mRecyclerView.setAdapter(mRecycleAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecycleAdapter.setOnItemClickLitener(new HomeStarAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(VodDetailItemModel bean, int position) {
                //直接播放视频
                UIDataller.getDataller().playVodItem(mActivity, bean);
            }

            @Override
            public void onKeyListener(int direction, int position) {

            }
        });

        mRecyclerView.setonItemKeyUpListener(new onItemKeyListener() {
            @Override
            public void onItemkeyUp() {
                mLvSpecialFocus.requestFocus();
            }

        });

//        mRecycleAdapter.setOnLeftEdgeListener(new HomeStarAdapter.OnLeftEdgeListener() {
//            @Override
//            public void onLeftEdge() {
//                BaseLazyFragment parentFragment = (BaseLazyFragment) getParentFragment();
//                parentFragment.setLeftLayoutRequestLayout();
//            }
//        });

    }


}

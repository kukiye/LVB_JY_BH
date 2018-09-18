package com.hhzt.iptv.lvb_x.fragments;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.adapter.MediaAdapter;
import com.hhzt.iptv.lvb_x.business.GlobalStore;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.customview.CustomRecyclerView;
import com.hhzt.iptv.lvb_x.handler.HandlerValue;
import com.hhzt.iptv.lvb_x.interfaces.IVodDetailListSuccessed;
import com.hhzt.iptv.lvb_x.interfaces.onItemKeyListener;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.model.VodDetailDataModel;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.model.VodTypeItemModel;
import com.hhzt.iptv.lvb_x.utils.BaseLazyFragment;
import com.hhzt.iptv.ui.BaihuHomeActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/28.
 */

public class VideoGrideFragment extends BaseLazyFragment {

    private VodTypeItemModel vodTypeItemModel;
    @ViewInject(R.id.recycle_view)
    CustomRecyclerView mRecyclerView;

    //    @ViewInject(R.id.tv_count)
    //    TextView mTvCount;//右上角的页角码

    private MediaAdapter mRecycleAdapter;

    private VodTypeItemModel vodType;

    //当前的页码
    private int pageNo = 1;
    //每页个数
    private int pageSize = 20;
    //当前条目数
    private int currentitemNum = 0;
    //总个数
    private int totalCount;
    private boolean isLoadMore = true;
    //上次选中的条目
    private int lastSelectItem;
    //是否进入视频详情
    private boolean isEnterVideoDetail;
    private ArrayList<VodDetailItemModel> factVodDetailList;
    //获得焦点的位置
    private int mFocusPosition;
    private int totalPages;
    private boolean isFirstCreate = true;
    private ItemNoListener itemNoListener;


    public void setModel(VodTypeItemModel vodTypeItemModel, boolean isEnterVideoDetail) {
        this.vodType = vodTypeItemModel;
        this.isEnterVideoDetail = isEnterVideoDetail;
    }

    @Override
    protected void initPrepare() {

        mRecyclerView.setonItemKeyUpListener(new onItemKeyListener() {
            @Override
            public void onItemkeyUp() {
                ((BaihuHomeActivity) mActivity).setTopButtonRequestFocus();
            }
        });
    }

    @Override
    protected void onInvisible() {

    }


    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_gride, container,
                false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void onVisible() {

        ((BaihuHomeActivity) mActivity).hideRightNews();

        getVodDetailData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (itemNoListener != null) {
            itemNoListener.setItemNo("");
        }
        ((BaihuHomeActivity) mActivity).showRightNews();

    }

    private void getVodDetailData() {
        UIDataller.getDataller().setVodDetailData(mActivity, vodType.getId(), pageNo, pageSize, new IVodDetailListSuccessed() {
            @Override
            public void onDetailListSuccess(VodDetailDataModel voddetailModel) {
                sendHandlerMessage(mHandler, HandlerValue.REQ_HOME_VIDEO_GRID, 0, voddetailModel);

            }

            @Override
            public void onFail(String result) {

            }
        });
    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {
            case HandlerValue.REQ_HOME_VIDEO_GRID:
                //左侧广告位  注意 很有可能有变化
                VodDetailDataModel voddetailModel = (VodDetailDataModel) msg.obj;

                if (voddetailModel != null) {
                    totalCount = voddetailModel.getTotalCount();
                    totalPages = voddetailModel.getTotalPages();

                    ArrayList<VodDetailItemModel> vodDetailList = voddetailModel.getResult();
                    setRecycleViewDataList(vodDetailList);
                }
                break;

        }
    }

    interface ItemNoListener {
        void setItemNo(String number);
    }

    public void setItemNoListener(ItemNoListener itemNoListener) {
        this.itemNoListener = itemNoListener;
    }


    private void setRecycleViewDataList(ArrayList<VodDetailItemModel> vodDetailList) {
        LogUtil.i("vodDetailList===" + vodDetailList.size());
        if (vodDetailList == null) {
            return;
        }

        if (vodDetailList.size() == 0) {
            isLoadMore = false;
            if (pageNo == 1) {
                if (itemNoListener != null) {
                    itemNoListener.setItemNo("0/0");
                }
                ((BaihuHomeActivity) mActivity).setTopButtonRequestFocus();
            }
            return;
        }
        ((BaihuHomeActivity) mActivity).setTopButtonRequestFocus();

        if (factVodDetailList == null) {
            factVodDetailList = new ArrayList<>();
        }
        if (pageNo == 1) {
            factVodDetailList.clear();

        }

        if (vodDetailList.size() > 0) {
            factVodDetailList.addAll(vodDetailList);
        }


        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(linearLayoutManager); //设置他的布局管理器

        if (mRecycleAdapter == null) {
            mRecycleAdapter = new MediaAdapter(mActivity, mRecyclerView, factVodDetailList);
            mRecyclerView.setAdapter(mRecycleAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        } else {
            mRecycleAdapter.notifyDataSetChanged(factVodDetailList);
        }
        mRecyclerView.setHasFixedSize(true);
        if (pageNo != 1) {
            if (isLoadMore) {
                LogUtil.i("currentitemNum==" + currentitemNum);
                //                if (mFocusPosition % 2 == 0) {
                //第一行条目获得焦点 右移加载更多

                //                滑动到指定位置
                mRecyclerView.scrollToPosition(currentitemNum);
                //                获得焦点
                mRecycleAdapter.setSelectItem(currentitemNum);
                //                } else {
                //                    //第二行条目获得焦点  右移加载更多 出现的条目会跑到第一行
                //
                //                    mRecyclerView.scrollToPosition(currentitemNum + 1);
                //                    mRecycleAdapter.setSelectItem(currentitemNum + 1);
                //                }
            }
        } else {

            if (isFirstCreate) {
                isFirstCreate = false;
                //                mRecyclerView.scrollToPosition(0);
                //                //                获得焦点
                mRecyclerView.requestFocus();
                mRecycleAdapter.setSelectItem(0);
                if (itemNoListener != null) {
                    itemNoListener.setItemNo(1 + " / " + totalCount);
                }
                GlobalStore.setIsRecycleViewHasFocus(true);
            }

        }

        currentitemNum = currentitemNum + vodDetailList.size();

        mRecycleAdapter.setOnLoadMoreListener(new MediaAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                LogUtil.d("onLoadMore......");
                if (isLoadMore) {

                    pageNo++;
                    getVodDetailData();
                }

            }
        });

        mRecycleAdapter.setOnItemClickLitener(new MediaAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(VodDetailItemModel bean, int position) {

                if (isEnterVideoDetail) {
                    //跳到影片详情页
                    ActivitySwitchMgr.gotoVodItemDetailActivity(getActivity(), bean, vodType.getId(),
                            getActivity().getIntent().getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG));
                } else {
                    //直接播放视频
                    UIDataller.getDataller().playVodItem(mActivity, bean);
                }


            }

            @Override
            public void onKeyListener(int direction, int position) {
                mFocusPosition = position;
                LogUtil.i("mFocusPosition==" + mFocusPosition);
                //                int pageInt = position / pageSize + 1;
                //                mTvCount.setText(pageInt + " / " + totalPages);
                if (itemNoListener != null) {
                    itemNoListener.setItemNo(position + 1 + " / " + totalCount);
                }
            }
        });
    }


    public void getFocus() {

        mRecyclerView.requestFocus();
        GlobalStore.setIsRecycleViewHasFocus(true);

        //        if (factVodDetailList != null && factVodDetailList.size() > 0) {
        //            mTvCount.setText(1 + " / " + factVodDetailList.size());
        //        }


    }

}

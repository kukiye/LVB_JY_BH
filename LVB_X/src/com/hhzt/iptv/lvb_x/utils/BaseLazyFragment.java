package com.hhzt.iptv.lvb_x.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.handler.IHandlerUtils;
import com.hhzt.iptv.lvb_x.log.LogUtil;

/**
 * Created by
 * <p>
 * 实现懒加载的Fragment
 */

public abstract class BaseLazyFragment extends BaseFragment {


    protected View mRootView;
    protected Context mContext;
    protected boolean isVisible;
    private boolean isPrepared;
    public boolean isFirst = true;

    //--------------------system method callback------------------------//

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        initPrepare();

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initView(inflater, container, savedInstanceState);
        }

        return mRootView;
    }


    public void setLeftLayoutRequestLayout() {

    }

    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        LogUtil.i("lazyLoad.....");
        onVisible();
        //第一次加载数据
        isFirst = false;
    }


    /**
     * 使用HANDLER 发送消息
     *
     * @param handler
     * @param what
     * @param arg1
     * @param obj
     */
    public void sendHandlerMessage(Handler handler, int what, int arg1, Object obj) {
        IHandlerUtils.sendHandlerMessage(handler, what, arg1, obj);
    }

    //--------------------------abstract method------------------------//

    /**
     * 在onActivityCreated中调用的方法，可以用来进行初始化操作。
     */
    protected abstract void initPrepare();

    /**
     * fragment被设置为不可见时调用
     */
    protected abstract void onInvisible();

    /**
     * 这里获取数据，刷新界面
     */
    protected abstract void onVisible();

    /**
     * 初始化布局，请不要把耗时操作放在这个方法里，这个方法用来提供一个
     * 基本的布局而非一个完整的布局，以免ViewPager预加载消耗大量的资源。
     */
    protected abstract View initView(LayoutInflater inflater,
                                     @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState);


}

package com.hhzt.iptv.lvb_x.adapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.config.CCTemplateConfig;
import com.hhzt.iptv.lvb_x.fragments.EnterPrisonTeachFragment;
import com.hhzt.iptv.lvb_x.fragments.HeartGuidanceFragment;
import com.hhzt.iptv.lvb_x.fragments.MovieEnjoyFragment;
import com.hhzt.iptv.lvb_x.fragments.NewsLinesFragment;
import com.hhzt.iptv.lvb_x.fragments.OutPrisonGuideFragment;
import com.hhzt.iptv.lvb_x.fragments.PrisonAffairsFragment;
import com.hhzt.iptv.lvb_x.fragments.PrisonCultureFragment;
import com.hhzt.iptv.lvb_x.fragments.ThreeClassTeachFragment;
import com.hhzt.iptv.lvb_x.fragments.TvFragment;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.model.MainmenuModel;

import java.util.List;

/**
 * @author: Aaron
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<MainmenuModel> dataList;
    private FragmentManager mFragmentManager;
    private int viewId = -1;
    private BaseFragment mCurrentFragment;

    public MainViewPagerAdapter(FragmentManager fm, List<MainmenuModel> lists) {
        super(fm);
        this.dataList = lists;
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment basefragment = null;
        if (dataList == null && !(dataList.size() > position)) {
            LogUtil.d("主页数据下发数据下发错误");
        } else {
            MainmenuModel model = dataList.get(position);
            String tem = model.getTemplatecode();

            if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_ENTER_TEACH)) {
                //入监教育
                basefragment = new EnterPrisonTeachFragment();
                //                basefragment.setModel(model);
                //                return fragment;

            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_CLASS_TEACH)) {
                //三课教育
                basefragment = new ThreeClassTeachFragment();
                //                fragment.setModel(model);
                //                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_OUT_GUIDE)) {
                //出监指南
                basefragment = new OutPrisonGuideFragment();
                //                fragment.setModel(model);
                //                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_NEWS_LINE)) {
                //新闻纵横
                basefragment = new NewsLinesFragment();
                //                fragment.setModel(model);
                //                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_CULTURE)) {
                //文化白湖
                PrisonCultureFragment fragment = new PrisonCultureFragment();
                fragment.setModel(model);
                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_HEART)) {
                //心理辅导
                HeartGuidanceFragment fragment = new HeartGuidanceFragment();
                fragment.setModel(model);
                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_LIVE)) {
                //数字电视
                TvFragment fragment = new TvFragment();
                fragment.setModel(model);
                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON)) {
                //狱务公开
                PrisonAffairsFragment fragment = new PrisonAffairsFragment();
                fragment.setModel(model);
                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_VOD)) {
                //影视欣赏
                MovieEnjoyFragment fragment = new MovieEnjoyFragment();
                fragment.setModel(model);
                return fragment;
            }

            if (basefragment != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("menuModel", model);
                //                bundle.putString("code", dataList.get(position).getCode());
                //                bundle.putInt("CodeId", dataList.get(position).getId());
                basefragment.setArguments(bundle);
            }
        }          //个人中心
        return basefragment;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    /**
     * @param container
     * @param position
     * @return 复写取得id
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {
            if (container == null) {
                return null;
            }
            viewId = container.getId();
            return super.instantiateItem(container, position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (BaseFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public BaseFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    private String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public BaseFragment getCurrentFragment(int position) {
        BaseFragment fragment = (BaseFragment) mFragmentManager.findFragmentByTag(makeFragmentName(viewId, position));
        return fragment;
    }
}

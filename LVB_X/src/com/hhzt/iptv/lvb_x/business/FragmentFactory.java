package com.hhzt.iptv.lvb_x.business;

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
import com.hhzt.iptv.lvb_x.model.MainmenuModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/14.
 */

public class FragmentFactory {

    private static Map<Integer, BaseFragment> mFragments = new HashMap<Integer, BaseFragment>();

    public static void clearMap() {
        mFragments.clear();
    }


    public static BaseFragment createFragment(int position, MainmenuModel model) {


        BaseFragment basefragment = mFragments.get(position);
        if (basefragment == null)   //如果在集合中没有取出来，需要重新创建
        {

            if (model == null) {
                return null;
            }

            String tem = model.getTemplatecode();

            if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_ENTER_TEACH)) {
                //入监教育
                EnterPrisonTeachFragment fragment = new EnterPrisonTeachFragment();
                fragment.setModel(model);
                mFragments.put(position, fragment);
                return fragment;

            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_CLASS_TEACH)) {
                //三课教育
                ThreeClassTeachFragment fragment = new ThreeClassTeachFragment();
                fragment.setModel(model);
                mFragments.put(position, fragment);
                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_OUT_GUIDE)) {
                //出监指南
                OutPrisonGuideFragment fragment = new OutPrisonGuideFragment();
                fragment.setModel(model);
                mFragments.put(position, fragment);
                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_NEWS_LINE)) {
                //新闻纵横
                NewsLinesFragment fragment = new NewsLinesFragment();
                fragment.setModel(model);
                mFragments.put(position, fragment);
                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_CULTURE)) {
                //文化白湖
                PrisonCultureFragment fragment = new PrisonCultureFragment();
                fragment.setModel(model);
                mFragments.put(position, fragment);
                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_HEART)) {
                //心理辅导
                HeartGuidanceFragment fragment = new HeartGuidanceFragment();
                fragment.setModel(model);
                mFragments.put(position, fragment);
                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_LIVE)) {
                //数字电视
                TvFragment fragment = new TvFragment();
                fragment.setModel(model);
                mFragments.put(position, fragment);
                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON)) {
                //狱务公开
                PrisonAffairsFragment fragment = new PrisonAffairsFragment();
                fragment.setModel(model);
                mFragments.put(position, fragment);
                return fragment;
            } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_VOD)) {
                //影视欣赏
                MovieEnjoyFragment fragment = new MovieEnjoyFragment();
                fragment.setModel(model);
                mFragments.put(position, fragment);
                return fragment;
            }
        }

        return basefragment;

    }


}

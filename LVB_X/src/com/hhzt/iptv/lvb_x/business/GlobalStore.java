package com.hhzt.iptv.lvb_x.business;

import com.hhzt.iptv.lvb_x.model.MainmenuModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/23.
 */

public class GlobalStore {

    private static ArrayList<MainmenuModel> mainmenuModelList;
    private static ArrayList<MainmenuModel> homeModelList;
    private static boolean isRecycleViewHasFocus=false;
    private static MainmenuModel specialModel;
    private static MainmenuModel starModel;

    public static boolean isRecycleViewHasFocus() {
        return isRecycleViewHasFocus;
    }

    public static void setIsRecycleViewHasFocus(boolean isRecycleViewHasFocus) {
        GlobalStore.isRecycleViewHasFocus = isRecycleViewHasFocus;
    }

    public static ArrayList<MainmenuModel> getMainmenuModelList() {
        return mainmenuModelList;
    }

    public static void setMainmenuModelList(ArrayList<MainmenuModel> mainmenuModelList) {
        GlobalStore.mainmenuModelList = mainmenuModelList;
    }

    public static ArrayList<MainmenuModel> getHomeModelList() {
        return homeModelList;
    }

    public static void setHomeModelList(ArrayList<MainmenuModel> homeModelList) {
        GlobalStore.homeModelList = homeModelList;
    }

    public static void setSpecialModel(MainmenuModel specialModel) {
        GlobalStore.specialModel=specialModel;
    }

    public static MainmenuModel getSpecialModel() {
        return specialModel;
    }

    public static MainmenuModel getStarModel() {
        return starModel;
    }

    public static void setStarModel(MainmenuModel starModel) {
        GlobalStore.starModel=starModel;

    }
}

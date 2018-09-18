/**
 * 作者：   Johnson
 * 日期：   2014年6月24日上午10:35:11
 * 包名：    com.hhzt.iptv.lvb_x.business
 * 工程名：LVB_X
 * 文件名：UIDataller.java
 */
package com.hhzt.iptv.lvb_x.business;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deytech.IDeytechManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.RemoteCMDType;
import com.hhzt.iptv.lvb_x.adapter.BillListAdapter;
import com.hhzt.iptv.lvb_x.adapter.CityGridViewAdapter;
import com.hhzt.iptv.lvb_x.adapter.OrderHistoryAdapter;
import com.hhzt.iptv.lvb_x.adapter.OrderHistoryDetailAdapter;
import com.hhzt.iptv.lvb_x.adapter.OrderMenuMainListAdapter;
import com.hhzt.iptv.lvb_x.adapter.OrderShopCarAdapter;
import com.hhzt.iptv.lvb_x.adapter.RateAdapter;
import com.hhzt.iptv.lvb_x.adapter.VodTypeListAdapter;
import com.hhzt.iptv.lvb_x.config.CCTemplateConfig;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.customview.AppItemSingle;
import com.hhzt.iptv.lvb_x.customview.VerticalScrollTextView;
import com.hhzt.iptv.lvb_x.customview.VodDetailSingle;
import com.hhzt.iptv.lvb_x.customview.VodItemSingle;
import com.hhzt.iptv.lvb_x.effect.CustomPathAnimation;
import com.hhzt.iptv.lvb_x.effect.TextShadowEffect;
import com.hhzt.iptv.lvb_x.interfaces.IBeanOnSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IListOnSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.ILiveDateListSelectItemCB;
import com.hhzt.iptv.lvb_x.interfaces.ILiveMainGridShowSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.ILiveMainListSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.ILiveSubListSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.INewOnSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IOnImageRequestSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IOnMainMenuDataSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IOnPairedSuccessable;
import com.hhzt.iptv.lvb_x.interfaces.IOnRequestDataCB;
import com.hhzt.iptv.lvb_x.interfaces.IOnRoomServiceHistorySuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IOnRoomStausReqCB;
import com.hhzt.iptv.lvb_x.interfaces.IOnSubMenuModelable;
import com.hhzt.iptv.lvb_x.interfaces.IOnVodSubSingleDataSuccess;
import com.hhzt.iptv.lvb_x.interfaces.IOnWeatherInfosSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IOrderMenuHistoryItemOnclickCB;
import com.hhzt.iptv.lvb_x.interfaces.IOrderMenuItemOnclickCB;
import com.hhzt.iptv.lvb_x.interfaces.IOrderMenuListSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IOrderShopCarListSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IOrderSubMenuItemOnclickCB;
import com.hhzt.iptv.lvb_x.interfaces.IOrderSubMenuListSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IPaymentCheckCB;
import com.hhzt.iptv.lvb_x.interfaces.IResponseable;
import com.hhzt.iptv.lvb_x.interfaces.IThirdmenuModelable;
import com.hhzt.iptv.lvb_x.interfaces.IUserSearchOnSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IVodDetailItemMoveLeftable;
import com.hhzt.iptv.lvb_x.interfaces.IVodDetailItemable;
import com.hhzt.iptv.lvb_x.interfaces.IVodDetailListSuccessed;
import com.hhzt.iptv.lvb_x.interfaces.IVodTotalSizeAble;
import com.hhzt.iptv.lvb_x.interfaces.IVodTypeListSuccessed;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mediaplayer.LVBMediaplayer;
import com.hhzt.iptv.lvb_x.mediaplayer.LVBMusicBgMediaPlayer;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.mgr.DeviceMgr;
import com.hhzt.iptv.lvb_x.mgr.InteractiveMgr;
import com.hhzt.iptv.lvb_x.mgr.MediaControllerMgr;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.mgr.ThreadPoolManager;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.mgr.WifiApMgr;
import com.hhzt.iptv.lvb_x.model.AppDetailDataModel;
import com.hhzt.iptv.lvb_x.model.AppItemMessageModel;
import com.hhzt.iptv.lvb_x.model.BillModel;
import com.hhzt.iptv.lvb_x.model.BookCategoryModel;
import com.hhzt.iptv.lvb_x.model.BookPageModel;
import com.hhzt.iptv.lvb_x.model.CityItemModel;
import com.hhzt.iptv.lvb_x.model.DefaultCityModel;
import com.hhzt.iptv.lvb_x.model.FoodModel;
import com.hhzt.iptv.lvb_x.model.ForcePlayBean;
import com.hhzt.iptv.lvb_x.model.LiveBackDataListModel;
import com.hhzt.iptv.lvb_x.model.LiveMainModel;
import com.hhzt.iptv.lvb_x.model.LiveSubModel;
import com.hhzt.iptv.lvb_x.model.LoginInfoModel;
import com.hhzt.iptv.lvb_x.model.MainmenuModel;
import com.hhzt.iptv.lvb_x.model.MsgResultBean;
import com.hhzt.iptv.lvb_x.model.MsgSocketServerInfo;
import com.hhzt.iptv.lvb_x.model.NewsDataItems;
import com.hhzt.iptv.lvb_x.model.OrderHistoryMainModel;
import com.hhzt.iptv.lvb_x.model.OrderMainMenuModel;
import com.hhzt.iptv.lvb_x.model.OrderShopCarSingleModel;
import com.hhzt.iptv.lvb_x.model.OrderShopCarSingleSubModel;
import com.hhzt.iptv.lvb_x.model.OrderSubMenuModel;
import com.hhzt.iptv.lvb_x.model.OrderSubSingleModel;
import com.hhzt.iptv.lvb_x.model.PairAuthorizationModel;
import com.hhzt.iptv.lvb_x.model.PrisonInfoCategoryModel;
import com.hhzt.iptv.lvb_x.model.PrisonInfoPageModel;
import com.hhzt.iptv.lvb_x.model.PrisonWebsitCategoryModel;
import com.hhzt.iptv.lvb_x.model.PrisonWebsitPageModel;
import com.hhzt.iptv.lvb_x.model.PrsionInfoModel;
import com.hhzt.iptv.lvb_x.model.RateModel;
import com.hhzt.iptv.lvb_x.model.RoomServiceModel;
import com.hhzt.iptv.lvb_x.model.SubmenuModel;
import com.hhzt.iptv.lvb_x.model.ThirdmenuModel;
import com.hhzt.iptv.lvb_x.model.ThrowBillResult;
import com.hhzt.iptv.lvb_x.model.UserRequestModel;
import com.hhzt.iptv.lvb_x.model.ViewConfigBean;
import com.hhzt.iptv.lvb_x.model.VodDetailDataModel;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.model.VodItemPlayMessageModel;
import com.hhzt.iptv.lvb_x.model.VodTypeItemModel;
import com.hhzt.iptv.lvb_x.model.WeatherInfoModel;
import com.hhzt.iptv.lvb_x.model.WelcomeInfoModel;
import com.hhzt.iptv.lvb_x.net.LVBDownloadManager;
import com.hhzt.iptv.lvb_x.net.LVBHttpUtils;
import com.hhzt.iptv.lvb_x.rootmanager.RootManager;
import com.hhzt.iptv.lvb_x.utils.ApkUtil;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.hhzt.iptv.lvb_x.utils.CCAnimationUtils;
import com.hhzt.iptv.lvb_x.utils.CommonUtil;
import com.hhzt.iptv.lvb_x.utils.DateTimeUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.hhzt.iptv.lvb_x.utils.download.DownloadManager;
import com.hhzt.iptv.lvb_x.utils.download.DownloadService;
import com.hhzt.iptv.ui.MediaPlayerActivity;
import com.hhzt.iptv.ui.WelcomeADActivity;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.util.ArrayList;

public class UIDataller {

    private static UIDataller mSingleInstance = new UIDataller();
    private int mEpisodePosition = 0;

    public static UIDataller getDataller() {
        if (null == mSingleInstance) {
            mSingleInstance = new UIDataller();
        }
        return mSingleInstance;
    }

    private UIDataller() {

    }

    /**
     * 打开设备热点
     *
     * @param context
     */
    public void openWiFiHotSpot(Context context) {
        String wifiApName = UserMgr.getWifiName();
        String wifiApPasswd = UserMgr.getInteracPassword();
        boolean hideFlag = UserMgr.getSSIDHideTag();

        // 开启热点动作
        if (!StringUtil.isEmpty(wifiApName)) {
            WifiApMgr wifiApMgr = new WifiApMgr(context);
            wifiApMgr.startWifiAp(wifiApName, wifiApPasswd, hideFlag);
        }
    }

    /**
     * 关闭设备热点
     *
     * @param context
     */
    public void closeWiFiHotSpot(Context context) {
        WifiApMgr wifiApMgr = new WifiApMgr(context);
        wifiApMgr.closeWifiAp(context);
    }

    /**
     * 初始化获取显示配置信息（检测设备状态、进入系统用户界面）
     */
    public void initViewTempConfigInfo(final Activity currentActivity,
                                       final Class<?> destActivityCls, final int languageFlag) {
        String url = UrlMgr.getViewConfigUrl();
        LogUtil.d("initViewTempConfigInfo--------" + url);
        // http://218.17.13.108:9999/epgjy/json/hotel/variable?groupId=3&account=10005
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArrayList<ViewConfigBean> listModels = gson.fromJson(result,
                        new TypeToken<ArrayList<ViewConfigBean>>() {
                        }.getType());
                ConfigMgr.getInstance().addConfigs(listModels);

                // 检测设备状态是否可用
                switch (Config.DEVICE_BOX_LOCK_TAG) {
                    case 1:
                        DeviceMgr.getInstance().checkStatus();
                        break;
                    default:
                        break;
                }

                // 进入系统用户界面
                if (null != currentActivity && null != destActivityCls) {

                    String video = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.AB_AD_WELCOME_VIDEO);
                    String time = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.AB_AD_WELCOME_TIME);
                    LogUtil.i("video:" + video + "---time---:" + time);
                    if (StringUtil.isEmpty(video) && StringUtil.isEmpty(time)) {
                        Intent intent = new Intent(currentActivity, destActivityCls);
                        intent.putExtra(Constant.IPTV_SYS_HOTEL_WANTED_LANG_TYPE,
                                languageFlag);
                        currentActivity.startActivity(intent);
                        currentActivity.finish();
                    } else {
                        Intent intent = new Intent(currentActivity, WelcomeADActivity.class);
                        currentActivity.startActivity(intent);
                        currentActivity.finish();
                    }


                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 设置自动义的背景图
     *
     * @param context
     * @param imageView
     */
    public void setMainMenuAniBg(Context context,
                                 IOnImageRequestSuccessCB successCB) {
        ArrayList<String> urlList = new ArrayList<String>();

        int number = ConfigMgr.getInstance().getNumberByHeadName(
                CCViewConfig.MAINMENU_BG_HEAD);
        for (int i = 0; i < number; i++) {
            String name = CCViewConfig.MAINMENU_BG_HEAD + (i + 1);
            String url = ConfigMgr.getInstance().getBeanVaule(name);
            if (!StringUtil.isEmpty(url)) {
                urlList.add(url);
            }
        }
        successCB.successCB(urlList);
    }

    /**
     * 设置欢迎界面自定义背景图
     *
     * @param activity
     * @param successCB
     */
    public void setWelcomeAniBg(Context context,
                                IOnImageRequestSuccessCB successCB) {
        ArrayList<String> urlList = new ArrayList<String>();

        int number = ConfigMgr.getInstance().getNumberByHeadName(
                CCViewConfig.WELCOME_BG);
        for (int i = 0; i < number; i++) {
            String name = CCViewConfig.WELCOME_BG + (i + 1);
            String url = ConfigMgr.getInstance().getBeanVaule(name);
            if (!StringUtil.isEmpty(url)) {
                urlList.add(url);
            }
        }
        successCB.successCB(urlList);
    }

    /**
     * 检测城市设置是否手动设置过
     */
    public boolean hasSettedCity() {
        boolean citySetFlag = UserMgr.getWeatherCitySettedFlag();
        String weatherCityName = UserMgr.getWeatherCityName();
        String weatherCityNumCode = UserMgr.getWeatherCityNumCode();
        String weatherCityAirCode = UserMgr.getWeatherCityAirCode();

        if (citySetFlag && !StringUtil.isEmpty(weatherCityName)
                && !StringUtil.isEmpty(weatherCityNumCode)
                && !StringUtil.isEmpty(weatherCityAirCode)) {
            return true;
        }

        return false;
    }

    /**
     * 设置默认城市
     */
    public void setDefaultCity() {
        String url = UrlMgr.getDefaultCityUrl();
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                if (!StringUtil.isEmpty(result)) {
                    Gson gson = new Gson();
                    DefaultCityModel model = gson.fromJson(result,
                            DefaultCityModel.class);
                    UserMgr.setWeatherCityName(model.getCityname());
                    UserMgr.setWeatherNumCode(model.getCityweathercode());
                    UserMgr.setWeatherAirCode(model.getCityaircode());
                } else {
                    BaseActivity.getInstance().showToast(
                            LVBXApp.getApp().getString(
                                    R.string.request_default_city_failed),
                            Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 设置欢迎界面数据信息
     *
     * @param context
     * @param hotelImage
     * @param hotelLogo
     * @param clientWelcomeTips
     * @param clientWelcomeContents
     */
    public void setWelcomeDatas(final Context context,
                                final TextView clientWelcomeTips,
                                final TextView clientWelcomeContents, final boolean enableHotspot) {
        String userName = UserMgr.getUserName();
        String url = UrlMgr.getWelcomeInfosUrl(userName);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                WelcomeInfoModel welcomeModel = gson.fromJson(result,
                        WelcomeInfoModel.class);
                if (welcomeModel != null) {
                    if (null != clientWelcomeTips) {
                        switch (SystemMgr.getSystemLangType()) {
                            case Constant.IPTV_SYSTEM_LANG_US_ENGLISH:
                                String custmerNameEn = welcomeModel
                                        .getCustomername();
                                if (!StringUtil.isEmpty(custmerNameEn)) {
                                    String clientEn1Tips = String.format(
                                            context.getString(R.string.welcome_client_tips),
                                            custmerNameEn);
                                    clientWelcomeTips.setText(clientEn1Tips);
                                } else {
                                    clientWelcomeTips.setText(context
                                            .getString(R.string.dear_customer_tips));
                                }
                                break;
                            case Constant.IPTV_SYSTEM_LANG_SIMPLE_CHINESE:
                                String custmerName = welcomeModel.getCustomername();
                                if (!StringUtil.isEmpty(custmerName)) {
                                    String clientTips = String.format(
                                            context.getString(R.string.welcome_client_tips),
                                            custmerName);
                                    clientWelcomeTips.setText(clientTips
                                            + context
                                            .getString(R.string.welcome_client_hello));
                                } else {
                                    clientWelcomeTips.setText(context
                                            .getString(R.string.dear_customer_tips));
                                }
                                TextShadowEffect.getInstance().setBlueShadow(5.0f,
                                        2.0f, 2.0f, Color.argb(255, 0, 140, 255),
                                        clientWelcomeTips);
                                break;
                            default:
                                break;
                        }
                    }

                    if (null != clientWelcomeContents) {
                        clientWelcomeContents.setText("    "
                                + welcomeModel.getWelcome());
                        TextShadowEffect.getInstance().setBlueShadow(5.0f,
                                2.0f, 2.0f, Color.argb(255, 0, 140, 255),
                                clientWelcomeContents);
                    }

                    // 检测客户身份为刚入住的客户，则进行数据的清除 //监狱项目不清楚此断点
                    //					if (UserMgr.checkIsNewClient(welcomeModel.getCheckintime())) {
                    //						// 删除点播保存的断点续播数据
                    //						DbUtils db = DbUtils.create(context, Constant.DB_NAME,
                    //								Config.DB_VERSION, null);
                    //						try {
                    //							db.deleteAll(VodBreakPointInfo.class);
                    //							if (null != db && db.getDatabase().isOpen()) {
                    //								db.close();
                    //							}
                    //						} catch (DbException e) {
                    //							e.printStackTrace();
                    //						}
                    //
                    //						// 删除直播保存的频道号信息
                    //						UserMgr.setSavedLiveChannelNumber(0);
                    //					}
                    UserMgr.setCheckInTime(welcomeModel.getCheckintime());

                    // 设置热点名称
                    if (enableHotspot) {
                        if ("on".equalsIgnoreCase(UserMgr.getHotSpotEnable())) {
                            switch (Config.LvbDeviceType) {
                                case Constant.DEVICE_TYPE_MOBILE:
                                case Constant.DEVICE_TYPE_MOBILE_HSJQ:
                                    break;
                                default:
                                    // 开启热点动作
                                    openWiFiHotSpot(context);
                                    break;
                            }
                        } else {
                            switch (Config.LvbDeviceType) {
                                case Constant.DEVICE_TYPE_MOBILE:
                                case Constant.DEVICE_TYPE_MOBILE_HSJQ:
                                    break;
                                default:
                                    // 关闭热点动作
                                    closeWiFiHotSpot(context);
                                    break;
                            }
                        }
                    }
                } else {
                    BaseActivity.getInstance().showToast(
                            LVBXApp.getApp().getString(
                                    R.string.request_url_error),
                            Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailed(String result) {
                BaseActivity.getInstance().showToast(
                        LVBXApp.getApp().getString(R.string.request_net_failed)
                                + result, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 设置热点详细信息
     *
     * @param activity
     * @param hotSpotNameTextView
     * @param hotSpotPasswdTextView
     */
    public void getHotSpotInfos(Activity activity,
                                TextView hotSpotNameTextView, TextView hotSpotPasswdTextView,
                                ImageView hotspotImageView) {
        TextShadowEffect.getInstance().setBlueShadow(5.0f, 2.0f, 2.0f,
                Color.argb(255, 0, 140, 255), hotSpotNameTextView,
                hotSpotPasswdTextView);
        if ("on".equalsIgnoreCase(UserMgr.getHotSpotEnable())) {
            switch (Config.LvbDeviceType) {
                case Constant.DEVICE_TYPE_MOBILE:
                case Constant.DEVICE_TYPE_MOBILE_HSJQ:
                    hotSpotNameTextView.setVisibility(View.INVISIBLE);
                    hotSpotPasswdTextView.setVisibility(View.INVISIBLE);
                    hotspotImageView.setVisibility(View.INVISIBLE);
                    break;
                default:
                    if (null != activity) {
                        // 设置热点名称
                        String hotSpotTag = activity
                                .getString(R.string.hotspot_tag);
                        String hotSpotName = hotSpotTag + ":"
                                + UserMgr.getWifiName();
                        hotSpotNameTextView.setText(hotSpotName);

                        // 设置热点密码
                        String wifiPasswdTag = activity
                                .getString(R.string.wifipasswd_tag);
                        String wifiPasswdValue = UserMgr.getInteracPassword();
                        String wifiPasswd = wifiPasswdTag + ":" + wifiPasswdValue;
                        hotSpotPasswdTextView.setText(wifiPasswd);

                        CCAnimationUtils.setRandomAnimation(hotspotImageView,
                                hotSpotNameTextView, hotSpotPasswdTextView);
                    }
                    break;
            }
        } else {
            hotSpotNameTextView.setVisibility(View.INVISIBLE);
            hotSpotPasswdTextView.setVisibility(View.INVISIBLE);
            hotspotImageView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置天气预报详细信息
     *
     * @param activity
     * @param weatherImage
     * @param cityNameTextView
     * @param tempTextView
     */
    public void getWeatherInfos(final Activity activity,
                                final TextView cityNameTextView, final ImageView weatherImage,
                                final TextView tempTextView) {
        TextShadowEffect.getInstance().setBlueShadow(5.0f, 2.0f, 2.0f,
                Color.argb(255, 0, 140, 255), cityNameTextView, tempTextView);
        String cityCode = UserMgr.getWeatherCityNumCode();
        String url = UrlMgr.getWeatherInUrl(cityCode, 1 + "");
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArrayList<WeatherInfoModel> listModels = gson.fromJson(result,
                        new TypeToken<ArrayList<WeatherInfoModel>>() {
                        }.getType());

                if (null != listModels && listModels.size() > 0) {
                    BitmapUtil.setFadeInImage(listModels.get(0)
                            .getLogoImageUrl(), weatherImage);
                    cityNameTextView.setText(listModels.get(0).getCityname());
                    tempTextView.setText(listModels.get(0).getTemp());

                    CCAnimationUtils.setRandomAnimation(weatherImage,
                            cityNameTextView, tempTextView);
                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 设置日期、时间和星期信息
     *
     * @param dateTextView
     * @param dateWeekTextView
     * @param needAnimation
     */
    public void setMainMenuDateInfo(final TextView dateTextView,
                                    final TextView timeTextView, final TextView dateWeekTextView,
                                    boolean needAnimation) {
        TextShadowEffect.getInstance().setBlueShadow(5.0f, 2.0f, 2.0f,
                Color.argb(255, 0, 140, 255), dateTextView, timeTextView,
                dateWeekTextView);
        String url = UrlMgr.getTimeInfoUrl();
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                if (!StringUtil.isEmpty(result)) {
                    long timeMillis = Long.parseLong(result);
                    String dateText = DateTimeUtil.toTime(timeMillis,
                            DateTimeUtil.DATE_DEFAULT_FORMATE_OTHER);
                    String timeText = DateTimeUtil.toTime(timeMillis,
                            DateTimeUtil.DATE_FORMATE_HOUR_MINUTE_SECOND);
                    dateTextView.setText(dateText);
                    timeTextView.setText(timeText);
                    dateWeekTextView.setText(DateTimeUtil.getWeekTime(0,
                            timeMillis));
                }
            }

            @Override
            public void onFailed(String result) {

            }
        });

        if (needAnimation) {
            CCAnimationUtils.setRandomAnimation(dateTextView, timeTextView,
                    dateWeekTextView);
        }
    }

    /**
     * 设置日期、时间和星期信息 带有农历
     *
     * @param dateTextView
     * @param dateWeekTextView
     * @param lunarDateTextView
     * @param needAnimation
     */
    public void setMainMenuDateInfoWihtLunar(final TextView dateTextView,
                                             final TextView timeTextView, final TextView dateWeekTextView,
                                             final TextView lunarDateTextView, boolean needAnimation) {
        TextShadowEffect.getInstance().setBlueShadow(5.0f, 2.0f, 2.0f,
                Color.argb(255, 0, 140, 255), dateTextView, timeTextView,
                dateWeekTextView);
        String url = UrlMgr.getTimeInfoUrl();
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                if (!StringUtil.isEmpty(result)) {
                    long timeMillis = Long.parseLong(result);
                    String dateText = DateTimeUtil.toTime(timeMillis,
                            DateTimeUtil.DATE_DEFAULT_FORMATE_OTHER);
                    String timeText = DateTimeUtil.toTime(timeMillis,
                            DateTimeUtil.DATE_FORMATE_HOUR_MINUTE);

                    String lunarDate = DateTimeUtil.toLunarDate(timeMillis);
                    lunarDateTextView.setText(lunarDate);
                    dateTextView.setText(dateText);
                    timeTextView.setText(timeText);
                    dateWeekTextView.setText(DateTimeUtil.getWeekTime(0,
                            timeMillis));
                }
            }

            @Override
            public void onFailed(String result) {

            }
        });

        if (needAnimation) {
            CCAnimationUtils.setRandomAnimation(dateTextView, timeTextView,
                    dateWeekTextView);
        }
    }

    /**
     * 获取服务器时间
     *
     * @param iOnRequestDataCB
     */
    public void getServerTimeMillis(
            final IOnRequestDataCB<Long> iOnRequestDataCB) {
        String url = UrlMgr.getTimeInfoUrl();
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                if (!StringUtil.isEmpty(result)) {
                    long timeMillis = Long.parseLong(result);
                    iOnRequestDataCB.onDataRequestSuccess(timeMillis);
                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 设置操作提示信息
     *
     * @param activity
     * @param homeImageView
     * @param resId
     * @param subPathTextView
     * @param subPath
     * @param actionOkTipsTextView
     * @param okTips
     * @param actionBackTipsTextView
     * @param backTips
     */
    public void setHsActionTips(Activity activity, ImageView homeImageView,
                                int resId, TextView subPathTextView, String subPath,
                                TextView actionOkTipsTextView, String okTips,
                                TextView actionBackTipsTextView, String backTips) {
        homeImageView.setBackgroundResource(resId);
        subPathTextView.setText(subPath);
        actionOkTipsTextView.setText(okTips);
        actionBackTipsTextView.setText(backTips);
    }

    /**
     * 获取确认操作提示信息
     *
     * @return
     */
    public String getOkActionTips(Activity activity) {
        String tips = "";
        tips = "\"OK\" " + activity.getString(R.string.enter);
        return tips;
    }

    /**
     * 获取返回操作提示信息
     *
     * @param activity
     * @return
     */
    public String getBackActionTips(Activity activity) {
        String tips = "";
        tips = "\"RETURN\" " + activity.getString(R.string.back);
        return tips;
    }

    /**
     * 获取酒店服务信息---一级菜单类型
     *
     * @param activity
     * @param hotelImageBg
     * @param mainTitle
     * @param subTitle
     */
    public void getSubMenuHomeInfos(final Activity activity, int mMenuId,
                                    final IOnSubMenuModelable successCB) {
        String url = UrlMgr.getSubMenuUrl(mMenuId);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArrayList<SubmenuModel> listModels = gson.fromJson(result,
                        new TypeToken<ArrayList<SubmenuModel>>() {
                        }.getType());

                successCB.setValue(listModels);
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 设置商务应用信息
     *
     * @param activity
     * @param appType
     * @param gridLayout
     */
    public void setSubAppInfo(final Activity activity, String menuCode,
                              final GridLayout gridLayout, final DownloadManager downloadManager) {
        String url = UrlMgr.getSubAppUrl(menuCode);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                AppDetailDataModel models = gson.fromJson(result,
                        new TypeToken<AppDetailDataModel>() {
                        }.getType());
                ArrayList<AppItemMessageModel> items = models.getResult();
                if (items != null && items.size() > 0) {
                    for (int i = 0; i < items.size(); i++) {
                        AppItemSingle single = new AppItemSingle(activity,
                                downloadManager, items.get(i).getDownloadurl(),
                                items.get(i));
                        single.setValues(items.get(i).getImageurl(),
                                items.get(i).getGamename());
                        gridLayout.addView(single);
                    }
                    gridLayout.getChildAt(0).requestLayout();
                    gridLayout.getChildAt(0).requestFocus();
                } else if (items != null && items.size() == 0) {

                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 获取酒店服务------二级菜单类型 模板一---------酒店介绍、房型介绍、旗下酒店使用同一个模板
     * LVBX1ITTT:LVB_X1_IMAGE_TEXT_TEXT_TEXT
     *
     * @param activity
     * @param serviceType
     * @param hotelImageBg
     * @param mainTitle
     * @param subTitle
     * @param detailContents
     */
    public void get_LVBX1ITTT_DetailsIntroduceInfos(final Activity activity,
                                                    String menuCode, final ImageView hotelImageBg,
                                                    final View titleView, final View contentView,
                                                    final TextView mainTitle, final TextView subTitle,
                                                    final VerticalScrollTextView detailContents,
                                                    ArrayList<ThirdmenuModel> thirdmenuModelList, int currentPageNum,
                                                    final TextView currentPageTextView,
                                                    final IThirdmenuModelable thirdmenuModelable) {
        if (null == thirdmenuModelList) {
            String url = UrlMgr.getThirdMenuUrl(menuCode);
            LVBHttpUtils.get(url, new IResponseable() {

                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    ArrayList<ThirdmenuModel> listModels = gson.fromJson(
                            result, new TypeToken<ArrayList<ThirdmenuModel>>() {
                            }.getType());
                    if (listModels != null && listModels.size() > 0) {
                        thirdmenuModelable.setValue(listModels);
                        BitmapUtil.setRandomImage(listModels.get(0)
                                .getLargeimage(), hotelImageBg);
                        CCAnimationUtils.setRandomAnimation(titleView,
                                contentView, mainTitle, subTitle);
                        mainTitle.setText(listModels.get(0).getText1());
                        subTitle.setText(listModels.get(0).getText2());
                        detailContents.setText(listModels.get(0).getAreatext());
                        String pageTips = 1 + File.separator
                                + listModels.size();
                        currentPageTextView.setText(pageTips);
                        SpannableString ss = new SpannableString(listModels
                                .get(0).getAreatext());
                        detailContents.setMText(ss);
                        detailContents.setTextSize(25);
                        detailContents.resetText();
                        detailContents.invalidate();
                    } else if (listModels != null && listModels.size() == 0) {

                    } else {

                    }
                }

                @Override
                public void onFailed(String result) {

                }
            });
        } else {
            BitmapUtil.setRandomImage(thirdmenuModelList.get(currentPageNum)
                    .getLargeimage(), hotelImageBg);
            CCAnimationUtils.setRandomAnimation(titleView, contentView,
                    mainTitle, subTitle);
            mainTitle
                    .setText(thirdmenuModelList.get(currentPageNum).getText1());
            subTitle.setText(thirdmenuModelList.get(currentPageNum).getText2());
            detailContents.setText(thirdmenuModelList.get(currentPageNum)
                    .getAreatext());
            String pageTips = (currentPageNum + 1) + File.separator
                    + thirdmenuModelList.size();
            currentPageTextView.setText(pageTips);
            SpannableString ss = new SpannableString(thirdmenuModelList.get(
                    currentPageNum).getAreatext());
            detailContents.setMText(ss);
            detailContents.resetText();
            detailContents.invalidate();
        }
    }

    /**
     * 酒店服务----三级界面数据
     *
     * @param activity
     * @param actionType
     * @param hotelImageBg
     */
    public void getThirdMenuImageBgInfos(final Activity activity,
                                         final String menuCode, final ImageView hotelImageBg,
                                         final ArrayList<ThirdmenuModel> thirdmenuModelList,
                                         int currentPageNum, final TextView currentPageTextView,
                                         IThirdmenuModelable ithirdmenuModelable) {
        if (null == thirdmenuModelList) {
            String url = UrlMgr.getThirdMenuUrl(menuCode);
            LVBHttpUtils.get(url, new IResponseable() {

                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    ArrayList<ThirdmenuModel> listModels = gson.fromJson(
                            result, new TypeToken<ArrayList<ThirdmenuModel>>() {
                            }.getType());
                    if (listModels != null && listModels.size() > 0) {
                        BitmapUtil.setFadeInImage(listModels.get(0)
                                .getLargeimage(), hotelImageBg);
                        if (null != currentPageTextView) {
                            String pageTips = 1 + File.separator
                                    + listModels.size();
                            currentPageTextView.setText(pageTips);
                        }
                    } else if (listModels != null && listModels.size() == 0) {

                    } else {

                    }
                }

                @Override
                public void onFailed(String result) {

                }
            });
        } else {
            if (null != hotelImageBg) {
                BitmapUtil.setFadeInImage(thirdmenuModelList
                        .get(currentPageNum).getLargeimage(), hotelImageBg);
            }
            if (null != currentPageTextView) {
                String pageTips = (currentPageNum + 1) + File.separator
                        + thirdmenuModelList.size();
                currentPageTextView.setText(pageTips);
            }
        }
    }

    /**
     * 启动第三方apk逻辑
     *
     * @param fromActivity
     * @param menuCode
     */
    public void startThirdMarketApp(final Context context, String menuCode) {
        String url = UrlMgr.getThirdMenuUrl(menuCode);

        LVBHttpUtils.get(url, new IResponseable() {
            LVBDownloadManager downloadManager = null;

            @SuppressLint("HandlerLeak")
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArrayList<ThirdmenuModel> listModels = gson.fromJson(result,
                        new TypeToken<ArrayList<ThirdmenuModel>>() {
                        }.getType());
                if (null != listModels && listModels.size() > 0) {
                    String packageName = listModels.get(0).getText1();
                    String activityName = listModels.get(0).getText2();
                    String downLoadUrl = listModels.get(0).getText3();
                    // 已经安装，则直接启动应用
                    if (ApkUtil.isAppInstalled(context, packageName)) {
                        if (!StringUtil.isEmpty(downLoadUrl)) {
                            String[] strs = downLoadUrl.split("/");
                            String nameDes = UrlMgr.getAppBusinessStoragePath()
                                    + strs[strs.length - 1];
                            File file = new File(nameDes);
                            if (file.exists()) {
                                file.delete();
                            }
                        }

                        Intent mIntent = new Intent();
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ComponentName comp = new ComponentName(packageName,
                                activityName);
                        mIntent.setComponent(comp);
                        mIntent.setAction("android.intent.action.VIEW");
                        context.startActivity(mIntent);
                    } else {
                        if (!StringUtil.isEmpty(downLoadUrl)) {
                            Handler managerHandler = new Handler() {

                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);
                                    switch (msg.what) {
                                        case Constant.IPTV_MSG_UPDATE_DOWNLOAD_FAILED:
                                            removeMessages(msg.what);
                                            Toast.makeText(
                                                    context,
                                                    context.getString(R.string.download_failed),
                                                    Toast.LENGTH_SHORT).show();
                                            break;
                                        case Constant.IPTV_MSG_APP_EXIST_TAG:
                                            removeMessages(msg.what);
                                            String url = msg
                                                    .getData()
                                                    .getString(
                                                            Constant.IPTV_SYS_MSG_APK_DOWNLOAD_URL,
                                                            null);
                                            boolean flag = msg
                                                    .getData()
                                                    .getBoolean(
                                                            Constant.IPTV_SYS_MSG_APK_EXIST_FLAG,
                                                            false);
                                            downloadManager.installOrDownloadApk(
                                                    url, flag);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            };
                            downloadManager = new LVBDownloadManager(context,
                                    managerHandler);
                            downloadManager.downloadAPPApk(downLoadUrl);
                        } else {
                            BaseActivity.getInstance().showToast(
                                    LVBXApp.getApp().getString(
                                            R.string.app_not_installed),
                                    Toast.LENGTH_LONG);
                        }
                    }
                } else {

                }
            }

            @Override
            public void onFailed(String result) {
                BaseActivity.getInstance().showToast(result, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 设置界面广告位图片显示
     *
     * @param activity
     * @param advType
     * @param advContainer
     */
    public void setAdvertisment(final Activity activity, final int advType,
                                final ImageView advContainer) {
        switch (advType) {
            case Constant.IPTV_ADV_INDEX_1:
                for (ViewConfigBean model : ConfigMgr.getInstance().getAllConfigs()) {
                    if (CCViewConfig.IPTV_ADV_SMARTAPP.equalsIgnoreCase(model
                            .getName())) {
                        BitmapUtil.setFadeInImage(model.getValue(), advContainer);
                    }
                }
                break;
            case Constant.IPTV_ADV_INDEX_2:
                for (ViewConfigBean model : ConfigMgr.getInstance().getAllConfigs()) {
                    if (CCViewConfig.IPTV_ADV_ROOMSERVICE.equalsIgnoreCase(model
                            .getName())) {
                        BitmapUtil.setFadeInImage(model.getValue(), advContainer);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 提交续房退房服务，然后刷新历史记录数据
     *
     * @param activity
     * @param serviceType
     * @param screenTag
     * @param roomname
     * @param userName
     * @param appointmenttime
     * @param clickCB
     */
    public void commitCheckOutInAction(final Activity activity,
                                       final int serviceType, final int screenTag, String roomname,
                                       final String userName, String appointmenttime,
                                       final IOnRoomServiceHistorySuccessCB clickCB) {
        String url = "";
        switch (serviceType) {
            case Constant.IPTV_ROOMSERVICE_TYPE_CHECK_OUT:
                url = UrlMgr.getCommitCheckOutUrl(roomname, userName,
                        appointmenttime);
                break;
            case Constant.IPTV_ROOMSERVICE_TYPE_CHECK_IN:
                url = UrlMgr.getCommitCheckInUrl(roomname, userName,
                        appointmenttime);
                break;
            default:
                break;
        }

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                if ("true".equalsIgnoreCase(result)) {
                    setRoomServiceHistoryListData(activity, screenTag, clickCB);
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 删除客房服务项数据
     *
     * @param activity
     * @param serviceId
     * @param listView
     * @param roomname
     * @param userName
     * @param appointmenttime
     * @param adapter
     * @param clickCB
     */
    public void deleteRoomServiceHistoryInfos(final Activity activity,
                                              final int screenTag, final int serviceId, String appointmenttime,
                                              final IOnRoomServiceHistorySuccessCB successCB) {
        String url = UrlMgr.getDeleteUnfinishServiceUrl(serviceId);

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                int xOffset = -(int) ((double) LVBXApp.getmScreenWidth() / 4.5) / 2;
                int yOffset = (int) ((double) LVBXApp.getmScreenHeight() / 3);
                if ("true".equalsIgnoreCase(result)) {
                    setRoomServiceHistoryListData(activity, screenTag,
                            successCB);

                    BaseActivity
                            .getInstance()
                            .showToast(
                                    activity.getString(R.string.request_success),
                                    Gravity.CENTER, xOffset, yOffset,
                                    Toast.LENGTH_LONG);
                } else {
                    BaseActivity.getInstance()
                            .showToast(
                                    activity.getString(R.string.requset_fail),
                                    Gravity.CENTER, xOffset, yOffset,
                                    Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 獲取客房服务的历史记录数据
     *
     * @param activity
     * @param userName
     * @param listView
     * @param adapter
     * @param clickCB
     */
    public void setRoomServiceHistoryListData(final Activity activity,
                                              int screenTag, final IOnRoomServiceHistorySuccessCB successCB) {
        String userName = UserMgr.getUserName();
        String url = "";
        switch (screenTag) {
            case Constant.HOTEL_CHECK_OUTIN_SCREEN:
                url = UrlMgr.getCheckInOutHistoryUrl(userName);
                break;
            case Constant.HOTEL_CLEAN_SCREEN:
                url = UrlMgr.getCleanUpHistoryUrl(userName);
                break;
            case Constant.HOTEL_WAKEUP_SCREEN:
                url = UrlMgr.getWakeUpHistoryUrl(userName);
                break;
            default:
                break;
        }

        if (StringUtil.isEmpty(url)) {
            return;
        }
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<RoomServiceModel> models = gson.fromJson(
                        result, new TypeToken<ArrayList<RoomServiceModel>>() {
                        }.getType());
                successCB.onSuccess(models);
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 请求客户退房请求标示
     *
     * @param models
     */
    public void getClientRoomRequsetType(final Activity activity,
                                         final IOnRoomStausReqCB CB) {
        String userName = UserMgr.getUserName();
        String url = UrlMgr.getCheckInOutHistoryUrl(userName);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<RoomServiceModel> models = gson.fromJson(
                        result, new TypeToken<ArrayList<RoomServiceModel>>() {
                        }.getType());

                if (null != models && models.size() > 0) {
                    int clientRoomType = models.get(0).getReqtype();
                    int clientRoomStaue = models.get(0).getReqstatus();
                    if (clientRoomType == Constant.IPTV_ROOMSERVICE_TYPE_CHECK_OUT
                            && clientRoomStaue != Constant.IPTV_ROOMSERVICE_STATUS_WAITING) {
                        BaseActivity
                                .getInstance()
                                .showToast(
                                        activity.getString(R.string.service_close_tips),
                                        Toast.LENGTH_LONG);
                    } else {
                        CB.finishCB();
                    }
                } else if (null != models && models.size() == 0) {
                    CB.finishCB();
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 提交打扫服务
     *
     * @param activity
     * @param screenTag
     * @param adapter
     * @param listView
     * @param minutes
     * @param successCB
     */
    public void commitCleanUpAction(final Activity activity,
                                    final int screenTag, int minutes,
                                    final IOnRoomServiceHistorySuccessCB successCB) {
        final String roomno = UserMgr.getHotelRoomNo();
        final String userName = UserMgr.getUserName();
        long timeStamp = System.currentTimeMillis() + minutes * 60 * 1000;
        String appointmenttime = DateTimeUtil.toTime(timeStamp,
                DateTimeUtil.DATE_FORMATE_TO_SERVER);

        String url = UrlMgr.getCommitCleanUpUrl(roomno, userName,
                appointmenttime);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                if ("true".equalsIgnoreCase(result)) {
                    setRoomServiceHistoryListData(activity, screenTag,
                            successCB);
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 提交叫醒服务
     *
     * @param activity
     * @param screenTag
     * @param adapter
     * @param listView
     * @param appointmenttime
     * @param successCB
     */
    public void commitWakeupService(final Activity activity,
                                    final int screenTag, final String appointmenttime,
                                    final IOnRoomServiceHistorySuccessCB successCB) {
        String roomname = UserMgr.getHotelName();
        String userName = UserMgr.getUserName();
        String url = UrlMgr.getCommitWakeupUrl(roomname, userName,
                appointmenttime);

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                if ("true".equalsIgnoreCase(result)) {
                    setRoomServiceHistoryListData(activity, screenTag,
                            successCB);
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 获取订餐服务我的菜谱listview数据
     *
     * @param activity
     * @param screenTag
     * @param listView
     * @param adapter
     * @param successCB
     * @param clickCB
     */
    public void setOrderMenuListData(final Activity activity, int screenTag,
                                     final ListView listView, final OrderMenuMainListAdapter adapter,
                                     final IOrderMenuListSuccessCB successCB,
                                     final IOrderMenuItemOnclickCB clickCB) {
        String url = "";
        switch (screenTag) {
            case Constant.HOTEL_ORDER_SCREEN:
            case Constant.HOTEL_TRAVEL_SPECAIL_SCREEN:
                url = UrlMgr.getOrderMainTypeUrl(screenTag);
                break;
            default:
                break;
        }
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<OrderMainMenuModel> models = gson.fromJson(
                        result, new TypeToken<ArrayList<OrderMainMenuModel>>() {
                        }.getType());
                if (models != null && models.size() > 0) {
                    successCB.setListDatas(models);
                    adapter.setDataList(models);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int arg2, long arg3) {
                            clickCB.onItemClickCB(arg2, models.get(arg2)
                                    .getId());
                        }
                    });
                    listView.setOnItemSelectedListener(new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int arg2, long arg3) {
                            clickCB.onItemClickCB(arg2, models.get(arg2)
                                    .getId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }
                    });
                    listView.requestLayout();
                    listView.requestFocus();
                    listView.setSelection(0);
                    int selectedPosition = listView.getSelectedItemPosition();
                    if (selectedPosition < 0) {
                        clickCB.onItemClickCB(0, models.get(0).getId());
                    }
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 设置订餐服务子菜单数据显示
     *
     * @param activity
     * @param screenTag
     * @param categoryId
     * @param pageNo
     * @param pageSize
     * @param horizontalContainer
     * @param bigMenuImage
     * @param successCB
     * @param clickCB
     */
    public void setOrderSubListData(final Activity activity, int screenTag,
                                    int categoryId, int pageNo, int pageSize,
                                    final int[] shopCarLocation,
                                    final LinearLayout horizontalContainer,
                                    final ImageView bigMenuImage,
                                    final VodDetailSingle vodDetailSingle,
                                    final IOrderSubMenuListSuccessCB successCB,
                                    final IOrderSubMenuItemOnclickCB clickCB) {
        String url = "";
        switch (screenTag) {
            case Constant.HOTEL_ORDER_SCREEN:
            case Constant.HOTEL_TRAVEL_SPECAIL_SCREEN:
                url = UrlMgr.getOrderSubMenuInfosUrl(screenTag, categoryId, pageNo,
                        pageSize);
                break;
            default:
                break;
        }
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final OrderSubMenuModel models = gson.fromJson(result,
                        OrderSubMenuModel.class);
                successCB.setListDatas(models);

                // 显示菜品在中间显示大图
                ArrayList<OrderSubSingleModel> lists = models.getResult();
                if (lists != null && lists.size() > 0) {
                    final int totalNum = models.getResult().size();
                    BitmapUtil.setRoundImage(models.getResult().get(0)
                            .getImageurl(), bigMenuImage);
                    vodDetailSingle.setOnClickListner(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            switch (Config.USER_ORDER_SPECIAL_ACTION) {
                                case 1:
                                    clickCB.onItemClickCB(0, models.getResult()
                                            .get(0).getId());

                                    // 获取被点击的中间大图在窗口中的坐标，并计算出动画起始坐标
                                    playCustomPathAnimation(vodDetailSingle,
                                            activity, shopCarLocation);
                                    break;
                                default:
                                    break;
                            }
                        }
                    });

                    // 显示菜品在底部显示小图列表
                    if (horizontalContainer.getChildCount() > 0) {
                        horizontalContainer.removeAllViews();
                    }
                    LayoutParams params = new LayoutParams((LVBXApp
                            .getmScreenWidth() - ((int) activity.getResources()
                            .getDimension(R.dimen.layx100) * 2)) / 5,
                            LayoutParams.MATCH_PARENT);
                    params.setMargins(0, 0, 0, 0);
                    if (null != activity) {
                        for (int i = 0; i < totalNum; i++) {

                            final int index = i;
                            final VodDetailSingle item = new VodDetailSingle(
                                    activity);
                            item.setImageValue(R.drawable.hotel_iptv_159x110);
                            item.setOnClickListner(new OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    switch (Config.USER_ORDER_SPECIAL_ACTION) {
                                        case 1:
                                            clickCB.onItemClickCB(index, models
                                                    .getResult().get(index).getId());

                                            // 获取列表中被点击项目在窗口中的坐标，并计算出动画起始坐标
                                            playCustomPathAnimation(item, activity,
                                                    shopCarLocation);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            });
                            item.setOnFocusListner(new OnFocusChangeListener() {

                                @Override
                                public void onFocusChange(View arg0,
                                                          boolean arg1) {
                                    if (arg1) {
                                        BitmapUtil.setRoundImage(models
                                                .getResult().get(index)
                                                .getImageurl(), bigMenuImage);
                                        item.setTextSelect(true);
                                        vodDetailSingle
                                                .setOnClickListner(new OnClickListener() {

                                                    @Override
                                                    public void onClick(
                                                            View arg0) {
                                                        switch (Config.USER_ORDER_SPECIAL_ACTION) {
                                                            case 1:
                                                                clickCB.onItemClickCB(
                                                                        index,
                                                                        models.getResult()
                                                                                .get(index)
                                                                                .getId());

                                                                // 获取被点击的中间大图在窗口中的坐标，并计算出动画起始坐标
                                                                playCustomPathAnimation(
                                                                        vodDetailSingle,
                                                                        activity,
                                                                        shopCarLocation);
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    }
                                                });
                                    } else {
                                        item.setTextSelect(false);
                                    }
                                }
                            });
                            item.setOnKeyListner(new OnKeyListener() {

                                @Override
                                public boolean onKey(View arg0, int arg1,
                                                     KeyEvent arg2) {
                                    switch (arg1) {
                                        case KeyEvent.KEYCODE_DPAD_LEFT:
                                            if (index == 0) {
                                                return true;
                                            }
                                            break;
                                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                                            if (index == totalNum - 1) {
                                                return true;
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                    return false;
                                }
                            });
                            item.hideNameText();
                            item.setImageValue(models.getResult().get(i)
                                    .getImageurl());
                            item.setInserLeftName(models.getResult().get(i)
                                    .getName()
                                    + "  "
                                    + "￥"
                                    + models.getResult().get(i).getPrice());
                            // item.setInsetRightName();
                            horizontalContainer.addView(item, i, params);
                        }
                    }
                } else if (lists != null && lists.size() == 0) {

                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 设置购物车上的数据进行显示
     *
     * @param activity
     * @param screenTag
     * @param username
     * @param blankTips
     * @param listView
     * @param adapter
     * @param successCB
     * @param clickCB
     */
    public void setShopCarMenuListData(final Activity activity, int screenTag,
                                       String username, final ImageView blankTips,
                                       final TextView shopCarGoodsCount,
                                       final TextView shopCarDatainfoTextView, final ListView listView,
                                       final OrderShopCarAdapter adapter, final Button orderCommitButton,
                                       final IOrderShopCarListSuccessCB successCB,
                                       final IOrderMenuItemOnclickCB clickCB) {
        String url = "";
        switch (screenTag) {
            case Constant.HOTEL_ORDER_SCREEN:
            case Constant.HOTEL_TRAVEL_SPECAIL_SCREEN:
                url = UrlMgr.getOrderShopCarInfosUrl(screenTag, username);
                break;
            default:
                break;
        }
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<OrderShopCarSingleModel> models = gson
                        .fromJson(
                                result,
                                new TypeToken<ArrayList<OrderShopCarSingleModel>>() {
                                }.getType());
                if (models != null && models.size() > 0) {
                    orderCommitButton.setVisibility(View.VISIBLE);
                    blankTips.setVisibility(View.GONE);
                    successCB.setListDatas(models);

                    // 设置数据进行显示
                    adapter.setListModels(models);
                    listView.setAdapter(adapter);
                    // 注册按键点击事件
                    listView.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int arg2, long arg3) {
                            clickCB.onItemClickCB(arg2, models.get(arg2)
                                    .getId());
                        }
                    });
                    int totalOrderNum = models.size();
                    int totalPrice = 0;
                    for (int i = 0; i < totalOrderNum; i++) {
                        totalPrice += models.get(i).getDish().getPrice();
                    }
                    String numberTips = String.format(
                            activity.getString(R.string.order_total_dish),
                            totalOrderNum);
                    String priceTips = String.format(
                            activity.getString(R.string.order_total_price),
                            totalPrice);
                    shopCarGoodsCount.setText(totalOrderNum + "");
                    shopCarDatainfoTextView.setText(numberTips + "   "
                            + priceTips);
                } else if (models != null && models.size() == 0) {
                    orderCommitButton.setVisibility(View.INVISIBLE);
                    blankTips.setVisibility(View.VISIBLE);
                    successCB.setListDatas(models);
                    // 设置数据进行显示
                    adapter.setListModels(models);
                    listView.setAdapter(adapter);

                    int totalOrderNum = models.size();
                    int totalPrice = 0;
                    for (int i = 0; i < totalOrderNum; i++) {
                        totalPrice += models.get(i).getDish().getPrice();
                    }
                    String numberTips = String.format(
                            activity.getString(R.string.order_total_dish),
                            totalOrderNum);
                    String priceTips = String.format(
                            activity.getString(R.string.order_total_price),
                            totalPrice);
                    shopCarGoodsCount.setText(totalOrderNum + "");
                    shopCarDatainfoTextView.setText(numberTips + "   "
                            + priceTips);
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 添加菜品进入购物车，然后再次获取购物车信息进行显示
     *
     * @param activity
     * @param screenTag
     * @param username
     * @param dishId
     * @param number
     * @param blankTips
     * @param listView
     * @param adapter
     * @param successCB
     * @param clickCB
     */
    public void addToShopCarAction(final Activity activity,
                                   final int screenTag, final String username, final int dishId,
                                   int number, final ImageView blankTips,
                                   final TextView shopCarGoodsCount,
                                   final TextView shopCarDatainfoTextView, final ListView listView,
                                   final OrderShopCarAdapter adapter, final Button orderCommitButton,
                                   final IOrderShopCarListSuccessCB successCB,
                                   final IOrderMenuItemOnclickCB clickCB) {
        String url = UrlMgr.getAddToShopCarActionUrl(screenTag, username,
                dishId, number);

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                if ("true".equalsIgnoreCase(result)) {
                    setShopCarMenuListData(activity, screenTag, username,
                            blankTips, shopCarGoodsCount,
                            shopCarDatainfoTextView, listView, adapter,
                            orderCommitButton, successCB, clickCB);
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 从购物车中删除某项菜品,然后重新设置购物车数据
     *
     * @param activity
     * @param screenTag
     * @param username
     * @param dishId
     * @param number
     * @param blankTips
     * @param listView
     * @param adapter
     * @param successCB
     * @param clickCB
     */
    public void deleteMenuFromShopCar(final Activity activity,
                                      final int screenTag, final String username, final int dishId,
                                      int number, final ImageView blankTips,
                                      final TextView shopCarGoodsCount,
                                      final TextView shopCarDatainfoTextView, final ListView listView,
                                      final OrderShopCarAdapter adapter, final Button orderCommitButton,
                                      final IOrderShopCarListSuccessCB successCB,
                                      final IOrderMenuItemOnclickCB clickCB) {
        String url = UrlMgr
                .getDeleteOrderSingleModelUrl(screenTag, dishId + "");

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                if ("true".equalsIgnoreCase(result)) {
                    setShopCarMenuListData(activity, screenTag, username,
                            blankTips, shopCarGoodsCount,
                            shopCarDatainfoTextView, listView, adapter,
                            orderCommitButton, successCB, clickCB);
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 提交订单接口，然后重新获取购物车数据进行购物车信息的刷新
     *
     * @param activity
     * @param screenTag
     * @param username
     * @param blankTips
     * @param listView
     * @param adapter
     * @param successCB
     * @param clickCB
     */
    public void commitOrderOfShopCarAction(final Activity activity,
                                           final int screenTag, final String username,
                                           final ImageView blankTips, final TextView shopCarGoodsCount,
                                           final TextView shopCarDatainfoTextView, final ListView listView,
                                           final OrderShopCarAdapter adapter, final Button orderCommitButton,
                                           final IOrderShopCarListSuccessCB successCB,
                                           final IOrderMenuItemOnclickCB clickCB) {
        String url = UrlMgr.getCommitShopCarActionUrl(screenTag, username);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                if ("true".equalsIgnoreCase(result)) {
                    setShopCarMenuListData(activity, screenTag, username,
                            blankTips, shopCarGoodsCount,
                            shopCarDatainfoTextView, listView, adapter,
                            orderCommitButton, successCB, clickCB);

                    BaseActivity.getInstance().showToast(
                            activity.getString(R.string.order_commit_success),
                            Toast.LENGTH_LONG);
                } else {
                    BaseActivity.getInstance().showToast(
                            activity.getString(R.string.order_commit_failed),
                            Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 设置历史订单按钮显示信息
     *
     * @param activity
     * @param typeTag
     * @param username
     * @param button
     */
    public void setOrderHistoryButtonText(final Activity activity, int typeTag,
                                          String username, final Button button) {
        String url = UrlMgr.getHistoryOrderListUrl(typeTag, username);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<OrderHistoryMainModel> models = gson.fromJson(
                        result,
                        new TypeToken<ArrayList<OrderHistoryMainModel>>() {
                        }.getType());
                if (null != models && models.size() > 0) {
                    int totalOrderCount = models.size();
                    String orderHistoryText = String.format(
                            activity.getString(R.string.order_history_count),
                            totalOrderCount);
                    button.setText(orderHistoryText);
                } else if (null == models || models.size() == 0) {
                    button.setText(R.string.order_history);
                }
            }

            @Override
            public void onFailed(String result) {
            }
        });
    }

    /**
     * 获取历史订单详细信息
     *
     * @param activity
     * @param typeTag
     * @param username
     * @param listView
     * @param adapter
     * @param clickCB
     */
    public void setOrderHistoryListData(final Activity activity, int typeTag,
                                        String username, final ListView listView,
                                        final TextView totalOrderInfos, final OrderHistoryAdapter adapter,
                                        final IOrderMenuHistoryItemOnclickCB clickCB) {
        String url = UrlMgr.getHistoryOrderListUrl(typeTag, username);

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<OrderHistoryMainModel> models = gson.fromJson(
                        result,
                        new TypeToken<ArrayList<OrderHistoryMainModel>>() {
                        }.getType());
                if (null != models && models.size() >= 0) {
                    // 设置数据进行显示
                    adapter.setListModels(models);
                    listView.setAdapter(adapter);
                    int totalOrderCount = models.size();
                    int totalOrderPrice = 0;
                    // 设置统计数据显示
                    for (int i = 0; i < totalOrderCount; i++) {
                        totalOrderPrice += models.get(i).getTotalprice();
                    }
                    String totalInfos = String.format(
                            activity.getString(R.string.order_total_count),
                            totalOrderCount)
                            + "   "
                            + String.format(activity
                                    .getString(R.string.order_total_price),
                            totalOrderPrice);
                    totalOrderInfos.setText(totalInfos);

                    // 注册按键点击事件
                    listView.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int arg2, long arg3) {
                            clickCB.onItemClickCB(arg2, models.get(arg2)
                                            .getId(), models.get(arg2).getOrdercode(),
                                    models.get(arg2).getCreationtime(), models
                                            .get(arg2).getStatus());
                        }
                    });
                } else {

                }

            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 获取历史订单某个订单号的详细信息
     *
     * @param orderNumberCode
     * @param activity
     * @param typeTag
     * @param username
     * @param listView
     * @param totalOrderInfos
     * @param orderDetailsNumberCode
     * @param adapter
     * @param clickCB
     */
    public void setOrderHistoryDetailsListData(final Activity activity,
                                               int typeTag, String username, int orderid, final ListView listView,
                                               final TextView totalOrderInfos,
                                               final TextView orderDetailsNumberCode,
                                               final String orderHistoryCode,
                                               final OrderHistoryDetailAdapter adapter, final int orderStateType,
                                               final String orderCreateTime) {
        String url = UrlMgr.getHistoryOrderModelDetailUrl(typeTag, orderid);

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<OrderShopCarSingleSubModel> models = gson
                        .fromJson(
                                result,
                                new TypeToken<ArrayList<OrderShopCarSingleSubModel>>() {
                                }.getType());

                if (null != models && models.size() >= 0) {
                    // 设置数据进行显示
                    adapter.setListModels(models);
                    adapter.setOrderState(orderStateType);
                    adapter.setCreateTime(orderCreateTime);
                    listView.setAdapter(adapter);
                    int totalOrderCount = 0;
                    int totalOrderPrice = 0;
                    // 设置统计数据显示
                    for (int i = 0; i < models.size(); i++) {
                        int pricePerMenu = models.get(i).getPrice();
                        int totalNumberPerMenu = models.get(i).getNumber();
                        totalOrderCount += totalNumberPerMenu;
                        totalOrderPrice += pricePerMenu * totalNumberPerMenu;
                    }
                    String totalInfos = String.format(
                            activity.getString(R.string.order_total_count),
                            totalOrderCount)
                            + "   "
                            + String.format(activity
                                    .getString(R.string.order_total_price),
                            totalOrderPrice);
                    totalOrderInfos.setText(totalInfos);
                    String orderCode = String.format(
                            activity.getString(R.string.order_detail_code),
                            orderHistoryCode);
                    orderDetailsNumberCode.setText(orderCode);
                    // 注册按键点击事件
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 获取订单状态
     *
     * @param context
     * @param state
     * @return
     */
    public String getOrderState(Context context, int state) {
        String statueDes = context.getString(R.string.order_state_unkonw);
        switch (state) {
            case 1:
                statueDes = context.getString(R.string.order_state_undoing);
                break;
            case 2:
                statueDes = context.getString(R.string.order_state_checked);
                break;
            case 3:
                statueDes = context.getString(R.string.order_state_sended);
                break;
            case 4:
                statueDes = context.getString(R.string.order_state_finished);
                break;
            case 5:
                statueDes = context.getString(R.string.order_state_canceled);
                break;
            default:
                break;
        }

        return statueDes;
    }

    /**
     * 获取汇率数据进行显示
     *
     * @param activity
     * @param adapter
     * @param listView
     */
    public void setRateListDatas(final Activity activity,
                                 final RateAdapter adapter, final ListView listView) {
        String url = UrlMgr.getDefaultRateInfosUrl();

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<RateModel> rateModels = gson.fromJson(result,
                        new TypeToken<ArrayList<RateModel>>() {
                        }.getType());

                if (null != rateModels && rateModels.size() >= 0) {
                    adapter.setListData(rateModels);
                    listView.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 获取指定城市指定天数的天气预报信息
     *
     * @param cityCode
     * @param weatherDay
     * @param successCB
     */
    public void setWeatherDetailInfos(String cityCode, int weatherDay,
                                      final IOnWeatherInfosSuccessCB successCB) {
        String url = UrlMgr.getWeatherDetailUrl(cityCode, weatherDay);

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<WeatherInfoModel> weatherModles = gson
                        .fromJson(result,
                                new TypeToken<ArrayList<WeatherInfoModel>>() {
                                }.getType());

                if (null != weatherModles && weatherModles.size() >= 0) {
                    successCB.success(weatherModles);
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 直播主类型获取数据接口---使用listview进行显示
     *
     * @param context
     * @param list
     * @param adapter
     */
    public void setLiveMainList(final ILiveMainListSuccessCB successCB) {
        String channelGroup = UserMgr.getChannelGroup();
        String url = UrlMgr.getMainLiveListUrl(channelGroup);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<LiveMainModel> models = gson.fromJson(result,
                        new TypeToken<ArrayList<LiveMainModel>>() {
                        }.getType());

                if (null != models && models.size() > 0) {
                    successCB.setListDatas(models);
                } else if (null != models && models.size() == 0) {
                    BaseActivity.getInstance().showToast(
                            LVBXApp.getApp().getString(R.string.live_no_item),
                            Toast.LENGTH_LONG);
                } else {
                    BaseActivity.getInstance().showToast(
                            LVBXApp.getApp().getString(
                                    R.string.request_url_error),
                            Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailed(String result) {
                BaseActivity.getInstance().showToast(
                        String.format(
                                LVBXApp.getApp().getString(
                                        R.string.request_net_failed), result),
                        Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 直播主类型获取数据接口----使用宫格进行显示
     *
     * @param context
     * @param list
     * @param adapter
     */
    public void setLiveGridShowData(final Activity fromActivity,
                                    final ILiveMainGridShowSuccessCB successCB) {
        String channelGroup = UserMgr.getChannelGroup();
        String url = UrlMgr.getMainLiveListUrl(channelGroup);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<LiveMainModel> models = gson.fromJson(result,
                        new TypeToken<ArrayList<LiveMainModel>>() {
                        }.getType());

                if (null != models && models.size() >= 0) {
                    successCB.setListDatas(models);
                } else {
                    BaseActivity.getInstance().showToast(
                            fromActivity.getString(R.string.live_no_item),
                            Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailed(String result) {
                BaseActivity.getInstance().showToast(result, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 获取直播频道列表的第一个频道进行播放
     *
     * @param fromActivity
     */
    public void playLiveChannel(final Activity fromActivity) {
        String channelGroup = UserMgr.getChannelGroup();
        String url = UrlMgr.getMainLiveListUrl(channelGroup);

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<LiveMainModel> models = gson.fromJson(result,
                        new TypeToken<ArrayList<LiveMainModel>>() {
                        }.getType());

                if (null != models && models.size() > 0) {
                    int liveIndex = getLiveIndexByChannelNumber(models);
                    int liveChannelNo = models.get(liveIndex)
                            .getChannelnumber();
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(fromActivity,
                            MediaPlayerActivity.class);
                    bundle.putInt(Constant.IPTV_MEDIA_TYPE,
                            Constant.IPTV_MEDIA_TYPE_LIVE);
                    bundle.putInt(Constant.IPTV_LIVE_CHANNEL_INDEX, liveIndex);
                    bundle.putInt(Constant.IPTV_LIVE_CHANNEL_NUMBER,
                            liveChannelNo);
                    bundle.putString(Constant.IPTV_MEDIA_URL,
                            models.get(liveIndex).getChannelurl());
                    bundle.putString(Constant.IPTV_MEDIA_NAME,
                            models.get(liveIndex).getChannelname());
                    intent.putExtra(Constant.IPTV_MEDIA_DATA_TAG, bundle);
                    intent.putExtra(Constant.IPTV_LIVE_CHANNEL_LIST, models);
                    fromActivity.startActivity(intent);
                } else if (null != models && models.size() == 0) {
                    BaseActivity.getInstance().showToast(
                            fromActivity.getString(R.string.live_no_item),
                            Toast.LENGTH_LONG);
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 通过保存的直播频道号获取频道列表索引
     *
     * @param models
     * @return
     */
    public int getLiveIndexByChannelNumber(ArrayList<LiveMainModel> models) {
        int index = 0;
        int totalCount = models.size();
        int savedChannelNumber = UserMgr.getSavedLiveChannelNumber();
        for (int i = 0; i < totalCount; i++) {
            int currentCahnnelNumber = models.get(i).getChannelnumber();
            if (savedChannelNumber == currentCahnnelNumber) {
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * 通过某个传入的直播频道号获取直播索引值
     *
     * @param channelNumber
     * @param models
     * @return
     */
    public int getLiveIndexByChannelNumber(int channelNumber,
                                           ArrayList<LiveMainModel> models) {
        int index = -1;
        int totalCount = models.size();
        for (int i = 0; i < totalCount; i++) {
            int currentCahnnelNumber = models.get(i).getChannelnumber();
            if (channelNumber == currentCahnnelNumber) {
                index = i;
                break;
            }
        }

        return index;
    }

    public void updateSubList(Activity activity, int channelId, String date,
                              int pageNo, int pageSize, final ILiveDateListSelectItemCB dateItemCB) {
        String url = UrlMgr.getSubLiveBackListUrl(channelId, date, pageNo,
                pageSize);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                LiveBackDataListModel model = gson.fromJson(result,
                        LiveBackDataListModel.class);
                ArrayList<LiveSubModel> models = model.result;
                if (models != null && models.size() >= 0) {
                    dateItemCB.onDateItemSelectCB(models);
                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 获取某个电视台的节目预告数据
     *
     * @param channelId
     * @param pageNo
     * @param pageSize
     * @param list
     * @param adapter
     */
    public void setLiveSubList(final int channelId, int pageNo, int pageSize,
                               final ILiveSubListSuccessCB successCB) {
        String url = UrlMgr.getSubLiveListUrl(channelId, pageNo, pageSize);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<LiveSubModel> models = gson.fromJson(result,
                        new TypeToken<ArrayList<LiveSubModel>>() {
                        }.getType());
                if (models != null && models.size() >= 0) {
                    successCB.setListDatas(models);
                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    public void setVodTypeData(final Activity currentActivity,
                               final IVodTypeListSuccessed onTypeSccess) {
        if (null == currentActivity) {
            LogUtil.d("currentActivity has finished!");
            return;
        }
        Intent intent = currentActivity.getIntent();
        String menuCode = intent
                .getStringExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG);
        String url = UrlMgr.getVodTypeListUrl(menuCode);
        LVBHttpUtils.get(url, new IResponseable() {

            @SuppressWarnings("unused")
            @Override
            public void onSuccess(String result) {
                if (null == currentActivity) {
                    LogUtil.d("currentActivity has finished!");
                    return;
                }
                Gson gson = new Gson();
                ArrayList<VodTypeItemModel> models = gson.fromJson(result,
                        new TypeToken<ArrayList<VodTypeItemModel>>() {
                        }.getType());
                onTypeSccess.onTypeListSuccess(models);
            }

            @SuppressWarnings("unused")
            @Override
            public void onFailed(String result) {
                if (null == currentActivity) {
                    LogUtil.d("currentActivity has finished!");
                    return;
                }
                BaseActivity.getInstance().showToast(
                        currentActivity.getString(R.string.vod_data_error),
                        Toast.LENGTH_LONG);
            }
        });
    }


    public void setVodTypeData(final Activity currentActivity, String menuCode,
                               final IVodTypeListSuccessed onTypeSccess) {
        if (null == currentActivity) {
            LogUtil.d("currentActivity has finished!");
            return;
        }
        //        Intent intent = currentActivity.getIntent();
        //        String menuCode = intent
        //                .getStringExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG);
        String url = UrlMgr.getVodTypeListUrl(menuCode);
        LVBHttpUtils.get(url, new IResponseable() {

            @SuppressWarnings("unused")
            @Override
            public void onSuccess(String result) {
                if (null == currentActivity) {
                    LogUtil.d("currentActivity has finished!");
                    return;
                }
                Gson gson = new Gson();
                ArrayList<VodTypeItemModel> models = gson.fromJson(result,
                        new TypeToken<ArrayList<VodTypeItemModel>>() {
                        }.getType());
                onTypeSccess.onTypeListSuccess(models);
            }

            @SuppressWarnings("unused")
            @Override
            public void onFailed(String result) {
                if (null == currentActivity) {
                    LogUtil.d("currentActivity has finished!");
                    return;
                }
//                BaseActivity.getInstance().showToast(
//                        currentActivity.getString(R.string.vod_data_error),
//                        Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 设置点播类型列表
     *
     * @param context
     * @param list
     * @param adapter
     */
    public void setVodTypeList(final Context context, final ListView list,
                               final VodTypeListAdapter adapter, ArrayList<VodTypeItemModel> models) {
        if (models != null && models.size() >= 0) {
            adapter.setmContext(context);
            adapter.setmList(models);
            list.setAdapter(adapter);
        } else if (models != null && models.size() == 0) {
            BaseActivity.getInstance().showToast(
                    context.getString(R.string.vod_no_item), Toast.LENGTH_LONG);
        } else {
            BaseActivity.getInstance().showToast(
                    context.getString(R.string.vod_data_error),
                    Toast.LENGTH_LONG);
        }
    }


    /**
     * 设置点播详细列表
     *
     * @param context
     * @param gridLayout
     * @param categoryId
     * @param pageNo
     * @param pageSize
     * @param upArrow
     * @param downArrow
     * @param pageShow
     * @param itemMoveable
     * @param isVodItemMove
     * @param movePosition
     * @param moveLeftable
     */
    public void setVodDetailList(final Context context,
                                 final GridLayout gridLayout, String url, final int pageNo,
                                 final int pageSize, final ImageView upArrow,
                                 final ImageView downArrow, final TextView pageShow,
                                 final IVodDetailItemable itemMoveable, final boolean isVodItemMove,
                                 final int movePosition,
                                 final IVodDetailItemMoveLeftable moveLeftable, final int width,
                                 final int height, final int columnCount, final int magrinLeftNum,
                                 final int magrinTopNum, final IVodTotalSizeAble sizeAble) {

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                VodDetailDataModel models = gson.fromJson(result,
                        new TypeToken<VodDetailDataModel>() {
                        }.getType());
                int totalPage = models.getTotalPages();
                if (null != sizeAble) {
                    sizeAble.vodTotalsized(totalPage);
                }
                if (gridLayout.getChildCount() > 0) {
                    gridLayout.removeAllViews();
                    gridLayout.requestLayout();
                }
                setPageText(pageShow, pageNo, totalPage);
                ArrayList<VodDetailItemModel> items = models.getResult();
                if (null != items && items.size() > 0) {
                    // 这个条件是为了防止出现每页数据不能完全显示的情况
                    if ((pageSize % columnCount) == 0) {
                        setVodItem(context, totalPage, items, gridLayout,
                                pageNo, pageSize, upArrow, downArrow,
                                itemMoveable, isVodItemMove, movePosition,
                                moveLeftable, width, height, columnCount,
                                magrinLeftNum, magrinTopNum);
                    }
                } else {
                    upArrow.setVisibility(View.GONE);
                    downArrow.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 设置点播详细列表
     *
     * @param context
     */
    public void setVodDetailData(final Context context, int mCategoryId, int mCurrentPageIndex, int pageSize, final IVodDetailListSuccessed vodDetailListSuccessed) {

        String url = UrlMgr.getVodDetailListUrl(mCategoryId, mCurrentPageIndex, pageSize, UserMgr.getVodTypeLeve());
        LogUtil.i("url====" + url);

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                VodDetailDataModel model = gson.fromJson(result,
                        new TypeToken<VodDetailDataModel>() {
                        }.getType());
                vodDetailListSuccessed.onDetailListSuccess(model);

            }

            @Override
            public void onFailed(String result) {
                vodDetailListSuccessed.onFail(result);

//                BaseActivity.getInstance().showToast(
//                        context.getString(R.string.vod_data_error),
//                        Toast.LENGTH_LONG);
            }
        });


    }

    private void setVodItem(Context context, int totalPage,
                            ArrayList<VodDetailItemModel> items, GridLayout gridLayout,
                            int pageNo, int pageSize, ImageView upArrow, ImageView downArrow,
                            IVodDetailItemable itemMoveable, boolean isVodItemMove,
                            int movePosition, IVodDetailItemMoveLeftable moveLeftable,
                            int width, int height, int columnCount, int magrinLeftNum,
                            int magrinTopNum) {
        gridLayout.setColumnCount(columnCount);
        if (null != context) {
            for (int i = 0; i < items.size(); i++) {
                final VodItemSingle single = new VodItemSingle(context);
                single.setTag(items.get(i));
                single.setButtonSelector(R.drawable.button_vod_selector);
                single.setImageValue(R.drawable.vod_item_162x215);
                single.setPadding(
                        context.getResources().getDimensionPixelOffset(
                                R.dimen.layx10),
                        context.getResources().getDimensionPixelOffset(
                                R.dimen.layy10),
                        context.getResources().getDimensionPixelOffset(
                                R.dimen.layx10),
                        context.getResources().getDimensionPixelOffset(
                                R.dimen.layy10));
                // 这里不使用CoordinateUtil.getX()进行计算,因为width已经是计算过的值
                single.setValues(items.get(i).getImageurl(), items.get(i)
                        .getProgramname(), i, width / columnCount, height
                        / (pageSize / columnCount));
                setVodItemListener(single, gridLayout, pageNo, pageSize,
                        totalPage, itemMoveable, moveLeftable);
                gridLayout.addView(single);
            }
        }
        /*--------------焦点的设置-----------*/
        if (isVodItemMove) {
            if (movePosition < gridLayout.getChildCount()) {
                if (movePosition < pageSize / 2) {
                    gridLayout.getChildAt(movePosition + pageSize / 2)
                            .requestLayout();
                    gridLayout.getChildAt(movePosition + pageSize / 2)
                            .requestFocus();
                } else {
                    gridLayout.getChildAt(movePosition - pageSize / 2)
                            .requestLayout();
                    gridLayout.getChildAt(movePosition - pageSize / 2)
                            .requestFocus();
                }
            } else {
                int position = movePosition - pageSize / 2;
                if (position < gridLayout.getChildCount()) {
                    gridLayout.getChildAt(position).requestLayout();
                    gridLayout.getChildAt(position).requestFocus();
                } else {
                    gridLayout.getChildAt(gridLayout.getChildCount() - 1)
                            .requestLayout();
                    gridLayout.getChildAt(gridLayout.getChildCount() - 1)
                            .requestFocus();
                }
            }
        }
        /*-------------- 箭头的设置------------*/
        if (totalPage == 1 || totalPage == 0) {
            upArrow.setVisibility(View.GONE);
            downArrow.setVisibility(View.GONE);
        } else if (pageNo <= 1) {
            upArrow.setVisibility(View.GONE);
            downArrow.setVisibility(View.VISIBLE);
        } else if (pageNo >= totalPage) {
            upArrow.setVisibility(View.VISIBLE);
            downArrow.setVisibility(View.GONE);
        } else {
            upArrow.setVisibility(View.VISIBLE);
            downArrow.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 点播item的监听
     *
     * @param single
     * @param gridLayout
     * @param pageNo
     * @param totalPage
     * @param itemMoveable
     * @param moveLeftable
     */
    private void setVodItemListener(final VodItemSingle single,
                                    final GridLayout gridLayout, final int pageNo, final int pageSize,
                                    final int totalPage, final IVodDetailItemable itemMoveable,
                                    final IVodDetailItemMoveLeftable moveLeftable) {
        single.setOnFocusListner(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    single.setTextSelect(true);
                } else {
                    single.setTextSelect(false);
                }
            }
        });

        single.setOnClickListner(new OnClickListener() {

            @Override
            public void onClick(View view) {
                VodDetailItemModel itemModel = (VodDetailItemModel) single
                        .getTag();
                itemMoveable.clickItemBtn(itemModel);
            }
        });

        single.setOnKeyListner(new OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (pageNo < totalPage) {
                                int totalPosition = gridLayout.getChildCount() - 1;
                                int currentSite = single.getPosition();
                                if (currentSite >= (pageSize / 2)
                                        && currentSite <= totalPosition) {
                                    itemMoveable.moveItemDown(currentSite,
                                            totalPage);
                                    return true;
                                }
                            } else {
                                if ((single.getPosition() + (pageSize / 2)) > (gridLayout
                                        .getChildCount() - 1)) {
                                    return true;
                                }
                            }
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            int currentPosition = single.getPosition();
                            if (currentPosition % (pageSize / 2) == 0) {
                                if (null != moveLeftable) {
                                    moveLeftable.moveItemLeft(currentPosition);
                                    return true;
                                }
                            }
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_UP:
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (pageNo > 1) {
                                int currentSite = single.getPosition();
                                if (currentSite < (pageSize / 2)) {
                                    itemMoveable.moveItemUp(currentSite);
                                    return true;
                                }
                            } else {
                                int currentSite = single.getPosition();
                                if (currentSite < (pageSize / 2)) {
                                    return true;
                                }
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 设置点播当前页数
     *
     * @param text
     * @param pageNo
     * @param totalpage
     */
    private void setPageText(TextView text, int pageNo, int totalPage) {
        if (totalPage == 0) {
            pageNo = 0;
        }
        text.setText(pageNo + "/" + totalPage);
    }

    /**
     * 设置点播视频基本信息
     *
     * @param context
     * @param item
     * @param movieName
     * @param movieNation
     * @param movieType
     * @param movieDirect
     * @param movieActor
     * @param movieIntroduce
     * @param moviePic
     */
    public void setVodDetailItemBaseMessage(Context context,
                                            VodDetailItemModel item, TextView movieName, TextView movieNation,
                                            TextView movieType, TextView movieDirect, TextView movieActor,
                                            TextView movieIntroduce, ImageView moviePic) {
        movieName.setText(item.getProgramname());
        movieNation.setText(context.getString(R.string.vod_item_nation) + "  "
                + item.getAreaname());
        movieType.setText(context.getString(R.string.vod_item_type) + "  "
                + item.getGenrenames());
        movieDirect.setText(context.getString(R.string.vod_item_direct) + "  "
                + item.getWriternames());
        movieActor.setText(context.getString(R.string.vod_item_actor) + "  "
                + item.getActornames());
        movieIntroduce.setText("    " + item.getDescription());
        String url = item.getImageurlbig();
        BitmapUtil.setFadeInImage(url, moviePic);
    }

    /**
     * 设置选集或推荐信息
     *
     * @param activity
     * @param id
     * @param view
     * @param playBtn
     */
    public void setVodDetailItemIntroduceOrSelectMessage(
            final Activity activity, final View view, final Button playBtn,
            final Button windowEntryBtn, final int categoryId,
            final String path, final VodDetailItemModel items) {
        String url = UrlMgr.getVodItemPlayMessageUrl(items.getId());
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<VodItemPlayMessageModel> models = gson
                        .fromJson(
                                result,
                                new TypeToken<ArrayList<VodItemPlayMessageModel>>() {
                                }.getType());
                if (models != null && models.size() > 0) {
                    playBtn.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            LVBMediaplayer.playVod(activity, models, models
                                            .get(0).getPlayurl(), models.get(0)
                                            .getViewname(), models.get(0).getId(),
                                    Constant.IPTV_MEDIA_TYPE_VOD, 0, items
                                            .getId(), items.getprice(), items
                                            .getProgramname(), null);
                        }
                    });
                    windowEntryBtn.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            ActivitySwitchMgr.gotoWindowControllerActivity(
                                    BaseActivity.getInstance(), models, items);
                        }
                    });
                    if (models.size() > 1) {
                        setEpisode(view, models, activity, playBtn,
                                items.getId(), items.getprice(),
                                items.getProgramname());
                    } else {
                        setVodIntroduceItem(view, activity, categoryId,
                                items.getId(), path);
                    }
                } else if (models != null && models.size() == 0) {

                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 直接进入播放页
     *
     * @param activity
     */
    public void playVodItem(
            final Activity activity, final VodDetailItemModel items) {
        String url = UrlMgr.getVodItemPlayMessageUrl(items.getId());
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<VodItemPlayMessageModel> models = gson
                        .fromJson(
                                result,
                                new TypeToken<ArrayList<VodItemPlayMessageModel>>() {
                                }.getType());
                if (models != null && models.size() > 0) {

                    LVBMediaplayer.playVod(activity, models, models
                                    .get(0).getPlayurl(), models.get(0)
                                    .getViewname(), models.get(0).getId(),
                            Constant.IPTV_MEDIA_TYPE_VOD, 0, items
                                    .getId(), items.getprice(), items
                                    .getProgramname(), null);
                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 根据单个视频id获取其子信息列表
     *
     * @param activity
     * @param id
     * @param success
     */
    public void getVodSubSingleItems(final Activity activity, int id,
                                     final IOnVodSubSingleDataSuccess success) {
        String url = UrlMgr.getVodItemPlayMessageUrl(id);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<VodItemPlayMessageModel> models = gson
                        .fromJson(
                                result,
                                new TypeToken<ArrayList<VodItemPlayMessageModel>>() {
                                }.getType());

                if (null != models && models.size() > 0) {
                    success.onSuccess(models);
                } else {
                    BaseActivity.getInstance().showToast(
                            String.format(activity
                                            .getString(R.string.request_net_failed),
                                    result), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailed(String result) {
                BaseActivity
                        .getInstance()
                        .showToast(
                                String.format(
                                        activity.getString(R.string.request_net_failed),
                                        result), Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 设置选集信息
     *
     * @param view
     * @param models
     * @param activity
     */
    private void setEpisode(View view,
                            ArrayList<VodItemPlayMessageModel> models, Activity activity,
                            Button playButton, int programeId, String price,
                            final String mediaFeeName) {
        ViewStub stub = (ViewStub) view.findViewById(R.id.vod_select_viewstub);
        stub.inflate();
        final LinearLayout episodeRangeLinearLayout = (LinearLayout) view
                .findViewById(R.id.vod_select_range_relative);
        LinearLayout episodeItem = (LinearLayout) view
                .findViewById(R.id.vod_select_detail_relative);
        int rangeNum = models.size() / Constant.VOD_NUM_PER_PAGE;
        int remainder = models.size() % Constant.VOD_NUM_PER_PAGE;

        playButton.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            episodeRangeLinearLayout.requestLayout();
                            episodeRangeLinearLayout.requestFocus();
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        if (rangeNum == 0) {
            Button episodeRange = getEpisodeRangeButton(activity, 0,
                    episodeRangeLinearLayout, episodeItem, rangeNum, remainder,
                    models, programeId, price, mediaFeeName);
            episodeRangeLinearLayout.addView(episodeRange);
        } else {
            if (remainder != 0) {
                for (int i = 0; i < rangeNum + 1; i++) {
                    Button episodeRange = getEpisodeRangeButton(activity, i,
                            episodeRangeLinearLayout, episodeItem, rangeNum,
                            remainder, models, programeId, price, mediaFeeName);
                    episodeRangeLinearLayout.addView(episodeRange);
                }
            } else {
                for (int i = 0; i < rangeNum; i++) {
                    Button episodeRange = getEpisodeRangeButton(activity, i,
                            episodeRangeLinearLayout, episodeItem, rangeNum,
                            remainder, models, programeId, price, mediaFeeName);
                    episodeRangeLinearLayout.addView(episodeRange);
                }
            }
        }
        // 初始化选集信息
        int position = 0;
        if (position < rangeNum) {
            int site = position * 10;
            for (int m = site; m < site + 10; m++) {
                Button button = getEpisodeItemButton(activity, models, m,
                        episodeRangeLinearLayout, programeId, price,
                        mediaFeeName);
                episodeItem.addView(button);
            }
        } else {
            for (int m = rangeNum * 10; m < rangeNum * 10 + remainder; m++) {
                Button button = getEpisodeItemButton(activity, models, m,
                        episodeRangeLinearLayout, programeId, price,
                        mediaFeeName);
                episodeItem.addView(button);
            }
        }
    }

    /**
     * 获取选集button
     *
     * @param activity
     * @param position
     * @param linearLayout
     * @param rangeNum
     * @param remainder
     * @param models
     * @return
     */
    private Button getEpisodeRangeButton(final Activity activity, int position,
                                         final LinearLayout rangeLinearlayout,
                                         final LinearLayout linearLayout, final int rangeNum,
                                         final int remainder,
                                         final ArrayList<VodItemPlayMessageModel> models,
                                         final int programeId, final String price, final String mediaFeeName) {
        Button button = new Button(activity);
        button.setBackgroundResource(R.drawable.vod_episode_selector);
        button.setTextColor(activity.getResources().getColor(R.color.white));
        button.setTextSize((int) activity.getResources().getDimension(
                R.dimen.layx15));
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        button.setTag(position);
        if (position == 0) {
            button.setText("01-10");
        } else {
            button.setText((position * 10 + 1) + "-" + (position + 1) * 10);
        }

        button.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus) {
                    int position = (Integer) view.getTag();
                    mEpisodePosition = position;
                    if (linearLayout.getChildCount() > 0) {
                        linearLayout.removeAllViews();
                    }
                    if (position < rangeNum) {
                        int site = position * 10;
                        for (int m = site; m < site + 10; m++) {
                            Button button = getEpisodeItemButton(activity,
                                    models, m, rangeLinearlayout, programeId,
                                    price, mediaFeeName);
                            linearLayout.addView(button);
                        }
                    } else {
                        for (int m = rangeNum * 10; m < rangeNum * 10
                                + remainder; m++) {
                            final Button button = getEpisodeItemButton(
                                    activity, models, m, rangeLinearlayout,
                                    programeId, price, mediaFeeName);
                            linearLayout.addView(button);
                        }
                    }
                }
            }
        });

        button.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            linearLayout.getChildAt(0).requestLayout();
                            linearLayout.getChildAt(0).requestFocus();
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        return button;
    }

    /**
     * 获取单集button
     *
     * @param activity
     * @param model
     * @param positon
     * @return
     */
    private Button getEpisodeItemButton(final Activity activity,
                                        final ArrayList<VodItemPlayMessageModel> models, final int positon,
                                        final LinearLayout linear, final int programeId,
                                        final String price, final String mediaFeeName) {
        final Button button = new Button(activity);
        button.setBackgroundResource(R.drawable.button_selector);
        button.setTextColor(activity.getResources().getColor(R.color.white));
        button.setTextSize((int) activity.getResources().getDimension(
                R.dimen.layx15));
        LayoutParams params = new LayoutParams((int) activity.getResources()
                .getDimension(R.dimen.layx90), (int) activity.getResources()
                .getDimension(R.dimen.layx60));
        button.setLayoutParams(params);
        VodItemPlayMessageModel item = models.get(positon);
        if (positon < (Constant.VOD_NUM_PER_PAGE - 1)) {
            button.setText("" + 0 + (positon + 1));
        } else {
            button.setText((positon + 1) + "");
        }
        button.setTag(item);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sendVodPushCmd(activity, models,
                        (VodItemPlayMessageModel) button.getTag(),
                        Constant.IPTV_MEDIA_TYPE_VOD, positon, programeId,
                        price, mediaFeeName);
            }
        });
        button.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_UP:
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            linear.getChildAt(mEpisodePosition).requestLayout();
                            linear.getChildAt(mEpisodePosition).requestFocus();
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        return button;
    }

    /**
     * 设置推荐电影
     *
     * @param view
     * @param context
     */
    private void setVodIntroduceItem(final View view, final Activity activity,
                                     final int categoryId, final int id, final String path) {
        String url = UrlMgr.getVodIntroduceItemUrl(categoryId,
                UserMgr.getVodTypeLeve());
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArrayList<VodDetailItemModel> items = gson.fromJson(result,
                        new TypeToken<ArrayList<VodDetailItemModel>>() {
                        }.getType());
                if (items != null && items.size() > 0) {
                    ViewStub stub = (ViewStub) view
                            .findViewById(R.id.vod_introduce_viewstub);
                    stub.inflate();
                    LinearLayout linear = (LinearLayout) view
                            .findViewById(R.id.vod_sub_introduce_linear);
                    if (items != null && items.size() > 0) {
                        if (null != activity) {

                            for (int i = 0; i < items.size(); i++) {
                                if (items.get(i).getId() != id) {
                                    final VodItemSingle single = new VodItemSingle(
                                            activity);
                                    single.setImageValue(R.drawable.vod_item_140x202);
                                    single.setTag(items.get(i));
                                    single.setButtonSelector(R.drawable.button_vod_selector);
                                    single.setImageValue(R.drawable.vod_item_162x215);
                                    single.setPadding(
                                            activity.getResources()
                                                    .getDimensionPixelOffset(
                                                            R.dimen.layx10),
                                            activity.getResources()
                                                    .getDimensionPixelOffset(
                                                            R.dimen.layy10),
                                            activity.getResources()
                                                    .getDimensionPixelOffset(
                                                            R.dimen.layx10),
                                            activity.getResources()
                                                    .getDimensionPixelOffset(
                                                            R.dimen.layy10));
                                    single.setValues(
                                            items.get(i).getImageurl(),
                                            items.get(i).getProgramname(),
                                            i,
                                            (int) activity.getResources()
                                                    .getDimension(
                                                            R.dimen.layx150),
                                            (int) activity.getResources()
                                                    .getDimension(
                                                            R.dimen.layx220));

                                    single.setOnFocusListner(new OnFocusChangeListener() {

                                        @Override
                                        public void onFocusChange(View view,
                                                                  boolean hasFocus) {
                                            if (hasFocus) {
                                                single.setTextSelect(true);
                                            } else {
                                                single.setTextSelect(false);
                                            }
                                        }
                                    });

                                    single.setOnClickListner(new OnClickListener() {

                                        @Override
                                        public void onClick(View view) {
                                            VodDetailItemModel itemModel = (VodDetailItemModel) single
                                                    .getTag();
                                            ActivitySwitchMgr
                                                    .gotoVodItemDetailActivity(
                                                            activity,
                                                            itemModel,
                                                            categoryId, path);
                                            activity.finish();
                                        }
                                    });
                                    linear.addView(single);
                                }
                            }

                        }
                    } else {

                    }
                } else if (items != null && items.size() == 0) {

                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 获取城市设置信息
     *
     * @param activity
     * @param grid
     * @param cityName
     */
    public void getCitySettingMessage(final Activity activity,
                                      final GridView grid, final TextView cityNameTextView,
                                      final boolean needSaveTag, final String passedCityName) {
        String url = UrlMgr.getCitySettingUrl();
        if (needSaveTag) {
            cityNameTextView.setText(UserMgr.getWeatherCityName());
        } else {
            cityNameTextView.setText(passedCityName);
        }
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<CityItemModel> models = gson.fromJson(result,
                        new TypeToken<ArrayList<CityItemModel>>() {
                        }.getType());
                if (null != models && models.size() > 0) {
                    CityGridViewAdapter adapter = new CityGridViewAdapter(
                            activity, models);
                    grid.setAdapter(adapter);

                    grid.requestLayout();
                    grid.requestFocus();

                    grid.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int arg2, long arg3) {
                            String cityName = models.get(arg2).getCityname();
                            String cityAirCode = models.get(arg2)
                                    .getCityaircode();
                            String cityNumCode = models.get(arg2)
                                    .getCityweathercode();
                            cityNameTextView.setText(cityName);

                            if (needSaveTag) {
                                // 保存城市编码和城市名称
                                UserMgr.setWeatherCitySettedFlag(true);
                                UserMgr.setWeatherCityName(cityName);
                                UserMgr.setWeatherAirCode(cityAirCode);
                                UserMgr.setWeatherNumCode(cityNumCode);
                            }

                            Intent intent = new Intent();
                            intent.putExtra(Constant.IPTV_USER_HOTEL_CITY_NAME,
                                    cityName);
                            intent.putExtra(
                                    Constant.IPTV_USER_HOTEL_CITY_NUM_CODE,
                                    cityNumCode);
                            activity.setResult(
                                    Constant.SETTING_CITYSETTING_SCREEN, intent);
                            activity.finish();
                        }
                    });
                } else if (null != models && models.size() == 0) {

                } else {

                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 获取消息推送服务器地址、端口,然后进行回调处理
     *
     * @param onSuucess
     */
    public void openMsgPushService() {
        String url = UrlMgr.getMsgServerInfoUrl();
        LogUtil.d("openMsgPushService-----" + url);
        // http://218.17.13.108:9999/epgjy/json/bulletin/serveraddress?groupId=3&account=10005
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MsgSocketServerInfo msgServerInfo = gson.fromJson(result,
                        MsgSocketServerInfo.class);
                // SystemMgr.setMsgServerIp("218.17.13.108");
                // SystemMgr.setMsgServerPort(10000);
                SystemMgr.setMsgServerIp(msgServerInfo.getIp());
                SystemMgr.setMsgServerPort(msgServerInfo.getPort());
                BaseActivity.getInstance().openMsgReceiveService();
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 获取所有的未读消息
     *
     * @param iptvUserName
     * @param pageNo
     * @param pageSize
     * @param onSuucess
     */
    public void getAllPulledNewMsg(String iptvUserName, int pageNo,
                                   int pageSize, INewOnSuccessCB onSuucess) {
        String url = UrlMgr.getAllNewsPulledInfoUrl(iptvUserName, pageNo,
                pageSize);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 获取未读信息
     *
     * @param iptvUserName
     * @param pageNo
     * @param pageSize
     * @param onSuucess
     */
    public void getUnreadNewsMsg(String iptvUserName, int pageNo, int pageSize,
                                 final INewOnSuccessCB onSuucess) {
        String url = UrlMgr.getUnreadNewsInfosUrl(iptvUserName, pageNo,
                pageSize);
        LogUtil.d("getUnreadNewsMsg----------" + url);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                LogUtil.d("getUnreadNewsMsg-----result= " + result);
                Gson gson = new Gson();
                NewsDataItems newsDataItems = gson.fromJson(result,
                        NewsDataItems.class);

                onSuucess.onSuccess(newsDataItems);
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 设置某条信息为已读状态
     *
     * @param iptvUserName
     * @param bulletinid
     * @param onSuucess
     */
    public void setNewsReadedTag(String iptvUserName, int bulletinid,
                                 INewOnSuccessCB onSuucess) {
        String url = UrlMgr.getNewsReadedTag(iptvUserName, bulletinid);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 获取所有未读消息数目
     *
     * @param iptvUserName
     * @param onSuucess
     */
    public void getUnReadedMsgNum(String iptvUserName, INewOnSuccessCB onSuucess) {
        String url = UrlMgr.getUnReadedMsgNumUrl(iptvUserName);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 一级菜单主调接口函数
     *
     * @param CB
     */
    public void setMainMenuHoriontalScroviewData(
            final IOnMainMenuDataSuccessCB CB) {
        String url = UrlMgr.getMainmenuUrl();

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArrayList<MainmenuModel> items = gson.fromJson(result,
                        new TypeToken<ArrayList<MainmenuModel>>() {
                        }.getType());

                CB.onDataSuccessCB(items);
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    /**
     * 设置快捷键属性值
     *
     * @param model
     */
    public void setOneKeyMenuValues(MainmenuModel model) {
        if (null != model) {
            String templateCode = model.getTemplatecode();
            if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_LIVE
                    .equalsIgnoreCase(templateCode)) {
                UserMgr.setLiveMenuName(model.getName());
            }
            //			else if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_VOD
            //					.equalsIgnoreCase(templateCode)
            //					&& Constant.IPTV_DEFAULT_VOD_MENUCODE.equals(model
            //							.getCode())) {
            //				UserMgr.setVodMenuName(model.getName());
            //			}
            else if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_VOD
                    .equalsIgnoreCase(templateCode)) {
                UserMgr.setVodMenuName(model.getName());
                UserMgr.setVodMenuCode(model.getCode());


            } else if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_HS
                    .equalsIgnoreCase(templateCode)) {
                UserMgr.setHSMenuId(model.getId());
                UserMgr.setHSMenuName(model.getName());
                UserMgr.setHSMenuCode(model.getCode());
            } else if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_TRVEL
                    .equalsIgnoreCase(templateCode)) {
                UserMgr.setTravelMenuId(model.getId());
                UserMgr.setTravelMenuName(model.getName());
            } else if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_APP
                    .equalsIgnoreCase(templateCode)) {
                UserMgr.setAppMenuId(model.getId());
                UserMgr.setAppMenuName(model.getName());
            } else if (!StringUtil.isEmpty(templateCode)
                    && templateCode
                    .contains(CCTemplateConfig.IPTV_LVB_X_TEMPLATE_THIRD_MARKET_APP_ENTRY)) {
                UserMgr.setThirdAppMenuCode(model.getCode());
                UserMgr.setThirdAppMenuName(model.getName());
            }
        }
    }

    /**
     * 一级菜单主调接口函数
     *
     * @param CB
     * @param id
     */
    public void setSecondHorizontalScroviewData(int id,
                                                final IOnMainMenuDataSuccessCB CB) {
        String url = UrlMgr.getSubMenuUrl(id);

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArrayList<MainmenuModel> items = gson.fromJson(result,
                        new TypeToken<ArrayList<MainmenuModel>>() {
                        }.getType());

                CB.onDataSuccessCB(items);
            }

            @Override
            public void onFailed(String result) {
                BaseActivity.getInstance().showToast(result, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 账单查询
     *
     * @param activity
     * @param listView
     * @param totalBillInfos
     */
    public void setBillDataValues(final Activity activity,
                                  final ListView listView, final TextView totalBillInfos) {
        String url = UrlMgr.getBillCheckUrl(UserMgr.getHotelRoomNo());

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArrayList<BillModel> items = gson.fromJson(result,
                        new TypeToken<ArrayList<BillModel>>() {
                        }.getType());

                int totalConsumer = items.size();
                if (null != items && totalConsumer >= 0) {
                    int totalPrice = 0;
                    for (int i = 0; i < totalConsumer; i++) {
                        totalPrice += Float.valueOf(items.get(i).getFee());
                    }
                    String totalInfosString = String.format(
                            activity.getString(R.string.consume_total_number),
                            totalConsumer)
                            + String.format(activity
                                    .getString(R.string.consume_total_price),
                            totalPrice);
                    totalBillInfos.setText(totalInfosString);
                    BillListAdapter adapter = new BillListAdapter(activity);
                    adapter.setListModels(items);
                    listView.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void onFailed(String result) {
                BaseActivity.getInstance().showToast(result, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 检测是否需要付费
     *
     * @param roomNumber
     * @param programId
     * @param CB
     */
    public void checkIsVodFree(String roomNumber, int programId,
                               final IPaymentCheckCB CB) {
        String url = UrlMgr.checkIsHadPayedUrl(roomNumber, programId);

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                // 未购买
                if (result != null && result.equalsIgnoreCase("false")) {
                    CB.onNeedPay();
                }
            }

            @Override
            public void onFailed(String result) {
                BaseActivity.getInstance().showToast(result, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 抛账请求
     *
     * @param roomNumber
     * @param price
     * @param quantity
     * @param remark
     * @param CB
     */
    public void throwVodBillAction(final Activity activity, String roomNumber,
                                   String price, int quantity, int programid, String remark,
                                   final IPaymentCheckCB CB) {
        String url = UrlMgr.getThrowAccountUrl(roomNumber, price, quantity,
                programid, remark);

        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ThrowBillResult throwBillResult = gson.fromJson(result,
                        ThrowBillResult.class);

                if (null != throwBillResult
                        && "true".equalsIgnoreCase(throwBillResult.getResult())) {
                    CB.onNeedPay();
                } else {
                    activity.finish();
                }
                BaseActivity.getInstance().showToast(throwBillResult.getDesc(),
                        Toast.LENGTH_LONG);
            }

            @Override
            public void onFailed(String result) {
                activity.finish();
                BaseActivity.getInstance().showToast(result, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 开启多屏互动接收线程
     */
    public void openInteractiveService() {
        InteractiveMgr.getInstance().init();
    }

    /**
     * 关闭多屏互动接收线程
     */
    public void closeInteractiveService() {
        InteractiveMgr.getInstance().deInit();
    }

    /**
     * 移动端发送配对请求
     *
     * @param resultModel
     */
    public void sendPairedAuthorithCmd(final Activity activity,
                                       LoginInfoModel loginModel, final IOnPairedSuccessable success) {
        ThreadPoolManager.getInstance().addTask(new Runnable() {

            @Override
            public void run() {
                // 1999#15754#99065918#192.168.1.68#78:F7:BE:52:61:2A(cmdType#accountName#interactivePasswd#localIpAdress#localMacAddress)
                String contents = RemoteCMDType.IPTV_PAIRE_CMD + "#"
                        + UserMgr.getUserName() + "#"
                        + UserMgr.getInteracPassword() + "#"
                        + CommonUtil.getLocalIpAddress() + "#"
                        + CommonUtil.getLocalMacAddress(LVBXApp.getApp());
                String ipAdress = UserMgr.getPairedIp();
                InteractiveMgr.getInstance().sendInteractiveRequest(ipAdress,
                        contents);
                success.success();
            }
        });
    }

    /**
     * 移动端发送直播push命令
     *
     * @param activity
     * @param gridViewModels
     * @param position
     */
    public void sendLivePushCmd(final Activity activity,
                                final ArrayList<LiveMainModel> gridViewModels, final int position,
                                final int channelNum) {
        ThreadPoolManager.getInstance().addTask(new Runnable() {

            @Override
            public void run() {
                // 2000#15754#99065918#192.168.43.249#78:F7:BE:52:61:2A#udp://236.0.0.3:1236#CCTV15#15
                String contents = RemoteCMDType.MEDIA_LIVE_PUSH + "#"
                        + UserMgr.getUserName() + "#"
                        + UserMgr.getInteracPassword() + "#"
                        + CommonUtil.getLocalIpAddress() + "#"
                        + CommonUtil.getLocalMacAddress(LVBXApp.getApp()) + "#"
                        + gridViewModels.get(position).getChannelurl() + "#"
                        + gridViewModels.get(position).getChannelname() + "#"
                        + position + "#" + channelNum;
                String ipAdress = UserMgr.getPairedIp();
                InteractiveMgr.getInstance().sendInteractiveRequest(ipAdress,
                        contents);
            }
        });
    }

    /**
     * 移动端发送点播点播push命令
     *
     * @param activity
     * @param models
     * @param items
     * @param mediaType
     * @param position
     * @param programeId
     * @param price
     * @param mediaFeeName
     */
    public void sendVodPushCmd(Activity activity,
                               ArrayList<VodItemPlayMessageModel> models,
                               final VodItemPlayMessageModel items, int mediaType,
                               final int position, final int programeId, final String price,
                               final String mediaFeeName) {
        switch (Config.LvbDeviceType) {
            case Constant.DEVICE_TYPE_MOBILE_HSJQ:
                UserMgr.setVodSavedIndex(position);
                String pairedName = UserMgr.getUserName();
                if (StringUtil.isEmpty(pairedName)) {
                    // 进行配对提示
                    ActivitySwitchMgr.gotoPairTipsActivity(activity,
                            activity.getString(R.string.interactive_seeting));
                } else {
                    // 发送按键模拟操作指令
                    ThreadPoolManager.getInstance().addTask(new Runnable() {

                        @Override
                        public void run() {
                            // 2001#15754#99065918#192.168.43.249#78:F7:BE:52:61:2A#http://192.168.1.247:9999/vod/催眠大师MKV.mkv#MKV（转码）#0#191#null#MKV格式测试
                            String contents = RemoteCMDType.MEDIA_VOD_PUSH
                                    + "#"
                                    + UserMgr.getUserName()
                                    + "#"
                                    + UserMgr.getInteracPassword()
                                    + "#"
                                    + CommonUtil.getLocalIpAddress()
                                    + "#"
                                    + CommonUtil.getLocalMacAddress(LVBXApp
                                    .getApp()) + "#" + items.getPlayurl()
                                    + "#" + items.getViewname() + "#" + position
                                    + "#" + programeId + "#" + price + "#"
                                    + mediaFeeName;
                            String ipAdress = UserMgr.getPairedIp();
                            InteractiveMgr.getInstance().sendInteractiveRequest(
                                    ipAdress, contents);
                        }
                    });
                }
                break;
            default:
                LVBMediaplayer.playVod(activity, models, items.getPlayurl(),
                        items.getViewname(), items.getId(),
                        Constant.IPTV_MEDIA_TYPE_VOD, position, programeId, price,
                        mediaFeeName, null);
                break;
        }
    }

    public void sendVodPreNextCmd(Activity activity,
                                  ArrayList<VodItemPlayMessageModel> models,
                                  final VodItemPlayMessageModel items, int mediaType,
                                  final int position, final int programeId, final String price,
                                  final String mediaFeeName, final int cmd) {
        UserMgr.setVodSavedIndex(position);
        String pairedName = UserMgr.getUserName();
        if (StringUtil.isEmpty(pairedName)) {
            // 进行配对提示
            ActivitySwitchMgr.gotoPairTipsActivity(activity,
                    activity.getString(R.string.interactive_seeting));
        } else {
            // 发送按键模拟操作指令
            ThreadPoolManager.getInstance().addTask(new Runnable() {

                @Override
                public void run() {
                    // 2001#15754#99065918#192.168.43.249#78:F7:BE:52:61:2A#http://192.168.1.247:9999/vod/催眠大师MKV.mkv#MKV（转码）#0#191#null#MKV格式测试
                    String contents = cmd + "#" + UserMgr.getUserName() + "#"
                            + UserMgr.getInteracPassword() + "#"
                            + CommonUtil.getLocalIpAddress() + "#"
                            + CommonUtil.getLocalMacAddress(LVBXApp.getApp())
                            + "#" + items.getPlayurl() + "#"
                            + items.getViewname() + "#" + position + "#"
                            + programeId + "#" + price + "#" + mediaFeeName;
                    String ipAdress = UserMgr.getPairedIp();
                    InteractiveMgr.getInstance().sendInteractiveRequest(
                            ipAdress, contents);
                }
            });
        }
    }

    /**
     * 多屏互动里面拖拉控制(由移动端向盒子端发送拖拽指令)
     *
     * @param activity
     * @param items
     * @param position
     * @param programeId
     * @param price
     * @param mediaFeeName
     */
    public void sendVodSeekTragControllerCmd(Activity activity,
                                             final VodItemPlayMessageModel items, final int position,
                                             final int programeId, final String price,
                                             final String mediaFeeName, final int progress) {
        UserMgr.setVodSavedIndex(position);
        String pairedName = UserMgr.getUserName();
        if (StringUtil.isEmpty(pairedName)) {
            // 进行配对提示
            ActivitySwitchMgr.gotoPairTipsActivity(activity,
                    activity.getString(R.string.interactive_seeting));
        } else {
            ThreadPoolManager.getInstance().addTask(new Runnable() {

                @Override
                public void run() {
                    String contents = RemoteCMDType.VOD_MEDIA_SEEK + "#"
                            + UserMgr.getUserName() + "#"
                            + UserMgr.getInteracPassword() + "#"
                            + CommonUtil.getLocalIpAddress() + "#"
                            + CommonUtil.getLocalMacAddress(LVBXApp.getApp())
                            + "#" + items.getPlayurl() + "#"
                            + items.getViewname() + "#" + position + "#"
                            + programeId + "#" + price + "#" + mediaFeeName
                            + "#" + progress;
                    String ipAdress = UserMgr.getPairedIp();
                    InteractiveMgr.getInstance().sendInteractiveRequest(
                            ipAdress, contents);
                }
            });
        }
    }

    /**
     * 发送点播播放器控制动作
     *
     * @param actionType
     */
    public void sendVodControllerCmd(int actionType) {
        switch (actionType) {
            case Constant.IPTV_MEDIAPLAYER_STATE_PLAYING:
                sendVodPlayPauseCmd(RemoteCMDType.VOD_MEDIA_PLAY);
                break;
            case Constant.IPTV_MEDIAPLAYER_STATE_PAUSING:
                sendVodPlayPauseCmd(RemoteCMDType.VOD_MEDIA_PAUSE);
                break;
            default:
                break;
        }
    }

    /**
     * 发送点播播放器播放、暂停操作指令
     *
     * @param cmd
     */
    public void sendVodPlayPauseCmd(final int cmd) {
        ThreadPoolManager.getInstance().addTask(new Runnable() {

            @Override
            public void run() {
                // 2007#15754#99065918#192.168.43.249#78:F7:BE:52:61:2A
                String contents = cmd + "#" + UserMgr.getUserName() + "#"
                        + UserMgr.getInteracPassword() + "#"
                        + CommonUtil.getLocalIpAddress() + "#"
                        + CommonUtil.getLocalMacAddress(LVBXApp.getApp());
                String ipAdress = UserMgr.getPairedIp();
                InteractiveMgr.getInstance().sendInteractiveRequest(ipAdress,
                        contents);
            }
        });
    }

    /**
     * 发送播放器控制指令
     *
     * @param cmd
     */
    public void sendVodControllerBroadcast(int cmd) {
        Intent intent = new Intent();
        intent.putExtra(Constant.IPTV_MEDIAPLAYER_CONTROLLER_MSG, cmd);
        intent.setAction(Constant.IPTV_LVB_X_BROADCAST_MSG_MEDIAPLAYER_STATE_CHANGE);
        LVBXApp.getApp().sendBroadcast(intent);
    }

    /**
     * 发送播放器拖拽控制指令
     *
     * @param cmd
     */
    public void sendVodSeekBroadcast(int cmd, String progress) {
        Intent intent = new Intent();
        intent.putExtra(Constant.IPTV_MEDIAPLAYER_CONTROLLER_MSG, cmd);
        intent.putExtra(Constant.IPTV_VOD_REMOTE_SEEK_MSG, progress);
        intent.setAction(Constant.IPTV_LVB_X_BROADCAST_MSG_MEDIAPLAYER_STATE_CHANGE);

        LVBXApp.getApp().sendBroadcast(intent);
    }

    /**
     * BOX端发送接收到配对授权广播
     *
     * @param msg
     */
    public void sendPairTipsBroadcast(Message msg) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.IPTV_KEY_PAIR_AUTHORIZE_MSG, msg.what);
        bundle.putParcelable(Constant.IPTV_VALUE_PAIR_AUTHORIZE_MSG,
                (PairAuthorizationModel) msg.obj);
        intent.putExtras(bundle);
        intent.setAction(Constant.IPTV_LVB_X_BROADCAST_MSG_PAIR_AUTHORITH);
        LVBXApp.getApp().sendBroadcast(intent);
    }

    /**
     * 皇室假期发送选座广播
     */
    public void sendSeatSelectionBroadcast() {
        // wujichang 修改保证Mediaplayer在播放直播和点播时的单一性
        MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
        if (null != mediaActivity && !mediaActivity.isFinishing()) {
            if (mediaActivity.getCurrentMediaType() == Constant.IPTV_MEDIA_TYPE_LIVE) {
                mediaActivity.finish();
            }
        }
        Intent intent = new Intent();
        intent.setAction(Constant.IPTV_LVB_X_BROADCAST_MSG_HSJQ_SEAT);
        LVBXApp.getApp().sendBroadcast(intent);
    }

    /**
     * 皇室假期取消留座广播
     */
    public void sendSeatCancelBroadcast() {
        Intent intent = new Intent();
        intent.setAction(Constant.IPTV_LVB_X_BROADCAST_MSG_HSJQ_SEAT_CANCEL);
        LVBXApp.getApp().sendBroadcast(intent);
    }

    /**
     * 开启互控服务功能
     */
    public void openCtrollerService() {
        // 设备标示是否开启多屏互动服务
        switch (Config.LvbDeviceType) {
            case Constant.DEVICE_TYPE_MOBILE:
            case Constant.DEVICE_TYPE_MOBILE_HSJQ:
            case Constant.DEVICE_TYPE_BOX_HSJQ:
                // 开启多屏互动接收线程
                openInteractiveService();
                return;
            default:
                break;
        }
    }

    /**
     * 关闭互控服务功能
     */
    public void closeControllerService() {
        // 设备标示是否关闭多屏互动服务
        switch (Config.LvbDeviceType) {
            case Constant.DEVICE_TYPE_MOBILE:
            case Constant.DEVICE_TYPE_MOBILE_HSJQ:
            case Constant.DEVICE_TYPE_BOX_HSJQ:
                // 关闭多屏互动接收线程
                closeInteractiveService();
                return;
            default:
                break;
        }
    }

    private void playCustomPathAnimation(View view, Activity activity,
                                         int[] shopCarLocation) {
        int[] itemLocation = new int[2];
        view.getLocationInWindow(itemLocation);
        int itemWidth = view.getWidth();
        itemLocation[0] += itemWidth / 2;
        CustomPathAnimation.start(activity, itemLocation, shopCarLocation);
    }

    public void playCustomPathAnimation(Activity activity, int[] startLocation,
                                        int[] shopCarLocation) {
        CustomPathAnimation.start(activity, startLocation, shopCarLocation);
    }

    // 播放背景音乐
    public void startPlayBackgroundMusic(Application app) {
        switch (Config.LvbDeviceType) {
            case Constant.DEVICE_TYPE_MOBILE:
            case Constant.DEVICE_TYPE_MOBILE_HSJQ:
                break;
            default:
                LVBMusicBgMediaPlayer.getInstance().startBgMusic(app);
                break;
        }
    }

    // 继续播放背景音乐
    public void continuePlayBackgroundMusic() {
        switch (Config.LvbDeviceType) {
            case Constant.DEVICE_TYPE_MOBILE:
            case Constant.DEVICE_TYPE_MOBILE_HSJQ:
                break;
            default:
                LVBMusicBgMediaPlayer.getInstance().continuePlayBgMusic();
                break;
        }
    }

    public void checkForcePlayMission() {
        String url = UrlMgr.getForcePlayMissionUrl();
        LogUtil.d("checkForcePlayMission------------" + url);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                if (!StringUtil.isEmpty(result)) {
                    Gson gson = new Gson();
                    MsgResultBean msgResultBean = gson.fromJson(result,
                            MsgResultBean.class);
                    if (msgResultBean.isResult()) {
                        Gson gsonsub = new Gson();
                        ForcePlayBean forceBean = gsonsub.fromJson(
                                msgResultBean.getDesc(), ForcePlayBean.class);
                        MediaControllerMgr.getInstance().startTicker(forceBean);
                    } else {
                        // 解锁媒体播放器动作
                        SystemMgr.setMediaLockTag(false);
                        ActivitySwitchMgr.closeLiveVodPlayer();
                    }
                }
            }

            @Override
            public void onFailed(String result) {
                System.out
                        .println("wujichang-->checkForcePlayMission-->error!");
            }
        });
    }

    /**
     * 获取新闻资讯分类
     *
     * @param activity
     * @param success
     */
    public void getPrisonInfoCategory(final Activity activity,
                                      final IListOnSuccessCB<PrisonInfoCategoryModel> success, int count) {
        String url = UrlMgr.getPrisonInfoCategoryUrl(count);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<PrisonInfoCategoryModel> models = gson
                        .fromJson(
                                result,
                                new TypeToken<ArrayList<PrisonInfoCategoryModel>>() {
                                }.getType());

                if (null != models && models.size() > 0) {
                    success.onSuccess(models);
                } else {
                    BaseActivity.getInstance().showToast(
                            String.format(activity
                                            .getString(R.string.request_net_failed),
                                    result), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailed(String result) {
                BaseActivity
                        .getInstance()
                        .showToast(
                                String.format(
                                        activity.getString(R.string.request_net_failed),
                                        result), Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 监区网站分类
     *
     * @param activity
     * @param success
     * @param count
     */
    public void getPrisonNewsWebsitCategory(final Activity activity,
                                            final IListOnSuccessCB<PrisonWebsitCategoryModel> success, int count) {
        String url = UrlMgr.getPrisonWebsitCategoryUrl(count);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<PrisonWebsitCategoryModel> models = gson
                        .fromJson(
                                result,
                                new TypeToken<ArrayList<PrisonWebsitCategoryModel>>() {
                                }.getType());

                if (null != models && models.size() > 0) {
                    success.onSuccess(models);
                } else {
                    BaseActivity.getInstance().showToast(
                            String.format(activity
                                            .getString(R.string.request_net_failed),
                                    result), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailed(String result) {
                BaseActivity
                        .getInstance()
                        .showToast(
                                String.format(
                                        activity.getString(R.string.request_net_failed),
                                        result), Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 获取新闻资讯内容
     *
     * @param activity
     * @param categoryId 分类id
     * @param count      条
     * @param notinIds   不在id中例如:2,3,4
     * @param type       (1普通 2图文3视频 )
     * @param success
     */
    public void getPrisonNewsFind(final Activity activity, int categoryId,
                                  int count, int notinIds, int type,
                                  final IListOnSuccessCB<PrsionInfoModel> success) {
        String url = UrlMgr
                .getPrisonNewsFind(categoryId, count, notinIds, type);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<PrsionInfoModel> models = gson.fromJson(result,
                        new TypeToken<ArrayList<PrsionInfoModel>>() {
                        }.getType());

                success.onSuccess(models);
            }

            @Override
            public void onFailed(String result) {
                BaseActivity
                        .getInstance()
                        .showToast(
                                String.format(
                                        activity.getString(R.string.request_net_failed),
                                        result), Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 新闻资讯分类
     *
     * @param activity
     * @param categoryId 分类
     * @param pageNo     当前的页码
     * @param pageSize   当前的分页数量
     * @param type
     * @param successCB
     */
    public void getPrisonNewsPages(final Activity activity, int categoryId,
                                   int pageNo, int pageSize, int type,
                                   final IBeanOnSuccessCB<PrisonInfoPageModel> successCB) {
        String url = UrlMgr.getPrisonNewsPages(categoryId, pageNo, pageSize,
                type);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final PrisonInfoPageModel models = gson.fromJson(result,
                        PrisonInfoPageModel.class);
                successCB.onSuccess(models);
            }

            @Override
            public void onFailed(String result) {
                BaseActivity
                        .getInstance()
                        .showToast(
                                String.format(
                                        activity.getString(R.string.request_net_failed),
                                        result), Toast.LENGTH_LONG);
            }

        });
    }

    public void getPrisonWebsitPages(final Activity activity, String category,
                                     int pageNo, int pageSize,
                                     final IBeanOnSuccessCB<PrisonWebsitPageModel> successCB) {
        String url = UrlMgr.getPrisonWebsitPagesUrl(category, pageNo, pageSize);
        LogUtil.d("getPrisonWebsitPages---------url=" + url);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final PrisonWebsitPageModel models = gson.fromJson(result,
                        PrisonWebsitPageModel.class);
                successCB.onSuccess(models);
            }

            @Override
            public void onFailed(String result) {
                BaseActivity
                        .getInstance()
                        .showToast(
                                String.format(
                                        activity.getString(R.string.request_net_failed),
                                        result), Toast.LENGTH_LONG);
            }

        });
    }

    /**
     * 获取新闻详情信息
     *
     * @param activity
     * @param id
     * @param successCB
     */
    public void getPrisonNewsDetail(final Activity activity, int id,
                                    final IBeanOnSuccessCB<PrsionInfoModel> successCB) {
        String url = UrlMgr.getPrsionNewsDetail(id);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final PrsionInfoModel models = gson.fromJson(result,
                        PrsionInfoModel.class);
                successCB.onSuccess(models);
            }

            @Override
            public void onFailed(String result) {
                BaseActivity
                        .getInstance()
                        .showToast(
                                String.format(
                                        activity.getString(R.string.request_net_failed),
                                        result), Toast.LENGTH_LONG);
            }

        });
    }

    /**
     * 书籍分类
     *
     * @param activity
     * @param success
     * @param count
     */
    public void getBookCategory(final Activity activity,
                                final IListOnSuccessCB<BookCategoryModel> success, int count) {
        String url = UrlMgr.getBookCategory(count);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<BookCategoryModel> models = gson.fromJson(
                        result, new TypeToken<ArrayList<BookCategoryModel>>() {
                        }.getType());

                if (null != models && models.size() > 0) {
                    success.onSuccess(models);
                } else {
                    BaseActivity.getInstance().showToast(
                            String.format(activity
                                            .getString(R.string.request_net_failed),
                                    result), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailed(String result) {
                BaseActivity
                        .getInstance()
                        .showToast(
                                String.format(
                                        activity.getString(R.string.request_net_failed),
                                        result), Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 获取书的分页信息
     *
     * @param activity
     * @param categoryId
     * @param pageNo
     * @param pageSize
     * @param successCB
     */
    public void getBookPage(final Activity activity, int categoryId,
                            int pageNo, int pageSize,
                            final IBeanOnSuccessCB<BookPageModel> successCB) {
        String url = UrlMgr.getBookLists(categoryId, pageNo, pageSize);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final BookPageModel models = gson.fromJson(result,
                        BookPageModel.class);
                successCB.onSuccess(models);
            }

            @Override
            public void onFailed(String result) {
                BaseActivity
                        .getInstance()
                        .showToast(
                                String.format(
                                        activity.getString(R.string.request_net_failed),
                                        result), Toast.LENGTH_LONG);
            }

        });
    }

    /**
     * 获取新闻资讯分类
     *
     * @param activity
     * @param success
     */
    public void loginPrisonUser(final Activity activity,
                                final IBeanOnSuccessCB<UserRequestModel> successCB,
                                String username, String password) {
        String url = UrlMgr.getPrisonUserInfoUrl(username, password);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final UserRequestModel models = gson.fromJson(result,
                        UserRequestModel.class);
                successCB.onSuccess(models);
            }

            @Override
            public void onFailed(String result) {
                BaseActivity
                        .getInstance()
                        .showToast(
                                String.format(
                                        activity.getString(R.string.request_net_failed),
                                        result), Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 狱务公开，信息查询
     *
     * @param activity
     * @param username
     * @param usercode
     * @param type
     * @param successCB
     */
    public void getPrisonUserSearch(final Activity activity, String username,
                                    String usercode, final int type,
                                    final IUserSearchOnSuccessCB successCB) {
        String url = UrlMgr.getPrisonUserSearchUrl(username, usercode, type);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                successCB.onSuccess(result, type);
            }

            @Override
            public void onFailed(String result) {
                BaseActivity
                        .getInstance()
                        .showToast(
                                String.format(
                                        activity.getString(R.string.request_net_failed),
                                        result), Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 获取菜谱清单
     *
     * @param activity
     * @param success
     */
    public void getFoods(final Activity activity,
                         final IListOnSuccessCB<FoodModel> success) {
        String url = UrlMgr.getPrisonFoodUrl();
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ArrayList<FoodModel> models = gson.fromJson(result,
                        new TypeToken<ArrayList<FoodModel>>() {
                        }.getType());

                if (null != models && models.size() > 0) {
                    success.onSuccess(models);
                } else {
                    BaseActivity.getInstance().showToast(
                            String.format(activity
                                            .getString(R.string.request_net_failed),
                                    result), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailed(String result) {
                BaseActivity
                        .getInstance()
                        .showToast(
                                String.format(
                                        activity.getString(R.string.request_net_failed),
                                        result), Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 点播记录
     *
     * @param activity
     * @param vodId
     * @param time
     */
    public void getVodRecordTime(final Activity activity, int programid,
                                 long watchtime) {
        String url = UrlMgr.getVodRecordTimeUrl(programid, watchtime);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onFailed(String result) {
            }
        });
    }

    /**
     * 直播记录
     *
     * @param activity
     * @param liveId
     * @param time
     */
    public void getLineRecordTime(final Activity activity, int channelid,
                                  long watchtime) {
        String url = UrlMgr.getLiveRecordTimeUrl(channelid, watchtime);
        LVBHttpUtils.get(url, new IResponseable() {

            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onFailed(String result) {
            }
        });
    }

    /**
     * 更换开机动
     */
    public void openBootanimaton() {
        final String url = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.BOOT_ANIMATION);
        final String urlLogo = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.AB_BOOT_LOGO);
        LogUtil.i("urlLogo:" + urlLogo + "---url----" + url);
        final IDeytechManager deytechManager = (IDeytechManager) LVBXApp.getApp().getSystemService(IDeytechManager.DEYTECH_SERVICE);
        if (StringUtil.isEmpty(url) && StringUtil.isEmpty(urlLogo)) {
            return;
        }
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String desPath = Constant.IPTV_LVB_W_BOOTANIMATION + "bootanimation.zip";
                    File file = new File(desPath);
                    if (file.isFile() && file.exists()) {
                        file.delete();
                    }
                    if (null != url) {
                        changeDataLocal(url, "bootanimation.zip", desPath);
                    }
                    if (null != deytechManager && null != urlLogo) {
                        final String logoName = urlLogo.substring(urlLogo.lastIndexOf("/") + 1, urlLogo.length());
                        String logoPath = Constant.IPTV_LVB_W_BOOTANIMATION + logoName;
                        File logoFile = new File(logoPath);
                        if (logoFile.isFile() && logoFile.exists()) {
                            logoFile.delete();
                        }
                        updateLogo(deytechManager, urlLogo, logoName, logoPath);
                    }

                } else {
                    String desPathLocal = Constant.IPTV_LVB_W_LOCAL_BOOTANIMATION + "bootanimation.zip";
                    File file = new File(desPathLocal);
                    if (file.isFile() && file.exists()) {
                        file.delete();
                    }
                    if (null != url) {
                        changeLocal(url, "bootanimation.zip", desPathLocal);
                    }

                    if (null != deytechManager && urlLogo != null) {
                        final String logoName = urlLogo.substring(url.lastIndexOf("/") + 1, url.length());
                        String logoPath = Constant.IPTV_LVB_W_BOOTANIMATION + logoName;
                        File logoFile = new File(logoPath);
                        if (logoFile.isFile() && logoFile.exists()) {
                            logoFile.delete();
                        }
                        updateLogo(deytechManager, urlLogo, logoName, logoPath);
                    }
                }
            }
        });
    }


    /**
     * 移动sdcard/data/boot/下的开机动画到data/local
     *
     * @param url
     * @param path
     * @param desPath
     */
    private void changeDataLocal(String url, String path, final String desPath) {
        try {
            DownloadService.getDownloadManager(LVBXApp.getApp().getApplicationContext()).addNewDownload(url, path, desPath, true, false, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    LogUtil.i("SuccessSuccess11" + desPath);
                    IDeytechManager deytechManager = (IDeytechManager) LVBXApp.getApp().getSystemService(IDeytechManager.DEYTECH_SERVICE);
                    LogUtil.i("deytechManager::" + deytechManager);
                    if (null != deytechManager) {
                        deytechManager.updateBootAnimation(desPath);
                    } else {
                        RootManager.getInstance().execRootCmd("mount -o remount /system");
                        RootManager.getInstance().execRootCmd("cp /sdcard/boot/bootanimation.zip /data/local");
                        RootManager.getInstance().execRootCmd("sync");
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    LogUtil.i("fail__welcom" + s);
                }
            });
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移动data/data/com.hhzt.iptv/boot/下的开机动画到data/local
     *
     * @param url
     * @param path
     * @param desPath
     */
    private void changeLocal(String url, String path, final String desPath) {
        try {
            DownloadService.getDownloadManager(LVBXApp.getApp().getApplicationContext()).addNewDownload(url, path, desPath, true, false, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    LogUtil.i("SuccessSuccess");
                    LogUtil.i("SuccessSuccess" + desPath);
                    IDeytechManager deytechManager = (IDeytechManager) LVBXApp.getApp().getSystemService(IDeytechManager.DEYTECH_SERVICE);
                    LogUtil.i("deytechManager::" + deytechManager);
                    if (null != deytechManager) {
                        deytechManager.updateBootAnimation(desPath);
                    } else {
                        RootManager.getInstance().execRootCmd("mount -o remount /system");
                        RootManager.getInstance().execRootCmd("cp /data/data/com.hhzt.iptv/files/boot/bootanimation.zip /data/local/");
                        RootManager.getInstance().execRootCmd("sync");
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    LogUtil.i("fail__welcom" + s);
                }
            });
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /***
     * 更换酷睿黑的开机logo
     *
     * @param url
     * @param desPath
     */
    public void updateLogo(final IDeytechManager deytechManager, String url, String name, final String desPath) {
        try {
            DownloadService.getDownloadManager(LVBXApp.getApp().getApplicationContext()).addNewDownload(url, name, desPath, true, false, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    LogUtil.i("SuccessSuccess" + desPath);
                    LogUtil.i("deytechManager22::" + deytechManager);
                    if (null != deytechManager) {
                        deytechManager.updateLogo(desPath);
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    LogUtil.i("fail__welcom" + s);
                }
            });
        } catch (DbException e) {
            e.printStackTrace();
        }

    }
}

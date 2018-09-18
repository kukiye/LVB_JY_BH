package com.hhzt.iptv.lvb_x.handler;

/**
 * @Package: com.jinggan.dear.common
 * @Description:定义handler数据值
 * @author: Aaron
 * @date: 2015-12-11
 * @Time: 13:22
 * @version: V1.0
 */
public class HandlerValue {
    //主界面
    public final static int REQ_WEATHER_SUCCESS = 0x100;
    public final static int REQ_MAIN_HOME_TEM_SUCCESS = 0x101;
    public final static int REQ_MAIN_LIVE_TEM_SUCCESS = 0x102;
    public final static int REQ_MAIN_VIDEO_TEM_SUCCESS = 0x103;
    public final static int REQ_MAIN_NEWS_TEM_SUCCESS = 0x104;
    public final static int REQ_SYSTEM_CONFIG_SUCCESS = 0x105;
    public final static int REQ_LIVE_BY_ID_SUCCESS = 0x106;
    public final static int REQ_MAIN_VIDEO_TEM_SUCCESSV2 = 0x107;
    public final static int REQ_MAIN_BOTTOM = 0x108;
    public final static int ResumeMsg = 0x109;  //为保证直播位置不偏移  所发送的消息
    public final static int RE_SKIP_TIME = 0x110;
    public final static int APK_DOWN_SUCCESS = 0x111;


    //直播
    public final static int REQ_LIVE_SUCCESS = 0x200;
    public final static int LIVE_1_VALUE = 0x201;
    public final static int LIVE_2_VALUE = 0x202;
    public final static int LIVE_3_VALUE = 0x203;
    public final static int LIVE_4_VALUE = 0x204;
    public final static int LIVE_5_VALUE = 0x205;
    public final static int LIVE_6_VALUE = 0x206;
    public final static int LIVE_7_VALUE = 0x207;
    public final static int LIVE_LIST_FOCUSECHANGE = 0x208;
    public final static int LIVE_REQUEST_SUCCESS = 0x209;

    //点播
    public final static int REQ_VIDEO_TYPE_SUCCESS = 0x300;
    public final static int REQ_VIDEO_TYPE_DETAILS_SUCCESS = 0x301;
    public final static int REQ_VIDEO_RECOMMEND_SUCCESS = 0x302;
    public final static int REQ_VIDEO_DETAILS_SUCCESS = 0x303;
    public final static int REQ_VIDEO_FOCUSVIEW_CHANGE = 0x308;
    public final static int REQ_VIDEO_SEARCH_SUCCESS = 0x309;
    public final static int REQ_VIDEO_UP_MOVE_SELECTPOS = 0x310;
    public final static int REQ_VIDEO_SEARCH_NEXT_SUCCESS = 0x311;
    public final static int REQ_VIDEO_SEARCH_REQUEEST = 0x312;
    public final static int REQ_VIDEO_PAYSINGLE_REQUEEST = 0x313;
    public final static int REQ_VIDEO_PAYLIST_REQUEEST = 0x314;

    public final static int VIDEO_VALUE_1 = 0x304;
    public final static int VIDEO_VALUE_2 = 0x305;
    public final static int VIDEO_VALUE_3 = 0x306;
    public final static int VIDEO_VALUE_4 = 0x307;

    //资讯类
    public final static int NEWS_VALUE_1 = 0x400;
    public final static int NEWS_F_View_SCS = 0x402;
    public final static int New_F_Data_Fail = 0x403;
    public final static int NEWS_VALUE_4 = 0x404;
    public final static int NEWS_VALUE_5 = 0x405;
    public final static int NEWS_VALUE_6 = 0x406;
    public final static int NEWS_VALUE_7 = 0x407;
    public final static int NEWS_VALUE_8 = 0x408;
    public final static int NEWS_VALUE_9 = 0x409;
    public final static int NEWS_VALUE_10 = 0x410;
    public final static int NEWS_HOME_REQUEST_SUCCESS = 0x411;
    public final static int NEWS_ACTIVITY_DELAYED = 0x412;

    //应用中心
    public final static int EDU_VALUE_1 = 0x500;
    public final static int EDU_VALUE_2 = 0x501;
    public final static int APP_DOWN_SUCCESS = 0x502;
    public final static int APP_INSTALL_SCUESS = 0x503;

    public final static int EDU_VALUE_5 = 0x504;

    //旅游信息
    public final static int LY_XX = 0x700;
    public final static int LY_LUNBO_KAIS = 0x701;

    //账号
    public final static int ACCOUNT_VALUE_1 = 1;
    public final static int ACCOUNT_VALUE_2 = 2;
    public final static int ACCOUNT_VALUE_3 = 3;
    public final static int ACCOUNT_VALUE_4 = 4;
    public final static int ACCOUNT_VALUE_5 = 5;
    public final static int ACCOUNT_VALUE_6 = 6;


    /**
     * 视频点播
     */
    public static final String VIDEO_URL = "video_url";// URL标签
    public static final int MEDIAPLAYER_STATE_LOADING = 9;
    public static final int MEDIAPLAYER_LAYOUT_HIDE = 10;
    public static final int MEDIAPLAYER_LAYOUT_SHOW = 11;
    public static final int MEDIAPLAYER_CORTORLL_BUTTON_HIDE = 12;
    public static final int MEDIAPLAYER_CORTORLL_BUTTON_SHOW = 13;
    public static final int MEDIAPLAYER_CORTORLL_HIDE_PLAYER_BUTTOM = 14;
    public static final int MEDIAPLAYRE_CHANNEL_SELETE = 15;
    public static final int MEDIAPLAYER_CHANNEL_SELETE_Q = 16;
    public static final int MEDIAPLAYER_CHANG_CHANNEL = 17;
    public static final int MEDIAPLAYER_CHANG_CLEAR_CHANNEL = 20;
    public static final int MONITOR_NETWORK_CHANGE = 21;
    public static final int MEDIPALAYER_STOP = 22;
    public static final int FROCE_PLAY_TIME = 23;


    //资费
    /*选择支付方式*/
    public static final int SELECTOR_PAYMENT = 0x601;

    public static final int TARRIF_VALUE_2 = 0x602;
    public static final int SET_VALUE_1 = 0x603;

    /*第三版回看*/
    public static final int REPLAY_INIT_VIEW = 0x801;
    public static final int REPLAY_VIDEOLIST_HASOCUSE = 0x802;
    public static final int REPLAY_DAY_DATA_SUSS = 0x803;
    public static final int REPLAY_DAY_LiveMenu_Thread_Success = 0x804;
    public static final int REPLAY_DAY_Dateinit_Suss = 0x805;



    public static final int REQ_MAIN_MENU = 0xAc0100;
    public static final int REQ_MAIN_SPECIAL_FOCUS = 0xAc0101;
    public static final int REQ_MAIN_REMOULD_STAR = 0xAc0102;

    public static final int REQ_HOME_ENTER_TEACH = 0xAD0101;
    public static final int REQ_HOME_CLASS_TEACH = 0xAD0102;
    public static final int REQ_HOME_OUT_GUIDE = 0xAD0103;
    public static final int REQ_HOME_NEWS_LINE = 0xAD0104;
    public static final int REQ_HOME_PRISION_CULTURE = 0xAD0105;
    public static final int REQ_HOME_PRISON_HEART = 0xAD0106;
    public static final int REQ_HOME_PROGRAM_LIVE = 0xAD0107;
    public static final int REQ_HOME_PRISON_ARRAIRS = 0xAD0108;
    public static final int REQ_HOME_MOVIE_ENJOY = 0xAD0109;
    public static final int REQ_HOME_VIDEO_GRID = 0xAD0110;
    public static final int REQ_HOME_SPECIAL_FOCUS = 0xAD0111;

    //主页广告数据成功右侧
    public static final int REQ_MAIN_HOME_AD_SUCCESS_RIHT = 0xAD0102;
    //主页广告数据左侧Viewpage切换 a 切换
    public static final int REQ_MAIN_HOME_AD_CHANGE_1 = 0xAD01a1;
    //主页广告数据右侧Viewpage切换
    public static final int REQ_MAIN_HOME_AD_CHANGE_2 = 0xAD01a2;
    //主页广告数据重新请求左侧  f 重新请求
    public static final int REQ_MAIN_HOME_AD_RE_LEFT = 0xAD01f1;
    //主页广告数据重新请求右侧
    public static final int REQ_MAIN_HOME_AD_RE_RIGHT = 0xAD01f2;
    //主页详情广告位切换
    public static final int REQ_SKIP_HOME_AD_CHANGE = 0xAD01f3;

    //单独广告界面广告请求成功
    public static final int REQ_ACTIVITY_AD_SUCCESS = 0xAD0501;
    public static final int REQ_ACTIVITY_AD_RE = 0xAD0502;
    public static final int REQ_ACTIVITY_AD_CHANGE = 0xAD0503;
    public static final int SystemSkipmsg = 0xAD0504;


    //直播广告数据成功
    public static final int REQ_MAIN_LIVE_AD_SUCCESS = 0xAD0201;
    //直播广告Viewpage切换
    public static final int REQ_MAIN_LIVE_AD_CHANGE = 0xAD02a1;
    //直播广告数据重新请求
    public static final int REQ_MAIN_LIVE_AD_RE = 0xAD0202;
    //直播点播广告请求成功 这里以广告Id进行下一步逻辑区分  有时间可以把主页进行抽取整理
    public static final int REQ_VOD_LIVE_AD_SUCCESS_L = 0xAD0301;
    public static final int REQ_VOD_LIVE_AD_SUCCESS_A = 0xAD0302;
    public static final int REQ_VOD_LIVE_AD_SUCCESS_R = 0xAD0303;
    //直播点播广告再次请求
    public static final int REQ_VOD_LIVE_AD_RE_L = 0xAD0321;
    public static final int REQ_VOD_LIVE_AD_RE_A = 0xAD0322;
    public static final int REQ_VOD_LIVE_AD_RE_R = 0xAD0323;
    //直播点播广告变换
    public static final int REQ_VOD_LIVE_AD_CHANGE_L = 0xAD0331;
    public static final int REQ_VOD_LIVE_AD_CHANGE_R = 0xAD0332;
    public static final int REQ_VOD_LIVE_AD_CHANGE_A = 0xAD0333;
    //展示
    public static final int REQ_VOD_LIVE_AD_SHOW_L = 0xAD0341;
    public static final int REQ_VOD_LIVE_AD_SHOW_R = 0xAD0342;
    public static final int REQ_VOD_LIVE_AD_SHOW_A = 0xAD0343;
    //关闭时间到
    public static final int REQ_VOD_LIVE_AD_CLOSE_L = 0xAD0351;
    public static final int REQ_VOD_LIVE_AD_CLOSE_R = 0xAD0352;
    public static final int REQ_VOD_LIVE_AD_CLOSE_A = 0xAD0353;
    //点播详情界面
    public static final int REQ_VOD_DETAILS_AD_SUCCESS = 0xAD0401;
    public static final int REQ_VOD_DETAILS_AD_RE = 0xAD0421;
    public static final int REQ_VOD_DETAILS_AD_CHANGE = 0xAD0431;

    //为主界面的viewpage 设置延迟时间
    public static final int mainHomeDelayed = 0xad999;
    public static final int MainLiveDelayed = 0xad998;


}

/**
 * 作者：   Johnson
 * 日期：   2014年6月11日下午2:29:03
 * 包名：    com.hhzt.iptv_lvb_x
 * 工程名：LVB_X
 * 文件名：Contants.java
 */
package com.hhzt.iptv.lvb_x;

import android.os.Environment;

public class Constant {
    public static final String LOG_TAG = "lvb_x";
    public static final String FACTORY_MODE_CMD = "0124";
    // iptv内部使用的默认值
    public static final String IPTV_DEFAULT_WEATHER_CITY_CODE = "1903";
    public static final String IPTV_DEFAULT_AIRCITY_CODE = "SZX";
    public static final String IPTV_DEFAULT_VOD_MENUCODE = "vod";
    public static final String LVC_HOTSPOT_DEFAULT_PASSWD = "********";// 热点默认密码
    public static final String IPTV_SYS_CONFIG = "LVB_CONFIG";
    public static final String IPTV_SYS_BASE_HOST_URL = "base_host_url";
    public static final String IPTV_SYS_BASE_HOST_IP = "base_host_ip";
    public static final String IPTV_SYS_FRAGMENT_TAG = "fragment_tag";
    public static final String IPTV_SYS_VOD_ITEM_SINGLE = "vod_item_single";
    public static final String IPTV_SYS_VOD_ITEM_CATEGORY_ID = "category_id";
    public static final String IPTV_SYS_HOTEL_ORDER_ID = "orderId";
    public static final String IPTV_SYS_HOTEL_ORDER_CODE = "orderCode";
    public static final String IPTV_SYS_HOTEL_ORDER_TIME = "orderTime";
    public static final String IPTV_SYS_HOTEL_ORDER_STATE = "orderState";
    public static final String IPTV_SYS_HOTEL_LANG_TYPE = "system_langType";
    public static final String IPTV_SYS_HOTEL_WANTED_LANG_TYPE = "system_wanted_langType";
    public static final String IPTV_SYS_HOTEL_LANG_NAME = "system_langName";
    public static final String IPTV_SYS_MSG_PUSHED_SERVER_IP = "msg_server_ip";
    public static final String IPTV_SYS_MSG_PUSHED_SERVER_PORT = "msg_server_port";
    public static final String IPTV_SYS_MSG_APK_EXIST_FLAG = "apk_exist";
    public static final String IPTV_SYS_MSG_APK_DOWNLOAD_URL = "apk_url";
    public static final String IPTV_SYS_MSG_BGMUSIC_STATE_TAG = "bgmusic_state";
    public static final String IPTV_SYS_MSG_INFO_CONTENT = "msg_info_content";

    public static final String IPTV_USER_HOTEL_CHECK_IN = "check_in_time";
    public static final String IPTV_USER_HOTEL_NAME = "hotel_name";
    public static final String IPTV_USER_HOTEL_ROOM_NUMBER = "hotel_room_no";
    public static final String IPTV_USER_USER_NAME = "user_name";
    public static final String IPTV_USER_USER_PASSWD = "user_passwd";
    public static final String IPTV_USER_USER_SERVERIP = "server_ip";
    public static final String IPTV_USER_PAIRED_IP = "paired_ip";
    public static final String IPTV_USER_PAIRED_MAC = "paired_mac";
    public static final String IPTV_USER_PAIRED_TAG = "paired_tag";
    public static final String IPTV_USER_CHANNER_GROUP = "channel_group";
    public static final String IPTV_USER_INTERACTIVE_PASSWD = "interactive_passwd";
    public static final String IPTV_USER_SSI_HIDE_TAG = "ssid_hide_tag";
    public static final String IPTV_USER_WIFI_NAME = "wifi_name";
    public static final String IPTV_USER_IS_ENCYPTED = "is_encrpted";
    public static final String IPTV_USER_HOTEL_LOGO_URL = "hotel_logo_url";
    public static final String IPTV_USER_HOTEL_IMAGE_URL = "hotel_image_url";
    public static final String IPTV_USER_HOTEL_CITY_AIR_CODE = "city_air_code";
    public static final String IPTV_USER_HOTEL_CITY_NUM_CODE = "city_num_code";
    public static final String IPTV_USER_HOTEL_CITY_NAME = "city_name";
    public static final String IPTV_USER_HOTEL_CITY_SETTED = "city_setted_flag";
    public static final String IPTV_USER_HOTEL_HOTSPOT_TAG = "hotspot_tag";
    public static final String IPTV_USER_HOTEL_VOD_FREE_ENABLE = "vod_free_enable";
    public static final String IPTV_SYS_MSG_MEDIA_LOCK_TAG = "media_lock_tag"; //播放器锁定

    public static final String  IPTV_LVB_W_BOOTANIMATION = Environment.getExternalStorageDirectory() + "/boot/"; // 开机动画文件存储根路径
    public static final String IPTV_LVB_W_LOCAL_BASEPATH = LVBXApp.getApp().getFilesDir().getPath() + "/lvb/"; // 项目文件本地存储根路径
    public static final String IPTV_LVB_W_LOCAL_BOOTANIMATION =IPTV_LVB_W_LOCAL_BASEPATH + "/boot/"; // 开机动画文件存储根路径


    // 世界时钟大小
    public static final int CLOCK_SIZE = 600;

    // 外网测试地址
    public static final String EXTERNAL_NET_ADDRESS = "www.baidu.com";

    // 数据库名称
    public static final String DB_NAME = "HHZT_LVB.db";

    // 节目清单数目
    public static final int LIVE_PRE_LIST_NUMBER = 20;
    public static final int LIVE_BACK_PRE_LIST_NUMBER = 100;
    // 节目列表状态标识
    public static final String LIVE_BACK_UNABLE_TAG = "1"; // 不可回看
    public static final String LIVE_BACK_ABLE_TAG = "2";// 可回看
    public static final String LIVE_PLAYING_TAG = "3";// 正在直播
    public static final String LIVE_GUIDE_TAG = "4";// 节目预告
    // 回看默认日期
    public static final String IPTV_LIVE_BACK_DEFAULT_DATE = "0";
    // 续房可延至最大天数
    public static final int IPTV_SYSTEM_DATE_NUM = 30;
    // 系统支持语言
    public static final int IPTV_SYSTEM_LANG_SIMPLE_CHINESE = 1;
    public static final int IPTV_SYSTEM_LANG_US_ENGLISH = 2;
    // 皇室假期留座默认时间
    public static final int HSJQ_DEFAULT_SEAT_TIME = 10;

    // 直播OSD标志
    public static final int IPTV_LIVE_OSD_HIDE = 0;
    public static final int IPTV_LIVE_OSD_MAIN_SHOW = 1;
    public static final int IPTV_LIVE_OSD_SUB_SHOW = 2;
    public static final int IPTV_LIVE_OSD_DATE_SHOW = 3;

    // 客房服务类型
    public static final int IPTV_ROOMSERVICE_TYPE_CHECK_OUT = 1;// 退房服务
    public static final int IPTV_ROOMSERVICE_TYPE_CHECK_IN = 2;// 续房服务
    public static final int IPTV_ROOMSERVICE_TYPE_WAKEUP = 3;// 叫醒服务
    public static final int IPTV_ROOMSERVICE_TYPE_CLEAN = 4;// 打扫卫生服务

    // 客房服务请求响应状态
    public static final int IPTV_ROOMSERVICE_STATUS_UNKNOWN = 0;// 未知
    public static final int IPTV_ROOMSERVICE_STATUS_WAITING = 1;// 待处理
    public static final int IPTV_ROOMSERVICE_STATUS_DOING = 2;// 已响应
    public static final int IPTV_ROOMSERVICE_STATUS_FINISHED = 3;// 已进行
    public static final int IPTV_ROOMSERVICE_STATUS_CANCELED = 4;// 已取消

    // 广告位类型索引
    public static final int IPTV_ADV_INDEX_1 = 100;
    public static final int IPTV_ADV_INDEX_2 = 101;

    // IPTV 系统消息
    public static final int IPTV_MSG_UPDATE_TIME_FREQUENCE = 999;
    public static final int IPTV_MSG_UPDATE_CONTROLLER_WAIT = 1000;
    public static final int IPTV_MSG_HIDE_LIVE_CHANNEL_OSD = 1001;
    public static final int IPTV_MSG_SHOW_LIVE_CHANNEL_OSD = 1002;
    public static final int IPTV_MSG_HIDE_LIVE_PROGRAME_OSD = 1003;
    public static final int IPTV_MSG_SHOW_LIVE_PROGRAME_OSD = 1004;
    public static final int IPTV_MSG_JUDGE_FILE_EXIST = 1005;
    public static final int IPTV_MSG_MAINMENU_BG_CHANGE = 1006;
    public static final int IPTV_MSG_HOTERSERVICE_BG_CHANGE = 1007;
    public static final int IPTV_MSG_TRAVERL_BG_CHANGE = 1008;
    public static final int IPTV_MSG_BUSINESS_BG_CHANGE = 1009;
    public static final int IPTV_MSG_SOCKET_HEART_CHECK = 1010;
    public static final int IPTV_MSG_SOCKET_SEND_RECONNECT_ACTION = 1011;
    public static final int IPTV_MSG_SOCKET_RECEIVE_CHECK = 1012;
    public static final int IPTV_MSG_SOCKET_SEND_LOGIN_ACTION = 1013;
    public static final int IPTV_MSG_SOCKET_RECEIVE_ACTION = 1014;
    public static final int IPTV_MSG_UPDATE_DOWNLOADING = 1015;
    public static final int IPTV_MSG_UPDATE_DOWNLOAD_FAILED = 1016;
    public static final int IPTV_MSG_UPDATE_DOWNLOAD_SUCCESS = 1017;
    public static final int IPTV_MSG_UPDATE_INSTALLING = 1018;
    public static final int IPTV_MSG_UPDATE_INSTALL_FINISH = 1019;
    public static final int IPTV_MSG_CHECK_MEDIA_PAYER_BUG = 1020;
    public static final int IPTV_MSG_LIVE_DOUBLE_CLICK = 1021;
    public static final int IPTV_MSG_VOD_PAY_TIPS = 1022;
    public static final int IPTV_MSG_AUTHORIZATION_TIPS = 1023;
    public static final int IPTV_MSG_APP_EXIST_TAG = 1024;
    public static final int IPTV_MSG_MEDIAPLAYER_LIVE_CHANNEL = 1025;
    public static final int IPTV_MSG_MEDIAPLAYER_LIVE_NUM_INTERVAL = 1026;
    public static final int IPTV_MSG_TEXT_CONTENT_SCROLL_COMPLETE = 1027;
    public static final int IPTV_MSG_PING_CONTENT_TAG = 1028;
    public static final int IPTV_MSG_PING_OK_TAG = 1029;
    public static final int IPTV_MSG_HSJQ_SEAT_TIME_TAG = 1030;
    public static final int IPTV_MSG_WELCOME_BG_CHANGE = 1031;
    public static final int IPTV_MSG_MEDIAPLAYER_ACTION_TIPS = 1032;


    // 消息推送sokect请求类型
    public static final int SOKECT_LOGIN = 1000;
    public static final int SOKECT_RECONNECT = 1001;

    // 消息推动回应类型
    public static final String SOCKET_MSG_TYPE = "msg";
    public static final String SOCKET_ACK_TYPE = "ack";
    public static final String SOCKET_LOGIN_TYPE = "login";
    public static final String SOCKET_ACTION_TYPE = "action";
    public static final String SOCKET_ROOMSTATUS_TYPE = "roomstatus";
    public static final String SOCKET_FORCE_PLAY = "forceplay";
    public static final String SOCKET_FORCE_FREE = "forcefree";

    // 网络测试标识
    public static final String EXTERNAL_NET_TEST = "external";
    public static final String CUSTOMER_NET_TEST = "customer";
    public static final String SERVER_NET_TEST = "server";

    // ott物理地址类型标示
    public static final int WIFI_MAC = 1;
    public static final int ETHERNET_MAC = 2;
    public static final String IPTV_AUTHORITHED_MAC = "f0:13";// 不区分大小写

    // 设备区分标示
    public static final int DEVICE_TYPE_BOX = 1;// 机顶盒
    public static final int DEVICE_TYPE_MOBILE = 2;// 移动端设备
    public static final int DEVICE_TYPE_BOX_HSJQ = 3; // 机顶盒---皇室假期
    public static final int DEVICE_TYPE_MOBILE_HSJQ = 4;// 移动设备---皇室假期

    // 播放器播放状态
    public static final int IPTV_MEDIAPLAYER_STATE_DEFAULT = 0;
    public static final int IPTV_MEDIAPLAYER_STATE_PLAYING = 1;
    public static final int IPTV_MEDIAPLAYER_STATE_PAUSING = 2;

    // 设备状态
    public static final int DEVICE_NORMAL = 0;
    public static final int DEVICE_TICKER = 1;
    public static final int DEVICE_LOCK = 2;

    // 时间
    public static final int IPTV_TIME_ONE_SECOND = 1 * 1000;
    public static final int IPTV_TIME_ONE_MINUTE = 60 * 1000;
    public static final int IPTV_TIME_ONE_MINUTE_IN_SECOND = 60;
    public static final int IPTV_TIME_ONE_HOUR_IN_MINUTE = 60;
    public static final int IPTV_TIME_ONE_HOUR_IN_SECOND = 60 * 60;
    public static final int FIVE_H_MILLSECONDES = 500;// 500ms
    public static final int IPTV_TIME_ONE_SECONDE = 1000;
    public static final int IPTV_TIME_TWO_SECONDES = 2 * 1000;
    public static final int IPTV_TIME_THREE_SECONDES = 3 * 1000;
    public static final int IPTV_TIME_FOUR_SECONDES = 4 * 1000;
    public static final int IPTV_TIME_FIVE_SECONDES = 5 * 1000;
    public static final int IPTV_TIME_SIX_SECONDES = 6 * 1000;
    public static final int IPTV_TIME_EIGHT_SECONDS = 8 * 1000;
    public static final int IPTV_TIME_TEN_SECONDES = 10 * 1000;
    public static final int IPTV_TIME_TWENTY_SECONDS = 20 * 1000;
    public static final int IPTV_TIME_FIVE_MINUTES = 5 * 60 * 1000;

    public static final String IPTV_MEDIA_DATA_TAG = "MediaData";
    public static final String IPTV_MEDIA_TYPE = "MediaType";
    public static final String IPTV_VOD_MEDIA_DATA = "Vod_datas";
    public static final String IPTV_VOD_MEDIA_PROGRME_ID = "Vod_programe_id";
    public static final String IPTV_VOD_MEDIA_PRICE = "Vod_price";
    public static final String IPTV_MEDIA_URL = "MediaUrl";
    public static final String IPTV_MEDIA_NAME = "MediaName";
    public static final String IPTV_MEDIA_FEE_NAME = "MediaFeeName";
    public static final String IPTV_LIVE_CHANNEL_LIST = "liveChannelList";
    public static final String IPTV_LIVE_CHANNEL_INDEX = "liveChannelIndex";
    public static final String IPTV_LIVE_CHANNEL_NUMBER = "liveChannelNumber";
    public static final String IPTV_MEDIA_MOVIE_ID = "movie_id";
    public static final String IPTV_LIVE_START_PLAY_TIME = "liveplaytime"; //开始时间
    public static final String IPTV_MEDIA_MOVIE_CURRENT_POSITION = "vod_current_position";
    public static final String IPTV_MEDIA_VOD_URLS = "vod_urls";
    public static final String IPTV_MENU_ITEM_LIVE_NAME = "menu_live_name";
    public static final String IPTV_MENU_ITEM_VOD_NAME = "menu_vod_name";
    public static final String IPTV_MENU_ITEM_VOD_CODE = "menu_vod_code";
    public static final String IPTV_MENU_ITEM_HS_NAME = "menu_hs_name";
    public static final String IPTV_MENU_ITEM_ORDER_NAME = "menu_order_name";
    public static final String IPTV_MENU_ITEM_TRAVEL_NAME = "menu_travel_name";
    public static final String IPTV_MENU_ITEM_APP_NAME = "menu_app_name";
    public static final String IPTV_MENU_ITEM_LANG_NAME = "menu_lang_name";
    public static final String IPTV_MENU_ITEM_HS_ID = "menu_hs_id";
    public static final String IPTV_MENU_ITEM_HS_CODE = "menu_hs_code";
    public static final String IPTV_MENU_ITEM_TRAVEL_ID = "menu_travel_id";
    public static final String IPTV_MENU_ITEM_APP_ID = "menu_app_id";
    public static final String IPTV_MENU_ITEM_THIRD_MARKET_MENUCODE = "menu_third_market_menucode";
    public static final String IPTV_MENU_ITEM_THIRD_MARKET_NAME = "menu_third_market_name";
    public static final String IPTV_KEY_PAIR_AUTHORIZE_MSG = "pair_authorize_type";
    public static final String IPTV_VALUE_PAIR_AUTHORIZE_MSG = "pair_authorize_data";
    public static final String IPTV_MEDIAPLAYER_CONTROLLER_MSG = "mediaplayer_controller_cmd";
    public static final String IPTV_VOD_SAVE_SITCOM_INDEX = "vod_save_index";
    public static final String IPTV_VOD_LEVEL_TYPE_TAG = "vod_level_type";
    public static final String IPTV_VOD_REMOTE_SEEK_MSG = "vod_remote_seek";
    public static final String IPTV_DEVICES_LICENSE_TAG = "device_license_content";
    public static final String IPTV_DATA_EPG_GROUP_ID = "epg_group_id";
    public static final String IPTV_NEW_VERSION_INFOS = "newVersionInfos";

    // 播放器类型
    public static final int IPTV_MEDIA_TYPE_DEFAULT = 99;
    public static final int IPTV_MEDIA_TYPE_LIVE = 100;
    public static final int IPTV_MEDIA_TYPE_VOD = 101;
    public static final int IPTV_MEDIA_TYPE_MUSIC = 102;
    public static final int IPTV_MEDIA_TYPE_LIVE_BACK = 103;
    public static final int IPTV_MEDIA_TYPE_LIVE_FORCE = 104;

    public static final int HOTEL_SETTING_SCREEN = 99;// 设置界面
    public static final int HOTEL_SPLASH_SCREEN = 100;// 启动界面
    public static final int HOTEL_WELCOME_SCREEN = 101; // 欢迎界面
    public static final int HOTEL_MAIN_MENU_SCREEN = 102;// 主界面
    public static final int HOTEL_LIVE_SCREEN = 103;// 直播主界面
    public static final int HOTEL_VOD_SCREEN = 104;// 点播主界面
    public static final int HOTEL_VOD_SUB_SCREEN = 105;// 点播二级界面

    public static final int HOTEL_MAIN_HS_SCREEN = 999;// 酒店服务主界面
    public static final int HOTEL_HS_INTRODUCE_SCREEN = 1000;// 酒店介绍界面
    public static final int HOTEL_ROOM_INTRODUCE_SCREEN = 1001;// 房型介绍界面
    public static final int HOTEL_SUBHS_INTRODUCE_SCREEN = 1002;// 旗下酒店界面
    public static final int HOTEL_CHECK_OUTIN_SCREEN = 1003;// 续房退房界面
    public static final int HOTEL_CLEAN_SCREEN = 1004;// 打扫卫生界面
    public static final int HOTEL_WAKEUP_SCREEN = 1005;// 叫醒服务界面
    public static final int HOTEL_ORDER_SCREEN = 1006;// 订餐服务界面
    public static final int HOTEL_ORDER_HISTORY_MAIN_SCREEN = 1007; // 订餐服务历史订单主界面
    public static final int HOTEL_ORDER_HISTORY_DETAILS_SCREEN = 1008; // 订餐服务历史订单详细界面

    public static final int HOTEL_TRAVEL_MAIN_SCREEN = 1999;// 智慧旅游主界面
    public static final int HOTEL_TRAVEL_CITY_SCENCE_SCREEN = 2000;// 城市风光界面
    public static final int HOTEL_TRAVEL_WEATHER_SCREEN = 2001;// 天气预报界面
    public static final int HOTEL_TRAVEL_WORLD_CLOCK_SCREEN = 2002;// 世界时钟界面
    public static final int HOTEL_TRAVEL_RATE_SCREEN = 2003;// 金融汇率界面
    public static final int HOTEL_TRAVEL_SPECAIL_SCREEN = 2004;// 本地特产界面
    public static final int HOTEL_TRAVEL_SPECAIL_HISTORY_MAIN_SCREEN = 2005;// 本地特产历史订单主界面
    public static final int HOTEL_TRAVEL_SPECAIL_HISTORY_DETAILS_SCREEN = 2006;// 本地特产历史订单详细界面

    public static final int APP_BUSINESS_MAIN_SCREEN = 2999;// 商务应用主界面
    public static final int APP_BUSINESS_ASSISTANT_SCREEN = 3000;// 商旅助手界面
    public static final int APP_BUSINESS_GAME_SCREEN = 3001;// 游戏应用界面
    public static final String APP_BUSINESS_TYPE = "app_type";// 商务应用类型标识
    public static final String APP_TYPE_ASSISTANT = "business";// 商旅助手类型
    public static final String APP_TYPE_GAME = "game";// 游戏娱乐类型

    public static final int SETTING_ACTIVITY_REQUEST_CODE = 3998;
    public static final int SETTING_MAIN_SCREEN = 3999;// 设置主界面
    public static final int SETTING_ADMIN_SCREEN = 4000;// 管理员界面
    public static final int SETTING_ACCOUNT_SCREEN = 4001;// 账户设置界面
    public static final int SETTING_CITYSETTING_SCREEN = 4002;// 城市设置界面
    public static final int SETTING_LANGUAGE_SCREEN = 4003;// 语言设置界面
    public static final int SETTING_ABOUT_SCREEN = 4004;// 关于界面
    public static final int SETTING_FEEDBACK_SCREEN = 4005;// 反馈界面
    public static final int SETTING_CLAUSE_SCREEN = 4006;// 相关政策界面
    public static final int SETTING_HELP_SCREEN = 4007;// /帮助信息界面

    public static final int PRISION_ENTER_SCREEN = 5007;// /入监教育界面
    public static final int PRISION_CLASS_SCREEN = 5008;// /三课教育界面

    public static final int VOD_NUM_PER_PAGE = 10;// 点播每页多少个电影单项
    public static final int VOD_NUM_SEARCH_PER_PAGE = 8;// 点播搜索每页显示多少个单项
    public static final int VOD_LIST_ITEM_WIDTH = 160;// 点播列表电影单项宽度
    public static final int VOD_LIST_ITEM_HEIGHT = 202;// 点播列表电影单项高度
    public static final int VOD_INTRODUCE_ITEM_WIDTH = 130;// 推荐列表电影单项宽度
    public static final int VOD_INTRODUCE_ITEM_HEIGHT = 160;// 推荐列表电影单项高度

    public static final String ORDER_TEMPLATE_TYPE_TAG = "order_tag";
    public static final String IPTV_LVB_X_SCREEN_TAG = "screen_tag";
    public static final String IPTV_LVB_X_MENU_ID_TAG = "id_tag";
    public static final String IPTV_LVB_X_MENU_CODE_TAG = "code_tag";
    public static final String IPTV_LVB_X_MENU_PATH_TAG = "path_tag";
    public static final String IPTV_LVB_X_MENU_TEMPLATE_TAG = "template_tag";
    public static final String IPTV_LVB_X_WINDOW_VOD_ITEM_DATA_TAG = "window_vod_data_tag";
    public static final String IPTV_LVB_X_WINDOW_EPISONDE_DATA_TAG = "window_episonde_data_tag";

    public static final String BAIHU_PRISION_PROGRAM_ENTER_TEACH = "入监教育";//
    public static final String BAIHU_PRISION_PROGRAM_CLASS_TEACH = "三课教育";//
    public static final String BAIHU_PRISION_PROGRAM_OUT_GUIDE = "出监指南";//
    public static final String BAIHU_PRISION_PROGRAM_NEWS_LINE = "新闻纵横";//
    public static final String BAIHU_PRISION_PROGRAM_PRISION_CULTURE = "文化白湖";//
    public static final String BAIHU_PRISION_PROGRAM_PRISON_HEART = "心理辅导";//
    public static final String BAIHU_PRISION_PROGRAM_LIVE = "数字电视";//
    public static final String BAIHU_PRISION_PROGRAM_PRISON_ARRAIRS = "狱务公开";//
    public static final String BAIHU_PRISION_PROGRAM_MOVIE_ENJOY = "影视欣赏";//

    public static final String BAIHU_PRISION_PROGRAM_SPECIAL_FOCUS = "特别关注";//
    public static final String BAIHU_PRISION_PROGRAM_REMOULD_STAR = "改造之星";//


    public static final String IPTV_LVB_X_ACTIVITY_SUBAPPACTIVITY = "com.hhzt.iptv.ui.SubAppActivity";

    public static final String IPTV_LVB_X_BASEPATH = Environment.getExternalStorageDirectory() + "/lvb/"; // 项目文件存储根路径
    public static final String IPTV_LVB_X_APP_BUSINESS_PATH = IPTV_LVB_X_BASEPATH + "apk/"; // 商务应用存储路径
    public static final String IPTV_LVB_X_BG_MUSIC_PATH = IPTV_LVB_X_BASEPATH + "bgmusic/";// 背景音乐存储路径
    public static final String IPTV_LVB_X_CLIENT_PATH = IPTV_LVB_X_BASEPATH + "LVBClient";// lvb客户端apk路径
    public static final String IPTV_LVB_X_BOOK_PATH = IPTV_LVB_X_BASEPATH + "book/";// lvb客户端书籍

    public static final String IPTV_LVB_X_LOCAL_BASEPATH = LVBXApp.getApp().getFilesDir().getPath() + "/lvb/"; // 项目文件本地存储根路径
    public static final String IPTV_LVB_X_LOCAL_APP_BUSINESS_PATH = IPTV_LVB_X_LOCAL_BASEPATH + "apk/"; // 商务应用本地存储路径
    public static final String IPTV_LVB_X_LOCAL_BG_MUSIC_PATH = IPTV_LVB_X_LOCAL_BASEPATH + "bgmusic/";// 背景音乐本地存储路径
    public static final String IPTV_LVB_X_LOCAL_CLIENT_PATH = IPTV_LVB_X_LOCAL_BASEPATH + "LVBClient";// lvb客户端本地存储apk路径
    public static final String IPTV_LVB_X_LOCAL_BOOK_PATH = IPTV_LVB_X_LOCAL_BASEPATH + "book/";// lvb客户端本地存储书籍


    /************************************
     * IPTV广播接收器标示begin
     ********************************/
    public static final String IPTV_LVB_X_BROADCAST_MSG_PAIR_AUTHORITH = "com.hhzt.iptv.lvb_x.pairtips";
    public static final String IPTV_LVB_X_BROADCAST_MSG_MEDIAPLAYER_STATE_CHANGE = "com.hhzt.iptv.ui.MediaPlayerActivity";
    public static final String IPTV_LVB_X_BROADCAST_MSG_HSJQ_SEAT = "com.hhzt.iptv.lvb_x.select_seat";
    public static final String IPTV_LVB_X_BROADCAST_MSG_HSJQ_SEAT_CANCEL = "com.hhzt.iptv.lvb_x.cancel_seat";
    public static final String IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE = "com.hhzt.iptv.lvb_x.update_date";
    public static final String IPTV_LVB_X_BROADCAST_HOME_MSG_UPDATE_DATE = "com.hhzt.iptv.lvb_x.home.update_date";
    // 长虹电视对接广播注册
    public static final String IPTV_LVB_X_BROADCAST_MSG_CHANGHONG_DTV = "com.changhong.system.systemkeyfor3rd";

    public static final String IPTV_LVB_X_BROADCAST_MSG_PLAY_UNLOCK = "com.hhzt.iptv.lvb_x.unlock";
    public static final String IPTV_LVB_X_BROADCAST_MSG_START_TICKER = "com.hhzt.iptv.lvb_x.ticker";
    public static final String IPTV_BOUNDLE_FORCE_PLAY_LOCK = "forceplayLock";
    public static final String IPTV_DATA_FORCE_PLAY_LOCK = "forceplayLock";

    //设置APK开机启动图片
    public static final String IPTV_APK_BOOT_IMAGE= "iptv_apk_boot_image";
    public static final String IPTV_APK_SING= "iptv_apk_sing";

    /************************************ IPTV广播接收器标示end ********************************/

    /************************************
     * 浮动窗口类型 begin
     ********************************/
    public static final int IPTV_FLOAT_WINDOW_TYPE_NONE_CONTROLLER = 0;
    public static final int IPTV_FLOAT_WINDOW_TYPE_BACK_CONTROLLER = 1;
    public static final int IPTV_FLOAT_WINDOW_TYPE_RC_CONTROLLER = 2;
    /************************************
     * 浮动窗口类型 end
     ********************************/

    public static final String MXPLAYER_APK_PACKAGE_NAME = "com.mxtech.videoplayer.ad";
    public static final String TOKENID_FILE_PATH = Environment.getExternalStorageDirectory() + "/tokenID.txt";
    public static final String LOCAL_TOKENID_FILE_PATH = LVBXApp.getApp().getFilesDir().getPath() + "/tokenID.txt";


    public static final String IPTV_LVB_W_WELCOME_VIDEO_PATH = IPTV_LVB_X_BASEPATH + "welcomevideo/";// 欢迎界面之前视频
    public static final String IPTV_LVB_W_CLIENT_PATH = IPTV_LVB_X_BASEPATH + "LVBClient";// lvb客户端apk路径
    public static final String IPTV_LVB_W_LOCAL_WELCOME_VIDEO_PATH = IPTV_LVB_X_BASEPATH + "welcomevideo/";// 欢迎界面之前视频

}

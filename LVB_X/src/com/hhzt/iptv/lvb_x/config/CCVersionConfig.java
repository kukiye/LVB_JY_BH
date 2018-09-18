package com.hhzt.iptv.lvb_x.config;

public class CCVersionConfig {
    /**
     * 开发调试模式标示位
     */
    public static final boolean DEBUG_MODE = true;
    /**
     * 调试模式标示符
     */
    public static final String DEBUG_TAG = "LVB_X";


    /**
     * 版本发布日期
     */
    public static final String IPTV_VERSION_DATE = "201712131905";

    /**
     * 版本发布信息
     */
    private static final String PRODUCT = "HHZT";// 产品名称
    private static final String SERIES = "IPTV";// 产品系列
    private static final String LINE = "PI";// 产品线类别
    //	private static final String BRANCH = "LZJY";// 分支编码
    private static final String BRANCH = "BHJY";// 分支编码
    public static int TO_CLIENT_NUMBER = 1;// 发给客户版本的次数

    /**
     * LVB_X_HH_MAIN_Ver1.3.5.4_201501021215 完整信息标示
     */
    public static final String VERSION_HEAD = PRODUCT + "_" + SERIES + "_" + LINE + "_" + BRANCH;
}

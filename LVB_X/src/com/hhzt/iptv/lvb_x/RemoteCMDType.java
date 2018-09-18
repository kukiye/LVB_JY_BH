package com.hhzt.iptv.lvb_x;

public class RemoteCMDType {
	public static final int DEFAULT_CDM = 0;

	public static final int PAD_CMD = 1; // 虚拟遥控控制指令
	public static final int PAD_UP = 2; // 方向键向上
	public static final int PAD_DOWN = 3;// 方向键向下
	public static final int PAD_LEFT = 4;// 方向键向左
	public static final int PAD_RIGHT = 5;// 方向键向右
	public static final int PAD_OK = 6;// 确定键

	public static final int PAD_DELETE = 7;// 删除键
	public static final int PAD_BACK = 8;// 返回键

	public static final int PAD_NUM0 = 9;// 数字键0
	public static final int PAD_NUM1 = 10; // 数字键1
	public static final int PAD_NUM2 = 11;// 数字键2
	public static final int PAD_NUM3 = 12;// 数字键3
	public static final int PAD_NUM4 = 13;// 数字键4
	public static final int PAD_NUM5 = 14;// 数字键5
	public static final int PAD_NUM6 = 15;// 数字键6
	public static final int PAD_NUM7 = 16;// 数字键7
	public static final int PAD_NUM8 = 17;// 数字键8
	public static final int PAD_NUM9 = 18;// 数字键9

	public static final int PAD_STAR = 19;// *键
	public static final int PAD_POUND = 20;// #键

	public static final int PAD_VOLUME_ADD = 21; // 音量增大按键
	public static final int PAD_VOLUME_REDUCE = 22; // 音量降低按键
	public static final int PAD_VOLUME_MUTE = 23; // 静音按键

	public static final int PAD_MENU = 24; // 菜单按键

	public static final int IPTV_PAIRE_CMD = 1999; // 互动配对指令

	public static final int MEDIA_LIVE_PUSH = 2000; // 直播推流指令
	public static final int MEDIA_VOD_PUSH = 2001;// 点播推流指令
	public static final int MEDIA_AUDIO_PUSH = 2002; // 音乐推流指令
	public static final int MEDIA_LIVE_PULL = 2003;// 直播拉流指令
	public static final int MEDIA_VOD_PULL = 2004;// 点播拉流指令
	public static final int MEDIA_AUDIO_PULL = 2005; // 音乐拉流指令
	public static final int MEDIA_IMAGE_PUSH = 2006;// 图片推流指令

	public static final int VOD_MEDIA_PLAY = 2007;// 点播播放指令
	public static final int VOD_MEDIA_PAUSE = 2008;// 点播暂停指令

	public static final int VOD_MEDIA_SEEK = 2009;// 点播拖拉指令
	public static final int VOD_MEDIA_PRE = 2010; // 点播上一集指令
	public static final int VOD_MEDIA_NEXT = 2011; // 点播下一集指令

	public static final int HSJQ_PAD_SEAT_SELECTION = 3001; // 皇室假期选座指令
	public static final int HSJQ_PAD_SEAT_CANCEL = 3002; // 皇室假期取消留座

	public static final int HSJQ_PAD_SOUND_CHANGE = 3003; // 皇室假期平板声音控制指令
}

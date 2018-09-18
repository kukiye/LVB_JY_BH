/**
 * 作者：   Johnson
 * 日期：   2014年6月11日下午3:26:53
 * 包名：    com.hhzt.iptv.lvb_x.mgr
 * 工程名：LVB_X
 * 文件名：UserMgr.java
 */
package com.hhzt.iptv.lvb_x.mgr;

import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

import android.content.Context;
import android.content.SharedPreferences;

public class UserMgr {
	public static String getCheckInTime() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String time = sp.getString(Constant.IPTV_USER_HOTEL_CHECK_IN, "");
		return time;
	}

	public static void setCheckInTime(String time) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_HOTEL_CHECK_IN, time).commit();
	}

	public static boolean checkIsNewClient(String newCheckInTime) {
		String savedTime = getCheckInTime();
		boolean tag = StringUtil.isEmpty(savedTime) ? true : (savedTime.equalsIgnoreCase(newCheckInTime) ? false : true);
		return tag;
	}

	public static String getHotelName() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_USER_HOTEL_NAME, "");
		return name;
	}

	public static void setHotelName(String hotelName) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_HOTEL_NAME, hotelName).commit();
	}

	public static String getHotelRoomNo() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_USER_HOTEL_ROOM_NUMBER, "");
		return name;
	}

	public static void setHotelRoomNo(String room) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_HOTEL_ROOM_NUMBER, room).commit();
	}

	public static String getUserName() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_USER_USER_NAME, "");
		return name;
	}

	public static void setUserName(String userName) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_USER_NAME, userName).commit();
	}

	public static String getAcountPasswd() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String passwd = sp.getString(Constant.IPTV_USER_USER_PASSWD, "");
		return passwd;
	}

	public static void setAcountPasswd(String passwd) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_USER_PASSWD, passwd).commit();
	}

	public static boolean getBgMusicState() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		boolean flag = sp.getBoolean(Constant.IPTV_SYS_MSG_BGMUSIC_STATE_TAG, false);
		return flag;
	}

	public static void setBgMusicState(boolean flag) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putBoolean(Constant.IPTV_SYS_MSG_BGMUSIC_STATE_TAG, flag).commit();
	}

	public static String getServerIp() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String serverIp = sp.getString(Constant.IPTV_USER_USER_SERVERIP, "");
		return serverIp;
	}

	public static void setServerIp(String serverIp) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_USER_SERVERIP, serverIp).commit();
	}

	public static String getPairedIp() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String pairedIp = sp.getString(Constant.IPTV_USER_PAIRED_IP, "");
		return pairedIp;
	}

	public static void setPairedIp(String pairedIp) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_PAIRED_IP, pairedIp).commit();
	}

	public static String getPairedMacAdress() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String macAddress = sp.getString(Constant.IPTV_USER_PAIRED_MAC, "");
		return macAddress;
	}

	public static void setPairedMacAdress(String macAdress) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_PAIRED_MAC, macAdress).commit();
	}

	public static boolean getPairedTag() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		boolean pairedTag = sp.getBoolean(Constant.IPTV_USER_PAIRED_TAG, false);
		return pairedTag;
	}

	public static void setPairedTag(boolean pairedTag) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putBoolean(Constant.IPTV_USER_PAIRED_TAG, pairedTag).commit();
	}

	public static String getChannelGroup() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_USER_CHANNER_GROUP, "");
		return name;
	}

	public static void setChannelGroup(String ChannelGroup) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_CHANNER_GROUP, ChannelGroup).commit();
	}

	public static String getWifiName() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_USER_WIFI_NAME, "");
		return name;
	}

	public static void setWifiName(String wifiName) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_WIFI_NAME, wifiName).commit();
	}

	public static String getInteracPassword() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_USER_INTERACTIVE_PASSWD, Constant.LVC_HOTSPOT_DEFAULT_PASSWD);
		return name;
	}

	public static void setInteracPassword(String InteracPassword) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_INTERACTIVE_PASSWD, InteracPassword).commit();
	}

	public static boolean getSSIDHideTag() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		boolean hide = sp.getBoolean(Constant.IPTV_USER_SSI_HIDE_TAG, false);
		return hide;
	}

	public static void setSSIDHideTag(boolean hide) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putBoolean(Constant.IPTV_USER_SSI_HIDE_TAG, hide).commit();
	}

	public static String isEncrypted() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String result = sp.getString(Constant.IPTV_USER_IS_ENCYPTED, "off");
		return result;
	}

	public static void setEncrypted(String isEncrypted) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_IS_ENCYPTED, isEncrypted).commit();
	}

	public static String getHotelLogoUrl() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String result = sp.getString(Constant.IPTV_USER_HOTEL_LOGO_URL, "");
		return result;
	}

	public static void setHotelLogoUrl(String url) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_HOTEL_LOGO_URL, url).commit();
	}

	public static String getHotelImageUrl() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String result = sp.getString(Constant.IPTV_USER_HOTEL_IMAGE_URL, "");
		return result;
	}

	public static void setHotelImageUrl(String url) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_HOTEL_IMAGE_URL, url).commit();
	}

	public static void setWeatherAirCode(String cityCode) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_HOTEL_CITY_AIR_CODE, cityCode).commit();
	}

	public static String getWeatherCityAirCode() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String cityCode = sp.getString(Constant.IPTV_USER_HOTEL_CITY_AIR_CODE, Constant.IPTV_DEFAULT_AIRCITY_CODE);
		return cityCode;
	}

	public static void setWeatherNumCode(String cityCode) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_HOTEL_CITY_NUM_CODE, cityCode).commit();
	}

	public static String getWeatherCityNumCode() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String cityCode = sp.getString(Constant.IPTV_USER_HOTEL_CITY_NUM_CODE, null);
		return cityCode;
	}

	public static void setWeatherCityName(String cityName) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_HOTEL_CITY_NAME, cityName).commit();
	}

	public static String getWeatherCityName() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String cityName = sp.getString(Constant.IPTV_USER_HOTEL_CITY_NAME, "");
		return cityName;
	}

	public static void setWeatherCitySettedFlag(boolean flag) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putBoolean(Constant.IPTV_USER_HOTEL_CITY_SETTED, flag).commit();
	}

	public static boolean getWeatherCitySettedFlag() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		boolean flag = sp.getBoolean(Constant.IPTV_USER_HOTEL_CITY_SETTED, false);
		return flag;
	}

	public static String getHotSpotEnable() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String tag = sp.getString(Constant.IPTV_USER_HOTEL_HOTSPOT_TAG, "on");
		return tag;
	}

	public static void setHotSpotEnable(String tag) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_HOTEL_HOTSPOT_TAG, tag).commit();
	}

	public static String getVodFreeEnable() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		// 默认需要收费
		String tag = sp.getString(Constant.IPTV_USER_HOTEL_VOD_FREE_ENABLE, "off");
		return tag;
	}

	public static void setVodFreeEnable(String tag) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_USER_HOTEL_VOD_FREE_ENABLE, tag).commit();
	}

	public static int getSavedLiveChannelNumber() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		int channelNumber = sp.getInt(Constant.IPTV_LIVE_CHANNEL_NUMBER, 0);
		return channelNumber;
	}

	public static void setSavedLiveChannelNumber(int channelNumber) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putInt(Constant.IPTV_LIVE_CHANNEL_NUMBER, channelNumber).commit();
	}

	public static void setLiveMenuName(String name) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_MENU_ITEM_LIVE_NAME, name).commit();
	}

	public static String getLiveMenuName() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_MENU_ITEM_LIVE_NAME, "");
		return name;
	}

	public static void setVodMenuName(String name) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_MENU_ITEM_VOD_NAME, name).commit();
	}

	public static String getVodMenuName() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_MENU_ITEM_VOD_NAME, "");
		return name;
	}
	
	public static void setVodMenuCode(String name) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_MENU_ITEM_VOD_CODE, name).commit();
	}

	public static String getVodMenuCode() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_MENU_ITEM_VOD_CODE, "");
		return name;
	}

	public static void setHSMenuName(String name) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_MENU_ITEM_HS_NAME, name).commit();
	}

	public static String getHSMenuName() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_MENU_ITEM_HS_NAME, "");
		return name;
	}

	public static void setHSMenuId(int id) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putInt(Constant.IPTV_MENU_ITEM_HS_ID, id).commit();
	}

	public static int getHSMenuId() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		int id = sp.getInt(Constant.IPTV_MENU_ITEM_HS_ID, 0);
		return id;
	}

	public static void setHSMenuCode(String menuCode) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_MENU_ITEM_HS_CODE, menuCode).commit();
	}

	public static String getHSMenuCode() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String menucode = sp.getString(Constant.IPTV_MENU_ITEM_HS_CODE, "");
		return menucode;
	}

	public static void setTravelMenuName(String name) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_MENU_ITEM_TRAVEL_NAME, name).commit();
	}

	public static String getTravelMenuName() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_MENU_ITEM_TRAVEL_NAME, "");
		return name;
	}

	public static void setTravelMenuId(int id) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putInt(Constant.IPTV_MENU_ITEM_TRAVEL_ID, id).commit();
	}

	public static int getTravelMenuId() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		int id = sp.getInt(Constant.IPTV_MENU_ITEM_TRAVEL_ID, 0);
		return id;
	}

	public static void setAppMenuName(String name) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_MENU_ITEM_APP_NAME, name).commit();
	}

	public static String getAppMenuName() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_MENU_ITEM_APP_NAME, "");
		return name;
	}

	public static void setAppMenuId(int id) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putInt(Constant.IPTV_MENU_ITEM_APP_ID, id).commit();
	}

	public static int getAppMenuId() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		int id = sp.getInt(Constant.IPTV_MENU_ITEM_APP_ID, 0);
		return id;
	}

	public static void setThirdAppMenuCode(String menuCode) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_MENU_ITEM_THIRD_MARKET_MENUCODE, menuCode).commit();
	}

	public static String getThirdAppMenuCode() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String menuCode = sp.getString(Constant.IPTV_MENU_ITEM_THIRD_MARKET_MENUCODE, "");
		return menuCode;
	}

	public static void setThirdAppMenuName(String name) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_MENU_ITEM_THIRD_MARKET_NAME, name).commit();
	}

	public static String getThirdAppMenuName() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String name = sp.getString(Constant.IPTV_MENU_ITEM_THIRD_MARKET_NAME, "");
		return name;
	}

	public static void setVodSavedIndex(int index) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putInt(Constant.IPTV_VOD_SAVE_SITCOM_INDEX, index).commit();
	}

	public static int getVodSavedIndex() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		int index = sp.getInt(Constant.IPTV_VOD_SAVE_SITCOM_INDEX, 0);
		return index;
	}

	public static void setVodTypeLeve(int level) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putInt(Constant.IPTV_VOD_LEVEL_TYPE_TAG, level).commit();
	}

	public static int getVodTypeLeve() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		int level = sp.getInt(Constant.IPTV_VOD_LEVEL_TYPE_TAG, 0);
		return level;
	}

	public static void setDeviceLicensecontent(String license) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_DEVICES_LICENSE_TAG, license).commit();
	}

	public static String getDeviceLicensecontent() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String license = sp.getString(Constant.IPTV_DEVICES_LICENSE_TAG, null);
		return license;
	}

	public static void setEpgGroupId(String id) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		sp.edit().putString(Constant.IPTV_DATA_EPG_GROUP_ID, id).commit();
	}

	public static String getEpgGroupId() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		String id = sp.getString(Constant.IPTV_DATA_EPG_GROUP_ID, "1");
		return id;
	}
	
	public static int getSavedLiveChannelIndex() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_PRIVATE);
		int channelIndex = sp.getInt(Constant.IPTV_LIVE_CHANNEL_INDEX, 0);
		return channelIndex;
	}

	public static void setSavedLiveChannelIndex(int channelIndex) {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_PRIVATE);
		sp.edit().putInt(Constant.IPTV_LIVE_CHANNEL_INDEX, channelIndex).commit();
	}

	public static String getApkImage() {
		SharedPreferences sp = LVBXApp.getApp().getSharedPreferences(Constant.IPTV_SYS_CONFIG, Context.MODE_MULTI_PROCESS);
		return sp.getString(Constant.IPTV_APK_BOOT_IMAGE,"fail");
	}

}

/**
 * 作者：   Johnson
 * 日期：   2014年6月17日下午4:34:16
 * 包名：    com.hhzt.iptv.lvb_x.mgr
 * 工程名：LVB_X
 * 文件名：UrlMgr.java
 */
package com.hhzt.iptv.lvb_x.mgr;

import android.os.Environment;

import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.utils.DeviceUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

import java.io.File;

public class UrlMgr {
	/**
	 * 通过group id来区分数据\不同账号分属不同group，以此来获取不同的数据
	 * http://218.17.13.108:9999/epgjy/json/channel/all/10005?locale=zh_CN
	 * 
	 * @return
	 */
	public static String getHeadDataGroupId() {
		return "?groupId=" + UserMgr.getEpgGroupId() + "&account=" + UserMgr.getUserName();
	}

	/**
	 * 通过group id来区分数据\不同账号分属不同group，以此来获取不同的数据
	 * 
	 * @return
	 */
	public static String getFootDataGroup() {
		return "&groupId=" + UserMgr.getEpgGroupId() + "&account=" + UserMgr.getUserName();
	}

	/**
	 * 组装主类型数据url
	 * 
	 * @return
	 */
	public static String getMainmenuUrl() {
		return SystemMgr.getBaseHostUrl() + "menu" + getHeadDataGroupId();
	}

	public static String getWelcomVideoStoragePath() {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return Constant.IPTV_LVB_W_WELCOME_VIDEO_PATH;
		} else {
			return Constant.IPTV_LVB_W_LOCAL_WELCOME_VIDEO_PATH;
		}
	}

	/**
	 * 组装子类型数据url
	 * 
	 * @param menuId
	 * @return
	 */
	public static String getSubMenuUrl(int menuId) {
		return SystemMgr.getBaseHostUrl() + "menu" + File.separator + menuId + getHeadDataGroupId();
	}

	/**
	 * 组装三级类型数据url
	 * 
	 * @param menuCode
	 * @return
	 */
	public static String getThirdMenuUrl(String menuCode) {
		return SystemMgr.getBaseHostUrl() + "infocontent" + File.separator + menuCode
				+ getHeadDataGroupId();
	}

	/********************************** 欢迎界面 begin *********************************/
	/**
	 * 获取欢迎界面url
	 * 
	 * @param iptvUserName
	 * @return
	 */
	public static String getWelcomeInfosUrl(String iptvUserName) {
		return SystemMgr.getBaseHostUrl() + "hotel?iptvusername=" + iptvUserName
				+ getFootDataGroup();
	}

	/**
	 * 登陆接口
	 * 
	 * @param userName
	 * @param passwd
	 * @param serverIP
	 * @return
	 */
	public static String getLoginInfoUrl(String userName, String passwd, String serverIP,
			String version, String token) {
		String tempUrl = "http://" + serverIP + "/epg/json/";
		SystemMgr.setBaseHostIp(serverIP);
		SystemMgr.setBaseHostUrl(tempUrl);
		String mac = DeviceUtil.getIptvMacString();
		String url = null;
		if (StringUtil.isEmpty(token)) {
			url = SystemMgr.getBaseHostUrl() + "profile" + File.separator + userName
					+ "?iptvpassword=" + passwd + "&mac=" + mac + "&stbversion=" + version
					+ "&token=";
		} else {
			url = SystemMgr.getBaseHostUrl() + "profile" + File.separator + userName
					+ "?iptvpassword=" + passwd + "&mac=" + mac + "&stbversion=" + version
					+ "&token=" + token;
		}
		return url;
	}

	/**
	 * 移动设备通过该接口获取登陆数据信息
	 * 
	 * @param serverIp
	 * @param passwd
	 * @return
	 */
	public static String getPairedPasswdInfosUrl(String serverIp, String passwd) {
		String tempUrl = "http://" + serverIp + "/epg/json/";
		SystemMgr.setBaseHostUrl(tempUrl);
		SystemMgr.setBaseHostIp(serverIp);
		return tempUrl + "profile/interaction/" + passwd;
	}

	/********************************** 欢迎界面 end *********************************/

	/********************************** 主界面 begin *********************************/
	/**
	 * 获取天气预报信息
	 */
	public static String getWeatherInUrl(String cityCode, String dayNumber) {
		return SystemMgr.getBaseHostUrl() + "weather/current/" + cityCode + File.separator
				+ dayNumber + getHeadDataGroupId();
	}

	/**
	 * 获取时间信息
	 */
	public static String getTimeInfoUrl() {
		return SystemMgr.getBaseHostUrl() + "profile/timesyn";
	}

	/**
	 * 获取所有显示所需配置信息
	 * 
	 * @return
	 */
	public static String getViewConfigUrl() {
		return SystemMgr.getBaseHostUrl() + "hotel/variable" + getHeadDataGroupId();
	}

	/********************************** 主界面 end *********************************/

	/********************************** 酒店服务 begin *********************************/
	public static String getCheckInOutHistoryUrl(String userName) {
		return SystemMgr.getBaseHostUrl() + "hotelcall/list/12/" + userName + getHeadDataGroupId();
	}

	public static String getWakeUpHistoryUrl(String userName) {
		return SystemMgr.getBaseHostUrl() + "hotelcall/list/3/" + userName + getHeadDataGroupId();
	}

	public static String getBoxVersionInfoUrl(String userAccount) {
		return SystemMgr.getBaseHostUrl() + "appinfo" + File.separator + 1 + "?iptvusername="
				+ userAccount + getFootDataGroup();
	}

	public static String getMXPlayerUrl(String userAccount) {
		return SystemMgr.getBaseHostUrl() + "appinfo" + File.separator + 9 + "?iptvusername="
				+ userAccount + getFootDataGroup();
	}

	public static String getMobileVersionInfoUrl(String userAccount) {
		return SystemMgr.getBaseHostUrl() + "appinfo" + File.separator + 2 + "?iptvusername="
				+ userAccount + getFootDataGroup();
	}

	public static String getCleanUpHistoryUrl(String userName) {
		return SystemMgr.getBaseHostUrl() + "hotelcall/list/4/" + userName + getHeadDataGroupId();
	}

	public static String getDeleteUnfinishServiceUrl(int serviceId) {
		return SystemMgr.getBaseHostUrl() + "hotelcall/delete/" + serviceId + getHeadDataGroupId();
	}

	public static String getCommitCheckOutUrl(String roomname, String userName,
			String appointmenttime) {
		return SystemMgr.getBaseHostUrl() + "hotelcall/save/1?roomname=" + roomname
				+ "&iptvusername=" + userName + "&appointmenttime=" + appointmenttime
				+ getFootDataGroup();
	}

	public static String getCommitCheckInUrl(String roomname, String userName,
			String appointmenttime) {
		return SystemMgr.getBaseHostUrl() + "hotelcall/save/2?roomname=" + roomname
				+ "&iptvusername=" + userName + "&appointmenttime=" + appointmenttime
				+ getFootDataGroup();
	}

	public static String getCommitWakeupUrl(String roomname, String userName, String appointmenttime) {
		return SystemMgr.getBaseHostUrl() + "hotelcall/save/3?roomname=" + roomname
				+ "&iptvusername=" + userName + "&appointmenttime=" + appointmenttime
				+ getFootDataGroup();
	}

	public static String getCommitCleanUpUrl(String roomname, String userName,
			String appointmenttime) {
		return SystemMgr.getBaseHostUrl() + "hotelcall/save/4?roomname=" + roomname
				+ "&iptvusername=" + userName + "&appointmenttime=" + appointmenttime
				+ getFootDataGroup();
	}

	/********************************** 酒店服务 end *********************************/

	/********************************** 智慧旅游 begin *********************************/
	public static String getDefaultCityUrl() {
		return SystemMgr.getBaseHostUrl() + "city/local" + getHeadDataGroupId();
	}

	public static String getDefaultRateInfosUrl() {
		return SystemMgr.getBaseHostUrl() + "rate" + getHeadDataGroupId();
	}

	public static String getWeatherDetailUrl(String cityCode, int weatherDay) {
		return SystemMgr.getBaseHostUrl() + "weather/current/" + cityCode + File.separator
				+ weatherDay + getHeadDataGroupId();
	}

	/********************************** 智慧旅游 end *********************************/

	/********************************** 电视直播 begin *********************************/
	public static String getMainLiveListUrl(String channelGroup) {
		// return SystemMgr.getBaseHostUrl() + "channel/all/" + channelGroup +
		// getHeadDataGroupId();
		return SystemMgr.getBaseHostUrl() + "channel/all/" + UserMgr.getUserName();
	}

	public static String getSubLiveListUrl(int channelId, int pageNo, int pageSize) {
		return SystemMgr.getBaseHostUrl() + "schedule/" + channelId + "?pageNo=" + pageNo
				+ "&pageSize=" + pageSize + getFootDataGroup();
	}

	public static String getSubLiveBackListUrl(int channelId, String date, int pageNo, int pageSize) {
		return SystemMgr.getBaseHostUrl() + "schedule/" + channelId + "/" + date + "?pageNo="
				+ pageNo + "&pageSize=" + pageSize + getFootDataGroup();
	}

	/********************************** 电视直播 end *********************************/

	/********************************** 视频点播 begin *********************************/
	public static String getVodTypeListUrl() {
		return SystemMgr.getBaseHostUrl() + "category/10" + getHeadDataGroupId();
	}

	/** @param menuCode */
	public static String getVodTypeListUrl(String menuCode) {
		return SystemMgr.getBaseHostUrl() + "category/10" + "?menucode=" + menuCode
				+ getFootDataGroup();
	}

	public static String getVodDetailListUrl(int categoryId, int pageNo, int pageSize, int level) {
		// return SystemMgr.getBaseHostUrl() + "program/10" + File.separator +
		// categoryId + "?pageNo=" + pageNo + "&pageSize=" + pageSize +
		// "&level="
		// + level + getFootDataGroup();
		return SystemMgr.getBaseHostUrl() + "program/10/" + UserMgr.getUserName() + "/"
				+ categoryId + "?pageNo=" + pageNo + "&pageSize=" + pageSize + "&level=" + level
				+ getFootDataGroup();
	}

	public static String getVodItemPlayMessageUrl(int id) {
		return SystemMgr.getBaseHostUrl() + "program/" + id + getHeadDataGroupId();
	}

	public static String getVodIntroduceItemUrl(int categoryId, int level) {
		// return SystemMgr.getBaseHostUrl() + "program/" + categoryId +
		// "/recommend" + "?level=" + level + getFootDataGroup();
		return SystemMgr.getBaseHostUrl() + "program/" + UserMgr.getUserName() + "/" + categoryId
				+ "/recommend" + "?level=" + level + getFootDataGroup();
	}

	public static String getVodSearchResultUrl(String Keyword, int pageNo, int pageSize, int level) {
		return SystemMgr.getBaseHostUrl() + "program/search?searchname=" + Keyword + "&pageNo="
				+ pageNo + "&pageSize=" + pageSize + "&level=" + level + getFootDataGroup();
	}

	/********************************** 视频点播 end *********************************/

	/********************************** 订餐服务、本地特产 begin *********************************/
	private static int getMenuId(int typeTag) {
		int menuId = 0;
		switch (typeTag) {
		case Constant.HOTEL_ORDER_SCREEN:
			menuId = 50;
			break;
		case Constant.HOTEL_TRAVEL_SPECAIL_SCREEN:
			menuId = 51;
			break;
		default:
			break;
		}

		return menuId;
	}

	private static String getTypeTag(int typeTag) {
		String tag = "";
		switch (typeTag) {
		case Constant.HOTEL_ORDER_SCREEN:
		case Constant.HOTEL_ORDER_HISTORY_MAIN_SCREEN:
		case Constant.HOTEL_ORDER_HISTORY_DETAILS_SCREEN:
			tag = "dish";
			break;
		case Constant.HOTEL_TRAVEL_SPECAIL_SCREEN:
		case Constant.HOTEL_TRAVEL_SPECAIL_HISTORY_MAIN_SCREEN:
		case Constant.HOTEL_TRAVEL_SPECAIL_HISTORY_DETAILS_SCREEN:
			tag = "goods";
			break;
		default:
			break;
		}

		return tag;
	}

	public static String getOrderMainTypeUrl(int typeTag) {
		return SystemMgr.getBaseHostUrl() + "category/" + getMenuId(typeTag) + getHeadDataGroupId();
	}

	public static String getOrderSubMenuInfosUrl(int typeTag, int categoryId, int pageNo,
			int pageSize) {
		return SystemMgr.getBaseHostUrl() + getTypeTag(typeTag) + "/list?categoryId=" + categoryId
				+ "&pageNo=" + pageNo + "&pageSize=" + pageSize + getFootDataGroup();
	}

	public static String getOrderShopCarInfosUrl(int typeTag, String username) {
		return SystemMgr.getBaseHostUrl() + getTypeTag(typeTag) + "/getcart?username=" + username
				+ getFootDataGroup();
	}

	public static String getCommitShopCarActionUrl(int typeTag, String username) {
		return SystemMgr.getBaseHostUrl() + getTypeTag(typeTag) + "/addorder?username=" + username
				+ "&tel=13312345678" + getFootDataGroup();
	}

	public static String getAddToShopCarActionUrl(int typeTag, String username, int dishId,
			int number) {
		return SystemMgr.getBaseHostUrl() + getTypeTag(typeTag) + "/addtocart?username=" + username
				+ "&" + getTypeTag(typeTag) + "Id=" + dishId + "&number=" + number + "&merge=no"
				+ getFootDataGroup();
	}

	public static String getHistoryOrderListUrl(int typeTag, String username) {
		return SystemMgr.getBaseHostUrl() + getTypeTag(typeTag) + "/myorders?username=" + username
				+ getFootDataGroup();
	}

	public static String getHistoryOrderModelDetailUrl(int typeTag, int orderid) {
		return SystemMgr.getBaseHostUrl() + getTypeTag(typeTag) + "/orderdetail?orderid=" + orderid
				+ getFootDataGroup();
	}

	public static String getDeleteOrderSingleModelUrl(int typeTag, String dishcartids) {
		return SystemMgr.getBaseHostUrl() + getTypeTag(typeTag) + "/delete?" + getTypeTag(typeTag)
				+ "cartids=" + dishcartids + getFootDataGroup();
	}

	public static String getClearAllShopCarUrl(int typeTag, String username) {
		return SystemMgr.getBaseHostUrl() + getTypeTag(typeTag) + "/clearcart?username=" + username
				+ getFootDataGroup();
	}

	public static String getShopCarModelNumUrl(int typeTag, String username) {
		return SystemMgr.getBaseHostUrl() + getTypeTag(typeTag) + "/getcarttotal?username="
				+ username + getFootDataGroup();
	}

	public static String getBgMusicUrl() {
		return SystemMgr.getBaseHostUrl() + "music/musiclist?musiccategoryId=2"
				+ getFootDataGroup();
	}

	/********************************** 订餐服务、本地特产 end *********************************/

	/********************************** 商务应用 begin *********************************/
	public static String getSubAppUrl(String type) {
		return SystemMgr.getBaseHostUrl() + "app/" + type + "?pageNo=1&pageSize=100"
				+ getFootDataGroup();
	}

	/********************************** 商务应用 end *********************************/

	public static String getCitySettingUrl() {
		return SystemMgr.getBaseHostUrl() + "city/list" + getHeadDataGroupId();
	}

	/********************************** 消息推送 begin *********************************/
	public static String getAllNewsPulledInfoUrl(String iptvUserName, int pageNo, int pageSize) {
		return SystemMgr.getBaseHostUrl() + "bulletin/all?iptvusername=" + iptvUserName
				+ "&pageNo=" + pageNo + "&pageSize=" + pageSize + getFootDataGroup();
	}

	public static String getUnreadNewsInfosUrl(String iptvUserName, int pageNo, int pageSize) {
		return SystemMgr.getBaseHostUrl() + "bulletin/unread?iptvusername=" + iptvUserName
				+ "&pageNo=" + pageNo + "&pageSize=" + pageSize + getFootDataGroup();
	}

	public static String getNewsReadedTag(String iptvUserName, int bulletinid) {
		return SystemMgr.getBaseHostUrl() + "bulletin/markread?iptvusername=" + iptvUserName
				+ "&bulletinid=" + bulletinid + getFootDataGroup();
	}

	public static String getMsgServerInfoUrl() {
		return SystemMgr.getBaseHostUrl() + "bulletin/serveraddress" + getHeadDataGroupId();
	}

	public static String getUnReadedMsgNumUrl(String iptvUserName) {
		return SystemMgr.getBaseHostUrl() + "bulletin/unreadtotal?iptvusername=" + iptvUserName
				+ getFootDataGroup();
	}

	// 后台管理
	public static String getBackMgrUrl(String url) {
		return "http://" + url + "/portal";
	}

	/********************************** 消息推送 end *********************************/

	/********************************** 计费Begin *********************************/
	// 查账单
	public static String getBillCheckUrl(String roomNumber) {
		return SystemMgr.getBaseHostUrl() + "pmsa/billquery?roomno=" + roomNumber
				+ getFootDataGroup();
	}

	// 是否已经购买
	public static String checkIsHadPayedUrl(String roomNumber, int programId) {
		return SystemMgr.getBaseHostUrl() + "pmsa/isorder?roomno=" + roomNumber + "&programid="
				+ programId + getFootDataGroup();
	}

	// 购买抛账
	public static String getThrowAccountUrl(String roomNumber, String price, int quantity,
			int programId, String remark) {
		return SystemMgr.getBaseHostUrl() + "pmsa/posting?roomno=" + roomNumber + "&price=" + price
				+ "&quantity=" + quantity + "&programid=" + programId + "&remark=" + remark
				+ getFootDataGroup();
	}

	/********************************** 计费End *********************************/
	/********************************** license授权 begin *************************/
	public static String getLicenseServerAddressUrl() {
		return SystemMgr.getBaseHostUrl() + "config/system/license_server";
	}

	public static String getLicenseCodeUrl() {
		return SystemMgr.getBaseHostUrl() + "config/system/license_code";
	}

	public static String getLicenseNoDeviceInfoUrl(String licenseServer, String hardwareinfo,
			String systeminfo, String appinfo) {
		return licenseServer + "/json/auth/again?hardwareinfo=" + hardwareinfo + "&systeminfo="
				+ systeminfo + "&appinfo=" + appinfo;
	}

	public static String getActiveWithLicenseAndDeviceInfo(String licenseServer,
			String hardwareinfo, String systeminfo, String appinfo, String licensecode) {
		return licenseServer + "/json/auth/first?hardwareinfo=" + hardwareinfo + "&systeminfo="
				+ systeminfo + "&appinfo=" + appinfo + "&licensecode=" + licensecode;
	}

	/********************************** license授权 end *************************/

	/********************************** 设备可用性 begin *************************/
	public static String getDeviceStatusUrl(String account) {
		return SystemMgr.getBaseHostUrl() + "roomstatus?account=" + account;
	}

	/********************************** 设备可用性 end *************************/

	/********************************** 文件存储路径 begin *************************/

	public static String getClientStoragePath() {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return Constant.IPTV_LVB_X_CLIENT_PATH;
		} else {
			return Constant.IPTV_LVB_X_LOCAL_CLIENT_PATH;
		}
	}

	/**
	 * 客户端存储书籍
	 * @return
	 */
	public static String getClientStorageBookPath() {
		
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return Constant.IPTV_LVB_X_BOOK_PATH;
		} else {
			return Constant.IPTV_LVB_X_LOCAL_BOOK_PATH;
		}
	}

	public static String getBgMusicStoragePath() {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return Constant.IPTV_LVB_X_BG_MUSIC_PATH;
		} else {
			return Constant.IPTV_LVB_X_LOCAL_BG_MUSIC_PATH;
		}
	}

	public static String getAppBusinessStoragePath() {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return Constant.IPTV_LVB_X_APP_BUSINESS_PATH;
		} else {
			return Constant.IPTV_LVB_X_LOCAL_APP_BUSINESS_PATH;
		}
	}

	public static String getTokenIDStoragePath() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return Constant.TOKENID_FILE_PATH;
		} else {
			return Constant.LOCAL_TOKENID_FILE_PATH;
		}
	}

	/********************************** 文件存储路径 end *************************/

	/**
	 * 获取内网账号信息url
	 * 
	 * @param serverIp
	 * @param macAddress
	 * @return
	 */
	public static String getAccoutInfoUrl(String serverIp, String macAddress) {
		return "http://" + serverIp + "/epg/json/stbinit/cfg?mac=" + macAddress;
	}

	public static String getForcePlayMissionUrl() {
		return SystemMgr.getBaseHostUrl() + "forceplay/status" + getHeadDataGroupId();
	}

	/**
	 * 资讯分类
	 * 
	 * @count 条数
	 * @return
	 */
	public static String getPrisonInfoCategoryUrl(int count) {
		// http://61.141.137.62:9999/epg/json/news/categoryList/10003?count=7
		return SystemMgr.getBaseHostUrl() + "news/categoryList/" + UserMgr.getUserName()
				+ "?count=" + count;
	}
	
	/**
	 * 获取新闻网站分类
	 * @param count
	 * @return
	 */
	public static String getPrisonWebsitCategoryUrl(int count) {
		// http://192.168.1.247:9999/epg/json/prisonweb/menu/10003?count=10
		return SystemMgr.getBaseHostUrl() + "prisonweb/menu/" + UserMgr.getUserName()
				+ "?count=" + count;
	}

	/**
	 * 新闻资讯查询
	 * 
	 * @categoryId 分类ID
	 * @count 条数
	 * @notinIds 不在id中例如:2,3,4
	 * @type 类型 (1普通 2图文3视频 )
	 * @return
	 */
	public static String getPrisonNewsFind(int categoryId, int count, int notinIds, int type) {
		if (categoryId == -1) {
			return SystemMgr.getBaseHostUrl() + "news/NewslistByCount/" + UserMgr.getUserName()
					+ "?count=" + count + "&notinIds=" + notinIds + "&type=" + type;
		} else {
			return SystemMgr.getBaseHostUrl() + "news/NewslistByCount/" + UserMgr.getUserName()
					+ "?count=" + count + "&notinIds=" + notinIds + "&type=" + type
					+ "&categoryId=" + categoryId;
		}
	}

	/**
	 * 新闻资讯分页查询
	 * 
	 * @param categoryId
	 *            资讯分类
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            分几页
	 * @param type
	 *            (1普通 2图文3视频 )
	 * @return
	 */
	public static String getPrisonNewsPages(int categoryId, int pageNo, int pageSize, int type) {
		// /json/news/{type}/{categoryId}/{account}?pageNo=1&pageSize=10
		return SystemMgr.getBaseHostUrl() + "news/list/" + type + "/" + categoryId + "/"
				+ UserMgr.getUserName() + "?pageNo=" + pageNo + "&pageSize=" + pageSize;
	}
	
	/**
	 * 监区网站URL
	 * @param category
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static String getPrisonWebsitPagesUrl(String category, int pageNo, int pageSize) {
		// http://192.168.1.247:9999/epg/json/prisonweb/list/hhhhh:url0/10003?pageNo=1&pageSize=8
		return SystemMgr.getBaseHostUrl() + "prisonweb/list/" + category + "/"
				+ UserMgr.getUserName() + "?pageNo=" + pageNo + "&pageSize=" + pageSize;
	}

	/**
	 * 新闻内容详情
	 * 
	 * @param id
	 * @return
	 */
	public static String getPrsionNewsDetail(int id) {
		// /json/news/view/{id}
		return SystemMgr.getBaseHostUrl() + "news/view/" + id;
	}
	
	/**
	 * 新闻内容展示
	 * @param id
	 * @return
	 */
	public static String getPrsionNewsDetailContent(int id) {
		// /json/news/view/{id}
		return SystemMgr.getBaseHostUrl() + "news/content/" + id;
	}
	
	/**
	 * 查询书籍分类
	 * @param count
	 * @return
	 */
	public static String getBookCategory(int count){
//		/json/ebook/categoryList/{account}?count={count}
		return SystemMgr.getBaseHostUrl() + "ebook/categoryList/" + UserMgr.getUserName() + "?count=" + count ;
	}
	
	/**
	 * 书籍分类查询
	 * @param categoryId 分类
	 * @param pageNo 当前页码
	 * @param pageSize 每页页数
	 * @return
	 */
	public static String getBookLists(int categoryId, int pageNo, int pageSize){
//		/json/ebook/list/{categoryId}/ {account}?pageNo=1&pageSize=10
		return SystemMgr.getBaseHostUrl() + "ebook/list/" + categoryId + 
				"/"+ UserMgr.getUserName() + "?pageNo=" + pageNo + "&pageSize=" + pageSize;
	}
	
	/**
	 * 获取书籍信息
	 * @param id
	 * @return
	 */
	public static String getBookInfo(int id){
		return SystemMgr.getBaseHostUrl() + "ebook/" + UserMgr.getUserName() +  "/" + id  ;
	}
	
	/**
	 * 监狱登录URL地址
	 * @param username
	 * @param password
	 * @return
	 */
	public static String getPrisonUserInfoUrl(String username, String password) {
		// http://192.168.1.247:9999/epg/json/prisonAffairs/10002?prisonPassword=123456&mac=ddff&account=10003
		return SystemMgr.getBaseHostUrl() + "prisonAffairs/" + username +"?prisonPassword="  + password 
				+ "&mac=" + DeviceUtil.getIptvMacString() + "&account=" + UserMgr.getUserName();
	}
	
	/**
	 * 获取监狱用户信息
	 * @param username
	 * @param usercode
	 * @param type
	 * @return
	 */
	public static String getPrisonUserSearchUrl(String username, String usercode, int type) {
		// http://192.168.1.247:9999/epg/json/prisonAffairs/10002?prisonPassword=123456&mac=ddff&account=10003
		return SystemMgr.getBaseHostUrl() + "prisonAffairs/" +type+"/" + usercode + "/" + username;
	}
	
	/**
	 * 监狱食谱
	 * @return
	 */
	public static String getPrisonFoodUrl() {
		// http://61.141.137.120:9999/epg/json/prisonAffairs/recipe?date=xxx
		return SystemMgr.getBaseHostUrl() + "prisonAffairs/recipe";
	}
	
	public static String getVodRecordTimeUrl(int programid, long watchtime) {
		// json/vodrecord/{username}/{programid}/{watchtime}
		return SystemMgr.getBaseHostUrl() + "vodrecord/" + UserMgr.getUserName() + "/" + programid + "/" + watchtime;
	}
	
	public static String getLiveRecordTimeUrl(int channelid, long watchtime) {
		// json/vodrecord/tv/{username}/{channelid}/{watchtime}
		return SystemMgr.getBaseHostUrl() + "vodrecord/tv/" + UserMgr.getUserName() + "/" + channelid + "/" + watchtime;
	}
}

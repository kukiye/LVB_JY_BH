/**
 * 作者：   Johnson
 * 日期：   2014年6月24日上午11:00:23
 * 包名：    com.hhzt.iptv.lvb_x.mgr
 * 工程名：LVB_X
 * 文件名：ActivitySwitchMgr.java
 */
package com.hhzt.iptv.lvb_x.mgr;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.interfaces.IOnRoomStausReqCB;
import com.hhzt.iptv.lvb_x.mediaplayer.LVBMusicBgMediaPlayer;
import com.hhzt.iptv.lvb_x.model.VersionInfosModel;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.model.VodItemPlayMessageModel;
import com.hhzt.iptv.ui.AboutActivity;
import com.hhzt.iptv.ui.AdminitratorActivity;
import com.hhzt.iptv.ui.AppActivity;
import com.hhzt.iptv.ui.BackgroundManagerActivity;
import com.hhzt.iptv.ui.BaihuHomeActivity;
import com.hhzt.iptv.ui.BillListActivity;
import com.hhzt.iptv.ui.BookActivity;
import com.hhzt.iptv.ui.BookListActivity;
import com.hhzt.iptv.ui.CitySetActivity;
import com.hhzt.iptv.ui.ClauseActivity;
import com.hhzt.iptv.ui.FeedBackActivity;
import com.hhzt.iptv.ui.FoodActivity;
import com.hhzt.iptv.ui.HSInfoTempateA2Activity;
import com.hhzt.iptv.ui.HSInfoTempateA3Activity;
import com.hhzt.iptv.ui.HSJQRCActivity;
import com.hhzt.iptv.ui.HSMainActivity;
import com.hhzt.iptv.ui.HSOrderHistoryActivity;
import com.hhzt.iptv.ui.HSOrderHistoryDetailsActivity;
import com.hhzt.iptv.ui.HSSubCleanActivity;
import com.hhzt.iptv.ui.HSSubHotelActivity;
import com.hhzt.iptv.ui.HSSubRoomIntroduceActivity;
import com.hhzt.iptv.ui.HSSubWakeupActivity;
import com.hhzt.iptv.ui.HelpTipsActivity;
import com.hhzt.iptv.ui.LangSetActivity;
import com.hhzt.iptv.ui.LiveGridShowActivity;
import com.hhzt.iptv.ui.LocalAppsActivity;
import com.hhzt.iptv.ui.MainmenuActivity;
import com.hhzt.iptv.ui.MediaControllerActivity;
import com.hhzt.iptv.ui.MediaPlayerActivity;
import com.hhzt.iptv.ui.NetworkTestActivity;
import com.hhzt.iptv.ui.PairAuthorithTipsActivity;
import com.hhzt.iptv.ui.PairedActionTipsActivity;
import com.hhzt.iptv.ui.PdfView_New_Activity;
import com.hhzt.iptv.ui.PrisonAffairsDetailActivity;
import com.hhzt.iptv.ui.PrisonAffairsLoginActivity;
import com.hhzt.iptv.ui.PrisonInfoActivity;
import com.hhzt.iptv.ui.PrisonInfoCategoryActivity;
import com.hhzt.iptv.ui.PrisonNewsActivity;
import com.hhzt.iptv.ui.PrisonNewsDetailActivity;
import com.hhzt.iptv.ui.PrisonNewsWebSitActivity;
import com.hhzt.iptv.ui.PrisonWebsitDetailActivity;
import com.hhzt.iptv.ui.ProjectTestActivity;
import com.hhzt.iptv.ui.RCActivity;
import com.hhzt.iptv.ui.SeatSelectionTipsActivity;
import com.hhzt.iptv.ui.SettingAccountMainActivity;
import com.hhzt.iptv.ui.SettingActivity;
import com.hhzt.iptv.ui.SubAppActivity;
import com.hhzt.iptv.ui.TLSubCitySenceActivity;
import com.hhzt.iptv.ui.TLSubRateActivity;
import com.hhzt.iptv.ui.TLSubSpecailActivity;
import com.hhzt.iptv.ui.TLSubWeatherActivity;
import com.hhzt.iptv.ui.TLSubWorldClockActivity;
import com.hhzt.iptv.ui.TravelActivity;
import com.hhzt.iptv.ui.TravelTempateA5Activity;
import com.hhzt.iptv.ui.UpdateActionActivity;
import com.hhzt.iptv.ui.UpdateTipsActivity;
import com.hhzt.iptv.ui.VodActivity;
import com.hhzt.iptv.ui.VodItemDetailActivity;
import com.hhzt.iptv.ui.VodSearchActivity;
import com.hhzt.iptv.ui.WelcomeActivity;

import java.util.ArrayList;

public class ActivitySwitchMgr {
	public static void gotoMainActivity(Context context) {
		Intent intent = new Intent(context, MainmenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static void gotoLiveActivity(Activity fromActivity) {
		// wujichang 修改保证Mediaplayer在播放直播和点播时的单一性
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !mediaActivity.isFinishing()) {
			mediaActivity.finish();
		}
		LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
		UIDataller.getDataller().playLiveChannel(fromActivity);
	}

	public static void gotoChangHoneLiveActivity(Context context) {
		String menuCode = UserMgr.getThirdAppMenuCode();
		String pathName = UserMgr.getThirdAppMenuName();
		ActivitySwitchMgr.gotoThirdMarketAppActivity(context, menuCode, pathName);
	}

	public static void gotoVodActivity(Context context, String menuCode, String path) {
		// wujichang 修改保证Mediaplayer在播放直播和点播时的单一性
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !mediaActivity.isFinishing()) {
			mediaActivity.finish();
		}
		LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
		Intent intent = new Intent(context, VodActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG, menuCode);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		context.startActivity(intent);
	}

	public static void gotoHSActivity(Activity fromActivity, int id, String path) {
		// wujichang 修改保证Mediaplayer在播放直播和点播时的单一性
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !mediaActivity.isFinishing()) {
			mediaActivity.finish();
		}
		Intent intent = new Intent(fromActivity, HSMainActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_ID_TAG, id);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoTravelActivity(Activity fromActivity, int id, String path) {
		Intent intent = new Intent(fromActivity, TravelActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_ID_TAG, id);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoAppActivity(Activity fromActivity, int id, String path) {
		Intent intent = new Intent(fromActivity, AppActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_ID_TAG, id);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoSettingActivity(Activity fromActivity) {
		Intent intent = new Intent(fromActivity, SettingActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, "  " + fromActivity.getString(R.string.setting_main_text));
		fromActivity.startActivityForResult(intent, Constant.SETTING_ACTIVITY_REQUEST_CODE);
	}

	public static void gotoHomeActivity(Activity fromActivity,int position) {
		Intent intent = new Intent(fromActivity, BaihuHomeActivity.class);
		intent.putExtra("position", position);
		fromActivity.startActivity(intent);
	}

	public static void gotoTemplateA9(Activity fromActivity, String code, String path) {
		Intent intent = new Intent(fromActivity, SubAppActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG, code);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoVodItemDetailActivity(Context fromActivity, VodDetailItemModel item, int id, String path) {
		Intent intent = new Intent(fromActivity, VodItemDetailActivity.class);
		intent.putExtra(Constant.IPTV_SYS_VOD_ITEM_SINGLE, item);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		intent.putExtra(Constant.IPTV_SYS_VOD_ITEM_CATEGORY_ID, id);
		fromActivity.startActivity(intent);
	}

	public static void gotoLangSetActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, LangSetActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivityForResult(intent, Constant.SETTING_ACTIVITY_REQUEST_CODE);
	}

	public static void gotoRemoteControllerActivity(Context fromActivity, String path) {
		Intent intent = new Intent(fromActivity, RCActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		fromActivity.startActivity(intent);
	}

	public static void gotoHSJQRemoteControllerActivity(Context fromActivity, String path) {
		Intent intent = new Intent(fromActivity, HSJQRCActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		fromActivity.startActivity(intent);
	}

	public static void gotoPairTipsActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, PairedActionTipsActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivityForResult(intent, Constant.SETTING_ACTIVITY_REQUEST_CODE);
	}

	public static void gotoBillListActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, BillListActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivityForResult(intent, Constant.SETTING_ACTIVITY_REQUEST_CODE);
	}

	public static void gotoThirdMarketAppActivity(Context context, String menuCode, String path) {
		// wujichang 修改保证Mediaplayer在播放直播和点播时的单一性
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !mediaActivity.isFinishing()) {
			mediaActivity.finish();
		}
		LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
		UIDataller.getDataller().startThirdMarketApp(context, menuCode);
	}

	public static void gotoLiveGridShowActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, LiveGridShowActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivityForResult(intent, Constant.SETTING_ACTIVITY_REQUEST_CODE);
	}

	public static void gotoTemplateA2(Activity fromActivity, String code, String path) {
		Intent intent = new Intent(fromActivity, HSInfoTempateA2Activity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG, code);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoRoomIntroduceInfos(Activity fromActivity, int templateType) {
		Intent intent = new Intent(fromActivity, HSSubRoomIntroduceActivity.class);
		fromActivity.startActivity(intent);
	}

	public static void gotoSubHotelInfos(Activity fromActivity, int templateType) {
		Intent intent = new Intent(fromActivity, HSSubHotelActivity.class);
		fromActivity.startActivity(intent);
	}

	public static void gotoTemplateA3(Activity fromActivity, String code, String path, String template) {
		Intent intent = new Intent(fromActivity, HSInfoTempateA3Activity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG, code);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_TEMPLATE_TAG, template);
		fromActivity.startActivity(intent);
	}

	public static void gotoCleanInfos(final Activity fromActivity, int templateType) {
		UIDataller.getDataller().getClientRoomRequsetType(fromActivity, new IOnRoomStausReqCB() {

			@Override
			public void finishCB() {
				Intent intent = new Intent(fromActivity, HSSubCleanActivity.class);
				fromActivity.startActivity(intent);
			}
		});

	}

	public static void gotoWakeUpInfos(final Activity fromActivity, int templateType) {
		UIDataller.getDataller().getClientRoomRequsetType(fromActivity, new IOnRoomStausReqCB() {

			@Override
			public void finishCB() {
				Intent intent = new Intent(fromActivity, HSSubWakeupActivity.class);
				fromActivity.startActivity(intent);
			}
		});
	}

	public static void gotoTemplateA5(final Activity fromActivity, final String code, final String path) {
		UIDataller.getDataller().getClientRoomRequsetType(fromActivity, new IOnRoomStausReqCB() {

			@Override
			public void finishCB() {
				// wujichang 修改保证Mediaplayer在播放直播和点播时的单一性
				MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
				if (null != mediaActivity && !mediaActivity.isFinishing()) {
					mediaActivity.finish();
				}
				Intent intent = new Intent(fromActivity, TravelTempateA5Activity.class);
				intent.putExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG, code);
				intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
				fromActivity.startActivity(intent);
			}
		});
	}

	public static void gotoTLCitySenceInfos(Activity fromActivity, int templateType) {
		Intent intent = new Intent(fromActivity, TLSubCitySenceActivity.class);
		fromActivity.startActivity(intent);
	}

	public static void gotoTemplateA6(Activity fromActivity, String code, String path) {
		Intent intent = new Intent(fromActivity, TLSubWeatherActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG, code);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoTempalteA7(Activity fromActivity, String code, String path) {
		Intent intent = new Intent(fromActivity, TLSubWorldClockActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG, code);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoTemplateA8(Activity fromActivity, String code, String path) {
		Intent intent = new Intent(fromActivity, TLSubRateActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG, code);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoTemplateA11(final Activity fromActivity, final String code, final String path) {
		UIDataller.getDataller().getClientRoomRequsetType(fromActivity, new IOnRoomStausReqCB() {

			@Override
			public void finishCB() {
				Intent intent = new Intent(fromActivity, TLSubSpecailActivity.class);
				intent.putExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG, code);
				intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
				fromActivity.startActivity(intent);
			}
		});
	}

	public static void gotoOrderHistoryMainInfos(Activity fromActivity, int screenTag, String path) {
		Intent intent = new Intent(fromActivity, HSOrderHistoryActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		intent.putExtra(Constant.IPTV_LVB_X_SCREEN_TAG, screenTag);
		fromActivity.startActivity(intent);
	}

	public static void gotoAdminitratorActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, AdminitratorActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoNetSettinActivity(Activity fromActivity) {
		fromActivity.startActivity(new Intent(Settings.ACTION_SETTINGS));
	}
	
	public static void gotoSignalSettinActivity(Activity fromActivity) {
		Intent IntentSignal = new Intent();
		IntentSignal.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		IntentSignal.setComponent(new ComponentName("com.tsb.tv", "com.tsb.tv.Tv_strategy"));
		IntentSignal.setAction("android.intent.action.VIEW");
		fromActivity.startActivity(IntentSignal);
	}

	public static void gotoAccountActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, SettingAccountMainActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoCitySetActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, CitySetActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivityForResult(intent, Constant.SETTING_ACTIVITY_REQUEST_CODE);
	}

	public static void gotoAboutActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, AboutActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoFeedbackActivity(Activity fromActivity) {
		Intent intent = new Intent(fromActivity, FeedBackActivity.class);
		fromActivity.startActivity(intent);
	}

	public static void gotoClauseActivity(Activity fromActivity) {
		Intent intent = new Intent(fromActivity, ClauseActivity.class);
		fromActivity.startActivity(intent);
	}

	public static void gotoOrderHistoryDetialsInfos(Activity fromActivity, int screenTag, String pathName, int orderId, String orderCode,
			String orderTime, int orderStateType) {
		Intent intent = new Intent(fromActivity, HSOrderHistoryDetailsActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_SCREEN_TAG, screenTag);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, pathName);
		intent.putExtra(Constant.IPTV_SYS_HOTEL_ORDER_ID, orderId);
		intent.putExtra(Constant.IPTV_SYS_HOTEL_ORDER_CODE, orderCode);
		intent.putExtra(Constant.IPTV_SYS_HOTEL_ORDER_TIME, orderTime);
		intent.putExtra(Constant.IPTV_SYS_HOTEL_ORDER_STATE, orderStateType);
		fromActivity.startActivity(intent);
	}

	public static void gotoPairedAuthorithTips(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Intent newIntent = new Intent(context, PairAuthorithTipsActivity.class);
		newIntent.putExtras(bundle);
		newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 注意，必须添加这个标记，否则启动会失败
		context.startActivity(newIntent);
	}

	public static void gotoSeatSelectionTips(Context context, Intent intent) {
		Intent newIntent = new Intent(context, SeatSelectionTipsActivity.class);
		newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 注意，必须添加这个标记，否则启动会失败
		context.startActivity(newIntent);
	}

	public static void gotoHelpTips(Context context, Intent intent) {
		Intent newIntent = new Intent(context, HelpTipsActivity.class);
		newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 注意，必须添加这个标记，否则启动会失败
		context.startActivity(newIntent);
	}

	public static void gotoVodSearchActivity(Activity fromActivity, String pathName) {
		Intent intent = new Intent(fromActivity, VodSearchActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, pathName);
		fromActivity.startActivity(intent);
	}

	public static void gotoWindowControllerActivity(Activity fromActivity, ArrayList<VodItemPlayMessageModel> episodeData,
			VodDetailItemModel vodItemData) {
		Intent intent = new Intent(fromActivity, MediaControllerActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_WINDOW_EPISONDE_DATA_TAG, episodeData);
		intent.putExtra(Constant.IPTV_LVB_X_WINDOW_VOD_ITEM_DATA_TAG, vodItemData);
		fromActivity.startActivity(intent);
	}

	public static void gotoFactoryModeActivity(Activity fromActivity, String code, String path) {
		Intent intent = new Intent(fromActivity, LocalAppsActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG, code);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoProjectTestListActivity(Activity fromActivity) {
		Intent intent = new Intent(fromActivity, ProjectTestActivity.class);
		fromActivity.startActivity(intent);
	}

	public static void gotoNetTestActivity(Activity fromActivity, String code, String path) {
		Intent intent = new Intent(fromActivity, NetworkTestActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG, code);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoBackMgrActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, BackgroundManagerActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	public static void gotoUpdateTipsActivity(Activity fromActivity, VersionInfosModel models) {
		Intent intent = new Intent(fromActivity, UpdateTipsActivity.class);
		intent.putExtra(Constant.IPTV_NEW_VERSION_INFOS, models);
		fromActivity.startActivity(intent);
	}

	public static void gotoUpdateActionActivity(Activity fromActivity, VersionInfosModel models) {
		Intent intent = new Intent(fromActivity, UpdateActionActivity.class);
		intent.putExtra(Constant.IPTV_NEW_VERSION_INFOS, models);
		fromActivity.startActivity(intent);
	}
	
	public static void closeLiveVodPlayer() {
		MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
		if (null != mediaActivity && !mediaActivity.isFinishing()) {
			if (mediaActivity.isForcePlayTag()) {
				mediaActivity.finish();
			}
		}
	}

	/**
	 * 狱务公开
	 * @param activity
	 */
	public static void gotoPrisonAffairsActivity(Activity fromActivity) {
		Intent intent = new Intent(fromActivity, PrisonAffairsLoginActivity.class);
		fromActivity.startActivity(intent);
	}
	
	/**
	 * 狱务公开详情
	 * @param activity
	 */
	public static void gotoPrisonAffairsDetailActivity(Activity fromActivity, String username, 
			String nickname, String usercode) {
		Intent intent = new Intent(fromActivity, PrisonAffairsDetailActivity.class);
		intent.putExtra("username", username);
		intent.putExtra("nickname", nickname);
		intent.putExtra("usercode", usercode);
		fromActivity.startActivity(intent);
	}
	
	/**
	 * 监区资讯
	 * @param fromActivity
	 * @param path
	 */
	public static void gotoPrisonInfoActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, PrisonInfoActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}
	/**
	 * 监区资讯分类界面
	 * @param fromActivity
	 * @param path
	 */
	public static void gotoPrisonInfoCategoryActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, PrisonInfoCategoryActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}
	
	/**
	 * 监区网站
	 * @param fromActivity
	 * @param path
	 */
	public static void gotoPrisonNewsWebSitActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, PrisonNewsWebSitActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}
	
	/**
	 * 监狱新闻
	 * @param fromActivity
	 * @param path
	 */
	public static void gotoPrisonNewsActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, PrisonNewsActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}
	
	/**
	 * 监狱新闻详情
	 * @param fromActivity
	 * @param path
	 * @param newsId
	 */
	public static void gotoPrisonNewsDetailActivity(Activity fromActivity, String path, int newsId) {
		Intent intent = new Intent(fromActivity, PrisonNewsDetailActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		intent.putExtra("new_id", newsId);
		fromActivity.startActivity(intent);
	}
	
	public static void gotoPrisonWebsitDetailActivity(Activity fromActivity, String path, String url) {
		Intent intent = new Intent(fromActivity, PrisonWebsitDetailActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		intent.putExtra("websit_url", url);
		fromActivity.startActivity(intent);
	}
	
	/**
	 * 博览群书
	 * @param fromActivity
	 * @param path
	 */
	public static void gotoBookActivity(Activity fromActivity, String path) {
		Intent intent = new Intent(fromActivity, BookActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		fromActivity.startActivity(intent);
	}

	/**
	 * 电子书列表
	 * @param fromActivity
	 * @param path
	 * @param categoryId
	 * @param type
	 */
	public static void gotoBookListActivity(Activity fromActivity, String path, int categoryId, int type) {
		Intent intent = new Intent(fromActivity, BookListActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, path);
		intent.putExtra("category_id", categoryId);
		intent.putExtra("book_type", type); //表示当前是哪一类
		fromActivity.startActivity(intent);
	}
	
	/**
	 * pdf展示
	 * @param fromActivity
	 * @param filePath
	 */
	public static void gotoPdfViewActivity(Activity fromActivity, String filePath) {
		Intent intent = new Intent(fromActivity, PdfView_New_Activity.class);
		intent.putExtra("pdf_filepath", filePath);
		fromActivity.startActivity(intent);
	}

	/**
	 * 菜谱
	 * @param fromActivity
	 * @param pathName
	 */
	public static void gotoFoodActivity(Activity fromActivity, String pathName) {
		Intent intent = new Intent(fromActivity, FoodActivity.class);
		intent.putExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG, pathName);
		fromActivity.startActivity(intent);		
	}

	public static void gotoADWelcomeVideo(Activity fromActivity){
		int languageFlag = SystemMgr.getWantedLangType();
		Intent intent = new Intent(fromActivity, WelcomeActivity.class);
		intent.putExtra(Constant.IPTV_SYS_HOTEL_WANTED_LANG_TYPE, languageFlag);
		fromActivity.startActivity(intent);
		fromActivity.finish();
	}
}

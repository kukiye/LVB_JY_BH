/**
 * 作者：   Johnson
 * 日期：   2014年7月18日上午10:23:11
 * 包名：    com.hhzt.iptv.lvb_x.mgr
 * 工程名：LVB_X
 * 文件名：MenuDataMgr.java
 */
package com.hhzt.iptv.lvb_x.mgr;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCTemplateConfig;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.customview.VodDetailSingle;
import com.hhzt.iptv.lvb_x.floatwindows.FloatWindowManager;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mediaplayer.LVBMusicBgMediaPlayer;
import com.hhzt.iptv.lvb_x.model.MainmenuModel;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.hhzt.iptv.ui.MediaPlayerActivity;

public class MenuDataMgr {

	/**
	 * 设置一级主菜单数据
	 * 
	 * @param activity
	 * @param items
	 * @param horizontalContainer
	 */
	public static void setMainMenuDataListeners(final Activity activity, ArrayList<MainmenuModel> items, LinearLayout horizontalContainer) {
		boolean hasSetFoucsTag = false;
		for (int i = 0; null != items && i < items.size(); i++) {
			MainmenuModel menuItem = items.get(i);
			String itemEnable = menuItem.getEnable();
			if ("1".equalsIgnoreCase(itemEnable)) {
				if (null != activity) {

					// id：获取数据使用
					final int id = menuItem.getId();
					// 模板标示使用
					final String templateCode = menuItem.getTemplatecode();
					// 各个界面顶部欢迎词路径使用
					final String pathName = "  " + menuItem.getName();
					// 没有子集菜单，直接使用menuCode进行相关数据的获取
					final String menuCode = menuItem.getCode();
					VodDetailSingle item = new VodDetailSingle(activity);
					item.hideNameText();
					item.setButtonSelector(R.drawable.button_menu_selector);
					item.setImageValue(R.drawable.hotel_iptv_menu_175x175);
					item.setItemBottomName(menuItem.getName());
					item.setValues(menuItem.getMenulogourl(), "", i, 
							(int) activity.getResources().getDimension(R.dimen.layx129), 
							(int) activity.getResources().getDimension(R.dimen.layy133));
					item.setMargin((int) activity.getResources().getDimension(R.dimen.layx15), 
							0, 
							(int) activity.getResources().getDimension(R.dimen.layx15), 
							0);
					item.setOnClickListner(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							enterMainMenuContents(activity, id, menuCode, templateCode, pathName);
						}
					});

					// 设置默认焦点为第一个菜单
					if (!hasSetFoucsTag) {
						hasSetFoucsTag = true;
						item.setItemButtonFocus();
					}

					if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_RC.equalsIgnoreCase(templateCode)
							|| CCTemplateConfig.IPTV_LVC_X_MAINMENU_TEMPLATE_GUIDE.equalsIgnoreCase(templateCode)
							|| CCTemplateConfig.IPTV_LVC_X_MAINMENU_TEMPLATE_COMPLAINT.equalsIgnoreCase(templateCode)
							|| CCTemplateConfig.IPTV_LVC_X_MAINMENU_TEMPLATE_LEAVE.equalsIgnoreCase(templateCode)
							|| CCTemplateConfig.IPTV_LVC_X_MAINMENU_TEMPLATE_RADIO.equalsIgnoreCase(templateCode)
							|| CCTemplateConfig.IPTV_LVC_X_MAINMENU_TEMPLATE_TECHNICIAN.equalsIgnoreCase(templateCode)) {
						switch (Config.LvbDeviceType) {
						case Constant.DEVICE_TYPE_MOBILE:
						case Constant.DEVICE_TYPE_MOBILE_HSJQ:
							horizontalContainer.addView(item);
							UIDataller.getDataller().setOneKeyMenuValues(menuItem);
							break;
						default:
							break;
						}
					} else {
						horizontalContainer.addView(item);
						UIDataller.getDataller().setOneKeyMenuValues(menuItem);
					}

				}
			} else {
				LogUtil.d("This mainItem is hided===>" + menuItem.getName());
			}
		}
	}

	/**
	 * 进入二级菜单界面
	 * 
	 * @param activity
	 * @param id
	 * @param code
	 */
	public static void enterMainMenuContents(Activity activity, int id, String menuCode, String templateCode, String pathName) {
		if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_LIVE.equalsIgnoreCase(templateCode)) {
			switch (Config.LvbDeviceType) {
			case Constant.DEVICE_TYPE_BOX:
			case Constant.DEVICE_TYPE_BOX_HSJQ:
				// wujichang 修改保证Mediaplayer在播放直播和点播时的单一性
				MediaPlayerActivity mediaActivity = MediaPlayerActivity.getInstance();
				if (null != mediaActivity && !mediaActivity.isFinishing()) {
					mediaActivity.finish();
				}
				LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
				UIDataller.getDataller().playLiveChannel(activity);
				break;
			case Constant.DEVICE_TYPE_MOBILE:
			case Constant.DEVICE_TYPE_MOBILE_HSJQ:
				LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
				ActivitySwitchMgr.gotoLiveGridShowActivity(activity, pathName);
				break;
			default:
				break;
			}
		} else if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_VOD.equalsIgnoreCase(templateCode)) {
			LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
			ActivitySwitchMgr.gotoVodActivity(activity, menuCode, pathName);

		} else if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_HS.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoHSActivity(activity, id, pathName);
		} else if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_TRVEL.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoTravelActivity(activity, id, pathName);
		} else if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_APP.equalsIgnoreCase(templateCode)) {
			LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
			ActivitySwitchMgr.gotoAppActivity(activity, id, pathName);
		} else if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_LANG.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoLangSetActivity(activity, pathName);
		} else if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_RC.equalsIgnoreCase(templateCode)) {
			switch (Config.LvbDeviceType) {
			case Constant.DEVICE_TYPE_MOBILE:
				ActivitySwitchMgr.gotoRemoteControllerActivity(activity, pathName);
				break;
			case Constant.DEVICE_TYPE_MOBILE_HSJQ:
				ActivitySwitchMgr.gotoHSJQRemoteControllerActivity(activity, pathName);
				break;
			}
		} else if (CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_BILL.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoBillListActivity(activity, pathName);
		} else if (!StringUtil.isEmpty(templateCode) && templateCode.contains(CCTemplateConfig.IPTV_LVB_X_TEMPLATE_THIRD_MARKET_APP_ENTRY)) {
			ActivitySwitchMgr.gotoThirdMarketAppActivity(activity, menuCode, pathName);
		} else if(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON.equalsIgnoreCase(templateCode)){
//			BaseActivity.getInstance().showToast("正在开发中，请稍后...", Toast.LENGTH_SHORT);
			LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
			ActivitySwitchMgr.gotoPrisonAffairsActivity(activity);
		} else if(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_INFO.equalsIgnoreCase(templateCode)){
//			BaseActivity.getInstance().showToast("正在开发中，请稍后...", Toast.LENGTH_SHORT);
			LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
//			ActivitySwitchMgr.gotoPrisonInfoActivity(activity, pathName);
			ActivitySwitchMgr.gotoPrisonNewsActivity(activity,pathName);
					
			
		} else if(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_BOOK.equalsIgnoreCase(templateCode)){
//			BaseActivity.getInstance().showToast("正在开发中，请稍后...", Toast.LENGTH_SHORT);
			LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
			ActivitySwitchMgr.gotoBookActivity(activity, pathName);
		} else if(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_PRISON_FOOD.equalsIgnoreCase(templateCode)){
//			BaseActivity.getInstance().showToast("正在开发中，请稍后...", Toast.LENGTH_SHORT);
			LVBMusicBgMediaPlayer.getInstance().releseMusicPlayer();
			ActivitySwitchMgr.gotoFoodActivity(activity, pathName);
		} if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_HOTEL_INTRODUCE.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoTemplateA2(activity, menuCode, pathName);
		} 
	}

	/**
	 * 设置二级菜单数据、并注册点击事件
	 * 
	 * @param activity
	 * @param items
	 * @param horizontalContainer
	 */
	public static void setSecondMenuDataListeners(final Activity activity, String parentPath, ArrayList<MainmenuModel> items,
			LinearLayout horizontalContainer) {
		boolean hasSetFoucsTag = false;
		for (int i = 0; null != items && i < items.size(); i++) {
			String itemEnable = items.get(i).getEnable();
			if ("1".equalsIgnoreCase(itemEnable)) {
				if (null != activity) {
					// 获取二级菜单项使用
					final String code = items.get(i).getCode();
					// 标示界面模板使用
					final String templateCode = items.get(i).getTemplatecode();
					// 路径标示使用
					final String path = parentPath + ">" + items.get(i).getName();
					VodDetailSingle item = new VodDetailSingle(activity);
					item.hideNameText();
					item.setButtonSelector(R.drawable.button_menu_selector);
					item.setImageValue(R.drawable.hotel_iptv_menu_175x175);
					item.setItemBottomName(items.get(i).getName());
					item.setValues(items.get(i).getMenulogourl(), "", i, (int) activity.getResources().getDimension(R.dimen.layx170), (int) activity
							.getResources().getDimension(R.dimen.layx170));
					item.setOnClickListner(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							enterSedondeMenuContents(activity, templateCode, code, path);
						}
					});

					// 设置默认焦点为第一个菜单
					if (!hasSetFoucsTag) {
						hasSetFoucsTag = true;
						item.setItemButtonFocus();
					}
					horizontalContainer.addView(item);

				}
			} else {
				LogUtil.d("This secondeItem is hided===>" + items.get(i).getName());
			}
		}
	}

	/**
	 * 进入三级菜单界面
	 * 
	 * @param activity
	 * @param templateCode
	 * @param code
	 */
	public static void enterSedondeMenuContents(final Activity activity, final String templateCode, String code, String path) {
		if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_HOTEL_INTRODUCE.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoTemplateA2(activity, code, path);
		} else if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_HOTEL_ROOMSERVICE_CHECKOUTIN.equalsIgnoreCase(templateCode)
				|| CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_HOTEL_ROOMSERVICE_CLEANUP.equalsIgnoreCase(templateCode)
				|| CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_HOTEL_ROOMSERVICE_WAKEUP.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoTemplateA3(activity, code, path, templateCode);
		} else if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_HOTEL_ORDERSERVICE.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoTemplateA5(activity, code, path);
		} else if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_TRAVEL_SPECAIL.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoTemplateA11(activity, code, path);
		} else if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_TRAVEL_WEATHER.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoTemplateA6(activity, code, path);
		} else if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_TRAVEL_WORLDCLOCK.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoTempalteA7(activity, code, path);
		} else if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_TRAVEL_RATE.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoTemplateA8(activity, code, path);
		} else if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_APP_ASSITANT.equalsIgnoreCase(templateCode)) {
			ActivitySwitchMgr.gotoTemplateA9(activity, code, path);
		} else {
		}
	}
}

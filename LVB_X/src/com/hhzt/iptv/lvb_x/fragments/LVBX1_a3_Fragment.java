/**
 * 作者：   Johnson
 * 日期：   2014年6月25日下午6:06:18
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：HSCheckOutInFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCTemplateConfig;
import com.hhzt.iptv.lvb_x.interfaces.IThirdmenuModelable;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.hhzt.iptv.lvb_x.model.ThirdmenuModel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LVBX1_a3_Fragment extends BaseFragment {
	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.introduce_image_bg)
	private ImageView mHotelImageGb;
	@ViewInject(R.id.right_adv)
	private ImageView mHotelAdvImageBg;
	@ViewInject(R.id.main_type_imgae)
	private ImageView mHomeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mCurrentPathTextView;
	@ViewInject(R.id.page_current_container)
	private RelativeLayout mPageInfosRlContainer;
	@ViewInject(R.id.current_page_tips)
	private TextView mCurrentPageTextView;
	@ViewInject(R.id.tips_back)
	private TextView mActionBackTipsTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mActionOkTipsTextView;
	@ViewInject(R.id.roomservice_main_container)
	private LinearLayout mRoomServiceMainContainer;

	private int mCurrentPageNum = 0;
	private String mMenuCode;
	private String mMenuPath;
	private String mTemplateCode;
	private ArrayList<ThirdmenuModel> mThirdmenuModelList = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_template_lvb_x1_a3, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			Intent intent = getActivity().getIntent();
			mMenuCode = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG);
			mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			mTemplateCode = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_TEMPLATE_TAG);

			setAdvDataInfo();
			setMainControllerContentItem();
			getLocalDataMetaInfos();
			getNetDataMetaInfos();
		}
	}

	// 设置主控制面板控件
	private void setMainControllerContentItem() {
		mRoomServiceMainContainer.setVisibility(View.VISIBLE);
		if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_HOTEL_ROOMSERVICE_CHECKOUTIN.equalsIgnoreCase(mTemplateCode)) {
			CheckInOutFragment checkInOutFragment = new CheckInOutFragment();
			FragmentMgr.replace(getActivity(), false, R.id.roomservice_main_container, checkInOutFragment, Constant.HOTEL_CHECK_OUTIN_SCREEN);
		} else if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_HOTEL_ROOMSERVICE_CLEANUP.equalsIgnoreCase(mTemplateCode)) {
			CleanUpFragment cleanUpFragment = new CleanUpFragment();
			FragmentMgr.replace(getActivity(), false, R.id.roomservice_main_container, cleanUpFragment, Constant.HOTEL_CLEAN_SCREEN);
		} else if (CCTemplateConfig.IPTV_LVB_X_SECONDMENU_TEMPLATE_HOTEL_ROOMSERVICE_WAKEUP.equalsIgnoreCase(mTemplateCode)) {
			WakeUpFragment wakeUpFragment = new WakeUpFragment();
			FragmentMgr.replace(getActivity(), false, R.id.roomservice_main_container, wakeUpFragment, Constant.HOTEL_WAKEUP_SCREEN);
		} else {

		}
	}

	private void getLocalDataMetaInfos() {
		UIDataller ller = UIDataller.getDataller();

		mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));

		ller.setHsActionTips(getActivity(), mHomeImageView, R.drawable.home_icon, mCurrentPathTextView, mMenuPath, mActionOkTipsTextView,
				ller.getOkActionTips(getActivity()), mActionBackTipsTextView, ller.getBackActionTips(getActivity()));
	}

	private void getNetDataMetaInfos() {
		UIDataller ller = UIDataller.getDataller();
		ller.getThirdMenuImageBgInfos(getActivity(), mMenuCode, mHotelImageGb, mThirdmenuModelList, mCurrentPageNum, mCurrentPageTextView,
				new IThirdmenuModelable() {

					@Override
					public void setValue(ArrayList<ThirdmenuModel> modelList) {
						mThirdmenuModelList = modelList;
					}

				});
	}

	private void setAdvDataInfo() {
		mHotelAdvImageBg.setImageResource(R.drawable.sub_right_adv_280x660);
		UIDataller ller = UIDataller.getDataller();
		ller.setAdvertisment(getActivity(), Constant.IPTV_ADV_INDEX_2, mHotelAdvImageBg);
	}

}

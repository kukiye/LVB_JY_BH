/**
 * 作者：   Johnson
 * 日期：   2014年6月24日上午11:43:00
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：HSFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.interfaces.IOnMainMenuDataSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IOnSubMenuModelable;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.mgr.MenuDataMgr;
import com.hhzt.iptv.lvb_x.model.MainmenuModel;
import com.hhzt.iptv.lvb_x.model.SubmenuModel;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.hhzt.iptv.lvb_x.utils.CCAnimationUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LVBX1_a1_Fragment extends BaseFragment {
	@ViewInject(R.id.hs_content_container)
	LinearLayout mHsHorizontalContentContainer;
	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.image_content)
	private ImageView mHotelContentImage;
	@ViewInject(R.id.content_title)
	private TextView mHotelContentMainTitle;
	@ViewInject(R.id.content_subtitle)
	private TextView mHotelContentSubTitle;
	@ViewInject(R.id.hotellogo)
	private ImageView mHotelImageLogoImageView;
	@ViewInject(R.id.main_type_imgae)
	private ImageView mHotelHomeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mHotelSubViewerTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mHotelActionOkTipsTextView;
	@ViewInject(R.id.tips_back)
	private TextView mHotelActionBackTipsTextView;
	@ViewInject(R.id.right_adv)
	private ImageView mAdvertimentImageView;
	@ViewInject(R.id.text_content_container)
	private LinearLayout mTextContentContainer;

	private int mMenuId;
	private String mPathName;
	private int mModelBgCurrentIndex;
	private ArrayList<SubmenuModel> mSubMenuListModels;
	private TemplateFragmentHandler mTemplateFragmentHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_template_lvb_x1_a1, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			Intent intent = getActivity().getIntent();
			mMenuId = intent.getIntExtra(Constant.IPTV_LVB_X_MENU_ID_TAG, 0);
			mPathName = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mPathName));

			BitmapUtil.setFadeInImage(ConfigMgr.getInstance().getBeanVaule(CCViewConfig.HOTEL_LOGO), mHotelImageLogoImageView);
			UIDataller.getDataller().setHsActionTips(getActivity(), mHotelHomeImageView, R.drawable.home_icon, mHotelSubViewerTextView, mPathName,
					mHotelActionOkTipsTextView, UIDataller.getDataller().getOkActionTips(getActivity()), mHotelActionBackTipsTextView,
					UIDataller.getDataller().getBackActionTips(getActivity()));
			UIDataller.getDataller().setAdvertisment(getActivity(), Constant.IPTV_ADV_INDEX_1, mAdvertimentImageView);

			UIDataller.getDataller().setSecondHorizontalScroviewData(mMenuId, new IOnMainMenuDataSuccessCB() {

				@Override
				public void onDataSuccessCB(ArrayList<MainmenuModel> items) {
					MenuDataMgr.setSecondMenuDataListeners(getActivity(), mPathName, items, mHsHorizontalContentContainer);
				}
			});
		}
	}

	private void updateHSHomeData() {
		UIDataller ller = UIDataller.getDataller();
		ller.getSubMenuHomeInfos(getActivity(), mMenuId, new IOnSubMenuModelable() {

			@Override
			public void setValue(ArrayList<SubmenuModel> listModels) {
				mSubMenuListModels = listModels;
				mTemplateFragmentHandler = new TemplateFragmentHandler();
				mTemplateFragmentHandler.sendEmptyMessage(Constant.IPTV_MSG_HOTERSERVICE_BG_CHANGE);
			}

		});
	}

	@Override
	public void onResume() {
		super.onResume();

		stopAllHandler();
		updateHSHomeData();
	}

	@Override
	public void onStop() {
		super.onStop();

		stopAllHandler();
	}

	private void nextBgImageIndex() {
		++mModelBgCurrentIndex;

		if (mModelBgCurrentIndex >= mSubMenuListModels.size()) {
			mModelBgCurrentIndex = 0;
		}
	}

	private void stopAllHandler() {
		if (null != mTemplateFragmentHandler) {
			mTemplateFragmentHandler.removeMessages(Constant.IPTV_MSG_HOTERSERVICE_BG_CHANGE);
			mTemplateFragmentHandler = null;
		}
	}

	@SuppressLint("HandlerLeak")
	class TemplateFragmentHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.IPTV_MSG_HOTERSERVICE_BG_CHANGE:
				if (null != mSubMenuListModels && mSubMenuListModels.size() > 0) {
					BitmapUtil.setFadeInImage(mSubMenuListModels.get(mModelBgCurrentIndex).getMenubgurl(), mHotelContentImage);
					CCAnimationUtils.setRandom2DAnimation(mTextContentContainer, mHotelContentMainTitle, mHotelContentSubTitle);
					mHotelContentMainTitle.setText(mSubMenuListModels.get(mModelBgCurrentIndex).getZhtitle());
					mHotelContentSubTitle.setText(mSubMenuListModels.get(mModelBgCurrentIndex).getEntitle());
					sendEmptyMessageDelayed(Constant.IPTV_MSG_HOTERSERVICE_BG_CHANGE, Constant.IPTV_TIME_TEN_SECONDES);
					nextBgImageIndex();
				}
				break;
			default:
				break;
			}
		}
	}

}

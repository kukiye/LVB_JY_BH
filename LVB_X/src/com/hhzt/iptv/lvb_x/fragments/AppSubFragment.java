package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.mgr.WindowController;
import com.hhzt.iptv.lvb_x.utils.download.DownloadManager;
import com.hhzt.iptv.lvb_x.utils.download.DownloadService;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AppSubFragment extends BaseFragment {

	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcometextTextView;
	@ViewInject(R.id.main_type_imgae)
	private ImageView setHsActionTips;
	@ViewInject(R.id.main_type_text)
	private TextView mMainTypeTextView;
	@ViewInject(R.id.tips_back)
	private TextView mBackTipsTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mOkTipsTextView;
	@ViewInject(R.id.right_adv)
	private ImageView mAdvertisementImageView;
	@ViewInject(R.id.sub_app_item_grid)
	private GridLayout mItemGridLayout;
	private DownloadManager mDownloadManager;

	private String mMenuCode;
	private String mMenuPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sub_business_app, container, false);
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
			mWelcometextTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));
			mAdvertisementImageView.setImageResource(R.drawable.sub_right_adv_280x660);

			UIDataller.getDataller().setAdvertisment(getActivity(), Constant.IPTV_ADV_INDEX_2, mAdvertisementImageView);
			UIDataller.getDataller().setHsActionTips(getActivity(), setHsActionTips, R.drawable.home_icon, mMainTypeTextView, mMenuPath,
					mOkTipsTextView, UIDataller.getDataller().getOkActionTips(getActivity()), mBackTipsTextView,
					UIDataller.getDataller().getBackActionTips(getActivity()));

			mDownloadManager = DownloadService.getDownloadManager(getActivity());
			UIDataller.getDataller().setSubAppInfo(getActivity(), mMenuCode, mItemGridLayout, mDownloadManager);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onResume() {
		super.onResume();
		WindowController.openFloatControllerWindow();
	}

	@Override
	public void onDestroy() {
		try {
			if (mDownloadManager != null) {
				mDownloadManager.backupDownloadInfoList();
			}
		} catch (DbException e) {
			LogUtils.e(e.getMessage(), e);
		}
		super.onDestroy();
	}

}

package com.hhzt.iptv.lvb_x.fragments;

import java.util.List;

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
import com.hhzt.iptv.lvb_x.customview.AppItemSingle;
import com.hhzt.iptv.lvb_x.model.AppInfoModel;
import com.hhzt.iptv.lvb_x.utils.AppInfoProvider;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LocalAppFragment extends BaseFragment {
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
	@ViewInject(R.id.sub_app_item_grid)
	private GridLayout mItemGridLayout;

	private String mMenuPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_local_app, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			Intent intent = getActivity().getIntent();
			mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			mWelcometextTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));

			UIDataller.getDataller().setHsActionTips(getActivity(), setHsActionTips, R.drawable.home_icon, mMainTypeTextView, mMenuPath,
					mOkTipsTextView, UIDataller.getDataller().getOkActionTips(getActivity()), mBackTipsTextView,
					UIDataller.getDataller().getBackActionTips(getActivity()));
			setLocalAppsInfos();
		}
	}

	public void setLocalAppsInfos() {
		AppInfoProvider appProvider = new AppInfoProvider(getActivity());
		List<AppInfoModel> appInfoList = appProvider.getAllApps(AppInfoProvider.ALL);

		for (int i = 0; i < appInfoList.size(); i++) {
			AppInfoModel appinfo = appInfoList.get(i);

			// 设置显示数据
			AppItemSingle single = new AppItemSingle(getActivity(), appinfo);
			single.setValues(appinfo.getIcon(), appinfo.getAppname());
			mItemGridLayout.addView(single);
		}
	}

}

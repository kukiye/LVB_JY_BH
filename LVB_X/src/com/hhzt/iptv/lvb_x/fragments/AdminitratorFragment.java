package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.adapter.AboutListAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class AdminitratorFragment extends BaseFragment {

	@ViewInject(R.id.setting_list_content)
	private ListView mSettingContentListView;
	@ViewInject(R.id.main_type_imgae)
	private ImageView mMainTypeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mMainTypeTextView;
	@ViewInject(R.id.tips_back)
	private TextView mBackTipsTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mOkTipsTextView;

	private String mMenuPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting_main, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if (null == savedInstanceState) {
			setValue();
		}
	}

	private void setValue() {
		Intent intent = getActivity().getIntent();
		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		UIDataller ller = UIDataller.getDataller();
		ller.setHsActionTips(getActivity(), mMainTypeImageView, R.drawable.home_icon, mMainTypeTextView, mMenuPath, mOkTipsTextView,
				ller.getOkActionTips(getActivity()), mBackTipsTextView, ller.getBackActionTips(getActivity()));

		String[] settingListName = { LVBXApp.getApp().getString(R.string.account), LVBXApp.getApp().getString(R.string.setting_main_text),LVBXApp.getApp().getString(R.string.setting_signal_text)};
		AboutListAdapter adapter = new AboutListAdapter(getActivity(), settingListName);
		mSettingContentListView.setAdapter(adapter);
		mSettingContentListView.requestLayout();
		mSettingContentListView.requestFocus();
	}

	@OnItemClick(R.id.setting_list_content)
	public void itemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 0:
			ActivitySwitchMgr.gotoAccountActivity(getActivity(), getString(R.string.login_screen));
			break;
		case 1:
			ActivitySwitchMgr.gotoNetSettinActivity(getActivity());
			break;
		case 2:
			ActivitySwitchMgr.gotoSignalSettinActivity(getActivity());
			break;
		default:
			break;
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
}

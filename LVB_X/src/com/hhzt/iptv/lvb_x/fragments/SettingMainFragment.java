package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.hhzt.iptv.lvb_x.adapter.SettingMainListAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.commonui.AuthorithDialog;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class SettingMainFragment extends BaseFragment {

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
	private SettingMainListAdapter mAdapter;

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

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	private void setValue() {
		Intent intent = getActivity().getIntent();
		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		UIDataller ller = UIDataller.getDataller();
		ller.setHsActionTips(getActivity(), mMainTypeImageView, R.drawable.home_icon, mMainTypeTextView, mMenuPath, mOkTipsTextView,
				ller.getOkActionTips(getActivity()), mBackTipsTextView, ller.getBackActionTips(getActivity()));

		mAdapter = new SettingMainListAdapter(getActivity(), getSettingListsName());
		mSettingContentListView.setAdapter(mAdapter);

		mSettingContentListView.requestLayout();
		mSettingContentListView.requestFocus();
	}

	private String[] getSettingListsName() {
		String[] settingListName = null;
		switch (Config.LvbDeviceType) {
		case Constant.DEVICE_TYPE_MOBILE:
		case Constant.DEVICE_TYPE_MOBILE_HSJQ:
			settingListName = new String[] { 
					LVBXApp.getApp().getString(R.string.weather_city), 
					LVBXApp.getApp().getString(R.string.system_language),
					LVBXApp.getApp().getString(R.string.about), 
					LVBXApp.getApp().getString(R.string.admin),
					LVBXApp.getApp().getString(R.string.pair_setting) };
			break;
		case Constant.DEVICE_TYPE_BOX:
		case Constant.DEVICE_TYPE_BOX_HSJQ:
			settingListName = new String[] { 
//					LVBXApp.getApp().getString(R.string.weather_city), 
//					LVBXApp.getApp().getString(R.string.system_language),
					LVBXApp.getApp().getString(R.string.about), 
					LVBXApp.getApp().getString(R.string.admin) };
			break;
		default:
			break;
		}
		return settingListName;
	}

	@OnItemClick(R.id.setting_list_content)
	public void itemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
//		case 0:
//			ActivitySwitchMgr.gotoCitySetActivity(getActivity(), mMenuPath + ">" + getString(R.string.weather_city));
//			break;
//		case 1:
//			ActivitySwitchMgr.gotoLangSetActivity(getActivity(), mMenuPath + ">" + getString(R.string.system_language));
//			break;
		case 0:
			ActivitySwitchMgr.gotoAboutActivity(getActivity(), mMenuPath + ">" + getString(R.string.about));
			break;
		case 1:
			new AuthorithDialog(getActivity(), R.style.lvbEdittextDialogTheme, false).show();
			break;
		case 4:
			ActivitySwitchMgr.gotoPairTipsActivity(getActivity(), mMenuPath + ">" + getString(R.string.pair_setting));
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Constant.SETTING_CITYSETTING_SCREEN) {
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Intent data = new Intent();
			getActivity().setResult(Constant.SETTING_ACTIVITY_REQUEST_CODE, data);
			getActivity().finish();
			return true;
		default:
			break;
		}
		return super.onKeyUp(keyCode, event);
	}
}

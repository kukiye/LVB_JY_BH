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

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.adapter.AboutListAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class AboutFragment extends BaseFragment {

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
	@ViewInject(R.id.version)
	private TextView mVersionTextView;
	@ViewInject(R.id.about_list_content)
	private ListView mAboutListView;

	private String mMenuPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_about_main, container, false);
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

		mVersionTextView.setText(SystemMgr.getSoftVersion());
		String[] mAboutListName = { LVBXApp.getApp().getString(R.string.feedbak), LVBXApp.getApp().getString(R.string.clause) };
		AboutListAdapter adapter = new AboutListAdapter(getActivity(), mAboutListName);
		mAboutListView.setAdapter(adapter);
		mAboutListView.requestLayout();
		mAboutListView.requestFocus();
	}

	@OnItemClick(R.id.about_list_content)
	public void setItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 0:
			ActivitySwitchMgr.gotoFeedbackActivity(getActivity());
			break;
		case 1:
			ActivitySwitchMgr.gotoClauseActivity(getActivity());
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

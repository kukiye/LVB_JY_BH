package com.hhzt.iptv.lvb_x.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.model.VersionInfosModel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class UpdateTipsFragment extends BaseFragment {

	@ViewInject(R.id.new_versionTips)
	private TextView mNewVersionTipsTextView;
	@ViewInject(R.id.new_versionAction)
	private TextView mUpdateNowActionButon;

	private VersionInfosModel mVersionInfosModel;

	@OnClick(R.id.new_versionAction)
	private void onClick(View view) {
		ActivitySwitchMgr.gotoUpdateActionActivity(getActivity(), mVersionInfosModel);
		getActivity().finish();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_update_tips, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			initValue();
		}
	}

	private void initValue() {
		setVersionInfos();
	}

	private void setVersionInfos() {
		mVersionInfosModel = getActivity().getIntent().getParcelableExtra(Constant.IPTV_NEW_VERSION_INFOS);
		String versionDes = String.format(getString(R.string.new_version_found),
				mVersionInfosModel.getVersion() + "." + mVersionInfosModel.getVersioncode());
		mNewVersionTipsTextView.setText(versionDes);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if ("1".equalsIgnoreCase(mVersionInfosModel.getForceupdate())) {
				return true;
			}
			break;
		default:
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

}

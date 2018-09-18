package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.Loginer;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class PairedActionTipsFragment extends BaseFragment {

	@ViewInject(R.id.main_type_imgae)
	private ImageView mHomeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mCurrentPathTextView;
	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mActionOkTipsTextView;
	@ViewInject(R.id.tips_back)
	private TextView mActionBackTipsTextView;

	@ViewInject(R.id.interactive_passwd_input)
	private EditText mInteractivePasswdInputTips;
	@ViewInject(R.id.interactive_serverip_input)
	private EditText mInteractiveServerIpInputTips;
	@ViewInject(R.id.pair_ok)
	private Button mPairConfirmButton;
	@ViewInject(R.id.pair_no)
	private Button mPairCancelButton;

	private String mMenuPath;

	@OnClick({ R.id.pair_ok, R.id.pair_no })
	public void onClickListner(View view) {
		switch (view.getId()) {
		case R.id.pair_ok:
			String passwd = mInteractivePasswdInputTips.getText().toString();
			String serverIp = mInteractiveServerIpInputTips.getText().toString();
			Loginer.getLoginer().getPairedActionInfos(getActivity(), serverIp, passwd);
			break;
		case R.id.pair_no:
			getActivity().finish();
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pair_tips, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setPairedInfos();
		setBottomTopInfos();
	}

	private void setPairedInfos() {
		if (UserMgr.getPairedTag()) {
			mInteractivePasswdInputTips.setText(UserMgr.getInteracPassword());
			mInteractiveServerIpInputTips.setText(UserMgr.getServerIp());
		}
	}

	private void setBottomTopInfos() {
		Intent intent = getActivity().getIntent();
		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		UIDataller ller = UIDataller.getDataller();
		mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));
		ller.setHsActionTips(getActivity(), mHomeImageView, R.drawable.home_icon, mCurrentPathTextView, mMenuPath, mActionOkTipsTextView,
				ller.getOkActionTips(getActivity()), mActionBackTipsTextView, ller.getBackActionTips(getActivity()));
	}

}

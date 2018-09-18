package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.business.Loginer;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.mgr.TokenMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.hhzt.iptv.ui.WelcomeActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AccountFragment extends BaseFragment {

	@ViewInject(R.id.main_type_imgae)
	private ImageView mMainTypeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mMainTypeTextView;
	@ViewInject(R.id.tips_back)
	private TextView mBackTipsTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mOkTipsTextView;
	@ViewInject(R.id.account_input_hint)
	private EditText inputAccountName;
	@ViewInject(R.id.passwd_input_hint)
	private EditText inputPasswd;
	@ViewInject(R.id.IPAdd_input_hint)
	private EditText inputIpAddress;
	@ViewInject(R.id.setting_cancel)
	private Button settingCancel;
	@ViewInject(R.id.setting_ok)
	private Button settingOk;

	private String mMenuPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_account, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			setValue();
			setSavedAccountInfos();
		}
	}

	private void setValue() {
		Intent intent = getActivity().getIntent();
		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		UIDataller ller = UIDataller.getDataller();
		ller.setHsActionTips(getActivity(), mMainTypeImageView, R.drawable.home_icon, mMainTypeTextView, mMenuPath, mOkTipsTextView,
				ller.getOkActionTips(getActivity()), mBackTipsTextView, ller.getBackActionTips(getActivity()));

	}

	private void setSavedAccountInfos() {
		String userName = UserMgr.getUserName();
		String userPasswd = UserMgr.getAcountPasswd();
		String serverIp = UserMgr.getServerIp();
		if (!StringUtil.isEmpty(userName)) {
			inputAccountName.setText(userName);
		}
		if (!StringUtil.isEmpty(userPasswd)) {
			inputPasswd.setText(userPasswd);
		}
		if (!StringUtil.isEmpty(serverIp)) {
			inputIpAddress.setText(serverIp);
		}
	}

	@OnClick({ R.id.setting_cancel, R.id.setting_ok })
	public void clickBtn(View view) {
		switch (view.getId()) {
		case R.id.setting_ok:
			loginIPTV();
			break;
		case R.id.setting_cancel:
			if (!StringUtil.isEmpty(UserMgr.getUserName())) {
				getActivity().finish();
			}
			break;
		default:
			break;
		}
	}

	private void loginIPTV() {
		Bundle bundle = getArguments();
		int screenTag = bundle.getInt(Constant.IPTV_SYS_FRAGMENT_TAG);
		String userName = inputAccountName.getText().toString();
		String passWd = inputPasswd.getText().toString();
		String serverIp = inputIpAddress.getText().toString();
		String currentLang = SystemMgr.getSystemLangName();
		int languageFlag = 0;
		if (getString(R.string.simple_chinese).equalsIgnoreCase(currentLang)) {
			languageFlag = Constant.IPTV_SYSTEM_LANG_SIMPLE_CHINESE;
		} else if ((getString(R.string.english).equalsIgnoreCase(currentLang))) {
			languageFlag = Constant.IPTV_SYSTEM_LANG_US_ENGLISH;
		}
		Loginer.getLoginer().loginAction(languageFlag, screenTag, userName, passWd, serverIp,TokenMgr.getTokenId(), getActivity(),
				WelcomeActivity.class);
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
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			ActivitySwitchMgr.gotoSettingActivity(getActivity());
			return true;
		case KeyEvent.KEYCODE_BACK:
			if (StringUtil.isEmpty(UserMgr.getUserName())) {
				return true;
			}
			break;
		default:
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

}

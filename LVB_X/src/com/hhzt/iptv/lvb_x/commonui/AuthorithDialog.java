package com.hhzt.iptv.lvb_x.commonui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;

public class AuthorithDialog extends Dialog implements android.view.View.OnClickListener {

	private String mMenuPath;
	private Activity mActivity;
	private boolean mCanBackPress;
	private EditText mContentPasswd;
	private Button mControllerButtonOk;
	private Button mControllerButtonCancel;

	public AuthorithDialog(Activity activity, int theme, boolean canBackPress) {
		super(activity, theme);

		mActivity = activity;
		mCanBackPress = canBackPress;
		Intent intent = mActivity.getIntent();
		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.author_dialog);
		findViewsById();
		setListners();
		setAnimation();
	}

	private void findViewsById() {
		mContentPasswd = (EditText) findViewById(R.id.passwd_input);
		mControllerButtonOk = (Button) findViewById(R.id.ok);
		mControllerButtonCancel = (Button) findViewById(R.id.no);
	}

	private void setListners() {
		mControllerButtonOk.setOnClickListener(this);
		mControllerButtonCancel.setOnClickListener(this);
	}

	private void setAnimation() {
		getWindow().setWindowAnimations(R.style.dialogWindowAnim);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok:
			authoriationAction();
			break;
		case R.id.no:
			dismiss();
			break;
		default:
			break;
		}
		AuthorithDialog.this.dismiss();
	}

	private void authoriationAction() {
		String userInputPasswd = mContentPasswd.getText().toString();
		String configurePwd = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.ADMIN_PWD);
		if (null != configurePwd && configurePwd.equalsIgnoreCase(userInputPasswd)) {
			ActivitySwitchMgr.gotoAdminitratorActivity(mActivity, mMenuPath);
		} else {
			if (null == configurePwd && "12345678".equalsIgnoreCase(userInputPasswd)) {
				ActivitySwitchMgr.gotoAdminitratorActivity(mActivity, mMenuPath);
			} else {
				Toast.makeText(mActivity, mActivity.getString(R.string.passwd_error), Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onBackPressed() {
		if (mCanBackPress) {
			super.onBackPressed();
		} else {
			dismiss();
		}
	}
}

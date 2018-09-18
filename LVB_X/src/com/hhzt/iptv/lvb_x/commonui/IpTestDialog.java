package com.hhzt.iptv.lvb_x.commonui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.interfaces.IOnIpSuccessdCB;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IpTestDialog extends Dialog implements OnClickListener {

	private Activity mActivity;
	private boolean mCanBackPress;
	private EditText mContentIp;
	private Button mControllerButtonOk;
	private Button mControllerButtonCancel;
	private IOnIpSuccessdCB mIpSucess;
	private boolean mVerificationIpTag;

	public IpTestDialog(Activity activity, int theme, boolean canBackPress, IOnIpSuccessdCB ipSucess, boolean verificationTag) {
		super(activity, theme);
		mActivity = activity;
		mCanBackPress = canBackPress;
		mIpSucess = ipSucess;
		mVerificationIpTag = verificationTag;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ip_input_dialog);
		findViewsById();
		setListners();
		setAnimation();
	}

	private void findViewsById() {
		mContentIp = (EditText) findViewById(R.id.passwd_input);
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
		IpTestDialog.this.dismiss();
	}

	private void authoriationAction() {
		String inputIp = mContentIp.getText().toString();
		if (mVerificationIpTag) {
			if (isIP(inputIp)) {
				mIpSucess.ipSuccessd(inputIp);
			} else {
				BaseActivity.getInstance().showToast(mActivity.getString(R.string.ip_error), Toast.LENGTH_SHORT);
			}
		} else {
			mIpSucess.ipSuccessd(inputIp);
		}
	}

	public boolean isIP(String addr) {
		if (StringUtil.isEmpty(addr)) {
			return false;
		}
		String rexp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(addr);
		boolean flag = mat.matches();
		return flag;
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

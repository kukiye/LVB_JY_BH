/**
 * 作 者：Kobe
 * 日 期：20152015年5月8日下午6:00:43
 * 包 名：com.hhzt.iptv.lvb_x.commonui
 * 工程名：MediaDetector
 * 文件名：LoginExceptionDialog.java
 */
package com.hhzt.iptv.lvb_x.commonui;

import com.hhzt.iptv.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

public class LoginExceptionDialog extends Dialog {

	public LoginExceptionDialog(Context context, int theme) {
		super(context, theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_login_exception);
	}

}

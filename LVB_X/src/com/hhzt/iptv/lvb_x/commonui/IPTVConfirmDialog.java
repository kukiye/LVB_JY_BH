/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 *
 * @author Johnson
 * 2013-10-21 下午2:21:49
 */
package com.hhzt.iptv.lvb_x.commonui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.interfaces.IOnDialogClickListner;

public class IPTVConfirmDialog extends Dialog implements android.view.View.OnClickListener {

	private Activity mActivity;
	private String mTitle;
	private String mContents;
	private String mleftName;
	private String mRightName;
	private IOnDialogClickListner mListner;

	private boolean mCanBackPress;
	private TextView mTitleTips;
	private TextView mContentsTips;
	private Button mControllerButtonOk;
	private Button mControllerButtonCancel;

	public IPTVConfirmDialog(Activity activity, String title, String contents, String leftTips, String rightTips, IOnDialogClickListner listner,
			boolean canBackPress) {
		super(activity);
		mActivity = activity;
		mTitle = title;
		mContents = contents;
		mleftName = leftTips;
		mRightName = rightTips;
		mListner = listner;
		mCanBackPress = canBackPress;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_dialog_twobutton);

		findViewsById();
		setValues();
		setListners();
		setAnimation();
	}

	private void findViewsById() {
		mTitleTips = (TextView) findViewById(R.id.dialog_title);
		mContentsTips = (TextView) findViewById(R.id.dialog_content_text);
		mControllerButtonOk = (Button) findViewById(R.id.dialog_ok);
		mControllerButtonCancel = (Button) findViewById(R.id.dialog_cancel);
	}

	private void setListners() {
		mControllerButtonOk.setOnClickListener(this);
		mControllerButtonCancel.setOnClickListener(this);
	}

	private void setValues() {
		mTitleTips.setText(mTitle);
		mContentsTips.setText(mContents);
		mControllerButtonOk.setText(mleftName);
		mControllerButtonCancel.setText(mRightName);

		mControllerButtonOk.requestLayout();
		mControllerButtonOk.requestFocus();
	}

	private void setAnimation() {
		getWindow().setWindowAnimations(R.style.dialogWindowAnim);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_ok:
			dismiss();
			mListner.OnOkClick(mActivity);
			break;
		case R.id.dialog_cancel:
			dismiss();
			mListner.OnCancelClick(mActivity);
			break;
		default:
			break;
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

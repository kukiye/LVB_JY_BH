/**
 * 作者：   Johnson
 * 日期：   2014年7月2日下午6:23:12
 * 包名：    com.hhzt.iptv.lvb_x.commonui
 * 工程名：LVB_X
 * 文件名：DialogTwoButton.java
 */
package com.hhzt.iptv.lvb_x.commonui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.interfaces.IOnButtonAnimationable;
import com.hhzt.iptv.lvb_x.interfaces.IOnClickListnerable;

public class DialogTwoButton extends Dialog implements android.view.View.OnClickListener {

	private String mTitle;
	private String mContents;
	private IOnClickListnerable mOnClick;
	private IOnButtonAnimationable mOnAnimation;

	private TextView mTitleTextView;
	private TextView mContentsTextView;
	private Button mConfirmButtom;
	private Button mCancelButton;
	private Boolean mCancelable;

	public DialogTwoButton(Context context, int theme, String title, String contents, IOnClickListnerable onClick,
			IOnButtonAnimationable onAnimation, boolean cancelable) {
		super(context, theme);

		mTitle = title;
		mContents = contents;
		mOnClick = onClick;
		mOnAnimation = onAnimation;
		mCancelable = cancelable;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_dialog_twobutton);
		setCancelable(mCancelable);

		findViewsById();
		setAllValues();
		setListners();
		setAnimation();
	}

	private void findViewsById() {
		mTitleTextView = (TextView) findViewById(R.id.dialog_title);
		mContentsTextView = (TextView) findViewById(R.id.dialog_content_text);
		mConfirmButtom = (Button) findViewById(R.id.dialog_ok);
		mCancelButton = (Button) findViewById(R.id.dialog_cancel);
	}

	private void setAllValues() {
		mTitleTextView.setText(mTitle);
		mContentsTextView.setText(mContents);
	}

	public void setListners() {
		mConfirmButtom.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);
	}

	private void setAnimation() {
		getWindow().setWindowAnimations(R.style.dialogWindowAnim);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.dialog_ok:
			mOnClick.ok();
			if (null != mOnAnimation) {
				mOnAnimation.play(mConfirmButtom);
			}
			break;
		case R.id.dialog_cancel:
			mOnClick.no();
			break;
		default:
			break;
		}
		dismiss();
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

}

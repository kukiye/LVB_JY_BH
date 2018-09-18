package com.hhzt.iptv.lvb_x.commonui;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.interfaces.IMediaPlayerActionable;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;

public class MediaActionDialog extends Dialog implements android.view.View.OnClickListener, 

OnFocusChangeListener {

	private Button mResetPlayButton;
	private Button mContinuePlayButton;
	private IMediaPlayerActionable mIMediaPlayerListner;

	public MediaActionDialog(Context context) {
		super(context);
	}

	public MediaActionDialog(Context context, IMediaPlayerActionable action) {
		super(context);
		mIMediaPlayerListner = action;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_media_action);
		findViewById();
		setAllListners();
	}

	private void findViewById() {
		mResetPlayButton = (Button) findViewById(R.id.play_continue);
		mContinuePlayButton = (Button) findViewById(R.id.play_reset);
	}

	private void setAllListners() {
		mResetPlayButton.setOnClickListener(this);
		mContinuePlayButton.setOnClickListener(this);
		mResetPlayButton.setOnFocusChangeListener(this);
		mContinuePlayButton.setOnFocusChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play_continue:
			mIMediaPlayerListner.continuePlay();
			dismiss();
			break;
		case R.id.play_reset:
			mIMediaPlayerListner.resetPlay();
			dismiss();
			break;
		default:
			break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.play_reset:
		case R.id.play_continue:
			mIMediaPlayerListner.onFoucsChange();
			break;
		default:
			break;
		}
	}

}

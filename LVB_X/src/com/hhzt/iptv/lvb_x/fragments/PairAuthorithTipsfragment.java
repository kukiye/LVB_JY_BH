package com.hhzt.iptv.lvb_x.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.PairAuthorizationModel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PairAuthorithTipsfragment extends BaseFragment {

	@ViewInject(R.id.dialog_title)
	private TextView mTitleTextView;
	@ViewInject(R.id.dialog_content_text)
	private TextView mContentsTextView;
	@ViewInject(R.id.dialog_ok)
	private Button mConfirmButtom;
	@ViewInject(R.id.dialog_cancel)
	private Button mCancelButton;

	PairAuthorizationModel mModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dialog_twobutton, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getBaseInfos();
		setAllValues();
		setAllListners();
	}

	private void getBaseInfos() {
		Bundle bundle = getActivity().getIntent().getExtras();
		mModel = bundle.getParcelable(Constant.IPTV_VALUE_PAIR_AUTHORIZE_MSG);
	}

	private void setAllValues() {
		mTitleTextView.setText(mModel.getTitle());
		mContentsTextView.setText(mModel.getContents());
		getActivity().setTheme(R.style.lvbTwoButtonDialogTheme);

		LayoutParams p = getActivity().getWindow().getAttributes();
		p.height = (int) (LVBXApp.getmScreenHeight() * 0.5);
		p.width = (int) (LVBXApp.getmScreenWidth() * 0.5);
		p.alpha = 1.0f;
		p.dimAmount = 0.0f;

		getActivity().getWindow().setAttributes(p);
		getActivity().getWindow().setGravity(Gravity.CENTER);
	}

	private void setAllListners() {
		mConfirmButtom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UserMgr.setPairedTag(true);
				UserMgr.setPairedIp(mModel.getPairFromUserIP());
				UserMgr.setPairedMacAdress(mModel.getPairFromMacAddr());
				((BaseActivity) getActivity()).showToast(getActivity().getString(R.string.authorize_success), Toast.LENGTH_LONG);
				getActivity().finish();
			}
		});

		mCancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((BaseActivity) getActivity()).showToast(getActivity().getString(R.string.authorize_deny), Toast.LENGTH_LONG);
				getActivity().finish();
			}
		});
	}

}

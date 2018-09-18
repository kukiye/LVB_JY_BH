/**
 * 作者：   Johnson
 * 日期：   2014年7月11日下午2:53:28
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：RateFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.adapter.RateAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RateFragment extends BaseFragment {
	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.rate_main_bg)
	private ImageView mRateMainBg;
	@ViewInject(R.id.rate_type)
	private TextView mRateTypeTextView;
	@ViewInject(R.id.rate_name)
	private TextView mRateNameTextView;
	@ViewInject(R.id.rate_cbuy)
	private TextView mRateCBuyTextView;
	@ViewInject(R.id.rate_hbuy)
	private TextView mRateHBuyTextView;
	@ViewInject(R.id.rate_sell)
	private TextView mRateSellTextView;
	@ViewInject(R.id.rate_baseprice)
	private TextView mRateBasePriceTextView;
	@ViewInject(R.id.rate_date)
	private TextView mRateDateTextView;
	@ViewInject(R.id.rate_list_content)
	private ListView mRateDateListView;

	@ViewInject(R.id.main_type_imgae)
	private ImageView mHomeTagImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mCurrentPathTextView;
	@ViewInject(R.id.tips_back)
	private TextView mBackActionTipsTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mOkActionTipsTextView;

	private String mMenuCode;
	private String mMenuPath;
	private RateAdapter mRateAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.framwork_rate_main, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			mMenuCode = getActivity().getIntent().getStringExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG);
			mRateAdapter = new RateAdapter(getActivity());
			setTitles();
			setRateBg();
			setRateListData();
			setcurrentPathTips();
		}
	}

	private void setRateBg() {
		UIDataller ller = UIDataller.getDataller();
		ller.getThirdMenuImageBgInfos(getActivity(), mMenuCode, mRateMainBg, null, 0, null, null);

		Intent intent = getActivity().getIntent();
		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));
	}

	private void setTitles() {
		mRateTypeTextView.setText(getString(R.string.rate_type));
		mRateNameTextView.setText(getString(R.string.rate_name));
		mRateCBuyTextView.setText(getString(R.string.rate_cbuyprice));
		mRateHBuyTextView.setText(getString(R.string.rate_hbuyprice));
		mRateSellTextView.setText(getString(R.string.rate_sell));
		mRateBasePriceTextView.setText(getString(R.string.rate_base_price));
		mRateDateTextView.setText(getString(R.string.rate_date));
	}

	private void setRateListData() {
		UIDataller ller = UIDataller.getDataller();
		ller.setRateListDatas(getActivity(), mRateAdapter, mRateDateListView);
	}

	private void setcurrentPathTips() {
		UIDataller ller = UIDataller.getDataller();
		ller.setHsActionTips(getActivity(), mHomeTagImageView, R.drawable.home_icon, mCurrentPathTextView, mMenuPath, mOkActionTipsTextView,
				ller.getOkActionTips(getActivity()), mBackActionTipsTextView, ller.getBackActionTips(getActivity()));
	}

}

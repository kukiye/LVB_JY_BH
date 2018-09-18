/**
 * 作者：   Johnson
 * 日期：   2014年8月14日下午5:42:21
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：BillListFragment.java
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
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class BillListFragment extends BaseFragment {

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

	@ViewInject(R.id.bill_details_items_list)
	private ListView mBillDataListView;
	@ViewInject(R.id.data_total_infos)
	private TextView mBillDataTotalInfosTextView;
	@ViewInject(R.id.bill_tag)
	private ImageView mBillTagImageView;
	@ViewInject(R.id.bill_type)
	private TextView mBillTypeTextView;
	@ViewInject(R.id.bill_name)
	private TextView mBillNameTextView;
	@ViewInject(R.id.bill_price)
	private TextView mBillPriceTextView;
	@ViewInject(R.id.bill_time)
	private TextView mBillTimeTextView;

	private String mMenuPath;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bill_list, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setBottomTopInfos();
		setBillDataListInfos();
		setBillDateTitleInfos();
	}

	private void setBottomTopInfos() {
		Intent intent = getActivity().getIntent();
		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		UIDataller ller = UIDataller.getDataller();
		mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));
		ller.setHsActionTips(getActivity(), mHomeImageView, R.drawable.home_icon, mCurrentPathTextView, mMenuPath, mActionOkTipsTextView,
				ller.getOkActionTips(getActivity()), mActionBackTipsTextView, ller.getBackActionTips(getActivity()));
	}

	private void setBillDateTitleInfos() {
		mBillTypeTextView.setText(R.string.consume_type);
		mBillNameTextView.setText(R.string.consume_name);
		mBillPriceTextView.setText(R.string.consume_price);
		mBillTimeTextView.setText(R.string.consume_time);
	}

	private void setBillDataListInfos() {
		UIDataller ller = UIDataller.getDataller();
		ller.setBillDataValues(getActivity(), mBillDataListView, mBillDataTotalInfosTextView);
	}

}

/**
 * 作者：   Johnson
 * 日期：   2014年7月4日下午7:56:34
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：HSOrderHistoryDetailFragmen.java
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

import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.adapter.OrderHistoryDetailAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HSOrderHistoryDetailFragmen extends BaseFragment {

	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.main_type_imgae)
	private ImageView mOrderPathHomeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mOrderPathHomeTextView;
	@ViewInject(R.id.tips_back)
	private TextView mOrderActionBackTips;
	@ViewInject(R.id.tips_ok)
	private TextView mOrderActionOkTips;
	@ViewInject(R.id.order_history_details_items_list)
	private ListView mHistoryOrderDetailsDataListView;
	@ViewInject(R.id.order_history_total_infos)
	private TextView mHistoryOrderDetailTotalTextView;
	@ViewInject(R.id.order_history_ordernumber_infos)
	private TextView mHistoryOrderDetailNumberCodeTextView;
	@ViewInject(R.id.order_index)
	private TextView mHistoryOrderDetailOrderIndexTextView;
	@ViewInject(R.id.order_name)
	private TextView mHistoryOrderDetailOrderNameTextView;
	@ViewInject(R.id.order_time)
	private TextView mHistoryOrderDedailOrderTimeTextView;
	@ViewInject(R.id.order_single_price)
	private TextView mHistoryOrderDetailOrderSinglePriceTextView;
	@ViewInject(R.id.order_count)
	private TextView mHistoryOrderDetailOrderCountTextView;
	@ViewInject(R.id.order_total_price)
	private TextView mHistoryOrderDetailOrderTotalPriceTextView;
	@ViewInject(R.id.order_state)
	private TextView mHistoryOrderDetailOrderSatetTextView;

	private int mScreenTag;
	private String mUserName;
	private String mMenuPath;
	private int mOrderNumberId;
	private int mOrderStateType;
	private String mOrderCreatTime;
	private String mOrderNumberCode;
	private OrderHistoryDetailAdapter mHistoryDetailsAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_order_history_details, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			Bundle bundle = getArguments();
			mScreenTag = bundle.getInt(Constant.IPTV_SYS_FRAGMENT_TAG);
			Intent intent = getActivity().getIntent();
			mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG) + ">" + getString(R.string.order_history_detail);
			mOrderNumberId = intent.getIntExtra(Constant.IPTV_SYS_HOTEL_ORDER_ID, 0);
			mOrderStateType = intent.getIntExtra(Constant.IPTV_SYS_HOTEL_ORDER_STATE, 0);
			mOrderNumberCode = intent.getStringExtra(Constant.IPTV_SYS_HOTEL_ORDER_CODE);
			mOrderCreatTime = intent.getStringExtra(Constant.IPTV_SYS_HOTEL_ORDER_TIME);

			mUserName = UserMgr.getUserName();
			mHistoryDetailsAdapter = new OrderHistoryDetailAdapter(getActivity());

			setTitleTextInfos();
			UIDataller ller = UIDataller.getDataller();
			// 设置顶部欢迎词
			mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));
			// 设置底部提示信息
			ller.setHsActionTips(getActivity(), mOrderPathHomeImageView, R.drawable.home_icon, mOrderPathHomeTextView, mMenuPath, mOrderActionOkTips,
					ller.getOkActionTips(getActivity()), mOrderActionBackTips, ller.getBackActionTips(getActivity()));

			// 设置订单详细数据信息
			ller.setOrderHistoryDetailsListData(getActivity(), mScreenTag, mUserName, mOrderNumberId, mHistoryOrderDetailsDataListView,
					mHistoryOrderDetailTotalTextView, mHistoryOrderDetailNumberCodeTextView, mOrderNumberCode, mHistoryDetailsAdapter,
					mOrderStateType, mOrderCreatTime);
		}
	}

	private void setTitleTextInfos() {
		mHistoryOrderDetailOrderIndexTextView.setText(R.string.order_index);
		mHistoryOrderDetailOrderNameTextView.setText(getString(R.string.name));
		mHistoryOrderDedailOrderTimeTextView.setText(getString(R.string.order_time));
		mHistoryOrderDetailOrderSinglePriceTextView.setText(R.string.single_price);
		mHistoryOrderDetailOrderCountTextView.setText(R.string.order_count);
		mHistoryOrderDetailOrderTotalPriceTextView.setText(R.string.order_price);
		mHistoryOrderDetailOrderSatetTextView.setText(R.string.order_state);
	}

}

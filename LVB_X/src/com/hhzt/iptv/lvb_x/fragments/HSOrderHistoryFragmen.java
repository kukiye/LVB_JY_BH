/**
 * 作者：   Johnson
 * 日期：   2014年7月4日下午12:14:23
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：HSOrderHistoryFragmen.java
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
import com.hhzt.iptv.lvb_x.adapter.OrderHistoryAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.interfaces.IOrderMenuHistoryItemOnclickCB;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HSOrderHistoryFragmen extends BaseFragment {
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
	@ViewInject(R.id.order_index)
	private TextView mHistoryOrderIndex;
	@ViewInject(R.id.order_num)
	private TextView mHistoryOrderTagNumber;
	@ViewInject(R.id.order_time)
	private TextView mHistoryOrderTagTime;
	@ViewInject(R.id.order_count)
	private TextView mHistoryOrderTagCount;
	@ViewInject(R.id.order_price)
	private TextView mHistoryOrderTagPrice;
	@ViewInject(R.id.order_state)
	private TextView mHistoryOrderTagState;
	@ViewInject(R.id.order_history_total_infos)
	private TextView mHistoryOrderTotalInfos;
	@ViewInject(R.id.order_history_items_list)
	private ListView mHistoryOrderDataListView;

	private int mScreenTag;
	private String mUserName;
	private String mMenuPath;
	private OrderHistoryAdapter mHistoryAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_order_history_main, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			Bundle bundle = getArguments();
			mScreenTag = bundle.getInt(Constant.IPTV_SYS_FRAGMENT_TAG);
			mUserName = UserMgr.getUserName();
			mHistoryAdapter = new OrderHistoryAdapter(getActivity());

			setTitleTextInfos();
			UIDataller ller = UIDataller.getDataller();
			// 设置顶部欢迎词
			Intent intent = getActivity().getIntent();
			mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));
			// 设置底部提示信息
			ller.setHsActionTips(getActivity(), mOrderPathHomeImageView, R.drawable.home_icon, mOrderPathHomeTextView, mMenuPath, mOrderActionOkTips,
					ller.getOkActionTips(getActivity()), mOrderActionBackTips, ller.getBackActionTips(getActivity()));

			// 设置实例订单数据进行显示
			ller.setOrderHistoryListData(getActivity(), mScreenTag, mUserName, mHistoryOrderDataListView, mHistoryOrderTotalInfos, mHistoryAdapter,
					new IOrderMenuHistoryItemOnclickCB() {

						@Override
						public void onItemClickCB(int index, int orderId, String orderCode, String time, int orderStateType) {
							ActivitySwitchMgr.gotoOrderHistoryDetialsInfos(getActivity(), mScreenTag, mMenuPath, orderId, orderCode, time,
									orderStateType);
						}
					});
		}
	}

	// 设置历史订单详细信息标题数据
	private void setTitleTextInfos() {
		mHistoryOrderIndex.setText(R.string.order_index);
		mHistoryOrderTagNumber.setText(R.string.order_number);
		mHistoryOrderTagTime.setText(R.string.order_time);
		mHistoryOrderTagCount.setText(R.string.order_count);
		mHistoryOrderTagPrice.setText(R.string.order_price);
		mHistoryOrderTagState.setText(R.string.order_state);
	}

}

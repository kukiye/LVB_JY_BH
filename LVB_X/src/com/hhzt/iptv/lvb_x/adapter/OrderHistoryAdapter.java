/**
 * 作者：   Johnson
 * 日期：   2014年7月4日下午4:50:42
 * 包名：    com.hhzt.iptv.lvb_x.adapter
 * 工程名：LVB_X
 * 文件名：OrderHistoryAdapter.java
 */
package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.model.OrderHistoryMainModel;

public class OrderHistoryAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<OrderHistoryMainModel> mHistoryOrders;

	public OrderHistoryAdapter(Context context) {
		mContext = context;
	}

	public void setListModels(ArrayList<OrderHistoryMainModel> models) {
		mHistoryOrders = models;
	}

	@Override
	public int getCount() {
		return mHistoryOrders.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mHistoryOrders.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return mHistoryOrders.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		HistoryInfosHolder holder = null;
		if (null == arg1) {
			holder = new HistoryInfosHolder();
			arg1 = LayoutInflater.from(mContext).inflate(R.layout.sub_order_hostory_title, null);
			holder.orderIndex = (TextView) arg1.findViewById(R.id.order_index);
			holder.orderNumber = (TextView) arg1.findViewById(R.id.order_num);
			holder.orderTime = (TextView) arg1.findViewById(R.id.order_time);
			holder.orderCount = (TextView) arg1.findViewById(R.id.order_count);
			holder.orderPrice = (TextView) arg1.findViewById(R.id.order_price);
			holder.orderState = (TextView) arg1.findViewById(R.id.order_state);

			arg1.setTag(holder);
		} else {
			holder = (HistoryInfosHolder) arg1.getTag();
		}

		setAllValues(holder, arg0);
		return arg1;
	}

	private void setAllValues(HistoryInfosHolder holder, int position) {
		holder.orderIndex.setText(position + 1 + "");
		holder.orderNumber.setText(mHistoryOrders.get(position).getOrdercode());
		holder.orderTime.setText(mHistoryOrders.get(position).getCreationtime());
		holder.orderCount.setText(mHistoryOrders.get(position).getTotalnumber() + "");
		holder.orderPrice.setText("￥" + mHistoryOrders.get(position).getTotalprice());

		UIDataller ller = UIDataller.getDataller();
		holder.orderState.setText(ller.getOrderState(mContext, mHistoryOrders.get(position).getStatus()));
	}

	final class HistoryInfosHolder {
		TextView orderIndex;
		TextView orderNumber;
		TextView orderTime;
		TextView orderCount;
		TextView orderPrice;
		TextView orderState;
	}

}

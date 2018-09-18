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
import com.hhzt.iptv.lvb_x.model.OrderShopCarSingleSubModel;

public class OrderHistoryDetailAdapter extends BaseAdapter {

	private Context mContext;
	private int mOrderStateType;
	private String mOrderCreateTime;
	private ArrayList<OrderShopCarSingleSubModel> mHistoryDetailOrders;

	public OrderHistoryDetailAdapter(Context context) {
		mContext = context;
	}

	public void setListModels(ArrayList<OrderShopCarSingleSubModel> models) {
		mHistoryDetailOrders = models;
	}

	public void setOrderState(int orderState) {
		mOrderStateType = orderState;
	}

	public void setCreateTime(String orderCreateTime) {
		mOrderCreateTime = orderCreateTime;
	}

	@Override
	public int getCount() {
		return mHistoryDetailOrders.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mHistoryDetailOrders.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return mHistoryDetailOrders.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		HistoryInfosHolder holder = null;
		if (null == arg1) {
			holder = new HistoryInfosHolder();
			arg1 = LayoutInflater.from(mContext).inflate(R.layout.sub_order_hostory_detail_title, null);
			holder.orderIndex = (TextView) arg1.findViewById(R.id.order_index);
			holder.orderName = (TextView) arg1.findViewById(R.id.order_name);
			holder.orderTime = (TextView) arg1.findViewById(R.id.order_time);
			holder.orderSinglePrice = (TextView) arg1.findViewById(R.id.order_single_price);
			holder.orderCount = (TextView) arg1.findViewById(R.id.order_count);
			holder.orderTotalPrice = (TextView) arg1.findViewById(R.id.order_total_price);
			holder.orderState = (TextView) arg1.findViewById(R.id.order_state);

			arg1.setTag(holder);
		} else {
			holder = (HistoryInfosHolder) arg1.getTag();
		}

		setAllValues(holder, arg0);
		return arg1;
	}

	private void setAllValues(HistoryInfosHolder holder, int position) {
		int count = mHistoryDetailOrders.get(position).getNumber();
		int singlePrice = mHistoryDetailOrders.get(position).getPrice();
		String orderState = UIDataller.getDataller().getOrderState(mContext, mOrderStateType);
		holder.orderIndex.setText(position + 1 + "");
		holder.orderName.setText(mHistoryDetailOrders.get(position).getName());
		holder.orderTime.setText(mOrderCreateTime);
		holder.orderSinglePrice.setText("￥" + singlePrice + "");
		holder.orderCount.setText(count + "");
		holder.orderTotalPrice.setText("￥" + singlePrice * count);
		holder.orderState.setText(orderState);
	}

	final class HistoryInfosHolder {
		TextView orderIndex;
		TextView orderName;
		TextView orderTime;
		TextView orderSinglePrice;
		TextView orderCount;
		TextView orderTotalPrice;
		TextView orderState;
	}

}

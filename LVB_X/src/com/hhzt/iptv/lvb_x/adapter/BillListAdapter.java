/**
 * 作者：   Johnson
 * 日期：   2014年8月14日下午6:37:31
 * 包名：    com.hhzt.iptv.lvb_x.adapter
 * 工程名：LVB_X
 * 文件名：BillListAdapter.java
 */
package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.BillModel;

public class BillListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<BillModel> mBillModels;

	public BillListAdapter(Context context) {
		mContext = context;
	}

	public void setListModels(ArrayList<BillModel> models) {
		mBillModels = models;
	}

	@Override
	public int getCount() {
		return mBillModels.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mBillModels.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		BillHolder holder = null;
		if (null == arg1) {
			holder = new BillHolder();
			arg1 = LayoutInflater.from(mContext).inflate(R.layout.bill_data_title, null);
			holder.tag = (ImageView) arg1.findViewById(R.id.bill_tag);
			holder.title = (TextView) arg1.findViewById(R.id.bill_type);
			holder.name = (TextView) arg1.findViewById(R.id.bill_name);
			holder.fee = (TextView) arg1.findViewById(R.id.bill_price);
			holder.feeDate = (TextView) arg1.findViewById(R.id.bill_time);

			arg1.setTag(holder);
		} else {
			holder = (BillHolder) arg1.getTag();
		}
		setAllValues(holder, arg0);

		return arg1;
	}

	private void setAllValues(BillHolder holder, int index) {
		BillModel billModel = mBillModels.get(index);
		holder.title.setText(billModel.getTypeTitle());
		holder.name.setText(billModel.getFeename());
		holder.fee.setText("￥ " + billModel.getFee());
		holder.feeDate.setText(billModel.getFeedate());
	}

	final class BillHolder {
		public ImageView tag;
		public TextView title;
		public TextView name;
		public TextView fee;
		public TextView feeDate;
	}

}

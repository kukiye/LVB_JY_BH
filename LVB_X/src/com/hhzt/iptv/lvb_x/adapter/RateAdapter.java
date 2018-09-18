/**
 * 作者：   Johnson
 * 日期：   2014年7月11日下午2:15:32
 * 包名：    com.hhzt.iptv.lvb_x.adapter
 * 工程名：LVB_X
 * 文件名：RateAdapter.java
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
import com.hhzt.iptv.lvb_x.model.RateModel;

public class RateAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<RateModel> mRateModels;

	public RateAdapter(Context context) {
		mContext = context;
	}

	public void setListData(ArrayList<RateModel> rateModels) {
		mRateModels = rateModels;
	}

	@Override
	public int getCount() {
		return mRateModels.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mRateModels.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return mRateModels.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		RateInfoHolder holder = null;
		if (null == arg1) {
			holder = new RateInfoHolder();
			arg1 = LayoutInflater.from(mContext).inflate(R.layout.sub_rate_list_info, null);
			holder.type = (TextView) arg1.findViewById(R.id.rate_type);
			holder.name = (TextView) arg1.findViewById(R.id.rate_name);
			holder.cBuyInPrice = (TextView) arg1.findViewById(R.id.rate_cbuy);
			holder.hBuyInPrice = (TextView) arg1.findViewById(R.id.rate_hbuy);
			holder.sellPrice = (TextView) arg1.findViewById(R.id.rate_sell);
			holder.basePrice = (TextView) arg1.findViewById(R.id.rate_baseprice);
			holder.date = (TextView) arg1.findViewById(R.id.rate_date);

			arg1.setTag(holder);
		} else {
			holder = (RateInfoHolder) arg1.getTag();
		}

		setAllValues(holder, arg0);
		return arg1;
	}

	private void setAllValues(RateInfoHolder holder, int position) {
		holder.type.setText(mRateModels.get(position).getSymbol());
		holder.name.setText(mRateModels.get(position).getName());
		holder.cBuyInPrice.setText(mRateModels.get(position).getMbuyprice());
		holder.hBuyInPrice.setText(mRateModels.get(position).getFbuyprice());
		holder.sellPrice.setText(mRateModels.get(position).getSellprice());
		holder.basePrice.setText(mRateModels.get(position).getBaseprice());
		holder.date.setText(mRateModels.get(position).getDate());
	}

	final class RateInfoHolder {
		public TextView type;
		public TextView name;
		public TextView cBuyInPrice;
		public TextView hBuyInPrice;
		public TextView sellPrice;
		public TextView basePrice;
		public TextView date;
	}

}

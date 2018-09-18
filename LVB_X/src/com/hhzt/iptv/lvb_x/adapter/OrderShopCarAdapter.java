/**
 * 作者：   Johnson
 * 日期：   2014年7月2日下午5:20:08
 * 包名：    com.hhzt.iptv.lvb_x.adapter
 * 工程名：LVB_X
 * 文件名：OrderShopCarAdapter.java
 */
package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.OrderShopCarSingleModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderShopCarAdapter extends BaseAdapter {

	private Context mContext;
	ArrayList<OrderShopCarSingleModel> mModels;

	public OrderShopCarAdapter(Context context) {
		mContext = context;
	}

	public void setListModels(ArrayList<OrderShopCarSingleModel> models) {
		mModels = models;
	}

	@Override
	public int getCount() {
		return mModels.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mModels.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return mModels.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ShopCarListHolder holder;
		if (null == convertView) {
			holder = new ShopCarListHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.order_shopcar_list, null);
			holder.name = (TextView) convertView.findViewById(R.id.shopcar_menu_name);
			holder.price = (TextView) convertView.findViewById(R.id.shopcar_menu_price);
			holder.clickCancel = (TextView) convertView.findViewById(R.id.shopcar_menu_cancel);
			convertView.setTag(holder);
		} else {
			holder = (ShopCarListHolder) convertView.getTag();
		}

		setAllValues(holder, arg0);
		return convertView;
	}

	public int getTotalNum() {
		if (null != mModels) {
			return mModels.size();
		}

		return 0;
	}

	public int getTotalPrice() {
		int totalPrice = 0;
		if (null != mModels) {
			for (int i = 0; i < mModels.size(); i++) {
				totalPrice += mModels.get(i).getDish().getPrice();
			}
		}

		return totalPrice;
	}

	private void setAllValues(ShopCarListHolder holder, int position) {
		holder.name.setText(mModels.get(position).getDish().getName());
		holder.price.setText("￥" + mModels.get(position).getDish().getPrice());
		holder.clickCancel.setText(mContext.getString(R.string.cancel));
	}

	final class ShopCarListHolder {
		TextView name;
		TextView price;
		TextView clickCancel;
	}

}

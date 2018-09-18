/**
 * 作者：   Johnson
 * 日期：   2014年7月2日上午11:09:36
 * 包名：    com.hhzt.iptv.lvb_x.adapter
 * 工程名：LVB_X
 * 文件名：OrderMenuMainListAdapter.java
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
import com.hhzt.iptv.lvb_x.model.OrderMainMenuModel;

public class OrderMenuMainListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<OrderMainMenuModel> mOrderMainMenuModels;

	public OrderMenuMainListAdapter(Context context) {
		mContext = context;
	}

	public void setDataList(ArrayList<OrderMainMenuModel> models) {
		mOrderMainMenuModels = models;
	}

	@Override
	public int getCount() {
		return mOrderMainMenuModels.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mOrderMainMenuModels.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return mOrderMainMenuModels.get(arg0).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		MenuListHolder holder;
		if (null == convertView) {
			holder = new MenuListHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.sub_order_mainmenu_list_item, null);
			holder.menuItemName = (TextView) convertView.findViewById(R.id.order_mainmenu_item_name);

			convertView.setTag(holder);
		} else {
			holder = (MenuListHolder) convertView.getTag();
		}

		setAllValues(holder, position);
		return convertView;
	}

	private void setAllValues(MenuListHolder holder, int position) {
		holder.menuItemName.setText(mOrderMainMenuModels.get(position).getCategoryname());
	}

	final class MenuListHolder {
		TextView menuItemName;
	}

}

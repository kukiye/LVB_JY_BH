package com.hhzt.iptv.lvb_x.adapter;

import com.hhzt.iptv.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProjectTestAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private String[] mProjectListName;

	public ProjectTestAdapter(Context mContext, String[] listName) {
		this.mContext = mContext;
		this.mProjectListName = listName;
		mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mProjectListName.length;
	}

	@Override
	public Object getItem(int position) {
		return mProjectListName[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemHolder holder = null;
		if (convertView == null) {
			holder = new ListItemHolder();
			convertView = mInflater.inflate(R.layout.project_list_item, null);
			holder.itemName = (TextView) convertView.findViewById(R.id.text_show);
			convertView.setTag(holder);
		} else {
			holder = (ListItemHolder) convertView.getTag();
		}
		holder.itemName.setText(mProjectListName[position]);
		return convertView;
	}

	class ListItemHolder {
		TextView itemName;
	}

}

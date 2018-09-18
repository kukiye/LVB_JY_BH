package com.hhzt.iptv.lvb_x.adapter;

import com.hhzt.iptv.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AboutListAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;

	private String[] mSettingListName;

	public AboutListAdapter(Context context, String[] listName) {
		mContext = context;
		mSettingListName = listName;
		mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return mSettingListName.length;
	}

	public Object getItem(int position) {
		return mSettingListName[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemHolder holder = null;
		if (convertView == null) {
			holder = new ListItemHolder();
			convertView = mInflater.inflate(R.layout.setting_list_item, null);
			holder.itemName = (TextView) convertView.findViewById(R.id.text_show);
			holder.itemImageBefore = (TextView) convertView.findViewById(R.id.before_image_show);

			convertView.setTag(holder);
		} else {
			holder = (ListItemHolder) convertView.getTag();
		}
		setAllValues(holder, position);

		return convertView;
	}

	private void setAllValues(ListItemHolder holder, int position) {
		holder.itemName.setText(mSettingListName[position]);
		holder.itemImageBefore.setVisibility(View.INVISIBLE);
	}

	class ListItemHolder {
		TextView itemName;
		TextView itemImageBefore;
	}
}

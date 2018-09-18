package com.hhzt.iptv.lvb_x.adapter;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SettingMainListAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private String[] mSettingListName;

	public SettingMainListAdapter(Context context, String[] listName) {
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
		switch (position) {
		case 0:
			holder.itemImageBefore.setVisibility(View.VISIBLE);
//			holder.itemImageBefore.setText(UserMgr.getWeatherCityName());
			break;
		case 1:
			holder.itemImageBefore.setVisibility(View.VISIBLE);
//			holder.itemImageBefore.setText(SystemMgr.getSystemLangName());
			break;
		case 4:
			holder.itemImageBefore.setVisibility(View.VISIBLE);
			holder.itemImageBefore.setText(UserMgr.getPairedTag() ? UserMgr.getInteracPassword() : mContext.getString(R.string.not_paired));
			break;
		default:
			holder.itemImageBefore.setVisibility(View.INVISIBLE);
			break;
		}
	}

	class ListItemHolder {
		TextView itemName;
		TextView itemImageBefore;
	}

}

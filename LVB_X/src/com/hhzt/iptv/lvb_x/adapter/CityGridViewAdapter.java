package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.CityItemModel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CityGridViewAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<CityItemModel> mItems;

	public CityGridViewAdapter(Context context, ArrayList<CityItemModel> items) {
		mContext = context;
		mItems = items;
		mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		CityItemHolder holder = null;
		if (convertView == null) {
			holder = new CityItemHolder();
			convertView = mInflater.inflate(R.layout.cities_item, null);
			holder.cityName = (TextView) convertView.findViewById(R.id.city_name);
			convertView.setTag(holder);
		} else {
			holder = (CityItemHolder) convertView.getTag();
		}
		setAllValues(holder, position);

		return convertView;
	}

	private void setAllValues(CityItemHolder holder, int position) {
		String cityName = mItems.get(position).getCityname();
		holder.cityName.setText(cityName);
	}

	class CityItemHolder {
		TextView cityName;
	}
}

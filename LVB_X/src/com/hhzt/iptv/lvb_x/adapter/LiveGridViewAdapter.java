package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.LiveMainModel;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class LiveGridViewAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<LiveMainModel> mItems;

	public LiveGridViewAdapter(Context context, ArrayList<LiveMainModel> items) {
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
		LiveItemHolder holder = null;
		if (convertView == null) {
			holder = new LiveItemHolder();
			convertView = mInflater.inflate(R.layout.live_item, null);
			holder.liveName = (ImageView) convertView.findViewById(R.id.live_name);
			convertView.setTag(holder);
		} else {
			holder = (LiveItemHolder) convertView.getTag();
		}
		setAllValues(holder, position);
		return convertView;
	}

	private void setAllValues(LiveItemHolder holder, int position) {
		BitmapUtil.setRoundFadeInImage(mItems.get(position).getCallsignurl(), holder.liveName);
	}

	class LiveItemHolder {
		ImageView liveName;
	}
}

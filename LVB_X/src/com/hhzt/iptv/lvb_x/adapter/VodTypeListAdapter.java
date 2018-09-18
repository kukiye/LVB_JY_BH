package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.VodTypeItemModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VodTypeListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<VodTypeItemModel> mList;

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public void setmList(ArrayList<VodTypeItemModel> mList) {
		this.mList = mList;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	final class VodTypeHolder {
		TextView typeName;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		VodTypeHolder holder = null;
		if (convertView == null) {
			holder = new VodTypeHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_vod_type, null);
			holder.typeName = (TextView) convertView.findViewById(R.id.vod_type_item_name);
			convertView.setTag(holder);
		} else {
			holder = (VodTypeHolder) convertView.getTag();
		}
		holder.typeName.setText(mList.get(position).getCategoryname());
		return convertView;
	}
}

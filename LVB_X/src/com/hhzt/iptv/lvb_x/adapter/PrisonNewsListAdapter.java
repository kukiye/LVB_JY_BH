package com.hhzt.iptv.lvb_x.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.PrisonInfoCategoryModel;

public class PrisonNewsListAdapter extends BaseAdapter {

	private Context mContext;
	private List<PrisonInfoCategoryModel> mList;

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public void setmList(List<PrisonInfoCategoryModel> mList) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_news_list, null);
			holder.typeName = (TextView) convertView.findViewById(R.id.news_txt);
			convertView.setTag(holder);
		} else {
			holder = (VodTypeHolder) convertView.getTag();
		}
		holder.typeName.setText(mList.get(position).getCategoryName());
		return convertView;
	}
}

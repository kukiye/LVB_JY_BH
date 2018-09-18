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
import com.hhzt.iptv.lvb_x.model.PrsionInfoModel;
import com.hhzt.iptv.lvb_x.utils.DateTimeUtil;

public class NewsContentListAdapter extends BaseAdapter {

	private Context mContext;
	private List<PrsionInfoModel> mList;

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public void setmList(List<PrsionInfoModel> mList) {
		this.mList = mList;
		notifyDataSetChanged();
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
		TextView news_title;
		TextView news_time;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		VodTypeHolder holder = null;
		if (convertView == null) {
			holder = new VodTypeHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_content_list, null);
			holder.news_title = (TextView) convertView.findViewById(R.id.news_title);
			holder.news_time = (TextView) convertView.findViewById(R.id.news_time);
			convertView.setTag(holder);
		} else {
			holder = (VodTypeHolder) convertView.getTag();
		}
		setValue(holder, position);
		return convertView;
	}

	private void setValue(VodTypeHolder holder, int position) {
		PrsionInfoModel model = mList.get(position);
		holder.news_title.setText(model.getTitle());
		holder.news_time.setText(DateTimeUtil.toTime(model.getUpdateTime(), DateTimeUtil.DATE_FORMATE_YEAR_MONTH_DAY));
	}
}

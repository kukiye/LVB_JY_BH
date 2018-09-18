package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.model.LiveDateMessageModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LiveBackDateSelectAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<LiveDateMessageModel> mDateList;
	private String mDate = Constant.IPTV_LIVE_BACK_DEFAULT_DATE;

	public LiveBackDateSelectAdapter(Context context, ArrayList<LiveDateMessageModel> list) {
		mContext = context;
		mDateList = list;
	}

	public void setSelectDate(String date) {
		mDate = date;
	}

	public String getSelectDate() {
		return mDate;
	}

	@Override
	public int getCount() {
		return mDateList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mDateList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LiveMainHolder holder = null;
		if (null == convertView) {
			holder = new LiveMainHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.live_back_date_item, null);
			holder.date = (TextView) convertView.findViewById(R.id.date_monthsday_text);
			holder.week = (TextView) convertView.findViewById(R.id.date_week_text);
			convertView.setTag(holder);
		} else {
			holder = (LiveMainHolder) convertView.getTag();
		}
		setAllValues(holder, position);
		return convertView;
	}

	@SuppressLint("NewApi")
	private void setAllValues(LiveMainHolder holder, int position) {
		if (position == 0) {
			holder.date.setText(mContext.getString(R.string.date_today));
		} else {
			holder.date.setText(mDateList.get(position).getMonthDay());
		}

		holder.week.setText(mDateList.get(position).getWeek());
		if (mDate.equalsIgnoreCase(mDateList.get(position).getMonthDay())) {
			holder.week.setBackgroundResource(R.drawable.live_back_date_select_tag);
		} else {
			holder.week.setBackgroundResource(0);
		}

		if (mDate.equalsIgnoreCase(Constant.IPTV_LIVE_BACK_DEFAULT_DATE)) {
			if (position == 0) {
				holder.week.setBackgroundResource(R.drawable.live_back_date_select_tag);
			}
		}
	}

	public static class LiveMainHolder {
		public TextView date;
		public TextView week;
	}

}

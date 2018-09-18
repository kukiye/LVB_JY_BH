/**
 * 作者：   Johnson
 * 日期：   2014年7月9日下午7:53:03
 * 包名：    com.hhzt.iptv.lvb_x.adapter
 * 工程名：LVB_X
 * 文件名：DateChooseAdapter.java
 */
package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;

import com.hhzt.iptv.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DateChooseAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<String> mDateTimeItemList;

	public DateChooseAdapter(Context context, ArrayList<String> dateTimeItemList) {
		mContext = context;
		mDateTimeItemList = dateTimeItemList;
	}

	@Override
	public int getCount() {
		return mDateTimeItemList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mDateTimeItemList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		DateChooseHolder holder = null;
		if (null == arg1) {
			holder = new DateChooseHolder();
			arg1 = LayoutInflater.from(mContext).inflate(R.layout.sub_datechoose_subitem, null);
			holder.dateText = (TextView) arg1.findViewById(R.id.dateSpe);

			arg1.setTag(holder);
		} else {
			holder = (DateChooseHolder) arg1.getTag();
		}

		holder.dateText.setText(mDateTimeItemList.get(arg0));
		return arg1;
	}

	final class DateChooseHolder {
		public TextView dateText;
	}

}

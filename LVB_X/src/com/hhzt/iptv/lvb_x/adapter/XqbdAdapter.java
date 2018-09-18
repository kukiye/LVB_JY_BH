/**
 * 作者：   Johnson
 * 日期：   2014年7月9日下午7:53:03
 * 包名：    com.hhzt.iptv.lvb_x.adapter
 * 工程名：LVB_X
 * 文件名：DateChooseAdapter.java
 */
package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.prisonaffairs.Score;
import com.hhzt.iptv.lvb_x.model.prisonaffairs.Xqbd;
import com.hhzt.iptv.lvb_x.utils.DateTimeUtil;

public class XqbdAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<Xqbd> mData;

	public XqbdAdapter(Context context, ArrayList<Xqbd> datas) {
		mContext = context;
		mData = datas;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Holder holder = null;
		if (null == arg1) {
			holder = new Holder();
			arg1 = LayoutInflater.from(mContext).inflate(
					R.layout.sub_affairs_item, null);
			holder.item1 = (TextView) arg1.findViewById(R.id.txt_item1);
			holder.item2 = (TextView) arg1.findViewById(R.id.txt_item2);
			holder.item3 = (TextView) arg1.findViewById(R.id.txt_item3);
			holder.item4 = (TextView) arg1.findViewById(R.id.txt_item4);
			holder.item5 = (TextView) arg1.findViewById(R.id.txt_item5);
			holder.item6 = (TextView) arg1.findViewById(R.id.txt_item6);
			holder.item7 = (TextView) arg1.findViewById(R.id.txt_item7);
			holder.item8 = (TextView) arg1.findViewById(R.id.txt_item8);
			holder.item9 = (TextView) arg1.findViewById(R.id.txt_item9);
			arg1.setTag(holder);
		} else {
			holder = (Holder) arg1.getTag();
		}
		holder.item7.setVisibility(View.GONE);
		holder.item8.setVisibility(View.GONE);
		holder.item9.setVisibility(View.GONE);
		setValue(holder, arg0);
		return arg1;
	}

	private void setValue(Holder holder, int position) {
		Xqbd model = mData.get(position);
		holder.item1.setText(model.getPrisonName());
		holder.item2.setText(model.getName());
		holder.item3.setText(model.getCode());
		holder.item4.setText(model.getType());
		holder.item5.setText(model.getNumber());
		holder.item6.setText(DateTimeUtil.toTime(model.getDate(),
				DateTimeUtil.DATE_FORMATE_YEAR_MONTH_DAY));
		holder.item7.setText(DateTimeUtil.toTime(model.getStartDate(),
				DateTimeUtil.DATE_FORMATE_YEAR_MONTH_DAY));
		holder.item8.setText(model.getPrisonTerm());
	}

	final class Holder {
		public TextView item1;
		public TextView item2;
		public TextView item3;
		public TextView item4;
		public TextView item5;
		public TextView item6;
		public TextView item7;
		public TextView item8;
		public TextView item9;
	}

}
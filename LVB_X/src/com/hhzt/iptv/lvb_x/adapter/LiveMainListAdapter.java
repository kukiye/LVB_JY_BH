/**
 * 作者：   Johnson
 * 日期：   2014年6月30日下午7:04:53
 * 包名：    com.hhzt.iptv.lvb_x.adapter
 * 工程名：LVB_X
 * 文件名：LiveMainListAdapter.java
 */
package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.LiveMainModel;

public class LiveMainListAdapter extends BaseAdapter {

	private Context mContext;
	private int mItemSelectionIndex;
	private ArrayList<LiveMainModel> mLiveMainModels;

	public LiveMainListAdapter(Context context) {
		mContext = context;
	}

	public void setLiveMainModels(ArrayList<LiveMainModel> liveMainModels) {
		mLiveMainModels = liveMainModels;
	}

	public void setSelection(int index) {
		mItemSelectionIndex = index;
	}

	public int getSelection() {
		return mItemSelectionIndex;
	}

	@Override
	public int getCount() {
		return mLiveMainModels.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mLiveMainModels.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return mLiveMainModels.get(arg0).getId();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LiveMainHolder holder = null;
		if (null == convertView) {
			holder = new LiveMainHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.sub_mainlive_item, null);
			holder.channelIndex = (TextView) convertView.findViewById(R.id.channle_num);
			holder.channelName = (TextView) convertView.findViewById(R.id.channle_name);
			holder.channelChoosedTag = (Button) convertView.findViewById(R.id.channel_current_tag);
			convertView.setTag(holder);
		} else {
			holder = (LiveMainHolder) convertView.getTag();
		}
		setAllValues(holder, position);
		return convertView;
	}

	private void setAllValues(LiveMainHolder holder, int position) {
		holder.channelIndex.setText(mLiveMainModels.get(position).getChannelnumber() + "");
		holder.channelName.setText(mLiveMainModels.get(position).getChannelname());
		if (mItemSelectionIndex == position) {
			holder.channelChoosedTag.setVisibility(View.VISIBLE);
		} else {
			holder.channelChoosedTag.setVisibility(View.INVISIBLE);
		}
	}

	public static class LiveMainHolder {
		public TextView channelIndex;
		public TextView channelName;
		public Button channelChoosedTag;
	}

}

/**
 * 作者：   Johnson
 * 日期：   2014年6月30日下午7:04:53
 * 包名：    com.hhzt.iptv.lvb_x.adapter
 * 工程名：LVB_X
 * 文件名：LiveMainListAdapter.java
 */
package com.hhzt.iptv.lvb_x.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.LiveMainModel;

import java.util.ArrayList;

public class TvLiveListAdapter extends BaseAdapter {

    private Context mContext;
    private int mItemSelectionIndex = -1;
    private ArrayList<LiveMainModel> mLiveMainModels;

    public TvLiveListAdapter(Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tv_live_item, null);
            holder.channelName = (TextView) convertView.findViewById(R.id.tv_channel_name);
            holder.itemLayout = (LinearLayout) convertView.findViewById(R.id.item_layout);
            convertView.setTag(holder);
        } else {
            holder = (LiveMainHolder) convertView.getTag();
        }
        setAllValues(holder, position);
        return convertView;
    }

    private void setAllValues(LiveMainHolder holder, int position) {
        holder.channelName.setText(mLiveMainModels.get(position).getChannelname());
//        if (mItemSelectionIndex == position) {
//            holder.itemLayout.setBackgroundResource(R.drawable.tv_item_focus);
//        } else {
//            holder.itemLayout.setBackgroundResource(R.drawable.tv_item_normal);
//        }
    }

    public static class LiveMainHolder {
        TextView channelName;
        LinearLayout itemLayout;
    }

}

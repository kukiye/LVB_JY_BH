package com.hhzt.iptv.lvb_x.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;

import java.util.ArrayList;

public class MainFocusListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<VodDetailItemModel> mList;

    public MainFocusListAdapter(Context context, ArrayList<VodDetailItemModel> items) {
        mContext = context;
        mList = items;
        mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return mList.size();
    }

    public VodDetailItemModel getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.main_focus_item, null);
            holder.tvNews = (TextView) convertView.findViewById(R.id.tv_news);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setAllValues(holder, position);

        return convertView;
    }

    private void setAllValues(ViewHolder holder, int position) {
        String model = mList.get(position).getProgramname();
        holder.tvNews.setText(model);


    }

    class ViewHolder {
        TextView tvNews;
    }
}

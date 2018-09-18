package com.hhzt.iptv.lvb_x.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.MainmenuModel;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;

import java.util.ArrayList;

public class MainGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<MainmenuModel> mList;

    public MainGridViewAdapter(Context context, ArrayList<MainmenuModel> items) {
        mContext = context;
        mList = items;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.main_gride_item, null);
            holder.programName = (TextView) convertView.findViewById(R.id.tv_program_name);
            holder.ivLogo = (ImageView) convertView.findViewById(R.id.iv_logo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setAllValues(holder, position);

        return convertView;
    }

    private void setAllValues(ViewHolder holder, int position) {
        MainmenuModel model = mList.get(position);
        String modelName = model.getName();
        holder.programName.setText(modelName);

        String menulogourl = model.getMenulogourl();
        BitmapUtil.setRoundFadeInImage(menulogourl, holder.ivLogo);


    }

    class ViewHolder {
        TextView programName;
        ImageView ivLogo;
    }
}

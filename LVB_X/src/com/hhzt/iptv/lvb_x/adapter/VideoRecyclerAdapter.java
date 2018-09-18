/*
 * Copyright (C) 2016 hejunlin <hejunlin2013@gmail.com>
 * github:https://github.com/hejunlin2013/TVSample
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hhzt.iptv.lvb_x.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

import java.util.List;

/**
 * Created by hejunlin on 2015/10/16.
 * blog: http://blog.csdn.net/hejjunlin
 */
public class VideoRecyclerAdapter extends RecyclerView.Adapter<VideoRecyclerAdapter.ViewHolder> {

    // 数据集
    private List<VodDetailItemModel> mDataset;
    private Context mContext;
    private int id;
    private View.OnFocusChangeListener mOnFocusChangeListener;
    private OnBindListener onBindListener;
    private static final String TAG = VideoRecyclerAdapter.class.getSimpleName();

    public interface OnBindListener {
        void onBind(View view, int i);
    }

    public VideoRecyclerAdapter(Context context, List<VodDetailItemModel> dataset) {
        super();
        mContext = context;
        mDataset = dataset;
    }

    public VideoRecyclerAdapter(Context context, List<VodDetailItemModel> dataset, int id) {
        super();
        mContext = context;
        mDataset = dataset;
        this.id = id;
        Log.d(TAG, "mDataset " + mDataset.size());
    }

    public VideoRecyclerAdapter(Context context, List<VodDetailItemModel> dataset, int id, View.OnFocusChangeListener onFocusChangeListener) {
        super();
        mContext = context;
        mDataset = dataset;
        this.id = id;
        this.mOnFocusChangeListener = onFocusChangeListener;
    }

    public void setOnBindListener(OnBindListener onBindListener) {
        this.onBindListener = onBindListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int resId = R.layout.framwork_vod_item;
        if (this.id > 0) {
            resId = this.id;
        }
        View view = LayoutInflater.from(mContext).inflate(resId, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (mDataset.size() == 0) {
            Log.d(TAG, "mDataset has no data!");
            return;
        }
        //

        viewHolder.item_name.setText(mDataset.get(position).getProgramname());
        String imageurl = mDataset.get(position).getImageurl();
        if (!StringUtil.isNull(imageurl)) {

            Glide.with(mContext).load(imageurl).into(viewHolder.item_image);
        } else {
            //默认图片
            viewHolder.item_image.setImageResource(R.drawable.video_default);
        }

        viewHolder.itemView.setTag(position);
        viewHolder.itemView.setOnFocusChangeListener(mOnFocusChangeListener);
        if (onBindListener != null) {
            onBindListener.onBind(viewHolder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //     ImageView vip;
        TextView item_name;
        ImageView item_image;
        RelativeLayout item__view_container;
        //        Button item_btn;

        public ViewHolder(View view) {
            super(view);
            item_name = (TextView) view.findViewById(R.id.item_name);
            item_image = (ImageView) view.findViewById(R.id.item_image);
            //   vip = (ImageView)view.findViewById(R.id.vip);
            //            item_btn = (Button) view.findViewById(R.id.item_btn);
            item__view_container = (RelativeLayout) view.findViewById(R.id.item__view_container);
        }
    }

    public void setData(List<VodDetailItemModel> data) {
        this.mDataset = data;
    }

}

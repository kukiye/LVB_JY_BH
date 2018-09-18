package com.hhzt.iptv.lvb_x.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kobe on 2015/12/19 16:04.
 * 邮箱：quzhongxiang_kobe@163.com
 */
public class ThreeClassMediaAdapter extends RecyclerView.Adapter<ThreeClassMediaAdapter.ViewHolder> {

    private List<VodDetailItemModel> list;
    //    private List<TextView> text =new ArrayList<>();
    //    private List<ImageView> image =new ArrayList<>();

    private LayoutInflater mInflater;
    public int width;
    public int heigth;
    private Activity mContext;
    private BaseFragment ment;
    private RecyclerView mRecyleview;
    private long mLastKeyDownTime;

    public void notifyDataSetChanged(ArrayList<VodDetailItemModel> factVodDetailList) {
        this.list = factVodDetailList;
        notifyDataSetChanged();
    }

    public interface OnItemClickLitener {
        void onItemClick(VodDetailItemModel bean, int position);

        void onKeyListener(int direction, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ThreeClassMediaAdapter(Activity context, RecyclerView recyleview, BaseFragment fragment) {
        mRecyleview = recyleview;
        width = recyleview.getWidth();
        heigth = recyleview.getHeight();
        mContext = context;
        ment = fragment;
        mInflater = LayoutInflater.from(context);

    }

    public void setDataList(List<VodDetailItemModel> liststr) {
        list = liststr;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //     ImageView vip;
        TextView item_name;
        ImageView item_image;
        RelativeLayout item__view_container;
        Button item_btn;

        public ViewHolder(View view) {
            super(view);
            item_name = (TextView) view.findViewById(R.id.item_name);
            item_image = (ImageView) view.findViewById(R.id.item_image);
            //   vip = (ImageView)view.findViewById(R.id.vip);
            item_btn = (Button) view.findViewById(R.id.item_btn);
            item__view_container = (RelativeLayout) view.findViewById(R.id.item__view_container);
        }
    }

    //此方法是为了创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.framwork_vod_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //        //  holder.setIsRecyclable(false);
        if (null != mOnItemClickLitener) {
            holder.item_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(list.get(pos), pos);
                }
            });
        }
        //        holder.item_btn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        //            @Override
        //            public void onFocusChange(View v, boolean hasFocus) {
        //                if (hasFocus) {
        //                    //       LogUtil.i("TAG","getAdapterPosition:"+holder.getAdapterPosition());
        //                    holder.item_name.setSelected(true);
        //                } else {
        //                    holder.item_name.setSelected(false);
        //                }
        //            }
        //        });
        //        ViewGroup.LayoutParams lp = holder.item__view_container.getLayoutParams();
        //        //      holder.setIsRecyclable(false);
        //        //        LogUtil.i("TAG","12345position:"+position);
        //        //        LogUtil.i("TAG","12345holder:"+holder.getAdapterPosition());
        //
        //        lp.width = width / 5 - 20;
        //        holder.item__view_container.setLayoutParams(lp);
        //   LogUtil.i("TAG","11111111:");
        return holder;
    }

    //此方法是为了给RecyclerView的子项进行赋值的，会在每个子项滚动到屏幕内的时候执行
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.item_name.setText(list.get(position).getProgramname());
        String imageurl = list.get(position).getImageurl();
        if (!StringUtil.isNull(imageurl)) {

            Glide.with(mContext).load(imageurl).into(holder.item_image);
        } else {
            //默认图片
            holder.item_image.setImageResource(R.drawable.default_vod_logo);
        }
        //  holder.item_image.setBackgroundResource(R.mipmap.vip);
        // BitmapUtil.setRandomImage(list.get(position).getPosterLocalImg(),holder.item_image);


        //        if(list.get(position).getIsFree() == 0){
        //            holder.vip.setVisibility(View.VISIBLE);
        //        }

        holder.item_btn.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                //
                //         LogUtil.i("TAG","getAdapter"+holder.getAdapterPosition());
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                            if (holder.getAdapterPosition() == 0 || holder.getAdapterPosition() == 1) {
                                //                                    if(null != mOnItemClickLitener){
                                //                                        mOnItemClickLitener.onKeyListener(KeyEvent.KEYCODE_DPAD_LEFT,holder.getAdapterPosition());
                                //                                    }
                                return true;
                            }
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        ////                       ((IqiyiVodMainFragment)ment).setHideButtonFocusable();
                        //                        LogUtil.i("TAG","LIST:"+list.size());
                        //                        LogUtil.i("TAG","position:"+holder.getAdapterPosition());

                        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                            if (holder.getAdapterPosition() == list.size() - 1 || holder.getAdapterPosition() == list.size() - 2) {
                                return true;
                            }
                            //                            long current = System.currentTimeMillis();
                            //                            boolean res ;
                            //                            if (current - mLastKeyDownTime < 150) {
                            //                                res = true;
                            //                            } else {
                            //                                if(holder.getAdapterPosition() == list.size()-1 ||holder.getAdapterPosition() == list.size()-2){
                            //                                    res = true;
                            //                                    return res;
                            //                                }else{
                            //                                    ((Button)mRecyleview.getTag(Config.setmRecyleview(holder.getAdapterPosition()))).requestFocus();
                            //                                  res = false;
                            //                                }
                            //                            }
                            //                            mLastKeyDownTime = current;
                            //                            return res;
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_UP:
                        //           ((IqiyiVodMainFragment)ment).setButtonFocusable();
                        //    LogUtil.i("TAG","77777");
                        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                            //        LogUtil.i("TAG","8888");
                            if (holder.getAdapterPosition() % 2 == 0) {
                                //           LogUtil.i("TAG","9999::"+holder.getAdapterPosition()%list.size());
                                //         LogUtil.i("TAG","9999position::"+holder.getAdapterPosition());
                                if (null != mOnItemClickLitener) {
                                    mRecyleview.setTag(R.id.movie_0, holder.item_btn);
                                    mOnItemClickLitener.onKeyListener(KeyEvent.KEYCODE_DPAD_UP, holder.getAdapterPosition());
                                }
                            }
                        }
                        break;
                }
                return false;
            }
        });


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //这个方法是告诉RecyclerView一共有多少个子项
    @Override
    public int getItemCount() {
        return list.size();
    }


}

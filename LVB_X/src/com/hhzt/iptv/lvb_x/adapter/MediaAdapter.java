package com.hhzt.iptv.lvb_x.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.hhzt.iptv.lvb_x.utils.SpringScaleInterpolator;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kobe on 2015/12/19 16:04.
 * 邮箱：quzhongxiang_kobe@163.com
 */
public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private List<VodDetailItemModel> list;

    private LayoutInflater mInflater;
    public int width;
    public int heigth;
    private Activity mContext;
    private RecyclerView mRecyleview;
    private int selectPosition = -1;

    public void notifyDataSetChanged(ArrayList<VodDetailItemModel> factVodDetailList) {
        this.list = factVodDetailList;
        notifyDataSetChanged();
    }

    public void setSelectItem(int position) {
        this.selectPosition = position;
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

    private OnLoadMoreListener mOnLoadMoreListener;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }


    public MediaAdapter(Activity context, RecyclerView recyleview, List<VodDetailItemModel> liststr) {
        mRecyleview = recyleview;
        width = recyleview.getWidth();
        heigth = recyleview.getHeight();
        mContext = context;
        list = liststr;
        mInflater = LayoutInflater.from(context);

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

            //            Glide.with(mContext).load(imageurl).into(holder.item_image);
            BitmapUtil.setFadeInImage(imageurl, holder.item_image);

        }
        Drawable drawable = holder.item_image.getDrawable();
        if (drawable == null) {
            holder.item_image.setBackgroundResource(R.drawable.video_default);
        }


        holder.item_btn.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                //
                LogUtil.i("getAdapterPosition===" + holder.getAdapterPosition());
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            if (null != mOnItemClickLitener) {
                                mOnItemClickLitener.onKeyListener(KeyEvent.KEYCODE_DPAD_LEFT, holder.getAdapterPosition());
                            }
                            if (holder.getAdapterPosition() == 0 || holder.getAdapterPosition() == 1) {
                                return true;
                            }
                        } else {
                            if (holder.getAdapterPosition() == 0 || holder.getAdapterPosition() == 1) {
                                ObjectAnimator animatorX = ObjectAnimator.ofFloat(holder.itemView, "translationX", 0, 20, 40, 20, 0);
                                ObjectAnimator animatorY = ObjectAnimator.ofFloat(holder.itemView, "translationY", 0, 0);
                                AnimatorSet set = new AnimatorSet();
                                set.setDuration(1200);
                                set.setInterpolator(new SpringScaleInterpolator(0.6f));
                                set.playTogether(animatorX, animatorY);
                                set.start();
                                return true;
                            }
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:

                        LogUtil.i("KEYCODE_DPAD_RIGHT....");

                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            if (null != mOnItemClickLitener) {
                                mOnItemClickLitener.onKeyListener(KeyEvent.KEYCODE_DPAD_RIGHT, holder.getAdapterPosition());
                            }
                            if (holder.getAdapterPosition() == list.size() - 1 || holder.getAdapterPosition() == list.size() - 2) {
                                return true;
                            }

                        } else {
                            if (holder.getAdapterPosition() == list.size() - 1 || holder.getAdapterPosition() == list.size() - 2) {
                                mOnLoadMoreListener.onLoadMore();
                                ObjectAnimator animatorX = ObjectAnimator.ofFloat(holder.itemView, "translationX", 0, 20, 40, 20, 0);
                                ObjectAnimator animatorY = ObjectAnimator.ofFloat(holder.itemView, "translationY", 0, 0);
                                AnimatorSet set = new AnimatorSet();
                                set.setDuration(1200);
                                set.setInterpolator(new SpringScaleInterpolator(0.6f));
                                set.playTogether(animatorX, animatorY);
                                set.start();
                                return true;
                            }
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_UP:
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            if (holder.getAdapterPosition() % 2 == 0) {
                                mOnItemClickLitener.onKeyListener(KeyEvent.ACTION_UP, holder.getAdapterPosition());
                            }
                        } else {
                            if (holder.getAdapterPosition() % 2 == 0 && null != mOnItemClickLitener) {
                                mRecyleview.setTag(R.id.movie_0, holder.item_btn);
                                mOnItemClickLitener.onKeyListener(KeyEvent.KEYCODE_DPAD_UP, holder.getAdapterPosition());
                            }
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            if (null != mOnItemClickLitener) {
                                mOnItemClickLitener.onKeyListener(KeyEvent.KEYCODE_DPAD_DOWN, holder.getAdapterPosition());
                            }
                        }
                        break;
                }
                return false;
            }
        });

        if (selectPosition != -1 && selectPosition == position) {
            holder.item_btn.requestFocus();
            selectPosition = -1;
            if (null != mOnItemClickLitener) {
                if (list.size() - position < 7) {
                    mOnItemClickLitener.onKeyListener(KeyEvent.KEYCODE_DPAD_RIGHT, holder.getAdapterPosition() - 1);
                } else {
                    mOnItemClickLitener.onKeyListener(KeyEvent.KEYCODE_DPAD_RIGHT, holder.getAdapterPosition());
                }

            }
        }


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

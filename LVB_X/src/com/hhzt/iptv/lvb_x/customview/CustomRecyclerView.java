package com.hhzt.iptv.lvb_x.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hhzt.iptv.lvb_x.interfaces.onItemKeyListener;
import com.hhzt.iptv.lvb_x.log.LogUtil;

import java.util.List;


/**
 * author songwenju
 * email：songwenju@outlook.com
 * 针对电视的自定义的RecyclerView，该RecyclerView具有以下功能：
 * 1.响应五向键，上下左右会跟着移动，并获得焦点，在获得焦点时会抬高
 * 2.在鼠标hover在条目上时会获得焦点。
 * 3.添加了条目的点击和长按事件
 * 4.添加了是否第一个可见条目和是否是最后一个可见条目的方法
 * 5.在item获得焦点时和失去焦点时，这里有相应的回调方法。
 */
public class CustomRecyclerView extends RecyclerView {

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private onItemKeyListener itemKeyUpListerner;

    public void setonItemKeyUpListener(onItemKeyListener itemKeyUpListerner) {
        this.itemKeyUpListerner = itemKeyUpListerner;

    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager  设置RecyclerView对应的manager
     * @param n  要跳转的位置
     */
    public  void moveToPosition(StaggeredGridLayoutManager manager, int n) {
        manager.scrollToPositionWithOffset(n, 0);
//        manager.setStackFromEnd(true);
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager   设置RecyclerView对应的manager
     * @param mRecyclerView  当前的RecyclerView
     * @param n  要跳转的位置
     */
    public  void moveToPosition( RecyclerView mRecyclerView, int n) {


        int firstItem = getFirstPosition();
        int lastItem = getLastPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }

    }

    /**
     * 目标项是否在最后一个可见项之后
     */
    private boolean mShouldScroll;
    /**
     * 记录目标项位置
     */
    private int mToPosition;

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    public void smoothMoveToPosition(CustomRecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //在recyclerView的move事件情况下，拦截调，只让它响应五向键和左右箭头移动
        return ev.getAction() == MotionEvent.ACTION_MOVE || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        boolean result = super.dispatchKeyEvent(event);
        View focusView = this.getFocusedChild();
        if (focusView == null) {
            return result;
        } else {
            int dy = 0;
            int dx = 0;
            if (getChildCount() > 0) {
                View firstView = this.getChildAt(0);
                dy = firstView.getHeight();
                dx = firstView.getWidth();
            }

            //处理左右方向键移动Item到边之后RecyclerView跟着移动
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    LogUtil.i("CustomRecyclerView.KEYCODE_DPAD_RIGHT.");
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        return true;
                    } else {
                        View rightView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_RIGHT);
                        LogUtil.i("rightView is null:" + (rightView == null));
                        if (rightView != null) {
                            LogUtil.i("CustomRecyclerView.requestFocusFromTouch.");
                            rightView.requestFocusFromTouch();
                            rightView.requestFocus();
                            return true;
                        } else {
                            this.smoothScrollBy(dx, 0);
                            //移动之后获得焦点，是在scroll方法中处理的。
                            return true;
                        }
                    }
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    View leftView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_LEFT);
                    //                    LogUtil.i(this, "left is null:" + (leftView == null));
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        return true;
                    } else {
                        if (leftView != null) {
                            leftView.requestFocusFromTouch();
                            leftView.requestFocus();
                            return true;
                        } else {
                            this.smoothScrollBy(-dx, 0);
                            return true;
                        }
                    }
                case KeyEvent.KEYCODE_DPAD_UP:
                    View upView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_UP);
                    LogUtil.i("upView is null:" + (upView == null));
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        return true;
                    } else {
                        if (upView != null) {
                            upView.requestFocus();
                            return true;
                        } else {
                            this.smoothScrollBy(0, -dy);
                            if (itemKeyUpListerner != null) {
                                itemKeyUpListerner.onItemkeyUp();
                            }
                            return true;
                        }

                    }
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    View downView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_DOWN);
                    LogUtil.i(" downView is null:" + (downView == null));
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        return true;
                    } else {
                        if (downView != null) {
                            downView.requestFocus();
                            return true;
                        } else {
                            this.smoothScrollBy(0, dy);
                            return true;
                        }
                    }

            }
        }
        return result;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        //        LogUtil.i(this, "CustomRecyclerView.onScrolled.");
        super.onScrolled(dx, dy);
        //响应五向键，在Scroll时去获得下一个焦点
        final View focusView = this.getFocusedChild();
        if (focusView != null) {
            if (dx > 0) {
                View rightView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_RIGHT);
                if (rightView != null) {
                    rightView.requestFocusFromTouch();
                }
            } else {
                View rightView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_LEFT);
                if (rightView != null) {
                    rightView.requestFocusFromTouch();
                }
            }
        }

    }


    /**
     * 第一个条目是否可见
     *
     * @return 可见返回true，不可见返回false
     */
    public boolean isFirstItemVisible() {
        return getFirstPosition() == 0;
    }

    /**
     * 第一个条目可见条目的position
     *
     * @return 可见返回true，不可见返回false
     */
    public int getFirstPosition() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] firstVisibleItems = null;
            firstVisibleItems = ((StaggeredGridLayoutManager) layoutManager).
                    findFirstCompletelyVisibleItemPositions(firstVisibleItems);
            return firstVisibleItems[0];
        } else if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        }
        return -1;
    }


    /**
     * 最后一个条目是否可见
     *
     * @param lineNum    行数
     * @param allItemNum item总数
     * @return 可见返回true，不可见返回false
     */
    public boolean isLastItemVisible(int lineNum, int allItemNum) {
        int position = getLastPosition();
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            //            LogUtil.i(this, "lastVisiblePosition:" + position);
            boolean isVisible = position >= (allItemNum - lineNum);
            //            if (isVisible) {
            //                scrollBy(1, 0);
            //            }
            return isVisible;
        } else if (layoutManager instanceof LinearLayoutManager) {
            return allItemNum - 1 == position;
        }
        return false;
    }


    /**
     * 最后一个条目可见条目的position
     *
     * @return 可见返回true，不可见返回false
     */
    public int getLastPosition() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItems = null;
            lastVisibleItems = ((StaggeredGridLayoutManager) layoutManager).findLastCompletelyVisibleItemPositions(lastVisibleItems);
            return lastVisibleItems[0];
        } else if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }
        return -1;
    }


    /**
     * 自定义的RecyclerView Adapter，他实现了hover获得焦点，放大的效果。
     * 实现了点击事件和长按点击事件。
     *
     * @param <T>
     */
    public static abstract class CustomAdapter<T> extends Adapter<ViewHolder> {
        private LayoutInflater mInflater;
        protected List<T> mData;
        protected Context mContext;

        public interface OnItemClickListener {
            void onItemClick(View view, int position);

            void onItemLongClick(View view, int position);
        }


        private OnItemClickListener mListener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }


        public CustomAdapter(Context context, List<T> data) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //            LogUtil.i(this,"HomeTvAdapter.onCreateViewHolder.");
            View view = mInflater.inflate(onSetItemLayout(), parent, false);
            return onSetViewHolder(view);
        }

        protected abstract ViewHolder onSetViewHolder(View view);

        /**
         * 设置item的layout
         *
         * @return item对应的layout
         */
        protected abstract
        @NonNull
        int onSetItemLayout();

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            //            LogUtil.i(this,"HomeTvAdapter.onBindViewHolder.");
            onSetItemData(holder, position);
            //item可以获得焦点，需要设置这个属性。
            //            holder.itemView.setFocusable(true);
            //            holder.itemView.setOnHoverListener(new View.OnHoverListener() {
            //                @Override
            //                public boolean onHover(View v, MotionEvent event) {
            //                    int what = event.getAction();
            //                    switch (what) {
            //                        case MotionEvent.ACTION_HOVER_ENTER:
            //                            RecyclerView recyclerView = (RecyclerView) holder.itemView.getParent();
            //                            int[] location = new int[2];
            //                            recyclerView.getLocationOnScreen(location);
            //                            int x = location[0];
            //                            //                            LogUtil.i("swj","GalleryAdapter.onHover.x="+x +",width = "+(recyclerView.getWidth()+x));
            //                            //为了防止滚动冲突，在滚动时候，获取焦点为了显示全，会回滚，这样会导致滚动停止
            //                            if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
            //                                //当超出RecyclerView的边缘时不去响应滚动
            //                                if (event.getRawX() > recyclerView.getWidth() + x || event.getRawX() < x) {
            //                                    return true;
            //                                }
            //                                //鼠标进入view，争取到焦点
            //                                v.requestFocusFromTouch();
            //                                v.requestFocus();
            //                                //                                LogUtil.i(this,"HomeTvAdapter.onHover.position:"+position);
            //                                focusStatus(v, position);
            //                            }
            //                            break;
            //                        case MotionEvent.ACTION_HOVER_MOVE:  //鼠标在view上移动
            //                            break;
            //                        case MotionEvent.ACTION_HOVER_EXIT:  //鼠标离开view
            //                            normalStatus(v, position);
            //                            break;
            //                    }
            //                    return false;
            //                }
            //            });
            //
            //            holder.itemView.setOnKeyListener(new View.OnKeyListener() {
            //                @Override
            //                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            //
            //                    LogUtil.i("getAdapter" + holder.getAdapterPosition());
            //
            //                    switch (keyCode) {
            //                        case KeyEvent.KEYCODE_DPAD_LEFT:
            //                            if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
            //
            //                                if (holder.getAdapterPosition() == 0 || holder.getAdapterPosition() == 1) {
            //                                    return true;
            //                                }
            //                            } else {
            //                                if (holder.getAdapterPosition() == 0 || holder.getAdapterPosition() == 1) {
            //                                    ObjectAnimator animatorX = ObjectAnimator.ofFloat(holder.itemView, "translationX", 0, 20, 40, 20, 0);
            //                                    ObjectAnimator animatorY = ObjectAnimator.ofFloat(holder.itemView, "translationY", 0, 0);
            //                                    AnimatorSet set = new AnimatorSet();
            //                                    set.setDuration(1200);
            //                                    set.setInterpolator(new SpringScaleInterpolator(0.6f));
            //                                    set.playTogether(animatorX, animatorY);
            //                                    set.start();
            //                                    return true;
            //                                }
            //                            }
            //                            break;
            //                        case KeyEvent.KEYCODE_DPAD_RIGHT:
            //
            //                            LogUtil.i("itemView  KEYCODE_DPAD_RIGHT....");
            //
            //                            if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
            //
            //                                if (holder.getAdapterPosition() == mData.size() - 1 || holder.getAdapterPosition() == mData.size() - 2) {
            //                                    return true;
            //                                }
            //
            //                            } else {
            //                                LogUtil.i("holder.getAdapterPosition()==" + holder.getAdapterPosition());
            //                                if (holder.getAdapterPosition() == mData.size() - 1 || holder.getAdapterPosition() == mData.size() - 2) {
            //                                    ObjectAnimator animatorX = ObjectAnimator.ofFloat(holder.itemView, "translationX", 0, 20, 40, 20, 0);
            //                                    ObjectAnimator animatorY = ObjectAnimator.ofFloat(holder.itemView, "translationY", 0, 0);
            //                                    AnimatorSet set = new AnimatorSet();
            //                                    set.setDuration(1200);
            //                                    set.setInterpolator(new SpringScaleInterpolator(0.6f));
            //                                    set.playTogether(animatorX, animatorY);
            //                                    set.start();
            //                                    return true;
            //                                }
            //                            }
            //                            break;
            //
            //                    }
            //                    return false;
            //                }
            //            });
            //
            //
            //            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            //                @Override
            //                public void onFocusChange(View v, boolean hasFocus) {
            //                    if (hasFocus) {
            //                        focusStatus(v, position);
            //                    } else {
            //                        normalStatus(v, position);
            //                    }
            //                }
            //            });
            //
            //            if (mListener != null) {
            //                holder.itemView.setOnClickListener(new View.OnClickListener() {
            //                    @Override
            //                    public void onClick(View v) {
            //                        mListener.onItemClick(v, position);
            //                    }
            //                });
            //
            //                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            //                    @Override
            //                    public boolean onLongClick(View v) {
            //                        mListener.onItemLongClick(v, position);
            //                        return true;
            //                    }
            //                });
            //            }
        }

        /**
         * 为Item的内容设置数据
         *
         * @param viewHolder viewHolder
         * @param position   位置
         */
        protected abstract void onSetItemData(ViewHolder viewHolder, int position);

        /**
         * item获得焦点时调用
         *
         * @param itemView view
         * @param position
         */
        private void focusStatus(View itemView, int position) {
            if (itemView == null) {
                return;
            }
            onItemFocus(itemView, position);
        }

        /**
         * 当item获得焦点时处理
         *
         * @param itemView itemView
         * @param position
         */
        protected abstract void onItemFocus(View itemView, int position);


        /**
         * item失去焦点时
         *
         * @param itemView item对应的View
         * @param position
         */
        private void normalStatus(View itemView, int position) {
            if (itemView == null) {
                return;
            }


            onItemNormal(itemView, position);

        }

        /**
         * 当条目失去焦点时调用
         *
         * @param itemView 条目对应的View
         */
        protected abstract void onItemNormal(View itemView, int position);

        @Override
        public int getItemCount() {
            if (mData != null) {
                return getCount();
            } else {
                return 0;
            }
        }

        protected abstract int getCount();
    }
}

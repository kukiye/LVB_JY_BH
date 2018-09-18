package com.hhzt.iptv.lvb_x.adapter;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.hhzt.iptv.R;


/**
 * Created by Administrator on 2017/6/6.
 */

public class MyStaggeredGridLayoutManager extends StaggeredGridLayoutManager {
    int mOrientation;

    public MyStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
        mOrientation = orientation;
    }


    /**
     * Base class which scrolls to selected view in onStop().
     */
    //    abstract class GridLinearSmoothScroller extends LinearSmoothScroller {
    //
    //
    //        public GridLinearSmoothScroller(Context context) {
    //            super(context);
    //        }
    //
    //        /**
    //         * 滑动完成后,让该targetPosition 处的item获取焦点
    //         */
    //        @Override
    //        protected void onStop() {
    //            super.onStop();
    //            View targetView = findViewByPosition(getTargetPosition());
    //
    //            if (targetView != null) {
    //                targetView.requestFocus();
    //            }
    //            super.onStop();
    //        }
    //
    //    }
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                if (targetPosition == 0) {
                    return null;
                }
                if (mOrientation == HORIZONTAL) {
                    return new PointF(targetPosition, 0);
                } else {
                    return new PointF(0, targetPosition);
                }
            }

            //该方法控制速度。
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 1f;
            }

            //该方法计算滑动所需时间。在此处间接控制速度。
            @Override
            protected int calculateTimeForScrolling(int dx) {
                      /*
                   控制距离, 然后根据上面那个方(calculateSpeedPerPixel())提供的速度算出时间,
                   默认一次 滚动 TARGET_SEEK_SCROLL_DISTANCE_PX = 10000个像素,
                   在此处可以减少该值来达到减少滚动时间的目的.
                */
                //间接计算时提高速度，也可以直接在calculateSpeedPerPixel提高
                if (dx > 3000) {
                    dx = 3000;
                }
                int time = super.calculateTimeForScrolling(dx);

                return time;
            }

            @Override
            protected void onStop() {
                View targetView = findViewByPosition(getTargetPosition() - 1);
                if (targetView != null) {
                    Button button = (Button) targetView.findViewById(R.id.item_btn);
                    button.requestFocus();
                }
                super.onStop();
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
    //    public PointF computeVectorForPosition(int targetPosition) {
    //        return super.computeScrollVectorForPosition(targetPosition);
    //
    //      //  return super.computeHorizontalScrollExtent(RecyclerView.State.class.);
    //       // return  null;
    //    }

}

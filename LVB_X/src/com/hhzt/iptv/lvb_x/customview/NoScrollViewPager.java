package com.hhzt.iptv.lvb_x.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Package: com.hhzt.nw.view
 * @Description:禁止ViewPager的左右滑动
 * @author: Aaron
 */
public class NoScrollViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public void setNoScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        //        if (noScroll)
        //            return false;
        //        else
        //            return super.onTouchEvent(arg0);
        return isCanScroll && super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        //        if (noScroll)
        //            return false;
        //        else
        //            return super.onInterceptTouchEvent(arg0);
        return isCanScroll && super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    //屏蔽按键左右滑动
//    @Override
    //    public boolean executeKeyEvent(KeyEvent event) {
    //
    //        return false;
    //    }

}

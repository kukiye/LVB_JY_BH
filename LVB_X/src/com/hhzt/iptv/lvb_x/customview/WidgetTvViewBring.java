package com.hhzt.iptv.lvb_x.customview;

import android.view.View;
import android.view.ViewGroup;

/**
 * 让ViewGroup中的控件在最上层 <br>
 * ListView, GridView, ViewGroup中使用. 想要置顶的子控件，不要忘记调用 bringToFront() 噢.
 * 
 * @author hailongqiu
 *
 */
public class WidgetTvViewBring {

	private int position = 0;

	public WidgetTvViewBring() {
	}

	/**
	 * @param vg
	 *
	  1.clipChild用来定义他的子控件是否要在他应有的边界内进行绘制。 默认情况下，clipChild被设置为true。 也就是不允许进行扩展绘制
	 *
	 * 2. clipToPadding用来定义ViewGroup是否允许在padding中绘制。默认情况下，cliptopadding被设置为ture， 也就是把padding中的值都进行裁切了。
     */
	public WidgetTvViewBring(ViewGroup vg) {
		vg.setClipChildren(false);
		vg.setClipToPadding(false);
		// vg.setChildrenDrawingOrderEnabled(true);
	}

	public void bringChildToFront(ViewGroup vg, View child) {
		position = vg.indexOfChild(child);
		if (position != -1) {
			vg.postInvalidate();
		}
	}

	/**
	 * 此函数 dispatchDraw 中调用. <br>
	 * 原理就是和最后一个要绘制的view，交换了位置. <br>
	 * 因为dispatchDraw最后一个绘制的view是在最上层的. <br>
	 * 这样就避免了使用 bringToFront 导致焦点错乱问题. <br>
	 */
	public int getChildDrawingOrder(int childCount, int i) {
		if (position != -1) {
			if (i == childCount - 1)
				return position;
			if (i == position)
				return childCount - 1;
		}
		return i;
	}

}

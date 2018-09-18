package com.hhzt.iptv.lvb_x.interfaces;

import android.widget.ListView;

public interface IListFragmentClickable {

	void clickItemListener(int position);

	void moveRightListener(int position,ListView listView);
}

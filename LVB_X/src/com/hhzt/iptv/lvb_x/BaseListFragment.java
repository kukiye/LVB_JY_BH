/**
 * 作者：   Johnson
 * 日期：   2014年6月11日下午2:49:21
 * 包名：    com.hhzt.iptv_lvb_x
 * 工程名：LVB_X
 * 文件名：BaseListFragment.java
 */
package com.hhzt.iptv.lvb_x;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseListFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}

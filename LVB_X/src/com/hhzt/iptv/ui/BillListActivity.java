/**
 * 作者：   Johnson
 * 日期：   2014年8月14日下午5:40:50
 * 包名：    com.hhzt.iptv.ui
 * 工程名：LVB_X
 * 文件名：BillListActivity.java
 */
package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.fragments.BillListFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class BillListActivity extends BaseActivity {

	private BillListFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragment = new BillListFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, 0);
	}

}

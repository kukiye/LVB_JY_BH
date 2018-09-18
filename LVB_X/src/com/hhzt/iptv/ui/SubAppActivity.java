package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.fragments.AppSubFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class SubAppActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AppSubFragment subFragment = new AppSubFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, subFragment, 0);
	}
}
package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.fragments.PrisonNewsFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class PrisonNewsActivity extends BaseActivity {
	private PrisonNewsFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragment = new PrisonNewsFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, 0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

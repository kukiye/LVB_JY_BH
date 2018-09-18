package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.fragments.PrisonAffairsLoginFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class PrisonAffairsLoginActivity extends BaseActivity{
	
	private PrisonAffairsLoginFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragment = new PrisonAffairsLoginFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, 0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}

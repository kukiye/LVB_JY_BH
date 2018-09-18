package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.fragments.PairedActionTipsFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class PairedActionTipsActivity extends BaseActivity {

	private PairedActionTipsFragment mPairedTipsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mPairedTipsFragment = new PairedActionTipsFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mPairedTipsFragment, 0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

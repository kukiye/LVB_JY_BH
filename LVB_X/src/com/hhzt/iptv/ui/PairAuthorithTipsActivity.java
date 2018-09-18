package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.fragments.PairAuthorithTipsfragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class PairAuthorithTipsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PairAuthorithTipsfragment fragment = new PairAuthorithTipsfragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, fragment, 0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

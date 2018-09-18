package com.hhzt.iptv.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.fragments.SeatSelectionFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class SeatSelectionTipsActivity extends BaseActivity {

	private SeatSelectionFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFragment = new SeatSelectionFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, mFragment, 0);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return mFragment.onKeyUp(keyCode, event) ? true : super.onKeyUp(keyCode, event);
	}

}

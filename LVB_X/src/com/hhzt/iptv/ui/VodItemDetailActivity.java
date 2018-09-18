package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.fragments.VodDetailItemFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class VodItemDetailActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		VodDetailItemFragment itemFragment = new VodDetailItemFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, itemFragment, Constant.HOTEL_VOD_SUB_SCREEN);
	}
}

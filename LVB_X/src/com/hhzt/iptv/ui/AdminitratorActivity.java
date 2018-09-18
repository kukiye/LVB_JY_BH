package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.fragments.AdminitratorFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class AdminitratorActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AdminitratorFragment fragment = new AdminitratorFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, fragment, Constant.SETTING_ADMIN_SCREEN);
	}
}

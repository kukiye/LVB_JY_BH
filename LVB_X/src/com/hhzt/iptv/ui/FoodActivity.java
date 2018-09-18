package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.fragments.AboutFragment;
import com.hhzt.iptv.lvb_x.fragments.FoodFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

/**
 * 菜谱
 */
@ContentView(R.layout.activity_base)
public class FoodActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FoodFragment fragment = new FoodFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, fragment, Constant.SETTING_ABOUT_SCREEN);
	}
}

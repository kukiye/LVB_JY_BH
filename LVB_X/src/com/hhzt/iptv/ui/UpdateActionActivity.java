package com.hhzt.iptv.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.fragments.UpdateActionFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class UpdateActionActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		UpdateActionFragment fragment = new UpdateActionFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, fragment, 0);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return true;
	}

}

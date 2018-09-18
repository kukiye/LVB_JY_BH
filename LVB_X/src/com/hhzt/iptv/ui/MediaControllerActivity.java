package com.hhzt.iptv.ui;

import android.os.Bundle;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.fragments.MediaControllerFragment;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_base)
public class MediaControllerActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MediaControllerFragment fragment = new MediaControllerFragment();
		FragmentMgr.replace(this, false, R.id.fragment_container, fragment, 0);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}

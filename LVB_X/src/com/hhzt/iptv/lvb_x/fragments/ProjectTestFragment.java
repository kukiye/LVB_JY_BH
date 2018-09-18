package com.hhzt.iptv.lvb_x.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.adapter.ProjectTestAdapter;
import com.hhzt.iptv.lvb_x.commonui.IpTestDialog;
import com.hhzt.iptv.lvb_x.interfaces.IOnIpSuccessdCB;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class ProjectTestFragment extends BaseFragment {

	@ViewInject(R.id.setting_list_content)
	private ListView mProjectContentListView;
	private ProjectTestAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_project_test, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			setValues();
		}
	}

	private void setValues() {
		mAdapter = new ProjectTestAdapter(getActivity(), getProjectTestList());
		mProjectContentListView.setAdapter(mAdapter);
		mProjectContentListView.requestLayout();
		mProjectContentListView.requestFocus();
	}

	private String[] getProjectTestList() {
		String[] listName = null;
		listName = new String[] { LVBXApp.getApp().getString(R.string.engineering_model), LVBXApp.getApp().getString(R.string.external_test),
				LVBXApp.getApp().getString(R.string.server_test), LVBXApp.getApp().getString(R.string.customer_net_test),
				LVBXApp.getApp().getString(R.string.background_mgr) };
		return listName;
	}

	@OnItemClick(R.id.setting_list_content)
	public void itemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 0:
			ActivitySwitchMgr.gotoFactoryModeActivity(getActivity(), "", getString(R.string.engineering_model));
			break;
		case 1:
			ActivitySwitchMgr.gotoNetTestActivity(getActivity(), Constant.EXTERNAL_NET_TEST, "");
			break;
		case 2:
			String serverIp = SystemMgr.getBaseHostIp();
			if (StringUtil.isEmpty(serverIp)) {
				new IpTestDialog(getActivity(), R.style.lvbEdittextDialogTheme, false, new IOnIpSuccessdCB() {

					@Override
					public void ipSuccessd(String addr) {
						ActivitySwitchMgr.gotoNetTestActivity(getActivity(), Constant.SERVER_NET_TEST, addr);
					}
				}, true);
			} else {
				if (serverIp.contains(":")) {
					int index = serverIp.indexOf(":");
					String ip = serverIp.substring(0, index);
					if (!StringUtil.isEmpty(ip)) {
						ActivitySwitchMgr.gotoNetTestActivity(getActivity(), Constant.SERVER_NET_TEST, ip);
					} else {
						BaseActivity.getInstance().showToast(getString(R.string.ip_error), Toast.LENGTH_SHORT);
					}
				} else {
					ActivitySwitchMgr.gotoNetTestActivity(getActivity(), Constant.SERVER_NET_TEST, serverIp);
				}
			}
			break;
		case 3:
			new IpTestDialog(getActivity(), R.style.lvbEdittextDialogTheme, false, new IOnIpSuccessdCB() {

				@Override
				public void ipSuccessd(String addr) {
					ActivitySwitchMgr.gotoNetTestActivity(getActivity(), Constant.CUSTOMER_NET_TEST, addr);
				}
			}, true).show();
			break;
		case 4:
			new IpTestDialog(getActivity(), R.style.lvbEdittextDialogTheme, false, new IOnIpSuccessdCB() {

				@Override
				public void ipSuccessd(String addr) {
					ActivitySwitchMgr.gotoBackMgrActivity(getActivity(), addr);
				}
			}, false).show();
			break;
		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}

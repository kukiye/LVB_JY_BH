/**
 * 作者：   Johnson
 * 日期：   2014年6月23日下午7:30:17
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：SplashFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.business.Loginer;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.interfaces.INetworkSuccessCB;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.mgr.TokenMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.utils.ApkUtil;
import com.hhzt.iptv.ui.WelcomeActivity;

public class SplashFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_splash, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		
		Loginer.getLoginer().testNetworkState(getActivity(), new INetworkSuccessCB() {

			@Override
			public void networkSuccessCB(boolean flag) {
				if (flag) {						
						toLoginSystem();								
					} else {
					
				}
			}
		});
	}
	
	// 登陆系统动作
	private void toLoginSystem() {
		switch (Config.LvbDeviceType) {
		case Constant.DEVICE_TYPE_MOBILE:
		case Constant.DEVICE_TYPE_MOBILE_HSJQ:
			if (UserMgr.getPairedTag()) {
				// 已经配对后，直接进入LVB_X系统
				Loginer.getLoginer().enterLVBXSystem(getActivity());
				
			} else {
				// 弹出配对界面进行配对
				ActivitySwitchMgr.gotoPairTipsActivity(getActivity(), getString(R.string.interactive_seeting));
				getActivity().finish();
			}
			break;
		default:
			Bundle bundle = getArguments();
			int screenTag = bundle.getInt(Constant.IPTV_SYS_FRAGMENT_TAG);
			String userName = UserMgr.getUserName();
			String userPasswd = UserMgr.getAcountPasswd();
			String serverIp = UserMgr.getServerIp();
			int languageFlag = SystemMgr.getWantedLangType();
			Loginer.getLoginer().loginAction(languageFlag, screenTag, userName, userPasswd, serverIp, TokenMgr.getTokenId(), getActivity(),
					WelcomeActivity.class);
			break;
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			ActivitySwitchMgr.gotoSettingActivity(getActivity());
			return true;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
//	public void ToService(){
//		if(LVBXApp.getServiceFlag()){
//			LVBXApp.setServiceFlag(false);
//			BaseActivity.getInstance().openMsgLHReceiveService();
//		}
//		
//	}
}

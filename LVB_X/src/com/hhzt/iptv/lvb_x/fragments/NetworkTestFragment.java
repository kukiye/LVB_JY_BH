package com.hhzt.iptv.lvb_x.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

@SuppressLint("HandlerLeak")
public class NetworkTestFragment extends BaseFragment {
	@ViewInject(R.id.test_name)
	private TextView mTestName;
	@ViewInject(R.id.test_output)
	private TextView mTestOutput;
	@ViewInject(R.id.test_tag)
	private TextView mPingTag;
	private Process mProcess;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.IPTV_MSG_PING_CONTENT_TAG:
				String text = (String) msg.obj;
				String content = mTestOutput.getText().toString() + "\r\n" + text;
				mTestOutput.setText(content);
				break;
			case Constant.IPTV_MSG_PING_OK_TAG:
				String pingTag = (String) msg.obj;
				mPingTag.setText(pingTag);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_network_test, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			setOutput();
		}
	}

	private void newThreadPing(final String ipAdd) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String pingTag = ping(ipAdd);
				Message msg = Message.obtain();
				msg.obj = pingTag;
				msg.what = Constant.IPTV_MSG_PING_OK_TAG;
				mHandler.sendMessage(msg);
			}
		}).start();
	}

	private void setOutput() {
		String testName = getActivity().getIntent().getStringExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG);
		if (Constant.EXTERNAL_NET_TEST.equalsIgnoreCase(testName)) {
			mTestName.setText(getString(R.string.external_test) + "---" + Constant.EXTERNAL_NET_ADDRESS);
			newThreadPing(Constant.EXTERNAL_NET_ADDRESS);
		} else if (Constant.CUSTOMER_NET_TEST.equalsIgnoreCase(testName)) {
			String ip = getActivity().getIntent().getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			mTestName.setText(getString(R.string.customer_net_test) + "---" + ip);
			if (!StringUtil.isEmpty(ip)) {
				newThreadPing(ip);
			}
		} else if (Constant.SERVER_NET_TEST.equalsIgnoreCase(testName)) {
			String serverIp = getActivity().getIntent().getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			mTestName.setText(getString(R.string.server_test) + "---" + serverIp);
			newThreadPing(serverIp);
		}
	}

	public String ping(String str) {
		String tag = "";
		try {
			// ping -c 3 -w 100 中 ，-c 是指ping的次数 3是指ping 3次 ，-w 100
			// 以秒为单位指定超时间隔，是指超时时间为100秒
			mProcess = Runtime.getRuntime().exec("ping -c 5 " + str);
			int status = mProcess.waitFor();
			InputStream input = mProcess.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = "";
			while ((line = in.readLine()) != null) {
				Message msg = Message.obtain();
				msg.what = Constant.IPTV_MSG_PING_CONTENT_TAG;
				msg.obj = line;
				mHandler.sendMessage(msg);
			}
			if (status == 0) {
				tag = "success";
			} else {
				tag = "faild";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return tag;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			mHandler.removeMessages(Constant.IPTV_MSG_PING_CONTENT_TAG);
			mHandler.removeMessages(Constant.IPTV_MSG_PING_OK_TAG);
			if (null != mProcess) {
				mProcess.destroy();
			}
			getActivity().finish();
			break;
		default:
			break;
		}
		return super.onKeyUp(keyCode, event);
	}
}

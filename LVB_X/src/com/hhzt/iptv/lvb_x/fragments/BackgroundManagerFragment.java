package com.hhzt.iptv.lvb_x.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class BackgroundManagerFragment extends BaseFragment implements OnClickListener {

	@ViewInject(R.id.mgr_webView)
	private WebView mMgrWebView;
	@ViewInject(R.id.ip_input_edit)
	private EditText mIpInputText;
	@ViewInject(R.id.input_ok_btn)
	private Button mIntoPageBtn;
	@ViewInject(R.id.page_loading_progressBar)
	private ProgressBar mLoadPageBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_back_mgr, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			String ipAddr = getActivity().getIntent().getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			if (!StringUtil.isEmpty(ipAddr)) {
				String mgrUrl = UrlMgr.getBackMgrUrl(ipAddr);
				mIpInputText.setText(mgrUrl);
				mIntoPageBtn.setOnClickListener(this);
				initWebView();
				openMgrPage(mgrUrl);
			}
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView() {
		WebSettings settings = mMgrWebView.getSettings();
		settings.setSupportZoom(true); // 支持缩放
		settings.setBuiltInZoomControls(true); // 启用内置缩放装置
		settings.setJavaScriptEnabled(true); // 支持JS

		mMgrWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				BaseActivity.getInstance().showToast(getString(R.string.background_mgr_faild) + errorCode, Toast.LENGTH_LONG);
			}
		});

		mMgrWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				mLoadPageBar.setProgress(newProgress);
			}
		});
	}

	private void openMgrPage(String url) {
		mLoadPageBar.setProgress(0);
		mMgrWebView.loadUrl(url);
		mMgrWebView.requestLayout();
		mMgrWebView.requestFocus();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.input_ok_btn:
			openMgrPage(mIpInputText.getText().toString());
			break;
		default:
			break;
		}
	}
}

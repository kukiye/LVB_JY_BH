/**
 * 作者：   Johnson
 * 日期：   2014年6月18日下午4:43:16
 * 包名：    com.hhzt.iptv.lvb_x.net
 * 工程名：LVB_X
 * 文件名：LVBHttpUtils.java
 */
package com.hhzt.iptv.lvb_x.net;

import java.io.File;

import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.interfaces.IResponseFileable;
import com.hhzt.iptv.lvb_x.interfaces.IResponseable;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.utils.CommonUtil;
import com.hhzt.iptv.lvb_x.utils.DeviceUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class LVBHttpUtils {
	public static void get(String url, final IResponseable iResponseable) {
		if (StringUtil.isEmpty(url)) {
			BaseActivity.getInstance().showToast(LVBXApp.getApp().getString(R.string.url_is_null), Toast.LENGTH_LONG);
			return;
		}

		if (CommonUtil.isNetworkAvailable(LVBXApp.getApp())) {
			String lang = DeviceUtil.getCustomSystemLang();
			String doActionUrl = url.indexOf("?") == -1 ? url + "?locale=" + lang : url + "&locale=" + lang;
			LogUtil.d("Request begin url=" + doActionUrl + "\n");
			HttpUtils http = new HttpUtils();
			http.configCurrentHttpCacheExpiry(0);
			http.send(HttpMethod.GET, doActionUrl, new RequestCallBack<String>() {

				@Override
				public void onStart() {
					super.onStart();
				}

				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					super.onLoading(total, current, isUploading);
					LogUtil.d("LVB_X httpRequest doing onLoading total=" + total + "current=" + current + "\n");
				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					LogUtil.d("LVB_X httpRequest end onSuccess responseInfo.result=" + responseInfo.result + "\n");
					if (StringUtil.isEmpty(responseInfo.result)) {
						BaseActivity.getInstance().showToast(LVBXApp.getApp().getString(R.string.request_url_error), Toast.LENGTH_LONG);
					} else {
						iResponseable.onSuccess(responseInfo.result);
					}
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					LogUtil.d("LVB_X httpRequest end onFailure msg=" + msg + "\n");
					// String failedContents =
					// String.format(LVBXApp.getApp().getString(R.string.request_net_failed),
					// msg);
					// iResponseable.defaultResultTips(failedContents);
					iResponseable.onFailed(msg);
				}
			});
		} else {
			iResponseable.onFailed(LVBXApp.getApp().getString(R.string.net_is_unusable_tips));
		}
	}

	public static void post(String url, final RequestParams params, final IResponseable iResponseable) {
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(0);
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				if (responseInfo.statusCode == 200) {
					iResponseable.onSuccess(responseInfo.result);
				} else {

				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				iResponseable.onFailed(msg);
			}
		});
	}

	public static void download(String srcUrl, String desPath, boolean isBreakPoint, boolean rename, final IResponseFileable iResponseable) {
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(5 * 1000);
		http.download(srcUrl, desPath, isBreakPoint, rename, new RequestCallBack<File>() {

			@Override
			public void onSuccess(ResponseInfo<File> responseInfo) {
				iResponseable.onSuccess(responseInfo.result);
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);
				iResponseable.onLoading(total, current, isUploading);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				iResponseable.onFailed(msg);
			}
		});
	}
}

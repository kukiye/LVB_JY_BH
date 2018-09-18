/**
 * 作者：   Johnson
 * 日期：   2014年6月18日下午4:37:37
 * 包名：    com.hhzt.iptv.lvb_x.interfaces
 * 工程名：LVB_X
 * 文件名：IResponseable.java
 */
package com.hhzt.iptv.lvb_x.interfaces;

import android.widget.Toast;

import com.hhzt.iptv.lvb_x.BaseActivity;

public abstract class IResponseable {
	public abstract void onSuccess(String result);

	public abstract void onFailed(String result);

	public void defaultResultTips(String content) {
		BaseActivity.getInstance().showToast(content, Toast.LENGTH_LONG);
	}

	public void onCancel() {
	}
}

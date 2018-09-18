package com.hhzt.iptv.lvb_x.interfaces;

import java.io.File;

public interface IResponseFileable {

	void onSuccess(File file);

	void onFailed(String msg);

	void onLoading(long total, long current, boolean isDownLoading);

}

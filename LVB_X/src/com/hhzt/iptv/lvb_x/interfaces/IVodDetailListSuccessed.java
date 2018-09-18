package com.hhzt.iptv.lvb_x.interfaces;

import com.hhzt.iptv.lvb_x.model.VodDetailDataModel;

public interface IVodDetailListSuccessed {

	void onDetailListSuccess(VodDetailDataModel models);

	void onFail(String result);
}

package com.hhzt.iptv.lvb_x.interfaces;

import java.util.ArrayList;

import com.hhzt.iptv.lvb_x.model.VodItemPlayMessageModel;

public interface IOnVodSubSingleDataSuccess {
	public abstract void onSuccess(ArrayList<VodItemPlayMessageModel> models);
}

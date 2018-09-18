package com.hhzt.iptv.lvb_x.interfaces;

import java.util.List;

public interface IListOnSuccessCB <T> {
	public abstract void onSuccess(List<T> datas);
}

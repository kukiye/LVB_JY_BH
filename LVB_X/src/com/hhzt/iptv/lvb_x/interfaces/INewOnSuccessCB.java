/**
 * 作者：   Johnson
 * 日期：   2014年7月15日下午5:59:05
 * 包名：    com.hhzt.iptv.lvb_x.interfaces
 * 工程名：LVB_X
 * 文件名：INewOnSuccessCB.java
 */
package com.hhzt.iptv.lvb_x.interfaces;

import com.hhzt.iptv.lvb_x.model.NewsDataItems;

public interface INewOnSuccessCB {
	public abstract void onSuccess(NewsDataItems newsDatas);
}

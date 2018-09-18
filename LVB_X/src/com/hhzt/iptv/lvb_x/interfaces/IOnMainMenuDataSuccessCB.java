/**
 * 作者：   Johnson
 * 日期：   2014年7月17日下午3:03:48
 * 包名：    com.hhzt.iptv.lvb_x.interfaces
 * 工程名：LVB_X
 * 文件名：IOnSrcoViewDataSuccessCB.java
 */
package com.hhzt.iptv.lvb_x.interfaces;

import java.util.ArrayList;

import com.hhzt.iptv.lvb_x.model.MainmenuModel;

public interface IOnMainMenuDataSuccessCB {
	public abstract void onDataSuccessCB(ArrayList<MainmenuModel> items);
}

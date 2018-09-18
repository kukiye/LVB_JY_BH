/**
 * 作者：   Johnson
 * 日期：   2014年7月1日下午2:25:06
 * 包名：    com.hhzt.iptv.lvb_x.interfaces
 * 工程名：LVB_X
 * 文件名：ILiveMainListSuccessCB.java
 */
package com.hhzt.iptv.lvb_x.interfaces;

import java.util.ArrayList;

import com.hhzt.iptv.lvb_x.model.LiveSubModel;
public interface ILiveSubListSuccessCB {
	public abstract void setListDatas(ArrayList<LiveSubModel> listModels);
}

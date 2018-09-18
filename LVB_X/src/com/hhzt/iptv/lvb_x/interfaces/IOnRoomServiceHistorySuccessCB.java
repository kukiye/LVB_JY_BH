/**
 * 作者：   Johnson
 * 日期：   2014年7月9日下午4:59:24
 * 包名：    com.hhzt.iptv.lvb_x.interfaces
 * 工程名：LVB_X
 * 文件名：IOnRoomServiceHistoryOnclickCB.java
 */
package com.hhzt.iptv.lvb_x.interfaces;

import java.util.ArrayList;

import com.hhzt.iptv.lvb_x.model.RoomServiceModel;

public interface IOnRoomServiceHistorySuccessCB {
	public abstract void onSuccess(ArrayList<RoomServiceModel> roomServiceModels);
}

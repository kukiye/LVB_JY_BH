/**
 * 作者：   Johnson
 * 日期：   2014年7月14日上午11:27:23
 * 包名：    com.hhzt.iptv.lvb_x.interfaces
 * 工程名：LVB_X
 * 文件名：ISubMenuModelable.java
 */
package com.hhzt.iptv.lvb_x.interfaces;

import java.util.ArrayList;

import com.hhzt.iptv.lvb_x.model.SubmenuModel;

public interface IOnSubMenuModelable {
	public abstract void setValue(ArrayList<SubmenuModel> modelList);
}

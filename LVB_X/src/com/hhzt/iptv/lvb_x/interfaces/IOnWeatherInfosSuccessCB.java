/**
 * 作者：   Johnson
 * 日期：   2014年7月12日下午12:16:21
 * 包名：    com.hhzt.iptv.lvb_x.interfaces
 * 工程名：LVB_X
 * 文件名：IOnWeatherInfosSuccessCB.java
 */
package com.hhzt.iptv.lvb_x.interfaces;

import java.util.ArrayList;

import com.hhzt.iptv.lvb_x.model.WeatherInfoModel;

public interface IOnWeatherInfosSuccessCB {
	public abstract void success(ArrayList<WeatherInfoModel> models);
}

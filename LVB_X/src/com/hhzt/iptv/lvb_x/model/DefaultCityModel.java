/**
 * 作者：   Johnson
 * 日期：   2014年7月10日下午5:15:09
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：DefaultCityModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class DefaultCityModel {
	private int id;
	private String cityname;
	private String cityaircode;
	private String cityweathercode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getCityaircode() {
		return cityaircode;
	}

	public void setCityaircode(String cityaircode) {
		this.cityaircode = cityaircode;
	}

	public String getCityweathercode() {
		return cityweathercode;
	}

	public void setCityweathercode(String cityweathercode) {
		this.cityweathercode = cityweathercode;
	}

	@Override
	public String toString() {
		return "DefaultCityModel [id=" + id + ", cityname=" + cityname + ", cityaircode=" + cityaircode + ", cityweathercode=" + cityweathercode
				+ "]";
	}

}

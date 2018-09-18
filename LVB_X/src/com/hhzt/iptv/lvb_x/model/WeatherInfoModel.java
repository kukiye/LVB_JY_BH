/**
 * 作者：   Johnson
 * 日期：   2014年6月23日上午11:58:40
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：WeatherInfoModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class WeatherInfoModel {
	private int id;
	private String weather;
	private String temp;
	private String wd;
	private String ws;
	private String cityname;
	private String citycode;
	private String currenttemp;
	private String weatherdate;
	private String logoImageUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getWs() {
		return ws;
	}

	public void setWs(String ws) {
		this.ws = ws;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getCurrenttemp() {
		return currenttemp;
	}

	public void setCurrenttemp(String currenttemp) {
		this.currenttemp = currenttemp;
	}

	public String getWeatherdate() {
		return weatherdate;
	}

	public void setWeatherdate(String weatherdate) {
		this.weatherdate = weatherdate;
	}

	public String getLogoImageUrl() {
		return logoImageUrl;
	}

	public void setLogoImageUrl(String logoImageUrl) {
		this.logoImageUrl = logoImageUrl;
	}

	@Override
	public String toString() {
		return "WeatherInfoModel [id=" + id + ", weather=" + weather + ", temp=" + temp + ", wd=" + wd + ", ws=" + ws + ", cityname=" + cityname
				+ ", citycode=" + citycode + ", currenttemp=" + currenttemp + ", weatherdate=" + weatherdate + ", logoImageUrl=" + logoImageUrl + "]";
	}

}

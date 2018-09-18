/**
 * 作者：   Johnson
 * 日期：   2014年6月20日上午11:39:05
 * 包名：    com.hhzt.iptv.lvb_x.modle
 * 工程名：LVB_X
 * 文件名：WelcomeInfoModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class WelcomeInfoModel {
	private int id;
	private String hotelname;
	private String hotellogo;
	private String hotelimage;
	private String checkinid;
	private String customername;
	private String sex;
	private String checkintime;
	private String checkouttime;
	private String welcome;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHotelname() {
		return hotelname;
	}

	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}

	public String getHotellogo() {
		return hotellogo;
	}

	public void setHotellogo(String hotellogo) {
		this.hotellogo = hotellogo;
	}

	public String getHotelimage() {
		return hotelimage;
	}

	public void setHotelimage(String hotelimage) {
		this.hotelimage = hotelimage;
	}

	public String getCheckinid() {
		return checkinid;
	}

	public void setCheckinid(String checkinid) {
		this.checkinid = checkinid;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCheckintime() {
		return checkintime;
	}

	public void setCheckintime(String checkintime) {
		this.checkintime = checkintime;
	}

	public String getCheckouttime() {
		return checkouttime;
	}

	public void setCheckouttime(String checkouttime) {
		this.checkouttime = checkouttime;
	}

	public String getWelcome() {
		return welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	@Override
	public String toString() {
		return "WelcomeInfoModel [id=" + id + ", hotelname=" + hotelname + ", hotellogo=" + hotellogo + ", hotelimage=" + hotelimage + ", checkinid="
				+ checkinid + ", customername=" + customername + ", sex=" + sex + ", checkintime=" + checkintime + ", checkouttime=" + checkouttime
				+ ", welcome=" + welcome + "]";
	}

}

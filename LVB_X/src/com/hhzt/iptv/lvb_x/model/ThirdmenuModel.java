/**
 * 作者：   Johnson
 * 日期：   2014年6月25日下午5:04:38
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：ThirdmenuModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class ThirdmenuModel {
	private int id;
	private String text1;
	private String text2;
	private String text3;
	private String text4;
	private String text5;
	private String areatext;
	private String videourl;
	private String smallimage;
	private String largeimage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	public String getText4() {
		return text4;
	}

	public void setText4(String text4) {
		this.text4 = text4;
	}

	public String getText5() {
		return text5;
	}

	public void setText5(String text5) {
		this.text5 = text5;
	}

	public String getAreatext() {
		return areatext;
	}

	public void setAreatext(String areatext) {
		this.areatext = areatext;
	}

	public String getVideourl() {
		return videourl;
	}

	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}

	public String getSmallimage() {
		return smallimage;
	}

	public void setSmallimage(String smallimage) {
		this.smallimage = smallimage;
	}

	public String getLargeimage() {
		return largeimage;
	}

	public void setLargeimage(String largeimage) {
		this.largeimage = largeimage;
	}

	@Override
	public String toString() {
		return "ThirdmenuModel [id=" + id + ", text1=" + text1 + ", text2=" + text2 + ", text3=" + text3 + ", text4=" + text4 + ", text5=" + text5
				+ ", areatext=" + areatext + ", videourl=" + videourl + ", smallimage=" + smallimage + ", largeimage=" + largeimage + "]";
	}

}

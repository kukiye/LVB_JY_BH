/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 *
 * @author Johnson
 * 2014年3月18日 下午6:05:32
 */
package com.hhzt.iptv.lvb_x.model;

public class NewsSingleItem {
	private int id;
	private int important; // 紧急级别 （2重要 1紧急  在任何界面都显示） （0 一般  只在首页 显示）
	private String releasetime;
	private int loopnumber;
	private String loopintervaltime;
	private String content;
	private String readstatus;
	private String type;
	private int font;
	
//	content:  内容
//	important:  紧急级别 （2重要 1紧急  在任何界面都显示） （0 一般  只在首页 显示）
//	loopnumber:  公告滚动循环次数
//	loopintervaltime: 滚动时间间隔
//	font: //字体大小+
	//"cronexpression":null,"epggroupid":0,"color":"#FFFFFF"

	public NewsSingleItem() {

	}

	public NewsSingleItem(int id, int important, String releasetime, int loopnumber, String loopintervaltime, String content,
			String readstatus, String type, int font) {
		super();
		this.id = id;
		this.important = important;
		this.releasetime = releasetime;
		this.loopnumber = loopnumber;
		this.loopintervaltime = loopintervaltime;
		this.content = content;
		this.readstatus = readstatus;
		this.type = type;
		this.font = font;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getImportant() {
		return important;
	}

	public void setImportant(int important) {
		this.important = important;
	}

	public String getReleasetime() {
		return releasetime;
	}

	public void setReleasetime(String releasetime) {
		this.releasetime = releasetime;
	}

	public int getLoopnumber() {
		return loopnumber;
	}

	public void setLoopnumber(int loopnumber) {
		this.loopnumber = loopnumber;
	}

	public String getLoopintervaltime() {
		return loopintervaltime;
	}

	public void setLoopintervaltime(String loopintervaltime) {
		this.loopintervaltime = loopintervaltime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReadstatus() {
		return readstatus;
	}

	public void setReadstatus(String readstatus) {
		this.readstatus = readstatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getFont() {
		return font;
	}
	
	public void setFont(int font) {
		this.font = font;
	}

	@Override
	public String toString() {
		return "NewsSingleItem [id=" + id + ", important=" + important + ", releasetime="
				+ releasetime + ", loopnumber=" + loopnumber + ", loopintervaltime="
				+ loopintervaltime + ", content=" + content + ", readstatus=" + readstatus
				+ ", type=" + type + "]";
	}

	
}

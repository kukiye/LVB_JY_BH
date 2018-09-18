/**
 * 作者：   Johnson
 * 日期：   2014年7月1日下午5:16:06
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：OrderMenuModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class OrderMainMenuModel {
	private int id;
	private String categoryname;
	private int sequence;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return "OrderMainMenuModel [id=" + id + ", categoryname=" + categoryname + ", sequence=" + sequence + "]";
	}

}

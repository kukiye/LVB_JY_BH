package com.hhzt.iptv.lvb_x.model;

public class VodTypeItemModel {
	private int id;
	private String categoryname;
	private String sequence;
	private String typeimage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public String getTypeimage() {
		return typeimage;
	}

	public void setTypeimage(String typeimage) {
		this.typeimage = typeimage;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return "VodTypeItemModel[id=" + id + " categoryname=" + categoryname + " sequence=" + sequence + "]";
	}
}

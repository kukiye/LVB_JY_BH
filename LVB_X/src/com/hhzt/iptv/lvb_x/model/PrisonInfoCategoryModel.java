package com.hhzt.iptv.lvb_x.model;

public class PrisonInfoCategoryModel {

	private int id;
	private String categoryName;
	private String description;
	private String orderBy;
	private int parentId;
	private String img;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "PrisonInfoCategoryModel [id=" + id + ", categoryName=" + categoryName
				+ ", description=" + description + ", orderBy=" + orderBy + ", parentId="
				+ parentId + "]";
	}
	
	
}

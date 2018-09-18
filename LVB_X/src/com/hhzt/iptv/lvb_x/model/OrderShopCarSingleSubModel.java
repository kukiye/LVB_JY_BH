/**
 * 作者：   Johnson
 * 日期：   2014年7月1日下午5:35:02
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：OrderShopCarSingleModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class OrderShopCarSingleSubModel {
	private int id;
	private String name;
	private String imageurl;
	private int price;
	private String saletype;
	private String cuisine;
	private String description;
	private String recommend;
	private int number;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getSaletype() {
		return saletype;
	}

	public void setSaletype(String saletype) {
		this.saletype = saletype;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "OrderShopCarSingleModel [id=" + id + ", name=" + name + ", imageurl=" + imageurl + ", price=" + price + ", saletype=" + saletype
				+ ", cuisine=" + cuisine + ", description=" + description + ", recommend=" + recommend + ", number=" + number + "]";
	}

}

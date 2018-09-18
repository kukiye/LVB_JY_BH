/**
 * 作者：   Johnson
 * 日期：   2014年7月1日下午5:46:22
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：OrderShopCarModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class OrderShopCarSingleModel {
	private int id;
	private String username;
	private OrderShopCarSingleSubModel dish;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public OrderShopCarSingleSubModel getDish() {
		return dish;
	}

	public void setDish(OrderShopCarSingleSubModel dish) {
		this.dish = dish;
	}

	@Override
	public String toString() {
		return "OrderShopCarSingleModel [id=" + id + ", username=" + username + ", dish=" + dish + "]";
	}

}

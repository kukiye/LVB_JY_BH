/**
 * 作者：   Johnson
 * 日期：   2014年7月4日下午4:55:54
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：OrderHistoryMainModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class OrderHistoryMainModel {
	private int id;
	private String roomname;
	private String username;
	private String ordercode;
	private int totalprice;
	private int totalnumber;
	private int status;
	private String creationtime;
	private String completetime;
	private String tel;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

	public int getTotalnumber() {
		return totalnumber;
	}

	public void setTotalnumber(int totalnumber) {
		this.totalnumber = totalnumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(String creationtime) {
		this.creationtime = creationtime;
	}

	public String getCompletetime() {
		return completetime;
	}

	public void setCompletetime(String completetime) {
		this.completetime = completetime;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "OrderHistoryMainModel [id=" + id + ", roomname=" + roomname + ", username=" + username + ", ordercode=" + ordercode + ", totalprice="
				+ totalprice + ", totalnumber=" + totalnumber + ", status=" + status + ", creationtime=" + creationtime + ", completetime="
				+ completetime + ", tel=" + tel + "]";
	}

}

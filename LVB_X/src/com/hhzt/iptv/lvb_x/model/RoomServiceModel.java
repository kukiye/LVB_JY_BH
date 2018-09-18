/**
 * 作者：   Johnson
 * 日期：   2014年7月9日下午4:33:01
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：CheckInOutModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class RoomServiceModel {
	private int id;
	private String roomname;
	private String iptvusername;
	private int reqtype;
	private int reqstatus;
	private long appointmenttime;
	private long reqtime;
	private long rsptime;
	private long comptime;

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

	public String getIptvusername() {
		return iptvusername;
	}

	public void setIptvusername(String iptvusername) {
		this.iptvusername = iptvusername;
	}

	public int getReqtype() {
		return reqtype;
	}

	public void setReqtype(int reqtype) {
		this.reqtype = reqtype;
	}

	public int getReqstatus() {
		return reqstatus;
	}

	public void setReqstatus(int reqstatus) {
		this.reqstatus = reqstatus;
	}

	public long getAppointmenttime() {
		return appointmenttime;
	}

	public void setAppointmenttime(long appointmenttime) {
		this.appointmenttime = appointmenttime;
	}

	public long getReqtime() {
		return reqtime;
	}

	public void setReqtime(long reqtime) {
		this.reqtime = reqtime;
	}

	public long getRsptime() {
		return rsptime;
	}

	public void setRsptime(long rsptime) {
		this.rsptime = rsptime;
	}

	public long getComptime() {
		return comptime;
	}

	public void setComptime(long comptime) {
		this.comptime = comptime;
	}

	@Override
	public String toString() {
		return "CheckInOutModel [id=" + id + ", roomname=" + roomname + ", iptvusername=" + iptvusername + ", reqtype=" + reqtype + ", reqstatus="
				+ reqstatus + ", appointmenttime=" + appointmenttime + ", reqtime=" + reqtime + ", rsptime=" + rsptime + ", comptime=" + comptime
				+ "]";
	}

}

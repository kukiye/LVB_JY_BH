/**
 * 作者：   Johnson
 * 日期：   2014年6月30日下午6:53:30
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：LiveSubModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class LiveSubModel {
	private int id;
	private int channelid;
	private String schedulename;
	private String scheduledate;
	private String starttime;
	private String endtime;
	private String hotflag;
	private String deleteflag;
	private String createdate;
	private String description;
	private String tvodurl;
	private String playstatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChannelid() {
		return channelid;
	}

	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}

	public String getSchedulename() {
		return schedulename;
	}

	public void setSchedulename(String schedulename) {
		this.schedulename = schedulename;
	}

	public String getScheduledate() {
		return scheduledate;
	}

	public void setScheduledate(String scheduledate) {
		this.scheduledate = scheduledate;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getHotflag() {
		return hotflag;
	}

	public void setHotflag(String hotflag) {
		this.hotflag = hotflag;
	}

	public String getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTvodurl() {
		return tvodurl;
	}

	public void setTvodurl(String tvodurl) {
		this.tvodurl = tvodurl;
	}

	public String getPlaystatus() {
		return playstatus;
	}

	public void setPlaystatus(String playstatus) {
		this.playstatus = playstatus;
	}

	@Override
	public String toString() {
		return "LiveSubModel [id=" + id + ", channelid=" + channelid + ", schedulename=" + schedulename + ", scheduledate=" + scheduledate
				+ ", starttime=" + starttime + ", endtime=" + endtime + ", hotflag=" + hotflag + ", deleteflag=" + deleteflag + ", createdate="
				+ createdate + ", description=" + description + ", tvodurl=" + tvodurl + ", playstatus=" + playstatus + "]";
	}

}

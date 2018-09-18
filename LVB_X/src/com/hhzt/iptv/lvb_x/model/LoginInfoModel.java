/**
 * 作者：   Johnson
 * 日期：   2014年6月23日下午3:12:56
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：LoginInfoModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class LoginInfoModel {
	private int id;
	private String iptvusername;
	private String channelgroup;
	private String status;
	private String ipaddress;
	private String interacpassword;
	private String epggroupid;
	private String code;
	private String isencrypted;
	private String roomname;
	private String roomno;
	private String message;
	private String hotspot;
	private int level;
	private String vodfree;
	private String wifiname;
	private boolean ssidhide;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIptvusername() {
		return iptvusername;
	}

	public void setIptvusername(String iptvusername) {
		this.iptvusername = iptvusername;
	}

	public String getChannelgroup() {
		return channelgroup;
	}

	public void setChannelgroup(String channelgroup) {
		this.channelgroup = channelgroup;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getInteracpassword() {
		return interacpassword;
	}

	public void setInteracpassword(String interacpassword) {
		this.interacpassword = interacpassword;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsencrypted() {
		return isencrypted;
	}

	public void setIsencrypted(String isencrypted) {
		this.isencrypted = isencrypted;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getRoomno() {
		return roomno;
	}

	public void setRoomno(String roomno) {
		this.roomno = roomno;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHotspot() {
		return hotspot;
	}

	public void setHotspot(String hotspot) {
		this.hotspot = hotspot;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getVodfree() {
		return vodfree;
	}

	public void setVodfree(String vodfree) {
		this.vodfree = vodfree;
	}

	public String getWifiname() {
		return wifiname;
	}

	public void setWifiname(String wifiname) {
		this.wifiname = wifiname;
	}

	public boolean getSSIDhide() {
		return ssidhide;
	}

	public void setSSIDhide(boolean ssidhide) {
		this.ssidhide = ssidhide;
	}

	public String getEpgGroupid() {
		return epggroupid;
	}

	public void setEpgGroupid(String epggroupid) {
		this.epggroupid = epggroupid;
	}

	@Override
	public String toString() {
		return "LoginInfoModel [id=" + id + ", iptvusername=" + iptvusername + ", channelgroup=" + channelgroup + ", status=" + status
				+ ", ipaddress=" + ipaddress + ", interacpassword=" + interacpassword + ", epggroupid=" + epggroupid + ", code=" + code
				+ ", isencrypted=" + isencrypted + ", roomname=" + roomname + ", roomno=" + roomno + ", message=" + message + ", hotspot=" + hotspot
				+ ", level=" + level + ", vodfree=" + vodfree + ", wifiname=" + wifiname + ", ssidhide=" + ssidhide + "]";
	}

}

package com.hhzt.iptv.lvb_x.model;

public class UserInfoModel {
	private int id;
	private String username;
	private String nickname;

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "UserInfoModel [id=" + id + ", username=" + username
				+ ", nickname=" + nickname + "]";
	}
	
	

}

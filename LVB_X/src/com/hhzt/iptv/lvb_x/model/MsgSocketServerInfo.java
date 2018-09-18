/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 *
 * @author Johnson
 * 2014年3月20日 下午4:31:37
 */
package com.hhzt.iptv.lvb_x.model;

public class MsgSocketServerInfo {
	private String ip;
	private int port;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "MsgSocketServerInfo [ip=" + ip + ", port=" + port + "]";
	}

}

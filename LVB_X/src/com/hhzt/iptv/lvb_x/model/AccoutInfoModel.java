/**
 * 作 者：Kobe
 * 日 期：20152015年5月8日下午6:09:15
 * 包 名：com.hhzt.iptv.lvb_x.model
 * 工程名：MediaDetector
 * 文件名：AccoutInfoModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class AccoutInfoModel {

	private String iptvusername;
	private String password;
	private String ipaddr;
	private String mask;
	private String gateway;
	private String dns;
	private String desc;
	private boolean result;

	public String getIptvusername() {
		return iptvusername;
	}

	public void setIptvusername(String iptvusername) {
		this.iptvusername = iptvusername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getDns() {
		return dns;
	}

	public void setDns(String dns) {
		this.dns = dns;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}

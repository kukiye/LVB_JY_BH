/**
 * 作者：   Johnson
 * 日期：   2014年6月25日下午5:02:47
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：SubmenuModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class SubmenuModel {
	private int id;
	private String name;
	private String code;
	private int sequence;
	private String templatecode;
	private String zhtitle;
	private String entitle;
	private String enable;
	private String menubgurl;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getTemplatecode() {
		return templatecode;
	}

	public void setTemplatecode(String templatecode) {
		this.templatecode = templatecode;
	}

	public String getZhtitle() {
		return zhtitle;
	}

	public void setZhtitle(String zhtitle) {
		this.zhtitle = zhtitle;
	}

	public String getEntitle() {
		return entitle;
	}

	public void setEntitle(String entitle) {
		this.entitle = entitle;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getMenubgurl() {
		return menubgurl;
	}

	public void setMenubgurl(String menubgurl) {
		this.menubgurl = menubgurl;
	}

	@Override
	public String toString() {
		return "SubmenuModel [id=" + id + ", name=" + name + ", code=" + code + ", sequence=" + sequence + ", templatecode=" + templatecode
				+ ", zhtitle=" + zhtitle + ", entitle=" + entitle + ", enable=" + enable + ", menubgurl=" + menubgurl + "]";
	}

}

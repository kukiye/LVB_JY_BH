package com.hhzt.iptv.lvb_x.model.prisonaffairs;

import java.io.Serializable;
import java.util.Date;

/**
 * 行政奖惩
 */
public class Xzjc implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private String prisonName;
	private String name;// 姓名
	private String code;// 编号
	private String type;// 奖惩类型
	private String detail;// 奖惩内容
	private String prisonid;// 监狱Id
	private long date;// 批准日期

	public String getPrisonName() {
		return prisonName;
	}

	public void setPrisonName(String prisonName) {
		this.prisonName = prisonName;
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

	public String getType() {
		return type;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPrisonid() {
		return prisonid;
	}

	public void setPrisonid(String prisonid) {
		this.prisonid = prisonid;
	}

	@Override
	public String toString() {
		return "Xzjc [prisonName=" + prisonName + ", name=" + name + ", code="
				+ code + ", type=" + type + ", detail=" + detail
				+ ", prisonid=" + prisonid + ", date=" + date + "]";
	}

}

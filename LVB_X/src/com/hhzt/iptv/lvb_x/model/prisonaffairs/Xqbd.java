package com.hhzt.iptv.lvb_x.model.prisonaffairs;

import java.io.Serializable;
import java.util.Date;

/**
 * 刑期变动
 */
public class Xqbd implements Serializable {
	private static final long serialVersionUID = 1L;
	private String prisonName;// 监狱名称
	private String name;// 姓名
	private String code;// 编号
	private String type;// 变动类型
	private String number;// 变动幅度
	private long date;// 裁判日期
	private long startDate;// 执行刑期起日
	private String prisonTerm;// 刑期

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

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public String getPrisonTerm() {
		return prisonTerm;
	}

	public void setPrisonTerm(String prisonTerm) {
		this.prisonTerm = prisonTerm;
	}

}

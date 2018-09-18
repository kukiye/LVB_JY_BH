package com.hhzt.iptv.lvb_x.model.prisonaffairs;

import java.io.Serializable;
import java.util.Date;

/**
 * 分级处遇
 */
public class Fjcy implements Serializable {
	private static final long serialVersionUID = 1L;
	private String prisonName;// 监狱名称
	private String name;// 姓名
	private String code;// 编号

	private long rynx;// 入狱年限
	private String prisonTerm;// 刑期
	private String type;// 变动类型
	private long date;// 变动日期
	private String xcyj;// 现处遇等级

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

	public String getPrisonTerm() {
		return prisonTerm;
	}

	public void setPrisonTerm(String prisonTerm) {
		this.prisonTerm = prisonTerm;
	}

	public String getXcyj() {
		return xcyj;
	}

	public void setXcyj(String xcyj) {
		this.xcyj = xcyj;
	}

	public long getRynx() {
		return rynx;
	}

	public void setRynx(long rynx) {
		this.rynx = rynx;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

}

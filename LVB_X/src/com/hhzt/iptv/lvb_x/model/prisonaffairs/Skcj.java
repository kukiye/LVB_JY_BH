package com.hhzt.iptv.lvb_x.model.prisonaffairs;

import java.io.Serializable;
import java.util.Date;

/**
 * 三课成绩
 * */
public class Skcj implements Serializable {
	private static final long serialVersionUID = 1L;

	private String prisonName;
	private String name;// 姓名
	private String code;// 编号
	private String kxcj;// 考试成绩
	private long date;// 考试日期
	private String jyjd;// 教育阶段
	private String sfhz; // 是否获证

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

	public String getKxcj() {
		return kxcj;
	}

	public void setKxcj(String kxcj) {
		this.kxcj = kxcj;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getJyjd() {
		return jyjd;
	}

	public void setJyjd(String jyjd) {
		this.jyjd = jyjd;
	}

	public String getSfhz() {
		return sfhz;
	}

	public void setSfhz(String sfhz) {
		this.sfhz = sfhz;
	}

}

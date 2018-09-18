package com.hhzt.iptv.lvb_x.model.prisonaffairs;

import java.io.Serializable;
import java.util.Date;

/***
 * 余刑查询
 **/
public class Yxcx implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private String prisonName;// 监狱名称
	private String name;// 姓名
	private String code;// 编号
	private long startDate;// 执行刑期起日
	private long endDate;// 执行刑期止日

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

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

}

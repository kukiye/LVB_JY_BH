package com.hhzt.iptv.lvb_x.model.prisonaffairs;

import java.io.Serializable;
import java.util.Date;

/**
 * 培训获证
 * 
 * */
public class Pxhz implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private String prisonName;// 监狱名称
	private String name;// 姓名
	private String code;// 编号
	private String zsmc;// 证书名称
	private String zshm;// 证书号码
	private long date;// 获证日期

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

	public String getZsmc() {
		return zsmc;
	}

	public void setZsmc(String zsmc) {
		this.zsmc = zsmc;
	}

	public String getZshm() {
		return zshm;
	}

	public void setZshm(String zshm) {
		this.zshm = zshm;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

}

package com.hhzt.iptv.lvb_x.model.prisonaffairs;

import java.io.Serializable;
import java.util.Date;
/**
 *计分考核 
 */
public class Score implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String prisonName;//监狱名称
    private String name;//姓名
    private String code;//编号
    private String kjlf;//扣奖励分
    private String ljlf;//累计奖励分
    private String prisonid;//监狱Id
    private long date;//考核日期
    private String yjjf;//月奖励分
    private String dkjf;//抵扣奖励分
    
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
    public String getKjlf() {
        return kjlf;
    }
    public void setKjlf(String kjlf) {
        this.kjlf = kjlf;
    }
    public String getLjlf() {
        return ljlf;
    }
    public void setLjlf(String ljlf) {
        this.ljlf = ljlf;
    }
    public String getPrisonid() {
        return prisonid;
    }
    public void setPrisonid(String prisonid) {
        this.prisonid = prisonid;
    }
    
    public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getYjjf() {
        return yjjf;
    }
    public void setYjjf(String yjjf) {
        this.yjjf = yjjf;
    }
    public String getDkjf() {
        return dkjf;
    }
    public void setDkjf(String dkjf) {
        this.dkjf = dkjf;
    }
	@Override
	public String toString() {
		return "Score [prisonName=" + prisonName + ", name=" + name + ", code="
				+ code + ", kjlf=" + kjlf + ", ljlf=" + ljlf + ", prisonid="
				+ prisonid + ", date=" + date + ", yjjf=" + yjjf + ", dkjf="
				+ dkjf + "]";
	}
    
    
}

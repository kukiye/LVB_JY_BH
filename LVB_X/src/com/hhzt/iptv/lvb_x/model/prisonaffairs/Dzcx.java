package com.hhzt.iptv.lvb_x.model.prisonaffairs;

import java.io.Serializable;
import java.util.Date;
/**
 * 大帐查询
 * */
public class Dzcx implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String prisonName;//监狱名称
    private String name;//姓名
    private String code;//编号
    private String type;//收支类型
    private String szzh;//收支账户
    private String zhye;//账户余额
    private String byxf;//本月消费
    private String kyye;//本月可用余额
    private long date;//年月
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
    public String getSzzh() {
        return szzh;
    }
    public void setSzzh(String szzh) {
        this.szzh = szzh;
    }
    public String getZhye() {
        return zhye;
    }
    public void setZhye(String zhye) {
        this.zhye = zhye;
    }
    public String getByxf() {
        return byxf;
    }
    public void setByxf(String byxf) {
        this.byxf = byxf;
    }
    public String getKyye() {
        return kyye;
    }
    public void setKyye(String kyye) {
        this.kyye = kyye;
    }
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
    
}

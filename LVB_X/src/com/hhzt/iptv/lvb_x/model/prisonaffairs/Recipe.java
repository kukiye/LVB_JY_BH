package com.hhzt.iptv.lvb_x.model.prisonaffairs;

import java.io.Serializable;
import java.util.Date;

/**  
 * 菜谱
 * */
public class Recipe implements Serializable{
    private static final long serialVersionUID = 1L;
    private String prisonName;//监狱名称
    private String week;//星期
    private Date date;//日期
    private String breakfastStaple;//早餐主食
    private String breakfastSupplement;//早餐副食
    private String lunchStaple;//午餐主食
    private String lunchSupplement;//午餐副食
    private String dinnerStaple;//晚餐主食
    private String dinnerSupplement;//晚餐副食
    
    public String getPrisonName() {
        return prisonName;
    }
    public void setPrisonName(String prisonName) {
        this.prisonName = prisonName;
    }
    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getBreakfastStaple() {
        return breakfastStaple;
    }
    public void setBreakfastStaple(String breakfastStaple) {
        this.breakfastStaple = breakfastStaple;
    }
    public String getBreakfastSupplement() {
        return breakfastSupplement;
    }
    public void setBreakfastSupplement(String breakfastSupplement) {
        this.breakfastSupplement = breakfastSupplement;
    }
    public String getLunchStaple() {
        return lunchStaple;
    }
    public void setLunchStaple(String lunchStaple) {
        this.lunchStaple = lunchStaple;
    }
    public String getLunchSupplement() {
        return lunchSupplement;
    }
    public void setLunchSupplement(String lunchSupplement) {
        this.lunchSupplement = lunchSupplement;
    }
    public String getDinnerStaple() {
        return dinnerStaple;
    }
    public void setDinnerStaple(String dinnerStaple) {
        this.dinnerStaple = dinnerStaple;
    }
    public String getDinnerSupplement() {
        return dinnerSupplement;
    }
    public void setDinnerSupplement(String dinnerSupplement) {
        this.dinnerSupplement = dinnerSupplement;
    }
    
}

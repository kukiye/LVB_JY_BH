package com.hhzt.iptv.lvb_x.model;

import java.io.Serializable;

/**
 * @author: Aaron
 * 整理了 请看接口说明文档
 */
public class MainMenuEntity implements Serializable {
    private int id;
    private String name;
    private String code;
    private int sequence;
    private String templatecode;
    private String zhtitle;
    private String entitle;
    private int enable;
    private int childnum;
    private String menubgurl;
    private String menulogourl;

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

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getChildnum() {
        return childnum;
    }

    public void setChildnum(int childnum) {
        this.childnum = childnum;
    }

    public String getMenubgurl() {
        return menubgurl;
    }

    public void setMenubgurl(String menubgurl) {
        this.menubgurl = menubgurl;
    }

    public String getMenulogourl() {
        return menulogourl;
    }

    public void setMenulogourl(String menulogourl) {
        this.menulogourl = menulogourl;
    }
}

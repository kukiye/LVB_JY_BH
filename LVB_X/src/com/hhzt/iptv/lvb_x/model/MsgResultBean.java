package com.hhzt.iptv.lvb_x.model;

/**
 * Created by kobe on 2015/12/5 17:55.
 * 邮箱：quzhongxiang_kobe@163.com
 */
public class MsgResultBean {
    private boolean result;
    private String desc;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "MsgResultBean [result=" + result + ", desc=" + desc + "]";
    }

}


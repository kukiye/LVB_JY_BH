package com.hhzt.iptv.lvb_x.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kobe on 2015/12/5 17:35.
 * 邮箱：quzhongxiang_kobe@163.com
 */
public class ForcePlayBean implements Parcelable {
    private String url;
    private long starttime;
    private long endtime;
    private int type; //（ 0视频 1 音频）
    private int priority; //（优先权。数字越大越优先播放 ）
    private String accountname;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
    public String toString() {
        return "ForcePlayBean [url=" + url + ", starttime=" + starttime + ", endtime=" + endtime + "]";
    }

    public static final Parcelable.Creator<ForcePlayBean> CREATOR = new Creator<ForcePlayBean>() {

        @Override
        public ForcePlayBean createFromParcel(Parcel source) {
            ForcePlayBean playBean = new ForcePlayBean();
            playBean.url = source.readString();
            playBean.starttime = source.readLong();
            playBean.endtime = source.readLong();  
            playBean.type = source.readInt();
            playBean.priority = source.readInt();
            return playBean;
        }

        @Override
        public ForcePlayBean[] newArray(int size) {
            return new ForcePlayBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeLong(starttime);
        dest.writeLong(endtime);
        dest.writeInt(type);
        dest.writeInt(priority);
    }

}


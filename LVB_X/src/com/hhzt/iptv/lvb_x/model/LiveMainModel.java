/**
 * 作者：   Johnson
 * 日期：   2014年6月30日下午6:44:40
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：LiveMainModel.java
 */
package com.hhzt.iptv.lvb_x.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LiveMainModel implements Parcelable {
	private int id;
	private String channelname;
	private String cpobjectcode;
	private int channelnumber;
	private String timeshift;
	private long timeshiftduration;
	private String timeshifturl;
	private String hdtv;
	private String casttype;
	private String recordstarttime;
	private String recordendtime;
	private String description;
	private String frequency;
	private String pmtpid;
	private String channelurl;
	private String unicasturl;
	private String channelgroup;
	private String servicegroup;
	private String nowplay;
	private String nextplay;
	private int categoryid;
	private String callsignurl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChannelname() {
		return channelname;
	}

	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}

	public String getCpobjectcode() {
		return cpobjectcode;
	}

	public void setCpobjectcode(String cpobjectcode) {
		this.cpobjectcode = cpobjectcode;
	}

	public int getChannelnumber() {
		return channelnumber;
	}

	public void setChannelnumber(int channelnumber) {
		this.channelnumber = channelnumber;
	}

	public String getTimeshift() {
		return timeshift;
	}

	public void setTimeshift(String timeshift) {
		this.timeshift = timeshift;
	}

	public long getTimeshiftduration() {
		return timeshiftduration;
	}

	public void setTimeshiftduration(long timeshiftduration) {
		this.timeshiftduration = timeshiftduration;
	}

	public String getTimeshifturl() {
		return timeshifturl;
	}

	public void setTimeshifturl(String timeshifturl) {
		this.timeshifturl = timeshifturl;
	}

	public String getHdtv() {
		return hdtv;
	}

	public void setHdtv(String hdtv) {
		this.hdtv = hdtv;
	}

	public String getCasttype() {
		return casttype;
	}

	public void setCasttype(String casttype) {
		this.casttype = casttype;
	}

	public String getRecordstarttime() {
		return recordstarttime;
	}

	public void setRecordstarttime(String recordstarttime) {
		this.recordstarttime = recordstarttime;
	}

	public String getRecordendtime() {
		return recordendtime;
	}

	public void setRecordendtime(String recordendtime) {
		this.recordendtime = recordendtime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getPmtpid() {
		return pmtpid;
	}

	public void setPmtpid(String pmtpid) {
		this.pmtpid = pmtpid;
	}

	public String getChannelurl() {
		return channelurl;
	}

	public void setChannelurl(String channelurl) {
		this.channelurl = channelurl;
	}

	public String getUnicasturl() {
		return unicasturl;
	}

	public void setUnicasturl(String unicasturl) {
		this.unicasturl = unicasturl;
	}

	public String getChannelgroup() {
		return channelgroup;
	}

	public void setChannelgroup(String channelgroup) {
		this.channelgroup = channelgroup;
	}

	public String getServicegroup() {
		return servicegroup;
	}

	public void setServicegroup(String servicegroup) {
		this.servicegroup = servicegroup;
	}

	public String getNowplay() {
		return nowplay;
	}

	public void setNowplay(String nowplay) {
		this.nowplay = nowplay;
	}

	public String getNextplay() {
		return nextplay;
	}

	public void setNextplay(String nextplay) {
		this.nextplay = nextplay;
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getCallsignurl() {
		return callsignurl;
	}

	public void setCallsignurl(String callsignurl) {
		this.callsignurl = callsignurl;
	}

	@Override
	public String toString() {
		return "LiveMainModel [id=" + id + ", channelname=" + channelname + ", cpobjectcode=" + cpobjectcode + ", channelnumber=" + channelnumber
				+ ", timeshift=" + timeshift + ", timeshiftduration=" + timeshiftduration + ", timeshifturl=" + timeshifturl + ", hdtv=" + hdtv
				+ ", casttype=" + casttype + ", recordstarttime=" + recordstarttime + ", recordendtime=" + recordendtime + ", description="
				+ description + ", frequency=" + frequency + ", pmtpid=" + pmtpid + ", channelurl=" + channelurl + ", unicasturl=" + unicasturl
				+ ", channelgroup=" + channelgroup + ", servicegroup=" + servicegroup + ", nowplay=" + nowplay + ", nextplay=" + nextplay
				+ ", categoryid=" + categoryid + ", callsignurl=" + callsignurl + "]";
	}

	public static final Parcelable.Creator<LiveMainModel> CREATOR = new Creator<LiveMainModel>() {

		@Override
		public LiveMainModel createFromParcel(Parcel source) {
			LiveMainModel liveItem = new LiveMainModel();
			liveItem.id = source.readInt();
			liveItem.channelname = source.readString();
			liveItem.cpobjectcode = source.readString();
			liveItem.channelnumber = source.readInt();
			liveItem.timeshift = source.readString();
			liveItem.timeshiftduration = source.readLong();
			liveItem.timeshifturl = source.readString();
			liveItem.hdtv = source.readString();
			liveItem.casttype = source.readString();
			liveItem.recordstarttime = source.readString();
			liveItem.recordendtime = source.readString();
			liveItem.description = source.readString();
			liveItem.frequency = source.readString();
			liveItem.pmtpid = source.readString();
			liveItem.channelurl = source.readString();
			liveItem.unicasturl = source.readString();
			liveItem.channelgroup = source.readString();
			liveItem.servicegroup = source.readString();
			liveItem.nowplay = source.readString();
			liveItem.nextplay = source.readString();
			liveItem.categoryid = source.readInt();
			liveItem.callsignurl = source.readString();
			return liveItem;
		}

		@Override
		public LiveMainModel[] newArray(int size) {
			return new LiveMainModel[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(channelname);
		dest.writeString(cpobjectcode);
		dest.writeInt(channelnumber);
		dest.writeString(timeshift);
		dest.writeLong(timeshiftduration);
		dest.writeString(timeshifturl);
		dest.writeString(hdtv);
		dest.writeString(casttype);
		dest.writeString(recordstarttime);
		dest.writeString(recordendtime);
		dest.writeString(description);
		dest.writeString(frequency);
		dest.writeString(pmtpid);
		dest.writeString(channelurl);
		dest.writeString(unicasturl);
		dest.writeString(channelgroup);
		dest.writeString(servicegroup);
		dest.writeString(nowplay);
		dest.writeString(nextplay);
		dest.writeInt(categoryid);
		dest.writeString(callsignurl);
	}

}

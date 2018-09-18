package com.hhzt.iptv.lvb_x.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VodItemPlayMessageModel implements Parcelable {

	private int id;
	private String viewname;
	private int sequence;
	private String duration;
	private String playurl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getViewname() {
		return viewname;
	}

	public void setViewname(String viewname) {
		this.viewname = viewname;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getPlayurl() {
		return playurl;
	}

	public void setPlayurl(String playurl) {
		this.playurl = playurl;
	}

	public static final Parcelable.Creator<VodItemPlayMessageModel> CREATOR = new Creator<VodItemPlayMessageModel>() {

		@Override
		public VodItemPlayMessageModel createFromParcel(Parcel source) {
			VodItemPlayMessageModel item = new VodItemPlayMessageModel();
			item.id = source.readInt();
			item.viewname = source.readString();
			item.sequence = source.readInt();
			item.duration = source.readString();
			item.playurl = source.readString();
			return item;
		}

		@Override
		public VodItemPlayMessageModel[] newArray(int size) {
			return new VodItemPlayMessageModel[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(viewname);
		dest.writeInt(sequence);
		dest.writeString(duration);
		dest.writeString(playurl);
	}

}

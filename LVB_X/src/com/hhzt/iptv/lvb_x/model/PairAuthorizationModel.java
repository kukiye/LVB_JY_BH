package com.hhzt.iptv.lvb_x.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PairAuthorizationModel implements Parcelable {
	private String title;
	private String contents;

	private String pairFromUserName;
	private String pairFromUserIP;
	private String pairFromMacAddr;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getPairFromUserName() {
		return pairFromUserName;
	}

	public void setPairFromUserName(String pairFromUserName) {
		this.pairFromUserName = pairFromUserName;
	}

	public String getPairFromUserIP() {
		return pairFromUserIP;
	}

	public void setPairFromUserIP(String pairFromUserIP) {
		this.pairFromUserIP = pairFromUserIP;
	}

	public String getPairFromMacAddr() {
		return pairFromMacAddr;
	}

	public void setPairFromMacAddr(String pairFromMacAddr) {
		this.pairFromMacAddr = pairFromMacAddr;
	}

	@Override
	public String toString() {
		return "AuthorizationMsg [title=" + title + ", contents=" + contents + ", pairFromUserName=" + pairFromUserName + ", pairFromUserIP="
				+ pairFromUserIP + ", pairFromMacAddr=" + pairFromMacAddr + "]";
	}

	public static final Parcelable.Creator<PairAuthorizationModel> CREATOR = new Creator<PairAuthorizationModel>() {

		@Override
		public PairAuthorizationModel createFromParcel(Parcel source) {
			PairAuthorizationModel item = new PairAuthorizationModel();
			item.title = source.readString();
			item.contents = source.readString();
			item.pairFromUserName = source.readString();
			item.pairFromUserIP = source.readString();
			item.pairFromMacAddr = source.readString();

			return item;
		}

		@Override
		public PairAuthorizationModel[] newArray(int size) {
			return new PairAuthorizationModel[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(contents);
		dest.writeString(pairFromUserName);
		dest.writeString(pairFromUserIP);
		dest.writeString(pairFromMacAddr);
	}

}

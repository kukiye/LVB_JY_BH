package com.hhzt.iptv.lvb_x.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VersionInfosModel implements Parcelable {
	private int id;
	private String version;
	private int versioncode;
	private String needupdate;
	private String downloadurl;
	private String forceupdate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getVersioncode() {
		return versioncode;
	}

	public void setVersioncode(int versioncode) {
		this.versioncode = versioncode;
	}

	public String getNeedupdate() {
		return needupdate;
	}

	public void setNeedupdate(String needupdate) {
		this.needupdate = needupdate;
	}

	public String getDownloadurl() {
		return downloadurl;
	}

	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}

	public String getForceupdate() {
		return forceupdate;
	}

	public void setForceupdate(String forceupdate) {
		this.forceupdate = forceupdate;
	}

	@Override
	public String toString() {
		return "VersionInfosModel [id=" + id + ", version=" + version + ", versioncode=" + versioncode + ", needupdate=" + needupdate
				+ ", downloadurl=" + downloadurl + ", forceupdate=" + forceupdate + "]";
	}

	public static final Parcelable.Creator<VersionInfosModel> CREATOR = new Creator<VersionInfosModel>() {

		@Override
		public VersionInfosModel createFromParcel(Parcel source) {
			VersionInfosModel item = new VersionInfosModel();
			item.id = source.readInt();
			item.version = source.readString();
			item.versioncode = source.readInt();
			item.needupdate = source.readString();
			item.downloadurl = source.readString();
			item.forceupdate = source.readString();
			return item;
		}

		@Override
		public VersionInfosModel[] newArray(int size) {
			return new VersionInfosModel[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(version);
		dest.writeInt(versioncode);
		dest.writeString(needupdate);
		dest.writeString(downloadurl);
		dest.writeString(forceupdate);
	}

}

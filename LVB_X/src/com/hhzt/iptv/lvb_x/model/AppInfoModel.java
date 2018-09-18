package com.hhzt.iptv.lvb_x.model;

import com.hhzt.iptv.lvb_x.utils.ImageFormatConvert;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class AppInfoModel implements Parcelable {
	private Drawable icon;
	private String appname;
	private String packname;
	private Boolean isSystemApp;

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getPackname() {
		return packname;
	}

	public void setPackname(String packname) {
		this.packname = packname;
	}

	public Boolean getIsSystemApp() {
		return isSystemApp;
	}

	public void setIsSystemApp(Boolean isSystemApp) {
		this.isSystemApp = isSystemApp;
	}

	public static final Parcelable.Creator<AppInfoModel> CREATOR = new Creator<AppInfoModel>() {

		@Override
		public AppInfoModel createFromParcel(Parcel source) {
			AppInfoModel appInfo = new AppInfoModel();
			byte[] bytes = (byte[]) source.readValue(byte[].class.getClassLoader());
			appInfo.icon = ImageFormatConvert.convertBytes2Drawable(bytes);
			appInfo.appname = source.readString();
			appInfo.packname = source.readString();
			appInfo.isSystemApp = (Boolean) source.readValue(Boolean.class.getClassLoader());
			return appInfo;
		}

		@Override
		public AppInfoModel[] newArray(int size) {
			return new AppInfoModel[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeValue(ImageFormatConvert.convertDrawable2Bytes(icon));
		dest.writeString(appname);
		dest.writeString(packname);
		dest.writeValue(isSystemApp);
	}

	@Override
	public String toString() {
		return "AppInfoModel [icon=" + icon + ", appname=" + appname + ", packname=" + packname + ", isSystemApp=" + isSystemApp + "]";
	}

}

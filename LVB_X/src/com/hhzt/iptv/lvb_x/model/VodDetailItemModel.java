package com.hhzt.iptv.lvb_x.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VodDetailItemModel implements Parcelable {

	private int id;
	private String programname;
	private String searchname;
	private String year;
	private String starrating;
	private String areaname;
	private String programtype;
	private String genrenames;
	private String actornames;
	private String writernames;
	private String description;
	private String price;
	private String imageurl;
	private String imageurlbig;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageurlbig() {
		return imageurlbig;
	}

	public void setImageurlbig(String imageurlbig) {
		this.imageurlbig = imageurlbig;
	}

	public String getProgramname() {
		return programname;
	}

	public void setProgramname(String programname) {
		this.programname = programname;
	}

	public String getSearchname() {
		return searchname;
	}

	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStarrating() {
		return starrating;
	}

	public void setStarrating(String starrating) {
		this.starrating = starrating;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getProgramtype() {
		return programtype;
	}

	public void setProgramtype(String programtype) {
		this.programtype = programtype;
	}

	public String getGenrenames() {
		return genrenames;
	}

	public void setGenrenames(String genrenames) {
		this.genrenames = genrenames;
	}

	public String getActornames() {
		return actornames;
	}

	public void setActornames(String actornames) {
		this.actornames = actornames;
	}

	public String getWriternames() {
		return writernames;
	}

	public void setWriternames(String writernames) {
		this.writernames = writernames;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getprice() {
		return price;
	}

	public void setprice(String price) {
		this.price = price;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeString(programname);
		dest.writeString(searchname);
		dest.writeString(year);
		dest.writeString(starrating);
		dest.writeString(areaname);
		dest.writeString(programtype);
		dest.writeString(genrenames);
		dest.writeString(actornames);
		dest.writeString(writernames);
		dest.writeString(description);
		dest.writeString(price);
		dest.writeString(imageurl);
		dest.writeString(imageurlbig);
	}

	public static final Parcelable.Creator<VodDetailItemModel> CREATOR = new Creator<VodDetailItemModel>() {

		@Override
		public VodDetailItemModel createFromParcel(Parcel source) {
			VodDetailItemModel item = new VodDetailItemModel();
			item.id = source.readInt();
			item.programname = source.readString();
			item.searchname = source.readString();
			item.year = source.readString();
			item.starrating = source.readString();
			item.areaname = source.readString();
			item.programtype = source.readString();
			item.genrenames = source.readString();
			item.actornames = source.readString();
			item.writernames = source.readString();
			item.description = source.readString();
			item.price = source.readString();
			item.imageurl = source.readString();
			item.imageurlbig = source.readString();
			return item;
		}

		@Override
		public VodDetailItemModel[] newArray(int size) {
			return new VodDetailItemModel[size];
		}
	};

}

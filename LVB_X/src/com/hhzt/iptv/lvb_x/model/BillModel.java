/**
 * 作者：   Johnson
 * 日期：   2014年8月14日下午6:33:32
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：BillModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class BillModel {
	private String feetype;
	private String typeTitle;
	private String feename;
	private String fee;
	private String feedate;

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public String getTypeTitle() {
		return typeTitle;
	}

	public void setTypeTitle(String typeTitle) {
		this.typeTitle = typeTitle;
	}

	public String getFeename() {
		return feename;
	}

	public void setFeename(String feename) {
		this.feename = feename;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getFeedate() {
		return feedate;
	}

	public void setFeedate(String feedate) {
		this.feedate = feedate;
	}

	@Override
	public String toString() {
		return "BillModel [feetype=" + feetype + ", typeTitle=" + typeTitle + ", feename=" + feename + ", fee=" + fee + ", feedate=" + feedate + "]";
	}

}

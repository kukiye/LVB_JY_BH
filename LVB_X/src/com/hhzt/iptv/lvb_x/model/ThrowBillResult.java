/**
 * 作者：   Johnson
 * 日期：   2014年8月15日下午4:16:00
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：ThrowBillResult.java
 */
package com.hhzt.iptv.lvb_x.model;

public class ThrowBillResult {
	private String result;
	private String desc;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
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
		return "ThrowBillResult [result=" + result + ", desc=" + desc + "]";
	}

}

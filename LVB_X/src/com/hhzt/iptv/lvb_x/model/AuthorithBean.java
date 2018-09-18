package com.hhzt.iptv.lvb_x.model;

public class AuthorithBean {
	private boolean result;
	private String desc;

	public boolean getResult() {
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
		return "AuthorithBean [result=" + result + ", desc=" + desc + "]";
	}

}

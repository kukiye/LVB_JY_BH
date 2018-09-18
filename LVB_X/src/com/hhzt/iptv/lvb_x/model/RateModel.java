/**
 * 作者：   Johnson
 * 日期：   2014年7月11日下午2:13:06
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：RateModel.java
 */
package com.hhzt.iptv.lvb_x.model;

public class RateModel {
	private int id;
	private String selfsymbol;
	private String selfname;
	private String symbol;
	private String name;
	private String mbuyprice;
	private String fbuyprice;
	private String sellprice;
	private String baseprice;
	private String date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSelfsymbol() {
		return selfsymbol;
	}

	public void setSelfsymbol(String selfsymbol) {
		this.selfsymbol = selfsymbol;
	}

	public String getSelfname() {
		return selfname;
	}

	public void setSelfname(String selfname) {
		this.selfname = selfname;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMbuyprice() {
		return mbuyprice;
	}

	public void setMbuyprice(String mbuyprice) {
		this.mbuyprice = mbuyprice;
	}

	public String getFbuyprice() {
		return fbuyprice;
	}

	public void setFbuyprice(String fbuyprice) {
		this.fbuyprice = fbuyprice;
	}

	public String getSellprice() {
		return sellprice;
	}

	public void setSellprice(String sellprice) {
		this.sellprice = sellprice;
	}

	public String getBaseprice() {
		return baseprice;
	}

	public void setBaseprice(String baseprice) {
		this.baseprice = baseprice;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "RateModel [id=" + id + ", selfsymbol=" + selfsymbol + ", selfname=" + selfname + ", symbol=" + symbol + ", name=" + name
				+ ", mbuyprice=" + mbuyprice + ", fbuyprice=" + fbuyprice + ", sellprice=" + sellprice + ", baseprice=" + baseprice + ", date="
				+ date + "]";
	}

}

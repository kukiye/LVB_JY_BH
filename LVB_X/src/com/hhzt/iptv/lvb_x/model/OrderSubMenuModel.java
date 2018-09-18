/**
 * 作者：   Johnson
 * 日期：   2014年7月1日下午5:27:30
 * 包名：    com.hhzt.iptv.lvb_x.model
 * 工程名：LVB_X
 * 文件名：OrderSubMenuModel.java
 */
package com.hhzt.iptv.lvb_x.model;

import java.util.ArrayList;

public class OrderSubMenuModel {
	private int pageNo;
	private int pageSize;
	private int totalCount;
	private ArrayList<OrderSubSingleModel> result;
	private int totalPages;
	private boolean hasNext;
	private int first;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public ArrayList<OrderSubSingleModel> getResult() {
		return result;
	}

	public void setResult(ArrayList<OrderSubSingleModel> result) {
		this.result = result;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	@Override
	public String toString() {
		return "OrderSubMenuModel [pageNo=" + pageNo + ", pageSize=" + pageSize + ", totalCount=" + totalCount + ", result=" + result
				+ ", totalPages=" + totalPages + ", hasNext=" + hasNext + ", first=" + first + "]";
	}

}

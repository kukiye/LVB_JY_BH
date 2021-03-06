package com.hhzt.iptv.lvb_x.model;

import java.util.ArrayList;

public class AppDetailDataModel {
	private int pageNo;
	private int pageSize;
	private int totalCount;
	private ArrayList<AppItemMessageModel> result = new ArrayList<AppItemMessageModel>();
	private int first;
	private int totalPages;
	private boolean hasNext;

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

	public ArrayList<AppItemMessageModel> getResult() {
		return result;
	}

	public void setResult(ArrayList<AppItemMessageModel> result) {
		this.result = result;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
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

}

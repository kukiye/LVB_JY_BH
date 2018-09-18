package com.hhzt.iptv.lvb_x.model;

import java.util.ArrayList;

public class BookPageModel {
	private int pageNo;
	private int pageSize;
	private int totalCount;
	private ArrayList<BookModel> result;
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

	public ArrayList<BookModel> getResult() {
		return result;
	}

	public void setResult(ArrayList<BookModel> result) {
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
		return "BookPageModel [pageNo=" + pageNo + ", pageSize=" + pageSize + ", totalCount="
				+ totalCount + ", result=" + result + ", totalPages=" + totalPages + ", hasNext="
				+ hasNext + ", first=" + first + "]";
	}

}

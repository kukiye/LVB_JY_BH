package com.hhzt.iptv.lvb_x.model;

import java.util.ArrayList;

public class LiveBackDataListModel {
	public int pageNo;
	public int pageSize;
	public int totalCount;
	public ArrayList<LiveSubModel> result;
	public int totalPages;
	public boolean hasNext;
}

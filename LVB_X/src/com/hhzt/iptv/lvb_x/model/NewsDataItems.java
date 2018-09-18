/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 *
 * @author Johnson
 * 2014年3月19日 下午8:15:42
 */
package com.hhzt.iptv.lvb_x.model;

import java.util.ArrayList;

public class NewsDataItems {
	public int pageNo;
	public int pageSize;
	public int totalCount;

	public ArrayList<NewsSingleItem> result = new ArrayList<NewsSingleItem>();
	public int first;
	public int totalPages;
	public boolean hasNext;
}

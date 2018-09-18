package com.hhzt.iptv.lvb_x.mgr;

import com.hhzt.iptv.lvb_x.model.NewsSingleItem;


public class MsgMgr {
	
	private static MsgMgr mMsgMgr;
	
	private NewsSingleItem mNewsItem = null;

	private MsgMgr() {

	}

	public static MsgMgr getInstance() {
		if (null == mMsgMgr) {
			synchronized (MsgMgr.class) {
				if (null == mMsgMgr) {
					mMsgMgr = new MsgMgr();
				}
			}
		}
		return mMsgMgr;
	}

	public void addNewsSingleItem(NewsSingleItem item) {
		if(mNewsItem == null){
			mNewsItem = new NewsSingleItem();
		}
		mNewsItem = item;
	}
	
	public NewsSingleItem getNewsSingleItem(){
		return mNewsItem;
	}
}

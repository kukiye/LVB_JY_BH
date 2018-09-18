/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 *
 * @author Johnson
 * 2014年3月19日 下午1:19:26
 */
package com.hhzt.iptv.lvb_x.model;

public class PushMessage {
	private String msgid;
	private String type;
	private String content;

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "PushMessage [msgid=" + msgid + ", type=" + type + ", content=" + content + "]";
	}

}

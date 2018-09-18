/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 *
 * @author Johnson
 * 2014年2月27日 上午10:09:28
 */
package com.hhzt.iptv.jni;

public class SmsMediaRtpToUdp {
	// jni底层初始化和释放动作
	public static final int MEDIA_INIT = 0;
	public static final int MEDIA_DEINIT = 1;

	public static native int Init(String rtpUrl, String udpUrl);

	public static native int Release();
  
	static {
		System.loadLibrary("RTP");
	}
}

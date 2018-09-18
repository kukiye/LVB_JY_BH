/**
 * 作 者：Johnson
 * 日 期：2015年6月18日下午4:33:34
 * 包 名：com.hhzt.iptv.lvb_x.scller
 * 工程名：MediaDetector
 * 文件名：UDPCLMgr.java
 */
package com.hhzt.iptv.lvb_x.scller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPCLMgr {
	private int mPort = 0;
	private String mIPAddress = "";
	private final static byte[] mHex = "0123456789ABCDEF".getBytes();

	private static UDPCLMgr mUDPCLMgr = new UDPCLMgr();
	private static DatagramSocket mDatagramSocket = null;

	private UDPCLMgr() {

	}

	public static UDPCLMgr getInstance() {
		if (null == mUDPCLMgr) {
			synchronized (UDPCLMgr.class) {
				if (null == mUDPCLMgr) {
					mUDPCLMgr = new UDPCLMgr();
				}
			}
		}

		return mUDPCLMgr;
	}

	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	public static byte[] HexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	public static String Bytes2HexString(byte[] b) {
		byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = mHex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = mHex[b[i] & 0x0f];
		}
		return new String(buff);
	}

	public void init(String ipPortString) {
		String ipPort[] = ipPortString.split(":");
		if (ipPort.length > 1) {
			mIPAddress = ipPort[0];
			mPort = Integer.parseInt(ipPort[1]);
		}
	}

	public void send(String cmdString) {
		if (null == mDatagramSocket) {
			try {
				mDatagramSocket = new DatagramSocket(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		byte[] bufs = HexString2Bytes(cmdString);

		InetAddress destination = null;
		try {
			destination = InetAddress.getByName(mIPAddress);
		} catch (Exception e) {
		}

		DatagramPacket dp = new DatagramPacket(bufs, bufs.length, destination, mPort);
		try {
			mDatagramSocket.send(dp);
		} catch (IOException e) {
		}
		mDatagramSocket.close();
		mDatagramSocket = null;
	}
}

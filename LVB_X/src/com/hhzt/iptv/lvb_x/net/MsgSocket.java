/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 *
 * @author Johnson
 * 2014年3月19日 上午11:07:02
 */
package com.hhzt.iptv.lvb_x.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.interfaces.INewOnSuccessCB;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.MsgMgr;
import com.hhzt.iptv.lvb_x.mgr.MsgPushMgr;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.mgr.ThreadPoolManager;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.DeviceInfoBean;
import com.hhzt.iptv.lvb_x.model.NewsDataItems;
import com.hhzt.iptv.lvb_x.model.NewsSingleItem;
import com.hhzt.iptv.lvb_x.model.PushMessage;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

import android.os.Handler;
import android.os.Message;

/**
 * 该消息推送Sokect基本参数如下：
 * 
 * 1.服务器60s内无请求会自动断开Sokect；
 * 
 * 2.客户端Sokect请求超时设置为60s=3次心跳包时间间隔
 * 
 * 3.客户端Sokect心跳间隔为20s
 * 
 * 4.初始化Sockect连接和重连失败时时间间隔为20s
 * 
 * @author Johnson
 * 
 */
public class MsgSocket {
	private static Handler mSocketHandler = null;
	private static Socket mSocket = null;
	private static MsgSocket mMsgSocket = null;
	private static BufferedReader mInBufferedReader = null;
	private static PrintWriter mOutPrintWriter = null;

	/**
	 * 初始化sokect参数，并建立连接
	 * 
	 * @param dstName
	 * @param dstPort
	 */
	private MsgSocket(String dstName, int dstPort) {
		if (null == mSocket) {
			try {
				// 重连时首先将之前的sokect
				closeSocket();
				mSocket = new Socket();
				mSocket.bind(null);
				mSocket.setSoTimeout(Constant.IPTV_TIME_ONE_MINUTE);
				mSocket.connect(new InetSocketAddress(dstName, dstPort), 0);
			} catch (Exception e) {
				LogUtil.e("MsgSocket>>>>>>>>>>>>>Exception=" + e);
			}
		}
	}

	/**
	 * 获取socket单例
	 * 
	 * @param dstName
	 * @param dstPort
	 * @param handler
	 * @return
	 */
	public static MsgSocket getInstance(String dstName, int dstPort,
			Handler handler) {
		if (null == mSocket) {
			try {
				mSocketHandler = handler;
				mMsgSocket = new MsgSocket(dstName, dstPort);
				if (null != mSocket && !mSocket.isClosed()
						&& mSocket.isConnected()) {
					mInBufferedReader = new BufferedReader(
							new InputStreamReader(mSocket.getInputStream()));
					mOutPrintWriter = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(mSocket.getOutputStream())),
							true);
				} else {
					mSocketHandler
							.removeMessages(Constant.IPTV_MSG_SOCKET_HEART_CHECK);
					mSocketHandler
							.removeMessages(Constant.IPTV_MSG_SOCKET_SEND_RECONNECT_ACTION);
					mSocketHandler.sendEmptyMessageDelayed(
							Constant.IPTV_MSG_SOCKET_SEND_RECONNECT_ACTION,
							Constant.IPTV_TIME_TWENTY_SECONDS);
				}
			} catch (Exception e) {
				LogUtil.e("MsgSocket getInstance>>>>>>>>>>>>>Exception=" + e);
			}
		}
		return mMsgSocket;
	}

	/**
	 * socket重连动作
	 * 
	 * @param handler
	 * @param dstName
	 * @param dstPort
	 */
	public void reconnect(final Handler handler, final String dstName,
			final int dstPort) {
		ThreadPoolManager.getInstance().addTask(new Runnable() {

			@Override
			public void run() {
				try {
					// 重连时首先将之前的sokect
					closeSocket();
					mSocket = new Socket();
					mSocket.bind(null);
					mSocket.setSoTimeout(Constant.IPTV_TIME_ONE_MINUTE);
					mSocket.connect(new InetSocketAddress(dstName, dstPort), 0);
					mInBufferedReader = new BufferedReader(
							new InputStreamReader(mSocket.getInputStream()));
					mOutPrintWriter = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(mSocket.getOutputStream())),
							true);

					// 重连成功之后，将重连消息删除
					handler.removeMessages(Constant.IPTV_MSG_SOCKET_SEND_RECONNECT_ACTION);
					// 开启sokect数据接收动作
					handler.sendEmptyMessage(Constant.IPTV_MSG_SOCKET_RECEIVE_CHECK);
					// 注册sokect登录动作
					handler.sendEmptyMessage(Constant.IPTV_MSG_SOCKET_SEND_LOGIN_ACTION);
					// 发送心跳包
					handler.sendEmptyMessage(Constant.IPTV_MSG_SOCKET_HEART_CHECK);

					LogUtil.d("reconnect>>>>>>>>>>>>>Successfully!");
				} catch (Exception e) {
					LogUtil.e("reconnect>>>>>>>>>>>>>Exception=" + e);
					handler.removeMessages(Constant.IPTV_MSG_SOCKET_HEART_CHECK);
					handler.removeMessages(Constant.IPTV_MSG_SOCKET_SEND_RECONNECT_ACTION);
					handler.sendEmptyMessageDelayed(
							Constant.IPTV_MSG_SOCKET_SEND_RECONNECT_ACTION,
							Constant.IPTV_TIME_TWENTY_SECONDS);
				}
			}
		});
	}

	/**
	 * 发送sokect数据
	 * 
	 * @param sokectType
	 */
	public void sendSocketCmd(int sokectType) {
		LogUtil.d("sendSocketCmd----------" + sokectType);
		try {
			switch (sokectType) {
			case Constant.SOKECT_LOGIN:
				loginRegister();
				break;
			case Constant.SOKECT_RECONNECT:
				reconnectSocket();
				break;
			default:
				break;
			}
			// showScroNewsMsg();
		} catch (Exception e) {
			LogUtil.e("sendSocketCmd>>>>>>>>>>>>>Exception=" + e);
		}
	}

	/**
	 * 登陆注册动作
	 */
	public void loginRegister() {
		PushMessage message = new PushMessage();
		message.setMsgid(UUID.randomUUID().toString());
		message.setType(Constant.SOCKET_LOGIN_TYPE);
		message.setContent(UserMgr.getUserName());

		Gson gson = new Gson();
		String datas = gson.toJson(message);
		if (null != mInBufferedReader) {
			LogUtil.d("loginRegister------------" + datas);
			mOutPrintWriter.println(datas);
			showScroNewsMsg();

			// 接收到服务器推送的消息时，实时更新主界面未读消息数
			// H_MainMenuActivity.refreshMsgNumber();
		} else {
			LogUtil.d("loginRegister-------->none");
		}
	}

	/**
	 * 重连sokect动作
	 */
	public void reconnectSocket() {
		reconnect(mSocketHandler, SystemMgr.getMsgServerIp(),
				SystemMgr.getMsgServerPort());
	}

	/**
	 * 堵塞模式读取网络下发的包
	 */
	public void receivePackage() {
		ThreadPoolManager.getInstance().addTask(new Runnable() {

			@Override
			public void run() {
				try {
					String result = null;
					while (null != mSocket && !mSocket.isClosed()) {
						if (null != mInBufferedReader
								&& (result = mInBufferedReader.readLine()) != null) {
							if (isCorrectContentFormat(result)) {
								Gson gson = new Gson();
								PushMessage pushMessage = gson.fromJson(result,
										PushMessage.class);
								Message msg = new Message();
								msg.obj = pushMessage;
								msg.what = Constant.IPTV_MSG_SOCKET_RECEIVE_ACTION;
								mSocketHandler.sendMessage(msg);
							} else if ("0".equalsIgnoreCase(result)) {
							} else {
							}
						} else {
							LogUtil.e(">>>>>>>>>>>>>readBuffer's content is null");
							break;
						}
					}
				} catch (Exception e) {
					LogUtil.e(">>>>>>>>>>>>>Exception=" + e);
					mSocketHandler
							.removeMessages(Constant.IPTV_MSG_SOCKET_HEART_CHECK);
					// 发送重连消息进行重连动作
					mSocketHandler
							.removeMessages(Constant.IPTV_MSG_SOCKET_SEND_RECONNECT_ACTION);
					mSocketHandler.sendEmptyMessageDelayed(
							Constant.IPTV_MSG_SOCKET_SEND_RECONNECT_ACTION,
							Constant.IPTV_TIME_TWENTY_SECONDS);

					return;
				}
				LogUtil.e(">>>>>>>>>>>>>Exception not be catched!");
				mSocketHandler
						.removeMessages(Constant.IPTV_MSG_SOCKET_HEART_CHECK);
				// 发送重连消息进行重连动作
				mSocketHandler
						.removeMessages(Constant.IPTV_MSG_SOCKET_SEND_RECONNECT_ACTION);
				mSocketHandler.sendEmptyMessageDelayed(
						Constant.IPTV_MSG_SOCKET_SEND_RECONNECT_ACTION,
						Constant.IPTV_TIME_TWENTY_SECONDS);
			}
		});
	}

	/**
	 * 发送sockect内容
	 * 
	 * @param contents
	 */
	public void sendPackage(String contents) {
		if (null != mOutPrintWriter) {
			mOutPrintWriter.println(contents);
		}
	}

	/**
	 * 获取未读消息进行滚动显示
	 */
	public void showScroNewsMsg() {
		String iptvUserName = UserMgr.getUserName();
		UIDataller.getDataller().getUnreadNewsMsg(iptvUserName, 1, 1000,
				new INewOnSuccessCB() {

					@Override
					public void onSuccess(NewsDataItems newsDataItems) {
						if (null != newsDataItems
								&& newsDataItems.result.size() > 0) {
							LogUtil.d("showScroNewsMsg----------" + newsDataItems.result);
							// 过滤消息
							MsgPushMgr msgPushMgr = MsgPushMgr.getMsgMgrInstance();
							msgPushMgr.initParam();
							msgPushMgr.showView(newsDataItems);
						}
					}
				});
	}

	/**
	 * 把消息设置为已读
	 * 
	 * @param id
	 */
	private void setRead(int id) {
		UIDataller.getDataller().setNewsReadedTag(UserMgr.getUserName(), id,
				new INewOnSuccessCB() {

					@Override
					public void onSuccess(NewsDataItems newsDatas) {

					}
				});
	}

	/**
	 * 关闭socket动作
	 */
	public void closeSocket() {
		if (null != mSocket) {
			try {
				if (!mSocket.isClosed() && mSocket.isConnected()) {
					mOutPrintWriter.close();
					mInBufferedReader.close();
					mOutPrintWriter = null;
					mInBufferedReader = null;
				}
				if (!mSocket.isClosed()) {
					mSocket.close();
					mSocket = null;
				}
			} catch (Exception e) {
				LogUtil.e("closeSocket>>>>>>>>>>>>>Exception=" + e);
			}
		}
	}

	/**
	 * 检测sokect是否处于关闭状态
	 * 
	 * @return
	 */
	public boolean isClosed() {
		if (null != mSocket) {
			return mSocket.isClosed();
		} else {
			return false;
		}
	}

	/**
	 * 检测sokect是否处于连接状态
	 * 
	 * @return
	 */
	public boolean isConnected() {
		if (null != mSocket) {
			return mSocket.isConnected();
		} else {
			return false;
		}
	}

	public boolean isCorrectContentFormat(String contents) {
		boolean result = false;
		if (!StringUtil.isEmpty(contents)) {
			if (contents.contains("msgid") && contents.contains("type")
					&& contents.contains("content")) {
				result = true;
			}
		}

		return result;
	}
}

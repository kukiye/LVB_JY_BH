/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 *
 * @author Johnson
 * 2014年3月19日 上午10:12:56
 */
package com.hhzt.iptv.lvb_x.services;

import com.google.gson.Gson;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.floatwindows.FloatWindowManager;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.MediaControllerMgr;
import com.hhzt.iptv.lvb_x.mgr.PowerMgr;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.model.DeviceInfoBean;
import com.hhzt.iptv.lvb_x.model.ForcePlayBean;
import com.hhzt.iptv.lvb_x.model.PushMessage;
import com.hhzt.iptv.lvb_x.net.MsgSocket;
import com.hhzt.iptv.ui.MainmenuActivity;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

public class MsgReceiveService extends Service {
	private MsgSocket mMsgSocket = null;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		deInitMsgService();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		deInitMsgService();
		initMsgService();
		return super.onStartCommand(intent, flags, startId);
	}

	private void initMsgService() {
		new Thread() {

			@Override
			public void run() {
				super.run();
				if (null == mMsgSocket) {
					// 初始化socket参数
					mMsgSocket = MsgSocket.getInstance(
							SystemMgr.getMsgServerIp(),
							SystemMgr.getMsgServerPort(), mSocketMsgHandler);
					LogUtil.d("initMsgService---------ServerIp="
							+ SystemMgr.getMsgServerIp() + "port="
							+ SystemMgr.getMsgServerPort());
					if (null != mMsgSocket && !mMsgSocket.isClosed()
							&& mMsgSocket.isConnected()) {
						// 开启sokect数据接收动作
						mSocketMsgHandler
								.sendEmptyMessage(Constant.IPTV_MSG_SOCKET_RECEIVE_CHECK);
						// 注册sokect登录动作
						mSocketMsgHandler
								.sendEmptyMessage(Constant.IPTV_MSG_SOCKET_SEND_LOGIN_ACTION);
						// 开启告知发送心跳包指令
						mSocketMsgHandler
								.sendEmptyMessage(Constant.IPTV_MSG_SOCKET_HEART_CHECK);
					}
				} else {
					mMsgSocket.reconnectSocket();
				}
			}

		}.start();
	}

	private void deInitMsgService() {
		removeAllMessages();
		if (null != mMsgSocket) {
			mMsgSocket.closeSocket();
		}
	}

	/**
	 * 删除队列里面所有的消息
	 */
	public void removeAllMessages() {
		if (null != mSocketMsgHandler) {
			mSocketMsgHandler
					.removeMessages(Constant.IPTV_MSG_SOCKET_SEND_LOGIN_ACTION);
			mSocketMsgHandler
					.removeMessages(Constant.IPTV_MSG_SOCKET_SEND_RECONNECT_ACTION);
			mSocketMsgHandler
					.removeMessages(Constant.IPTV_MSG_SOCKET_RECEIVE_ACTION);
			mSocketMsgHandler
					.removeMessages(Constant.IPTV_MSG_SOCKET_RECEIVE_CHECK);
			mSocketMsgHandler
					.removeMessages(Constant.IPTV_MSG_SOCKET_HEART_CHECK);
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler mSocketMsgHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.IPTV_MSG_SOCKET_SEND_LOGIN_ACTION:
				mMsgSocket.sendSocketCmd(Constant.SOKECT_LOGIN);
				break;
			case Constant.IPTV_MSG_SOCKET_SEND_RECONNECT_ACTION:
				LogUtil.d("mSocketMsgHandler---------");
				// 需要重连时将心跳包命令删除，待重连成功后会自动按照相应的频率再次发送心跳包
				removeMessages(Constant.IPTV_MSG_SOCKET_HEART_CHECK);
				mMsgSocket.sendSocketCmd(Constant.SOKECT_RECONNECT);
				break;
			case Constant.IPTV_MSG_SOCKET_RECEIVE_ACTION:
				PushMessage message = (PushMessage) msg.obj;
				String type = message.getType();
				String content = message.getContent();
				LogUtil.d("mSocketMsgHandler-------content " + content
						+ " type=" + type);
				if (Constant.SOCKET_MSG_TYPE.equalsIgnoreCase(type)) {
					mMsgSocket.showScroNewsMsg();
				} else if (Constant.SOCKET_ACK_TYPE.equalsIgnoreCase(type)) {

				} else if (Constant.SOCKET_ACTION_TYPE.equalsIgnoreCase(type)) {
					PowerMgr.getPowerMgrInstance().actionCmdSystem(content);
				} else if (Constant.SOCKET_ROOMSTATUS_TYPE
						.equalsIgnoreCase(type)) {
					Gson gson = new Gson();
					DeviceInfoBean bean = gson.fromJson(content,
							DeviceInfoBean.class);
					if (null != bean) {
						int status = bean.getStatus();
						int tickSecond = bean.getCountdownSecond();
						switch (status) {
						case Constant.DEVICE_NORMAL:
							// 正常状态,将倒计时和锁定界面都消失掉
							FloatWindowManager.getInstance().showNormalWindow();
							break;
						case Constant.DEVICE_TICKER:
							// 倒计时状态
							FloatWindowManager.getInstance().showTickerWindow(
									tickSecond);
							break;
						case Constant.DEVICE_LOCK:
							// 锁定状态
							FloatWindowManager.getInstance().showLockedWindow();
							break;
						default:
							break;
						}
					}
				} else if (Constant.SOCKET_FORCE_PLAY.equalsIgnoreCase(type)) {
					// 解析相应content数据
					if (content != null && !TextUtils.isEmpty(content)) {
						Gson gson = new Gson();
						ForcePlayBean bean = gson.fromJson(content,
								ForcePlayBean.class);
						// 开启定时器进行定时
						MediaControllerMgr.getInstance().startTicker(bean);
					} else {
						UIDataller.getDataller().checkForcePlayMission();
					}
				} else if (Constant.SOCKET_FORCE_FREE.equalsIgnoreCase(type)) {
					UIDataller.getDataller().checkForcePlayMission();
				}
				break;
			case Constant.IPTV_MSG_SOCKET_RECEIVE_CHECK:
				mMsgSocket.receivePackage();

				// 网络恢复时自动重新检测设备状态
				UIDataller.getDataller().initViewTempConfigInfo(null, null, 0);
				break;
			case Constant.IPTV_MSG_SOCKET_HEART_CHECK:
				mMsgSocket.sendPackage("0");
				sendEmptyMessageDelayed(Constant.IPTV_MSG_SOCKET_HEART_CHECK,
						Constant.IPTV_TIME_TWENTY_SECONDS);
				break;
			default:
				break;
			}
		}
	};
}

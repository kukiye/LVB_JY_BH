package com.hhzt.iptv.lvb_x.mgr;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.ClickType;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.RemoteCMDType;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.engine.SoundController;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mediaplayer.LVBMediaplayer;
import com.hhzt.iptv.lvb_x.model.PairAuthorizationModel;
import com.hhzt.iptv.lvb_x.utils.ClickFilter;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.hhzt.iptv.lvb_x.utils.VirturlKeyPadCtr;

public class InteractiveMgr {
	private static final int LISTNER_PORT = 40000;
	private static final int MAX_LENGTH = 1024;

	private boolean isRunning = false;
	private DatagramSocket mUdpSocket = null;

	private String pairFromUserName = null;
	private String pairFromUserIP = null;
	private String pairFromPasswd = null;
	private String pairFromMacAdress = null;

	private static int oldVolumeValue = 0;
	private static boolean isVolumeStoreMode = false;

	private InteractiveMgr() {
	}

	private static final InteractiveMgr single = new InteractiveMgr();

	public static InteractiveMgr getInstance() {
		return single;
	}

	public void init() {
		try {
			isRunning = true;
			pairFromUserIP = null;
			pairFromPasswd = null;
			pairFromUserName = null;
			mUdpSocket = new DatagramSocket(LISTNER_PORT);
			recivRequest();
		} catch (Exception e) {
			LogUtil.e("Exception=" + e);
		}
	}

	public void recivRequest() {
		ThreadPoolManager.getInstance().addTask(new Runnable() {

			@Override
			public void run() {
				while (isRunning) {
					try {
						byte[] recvBuffer = new byte[MAX_LENGTH];
						DatagramPacket dataPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
						mUdpSocket.receive(dataPacket);

						parsePackage(dataPacket);
					} catch (IOException e) {
						if (null != mUdpSocket && !mUdpSocket.isClosed()) {
							mUdpSocket.close();
						}
						mUdpSocket = null;
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void parsePackage(final DatagramPacket dataPacket) {
		ThreadPoolManager.getInstance().addTask(new Runnable() {

			@Override
			public void run() {
				String[] datagramPacketSegment = getDatagramPacketSegment(dataPacket);
				int cmd = Integer.valueOf(datagramPacketSegment[0]);
				switch (cmd) {
				case RemoteCMDType.IPTV_PAIRE_CMD:
					// 101#15754#99065918#192.168.1.15#78:F7:BE:52:61:2A(cmdType#accountName#interactivePasswd#localIpAdress#localMacAddress)
					String title = LVBXApp.getApp().getString(R.string.connect_requst_title);
					String contents = getContents(datagramPacketSegment);

					if (isCorrectPasswd(pairFromPasswd)) {
						Message msg = new Message();
						PairAuthorizationModel authorizationMsg = new PairAuthorizationModel();
						authorizationMsg.setTitle(title);
						authorizationMsg.setContents(contents);
						authorizationMsg.setPairFromUserName(pairFromUserName);
						authorizationMsg.setPairFromUserIP(pairFromUserIP);
						authorizationMsg.setPairFromMacAddr(pairFromMacAdress);

						msg.obj = authorizationMsg;
						msg.what = Constant.IPTV_MSG_AUTHORIZATION_TIPS;

						// 发送广播通知界面进行提示
						UIDataller.getDataller().sendPairTipsBroadcast(msg);
					}
					break;
				case RemoteCMDType.HSJQ_PAD_SEAT_SELECTION:
					// 101#15754#99065918#10#78:F7:BE:52:61:2A(cmdType#accountName#interactivePasswd#macAddress)
					if (datagramPacketSegment.length > 3) {
						String pairedUserAccountName = datagramPacketSegment[1];
						String pairedInteractivePasswd = datagramPacketSegment[2];
						String macAdress = datagramPacketSegment[3];
						if (checkPairedInfoCerrect(pairedUserAccountName, pairedInteractivePasswd, macAdress)) {
							UIDataller.getDataller().sendSeatSelectionBroadcast();
						}
					}
					break;
				case RemoteCMDType.HSJQ_PAD_SEAT_CANCEL:
					// 101#15754#99065918#10#78:F7:BE:52:61:2A(cmdType#accountName#interactivePasswd#macAddress)
					if (datagramPacketSegment.length > 3) {
						String pairedUserAccountName = datagramPacketSegment[1];
						String pairedInteractivePasswd = datagramPacketSegment[2];
						String macAdress = datagramPacketSegment[3];
						if (checkPairedInfoCerrect(pairedUserAccountName, pairedInteractivePasswd, macAdress)) {
							UIDataller.getDataller().sendSeatCancelBroadcast();
						}
					}
					break;
				case RemoteCMDType.PAD_CMD:
					// 101#15754#99065918#10#78:F7:BE:52:61:2A(cmdType#accountName#interactivePasswd#macAddress#keyCodeValue)
					if (datagramPacketSegment.length > 3) {
						String pairedUserAccountName = datagramPacketSegment[1];
						String pairedInteractivePasswd = datagramPacketSegment[2];
						String macAdress = datagramPacketSegment[3];
						String rTCmdCode = datagramPacketSegment[4];
						if (checkPairedInfoCerrect(pairedUserAccountName, pairedInteractivePasswd, macAdress)) {
							SendRTKeyCodeCMDToSystem(rTCmdCode);
						}
					}
					break;
				case RemoteCMDType.MEDIA_LIVE_PUSH:
					if (ClickFilter.checkClick(ClickType.DOUBLE_CLICK)) {
						return;
					}
					// 2000#15754#99065918#192.168.43.249#78:F7:BE:52:61:2A#udp://236.0.0.3:1236#CCTV15#15
					if (datagramPacketSegment.length > 8) {
						String pairedUserAccountName = datagramPacketSegment[1];
						String pairedInteractivePasswd = datagramPacketSegment[2];
						// String pairedFromIp = datagramPacketSegment[3];
						String pairedMacAddr = datagramPacketSegment[4];
						String url = datagramPacketSegment[5];
						String name = datagramPacketSegment[6];
						String channelIndex = datagramPacketSegment[7];
						String channelNum = datagramPacketSegment[8];
						if (checkPairedInfoCerrect(pairedUserAccountName, pairedInteractivePasswd, pairedMacAddr)) {
							if (!StringUtil.isEmpty(url)) {
								LVBMediaplayer.playRemoteIntertiveLive(BaseActivity.getInstance(), url, name, channelIndex, channelNum);
							}
						}
					}
					break;
				case RemoteCMDType.MEDIA_VOD_PUSH:
					if (ClickFilter.checkClick(ClickType.DOUBLE_CLICK)) {
						return;
					}
					// 2001#15754#99065918#192.168.43.249#78:F7:BE:52:61:2A#http://192.168.1.247:9999/vod/催眠大师MKV.mkv#MKV（转码）#0#191#null#MKV格式测试
					if (datagramPacketSegment.length > 10) {
						String pairedUserAccountName = datagramPacketSegment[1];
						String pairedInteractivePasswd = datagramPacketSegment[2];
						// String pairedFromIp = datagramPacketSegment[3];
						String pairedMacAddr = datagramPacketSegment[4];
						String url = datagramPacketSegment[5];
						String name = datagramPacketSegment[6];
						String position = datagramPacketSegment[7];
						String id = datagramPacketSegment[8];
						String price = datagramPacketSegment[9];
						String feeMediaName = datagramPacketSegment[10];
						if (checkPairedInfoCerrect(pairedUserAccountName, pairedInteractivePasswd, pairedMacAddr)) {
							if (!StringUtil.isEmpty(url)) {
								LVBMediaplayer.playRemoteInteractiveVod(BaseActivity.getInstance(), null, url.trim(), name, 0,
										Constant.IPTV_MEDIA_TYPE_VOD, Integer.valueOf(position), Integer.valueOf(id), price, feeMediaName);
							}
						}
					}
					break;
				case RemoteCMDType.MEDIA_AUDIO_PUSH:
					break;
				case RemoteCMDType.MEDIA_IMAGE_PUSH:
					break;
				case RemoteCMDType.MEDIA_LIVE_PULL:
					break;
				case RemoteCMDType.MEDIA_VOD_PULL:
					break;
				case RemoteCMDType.MEDIA_AUDIO_PULL:
					break;
				case RemoteCMDType.VOD_MEDIA_PLAY:
				case RemoteCMDType.VOD_MEDIA_PAUSE:
					// 2007#15754#99065918#192.168.43.249#78:F7:BE:52:61:2A
					if (datagramPacketSegment.length > 4) {
						String pairedUserAccountName = datagramPacketSegment[1];
						String pairedInteractivePasswd = datagramPacketSegment[2];
						// String pairedFromIp = datagramPacketSegment[3];
						String pairedMacAddr = datagramPacketSegment[4];
						if (checkPairedInfoCerrect(pairedUserAccountName, pairedInteractivePasswd, pairedMacAddr)) {
							UIDataller.getDataller().sendVodControllerBroadcast(cmd);
						}
					}
					break;
				case RemoteCMDType.VOD_MEDIA_SEEK:
					if (datagramPacketSegment.length > 11) {
						String pairedUserAccountName = datagramPacketSegment[1];
						String pairedInteractivePasswd = datagramPacketSegment[2];
						// String pairedFromIp = datagramPacketSegment[3];
						String pairedMacAddr = datagramPacketSegment[4];
						String url = datagramPacketSegment[5];
						String name = datagramPacketSegment[6];
						String position = datagramPacketSegment[7];
						String id = datagramPacketSegment[8];
						String price = datagramPacketSegment[9];
						String feeMediaName = datagramPacketSegment[10];
						String progress = datagramPacketSegment[11];
						if (checkPairedInfoCerrect(pairedUserAccountName, pairedInteractivePasswd, pairedMacAddr)) {
							LVBMediaplayer.seekRemoteInteractiveVod(BaseActivity.getInstance(), null, url, name, 0, Constant.IPTV_MEDIA_TYPE_VOD,
									Integer.valueOf(position), Integer.valueOf(id), price, feeMediaName, progress);
						}
					}
					break;
				case RemoteCMDType.VOD_MEDIA_PRE:
				case RemoteCMDType.VOD_MEDIA_NEXT:
					// 2001#15754#99065918#192.168.43.249#78:F7:BE:52:61:2A#http://192.168.1.247:9999/vod/催眠大师MKV.mkv#MKV（转码）#0#191#null#MKV格式测试
					if (datagramPacketSegment.length > 10) {
						String pairedUserAccountName = datagramPacketSegment[1];
						String pairedInteractivePasswd = datagramPacketSegment[2];
						// String pairedFromIp = datagramPacketSegment[3];
						String pairedMacAddr = datagramPacketSegment[4];
						String url = datagramPacketSegment[5];
						String name = datagramPacketSegment[6];
						String position = datagramPacketSegment[7];
						String id = datagramPacketSegment[8];
						String price = datagramPacketSegment[9];
						String feeMediaName = datagramPacketSegment[10];
						if (checkPairedInfoCerrect(pairedUserAccountName, pairedInteractivePasswd, pairedMacAddr)) {
							LVBMediaplayer.playPreNextMediaVod(BaseActivity.getInstance(), null, url, name, 0, Constant.IPTV_MEDIA_TYPE_VOD,
									Integer.valueOf(position), Integer.valueOf(id), price, feeMediaName, cmd);
						}
					}
					break;
				case RemoteCMDType.HSJQ_PAD_SOUND_CHANGE:
					if (datagramPacketSegment.length > 5) {
						String pairedUserAccountName = datagramPacketSegment[1];
						String pairedInteractivePasswd = datagramPacketSegment[2];
						// String pairedFromIp = datagramPacketSegment[3];
						String pairedMacAddr = datagramPacketSegment[4];
						String progress = datagramPacketSegment[5];
						if (checkPairedInfoCerrect(pairedUserAccountName, pairedInteractivePasswd, pairedMacAddr)) {
							SoundController ller = new SoundController(LVBXApp.getApp());
							ller.setStreamVolumeCurrent(Integer.valueOf(progress) * ller.getStreamVolumeMax() / 100);
						}
					}
					break;
				default:
					break;
				}
			}
		});
	}

	public void sendInteractiveRequest(String ipAddress, String contents) {
		LogUtil.d("sendInteractiveRequest--->sendContents=" + contents);
		byte[] data = contents.getBytes();
		byte[] recvBuffer = new byte[MAX_LENGTH];
		DatagramPacket dataPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
		dataPacket.setData(data);
		dataPacket.setLength(data.length);
		dataPacket.setPort(LISTNER_PORT);

		InetAddress sendAddr = null;
		try {
			sendAddr = InetAddress.getByName(ipAddress);

			dataPacket.setAddress(sendAddr);
			mUdpSocket.send(dataPacket);
		} catch (Exception e) {
			LogUtil.e("Exception=" + e);
		}
	}

	private String getContents(String[] datagramPacketSegment) {
		String contentOneTips = LVBXApp.getApp().getString(R.string.connect_content_tag_one);
		// 101#15754#99065918#192.168.1.15#78:F7:BE:52:61:2A(cmdType#accountName#interactivePasswd#localIpAdress#localMacAddress)
		if (datagramPacketSegment.length > 1) {
			pairFromUserName = datagramPacketSegment[1];
		}

		if (datagramPacketSegment.length > 2) {
			pairFromPasswd = datagramPacketSegment[2];
		}

		String fromIPTips = LVBXApp.getApp().getString(R.string.recice_from_ip);
		if (datagramPacketSegment.length > 3) {
			pairFromUserIP = datagramPacketSegment[3];
		}

		if (datagramPacketSegment.length > 4) {
			pairFromMacAdress = datagramPacketSegment[4];
		}

		String contentTwoTips = LVBXApp.getApp().getString(R.string.connect_content_tag_two);

		String contents = contentOneTips + fromIPTips + pairFromUserIP + contentTwoTips;

		return contents;
	}

	private String[] getDatagramPacketSegment(DatagramPacket dataPacket) {
		if (null == dataPacket) {
			return null;
		}

		int length = 0;
		byte[] bytes = dataPacket.getData();
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] == 0) {
				length = i;
				break;
			}
			length = i + 1;
		}
		String recivContent = new String(bytes, 0, length);
		LogUtil.d("getDatagramPacketSegment---->recivContent=" + recivContent);
		String[] reciveString = recivContent.split("#");

		return reciveString;
	}

	private boolean isCorrectPasswd(String recivePasswd) {
		String mySelfDynamicPasswd = UserMgr.getInteracPassword();
		if (null != recivePasswd && recivePasswd.equalsIgnoreCase(mySelfDynamicPasswd)) {
			return true;
		}
		return false;
	}

	public static void SendRTKeyCodeCMDToSystem(String KeyCode) {
		int code = Integer.parseInt(KeyCode, 10);
		int realCode = 0;
		switch (code) {
		case RemoteCMDType.PAD_UP:
			realCode = KeyEvent.KEYCODE_DPAD_UP;
			break;
		case RemoteCMDType.PAD_DOWN:
			realCode = KeyEvent.KEYCODE_DPAD_DOWN;
			break;
		case RemoteCMDType.PAD_LEFT:
			realCode = KeyEvent.KEYCODE_DPAD_LEFT;
			break;
		case RemoteCMDType.PAD_RIGHT:
			realCode = KeyEvent.KEYCODE_DPAD_RIGHT;
			break;
		case RemoteCMDType.PAD_OK:
			realCode = KeyEvent.KEYCODE_DPAD_CENTER;
			break;
		case RemoteCMDType.PAD_VOLUME_ADD:
			realCode = KeyEvent.KEYCODE_VOLUME_UP;
			break;
		case RemoteCMDType.PAD_VOLUME_REDUCE:
			realCode = KeyEvent.KEYCODE_VOLUME_DOWN;
			break;
		case RemoteCMDType.PAD_VOLUME_MUTE:
			changeVolumeSlient();
			return;
		case RemoteCMDType.PAD_MENU:
			realCode = KeyEvent.KEYCODE_MENU;
			break;
		case RemoteCMDType.PAD_DELETE:
			realCode = KeyEvent.KEYCODE_DEL;
			break;
		case RemoteCMDType.PAD_BACK:
			realCode = KeyEvent.KEYCODE_BACK;
			break;
		case RemoteCMDType.PAD_NUM0:
			realCode = KeyEvent.KEYCODE_0;
			break;
		case RemoteCMDType.PAD_NUM1:
			realCode = KeyEvent.KEYCODE_1;
			break;
		case RemoteCMDType.PAD_NUM2:
			realCode = KeyEvent.KEYCODE_2;
			break;
		case RemoteCMDType.PAD_NUM3:
			realCode = KeyEvent.KEYCODE_3;
			break;
		case RemoteCMDType.PAD_NUM4:
			realCode = KeyEvent.KEYCODE_4;
			break;
		case RemoteCMDType.PAD_NUM5:
			realCode = KeyEvent.KEYCODE_5;
			break;
		case RemoteCMDType.PAD_NUM6:
			realCode = KeyEvent.KEYCODE_6;
			break;
		case RemoteCMDType.PAD_NUM7:
			realCode = KeyEvent.KEYCODE_7;
			break;
		case RemoteCMDType.PAD_NUM8:
			realCode = KeyEvent.KEYCODE_8;
			break;
		case RemoteCMDType.PAD_NUM9:
			realCode = KeyEvent.KEYCODE_9;
			break;
		case RemoteCMDType.PAD_POUND:
			realCode = KeyEvent.KEYCODE_POUND;
			break;
		case RemoteCMDType.PAD_STAR:
			realCode = KeyEvent.KEYCODE_STAR;
			break;
		default:
			break;
		}
		VirturlKeyPadCtr.simulateKeystroke(realCode);
	}

	private static void changeVolumeSlient() {
		SoundController soundController = new SoundController(LVBXApp.getApp());
		if (!isVolumeStoreMode) {
			isVolumeStoreMode = true;

			oldVolumeValue = soundController.getStreamVolumeCurrent();
			soundController.setStreamVolumeCurrent(0);

			final String soundModeTips = LVBXApp.getApp().getString(R.string.goto_sound_silient_mode);
			ThreadPoolManager.getInstance().addTask(new Runnable() {

				@Override
				public void run() {
					Looper.prepare();
					BaseActivity.getInstance().showToast(soundModeTips, Toast.LENGTH_LONG);
					Looper.loop();
				}
			});
		} else {
			isVolumeStoreMode = false;

			soundController.setStreamVolumeCurrent(oldVolumeValue);

			final String soundModeTips = LVBXApp.getApp().getString(R.string.goto_sound_normal_mode);
			ThreadPoolManager.getInstance().addTask(new Runnable() {

				@Override
				public void run() {
					Looper.prepare();
					BaseActivity.getInstance().showToast(soundModeTips, Toast.LENGTH_LONG);
					Looper.loop();
				}
			});
		}
	}

	private boolean checkPairedInfoCerrect(String userName, String interactivePasswd, String macAdress) {
		if (checkPairedAccountIsCorrect(userName) && checkPairedInteractivePasswdIsCorrect(interactivePasswd)
				&& checkPariedMacAdressIsCorrect(macAdress)) {
			return true;
		}
		return false;
	}

	private boolean checkPairedAccountIsCorrect(String userName) {
		String pairedUserName = UserMgr.getUserName();
		if (null != userName && userName.equalsIgnoreCase(pairedUserName)) {
			return true;
		}
		return false;
	}

	private boolean checkPairedInteractivePasswdIsCorrect(String interactivePasswd) {
		String pairedPasswd = UserMgr.getInteracPassword();
		if (null != interactivePasswd && interactivePasswd.equalsIgnoreCase(pairedPasswd)) {
			return true;
		}
		return false;
	}

	private boolean checkPariedMacAdressIsCorrect(String macAddress) {
		String savedPairedMacAddress = UserMgr.getPairedMacAdress();
		if (null != savedPairedMacAddress && savedPairedMacAddress.equalsIgnoreCase(macAddress)) {
			return true;
		}

		return false;
	}

	public void deInit() {
		if (null != mUdpSocket) {
			isRunning = false;
			mUdpSocket.close();
			mUdpSocket = null;
		}
	}

}

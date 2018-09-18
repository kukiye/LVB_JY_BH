package com.hhzt.iptv.lvb_x.net;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.AuthorithMgr;
import com.hhzt.iptv.lvb_x.mgr.ThreadPoolManager;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.rootmanager.RootManager;
import com.hhzt.iptv.lvb_x.utils.ApkUtil;
import com.hhzt.iptv.lvb_x.utils.FileUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

@SuppressLint("HandlerLeak")
public class LVBDownloadManager {

	private Context mContext;
	private Handler mHandler;

	public LVBDownloadManager(Context context, Handler handler) {
		this.mContext = context;
		this.mHandler = handler;
	}

	/**
	 * 下载apk文件
	 * 
	 * @param url
	 */
	public void downloadAPPApk(final String netApkUrl) {
		try {
			checkApkIsExist(netApkUrl);
		} catch (Exception e) {
			LogUtil.e("Exception=" + e);
		}
	}

	/**
	 * 全屏界面现在和安装方式进行下载和安装
	 * 
	 * @param url
	 * @param existFlag
	 * @param handler
	 */
	public void startSmartDownloadAndInstall(final String url, boolean existFlag, final Handler handler) {
		// 如果已经下载，则直接进行安装
		if (existFlag) {
			ThreadPoolManager.getInstance().addTask(new Runnable() {

				@Override
				public void run() {
					// 发送开始安装消息
					handler.sendEmptyMessage(Constant.IPTV_MSG_UPDATE_INSTALLING);

					// 根据url获取apk存放路径
					File file = getAPPApkPath(url);

					// 1.静默安装动作、2.发送完成安装消息
					Message msg = new Message();
					msg.obj = RootManager.getInstance().installPackage(file.getAbsolutePath());
					msg.what = Constant.IPTV_MSG_UPDATE_INSTALL_FINISH;
					handler.sendMessage(msg);
				}
			});
		} else {
			// 如果没有下载，则首先进行下载再进行安装
			ThreadPoolManager.getInstance().addTask(new Runnable() {

				@Override
				public void run() {
					try {
						// 从服务器下载apk
						String filepath = getFileFromServer(url, handler);
						// 发送下载完成消息给主线程更新显示状态
						handler.sendEmptyMessage(Constant.IPTV_MSG_UPDATE_DOWNLOAD_SUCCESS);
						// 1.静默安装动作、2.发送完成安装消息
						Message msg = new Message();
						msg.obj = RootManager.getInstance().installPackage(filepath);
						msg.what = Constant.IPTV_MSG_UPDATE_INSTALL_FINISH;
						handler.sendMessage(msg);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	/**
	 * 带Dialog的方式进行下载和安装
	 * 
	 * @param url
	 * @param flag
	 */
	public void installOrDownloadApk(final String url, boolean flag) {
		if (flag) {
			new AlertDialog.Builder(mContext).setTitle(R.string.app_install).setMessage(R.string.app_apk_isexist).setCancelable(true)
					.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							// 下载新APK
							File file = getAPPApkPath(url);
							// 使用如下方式对文件夹chang mode, 会报错, 可能的原因是:设备没有ROOT权限
							// String command = "chmod 777 " +
							// file.getAbsolutePath();
							// AuthorithMgr.getInstance().RootCmd(command);
							AuthorithMgr.getInstance().changeMode(file.getAbsolutePath());

							ApkUtil.installApk(mContext, file);
						}
					}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					}).show();
		} else {
			final ProgressDialog pd; // 进度条对话框
			pd = new ProgressDialog(mContext);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setCancelable(true);
			pd.setMessage(mContext.getString(R.string.app_downloading));
			pd.show();
			new Thread() {
				String filepath = null;
				File file = null;

				@Override
				public void run() {
					try {
						sleep(3000);
						filepath = getFileFromServer(url, pd);
						file = new File(filepath);
						sleep(3000);
						// 使用如下方式对文件夹chang mode, 会报错, 可能的原因是:设备没有ROOT权限
						// String command = "chmod 777 " +
						// file.getAbsolutePath();
						// AuthorithMgr.getInstance().RootCmd(command);
						AuthorithMgr.getInstance().changeMode(file.getAbsolutePath());

						ApkUtil.installApk(mContext, file);
						pd.dismiss(); // 结束掉进度条对话框
					} catch (Exception e) {
						Message msg = new Message();
						msg.what = Constant.IPTV_MSG_UPDATE_DOWNLOAD_FAILED;
						pd.dismiss();
						mHandler.sendMessage(msg);
						LogUtil.e("Exception=" + e);
					}
				}
			}.start();
		}
	}

	/**
	 * 从服务器获取apk文件--带Dialog的方式显示下载进度
	 * 
	 * @param path
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String getFileFromServer(String path, ProgressDialog pd) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		HttpEntity entity = null;
		RandomAccessFile rafile = null;
		String targetFilepath = null;
		String filename = path.substring(path.lastIndexOf(File.separator));

		String clientStoragePath = UrlMgr.getClientStoragePath();
		// 下载的更新文件存在的路径
		File dir = new File(clientStoragePath);

		/* 判断文件夹是否存在，不存在则创建 */
		if (!dir.exists()) {
			FileUtils.makeDir(dir);
		}
		rafile = new RandomAccessFile(dir + filename, "rw");
		targetFilepath = dir + filename;

		byte[] buf = new byte[1024];
		HttpGet httpGet = new HttpGet(path);
		if (rafile.length() != 0) {
			// 线程中不能直接操作View属性，导致升级失败问题
			// pd.setMessage(mContext.getString(R.string.app_downloading_continue));
		}
		httpGet.addHeader("Range", "bytes=" + rafile.length() + "-");
		httpResponse = httpClient.execute(httpGet);
		entity = httpResponse.getEntity();

		int len = (int) entity.getContentLength();
		pd.setMax((int) (len + rafile.length()));
		rafile.seek(rafile.length());

		if (entity != null) {
			InputStream is = entity.getContent();
			int numRead = 0;
			int total = (int) rafile.length();
			while ((numRead = is.read(buf)) != -1) {
				rafile.write(buf, 0, numRead);
				total += numRead;
				// 获取当前下载量
				pd.setProgress(total);
			}
			is.close();
		}
		rafile.close();
		return targetFilepath;
	}

	/**
	 * 从服务器获取apk文件--以消息发送的方式更新下载进度
	 * 
	 * @param path
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	public String getFileFromServer(String path, Handler handler) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		HttpEntity entity = null;
		RandomAccessFile rafile = null;
		String targetFilepath = null;
		String filename = path.substring(path.lastIndexOf(File.separator));

		String clientStoragePath = UrlMgr.getClientStoragePath();
		// 下载的更新文件存在的路径
		File dir = new File(clientStoragePath);

		/* 判断文件夹是否存在，不存在则创建 */
		if (!dir.exists()) {
			FileUtils.makeDir(dir);
		}
		rafile = new RandomAccessFile(dir + filename, "rw");
		targetFilepath = dir + filename;

		byte[] buf = new byte[1024];
		HttpGet httpGet = new HttpGet(path);
		httpGet.addHeader("Range", "bytes=" + rafile.length() + "-");
		httpResponse = httpClient.execute(httpGet);
		entity = httpResponse.getEntity();

		rafile.seek(rafile.length());

		if (entity != null) {
			InputStream is = entity.getContent();
			int numRead = 0;
			int total = (int) rafile.length();
			while ((numRead = is.read(buf)) != -1) {
				rafile.write(buf, 0, numRead);
				total += numRead;

				// 更新当前下载量
				Message message = new Message();
				message.what = Constant.IPTV_MSG_UPDATE_DOWNLOADING;
				message.arg1 = total / 1024;
				handler.sendMessage(message);
			}
			is.close();
		}
		rafile.close();
		return targetFilepath;
	}

	/**
	 * 判断apk文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public void checkApkIsExist(final String netApkUrl) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse httpResponse = null;
				HttpEntity entity = null;
				File file = null;
				boolean flag = false;
				file = getAPPApkPath(netApkUrl);
				HttpGet httpGet = new HttpGet(netApkUrl);
				if (file.length() != 0) {
					httpGet.addHeader("Range", "bytes=" + 0 + "-");
					try {
						httpResponse = httpClient.execute(httpGet);
					} catch (Exception e) {
						LogUtil.e("Exception=" + e);
					}
					entity = httpResponse.getEntity();
					if (file.length() == (int) entity.getContentLength()) {
						flag = true;
					} else {
						flag = false;
					}
				}
				Message message = Message.obtain();
				Bundle bundle = new Bundle();
				message.what = Constant.IPTV_MSG_APP_EXIST_TAG;
				bundle.putBoolean(Constant.IPTV_SYS_MSG_APK_EXIST_FLAG, flag);
				bundle.putString(Constant.IPTV_SYS_MSG_APK_DOWNLOAD_URL, netApkUrl);
				message.setData(bundle);
				mHandler.sendMessage(message);
			}
		}).start();
	}

	/**
	 * 获取apk文件的路径
	 * 
	 * @param path
	 * @return
	 */
	public File getAPPApkPath(String netApkUrl) {

		String filename = netApkUrl.substring(netApkUrl.lastIndexOf("/"));
		String clientStoragePath = UrlMgr.getClientStoragePath();

		// 下载的更新文件存在的路径
		File dir = new File(clientStoragePath);

		/* 判断文件夹是否存在，不存在则创建 */
		if (!dir.exists()) {
			FileUtils.makeDir(dir);
		}

		File file = new File(dir + filename);
		return file;
	}
}

package com.hhzt.iptv.lvb_x.net;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;

import com.hhzt.iptv.lvb_x.interfaces.IBeanOnSuccessCB;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.utils.FileUtils;

public class BookDownloadManager {

	/**
	 * 从服务器获取apk文件--带Dialog的方式显示下载进度
	 * 
	 * @param path
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String getFileFromServer(String path, ProgressDialog pd)
			throws Exception {
		LogUtil.d("getFileFromServer---------" + path);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		HttpEntity entity = null;
		RandomAccessFile rafile = null;
		String targetFilepath = null;
		String filename = path.substring(path.lastIndexOf(File.separator));

		String clientStoragePath = UrlMgr.getClientStorageBookPath();
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
		if (pd != null) {
			pd.setMax((int) (len + rafile.length()));
		}
		rafile.seek(rafile.length());

		if (entity != null) {
			InputStream is = entity.getContent();
			int numRead = 0;
			int total = (int) rafile.length();
			while ((numRead = is.read(buf)) != -1) {
				rafile.write(buf, 0, numRead);
				total += numRead;
				// 获取当前下载量
				if (pd != null) {
					pd.setProgress(total);
				}
			}
			is.close();
		}
		rafile.close();
		return targetFilepath;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param netApkUrl
	 */
	public void checkIsExistAndDownload(final String netApkUrl,
			final IBeanOnSuccessCB<String> successCB) {
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
				if (flag) {
					successCB.onSuccess(file.getAbsolutePath());
				} else {
					try {
						String getFile = getFileFromServer(netApkUrl, null);
						successCB.onSuccess(getFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public File getAPPApkPath(String netApkUrl) {

		String filename = netApkUrl.substring(netApkUrl.lastIndexOf("/"));
		String clientStoragePath = UrlMgr.getClientStorageBookPath();

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

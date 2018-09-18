/**
 * 作者：   Johnson
 * 日期：   2014年6月17日下午5:56:07
 * 包名：    com.hhzt.iptv.lvb_x.utils
 * 工程名：LVB_X
 * 文件名：BitmapHelp.java
 */
package com.hhzt.iptv.lvb_x.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.hhzt.iptv.lvb_x.LVBXApp;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class BitmapUtil {

	private BitmapUtil() {
	}

	private static BitmapUtils bitmapUtils;

	/**
	 * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
	 * 
	 * @param appContext
	 *            application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils(Context appContext) {
		if (bitmapUtils == null) {
			bitmapUtils = new BitmapUtils(appContext);
		}
		return bitmapUtils;
	}

	public static void setRandomImage(String url, ImageView... views) {
		for (int i = 0; i < views.length; i++) {
			LVBXApp.getImageLoader().displayImage(url, views[i], LVBXApp.getFadeInOptions(), new SimpleImageLoadingListener() {

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					super.onLoadingComplete(imageUri, view, loadedImage);
					CCAnimationUtils.setRandomAnimation(view);
				}

			}, new ImageLoadingProgressListener() {

				@Override
				public void onProgressUpdate(String imageUri, View view, int current, int total) {

				}
			});
		}
	}

	public static void setImage(String url, ImageView... views) {
		for (int i = 0; i < views.length; i++) {
			LVBXApp.getImageLoader().displayImage(url, views[i], LVBXApp.getOptions(), new SimpleImageLoadingListener(),
					new ImageLoadingProgressListener() {

						@Override
						public void onProgressUpdate(String imageUri, View view, int current, int total) {

						}
					});
		}
	}

	public static void setFadeInImage(String url, ImageView... views) {
		for (int i = 0; i < views.length; i++) {
			LVBXApp.getImageLoader().displayImage(url, views[i], LVBXApp.getFadeInOptions(), new SimpleImageLoadingListener(),
					new ImageLoadingProgressListener() {

						@Override
						public void onProgressUpdate(String imageUri, View view, int current, int total) {

						}
					});
		}
	}

	public static void setRoundImage(String url, ImageView... views) {
		for (int i = 0; i < views.length; i++) {
			LVBXApp.getImageLoader().displayImage(url, views[i], LVBXApp.getRoundOptions(), new SimpleImageLoadingListener(),
					new ImageLoadingProgressListener() {

						@Override
						public void onProgressUpdate(String imageUri, View view, int current, int total) {

						}
					});
		}
	}

	public static void setRoundFadeInImage(String url, ImageView... views) {
		for (int i = 0; i < views.length; i++) {
			LVBXApp.getImageLoader().displayImage(url, views[i], LVBXApp.getRoundOptions(), new SimpleImageLoadingListener() {

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					super.onLoadingComplete(imageUri, view, loadedImage);
					FadeInBitmapDisplayer.animate(view, 1000);
				}

			}, new ImageLoadingProgressListener() {

				@Override
				public void onProgressUpdate(String imageUri, View view, int current, int total) {

				}
			});
		}
	}
}

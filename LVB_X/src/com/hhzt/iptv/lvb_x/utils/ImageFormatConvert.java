package com.hhzt.iptv.lvb_x.utils;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 图片资源转化工具类
 * 
 * @author Administrator
 * 
 */
public class ImageFormatConvert {
	// Bitmap转换成Drawable
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);

		return bd;
	}

	// Drawable转换成Bitmap
	public static Bitmap drawable2Bitmap(Drawable db) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) db;
		Bitmap bm = bitmapDrawable.getBitmap();

		return bm;
	}

	// 从资源中获取Bitmap
	public static Bitmap getBitmapFromResources(Activity act, int resId) {
		Resources res = act.getResources();
		return BitmapFactory.decodeResource(res, resId);
	}

	// byte[] → Bitmap
	public static Bitmap convertBytes2Bitmap(byte[] b) {
		if (b.length == 0) {
			return null;
		}
		return BitmapFactory.decodeByteArray(b, 0, b.length);
	}

	// Bitmap → byte[]
	public static byte[] convertBitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	// byte[] → Drawable
	public static Drawable convertBytes2Drawable(byte[] b) {
		Bitmap bitmap = convertBytes2Bitmap(b);
		return bitmap2Drawable(bitmap);
	}

	// Drawable → byte[]
	public static byte[] convertDrawable2Bytes(Drawable bm) {
		Bitmap bitmap = drawable2Bitmap(bm);
		return convertBitmap2Bytes(bitmap);
	}

}

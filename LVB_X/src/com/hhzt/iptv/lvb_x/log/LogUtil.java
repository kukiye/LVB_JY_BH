package com.hhzt.iptv.lvb_x.log;

import com.hhzt.iptv.lvb_x.config.CCVersionConfig;

/**
 * Author: Supermanzc Project: Sunrise Package:
 * com.sunrise.smartwatch.framework.utils Time: 2015/8/4 15:02 日志打印类
 */
public class LogUtil {
	public static final boolean isDebug = CCVersionConfig.DEBUG_MODE;

	public static final String TAG = CCVersionConfig.DEBUG_TAG;

	private static String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (st.getClassName().equals(LogUtil.class.getName())) {
				continue;
			}

			return "[" + Thread.currentThread().getName() + "(" + Thread.currentThread().getId()
					+ "): " + st.getFileName() + ":" + st.getLineNumber() + "]";
		}
		return null;
	}

	private static String createMessage(String msg) {
		String functionName = getFunctionName();
		String message = (functionName == null ? msg : (functionName + " - " + msg));
		return message;
	}

	public static void v(String msg) {
		if (isDebug) {
			String message = createMessage(msg);
			android.util.Log.v(TAG, message);
		}
	}

	public static void v(String msg, Throwable t) {
		if (isDebug) {
			String message = createMessage(msg);
			android.util.Log.v(TAG, message, t);
		}
	}

	public static void d(String msg) {
		if (isDebug) {
			String message = createMessage(msg);
			android.util.Log.d(TAG, message);
		}
	}

	public static void d(String msg, Throwable t) {
		if (isDebug) {
			String message = createMessage(msg);
			android.util.Log.d(TAG, message, t);
		}
	}

	public static void i(String msg) {
		if (isDebug) {
			String message = createMessage(msg);
			android.util.Log.i(TAG, message);
		}
	}

	public static void i(String msg, Throwable t) {
		if (isDebug) {
			String message = createMessage(msg);
			android.util.Log.i(TAG, message, t);
		}
	}

	public static void w(String msg) {
		if (isDebug) {
			String message = createMessage(msg);
			android.util.Log.w(TAG, message);
		}
	}

	public static void w(String msg, Throwable t) {
		if (isDebug) {
			String message = createMessage(msg);
			android.util.Log.w(TAG, message, t);
		}
	}

	public static void e(String msg) {
		if (isDebug) {
			String message = createMessage(msg);
			android.util.Log.e(TAG, message);
		}
	}

	public static void e(String msg, Throwable t) {
		if (isDebug) {
			String message = createMessage(msg);
			android.util.Log.e(TAG, message, t);
		}
	}
}

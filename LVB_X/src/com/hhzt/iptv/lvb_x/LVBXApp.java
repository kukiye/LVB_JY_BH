/**
 * 作者：   Johnson
 * 日期：   2014年6月11日下午2:37:31
 * 包名：    com.hhzt.iptv_lvb_x
 * 工程名：LVB_X
 * 文件名：LVBApp.java
 */
package com.hhzt.iptv.lvb_x;

import com.hhzt.iptv.lvb_x.config.CCChannelConfig;
import com.hhzt.iptv.lvb_x.config.CCClientConfig;
import com.hhzt.iptv.lvb_x.config.CCVersionConfig;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.utils.CommonUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class LVBXApp extends Application {
	// Application实例
	private static LVBXApp mApp;
	// 屏幕宽、高
	private static int mScreenWidth;
	private static int mScreenHeight;
	// 屏幕密度
	private static float mScreenDensity;
	// 应用图片的基准分辨率
	public static final float S_MBASE_SCREENWIDTH = 1280;
	public static final float S_MBASE_SCREENHEIGHT = 720;
	// 图片加载相关类
	private static ImageLoader mImageLoader;
	private static DisplayImageOptions mOptions;
	private static DisplayImageOptions mFadeInOptions;
	private static DisplayImageOptions mRoundOptions;
	private WindowManager mWindowManager;
	
	private static boolean gotoLHService=true;

	public static DisplayImageOptions getOptions() {
		return mOptions;
	}

	public static DisplayImageOptions getFadeInOptions() {
		return mFadeInOptions;
	}

	public static DisplayImageOptions getRoundOptions() {
		return mRoundOptions;
	}

	public static ImageLoader getImageLoader() {
		return mImageLoader;
	}

	@Override
	public void onCreate() {
		// if (Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >=
		// Build.VERSION_CODES.GINGERBREAD) {
		// StrictMode.setThreadPolicy(new
		// StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
		// StrictMode.setVmPolicy(new
		// StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		// }
		super.onCreate();
		setApp(this);
		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		initScreenInfo();
		initBuglyConfigration();
		initImageLoadConfiguration();
	}

	public WindowManager getWindowManager() {
		return mWindowManager;
	}

	public void setWindowManager(WindowManager mWindowManager) {
		this.mWindowManager = mWindowManager;
	}

	public static LVBXApp getApp() {
		return mApp;
	}

	public static void setApp(LVBXApp app) {
		mApp = app;
	}

	public static int getmScreenWidth() {
		return mScreenWidth;
	}

	public static void setmScreenWidth(int screenWidth) {
		mScreenWidth = screenWidth;
	}

	public static int getmScreenHeight() {
		return mScreenHeight;
	}

	public static void setmScreenHeight(int screenHeight) {
		mScreenHeight = screenHeight;
	}

	public static float getmScreenDensity() {
		return mScreenDensity;
	}

	public static void setmScreenDensity(float screenDensity) {
		mScreenDensity = screenDensity;
	}
	
	public static boolean getServiceFlag() {
		return gotoLHService;
	}

	public static void setServiceFlag(boolean flag) {
		gotoLHService = flag;
	}

	/**
	 * 初始化屏幕基准信息
	 */
	public void initScreenInfo() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		int screenWidth = dm.widthPixels; // 屏幕宽（像素，如：1280px）
		int screenHeight = dm.heightPixels; // 屏幕高（像素，如：720px）
		setmScreenDensity(density);
		setmScreenWidth(screenWidth);
		setmScreenHeight(screenHeight);
	}

	private void initImageLoadConfiguration() {
		mOptions = new DisplayImageOptions.Builder().showImageOnLoading(null).showImageForEmptyUri(null).showImageOnFail(null).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.ARGB_8888).build();
		mFadeInOptions = new DisplayImageOptions.Builder().showImageOnLoading(null).showImageForEmptyUri(null).showImageOnFail(null)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.ARGB_8888)
				.displayer(new FadeInBitmapDisplayer(2000)).build();
		mRoundOptions = new DisplayImageOptions.Builder().showImageOnLoading(null).showImageForEmptyUri(null).showImageOnFail(null)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.ARGB_8888)
				.displayer(new RoundedBitmapDisplayer(20)).build();
		mImageLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		mImageLoader.init(configuration);
	}

	private void initBuglyConfigration() {
		if (CCVersionConfig.DEBUG_MODE) {
			System.loadLibrary("Bugly");
			UserStrategy customStrategy = new UserStrategy(this);
			customStrategy.setAppChannel(CCChannelConfig.LVB_X_HOTEL);
			customStrategy.setAppVersion(CommonUtil.getVersionName() + "." + CommonUtil.getVersionCode());
			CrashReport.initCrashReport(getApplicationContext(), Config.APP_ID, false, customStrategy);
			CrashReport.setUserId(CCClientConfig.LVB_X_MAIN);
		}
	}
}

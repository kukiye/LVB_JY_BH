package com.hhzt.iptv.lvb_x.customview;

import java.io.File;
import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.mgr.AuthorithMgr;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.model.AppInfoModel;
import com.hhzt.iptv.lvb_x.model.AppItemMessageModel;
import com.hhzt.iptv.lvb_x.utils.ApkUtil;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.hhzt.iptv.lvb_x.utils.download.DownloadInfo;
import com.hhzt.iptv.lvb_x.utils.download.DownloadManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnFocusChange;

public class AppItemSingle extends LinearLayout {

	private Context mContext;
	@ViewInject(R.id.app_detail_item_img)
	private ImageView mAppPicImageView;
	@ViewInject(R.id.app_item_btn)
	private Button mAppItemButton;
	@ViewInject(R.id.app_detail_appname_text)
	private TextView mAppNameTextView;
	@ViewInject(R.id.app_install_tips_text)
	private TextView mAppInstallTipsTextView;
	@ViewInject(R.id.app_item_install_progress_bar)
	private ProgressBar mInstallProgressBar;
	// 下载地址
	private String mSrcUrl = null;
	// apk存放路径
	private String mDesPath = null;
	private DownloadManager mDownloadManager;
	// apk下载信息
	private DownloadInfo mDownloadInfo = null;
	// apk信息
	private AppItemMessageModel mAppItem;
	// 标识是否为本地apk
	private AppInfoModel mLocalAppInfoModel;
	private boolean mIsNetAppTag = false;

	public AppItemSingle(Context context, AppInfoModel loacalAppInfoModel) {
		super(context);
		this.mContext = context;
		this.mLocalAppInfoModel = loacalAppInfoModel;
		init(false);
	}

	public AppItemSingle(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init(true);
	}

	public AppItemSingle(Context context, DownloadManager downloadManager, String url, AppItemMessageModel item) {
		super(context);
		this.mContext = context;
		this.mDownloadManager = downloadManager;
		this.mSrcUrl = url;
		this.mAppItem = item;
		init(true);
	}

	private void init(boolean isNetApp) {
		mIsNetAppTag = isNetApp;
		View view = LayoutInflater.from(mContext).inflate(R.layout.sub_app_item, this, true);
		ViewUtils.inject(this, view);
		mAppItemButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mAppItemButton.requestLayout();
					mAppItemButton.requestFocus();
					break;
				case MotionEvent.ACTION_UP:
					click(view);
					break;
				default:
					break;
				}
				return true;
			}
		});
		if (isNetApp) {
			String[] strs = mSrcUrl.split("/");
			mDesPath = UrlMgr.getAppBusinessStoragePath() + strs[strs.length - 1];
			getDownLoadMessage();
		}
	}

	public void setValues(String imgUrl, String text) {
		mAppNameTextView.setText(text);
		BitmapUtil.setFadeInImage(imgUrl, mAppPicImageView);
	}

	@SuppressWarnings("deprecation")
	public void setValues(Drawable d, String text) {
		mAppNameTextView.setText(text);
		mAppPicImageView.setBackgroundDrawable(d);
	}

	@OnFocusChange(R.id.app_item_btn)
	public void focusChange(View view, boolean hasFocus) {
		if (hasFocus) {
			if (!ApkUtil.isAppInstalled(mContext, getAppPackageName())) {
				if (mDownloadInfo != null) {
					if (mInstallProgressBar.getProgress() >= 100) {
						showInstallTips();
					} else {
						showProgress();
					}
				} else {
					showInstallTips();
				}
			}
			mAppNameTextView.setSelected(true);
		} else {
			if (mDownloadInfo != null) {
				showProgress();
				if (mInstallProgressBar.getProgress() >= 100) {
					showAppName();
				}
			} else {
				showAppName();
			}
			mAppNameTextView.setSelected(false);
		}
	}

	@OnClick(R.id.app_item_btn)
	public void click(View view) {
		if (ApkUtil.isAppInstalled(mContext, getAppPackageName())) {
			if (mIsNetAppTag) {
				File file = new File(mDesPath);
				if (file.exists()) {
					file.delete();
				}
			}
			Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(getAppPackageName());
			mContext.startActivity(intent);
		} else {
			installApk();
		}
	}

	public void showProgress() {
		mInstallProgressBar.setVisibility(View.VISIBLE);
		mAppNameTextView.setVisibility(View.GONE);
		mAppInstallTipsTextView.setVisibility(View.GONE);
	}

	public void showInstallTips() {
		mAppInstallTipsTextView.setVisibility(View.VISIBLE);
		mAppNameTextView.setVisibility(View.GONE);
		mInstallProgressBar.setVisibility(View.GONE);
	}

	public void showAppName() {
		mAppNameTextView.setVisibility(View.VISIBLE);
		mInstallProgressBar.setVisibility(View.GONE);
		mAppInstallTipsTextView.setVisibility(View.GONE);
	}

	private String getAppPackageName() {
		String pakageName = null;
		if (mIsNetAppTag) {
			pakageName = mAppItem.getGamepackage();
		} else {
			pakageName = mLocalAppInfoModel.getPackname();
		}

		return pakageName;
	}

	/**
	 * 下载、安装apk逻辑
	 */
	private void installApk() {
		File file = new File(mDesPath);
		/*-------判断是否存在apk文件------------*/
		if (file.exists()) {
			showAppName();
			AuthorithMgr.getInstance().changeMode(file.getAbsolutePath());
			ApkUtil.installApk(mContext, file);
		} else {
			/*--------------下载apk-----------------*/
			try {
				mDownloadManager.addNewDownload(mSrcUrl, mAppItem.getGamename(), mDesPath, true, false, null);
			} catch (DbException e) {
				LogUtils.e(e.getMessage(), e);
			}
			getDownLoadMessage();
		}
	}

	/************************************** 网络apk安装部分 begin ***********************************************/
	@SuppressWarnings("rawtypes")
	private void getDownLoadMessage() {
		mDownloadInfo = mDownloadManager.getDownloadInfo(mSrcUrl);
		if (mDownloadInfo != null) {
			if (mDownloadInfo.getState() != HttpHandler.State.SUCCESS) {
				showProgress();
				refresh();
				HttpHandler<File> handler = mDownloadInfo.getHandler();
				if (handler != null) {
					RequestCallBack callBack = handler.getRequestCallBack();
					if (callBack instanceof DownloadManager.ManagerCallBack) {
						DownloadManager.ManagerCallBack managerCallBack = (DownloadManager.ManagerCallBack) callBack;
						if (managerCallBack.getBaseCallBack() == null) {
							managerCallBack.setBaseCallBack(new DownloadRequestCallBack());
						}
					}
					callBack.setUserTag(new WeakReference<AppItemSingle>(this));
				}
			}
		}
	}

	public void refresh() {
		if (mDownloadInfo.getFileLength() > 0) {
			int progress = (int) (mDownloadInfo.getProgress() * 100 / mDownloadInfo.getFileLength());
			mInstallProgressBar.setProgress(progress);
		} else {
			mInstallProgressBar.setProgress(0);
		}
		HttpHandler.State state = mDownloadInfo.getState();
		switch (state) {
		case WAITING:
		case STARTED:
		case LOADING:
			break;
		case CANCELLED:
			break;
		case SUCCESS:
			File file = new File(mDesPath);
			AuthorithMgr.getInstance().changeMode(file.getAbsolutePath());
			ApkUtil.installApk(mContext, file);
			showAppName();
			try {
				mDownloadManager.removeDownload(mDownloadInfo);
			} catch (DbException e) {
				e.printStackTrace();
			}
			break;
		case FAILURE:
			break;
		default:
			break;
		}
	}

	private class DownloadRequestCallBack extends RequestCallBack<File> {

		@SuppressWarnings("unchecked")
		private void refreshListItem() {
			if (userTag == null) {
				return;
			}
			WeakReference<AppItemSingle> tag = (WeakReference<AppItemSingle>) userTag;
			AppItemSingle holder = tag.get();
			if (holder != null) {
				holder.refresh();
			}
		}

		@Override
		public void onStart() {
			refreshListItem();
		}

		@Override
		public void onLoading(long total, long current, boolean isUploading) {
			refreshListItem();
		}

		@Override
		public void onSuccess(ResponseInfo<File> responseInfo) {
			refreshListItem();
		}

		@Override
		public void onFailure(HttpException error, String msg) {
			refreshListItem();
		}

		@Override
		public void onCancelled() {
			refreshListItem();
		}
	}
	/************************************** 网络apk安装部分 end ***********************************************/
}

package com.hhzt.iptv.lvb_x.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.model.VersionInfosModel;
import com.hhzt.iptv.lvb_x.net.LVBDownloadManager;
import com.hhzt.iptv.lvb_x.rootmanager.container.Result;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class UpdateActionFragment extends BaseFragment {

	@ViewInject(R.id.update_log_animation)
	private GifView mUpdateLogAnimation;
	@ViewInject(R.id.update_progress_animation)
	private GifView mUpdateProgressAnimation;
	@ViewInject(R.id.update_progress)
	private TextView mUpdateProgress;

	private LVBDownloadManager mLvbDownloadManager;
	private VersionInfosModel mVersionInfosModel;

	@SuppressLint("HandlerLeak")
	public Handler mMainHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.IPTV_MSG_APP_EXIST_TAG:
				removeMessages(msg.what);
				String apkUrl = msg.getData().getString(Constant.IPTV_SYS_MSG_APK_DOWNLOAD_URL, null);
				boolean existFlag = msg.getData().getBoolean(Constant.IPTV_SYS_MSG_APK_EXIST_FLAG, false);
				mLvbDownloadManager.startSmartDownloadAndInstall(apkUrl, existFlag, this);
				break;
			case Constant.IPTV_MSG_UPDATE_DOWNLOADING:
				removeMessages(msg.what);
				mUpdateProgress.setText(String.format(getString(R.string.downloading_progress), msg.arg1 + "KB"));
				break;
			case Constant.IPTV_MSG_UPDATE_DOWNLOAD_FAILED:
				removeMessages(msg.what);
				mUpdateProgress.setText(R.string.download_failed);
				break;
			case Constant.IPTV_MSG_UPDATE_DOWNLOAD_SUCCESS:
				removeMessages(msg.what);
				sendEmptyMessage(Constant.IPTV_MSG_UPDATE_INSTALLING);
				break;
			case Constant.IPTV_MSG_UPDATE_INSTALLING:
				removeMessages(msg.what);
				mUpdateProgress.setText(R.string.installing_waitting);
				break;
			case Constant.IPTV_MSG_UPDATE_INSTALL_FINISH:
				removeMessages(msg.what);
				Result result = (Result) msg.obj;
				mUpdateProgress.setText(String.format(getString(R.string.install_finish), result.getMessage()));
				break;
			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_update_action, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			initValue();
		}
	}

	private void initValue() {
		setGifImageShow();
		actionToUpdate();
	}

	private void setGifImageShow() {
		mUpdateLogAnimation.setGifImage(R.drawable.update_log_gif);
		mUpdateLogAnimation.setGifImageType(GifImageType.COVER);

		mUpdateProgressAnimation.setGifImage(R.drawable.update_progress_gif);
		mUpdateProgressAnimation.setGifImageType(GifImageType.COVER);
	}

	private void actionToUpdate() {
		mVersionInfosModel = getActivity().getIntent().getParcelableExtra(Constant.IPTV_NEW_VERSION_INFOS);
		mLvbDownloadManager = new LVBDownloadManager(getActivity(), mMainHandler);
		mLvbDownloadManager.downloadAPPApk(mVersionInfosModel.getDownloadurl());
	}

	private void destoryGifRes() {
		mUpdateLogAnimation.destroy();
		mUpdateProgressAnimation.destroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		destoryGifRes();
	}

}

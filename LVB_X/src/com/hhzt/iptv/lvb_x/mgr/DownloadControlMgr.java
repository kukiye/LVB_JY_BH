package com.hhzt.iptv.lvb_x.mgr;


import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.interfaces.IDownloadCB;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.utils.download.DownloadInfo;
import com.hhzt.iptv.lvb_x.utils.download.DownloadManager;
import com.hhzt.iptv.lvb_x.utils.download.DownloadService;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by kobe on 2015/12/11 15:31.
 * 邮箱：quzhongxiang_kobe@163.com
 */
public class DownloadControlMgr {

    private DownloadManager mDownloadManager;
    private DownloadInfo mDownloadInfo;
    private IDownloadCB mDownloadCB;
    private static DownloadControlMgr mDownloadControlMgr;

    public static DownloadControlMgr getInstance() {
        if (null == mDownloadControlMgr) {
            mDownloadControlMgr = new DownloadControlMgr();
        }
        return mDownloadControlMgr;
    }

    private DownloadControlMgr() {
        super();
        mDownloadManager = DownloadService.getDownloadManager(LVBXApp.getApp().getApplicationContext());
    }

    /**
     * 判断是否下载成功
     *
     * @param url
     * @return
     */
    public boolean getDownloadSuccessTag(String url) {
        boolean downloadTag = false;
        DownloadInfo downloadInfo = mDownloadManager.getDownloadInfo(url);
        if (null == downloadInfo || downloadInfo.getState() == HttpHandler.State.SUCCESS) {
            downloadTag = true;
        }
        return downloadTag;
    }

    public void backupDownloadInfoList() {
        try {
            if (mDownloadManager != null) {
                mDownloadManager.backupDownloadInfoList();
            }
        } catch (DbException e) {
            LogUtils.e(e.getMessage(), e);
        }
    }

    /**
     * 开始下载
     *
     * @param srcUrl
     * @param name
     * @param desUrl
     * @param downloadCB
     */
    public void startDownload(String srcUrl, String name, String desUrl, IDownloadCB downloadCB) {
        try {
            mDownloadCB = downloadCB;
            mDownloadManager.addNewDownload(srcUrl, name, desUrl, true, false, null);
            LogUtil.i("srcUrl:"+srcUrl);
            getDownLoadMessage(srcUrl);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取下载信息
     *
     * @param url
     */
    @SuppressWarnings("rawtypes")
    public void getDownLoadMessage(String url) {
        mDownloadInfo = mDownloadManager.getDownloadInfo(url);
        if (mDownloadInfo != null) {
            LogUtil.i("mDownloadInfo.getState():"+mDownloadInfo.getState());
            if (mDownloadInfo.getState() != HttpHandler.State.SUCCESS) {
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
                    callBack.setUserTag(new WeakReference<DownloadControlMgr>(this));
                }
            }
        }
    }

    public void refresh() {
        int progress = 0;
        if (mDownloadInfo.getFileLength() > 0) {
            progress = (int) (mDownloadInfo.getProgress() * 100 / mDownloadInfo.getFileLength());
        }
        mDownloadCB.loading(progress);
        HttpHandler.State state = mDownloadInfo.getState();
        LogUtil.i("stateaaaaaaaaa:"+state);
        switch (state) {
            case WAITING:
            case STARTED:
            case LOADING:
                break;
            case CANCELLED:

                break;
            case SUCCESS:
                try {
                    if (null != mDownloadCB) {
                        mDownloadCB.downloadSuccessCB();
                        mDownloadManager.removeDownload(mDownloadInfo);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case FAILURE:
                try {
                    if (null != mDownloadCB) {
                        mDownloadCB.downloadFaildCB();
                        mDownloadManager.removeDownload(mDownloadInfo);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


    private class DownloadRequestCallBack extends RequestCallBack<File> {

        @SuppressWarnings("unchecked")
        private void refreshListItem() {
            if (userTag == null)
                return;
            WeakReference<DownloadControlMgr> tag = (WeakReference<DownloadControlMgr>) userTag;
            DownloadControlMgr holder = tag.get();
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

}

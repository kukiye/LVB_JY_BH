package com.hhzt.iptv.lvb_x.model;

import com.lidroid.xutils.db.annotation.Transient;
import com.lidroid.xutils.http.HttpHandler;

import java.io.File;

/**
 * Created by kobe on 2015/12/10 16:32.
 * 邮箱：quzhongxiang_kobe@163.com
 */
public class DownloadInfoBean {

    public DownloadInfoBean() {
    }

    private long id;

    @Transient
    private HttpHandler<File> handler;

    private HttpHandler.State state;

    private String downloadUrl;

    private String fileName;

    private String fileSavePath;

    private long progress;

    private long fileLength;

    private boolean autoResume;

    private boolean autoRename;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HttpHandler<File> getHandler() {
        return handler;
    }

    public void setHandler(HttpHandler<File> handler) {
        this.handler = handler;
    }

    public HttpHandler.State getState() {
        return state;
    }

    public void setState(HttpHandler.State state) {
        this.state = state;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSavePath() {
        return fileSavePath;
    }

    public void setFileSavePath(String fileSavePath) {
        this.fileSavePath = fileSavePath;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public boolean isAutoResume() {
        return autoResume;
    }

    public void setAutoResume(boolean autoResume) {
        this.autoResume = autoResume;
    }

    public boolean isAutoRename() {
        return autoRename;
    }

    public void setAutoRename(boolean autoRename) {
        this.autoRename = autoRename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DownloadInfoBean))
            return false;

        DownloadInfoBean that = (DownloadInfoBean) o;

        if (id != that.id)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}

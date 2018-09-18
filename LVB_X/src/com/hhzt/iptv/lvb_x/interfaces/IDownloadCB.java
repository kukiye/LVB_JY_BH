package com.hhzt.iptv.lvb_x.interfaces;

/**
 * Created by kobe on 2015/12/11 15:26.
 * 邮箱：quzhongxiang_kobe@163.com
 */
public interface IDownloadCB {

    void downloadSuccessCB();

    void downloadFaildCB();

    void loading(int progress);

}

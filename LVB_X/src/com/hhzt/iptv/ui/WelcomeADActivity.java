package com.hhzt.iptv.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.interfaces.IDownloadCB;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.AuthorithMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.mgr.DownloadControlMgr;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

import java.io.File;

public class WelcomeADActivity extends BaseActivity {


    private int time;
    //当前图片pos
    private int mPicturePosition;
    private String[] ad_picture;
    private int adChangeTime;
    private VideoView mAd_welcome_video;
    private TextView hastime_T_V;
    private ImageView mSphash_ad_image;
    private String videoTag;
    private String adInfo;
    private String mDesPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_ad);
        initView();


        videoTag = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.AB_AD_WELCOME_VIDEO_TAG);
        adInfo = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.AB_AD_WELCOME_VIDEO);
    }

    private void initVideo() {
        time = Integer.parseInt(ConfigMgr.getInstance().getBeanVaule(CCViewConfig.AB_AD_WELCOME_TIME));
        LogUtil.i("videoTag:;" + videoTag);
        if (!StringUtil.isEmpty(videoTag) && videoTag.equals("1")) {
            playVideo(deleteVideo());
        }
    }

    private void playVideo(String adInfo) {
        if (StringUtil.isNull(adInfo)) {
            ActivitySwitchMgr.gotoADWelcomeVideo(WelcomeADActivity.this);
            return;
        }
        mAd_welcome_video.setVideoPath(adInfo);
        mAd_welcome_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                LogUtil.i("111111111111");
                mSphash_ad_image.setVisibility(View.GONE);
                mediaPlayer.start();
                timeSyn();
            }
        });
        mAd_welcome_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (null != mAd_welcome_video) {
                    mAd_welcome_video.stopPlayback();
                    ActivitySwitchMgr.gotoADWelcomeVideo(WelcomeADActivity.this);
                }
            }
        });
        mAd_welcome_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                LogUtil.i("iiiii:" + i + "-------" + i1);
                if (null != mAd_welcome_video) {
                    mAd_welcome_video.stopPlayback();
                    ActivitySwitchMgr.gotoADWelcomeVideo(WelcomeADActivity.this);
                }

                return false;
            }
        });
    }

    //初始化布局控件
    private void initView() {
        mAd_welcome_video = (VideoView) findViewById(R.id.ad_welcome_video);
        hastime_T_V = (TextView) findViewById(R.id.has_time);
        mSphash_ad_image = (ImageView) findViewById(R.id.sphash_ad_image);

        String apkurl = UserMgr.getApkImage();
        if (!StringUtil.isEmpty(apkurl)) {
            Glide.with(this).load(apkurl).into(mSphash_ad_image);
        } else {
            Glide.with(this).load(R.drawable.splash_new).into(mSphash_ad_image);
        }

    }

    @Override
    protected void onResume() {
        initVideo();
        super.onResume();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //                case 200:
                //                    removeMessages(200);
                //                    if(mPicturePosition < ad_picture.length){
                //                        mPicturePosition++;
                //                        LogUtil.i("TAG","mPicturePosition:"+mPicturePosition+"----"+ad_picture[mPicturePosition % ad_picture.length]);
                //                        Glide.with(WelcomeADActivity.this).load(ad_picture[mPicturePosition % ad_picture.length]).into(adImageView);
                //                        sendEmptyMessageDelayed(200,  adChangeTime*1000);
                //                    }else {
                //                        ActivitySwitchMgr.gotoADWelcomeVideo(WelcomeADActivity.this);
                //                    }
                //                    break;
                case 300:
                    break;
            }
        }
    };

    /**
     * 时间同步  考虑后台下发的时间 同步跳转
     */
    private void timeSyn() {
        time--;
        hastime_T_V.setText(String.format(getResources().getString(R.string.AD_Hastiome), time + ""));
        mHandler.postDelayed(timeSynRunable, 1000);
    }

    public Runnable timeSynRunable = new Runnable() {

        @Override
        public void run() {
            if (time > 0) {
                timeSyn();
            } else {
                mAd_welcome_video.stopPlayback();
                ActivitySwitchMgr.gotoADWelcomeVideo(WelcomeADActivity.this);
            }
        }
    };

    //    @Override
    //    public boolean dispatchKeyEvent(KeyEvent event) {
    //        if (event.getAction() == KeyEvent.ACTION_DOWN) {
    //            if (null != mAd_welcome_video) {
    //                mAd_welcome_video.stopPlayback();
    //            }
    //            ActivitySwitchMgr.gotoADWelcomeVideo(WelcomeADActivity.this);
    //        }
    //        return true;
    //    }


    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacksAndMessages(null);
        super.onPause();
        if (null != mAd_welcome_video) {
            mAd_welcome_video.stopPlayback();
        }
    }

    public String deleteVideo() {
        LogUtil.i("adInfo==" + adInfo);
        if (!StringUtil.isNull(adInfo)) {
            String videoName = adInfo.substring(adInfo.lastIndexOf("/")).substring(1);
            LogUtil.i("videoName::" + videoName);
            String bgMusicPath = UrlMgr.getWelcomVideoStoragePath();
            File musicDir = new File(bgMusicPath);
            if (musicDir.exists()) {
                File[] files = musicDir.listFiles();
                for (File file : files) {
                    if (!file.getAbsolutePath().equalsIgnoreCase(bgMusicPath + videoName)) {
                        file.delete();
                    }
                }
            }
            if (null != videoName) {
                mDesPath = UrlMgr.getWelcomVideoStoragePath() + videoName;
                AuthorithMgr.getInstance().changeMode(mDesPath);
                File musicFile = new File(mDesPath);
                if (musicFile.exists()) {
                    return mDesPath;
                } else {
                    downloadVideo(adInfo, videoName);
                    return adInfo;
                }
            }
        }
        return adInfo;
    }

    /**
     * 下载开机视频
     */
    private void downloadVideo(String url, String name) {
        DownloadControlMgr.getInstance().startDownload(url, name, mDesPath, new IDownloadCB() {
            @Override
            public void downloadSuccessCB() {
                //  playBgMusic(mDesPath);
            }

            @Override
            public void downloadFaildCB() {

            }

            @Override
            public void loading(int progress) {

            }
        });
    }
}


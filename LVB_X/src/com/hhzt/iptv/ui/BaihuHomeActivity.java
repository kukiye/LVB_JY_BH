/**
 * 作者：   Johnson
 * 日期：   2014年6月18日下午2:37:30
 * 包名：    com.hhzt.iptv.lvb_x.controller
 * 工程名：LVB_X
 * 文件名：MainmenuActivity.java
 */
package com.hhzt.iptv.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.adapter.MainFocusListAdapter;
import com.hhzt.iptv.lvb_x.business.FragmentFactory;
import com.hhzt.iptv.lvb_x.business.GlobalStore;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.customview.LinearMainLayout;
import com.hhzt.iptv.lvb_x.fragments.TvFragment;
import com.hhzt.iptv.lvb_x.handler.HandlerValue;
import com.hhzt.iptv.lvb_x.interfaces.IVodDetailListSuccessed;
import com.hhzt.iptv.lvb_x.interfaces.IVodTypeListSuccessed;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.hhzt.iptv.lvb_x.model.MainmenuModel;
import com.hhzt.iptv.lvb_x.model.VodDetailDataModel;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.model.VodTypeItemModel;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_baihu_home)
public class BaihuHomeActivity extends BaseActivity {

    public static BaihuHomeActivity mMainMenuActivity = null;

    //顶部标题栏
    @ViewInject(R.id.iv_title_ch)
    private ImageView mTitleChIv;
    @ViewInject(R.id.iv_title_en)
    private ImageView mTitleEnIv;
    @ViewInject(R.id.mainmenu_date)
    private TextView mMainmenuDateTextView;
    @ViewInject(R.id.mainmenu_time)
    private TextView mMainmenuTimeTextView;
    @ViewInject(R.id.mainmenu_week)
    private TextView mMainmenuWeekTextView;
    @ViewInject(R.id.mainmenu_chinese_date)
    private TextView mLunarDateTextView;
    @ViewInject(R.id.right_layout)
    private View mRightLayout;

    @ViewInject(R.id.base_weath_city)
    private TextView mCityTextView;
    @ViewInject(R.id.base_weath_icon)
    private ImageView mWeatherImageView;
    @ViewInject(R.id.base_weath_tem)
    private TextView mWeatherTextView;

    private DateAlarmReceiver mDateAlarmReceiver;
    private IntentFilter mDateAlarmFilter;

    //右边特别关注  改造之星
    @ViewInject(R.id.lv_focus)
    ListView mLvSpecialFocus;

    @ViewInject(R.id.iv_star_1)
    private ImageView mIvStar1;
    @ViewInject(R.id.iv_star_2)
    private ImageView mIvStar2;
    @ViewInject(R.id.iv_star_3)
    private ImageView mIvStar3;

    private MainFocusListAdapter mFocusAdapter;
    private ArrayList<VodDetailItemModel> mFocusVodDetailList;
    private boolean isNoFocusData = false;

    private int starTime;
    private String[] mStarImageArr;


    @ViewInject(R.id.main_tab_layout)
    private LinearMainLayout mTopButtonGroup;
    @ViewInject(R.id.tv_count)
    private TextView mTvCount;

    private boolean buttonHasFocuse = false;
    private int oldpos;
    private BaseFragment mContentfragment;
    private MainmenuModel specialModel;


    public static BaihuHomeActivity getInstance() {
        return mMainMenuActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        mMainMenuActivity = this;

        registerReceiver();
        setTimeShow(true);
        FragmentFactory.clearMap();
        initData();
        initListener();


    }

    @Override
    protected void onStart() {
        super.onStart();
        startDateUpdateAlarm();
    }

    public TextView getTvCount() {
        return mTvCount;
    }


    private void initListener() {

        mLvSpecialFocus.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {

                    if (mLvSpecialFocus.getSelectedItemPosition() == 0 && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        setTopButtonRequestFocus();
                        return true;
                    }
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        if (oldpos == 6) {
                            TvFragment tvfragment = (TvFragment) FragmentFactory.createFragment(6, null);
                            tvfragment.setTvListViewRequestFocus();
                            return true;
                        }

                    }
                }

                return false;
            }
        });


        mLvSpecialFocus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VodDetailItemModel item = mFocusAdapter.getItem(i);

                //直接播放视频
                UIDataller.getDataller().playVodItem(BaihuHomeActivity.this, item);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();


        if (mStarImageArr != null && mStarImageArr.length > 0) {
            handler.post(runnable);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        cancelDateUpdateAlarm();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseActivity.getInstance().getApplicationContext().unregisterReceiver(mDateAlarmReceiver);
    }

    private void initData() {

        Intent intent = getIntent();
        if (intent != null) {
            oldpos = intent.getIntExtra("position", 0);
        }
        ArrayList<MainmenuModel> modelList = GlobalStore.getHomeModelList();
        if (modelList != null && modelList.size() > 0) {
            addBottomItem(modelList);
        }

        specialModel = GlobalStore.getSpecialModel();
        initStarData();
        if (specialModel != null) {
            UIDataller.getDataller().setVodTypeData(this, specialModel.getCode(), new IVodTypeListSuccessed() {
                @Override
                public void onTypeListSuccess(ArrayList<VodTypeItemModel> models) {

                    sendHandlerMessage(mHandler, HandlerValue.REQ_HOME_SPECIAL_FOCUS, 0, models);

                }
            });
        }


    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {

            case HandlerValue.REQ_HOME_SPECIAL_FOCUS:
                ArrayList<VodTypeItemModel> focusDataList = (ArrayList<VodTypeItemModel>) msg.obj;

                setFocusModel(focusDataList);
                break;
        }
    }

    private void setFocusModel(ArrayList<VodTypeItemModel> focusDataList) {
        if (focusDataList != null && focusDataList.size() > 0) {

            VodTypeItemModel vodType = focusDataList.get(0);

            UIDataller.getDataller().setVodDetailData(this, vodType.getId(), 1, 100, new IVodDetailListSuccessed() {
                @Override
                public void onDetailListSuccess(VodDetailDataModel voddetailModel) {

                    if (voddetailModel != null) {
                        mFocusVodDetailList = voddetailModel.getResult();
                        setFocusDataList(mFocusVodDetailList);
                    }
                }

                @Override
                public void onFail(String result) {
                    if (!StringUtil.isNull(result)) {
                        isNoFocusData = true;
                    }
                }
            });
        }
    }

    private void setFocusDataList(ArrayList<VodDetailItemModel> vodDetailList) {
        if (vodDetailList != null && vodDetailList.size() > 0) {
            isNoFocusData = false;
            mFocusAdapter = new MainFocusListAdapter(this, vodDetailList);
            mLvSpecialFocus.setAdapter(mFocusAdapter);
        }


    }

    Handler handler = new Handler();

    private int count = 0;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    setImage(mIvStar1, count * 3);
                    setImage(mIvStar2, count * 3 + 1);
                    setImage(mIvStar3, count * 3 + 2);

                }
            });
            ++count;

            int size = mStarImageArr.length;
            if (size % 3 == 0) {
                if (count == size / 3) {
                    count = 0;
                }
            } else {
                if (count == (size / 3) + 1) {
                    count = 0;
                }
            }

            handler.postDelayed(this, starTime * 1000);
        }
    };

    private void setImage(ImageView imageView, int position) {
        if (position < mStarImageArr.length) {
            Glide.with(this).load(mStarImageArr[position]).into(imageView);

        } else {
            imageView.setImageResource(R.drawable.star_default);
        }
    }

    private void initStarData() {

        String starImage = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.AB_STAR_IMAGE);
        starTime = Integer.parseInt(ConfigMgr.getInstance().getBeanVaule(CCViewConfig.AB_STAR_TIME));
        if (!StringUtil.isNull(starImage)) {
            if (starImage.contains(",")) {
                mStarImageArr = starImage.split(",");
            }

        }
        handler.post(runnable);

    }

    /**
     * 底部Bottom
     */
    private void addBottomItem(List<MainmenuModel> lists) {
        mTopButtonGroup.removeAllViews();
        if (lists.size() == 0) {
            return;
        }

        WindowManager wm = this.getWindowManager();

        int displayWidth = wm.getDefaultDisplay().getWidth();
        int bottonWidth = (displayWidth - (int) getResources().getDimension(R.dimen.layx35)) / (lists.size() + 1)
                - (int) getResources().getDimension(R.dimen.layx15);
        //单个单元~~
        for (int i = 0; i < lists.size(); i++) {
            final MainmenuModel model = lists.get(i);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bottonWidth,
                    (int) getResources().getDimension(R.dimen.layx36));

            Button button = new Button(this);
            layoutParams.setMargins(0, 0, (int) getResources().getDimension(R.dimen.layx15), 0);
            button.setTextSize(getResources().getDimension(R.dimen.layx10));
            button.setTextColor(getResources().getColor(R.color.main_text_bule));
            button.setGravity(Gravity.CENTER);
            button.setLayoutParams(layoutParams);
            button.setBackground(getResources().getDrawable(R.drawable.home_tab_selector));

            button.setText(model.getName());
            button.setTag(i);

            final MainmenuModel finalModel = model;
            button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        buttonHasFocuse = true;

                        int position = StringUtil.toInt(view.getTag().toString());
                        if (oldpos != position) {
                            mTopButtonGroup.getChildAt(oldpos).setBackgroundResource(R.drawable.main_right_news_item_bg);
                            view.setBackgroundResource(R.drawable.main_content_gride_item_focus);
                            oldpos = position;
                        } else if (oldpos == position) {
                            view.setBackgroundResource(R.drawable.main_content_gride_item_focus);
                        }

                        mContentfragment = FragmentFactory.createFragment(position, finalModel);
                        try {

                            FragmentMgr.replace(BaihuHomeActivity.this, false, R.id.fragment_content, mContentfragment, Constant.HOTEL_MAIN_MENU_SCREEN);
                        } catch (IllegalStateException e) {
                            e = null;
                        }

                    } else {
                        buttonHasFocuse = false;
                    }

                }
            });
            mTopButtonGroup.addView(button);

        }


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bottonWidth,
                (int) getResources().getDimension(R.dimen.layx36));

        Button button = new Button(this);
        layoutParams.setMargins(0, 0, (int) getResources().getDimension(R.dimen.layx15), 0);
        button.setTextSize(getResources().getDimension(R.dimen.layx10));
        button.setBackground(getResources().getDrawable(R.drawable.home_tab_selector));
        button.setTextColor(getResources().getColor(R.color.main_text_bule));
        button.setGravity(Gravity.CENTER);
        button.setLayoutParams(layoutParams);
        button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    setAllTabBg(mTopButtonGroup.getChildCount() - 1);
                } else {
                    view.setBackground(getResources().getDrawable(R.drawable.main_right_news_item_bg));
                    setAllTabBg(mTopButtonGroup.getChildCount() - 2);
                }
            }
        });

        button.setText(getResources().getString(R.string.back));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaihuHomeActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mTopButtonGroup.addView(button);

        setTopButtonRequestFocus();

    }

    private BaseFragment currentFragment;

    private FragmentTransaction switchFragment(BaseFragment targetFragment) {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        LogUtil.i("ytj----currentFragment==" + currentFragment);
        if (!targetFragment.isAdded()) {
            LogUtil.i("ytj----isAdded==false");
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.fragment_content, targetFragment, targetFragment.getClass().getName()).show(targetFragment);

        } else {
            LogUtil.i("ytj----isAdded==true");
            transaction.hide(currentFragment).show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }


    private void setAllTabBg(int position) {

        for (int i = 0; i < mTopButtonGroup.getChildCount(); i++) {
            View view = mTopButtonGroup.getChildAt(i);
            if (position == i) {
                view.setBackground(getResources().getDrawable(R.drawable.main_content_gride_item_focus));
            } else {
                view.setBackground(getResources().getDrawable(R.drawable.main_right_news_item_bg));
            }

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return mContentfragment.onKeyDown(keyCode, event) ? true : super.onKeyDown(keyCode, event);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                ActivitySwitchMgr.gotoSettingActivity(this);
                return true;
            default:
                return mContentfragment.onKeyUp(keyCode, event) ? true : super.onKeyUp(keyCode, event);
        }
    }


    public void setTopButtonRequestFocus() {
//        GlobalStore.setIsRecycleViewHasFocus(false);

        mTopButtonGroup.getChildAt(oldpos).requestFocus();
        mTopButtonGroup.getChildAt(oldpos).requestFocusFromTouch();
    }

    public void setSpecialFocusRequestFocus() {
        mLvSpecialFocus.requestFocus();
    }

    public void showRightNews() {
        mRightLayout.setVisibility(View.VISIBLE);
        mTvCount.setVisibility(View.GONE);

    }

    public void hideRightNews() {

        mRightLayout.setVisibility(View.GONE);
        mTvCount.setVisibility(View.VISIBLE);
    }


    private void startDateUpdateAlarm() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Constant.IPTV_LVB_X_BROADCAST_HOME_MSG_UPDATE_DATE);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + Constant.IPTV_TIME_ONE_MINUTE, Constant.IPTV_TIME_ONE_MINUTE, pi);
    }

    private void cancelDateUpdateAlarm() {
        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Constant.IPTV_LVB_X_BROADCAST_HOME_MSG_UPDATE_DATE);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pi);
    }

    private void registerReceiver() {
        mDateAlarmReceiver = new DateAlarmReceiver();
        mDateAlarmFilter = new IntentFilter(
                Constant.IPTV_LVB_X_BROADCAST_HOME_MSG_UPDATE_DATE);
        BaseActivity.getInstance().getApplicationContext().registerReceiver(mDateAlarmReceiver, mDateAlarmFilter);
    }

    public class DateAlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constant.IPTV_LVB_X_BROADCAST_HOME_MSG_UPDATE_DATE.equals(intent
                    .getAction())) {
                setTimeShow(false);
            }
        }
    }

    // 设置和更新主界面时间信息
    private void setTimeShow(boolean needAnimation) {
        UIDataller.getDataller().setMainMenuDateInfoWihtLunar(mMainmenuDateTextView,
                mMainmenuTimeTextView, mMainmenuWeekTextView, mLunarDateTextView, needAnimation);
    }

}

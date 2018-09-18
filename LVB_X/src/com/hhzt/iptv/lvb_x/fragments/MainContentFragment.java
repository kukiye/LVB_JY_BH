package com.hhzt.iptv.lvb_x.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.adapter.MainFocusListAdapter;
import com.hhzt.iptv.lvb_x.adapter.MainGridViewAdapter;
import com.hhzt.iptv.lvb_x.business.GlobalStore;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCTemplateConfig;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.handler.HandlerValue;
import com.hhzt.iptv.lvb_x.interfaces.IOnMainMenuDataSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IVodDetailListSuccessed;
import com.hhzt.iptv.lvb_x.interfaces.IVodTypeListSuccessed;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.mgr.UpdateMgr;
import com.hhzt.iptv.lvb_x.model.MainmenuModel;
import com.hhzt.iptv.lvb_x.model.VodDetailDataModel;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.model.VodTypeItemModel;
import com.hhzt.iptv.lvb_x.utils.BaseLazyFragment;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/20.
 */

public class MainContentFragment extends BaseLazyFragment implements AdapterView.OnItemClickListener {

    @ViewInject(R.id.gv_main_program)
    private GridView mProgramGridView;

    @ViewInject(R.id.lv_focus)
    ListView mLvSpecialFocus;

    @ViewInject(R.id.iv_star_1)
    private ImageView mIvStar1;
    @ViewInject(R.id.iv_star_2)
    private ImageView mIvStar2;
    @ViewInject(R.id.iv_star_3)
    private ImageView mIvStar3;

    ArrayList<MainmenuModel> mDataList = new ArrayList<>();
    private MainmenuModel specialModel;
    private MainmenuModel starModel;
    private UpdateMgr mUpdateMgr;

    private MainFocusListAdapter mFocusAdapter;
    private ArrayList<VodDetailItemModel> mFocusVodDetailList;
    private boolean isNoFocusData = false;
    private boolean isNoStarData = false;
    private int mGrideSelectPosition = 0;
    private int starTime;
    private String[] mStarImageArr;


    public GridView getProgramGridView() {
        return mProgramGridView;
    }

    @Override
    protected void initPrepare() {
        initData();
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void onVisible() {

        checkNewVersion();
        initListener();
        playBackgroundMusic();
    }


    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_content, container,
                false);
        ViewUtils.inject(this, view);
        return view;
    }


    // 播放背景音乐
    private void playBackgroundMusic() {
        UIDataller.getDataller().continuePlayBackgroundMusic();
    }


    private void checkNewVersion() {
        if (null == mUpdateMgr) {
            mUpdateMgr = new UpdateMgr(getActivity());
        }

        mUpdateMgr.checkNewVersion();
    }

    private void initListener() {
        mProgramGridView.setOnItemClickListener(this);

        //解决当特别关注和改造之星没有数据时，这时按右键时焦点会消失
        mProgramGridView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {

                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        int selectedItemPosition = mProgramGridView.getSelectedItemPosition();
                        if (selectedItemPosition == 2 || selectedItemPosition == 5 || selectedItemPosition == 8) {
                            if (isNoStarData && isNoFocusData) {
                                return true;
                            }
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
                UIDataller.getDataller().playVodItem(mActivity, item);
            }
        });

        mLvSpecialFocus.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                        return true;
                    }
                }
                if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                        mProgramGridView.requestFocus();
                    }
                }
                return false;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        if (mStarImageArr != null && mStarImageArr.length > 0) {
            handler.postDelayed(runnable,starTime * 1000);
        }

    }

    private void initData() {

        initStarData();
        UIDataller.getDataller().setMainMenuHoriontalScroviewData(
                new IOnMainMenuDataSuccessCB() {

                    @Override
                    public void onDataSuccessCB(
                            ArrayList<MainmenuModel> items) {

                        sendHandlerMessage(mHandler, HandlerValue.REQ_MAIN_MENU, 0, items);

                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();

        handler.removeCallbacks(runnable);
    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {
            case HandlerValue.REQ_MAIN_MENU:
                //左侧广告位  注意 很有可能有变化
                ArrayList<MainmenuModel> models = (ArrayList<MainmenuModel>) msg.obj;
                if (models != null && models.size() > 0) {
                    for (MainmenuModel model : models) {
                        String tem = model.getTemplatecode();
                        if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_SPECIAL_FOCUS)) {
                            specialModel = model;
                            GlobalStore.setSpecialModel(specialModel);
                        } else if (tem.equals(CCTemplateConfig.IPTV_LVB_X_MAINMENU_TEMPLATE_REMOULD_STAR)) {
                            starModel = model;
                            GlobalStore.setStarModel(starModel);
                        } else {
                            mDataList.add(model);
                        }
                    }
                }

                setData();
                break;

            case HandlerValue.REQ_MAIN_SPECIAL_FOCUS:
                ArrayList<VodTypeItemModel> focusDataList = (ArrayList<VodTypeItemModel>) msg.obj;

                setFocusModel(focusDataList);
                break;
            case HandlerValue.REQ_MAIN_REMOULD_STAR:

                handler.post(runnable);

                break;
        }
    }

    private void setData() {
        GlobalStore.setHomeModelList(mDataList);
        MainGridViewAdapter gridViewAdapter = new MainGridViewAdapter(mActivity, mDataList);
        mProgramGridView.setAdapter(gridViewAdapter);
        mProgramGridView.requestFocus();
        mProgramGridView.requestFocusFromTouch();
        mProgramGridView.setSelection(0);


        if (specialModel != null) {
            UIDataller.getDataller().setVodTypeData(mActivity, specialModel.getCode(), new IVodTypeListSuccessed() {
                @Override
                public void onTypeListSuccess(ArrayList<VodTypeItemModel> models) {

                    sendHandlerMessage(mHandler, HandlerValue.REQ_MAIN_SPECIAL_FOCUS, 0, models);

                }
            });
        }


    }

    Handler handler = new Handler();

    private int count = 0;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    setImage(mIvStar1, count * 3);
                    setImage(mIvStar2, count * 3 + 1);
                    setImage(mIvStar3, count * 3 + 2);

                }
            });
            ++count;
            if (mStarImageArr != null) {
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

        }
    };

    private void setImage(ImageView imageView, int position) {
        if (mStarImageArr != null) {
            if (position < mStarImageArr.length) {
                Glide.with(mActivity).load(mStarImageArr[position]).into(imageView);

            } else {
                imageView.setImageResource(R.drawable.star_default);
            }
        }

    }

    private void initStarData() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String starImage = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.AB_STAR_IMAGE);
                String starTimeStr = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.AB_STAR_TIME);
                if (!StringUtil.isNull(starTimeStr)) {
                    starTime = Integer.parseInt(starTimeStr);
                }

                if (!StringUtil.isNull(starImage)) {
                    if (starImage.contains(",")) {
                        mStarImageArr = starImage.split(",");
                    }

                }
                sendHandlerMessage(mHandler, HandlerValue.REQ_MAIN_REMOULD_STAR, 0, mStarImageArr);
            }
        });

    }


    private void setFocusModel(ArrayList<VodTypeItemModel> focusDataList) {
        if (focusDataList != null && focusDataList.size() > 0) {

            VodTypeItemModel vodType = focusDataList.get(0);

            UIDataller.getDataller().setVodDetailData(mActivity, vodType.getId(), 1, 100, new IVodDetailListSuccessed() {
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
            mFocusAdapter = new MainFocusListAdapter(getActivity(), vodDetailList);
            mLvSpecialFocus.setAdapter(mFocusAdapter);
        }


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        ActivitySwitchMgr.gotoHomeActivity(mActivity, position);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                ActivitySwitchMgr.gotoSettingActivity(getActivity());
                return true;
            default:
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}

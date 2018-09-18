/**
 * 作者：   Johnson
 * 日期：   2014年6月25日下午3:13:49
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：HSDetailsFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.customview.VerticalScrollTextView;
import com.hhzt.iptv.lvb_x.interfaces.IScrollerSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IThirdmenuModelable;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.model.ThirdmenuModel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

@SuppressLint("HandlerLeak")
public class LVBX1_a2_Fragment extends BaseFragment {
	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.main_type_imgae)
	private ImageView mHomeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mCurrentPathTextView;
	@ViewInject(R.id.page_current_container)
	private RelativeLayout mCurrentPageContainerRl;
	@ViewInject(R.id.current_page_tips)
	private TextView mCurrentPageTextView;
	@ViewInject(R.id.tips_back)
	private TextView mActionBackTipsTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mActionOkTipsTextView;
	@ViewInject(R.id.image_content)
	private ImageView mHotelIntroduceImageview;
	@ViewInject(R.id.content_title)
	private TextView mHotelIntroduceTitle;
	@ViewInject(R.id.content_sub_title)
	private TextView mHotelIntroduceSubTitle;
	@ViewInject(R.id.content_text_container)
	private RelativeLayout mTextContainerLayout;
	@ViewInject(R.id.content_title_container)
	private LinearLayout mContentTitleContainer;

	private int mTotalPageNum = 0;
	private int mCurrentPageNum = 0;
	private String mMenuCode;
	private String mMenuPath;
	private ArrayList<ThirdmenuModel> mThirdmenuModelList = null;

	private float mTouchPointBeginX;
	private float mTouchPointEndX;
	private VerticalScrollTextView mHotelIntroduceContent;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.IPTV_MSG_TEXT_CONTENT_SCROLL_COMPLETE:
				removeMessages(msg.what);
				mHotelIntroduceContent.resetText();
				mHotelIntroduceContent.invalidate();
				break;
			default:
				break;
			}
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_template_lvb_x1_a2, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			mCurrentPageContainerRl.setVisibility(View.VISIBLE);

			Intent intent = getActivity().getIntent();
			mMenuCode = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_CODE_TAG);
			mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			addScrollText();
			getLocalDataMetaInfos();
			getNetDataMetaInfos();
		}
	}

	private void addScrollText() {
		float speed = 0.15f;
		try {
			String value = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.TEXT_SCROLL_SPEED);
			speed = Float.valueOf(value);
		} catch (Exception e) {
			LogUtil.e("Exception=" + e);
		} finally {
			mHotelIntroduceContent = new VerticalScrollTextView(getActivity(), speed, new IScrollerSuccessCB() {

				@Override
				public void scrolled(boolean tag) {
					if (null != mThirdmenuModelList && mThirdmenuModelList.size() > 0) {
						mHandler.sendEmptyMessageDelayed(Constant.IPTV_MSG_TEXT_CONTENT_SCROLL_COMPLETE, Constant.IPTV_TIME_THREE_SECONDES);
					}
				}
			});
			mTextContainerLayout.addView(mHotelIntroduceContent, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeMessages(Constant.IPTV_MSG_TEXT_CONTENT_SCROLL_COMPLETE);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_LEFT:
			if (null != mThirdmenuModelList && mThirdmenuModelList.size() > 1) {
				mHandler.removeMessages(Constant.IPTV_MSG_TEXT_CONTENT_SCROLL_COMPLETE);
				prePageNum();
				getNetDataMetaInfos();
			}
			return true;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			if (null != mThirdmenuModelList && mThirdmenuModelList.size() > 1) {
				mHandler.removeMessages(Constant.IPTV_MSG_TEXT_CONTENT_SCROLL_COMPLETE);
				nextPageNum();
				getNetDataMetaInfos();
			}
			return true;
		default:
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mTouchPointBeginX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			mTouchPointEndX = event.getX();

			float differ = mTouchPointEndX - mTouchPointBeginX;
			if (differ > 0 && Math.abs(differ) > LVBXApp.getmScreenWidth() / 3) {
				if (null != mThirdmenuModelList && mThirdmenuModelList.size() > 1) {
					mHandler.removeMessages(Constant.IPTV_MSG_TEXT_CONTENT_SCROLL_COMPLETE);
					prePageNum();
					getNetDataMetaInfos();
				}
			} else if (differ < 0 && Math.abs(differ) > LVBXApp.getmScreenWidth() / 3) {
				if (null != mThirdmenuModelList && mThirdmenuModelList.size() > 1) {
					mHandler.removeMessages(Constant.IPTV_MSG_TEXT_CONTENT_SCROLL_COMPLETE);
					nextPageNum();
					getNetDataMetaInfos();
				}
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	private void getLocalDataMetaInfos() {
		UIDataller ller = UIDataller.getDataller();

		mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));

		ller.setHsActionTips(getActivity(), mHomeImageView, R.drawable.home_icon, mCurrentPathTextView, mMenuPath, mActionOkTipsTextView,
				ller.getOkActionTips(getActivity()), mActionBackTipsTextView, ller.getBackActionTips(getActivity()));
	}

	private void getNetDataMetaInfos() {
		UIDataller ller = UIDataller.getDataller();
		ller.get_LVBX1ITTT_DetailsIntroduceInfos(getActivity(), mMenuCode, mHotelIntroduceImageview, mContentTitleContainer, mTextContainerLayout,
				mHotelIntroduceTitle, mHotelIntroduceSubTitle, mHotelIntroduceContent, mThirdmenuModelList, mCurrentPageNum, mCurrentPageTextView,
				new IThirdmenuModelable() {

					@Override
					public void setValue(ArrayList<ThirdmenuModel> modelList) {
						mTotalPageNum = modelList.size();
						mThirdmenuModelList = modelList;
					}
				});
	}

	private void prePageNum() {
		--mCurrentPageNum;
		if (mCurrentPageNum < 0) {
			mCurrentPageNum = mTotalPageNum - 1;
		}
	}

	private void nextPageNum() {
		++mCurrentPageNum;
		if (mCurrentPageNum >= mTotalPageNum) {
			mCurrentPageNum = 0;
		}
	}

}

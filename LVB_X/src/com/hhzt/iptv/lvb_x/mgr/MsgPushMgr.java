package com.hhzt.iptv.lvb_x.mgr;

import java.util.ArrayList;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.customview.RollTextView;
import com.hhzt.iptv.lvb_x.model.NewsDataItems;
import com.hhzt.iptv.lvb_x.model.NewsSingleItem;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

public class MsgPushMgr {

	private static MsgPushMgr mMsgPushMgr = new MsgPushMgr();
	private View mView;// 透明窗体
	private RollTextView mRollTextView;
	private boolean mViewAdded = false;// 透明窗体是否已经显示
	private LayoutParams mLayoutParams = null;
	private WindowManager mWindowManager;
	public static ArrayList<NewsSingleItem> mNewsSingleItemList = new ArrayList<NewsSingleItem>();

	public static MsgPushMgr getMsgMgrInstance() {

		return mMsgPushMgr;
	}

	public void initParam() {
		if (mLayoutParams == null) {
			mLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, LayoutParams.TYPE_SYSTEM_ERROR,
					LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
		}
	}

	public void showView(NewsDataItems datas) {
		addList(datas);
		if (!StringUtil.isEmpty(mNewsSingleItemList.get(0).getContent()) && mViewAdded == false) {
			addView();
		} else if (!StringUtil.isEmpty(mNewsSingleItemList.get(0).getContent()) && mViewAdded == true) {
			updateView();
		}
	}

	private void addView() {
		mWindowManager = LVBXApp.getApp().getWindowManager();
		mView = LayoutInflater.from(LVBXApp.getApp()).inflate(R.layout.relative_notice_hint, null);
		mRollTextView = (RollTextView) mView.findViewById(R.id.rollTextView);
		mRollTextView.init(mWindowManager, this);
		mRollTextView.startScroll();
		mLayoutParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
		mWindowManager.addView(mView, mLayoutParams);
		mViewAdded = true;
	}

	private void updateView() {
		mView.setVisibility(View.VISIBLE);
		mRollTextView.init(mWindowManager, this);
		mRollTextView.startScroll();
		mLayoutParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
		mWindowManager.updateViewLayout(mView, mLayoutParams);
	}

	private void addList(NewsDataItems datas) {
		synchronized (mNewsSingleItemList) {
			if (mNewsSingleItemList.size() == 0) {
				mNewsSingleItemList.addAll(datas.result);
			} else if (mNewsSingleItemList.size() > 0) {
				int id = mNewsSingleItemList.get(0).getId();
				int num = mNewsSingleItemList.get(0).getLoopnumber();
				mNewsSingleItemList.clear();
				mNewsSingleItemList.addAll(datas.result);
				for (int i = 0; i < mNewsSingleItemList.size(); i++) {
					if (mNewsSingleItemList.get(i).getId() == id) {
						mNewsSingleItemList.get(i).setLoopnumber(num);
					}
				}
			}
		}
	}

	public void hideView() {
		if (mViewAdded) {
			mView.setVisibility(View.GONE);
		}
	}

	public void removeView() {
		if (mViewAdded) {
			mViewAdded = false;
			mWindowManager.removeView(mView);
		}
	}

	public void changeTopSite() {
		if (mViewAdded && mView != null) {
			mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
			mWindowManager.updateViewLayout(mView, mLayoutParams);
		}
	}

	public void changeBottomSite() {
		if (mViewAdded && mView != null) {
			mLayoutParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
			mWindowManager.updateViewLayout(mView, mLayoutParams);
		}
	}
}

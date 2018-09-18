package com.hhzt.iptv.lvb_x.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.interfaces.IVodDetailItemMoveLeftable;
import com.hhzt.iptv.lvb_x.interfaces.IVodDetailItemable;
import com.hhzt.iptv.lvb_x.interfaces.IVodTotalSizeAble;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.model.VodTypeItemModel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class VodDetailListFragment extends BaseFragment implements IVodDetailItemable {

	@ViewInject(R.id.vod_detail_up_img)
	private ImageView mUpTipsImageView;
	@ViewInject(R.id.vod_detail_down_img)
	private ImageView mDownTipsImageView;
	@ViewInject(R.id.vod_movie_gridlayout)
	private GridLayout mVodDetailListGridLayout;
	@ViewInject(R.id.vod_item_page_text)
	private TextView mPageTextView;
	private IVodDetailItemable mItemMoveable;
	private IVodDetailItemMoveLeftable mMoveLeftable;
	private UIDataller dataller;
	// 页码标号1、2、3、4...
	private int mCurrentPageIndex = 1;
	private int mTotalPage = 0;
	// 点播类型id
	private int mCategoryId = 0;
	private String mPathName;
	private ArrayList<VodTypeItemModel> mVodTypeList;
	private int mGridLayoutHeight;
	private int mGridLayoutWidth;

	public void setGridListClickable(IVodDetailItemMoveLeftable itemMoveLeftable) {
		mMoveLeftable = itemMoveLeftable;
	}

	public VodDetailListFragment() {

	}

	public static VodDetailListFragment getInstance(ArrayList<VodTypeItemModel> models) {
		VodDetailListFragment detailListFragment = new VodDetailListFragment();
		detailListFragment.mVodTypeList = models;
		return detailListFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataller = UIDataller.getDataller();
		mItemMoveable = this;
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_vod_detail, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			ViewTreeObserver vto = mVodDetailListGridLayout.getViewTreeObserver();
			vto.addOnPreDrawListener(new OnPreDrawListener() {
				boolean flag = false;

				@Override
				public boolean onPreDraw() {
					if (!flag) {
						flag = true;
						mGridLayoutWidth = mVodDetailListGridLayout.getWidth();
						mGridLayoutHeight = mVodDetailListGridLayout.getHeight();
						initList();
					}
					return true;
				}
			});
		}
	}

	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	/**
	 * 更新点播列表
	 * 
	 * @param position
	 */
	public void updateList(int position) {
		mCurrentPageIndex = 1;
		mCategoryId = mVodTypeList.get(position).getId();
		dataller.setVodDetailList(getActivity(), mVodDetailListGridLayout, getVodListUrl(), mCurrentPageIndex, Constant.VOD_NUM_PER_PAGE,
				mUpTipsImageView, mDownTipsImageView, mPageTextView, mItemMoveable, false, 0, mMoveLeftable, mGridLayoutWidth, mGridLayoutHeight, 5,
				8, 13, new IVodTotalSizeAble() {

					@Override
					public void vodTotalsized(int totalSize) {
						mTotalPage = totalSize;
					}
				});
	}

	/**
	 * 设置电影列表默认焦点
	 */
	public void setDefaultFocus(int position, ListView listView) {
		if (mVodDetailListGridLayout.getChildCount() > 0) {
			listView.setFocusable(false);
			listView.setFocusableInTouchMode(false);
			if (position == -1) {
				mVodDetailListGridLayout.getChildAt(0).requestLayout();
				mVodDetailListGridLayout.getChildAt(0).requestFocus();
			} else {
				mVodDetailListGridLayout.getChildAt(position).requestLayout();
				mVodDetailListGridLayout.getChildAt(position).requestFocus();
			}
		}
	}

	/**
	 * 初始化点播列表
	 */
	private void initList() {
		// 添加点播类型列表
		if (mVodTypeList != null && mVodTypeList.size() > 0) {
			mCategoryId = mVodTypeList.get(0).getId();
			dataller.setVodDetailList(getActivity(), mVodDetailListGridLayout, getVodListUrl(), mCurrentPageIndex, Constant.VOD_NUM_PER_PAGE,
					mUpTipsImageView, mDownTipsImageView, mPageTextView, mItemMoveable, false, 0, mMoveLeftable, mGridLayoutWidth, mGridLayoutHeight,
					5, 8, 13, new IVodTotalSizeAble() {

						@Override
						public void vodTotalsized(int totalSize) {
							mTotalPage = totalSize;
						}
					});
		} else if (mVodTypeList != null && mVodTypeList.size() == 0) {
			BaseActivity.getInstance().showToast(getActivity().getString(R.string.vod_no_item), Toast.LENGTH_LONG);
		} else {
			BaseActivity.getInstance().showToast(getActivity().getString(R.string.vod_data_error), Toast.LENGTH_LONG);
		}
	}

	private String getVodListUrl() {
		String url = UrlMgr.getVodDetailListUrl(mCategoryId, mCurrentPageIndex, Constant.VOD_NUM_PER_PAGE, UserMgr.getVodTypeLeve());
		return url;
	}

	@OnClick({ R.id.vod_detail_up_img, R.id.vod_detail_down_img })
	private void setClickListener(View view) {
		switch (view.getId()) {
		case R.id.vod_detail_up_img:
			mCurrentPageIndex--;
			if (mCurrentPageIndex < 1) {
				mCurrentPageIndex = 1;
			} else {
				dataller.setVodDetailList(getActivity(), mVodDetailListGridLayout, getVodListUrl(), mCurrentPageIndex, Constant.VOD_NUM_PER_PAGE,
						mUpTipsImageView, mDownTipsImageView, mPageTextView, mItemMoveable, false, 0, mMoveLeftable, mGridLayoutWidth,
						mGridLayoutHeight, 5, 8, 13, null);
			}
			break;
		case R.id.vod_detail_down_img:
			mCurrentPageIndex++;
			if (mCurrentPageIndex > mTotalPage) {
				mCurrentPageIndex = mTotalPage;
			} else {
				dataller.setVodDetailList(getActivity(), mVodDetailListGridLayout, getVodListUrl(), mCurrentPageIndex, Constant.VOD_NUM_PER_PAGE,
						mUpTipsImageView, mDownTipsImageView, mPageTextView, mItemMoveable, false, 0, mMoveLeftable, mGridLayoutWidth,
						mGridLayoutHeight, 5, 8, 13, null);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void clickItemBtn(VodDetailItemModel itemModel) {
		Intent intent = getActivity().getIntent();
		mPathName = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		ActivitySwitchMgr.gotoVodItemDetailActivity(getActivity(), itemModel, mCategoryId, mPathName);
	}

	@Override
	public void moveItemUp(int position) {
		mCurrentPageIndex--;
		if (mCurrentPageIndex < 1) {
			mCurrentPageIndex = 1;
		} else {
			dataller.setVodDetailList(getActivity(), mVodDetailListGridLayout, getVodListUrl(), mCurrentPageIndex, Constant.VOD_NUM_PER_PAGE,
					mUpTipsImageView, mDownTipsImageView, mPageTextView, mItemMoveable, true, position, mMoveLeftable, mGridLayoutWidth,
					mGridLayoutHeight, 5, 8, 13, null);
		}
	}

	@Override
	public void moveItemDown(int position, int totalPage) {
		mCurrentPageIndex++;
		if (mCurrentPageIndex > totalPage) {
			mCurrentPageIndex = totalPage;
		} else {
			dataller.setVodDetailList(getActivity(), mVodDetailListGridLayout, getVodListUrl(), mCurrentPageIndex, Constant.VOD_NUM_PER_PAGE,
					mUpTipsImageView, mDownTipsImageView, mPageTextView, mItemMoveable, true, position, mMoveLeftable, mGridLayoutWidth,
					mGridLayoutHeight, 5, 8, 13, null);
		}
	}
}
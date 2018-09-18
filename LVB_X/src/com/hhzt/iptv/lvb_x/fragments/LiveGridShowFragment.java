/**
 * 作者：   Johnson
 * 日期：   2014年8月13日下午7:18:02
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：LiveGridShowFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.adapter.LiveGridViewAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.interfaces.ILiveMainGridShowSuccessCB;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.model.LiveMainModel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LiveGridShowFragment extends BaseFragment {

	@ViewInject(R.id.main_type_imgae)
	private ImageView mHomeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mCurrentPathTextView;
	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mActionOkTipsTextView;
	@ViewInject(R.id.tips_back)
	private TextView mActionBackTipsTextView;
	@ViewInject(R.id.live_data_item)
	private GridView mLiveItemsGridView;

	private String mMenuPath;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_live_grid_show, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setBottomTopInfos();
		setLiveItemDatas();
	}

	private void setBottomTopInfos() {
		Intent intent = getActivity().getIntent();
		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		UIDataller ller = UIDataller.getDataller();
		mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));
		ller.setHsActionTips(getActivity(), mHomeImageView, R.drawable.home_icon, mCurrentPathTextView, mMenuPath, mActionOkTipsTextView,
				ller.getOkActionTips(getActivity()), mActionBackTipsTextView, ller.getBackActionTips(getActivity()));
	}

	private void setLiveItemDatas() {
		UIDataller.getDataller().setLiveGridShowData(getActivity(), new ILiveMainGridShowSuccessCB() {

			@Override
			public void setListDatas(final ArrayList<LiveMainModel> gridViewModels) {
				Activity currentActivity = getActivity();
				if (null == currentActivity) {
					LogUtil.d("currentActivity has finished!");
					return;
				}
				LiveGridViewAdapter adapter = new LiveGridViewAdapter(currentActivity, gridViewModels);
				mLiveItemsGridView.setAdapter(adapter);

				mLiveItemsGridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
						SystemMgr.setControllerVibrate();
						UIDataller.getDataller().sendLivePushCmd(getActivity(), gridViewModels, position,
								gridViewModels.get(position).getChannelnumber());
					}
				});
			}
		});
	}
}

package com.hhzt.iptv.lvb_x.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseListFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.adapter.VodTypeListAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.interfaces.IListFragmentClickable;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.model.VodTypeItemModel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class VodTypeListFragment extends BaseListFragment implements OnItemSelectedListener {

	@ViewInject(R.id.vod_search_button)
	private Button mVodSearchButton;

	private VodTypeListAdapter mTypeListAdapter;
	private IListFragmentClickable mIListFragmentClickable;
	// 当前点播类型列表所在位置
	private int mCurrentSelectedIndex = 0;
	// 点播item所在焦点位置
	private int mDetailListPosition = -1;

	private ArrayList<VodTypeItemModel> mVodTypeList;

	public void setListClickable(IListFragmentClickable clickable) {
		mIListFragmentClickable = clickable;
	}

	public VodTypeListFragment() {

	}

	@OnClick(R.id.vod_search_button)
	public void onclickButton(View v) {
		switch (v.getId()) {
		case R.id.vod_search_button:
			String pathName = getActivity().getIntent().getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			ActivitySwitchMgr.gotoVodSearchActivity(getActivity(), pathName);
			break;
		default:
			break;
		}
	}

	public static VodTypeListFragment getInstance(ArrayList<VodTypeItemModel> models) {
		VodTypeListFragment typeListFragment = new VodTypeListFragment();
		typeListFragment.mVodTypeList = models;
		return typeListFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listfragment_vod_type, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			mTypeListAdapter = new VodTypeListAdapter();
			UIDataller.getDataller().setVodTypeList(getActivity(), getListView(), mTypeListAdapter, mVodTypeList);
			getListView().setOnItemSelectedListener(this);
			setListener();
		}
	}

	private void setListener() {
		getListView().setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_DOWN:
					int size = getListView().getCount();
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						if (mCurrentSelectedIndex == (size - 1)) {
							return true;
						}
					}
					break;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						mIListFragmentClickable.moveRightListener(mDetailListPosition, getListView());
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	public void setSelect(int position) {
		getListView().setFocusable(true);
		getListView().setFocusableInTouchMode(true);
		mDetailListPosition = position;
		getListView().requestLayout();
		getListView().requestFocus();
		getListView().setSelection(mCurrentSelectedIndex);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (mCurrentSelectedIndex != position) {
			mDetailListPosition = -1;
			mCurrentSelectedIndex = position;
			mIListFragmentClickable.clickItemListener(position);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (mCurrentSelectedIndex != arg2) {
			mDetailListPosition = -1;
			mCurrentSelectedIndex = arg2;
			mIListFragmentClickable.clickItemListener(arg2);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}

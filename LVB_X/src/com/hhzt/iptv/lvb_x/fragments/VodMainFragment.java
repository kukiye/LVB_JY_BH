package com.hhzt.iptv.lvb_x.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.interfaces.IListFragmentClickable;
import com.hhzt.iptv.lvb_x.interfaces.IVodDetailItemMoveLeftable;
import com.hhzt.iptv.lvb_x.interfaces.IVodTypeListSuccessed;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.mgr.FragmentMgr;
import com.hhzt.iptv.lvb_x.model.VodTypeItemModel;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class VodMainFragment extends BaseFragment implements IListFragmentClickable, IVodDetailItemMoveLeftable {

	@ViewInject(R.id.tv_mainmenu_name)
	private TextView mMainmenuName;

	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.sub_vod_type)
	private TextView mVodTypeTextView;
	private VodDetailListFragment mDetailListFragment = null;
	private VodTypeListFragment mTypeListFragment = null;
	private String mPathName;
	private VodMainFragment mInstance;
	
	@ViewInject(R.id.mainmenu_logo_b)
	private ImageView mMainLoginImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_vodmain, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			UIDataller.getDataller().setVodTypeData(getActivity(), new IVodTypeListSuccessed() {

				@Override
				public void onTypeListSuccess(ArrayList<VodTypeItemModel> models) {
					if (models != null && models.size() >= 0) {
						Activity activity = getActivity();
						// 防止网络访问慢时，导致空指针异常问题
						if (null == activity) {
							return;
						}
						Intent intent = activity.getIntent();
						mPathName = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
						mTypeListFragment = VodTypeListFragment.getInstance(models);
						mDetailListFragment = VodDetailListFragment.getInstance(models);
						mTypeListFragment.setListClickable(mInstance);
						mDetailListFragment.setGridListClickable(mInstance);
						mVodTypeTextView.setText(mPathName);
						mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mPathName));
						FragmentMgr.replace(getActivity(), false, R.id.vod_type_list_framelayout, mTypeListFragment, 0);
						FragmentMgr.replace(getActivity(), false, R.id.vod_detail_framelayout, mDetailListFragment, 0);
					} else if (models != null && models.size() == 0) {
						BaseActivity.getInstance().showToast(getActivity().getString(R.string.vod_no_item), Toast.LENGTH_LONG);
					} else {
						BaseActivity.getInstance().showToast(getActivity().getString(R.string.vod_data_error), Toast.LENGTH_LONG);
					}
				}
			});
			
			String mainmenuName = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.HOTEL_NAME);
			if (mainmenuName != null) {
				mMainmenuName.setText(mainmenuName);
			}
			
			String mainLogo = ConfigMgr.getInstance().getBeanVaule(
					CCViewConfig.MAINMENU_LOGO);
			if (mainLogo != null) {
				BitmapUtil.setImage(mainLogo, mMainLoginImageView); 
			}
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void clickItemListener(int position) {
		if (mDetailListFragment != null) {
			mDetailListFragment.updateList(position);
		} else {

		}
	}

	@Override
	public void moveItemLeft(int position) {
		if (mTypeListFragment != null) {
			mTypeListFragment.setSelect(position);
		}
	}

	@Override
	public void moveRightListener(int position, ListView listView) {
		if (mDetailListFragment != null) {
			mDetailListFragment.setDefaultFocus(position, listView);
		}
	}
}

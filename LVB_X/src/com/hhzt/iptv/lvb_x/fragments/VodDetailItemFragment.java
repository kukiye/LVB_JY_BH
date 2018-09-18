package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class VodDetailItemFragment extends BaseFragment {
	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.vod_item_movie_name_text)
	private TextView mMovieNameTextView;
	@ViewInject(R.id.vod_item_movie_nation_text)
	private TextView mMoVieNationtextView;
	@ViewInject(R.id.vod_item_movie_type_text)
	private TextView mMovieTypeTextView;
	@ViewInject(R.id.vod_item_movie_direct_text)
	private TextView mMovieDirectTextView;
	@ViewInject(R.id.vod_item_movie_actor_text)
	private TextView mMovieActorTextView;
	@ViewInject(R.id.vod_detail_introduce_text)
	private TextView mMovieIntroduceTextView;
	@ViewInject(R.id.vod_item_detail_play_btn)
	private Button mMoviePlayButton;
	@ViewInject(R.id.window_controller_entry_container)
	private LinearLayout mMovieWindowEntryContainer;
	@ViewInject(R.id.window_controller_entry_title)
	private Button mMovieWindowEntryButton;
	@ViewInject(R.id.vod_item_introduce_img)
	private ImageView mMoviePicImageView;

	private View view;
	private int mCategoryId = 0;
	private String mMenuPath;
	private VodDetailItemModel items;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		items = getActivity().getIntent().getParcelableExtra(Constant.IPTV_SYS_VOD_ITEM_SINGLE);
		mCategoryId = getActivity().getIntent().getIntExtra(Constant.IPTV_SYS_VOD_ITEM_CATEGORY_ID, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_vod_detail_item, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			UserMgr.setVodSavedIndex(0);
			UIDataller.getDataller().setVodDetailItemBaseMessage(getActivity(), items, mMovieNameTextView, mMoVieNationtextView, mMovieTypeTextView,
					mMovieDirectTextView, mMovieActorTextView, mMovieIntroduceTextView, mMoviePicImageView);

			Intent intent = getActivity().getIntent();
			mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));

			checkPauseBtnVisiable();
			UIDataller.getDataller().setVodDetailItemIntroduceOrSelectMessage(getActivity(), view, mMoviePlayButton, mMovieWindowEntryButton,
					mCategoryId, mMenuPath, items);
			mMovieNameTextView.setSelected(true);
		}
	}

	private void checkPauseBtnVisiable() {
		switch (Config.LvbDeviceType) {
		case Constant.DEVICE_TYPE_MOBILE_HSJQ:
			mMoviePlayButton.setVisibility(View.GONE);
			mMovieWindowEntryContainer.setVisibility(View.VISIBLE);
			break;
		default:
			mMoviePlayButton.setVisibility(View.VISIBLE);
			mMovieWindowEntryContainer.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}

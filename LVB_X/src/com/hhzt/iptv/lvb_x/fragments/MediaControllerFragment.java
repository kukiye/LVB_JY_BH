package com.hhzt.iptv.lvb_x.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.RemoteCMDType;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.model.VodItemPlayMessageModel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MediaControllerFragment extends BaseFragment {

	@ViewInject(R.id.current_vod_name)
	private TextView mWindowMediaCurrentMovieName;
	@ViewInject(R.id.current_percent)
	private TextView mWindowMediaStartPercent;
	@ViewInject(R.id.total_percent)
	private TextView mWindowMediaTotalPercent;
	@ViewInject(R.id.window_mediacontroller_seekbar)
	private SeekBar mWindowMediaSeekBar;
	@ViewInject(R.id.window_controller_pre_next_container)
	private LinearLayout mWindowControllerPreNext;

	private VodDetailItemModel mVodItemData;
	private ArrayList<VodItemPlayMessageModel> mEpisodeData;

	@OnClick({ R.id.window_controller_play, R.id.window_controller_pause, R.id.pre_sitcom_num, R.id.next_sitcom_num })
	public void onClickListner(View view) {
		switch (view.getId()) {
		case R.id.window_controller_play:
			// 发送点播播放指令
			UIDataller.getDataller().sendVodPushCmd(getActivity(), mEpisodeData, mEpisodeData.get(UserMgr.getVodSavedIndex()),
					Constant.IPTV_MEDIA_TYPE_VOD, UserMgr.getVodSavedIndex(), mVodItemData.getId(), mVodItemData.getprice(),
					mVodItemData.getProgramname());
			break;
		case R.id.window_controller_pause:
			// 发送点播暂停指令
			UIDataller.getDataller().sendVodControllerCmd(Constant.IPTV_MEDIAPLAYER_STATE_PAUSING);
			break;
		case R.id.pre_sitcom_num:
			int position1 = getPreMediaIndex();
			// 发送上一集视频播放指令
			UIDataller.getDataller().sendVodPreNextCmd(getActivity(), mEpisodeData, mEpisodeData.get(position1), Constant.IPTV_MEDIA_TYPE_VOD,
					position1, mVodItemData.getId(), mVodItemData.getprice(), mVodItemData.getProgramname(), RemoteCMDType.VOD_MEDIA_PRE);
			break;
		case R.id.next_sitcom_num:
			int position2 = getNextMediaIndex();
			// 发送下一集视频播放指令
			UIDataller.getDataller().sendVodPreNextCmd(getActivity(), mEpisodeData, mEpisodeData.get(position2), Constant.IPTV_MEDIA_TYPE_VOD,
					position2, mVodItemData.getId(), mVodItemData.getprice(), mVodItemData.getProgramname(), RemoteCMDType.VOD_MEDIA_NEXT);
			break;
		default:
			break;
		}
	}

	private int getNextMediaIndex() {
		int currentPosition = UserMgr.getVodSavedIndex();
		currentPosition++;
		if (currentPosition > mEpisodeData.size() - 1) {
			currentPosition = 0;
		}

		return currentPosition;
	}

	private int getPreMediaIndex() {
		int currentPosition = UserMgr.getVodSavedIndex();
		currentPosition--;
		if (currentPosition < 0) {
			currentPosition = mEpisodeData.size() - 1;
		}

		return currentPosition;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_vod_media_controller, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (null == savedInstanceState) {
			setValues();
		}
	}

	private void setValues() {
		getVodMediaData();
		setCurrentMovieName();
		setCurrentMoviePercent(0);
		setMediaPreNextVisible();
		setSeekBarEventListner();
	}

	private void getVodMediaData() {
		Intent intent = getActivity().getIntent();
		mVodItemData = intent.getParcelableExtra(Constant.IPTV_LVB_X_WINDOW_VOD_ITEM_DATA_TAG);
		mEpisodeData = intent.getParcelableArrayListExtra(Constant.IPTV_LVB_X_WINDOW_EPISONDE_DATA_TAG);
	}

	private void setCurrentMovieName() {
		mWindowMediaCurrentMovieName.setText(mVodItemData.getProgramname());
	}

	private void setCurrentMoviePercent(int percent) {
		mWindowMediaStartPercent.setText(percent + "%");
		mWindowMediaTotalPercent.setText("100%");
	}

	private void setMediaPreNextVisible() {
		if (null != mEpisodeData && mEpisodeData.size() > 1) {
			mWindowControllerPreNext.setVisibility(View.VISIBLE);
		} else {
			mWindowControllerPreNext.setVisibility(View.GONE);
		}
	}

	private void setSeekBarEventListner() {
		mWindowMediaSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				UIDataller.getDataller().sendVodSeekTragControllerCmd(getActivity(), mEpisodeData.get(UserMgr.getVodSavedIndex()),
						UserMgr.getVodSavedIndex(), mVodItemData.getId(), mVodItemData.getprice(), mVodItemData.getProgramname(),
						seekBar.getProgress());
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				setCurrentMoviePercent(progress);
			}
		});
	}

}

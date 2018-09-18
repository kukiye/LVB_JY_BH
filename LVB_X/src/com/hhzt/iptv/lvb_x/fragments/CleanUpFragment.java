/**
 * 作者：   Johnson
 * 日期：   2014年7月9日下午3:59:17
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：CleanUpFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.adapter.RoomServiceHistoryAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.commonui.DialogTwoButton;
import com.hhzt.iptv.lvb_x.interfaces.IOnClickListnerable;
import com.hhzt.iptv.lvb_x.interfaces.IOnRoomServiceHistorySuccessCB;
import com.hhzt.iptv.lvb_x.model.RoomServiceModel;
import com.hhzt.iptv.lvb_x.utils.CCAnimationUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.lidroid.xutils.view.annotation.event.OnKey;

public class CleanUpFragment extends BaseFragment {

	@ViewInject(R.id.clean_arrow_up)
	private Button mCleanUpModifyUp;
	@ViewInject(R.id.clean_arrow_down)
	private Button mCleanUpModifyDown;
	@ViewInject(R.id.clean_up_minutes)
	private Button mCleanUpMinutesButton;
	@ViewInject(R.id.clean_up_commit)
	private Button mCleanUpCommitBttuon;
	@ViewInject(R.id.clean_history_list)
	private ListView mRoomServiceHistoryList;

	private int mScreenTag;
	private int mCurrentMinutsNum;
	private ArrayList<RoomServiceModel> mRoomServiceModels;
	private RoomServiceHistoryAdapter mRoomServiceHistoryAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sub_roomservice_cleanup, container, false);
		ViewUtils.inject(this, view);
		CCAnimationUtils.setRandomAnimation(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			Bundle bundle = getArguments();
			mScreenTag = bundle.getInt(Constant.IPTV_SYS_FRAGMENT_TAG);
			mRoomServiceHistoryAdapter = new RoomServiceHistoryAdapter(getActivity());
			String minuteNum = mCleanUpMinutesButton.getText().toString();
			mCurrentMinutsNum = Integer.valueOf(minuteNum);

			updateHistoryData();
		}
	}

	@OnKey(R.id.clean_up_minutes)
	public boolean onKey(View arg0, int keyCode, KeyEvent arg2) {
		if (arg2.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_UP:
				nextTypeMinutes();
				return true;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				preTypeMinutes();
				return true;
			default:
				break;
			}

			return false;
		} else {
			return false;
		}
	}

	@OnClick({ R.id.clean_up_commit, R.id.clean_arrow_down, R.id.clean_arrow_up })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.clean_up_commit:
			commitTips();
			break;
		case R.id.clean_arrow_up:
			nextTypeMinutes();
			break;
		case R.id.clean_arrow_down:
			preTypeMinutes();
			break;
		default:
			break;
		}
	}

	@OnItemClick(R.id.clean_history_list)
	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
		if (arg2 > 0) {
			return;
		} else {
			int serviceStatus = mRoomServiceModels.get(0).getReqstatus();
			if (serviceStatus != Constant.IPTV_ROOMSERVICE_STATUS_WAITING) {
				return;
			}
		}
		deleteCleanupHistory(arg2);
	}

	private void deleteCleanupHistory(final int position) {
		String title = getString(R.string.cancel_cleanup);
		String contents = getString(R.string.cancel_clean_contents);

		DialogTwoButton dialog = new DialogTwoButton(getActivity(), R.style.lvbTwoButtonDialogTheme, title, contents, new IOnClickListnerable() {

			@Override
			public void ok() {
				getView().setVisibility(View.VISIBLE);
				UIDataller ller = UIDataller.getDataller();
				ller.deleteRoomServiceHistoryInfos(getActivity(), mScreenTag, mRoomServiceModels.get(position).getId(), System.currentTimeMillis()
						+ "", new IOnRoomServiceHistorySuccessCB() {

					@Override
					public void onSuccess(ArrayList<RoomServiceModel> roomServiceModels) {
						setHistoryListDate(roomServiceModels);
					}
				});
			}

			@Override
			public void no() {
				getView().setVisibility(View.VISIBLE);
			}
		}, null, true);

		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.x = -(int) ((double) LVBXApp.getmScreenWidth() / 4.5) / 2;
		dialog.show();
		getView().setVisibility(View.INVISIBLE);
	}

	private void commitTips() {
		String title = getString(R.string.clean_confirm);
		String contents = String.format(getString(R.string.clean_confirm_contents), mCurrentMinutsNum);
		DialogTwoButton dialog = new DialogTwoButton(getActivity(), R.style.lvbTwoButtonDialogTheme, title, contents, new IOnClickListnerable() {

			@Override
			public void ok() {
				getView().setVisibility(View.VISIBLE);
				commit();
			}

			@Override
			public void no() {
				getView().setVisibility(View.VISIBLE);
			}
		}, null, true);

		dialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					dialog.dismiss();
					getView().setVisibility(View.VISIBLE);
				}
				return false;
			}
		});
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.x = -(int) ((double) LVBXApp.getmScreenWidth() / 4.5) / 2;
		dialog.show();
		getView().setVisibility(View.INVISIBLE);
	}

	private void commit() {
		UIDataller ller = UIDataller.getDataller();
		ller.commitCleanUpAction(getActivity(), mScreenTag, mCurrentMinutsNum, new IOnRoomServiceHistorySuccessCB() {

			@Override
			public void onSuccess(ArrayList<RoomServiceModel> roomServiceModels) {
				setHistoryListDate(roomServiceModels);
				int xOffset = -(int) ((double) LVBXApp.getmScreenWidth() / 4.5) / 2;
				int yOffset = (int) ((double) LVBXApp.getmScreenHeight() / 3);
				BaseActivity.getInstance().showToast(getString(R.string.request_success), Gravity.CENTER, xOffset, yOffset, Toast.LENGTH_LONG);
			}
		});
	}

	private void updateHistoryData() {
		UIDataller ller = UIDataller.getDataller();
		ller.setRoomServiceHistoryListData(getActivity(), mScreenTag, new IOnRoomServiceHistorySuccessCB() {

			@Override
			public void onSuccess(ArrayList<RoomServiceModel> roomServiceModels) {
				setHistoryListDate(roomServiceModels);
			}
		});
	}

	private void nextTypeMinutes() {
		mCurrentMinutsNum += 5;
		if (mCurrentMinutsNum > 15) {
			mCurrentMinutsNum = 5;
		}

		mCleanUpMinutesButton.setText(mCurrentMinutsNum + "");
	}

	private void preTypeMinutes() {
		mCurrentMinutsNum -= 5;
		if (0 >= mCurrentMinutsNum) {
			mCurrentMinutsNum = 15;
		}

		mCleanUpMinutesButton.setText(mCurrentMinutsNum + "");
	}

	private void setHistoryListDate(ArrayList<RoomServiceModel> roomServiceModels) {
		mRoomServiceModels = roomServiceModels;
		if (null != roomServiceModels && roomServiceModels.size() > 0) {
			mRoomServiceHistoryList.setVisibility(View.VISIBLE);
			mRoomServiceHistoryAdapter.setListData(roomServiceModels);
			if (null == mRoomServiceHistoryList.getAdapter()) {
				mRoomServiceHistoryList.setAdapter(mRoomServiceHistoryAdapter);
			} else {
				mRoomServiceHistoryAdapter.notifyDataSetChanged();
			}
			mRoomServiceHistoryList.requestLayout();
			mRoomServiceHistoryList.requestFocus();
			mRoomServiceHistoryList.setSelection(0);
		} else if (null != roomServiceModels && roomServiceModels.size() == 0) {
			mRoomServiceHistoryList.setVisibility(View.GONE);
		} else {

		}
	}

}

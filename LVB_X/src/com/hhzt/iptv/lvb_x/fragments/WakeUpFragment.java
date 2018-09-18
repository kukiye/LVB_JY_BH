/**
 * 作者：   Johnson
 * 日期：   2014年7月9日下午4:02:04
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：WakeUpFragment.java
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
import com.hhzt.iptv.lvb_x.utils.DateTimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.lidroid.xutils.view.annotation.event.OnKey;

public class WakeUpFragment extends BaseFragment {

	@ViewInject(R.id.hour_arrow_up)
	private Button mHourModifyUp;
	@ViewInject(R.id.hour_arrow_down)
	private Button mHourModifyDown;
	@ViewInject(R.id.minute_arrow_up)
	private Button mMinuteModifyUp;
	@ViewInject(R.id.minute_arrow_down)
	private Button mMinuteModifyDown;
	@ViewInject(R.id.wakeup_hour)
	private Button mHourControllerButton;
	@ViewInject(R.id.wakeup_minute)
	private Button mMinuteControllerButton;
	@ViewInject(R.id.wake_up_commit)
	private Button mTimeCommittButton;
	@ViewInject(R.id.wakeup_history_list)
	private ListView mRoomServiceHistoryList;

	private int mScreenTag;
	private int mCurrentHour;
	private int mCurrentMinute;

	private ArrayList<RoomServiceModel> mRoomServiceModels;
	private RoomServiceHistoryAdapter mRoomServiceHistoryAdapter;

	@OnClick({ R.id.wake_up_commit, R.id.hour_arrow_up, R.id.hour_arrow_down, R.id.minute_arrow_up, R.id.minute_arrow_down })
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.wake_up_commit:
			commitTips();
			break;
		case R.id.hour_arrow_up:
			perHour();
			break;
		case R.id.hour_arrow_down:
			nextHour();
			break;
		case R.id.minute_arrow_up:
			preMinute();
			break;
		case R.id.minute_arrow_down:
			nextMinute();
			break;
		default:
			break;
		}
	}

	@OnItemClick(R.id.wakeup_history_list)
	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
		if (arg2 > 0) {
			return;
		} else {
			int serviceStatus = mRoomServiceModels.get(0).getReqstatus();
			if (serviceStatus != Constant.IPTV_ROOMSERVICE_STATUS_WAITING) {
				return;
			}
		}
		deleteWakeupHistory(arg2);
	}

	@OnKey({ R.id.wakeup_hour, R.id.wakeup_minute })
	public boolean onKey(View arg0, int keyCode, KeyEvent arg2) {
		if (arg2.getAction() == KeyEvent.ACTION_DOWN) {
			switch (arg0.getId()) {
			case R.id.wakeup_hour:
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
					perHour();
					return true;
				case KeyEvent.KEYCODE_DPAD_DOWN:
					nextHour();
					return true;
				default:
					break;
				}
				break;
			case R.id.wakeup_minute:
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
					preMinute();
					return true;
				case KeyEvent.KEYCODE_DPAD_DOWN:
					nextMinute();
					return true;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}

		return false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sub_roomservice_wakeup, container, false);
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
			mCurrentHour = DateTimeUtil.getCurrentHour();
			mCurrentMinute = DateTimeUtil.getCurrentMinute();
			setCurrentTime();

			updateHitoryDate();

			mTimeCommittButton.requestLayout();
			mTimeCommittButton.requestFocus();
		}
	}

	private void setCurrentTime() {
		setHourValue(mCurrentHour);
		setMinuteValue(mCurrentMinute);
	}

	private void updateHitoryDate() {
		UIDataller ller = UIDataller.getDataller();
		ller.setRoomServiceHistoryListData(getActivity(), mScreenTag, new IOnRoomServiceHistorySuccessCB() {

			@Override
			public void onSuccess(ArrayList<RoomServiceModel> roomServiceModels) {
				setHistoryListDate(roomServiceModels);
			}
		});
	}

	private void nextHour() {
		++mCurrentHour;
		if (mCurrentHour > 23) {
			mCurrentHour = 0;
		}

		setHourValue(mCurrentHour);
	}

	private void perHour() {
		--mCurrentHour;
		if (mCurrentHour < 0) {
			mCurrentHour = 23;
		}

		setHourValue(mCurrentHour);
	}

	private void nextMinute() {
		++mCurrentMinute;
		if (mCurrentMinute > 59) {
			mCurrentMinute = 0;
		}

		setMinuteValue(mCurrentMinute);
	}

	private void preMinute() {
		--mCurrentMinute;
		if (mCurrentMinute < 0) {
			mCurrentMinute = 59;
		}

		setMinuteValue(mCurrentMinute);
	}

	private void setHourValue(int hour) {
		if (hour > 9) {
			mHourControllerButton.setText(hour + "");
		} else {
			mHourControllerButton.setText("0" + hour);
		}
	}

	private void setMinuteValue(int minute) {
		if (minute > 9) {
			mMinuteControllerButton.setText(minute + "");
		} else {
			mMinuteControllerButton.setText("0" + minute);
		}
	}

	private String getWakeupDotTime() {
		String hour = (mCurrentHour > 9) ? mCurrentHour + "" : "0" + mCurrentHour;
		String minute = (mCurrentMinute > 9) ? mCurrentMinute + "" : "0" + mCurrentMinute;

		String resultTime = hour + ":" + minute;
		return resultTime;
	}

	private String getWakeupTimeHHMM() {
		String hour = (mCurrentHour > 9) ? mCurrentHour + "" : "0" + mCurrentHour;
		String minute = (mCurrentMinute > 9) ? mCurrentMinute + "" : "0" + mCurrentMinute;

		String resultTime = hour + minute;
		return resultTime;
	}

	private String getAppointmentTime() {
		String yyyyMMDD = DateTimeUtil.toTime(System.currentTimeMillis(), DateTimeUtil.DATE_DEFAULT_FORMATE);
		String HHMM = getWakeupTimeHHMM();

		String resultTime = yyyyMMDD + HHMM + "00";

		return resultTime;
	}

	private void deleteWakeupHistory(final int position) {
		long timeStamp = mRoomServiceModels.get(position).getAppointmenttime();
		String appointTimeContentTips = DateTimeUtil.toTime(timeStamp, DateTimeUtil.DATE_FORMATE_HOUR_MINUTE);
		final String appointTimeString = DateTimeUtil.toTime(timeStamp, DateTimeUtil.DATE_FORMATE_TO_SERVER);
		String title = getString(R.string.cancel_wakeup);
		String contents = String.format(getString(R.string.cancel_wakeup_confirm_content), appointTimeContentTips);

		DialogTwoButton dialog = new DialogTwoButton(getActivity(), R.style.lvbTwoButtonDialogTheme, title, contents, new IOnClickListnerable() {

			@Override
			public void ok() {
				getView().setVisibility(View.VISIBLE);
				UIDataller ller = UIDataller.getDataller();
				ller.deleteRoomServiceHistoryInfos(getActivity(), mScreenTag, mRoomServiceModels.get(position).getId(), appointTimeString,
						new IOnRoomServiceHistorySuccessCB() {

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
		String title = getString(R.string.wakeup_confirm);
		String contents = String.format(getString(R.string.wakeup_confirm_contents), getWakeupDotTime());

		DialogTwoButton dialog = new DialogTwoButton(getActivity(), R.style.lvbTwoButtonDialogTheme, title, contents, new IOnClickListnerable() {

			@Override
			public void ok() {
				getView().setVisibility(View.VISIBLE);
				UIDataller ller = UIDataller.getDataller();
				ller.commitWakeupService(getActivity(), mScreenTag, getAppointmentTime(), new IOnRoomServiceHistorySuccessCB() {

					@Override
					public void onSuccess(ArrayList<RoomServiceModel> roomServiceModels) {
						setHistoryListDate(roomServiceModels);
						int xOffset = -(int) ((double) LVBXApp.getmScreenWidth() / 4.5) / 2;
						int yOffset = (int) ((double) LVBXApp.getmScreenHeight() / 3);
						BaseActivity.getInstance()
								.showToast(getString(R.string.request_success), Gravity.CENTER, xOffset, yOffset, Toast.LENGTH_LONG);
					}
				});
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

/**
 * 作者：   Johnson
 * 日期：   2014年7月9日下午3:44:12
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：CheckInOutFragment.java
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
import com.hhzt.iptv.lvb_x.commonui.DateChooseDialog;
import com.hhzt.iptv.lvb_x.commonui.DialogTwoButton;
import com.hhzt.iptv.lvb_x.interfaces.IListItemOnClickCB;
import com.hhzt.iptv.lvb_x.interfaces.IOnClickListnerable;
import com.hhzt.iptv.lvb_x.interfaces.IOnRequestDataCB;
import com.hhzt.iptv.lvb_x.interfaces.IOnRoomServiceHistorySuccessCB;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.RoomServiceModel;
import com.hhzt.iptv.lvb_x.utils.CCAnimationUtils;
import com.hhzt.iptv.lvb_x.utils.DateTimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class CheckInOutFragment extends BaseFragment {

	@ViewInject(R.id.check_check_in)
	private Button mCheckInButton;
	@ViewInject(R.id.check_check_out)
	private Button mCheckOutButton;
	@ViewInject(R.id.checkinou_history_list)
	private ListView mRoomServiceHistoryList;

	private int mScreenTag;
	private String mUserName;
	private String mRoomNo;
	private ArrayList<String> mDateTimeItemList;
	private ArrayList<RoomServiceModel> mRoomServiceModels;
	private RoomServiceHistoryAdapter mRoomServiceHistoryAdapter;
	private long mServerTimeMillis;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sub_roomservice_checkinout, container, false);
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
			mUserName = UserMgr.getUserName();
			mRoomNo = UserMgr.getHotelRoomNo();
			mRoomServiceHistoryAdapter = new RoomServiceHistoryAdapter(getActivity());

			UIDataller ller = UIDataller.getDataller();
			// 初始化日期时间数据
			ller.getServerTimeMillis(new IOnRequestDataCB<Long>() {

				@Override
				public void onDataRequestSuccess(Long serverTimeMillis) {
					mServerTimeMillis = serverTimeMillis;
					mDateTimeItemList = DateTimeUtil.initDateTimeFormat(serverTimeMillis, Constant.IPTV_SYSTEM_DATE_NUM,
							DateTimeUtil.DATE_FORMATE_YEAR_MONTH_DAY);
				}
			});
			ller.setRoomServiceHistoryListData(getActivity(), mScreenTag, new IOnRoomServiceHistorySuccessCB() {

				@Override
				public void onSuccess(ArrayList<RoomServiceModel> roomServiceModels) {
					setHistoryListDate(roomServiceModels);
				}

			});
		}
	}

	private void checkCheckInOutState(ArrayList<RoomServiceModel> roomServiceModels) {
		if (null != roomServiceModels && roomServiceModels.size() > 0) {
			int reqType = roomServiceModels.get(0).getReqtype();
			int reqStaue = roomServiceModels.get(0).getReqstatus();
			mCheckInButton.setText(R.string.roomservice_checkin_modify);
			switch (reqType) {
			case Constant.IPTV_ROOMSERVICE_TYPE_CHECK_IN:
			case Constant.IPTV_ROOMSERVICE_TYPE_CHECK_OUT:
				if (reqStaue == Constant.IPTV_ROOMSERVICE_STATUS_DOING) {
					mCheckOutButton.setClickable(false);
					mCheckOutButton.setFocusable(false);
					mCheckOutButton.setEnabled(false);
					mCheckOutButton.setBackgroundResource(R.drawable.button_disable_selector);

					mCheckInButton.setClickable(false);
					mCheckInButton.setFocusable(false);
					mCheckInButton.setEnabled(false);
					mCheckInButton.setBackgroundResource(R.drawable.button_disable_selector);
				} else {
					mCheckOutButton.setClickable(true);
					mCheckOutButton.setFocusable(true);
					mCheckOutButton.setEnabled(true);
					mCheckOutButton.setBackgroundResource(R.drawable.button_selector);

					mCheckInButton.setClickable(true);
					mCheckInButton.setFocusable(true);
					mCheckInButton.setEnabled(true);
					mCheckInButton.setBackgroundResource(R.drawable.button_selector);
				}
				break;
			default:
				break;
			}
		}
	}

	@OnClick({ R.id.check_check_in, R.id.check_check_out })
	public void checkInOutAction(View view) {
		switch (view.getId()) {
		case R.id.check_check_in:
			checkInAction();
			break;
		case R.id.check_check_out:
			checkOutAction();
			break;
		default:
			break;
		}
	}

	@OnItemClick(R.id.checkinou_history_list)
	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
		// 只有第一项为可点击事件，其他为已过期
		if (arg2 > 0) {
			return;
		} else {
			int serviceStatus = mRoomServiceModels.get(0).getReqstatus();
			if (serviceStatus != Constant.IPTV_ROOMSERVICE_STATUS_WAITING) {
				return;
			}
		}
		int type = mRoomServiceModels.get(arg2).getReqtype();
		String title = "";
		switch (type) {
		case Constant.IPTV_ROOMSERVICE_TYPE_CHECK_OUT:
			title = getString(R.string.cancel_checkout);
			break;
		case Constant.IPTV_ROOMSERVICE_TYPE_CHECK_IN:
			title = getString(R.string.cancel_checkin);
			break;
		default:
			break;
		}
		String contents = getString(R.string.cancel_confirm_tips);
		DialogTwoButton dialog = new DialogTwoButton(getActivity(), R.style.lvbTwoButtonDialogTheme, title, contents, new IOnClickListnerable() {

			@Override
			public void ok() {
				getView().setVisibility(View.VISIBLE);
				UIDataller ller = UIDataller.getDataller();
				ller.deleteRoomServiceHistoryInfos(getActivity(), mScreenTag, mRoomServiceModels.get(arg2).getId(), System.currentTimeMillis() + "",
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

	private void checkInAction() {
		DateChooseDialog dialog = new DateChooseDialog(getActivity(), R.style.lvbTwoButtonDialogTheme, getView(), getString(R.string.date_choose),
				mServerTimeMillis, new IListItemOnClickCB() {

					@Override
					public void onClick(int position) {
						checkInConfirmTips(position, mScreenTag);
					}
				});
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.x = -(int) ((double) LVBXApp.getmScreenWidth() / 4.5) / 2;
		dialog.show();
		getView().setVisibility(View.INVISIBLE);
	}

	private void checkInConfirmTips(final int position, final int screenTag) {
		String title = getString(R.string.checkin_confirm);
		String contents = String.format(getString(R.string.checkin_confirm_content), mDateTimeItemList.get(position));
		DialogTwoButton dialogTwoButton = new DialogTwoButton(getActivity(), R.style.lvbTwoButtonDialogTheme, title, contents,
				new IOnClickListnerable() {

					@Override
					public void ok() {
						getView().setVisibility(View.VISIBLE);
						UIDataller ller = UIDataller.getDataller();
						ller.commitCheckOutInAction(getActivity(), Constant.IPTV_ROOMSERVICE_TYPE_CHECK_IN, screenTag, mRoomNo, mUserName,
								DateTimeUtil.getDateTimeAllDetails(mDateTimeItemList.get(position), DateTimeUtil.DATE_FORMATE_YEAR_MONTH_DAY,
										DateTimeUtil.DATE_FORMATE_TO_SERVER), new IOnRoomServiceHistorySuccessCB() {

									@Override
									public void onSuccess(ArrayList<RoomServiceModel> roomServiceModels) {
										setHistoryListDate(roomServiceModels);
										int xOffset = -(int) ((double) LVBXApp.getmScreenWidth() / 4.5) / 2;
										int yOffset = (int) ((double) LVBXApp.getmScreenHeight() / 3);
										BaseActivity.getInstance().showToast(getString(R.string.request_success), Gravity.CENTER, xOffset, yOffset,
												Toast.LENGTH_LONG);
									}
								});
					}

					@Override
					public void no() {
						getView().setVisibility(View.VISIBLE);
					}
				}, null, true);

		dialogTwoButton.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					dialog.dismiss();
					getView().setVisibility(View.VISIBLE);
				}
				return false;
			}
		});
		WindowManager.LayoutParams params = dialogTwoButton.getWindow().getAttributes();
		params.x = -(int) ((double) LVBXApp.getmScreenWidth() / 4.5) / 2;
		dialogTwoButton.show();
		getView().setVisibility(View.INVISIBLE);
	}

	private void checkOutAction() {
		String title = getString(R.string.checkout_confirm);
		String contents = getString(R.string.checkout_confirm_content);
		DialogTwoButton dialogTwoButton = new DialogTwoButton(getActivity(), R.style.lvbTwoButtonDialogTheme, title, contents,
				new IOnClickListnerable() {

					@Override
					public void ok() {
						getView().setVisibility(View.VISIBLE);
						UIDataller ller = UIDataller.getDataller();
						ller.commitCheckOutInAction(getActivity(), Constant.IPTV_ROOMSERVICE_TYPE_CHECK_OUT, mScreenTag, mRoomNo, mUserName,
								DateTimeUtil.getDateTimeAllDetails(mDateTimeItemList.get(0), DateTimeUtil.DATE_FORMATE_YEAR_MONTH_DAY,
										DateTimeUtil.DATE_FORMATE_TO_SERVER), new IOnRoomServiceHistorySuccessCB() {

									@Override
									public void onSuccess(ArrayList<RoomServiceModel> roomServiceModels) {
										setHistoryListDate(roomServiceModels);
										int xOffset = -(int) ((double) LVBXApp.getmScreenWidth() / 4.5) / 2;
										int yOffset = (int) ((double) LVBXApp.getmScreenHeight() / 3);
										BaseActivity.getInstance().showToast(getString(R.string.request_success), Gravity.CENTER, xOffset, yOffset,
												Toast.LENGTH_LONG);
									}
								});
					}

					@Override
					public void no() {
						getView().setVisibility(View.VISIBLE);
					}
				}, null, true);

		dialogTwoButton.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					dialog.dismiss();
					getView().setVisibility(View.VISIBLE);
				}
				return false;
			}
		});
		WindowManager.LayoutParams params = dialogTwoButton.getWindow().getAttributes();
		params.x = -(int) ((double) LVBXApp.getmScreenWidth() / 4.5) / 2;
		dialogTwoButton.show();
		getView().setVisibility(View.INVISIBLE);
	}

	private void setHistoryListDate(ArrayList<RoomServiceModel> roomServiceModels) {
		mRoomServiceModels = roomServiceModels;
		if (null != roomServiceModels && roomServiceModels.size() > 0) {
			mRoomServiceHistoryList.setVisibility(View.VISIBLE);
			mRoomServiceHistoryAdapter.setListData(roomServiceModels);
			mRoomServiceHistoryList.setAdapter(mRoomServiceHistoryAdapter);
			checkCheckInOutState(roomServiceModels);
			mRoomServiceHistoryList.requestLayout();
			mRoomServiceHistoryList.requestFocus();
			mRoomServiceHistoryList.setSelection(0);
		} else if (null != roomServiceModels && roomServiceModels.size() == 0) {
			mRoomServiceHistoryList.setVisibility(View.INVISIBLE);
		} else {

		}
	}
}

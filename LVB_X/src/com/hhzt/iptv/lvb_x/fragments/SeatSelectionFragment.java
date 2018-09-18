package com.hhzt.iptv.lvb_x.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.utils.CoordinateUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class SeatSelectionFragment extends BaseFragment {

	@ViewInject(R.id.seat_time)
	private TextView mSeatTimeTipsText;
	private TimeCounter mTimeCounter;
	private int mSeatTime = Constant.HSJQ_DEFAULT_SEAT_TIME;

	// private Handler mHandler = new Handler() {
	//
	// @Override
	// public void handleMessage(Message msg) {
	// super.handleMessage(msg);
	// switch (msg.what) {
	// case Constant.IPTV_MSG_HSJQ_SEAT_TIME_TAG:
	// removeMessages(msg.what);
	// mSeatTime--;
	// if (mSeatTime > 0) {
	// mSeatTimeTipsText.setText(mSeatTime + " " +
	// getString(R.string.seat_time_unit));
	// mHandler.sendEmptyMessageDelayed(msg.what,
	// Constant.IPTV_TIME_ONE_MINUTE);
	// } else {
	// getActivity().finish();
	// }
	// break;
	// default:
	// break;
	// }
	// }
	// };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_seat_tips, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setAllValues();
		setTimeTips();
	}

	private void setAllValues() {
		getActivity().setTheme(R.style.lvbEdittextDialogTheme);
		LayoutParams p = getActivity().getWindow().getAttributes();
		p.height = (int) CoordinateUtil.getX(LVBXApp.getmScreenWidth() / 9);
		p.width = (int) CoordinateUtil.getX(LVBXApp.getmScreenWidth() / 3);
		p.alpha = 1.0f;
		p.dimAmount = 0.0f;
		getActivity().getWindow().setAttributes(p);
		getActivity().getWindow().setGravity(Gravity.CENTER);
	}

	private void setTimeTips() {
		try {
			String timeString = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.HSJQ_SEAT_TIPS);
			int parseTime = Integer.parseInt(timeString);
			if (parseTime > 0) {
				mSeatTime = parseTime;
			}
		} catch (Exception exception) {

		} finally {
			long seatMillisSecond = mSeatTime * Constant.IPTV_TIME_ONE_MINUTE;
			mTimeCounter = new TimeCounter(seatMillisSecond, Constant.IPTV_TIME_ONE_SECONDE);
			mTimeCounter.start();
		}
	}

	class TimeCounter extends CountDownTimer {

		public TimeCounter(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {

			setSeatTimeTipsText(millisUntilFinished);
		}

		private void setSeatTimeTipsText(long millisUntilFinished) {
			long totalSeconds = millisUntilFinished / Constant.IPTV_TIME_ONE_SECONDE;
			long remainingSeconds = totalSeconds % Constant.IPTV_TIME_ONE_MINUTE_IN_SECOND;
			long remainingMinutes = (totalSeconds / Constant.IPTV_TIME_ONE_MINUTE_IN_SECOND) % Constant.IPTV_TIME_ONE_HOUR_IN_MINUTE;
			long remainingHours = totalSeconds / Constant.IPTV_TIME_ONE_HOUR_IN_SECOND;
			if (remainingHours >= 1) {
				mSeatTimeTipsText.setText(remainingHours + "" + getString(R.string.seat_time_hour) + remainingMinutes + ""
						+ getString(R.string.seat_time_minute) + remainingSeconds + "" + getString(R.string.seat_time_second));
			} else {
				mSeatTimeTipsText.setText(remainingMinutes + "" + getString(R.string.seat_time_minute) + remainingSeconds + ""
						+ getString(R.string.seat_time_second));
			}
		}

		@Override
		public void onFinish() {
			getActivity().finish();
		}

	}

	@Override
	public void onStop() {
		mTimeCounter.cancel();
		getActivity().finish();
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// mHandler.removeMessages(Constant.IPTV_MSG_HSJQ_SEAT_TIME_TAG);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		mTimeCounter.cancel();
		getActivity().finish();
		return true;
	}

}

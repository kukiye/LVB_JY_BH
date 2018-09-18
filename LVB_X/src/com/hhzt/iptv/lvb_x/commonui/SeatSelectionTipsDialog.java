/**
 * 作 者：Kobe
 * 日 期：20152015年6月3日下午8:20:10
 * 包 名：com.hhzt.iptv.lvb_x.commonui
 * 工程名：MediaDetector
 * 文件名：SeatSelectionTipsDialog.java
 */
package com.hhzt.iptv.lvb_x.commonui;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;

public class SeatSelectionTipsDialog extends Dialog {

	private TextView mSeatTimeTipsText;
	private TimeCounter mTimeCounter;
	private int mSeatTime = Constant.HSJQ_DEFAULT_SEAT_TIME;
	private OnFinishListener mFinishListener;

	public SeatSelectionTipsDialog(Activity context, int theme, OnFinishListener finishListener) {
		super(context, theme);
		mFinishListener = finishListener;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_seat_tips);
		findViewsById();
		setTimeTips();
	}

	private void findViewsById() {
		mSeatTimeTipsText = (TextView) findViewById(R.id.seat_time);
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
				mSeatTimeTipsText.setText(remainingHours + "" + LVBXApp.getApp().getString(R.string.seat_time_hour) + remainingMinutes + ""
						+ LVBXApp.getApp().getString(R.string.seat_time_minute) + remainingSeconds + ""
						+ LVBXApp.getApp().getString(R.string.seat_time_second));
			} else {
				mSeatTimeTipsText.setText(remainingMinutes + "" + LVBXApp.getApp().getString(R.string.seat_time_minute) + remainingSeconds + ""
						+ LVBXApp.getApp().getString(R.string.seat_time_second));
			}
		}

		@Override
		public void onFinish() {
			mFinishListener.onFinish();
			dismiss();
		}
	}

	@Override
	public void onStop() {
		mTimeCounter.cancel();
		mFinishListener.onFinish();
		super.onStop();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		mTimeCounter.cancel();
		mFinishListener.onFinish();
		dismiss();
		return true;
	}

	public interface OnFinishListener {
		public void onFinish();
	}

}

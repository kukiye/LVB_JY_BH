package com.hhzt.iptv.lvb_x.customview;

import com.hhzt.iptv.lvb_x.mgr.MsgPushMgr;
import com.hhzt.iptv.lvb_x.model.NewsSingleItem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class MsgRollTextView extends TextView {

	private float mTextLength = 0f;// 文本长度
	private float mViewWidth = 0f;// textview长度
	private float mStep = 0f;
	private float mY = 0f;// 文字的纵坐标
	private float temp_view_plus_text_length = 0.0f;// 用于计算的临时变量
	private float temp_view_plus_two_text_length = 0.0f;// 用于计算的临时变量
	public boolean isStarting = false;// 是否开始滚动
	private Paint paint = null;// 绘图样式
	private String mText = "";// 文本内容

	public MsgRollTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MsgRollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MsgRollTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void init(WindowManager windowManager, String text) {
		paint = getPaint();
		paint.setColor(Color.WHITE);
		mViewWidth = getWidth();
		if (mViewWidth == 0) {
			if (windowManager != null) {
				Display display = windowManager.getDefaultDisplay();
				mViewWidth = display.getWidth();
			}
		}
		mY = getTextSize() + 3;
		mText = text;
		setTexts(text);
	}

	private void setTexts(String text) {
		mText = text;
		mTextLength = paint.measureText(mText);
		mStep = mTextLength;
		temp_view_plus_text_length = mViewWidth + mTextLength;
		temp_view_plus_two_text_length = mViewWidth + mTextLength * 2;
	}

	public void startScroll() {
		isStarting = true;
		invalidate();
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (isStarting) {
			if (mStep > temp_view_plus_two_text_length) {
				setScollAgain();  
			} else {
				if (isStarting) {
					canvas.drawText(mText, temp_view_plus_text_length - mStep,
							mY, paint);
					mStep += 2;// 文字滚动速度。
					invalidate();
				}
			}
		}
	}

	private void setScollAgain() {
		synchronized (MsgPushMgr.mNewsSingleItemList) {
			setTexts(mText);
			if (isStarting) {
				invalidate();
			}
		}
	}

}

package com.hhzt.iptv.lvb_x.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.interfaces.INewOnSuccessCB;
import com.hhzt.iptv.lvb_x.mgr.MsgPushMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.NewsDataItems;
import com.hhzt.iptv.lvb_x.model.NewsSingleItem;

/**
 * 跑马灯形式的TextView
 * 
 */
public class RollTextView extends TextView {

	private float mTextLength = 0f;// 文本长度
	private float mViewWidth = 0f;// textview长度
	private float mStep = 0f;
	private float mY = 0f;// 文字的纵坐标
	private float temp_view_plus_text_length = 0.0f;// 用于计算的临时变量
	private float temp_view_plus_two_text_length = 0.0f;// 用于计算的临时变量
	public boolean isStarting = false;// 是否开始滚动
	private Paint paint = null;// 绘图样式
	private String mText = "";// 文本内容
	private MsgPushMgr mMsgPushMgr;
	boolean flag=true;
	
	@SuppressLint("HandlerLeak")
	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				setTexts();
				if (isStarting) {
					invalidate();
				}	
				break;
			default:
				break;
			}
			
		}
	};

	public RollTextView(Context context) {
		super(context);
	}

	public RollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RollTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void init(WindowManager windowManager, MsgPushMgr msgPushMgr) {
		this.mMsgPushMgr = msgPushMgr;
		paint = getPaint();
		paint.setColor(Color.WHITE);
		if(MsgPushMgr.mNewsSingleItemList.get(0).getFont() < 15){
			paint.setTextSize(15);
		}else if(MsgPushMgr.mNewsSingleItemList.get(0).getFont() > 50){
			paint.setTextSize(50);
		}else{
			paint.setTextSize(MsgPushMgr.mNewsSingleItemList.get(0).getFont());
		}
		
		mViewWidth = getWidth();
		if (mViewWidth == 0) {
			if (windowManager != null) {
				Display display = windowManager.getDefaultDisplay();
				mViewWidth = display.getWidth();
			}
		}
		mY = getTextSize() + 10;
		setTexts();
		flag=false;
	}

	private void setTexts() {
		mText = MsgPushMgr.mNewsSingleItemList.get(0).getContent();
		mTextLength = paint.measureText(mText);
		mStep = mTextLength;
		temp_view_plus_text_length = mViewWidth + mTextLength;
		temp_view_plus_two_text_length = mViewWidth + mTextLength * 2;
		
		paint = getPaint();
		if (MsgPushMgr.mNewsSingleItemList.get(0).getImportant() == 0) {
			paint.setColor(Color.WHITE);
		} else if (MsgPushMgr.mNewsSingleItemList.get(0).getImportant() == 1) {
			paint.setColor(Color.parseColor("#ffd400"));
		} else {
			paint.setColor(Color.parseColor("#ff001f"));
		}
		Drawable drawable= LVBXApp.getApp().getResources().getDrawable(R.drawable.mainpage_title_logo);
		setCompoundDrawablePadding(5);
		setCompoundDrawables(drawable,null,null,null);
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
			int loopnum = MsgPushMgr.mNewsSingleItemList.get(0).getLoopnumber();
			String min = MsgPushMgr.mNewsSingleItemList.get(0).getLoopintervaltime();
			loopnum--;
			MsgPushMgr.mNewsSingleItemList.get(0).setLoopnumber(loopnum);
			if (loopnum == 0) {
				NewsSingleItem item = MsgPushMgr.mNewsSingleItemList.remove(0);
				MsgPushMgr.mNewsSingleItemList.clear();
				setRead(item);
				if (MsgPushMgr.mNewsSingleItemList.size() > 0) {
					setTexts();
					if (isStarting) {
						invalidate();
					}
				} else {
					isStarting = false;
					mMsgPushMgr.hideView();
					//新增
					mMsgPushMgr.removeView();
				}
			} else if (loopnum > 0) {
				if(flag){
					flag=false;
					setTexts();
					if (isStarting) {
						invalidate();
					}	
				}else{
//					try {
						 int delaytimes = Integer.valueOf(min).intValue();
						 Message msg=new Message();
						 msg.what = 1;
						 mHandler.sendMessageDelayed(msg, delaytimes*1000);
						 //由线程睡眠改为handler延时改善了线程休眠时back键不起作用。
						//Thread.sleep(delaytimes*1000);
//						setTexts();
//						if (isStarting) {
//							invalidate();
//						}	
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
				}
			}
		}
	}

	private void setRead(final NewsSingleItem item) {
		UIDataller.getDataller().setNewsReadedTag(UserMgr.getUserName(),
				item.getId(), new INewOnSuccessCB() {

					@Override
					public void onSuccess(NewsDataItems newsDatas) {

					}
				});
	}
}

package com.hhzt.iptv.lvb_x.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class VodItemSingle extends LinearLayout {

    private Context mContext;
    // 当前位置
    private int mCurrentPosition = 0;
    @ViewInject(R.id.item_btn)
    private Button mMoviePicButton;

    @ViewInject(R.id.item_img)
    private ImageView mMovieImageView;

    @ViewInject(R.id.vod_item_name)
    private TextView mMovieNameTextView;
    @ViewInject(R.id.vod_item_show_relative)
    private RelativeLayout mRelativeLayout;

    @ViewInject(R.id.vod_item_linear)
    private LinearLayout mVodItem;

    public VodItemSingle(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public VodItemSingle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vod_item_single, this, true);
        ViewUtils.inject(this, view);
    }

    public void setValues(String imgUrl, String text, int currentPosition, int width, int height) {
        mMovieNameTextView.setText(text);
        BitmapUtil.setFadeInImage(imgUrl, mMovieImageView);
        LayoutParams params = new LayoutParams(width, height);
        mVodItem.setLayoutParams(params);
        mCurrentPosition = currentPosition;
    }


    /**
     * 设置padding
     */
    public void setPadding(int left, int top, int right, int bottom) {
        mVodItem.setPadding(left, top, right, bottom);
    }

    /**
     * 设置间隔
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setMargin(int left, int top, int right, int bottom) {
        LayoutParams params = (LayoutParams) mVodItem.getLayoutParams();
        params.setMargins(left, top, right, bottom);
        mVodItem.setLayoutParams(params);
    }

    public void hideNameText() {
        mMovieNameTextView.setVisibility(View.GONE);
    }

    public void setTextSelect(boolean flag) {
        mMovieNameTextView.setSelected(flag);
    }

    public void setImageValue(String imgUrl) {
        BitmapUtil.setRoundFadeInImage(imgUrl, mMovieImageView);
    }

    public void setImageValue(int resid) {
        mMovieImageView.setImageResource(resid);
    }

    public int getPosition() {
        return mCurrentPosition;
    }

    public void setButtonSelector(int resId) {
        mMoviePicButton.setBackgroundResource(resId);
    }

    public void setItemButtonFocus() {
        mMoviePicButton.requestLayout();
        mMoviePicButton.requestFocus();
    }

    public void setOnClickListner(final OnClickListener listner) {
        mMoviePicButton.setOnClickListener(listner);
        mMoviePicButton.setOnTouchListener(new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setItemButtonFocus();
                        break;
                    case MotionEvent.ACTION_UP:
                        listner.onClick(v);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void setOnFocusListner(OnFocusChangeListener listner) {
        mMoviePicButton.setOnFocusChangeListener(listner);
    }

    public void setOnKeyListner(OnKeyListener listner) {
        mMoviePicButton.setOnKeyListener(listner);
    }

}

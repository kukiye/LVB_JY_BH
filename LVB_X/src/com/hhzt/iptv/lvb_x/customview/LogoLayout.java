package com.hhzt.iptv.lvb_x.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LogoLayout extends LinearLayout {

    private Context mContext;
    // 当前位置
    private int mCurrentPosition = 0;
    @ViewInject(R.id.item_btn)
    private Button mButton;

    @ViewInject(R.id.item_logo)
    private ImageView mLogoImageView;
    @ViewInject(R.id.item_text)
    private TextView mNameTv;


    public LogoLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public LogoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.logo_layout, this, true);
        ViewUtils.inject(this, view);
    }

    public void setNetInfo(String logoImgUrl, String modelName) {
        Glide.with(mContext).load(logoImgUrl).into(mLogoImageView);

        if (!StringUtil.isNull(modelName)) {
            int length = modelName.length();
            if (length > 4) {
                ViewGroup.LayoutParams layoutParams = mNameTv.getLayoutParams();
                layoutParams.width = LayoutParams.WRAP_CONTENT;
                mNameTv.setLayoutParams(layoutParams);
            } else {
                ViewGroup.LayoutParams layoutParams = mNameTv.getLayoutParams();
                layoutParams.width = (int) getResources().getDimension(R.dimen.layx130);
                mNameTv.setLayoutParams(layoutParams);
            }

            mNameTv.setText(modelName);
        }


    }

    public void setLocalImage(int logoImgResorceId, int textImgImgResorceId) {
        mLogoImageView.setImageResource(logoImgResorceId);

    }


    public void setItemButtonFocus() {
        mButton.requestLayout();
        mButton.requestFocus();
    }

    public void setOnClickListner(final OnClickListener listner) {
        mButton.setOnClickListener(listner);
        mButton.setOnTouchListener(new OnTouchListener() {

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
        mButton.setOnFocusChangeListener(listner);
    }

    public void setOnKeyListner(OnKeyListener listner) {
        mButton.setOnKeyListener(listner);
    }

}

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
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import static com.hhzt.iptv.R.id.item_name;

public class HomeStarLayout extends LinearLayout {

    private Context mContext;
    // 当前位置
    private int mCurrentPosition = 0;
    @ViewInject(R.id.item_btn)
    private Button mButton;

    @ViewInject(R.id.item_image)
    private ImageView mIvStar;
    @ViewInject(item_name)
    private TextView mTvStarName;

    @ViewInject(R.id.item_layout)
    private LinearLayout mItemLayout;


    public HomeStarLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeStarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_star_layout, this, true);
        ViewUtils.inject(this, view);
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
        LayoutParams params = (LayoutParams) mItemLayout.getLayoutParams();
        params.setMargins(left, top, right, bottom);
        mItemLayout.setLayoutParams(params);
    }

        public void setImageViewSize(int width, int height) {
            LayoutParams params = (LayoutParams) mIvStar.getLayoutParams();
            params.width = width;
            params.weight = height;
            mIvStar.setLayoutParams(params);
        }

    public void setNetImage(String logoImgUrl, String name) {
        BitmapUtil.setFadeInImage(logoImgUrl, mIvStar);
        mTvStarName.setText(name);

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
        mIvStar.setOnFocusChangeListener(listner);
    }

    public void setOnKeyListner(OnKeyListener listner) {
        mIvStar.setOnKeyListener(listner);
    }

}

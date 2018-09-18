package com.hhzt.iptv.lvb_x.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.VodTypeItemModel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HomeDiamondLayout extends LinearLayout {

    private Context mContext;
    // 当前位置
    private int mCurrentPosition = 0;
    @ViewInject(R.id.item_btn)
    private Button mButton;

    @ViewInject(R.id.item_logo)
    private ImageView mLogoImageView;
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    private VodTypeItemModel mainmenuModel;


    public HomeDiamondLayout(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HomeDiamondLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_diamonds_layout, this, true);
        ViewUtils.inject(this, view);
    }

    public void setNetImage(String logoImgUrl, String name) {
        Glide.with(mContext).load(logoImgUrl).into(mLogoImageView);

        mTvName.setText(name);

    }

    public void setItemButtonFocus() {
        mButton.requestLayout();
        mButton.requestFocus();
    }

    public void setClickListener(OnClickListener listner) {
        mButton.setOnClickListener(listner);
        //        mButton.setOnTouchListener(new OnTouchListener() {
        //
        //            @SuppressLint("ClickableViewAccessibility")
        //            @Override
        //            public boolean onTouch(View v, MotionEvent event) {
        //                switch (event.getAction()) {
        //                    case MotionEvent.ACTION_DOWN:
        //                        setItemButtonFocus();
        //                        break;
        //                    case MotionEvent.ACTION_UP:
        //                        listner.onClick(v);
        //                        break;
        //                    default:
        //                        break;
        //                }
        //                return true;
        //            }
        //        });
    }

    public void setOnFocusListner(OnFocusChangeListener listner) {
        mButton.setOnFocusChangeListener(listner);
    }

    public void setKeyListner(OnKeyListener listner) {
        mButton.setOnKeyListener(listner);
    }

    public void setModel(VodTypeItemModel mainmenuModel) {
        this.mainmenuModel = mainmenuModel;
        setNetImage(mainmenuModel.getTypeimage(), mainmenuModel.getCategoryname());
    }

    public VodTypeItemModel getModel() {
        return mainmenuModel;
    }
}

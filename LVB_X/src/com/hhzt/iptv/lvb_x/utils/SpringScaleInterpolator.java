package com.hhzt.iptv.lvb_x.utils;

import android.view.animation.Interpolator;

/**
 * Created by Administrator on 2017-09-20.
 */

public class SpringScaleInterpolator implements Interpolator {
    //弹性因数
    private float factor;

    public SpringScaleInterpolator(float factor) {
        this.factor = factor;
    }

    @Override
    public float getInterpolation(float input) {

        return (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
    }

}

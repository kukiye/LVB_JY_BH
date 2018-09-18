package com.hhzt.iptv.lvb_x.effect;

import com.hhzt.iptv.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomPathAnimation {

	public static void start(Context context, int[] startLocation, int[] endLocation) {
		ImageView orderAnimImage = new ImageView(context);
		orderAnimImage.setBackgroundResource(R.drawable.addshopcart_sign);
		setAnimationPath(orderAnimImage, context, startLocation, endLocation);
	}

	private static void setAnimationPath(final View v, Context context, int[] startLocation, int[] endLocation) {
		ViewGroup animViewGroup = creatAnimationLayout(context);
		animViewGroup.addView(v);
		View view = addViewToAnimationLayout(animViewGroup, v, startLocation);

		TranslateAnimation translateAnimationX = new TranslateAnimation(0, endLocation[0] - startLocation[0], 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setFillAfter(true);
		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endLocation[1] - startLocation[1]);
		translateAnimationY.setInterpolator(new AccelerateDecelerateInterpolator());
		translateAnimationY.setFillAfter(true);

		ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.05f, 1.0f, 0.05f);
		// Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(800);

		AnimationSet set = new AnimationSet(true);
		set.addAnimation(translateAnimationX);
		set.addAnimation(translateAnimationY);
		// set.addAnimation(scaleAnimation);
		set.setDuration(800);
		view.startAnimation(set);

		set.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
			}
		});

	}

	private static View addViewToAnimationLayout(ViewGroup vg, View v, int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		v.setLayoutParams(lp);
		return v;
	}

	private static ViewGroup creatAnimationLayout(Context context) {
		ViewGroup rootView = (ViewGroup) ((Activity) context).getWindow().getDecorView();
		LinearLayout ll = new LinearLayout(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		ll.setLayoutParams(lp);
		ll.setId(Integer.MAX_VALUE);
		ll.setBackgroundResource(android.R.color.transparent);
		rootView.addView(ll);
		return ll;
	}

}

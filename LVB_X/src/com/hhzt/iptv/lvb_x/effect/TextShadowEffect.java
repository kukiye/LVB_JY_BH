package com.hhzt.iptv.lvb_x.effect;

import android.widget.TextView;

public class TextShadowEffect {
	private static TextShadowEffect mInstance = new TextShadowEffect();

	private TextShadowEffect() {

	}

	public static TextShadowEffect getInstance() {
		if (null == mInstance) {
			synchronized (TextShadowEffect.class) {
				if (null == mInstance) {
					mInstance = new TextShadowEffect();
				}
			}
		}

		return mInstance;
	}

	//
	public void setBlueShadow(float radius, float dx, float dy, int color, TextView... textView) {
		for (int i = 0; i < textView.length; i++) {
			textView[i].setShadowLayer(radius, dx, dy, color);
		}
	}
}

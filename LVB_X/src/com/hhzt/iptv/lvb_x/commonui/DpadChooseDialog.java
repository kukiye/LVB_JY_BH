package com.hhzt.iptv.lvb_x.commonui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.interfaces.IDpadOnclickListner;

public class DpadChooseDialog extends Dialog implements android.view.View.OnClickListener {

	private LinearLayout mDpadChooseContainer;

	public DpadChooseDialog(Context context, int theme) {
		super(context, theme);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCanceledOnTouchOutside(false);
		setAnimation();

	}

	public DpadChooseDialog(Context context, int theme, String dpadString, final IDpadOnclickListner onClickLisner) {
		super(context, theme);

		setContentView(R.layout.dpad_choose);
		mDpadChooseContainer = (LinearLayout) findViewById(R.id.dpad_choose_container);

		int dialogWidth = 0;
		for (int i = 0; i < dpadString.length(); i++) {
			int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.dpad_choose_item, null);
			final Button button = (Button) view.findViewById(R.id.dpad_item);
			button.setText(dpadString.substring(i, i + 1));
			button.measure(w, h);

			button.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					onClickLisner.onclick(button);
					dismiss();
				}
			});
			mDpadChooseContainer.addView(view);

			dialogWidth += button.getMeasuredWidth();
		}

		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = dialogWidth;
	}

	private void setAnimation() {
		getWindow().setWindowAnimations(R.style.dialogWindowAnim);
	}

	@Override
	public void onClick(View v) {

	}

}

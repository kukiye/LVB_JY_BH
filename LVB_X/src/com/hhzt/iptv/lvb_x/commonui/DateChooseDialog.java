/**
 * 作者：   Johnson
 * 日期：   2014年7月9日下午7:17:28
 * 包名：    com.hhzt.iptv.lvb_x.commonui
 * 工程名：LVB_X
 * 文件名：DateChooseDialog.java
 */
package com.hhzt.iptv.lvb_x.commonui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.adapter.DateChooseAdapter;
import com.hhzt.iptv.lvb_x.interfaces.IListItemOnClickCB;
import com.hhzt.iptv.lvb_x.utils.DateTimeUtil;

public class DateChooseDialog extends Dialog implements OnItemClickListener {

	private String mtitle;
	private Context mContext;
	private View mBgView;
	private IListItemOnClickCB mOnClickCB;

	private TextView mTitleTextView;
	private ListView mDateListView;
	private long mBeginTime;

	public DateChooseDialog(Context context, int theme, View bgView, String title, long beginTime, IListItemOnClickCB onClick) {
		super(context, theme);
		mtitle = title;
		mBgView = bgView;
		mContext = context;
		mBeginTime = beginTime;
		mOnClickCB = onClick;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sub_datechoose);
		findViewsById();
		setAllValues();
		setListners();
		setAnimation();
	}

	private void findViewsById() {
		mTitleTextView = (TextView) findViewById(R.id.date_title);
		mDateListView = (ListView) findViewById(R.id.choosedDatelist);
	}

	private void setAllValues() {
		mTitleTextView.setText(mtitle);

		DateChooseAdapter adapter = new DateChooseAdapter(mContext, DateTimeUtil.initDateTimeFormat(mBeginTime, Constant.IPTV_SYSTEM_DATE_NUM,
				DateTimeUtil.DATE_FORMATE_YEAR_MONTH_DAY));
		mDateListView.setAdapter(adapter);
	}

	private void setListners() {
		mDateListView.setOnItemClickListener(this);
	}

	private void setAnimation() {
		getWindow().setWindowAnimations(R.style.dialogWindowAnim);
	}

	@Override
	public void dismiss() {
		super.dismiss();

		if (null != mBgView) {
			mBgView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		dismiss();
		mOnClickCB.onClick(arg2);
	}

}

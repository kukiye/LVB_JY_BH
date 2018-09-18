package com.hhzt.iptv.lvb_x.commonui;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.adapter.UserChooseAdapter;
import com.hhzt.iptv.lvb_x.interfaces.IBeanOnSuccessCB;
import com.hhzt.iptv.lvb_x.model.UserInfoModel;
import com.lidroid.xutils.DbUtils;

public class UserChooseDialog extends Dialog implements OnItemClickListener {

	private Context mContext;
	private ListView mDateListView;
	private List<UserInfoModel> mDatas;
	private IBeanOnSuccessCB<UserInfoModel> mSuccessCB;

	public UserChooseDialog(Context context, int theme, List<UserInfoModel> models, 
			IBeanOnSuccessCB<UserInfoModel> successCB) {
		super(context, theme);
		mContext = context;
		mDatas = models;
		mSuccessCB = successCB;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sub_user_choose);
		findViewsById();
		setAllValues();
		setListners();
		setAnimation();
	}

	private void findViewsById() {
		mDateListView = (ListView) findViewById(R.id.choosedDatelist);
	}

	private void setAllValues() {
		UserChooseAdapter adapter = new UserChooseAdapter(mContext, mDatas);
		mDateListView.setAdapter(adapter);
	}

	private void setListners() {
		mDateListView.setOnItemClickListener(this);
	}

	private void setAnimation() {
		getWindow().setWindowAnimations(R.style.dialogWindowAnim);
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		dismiss();
		if(mSuccessCB != null){
			mSuccessCB.onSuccess(mDatas.get(arg2));
		}
	}

}

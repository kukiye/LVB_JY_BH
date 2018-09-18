/**
 * 作者：   Johnson
 * 日期：   2014年7月9日下午7:53:03
 * 包名：    com.hhzt.iptv.lvb_x.adapter
 * 工程名：LVB_X
 * 文件名：DateChooseAdapter.java
 */
package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.model.UserInfoModel;

public class UserChooseAdapter extends BaseAdapter {

	private Context mContext;
	private List<UserInfoModel> mDatas;

	public UserChooseAdapter(Context context, List<UserInfoModel> models) {
		mContext = context;
		mDatas = models;
		if (mDatas == null) {
			mDatas = new ArrayList<UserInfoModel>();
		}
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		DateChooseHolder holder = null;
		if (null == arg1) {
			holder = new DateChooseHolder();
			arg1 = LayoutInflater.from(mContext).inflate(
					R.layout.sub_userchoose_subitem, null);
			holder.username = (TextView) arg1.findViewById(R.id.username);
			holder.nickname = (TextView) arg1.findViewById(R.id.nickname);
			arg1.setTag(holder);
		} else {
			holder = (DateChooseHolder) arg1.getTag();
		}

		holder.username.setText(String.format(mContext
				.getString(R.string.prison_user_name), mDatas.get(arg0)
				.getNickname()));
		holder.nickname.setText(String.format(mContext
						.getString(R.string.prison_user_code), mDatas.get(arg0)
						.getUsername()));
		return arg1;
	}

	final class DateChooseHolder {
		public TextView username;
		public TextView nickname;
	}

}

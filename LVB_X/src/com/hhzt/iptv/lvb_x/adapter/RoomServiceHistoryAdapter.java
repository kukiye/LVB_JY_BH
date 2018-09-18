/**
 * 作者：   Johnson
 * 日期：   2014年7月9日下午4:38:05
 * 包名：    com.hhzt.iptv.lvb_x.adapter
 * 工程名：LVB_X
 * 文件名：RoomServiceHistoryAdapter.java
 */
package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.model.RoomServiceModel;
import com.hhzt.iptv.lvb_x.utils.DateTimeUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RoomServiceHistoryAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<RoomServiceModel> mRoomServiceModels;

	public RoomServiceHistoryAdapter(Context context) {
		mContext = context;
	}

	public void setListData(ArrayList<RoomServiceModel> roomServiceModels) {
		mRoomServiceModels = roomServiceModels;
	}

	@Override
	public int getCount() {
		return mRoomServiceModels.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mRoomServiceModels.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return mRoomServiceModels.get(arg0).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		HistoryDataHolder holder = null;
		if (null == convertView) {
			holder = new HistoryDataHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.sub_roomservice_history, null);
			holder.historyDes = (TextView) convertView.findViewById(R.id.roomservice_des);
			holder.clickCancel = (TextView) convertView.findViewById(R.id.roomservice_click_cancel);

			convertView.setTag(holder);
		} else {
			holder = (HistoryDataHolder) convertView.getTag();
		}

		setHistoryDes(holder, position);
		return convertView;
	}

	private void setHistoryDes(HistoryDataHolder holder, int position) {
		String dateTime = DateTimeUtil.toTime(mRoomServiceModels.get(position).getReqtime(), DateTimeUtil.DATE_FORMATE_YMD_TIME);
		String desDateTime = getDateTimeDes(mRoomServiceModels.get(position).getReqtype(), position);
		String tempdes = getTemDescription(mRoomServiceModels.get(position).getReqtype());
		String statusDes = getServiceStaus(mRoomServiceModels.get(position).getReqstatus());

		String realDes = dateTime + " " + tempdes + LVBXApp.getApp().getString(R.string.to) + desDateTime + " " + "(" + statusDes + ")";
		holder.historyDes.setText(realDes);

		// 只有第一项是白色字体
		if (0 == position) {
			int serviceStatus = mRoomServiceModels.get(0).getReqstatus();
			if (serviceStatus == Constant.IPTV_ROOMSERVICE_STATUS_WAITING) {
				holder.clickCancel.setText(R.string.click_cancel);
				holder.historyDes.setTextColor(Color.WHITE);
				holder.clickCancel.setTextColor(Color.WHITE);
			} else {
				holder.clickCancel.setText(R.string.expiry_tips);
				holder.historyDes.setTextColor(Color.GRAY);
				holder.clickCancel.setTextColor(Color.GRAY);
			}
		} else {
			holder.clickCancel.setText(R.string.expiry_tips);
			holder.historyDes.setTextColor(Color.GRAY);
			holder.clickCancel.setTextColor(Color.GRAY);
		}
	}

	private String getDateTimeDes(int reqtype, int position) {
		String resultTimeDes = "";
		switch (reqtype) {
		case Constant.IPTV_ROOMSERVICE_TYPE_CHECK_OUT:
		case Constant.IPTV_ROOMSERVICE_TYPE_CHECK_IN:
			resultTimeDes = DateTimeUtil.toTime(mRoomServiceModels.get(position).getAppointmenttime(), DateTimeUtil.DATE_FORMATE_YEAR_MONTH_DAY);
			break;
		case Constant.IPTV_ROOMSERVICE_TYPE_WAKEUP:
		case Constant.IPTV_ROOMSERVICE_TYPE_CLEAN:
			resultTimeDes = DateTimeUtil.toTime(mRoomServiceModels.get(position).getAppointmenttime(), DateTimeUtil.DATE_FORMATE_HOUR_MINUTE);
			break;
		default:
			break;
		}

		return resultTimeDes;
	}

	private String getTemDescription(int reqtype) {
		String tempdes = "";
		switch (reqtype) {
		case Constant.IPTV_ROOMSERVICE_TYPE_CHECK_OUT:
			tempdes = LVBXApp.getApp().getString(R.string.commit_check_out);
			break;
		case Constant.IPTV_ROOMSERVICE_TYPE_CHECK_IN:
			tempdes = LVBXApp.getApp().getString(R.string.commit_check_in);
			break;
		case Constant.IPTV_ROOMSERVICE_TYPE_WAKEUP:
			tempdes = LVBXApp.getApp().getString(R.string.commit_wakeup);
			break;
		case Constant.IPTV_ROOMSERVICE_TYPE_CLEAN:
			tempdes = LVBXApp.getApp().getString(R.string.commit_clean);
			break;
		default:
			break;
		}

		return tempdes;
	}

	private String getServiceStaus(int reqstatus) {
		String status = "";
		switch (reqstatus) {
		case Constant.IPTV_ROOMSERVICE_STATUS_UNKNOWN:
			status = LVBXApp.getApp().getString(R.string.status_unknow);
			break;
		case Constant.IPTV_ROOMSERVICE_STATUS_WAITING:
			status = LVBXApp.getApp().getString(R.string.status_waiting);
			break;
		case Constant.IPTV_ROOMSERVICE_STATUS_DOING:
			status = LVBXApp.getApp().getString(R.string.status_doing);
			break;
		case Constant.IPTV_ROOMSERVICE_STATUS_FINISHED:
			status = LVBXApp.getApp().getString(R.string.status_finish);
			break;
		case Constant.IPTV_ROOMSERVICE_STATUS_CANCELED:
			status = LVBXApp.getApp().getString(R.string.status_cancel);
			break;
		}

		return status;
	}

	final class HistoryDataHolder {
		private TextView historyDes;
		private TextView clickCancel;
	}
}

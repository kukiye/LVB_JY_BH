/**
 * 作者：   Johnson
 * 日期：   2014年6月30日下午7:05:46
 * 包名：    com.hhzt.iptv.lvb_x.adapter
 * 工程名：LVB_X
 * 文件名：SubLiveListAdapter.java
 */
package com.hhzt.iptv.lvb_x.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.model.LiveSubModel;
import com.hhzt.iptv.lvb_x.utils.DateTimeUtil;

public class LiveSubListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<LiveSubModel> mLiveSubModels;
	private int mMediaType;
	private String mUrl;
	private int mChannelId;
	private String mLiveBackTag;

	public LiveSubListAdapter(Context context) {
		mContext = context;
	}

	public void initAdapter(int type, String url, int id, String tag) {
		mMediaType = type;
		mUrl = url;
		mChannelId = id;
		mLiveBackTag = tag;
	}

	public void setDataList(ArrayList<LiveSubModel> liveSubModels) {
		mLiveSubModels = liveSubModels;
	}

	@Override
	public int getCount() {
		return mLiveSubModels.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mLiveSubModels.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return mLiveSubModels.get(arg0).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LiveMainHolder holder = null;
		if (null == convertView) {
			holder = new LiveMainHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.sub_sublive_item, null);
			holder.programTime = (TextView) convertView.findViewById(R.id.program_time);
			holder.programName = (TextView) convertView.findViewById(R.id.program_name);
			holder.backTagView = (RelativeLayout) convertView.findViewById(R.id.look_back_tag_layout);
			holder.programStateImage = (ImageView) convertView.findViewById(R.id.program_state_img);
			holder.backTagText = (TextView) convertView.findViewById(R.id.back_tag_text);
			convertView.setTag(holder);
		} else {
			holder = (LiveMainHolder) convertView.getTag();
		}
		setAllValues(holder, position);
		return convertView;
	}

	private void setAllValues(LiveMainHolder holder, int position) {
		long milliseconds = Long.valueOf(mLiveSubModels.get(position).getStarttime());
		String startTime = DateTimeUtil.toTime(milliseconds, DateTimeUtil.DATE_FORMATE_HOUR_MINUTE);
		holder.programTime.setText(startTime);
		holder.programName.setText(mLiveSubModels.get(position).getSchedulename());
		setProgramTag(holder, mLiveSubModels.get(position).getPlaystatus(), position);
	}

	private void setProgramTag(LiveMainHolder holder, String stateTag, int position) {
		if (mLiveBackTag.equalsIgnoreCase("0")) {
			holder.backTagView.setVisibility(View.GONE);
		} else if (mLiveBackTag.equalsIgnoreCase("1")) {
			switch (mMediaType) {
			case Constant.IPTV_MEDIA_TYPE_LIVE:
				if (Constant.LIVE_BACK_ABLE_TAG.equalsIgnoreCase(stateTag)) {
					holder.programStateImage.setBackgroundResource(R.drawable.live_back_tag);
					holder.backTagText.setText(mContext.getString(R.string.back_text));
					holder.backTagView.setVisibility(View.VISIBLE);
				} else if (Constant.LIVE_PLAYING_TAG.equalsIgnoreCase(stateTag)) {
					if (mLiveSubModels.get(position).getChannelid() == mChannelId) {
						holder.programStateImage.setBackgroundResource(R.drawable.live_playing_tag);
						holder.backTagText.setText(mContext.getString(R.string.playing_text));
					} else {
						holder.programStateImage.setBackgroundResource(R.drawable.live_back_tag);
						holder.backTagText.setText(mContext.getString(R.string.live_text));
					}
					holder.backTagView.setVisibility(View.VISIBLE);
				} else {
					holder.backTagView.setVisibility(View.GONE);
				}
				break;
			case Constant.IPTV_MEDIA_TYPE_LIVE_BACK:
				if (Constant.LIVE_BACK_ABLE_TAG.equalsIgnoreCase(stateTag)) {
					if (mLiveSubModels.get(position).getTvodurl().equalsIgnoreCase(mUrl)) {
						holder.programStateImage.setBackgroundResource(R.drawable.live_playing_tag);
						holder.backTagText.setText(mContext.getString(R.string.back_playing_text));
					} else {
						holder.programStateImage.setBackgroundResource(R.drawable.live_back_tag);
						holder.backTagText.setText(mContext.getString(R.string.back_text));
					}
					holder.backTagView.setVisibility(View.VISIBLE);
				} else if (Constant.LIVE_PLAYING_TAG.equalsIgnoreCase(stateTag)) {
					holder.programStateImage.setBackgroundResource(R.drawable.live_back_tag);
					holder.backTagText.setText(mContext.getString(R.string.live_text));
					holder.backTagView.setVisibility(View.VISIBLE);
				} else {
					holder.backTagView.setVisibility(View.GONE);
				}
				break;
			default:
				break;
			}
		}
	}

	final class LiveMainHolder {
		TextView programTime;
		TextView programName;
		RelativeLayout backTagView;
		ImageView programStateImage;
		TextView backTagText;
	}

}

package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.RemoteCMDType;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.mgr.InteractiveMgr;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.lvb_x.mgr.ThreadPoolManager;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.hhzt.iptv.lvb_x.utils.CommonUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HSJQRCFragment extends BaseFragment {

	@ViewInject(R.id.rc_volume_down)
	private Button mVolumeDownButton;
	@ViewInject(R.id.rc_volume_up)
	private Button mVolumeUpButton;
	@ViewInject(R.id.rc_arrow_up)
	private Button mArrowUpButton;
	@ViewInject(R.id.rc_arrow_down)
	private Button mArrowDownButton;
	@ViewInject(R.id.rc_arrow_left)
	private Button mArrowLeftButton;
	@ViewInject(R.id.rc_arrow_right)
	private Button mArrowRightButton;
	@ViewInject(R.id.rc_menu)
	private Button mMenuButton;
	@ViewInject(R.id.rc_volume_mute)
	private Button mVolumeMuteButton;
	@ViewInject(R.id.rc_ok)
	private Button mOkButton;

	@ViewInject(R.id.rc_delete)
	private Button mDeleteButton;
	@ViewInject(R.id.rc_back)
	private Button BackButton;
	@ViewInject(R.id.rc_digital_zero)
	private Button mDigitalAeroButton;
	@ViewInject(R.id.rc_digital_one)
	private Button mDigitalOneButton;
	@ViewInject(R.id.rc_digital_two)
	private Button mDigitalTwoButton;
	@ViewInject(R.id.rc_digital_three)
	private Button mDigitalThreeButton;
	@ViewInject(R.id.rc_digital_four)
	private Button mDigitalFourButton;
	@ViewInject(R.id.rc_digital_four)
	private Button mDigitalFiveButton;
	@ViewInject(R.id.rc_digital_five)
	private Button mDigitalSixButton;
	@ViewInject(R.id.rc_digital_six)
	private Button mDigitalSevenButton;
	@ViewInject(R.id.rc_digital_eight)
	private Button mDigitalEightButton;
	@ViewInject(R.id.rc_digital_nine)
	private Button mDigitalNineButton;
	@ViewInject(R.id.rc_digital_star)
	private Button mDigitalStarButton;
	@ViewInject(R.id.rc_digital_pound)
	private Button mDigitalPounButton;

	@ViewInject(R.id.main_type_imgae)
	private ImageView mHomeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mCurrentPathTextView;
	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mActionOkTipsTextView;
	@ViewInject(R.id.tips_back)
	private TextView mActionBackTipsTextView;
	@ViewInject(R.id.seat_select)
	private Button mSelectSeatBtn;
	@ViewInject(R.id.rc_logo)
	private ImageView mRcLogo;

	private String mMenuPath;

	@OnClick({ R.id.rc_volume_down, R.id.rc_volume_up, R.id.rc_arrow_up, R.id.rc_arrow_down, R.id.rc_arrow_left, R.id.rc_arrow_right, R.id.rc_menu,
			R.id.rc_volume_mute, R.id.rc_ok, R.id.rc_delete, R.id.rc_back, R.id.rc_digital_zero, R.id.rc_digital_one, R.id.rc_digital_two,
			R.id.rc_digital_three, R.id.rc_digital_four, R.id.rc_digital_five, R.id.rc_digital_six, R.id.rc_digital_seven, R.id.rc_digital_eight,
			R.id.rc_digital_nine, R.id.rc_digital_star, R.id.rc_digital_pound, R.id.seat_select })
	public void OnClickListner(View view) {
		switch (view.getId()) {
		case R.id.rc_volume_down:
			sentKeycodeValue(RemoteCMDType.PAD_VOLUME_REDUCE);
			break;
		case R.id.rc_volume_up:
			sentKeycodeValue(RemoteCMDType.PAD_VOLUME_ADD);
			break;
		case R.id.rc_arrow_up:
			sentKeycodeValue(RemoteCMDType.PAD_UP);
			break;
		case R.id.rc_arrow_down:
			sentKeycodeValue(RemoteCMDType.PAD_DOWN);
			break;
		case R.id.rc_arrow_left:
			sentKeycodeValue(RemoteCMDType.PAD_LEFT);
			break;
		case R.id.rc_arrow_right:
			sentKeycodeValue(RemoteCMDType.PAD_RIGHT);
			break;
		case R.id.rc_volume_mute:
			sentKeycodeValue(RemoteCMDType.PAD_VOLUME_MUTE);
			break;
		case R.id.rc_ok:
			sentKeycodeValue(RemoteCMDType.PAD_OK);
			break;
		case R.id.rc_menu:
			sentKeycodeValue(RemoteCMDType.PAD_MENU);
			break;
		case R.id.rc_delete:
			sentKeycodeValue(RemoteCMDType.PAD_DELETE);
			break;
		case R.id.rc_back:
			sentKeycodeValue(RemoteCMDType.PAD_BACK);
			break;
		case R.id.rc_digital_zero:
			sentKeycodeValue(RemoteCMDType.PAD_NUM0);
			break;
		case R.id.rc_digital_one:
			sentKeycodeValue(RemoteCMDType.PAD_NUM1);
			break;
		case R.id.rc_digital_two:
			sentKeycodeValue(RemoteCMDType.PAD_NUM2);
			break;
		case R.id.rc_digital_three:
			sentKeycodeValue(RemoteCMDType.PAD_NUM3);
			break;
		case R.id.rc_digital_four:
			sentKeycodeValue(RemoteCMDType.PAD_NUM4);
			break;
		case R.id.rc_digital_five:
			sentKeycodeValue(RemoteCMDType.PAD_NUM5);
			break;
		case R.id.rc_digital_six:
			sentKeycodeValue(RemoteCMDType.PAD_NUM6);
			break;
		case R.id.rc_digital_seven:
			sentKeycodeValue(RemoteCMDType.PAD_NUM7);
			break;
		case R.id.rc_digital_eight:
			sentKeycodeValue(RemoteCMDType.PAD_NUM8);
			break;
		case R.id.rc_digital_nine:
			sentKeycodeValue(RemoteCMDType.PAD_NUM9);
			break;
		case R.id.rc_digital_star:
			sentKeycodeValue(RemoteCMDType.PAD_STAR);
			break;
		case R.id.rc_digital_pound:
			sentKeycodeValue(RemoteCMDType.PAD_POUND);
			break;
		case R.id.seat_select:
			sentSeatSelectionValue();
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_hsjq_rc, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setBottomTopInfos();
	}

	private void setBottomTopInfos() {
		Intent intent = getActivity().getIntent();
		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		UIDataller ller = UIDataller.getDataller();
		mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));
		ller.setHsActionTips(getActivity(), mHomeImageView, R.drawable.home_icon, mCurrentPathTextView, mMenuPath, mActionOkTipsTextView,
				ller.getOkActionTips(getActivity()), mActionBackTipsTextView, ller.getBackActionTips(getActivity()));
		BitmapUtil.setFadeInImage(ConfigMgr.getInstance().getBeanVaule(CCViewConfig.RC_LOGO), mRcLogo);
	}

	private void sentKeycodeValue(final int keyCode) {
		SystemMgr.setControllerVibrate();
		String pairedName = UserMgr.getUserName();
		if (StringUtil.isEmpty(pairedName)) {
			// 进行配对提示
			ActivitySwitchMgr.gotoPairTipsActivity(getActivity(), getString(R.string.interactive_seeting));
		} else {
			// 发送按键模拟操作指令
			ThreadPoolManager.getInstance().addTask(new Runnable() {

				@Override
				public void run() {
					String mainCmd = RemoteCMDType.PAD_CMD + "";
					// 101#15754#99065918#10#78:F7:BE:52:61:2A(cmdType#accountName#interactivePasswd#macAddress#keyCodeValue)
					String contents = mainCmd + "#" + UserMgr.getUserName() + "#" + UserMgr.getInteracPassword() + "#"
							+ CommonUtil.getLocalMacAddress(LVBXApp.getApp()) + "#" + keyCode;
					String ipAdress = UserMgr.getPairedIp();
					InteractiveMgr.getInstance().sendInteractiveRequest(ipAdress, contents);
				}
			});
		}
	}

	/**
	 * 皇室假期新增选座按钮
	 * 
	 * @param keyCode
	 */
	private void sentSeatSelectionValue() {
		SystemMgr.setControllerVibrate();
		String pairedName = UserMgr.getUserName();
		if (StringUtil.isEmpty(pairedName)) {
			// 进行配对提示
			ActivitySwitchMgr.gotoPairTipsActivity(getActivity(), getString(R.string.interactive_seeting));
		} else {
			// 发送按键模拟操作指令
			ThreadPoolManager.getInstance().addTask(new Runnable() {

				@Override
				public void run() {
					String mainCmd = RemoteCMDType.HSJQ_PAD_SEAT_SELECTION + "";
					// 101#15754#99065918#10#78:F7:BE:52:61:2A(cmdType#accountName#interactivePasswd#macAddress#keyCodeValue)
					String contents = mainCmd + "#" + UserMgr.getUserName() + "#" + UserMgr.getInteracPassword() + "#"
							+ CommonUtil.getLocalMacAddress(LVBXApp.getApp());
					String ipAdress = UserMgr.getPairedIp();
					InteractiveMgr.getInstance().sendInteractiveRequest(ipAdress, contents);
				}
			});
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return super.onKeyUp(keyCode, event);
	}
}

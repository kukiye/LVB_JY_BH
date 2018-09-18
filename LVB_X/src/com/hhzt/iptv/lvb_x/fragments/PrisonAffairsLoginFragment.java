package com.hhzt.iptv.lvb_x.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.commonui.UserChooseDialog;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.interfaces.IBeanOnSuccessCB;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.model.UserInfoModel;
import com.hhzt.iptv.lvb_x.model.UserRequestModel;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

public class PrisonAffairsLoginFragment extends BaseFragment {

	// 顶部时钟以及菜单展示
	@ViewInject(R.id.mainmenu_img)
	private ImageView mMainmenuImageView; // 图片
	@ViewInject(R.id.mainmenu_txt)
	private TextView mMainmenuTitleTextView; // 文字
	//时钟
	@ViewInject(R.id.mainmenu_date)
	private TextView mMainmenuDateTextView;
	@ViewInject(R.id.mainmenu_time)
	private TextView mMainmenuTimeTextView;
	@ViewInject(R.id.mainmenu_week)
	private TextView mMainmenuWeekTextView;
	private DateAlarmReceiver mDateAlarmReceiver;
	private IntentFilter mDateAlarmFilter;
	
	@ViewInject(R.id.tv_mainmenu_name)
	private TextView mMainmenuName;
	
	@ViewInject(R.id.ll_prison_contrainer)
	private LinearLayout mPrisonContrainer;
	
	//内容
	@ViewInject(R.id.et_affair_username)
	private EditText mAffairsUsername;
	@ViewInject(R.id.et_affairs_pwd)
	private EditText mAffairsPwd;
	
	private static final String DB_NAME = "old_user.db";
	private static final int DB_VERSION = 1;
	private DbUtils mDb;
	
	@ViewInject(R.id.mainmenu_logo_b)
	private ImageView mMainLoginImageView;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_prison_affairs_login, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			initValue();
			registerReceiver();
			createDBUser();
		}
	}

	//创建数据库
	private void createDBUser() {
		mDb = DbUtils.create(getActivity(), DB_NAME, DB_VERSION, new DbUpgradeListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onUpgrade(DbUtils arg0, int arg1, int arg2) {
				if (arg1 != arg2) {
					try {
						arg0.dropDb();
						arg0.create(getActivity(), Constant.DB_NAME);
					} catch (DbException e) {
						e.printStackTrace();
					}
				}
			}
		});		
	}
	
	/**
	 * 存入数据库中
	 * @param userInfo
	 */
	private void setSqlit(UserInfoModel userInfo){
		try {
			UserInfoModel oldInfo = mDb.findFirst(Selector.from(UserInfoModel.class).where("username", "=", userInfo.getUsername()));
			LogUtil.d("PrisonAffairsLoginFragment---setSqlit=" + oldInfo);
			if(oldInfo == null){
				mDb.saveBindingId(userInfo);
			}else{
				oldInfo.setUsername(userInfo.getUsername());
				oldInfo.setNickname(userInfo.getNickname());
				mDb.saveOrUpdate(oldInfo);
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化数据
	 */
	private void initValue() {
		mMainmenuImageView.setImageResource(R.drawable.affairs_img);
		mMainmenuTitleTextView.setText(getActivity().getString(R.string.affairs_txt));
		mPrisonContrainer.setVisibility(View.GONE);
		String mainmenuName = ConfigMgr.getInstance().getBeanVaule(CCViewConfig.HOTEL_NAME);
		if (mainmenuName != null) {
			mMainmenuName.setText(mainmenuName);
		}

		String mainLogo = ConfigMgr.getInstance().getBeanVaule(
				CCViewConfig.MAINMENU_LOGO);
		if (mainLogo != null) {
			BitmapUtil.setImage(mainLogo, mMainLoginImageView); 
		}
	}
	
	@OnClick({R.id.affairs_cancel, R.id.affairs_ok, R.id.btn_select_user})
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.affairs_cancel:
			getActivity().finish();
			break;
		case R.id.affairs_ok:
			affairsOk();
			break;
		case R.id.btn_select_user:
			//选择old的用户信息
			try {
				List<UserInfoModel> datas = mDb.findAll(UserInfoModel.class);
				LogUtil.d("PrisonAffairsLoginFragment---db=" + datas);
				UserChooseDialog dialog = new UserChooseDialog(getActivity(), R.style.UserChooseDialogTheme, 
						datas, new IBeanOnSuccessCB<UserInfoModel>() {
							
							@Override
							public void onSuccess(UserInfoModel bean) {
								mAffairsUsername.setText(bean.getUsername());
							}
						});
				dialog.show();
			} catch (DbException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 确定提交
	 */
	private void affairsOk() {
		final String username = mAffairsUsername.getText().toString().trim();
		final String password = mAffairsPwd.getText().toString().trim();
		if(TextUtils.isEmpty(username)){
			BaseActivity.getInstance().showToast("请输入用户名", Toast.LENGTH_SHORT);
			mAffairsUsername.requestFocus();
			return;
		}
		if(TextUtils.isEmpty(password)){
			BaseActivity.getInstance().showToast("请输入密码", Toast.LENGTH_SHORT);
			mAffairsPwd.requestFocus();
			return;
		}
		
		////10001: "账号异常" 
//		10002: "账号或密码不正确" 
//		10003: "服务器暂时不允许登陆"  
//		10004: "账号未激活"
		UIDataller.getDataller().loginPrisonUser(getActivity(), new IBeanOnSuccessCB<UserRequestModel>() {
			
			@Override
			public void onSuccess(UserRequestModel bean) {
				if(bean.getCode() == 200){
					//登录成功之后，保存当前信息
					UserInfoModel model = new UserInfoModel();
					model.setUsername(username);
					model.setNickname(bean.getUsername());
					setSqlit(model);
					BaseActivity.getInstance().showToast(
							BaseActivity.getInstance().getString(R.string.prison_user_success), Toast.LENGTH_SHORT);
					ActivitySwitchMgr.gotoPrisonAffairsDetailActivity(getActivity(), model.getUsername(),model.getNickname(), bean.getUsercode());
				}else if(bean.getCode() == 10001){
					BaseActivity.getInstance().showToast(
							BaseActivity.getInstance().getString(R.string.prison_user_error1), Toast.LENGTH_SHORT);
				}else if(bean.getCode() == 10002){
					BaseActivity.getInstance().showToast(
							BaseActivity.getInstance().getString(R.string.prison_user_error2), Toast.LENGTH_SHORT);
				}else if(bean.getCode() == 10003){
					BaseActivity.getInstance().showToast(
							BaseActivity.getInstance().getString(R.string.prison_user_error3), Toast.LENGTH_SHORT);
				}else if(bean.getCode() == 10004){
					BaseActivity.getInstance().showToast(
							BaseActivity.getInstance().getString(R.string.prison_user_error4), Toast.LENGTH_SHORT);
				}
			}
		}, username, password);
		
		//请求网络
//		ActivitySwitchMgr.gotoPrisonAffairsDetailActivity(getActivity(), username);
	}
	
	private void closeDatabase() {
		if (null != mDb && mDb.getDatabase().isOpen()) {
			mDb.close();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setTimeShow(true);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		BaseActivity.getInstance().getApplicationContext().unregisterReceiver(mDateAlarmReceiver);
		closeDatabase();
	}

	// 设置和更新主界面时间信息
	private void setTimeShow(boolean needAnimation) {
		UIDataller.getDataller().setMainMenuDateInfo(mMainmenuDateTextView, mMainmenuTimeTextView,
				mMainmenuWeekTextView,  needAnimation);
	}

	/**
	 * 注册时钟
	 */
	private void registerReceiver() {
		mDateAlarmReceiver = new DateAlarmReceiver();
		mDateAlarmFilter = new IntentFilter(Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE);
		BaseActivity.getInstance().getApplicationContext().registerReceiver(mDateAlarmReceiver, mDateAlarmFilter);
	}

	public class DateAlarmReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Constant.IPTV_LVB_X_BROADCAST_MSG_UPDATE_DATE.equals(intent.getAction())) {
				setTimeShow(false);
			}
		}
	}
}

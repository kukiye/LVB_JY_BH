/**
 * 作者：   Johnson
 * 日期：   2014年6月24日下午5:31:28
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：LangSetFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.commonui.DialogTwoButton;
import com.hhzt.iptv.lvb_x.interfaces.IOnClickListnerable;
import com.hhzt.iptv.lvb_x.mgr.SystemMgr;
import com.hhzt.iptv.ui.SplashActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class LangSetFragment extends BaseFragment {

	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.main_type_imgae)
	private ImageView mMainTypeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mMainTypeTextView;
	@ViewInject(R.id.tips_back)
	private TextView mBackTipsTextView;
	@ViewInject(R.id.tips_ok)
	private TextView mOkTipsTextView;

	@ViewInject(R.id.lang_simple_chinese_Button)
	private Button mChineseButton;
	@ViewInject(R.id.lang_simple_english_Button)
	private Button mEnglishButton;
	@ViewInject(R.id.lang_simple_chinese_image)
	private GifView mChineseGifImage;
	@ViewInject(R.id.lang_simple_english_image)
	private GifView mEnglishGifImage;

	private String mMenuPath;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_language_set, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			setValue();
			setDefaultFocus();
			setGifImageShow();
		}
	}

	private void setDefaultFocus() {
		int languageFlag = SystemMgr.getSystemLangType();
		if (languageFlag == Constant.IPTV_SYSTEM_LANG_US_ENGLISH) {
			mEnglishButton.requestLayout();
			mEnglishButton.requestFocus();
		}
	}

	private void setValue() {
		UIDataller ller = UIDataller.getDataller();

		Intent intent = getActivity().getIntent();
		mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));

		ller.setHsActionTips(getActivity(), mMainTypeImageView, R.drawable.home_icon, mMainTypeTextView, mMenuPath, mOkTipsTextView,
				ller.getOkActionTips(getActivity()), mBackTipsTextView, ller.getBackActionTips(getActivity()));
	}

	@OnClick({ R.id.lang_simple_chinese_Button, R.id.lang_simple_english_Button })
	public void onClick(View view) {
		String currentLang = SystemMgr.getSystemLangName();
		switch (view.getId()) {
		case R.id.lang_simple_chinese_Button:
			if (getString(R.string.simple_chinese).equalsIgnoreCase(currentLang)) {
				String tips = getString(R.string.no_need_switch_lang_tips);
				BaseActivity.getInstance().showToast(tips, Toast.LENGTH_LONG);
			} else {
				resetSystemForChangeLangTips(Constant.IPTV_SYSTEM_LANG_SIMPLE_CHINESE);
			}
			break;
		case R.id.lang_simple_english_Button:
			if (getString(R.string.english).equalsIgnoreCase(currentLang)) {
				String tips = getString(R.string.no_need_switch_lang_tips);
				BaseActivity.getInstance().showToast(tips, Toast.LENGTH_LONG);
			} else {
				resetSystemForChangeLangTips(Constant.IPTV_SYSTEM_LANG_US_ENGLISH);
			}
			break;
		default:
			break;
		}
	}

	private void resetSystemForChangeLangTips(final int langType) {
		String title = getString(R.string.exit_tips);
		String contents = getString(R.string.reset_system);
		DialogTwoButton dialog = new DialogTwoButton(getActivity(), R.style.lvbTwoButtonDialogTheme, title, contents, new IOnClickListnerable() {

			@Override
			public void ok() {
				switch (langType) {
				case Constant.IPTV_SYSTEM_LANG_SIMPLE_CHINESE:
					SystemMgr.setWantedLangType(langType);
					SystemMgr.setSystemLangType(langType);
					SystemMgr.setSystemLangName(LVBXApp.getApp().getString(R.string.simple_chinese));
					SystemMgr.changeLanguage(langType);

					Intent intent1 = new Intent();
					intent1.putExtra(Constant.IPTV_SYS_HOTEL_LANG_TYPE, Constant.IPTV_SYSTEM_LANG_SIMPLE_CHINESE);
					intent1.setClass(getActivity(), SplashActivity.class);
					intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent1);
					getActivity().finish();
					break;
				case Constant.IPTV_SYSTEM_LANG_US_ENGLISH:
					SystemMgr.setWantedLangType(langType);
					SystemMgr.setSystemLangType(langType);
					SystemMgr.setSystemLangName(LVBXApp.getApp().getString(R.string.english));
					SystemMgr.changeLanguage(langType);

					Intent intent2 = new Intent();
					intent2.putExtra(Constant.IPTV_SYS_HOTEL_LANG_TYPE, Constant.IPTV_SYSTEM_LANG_US_ENGLISH);
					intent2.setClass(getActivity(), SplashActivity.class);
					intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent2);
					getActivity().finish();
					break;
				default:
					break;
				}
			}

			@Override
			public void no() {

			}
		}, null, true);
		dialog.show();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		destoryGifRes();
	}

	private void setGifImageShow() {
		mChineseGifImage.setGifImage(R.drawable.chinese_gif);
		mChineseGifImage.setGifImageType(GifImageType.COVER);

		mEnglishGifImage.setGifImage(R.drawable.english_gif);
		mEnglishGifImage.setGifImageType(GifImageType.COVER);
	}

	private void destoryGifRes() {
		mChineseGifImage.destroy();
		mEnglishGifImage.destroy();
	}

}

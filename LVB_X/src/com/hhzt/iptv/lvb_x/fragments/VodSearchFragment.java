package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.commonui.DpadChooseDialog;
import com.hhzt.iptv.lvb_x.interfaces.IDpadOnclickListner;
import com.hhzt.iptv.lvb_x.interfaces.IVodDetailItemable;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.UrlMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.VodDetailItemModel;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class VodSearchFragment extends BaseFragment implements IVodDetailItemable {

	@ViewInject(R.id.virtual_pad_t9)
	private Button mVirtualPadT9Button;
	@ViewInject(R.id.virtual_pad_all)
	private Button mVirtualPadAllButton;
	@ViewInject(R.id.dpad_framwork_container_t9)
	private View mDpadFramworkContainerT9;
	@ViewInject(R.id.dpad_framwork_container_all)
	private View mDpadFramworkContainerAll;

	// 全键盘按钮
	@ViewInject(R.id.all_search_contents)
	private TextView mAllSearchVodNameTextView;
	@ViewInject(R.id.all_clear_all)
	private Button mSearchVodClearAll;
	@ViewInject(R.id.all_dpad_delete)
	private Button mSearchVodDelete;
	@ViewInject(R.id.all_dpad_a)
	private Button mSearchVodDpadA;
	@ViewInject(R.id.all_dpad_b)
	private Button mSearchVodDpadB;
	@ViewInject(R.id.all_dpad_c)
	private Button mSearchVodDpadC;
	@ViewInject(R.id.all_dpad_d)
	private Button mSearchVodDpadD;
	@ViewInject(R.id.all_dpad_e)
	private Button mSearchVodDpadE;
	@ViewInject(R.id.all_dpad_f)
	private Button mSearchVodDpadF;
	@ViewInject(R.id.all_dpad_g)
	private Button mSearchVodDpadG;
	@ViewInject(R.id.all_dpad_h)
	private Button mSearchVodDpadH;
	@ViewInject(R.id.all_dpad_i)
	private Button mSearchVodDpadI;
	@ViewInject(R.id.all_dpad_j)
	private Button mSearchVodDpadJ;
	@ViewInject(R.id.all_dpad_k)
	private Button mSearchVodDpadK;
	@ViewInject(R.id.all_dpad_l)
	private Button mSearchVodDpadL;
	@ViewInject(R.id.all_dpad_m)
	private Button mSearchVodDpadM;
	@ViewInject(R.id.all_dpad_n)
	private Button mSearchVodDpadN;
	@ViewInject(R.id.all_dpad_o)
	private Button mSearchVodDpadO;
	@ViewInject(R.id.all_dpad_p)
	private Button mSearchVodDpadP;
	@ViewInject(R.id.all_dpad_q)
	private Button mSearchVodDpadQ;
	@ViewInject(R.id.all_dpad_r)
	private Button mSearchVodDpadR;
	@ViewInject(R.id.all_dpad_s)
	private Button mSearchVodDpadS;
	@ViewInject(R.id.all_dpad_t)
	private Button mSearchVodDpadT;
	@ViewInject(R.id.all_dpad_u)
	private Button mSearchVodDpadU;
	@ViewInject(R.id.all_dpad_v)
	private Button mSearchVodDpadV;
	@ViewInject(R.id.all_dpad_w)
	private Button mSearchVodDpadW;
	@ViewInject(R.id.all_dpad_x)
	private Button mSearchVodDpadX;
	@ViewInject(R.id.all_dpad_y)
	private Button mSearchVodDpadY;
	@ViewInject(R.id.all_dpad_z)
	private Button mSearchVodDpadZ;
	@ViewInject(R.id.all_dpad_one)
	private Button mAllDpadDigitalOne;
	@ViewInject(R.id.all_dpad_two)
	private Button mAllDpadDigitalTwo;
	@ViewInject(R.id.all_dpad_three)
	private Button mAllDpadDigitalThree;
	@ViewInject(R.id.all_dpad_four)
	private Button mAllDpadDigitalFour;
	@ViewInject(R.id.all_dpad_five)
	private Button mAllDpadDigitalFive;
	@ViewInject(R.id.all_dpad_six)
	private Button mAllDpadDigitalSix;
	@ViewInject(R.id.all_dpad_seven)
	private Button mAllDpadDigitalSeven;
	@ViewInject(R.id.all_dpad_eight)
	private Button mAllDpadDigitalEight;
	@ViewInject(R.id.all_dpad_nine)
	private Button mAllDpadDigitalNine;
	@ViewInject(R.id.all_dpad_zero)
	private Button mAllDpadDigitalZero;

	// T9键盘按钮
	@ViewInject(R.id.t9_search_contents)
	private TextView mT9SearchVodNameTextView;
	@ViewInject(R.id.t9_clear_all)
	private Button mT9ClearAllButton;
	@ViewInject(R.id.t9_dpad_zero)
	private Button mT9DpadZeroButton;
	@ViewInject(R.id.t9_dpad_delete)
	private Button mT9DpadDeleteButton;
	@ViewInject(R.id.t9_dpad_one)
	private Button mT9DpadOneButton;
	@ViewInject(R.id.t9_dpad_abc2)
	private Button mT9DpadTwoABCButton;
	@ViewInject(R.id.t9_dpad_def3)
	private Button mT9DpadThreDEFButton;
	@ViewInject(R.id.t9_dpad_ghi4)
	private Button mT9DpadFourGHIButton;
	@ViewInject(R.id.t9_dpad_jkl5)
	private Button mT9DpadFiveJKLButton;
	@ViewInject(R.id.t9_dpad_mno6)
	private Button mT9DpadSixMNOButton;
	@ViewInject(R.id.t9_dpad_pqrs7)
	private Button mT9DpadSevenPQRSButton;
	@ViewInject(R.id.t9_dpad_tuv8)
	private Button mT9DpadEightTUVButton;
	@ViewInject(R.id.t9_dpad_wxyz9)
	private Button mT9DpadNineWXYZButton;

	// 搜索结果显示
	@ViewInject(R.id.vod_movie_gridlayout)
	private GridLayout mVodSearchResultGridLayout;
	@ViewInject(R.id.vod_detail_up_img)
	private ImageView mVodSearchResultUpArrow;
	@ViewInject(R.id.vod_detail_down_img)
	private ImageView mVodSearchResultDownArrow;
	@ViewInject(R.id.vod_item_page_text)
	private TextView mVodSearchResultPageShow;

	private int mCurrentPageIndex = 1;
	private int mCurrentDpadType = 0;

	private static final int VIRTUAL_T9 = 0;
	private static final int VIRTUAL_ALL = 1;

	private String mSearchContentText = "";

	@OnClick({ R.id.virtual_pad_t9, R.id.virtual_pad_all, R.id.all_clear_all, R.id.all_dpad_delete, R.id.all_dpad_a, R.id.all_dpad_b,
			R.id.all_dpad_c, R.id.all_dpad_d, R.id.all_dpad_e, R.id.all_dpad_f, R.id.all_dpad_g, R.id.all_dpad_h, R.id.all_dpad_i, R.id.all_dpad_j,
			R.id.all_dpad_k, R.id.all_dpad_l, R.id.all_dpad_m, R.id.all_dpad_n, R.id.all_dpad_o, R.id.all_dpad_p, R.id.all_dpad_q, R.id.all_dpad_r,
			R.id.all_dpad_s, R.id.all_dpad_s, R.id.all_dpad_t, R.id.all_dpad_u, R.id.all_dpad_v, R.id.all_dpad_w, R.id.all_dpad_x, R.id.all_dpad_y,
			R.id.all_dpad_z, R.id.all_dpad_one, R.id.all_dpad_two, R.id.all_dpad_three, R.id.all_dpad_four, R.id.all_dpad_five, R.id.all_dpad_six,
			R.id.all_dpad_seven, R.id.all_dpad_eight, R.id.all_dpad_nine, R.id.all_dpad_zero, R.id.t9_clear_all, R.id.t9_dpad_zero,
			R.id.t9_dpad_delete, R.id.t9_dpad_one, R.id.t9_dpad_abc2, R.id.t9_dpad_def3, R.id.t9_dpad_ghi4, R.id.t9_dpad_jkl5, R.id.t9_dpad_mno6,
			R.id.t9_dpad_pqrs7, R.id.t9_dpad_tuv8, R.id.t9_dpad_wxyz9 })
	public void onClickItem(View v) {
		switch (v.getId()) {
		case R.id.virtual_pad_t9:
			changVirtualPadShow(VIRTUAL_T9);
			break;
		case R.id.virtual_pad_all:
			changVirtualPadShow(VIRTUAL_ALL);
			break;
		case R.id.all_clear_all:
			mSearchContentText = "";
			setInputContentResults(mAllSearchVodNameTextView, mSearchContentText);
			break;
		case R.id.all_dpad_delete:
			deletAllKeyOneCharAction();
			break;
		case R.id.all_dpad_a:
		case R.id.all_dpad_b:
		case R.id.all_dpad_c:
		case R.id.all_dpad_d:
		case R.id.all_dpad_e:
		case R.id.all_dpad_f:
		case R.id.all_dpad_g:
		case R.id.all_dpad_h:
		case R.id.all_dpad_i:
		case R.id.all_dpad_j:
		case R.id.all_dpad_k:
		case R.id.all_dpad_l:
		case R.id.all_dpad_m:
		case R.id.all_dpad_n:
		case R.id.all_dpad_o:
		case R.id.all_dpad_p:
		case R.id.all_dpad_q:
		case R.id.all_dpad_r:
		case R.id.all_dpad_s:
		case R.id.all_dpad_t:
		case R.id.all_dpad_u:
		case R.id.all_dpad_v:
		case R.id.all_dpad_w:
		case R.id.all_dpad_x:
		case R.id.all_dpad_y:
		case R.id.all_dpad_z:
		case R.id.all_dpad_one:
		case R.id.all_dpad_two:
		case R.id.all_dpad_three:
		case R.id.all_dpad_four:
		case R.id.all_dpad_five:
		case R.id.all_dpad_six:
		case R.id.all_dpad_seven:
		case R.id.all_dpad_eight:
		case R.id.all_dpad_nine:
		case R.id.all_dpad_zero:
			mSearchContentText += ((Button) v).getText().toString();
			setInputContentResults(mAllSearchVodNameTextView, mSearchContentText);
			break;
		case R.id.t9_clear_all:
			mSearchContentText = "";
			setInputContentResults(mT9SearchVodNameTextView, mSearchContentText);
			break;
		case R.id.t9_dpad_delete:
			deleteT9OneCharAction();

			break;
		case R.id.t9_dpad_zero:
		case R.id.t9_dpad_one:
			mSearchContentText += ((Button) v).getText().toString();
			setInputContentResults(mT9SearchVodNameTextView, mSearchContentText);
			break;
		case R.id.t9_dpad_abc2:
		case R.id.t9_dpad_def3:
		case R.id.t9_dpad_ghi4:
		case R.id.t9_dpad_jkl5:
		case R.id.t9_dpad_mno6:
		case R.id.t9_dpad_pqrs7:
		case R.id.t9_dpad_tuv8:
		case R.id.t9_dpad_wxyz9:
			showDdapChooseTips(v);
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_main, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void deleteT9OneCharAction() {
		if (StringUtil.isEmpty(mSearchContentText)) {
			mSearchContentText = "";
		} else {
			mSearchContentText = mSearchContentText.substring(0, mSearchContentText.length() - 1);
		}
		setInputContentResults(mT9SearchVodNameTextView, mSearchContentText);
	}

	private void deletAllKeyOneCharAction() {
		if (StringUtil.isEmpty(mSearchContentText)) {
			mSearchContentText = "";
		} else {
			mSearchContentText = mSearchContentText.substring(0, mSearchContentText.length() - 1);
		}
		setInputContentResults(mAllSearchVodNameTextView, mSearchContentText);
	}

	private void changVirtualPadShow(int padType) {
		switch (padType) {
		case VIRTUAL_T9:
			mSearchContentText = "";
			mCurrentDpadType = VIRTUAL_T9;
			mDpadFramworkContainerAll.setVisibility(View.GONE);
			mDpadFramworkContainerT9.setVisibility(View.VISIBLE);
			break;
		case VIRTUAL_ALL:
			mSearchContentText = "";
			mCurrentDpadType = VIRTUAL_ALL;
			mDpadFramworkContainerT9.setVisibility(View.GONE);
			mDpadFramworkContainerAll.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	private void showDdapChooseTips(View v) {
		DpadChooseDialog dialog = new DpadChooseDialog(getActivity(), R.style.lvbTwoButtonDialogTheme, ((Button) v).getText().toString(),
				new IDpadOnclickListner() {

					@Override
					public void onclick(View view) {
						mSearchContentText += ((Button) view).getText().toString();
						setInputContentResults(mT9SearchVodNameTextView, mSearchContentText);
					}
				});
		Window dialogWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		dialogWindow.setGravity(Gravity.START | Gravity.TOP);
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		lp.x = location[0] - (dialogWindow.getAttributes().width - v.getWidth()) / 2;
		lp.y = location[1] - (dialogWindow.getAttributes().height - v.getHeight()) / 2;
		dialogWindow.setAttributes(lp);
		dialog.show();
	}

	private void setInputContentResults(TextView inputContainer, String inputContent) {
		String url = UrlMgr.getVodSearchResultUrl(inputContent, mCurrentPageIndex, Constant.VOD_NUM_SEARCH_PER_PAGE, UserMgr.getVodTypeLeve());
		inputContainer.setText(inputContent);
		UIDataller.getDataller().setVodDetailList(getActivity(), mVodSearchResultGridLayout, url, 1, Constant.VOD_NUM_SEARCH_PER_PAGE,
				mVodSearchResultUpArrow, mVodSearchResultDownArrow, mVodSearchResultPageShow, this, false, 0, null,
				mVodSearchResultGridLayout.getWidth(), mVodSearchResultGridLayout.getHeight(), 4, 13, 17, null);
	}

	@Override
	public void moveItemDown(int position, int totalPage) {
		mCurrentPageIndex++;
		if (mCurrentPageIndex > totalPage) {
			mCurrentPageIndex = totalPage;
		}
		String searchUrl = UrlMgr.getVodSearchResultUrl(mSearchContentText, mCurrentPageIndex, Constant.VOD_NUM_SEARCH_PER_PAGE,
				UserMgr.getVodTypeLeve());
		UIDataller.getDataller().setVodDetailList(getActivity(), mVodSearchResultGridLayout, searchUrl, mCurrentPageIndex,
				Constant.VOD_NUM_SEARCH_PER_PAGE, mVodSearchResultUpArrow, mVodSearchResultDownArrow, mVodSearchResultPageShow, this, true, position,
				null, mVodSearchResultGridLayout.getWidth(), mVodSearchResultGridLayout.getHeight(), 4, 13, 17, null);
	}

	@Override
	public void clickItemBtn(VodDetailItemModel itemModel) {
		Intent intent = getActivity().getIntent();
		String pathName = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
		ActivitySwitchMgr.gotoVodItemDetailActivity(getActivity(), itemModel, 0, pathName);
	}

	@Override
	public void moveItemUp(int position) {
		mCurrentPageIndex--;
		if (mCurrentPageIndex < 1) {
			mCurrentPageIndex = 1;
		}
		String searchUrl = UrlMgr.getVodSearchResultUrl(mSearchContentText, mCurrentPageIndex, Constant.VOD_NUM_SEARCH_PER_PAGE,
				UserMgr.getVodTypeLeve());
		UIDataller.getDataller().setVodDetailList(getActivity(), mVodSearchResultGridLayout, searchUrl, mCurrentPageIndex,
				Constant.VOD_NUM_SEARCH_PER_PAGE, mVodSearchResultUpArrow, mVodSearchResultDownArrow, mVodSearchResultPageShow, this, true, position,
				null, mVodSearchResultGridLayout.getWidth(), mVodSearchResultGridLayout.getHeight(), 4, 13, 17, null);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DEL:
			switch (mCurrentDpadType) {
			case VIRTUAL_T9:
				deleteT9OneCharAction();
				break;
			case VIRTUAL_ALL:
				deletAllKeyOneCharAction();
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

}

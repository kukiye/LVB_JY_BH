/**
 * 作者：   Johnson
 * 日期：   2014年6月26日下午2:20:07
 * 包名：    com.hhzt.iptv.lvb_x.fragments
 * 工程名：LVB_X
 * 文件名：HSOrderFragment.java
 */
package com.hhzt.iptv.lvb_x.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.adapter.OrderMenuMainListAdapter;
import com.hhzt.iptv.lvb_x.adapter.OrderShopCarAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.commonui.DialogTwoButton;
import com.hhzt.iptv.lvb_x.config.Config;
import com.hhzt.iptv.lvb_x.customview.VodDetailSingle;
import com.hhzt.iptv.lvb_x.interfaces.IOnButtonAnimationable;
import com.hhzt.iptv.lvb_x.interfaces.IOnClickListnerable;
import com.hhzt.iptv.lvb_x.interfaces.IOrderMenuItemOnclickCB;
import com.hhzt.iptv.lvb_x.interfaces.IOrderMenuListSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IOrderShopCarListSuccessCB;
import com.hhzt.iptv.lvb_x.interfaces.IOrderSubMenuItemOnclickCB;
import com.hhzt.iptv.lvb_x.interfaces.IOrderSubMenuListSuccessCB;
import com.hhzt.iptv.lvb_x.mgr.ActivitySwitchMgr;
import com.hhzt.iptv.lvb_x.mgr.UserMgr;
import com.hhzt.iptv.lvb_x.model.OrderMainMenuModel;
import com.hhzt.iptv.lvb_x.model.OrderShopCarSingleModel;
import com.hhzt.iptv.lvb_x.model.OrderSubMenuModel;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HSOrderFragment extends BaseFragment {
	@ViewInject(R.id.top_blank_banner)
	private TextView mWelcomeTextView;
	@ViewInject(R.id.menu_name_tag)
	private TextView mMenuTitleName;
	@ViewInject(R.id.shopcar_name_tag)
	private TextView mMenuShopCarTitleName;
	@ViewInject(R.id.mainmenu_LinearLayout)
	private LinearLayout mSingleMenuLinearLayout;
	@ViewInject(R.id.order_left_arrow)
	private ImageView mLeftArrowTipsButton;
	@ViewInject(R.id.order_right_arrow)
	private ImageView mRightArrowTipsButton;
	@ViewInject(R.id.vod_detail_movie_img)
	private ImageView mMovieImageView;
	@ViewInject(R.id.menu_items)
	private ListView mOrderMenuItemList;
	@ViewInject(R.id.menu_history_check)
	private Button mHistoryOrderMenuButton;
	@ViewInject(R.id.menu_history_list_tips_line)
	private ImageView mHistoryOrderTipsLine;
	@ViewInject(R.id.order_image_blank_tips)
	private ImageView mOrderMenuIsBlankTipsImageView;
	@ViewInject(R.id.shopcar_items_list)
	private ListView mOrderShopCarListView;
	@ViewInject(R.id.shopcar_data_tips)
	private TextView mShopCarDatainfoTextView;
	@ViewInject(R.id.shopcar_commit_tips)
	private Button mShopCarCommitButton;
	@ViewInject(R.id.main_type_imgae)
	private ImageView mOrderPathHomeImageView;
	@ViewInject(R.id.main_type_text)
	private TextView mOrderPathHomeTextView;
	@ViewInject(R.id.tips_back)
	private TextView mOrderActionBackTips;
	@ViewInject(R.id.tips_ok)
	private TextView mOrderActionOkTips;
	@ViewInject(R.id.order_middle_big_image_container)
	private VodDetailSingle mMiddleImageButton;

	@ViewInject(R.id.shopcar_goods_account)
	private TextView mShopCarGoodsCount;
	@ViewInject(R.id.shopcar_icon)
	private ImageView mShopCar;

	@ViewInject(R.id.right_shopcar_container)
	private View mRightShopCarContainer;
	@ViewInject(R.id.adv_common_container)
	private View mAdvCommonContainer;
	@ViewInject(R.id.right_adv)
	private ImageView mAdvertimentImageView;

	@OnClick({ R.id.shopcar_commit_tips, R.id.menu_history_check })
	public void onClickListners(View view) {
		switch (view.getId()) {
		case R.id.shopcar_commit_tips:
			showCommitOrderTips();
			break;
		case R.id.menu_history_check:
			showOrderHistoryScreen(mMenuPath + ">" + getString(R.string.order_history));
			break;
		default:
			break;
		}
	}

	// 界面标示
	private int mScreenTag;
	// 登陆用户名
	private String mUsername;
	// 顶部路劲
	private String mMenuPath;
	// 购物车菜品数量
	// private int mTotalNumInShopcar;
	// 购物车所有菜品总价格
	private int mTotalPriceInShopCar;
	// 主类型数据
	// private ArrayList<OrderMainMenuModel> mOrderMenuListModels;
	// 二级类型数据
	// private OrderSubMenuModel mOrderSubListModels;
	// 购物车数据
	private ArrayList<OrderShopCarSingleModel> mShopCarDatamodels;

	// 菜品类型列表适配器
	private OrderMenuMainListAdapter mMenuListadapter;
	// 购物车数据列表适配器
	private OrderShopCarAdapter mShopCarListAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_order_main, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			Bundle bundle = getArguments();
			mScreenTag = bundle.getInt(Constant.IPTV_SYS_FRAGMENT_TAG);

			Intent intent = getActivity().getIntent();
			mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);

			mUsername = UserMgr.getUserName();
			mMenuListadapter = new OrderMenuMainListAdapter(getActivity());
			mShopCarListAdapter = new OrderShopCarAdapter(getActivity());
			mMiddleImageButton.setImageValue(R.drawable.hotel_iptv_846x441);

			// 设置"我的订单"按钮的可见性
			showMyHistoryOrderButton();
			// 设置右侧显示的内容：广告位或者购物车
			showRightContainerContent();
			// 设置入口类型信息
			setTypeTitleTips(mScreenTag);

			// 设置底部提示信息
			UIDataller.getDataller().setHsActionTips(getActivity(), mOrderPathHomeImageView, R.drawable.home_icon, mOrderPathHomeTextView, mMenuPath,
					mOrderActionOkTips, UIDataller.getDataller().getOkActionTips(getActivity()), mOrderActionBackTips,
					UIDataller.getDataller().getBackActionTips(getActivity()));
			// 设置顶部欢迎词
			mMenuPath = intent.getStringExtra(Constant.IPTV_LVB_X_MENU_PATH_TAG);
			mWelcomeTextView.setText(String.format(getString(R.string.welcome_text_format), mMenuPath));

			// 设置"我的订单"数量信息
			UIDataller.getDataller().setOrderHistoryButtonText(getActivity(), mScreenTag, mUsername, mHistoryOrderMenuButton);

			// 设置左边主类型数据
			UIDataller.getDataller().setOrderMenuListData(getActivity(), mScreenTag, mOrderMenuItemList, mMenuListadapter,
					new IOrderMenuListSuccessCB() {

						@Override
						public void setListDatas(ArrayList<OrderMainMenuModel> listModels) {
							// mOrderMenuListModels = listModels;
						}
					}, new IOrderMenuItemOnclickCB() {

						@Override
						public void onItemClickCB(int index, int menuId) {
							int[] shopCarLocation = new int[2];
							mShopCar.getLocationInWindow(shopCarLocation);
							// 设置子类型数据进行显示
							UIDataller.getDataller().setOrderSubListData(getActivity(), mScreenTag, menuId, 1, 1000, shopCarLocation,
									mSingleMenuLinearLayout, mMovieImageView, mMiddleImageButton, new IOrderSubMenuListSuccessCB() {

										@Override
										public void setListDatas(OrderSubMenuModel model) {
											// mOrderSubListModels = model;
										}
									}, new IOrderSubMenuItemOnclickCB() {

										@Override
										public void onItemClickCB(int index, int menuId) {
											addSingleMenuToShopCar(index, menuId);
										}

									});
						}
					});

			// 获取购物车数据
			UIDataller.getDataller().setShopCarMenuListData(getActivity(), mScreenTag, mUsername, mOrderMenuIsBlankTipsImageView, mShopCarGoodsCount,
					mShopCarDatainfoTextView, mOrderShopCarListView, mShopCarListAdapter, mShopCarCommitButton, new IOrderShopCarListSuccessCB() {

						@Override
						public void setListDatas(ArrayList<OrderShopCarSingleModel> models) {
							mShopCarDatamodels = models;
						}
					}, new IOrderMenuItemOnclickCB() {

						@Override
						public void onItemClickCB(int index, int menuId) {
							String deleteMenuInfos = getDeleteMenuInfos(index);
							showDeleteSingleMenuTips(index, menuId, deleteMenuInfos);
						}
					});
		}
	}

	private void setTypeTitleTips(int screenType) {
		switch (screenType) {
		case Constant.HOTEL_TRAVEL_SPECAIL_SCREEN:
			mMenuTitleName.setText(R.string.special_type);
			mMenuShopCarTitleName.setText(R.string.choosed_specail);
			break;
		case Constant.HOTEL_ORDER_SCREEN:
			mMenuTitleName.setText(R.string.my_menu);
			mMenuShopCarTitleName.setText(R.string.choosed_menu);
			break;
		default:
			break;
		}
	}

	/**
	 * 添加菜品进入购物车
	 * 
	 * @param index
	 * @param menuId
	 */
	private void addSingleMenuToShopCar(int index, int menuId) {
		UIDataller ller = UIDataller.getDataller();

		ller.addToShopCarAction(getActivity(), mScreenTag, mUsername, menuId, 1, mOrderMenuIsBlankTipsImageView, mShopCarGoodsCount,
				mShopCarDatainfoTextView, mOrderShopCarListView, mShopCarListAdapter, mShopCarCommitButton, new IOrderShopCarListSuccessCB() {

					@Override
					public void setListDatas(ArrayList<OrderShopCarSingleModel> models) {
						mShopCarDatamodels = models;
					}
				}, new IOrderMenuItemOnclickCB() {

					@Override
					public void onItemClickCB(int index, int menuId) {
						String deleteMenuInfos = getDeleteMenuInfos(index);
						showDeleteSingleMenuTips(index, menuId, deleteMenuInfos);
					}
				});
	}

	// 组装需要删除的菜品之信息
	private String getDeleteMenuInfos(int index) {
		String menuName = mShopCarDatamodels.get(index).getDish().getName();
		String menuPrice = "￥" + mShopCarDatamodels.get(index).getDish().getPrice();
		String menuInfos = menuName + menuPrice;

		return menuInfos;
	}

	/**
	 * 从购物车中删除某项菜品
	 * 
	 * @param index
	 * @param menuId
	 */
	private void deleteSingleMenuFromShopCar(int index, int menuId) {
		UIDataller ller = UIDataller.getDataller();
		ller.deleteMenuFromShopCar(getActivity(), mScreenTag, mUsername, menuId, 1, mOrderMenuIsBlankTipsImageView, mShopCarGoodsCount,
				mShopCarDatainfoTextView, mOrderShopCarListView, mShopCarListAdapter, mShopCarCommitButton, new IOrderShopCarListSuccessCB() {

					@Override
					public void setListDatas(ArrayList<OrderShopCarSingleModel> models) {
						mShopCarDatamodels = models;
					}
				}, new IOrderMenuItemOnclickCB() {

					@Override
					public void onItemClickCB(int index, int menuId) {
						String deleteMenuInfos = getDeleteMenuInfos(index);
						showDeleteSingleMenuTips(index, menuId, deleteMenuInfos);
					}
				});
	}

	// 删除某项菜品时，弹出提示框确认取消
	private void showDeleteSingleMenuTips(final int index, final int menuId, String menuInfos) {
		String title = getString(R.string.cancel_menu);
		String contents = String.format(getString(R.string.cancel_tips), menuInfos);
		DialogTwoButton dialog = new DialogTwoButton(getActivity(), R.style.lvbTwoButtonDialogTheme, title, contents, new IOnClickListnerable() {

			@Override
			public void ok() {
				deleteSingleMenuFromShopCar(index, menuId);
			}

			@Override
			public void no() {

			}
		}, null, true);
		dialog.show();
	}

	private void setDishTotalNumber() {
		if (null != mShopCarListAdapter) {
			// mTotalNumInShopcar = mShopCarListAdapter.getTotalNum();
		}
	}

	private void setDishTotalprice() {
		if (null != mShopCarListAdapter) {
			mTotalPriceInShopCar = mShopCarListAdapter.getTotalPrice();
		}
	}

	// 提交订单提示框
	private void showCommitOrderTips() {
		setDishTotalNumber();
		setDishTotalprice();
		String title = getString(R.string.commit_order_title);
		String contents = String.format(getString(R.string.commit_order_content), mTotalPriceInShopCar);
		DialogTwoButton dialog = new DialogTwoButton(getActivity(), R.style.lvbTwoButtonDialogTheme, title, contents, new IOnClickListnerable() {

			@Override
			public void ok() {
				UIDataller ller = UIDataller.getDataller();
				ller.commitOrderOfShopCarAction(getActivity(), mScreenTag, mUsername, mOrderMenuIsBlankTipsImageView, mShopCarGoodsCount,
						mShopCarDatainfoTextView, mOrderShopCarListView, mShopCarListAdapter, mShopCarCommitButton, new IOrderShopCarListSuccessCB() {

							@Override
							public void setListDatas(ArrayList<OrderShopCarSingleModel> models) {
								mShopCarDatamodels = models;
							}
						}, new IOrderMenuItemOnclickCB() {

							@Override
							public void onItemClickCB(int index, int menuId) {
								String deleteMenuInfos = getDeleteMenuInfos(index);
								showDeleteSingleMenuTips(index, menuId, deleteMenuInfos);
							}
						});
			}

			@Override
			public void no() {

			}
		}, new IOnButtonAnimationable() {

			@Override
			public void play(View v) {
				// 获取对话框点击动画结束位置
				final int[] orderHistoryButtonLocation = new int[2];
				mHistoryOrderMenuButton.getLocationInWindow(orderHistoryButtonLocation);
				int orderMenuButtonWidth = mHistoryOrderMenuButton.getWidth();
				int orderMenuButtonHeight = mHistoryOrderMenuButton.getHeight();
				orderHistoryButtonLocation[0] += orderMenuButtonWidth * 3 / 4;
				orderHistoryButtonLocation[1] += orderMenuButtonHeight / 3;

				// 获取对话框点击动画位置
				int[] confirmButtonLocation = new int[2];
				v.getLocationOnScreen(confirmButtonLocation);
				int confirmButtonWidth = v.getWidth();
				confirmButtonLocation[0] += confirmButtonWidth / 2;

				// 实现点击动画
				UIDataller ller = UIDataller.getDataller();
				ller.playCustomPathAnimation(getActivity(), confirmButtonLocation, orderHistoryButtonLocation);

				// 更新"我的订单"上的数量信息
				String str = mHistoryOrderMenuButton.getText().toString();
				str = str.trim();
				String countStr = "";
				if (!StringUtil.isEmpty(str)) {
					for (int i = 0; i < str.length(); i++) {
						// 判断该位置的字符内容是否为数字，数字0-9对应ASCII码值为48-57
						if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
							countStr += str.charAt(i);
						}
					}
				}
				int count = 0;
				if (!StringUtil.isEmpty(countStr)) {
					count = Integer.parseInt(countStr);
				}
				count++;
				String orderHistoryButtonText = String.format(getActivity().getString(R.string.order_history_count), count);
				mHistoryOrderMenuButton.setText(orderHistoryButtonText);
			}
		}, true);
		dialog.show();
	}

	private void showOrderHistoryScreen(String path) {
		ActivitySwitchMgr.gotoOrderHistoryMainInfos(getActivity(), mScreenTag, path);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * 设置“我的订单”按钮的可见性
	 */
	private void showMyHistoryOrderButton() {
		switch (Config.USER_ORDER_SPECIAL_ACTION) {
		case 1:
			mHistoryOrderMenuButton.setVisibility(View.VISIBLE);
			mHistoryOrderTipsLine.setVisibility(View.VISIBLE);
			break;
		default:
			mHistoryOrderMenuButton.setVisibility(View.GONE);
			mHistoryOrderTipsLine.setVisibility(View.GONE);
			break;
		}
	}

	/**
	 * 设置界面右侧显示的内容
	 */
	private void showRightContainerContent() {
		switch (Config.USER_ORDER_SPECIAL_ACTION) {
		case 1:
			mAdvCommonContainer.setVisibility(View.GONE);
			mRightShopCarContainer.setVisibility(View.VISIBLE);
			break;
		default:
			mAdvCommonContainer.setVisibility(View.VISIBLE);
			mRightShopCarContainer.setVisibility(View.GONE);
			setTypeAdvContent();
			break;
		}
	}

	/**
	 * 设置广告位内容
	 */
	private void setTypeAdvContent() {
		switch (mScreenTag) {
		case Constant.HOTEL_TRAVEL_SPECAIL_SCREEN:
			UIDataller.getDataller().setAdvertisment(getActivity(), Constant.IPTV_ADV_INDEX_1, mAdvertimentImageView);
			break;
		case Constant.HOTEL_ORDER_SCREEN:
			UIDataller.getDataller().setAdvertisment(getActivity(), Constant.IPTV_ADV_INDEX_2, mAdvertimentImageView);
			break;
		default:
			break;
		}
	}
}

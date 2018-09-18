package com.hhzt.iptv.lvb_x.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseFragment;
import com.hhzt.iptv.lvb_x.adapter.DzcxAdapter;
import com.hhzt.iptv.lvb_x.adapter.FjcyAdapter;
import com.hhzt.iptv.lvb_x.adapter.PxhzAdapter;
import com.hhzt.iptv.lvb_x.adapter.ScoreAdapter;
import com.hhzt.iptv.lvb_x.adapter.SkcjAdapter;
import com.hhzt.iptv.lvb_x.adapter.XqbdAdapter;
import com.hhzt.iptv.lvb_x.adapter.XzjcAdapter;
import com.hhzt.iptv.lvb_x.adapter.YxcxAdapter;
import com.hhzt.iptv.lvb_x.business.UIDataller;
import com.hhzt.iptv.lvb_x.config.CCViewConfig;
import com.hhzt.iptv.lvb_x.interfaces.IUserSearchOnSuccessCB;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.mgr.ConfigMgr;
import com.hhzt.iptv.lvb_x.model.prisonaffairs.Dzcx;
import com.hhzt.iptv.lvb_x.model.prisonaffairs.Fjcy;
import com.hhzt.iptv.lvb_x.model.prisonaffairs.Pxhz;
import com.hhzt.iptv.lvb_x.model.prisonaffairs.Score;
import com.hhzt.iptv.lvb_x.model.prisonaffairs.Skcj;
import com.hhzt.iptv.lvb_x.model.prisonaffairs.Xqbd;
import com.hhzt.iptv.lvb_x.model.prisonaffairs.Xzjc;
import com.hhzt.iptv.lvb_x.model.prisonaffairs.Yxcx;
import com.hhzt.iptv.lvb_x.utils.BitmapUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnFocusChange;

import java.util.ArrayList;

public class PrisonAffairsDetailFragment extends BaseFragment {

	@ViewInject(R.id.mainmenu_img)
	private ImageView mMainmenuImageView; // 图片
	@ViewInject(R.id.mainmenu_txt)
	private TextView mMainmenuTitleTextView; // 文字
	@ViewInject(R.id.weather_time_container)
	private LinearLayout mWeatherTimeContainer;
	@ViewInject(R.id.ll_prison_contrainer)
	private LinearLayout mPrisonContrainer;

	@ViewInject(R.id.ll_user_container)
	private LinearLayout mUserContainer;
	@ViewInject(R.id.tv_user_number)
	private TextView mUserNumberTextView;

	@ViewInject(R.id.prison_detail_framelayout)
	private FrameLayout mDetailFrameLayout;

	@ViewInject(R.id.tv_mainmenu_name)
	private TextView mMainmenuName;

	private String mUserCode;
	private String mUsername;

	@ViewInject(R.id.btn_1)
	private Button mBtn1;
	@ViewInject(R.id.btn_2)
	private Button mBtn2;
	@ViewInject(R.id.btn_3)
	private Button mBtn3;
	@ViewInject(R.id.btn_4)
	private Button mBtn4;
	@ViewInject(R.id.btn_5)
	private Button mBtn5;
	@ViewInject(R.id.btn_6)
	private Button mBtn6;
	@ViewInject(R.id.btn_7)
	private Button mBtn7;
	@ViewInject(R.id.btn_8)
	private Button mBtn8;

	@ViewInject(R.id.sub_affairs_item)
	private LinearLayout mSubAffairsItem;
	@ViewInject(R.id.txt_item1)
	private TextView mItemTextView1;
	@ViewInject(R.id.txt_item2)
	private TextView mItemTextView2;
	@ViewInject(R.id.txt_item3)
	private TextView mItemTextView3;
	@ViewInject(R.id.txt_item4)
	private TextView mItemTextView4;
	@ViewInject(R.id.txt_item5)
	private TextView mItemTextView5;
	@ViewInject(R.id.txt_item6)
	private TextView mItemTextView6;
	@ViewInject(R.id.txt_item7)
	private TextView mItemTextView7;
	@ViewInject(R.id.txt_item8)
	private TextView mItemTextView8;
	@ViewInject(R.id.txt_item9)
	private TextView mItemTextView9;

	@ViewInject(R.id.prison_list)
	private ListView mPrisonList;

	private int mCurrentFocus = 1;
	
	@ViewInject(R.id.mainmenu_logo_b)
	private ImageView mMainLoginImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_prison_affairs_detail,
				container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null == savedInstanceState) {
			setValue();
			String mainmenuName = ConfigMgr.getInstance().getBeanVaule(
					CCViewConfig.HOTEL_NAME);
			if (mainmenuName != null) {
				mMainmenuName.setText(mainmenuName);
			}
		}
	}

	@OnFocusChange({ R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
			R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8 })
	public void onFocusChange(View v, boolean hasFocus) {
		int focus = 1;
		if (hasFocus) {
			switch (v.getId()) {
			case R.id.btn_1:
				focus = 1;
				break;
			case R.id.btn_2:
				focus = 2;
				break;
			case R.id.btn_3:
				focus = 3;
				break;
			case R.id.btn_4:
				focus = 4;
				break;
			case R.id.btn_5:
				focus = 5;
				break;
			case R.id.btn_6:
				focus = 6;
				break;
			case R.id.btn_7:
				focus = 7;
				break;
			case R.id.btn_8:
				focus = 8;
				break;

			default:
				break;
			}

			UIDataller.getDataller().getPrisonUserSearch(getActivity(),
					mUsername, mUserCode, focus, new IUserSearchOnSuccessCB() {

						@Override
						public void onSuccess(String result, int type) {
							refreshUI(result, type);
						}
					});
		}
	}
	
	@OnClick({ R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
		R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8 })
	public void onClick(View v) {
		int focus = 1;
		switch (v.getId()) {
		case R.id.btn_1:
			focus = 1;
			break;
		case R.id.btn_2:
			focus = 2;
			break;
		case R.id.btn_3:
			focus = 3;
			break;
		case R.id.btn_4:
			focus = 4;
			break;
		case R.id.btn_5:
			focus = 5;
			break;
		case R.id.btn_6:
			focus = 6;
			break;
		case R.id.btn_7:
			focus = 7;
			break;
		case R.id.btn_8:
			focus = 8;
			break;

		default:
			break;
		}

		UIDataller.getDataller().getPrisonUserSearch(getActivity(),
				mUsername, mUserCode, focus, new IUserSearchOnSuccessCB() {

					@Override
					public void onSuccess(String result, int type) {
						refreshUI(result, type);
					}
				});
	}

	/**
	 * 更新UI界面
	 * 
	 * @param result
	 * @param type
	 *            1计分考核 2行政奖惩 3分级处遇 4刑期变动 5余刑查询 6培训获证 7三课成绩 8大帐查询
	 */
	protected void refreshUI(String result, int type) {
		LogUtil.d("refreshUI-------result=" + result);
		mSubAffairsItem.setBackgroundResource(R.drawable.prison_top_bg);
		mItemTextView1.setVisibility(View.VISIBLE);
		mItemTextView2.setVisibility(View.VISIBLE);
		mItemTextView3.setVisibility(View.VISIBLE);
		mItemTextView4.setVisibility(View.VISIBLE);
		mItemTextView5.setVisibility(View.VISIBLE);
		mItemTextView6.setVisibility(View.VISIBLE);
		mItemTextView7.setVisibility(View.VISIBLE);
		mItemTextView8.setVisibility(View.VISIBLE);
		mItemTextView9.setVisibility(View.VISIBLE);
		Gson gson = new Gson();
		switch (type) {
		case 1:
			mItemTextView1.setText(getString(R.string.prisonname));
			mItemTextView2.setText(getString(R.string.sname));
			mItemTextView3.setText(getString(R.string.code));
			mItemTextView4.setText(getString(R.string.kjlf));
			mItemTextView5.setText(getString(R.string.ljlf));
			mItemTextView6.setText(getString(R.string.prisonid));
			mItemTextView7.setText(getString(R.string.date));
			mItemTextView8.setText(getString(R.string.yjjf));
			mItemTextView9.setText(getString(R.string.dkjf));
			mItemTextView6.setVisibility(View.GONE);
			final ArrayList<Score> scores = gson.fromJson(result,
					new TypeToken<ArrayList<Score>>() {
					}.getType());
			ScoreAdapter adapter1 = new ScoreAdapter(getActivity(), scores);
			mPrisonList.setAdapter(adapter1);
			break;
		case 2:
			mItemTextView1.setText(getString(R.string.prisonname));
			mItemTextView2.setText(getString(R.string.sname));
			mItemTextView3.setText(getString(R.string.code));
			mItemTextView4.setText(getString(R.string.type));
			mItemTextView5.setText(getString(R.string.detail));
			mItemTextView6.setText(getString(R.string.prisonid));
			mItemTextView7.setText(getString(R.string.xzjc_date));
			mItemTextView6.setVisibility(View.GONE);
			mItemTextView8.setVisibility(View.GONE);
			mItemTextView9.setVisibility(View.GONE);
			final ArrayList<Xzjc> zxjcs = gson.fromJson(result,
					new TypeToken<ArrayList<Xzjc>>() {
					}.getType());
			XzjcAdapter adapter2 = new XzjcAdapter(getActivity(), zxjcs);
			mPrisonList.setAdapter(adapter2);
			break;
		case 3:
			mItemTextView1.setText(getString(R.string.prisonname));
			mItemTextView2.setText(getString(R.string.sname));
			mItemTextView3.setText(getString(R.string.code));
			mItemTextView4.setText(getString(R.string.rynx));
			mItemTextView5.setText(getString(R.string.prisonTerm));
			mItemTextView6.setText(getString(R.string.fjcy_type));
			mItemTextView7.setText(getString(R.string.fjcy_date));
			mItemTextView8.setText(getString(R.string.xcyj));
			mItemTextView5.setVisibility(View.GONE);
			mItemTextView9.setVisibility(View.GONE);
			final ArrayList<Fjcy> sjcys = gson.fromJson(result,
					new TypeToken<ArrayList<Fjcy>>() {
					}.getType());
			FjcyAdapter adapter3 = new FjcyAdapter(getActivity(), sjcys);
			mPrisonList.setAdapter(adapter3);
			break;
		case 4:
			mItemTextView1.setText(getString(R.string.prisonname));
			mItemTextView2.setText(getString(R.string.sname));
			mItemTextView3.setText(getString(R.string.code));
			mItemTextView4.setText(getString(R.string.xqbd_type));
			mItemTextView5.setText(getString(R.string.xqbd_number));
			mItemTextView6.setText(getString(R.string.xqbd_date));
			mItemTextView7.setText(getString(R.string.xqbd_startDate));
			mItemTextView8.setText(getString(R.string.xqbd_prisonTerm));
			mItemTextView9.setVisibility(View.GONE);
			mItemTextView7.setVisibility(View.GONE);
			mItemTextView8.setVisibility(View.GONE);
			final ArrayList<Xqbd> xqbds = gson.fromJson(result,
					new TypeToken<ArrayList<Xqbd>>() {
					}.getType());
			XqbdAdapter adapter4 = new XqbdAdapter(getActivity(), xqbds);
			mPrisonList.setAdapter(adapter4);
			break;
		case 5:
			// 余刑查询
			mItemTextView1.setText(getString(R.string.prisonname));
			mItemTextView2.setText(getString(R.string.sname));
			mItemTextView3.setText(getString(R.string.code));
			mItemTextView4.setText(getString(R.string.yxcx_startDate));
			mItemTextView5.setText(getString(R.string.yxcx_endDate));
			mItemTextView6.setVisibility(View.GONE);
			mItemTextView7.setVisibility(View.GONE);
			mItemTextView8.setVisibility(View.GONE);
			mItemTextView9.setVisibility(View.GONE);
			final Yxcx yxcy = gson.fromJson(result, Yxcx.class);
			ArrayList<Yxcx> yxcys = new ArrayList<Yxcx>();
			yxcys.add(yxcy);
			YxcxAdapter adapter5 = new YxcxAdapter(getActivity(), yxcys);
			mPrisonList.setAdapter(adapter5);
			break;
		case 6:
			// 培训获证
			mItemTextView1.setText(getString(R.string.prisonname));
			mItemTextView2.setText(getString(R.string.sname));
			mItemTextView3.setText(getString(R.string.code));
			mItemTextView4.setText(getString(R.string.pxhz_zsmc));
			mItemTextView5.setText(getString(R.string.pxhz_zshm));
			mItemTextView6.setText(getString(R.string.pxhz_date));
			mItemTextView7.setVisibility(View.GONE);
			mItemTextView8.setVisibility(View.GONE);
			mItemTextView9.setVisibility(View.GONE);
			final ArrayList<Pxhz> pxhzs = gson.fromJson(result,
					new TypeToken<ArrayList<Pxhz>>() {
					}.getType());
			PxhzAdapter adapter6 = new PxhzAdapter(getActivity(), pxhzs);
			mPrisonList.setAdapter(adapter6);
			break;
		case 7:
			// 三课成绩
			mItemTextView1.setText(getString(R.string.prisonname));
			mItemTextView2.setText(getString(R.string.sname));
			mItemTextView3.setText(getString(R.string.code));
			mItemTextView4.setText(getString(R.string.skcj_kxcj));
			mItemTextView5.setText(getString(R.string.skcj_date));
			mItemTextView6.setText(getString(R.string.skcj_jyjd));
			mItemTextView7.setText(getString(R.string.skcj_sfhz));
			mItemTextView8.setVisibility(View.GONE);
			mItemTextView9.setVisibility(View.GONE);
			final ArrayList<Skcj> skcjs = gson.fromJson(result,
					new TypeToken<ArrayList<Skcj>>() {
					}.getType());
			SkcjAdapter adapter7 = new SkcjAdapter(getActivity(), skcjs);
			mPrisonList.setAdapter(adapter7);
			break;
		case 8:
			// 8大帐查询
			mItemTextView1.setText(getString(R.string.prisonname));
			mItemTextView2.setText(getString(R.string.sname));
			mItemTextView3.setText(getString(R.string.code));
			mItemTextView4.setText(getString(R.string.dzcx_type));
			mItemTextView5.setText(getString(R.string.dzcx_szzh));
			mItemTextView6.setText(getString(R.string.dzcx_zhye));
			mItemTextView7.setText(getString(R.string.dzcx_byxf));
			mItemTextView8.setText(getString(R.string.dzcx_kyye));
			mItemTextView9.setText(getString(R.string.dzcx_date));
			mItemTextView5.setVisibility(View.GONE);
			final ArrayList<Dzcx> dzcxs = gson.fromJson(result,
					new TypeToken<ArrayList<Dzcx>>() {
					}.getType());
			DzcxAdapter adapter8 = new DzcxAdapter(getActivity(), dzcxs);
			mPrisonList.setAdapter(adapter8);
			break;
		default:
			mItemTextView1.setVisibility(View.GONE);
			mItemTextView2.setVisibility(View.GONE);
			mItemTextView3.setVisibility(View.GONE);
			mItemTextView4.setVisibility(View.GONE);
			mItemTextView5.setVisibility(View.GONE);
			mItemTextView6.setVisibility(View.GONE);
			mItemTextView7.setVisibility(View.GONE);
			mItemTextView8.setVisibility(View.GONE);
			mItemTextView9.setVisibility(View.GONE);
			break;
		}

		mPrisonList.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_LEFT:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						setSelect(mCurrentFocus);
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBtn1.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						mCurrentFocus = 1;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBtn2.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						mCurrentFocus = 2;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBtn3.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						mCurrentFocus = 3;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBtn4.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						mCurrentFocus = 4;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBtn5.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						mCurrentFocus = 5;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBtn6.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						mCurrentFocus = 6;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBtn7.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						mCurrentFocus = 7;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		mBtn8.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						mCurrentFocus = 8;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	/**
	 * 设置焦点
	 * 
	 * @param focus
	 */
	protected void setSelect(int focus) {
		switch (focus) {
		case 1:
			mBtn1.requestFocus();
			mBtn1.requestLayout();
			break;
		case 2:
			mBtn2.requestFocus();
			mBtn2.requestLayout();
			break;
		case 3:
			mBtn3.requestFocus();
			mBtn3.requestLayout();
			break;
		case 4:
			mBtn4.requestFocus();
			mBtn4.requestLayout();
			break;
		case 5:
			mBtn5.requestFocus();
			mBtn5.requestLayout();
			break;
		case 6:
			mBtn6.requestFocus();
			mBtn6.requestLayout();
			break;
		case 7:
			mBtn7.requestFocus();
			mBtn7.requestLayout();
			break;
		case 8:
			mBtn8.requestFocus();
			mBtn8.requestLayout();
			break;

		default:
			break;
		}
	}

	private void setValue() {
		mMainmenuImageView.setImageResource(R.drawable.affairs_img);
		mMainmenuTitleTextView.setText(getActivity().getString(
				R.string.affairs_txt));
		mWeatherTimeContainer.setVisibility(View.GONE);
		mPrisonContrainer.setVisibility(View.VISIBLE);

		Intent intent = getActivity().getIntent();
		if (intent != null) {
			mUsername = intent.getStringExtra("username");
			String nickname = intent.getStringExtra("nickname");
			mUserContainer.setVisibility(View.VISIBLE);
			if (nickname != null) {
				mUserNumberTextView.setText("用户：" + nickname);
			} else {
				mUserNumberTextView.setText("用户：" + mUsername);
			}
			mUserCode = intent.getStringExtra("usercode");
		}
		

		String mainLogo = ConfigMgr.getInstance().getBeanVaule(
				CCViewConfig.MAINMENU_LOGO);
		if (mainLogo != null) {
			BitmapUtil.setImage(mainLogo, mMainLoginImageView); 
		}

		// // 请求网络数据
		// UIDataller.getDataller().getPrisonUserSearch(getActivity(),
		// mUsername,
		// mUserCode, 1, new IUserSearchOnSuccessCB() {
		//
		// @Override
		// public void onSuccess(String result, int type) {
		//
		// }
		// });
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}

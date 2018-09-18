/**
 * 作者：   Johnson
 * 日期：   2014年6月23日下午7:44:41
 * 包名：    com.hhzt.iptv.lvb_x.mgr
 * 工程名：LVB_X
 * 文件名：FragmentMgr.java
 */
package com.hhzt.iptv.lvb_x.mgr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.hhzt.iptv.lvb_x.Constant;

public class FragmentMgr {
	private static final int ADD = 1;
	private static final int REMOVE = 2;
	private static final int REPLACE = 3;

	public static void add(FragmentActivity activity, boolean addToStack, int containerId, Fragment fragment, int tag) {
		action(ADD, activity, addToStack, containerId, fragment, tag);
	}

	public static void remove(FragmentActivity activity, boolean addToStack, int containerId, Fragment fragment,int tag) {
		action(REMOVE, activity, addToStack, containerId, fragment, tag);
	}

	public static void replace(FragmentActivity activity, boolean addToStack, int containerId, Fragment fragment,int tag) {
		action(REPLACE, activity, addToStack, containerId, fragment, tag);
	}

	private static void action(int antionType, FragmentActivity activity, boolean addToStack, int containerId, Fragment fragment, int tag) {
		// 设置参数
		Bundle bundle = new Bundle();
		bundle.putInt(Constant.IPTV_SYS_FRAGMENT_TAG, tag);
		fragment.setArguments(bundle);
		// 实例化fragment事务管理器
		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		switch (antionType) {
		case ADD:
			// 用新创建的fragment来代替fragment_container
			transaction.add(containerId, fragment);

			break;
		case REMOVE:
			// 用新创建的fragment来代替fragment_container
			transaction.remove(fragment);
			break;
		case REPLACE:
			// 用新创建的fragment来代替fragment_container
			transaction.replace(containerId, fragment);
			break;
		default:
			break;
		}

		if (addToStack) {
			// 添加进栈中
			transaction.addToBackStack(null);
		}
		// 提交事务
		transaction.commit();
	}
}

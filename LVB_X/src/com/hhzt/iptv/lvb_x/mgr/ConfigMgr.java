package com.hhzt.iptv.lvb_x.mgr;

import java.util.ArrayList;

import com.hhzt.iptv.lvb_x.model.ViewConfigBean;
import com.hhzt.iptv.lvb_x.utils.StringUtil;

public class ConfigMgr {
	private static ConfigMgr mConfigMgr = new ConfigMgr();
	private ArrayList<ViewConfigBean> mConfigBeans = new ArrayList<ViewConfigBean>();

	private ConfigMgr() {

	}

	public static ConfigMgr getInstance() {
		if (null == mConfigMgr) {
			synchronized (ConfigMgr.class) {
				if (null == mConfigMgr) {
					mConfigMgr = new ConfigMgr();
				}
			}
		}
		return mConfigMgr;
	}

	public void addConfigs(ArrayList<ViewConfigBean> beans) {
		mConfigBeans.clear();
		mConfigBeans.addAll(beans);
	}

	public void addConfig(ViewConfigBean bean) {
		mConfigBeans.add(bean);
	}

	public void removeConfig(ViewConfigBean bean) {
		mConfigBeans.remove(bean);
	}

	public void reset() {
		mConfigBeans.clear();
	}

	public ArrayList<ViewConfigBean> getAllConfigs() {
		return mConfigBeans;
	}

	public String getBeanVaule(String name) {
		if (StringUtil.isEmpty(name)) {
			return null;
		}

		String value = null;
		for (ViewConfigBean beans : mConfigBeans) {
			if (name.equalsIgnoreCase(beans.getName())) {
				value = beans.getValue();
				break;
			}
		}

		return value;
	}

	public String getBeanName(String value) {
		if (StringUtil.isEmpty(value)) {
			return null;
		}

		String name = null;
		for (ViewConfigBean beans : mConfigBeans) {
			if (value.equalsIgnoreCase(beans.getName())) {
				name = beans.getValue();
				break;
			}
		}

		return name;
	}

	public int getNumberByHeadName(String headName) {
		int number = 0;
		for (ViewConfigBean beans : mConfigBeans) {
			if (!StringUtil.isEmpty(beans.getName()) && beans.getName().contains(headName)) {
				++number;
			}
		}

		return number;
	}
}

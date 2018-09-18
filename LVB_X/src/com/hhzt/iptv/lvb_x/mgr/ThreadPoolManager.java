/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 * @author Jhonson
 * 2013-8-16 下午3:22:41
 */
package com.hhzt.iptv.lvb_x.mgr;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
	private ExecutorService service;

	/**
	 * 
	 */
	private ThreadPoolManager() {
		service = Executors.newCachedThreadPool();
	}

	private static ThreadPoolManager manager;

	/**
	 * @return
	 */
	public static ThreadPoolManager getInstance() {
		if (manager == null) {
			manager = new ThreadPoolManager();
		}
		return manager;
	}

	/**
	 * @param runnable
	 */
	public void addTask(Runnable runnable) {
		service.submit(runnable);
	}

}

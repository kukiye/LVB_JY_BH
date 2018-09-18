package com.hhzt.iptv.lvb_x.utils;

import java.io.File;

public class FileUtils {
	public static void makeDir(File dir) {
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}
}

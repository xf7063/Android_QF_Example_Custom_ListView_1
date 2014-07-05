package com.qf.example.custom.listview1.util;

import android.os.Environment;

public class CommonKey {
	/**
	 * 超时时间
	 */
	public static final int TIMEOUT = 200000;
	
	public static final String SERVER_URL = "http://192.168.0.100:8080/";
	
	/**
	 * SD卡下载文件存储路径
	 */
	public static final String SD_DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + "/qf3gdownload/";
}

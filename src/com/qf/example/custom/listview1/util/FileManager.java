package com.qf.example.custom.listview1.util;

public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "com.qf/files/";
		} else {
			return CommonUtil.getRootFilePath() + "com.qf/files/";
		}
	}
}

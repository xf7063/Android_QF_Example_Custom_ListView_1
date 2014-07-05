package com.qf.example.custom.listview1.entity;

import java.io.Serializable;

public class Soft implements Serializable {
	private static final long serialVersionUID = 4911494005613727861L;
	private String softName;
	private String softDesc;
	private String softImgUrl;
	private String softDownUrl;
	private int softCount;
	private int softSize;
	private boolean downloading;

	public boolean isDownloading() {
		return downloading;
	}

	public void setDownloading(boolean downloading) {
		this.downloading = downloading;
	}

	public String getSoftName() {
		return softName;
	}

	public void setSoftName(String softName) {
		this.softName = softName;
	}

	public String getSoftDesc() {
		return softDesc;
	}

	public void setSoftDesc(String softDesc) {
		this.softDesc = softDesc;
	}

	public String getSoftImgUrl() {
		return softImgUrl;
	}

	public void setSoftImgUrl(String softImgUrl) {
		this.softImgUrl = softImgUrl;
	}

	public String getSoftDownUrl() {
		return softDownUrl;
	}

	public void setSoftDownUrl(String softDownUrl) {
		this.softDownUrl = softDownUrl;
	}

	public int getSoftCount() {
		return softCount;
	}

	public void setSoftCount(int softCount) {
		this.softCount = softCount;
	}

	public int getSoftSize() {
		return softSize;
	}

	public void setSoftSize(int softSize) {
		this.softSize = softSize;
	}
}

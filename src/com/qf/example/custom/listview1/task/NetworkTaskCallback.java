package com.qf.example.custom.listview1.task;

import java.util.List;

public interface NetworkTaskCallback<T> {
	public List<T> doInBackground();
	public void onPostExcute(List<T> list);
}

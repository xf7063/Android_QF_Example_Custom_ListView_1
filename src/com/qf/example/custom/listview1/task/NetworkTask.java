package com.qf.example.custom.listview1.task;

import java.util.List;

import android.os.AsyncTask;

public class NetworkTask<T> extends AsyncTask<Void, Void, List<T>> {
	private NetworkTaskCallback<T> callback;
	
	public NetworkTask(NetworkTaskCallback<T> callback) {
		this.callback = callback;
	}

	@Override
	protected List<T> doInBackground(Void... params) {
		return callback.doInBackground();
	}
	
	@Override
	protected void onPostExecute(List<T> result) {
		callback.onPostExcute(result);
	}
	
}

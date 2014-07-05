package com.qf.example.custom.listview1.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.qf.example.custom.listview1.util.CommonKey;

public class DownloadFileTask extends AsyncTask<String, Integer, Integer> {
	private Context context;
	private String fileName;
	private ProgressBar progressBar;
	
	private String downloadFileName;
	private File downloadFile = null;
	
	private int progressLen = -1;
	
	public DownloadFileTask(Context context, String fileName) {
		this.context = context;
		this.fileName = fileName;
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		AndroidHttpClient client = null;
		InputStream inputStream= null;
		FileOutputStream fos = null;
		
		try {
			// 创建一个AndroidHttpClient
			client = AndroidHttpClient.newInstance("Android");
			if (client != null) {
				// 创建一个Get请求
				HttpGet httpGet = new HttpGet(params[0]);
				// 发送Get请求
				HttpResponse httpResponse = client.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = httpResponse.getEntity();
					if (entity != null) {
						// 获取文件大小
						long length = entity.getContentLength();
						inputStream = entity.getContent();
						// SD卡存在，则将文件放入SD卡中
						if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
							// 创建文件下载路径
							downloadFileName = CommonKey.SD_DOWNLOAD_PATH + fileName;
							// 创建文件目录
							File file = new File(CommonKey.SD_DOWNLOAD_PATH);
							if (!file.exists()) {
								file.mkdir();
							}
						} else {
							// 将文件下载到系统目录
							downloadFileName = "//data//data//" + context.getPackageName() + fileName;
						}
						
						// 创建下载文件
						downloadFile = new File(downloadFileName);
						fos = new FileOutputStream(downloadFile, false);
						
						byte[] buf = new byte[1024];
						int count = 0, len = -1;
						while ((len = inputStream.read(buf)) != -1) {
							fos.write(buf, 0, len);
							count += len;
							progressLen = (int) ((count * 100) / length); // 计算下载量
							publishProgress(progressLen);
							fos.flush();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (client != null) {
					client.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return progressLen;
	}
	
	/**
	 * 更新下载进度
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		progressBar.setProgress(values[0]);
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		if (result == 100) {
			Toast.makeText(context, "下载完成", Toast.LENGTH_LONG).show();
		}
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

}

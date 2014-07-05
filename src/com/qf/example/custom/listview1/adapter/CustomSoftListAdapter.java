package com.qf.example.custom.listview1.adapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qf.example.custom.listview1.activity.R;
import com.qf.example.custom.listview1.cache.ImageLoader;
import com.qf.example.custom.listview1.entity.Soft;
import com.qf.example.custom.listview1.task.DownloadFileTask;
import com.qf.example.custom.listview1.util.CommonKey;

/**
 * 自定义下载列表适配器
 * 实现OnClickListener接口，设置View中控件的事件
 * @author Administrator
 * 
 */
public class CustomSoftListAdapter extends BaseAdapter implements OnClickListener {
	private static final String TAG = "CustomSoftListAdapter";
	private boolean mBusy = false;
	private ViewHolder holder = null;
	private DownloadFileTask task;
	
	// 这里设置线程池，可以开启同时异步的任务（如同时下载），这里开了3个异步任务
	private ExecutorService executorService = Executors.newFixedThreadPool(3);

	public void setFlagBusy(boolean busy) {
		this.mBusy = busy;
	}

	private ImageLoader imageLoader;
	private Context context;
	private List<Soft> data;

	public CustomSoftListAdapter(Context context, List<Soft> data) {
		this.context = context;
		this.data = data;
		imageLoader = new ImageLoader(context);
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_custom_download, null);

			holder = new ViewHolder();
			holder.txSoftName = (TextView) convertView.findViewById(R.id.txSoftName);
			holder.txSoftOther = (TextView) convertView.findViewById(R.id.txSoftOther);
			holder.txSoftDesc = (TextView) convertView.findViewById(R.id.txSoftDesc);
			holder.imgSoftImage = (ImageView) convertView.findViewById(R.id.imgSoftImage);
			holder.btnDownload = (Button) convertView.findViewById(R.id.btnDownload);
			holder.btnDownload.setOnClickListener(this);
			holder.proDownload = (ProgressBar) convertView.findViewById(R.id.proDownload);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 填充ListView

		String url = "";
		url = data.get(position % data.size()).getSoftImgUrl();

		Soft soft = data.get(position);
		// 把数据传到按钮中
		holder.btnDownload.setTag(soft);

		holder.txSoftName.setText(soft.getSoftName());
		holder.txSoftOther.setText(String.format("共有%s人下载    %sMB", soft.getSoftCount(), soft.getSoftSize() / 1024.));
		holder.txSoftDesc.setText(soft.getSoftDesc());
		
		// 判断组件是否生效
		if (soft.isDownloading()) {
			holder.proDownload.setVisibility(View.VISIBLE);
			holder.txSoftOther.setVisibility(View.INVISIBLE);
		} else {
			holder.proDownload.setVisibility(View.INVISIBLE);
			holder.txSoftOther.setVisibility(View.VISIBLE);
		}
		
		// 设置图片缓存
		holder.imgSoftImage.setImageResource(R.drawable.ic_launcher);
		if (!mBusy) {
			imageLoader.DisplayImage(url, holder.imgSoftImage, false);
		} else {
			imageLoader.DisplayImage(url, holder.imgSoftImage, false);
		}

		return convertView;
	}

	class ViewHolder {
		public TextView txSoftName;
		public TextView txSoftDesc;
		public TextView txSoftOther;
		public ImageView imgSoftImage;
		public Button btnDownload;
		public ProgressBar proDownload;
	}

	/**
	 * 重写OnClickListener的onClick方法
	 */
	@Override
	public void onClick(View v) {
		// 获取组件，来设置不同的事件
		switch (v.getId()) {
		case R.id.btnDownload:
			Soft s = (Soft) v.getTag();
			// 到父类中找到设置的ViewHolder
			ViewHolder holder = (ViewHolder) ((View) v.getParent()).getTag();
			// 基本的条件判断
			if (s.isDownloading()) {
				holder.proDownload.setVisibility(View.INVISIBLE);
				holder.txSoftOther.setVisibility(View.VISIBLE);
				s.setDownloading(false);
			} else {
				holder.proDownload.setVisibility(View.VISIBLE);
				holder.txSoftOther.setVisibility(View.INVISIBLE);
				s.setDownloading(true);
				
				task = new DownloadFileTask(context, "yingyutucao.mp4");
				task.setProgressBar(holder.proDownload);
				task.executeOnExecutor(executorService, CommonKey.SERVER_URL + "download/yingyutucao.mp4");
			}
			break;
		}
	}

}

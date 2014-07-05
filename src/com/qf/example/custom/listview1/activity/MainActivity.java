package com.qf.example.custom.listview1.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.qf.example.custom.listview1.adapter.CustomSoftListAdapter;
import com.qf.example.custom.listview1.entity.Soft;
import com.qf.example.custom.listview1.task.NetworkTask;
import com.qf.example.custom.listview1.task.NetworkTaskCallback;
import com.qf.example.custom.listview1.util.CommonKey;
import com.qf.example.custom.listview1.util.JSONHelper;

public class MainActivity extends Activity {
	private ListView lvSoft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initUI();
		initLvSofData();
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		lvSoft = (ListView) findViewById(R.id.lvSoft);
	}

	/**
	 * 初始化下载列表数据
	 * 
	 * @throws JSONException
	 */
	private void initLvSofData() {
		NetworkTask<Soft> task = new NetworkTask<Soft>(new NetworkTaskCallback<Soft>() {
			@Override
			public List<Soft> doInBackground() {
				List<Soft> list = new ArrayList<Soft>();

				try {
					JSONArray array = JSONHelper.getJsonArray(CommonKey.SERVER_URL + "Java_QF_Lusifer_WebData/servlet/QFListDataServlet");
					if (array != null && array.length() > 0) {
						Soft soft = null;

						JSONObject object = null;

						for (int i = 0; i < array.length(); i++) {
							object = array.getJSONObject(i);

							soft = new Soft();
							soft.setSoftCount(object.getInt("softCount"));
							soft.setSoftDesc(object.getString("softDesc"));
							soft.setSoftDownUrl(object.getString("softDownUrl"));
							soft.setSoftImgUrl(object.getString("softImgUrl"));
							soft.setSoftName(object.getString("softName"));
							soft.setSoftSize(object.getInt("softSize"));
							list.add(soft);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return list;
			}

			@Override
			public void onPostExcute(List<Soft> list) {
				CustomSoftListAdapter adapter = new CustomSoftListAdapter(MainActivity.this, list);
				lvSoft.setAdapter(adapter);
			}
		});
		
		task.execute();
	}

}

package com.qf.example.custom.listview1.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;

public class JSONHelper {
	public static JSONArray getJsonArray(String url) {
		try {
			StringBuffer json = new StringBuffer();

			HttpClient client = new DefaultHttpClient();
			HttpParams params = client.getParams();
			HttpConnectionParams
					.setConnectionTimeout(params, CommonKey.TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, CommonKey.TIMEOUT);
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);

			if (response != null) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(entity.getContent()));
					String line = null;
					while ((line = reader.readLine()) != null) {
						json.append(line + "/n");
					}
					reader.close();
				}
			}

			return new JSONArray(json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

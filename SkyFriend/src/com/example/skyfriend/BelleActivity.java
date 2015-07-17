package com.example.skyfriend;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.skyfriend.adapter.ImageBaseAdapter;
import com.example.skyfriend.adapter.ImageRequestAdapter;
import com.example.skyfriend.util.Constants;
import com.example.skyfriend.util.GetPostUtil;
import com.example.skyfriend.util.StringUtil;
import com.example.skyfriend.util.ToastUtil;
import com.example.skyfriend.util.VolleyUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.GridView;

public class BelleActivity extends Activity{
	private String[] imageUrlArray=new String[30];
	private GridView gvBelle;
	private String json;
	
	private Handler h1=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				gvBelle = (GridView)findViewById(R.id.gv_belle);
				ImageBaseAdapter adapter = new ImageRequestAdapter(BelleActivity.this,imageUrlArray);
				gvBelle.setAdapter(adapter);
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sf_belle);
		new Thread(
			new Runnable() {
				
				@Override
				public void run() {
					getJsonByUtil();
				}
			}).start();
		
//		getJsonByVolley();//通过Volley返回json数据
		
	}
	

	private void getJsonByUtil() {
		json=GetPostUtil.sendGet("http://image.baidu.com/channel/listjson?pn=0&rn=30&tag1=%E7%BE%8E%E5%A5%B3&tag2=%E5%85%A8%E9%83%A8&ie=utf8");
//Log.i("lala","getJsonByUtil："+json);
		try {
			JSONObject jo=new JSONObject(json);
			JSONArray ja=jo.getJSONArray("data");
			
			for (int i = 0; i < ja.length(); i++) {
				JSONObject series = ja.getJSONObject(i);
				imageUrlArray[i]=series.getString("share_url");
//				Map<String, String> BelleMap = new HashMap<String, String>();
//				BelleMap.put("name", series.getString("name"));
//				Log.i("lala","打印路径："+imageUrlArray[i]);
				h1.sendEmptyMessage(1);
			}
			
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void getJsonByVolley() {
		// 发起请求
		JsonObjectRequest request = new JsonObjectRequest(StringUtil.preUrl(Constants.DEFAULT_JSON_REQUEST_URL), null,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
Log.i("lala", "getJsonByVolley:"+response);
						try {
							if (!response.has("result")) {
								return;
							}
							JSONObject result = response.getJSONObject("result");
							if (!result.has("fctlist")) {
								return;
							}
							JSONArray factoryArray = result.getJSONArray("fctlist");
							if (factoryArray.length() == 0) {
								return;
							}
							JSONObject factory = factoryArray.getJSONObject(0);
							if (!factory.has("serieslist")) {
								return;
							}
							JSONArray seriesArray = factory.getJSONArray("serieslist");

							for (int i = 0; i < seriesArray.length(); i++) {
								JSONObject series = seriesArray.getJSONObject(i);
								Map<String, String> seriesMap = new HashMap<String, String>();

								seriesMap.put("name", series.getString("name"));
								seriesMap.put("level", "级别："+series.getString("levelname"));
								seriesMap.put("price", "价格："+series.getString("price"));
							}
							
						} catch (Exception e) {
							ToastUtil.showToast(BelleActivity.this, "请求失败");
						}

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						ToastUtil.showToast(BelleActivity.this, "请求失败");
					}
				});	
		VolleyUtil.getQueue(BelleActivity.this).add(request);
	}

}

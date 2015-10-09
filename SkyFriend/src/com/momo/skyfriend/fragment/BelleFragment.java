package com.momo.skyfriend.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.skyfriend.R;
import com.momo.skyfriend.activity.ActivityBelleDetail;
import com.momo.skyfriend.adapter.ImageBaseAdapter;
import com.momo.skyfriend.adapter.ImageRequestAdapter;
import com.momo.skyfriend.util.GetPostUtil;

public class BelleFragment extends FragmentBase{
	View v;
	private String[] imageUrlArray=new String[30];//下载图片数量
	private GridView gvBelle;
	private String json;
	//还是用的Volley框架发起的请求哟
	private Handler h1=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				gvBelle = (GridView)v.findViewById(R.id.gv_belle);
				ImageBaseAdapter adapter = new ImageRequestAdapter(getActivity(),imageUrlArray);
				gvBelle.setAdapter(adapter);
				gvBelle.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent=new Intent();
						intent.putExtra("imageUrl", imageUrlArray[position]);
						intent.setClass(getActivity(), ActivityBelleDetail.class);
						startActivity(intent);
					}
				});
			}
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.sf_belle, container,false);
		this.v=v;
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						getJsonByUtil();
					}
				}).start();
		return v;
	}
	
	
	private void getJsonByUtil() {
		json=GetPostUtil.sendGet("http://image.baidu.com/channel/listjson?pn=0&rn=30&tag1=%E7%BE%8E%E5%A5%B3&tag2=%E5%85%A8%E9%83%A8&ie=utf8");
Log.i("lala","getJsonByUtil："+json);
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
}

package com.momo.skyfriend.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.anim;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.skyfriend.R;
import com.momo.skyfriend.activity.ActivityBelleDetail;
import com.momo.skyfriend.adapter.ImageBaseAdapter;
import com.momo.skyfriend.adapter.ImageRequestAdapter;
import com.momo.skyfriend.util.GetPostUtil;

public class BelleFragment extends FragmentBase{
	private Spinner tagSpinner;//大类
	private Spinner ftagSpinner;//小类
	ArrayAdapter<String> tagAdapter = null;  //大类适配器
    ArrayAdapter<String> ftagAdapter = null;    //小类适配器
    private Button btnOk;//确定
	
	View v;//当前视图
	private String[] imageUrlArray=new String[30];//下载图片数量
	private GridView gvBelle;
	private String json;
	
	String [] tags=new String[]{"明星","美女"};
	String [][] ftags=new String [][]{{"男明星","女明星"},{"小清新","素颜","可爱"}};
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
		setSpinner();
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						try {
							getJsonByUtil();
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}).start();
		return v;
	}
	
	/**
	 * 初始化下拉框查找
	 */
	private void setSpinner() {
		
		tagSpinner=(Spinner) v.findViewById(R.id.spin_tag1);
		ftagSpinner=(Spinner) v.findViewById(R.id.spin_ftags);
		btnOk=(Button) v.findViewById(R.id.btn_ok);
		
		//绑定适配器和值
		tagAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item	, tags);
		tagSpinner.setAdapter(tagAdapter);
//		tagSpinner.setSelection(1);//设置默认选中项
		ftagAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ftags[0]);
		ftagSpinner.setAdapter(ftagAdapter);
		
		//大类下拉框监听
		tagSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ftagAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ftags[position]);
				ftagSpinner.setAdapter(ftagAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(
						new Runnable() {
							@Override
							public void run() {
								try {
									getJsonByUtil();
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
								}
							}
						}).start();
			}
		});
		
	}

	
	
	private void getJsonByUtil() throws UnsupportedEncodingException {
		String tag1=tagSpinner.getSelectedItem().toString();
		String tag2="全部";
		String ftags=ftagSpinner.getSelectedItem().toString();
		
		String url="http://image.baidu.com/channel/listjson?pn=0&rn=30&tag1="
				+URLEncoder.encode(tag1, "utf8")
				+"&tag2="+URLEncoder.encode(tag2, "utf8")
				+"&ftags="+URLEncoder.encode(ftags, "utf8")
				+"&ie=utf8";
		json=GetPostUtil.sendGet(url);
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

package com.momo.skyfriend.activity;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.skyfriend.R;
import com.momo.skyfriend.util.VolleyUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ActivityBelleDetail extends ActivityBase {
	ImageView iv_belle;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.belle_image);
		setView();
		Intent intent=getIntent();
		String data=intent.getStringExtra("imageUrl");
		
		
		ImageRequest request=new ImageRequest(data, new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap bitmap) {
				iv_belle.setImageBitmap(bitmap);
			}
		}, 0, 0, Config.RGB_565, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				iv_belle.setImageResource(R.drawable.ic_empty);
			}
		});
		
		request.setTag(iv_belle);
		VolleyUtil.getQueue(this).add(request);
		
		Log.i("lala", "当前点击了"+data);
	}

	private void setView() {
		iv_belle=(ImageView) findViewById(R.id.iv_belle);
	}
	
}

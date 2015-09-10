package com.momo.skyfriend.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.example.skyfriend.R;
import com.momo.skyfriend.util.GetPostUtil;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class RobotActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sf_robot);
		getRobot();
	}

	private void getRobot() {
	    try {
	    	String APIKEY = "a5b6f341d7981afc732dec4b3cb00379"; 
			String INFO = URLEncoder.encode("北京今日天气", "utf-8");
			String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
Log.i("lala", "getURL:"+getURL);			
			String a=GetPostUtil.sendGet(getURL);
Log.i("lala", "打印的json:"+a);
	    } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}


}

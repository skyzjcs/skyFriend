package com.momo.skyfriend.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.example.skyfriend.R;
import com.momo.skyfriend.util.GetPostUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RobotFragment extends FragmentBase {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.sf_robot, null);
		getRobot();
		return v;
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

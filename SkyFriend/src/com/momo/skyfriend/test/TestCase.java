package com.momo.skyfriend.test;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.skyfriend.R;
import com.momo.skyfriend.util.GetPostUtil;
import com.momo.skyfriend.util.HttpClients;
import com.momo.skyfriend.util.HttpResponseMessage;

public class TestCase extends AndroidTestCase{
	
	/*@Test
	public void testJson() throws Exception{
		String tag1="明星";
		String tag2="全部";
		
		String url="http://image.baidu.com/channel/listjson?pn=0&rn=30&tag1="+URLEncoder.encode(tag1, "utf8")+"&tag2="+URLEncoder.encode(tag2, "utf8")+"&ie=utf8";
		
		String json=GetPostUtil.sendGet(url);
		Log.i("lala","getJsonByUtil："+json);
	}*/
	
	@Test
	public void testFace(){
		Bitmap bitmap=BitmapFactory.decodeResource(getContext().getResources(), R.drawable.fanbinbin);
		String url="http://apicn.faceplusplus.com/v2/detection/detect";
		HttpClients httpClients = new HttpClients(getContext());
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("api_key", "cb60f456f34cc8d4aa97c21db6a319aa"));
		params.add(new BasicNameValuePair("api_secret", "as2hMy12MhMX4k-3CxGaNqkIJYY6rAtT"));
		params.add(new BasicNameValuePair("url", "http://faceplusplus.com/static/img/demo/1.jpg"));
//		params.add("img", bitmap);
		params.add(new BasicNameValuePair("attribute", "age"));
		HttpResponseMessage httpResponseMessage = httpClients.doPost(url, params);
		Log.i("lala","getFace："+httpResponseMessage.getData());
	}
}

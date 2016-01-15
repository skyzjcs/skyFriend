package com.momo.skyfriend.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Test;

import com.momo.skyfriend.util.GetPostUtil;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestCase extends AndroidTestCase{
	
	@Test
	public void testJson() throws Exception{
		String tag1="明星";
		String tag2="全部";
		
		String url="http://image.baidu.com/channel/listjson?pn=0&rn=30&tag1="+URLEncoder.encode(tag1, "utf8")+"&tag2="+URLEncoder.encode(tag2, "utf8")+"&ie=utf8";
		
		String json=GetPostUtil.sendGet(url);
		Log.i("lala","getJsonByUtil："+json);
	}
}
